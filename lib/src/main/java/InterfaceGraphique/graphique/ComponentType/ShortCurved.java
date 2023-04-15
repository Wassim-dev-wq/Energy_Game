package InterfaceGraphique.graphique.ComponentType;


import java.awt.image.BufferedImage;

public class ShortCurved extends Component {
    private static BufferedImage off_short_curved;
    private static BufferedImage on_short_curved;

    public static BufferedImage src = ImageLoader.getSrc();

    private void createShortCurvedOn(int x, int y, int w, int h) {
        if (on_short_curved == null) {
            x = 480;
            y = 600;
            on_short_curved = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            on_short_curved.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        }
    }

    private void createShortCurvedOff(int x, int y, int w, int h) {
        if (off_short_curved == null) {
            x = 480;
            y = 240;
            off_short_curved = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            off_short_curved.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        }
    }
    @Override
    public void rotate() {
    }
    public ShortCurved(int x, int y, boolean isOn) {
        super(x, y);
        updateGraphics(x, y, 120, 104, isOn);
    }

    private void updateGraphics(int x, int y, int w, int h, boolean isOn) {
        if (isOn) {
            createShortCurvedOn(x, y, w, h);
            setCurrentImage(on_short_curved);
        } else {
            createShortCurvedOff(x, y, w, h);
            setCurrentImage(off_short_curved);
        }
    }
}
