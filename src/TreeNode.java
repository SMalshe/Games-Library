import java.util.ArrayList;

class TreeNode {
    char[][] board;
    int score;
    ArrayList<TreeNode> children;
    int row, col;

    public TreeNode(char[][] board) { //every node has a copy of the board and a colletion of children
        this.board = copyBoard(board);
        this.children = new ArrayList<>();
    }

    public char[][] copyBoard(char[][] board) { //makes a copy of the board for the Node to use
        char[][] newBoard = new char[3][3];
        for (int i = 0; i < 3; i++)
            System.arraycopy(board[i], 0, newBoard[i], 0, 3);
        return newBoard;
    }

    public void addChild(TreeNode child) { //adds subnode
        children.add(child);
    }
}