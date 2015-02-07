public class Driver {

    public static void main(String[] args) {
        System.out.println("Welcome to the maze generator!");
        MazeEngine engine = new MazeEngine(10);
        engine.generateMaze();
        engine.drawMaze();
    }

}
