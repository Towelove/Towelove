package blossom.project.towelove.community.enums;/**
 * @Author:Serendipity
 * @Date:
 * @Description:
 */

/**
 * @author: ZhangBlossom
 * @date: 2024/6/11 11:20
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 *
 */
public enum ShowTags {

    //作者本人
    IS_AUTHOR("is_author"),
    //置顶
    PINNED("pinned");

    private final String value;

    ShowTags(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ShowTags fromValue(String value) {
        for (ShowTags type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown enum value: " + value);
    }

}

    