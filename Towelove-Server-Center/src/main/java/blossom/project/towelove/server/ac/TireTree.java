package blossom.project.towelove.server.ac;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.dto
 * @className: TireTree
 * @author: Link Ji
 * @description: AC自动机算法： tireTree树
 * @date: 2023/12/17 14:12
 * @version: 1.0
 */
@Getter
@Setter
public class TireTree {
    /**
     * tire树头节点
     */
    private AcTireNode root = buildRoot();
    /**
     * 是否成功构建失败节点
     */



    /**
     * TireTree中插入字符
     *
     * @param word
     */
    public void insert(String word) {
        AcTireNode cur = this.root;
        for (int i = 0; i < word.length(); i++) {
            Map<Character, AcTireNode> childNodes = cur.getChildNodes();
            char charred = word.charAt(i);
            if (!childNodes.containsKey(charred)) {
                //节点中不存在这个字符，则创建新节点存入
                childNodes.put(charred, new AcTireNode());
            }
            cur = childNodes.get(charred);
        }
        //字符插入完毕，记录尾部节点
        cur.getEndWordSize().add(word.length());
    }

    /**
     * TireTree 搜索单词
     *
     * @param word
     * @return
     */
    public boolean search(String word) {
        //从头节点向下寻找
        AcTireNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            Map<Character, AcTireNode> childNodes = cur.getChildNodes();
            char charred = word.charAt(i);
            AcTireNode acTireNode = childNodes.get(charred);
            if (Objects.isNull(acTireNode)) {
                //节点不存在，匹配失败
                return false;
            }
            cur = acTireNode;
        }
        return !cur.getEndWordSize().isEmpty();
    }


    public  AcTireNode buildRoot(){
        AcTireNode root1 = new AcTireNode();
        root1.setRoot(true);
        root1.setFailureNode(root1);
        return root1;
    }
}
