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

import java.time.LocalDateTime;
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

    /**
     * 创建相册
     * @param createRequest
     * @return
     */
    @Override
    public Long createLoveAlbum(LoveAlbumCreateRequest createRequest) {
        LoveAlbum loveAlbum = LoveAlbumConvert.INSTANCE.convert(createRequest);
        loveAlbumMapper.insert(loveAlbum);
        return loveAlbum.getId();
    }

    /**
     * 修改相册内容
     * 当用户选择上传头像的时候，要求前端首先调用uploadfile方法得到url
     * 然后先存在前端本地，然后用户可以继续上传文件，得到文件urls
     * 最后用户点击保存之后，调用update方法，将所有的数据都给我然后我保存到数据库。
     * 当用户要对某个照片进行删除的时候，直接调用remove方法从minio中进行删除，
     * 并且要删除某张照片会进行提示，点击× 之后会提示确定删除，如果是，那么就直接保存更新
     * 也就是删除后马上要求前端再次调用一个update方法。
     * @param request
     * @return
     */
    @Override
    public LoveAlbumDetailResponse updateLoveAlbum(LoveAlbumUpdateRequest request) {
        LoveAlbum loveAlbum = LoveAlbumConvert.INSTANCE.convert(request);
        loveAlbum.setUpdateTime(LocalDateTime.now());
        loveAlbumMapper.updateById(loveAlbum);
        loveAlbum = loveAlbumMapper.selectById(loveAlbum.getId());
        return LoveAlbumConvert.INSTANCE.convert(loveAlbum);
    }


    /**
     * 根据id返回详细的相册信息
     * @param loveAlbumId
     * @return
     */
    @Override
    public LoveAlbumDetailResponse getLoveAlbumDetailById(Long loveAlbumId) {
        return Optional.ofNullable(loveAlbumMapper.selectById(loveAlbumId))
                .map(LoveAlbumConvert.INSTANCE::convert)
                .orElseThrow(() -> new EntityNotFoundException("LoveAlbum not found with id: " + loveAlbumId, null,
                        BaseErrorCode.ENTITY_NOT_FOUNT));
    }

    /**
     * 分页查询
     * @param requestParam
     * @return
     */
    @Override
    public PageResponse<LoveAlbumPageResponse> pageQueryLoveAlbum(LoveAlbumPageRequest requestParam) {

        requestParam.setPageNo((requestParam.getPageNo() - 1) * requestParam.getPageSize());
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


    /**
     * 删除
     * @param loveAlbumId
     * @return
     */
    @Override
    public Boolean deleteLoveAlbumById(Long loveAlbumId) {
        int affectedRows = loveAlbumMapper.deleteById(loveAlbumId);
        return affectedRows > 0;
    }

}


