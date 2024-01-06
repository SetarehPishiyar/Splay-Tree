//package DSProject;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Node{
    int key;
    Node left;
    Node right;
    Node parent;

    public Node(int key){
        this.key = key;
        left = right = parent = null;
    }
}

class SplayTree{
    Node root = null;


    public void zag(Node Root) {
        Node temp = Root.right;
        Root.right = temp.left;
        if (temp.left != null)
            temp.left.parent = Root;
        temp.parent = Root.parent;

        // for the recursive situations
        if (Root.parent == null)
            root = temp;
        else if (Root == Root.parent.left)
            Root.parent.left = temp;
        else
            Root.parent.right = temp;

        temp.left = Root;
        Root.parent = temp;
    }

    public void zig(Node Root) {
        Node temp = Root.left;
        Root.left = temp.right;
        if (temp.right != null)
            temp.right.parent = Root;
        temp.parent = Root.parent;

        // for the recursive situations
        if (Root.parent == null)
            root = temp;
        else if (Root == Root.parent.left)
            Root.parent.left = temp;
        else
            Root.parent.right = temp;

        temp.right = Root;
        Root.parent = temp;
    }

    public void splay(Node temp) {
        while (temp != root) {
            // first child
            if (temp.parent == root) {
                if (temp == temp.parent.left)
                    zig(temp.parent);
                else
                    zag(temp.parent);
            }
            // second child left subtree
            else if (temp == temp.parent.left) {
                if (temp.parent == temp.parent.parent.left) {
                    zig(temp.parent.parent);
                    zig(temp.parent);
                } else {
                    zig(temp.parent);
                    zag(temp.parent);
                }
            }
            // second child right subtree
            else {
                if (temp.parent == temp.parent.parent.right) {
                    zag(temp.parent.parent);
                    zag(temp.parent);
                } else {
                    zag(temp.parent);
                    zig(temp.parent);
                }
            }
            if (temp.parent == null) {
                root = temp;
            }
        }
    }

    public void add(int key) {
        // if the key is not in tree then add
        if (searchNode(root, key) == null) {
            root = addBST(key, root);
            splay(getNode(key));
        }
    }

    // binary tree addition
    public Node addBST(int item, Node node) {
        if (node == null)
            return new Node(item);
        else {
            if (item < node.key) {
                node.left = addBST(item, node.left);
                node.left.parent = node;
            }
            else if (item > node.key) {
                node.right = addBST(item, node.right);
                node.right.parent = node;
            }
            return node;
        }
    }

    public void del(int key){

        Node curr = getNode(key);
        if (curr == null)
            return;
        if(curr.key != key)
            return;

        splay(curr);

        // if it has no child
        if(curr.right==null && curr.left == null)
            root = null;

        // if it has only one child
        else if (curr.left!= null && curr.right == null){
            root = curr.left;
            root.parent = null;
        }
        else if (curr.left == null){
            root = curr.right;
            root.parent = null;
        }
        // if it has 2 children
        else {
            // finding left maximum
            Node leftMax = curr.left;
            while (leftMax.right != null)
                leftMax = leftMax.right;

            splay(leftMax);

            // delete curr and make leftMax root
            leftMax.right = curr.right;
            root = leftMax;
            if (leftMax.right != null)
                leftMax.right.parent = leftMax;
        }
    }

    public long sum(int start, int end){
        long result =0;
        if (root == null)
            return 0;
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty())
        {
            Node curr = q.peek();
            q.remove();
            if (curr.key >= start && curr.key <= end)
                result += curr.key;
            if (curr.left != null && curr.key > start)
                q.add(curr.left);
            if (curr.right != null && curr.key < end)
                q.add(curr.right);
        }
        return result;
    }

    public Boolean find(int key){
        if (getNode(key) == null)
            return false;
        splay(getNode(key));
        return root.key == key;
    }
    public void preOrder(Node root)
    {
        if (root != null)
        {
            System.out.print(root.key + " ");
            preOrder(root.left);
            preOrder(root.right);
        }
    }

    Node searchNode(Node root, int key) {
        if (root == null || root.key == key)
            return root;
        if (root.key < key)
            return searchNode(root.right, key);
        return searchNode(root.left, key);
    }

    public Node getNode(int key) {
        Node temp = root;
        Node parent = root;
        while (temp!=null){
            if(temp.key == key)
                return temp;
            else if(temp.key > key){
                parent = temp;
                temp = temp.left;
            }
            else {
                parent = temp;
                temp = temp.right;
            }
        }
        return parent;
    }
}

public class Project {
    public static void main(String[] args){
        SplayTree tree = new SplayTree();

//        try {
//            System.setIn(new FileInputStream("D:\\DS\\src\\Splay\\input25.txt"));
//            System.setOut(new PrintStream(new FileOutputStream("D:\\DS\\src\\Splay\\your-output.txt")));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        int count;
        Scanner input = new Scanner(System.in);
        count = input.nextInt();
        for (int i = 0; i < count; i++) {

            String op = input.next();
            int key1 = input.nextInt();
            Integer key2 = null;
            if (op.equals("sum"))
                key2 = input.nextInt();

            switch (op.toLowerCase()) {
                case "find" -> System.out.println(tree.find(key1));
                case "add" -> tree.add(key1);
                case "del" -> tree.del(key1);
                case "sum" -> System.out.println(tree.sum(key1,key2));
            }
        }
    }
}
