import java.awt.Graphics;
import java.awt.Color;

public class MazeArtist {

    public static void prepareCanvas(int size, int cellSize, Graphics g) {
        // Draw the background of the maze
        g.setColor(Color.BLACK);
        g.fillRect(0,0,cellSize * (size * 2 + 1), cellSize * (size * 2 + 1));
        g.setColor(Color.WHITE);
        // Draw the entrance to the maze
        g.fillRect(cellSize,0,cellSize,cellSize);
        // Draw the exit of the maze
        g.fillRect((size*2-1)*cellSize,(size*2)*cellSize,cellSize,cellSize);
    }

    public static void drawCell(int x, int y, int encodedDirection, int cellSize, Graphics g) {
        g.fillRect((x*2+1)*cellSize,(y*2+1)*cellSize,cellSize,cellSize);
        switch(encodedDirection) {
            case 0:
                if(!(x==0 && y==0)) {
                    g.fillRect((x*2+1+1)*cellSize,(y*2+1)*cellSize,cellSize,cellSize);
                }
                break;
            case 1:
                g.fillRect((x*2-1+1)*cellSize,(y*2+1)*cellSize,cellSize,cellSize);
                break;
            case 2:
                g.fillRect((x*2+1)*cellSize,(y*2+2)*cellSize,cellSize,cellSize);
                break;
            case 3:
                g.fillRect((x*2+1)*cellSize,(y*2)*cellSize,cellSize,cellSize);
                break;
        }
    }

    public static void drawMaze(Maze maze, int cellSize, Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,cellSize * (maze.getSize() * 2 + 1), cellSize * (maze.getSize() * 2 + 1));
        g.setColor(Color.WHITE);
        // Draw the entrance to the maze
        g.fillRect(cellSize,0,cellSize,cellSize);
        // Draw the exit of the maze
        g.fillRect((maze.getSize()*2-1)*cellSize,(maze.getSize()*2)*cellSize,cellSize,cellSize);
        // Draw the actual maze
        float percent=0;
        float currentPercent;
        int size = maze.getSize();
        for(int x=0;x<size;x++) {
            for(int y=0;y<size;y++) {
                currentPercent = (float)(y*size+x) / size / size * 100;
                if(currentPercent - percent > 1) {
                    percent = currentPercent;
                    System.out.println(percent + "% complete...");
                }
                Direction direction = maze.getCell(x,y);
                if(direction!=null) {
                    g.fillRect((x*2+1)*cellSize,(y*2+1)*cellSize,cellSize,cellSize);
                    switch(direction) {
                        case NONE:
                            break;
                        case LEFT:
                            g.fillRect((x*2+1+1)*cellSize,(y*2+1)*cellSize,cellSize,cellSize);
                            break;
                        case RIGHT:
                            g.fillRect((x*2-1+1)*cellSize,(y*2+1)*cellSize,cellSize,cellSize);
                            break;
                        case UP:
                            g.fillRect((x*2+1)*cellSize,(y*2+2)*cellSize,cellSize,cellSize);
                            break;
                        case DOWN:
                            g.fillRect((x*2+1)*cellSize,(y*2)*cellSize,cellSize,cellSize);
                            break;
                        default:
                            System.out.println("default case");
                    }
                }
            }
        }
    }

}
