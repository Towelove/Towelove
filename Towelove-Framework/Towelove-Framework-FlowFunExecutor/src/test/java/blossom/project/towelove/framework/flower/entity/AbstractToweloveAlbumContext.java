package blossom.project.towelove.framework.flower.entity;

import blossom.project.towelove.framework.flower.iface.ToweloveBizIface;
import blossom.project.towelove.framework.flower.model.ToweloveBizContext;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: 张锦标
 * @date: 2024/3/13 3:19 PM
 * ToweloveAlbumBizContext类
 * towelove的相册上下文对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractToweloveAlbumContext extends ToweloveBizContext implements
        ToweloveBizIface {
//todo 这里可以实现更多的接口 从而提供更加强大的能力
    private List<AlbumEntity> albums;

    @Override
    public String getToweloveBizTestString() {
        return ToweloveBizIface.super.getToweloveBizTestString();
    }

    /**
     * 把不断包装处理得到的结果进行set
     * 然后继续往下游传递
     * 也就是下游还是处理这个albums对象
     * @param albums
     */
    public void setResult(List<AlbumEntity> albums){
        this.albums = albums;
    }
}
