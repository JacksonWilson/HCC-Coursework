package labs.lab20;

    public class BinaryTreeNode {
        private char data;
        private BinaryTreeNode vL;
        private BinaryTreeNode vR;

        public BinaryTreeNode (char data) {
            this.data = data;

            vL = null;
            vR = null;
        }

        public BinaryTreeNode getLeftChild() {
            return vL;
        }

        public void setLeftChild(char data) {
            if (vL == null) {
                vL = new BinaryTreeNode(data);
            }
            else {
                vL.setData(data);
            }
        }

        public BinaryTreeNode getRightChild() {
            return vR;
        }

        public void setRightChild(char data) {
            if (vR == null) {
                vR = new BinaryTreeNode(data);
            }
            else {
                vR.setData(data);
            }
        }

        public char getData () {
            return data;
        }

        public void setData (char data) {
            this.data = data;
        }
    }