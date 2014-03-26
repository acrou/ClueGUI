import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.*;


import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
* Created by Ally and Marissa on 3/11/14.
*/
public class GameSetupTests {

    private static Board b;
    private static ClueGame myGame;

    /**Setups the tests by constructing
     * a clue game complete with read in
     * Players and Cards
     * @throws FileNotFoundException
     */
    @BeforeClass
    public static void setUp() throws FileNotFoundException {
         b = new Board("gameLayout.csv","legend.txt");
         myGame = new ClueGame (b);
    }
    /**
     * Testing loading people
     */

    @Test
    public void testHumanPlayer(){
         //test the name has first and last because human might only enter one and
         //program must handle it
         assertEquals(myGame.getHuman().getName().split(" ").length, 2);
        //test the color is red
        assertEquals(myGame.getHuman().getColor(), Color.RED);
        //and the location is correct
        assertEquals(myGame.getBoard().calcIndex(myGame.getHuman().getLocation().getRow(), myGame.getHuman().getLocation().getColumn()), 473 );
    }
    @Test
    public void testComputerPlayerOne(){
        Player computer = myGame.getPlayer(12,4);
        assertEquals(computer.getName(), "Elessar Telcontal");//aka Aragorn
        //test the color is red
        assertEquals(computer.getColor(), Color.BLUE);
    }
    @Test
    public void testComputerPlayerTwo(){

        Player computer = myGame.getPlayer(16,15);
        assertEquals(computer.getName(), "Frodo Baggins");
        //test the color is red
        assertEquals(computer.getColor(), Color.YELLOW);
    }
    /**
     * Loading the cards
     */
    @Test
    public void testDeckLoading (){
        ArrayList<Card> myDeck = myGame.getDeck();
        myGame.generateNumTypes();
        Map<Card.CardType, Integer> myNumTypes = myGame.getNumTypes();
        assertEquals(myDeck.size(), 21); //tests the deck contains the correct total number of cards

        //The deck contains the correct number of cards of each type
        assertEquals(myNumTypes.get(Card.CardType.PERSON), (Integer)6);
        assertEquals(myNumTypes.get(Card.CardType.WEAPON),(Integer) 6);
        assertEquals(myNumTypes.get(Card.CardType.ROOM),(Integer) 9);

        //Selects one room, one weapon, and one person, and ensure the deck contains each of those
        assertTrue(myGame.hasInDeck("Gandalf Grey"));
        assertTrue(myGame.hasInDeck("Gondor"));
        assertTrue(myGame.hasInDeck("Aeglos"));
    }

    /**
     * Test dealing the cards
     */

    @Test
    public void testAllCardsDealt(){
        myGame.passOutCards();

        Map<Card.CardType, Integer> myNumTypes = myGame.getNumTypes();

        //Each card type is no longer present
        assertEquals(myNumTypes.get(Card.CardType.PERSON), (Integer)0);
        assertEquals(myNumTypes.get(Card.CardType.WEAPON), (Integer)0);
        assertEquals(myNumTypes.get(Card.CardType.ROOM), (Integer)0);

        //Deck is completely empty
        assertEquals(myGame.getDeck().size(), 0);

    }

    /**Uses helper function in ClueGame
     * to detemine distribution evenness
     * Passes: each player has either
     * 3 or 4 cards
     */
    @Test
    public void testDistributionEvenness(){
        assertTrue(myGame.equalityInDistribution());

    }

    /**Tests whether or not the deck
     * has repetitions
     */
    @Test
    public void testCardUniquenessOne (){
        assertEquals(true, myGame.uniquenessOfCards());
    }

    /**Tests that only one copy of
     * a card exists after cards are
     * passed out to the players
     */
    @Test
    public void cardUniquenessTwo(){
        boolean unique = true;
        for(Player p: myGame.getPlayers())
            for(Card c: p.getMyCards())
                for(Player q: myGame.getPlayers()){
                    if(!(p.equals(q)))
                    for(Card f: q.getMyCards())
                        if(f.equals(c))
                            unique = false;
                }
        assertEquals(unique, true);
    }



}
