public class Neighbor {
    private Cell parent;
    private Direction direction;
    private Cell child;

    public Neighbor(Cell parent, Direction direction, Cell child) {
        this.parent = parent;
        this.direction = direction;
        this.child = child;
    }

    public Cell getParent() {
        return parent;
    }

    public Direction getDirection() {
        return direction;
    }

    public Cell getChild() {
        return child;
    }

}
