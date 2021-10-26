package com.emily.emilyservice.algorithm.list;

public class DeleteNode {
    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        head.next = node2;
        node2.next = node3;
        node3.next = node4;
        new DeleteNode().printListNode(head);
        new DeleteNode().deleteNode(node2);
        new DeleteNode().printListNode(head);
    }
    public void deleteNode(ListNode node){
        node.val = node.next.val;
        node.next = node.next.next;
    }

    public void printListNode(ListNode head){
        ListNode node = head;
        while(true){
            if(node.next!=null){
                System.out.print(node.val+"->");
                node = node.next;
            }else{
                break;
            }
        }
        System.out.println("");
    }


}
