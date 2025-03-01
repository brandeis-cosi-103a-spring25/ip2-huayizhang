package edu.brandeis.cosi103a.harness.Piles;

import java.util.ArrayList;
import java.util.Collections;

import edu.brandeis.cosi103a.harness.Card;
import edu.brandeis.cosi103a.harness.Cryptocurrency_Card.Bitcoin;

public class Pile {
    
    protected ArrayList<Card> pile;

    public Pile() {
        this.pile = new ArrayList<>();
    }

    public void addCard(Card card) {
        this.pile.add(card);
    }

    public void removeCard(Card card) {
        this.pile.remove(card);
    }

    public void shuffle() {
        Collections.shuffle(this.pile);
    }

    public Card get(int i) {
        return this.pile.get(i);
    }

    public void addAll (Pile pile) {
        this.pile.addAll(pile.pile);
    }

    public int size() {
        return this.pile.size();
    }

    public ArrayList<Card> getPile() {
        return pile;
    }

    public boolean isEmpty() {
        return this.pile.isEmpty();
    }

    @Override
    public String toString() {
        String result = "";
        for (Card card : this.pile) {
            result += card.getName() + " \n";
        }
        return result;
    }

    public static void main(String[] args) {
        Pile p = new Pile();
        System.out.println(p);
        Card card = new Bitcoin("Bitcoin1");
        p.addCard(card);

        System.out.println(p.size());
        p.removeCard(card);

        System.out.println(p.size());
    }

}
