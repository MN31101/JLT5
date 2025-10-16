package lab6;

import com.sun.source.tree.Tree;

import static lab6.Color.*;

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
            color = RED;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }

    public RBtree(){
        NIL = new Node<>(null);
        NIL.color = BLACK;
        root = NIL;
    }
    public Node<E> getRoot() {
        return root;
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

        if (y.left != null) {
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
        if (y.right != null) {
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
    public void insert(E value) {
        Node<E> newNode = new Node<>(value);
        newNode.left = NIL;
        newNode.right = NIL;

        Node<E> parent = null;
        Node<E> current = this.root;

        while (current != NIL) {
            parent = current;
            if (newNode.value.compareTo(current.value) < 0) {
                current = current.left;
            } else if (newNode.value.compareTo(current.value) > 0) {
                current = current.right;
            } else {
                throw new IllegalArgumentException("BST already contains a node with key " + value);
            }
        }

        newNode.parent = parent;

        if (parent == null) {
            this.root = newNode;
        } else if (newNode.value.compareTo(parent.value) < 0) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        fixInsert(newNode);
    }

    private void fixInsert(Node<E> node) {
        while (node != root && node.parent.color == RED) {
            Node<E> parent = node.parent;
            Node<E> grandparent = parent.parent;

            if (parent == grandparent.left) {
                Node<E> uncle = grandparent.right;

                if (uncle != null && uncle.color == RED) {
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grandparent.color = RED;
                    node = grandparent;
                } else {
                    if (node == parent.right) {
                        node = parent;
                        leftRotation(node);
                    }
                    parent.color = BLACK;
                    grandparent.color = RED;
                    rightRotation(grandparent);
                }
            } else {
                Node<E> uncle = grandparent.left;

                if (uncle != null && uncle.color == RED) {
                    parent.color = BLACK;
                    uncle.color = BLACK;
                    grandparent.color = RED;
                    node = grandparent;
                } else {
                    if (node == parent.left) {
                        node = parent;
                        rightRotation(node);
                    }
                    parent.color = BLACK;
                    grandparent.color = RED;
                    leftRotation(grandparent);
                }
            }
        }
        root.color = BLACK;
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
    public void deleteNode(E value) {
        Node<E> node = root;

        while (node != NIL && !node.value.equals(value)) {
            if (value.compareTo(node.value) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        if (node == NIL) return;

        Node<E> nodeToFix;
        Color deletedColor;

        if (node.left == NIL || node.right == NIL) {
            nodeToFix = deleteNodeWithZeroOrOneChild(node);
            deletedColor = node.color;
        } else {
            Node<E> successor = findMinimum(node.right);
            node.value = successor.value;
            nodeToFix = deleteNodeWithZeroOrOneChild(successor);
            deletedColor = successor.color;
        }

        if (deletedColor == BLACK) {
            fixRedBlackPropertiesAfterDelete(nodeToFix);
        }
    }

    private Node<E> deleteNodeWithZeroOrOneChild(Node<E> node) {
        Node<E> child = node.left != NIL ? node.left : node.right;

        replaceNode(node, child);

        if (child == NIL) child.parent = node.parent;

        return child;
    }

    private void replaceNode(Node<E> target, Node<E> replacement) {
        if (target.parent == null) {
            root = replacement;
        } else if (target == target.parent.left) {
            target.parent.left = replacement;
        } else {
            target.parent.right = replacement;
        }
        replacement.parent = target.parent;
    }

    private Node<E> findMinimum(Node<E> node) {
        while (node.left != NIL) {
            node = node.left;
        }
        return node;
    }
    private void fixRedBlackPropertiesAfterDelete(Node<E> node) {
        // If its root, nothing to change
        if (node == root) {
            return;
        }
        Node<E> sibling = getSibling(node);

        // Red sibling
        if (sibling.color == RED) {
            handleRedSibling(node, sibling);
            sibling = getSibling(node);
        }

        // Black sibling with two black children
        if (isBlack(sibling.left) && isBlack(sibling.right)) {
            sibling.color = RED;

            // Black sibling with two black children + red parent
            // ELSE
            // Black sibling with two black children + black parent
            if (node.parent.color == RED) {
                node.parent.color = BLACK;
            }
            else {
                fixRedBlackPropertiesAfterDelete(node.parent);
            }
        }

        // Black sibling with at least one red child
        else {
            handleBlackSiblingWithAtLeastOneRedChild(node, sibling);
        }
    }
    private Node<E> getSibling(Node<E> node) {
        Node<E> parent = node.parent;
        if (node == parent.left) {
            return parent.right;
        } else if (node == parent.right) {
            return parent.left;
        } else {
            throw new IllegalStateException("Parent is not a child of its grandparent");
        }
    }

    private boolean isBlack(Node<E> node) {
        return node == null || node.color == BLACK;
    }
    private void handleRedSibling(Node<E> node, Node<E> sibling) {
        // Recolor...
        sibling.color = BLACK;
        node.parent.color = RED;
        // rotate
        if (node == node.parent.left) {
            leftRotation(node.parent);
        } else {
            rightRotation(node.parent);
        }
    }
    private void handleBlackSiblingWithAtLeastOneRedChild(Node<E> node, Node<E> sibling) {
        boolean nodeIsLeftChild = node == node.parent.left;

        // Black sibling with at least one red child + "outer nephew" is black
        // --> Recolor sibling and its child, and rotate around sibling
        if (nodeIsLeftChild && isBlack(sibling.right)) {
            sibling.left.color = BLACK;
            sibling.color = RED;
            rightRotation(sibling);
            sibling = node.parent.right;
        } else if (!nodeIsLeftChild && isBlack(sibling.left)) {
            sibling.right.color = BLACK;
            sibling.color = RED;
            leftRotation(sibling);
            sibling = node.parent.left;
        }

        // Black sibling with at least one red child + "outer nephew" is red
        // --> Recolor sibling + parent + sibling's child, and rotate around parent
        sibling.color = node.parent.color;
        node.parent.color = BLACK;
        if (nodeIsLeftChild) {
            sibling.right.color = BLACK;
            leftRotation(node.parent);
        } else {
            sibling.left.color = BLACK;
            rightRotation(node.parent);
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

