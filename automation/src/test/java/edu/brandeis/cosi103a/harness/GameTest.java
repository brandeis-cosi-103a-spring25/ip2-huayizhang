package edu.brandeis.cosi103a.harness;
import java.util.*;

import static org.junit.Assert.assertTrue;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GameTest {

    @Test
    public void testGame() {
        Player player = new Player();
        Card card = new CryptocurrencyCard("Bitcoin", 0, 1);
        player.addCardToDeck(card);

        assertEquals(card, player.getDeck().get(0));
    }
}
