package InterfaceGraphique.graphique.ComponentType;


import java.awt.image.BufferedImage;

public class LongCurved extends Component {
    private static BufferedImage off_long_curved;
    private static BufferedImage on_long_curved;

    public static BufferedImage src = ImageLoader.getSrc();
    int angle;

    private void createLongCurvedOn(int x, int y, int w, int h) {
        if (on_long_curved == null) {
            x = 600;
            y = 600;
            on_long_curved = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            on_long_curved.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        }
    }
    //TODO
    @Override
    public void rotate() {
    }
    private void createLongCurvedOff(int x, int y, int w, int h) {
        if (off_long_curved == null) {
            x = 600;
            y = 240;
            off_long_curved = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            off_long_curved.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        }
    }

    public LongCurved(int x, int y, boolean isOn, String format) {
        super(x, y);
        updateGraphics(x, y, 120, 104, isOn);
    }
    public int getAngle() {
        return angle;
    }
    private void updateGraphics(int x, int y, int w, int h, boolean isOn) {
        if (isOn) {
            createLongCurvedOn(x, y, w, h);
            setCurrentImage(on_long_curved);
        } else {
            createLongCurvedOff(x, y, w, h);
            setCurrentImage(off_long_curved);
        }
    }
}
