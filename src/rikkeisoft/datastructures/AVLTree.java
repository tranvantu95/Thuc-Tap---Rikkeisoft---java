/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rikkeisoft.datastructures;

import java.util.LinkedList;

/**
 *
 * @author Admin
 */
public class AVLTree {
 
    private Node root;
 
    private static class Node {
        private int key;
        private int balance;
        private int height;
        private Node left, right, parent;
        
        private boolean leftTree, rightTree;
 
        Node(int k, Node p) {
            key = k;
            parent = p;
        }
        
        public void printLevelOrder() {
            LinkedList<Node> queue = new LinkedList<>();
            queue.add(this);
            
            this.leftTree = true;
            this.rightTree = true;
            
            String s = "";
            int nt = (int) Math.pow(2, this.height - 1);
//            for(int i = this.height; i > 0; i--) nt *= 2;
            for(int i = nt / 2; i >= 0; i--) s += "  ";
            
            while (queue.size() > 0) {
                Node head = queue.remove();
                
                System.out.print((head.leftTree ? s : (s + s)) + head.key);
                
                if(head.rightTree) {
                    s = "";
                    nt /= 2;
                    for(int i = nt / 2; i >= 0; i--) s += "  ";
//                    s = s.substring(0, s.length() - 1);
                    System.out.println("");
                }
                 
                // Add children of recently-printed node to queue, if they exist.
                if (head.left != null) {
                    queue.add(head.left);
                    
                    head.left.leftTree = head.leftTree;
                    head.left.rightTree = false;
                }
                if (head.right != null) {
                    queue.add(head.right);
                    
                    head.right.rightTree = head.rightTree;
                    head.right.leftTree = false;
                }
            }
        }
    }
 
    public boolean insert(int key) {
        if (root == null)
            root = new Node(key, null);
        else {
            Node n = root;
            Node parent;
            while (true) {
                if (n.key == key)
                    return false;
 
                parent = n;
 
                boolean goLeft = n.key > key;
                n = goLeft ? n.left : n.right;
 
                if (n == null) {
                    if (goLeft) {
                        parent.left = new Node(key, parent);
                    } else {
                        parent.right = new Node(key, parent);
                    }
                    rebalance(parent);
                    break;
                }
            }
        }
        return true;
    }
 
    private void delete(Node node){
        if(node.left == null && node.right == null){
            if(node.parent == null) root = null;
            else{
                Node parent = node.parent;
                if(parent.left == node){
                    parent.left = null;
                }else parent.right = null;
                rebalance(parent);
            }
            return;
        }
        if(node.left!=null){
            Node child = node.left;
            while (child.right!=null) child = child.right;
            node.key = child.key;
            delete(child);
        }else{
            Node child = node.right;
            while (child.left!=null) child = child.left;
            node.key = child.key;
            delete(child);
        }
    }
 
    public void delete(int delKey) {
        if (root == null)
            return;
        Node node = root;
        Node child = root;
 
        while (child != null) {
            node = child;
            child = delKey >= node.key ? node.right : node.left;
            if (delKey == node.key) {
                delete(node);
                return;
            }
        }
    }
 
    private void rebalance(Node n) {
        setBalance(n);
        
//        System.out.println("" + n.height);
//        return;
 
        if (n.balance == -2) {
            if (height(n.left.left) >= height(n.left.right))
                n = rotateRight(n);
            else
                n = rotateLeftThenRight(n);
 
        } else if (n.balance == 2) {
            if (height(n.right.right) >= height(n.right.left))
                n = rotateLeft(n);
            else
                n = rotateRightThenLeft(n);
        }
 
        if (n.parent != null) {
            rebalance(n.parent);
        } else {
            root = n;
        }
    }
 
    private Node rotateLeft(Node a) {
 
        Node b = a.right;
        b.parent = a.parent;
 
        a.right = b.left;
 
        if (a.right != null)
            a.right.parent = a;
 
        b.left = a;
        a.parent = b;
 
        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }
 
        setBalance(a, b);
 
        return b;
    }
 
    private Node rotateRight(Node a) {
 
        Node b = a.left;
        b.parent = a.parent;
 
        a.left = b.right;
 
        if (a.left != null)
            a.left.parent = a;
 
        b.right = a;
        a.parent = b;
 
        if (b.parent != null) {
            if (b.parent.right == a) {
                b.parent.right = b;
            } else {
                b.parent.left = b;
            }
        }
 
        setBalance(a, b);
 
        return b;
    }
 
    private Node rotateLeftThenRight(Node n) {
        n.left = rotateLeft(n.left);
        return rotateRight(n);
    }
 
    private Node rotateRightThenLeft(Node n) {
        n.right = rotateRight(n.right);
        return rotateLeft(n);
    }
 
    private int height(Node n) {
        if (n == null)
            return -1;
        return n.height;
    }
 
    private void setBalance(Node... nodes) {
        for (Node n : nodes) {
            reheight(n);
            n.balance = height(n.right) - height(n.left);
        }
    }
 
    public void printBalance() {
        printBalance(root);
    }
 
    private void printBalance(Node n) {
        if (n != null) {
            printBalance(n.left);
            System.out.printf("%s ", n.balance);
            printBalance(n.right);
        }
    }
 
    private void reheight(Node node){
        if(node!=null){
            node.height=1 + Math.max(height(node.left), height(node.right));
        }
    }
 
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
 
        String s = "";
        for (int i = 1; i < 100; i++) {
            int rd = (int) (Math.random() * 100);
            tree.insert(rd);
            s += rd + " ";
        }
        System.out.println("Inserting values: " + s);
        
        System.out.println("root height: " + tree.root.height);
       
        System.out.println("Level order traversal: ");
        tree.root.printLevelOrder();
        System.out.println();

        System.out.print("Printing balance: ");
        tree.printBalance();
    }
}
