public class Card {
    private String rank; // rank of the card
    private String suit; // suit of the card
    private int value; // value of the card for blackjack

    //creates Card object
    public Card(String rank, String suit, int value) {
        this.rank = rank;
        this.suit = suit;
        this.value = value;
    }
    //returns rank of card
    public String getRank() {
        return rank;
    }
    // returns suit of card
    public String getSuit() {
        return suit;
    }
    //returns value of card
    public int getValue() {
        return value;
    }
    // returns details of the card
    public String toString() {
        return rank + " of " + suit;
    }
}
