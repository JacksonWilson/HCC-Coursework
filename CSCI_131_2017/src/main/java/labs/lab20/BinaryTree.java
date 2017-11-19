package labs.lab20;

import java.util.Scanner;

public class BinaryTree {
    private BinaryTreeNode root;
    private static Scanner scan;

    static {
        scan = new Scanner(System.in);
    }

    public BinaryTree(char data) {
        root = new BinaryTreeNode(data);
        enterTreeData (root);
    }

    private void enterTreeData(BinaryTreeNode curNode) {
        if (curNode == null){
            return;
        }

        char hasChild, leftChild, rightChild;

        System.out.print("Does the node with data " + curNode.getData() + " have a left child (Y/N): ");
        hasChild = scan.nextLine().charAt(0);

        if (Character.toUpperCase(hasChild) == 'Y') {
            System.out.print("Enter data for left child: ");
            leftChild = scan.nextLine().charAt(0);

            curNode.setLeftChild(leftChild);
        }

        System.out.print("Does the node with data " + curNode.getData() + " have a right child (Y/N): ");
        hasChild = scan.nextLine().charAt(0);

        if (Character.toUpperCase(hasChild) == 'Y') {
            System.out.print("Enter data for right child: ");
            rightChild = scan.nextLine().charAt(0);

            curNode.setRightChild(rightChild);
        }

        enterTreeData(curNode.getLeftChild());
        enterTreeData(curNode.getRightChild());
    }

    public BinaryTreeNode getRoot() {
        return root;
    }
}