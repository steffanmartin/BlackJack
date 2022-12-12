package com.application;
import com.domain.Card;
import com.domain.Deck;

public class Dealer {

    private Deck deck;

    public Dealer() {
        deck = new Deck();
    }

    public Card deal() {
        return deck.draw();
    }

    public void reshuffle() {
        if (deck.getSize() < 10) {
            deck = new Deck();
        }
        deck.shuffle();
    }
}
