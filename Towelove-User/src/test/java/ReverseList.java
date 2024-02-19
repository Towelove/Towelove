/**
 * @author: ZhangBlossom
 * @date: 2024/2/5 16:18
 * @contact: QQ:4602197553
 * @contact: WX:qczjhczs0114
 * @blog: https://blog.csdn.net/Zhangsama1
 * @github: https://github.com/ZhangBlossom
 * @description:
 */
public class ReverseList {

    public Node reverList(Node head){
        Node pre = null;//pre node
        Node current = head; // current node
        while(current!=null){
            Node nextTemp = current.next; //temp store the next node
            current.next = pre; //point this node to the pre
            pre = current; //pre node move to head
            current = nextTemp;//this node move to pre
        }//value dicengyuanli
        return pre;
    }

    public static void main(String[] args) {

    }

}

class Node{
    int data;
    Node next;

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
    public Node(){

    }
    public Node(int data, Node next) {
        this.data = data;
        this.next = next;
    }
}
