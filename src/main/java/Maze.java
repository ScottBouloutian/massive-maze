public class Maze {

    private int size = 0;
    private Direction[][] cells;

    public Maze(int size) {
        this.size = size;
        cells = new Direction[size][size];
        for(int x=0;x<size;x++) {
            for(int y=0;y<size;y++) {
                cells[x][y] = null;
            }
        }
    }

    public boolean isCellVisited(Cell cell) {
        return cells[cell.x][cell.y] != null;
    }

    public int getSize() {
        return size;
    }

    public void setCell(Cell cell, Direction direction) {
        cells[cell.x][cell.y] = direction;
    }

    public Direction getCell(int x, int y) {
        return cells[x][y];
    }

    public boolean equals(Object o) {
        if(o instanceof Maze) {
            Maze maze = (Maze)o;
            if(size == maze.getSize()){
                for(int x=0;x<cells.length;x++) {
                    for(int y=0;y<cells.length;y++) {
                        if(cells[x][y] != maze.getCell(x,y)){
                            return false;
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

}
