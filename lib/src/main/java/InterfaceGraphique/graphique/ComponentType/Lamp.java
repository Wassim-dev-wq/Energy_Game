package InterfaceGraphique.graphique.ComponentType;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

public class Lamp extends Component {
    private static BufferedImage dark_lamp;
    private static BufferedImage light_lamp;
    private BufferedImage combined_lamp_dark;
    private BufferedImage combined_lamp_light;

    public static BufferedImage src = ImageLoader.getSrc();

    private boolean isOn;
    private int direction;

    public Lamp(int x, int y, int w, int h, boolean isOn, int direction) {
        super(x, y);
        this.isOn = isOn;
        this.direction = direction;
        updateGraphics(x, y, w, h);
    }

    private void createCombinedLampDark(int x, int y, int w, int h) {
        x = 240;
        y = 120;
        dark_lamp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        dark_lamp.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        ShortPath dark_short_path = new ShortPath(0, 0, false);

        combined_lamp_dark = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_lamp_dark = combined_lamp_dark.createGraphics();

        g_combined_lamp_dark.drawImage(dark_lamp, 0, 0, null);

        double rotationAngle = Math.toRadians(90 * direction);
        AffineTransform darkShortPathTransform = AffineTransform.getRotateInstance(rotationAngle, (double) w / 2, (double) h / 2);
        g_combined_lamp_dark.drawImage(dark_short_path.getCurrentImage(), darkShortPathTransform, null);
    }

    private void createCombinedLampLight(int x, int y, int w, int h) {
        x = 240;
        y = 480;
        light_lamp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        light_lamp.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        ShortPath light_short_path = new ShortPath(0, 0, true);

        combined_lamp_light = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_lamp_light = combined_lamp_light.createGraphics();
        g_combined_lamp_light.drawImage(light_lamp, 0, 0, null);

        double rotationAngle = Math.toRadians(90 * direction);
        AffineTransform lightShortPathTransform = AffineTransform.getRotateInstance(rotationAngle, (double) w / 2, (double) h / 2);
        g_combined_lamp_light.drawImage(light_short_path.getCurrentImage(), lightShortPathTransform, null);
    }

    private void updateGraphics(int x, int y, int w, int h) {
        if (isOn) {
            createCombinedLampLight(x, y, w, h);
            setCurrentImage(combined_lamp_light);
        } else {
            createCombinedLampDark(x, y, w, h);
            setCurrentImage(combined_lamp_dark);
        }
    }
}
