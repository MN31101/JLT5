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
        if (parent.color == BLACK) {
            return;
        }

        Node<E> grandparent = parent.parent;
        if (grandparent == null) {
            parent.color = BLACK;
            return;
        }
        Node<E> uncle = getUncle(parent);

        if (uncle != null && uncle.color == RED) {
            parent.color = BLACK;
            grandparent.color = RED;
            uncle.color = BLACK;

            fixInsert(grandparent);
        }
        else if (parent == grandparent.left) {
            if (node == parent.right) {
                leftRotation(parent);
                parent = node;
            }
            rightRotation(grandparent);
            parent.color = BLACK;
            grandparent.color = RED;
        }
        else {
            if (node == parent.left) {
                rightRotation(parent);
                parent = node;
            }
            leftRotation(grandparent);
            parent.color = BLACK;
            grandparent.color = RED;
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
    private void deleteNode(E value){
        Node<E> node = this.root;
        while (node != NIL && node.value != value) {
            if (value.compareTo(node.value)<0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        if (node == NIL) {
            return;
        }

        Node<E> movedUpNode;
        Color deletedNodeColor;

        // Node has zero or one child
        if (node.left == NIL || node.right == NIL) {
            movedUpNode = deleteNodeWithZeroOrOneChild(node);
            deletedNodeColor = node.color;
        } else { // Node has two children
            // Find minimum node of right subtree ("inorder successor" of current node)
            Node<E> inOrderSuccessor = findMinimum(node.right);

            // Copy inorder successor's data to current node (keep its color!)
            node.value = inOrderSuccessor.value;

            // Delete inorder successor just as we would delete a node with 0 or 1 child
            movedUpNode = deleteNodeWithZeroOrOneChild(inOrderSuccessor);
            deletedNodeColor = inOrderSuccessor.color;
        }

        if (deletedNodeColor == BLACK) {
            fixRedBlackPropertiesAfterDelete(movedUpNode);
            if (movedUpNode == NIL) {
                if (movedUpNode.parent == null) {
                    root = movedUpNode;
                } else if (movedUpNode.parent.left == movedUpNode) {
                    movedUpNode.parent.left = null;
                } else {
                    movedUpNode.parent.right = null;
                }
            }
        }
    }
    private Node<E> deleteNodeWithZeroOrOneChild(Node<E> node) {
        // Node has ONLY a left child --> replace by its left child
        // ELSE IF
        // Node has ONLY a right child --> replace by its right child
        // ELSE
        // Node has no child is red -> delete, is black -> NIL
        if (node.left != null) {
            if (node.parent.parent == null) {
                root = node.left;
            } else if (node.parent.parent.left == node) {
                node.parent.parent.left = node.left;
            } else {
                node.parent.parent.right = node.left;
            }
            return node.left;
        } else if (node.right != null) {
            if (node.parent.parent == null) {
                root = node.right;
            } else if (node.parent.parent.right == node) {
                node.parent.parent.left = node.right;
            } else {
                node.parent.parent.right = node.right;
            }
            return node.right;
        } else {
            Node<E> newChild = node.color == BLACK ? NIL : null;
            if (node.parent.parent == null) {
                root = newChild;
            } else if (node.parent.parent.right == node) {
                node.parent.parent.left = newChild;
            } else {
                node.parent.parent.right = newChild;
            }
            return newChild;
        }
    }
    private Node<E> findMinimum(Node<E> node) {
        while (node.left != null) {
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

