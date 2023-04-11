package InterfaceGraphique.graphique.ComponentType;


import java.awt.image.BufferedImage;

public class LongPath extends Component {
    private static BufferedImage off_long_path;
    private static BufferedImage on_long_path;

    public static BufferedImage src = ImageLoader.getSrc();

    private void createLongPathOn(int x, int y, int w, int h) {
        if (on_long_path == null) {
            x = 240;
            y = 600;
            on_long_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            on_long_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        }
    }

    private void createLongPathOff(int x, int y, int w, int h) {
        if (off_long_path == null) {
            x = 240;
            y = 240;
            off_long_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            off_long_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        }
    }

    public LongPath(int x, int y, boolean isOn) {
        super(x, y);
        updateGraphics(x, y, 120, 120, isOn);
    }

    private void updateGraphics(int x, int y, int w, int h, boolean isOn) {
        if (isOn) {
            createLongPathOn(x, y, w, h);
            setCurrentImage(on_long_path);
        } else {
            createLongPathOff(x, y, w, h);
            setCurrentImage(off_long_path);
        }
    }
}
