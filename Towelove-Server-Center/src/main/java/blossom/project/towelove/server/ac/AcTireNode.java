package blossom.project.towelove.server.ac;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * TireTree树节点
 */
@Getter
@Setter
public class AcTireNode {
    /**
     * 子节点集合
     */
    private Map<Character, AcTireNode> childNodes = new HashMap<>();

    /**
     * 完整敏感词长度，标识为走完一个完整敏感词
     */
    private Set<Integer> endWordSize = new HashSet<>();


    private boolean isRoot = false;


    /**
     * 失败跳转节点
     */
    private AcTireNode failureNode;


    public void setFailureNode(AcTireNode failureNode) {
        if (this == failureNode || isAncestorNode(this, failureNode)) {
            return; // 避免循环引用
        }
        this.failureNode = failureNode;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public void setEndWordSize(Set<Integer> endWordSize) {
        this.endWordSize = endWordSize;
    }

    public void setChildNodes(Map<Character, AcTireNode> childNodes) {
        this.childNodes = childNodes;
    }

    private boolean isAncestorNode(AcTireNode node, AcTireNode ancestorNode) {
        if (node == null) {
            return false;
        }
        if (node == ancestorNode) {
            return true;
        }
        return isAncestorNode(node.failureNode, ancestorNode);
    }
}