public class Neighbor {
    private int parent;
    private Direction direction;
    private int child;

    public Neighbor(int parent, Direction direction, int child) {
        this.parent = parent;
        this.direction = direction;
        this.child = child;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getChild() {
        return child;
    }

}
