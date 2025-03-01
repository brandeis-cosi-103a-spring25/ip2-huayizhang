package edu.brandeis.cosi103a.harness;

import edu.brandeis.cosi103a.harness.Automation_Card.AutomationCard;
import edu.brandeis.cosi103a.harness.Cryptocurrency_Card.CryptocurrencyCard;
import edu.brandeis.cosi103a.harness.Piles.Pile;

public class Player {
    
    private Pile drawPile = new Pile();
    private Pile discardPile = new Pile();
    private Pile hand = new Pile();

    private String name;
    private int cryptocoins = 0;
    private int APs = 0;

    public Player(String name) {
        this.name = name;
        this.drawPile = new Pile();
        this.discardPile = new Pile();
        this.cryptocoins = 0;
        this.APs = 0;
    }

    public void playCard(Card card) {
        this.drawPile.removeCard(card);
    }

    /**
     * Add a card directly into player's discard pile
     * @param card the card bought
     */
    public void buyCard(Card card) {
        if (this.cryptocoins >= card.getCost()) {
            this.cryptocoins -= card.getCost();
            this.discardPile.addCard(card);
        } else {
            throw new IllegalArgumentException("Not enough cryptocoins to buy this card");
        }
        if (card instanceof AutomationCard) {
            this.APs += card.getValue();
        }
    }

    //A player can only buy with the current cryptocoins they have in the current round of hands
    public void setCryptocoins() {
        for (int i = 0; i < this.hand.size(); i++) {
            Card card = this.hand.get(i);
            if (card instanceof CryptocurrencyCard) {
                this.cryptocoins += card.getValue();
            }
        }
    }

    public int getCryptocoins() {
        return this.cryptocoins;
    }

    public String getName() {
        return this.name;
    }

    public int getAPs() {
        return this.APs;
    }

    public Pile getDrawPile() {
        return this.drawPile;
    }

    public Pile getDiscardPile() {
        return this.discardPile;
    }

    public Pile getHand() {
        return this.hand;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
