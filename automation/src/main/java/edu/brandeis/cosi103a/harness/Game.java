package edu.brandeis.cosi103a.harness;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private List<Card> supply;
    private Player player1;
    private Player player2;

    public Game() {
        supply = new ArrayList<>();
        player1 = new Player();
        player2 = new Player();
        setupGame();
    }

    private void setupGame() {
        // Add cards to supply
        addCardsToSupply(new AutomationCard("Method", 2, 1), 14);
        addCardsToSupply(new AutomationCard("Module", 5, 3), 8);
        addCardsToSupply(new AutomationCard("Framework", 8, 6), 8);
        addCardsToSupply(new CryptocurrencyCard("Bitcoin", 0, 1), 60);
        addCardsToSupply(new CryptocurrencyCard("Ethereum", 3, 2), 40);
        addCardsToSupply(new CryptocurrencyCard("Dogecoin", 6, 3), 30);

        // Distribute starter decks
        distributeStarterDeck(player1);
        distributeStarterDeck(player2);

        // Shuffle decks
        player1.drawHand();
        player2.drawHand();
    }

    private void addCardsToSupply(Card card, int count) {
        for (int i = 0; i < count; i++) {
            supply.add(card);
        }
    }

    private void distributeStarterDeck(Player player) {
        for (int i = 0; i < 7; i++) {
            player.addCardToDeck(new CryptocurrencyCard("Bitcoin", 0, 1));
        }
        for (int i = 0; i < 3; i++) {
            player.addCardToDeck(new AutomationCard("Method", 2, 1));
        }
    }

    public void playGame() {
        while (!isGameOver()) {
            takeTurn(player1);
            takeTurn(player2);
        }
        determineWinner();
    }

    private void takeTurn(Player player) {
        int availableCoins = 0;

        // Play all coins in hand
        for (Card card : player.getHand()) {
            if (card instanceof CryptocurrencyCard) {
                availableCoins += ((CryptocurrencyCard) card).getValue();
            }
        }

        // Attempt to buy a card
        for (Card card : supply) {
            if (card.getCost() <= availableCoins) {
                player.getDiscardPile().add(card);
                supply.remove(card);
                break;
            }
        }

        // Cleanup phase
        player.discardHand();
        player.drawHand();
    }

    private boolean isGameOver() {
        return supply.stream().noneMatch(card -> card.getName().equals("Framework"));
    }

    private void determineWinner() {
        int player1AP = calculateAutomationPoints(player1);
        int player2AP = calculateAutomationPoints(player2);

        System.out.println("Player 1 AP: " + player1AP);
        System.out.println("Player 2 AP: " + player2AP);

        if (player1AP > player2AP) {
            System.out.println("Player 1 wins!");
        } else if (player2AP > player1AP) {
            System.out.println("Player 2 wins!");
        } else {
            System.out.println("It's a tie!");
        }
    }

    private int calculateAutomationPoints(Player player) {
        return player.getDeck().stream()
                .filter(card -> card instanceof AutomationCard)
                .mapToInt(card -> ((AutomationCard) card).getValue())
                .sum();
    }

    public List<Card> getSupply() {
        return supply;
    }
}
