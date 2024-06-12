package blossom.project.towelove.community.enums;


/**
 * @author: ZhangBlossom
 * @date: 2024-06-09 00:08:01
 * @contact: QQ:4602197553
 * @contact: WX:zhangblossom0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */

public enum BehaviorsType {

    //浏览
    VIEW("view"),
    //喜欢
    LIKE("like"),
    //收藏
    FAVORITE("favorite"),

    //分享
    SHARE("share"),
    //评论
    COMMENT("comment");

    private final String value;

    BehaviorsType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static BehaviorsType fromValue(String value) {
        for (BehaviorsType type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }
}
