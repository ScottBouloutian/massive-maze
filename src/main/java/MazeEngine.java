import java.util.LinkedList;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.util.Random;

public class MazeEngine {

    private Maze maze;
    private static final int CELL_SIZE = 10;
    private static final int MAX_DRAWN_MAZE_SIZE = 30;
    private int beginHuntIndex = 0;
    private float percent = 0;

    public MazeEngine(int size) {
        maze = new Maze(size);
    }

    public void generateMaze() {
        Neighbor hunt = new Neighbor(new Cell(0,0),Direction.NONE,new Cell(0,0));
        while(hunt != null ){
            randomWalk(hunt.getParent(), reverseDirection(hunt.getDirection()));
            hunt = hunt();
        }
    }

    private Direction reverseDirection(Direction direction) {
        switch(direction) {
            case LEFT:
                return Direction.RIGHT;
            case RIGHT:
                return Direction.LEFT;
            case UP:
                return Direction.DOWN;
            case DOWN:
                return Direction.UP;
        }
        return Direction.NONE;
    }

    private void randomWalk(Cell start, Direction initialDirection) {
        maze.setCell(start, initialDirection);
        Cell currentCell = start;
        while(currentCell != null) {
            Neighbor neighbor = getRandomNeighbor(currentCell);
            currentCell = neighbor.getChild();
            if(currentCell != null) {
                maze.setCell(neighbor.getChild(), neighbor.getDirection());
            }
        }
    }

    private Neighbor hunt() {
        int size = maze.getSize();
        float currentPercent = (float)beginHuntIndex / (size * size) * 100;
        if(currentPercent - percent > 1) {
            percent = currentPercent;
            System.out.println(percent + "% complete...");
        }
        for(int y=beginHuntIndex/size;y<size;y++) {
            for(int x=beginHuntIndex%size;x<size;x++) {
                if(!maze.isCellVisited(new Cell(x,y))) {
                    LinkedList<Neighbor> neighbors = getVisitedNeighbors(new Cell(x,y));
                    for(Neighbor neighbor:neighbors) {
                        if(maze.isCellVisited(neighbor.getChild())) {
                            beginHuntIndex++;
                            return neighbor;
                        }
                    }
                }else{
                    beginHuntIndex++;
                }
            }
        }
        return null;
    }

    private Neighbor getRandomNeighbor(Cell cell) {
        LinkedList<Neighbor> neighbors = getNeighbors(cell);
        if(neighbors.size() == 0) {
            return new Neighbor(cell,null,null);
        };
        Random random = new Random();
        int randomIndex = random.nextInt(neighbors.size());
        return neighbors.get(randomIndex);
    }

    private LinkedList<Neighbor> getNeighbors(Cell cell) {
        LinkedList<Neighbor> neighbors = new LinkedList<Neighbor>();
        int size = maze.getSize();
        Cell leftCell = cell.moveInDirection(Direction.LEFT);
        if(leftCell.x >= 0 && !maze.isCellVisited(leftCell)) {
            neighbors.push(new Neighbor(cell,Direction.LEFT,leftCell));
        }
        Cell rightCell = cell.moveInDirection(Direction.RIGHT);
        if(rightCell.x < size && !maze.isCellVisited(rightCell)) {
            neighbors.push(new Neighbor(cell,Direction.RIGHT,rightCell));
        }
        Cell upCell = cell.moveInDirection(Direction.UP);
        if(upCell.y >= 0 && !maze.isCellVisited(upCell)) {
            neighbors.push(new Neighbor(cell,Direction.UP,upCell));
        }
        Cell downCell = cell.moveInDirection(Direction.DOWN);
        if(downCell.y < size && !maze.isCellVisited(downCell)) {
            neighbors.push(new Neighbor(cell,Direction.DOWN,downCell));
        }
        return neighbors;
    }

    private LinkedList<Neighbor> getVisitedNeighbors(Cell cell) {
        LinkedList<Neighbor> neighbors = new LinkedList<Neighbor>();
        int size = maze.getSize();
        Cell leftCell = cell.moveInDirection(Direction.LEFT);
        if(leftCell.x >= 0 && maze.isCellVisited(leftCell)) {
            neighbors.push(new Neighbor(cell,Direction.LEFT,leftCell));
        }
        Cell rightCell = cell.moveInDirection(Direction.RIGHT);
        if(rightCell.x < size && maze.isCellVisited(rightCell)) {
            neighbors.push(new Neighbor(cell,Direction.RIGHT,rightCell));
        }
        Cell upCell = cell.moveInDirection(Direction.UP);
        if(upCell.y >= 0 && maze.isCellVisited(upCell)) {
            neighbors.push(new Neighbor(cell,Direction.UP,upCell));
        }
        Cell downCell = cell.moveInDirection(Direction.DOWN);
        if(downCell.y < size && maze.isCellVisited(downCell)) {
            neighbors.push(new Neighbor(cell,Direction.DOWN,downCell));
        }
        return neighbors;
    }

    public void drawMaze() {
        if(maze.getSize() > MAX_DRAWN_MAZE_SIZE) {
            System.out.println("Prevented the drawing of a large maze to the screen.");
        }
        JFrame frame = new JFrame();
        Dimension size = new Dimension(CELL_SIZE * (maze.getSize() * 2 + 1), CELL_SIZE * (maze.getSize() * 2 + 1) + 15);
        frame.setSize(size);
        MazePanel panel = new MazePanel(maze, CELL_SIZE);
        panel.setSize(size);
        frame.add(panel);
        frame.setVisible(true);
    }

    public Maze getMaze(){
        return maze;
    }

}
