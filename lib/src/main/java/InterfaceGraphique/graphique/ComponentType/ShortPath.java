package InterfaceGraphique.graphique.ComponentType;


import java.awt.image.BufferedImage;

public class ShortPath extends Component {
    private static BufferedImage off_short_path;
    private static BufferedImage on_short_path;

    public static BufferedImage src = ImageLoader.getSrc();

    private void createShortPathOn(int x, int y, int w, int h, String format) {
        if (on_short_path == null) {
            if (format.equals("H")) x = 360;
            else x = 0;
            y = 600;
            on_short_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            on_short_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        }
    }
    @Override
    public void rotate() {
    }
    private void createShortPathOff(int x, int y, int w, int h, String format) {
        if (off_short_path == null) {
            if (format.equals("H")) x = 360;
            else x = 0;
            y = 240;
            off_short_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            off_short_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        }
    }

    public ShortPath(int x, int y, boolean isOn, String format) {
        super(x, y);
        int width = 120;
        int height = 120;
        if (format.equals("H")) height = 104;
        updateGraphics(x, y, width, height, isOn, format);
    }

    private void updateGraphics(int x, int y, int w, int h, boolean isOn, String format) {
        if (isOn) {
            createShortPathOn(x, y, w, h, format);
            setCurrentImage(on_short_path);
        } else {
            createShortPathOff(x, y, w, h, format);
            setCurrentImage(off_short_path);
        }
    }
}
