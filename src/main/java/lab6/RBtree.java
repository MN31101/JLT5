package lab6;

import com.sun.source.tree.Tree;

/*
root node = black
red node cant jave child red node
each node black or red

all leafs are black
 */
public class RBtree<E extends Comparable<E>> {
    private Node<E> root;
    private final Node<E> NIL;

    private static class Node<E extends Comparable<E>> {
        E value;
        Color color;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E item) {
            value = item;
            parent = left = right = null;
            color = Color.RED;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }

    public RBtree(){
        NIL = new Node<>(null);
        NIL.color = Color.BLACK;
        root = NIL;
    }

    private void leftRotation(Node<E> x) {
        /*
        x               y
         \             / \
          y     ->    x   b
         / \           \
        a   b           a
         */

        Node<E> y = x.right;
        x.right = y.left;

        if (y.left != NIL) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotation(Node<E> x) {
        /*
            x               y
           /               / \
          y       ->      a   x
         / \                 /
        a   b               b
         */

        Node<E> y = x.left;
        x.left = y.right;
        if (y.right != NIL) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    public Node<E> search(E value){
        Node<E> node = this.root;
        while (node != NIL){
            if(value == node.value){
                return node;
            } else if (value.compareTo(node.value) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }
    private void insert(E value){
        Node<E> newNode = new Node<>(value);
        Node<E> parent = null;
        Node<E> root = this.root;

        while (root!= NIL){
            parent = root;
            if (newNode.value.compareTo(root.value) < 0){
                root = root.left;
            }else if(newNode.value.compareTo(root.value)>0){
                root =root.right;
            }else {
                throw new IllegalArgumentException("BST already contains a node with key " + value);
            }
        }

        newNode.parent = parent;

        if(parent == null){
            this.root = newNode;
        }else if(newNode.value.compareTo(parent.value)<0){
            parent.left = newNode;
        }else {
            parent.right = newNode;
        }
        fixInsert(newNode);
    }

    private void fixInsert(Node<E> node) {
        Node<E> parent = node.parent;

        if (parent == null) {
            return;
        }
        if (parent.color == Color.BLACK) {
            return;
        }

        Node<E> grandparent = parent.parent;
        if (grandparent == null) {
            parent.color = Color.BLACK;
            return;
        }
        Node<E> uncle = getUncle(parent);

        if (uncle != null && uncle.color == Color.RED) {
            parent.color = Color.BLACK;
            grandparent.color = Color.RED;
            uncle.color = Color.BLACK;

            fixInsert(grandparent);
        }
        else if (parent == grandparent.left) {
            if (node == parent.right) {
                leftRotation(parent);
                parent = node;
            }
            rightRotation(grandparent);
            parent.color = Color.BLACK;
            grandparent.color = Color.RED;
        }
        else {
            if (node == parent.left) {
                rightRotation(parent);
                parent = node;
            }
            leftRotation(grandparent);
            parent.color = Color.BLACK;
            grandparent.color = Color.RED;
        }
    }
    private Node<E> getUncle(Node<E> parent) {
        Node<E> grandparent = parent.parent;
        if (grandparent.left == parent) {
            return grandparent.right;
        } else if (grandparent.right == parent) {
            return grandparent.left;
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }
}

enum Color {
    BLACK("Black"),
    RED("Red");
    private final String color;

    Color(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}

