import java.util.LinkedList;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.Math;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.util.Scanner;

public class MazeEngine {

    private Maze maze;
    private static final int CELL_SIZE = 10;
    private static final int MAX_DRAWN_MAZE_SIZE = 30;
    private int beginHuntIndex = 0;
    private float percent = 0;

    public MazeEngine() {

    }

    public MazeEngine(int size) {
        maze = new Maze(size);
    }

    public void setCells(Direction[][] cells) {
        int size = cells.length;
        maze = new Maze(size);
        for(int y=0;y<size;y++) {
            for(int x=0;x<size;x++) {
                maze.setCell(x,y,cells[y][x]);
            }
        }
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

    public void saveMaze(String filePath) {
        int size = maze.getSize();
        double totalBits = size * size * 2;
        double numBytes = Math.ceil(totalBits/8);
        int currentByte = 0;
        int numBits = 0;
        try {
            FileOutputStream out = new FileOutputStream(filePath);
            for(int y=size-1;y>=0;y--) {
                for(int x=0;x<size;x++) {
                    switch(maze.getCell(x,y)) {
                        case RIGHT:
                            currentByte = currentByte + 1;
                            break;
                        case UP:
                            currentByte = currentByte + 2;
                            break;
                        case DOWN:
                            currentByte = currentByte + 3;
                            break;
                    }
                    currentByte = currentByte << 2;
                    numBits += 2;
                    if(numBits == 8) {
                        currentByte = currentByte >> 2;
                        out.write(currentByte);
                        currentByte = 0;
                        numBits = 0;
                    }
                }
            }
            if(numBits > 0) {
                currentByte = currentByte << (6-numBits);
                out.write(currentByte);
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadMaze(String filePath) {
        System.out.println("Loading maze");
        try {
            File file = new File(filePath);
            System.out.println("The file is of length " + file.length());
            int size = (int)Math.floor(Math.sqrt(file.length()*4));
            System.out.println("The maze is of size: " + size);
            maze = new Maze(size);
            FileInputStream in = new FileInputStream(filePath);
            int currentByte;
            float percent = 0;
            float currentPercent;
            long fileSize = file.length();
            byte[] buffer = new byte[1000];
            for(int b=0;b<fileSize;b++) {
                if(b%1000 == 0) {
                    in.read(buffer);
                }
                currentPercent = (float)b/fileSize * 100;
                if(currentPercent - percent > 1) {
                    percent = currentPercent;
                    System.out.println(percent + "% complete...");
                }
                currentByte = buffer[b%1000];
                currentByte+=256;
                for(int i=3;i>=0;i--) {
                    Direction direction;
                    byte encodedDirection = (byte)(currentByte%4);
                    int index = b * 4 + i;
                    int x = index%size;
                    int y = (size-1)-(index/size);
                    if(x>=0 && x<size && y>=0 && y<size) {
                        maze.setCell(x, y, encodedDirection);
                    }
                    currentByte = (byte)(currentByte >> 2);
                }
            }
            in.close();
            maze.setCell(0, 0, Direction.NONE);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public int getMazeSize() {
        return maze.getSize();
    }

    public void renderMaze(String filePath) {
        int imageSize = 1 * (maze.getSize() * 2 + 1);
        BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = image.createGraphics();
        MazeArtist.drawMaze(maze, 1, g);
        File file = new File(filePath);
        try {
            ImageIO.write(image, "gif", file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void renderMaze(MazeData data, String pathname) {
        // Calculate the size of the maze
        long fileSize = data.length();
        int size = (int)Math.floor(Math.sqrt(fileSize*4));

        // Create the image and graphics objects
        int imageSize = 1 * (size * 2 + 1);
        BufferedImage image = new BufferedImage(imageSize, imageSize, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = image.createGraphics();

        MazeArtist.prepareCanvas(size, 1, g);
        System.out.println("Loading maze");
        try {
            System.out.println("The file is of length: " + fileSize);
            System.out.println("The maze is of size: " + size);
            FileInputStream in = new FileInputStream(data);
            int currentByte;
            float percent = 0;
            float currentPercent;
            for(int b=0;b<fileSize;b++) {
                currentPercent = (float)b/fileSize * 100;
                if(currentPercent - percent > 1) {
                    percent = currentPercent;
                    System.out.println(percent + "% complete...");
                }
                currentByte = in.read();
                for(int i=3;i>=0;i--) {
                    Direction direction;
                    int encodedDirection = currentByte%4;
                    int mazeIndex = b * 4 + i;
                    int x = mazeIndex%size;
                    int y = mazeIndex/size;
                    if(x<size && y<size) {
                        MazeArtist.drawCell(x, y, encodedDirection, 1, g);
                    }
                    currentByte = currentByte >> 2;
                }
            }
            in.close();
            //maze.setCell(0, 0, Direction.NONE);
            File file = new File(pathname);
            ImageIO.write(image, "gif", file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
