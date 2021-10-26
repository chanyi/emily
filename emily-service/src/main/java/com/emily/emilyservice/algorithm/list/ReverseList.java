package com.emily.emilyservice.algorithm.list;

public class ReverseList {
    public static void main(String[] args) {

    }

    /**
     * 反转链表（两种解题思路 双指针，栈）
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        // 基本思路是：两个指针，将后面的指针cur指向前一个指针pre实现节点反转
        // 前一个指针pre
        ListNode pre = null;
        // 后一个指针cur
        ListNode cur = head;
        // 临时指针 temp 用来保存 cur的下个节点
        ListNode temp = null;
        while(null != cur){
            //保存cur的下个节点
            temp = cur.next;
            // 将cur反向指向pre，代表反转
            cur.next = pre;
            // 将pre和cur都向后移动
            pre = cur;
            cur = temp;
        }
        return pre;
    }
}
