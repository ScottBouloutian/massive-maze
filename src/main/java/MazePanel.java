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
        g.fillRect(0,0,cellSize * maze.getSize() * 2, cellSize * maze.getSize() * 2);
        g.setColor(Color.WHITE);
        for(int i=0;i<maze.getNumCells();i++) {
            Direction direction = maze.getCell(i);
            if(direction!=null) {
                int row = i / maze.getSize();
                int col = i % maze.getSize();
                g.fillRect(col*2*cellSize,row*2*cellSize,cellSize,cellSize);
                switch(direction) {
                    case NONE:
                        break;
                    case LEFT:
                        g.fillRect((col*2+1)*cellSize,row*2*cellSize,cellSize,cellSize);
                        break;
                    case RIGHT:
                        g.fillRect((col*2-1)*cellSize,row*2*cellSize,cellSize,cellSize);
                        break;
                    case UP:
                        g.fillRect(col*2*cellSize,(row*2+1)*cellSize,cellSize,cellSize);
                        break;
                    case DOWN:
                        g.fillRect(col*2*cellSize,(row*2-1)*cellSize,cellSize,cellSize);
                        break;
                    default:
                        System.out.println("default case");
                }
            }
        }
    }
}
