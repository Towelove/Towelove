package com.towelove.core.service;


import com.towelove.common.core.domain.PageResult;
import com.towelove.core.domain.lovealbum.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 恋爱相册(LoveAlbum) 服务接口
 *
 * @author 张锦标
 * @since 2023-03-26 20:40:06
 */
public interface LoveAlbumService {
    /**
     * 根据分页条件和恋爱相册信息查询恋爱相册数据
     * @return 分页数据对象 (TableDataInfo)
     */

    default List<LoveAlbum> selectList(){
        return null;
    };
    default PageResult<LoveAlbumBaseVO> selectPage(LoveAlbumPageReqVO pageReqVO){
        return null;
    };
    public Long getUserIdFromLoveAlbum(Long userId);
    /**
     * 根据ID查询恋爱相册详情
     * @param loveAlbumId 恋爱相册ID
     * @return 结果
     */
    LoveAlbum selectLoveAlbumById(Long loveAlbumId);

    /**
     * 新增恋爱相册
     * @param loveAlbum 恋爱相册
     * @return 结果返回插入后的id
     */
    long insertLoveAlbum(LoveAlbumCreateReqVO loveAlbum);
    
    /**
     * 修改恋爱相册
     * @param loveAlbum 恋爱相册
     * @return 结果返回是否更新成功
     */
    boolean updateLoveAlbum(LoveAlbumUpdateReqVO loveAlbum);
    
    /**
     * 删除恋爱相册
     * @param loveAlbumIds 恋爱相册
     * @return 结果返回是否删除成功
     */
    boolean deleteLoveAlbum(ArrayList<Long> loveAlbumIds);


    Long selectLoveAlbumIdByUserId(String userId);

    Long getLoveAlbumIdByUserId(String userId);
}

