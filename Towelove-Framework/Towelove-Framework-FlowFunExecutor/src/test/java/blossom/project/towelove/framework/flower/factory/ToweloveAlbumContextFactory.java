package blossom.project.towelove.framework.flower.factory;

import blossom.project.towelove.framework.flower.entity.ToweloveAlbumContext;
import blossom.project.towelove.framework.flower.request.ToweloveAlbumRequest;

/**
 * @author: 张锦标
 * @date: 2024/4/19 11:40 AM
 * ToweloveAlbumContextFactory类
 */
public class ToweloveAlbumContextFactory {

    public static ToweloveAlbumContext buildToweloveAlbumContext(ToweloveAlbumRequest request){
        ToweloveAlbumContext context = new ToweloveAlbumContext();
        //todo 将request转化为context
        return context;
    }
}
