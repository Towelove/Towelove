package blossom.project.towelove.community.enums;



/**
 * @author: ZhangBlossom
 * @date: 2024-06-09 00:49:37
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
public enum SortOrder {
    ASC("ASC"),
    DESC("DESC");

    private final String value;

    SortOrder(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
