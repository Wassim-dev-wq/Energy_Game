package InterfaceGraphique.graphique.ComponentType;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Wifi extends Component {
    private static BufferedImage off_wifi;
    private static BufferedImage on_wifi;
    private static BufferedImage combined_wifi_off;
    private static BufferedImage combined_wifi_on;

    public static BufferedImage src = ImageLoader.getSrc();

    private boolean isOn;

    public Wifi(int x, int y, int w, int h, boolean isOn) {
        super(x, y);
        this.isOn = isOn;
        updateGraphics(x, y, w, h);
    }

    private void createCombinedWifiOff(int x, int y, int w, int h) {
        if (combined_wifi_off == null) {
            x = 120;
            y = 120;
            off_wifi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            off_wifi.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
            ShortPath off_short_path = new ShortPath(0, 0,false);
            combined_wifi_off = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g_combined_wifi_off = combined_wifi_off.createGraphics();
            g_combined_wifi_off.drawImage(off_wifi, 0, 0, null);
            g_combined_wifi_off.drawImage(off_short_path.getCurrentImage(), 0, 0, null);
        }
    }

    private void createCombinedWifiOn(int x, int y, int w, int h) {
        if (combined_wifi_on == null) {
            x = 120;
            y = 480;
            on_wifi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            on_wifi.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
            ShortPath on_short_path = new ShortPath(0, 0,true);
            combined_wifi_on = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g_combined_wifi_on = combined_wifi_on.createGraphics();
            g_combined_wifi_on.drawImage(on_wifi, 0, 0, null);
            g_combined_wifi_on.drawImage(on_short_path.getCurrentImage(), 0, 0, null);
        }
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
