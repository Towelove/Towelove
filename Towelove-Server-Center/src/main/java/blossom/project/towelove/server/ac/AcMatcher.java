package blossom.project.towelove.server.ac;

/**
 * @projectName: Towelove
 * @package: blossom.project.towelove.server.dto
 * @className: AcMatcher
 * @author: Link Ji
 * @description: GOGO
 * @date: 2023/12/17 22:11
 * @version: 1.0
 */

import blossom.project.towelove.common.exception.ServiceException;
import blossom.project.towelove.server.mapper.SensitiveWordsMapper;
import lombok.Getter;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * AC自动机匹配
 */
@Component
//@LoveLog
public class AcMatcher implements ApplicationRunner {

    private final AcTireNode root;
    @Resource
    private SensitiveWordsMapper sensitiveWordsMapper;

    @Getter
    private boolean fetchInitFailurePoints = false;

    public AcMatcher() {
        AcTireNode root = new AcTireNode();
        root.setRoot(true);
        this.root = root;
    }

    public void buildAcMatcher(List<String> sensitiveWord) {
        //先构建树，然后构建失败节点
        TireTree tireTree = new TireTree();
        tireTree.setRoot(root);
        try {
            sensitiveWord.forEach(tireTree::insert);
        } catch (Exception e) {
            throw new ServiceException("构建TireTree失败");
        }
        //利用层序遍历构建失败跳转节点
        constructorFailurePoints();
        this.fetchInitFailurePoints = true;
    }

    /**
     * 层序遍历构建失败节点
     * @param
     */
    public void constructorFailurePoints() {
        Queue<AcTireNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            AcTireNode parentNode = queue.poll();
            AcTireNode parentFailureNode = parentNode.getFailureNode();
            Map<Character, AcTireNode> childNodes = parentNode.getChildNodes();
            for (Map.Entry<Character, AcTireNode> characterAcTireNodeEntry : childNodes.entrySet()) {
                Character c = characterAcTireNodeEntry.getKey();
                //遍历当前节点的子节点，失败指针设置好之后存入队列中
                AcTireNode currentNode = characterAcTireNodeEntry.getValue();
                //如果当前节点父节点指向的fail指针节点的子节点存在与当前节点相同的节点则该节点fail指向指向他，否则指向root节点
                if (Objects.isNull(parentFailureNode)) {
                    //父节点是头节点，直接指向头节点
                    currentNode.setFailureNode(root);
                } else if (parentFailureNode.getChildNodes().containsKey(c)) {
                    AcTireNode nextFailure = parentFailureNode.getChildNodes().get(c);
                    currentNode.setFailureNode(nextFailure);
                } else {
                    currentNode.setFailureNode(root);
                }
                //队列中加入当前节点
                queue.add(currentNode);
            }
        }
    }

    public Set<String> search(String word) {
        Set<String> sensitiveWords = new HashSet<>();
        //从头节点向下寻找
        AcTireNode cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            while(cur != null && Objects.isNull(cur.getChildNodes().get(c))){
                cur = cur.getFailureNode();
            }
            if (Objects.isNull(cur)){
                cur = root;
            }else if (Objects.nonNull(cur.getChildNodes().get(c))){
                AcTireNode curCharNode = cur.getChildNodes().get(c);
                Set<Integer> endWordSize = curCharNode.getEndWordSize();
                if (!endWordSize.isEmpty()){
                    for (Integer size : endWordSize) {
                        sensitiveWords.add(word.substring(i - size + 1,i + 1));
                    }
                }
                cur = curCharNode;

            }else {
                cur = root;
            }
        }
        return sensitiveWords;
    }

    //    @Override
//    public void afterPropertiesSet() throws Exception {
//        //从数据库中得到敏感词库
//        buildAcMatcher(List.of("流氓","abc"));
//    }

    public static void main(String[] args) {
        AcMatcher acMatcher = new AcMatcher();
        List<String> sensitives = List.of("ab", "abc", "123");
        acMatcher.buildAcMatcher(sensitives);
        Collection<String> search = acMatcher.search("dsabihoafoiaababcababc12332112123");
        search.forEach(System.out::println);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<SensitiveWords> sensitiveWords = sensitiveWordsMapper.selectList(null);
        if (!sensitiveWords.isEmpty()){
            List<String> words = sensitiveWords.stream().map(SensitiveWords::getWord).toList();
            buildAcMatcher(words);
        }
    }
}
