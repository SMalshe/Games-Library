import java.util.ArrayList; // Arhaan Kapil Lahoti
import java.util.Collections;

public class Deck {
    private ArrayList<Card> cards; // deck of cards
    private final String[][] cardInfo = { //card details, 2D array, final keyword because it needs to stay same
            {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"},
            {"Hearts", "Diamonds", "Clubs", "Spades"}
    };
    private static final int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13}; // card values for blackjack

    public Deck() {
        cards = new ArrayList<>();
        for (String suit : cardInfo[1]) {
            for (int i = 0; i < cardInfo[0].length; i++) {
                cards.add(new Card(cardInfo[0][i], suit, values[i])); // creates a card for each suit and rank, and adds it to the deck
            }
        }
        shuffle(); //randomizes the deck
    }

    public void shuffle() {
        Collections.shuffle(cards); //uses shuffle method from java.util.Collections
    }

    public Card drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(0); //removes and returns the first card from the d1eck
        } else {
            System.out.println("Deck is empty. Cannot draw a card.");
            return null;
        }
    }

    public String toString() { // returns total number of cards in the deck
        return "Deck of " + cards.size() + " cards";
    }

    public void resetDeck() {
        cards.clear(); //clears the deck
        for (String suit : cardInfo[1]) {
            for (int i = 0; i < cardInfo[0].length; i++) {
                cards.add(new Card(cardInfo[0][i], suit, values[i])); // creates and adds each card back into the deck
            }
        }
        shuffle(); //randomizes the deck
    }
}
