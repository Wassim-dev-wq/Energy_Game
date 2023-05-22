package InterfaceGraphique.graphique.ComponentType;


import java.awt.image.BufferedImage;

public class LongPath extends Component {
    private static BufferedImage off_long_path;
    private static BufferedImage on_long_path;

    public static BufferedImage src = ImageLoader.getSrc();
    int angle;
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

    @Override
    public String getName() {
        return null;
    }

    private void createLongPathOn(int x, int y, int w, int h, String format) {
        if (on_long_path == null) {
            if (format.equals("S")){
                x = 240;
            } else{
                x = 720;
                h = 104;
            }
            y = 600;
            on_long_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            on_long_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        }
    }
    //TODO
    @Override
    public void rotate() {
    }

    @Override
    public void updates() {

    }

    private void createLongPathOff(int x, int y, int w, int h, String format) {
        if (off_long_path == null) {
            if (format.equals("S")){
                x = 240;
            } else{
                x = 720;
            }
            y = 240;
            off_long_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            off_long_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        }
    }

    public LongPath(int x, int y, boolean isOn, String format) {
        super(x, y);
        int w = 120;
        int h = 120;
        if (format.equals("H")) h = 104;
        updateGraphics(x, y, w, h, isOn, format);
    }

    private void updateGraphics(int x, int y, int w, int h, boolean isOn, String format) {
        if (isOn) {
            createLongPathOn(x, y, w, h, format);
            setCurrentImage(on_long_path);
        } else {
            createLongPathOff(x, y, w, h, format);
            setCurrentImage(off_long_path);
        }
    }
}
