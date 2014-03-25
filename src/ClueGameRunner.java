/**
 * Created by Marissa on 3/16/14.
 */
import java.io.FileNotFoundException;

public class ClueGameRunner  {
    public static void main(String args[]) throws FileNotFoundException {
    Board b = new Board("gameLayout.csv","legend.txt");
    ClueGame clueGame = new ClueGame(b); //initialize all data that needs to be read in
    System.out.println("size of deck: " + clueGame.getDeck().size());
       // clueGame.equalityInDistribution();

        clueGame.passOutCards();
//        int counter = 0;
        for(Player p: clueGame.getPlayers() ){
            System.out.println("Player: " + p.getName());
            for (Card c : p.getMyCards()){
            	System.out.println(c.getName());
            }
//
//            boolean even = true;
//            System.out.println("Count: " + counter);
//                for(Player p: clueGame.getPlayers() ){
//            if(!(p.getMyCards().size() == 3 || p.getMyCards().size() == 4))//21/6 = 3.5
//                even = false;
//
//        }
//        System.out.println("even: " +even);

//    clueGame.generateNumTypes();
//    clueGame.passOutCards();
//    clueGame.getHuman();

        }
    }

}
