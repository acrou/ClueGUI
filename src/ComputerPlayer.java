import org.junit.runner.Computer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Marissa on 3/10/14.
 */
public class ComputerPlayer extends Player {
	public static int counter = 0;
	//    public void setUpLocationChooser (){
	//        Board.pickLocation(location);
	//    }
	public ComputerPlayer (){
		super();
		if(counter == 0){
			setColor(Color.BLUE);
			setName("Elessar Telcontal");
		}
		if(counter ==1){
			setColor(Color.PINK);
			setName ("Samwise Gamgee");
		}
		if(counter==2){
			setColor(Color.BLACK);
			setName("Gandalf Grey");
		}
		if(counter==3){
			setColor(Color.GREEN);
			setName ("Gollum Trahald");
		}
		if(counter==4){
			setColor(Color.YELLOW);

			setName("Frodo Baggins");
		}
		counter ++;



	}
	public BoardCell pickLocation (ArrayList<BoardCell> arrayList, Board b){
		Random randGen = new Random();
		int rand = randGen.nextInt()%10;
		return b.pickLocation(location, rand, prevRoom);


	}

}
