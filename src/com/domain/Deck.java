package com.domain;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {

    private List<Card> cards = new ArrayList<>();

    public List<Card> getCards() {
        return cards;
    }

    /**
     * Initializes a new deck with 52 cards
     */
    public Deck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Value value : Card.Value.values()) {
                cards.add(new Card(suit, value));
            }
        }
    }

    /**
     * Get the current size of the deck
     * @return The number of cards in the deck
     */
    public int getSize() {
        return cards.size();
    }

    /**
     * Shuffles the card deck randomly
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Draw a card on top of the deck
     * @return The top card of the deck
     */
    public Card draw() {
        return cards.remove(cards.size()-1);
    }
}
