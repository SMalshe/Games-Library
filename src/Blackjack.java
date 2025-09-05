import java.util.ArrayList;
import java.util.Scanner;

public class Blackjack extends Game{
    //instance variables
    Scanner scanner = new Scanner(System.in);
    private static boolean playerTurn = true; //track turns, so that class knows which to play
    private static boolean dealerTurn = false;
    private static ArrayList<Card> playerHand = new ArrayList<>(); //keep track of hands
    private static ArrayList<Card> dealerHand = new ArrayList<>();
    private static Deck deck = new Deck(); //create deck object to use, static so that its same throughout program
    private static int session = 0;//just so the game looks formatted when playing
    //no constructor because we want to call default no-arg constructor
    public void playGame() { //main function that does everything basically
        if(session == 0){
            super.playGame();//inheritance from game class
            System.out.println("\n===== Welcome to Blackjack! =====");
        }
        System.out.println("\nGame " + (++session) + " started!");//tracks games
        System.out.println("--------------------------------");
        playerHand.add(deck.drawCard());// sets up game
        playerHand.add(deck.drawCard());
        dealerHand.add(deck.drawCard());
        dealerHand.add(deck.drawCard());

        System.out.println("Your hand: " + playerHand); //displays hands, works bc ArrayList inherits toString from List
        System.out.println("Dealer's hand: " + dealerHand.get(0) + " and [hidden]");
        while (playerTurn) {
            System.out.println("Would you like to hit or stand? (H to hit, S to stand)");//asks player input
            String input = scanner.nextLine().toUpperCase().trim();//toUpperCase so you don't have to check multiple cases,
            System.out.println();                                  //.trim to remove any extra spaces
            switch (input){ //Switch: New thing basically if-else ladder, but more readable and efficient for our use case
                case "H":
                    hit(playerHand); //calls hit function, defined later
                    break;
                case "S":
                    stand(); //calls stand, defined later
                    break;
                case "Q":
                    System.out.println("\nPick a new game to play!");
                    naim(); //quits and goes back to the main menu to pick other games
                    break;
                default: // Exception handling
                    System.out.println("Invalid input, please pick H to hit, S to stand, or Q to quit.\n");
                    break;
            }
        }
        while (dealerTurn) {
            playDealerTurn();//defined later
        }
    }
    public int getHandValue(ArrayList<Card> hand) { //returns total numerical value of all cards in a hand
        int handValue = 0;
        int aceCount = 0;
        for (Card c : hand) {
            if (c.getRank().equals("Jack") ||
                    c.getRank().equals("Queen") ||
                    c.getRank().equals("King")){
                handValue += 10;
            }
            else if (c.getRank().equals("Ace")) {
                handValue += 11;
                aceCount++;
            }
            else {
                handValue += c.getValue();
            }
        }
        while (handValue > 21 && aceCount > 0) {// changes ace value to 1 if hand value is over 21 to prevent bust
            handValue -= 10;
            aceCount--;
        }

        return handValue;
    }
    // adds a card to the deck and checks if it is a bust or not
    public void hit(ArrayList<Card> hand) {
        hand.add(deck.drawCard());
        System.out.println("You drew: The " + playerHand.get(playerHand.size() - 1));
        if (getHandValue(hand) > 21) {
            System.out.println("Bust! You lose.");
            restartGame();
        } else {
            System.out.println("Your hand: " + playerHand); //prints new hand
            System.out.println();
        }
    }
    // switches turns to dealer's turn
    public void stand() {
        playerTurn = false;
        dealerTurn = true;
    }
    private void restartGame() {//gives player options for what to do
        System.out.println("Would you like to play again? (Y/N)"); //asks if player wants to play again
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            switch (input) {
                case "Y":
                    System.out.println();
                    resetGame();
                    playGame();
                    break;
                case "N":
                    System.out.println("\nPick a new game to play!");//returns to main menu
                    session = 0;
                    naim();
                    break;
                default:
                    System.out.println("Invalid input. Please enter Y or N.");
            }
        }
    }
    public void resetGame() {//resets everything to defaults so future games don't get messed up
        playerTurn = true;
        dealerTurn = false;
        playerHand.clear();
        dealerHand.clear();
        deck.resetDeck();
    }
    public void playDealerTurn(){ //logic for dealer's turn
        while (getHandValue(dealerHand) < 17) {//trying to make dealer play similar to human, so it plays carefully
            dealerHit(dealerHand);
        }
        if (getHandValue(dealerHand) >= 17) {//dealer has small chance of hitting even when not safe, similar to human
            double decision = Math.random();
            if (decision > 0.75) {
                dealerHit(dealerHand);
            }
            else {
                dealerStand(); //most of the time dealer will play it safe
            }
        }
    }
    public void dealerHit(ArrayList<Card> hand){//dealer hits
        System.out.println("\nDealer hits...\n");
        System.out.println("Dealer's new hand: " + dealerHand + "");
        dealerHand.add(deck.drawCard());
        if (getHandValue(dealerHand) > 21) { //can't use checkWin because it prints the wrong thing
            System.out.println("Bust! You win!");//only want to use checkWin when dealer stands, because thats when scores
            restartGame();                       //matter
        }

    }
    public void dealerStand() {
        dealerTurn = false;
        System.out.println("Dealer stands.\n");
        checkWin(); // check win because both stands, so it's the only time score matters
        restartGame(); // restarts game
    }
    public void checkWin(){
        if(getHandValue(playerHand) > getHandValue(dealerHand)){
            System.out.print("Your score: " + getHandValue(playerHand) + "\nDealer's score: " + getHandValue(dealerHand) + "\nYou win! ");
        }
        else if(getHandValue(playerHand) < getHandValue(dealerHand)){
            System.out.print("Your score: " + getHandValue(playerHand) + "\nDealer's score: " + getHandValue(dealerHand) + "\nYou lose! ");
        }
        else {
            System.out.println("It's a tie!");
        }
    }
}
