package edu.brandeis.cosi103a.harness.Piles;

import edu.brandeis.cosi103a.harness.Cryptocurrency_Card.Bitcoin;
import edu.brandeis.cosi103a.harness.Cryptocurrency_Card.Dogecoin;
import edu.brandeis.cosi103a.harness.Cryptocurrency_Card.Ethereum;

public class CryptocurrencyCardPile extends Pile {

    /*
     * Cryptocurrency cards in the game:
     * -- Bitcoin x60 (cost: 0, value: 1)
     * -- Ethereum x40 (cost: 3, value 2)
     * -- Dogecoin x30 (cost: 6, value 3)
     */
    public CryptocurrencyCardPile() {
        super();
        initializePile();
    }

    private void initializePile() {
        for (int i = 0; i < 60; i++) {
            String name = "Bitcoin " + (i+1);
            this.addCard(new Bitcoin(name));
        }
        for (int i = 0; i < 40; i++) {
            String name = "Ethereum " + (i+1);
            this.addCard(new Ethereum( name));
        }
        for (int i = 0; i < 30; i++) {
            String name = "Dogecoin " + (i+1);
            this.addCard(new Dogecoin(name));
        }
    }

    public static void main(String[] args) {
        CryptocurrencyCardPile cp = new CryptocurrencyCardPile();
        cp.shuffle();
        System.out.println(cp);
    }


}