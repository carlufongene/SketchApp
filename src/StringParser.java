import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StringParser {
    /**
     * String parser class used to decode messages coming from the client
     */
    private final String[] line;

    /**
     *
     * @param words are the words that are to be decoded
     */
    public StringParser(String words) {
        //split by spaces
        line = words.split(" ");
    }

    public void parser(Sketch sketch) {
        // utilize switch statements
        switch (line[0]) {
            // if the first words happens to be add-
            case "add" -> {
                Shape shape = null;
                // check which shape it is
                switch (line[2]) {
                    case "rectangle" -> shape = new Rectangle(Integer.parseInt(line[3]),
                            Integer.parseInt(line[4]),
                            Integer.parseInt(line[5]),
                            Integer.parseInt(line[6]),
                            new Color(Integer.parseInt(line[7])));
                    case "segment" -> shape = new Segment(Integer.parseInt(line[3]),
                            Integer.parseInt(line[4]),
                            Integer.parseInt(line[5]),
                            Integer.parseInt(line[6]),
                            new Color(Integer.parseInt(line[7])));
                    case "ellipse" -> shape =
                            new Ellipse(Integer.parseInt(line[3]),
                                    Integer.parseInt(line[4]),
                                    Integer.parseInt(line[5]),
                                    Integer.parseInt(line[6]),
                                    new Color(Integer.parseInt(line[7])));
                    case "polyline" -> {
                        List<Point> points = new ArrayList<Point>();
                        for (int i = 3; i < line.length - 1; i += 2) {
                            points.add(new Point(Integer.parseInt(line[i]),
                                    Integer.parseInt(line[i + 1]))); shape = new Polyline(points, new
                                    Color(Integer.parseInt(line[line.length - 1])));
                        }
                    }
                    // else say it is a wrong shape type
                    default -> throw new IllegalArgumentException("Wrong shape");
                }
                // if the shape is new, given id,  add a new shape
                sketch.addShape(Integer.parseInt(line[1]), shape);
            }
            // cases for moving, recoloring and deleting
            case "move" -> sketch.move(
                    Integer.parseInt(line[1]), Integer.parseInt(line[2]),
                    Integer.parseInt(line[3]));

            case "recolor" -> sketch.recolor(
                    Integer.parseInt(line[1]),
                    new Color(Integer.parseInt(line[2])));

            case "delete" -> sketch.delete(
                    Integer.parseInt(line[1]));

            default -> System.err.println("unrecognized command");
        }
    }

    public String toString() {
        StringBuilder s = new StringBuilder(line[0]);
        for (int i=1; i<line.length; i++)
            s.append(" ").append(line[i]);
        return s.toString();
    }

}
