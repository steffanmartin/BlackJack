package com.application;
import com.domain.Hand;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Controller {

    private static Dealer dealer;
    private static Hand dealerHand, playerHand;
    private static boolean playerTurn;
    private static final Map<Character,String> optionsDescriptions =
            Map.of('P', "Play again", 'H', "Hit", 'S', "Stand", 'X', "Exit");
    private static Set<Character> options;

    public static boolean loop() throws InterruptedException {
        if (setupGame()) {
            if (!playerTurn()) return false;
            dealerTurn();
            determineResult();
        }
        return replay();
    }

    private static boolean setupGame() {
        // Game setup
        dealer.reshuffle();
        dealerHand = new Hand();
        playerHand = new Hand();
        playerHand.addCard(dealer.deal());
        dealerHand.addCard(dealer.deal());
        playerHand.addCard(dealer.deal());
        dealerHand.addCard(dealer.deal());
        playerTurn = true;
        printHands();
        if (playerHand.getOptimalValue() == 21) {
            System.out.println("Player wins!");
            return false;
        }
        options = new HashSet<>(optionsDescriptions.keySet());
        options.remove('P');
        return true;
    }

    private static boolean playerTurn() {
        // Player's turn
        char option;
        while ((option = getUserInput()) != 'S') {
            if (option == 'H') {
                playerHand.addCard(dealer.deal());
            } else if (option == 'X') {
                return false;
            }
            printHands();
            if (playerHand.getMinValue() > 21) break;
        }
        return true;
    }

    private static void dealerTurn() throws InterruptedException {
        // Dealer's turn
        playerTurn = false;
        options.clear();
        printHands();
        while (dealerHand.getOptimalValue() < 17) {
            dealerHand.addCard(dealer.deal());
            TimeUnit.SECONDS.sleep(3);
            printHands();
        }
    }

    private static void determineResult() {
        // Find the winner/loser(s)
        int playerScore = playerHand.getOptimalValue();
        int dealerScore = dealerHand.getOptimalValue();
        if (playerScore <= 21 && (playerScore > dealerScore || dealerScore > 21)) {
            System.out.println("Player wins!");
        } else if (dealerScore <= 21 && (dealerScore > playerScore || playerScore > 21)) {
            System.out.println("Dealer wins!");
        } else if (dealerScore == playerScore && playerScore <= 21) {
            System.out.println("Tie!"); //TODO: Determine tie-break hands
        } else {
            System.out.println("Both players bust");
        }
    }

    private static boolean replay() {
        options = new HashSet<>(){{ add('P'); add('X'); }};
        for (;;) {
            char option = getUserInput();
            if (option == 'X') {
                return false;
            } else if (option == 'P') {
                return true;
            }
        }
    }

    public static char getUserInput() {
        Scanner sc = new Scanner(System.in);
        char option;
        do {
            printOptions();
            option = sc.next().toUpperCase().charAt(0);
        } while (!options.contains(option));
        return option;
    }

    private static void printOptions() {
        for (Character option : options) {
            System.out.print(option + " - " + optionsDescriptions.get(option) + "; ");
        }
    }

    public static void printHands() {
        System.out.println("<<< Dealer >>>");
        System.out.println(dealerHand.toString(playerTurn));
        System.out.println("Hand value: " + (playerTurn ? "XX" : dealerHand.getOptimalValue()));
        System.out.println("<<< Player >>>");
        System.out.println(playerHand.toString(false));
        System.out.println("Hand value: " + playerHand.getOptimalValue());
    }

    public static void main(String[] args) throws InterruptedException {
        dealer = new Dealer();
        while (loop());
    }
}
