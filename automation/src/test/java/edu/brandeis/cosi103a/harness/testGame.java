package edu.brandeis.cosi103a.harness;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import edu.brandeis.cosi103a.harness.Automation_Card.AutomationCard;
import edu.brandeis.cosi103a.harness.Automation_Card.Framework;
import edu.brandeis.cosi103a.harness.Piles.MainPile;
import edu.brandeis.cosi103a.harness.Piles.Pile;

/**
 * Unit test for automation
 */
public class testGame {
    
    private Game game = new Game("Nancy", "Jiayi");
    private MainPile mainPile = (MainPile) game.MainPile;
    private Player player1 = game.player1;
    private Player player2 = game.player2;

     @Test
    public void testMainPile() {
        assertTrue(mainPile.size() == 160); //Are all the cards there
    }

    @Test
    public void testFrameWork() {
        game.playGame();
        assertTrue(mainPile.frameworkCount == 0);
    }

    @Test
    public void testCardCounts() {
        int totalCards = game.MainPile.size();
        //--------Are the cards being distributed correctedly?----------
        game.playGame();
        int count = game.turnCount;
        int cardsLeft = totalCards - 20; //Minus the initial drawPile distributed

        //Minus the two cards bought from each player in each turn, 
        //not including the first run in which they are just gaining their initial drawhands
        cardsLeft -= 2*(count-1); 

        assertEquals(game.MainPile.size(), cardsLeft);

        //Total number of cards should remain the same
        int playerCardCount = player1.getDiscardPile().size()+player1.getHand().size()+player1.getDrawPile().size();
        assertEquals(160, cardsLeft + playerCardCount*2); //2 players
    }

    @Test
    /**
     * Test correct AP counts for two players
     */
    public void testAPs() {
        int countAPs = 0;
        Pile pile = new Pile();

        Game game = new Game("Player1", "Player2");
        game.playGame();

        pile.addAll(player1.getDiscardPile());
        pile.addAll(player1.getHand());
        pile.addAll(player1.getDrawPile());

        for (int i = 0; i < pile.size(); i++) {
            Card card = pile.get(i);
            if (card instanceof AutomationCard) {
                countAPs += card.getValue();
            }
        }

        assertEquals(player1.getAPs(), countAPs);
    //------------------------------------------------
        int countAPs2 = 0;
        Pile pile2 = new Pile();

        pile2.addAll(player2.getDiscardPile());
        pile2.addAll(player2.getHand());
        pile2.addAll(player2.getDrawPile());

        for (int i = 0; i < pile2.size(); i++) {
            Card card = pile2.get(i);
            if (card instanceof AutomationCard) {
                countAPs2 += card.getValue();
            }
        }
        assertEquals(player2.getAPs(), countAPs2);
    }

    @Test
    /**
     * Test some specific turn conditions
     */
    public void testTakeTurn() {
        game.playGame();
        assertEquals(player1.getDiscardPile().size(), player2.getDiscardPile().size()); //Two players should have equal num of cards
        
        int temp = player1.getDiscardPile().size();
        game.buyPhase(player1);
        assertTrue(temp - player1.getDiscardPile().size() == -1);

        //After the game ends there should be no framework cards left
        assertTrue(!(player1.getDiscardPile().get(player1.getDiscardPile().size()-1) instanceof Framework));
    }

    /**
     * Test some specific turn conditions
     */
    @Test 
    public void testTakeTurnAgainAndAgain() {
        game.buyPhase(player1);
        game.buyPhase(player2);
        int AP1 = 0;
        int AP2 = 0;

        Card card1 = player1.getDiscardPile().get(player1.getDiscardPile().size()-1);
        Card card2 = player2.getDiscardPile().get(player2.getDiscardPile().size()-1);

        if (card1 instanceof AutomationCard) {
            AP1 += card1.getValue();
        }
        if (card2 instanceof AutomationCard) {
            AP2 += card2.getValue();
        }
        
        assertEquals(player1.getAPs(), AP1);
        assertEquals(player2.getAPs(), AP2);

        //Take another single buy decision without cleaning up just yet
        game.buyPhase(player1);
        game.buyPhase(player2);

        Card card3 = player1.getDiscardPile().get(player1.getDiscardPile().size()-1);
        Card card4 = player2.getDiscardPile().get(player2.getDiscardPile().size()-1);

        if (card1 instanceof AutomationCard) {
            AP1 += card3.getValue();
        }
        if (card2 instanceof AutomationCard) {
            AP2 += card4.getValue();
        }
        
        assertEquals(player1.getAPs(), AP1);
        assertEquals(player2.getAPs(), AP2);
    }



}
