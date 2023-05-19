package InterfaceGraphique.graphique.ComponentType;


import java.awt.image.BufferedImage;

public class ShortCurved extends Component {
    private static BufferedImage off_short_curved;
    private static BufferedImage on_short_curved;

    public static BufferedImage src = ImageLoader.getSrc();
    int angle;
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

    @Override
    public void updates() {

    }

    public ShortCurved(int x, int y, boolean isOn, String format) {
        super(x, y);
        updateGraphics(x, y, 120, 104, isOn);
    }
    public int getAngle() {
        return angle;
    }

    @Override
    public boolean getElectric() {
        return false;
    }

    @Override
    public boolean getIsOn() {
        return false;
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
