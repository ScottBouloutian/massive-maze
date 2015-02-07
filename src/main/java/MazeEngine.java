import java.util.LinkedList;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.util.Random;

public class MazeEngine {

    private Maze maze;
    private static final int CELL_SIZE = 25;
    private boolean[] cellVisited;

    public MazeEngine(int size) {
        maze = new Maze(size);
        cellVisited = new boolean[maze.getNumCells()];
    }

    public void generateMaze() {
        int start = 0;
        randomWalk(start);
    }

    private void randomWalk(int start) {
        final int START_CELL = 0;
        maze.setCell(START_CELL, Direction.NONE);
        int currentCell = START_CELL;
        while(currentCell != -1) {
            cellVisited[currentCell] = true;
            Neighbor neighbor = getRandomNeighbor(currentCell);
            currentCell = neighbor.getChild();
            if(currentCell != -1) {
                maze.setCell(neighbor.getChild(), neighbor.getDirection());
            }
        }
    }

    private Neighbor getRandomNeighbor(int cell) {
        LinkedList<Neighbor> neighbors = getNeighbors(cell);
        if(neighbors.size() == 0) {
            return new Neighbor(cell,null,-1);
        };
        Random random = new Random();
        int randomIndex = random.nextInt(neighbors.size());
        return neighbors.get(randomIndex);
    }

    private LinkedList<Neighbor> getNeighbors(int cell) {
        LinkedList<Neighbor> neighbors = new LinkedList<Neighbor>();
        int size = maze.getSize();
        int row = cell / size;
        int col = cell % size;
        if(col > 0 && !cellVisited[cell-1]) {
            neighbors.push(new Neighbor(cell,Direction.LEFT,cell-1));
        }
        if(col < size - 1 && !cellVisited[cell+1]) {
            neighbors.push(new Neighbor(cell,Direction.RIGHT,cell+1));
        }
        if(row > 0 && !cellVisited[cell-size]) {
            neighbors.push(new Neighbor(cell,Direction.UP,cell-size));
        }
        if(row < size - 1 && !cellVisited[cell+size]) {
            neighbors.push(new Neighbor(cell,Direction.DOWN,cell+size));
        }
        return neighbors;
    }

    public void drawMaze() {
        JFrame frame = new JFrame();
        Dimension size = new Dimension(CELL_SIZE * maze.getSize() * 2, CELL_SIZE * maze.getSize() * 2 + 15);
        frame.setSize(size);
        MazePanel panel = new MazePanel(maze, CELL_SIZE);
        panel.setSize(size);
        frame.add(panel);
        frame.setVisible(true);
    }

}
