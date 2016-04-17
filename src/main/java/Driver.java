import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        System.out.println("Welcome to the maze generator!");
        MazeEngine engine = new MazeEngine(10000);
        engine.generateMaze();
        engine.saveMaze("maze-10000.dat");
    }

}
