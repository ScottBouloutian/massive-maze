import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class MazePanel extends JPanel {

    private Maze maze;
    private int cellSize;

    public MazePanel(Maze maze, int cellSize) {
        super();
        this.maze = maze;
        this.cellSize = cellSize;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,cellSize * (maze.getSize() * 2 + 1), cellSize * (maze.getSize() * 2 + 1));
        g.setColor(Color.WHITE);
        // Draw the entrance to the maze
        g.fillRect(cellSize,0,cellSize,cellSize);
        // Draw the exit of the maze
        g.fillRect((maze.getSize()*2-1)*cellSize,(maze.getSize()*2)*cellSize,cellSize,cellSize);
        // Draw the actual maze
        for(int x=0;x<maze.getSize();x++) {
            for(int y=0;y<maze.getSize();y++) {
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
