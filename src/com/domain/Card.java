package com.domain;

public class Card {

    public enum Suit {
        Clubs, Diamonds, Hearts, Spades;
    }

    public enum Value {
        Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King, Ace;
    }

    private final Suit suit;

    private final Value value;

    public Card(Suit suit, Value value) {
        this.suit = suit;
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public Value getValue() {
        return value;
    }

    public String[] stringComps(boolean hidden) {

        String suitSym = switch (suit) {
            case Clubs -> "\u2663";
            case Diamonds -> "\u2666";
            case Hearts -> "\u2665";
            case Spades -> "\u2660";
        };

        String valueSym = switch (value) {
            case Two -> "2";
            case Three -> "3";
            case Four -> "4";
            case Five -> "5";
            case Six -> "6";
            case Seven -> "7";
            case Eight -> "8";
            case Nine -> "9";
            case Ten -> "10";
            case Jack -> "J";
            case Queen -> "Q";
            case King -> "K";
            case Ace -> "A";
        };

        String[] rows = new String[5];
        if (hidden) {
            suitSym = "  ";
            valueSym = " ";
        }
        rows[0] = "̲ ̲ ̲ ̲ ̲ ̲ ";
        rows[1] = String.format("|%-2s%s|", valueSym, suitSym);
        rows[2] = "|    |";
        rows[3] = String.format("|%s%2s|", suitSym, valueSym);
        rows[4] = "‾‾‾‾‾‾";

        return rows;
    }

    public String toString(boolean hidden) {
        return String.join("\n", stringComps(hidden));
    }
}
