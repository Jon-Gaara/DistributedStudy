package com.yumaolin.deepunderstand.leetcode.algorithm;

/**
 * @author yml
 * @Description 反转链表
 * @Date 2021-03-18 13:01
 */
public class ReverseLinkedBetween {

    public ListNode reverseBetween(ListNode head, int left, int right) {
        int between = right - left;
        if(between == 0){
            return head;
        }
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;

        ListNode current = head;
        //最左节点
        ListNode leftNode = dummyHead;
        //当前节点的前一个节点
        ListNode nextPrev = current;
        int count = 1;
        while(current != null){
            if(count == left-1){
                leftNode = current;
            }else if(count > left){
                current = nodeExchange(leftNode,current,nextPrev);
            }
            if(count == right){
                break;
            }
            nextPrev = current;
            current = current.next;
            ++count;
        }
        return dummyHead.next;
    }

    /**
     * 节点交换
     * @param leftNode left节点的上一个节点
     * @param exchangeNode 需要交换的节点
     * @param nextPrev 当前节点的前一个节点
     * @return
     */
    private ListNode nodeExchange(ListNode leftNode,ListNode exchangeNode,ListNode nextPrev){
        ListNode exchangeNextNode = exchangeNode.next;
        ListNode leftNextNode = leftNode.next;
        nextPrev.next = exchangeNextNode;
        leftNode.next = exchangeNode;
        exchangeNode.next = leftNextNode;
        return nextPrev;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        ReverseLinkedBetween reverseLinkedBetween = new ReverseLinkedBetween();
        ListNode node = reverseLinkedBetween.new ListNode(1);
        ListNode node2 = reverseLinkedBetween.new ListNode(2);
        ListNode node3 = reverseLinkedBetween.new ListNode(3);
        ListNode node4 = reverseLinkedBetween.new ListNode(4);
        ListNode node5 = reverseLinkedBetween.new ListNode(5);
        /*node.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;*/
        node3.next = node5;
        ListNode head = reverseLinkedBetween.reverseBetween(node3,1,2);
        ListNode next = head;
        while (next != null){
            System.out.println(next.val);
            next = next.next;
        }
    }
}
