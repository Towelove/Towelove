package blossom.project.towelove.framework.flower.entity;

import java.util.function.Supplier;

/**
 * @author: 张锦标
 * @date: 2024/3/13 3:27 PM
 * ToweloveAlbumShowContext类
 */
public class ToweloveAlbumContext extends AbstractToweloveAlbumContext implements
        Supplier
{

    /**
     * 这里随便实现一个方法，目的是为了说明当前showcontext这个对象也可以有具体的方法
     * @return
     */
    @Override
    public Object get() {
        return null;
    }
}
