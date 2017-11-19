package labs.lab20;

import java.util.Stack;

public class Driver {

    public static void main(String[] args) {
        BinaryTree tree = new BinaryTree('+');
        System.out.println("Preorder: " + preOrderSearch(tree.getRoot()));
        System.out.println("Inorder: " + inOrderSearch(tree.getRoot()));
        System.out.println("Postorder: " + postOrderSearch(tree.getRoot()));

        System.out.println("Postfix evaluation: " + postfixEvaluation(postOrderSearch(tree.getRoot())));
        System.out.println("Prefix evaluation: " + prefixEvaluation(preOrderSearch(tree.getRoot())));
    }

    private static String preOrderSearch(BinaryTreeNode root) {
        StringBuilder ret = new StringBuilder();
        preOrder(root, ret);
        return ret.toString();
    }

    private static void preOrder(BinaryTreeNode root, StringBuilder sb) {
        if (root != null) {
            sb.append(root.getData() + " ");
            preOrder(root.getLeftChild(), sb);
            preOrder(root.getRightChild(), sb);
        }
    }

    private static String inOrderSearch(BinaryTreeNode root) {
        StringBuilder ret = new StringBuilder();
        inOrder(root, ret);
        return ret.toString();
    }

    private static void inOrder(BinaryTreeNode root, StringBuilder sb) {
        if (root != null) {
            inOrder(root.getLeftChild(), sb);
            sb.append(root.getData() + " ");
            inOrder(root.getRightChild(), sb);
        }
    }

    private static String postOrderSearch(BinaryTreeNode root) {
        StringBuilder ret = new StringBuilder();
        postOrder(root, ret);
        return ret.toString();
    }

    private static void postOrder(BinaryTreeNode root, StringBuilder sb) {
        if (root != null) {
            postOrder(root.getLeftChild(), sb);
            postOrder(root.getRightChild(), sb);
            sb.append(root.getData() + " ");
        }
    }

    private static int prefixEvaluation(String prefixStr) {
        String[] expr = prefixStr.split(" ");
        Stack<String> stack = new Stack<>();

        for (int i = expr.length - 1; i >= 0; i--) {
            if (Character.isDigit(expr[i].charAt(0))) {
                stack.push(expr[i]);
            } else {
                String op1 = stack.pop();
                String op2 = stack.pop();
                stack.push(Integer.toString(evalExpression(expr[i], op1, op2)));
            }
        }

        return Integer.parseInt(stack.pop());
    }

    private static int postfixEvaluation(String postfixStr) {
        String[] expr = postfixStr.split(" ");
        Stack<String> stack = new Stack<>();

        for (String s : expr) {
            if (Character.isDigit(s.charAt(0))) {
                stack.push(s);
            } else {
                String op2 = stack.pop();
                String op1 = stack.pop();
                stack.push(Integer.toString(evalExpression(s, op1, op2)));
            }
        }
        
        return Integer.parseInt(stack.pop());
    }

    private static int evalExpression(String operator, String op1, String op2) {
        int num1 = Integer.parseInt(op1);
        int num2 = Integer.parseInt(op2);

        switch (operator) {
        case "+": return num1 + num2;
        case "-": return num1 - num2;
        case "*": return num1 * num2;
        case "/": return num1 / num2;
        case "%": return num1 % num2;
        case "^": return (int)Math.pow(num1, num2);
        }
        return 0;
    }
}