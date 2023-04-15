package InterfaceGraphique.graphique.ComponentType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.util.List;

public class Wifi extends Component {
    private static BufferedImage off_wifi;
    private static BufferedImage on_wifi;
    private BufferedImage combined_wifi_off;
    private BufferedImage combined_wifi_on;

    public static BufferedImage src = ImageLoader.getSrc();

    private String format;

    private boolean isOn;
    private List<String> directions;
    //TODO
    @Override
    public void rotate() {
    }
    public Wifi(int x, int y, int w, int h, boolean isOn, List<String> directions, String format) {
        super(x, y);
        this.isOn = isOn;
        this.directions = directions;
        this.format = format;
        updateGraphics(x, y, w, h);
    }

    private void createCombinedWifiOff(int x, int y, int w, int h) {
        int angle = 60;
        if (format.equals("H")){
            x = 480;
        }else{
            x = 120;
            angle = 90;
        }
        y = 120;
        off_wifi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        off_wifi.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        ShortPath off_short_path = new ShortPath(0, 0, false, format);

        combined_wifi_off = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_wifi_off = combined_wifi_off.createGraphics();

        g_combined_wifi_off.drawImage(off_wifi, 0, 0, null);
        for (int i=0; i<directions.size(); i++){
            double rotationAngle = Math.toRadians(angle * Integer.parseInt(directions.get(i)));
            AffineTransform darkShortPathTransform = AffineTransform.getRotateInstance(rotationAngle, (double) w / 2, (double) h / 2);
            g_combined_wifi_off.drawImage(off_short_path.getCurrentImage(), darkShortPathTransform, null);
        }
        Empty empty = new Empty(0, 0, format, false);
        g_combined_wifi_off.drawImage(empty.getCurrentImage(), 0, 0, null);
    }

    private void createCombinedWifiOn(int x, int y, int w, int h) {
        int angle = 60;
        if (format.equals("H")){
            x = 480;
            y = 480;
        }else{
            x = 120;
            y = 480;
            angle = 90;
        }
        on_wifi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        on_wifi.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        ShortPath on_short_path = new ShortPath(0, 0, true, format);

        combined_wifi_on = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_wifi_on = combined_wifi_on.createGraphics();

        g_combined_wifi_on.drawImage(on_wifi, 0, 0, null);
        for (int i=0; i<directions.size(); i++){
            double rotationAngle = Math.toRadians(angle * Integer.parseInt(directions.get(i)));
            AffineTransform darkShortPathTransform = AffineTransform.getRotateInstance(rotationAngle, (double) w / 2, (double) h / 2);
            g_combined_wifi_on.drawImage(on_short_path.getCurrentImage(), darkShortPathTransform, null);
        }
        Empty empty = new Empty(0, 0, format, true);
        g_combined_wifi_on.drawImage(empty.getCurrentImage(), 0, 0, null);
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
