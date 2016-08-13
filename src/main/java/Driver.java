public class Driver {

    public static void main(String[] args) {
        System.out.println("Welcome to the maze generator!");
        int mazeSize = (args.length >= 1 && !args[0].isEmpty()) ? Integer.parseInt(args[0]) : 30;
        System.out.println("Generating a " + mazeSize + "x" + mazeSize + " maze");
        MazeEngine engine = new MazeEngine(mazeSize);
        engine.generateMaze();
        engine.saveMaze("maze.dat");
    }

}
