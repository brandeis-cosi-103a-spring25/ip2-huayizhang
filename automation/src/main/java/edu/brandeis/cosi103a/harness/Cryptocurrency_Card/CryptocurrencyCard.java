package edu.brandeis.cosi103a.harness.Cryptocurrency_Card;
import edu.brandeis.cosi103a.harness.Card;

public abstract class CryptocurrencyCard implements Card {
    
    protected String name;
    protected int cost;
    protected int value; //The number of cryptocoins the card is worth when played


    public CryptocurrencyCard (int cost, int value, String name) {
        this.cost = cost;
        this.value = value;
        this.name = name;
    }

    @Override
    public int getCost() {
        return cost;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getValue() {
        return value;
    }

}
