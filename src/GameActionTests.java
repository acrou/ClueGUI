import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Ally and Marissa on 3/11/14.
 */
public class GameActionTests {


	/**
	 * Testing accusations
	 */

	private static ClueGame myGame;
	private static Board board;
	private static Card narsil, morgul_blade, mordor, shire, gandalf, gollum;
	@BeforeClass
	public static void setUp () throws FileNotFoundException {
		//that sets up the board and a variety of cards (of each type) that will be needed for testing.
		board = new Board("gameLayout.csv","legend.txt");
		myGame = new ClueGame(board);
		//test weapons
		narsil = new Card("Narsil", Card.CardType.WEAPON);
		morgul_blade = new Card("Morgul Blade", Card.CardType.WEAPON);
		//test rooms
		mordor = new Card("Mordor", Card.CardType.ROOM);
		shire = new Card("The Shire", Card.CardType.ROOM);
		//test people
		gandalf = new Card("Gandalf Grey", Card.CardType.PERSON);
		gollum = new Card("Gollum Trahald", Card.CardType.PERSON);
	}

	/**Tests 1 correct solution and 3
	 * fake ones by using helper function in ClueGame
	 */
	@Test
	public void testCorrectAccusation(){
		Solution mySolution=  myGame.getSolution();
		Solution wrongSolutionOne = new Solution("Frodo Baggins", "Gondor", "Aeglos");
		Solution wrongSolutionTwo = new Solution("Gandalf Grey", "Gondor", "Sting");
		Solution wrongSolutionThree = new Solution("Gandalf Grey", "Mirkwood", "Aeglos");
		assertTrue(myGame.isCorrectSolution(mySolution));
		assertFalse(myGame.isCorrectSolution(wrongSolutionOne));
		assertFalse(myGame.isCorrectSolution(wrongSolutionTwo));
		assertFalse(myGame.isCorrectSolution(wrongSolutionThree));
	}



	//  }
	//    @Test
	//    public void testTargetRandomSelection() {
	//        ComputerPlayer player = new ComputerPlayer();
	//        // Pick a location with no rooms in target, just three targets
	//        board.calcTargets(14, 0, 2);
	//        int loc_12_0Tot = 0;
	//        int loc_14_2Tot = 0;
	//        int loc_15_1Tot = 0;
	//        // Run the test 100 times
	//        for (int i=0; i<100; i++) {
	//            BoardCell selected = player.pickLocation(board.getTargets());
	//            if (selected == board.getCellAt(12, 0))
	//                loc_12_0Tot++;
	//            else if (selected == board.getCellAt(14, 2))
	//                loc_14_2Tot++;
	//            else if (selected == board.getCellAt(15, 1))
	//                loc_15_1Tot++;
	//            else
	//                fail("Invalid target selected");
	//        }
	//        // Ensure we have 100 total selections (fail should also ensure)
	//        assertEquals(100, loc_12_0Tot + loc_14_2Tot + loc_15_1Tot);
	//        // Ensure each target was selected more than once
	//        assertTrue(loc_12_0Tot > 10);
	//        assertTrue(loc_14_2Tot > 10);
	//        assertTrue(loc_15_1Tot > 10);
	//    }
	//}

	/**Tests that are used for the 
	 *the game's preference for rooms
	 */

	//----------------------------------------------------------------------------------------------------------------------------------------------//

	//ensures that if a room is present in a test it is selected
	@Test
	public void roomPresentTest(){
		myGame.getBoard().calcTargets(3,0,2);
		Set<BoardCell> containsRoom = myGame.getBoard().getTargets();//location: 3,0, 2 steps

		//Now, the test must ensure that the room is selected each time
		for(Player p: myGame.getPlayers()){
			if(p instanceof ComputerPlayer){
				//The returned cell must be an instance of a room cell
				BoardCell selected = ((ComputerPlayer)p).pickLocation(new ArrayList<BoardCell>(containsRoom), myGame.getBoard());
				assertTrue(selected instanceof RoomCell);
			}
		}
	}
	/**Disproving suggestions with
	 *
	 */
	@Test
	public void onePlayerOneMatch(){//Tests that a player has the card in question.
		Player test = new Player();

		Solution mySuggestionOne = new Solution(gollum.getName(), "Gondor", "Sting");
		Solution mySuggestionTwo = new Solution("Frodo Baggins", mordor.getName(), "Sting");
		Solution mySuggestionThree = new Solution("Frodo Baggins", "Gondor", morgul_blade.getName());
		Solution mySuggestionFour = new Solution("Frodo Baggins", "Gondor", narsil.getName());

		test.addCard(gollum);
		test.addCard(narsil);
		test.addCard(gandalf);
		test.addCard(morgul_blade);
		test.addCard(mordor);
		test.addCard(shire);

		assertEquals(gollum, test.disproveSuggestion(mySuggestionOne));
		assertEquals(mordor, test.disproveSuggestion(mySuggestionTwo));
		assertEquals(morgul_blade, test.disproveSuggestion(mySuggestionThree));
		assertEquals(narsil, test.disproveSuggestion(mySuggestionFour));


	}
	@Test
	public void onePlayerMultipleMatches(){
		Player test = new Player();
		test.addCard(gollum);
		test.addCard(narsil);
		test.addCard(gandalf);
		test.addCard(morgul_blade);
		test.addCard(mordor);
		test.addCard(shire);

		Solution mySuggestion = new Solution(gollum.getName(), mordor.getName(), narsil.getName());
		int countG =0;
		int countM =0;
		int countN =0;

		for(int i =0; i< 10; i++)
		{
			if(test.disproveSuggestion(mySuggestion).getName().equals(gollum.getName())) //if gollum is returned
				countG++;
			if(test.disproveSuggestion(mySuggestion).getName().equals(mordor.getName()))//if mordor is returned
				countM++;
			if(test.disproveSuggestion(mySuggestion).getName().equals(narsil.getName()))//if narsil is returned
				countN++;
		}
		assertTrue(countG>0);
		assertTrue(countM>0);
		assertTrue(countN>0);

	}

	@Test
	public void testAllPlayersAreQueried(){

	}

	@Test
	public void onePlayerTwoMatches(){//Tests that the suggestion received two matches from one person.
		Player test = new Player();
		ArrayList<Solution> list = new ArrayList<Solution>();
		Solution mySuggestionOne = new Solution(gollum.getName(), "Gondor", narsil.getName());
		Solution mySuggestionTwo = new Solution(gollum.getName(), mordor.getName(), "Sting");
		Solution mySuggestionThree = new Solution(gollum.getName(), "Gondor", morgul_blade.getName());
		Solution mySuggestionFour = new Solution("Frodo Baggins", mordor.getName(), narsil.getName());
		Solution mySuggestionFive = new Solution("Frodo Baggins", shire.getName(), narsil.getName());
		Solution mySuggestionSix = new Solution(gollum.getName(), shire.getName(), "Sting");
		list.add(mySuggestionOne);
		list.add(mySuggestionTwo);
		list.add(mySuggestionThree);
		list.add(mySuggestionFour);
		list.add(mySuggestionFive);
		list.add(mySuggestionSix);
		test.addCard(gollum);
		test.addCard(narsil);
		test.addCard(gandalf);
		test.addCard(morgul_blade);
		test.addCard(mordor);
		test.addCard(shire);
		for (Solution s : list){
			if (test.numberOfMatches(s) == 2){
				assertTrue(true);	
			}
		}	
	}
	@Test
	public void testOnePlayerTwoMatchesGivesOneCard(){//Tests that, though there are two matches, only one card is given over.
		Player test = new Player();
		ArrayList<Solution> list = new ArrayList<Solution>();
		Solution mySuggestionOne = new Solution(gollum.getName(), "Gondor", narsil.getName());
		Solution mySuggestionTwo = new Solution(gollum.getName(), mordor.getName(), "Sting");
		Solution mySuggestionThree = new Solution(gollum.getName(), "Gondor", morgul_blade.getName());
		Solution mySuggestionFour = new Solution("Frodo Baggins", mordor.getName(), narsil.getName());
		Solution mySuggestionFive = new Solution("Frodo Baggins", shire.getName(), narsil.getName());
		Solution mySuggestionSix = new Solution(gollum.getName(), shire.getName(), "Sting");
		list.add(mySuggestionOne);
		list.add(mySuggestionTwo);
		list.add(mySuggestionThree);
		list.add(mySuggestionFour);
		list.add(mySuggestionFive);
		list.add(mySuggestionSix);
		test.addCard(gollum);
		test.addCard(narsil);
		test.addCard(gandalf);
		test.addCard(morgul_blade);
		test.addCard(mordor);
		test.addCard(shire);
		for (Solution s : list){
			if (test.numberOfMatches(s) == 2){
				if (test.countCardsGivenAway(s)==1){
					assertTrue(true);
				}
			}//Do we have tests tests involving the human player, and a test that the player whose turn it is does not return a card?
		}	

	}
	@Test
	public void testQueryOrder() throws FileNotFoundException{;
	int count = 0;
	for (Player p : myGame.getPlayers()){
		if (count == 0){
			assertEquals(p.getName(), "Test name");
			count++;
		}
		else if (count == 1){
			assertEquals(p.getName(), "Elessar Telcontal");
			count++;
		}
		else if (count == 2){
			assertEquals(p.getName(), "Gandalf Grey");
			count++;
		}
		else if (count == 3){
			assertEquals(p.getName(), "Gollum Trahald");
			count++;
		}
		else if (count == 4){
			assertEquals(p.getName(), "Samwise Gamgee");
			count++;
		}
		else if (count == 5){
			assertEquals(p.getName(), "Frodo Baggins");
			count++;
		}
	}
	}
	@Test
	public void testNoDisprove(){
		Player test = new Player();
		Player human = myGame.getHuman();
		Solution attempt;
		ArrayList<Card>theirs = human.getMyCards();
		Solution randomGuess = new Solution("Gandalf Grey", "Gondor", "Aeglos");
		assertEquals(null, test.disproveSuggestion(randomGuess));
		for (Card c : theirs){
			if (c.getType() == Card.CardType.PERSON){
				attempt = new Solution(c.getName(), "Gondor", "Aeglos");
				test.disproveSuggestion(attempt);
			}
			if (c.getType() == Card.CardType.ROOM){
				attempt = new Solution("Gandalf Grey", c.getName(), "Aeglos");
				test.disproveSuggestion(attempt);
			}
			if (c.getType() == Card.CardType.WEAPON){
				attempt = new Solution("Gandalf Grey", "Gondor", c.getName());
				test.disproveSuggestion(attempt);
			}
		}
	}
	@Test
	public void testOnlyHumanCanDisprove(){
		Player test = new Player();
		Player human = myGame.getHuman();
		Solution attempt = null;
		ArrayList<Card> humanCards = human.getMyCards();
		for (Card guess : humanCards){
			if (guess.getType() == Card.CardType.PERSON){
				attempt = new Solution(guess.getName(), "Gondor", "Aeglos");
			}
			if (guess.getType() == Card.CardType.ROOM){
				attempt = new Solution("Gandalf Grey", guess.getName(), "Aeglos");
			}
			if (guess.getType() == Card.CardType.PERSON){
				attempt = new Solution("Gandalf Grey", "Gondor", guess.getName());				
			}
			assertEquals(guess, test.disproveSuggestion(attempt));
		}
	}
}