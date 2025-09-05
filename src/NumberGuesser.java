import java.util.Scanner;
import java.util.ArrayList;

public class NumberGuesser extends Game {
    //all instance variables even temporary ones because it's easier to locate  and you don't need to keep making new ones
    private Scanner scanner = new Scanner(System.in); //scanner at top so you dont need to make new one every time
    private int max = 0;
    private int min = 0;
    private static int cguesses = 0;
    private static int guess = 0;
    private static int session = 0;
    private static int numtoGuess = 0;
    private ArrayList<Integer> guessed = new ArrayList<>();

    public void playGame() {//overrides
        super.playGame();//calls superclass playGame method
        if (session == 0) {
            System.out.println("\n===== Welcome to NumberGuesser! =====");
        }
        System.out.println("\nGame " + (++session) + " started!");
        System.out.println("--------------------------------");
        System.out.println("Do you want to play the easy, medium, hard, or extreme difficulty?\n(Press E for Easy, M for Medium, H for Hard, X for Extreme, I for Insane, or C for Custom)");
        while (true) {//so it just runs forever until theres a valid input, when it will call jugar()
            String input = scanner.nextLine().trim().toUpperCase();
            switch(input) {
                case "E":
                    jugar(4, 0, 10);
                    break;
                case "M":
                    jugar(7, 0, 100);
                    break;
                case "H":
                    jugar(10, 0, 1000);
                    break;
                case "X":
                    jugar(14, 0, 10000);
                    break;
                case "I":
                    jugar(20, 0, 1000000);
                    break;
                case "C":
                    getCustomRange();
                    break;
                case "Q":
                    System.out.println("\nPick a new game to play!");
                    naim();
                    break;
                default:
                    System.out.println("Invalid input, please pick E, M, H, X, I, C, or Q to quit to main menu.");
                    break;
            }
        }
    }

    private void getCustomRange() {//gets custom range from user
        System.out.println("Enter the minimum value: ");
        while (!scanner.hasNextInt()) {//exception handling for if user inputs a string
            System.out.println("Invalid input. Please enter an integer: ");
            scanner.next();
        }
        min = scanner.nextInt();

        System.out.println("Enter the maximum value: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer: ");
            scanner.next();
        }
        max = scanner.nextInt();

        if (min >= max) {
            System.out.println("Invalid range, make sure the minimum should be less than maximum.");
            getCustomRange();//counts as recursion?
        } else {
            cguesses = (int) Math.ceil(Math.log(max - min) / Math.log(2));// uses binary search logic to see how many guesses should be allowed based on given range. Log base(2) found using something i learned in EM3
            jugar(cguesses, min, max);
        }
    }

    private void jugar(int guesses, int low, int high) {//actually plays the game(jugar = play in spanish)
        numtoGuess = (int) (Math.random() * (high - low + 1)) + low;
        System.out.println("\nI'm thinking of a number between " + low + " and " + high
                + ", both inclusive, and you have to guess it in " + guesses + " tries. What's your first guess?\n");

        while (guesses > 0) {
            System.out.print("Enter your guess: ");
            if (scanner.hasNextInt()) {
                guess = scanner.nextInt();
            } else if (scanner.nextLine().equalsIgnoreCase("Q")) {
                System.out.println("\nPick a new game to play!");
                resetGame();
                naim();
                break;
            } else {
                System.out.print("Invalid input. Please enter an integer or press Q to quit: ");
                continue;
            }

            if (guess < low || guess > high) {
                System.out.println("\nYour guess is out of range. Please try again.\n");
                continue;
            }

            if (guessed.contains(guess)) {
                System.out.println("Already guessed, try another number");
                continue;
            }

            guessed.add(guess);
            guesses--;

            if (guess > numtoGuess && guesses > 0) {
                System.out.println("The number I'm thinking of is lower than " + guess + ". ");
            } else if (guess < numtoGuess && guesses > 0) {
                System.out.println("The number I'm thinking of is higher than " + guess + ". ");
            } else if(numtoGuess == guess && guesses>=0){
                System.out.println("You win! The number I was thinking of was " + numtoGuess + ".\n");
                restartGame();
                break;
            }

            if (guesses > 0) {
                System.out.println("You have " + guesses + " " + (guesses == 1 ? "guess" : "guesses") + " left.\n"); //Conditional operator ? new thing
            } else {
                System.out.println("You lose! The number I was thinking of was " + numtoGuess + ".\n");
                restartGame();
            }
        }
    }

    private void restartGame() {
        System.out.println("Would you like to play again? (Y/N)");
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            switch (input) {
                case "Y":
                    System.out.println();
                    resetGame();
                    playGame();
                    break;
                case "N":
                    System.out.println("\nPick a new game to play!");
                    session = 0;
                    naim();
                    break;
                default:
                    System.out.println("Invalid input. Please enter Y or N.");
            }
        }
    }

    private void resetGame() {
        numtoGuess = 0;
        guess = 0;
        guessed.clear();
    }
}
