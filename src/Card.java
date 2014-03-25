/**
 * Created by Marissa on 3/10/14.
 */
public class Card {
    public enum CardType {
        PERSON, WEAPON, ROOM
    };
    private CardType type;
    private String name; //like specifics of the type

    public Card (){

    }
    public Card(String name, CardType type){
        this.name = name;
        this.type = type;
    }
    //getters
    public CardType getType() {
        return type;
    }
    public String getName(){
        return name;
    }
    //setters
    public void setType(CardType type) {
        this.type = type;
    }
    public void setName (String name){
        this.name = name;
    }
    @Override
    public boolean equals (Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Card))return false;

       Card c = (Card)other;
        if(c.getName().equals(name) &&c.getType().equals(type))
            return true;
        return false;

    }
}
