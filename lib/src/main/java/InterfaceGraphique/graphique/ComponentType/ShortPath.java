package InterfaceGraphique.graphique.ComponentType;


import java.awt.image.BufferedImage;

public class ShortPath extends Component {
    private static BufferedImage off_short_path;
    private static BufferedImage on_short_path;

    public static BufferedImage src = ImageLoader.getSrc();

    private void createShortPathOn(int x, int y, int w, int h) {
        if (on_short_path == null) {
            x = 0;
            y = 600;
            on_short_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            on_short_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        }
    }

    private void createShortPathOff(int x, int y, int w, int h) {
        if (off_short_path == null) {
            x = 0;
            y = 240;
            off_short_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            off_short_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        }
    }

    public ShortPath(int x, int y, boolean isOn) {
        super(x, y);
        updateGraphics(x, y, 120, 120, isOn);
    }

    private void updateGraphics(int x, int y, int w, int h, boolean isOn) {
        if (isOn) {
            createShortPathOn(x, y, w, h);
            setCurrentImage(on_short_path);
        } else {
            createShortPathOff(x, y, w, h);
            setCurrentImage(off_short_path);
        }
    }
}
