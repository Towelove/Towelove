package blossom.project.towelove.loves.service.Impl;

import blossom.project.towelove.common.page.PageResponse;
import blossom.project.towelove.framework.log.client.LoveLogClient;
import blossom.project.towelove.framework.oss.service.FileUploadService;
import blossom.project.towelove.loves.entity.LoveAlbum;
import blossom.project.towelove.loves.mapper.LoveAlbumMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import blossom.project.towelove.loves.service.LoveAlbumService;
import blossom.project.towelove.common.response.love.album.LoveAlbumResponse;
import blossom.project.towelove.common.request.loves.album.LoveAlbumCreateRequest;
import blossom.project.towelove.common.request.loves.album.LoveAlbumPageRequest;
import blossom.project.towelove.common.request.loves.album.LoveAlbumUpdateRequest;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


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

    private final FileUploadService fileUploadService;
    @Override
    public LoveAlbumResponse createLoveAlbum(List<MultipartFile> files,
                                             LoveAlbumCreateRequest createRequest) {
        List<String> uploadFiles = fileUploadService.uploadFiles(files, null, 0);
        System.out.println(uploadFiles);
        return null;
    }
    @Override
    public LoveAlbumResponse getLoveAlbumById(Long LoveAlbumId) {
        return null;
    }

    @Override
    public PageResponse<LoveAlbumResponse> pageQueryLoveAlbum(LoveAlbumPageRequest requestParam) {
        return null;
    }

    @Override
    public LoveAlbumResponse updateLoveAlbum(LoveAlbumUpdateRequest updateRequest) {
        return null;
    }

    @Override
    public Boolean deleteLoveAlbumById(Long LoveAlbumId) {
        return null;
    }

    @Override
    public Boolean batchDeleteLoveAlbum(List<Long> ids) {
        return null;
    }

}

