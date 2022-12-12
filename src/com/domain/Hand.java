package com.domain;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Hand {

    private List<Card> cards;

    public Hand() {
        this.cards = new ArrayList<>();
    }

    public String toString(boolean hideFirst) {
        AtomicInteger idx = new AtomicInteger();
        List<String[]> cardRows = cards.stream().map(
                card -> card.stringComps(hideFirst && (idx.incrementAndGet()) == 1)).toList();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            sb.append(String.join("\t", cardRows.stream().map(x -> x[finalI]).toList()));
            if (i < 4) sb.append("\n");
        }
        return sb.toString();
    }

    public void flush() {
        cards.clear();
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public int getNumericValue(Card card, boolean max) {
        return (switch (card.getValue()) {
            case Two -> 2;
            case Three -> 3;
            case Four -> 4;
            case Five -> 5;
            case Six -> 6;
            case Seven -> 7;
            case Eight -> 8;
            case Nine -> 9;
            case Ten,Jack,Queen,King -> 10;
            case Ace -> max ? 11 : 1;
        });
    }

    public int getMinValue() {
        return cards.stream().reduce(0,
                (subtotal,card) -> subtotal + getNumericValue(card, false), Integer::sum);
    }

    public int getMaxValue() {
        return cards.stream().reduce(0,
                (subtotal,card) -> subtotal + getNumericValue(card, true), Integer::sum);
    }

    public int getOptimalValue() {
        int sum = 0;
        List<Card> aces = new ArrayList<>();
        for (Card card : cards) {
            if (card.getValue().equals(Card.Value.Ace)) {
                aces.add(card);
            } else {
                sum += getNumericValue(card, false);
            }
        }
        if (!aces.isEmpty() && (sum <= 10)) {
            Card bigAce = aces.remove(0);
            sum += getNumericValue(bigAce, true);
        }
        for (Card ace : aces) {
            sum += getNumericValue(ace, false);
        }
        return sum;
    }
}
