package InterfaceGraphique.graphique.ComponentType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class CombinedPath extends Component {
    private List<String> directions;

    private LongCurved off_curved_path;
    private LongPath off_long_path;
    private Empty empty;
    private int _x;
    private int _y;

    public CombinedPath(int x, int y, List<String> directions) {
        super(x, y);
        this._x = x;
        this._y = y;
        this.directions = directions;
        createComponents();
    }

    public int getX() {
        return _x;
    }

    public int getY() {
        return _y;
    }

    private void createComponents() {
        off_curved_path = new LongCurved(0, 0, false);
        off_long_path = new LongPath(0, 0, false);
        empty = new Empty(0, 0);
    }

    @Override
    public void draw(Graphics g) {
        BufferedImage combined_path = new BufferedImage(120, 120, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_path = combined_path.createGraphics();
        int difference = 0;
        try {
            difference = Integer.parseInt(directions.get(1)) - Integer.parseInt(directions.get(0));
        } catch (NumberFormatException e) {
            System.out.println("Invalid value : " + e.getMessage());
        }

        if (difference == 1 || difference == 3) {
            if (difference == 1) {
                g_combined_path.rotate(Math.toRadians(Integer.parseInt(directions.get(0)) * 90), 60, 60);
                off_curved_path.draw(g_combined_path);

            } else {
                g_combined_path.rotate(Math.toRadians(Integer.parseInt(directions.get(1)) * 90), 60, 60);
                off_curved_path.draw(g_combined_path);
            }
        } else if (difference == 2) {
            g_combined_path.rotate(Math.toRadians(Integer.parseInt(directions.get(0)) * 90), 60, 60);
            off_long_path.draw(g_combined_path);
        }

        empty.draw(g_combined_path);
        g.drawImage(combined_path, getX(), getY(), null);
    }
}
