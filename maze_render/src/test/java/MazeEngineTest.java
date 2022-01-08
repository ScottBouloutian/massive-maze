import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;

/**
 * Tests for {@link MazeEngine}.
 *
 * @author ScottBouloutian@gmail.com (Scott Bouloutian)
 */
public class MazeEngineTest {

    @Test
    public void constructor_sets_maze() {
        MazeEngine engine = new MazeEngine(10);
        assertEquals("the maze should be set correctly",new Maze(10),engine.getMaze());
        engine = new MazeEngine(100);
        assertEquals("the maze should be set correctly",new Maze(100),engine.getMaze());
    }

    @Test
    public void saveMaze_saves_maze() {
        Direction[][] cells = {
            {Direction.NONE,Direction.UP,Direction.RIGHT},
            {Direction.DOWN,Direction.LEFT,Direction.UP},
            {Direction.DOWN,Direction.RIGHT,Direction.RIGHT}
        };

        // Save the maze to a file
        MazeEngine engine = new MazeEngine();
        engine.setCells(cells);
        engine.saveMaze("src/test/resources/maze-3-save.dat");

        // Check the validity of the file
        Path path = Paths.get("src/test/resources/maze-3-save.dat");
        try {
            byte[] expectedBytes = {-41,34,64};
            byte[] data = Files.readAllBytes(path);
            for(int i=0;i<data.length;i++) {
                assertEquals("file byte " + i + " should be correct",expectedBytes[i],data[i]);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void loadMaze_loads_maze() {
        // Ensure a simple 3x3 maze loads correctly
        Direction[][] expectedCells = {
             {Direction.NONE,Direction.UP,Direction.RIGHT},
             {Direction.DOWN,Direction.LEFT,Direction.UP},
             {Direction.DOWN,Direction.RIGHT,Direction.RIGHT}
        };
        MazeEngine engine = new MazeEngine();
        engine.loadMaze("src/test/resources/maze-3-load.dat");
        assertEquals("the size should be set correctly",3,engine.getMazeSize());
        for(int y=0;y<3;y++) {
            for(int x=0;x<3;x++) {
                assertEquals("cell ("+x+","+y+") should be loaded correctly",expectedCells[y][x],engine.getMaze().getCell(x,y));
            }
        }
    }
}
