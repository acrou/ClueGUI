import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.*;

import javax.swing.JPanel;


public class Board extends JPanel {

	private int numRows;
	private int numColumns;
	private String walkwayChar;
	private Map<Character, String> rooms;
	private ArrayList<BoardCell> cells;
	private String layoutFile, legendFile;
	private Map<Integer, LinkedList<Integer>> adjMtx;
	private boolean visited[];
	//todo prev location
	private BoardCell prevLocation;

	private Set<BoardCell> targets;

	public Board(String layoutFile, String legendFile) {
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		adjMtx = new HashMap<Integer, LinkedList<Integer>>();
		targets = new HashSet<BoardCell>();
		this.layoutFile = layoutFile;
		this.legendFile = legendFile;
		loadConfigFiles();
		calcAdjacencies();
		visited = new boolean[numRows*numColumns];
	}

	public Board() {
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		adjMtx = new HashMap<Integer, LinkedList<Integer>>();
		targets = new HashSet<BoardCell>();
		this.layoutFile = "gameLayout.csv";
		this.legendFile = "legend.txt";
		loadConfigFiles();
		calcAdjacencies();
		visited = new boolean[numRows*numColumns];
	}

	public void loadConfigFiles() {
		loadRoomConfig();
		loadBoardConfig();
	}

	public int calcIndex(int rowNum, int columnNum) {
		return columnNum+rowNum*numColumns;
	}

	public void calcAdjacencies() {
		for (int i=0; i<numRows; i++) {
			for (int j=0; j<numColumns; j++) {
				int index = calcIndex(i,j);
				adjMtx.put(index, new LinkedList<Integer>());
				if (getCellAt(index).isDoorway()) {
					switch (((RoomCell)getCellAt(index)).getDoorDirection()) {
					case UP:
						if (i>0 && (getCellAt(calcIndex(i-1,j)).isWalkway() || getCellAt(calcIndex(i-1,j)).isDoorway()))
							adjMtx.get(index).add(calcIndex(i-1,j));
						break;
					case LEFT:
						if (j>0 && (getCellAt(calcIndex(i,j-1)).isWalkway() || getCellAt(calcIndex(i,j-1)).isDoorway()))
							adjMtx.get(index).add(calcIndex(i,j-1));
						break;
					case DOWN:
						if (i<numRows-1 && (getCellAt(calcIndex(i+1,j)).isWalkway() || getCellAt(calcIndex(i+1,j)).isDoorway()))
							adjMtx.get(index).add(calcIndex(i+1,j));
						break;
					case RIGHT:
						if (j<numColumns-1 && (getCellAt(calcIndex(i,j+1)).isWalkway() || getCellAt(calcIndex(i,j+1)).isDoorway()))
							adjMtx.get(index).add(calcIndex(i,j+1));
						break;
					default:
						break;
					}
				} else if (getCellAt(index).isWalkway()){
					if (i>0 && (getCellAt(calcIndex(i-1,j)).isWalkway() ||
							(getCellAt(calcIndex(i-1,j)).isDoorway() && ((RoomCell)getCellAt(calcIndex(i-1,j))).getDoorDirection() == RoomCell.DoorDirection.DOWN)))
						adjMtx.get(index).add(calcIndex(i-1,j));
					if (j>0 && (getCellAt(calcIndex(i,j-1)).isWalkway() ||
							(getCellAt(calcIndex(i,j-1)).isDoorway()) && ((RoomCell)getCellAt(calcIndex(i,j-1))).getDoorDirection() == RoomCell.DoorDirection.RIGHT))
						adjMtx.get(index).add(calcIndex(i,j-1));
					if (i<numRows-1 && (getCellAt(calcIndex(i+1,j)).isWalkway() ||
							(getCellAt(calcIndex(i+1,j)).isDoorway()) && ((RoomCell)getCellAt(calcIndex(i+1,j))).getDoorDirection() == RoomCell.DoorDirection.UP))
						adjMtx.get(index).add(calcIndex(i+1,j));
					if (j<numColumns-1 && (getCellAt(calcIndex(i,j+1)).isWalkway() ||
							(getCellAt(calcIndex(i,j+1)).isDoorway()) && ((RoomCell)getCellAt(calcIndex(i,j+1))).getDoorDirection() == RoomCell.DoorDirection.LEFT))
						adjMtx.get(index).add(calcIndex(i,j+1));
				}
			}
		}
	}

	public void calcTargets(int row, int col,  int steps) {
		//  prevLocation = cells.get(calcIndex(row, col));
		targets = new HashSet<BoardCell>();
		createTargets(row, col, steps);
	}

	public void createTargets(int row, int col, int steps) {
		int location = calcIndex(row,col);
		visited[location] = true;
		LinkedList<Integer> adjList = adjMtx.get(location);
		int adjLoc;
		for (int i=0; i<adjList.size(); i++) {
			adjLoc = adjList.get(i);
			if (visited[adjLoc] == false) {
				visited[adjLoc] = true;
				if (steps == 1 && !targets.contains(adjLoc) || getCellAt(adjLoc).isDoorway()) {
					targets.add(getCellAt(adjLoc));
				} else
					createTargets(getCellAt(adjLoc).getRow(),
							getCellAt(adjLoc).getColumn(), steps - 1);
				visited[adjLoc] = false;
			}
		}
		visited[location] = false;
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public LinkedList<Integer> getAdjList(int index) {
		return adjMtx.get(index);
	}
	/**Chooses a specific next location, randomly if no room cells are targets
	 * @param location      The player's current location
	 * @param randSteps     Randomly generated steps to calculate targets
	 * @param rc            Most recent room the player entered
	 * @return              next location for the player
	 */
	public BoardCell pickLocation(BoardCell location, int randSteps, RoomCell rc){

		calcTargets(location.getRow(), location.getColumn(), randSteps);
		for(BoardCell bc: targets)
			if(bc instanceof RoomCell && !(bc.equals(rc)))
				return bc;
		Random rand = new Random ();
		int randNum = rand.nextInt()%cells.size();
		//test if random number is negative
		if(randNum<0)
			randNum *= -1;
		return cells.get(randNum);
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}
	public ArrayList<String> getRoomNames(){
		ArrayList<String> roomNames = new ArrayList<String>();
		Set<Character> keySet = rooms.keySet();
		for(Character c :keySet)
			roomNames.add(rooms.get(c));
		return roomNames;
	}

	public RoomCell getRoomCellAt(int i, int j) {
		return (RoomCell) cells.get(calcIndex(i, j));
	}

	public BoardCell getCellAt(int i) {

		return cells.get(i);
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public void loadRoomConfig() {
		try {
			FileReader reader = new FileReader(legendFile);
			Scanner scan  = new Scanner(reader);
			String str;
			String[] split;
			while (scan.hasNextLine()) {
				str = scan.nextLine();

				split = str.split(", ");
				if (split.length > 2 || split[0].length()>1)
					throw new BadConfigFormatException("Legend format is incorrect.");
				rooms.put(split[0].charAt(0), split[1]);
				if (split[1].equals("Walkway")) walkwayChar = split[0];
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}

	public void loadBoardConfig() {
		try {
			FileReader reader = new FileReader(layoutFile);
			Scanner scan = new Scanner(reader);
			String str;
			String[] letters;
			numColumns = 0;
			numRows = 0;
			for(int i=0; scan.hasNextLine(); i++, numRows++) {
				str = scan.nextLine();
				letters = str.split(",");
				if (numColumns == 0) numColumns = letters.length;
				if (numColumns != letters.length)
					throw new BadConfigFormatException("Column format is incorrect.");
				if (!rooms.containsKey(letters[0].charAt(0)))
					throw new BadConfigFormatException("Room key does not exist.");
				for (int j=0; j<letters.length; j++) {
					str = letters[j];
					if (!str.equals(walkwayChar)) {
						RoomCell temp =new RoomCell (i,j ,str);
						temp.setFullName(rooms.get(str));
						cells.add(temp);
					}
					else cells.add(new WalkwayCell(i,j));
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}
	public void paintComponent(Graphics g){
		BoardCell b = getCellAt(0);
		super.paintComponent(g);
		int x=0, y=0;
		g.setColor(Color.BLUE);
		for (int i = 0; i < numRows; i++){
			for (int j = 0; j < numColumns; j++){
				if (b.isWalkway() == true){
					g.setColor(Color.BLACK);
					g.drawRect(x, y, 20, 20);
					g.setColor(Color.YELLOW);
					g.fillRect(x, y, 20, 20);
					System.out.println("Walkway");
				}
				if (b.isRoom() == true){
					g.setColor(Color.GRAY);
					g.drawRect(x, y, 20, 20);
					g.fillRect(x, y, 20, 20);
					System.out.println("Room");
				}
				x += 20;
				b = getCellAt(i*23+j);
				System.out.println(i*23+j);
			}
			y+=20;
			x = 0;
		}
		
	}



}
