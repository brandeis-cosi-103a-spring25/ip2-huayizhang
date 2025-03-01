package edu.brandeis.cosi103a.harness.Piles;

import edu.brandeis.cosi103a.harness.Automation_Card.Framework;
import edu.brandeis.cosi103a.harness.Automation_Card.Method;
import edu.brandeis.cosi103a.harness.Automation_Card.Module;

public class AutomationCardPile extends Pile {
    
    /**
     * Automation Cards in the game:
     * -- Method x14 (cost: 2, value: 1)
     * -- Module x8 (cost: 5, value: 3)
     * -- Framework x8 (cost: 8, value: 6)
     * 
     */
    public AutomationCardPile() {
        super();
        initializeCards();
    }

    private void initializeCards() {
        for (int i = 0; i < 14; i++) {
            String name = "Method " + (i+1);
            this.addCard(new Method(name));
        }
        for (int i = 0; i < 8; i++) {
            String name = "Module " + (i+1);
            this.addCard(new Module(name));
        }
        for (int i = 0; i < 8; i++) {
            String name = "Framework " + (i+1);
            this.addCard(new Framework(name));
        }
    }

    public static void main(String[] args) {
        AutomationCardPile cp = new AutomationCardPile();
        System.out.println(cp);
    }

}