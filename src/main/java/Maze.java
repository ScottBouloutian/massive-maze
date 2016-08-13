public class Maze {

    private int size = 0;
    private byte[][] cells;

    public Maze(int size) {
        this.size = size;
        cells = new byte[size][size];
        for(int x=0;x<size;x++) {
            for(int y=0;y<size;y++) {
                cells[x][y] = -1;
            }
        }
    }

    public boolean isCellVisited(Cell cell) {
        return cells[cell.x][cell.y] != -1;
    }

    public int getSize() {
        return size;
    }

    public void setCell(int x, int y, byte encodedDirection) {
        cells[x][y] = (byte)(encodedDirection + 1);
    }

    public void setCell(int x, int y, Direction direction) {
        switch(direction) {
            case NONE:
                cells[x][y] = 0;
                break;
            case LEFT:
                cells[x][y] = 1;
                break;
            case RIGHT:
                cells[x][y] = 2;
                break;
            case UP:
                cells[x][y] = 3;
                break;
            case DOWN:
                cells[x][y] = 4;
                break;
        }
    }

    public void setCell(Cell cell, Direction direction) {
        switch(direction) {
            case NONE:
                cells[cell.x][cell.y] = 0;
                break;
            case LEFT:
                cells[cell.x][cell.y] = 1;
                break;
            case RIGHT:
                cells[cell.x][cell.y] = 2;
                break;
            case UP:
                cells[cell.x][cell.y] = 3;
                break;
            case DOWN:
                cells[cell.x][cell.y] = 4;
                break;
        }
    }

    public Direction getCell(int x, int y) {
        switch(cells[x][y]) {
            case 0:
                return Direction.NONE;
            case 1:
                return Direction.LEFT;
            case 2:
                return Direction.RIGHT;
            case 3:
                return Direction.UP;
            case 4:
                return Direction.DOWN;
            default:
                return null;
        }
    }

    public boolean equals(Object o) {
        if(o instanceof Maze) {
            Maze maze = (Maze)o;
            if(size == maze.getSize()){
                for(int x=0;x<cells.length;x++) {
                    for(int y=0;y<cells.length;y++) {
                        if(getCell(x,y) != maze.getCell(x,y)){
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    public String toString() {
        String result = "";
        for(int x=0;x<size;x++) {
            for(int y=0;y<size;y++) {
                result = result + getCell(x,y) + " ";
            }
            result = result + "\n";
        }
        return result;
    }
}
