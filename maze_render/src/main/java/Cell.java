public class Cell {
    public int x;
    public int y;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Cell moveInDirection(Direction direction) {
        switch(direction) {
            case LEFT:
                return new Cell(x-1,y);
            case RIGHT:
                return new Cell(x+1,y);
            case UP:
                return new Cell(x,y-1);
            case DOWN:
                return new Cell(x,y+1);
            default:
                return new Cell(x,y);
        }
    }

}
