

public class RoomCell extends BoardCell {

    DoorDirection doorDirection;
    char roomInitial;
    private String fullName;

    public RoomCell(int row, int column, String type) {
        super(row, column);
        roomInitial = type.charAt(0);
        if (type.length() == 2) {
            char door = type.charAt(1);
            switch (door) {
                case 'U':
                    doorDirection = DoorDirection.UP;
                    break;
                case 'D':
                    doorDirection = DoorDirection.DOWN;
                    break;
                case 'L':
                    doorDirection = DoorDirection.LEFT;
                    break;
                case 'R':
                    doorDirection = DoorDirection.RIGHT;
                    break;
                default:
                    doorDirection = DoorDirection.NONE;
            }

        } else doorDirection = DoorDirection.NONE;
    }

    // Enumerator for the direction the door faces, or if there is no door, NONE
    public enum DoorDirection {
        UP, DOWN, LEFT, RIGHT, NONE;
    }


    public boolean isRoom() {
        return true;
    }
    public String getFullName(){
        return fullName;
    }
    public void setFullName (String type){
        fullName = type;
    }

    public boolean isDoorway() {
        if (doorDirection != DoorDirection.NONE) return true;
        else return false;
    }

    public DoorDirection getDoorDirection() {
        return this.doorDirection;
    }

    public char getInitial() {
        return roomInitial;
    }

    // future draw function override here
    @Override
    public boolean equals (Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof RoomCell))return false;
        RoomCell rc = (RoomCell)other;
        if(rc.getFullName().equals(fullName))
            return true;
        return false;


    }

}
