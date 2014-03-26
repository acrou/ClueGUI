import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Marissa on 3/13/14.
 */
public class Solution {
	public String weapon;
	public String person;
	public String room;

	public Solution (String person, String room, String weapon){
		this.person = person;
		this.room = room;
		this.weapon = weapon;

	}
    public Solution (ArrayList<Card> solution){
        if(solution.size()==3)
            for(Card c: solution){
                switch (c.getType()){
                    case WEAPON : weapon = c.getName();
                        break;
                    case PERSON : person = c.getName();
                        break;
                    case ROOM : room = c.getName();
                        break;
                    default :
                        break;
                }
            }
    }

	@Override
	public boolean equals (Object other){
		if (other == null) return false;
		if (other == this) return true;
		if (!(other instanceof Solution))return false;

		Solution c = (Solution)other;
		if(c.weapon.equals(weapon) &&c.person.equals(person)&&c.room.equals(room))
			return true;
		return false;


    }
    public boolean contains(Card c){

        if(person.equals(c.getName()))
            return true;
        if(room.equals(c.getName()))
            return true;
        if(weapon.equals(c.getName()))
            return true;
        else
            return false;
    }


}
