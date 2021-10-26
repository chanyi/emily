package com.emily.emilyservice.algorithm.tree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class MaxDepth {


    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode();
        TreeNode root = treeNode.initTreeNode();
        System.out.println("treeNode的深度="+new MaxDepth().maxDepthBFS(root));
    }

    /**
     * 给定一个二叉树，找出其最大深度。
     *
     * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
     *
     * 说明:叶子节点是指没有子节点的节点。
     *
     * 示例：
     * 给定二叉树 [3,9,20,null,null,15,7]，
     *
     *     3
     *    / \
     *   9  20
     *     /  \
     *    15   7
     * 返回它的最大深度3 。
     *
     * @param
     */
    public int maxDepth(TreeNode root) {
        if(root==null){
            return 0;
        }
       return Math.max(maxDepth(root.right),maxDepth(root.left))+1;
    }

    public int maxDepthDFS(TreeNode root) {
        if (root == null)
            return 0;
        //stack记录的是节点，而level中的元素和stack中的元素
        //是同时入栈同时出栈，并且level记录的是节点在第几层
        Stack<TreeNode> stack = new Stack<>();
        Stack<Integer> level = new Stack<>();
        stack.push(root);
        level.push(1);
        int max = 0;
        while (!stack.isEmpty()) {
            //stack中的元素和level中的元素同时出栈
            TreeNode node = stack.pop();
            int temp = level.pop();
            max = Math.max(temp, max);
            if (node.left != null) {
                //同时入栈
                stack.push(node.left);
                level.push(temp + 1);
            }
            if (node.right != null) {
                //同时入栈
                stack.push(node.right);
                level.push(temp + 1);
            }
        }
        return max;
    }

    public int maxDepthBFS(TreeNode root) {
        if (root == null)
            return 0;
        //创建一个队列
        Deque<TreeNode> deque = new LinkedList<>();
        deque.push(root);
        int count = 0;
        while (!deque.isEmpty()) {
            //每一层的个数
            int size = deque.size();
            while (size-- > 0) {
                TreeNode cur = deque.pop();
                if (cur.left != null)
                    deque.addLast(cur.left);
                if (cur.right != null)
                    deque.addLast(cur.right);
            }
            count++;
        }
        return count;
    }


}
