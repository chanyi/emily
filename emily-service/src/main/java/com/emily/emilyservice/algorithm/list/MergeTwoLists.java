package com.emily.emilyservice.algorithm.list;

public class MergeTwoLists {
    public static void main(String[] args) {
        ListNode listNode1 = new ListNode();
        ListNode listNode2 = new ListNode();
        listNode1 = listNode1.initListNode();
        listNode2 = listNode2.initListNode();
        MergeTwoLists mergeTwoLists = new MergeTwoLists();
        ListNode listNode = mergeTwoLists.mergeTwoLists(listNode1,listNode2);
        listNode.printListNode(listNode);
    }

    /**
     * 将两个升序链表合并为一个新的 升序 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的
     * <p>
     * 输入：l1 = [1,2,4], l2 = [1,3,4]
     * 输出：[1,1,2,3,4,4]
     * 示例 2：
     * <p>
     * 输入：l1 = [], l2 = []
     * 输出：[]
     * 示例 3：
     * <p>
     * 输入：l1 = [], l2 = [0]
     * 输出：[0]
     *
     * @param linked1
     * @param linked2
     * @return
     */
    public ListNode mergeTwoLists(ListNode linked1, ListNode linked2) {
        if (linked1 == null)
            return linked2;
        if (linked2 == null)
            return linked1;
        if (linked1.val < linked2.val) {
            linked1.next = mergeTwoLists(linked1.next, linked2);
            return linked1;
        } else {
            linked2.next = mergeTwoLists(linked1, linked2.next);
            return linked2;
        }
    }
}
