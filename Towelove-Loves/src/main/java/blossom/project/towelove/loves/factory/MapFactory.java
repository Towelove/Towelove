package blossom.project.towelove.loves.factory;

import blossom.project.towelove.common.constant.LovesConstant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ZhangBlossom
 * @date: 2023/11/30 21:10
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * MapFactory类
 */
public class MapFactory {

    private static final Map<Integer,String> cache = new HashMap<>(9);

    static{
        for (int i =0;i< LovesConstant.PHOTO_NUMS;i++){
            cache.put(i,"");
        }
    }
    public static Map<Integer, String> getMap() {
        return new HashMap<>(cache); // 返回拷贝
    }


}
