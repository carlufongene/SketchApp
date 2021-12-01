
import java.awt.Color;
import java.rmi.NoSuchObjectException;
import java.util.Map;
import java.util.TreeMap;


public class Sketch{
    private TreeMap<Integer, Shape> shapes; // shape map
    private int curID; // keeps track of ids


    public Sketch() {
        shapes = new TreeMap<>();

    }


    public TreeMap<Integer, Shape> getShapes() {
        return shapes;
    }

    /**
     *
     * @param x
     * @param y
     * @return the id of the collided with object, else return nothing
     */

    public synchronized int upperCollision(int x, int y) {
        for (Integer i : shapes.descendingKeySet()) {
            if (shapes.get(i).contains(x,y)) {
                return i;
            }
        }
        return -1;
    }

    /**
     *
     * @param id
     * @param shape
     * add new shape
     */

    public synchronized void addShape(int id, Shape shape) {
        if (shape == null) {return;}
        shapes.put(curID ++, shape);

    }

    /**
     *
     * @param id
     * delete the connecting id;
     */

    public synchronized void delete(int id) {
        shapes.remove(id);
    }

    /**
     * \
     * @param id
     * @param color
     * recolor the colliding shape
     */

    public synchronized void recolor(int id, Color color) {
        if (shapes.containsKey(id)) shapes.get(id).setColor(color);
    }

    /**
     *
     * @param id
     * @param dx
     * @param dy
     *
     * move the colliding shape
     */

    public synchronized void move(int id, int dx, int dy) {
        if (shapes.containsKey(id)) shapes.get(id).moveBy(dx, dy);
    }


}



