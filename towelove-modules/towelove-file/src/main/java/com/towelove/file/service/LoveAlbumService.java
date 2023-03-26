package com.towelove.file.service;


import com.towelove.common.core.domain.PageParam;
import com.towelove.common.core.domain.PageResult;
import com.towelove.file.domain.LoveAlbum;
import com.towelove.file.domain.vo.LoveAlbumPageReqVO;

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
     * @param loveAlbum 恋爱相册
     * @return 分页数据对象 (TableDataInfo)
     */
    default List<LoveAlbum> selectList(){
        return null;
    };
    default PageResult<LoveAlbum> selectPage(LoveAlbumPageReqVO pageReqVO){
        return null;
    };
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
    long insertLoveAlbum(LoveAlbum loveAlbum);
    
    /**
     * 修改恋爱相册
     * @param loveAlbum 恋爱相册
     * @return 结果返回是否更新成功
     */
    boolean updateLoveAlbum(LoveAlbum loveAlbum);
    
    /**
     * 删除恋爱相册
     * @param loveAlbumIds 恋爱相册
     * @return 结果返回是否删除成功
     */
    boolean deleteLoveAlbum(ArrayList<Long> loveAlbumIds);
    

}

