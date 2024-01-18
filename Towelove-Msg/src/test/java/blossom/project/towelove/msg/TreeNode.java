package blossom.project.towelove.msg;

/**
 * @author: ZhangBlossom
 * @date: 2024/1/18 15:39
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
public class TreeNode {
    public TreeNode[] children;
    public boolean isEndOfWord;

    public TreeNode(){
        this.children = new TreeNode[26];
        isEndOfWord = false;
    }
}
