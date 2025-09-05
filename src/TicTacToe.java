import java.util.Scanner;

public class TicTacToe extends Game {
    private static final char emptyBox = ' '; //creates the 3 possible things that could be on the board
    private static final char playerToken= 'X';
    private static final char botToken = 'O';

    private static char[][] board;

    public void playGame() { //actually plays game
        super.playGame();
        System.out.println("Welcome to Tic-Tac-Toe!");
        resetBoard(); // reset board at start of each game
        printBoard();

        while (true) {
            playerMove(Game.scanner); // use shared scanner from Game
            printBoard();
            if (gameOver()) break;

            System.out.println("\nAI is thinking...");
            int[] move = bestMove();
            board[move[0]][move[1]] = botToken;
            printBoard();
            if (gameOver()) break;
        }

        restartGame(); // ask player if they want to replay or return to menu
    }

    private static void printBoard() { //prints out the board
        System.out.println("  0 1 2"); //coordinates
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] == emptyBox ? "-" : board[i][j]); //fills each empty box with a dash using conditional operator
                if (j < 2) System.out.print("|"); //creates a grid and only goes to 2 so that it creates as close to a regular hash as possible
            }
            System.out.println();
        }
    }

    private static void playerMove(Scanner scanner) {
        int row, col;
        while (true) { //move loop
            System.out.print("Enter your move (row and column: 0-2 0-2, or Q to quit): ");
            if (scanner.hasNextInt()) {
                row = scanner.nextInt(); //takes next two numbers for coordinates
                col = scanner.nextInt();
                scanner.nextLine(); // consume leftover newline
                if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == emptyBox) { //makes sure user entered a valid coordinate
                    board[row][col] = playerToken; //places a token at the requested coordinate
                    break; //ends loop since the player didn't mess up coordinates
                }
                System.out.println("Invalid move. Try again.");
            } else {
                String input = scanner.nextLine().trim().toUpperCase();
                if (input.equals("Q")) { //allow quitting back to main menu
                    System.out.println("\nPick a new game to play!");
                    naim();
                    return;
                }
                System.out.println("Invalid input. Please enter valid coordinates or Q to quit.");
            }
        }
    }

    private static char checkWinner(char[][] board) { //all return winning token
        for (int i = 0; i < 3; i++) {
            //checks if win by rows
            if (board[i][0] != emptyBox &&
                    board[i][0] == board[i][1] &&
                    board[i][1] == board[i][2])
            { return board[i][0]; }
            //checks if win by columns
            if (board[0][i] != emptyBox &&
                    board[0][i] == board[1][i] &&
                    board[1][i] == board[2][i])
            { return board[0][i]; }
        }
        //checks if win by negative diagonal
        if (board[0][0] != emptyBox &&
                board[0][0] == board[1][1] &&
                board[1][1] == board[2][2])
        { return board[0][0]; }
        //checks if win by positive diagonal
        if (board[0][2] != emptyBox &&
                board[0][2] == board[1][1] &&
                board[1][1] == board[2][0])
        { return board[0][2]; }

        return emptyBox; //returns empty if theres no win
    }

    private static boolean isBoardFull(char[][] board) { //checks if any space is equal to emptyBox
        for (char[] row : board)
            for (char cell : row)
                if (cell == emptyBox) return false;
        return true;
    }

    private static int evaluateBoard(char[][] board) { //helps give a score to possible board
        char winner = checkWinner(board);
        if (winner == botToken) return 10;
        if (winner == playerToken) return -10;
        return 0;
    }

    private static void buildTree(TreeNode node, char currentPlayer) {
        if (checkWinner(node.board) != emptyBox || isBoardFull(node.board)) { //makes sure nobody won since there would be no point to the tree
            node.score = evaluateBoard(node.board);
            return;
        }

        for (int i = 0; i < 3; i++) { //finds every empty box in on the board and evaluates the moves the bot can make
            for (int j = 0; j < 3; j++) {
                if (node.board[i][j] == emptyBox) {
                    TreeNode child = new TreeNode(node.board);
                    child.board[i][j] = currentPlayer;
                    child.row = i;
                    child.col = j;
                    node.addChild(child);
                    buildTree(child, getOpponent(currentPlayer)); //recurses and creates every possible position
                }
            }
        }
    }

    private static int minimax(TreeNode node, boolean isMaximizing, int alpha, int beta) { //learned about this algorithm from geeksforgeeks.com
        char winner = checkWinner(node.board); //check if this is a leaf(game won)
        if (winner != emptyBox) return node.score = evaluateBoard(node.board); //scores +10 is bot won and -10 if player won
        if (isBoardFull(node.board)) return node.score = 0; //returns 0 if draw
        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE; //sets best score on whether a player or bot move is being simulated, starts at min for AIbecause it needs to keep raising the score
        for (TreeNode child : node.children) { //go through all possible boards
            int score = minimax(child, !isMaximizing, alpha, beta); //keep calling minimax and switches based on who is being simulated
            if (isMaximizing) { //if its the AI's turn, maximize score
                bestScore = Math.max(bestScore, score); //max() returns bigger of the two values
                alpha = Math.max(alpha, bestScore); // Update alpha (best guaranteed score for maximizer)
            }
            else { //if its the player's turn minimize score(because the AI is playing in worst case scenario ie the player makes all the best moves)
                bestScore = Math.min(bestScore, score);
                beta = Math.min(beta, bestScore);
            }
            if (beta <= alpha) break; //Pruning, if the move is worse than a previous move, then the code stops searching and moves to next one
        }

        return node.score = bestScore; //give current node best score and send it back
    }

    private static int[] bestMove() {
        TreeNode root = new TreeNode(board); //makes a tree node initialized to board
        buildTree(root, botToken); //builds tree
        minimax(root, true, Integer.MIN_VALUE, Integer.MAX_VALUE); //finds best move from minimax

        int[] bestMove = new int[2];
        int bestScore = Integer.MIN_VALUE;

        for (TreeNode child : root.children) { //sets the best move
            if (child.score > bestScore) {
                bestScore = child.score;
                bestMove[0] = child.row;
                bestMove[1] = child.col;
            }
        }
        return bestMove;
    }

    private static char getOpponent(char player) {
        return (player == 'X') ? 'O' : 'X'; //self explanatory
    }

    private static boolean gameOver() { // handles endings
        char winner = checkWinner(board);
        if (winner != emptyBox) {
            System.out.println("Game Over! Winner: " + winner);
            return true;
        }
        if (isBoardFull(board)) {
            System.out.println("Game Over! It's a draw.");
            return true;
        }
        return false;
    }

    private void restartGame() { //handles replay option
        System.out.println("Would you like to play again? (Y/N)");
        while (true) {
            String input = Game.scanner.nextLine().trim().toUpperCase();
            switch (input) {
                case "Y":
                    resetBoard();
                    playGame();
                    return;
                case "N":
                    System.out.println("\nPick a new game to play!");
                    naim();
                    return;
                default:
                    System.out.println("Invalid input. Please enter Y or N.");
            }
        }
    }

    private void resetBoard() { //resets the board for a new game
        board = new char[][]{
                {emptyBox, emptyBox, emptyBox},
                {emptyBox, emptyBox, emptyBox},
                {emptyBox, emptyBox, emptyBox}
        };
    }
}
