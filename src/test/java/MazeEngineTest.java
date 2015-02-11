import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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


}
