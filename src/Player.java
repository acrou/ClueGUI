import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**Abstracts qualities of both types of Players
 * Created by Marissa on 3/10/14.
 */

public class Player {
    protected static ArrayList<Card> seenCards;
    protected String name;
    protected ArrayList<Card> myCards;
    protected BoardCell location;
    protected RoomCell prevRoom;
    protected Card proof;
    protected Color color;
    public Player (){
       seenCards = new ArrayList<Card>();
       myCards = new ArrayList<Card>();
    }
    //getters
    public BoardCell getLocation (){
        return location;
    }
    public String getName (){
        return name;

    }
    public ArrayList<Card> getMyCards (){
        return myCards;
    }
    public RoomCell getPrevRoom (){
        return prevRoom;
    }
    public Card getProof (){
        return proof;
    }
    public  Color getColor(){
        return color;

    }
    //setters
    public void setColor (Color  c){
        this.color = c;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setLocation(int loc, Board b){
        location = b.getCellAt(loc);
    }
    public void addCard(Card c){
        myCards.add(c);
        addToSeenCards(c);
    }

    public Solution makeSuggestion (ArrayList<Card> deck){
        if(location instanceof RoomCell){
            if(onlyHasSolution())
                        return checkRoom( new Solution(unseenCards(deck)));
            //else randomly generate a suggestion that has the room set to Player's current
            return new Solution (generateRandomSuggestion());
        }
        else
            return null;



    }    
    public Solution checkRoom(Solution s){
        if(s.room.equals(((RoomCell) location).getFullName()))
            return s;
        return null;
    }
    public ArrayList<Card> generateRandomSuggestion(){
        ArrayList<Card> suggestion = new ArrayList<Card>();
        suggestion.add(new Card(((RoomCell)location).getFullName(), Card.CardType.ROOM));
        //Random rand = new Random ();

        Card personSuggestion = new Card(), weaponSuggestion = new Card();
       for(Card c : seenCards)
       {
           if(c.getType().equals(Card.CardType.PERSON))
               personSuggestion = c;
           if(c.getType().equals(Card.CardType.WEAPON))
               weaponSuggestion =c;

       }
       suggestion.add(personSuggestion);
        suggestion.add(weaponSuggestion);
        return  suggestion;


    }
    public boolean onlyHasSolution (){
        int countWeapon =0;
        int countPerson=0;
        int countRoom =0;
        for(Card c: seenCards){
            switch (c.getType()){
            case WEAPON : countWeapon++;
                break;
            case PERSON : countPerson++;
                break;
            case ROOM : countRoom++;
                break;
            default :
                break;
            }
        }
        if(countPerson==5&&countRoom==8&&countWeapon==5) //only one combination remains then
            return true;
        return false;
    }
    public void addToSeenCards(Card c){
        seenCards.add(c);
    }

    public ArrayList<Card> unseenCards(ArrayList<Card> deck){
        ArrayList<Card> unseenCards = new ArrayList<Card>();
        for(Card c: deck){
            if(!(seenCards.contains(c)))
                unseenCards.add(c);

        }
        return unseenCards;
    }

    public int countCardsGivenAway(Solution s){
    	Player p = new Player();
        if (p.disproveSuggestion(s) == null){
        	return 0;
        }
        else{
        	return 1;
        }
    }
    public Card disproveSuggestion(Solution suggestion){
        ArrayList<Card> found = new ArrayList<Card>();
          for(int i = 0; i<myCards.size(); i++){
            if(suggestion.contains(myCards.get(i))){
               found.add(myCards.get(i));
           }
        }

        if(found.size()==0)
            return null;
        Random rand = new Random();
        int randPosition = rand.nextInt()%found.size();
        if(randPosition<0){
           randPosition *= -1;
        }
        return found.get(randPosition);


    }
    public int numberOfMatches(Solution suggestion){
    	ArrayList<Card> found = new ArrayList<Card>();
        for(int i = 0; i<myCards.size(); i++){
          if(suggestion.contains(myCards.get(i))){
             found.add(myCards.get(i));
         }
      }
        return found.size();
    }
}
