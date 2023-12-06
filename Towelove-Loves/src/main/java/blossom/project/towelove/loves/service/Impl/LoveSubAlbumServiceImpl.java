package blossom.project.towelove.loves.service.Impl;

import blossom.project.towelove.common.page.PageResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import blossom.project.towelove.loves.entity.LoveSubAlbum;
import blossom.project.towelove.loves.mapper.LoveSubAlbumMapper;
import blossom.project.towelove.loves.service.LoveSubAlbumService;
import blossom.project.towelove.common.response.love.album.LoveSubAlbumResponse;
import blossom.project.towelove.common.request.loves.album.LoveSubAlbumCreateRequest;
import blossom.project.towelove.common.request.loves.album.LoveSubAlbumPageRequest;
import blossom.project.towelove.common.request.loves.album.LoveSubAlbumUpdateRequest;

import java.util.List;


/**
 * (LoveSubAlbum) 表服务实现类
 *
 * @author 张锦标
 * @since 2023-12-04 13:58:28
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LoveSubAlbumServiceImpl extends ServiceImpl<LoveSubAlbumMapper, LoveSubAlbum> implements LoveSubAlbumService {
 
    private final LoveSubAlbumMapper loveSubAlbumMapper;
    @Override
    public PageResponse<LoveSubAlbumResponse> pageQueryLoveSubAlbum(LoveSubAlbumPageRequest requestParam) {
        return null;
    }

    @Override
    public LoveSubAlbumResponse updateLoveSubAlbum(LoveSubAlbumUpdateRequest updateRequest) {
        return null;
    }


    @Override
    public LoveSubAlbumResponse createLoveSubAlbum(LoveSubAlbumCreateRequest createRequest) {
        return null;
    }
}

