package blossom.project.towelove.msg;

/**
 * @author: ZhangBlossom
 * @date: 2024/1/18 15:38
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
public class PrefixTree {
    private TreeNode root;

    public PrefixTree(){
        root = new TreeNode();
    }

    /**
     *
     *
     */


    public static void main(String[] args) {
        PrefixTree node = new PrefixTree();
        node.insert("apple");
        //true
        System.out.println(node.search("apple"));
        //false
        System.out.println(node.search("apples"));

        node.insert("app");
        //true
        System.out.println(node.search("app"));

        node.update("app","application");
        //false
        System.out.println(node.search("app"));
        //true
        System.out.println(node.search("application"));

        node.delete("application");
        //false
        System.out.println(node.search("application"));
    }

    //插入
    public void insert(String word){
        TreeNode current = root;
        for(int i = 0;i<word.length();i++){
            int index = word.charAt(i) - 'a';
            if (current.children[index]==null){
                current.children[index] = new TreeNode();
            }
            current = current.children[index];
        }
        current.isEndOfWord = true;
    }

    //查询
    public boolean search(String word){
        TreeNode current = root;

        for(int i = 0;i<word.length();i++){
            int index = word.charAt(i) - 'a';
            if (current.children[index]==null){
                return false;
            }
            current = current.children[index];
        }
        return current.isEndOfWord;

    }

    //删除
    public void delete(String word){
        delete(root,word,0);
    }

    public boolean delete(TreeNode current,String word,int depth){
        if (depth == word.length()){
            if (!current.isEndOfWord){
                return false;
            }
            current.isEndOfWord = false;
            return isEmpty(current);
        }
        int index = word.charAt(depth) - 'a';
        TreeNode node = current.children[index];
        if (node==null){
            return false;
        }
        boolean xNode = delete(node,word,depth+1)
                && !node.isEndOfWord;
        if (xNode){
            current.children[index] = null;
            return isEmpty(current);
        }
        return false;
    }

    private boolean isEmpty(TreeNode node){
        for (int i = 0;i<node.children.length;i++){
            if (node.children[i]!=null){
                return false;
            }
        }
        return true;
    }
    //update
    public void update(String oldWord,String newWord){
        delete(oldWord);
        insert(newWord);
    }

}
