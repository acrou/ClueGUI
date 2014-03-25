import sun.tools.jar.resources.jar_ko;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import javax.swing.JFrame;

/**
 * Brings together the various components of Clue (like Board, Players, etc)
 * and renders a fully functioning game
 *
 * @author Marissa Renfro
 * @author Allison Crouch
 * @version March, 2014.
 */
public class ClueGame extends JFrame {
    private ArrayList<Card> deck;
    private Map<Card.CardType, Integer> numTypes;
    private Player human;
    private Player current; //the player's whose turn it is
    private Board board;
    private Solution solution;
    //names for configuration
    private String peopleFileName;
    private String weaponsFileName;
    //Lists for read in data
    private ArrayList<Player> players;
    private ArrayList<Integer> startingLocation;
    private ArrayList<String> weapons;

    /**
     * Single parameter constructor
     *
     * @param b Board being played on
     */
    public ClueGame(Board b) throws FileNotFoundException {
        //Initializes class variables
        board = b;
        peopleFileName = "playerNames.txt";
        weaponsFileName = "weapons.txt";
        deck = new ArrayList<Card>();
        players = new ArrayList<Player>();
        startingLocation = new ArrayList<Integer>();
        weapons = new ArrayList<String>();
        numTypes = new TreeMap<Card.CardType, Integer>();
        numTypes.put(Card.CardType.ROOM, 0);
        numTypes.put(Card.CardType.WEAPON, 0);
        numTypes.put(Card.CardType.PERSON, 0);
        //Call to configure the game
        setUp();
    }

    public void setUp() throws FileNotFoundException {
        readInStartingLocations();
        loadPeople();
        loadWeapons();
        generateDeck();
    }


    //Getter methods
    public ArrayList<Integer> getStartingLocation() {
        return startingLocation;
    }

    public Player getPlayer(int row, int column) {

        for (Player p : players) {
            if (p.getLocation().getRow() == row && p.getLocation().getColumn() == column) //Player must exist at location
                return p;
        }
        return null;
    }
    public Player getHuman() {
        return human;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public Map<Card.CardType, Integer> getNumTypes() {

          return numTypes;
    }

    /**
     * Hard-coded solution to the game
     *
     * @return solution
     */
    public Solution getSolution() {
        solution = new Solution("Gandalf Grey", "Gondor", "Aeglos");
        return solution;
    }


    /**
     * Uses a text file to input in the
     * initial locations for all (both human and
     * computer) players.
     *
     * @throws FileNotFoundException
     */
    public void readInStartingLocations() throws FileNotFoundException {
        File file = new File("startLocations.txt");
        Scanner scan = new Scanner(file);
        int count = 0;
        while (count < 6) {
            startingLocation.add(Integer.parseInt(scan.nextLine()));
            count++;
        }
    }

    /**
     * Console input and initialLocation file are used to
     * construct the only HumanPlayer
     */
    public void readInHuman() {
        Scanner scan = new Scanner(System.in);
        String name = "Test name";
        BoardCell start = board.getCellAt(0);
        HumanPlayer middleEarthAdventurist = new HumanPlayer(name, start, Color.RED);
        middleEarthAdventurist.setLocation(startingLocation.get(0), board);
        human = middleEarthAdventurist;
        players.add(middleEarthAdventurist);
    }

    /**
     * Uses an auxiliary text file to input in the names
     * of the ComputerPlayer(s) and calls helper function to
     * input HumanPlayer info
     *
     * @throws FileNotFoundException
     */
    public void loadPeople() throws FileNotFoundException {
        readInHuman();
        FileReader people = new FileReader(peopleFileName);
        Scanner scan = new Scanner(people);

        while (scan.hasNext()) {
            ComputerPlayer computer = new ComputerPlayer();
            computer.setLocation(startingLocation.get(ComputerPlayer.counter), board);
            computer.setName(scan.nextLine());
            players.add(computer);
        }
        scan.close();
    }

    public void loadWeapons() throws FileNotFoundException {
        FileReader weaponsFile = new FileReader(weaponsFileName);
        Scanner scan = new Scanner(weaponsFile);
        while (scan.hasNext())
            weapons.add(scan.nextLine());
    }

    ////****************GENERATOR METHODS***************////

    public void generateNumTypes() {
        // Map<Card.CardType, Integer> tempNumType = new TreeMap<Card.CardType, Integer>();
        if (deck.size() == 0)
            zeroCardTypes();
        for (Card c : deck) {
            switch (c.getType()) {
                case ROOM:
                    numTypes.put(Card.CardType.ROOM, (Integer) (numTypes.get(Card.CardType.ROOM) + 1));
                    break;
                case PERSON:
                    numTypes.put(Card.CardType.PERSON, (Integer) (numTypes.get(Card.CardType.PERSON) + 1));
                    break;
                case WEAPON:
                    numTypes.put(Card.CardType.WEAPON, (Integer) (numTypes.get(Card.CardType.WEAPON) + 1));
                    break;
                default:
                    break;

            }
        }
    }

    /**
     * Based on the criteria for Cards,
     * the appropriate amount of each type of
     * Card is generated
     */
    public void generateDeck() {
        //loads people
        for (int i = 0; i < 6; i++) {
            Card tempPerson = new Card();
            tempPerson.setType(Card.CardType.PERSON);
            tempPerson.setName(players.get(i).getName());
            deck.add(tempPerson);
        }
        //loads weapons
        for (int i = 0; i < 6; i++) {
            Card tempWeapon = new Card();
            tempWeapon.setType(Card.CardType.WEAPON);
            tempWeapon.setName(weapons.get(i));
            deck.add(tempWeapon);
        }
        //loads rooms
        for (int i = 0; i < 9; i++) {
            Card tempRoom = new Card();
            tempRoom.setType(Card.CardType.ROOM);
            tempRoom.setName(board.getRoomNames().get(i));
            deck.add(tempRoom);
        }

    }

    /**
     * Quasi-evenly distributes Cards to each Player
     * Relies on random generation of index but distributes
     * in a cycle.
     */

    public void passOutCards() {
        Random rand = new Random();
        int playerCurrent = 0;
        //hands out cards in a "round of cards" manner
        while (deck.size() != 0) {
            //starts back at the first player again if reached end
            if (playerCurrent == 6)
                playerCurrent = 0;
            int randomPos = rand.nextInt() % deck.size();
            if (randomPos < 0) //ensures the random number is a positive index
                randomPos *= -1;
            players.get(playerCurrent).addCard(deck.get(randomPos));
            //remove from deck
            deck.remove(randomPos);
            playerCurrent++;

        }
        zeroCardTypes();
    }

    ////****************HELPER FUNCTIONS***************////
    public boolean uniquenessOfCards(){
        generateDeck();
        HashSet<Card> unique= new HashSet<Card>(deck);
        return (unique.size()==deck.size());
    }
    public boolean equalityInDistribution(){
        boolean even = true;
        for(Player p: getPlayers() ){
            int size = p.getMyCards().size() / 3;
            if(!(p.getMyCards().size() == 3 || p.getMyCards().size() == 4)){
                even  = false;
            }
        }
        return even;
    }

    /**
     * Check if a given name is present in the deck of cards
     *
     * @param name Specific name (like "Frodo Baggins" as a PLAYER)
     * @return Presence of the card in the deck
     */
    public boolean hasInDeck(String name) {
        for (Card c : deck) {
            if (c.getName().equals(name))
                return true;
        }
        return false;
    }

    /**
     * Sets the presence of every card
     * type to zero.
     * Used along with draining the deck of it's contents
     */
    public void zeroCardTypes() {
        numTypes.put(Card.CardType.ROOM, (Integer) 0);
        numTypes.put(Card.CardType.PERSON, (Integer) 0);
        numTypes.put(Card.CardType.WEAPON, (Integer) 0);

    }

    /**
     * Tests an accusation validity
     *
     * @param accusation
     * @return correctness of accusation
     */
    public boolean isCorrectSolution(Solution accusation) {
        return solution.equals(accusation);
    }
    public static void main(String[] args){
    	JFrame frame = new JFrame();
    	frame.setSize(500, 500);
    	frame.setTitle("Game");
    	frame.add(new Board());
    	frame.setVisible(true);
    }

}
