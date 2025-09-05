import java.util.Scanner;

public class Game {
    private static String choice = "";
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Pick a game to play!");
        naim();
    }

    public static void naim() { //"home page" for users to pick a new game to play
        System.out.print(
                "Press B for Blackjack\nPress T for Tic Tac Toe\nPress N for NumberGuesser\nPress Q to quit\nEnter your choice: ");
        while (true) {
            choice = scanner.nextLine().toUpperCase().trim(); // ensures user input is upper case and has no white space
            switch (choice) {
                case "B":
                    Blackjack blackjack = new Blackjack();
                    blackjack.playGame(); //starts blackjack game
                    break;
                case "N":
                    NumberGuesser numberGuesser = new NumberGuesser();
                    numberGuesser.playGame(); // starts NumberGuesser game
                    break;
                case "T":
                    TicTacToe tictactoe = new TicTacToe();
                    tictactoe.playGame(); //starts TicTacToe game
                    break;
                case "Q":
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0); // exits the program
                    break;
                default:
                    System.out.println("Invalid input, please pick from the options given");
            }
        }
    }

    public void playGame() {
        System.out.println("\nLoading game...");
    }
}
