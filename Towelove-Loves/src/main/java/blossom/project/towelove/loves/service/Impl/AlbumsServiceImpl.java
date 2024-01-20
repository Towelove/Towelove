//package blossom.project.towelove.loves.service.Impl;
//
//import blossom.project.towelove.common.constant.Constant;
//import blossom.project.towelove.common.exception.EntityNotFoundException;
//import blossom.project.towelove.common.exception.errorcode.BaseErrorCode;
//import blossom.project.towelove.common.exception.errorcode.IErrorCode;
//import blossom.project.towelove.common.page.PageResponse;
//import blossom.project.towelove.common.request.loves.album.AlbumsPageRequest;
//import blossom.project.towelove.common.response.love.album.AlbumsPageRespDTO;
//import blossom.project.towelove.common.utils.StringUtils;
//import blossom.project.towelove.loves.convert.AlbumConvert;
//import cn.hutool.core.collection.CollectionUtil;
//import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import lombok.extern.slf4j.Slf4j;
//import blossom.project.towelove.loves.entity.Albums;
//import blossom.project.towelove.loves.mapper.AlbumsMapper;
//import blossom.project.towelove.loves.service.AlbumsService;
//import blossom.project.towelove.common.response.love.album.AlbumsRespDTO;
//import blossom.project.towelove.common.request.loves.album.AlbumsCreateRequest;
//import blossom.project.towelove.common.request.loves.album.AlbumsUpdateRequest;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//import java.util.Objects;
//
//
///**
// * @author: ZhangBlossom
// * @date: 2024/1/17 13:06
// * @contact: QQ:4602197553
// * @contact: WX:qczjhczs0114
// * @blog: https://blog.csdn.net/Zhangsama1
// * @github: https://github.com/ZhangBlossom
// */
//@Service
//@Slf4j
//@RequiredArgsConstructor
//public class AlbumsServiceImpl extends ServiceImpl<AlbumsMapper, Albums> implements AlbumsService {
//
//    private final AlbumsMapper albumsMapper;
//
//
//    @Override
//    public AlbumsRespDTO createAlbums(AlbumsCreateRequest createRequest) {
//        Albums albums = AlbumConvert.INSTANCE.convert(createRequest);
//        if (Objects.isNull(albums)) {
//            log.info("the album is null...");
//            return null;
//        }
//        if (!StringUtils.isBlank(createRequest.getPhotoUrls())) {
//            //设定图片数量
//            albums.setPhotoNums(StringUtils.countCharacter(createRequest.getPhotoUrls(), Constant.COMMA));
//        }
//        albumsMapper.insert(albums);
//        AlbumsRespDTO respDTO = AlbumConvert.INSTANCE.convert(albums);
//        return respDTO;
//    }
//
//    @Override
//    public AlbumsRespDTO getAlbumsDetailById(Long albumsId) {
//        Albums albums = albumsMapper.selectById(albumsId);
//        if (Objects.isNull(albums)) {
//            log.info("the album is null...");
//            return null;
//        }
//        AlbumsRespDTO respDTO = AlbumConvert.INSTANCE.convert(albums);
//        return respDTO;
//    }
//
//    @Override
//    public PageResponse<AlbumsPageRespDTO> pageQueryAlbums(AlbumsPageRequest pageRequest) {
//        LambdaQueryWrapper<Albums> lqw = new LambdaQueryWrapper<>();
//        lqw.eq(Albums::getCoupleId, pageRequest.getCoupleId());
//        Page<Albums> page = new Page(pageRequest.getPageNo() - 1, pageRequest.getPageSize());
//        Page<Albums> albumsPage = albumsMapper.selectPage(page, lqw);
//        if (CollectionUtil.isEmpty(albumsPage.getRecords())) {
//            return new PageResponse<>(pageRequest.getPageNo(), pageRequest.getPageSize(), Collections.emptyList());
//        }
//        List<AlbumsPageRespDTO> respDTOList = AlbumConvert.INSTANCE.convert(albumsPage.getRecords());
//        return new PageResponse<>(pageRequest.getPageNo(), pageRequest.getPageSize(), respDTOList);
//    }
//
//    @Override
//    public AlbumsRespDTO updateAlbums(AlbumsUpdateRequest updateRequest) {
//        Albums albums = albumsMapper.selectById(updateRequest.getId());
//        if (Objects.isNull(albums)) {
//            log.error("the album is null");
//            throw new EntityNotFoundException("can not find album whick id is: " + updateRequest.getId()
//                    , BaseErrorCode.ENTITY_NOT_FOUNT);
//        }
//        try {
//            Albums albums1 = AlbumConvert.INSTANCE.convert(updateRequest);
//            if (!StringUtils.isBlank(albums1.getPhotoUrls())) {
//                albums1.setPhotoNums(StringUtils.countCharacter(albums1.getPhotoUrls(), Constant.COMMA));
//            }
//            albumsMapper.updateById(albums1);
//            Albums resp = albumsMapper.selectById(albums.getId());
//            AlbumsRespDTO respDTO = AlbumConvert.INSTANCE.convert(resp);
//            return respDTO;
//        } catch (Exception e) {
//            throw e;
//        }
//
//    }
//
//    @Override
//    public Boolean deleteAlbumsById(Long albumsId) {
//        return (albumsMapper.deleteById(albumsId)) > 0;
//    }
//
//    @Override
//    public Boolean batchDeleteAlbums(List<Long> ids) {
//        if (CollectionUtil.isEmpty(ids)) {
//            log.info("ids is null...");
//            return true;
//        }
//        return albumsMapper.deleteBatchIds(ids) > 0;
//    }
//
//}
//
//
