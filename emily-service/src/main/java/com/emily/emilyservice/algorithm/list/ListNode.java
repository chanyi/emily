package com.emily.emilyservice.algorithm.list;

public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int x) {
        val = x;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public void printListNode(ListNode head){
        ListNode node = head;
        while(true){
            if(node != null){
                System.out.print(node.val+"->");
                node = node.next;
            }else{
                break;
            }
        }
        System.out.println("");
    }
    public ListNode initListNode(){
        ListNode head = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        head.next = node2;
        node2.next = node3;
        node3.next = node4;
        return head;
    }
}
