
public abstract class BoardCell {
    // Values are for the to-be-created draw function
    private int row;
    private int column;

    public BoardCell (int row, int column) {
        this.row = row;
        this.column = column;
    }

    // All is_____ functions default to false, the functions are overwritten in the child classes
    public boolean isWalkway() {
        return false;
    }

    public boolean isRoom() {
        return false;
    }

    public boolean isDoorway() {
        return false;
    }

    public int getRow(){
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals (Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof RoomCell))return false;
        BoardCell bc = (BoardCell)other;
        if(bc.getRow()==(row)&&bc.getColumn()==(column))
            return true;
        return false;
    }


    // future draw function here
}
