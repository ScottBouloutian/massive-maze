import javax.swing.JPanel;
import java.awt.Graphics;

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
        MazeArtist.drawMaze(maze, cellSize, g);
    }
}
