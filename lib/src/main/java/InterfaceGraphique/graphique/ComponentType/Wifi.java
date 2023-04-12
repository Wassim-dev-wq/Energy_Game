package InterfaceGraphique.graphique.ComponentType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;

public class Wifi extends Component {
    private static BufferedImage off_wifi;
    private static BufferedImage on_wifi;
    private BufferedImage combined_wifi_off;
    private BufferedImage combined_wifi_on;

    public static BufferedImage src = ImageLoader.getSrc();

    private boolean isOn;
    private int direction;

    public Wifi(int x, int y, int w, int h, boolean isOn, int direction) {
        super(x, y);
        this.isOn = isOn;
        this.direction = direction;
        updateGraphics(x, y, w, h);
    }

    private void createCombinedWifiOff(int x, int y, int w, int h) {
        x = 120;
        y = 120;
        off_wifi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        off_wifi.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        ShortPath off_short_path = new ShortPath(0, 0, false);

        combined_wifi_off = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_wifi_off = combined_wifi_off.createGraphics();

        double rotationAngle = Math.toRadians(90 * direction);
        g_combined_wifi_off.drawImage(off_wifi, 0,0, null);

        AffineTransform offShortPathTransform = AffineTransform.getRotateInstance(rotationAngle, (double) w / 2, (double) h / 2);
        g_combined_wifi_off.drawImage(off_short_path.getCurrentImage(), offShortPathTransform, null);
    }

    private void createCombinedWifiOn(int x, int y, int w, int h) {
        x = 120;
        y = 480;
        on_wifi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        on_wifi.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        ShortPath on_short_path = new ShortPath(0, 0, true);

        combined_wifi_on = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_wifi_on = combined_wifi_on.createGraphics();

        double rotationAngle = Math.toRadians(90 * direction);
        g_combined_wifi_on.drawImage(on_wifi, 0,0,null);

        AffineTransform onShortPathTransform = AffineTransform.getRotateInstance(rotationAngle, (double) w / 2, (double) h / 2);
        g_combined_wifi_on.drawImage(on_short_path.getCurrentImage(), onShortPathTransform, null);
    }

    private void updateGraphics(int x, int y, int w, int h) {
        if (isOn) {
            createCombinedWifiOn(x, y, w, h);
            setCurrentImage(combined_wifi_on);
        } else {
            createCombinedWifiOff(x, y, w, h);
            setCurrentImage(combined_wifi_off);
        }
    }
}
