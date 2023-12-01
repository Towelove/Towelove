package blossom.project.towelove.loves.service.Impl;

import blossom.project.towelove.common.exception.EntityNotFoundException;
import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.common.response.love.album.LoveAlbumPageResponse;
import blossom.project.towelove.framework.log.client.LoveLogClient;
import blossom.project.towelove.framework.oss.service.OssService;
import blossom.project.towelove.loves.convert.LoveAlbumConvert;
import blossom.project.towelove.loves.entity.LoveAlbum;
import blossom.project.towelove.loves.mapper.LoveAlbumMapper;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import blossom.project.towelove.loves.service.LoveAlbumService;
import blossom.project.towelove.common.response.love.album.LoveAlbumDetailResponse;
import blossom.project.towelove.common.request.loves.album.LoveAlbumCreateRequest;
import blossom.project.towelove.common.request.loves.album.LoveAlbumPageRequest;
import blossom.project.towelove.common.request.loves.album.LoveAlbumUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * (LoveAlbum) 表服务实现类
 *
 * @author 张锦标
 * @since 2023-11-30 16:20:46
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LoveAlbumServiceImpl extends ServiceImpl<LoveAlbumMapper, LoveAlbum> implements LoveAlbumService {

    private final LoveAlbumMapper loveAlbumMapper;

    private final LoveLogClient logClient;

    private final OssService oSSService;


    /**
     * 同步上传有序、但是慢
     * 异步上传无序、但是速度快
     * 因为
     * @param files
     * @param createRequest
     * @return
     */
    @Override
    public LoveAlbumDetailResponse createLoveAlbum(List<MultipartFile> files, LoveAlbumCreateRequest createRequest) {
        List<String> uploadFiles = oSSService.uploadFilesAsync(files, null, 0);
        if (CollectionUtil.isEmpty(uploadFiles)) {
            log.info("the returned fileNames is empty");
            return null;
        }

        // 使用 Optional 来处理可能为 null 的情况
        return Optional.ofNullable(createRequest)
                .map(req -> {
                    // 上传封面
                    if (req.getStatus().equals(0) && uploadFiles.size() == 1) {
                        return handleCoverUpload(req, uploadFiles.get(0));
                    }
                    // 上传九宫格图片
                    else if (req.getStatus().equals(1)) {
                        return handleGridUpload(req, uploadFiles);
                    }
                    return null;
                })
                .orElse(null);
    }

    /**
     * 处理封面照片
     * @param request 上传的文件相册信息
     * @param fileUrl 封面URL
     * @return
     */
    private LoveAlbumDetailResponse handleCoverUpload(LoveAlbumCreateRequest request, String fileUrl) {
        LoveAlbum loveAlbum = LoveAlbumConvert.INSTANCE.convert(request);
        loveAlbum.setAlbumCoverUrl(fileUrl);
        loveAlbumMapper.insert(loveAlbum);
        return LoveAlbumConvert.INSTANCE.convert(loveAlbum);
    }

    /**
     * 优雅处理文件上传
     * @param request 相册信息
     * @param uploadFiles 上传的照片信息
     * @return
     */
    private LoveAlbumDetailResponse handleGridUpload(LoveAlbumCreateRequest request, List<String> uploadFiles) {
        // 使用 Optional 进行更优雅的 null 检查
        return Optional.ofNullable(request.getId())
                .map(id -> {
                    LoveAlbum loveAlbum = loveAlbumMapper.selectById(id);
                    TreeMap<Integer, String> photoDesc = Optional.ofNullable(loveAlbum.getPhotoDesc()).orElse(new TreeMap<>());

                    // 使用 Stream API 优化照片描述的构建
                    IntStream.range(0, uploadFiles.size())
                            .forEach(i -> photoDesc.put(photoDesc.size() + i, uploadFiles.get(i)));

                    loveAlbum.setPhotoDesc(photoDesc);
                    loveAlbumMapper.updateById(loveAlbum);
                    return LoveAlbumConvert.INSTANCE.convert(loveAlbum);
                })
                .orElseGet(() -> {
                    log.info("the create id can not be null!");
                    return null;
                });
    }

    /**
     * 根据图片索引位置删除图片
     * @param id 相册id
     * @param imageIndex 图片索引位置0-8
     */
    @Override
    public void deleteImageFromAlbum(Long id, Integer imageIndex) {
        Optional.ofNullable(loveAlbumMapper.selectById(id))
                .map(LoveAlbum::getPhotoDesc)
                .ifPresent(photoDesc -> {
                    if (photoDesc.containsKey(imageIndex)) {
                        String removedUrl = photoDesc.remove(imageIndex);
                        oSSService.removeFiles(removedUrl,null);
                        // 使用 Stream API 重建索引
                        TreeMap<Integer, String> newPhotoDesc = IntStream.range(0, photoDesc.size())
                                .boxed()
                                .collect(Collectors.toMap(
                                        Function.identity(),
                                        i -> new ArrayList<>(photoDesc.values()).get(i),
                                        (e1, e2) -> e1,
                                        TreeMap::new
                                ));

                        // 更新九宫格
                        LoveAlbum loveAlbum = new LoveAlbum();
                        loveAlbum.setId(id);
                        loveAlbum.setPhotoDesc(newPhotoDesc);
                        loveAlbumMapper.updateById(loveAlbum);
                    }
                });
    }


    /**
     * 根据id返回详细的相册信息
     * @param loveAlbumId
     * @return
     */
    @Override
    public LoveAlbumDetailResponse getLoveAlbumById(Long loveAlbumId) {
        //使用optional解决判空
        return Optional.ofNullable(loveAlbumMapper.selectById(loveAlbumId))
                .map(LoveAlbumConvert.INSTANCE::convert)
                .orElseThrow(() -> new EntityNotFoundException("LoveAlbum not found with id: " + loveAlbumId,null,
                        BaseErrorCode.ENTITY_NOT_FOUNT));
    }

    @Override
    public PageResponse<LoveAlbumPageResponse> pageQueryLoveAlbum(LoveAlbumPageRequest requestParam) {

        List<LoveAlbum> loveAlbums = loveAlbumMapper.selectLoveAlbums(requestParam);
        List<LoveAlbumPageResponse> responses = Optional.ofNullable(loveAlbums)
                .map(LoveAlbumConvert.INSTANCE::convert)
                .orElse(Collections.emptyList());

        PageResponse<LoveAlbumPageResponse> response = new PageResponse<>();
        response.setRecords(responses);
        response.setPageNo(requestParam.getPageNo());
        response.setPageSize(requestParam.getPageSize());
        return response;
    }

    @Override
    public LoveAlbumDetailResponse updateLoveAlbum(LoveAlbumUpdateRequest updateRequest) {
        return null;
    }

    @Override
    public Boolean deleteLoveAlbumById(Long loveAlbumId) {
        int affectedRows = loveAlbumMapper.deleteById(loveAlbumId);
        return affectedRows > 0;
    }

    @Override
    public Boolean batchDeleteLoveAlbum(List<Long> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            throw new IllegalArgumentException("IDs list cannot be null or empty");
        }

        int affectedRows = loveAlbumMapper.deleteBatchIds(ids);
        return affectedRows == ids.size();
    }

}


