class RedBlackTree {
    Node root;

    static class Node {
        private final int value;
        private Node left;
        private Node right;
        private Color color;

        public Node(final int value) {
            this.value = value;
        }
    }

    public boolean find(int value) {
        Node node = root;
        while (node != null) {
            if (node.value == value) {
                return true;
            }
            if (node.value < value) {
                node = node.right;
            } else {
                node = node.left;
            }
        }
        return false;
    }

    public void add(int value) {
        if (root == null) {
            root = new Node(value);
        } else {
            boolean result = addNode(root, value);
            root = rebalance(root);
        }
        root.color = Color.BLACK;
    }

    private boolean addNode(Node node, int value) {
        if (node.value == value) {
            return false;
        }
        if (node.value < value) {
            if (node.right == null) {
                node.right = new Node(value);
                node.right.color = Color.RED;
                return true;
            } else {
                boolean result = addNode(node.right, value);
                node.right = rebalance(node.right);
                return result;
            }
        } else {
            if (node.left == null) {
                node.left = new Node(value);
                node.left.color = Color.RED;
                return true;
            } else {
                boolean result = addNode(node.left, value);
                node.left = rebalance(node.left);
                return result;
            }
        }
    }

    private Node rebalance(Node node) {
        Node result = node;
        boolean needRebalance;

        do {
            needRebalance = false;
            if (result.right != null && result.right.color == Color.RED &&
                    (result.left == null || result.left.color == Color.BLACK)) {
                needRebalance = true;
                result = leftSwap(result);
            }
            if (result.left != null && result.left.color == Color.RED &&
                    result.left.left != null && result.left.left.color == Color.RED) {
                needRebalance = true;
                result = rightSwap(result);
            }
            if (result.left != null && result.left.color == Color.RED &&
                    result.right != null && result.right.color == Color.RED) {
                needRebalance = true;
                colorSwap(result);
            }
        }
            while (needRebalance) ;
            return result;
        }

        private Node leftSwap (Node node){
            Node rightChild = node.right;
            Node tempNode = rightChild.left;
            rightChild.left = node;
            node.right = tempNode;
            rightChild.color = node.color;
            node.color = Color.RED;
            return rightChild;
        }

        private Node rightSwap (Node node){
            Node leftChild = node.left;
            Node tempNode = leftChild.right;
            leftChild.right = node;
            node.left = tempNode;
            leftChild.color = node.color;
            node.color = Color.RED;
            return leftChild;
        }

        private void colorSwap (Node node){
            node.color = Color.RED;
            node.left.color = Color.BLACK;
            node.right.color = Color.BLACK;
        }

     private enum Color {
        RED, BLACK
    }
}

public class Main {
    public static void main(String[] args) {

        RedBlackTree tree = new RedBlackTree();

        for (int i = 0; i < 10; i++) {
            tree.add(i);
        }
    }
}