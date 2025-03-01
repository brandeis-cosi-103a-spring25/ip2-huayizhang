package edu.brandeis.cosi103a.harness;

import java.util.ArrayList;
import java.util.Random;

import edu.brandeis.cosi103a.harness.Automation_Card.AutomationCard;
import edu.brandeis.cosi103a.harness.Cryptocurrency_Card.CryptocurrencyCard;
import edu.brandeis.cosi103a.harness.Piles.MainPile;
import edu.brandeis.cosi103a.harness.Piles.Pile;


public class Game {

    public Player player1;
    public Player player2;
    public Pile MainPile;
    public Pile DiscardedPile;
    public int turnCount;

    public Game(String name1, String name2) {
        this.player1 = new Player(name1);
        this.player2 = new Player(name2);
        this.MainPile = new MainPile();
    }

    //Each player begins with a starter deck, consisting of 7 Bitcoins and 3 Methods
    //Remember, CANNOT alter the arrayList in a for-each loop! Or use an iterator instead.
    public void initializeDrawPile (Player player) {
        int BitCoinCount = 0;
        int MethodCount = 0;
        ArrayList<Card> cardsToRemove = new ArrayList<>();
        this.MainPile.shuffle();

        for (Card card: this.MainPile.getPile()) {
            if (BitCoinCount < 7 && card.getName().contains("Bitcoin")) {
                player.getDrawPile().addCard(card);
                cardsToRemove.add(card);
                BitCoinCount++;
            } else if (MethodCount < 3 && card.getName().contains("Method")) {
                player.getDrawPile().addCard(card);
                cardsToRemove.add(card);
                MethodCount++;
            }

            if (BitCoinCount == 7 && MethodCount == 3) {
                break;
            }
        }    

        player.getDrawPile().shuffle();

        for (Card cardToRemove: cardsToRemove) {
            this.MainPile.removeCard(cardToRemove);
        }
    }

    //This is an automated machine player, so it will just buy the card with the maximum value of AP with its current cryptocoins
    public void buyPhase(Player player) {
        player.setCryptocoins();

        System.out.println(player.getName() + " has " + player.getCryptocoins() + " cryptocoins.");
        
        int maxAP = -1;
        Card bestAutomationCard = null;

        for (Card card: this.MainPile.getPile()) {
            if (card instanceof AutomationCard) {
                if (player.getCryptocoins() >= card.getCost() && card.getValue() > maxAP) {
                    maxAP = card.getValue();
                    bestAutomationCard = card;
                }     
            }
        }
        if (bestAutomationCard != null) {
            player.buyCard(bestAutomationCard);
            this.MainPile.removeCard(bestAutomationCard);

            if (bestAutomationCard.getName().contains("Framework")) {
                ((MainPile) this.MainPile).frameworkCount--;
            }

            System.out.println(player.getName() + " bought " + bestAutomationCard.getName() + ".");
            System.out.println(player.getName() + " has " + player.getCryptocoins() + " cryptocoins left.");
            System.out.println("\n");

            return;
        } 

        //If there are no Automation cards left in the main pile, buy the card with the maximum value of cryptocoins
        int maxCryptoCurrency = -1;
        Card bestCryptocurrencyCard = null;

        for (Card card: this.MainPile.getPile()) {
            if (card instanceof CryptocurrencyCard) {
                if (player.getCryptocoins() >= card.getCost() && card.getValue() > maxCryptoCurrency) {
                    maxCryptoCurrency = card.getValue();
                    bestCryptocurrencyCard = card;
                }     
            }
        }
        if (bestCryptocurrencyCard != null) {
            player.buyCard(bestCryptocurrencyCard);
            this.MainPile.removeCard(bestCryptocurrencyCard);

            System.out.println(player.getName() + " bought " + bestCryptocurrencyCard.getName());
            System.out.println(player.getName() + " has " + player.getCryptocoins() + " cryptocoins left.");
            System.out.println("\n");
        }
    }

    public void cleanUpPhase(Player player) {
        //Discard all cards in the player's hand
        player.getDiscardPile().addAll(player.getHand());
        player.getHand().getPile().clear();

        //Add new hand through the draw pile
        for (int i = 0; i < 5; i++) {
            if (player.getDrawPile().isEmpty()) { //If the draw pile is empty, shuffle the discard pile and add it to the draw pile
                player.getDrawPile().addAll(player.getDiscardPile());
                player.getDiscardPile().getPile().clear();
                player.getDrawPile().shuffle();
            }
            if (!player.getDrawPile().getPile().isEmpty()) {
                Card drawnCard = player.getDrawPile().get(0);
                player.getHand().addCard(drawnCard);
                player.getDrawPile().removeCard(drawnCard);
            } else {
                break;
            }
        }   
        System.out.println("This is " + player.getName() + "'s new hand: \n" + player.getHand());
    }
    
    public void buy(Player player1, Player player2) {
        System.out.println("Buying...");

        buyPhase(player1);
        buyPhase(player2);
    }

    public void cleanUp(Player player1, Player player2) {
        System.out.println("Cleaning up...");
        
        cleanUpPhase(player1);
        cleanUpPhase(player2);
    }

    public void logAPs(Player player1, Player player2) {
        System.out.println(player1.getName() + " now has " + player1.getAPs() + " Automation points!");
        System.out.println(player2.getName() + " now has " + player2.getAPs() + " Automation points!");
        if (player1.getAPs() > player2.getAPs()) {
            System.out.println("Looks like " + player2.getName() + " needs to catch up!\n" 
            + player1.getName() + ", keep up the good work!");
        } else if (player2.getAPs() > player1.getAPs()) {
            System.out.println("Looks like " + player1.getName() + " needs to catch up!\n" 
            + player2.getName() + ", keep up the good work!");
        } else {
            System.out.println("Oh my god, it's a tie -- things are getting heated up here!\n");
        }
    }

    public void takeTurn(Player player1, Player player2) {
        buy(player1, player2);
        cleanUp(player1, player2);
    }

    public void decideWinner(Player player1, Player player2) {
        System.out.println(player1.getName() + " has " + player1.getAPs() + " points.");
        System.out.println(player2.getName() + " has " + player2.getAPs() + " points.");
        
        if (player1.getAPs() > player2.getAPs()) {
            System.out.println(player1.getName() + " wins!");
        }

        if (player2.getAPs() > player1.getAPs()) {
            System.out.println(player2.getName() + " wins!");
        }

        if (player2.getAPs() == player1.getAPs()) {
            System.out.println("It's a tie! We love world peace don't we?");
        }
    }

    public void logFrameworkCounts() {
        System.out.println("There are " + ((MainPile)this.MainPile).frameworkCount + " Method cards left.\nWho will be the winner?\n");
    }

    public void gameStarterBanners(int i) {
        if (i == 1) {
            System.out.println("We are flipping the coin...");
            System.out.println("It's heads!");
            System.out.println(this.player1.getName() + " will play first!");
            System.out.println("Sorry, " + this.player2.getName() + "!\n" 
            + "I am sure luck will be with you later in the game!");
        } else {
            System.out.println("We are flipping the coin...");
            System.out.println("It's tails!");
            System.out.println(this.player2.getName() + " will play first!");
            System.out.println("Sorry, " + this.player1.getName() + "!\n" 
            + "I am sure luck will be with you later in the game!");
        }
    }

    public void playGame() {
        Random rand = new Random();
        int seed = rand.nextInt((2 - 1) + 1) + 1;

        //---Game starts here---
        gameStarterBanners(seed);

        if (seed == 1) {
            initializeDrawPile(player1);
            initializeDrawPile(player2);
            turnCount = 1;

            while (((MainPile)this.MainPile).frameworkCount > 0) {
                System.out.println("Turn " + turnCount + " has started!");
                
                logFrameworkCounts();
                takeTurn(this.player1, this.player2);
                logAPs(player1, player2);

                System.out.println("Turn " + turnCount + " has ended!");

                System.out.println("------------------------------------------");

                turnCount++;
            }

            System.out.println("Game Over!");
            decideWinner(player1, player2);

        } else {
            initializeDrawPile(player2);
            initializeDrawPile(player1);
            turnCount = 1;

            while (((MainPile)this.MainPile).frameworkCount > 0) {
                System.out.println("Turn " + turnCount + " has started!");
                
                logFrameworkCounts();
                takeTurn(this.player2, this.player1);
                logAPs(player2, player1);

                System.out.println("Turn " + turnCount + " has ended!");
                System.out.println("------------------------------------------");

                turnCount++;
            }

            System.out.println("Game Over!");
            decideWinner(player2, player1);

        }
    }

    // public static void main(String[] args) {
    //     Player player1 = new Player("Alice");
    //     Player player2 = new Player("Bob");
    //     Game game = new Game("Alice", "Bob");

    //     game.initializeDrawPile(player1);
    //     // System.out.println(player1.drawPile);
    //     // System.out.println(player1.discardPile);
    //     game.MainPile.shuffle();

    //     System.out.println(game.MainPile.get(2).getName());
    //     game.cleanUpPhase(player1);

    //     game.playGame();
    //     System.out.println(game.MainPile.size());
    // }

}
