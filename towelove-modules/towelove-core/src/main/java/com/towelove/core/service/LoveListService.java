package com.towelove.core.service;



import com.towelove.core.domain.lovelist.LoveList;

import java.util.ArrayList;
import java.util.List;

/**
 * 代办列表(LoveList) 服务接口
 *
 * @author 张锦标
 * @since 2023-05-12 19:33:56
 */
public interface LoveListService {


    /**
     * 根据ID查询代办列表详情
     * @param loveAlbumId 恋爱相册id
     * @return 查询到的代办列表详情
     */

    List<LoveList> selectLoveListByLoveAlbumId(Long loveAlbumId);

    /**
     * 新增代办列表
     * @param loveList 代办列表
     * @return 返回插入成功后的id
     */
    long insertLoveList(LoveList loveList);

    /**
     * 修改代办列表
     * @param loveList 代办列表
     * @return 返回是否修改成功
     */
    boolean updateLoveList(LoveList loveList);

    /**
     * 删除代办列表
     * @param loveListIds 代办列表
     * @return 返回是否删除成功
     */
    boolean deleteLoveList(Long loveListId);


    LoveList selectLoveListById(Long loveListId);
}

