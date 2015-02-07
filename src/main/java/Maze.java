public class Maze {

    int size = 0;
    Direction[] cells;

    public Maze(int size) {
        this.size = size;
        cells = new Direction[size * size];
        for(int i=0;i<cells.length;i++) {
            cells[i] = null;
        }
    }

    public int getSize() {
        return size;
    }

    public int getNumCells() {
        return size * size;
    }

    public void setCell(int index, Direction direction) {
        cells[index] = direction;
    }

    public Direction getCell(int index) {
        return cells[index];
    }

}
