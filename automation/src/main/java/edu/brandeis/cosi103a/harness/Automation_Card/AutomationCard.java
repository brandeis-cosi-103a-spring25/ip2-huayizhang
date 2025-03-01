package edu.brandeis.cosi103a.harness.Automation_Card;

import edu.brandeis.cosi103a.harness.Card;

public abstract class AutomationCard implements Card {

    protected String name;
    protected int cost;
    protected int apValue; //The number of APs the card is worth at the end of the game

    public AutomationCard(int cost, int apValue, String name) {
        this.cost = cost;
        this.apValue = apValue;
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
        return apValue;
    }

}
