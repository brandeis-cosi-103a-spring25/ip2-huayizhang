package edu.brandeis.cosi103a.harness.Piles;

public class MainPile extends Pile {

    public int frameworkCount;

    public MainPile() {
        super();
        Pile automationCardPile = new AutomationCardPile();
        Pile cryptocurrencyCardPile = new CryptocurrencyCardPile();
        this.addAll(automationCardPile);
        this.addAll(cryptocurrencyCardPile);

        this.frameworkCount = 0;
        for (int i = 0; i < this.pile.size(); i++) {
            if (this.pile.get(i).getName().contains("Framework")) {
                this.frameworkCount++;
            }
        }
    }
    
    public static void main(String[] args) {
        MainPile mp = new MainPile();
        System.out.println(mp);
    }

}
