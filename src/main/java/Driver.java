import java.util.Scanner;

public class Driver {

    public static void main(String[] args) {
        System.out.println("Welcome to the maze generator!");
        Scanner scanner = new Scanner(System.in);
        int size;
        MazeEngine engine;
        while(true){
            System.out.println("What size maze would you like to generate?");
            size = scanner.nextInt();
            engine = new MazeEngine(size);
            long startTime = System.currentTimeMillis();
            engine.generateMaze();
            long endTime = System.currentTimeMillis();
            //engine.drawMaze();
            float time = (float)(endTime - startTime)/1000;
            System.out.println("Maze generated in: " + time + " seconds.");
        }
    }

}
