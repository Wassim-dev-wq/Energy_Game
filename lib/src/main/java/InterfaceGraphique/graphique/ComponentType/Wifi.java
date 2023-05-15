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
    private int angle;
    private boolean isOn;
    private List<String> directions;

    private boolean isFirstRotation = true;


    @Override
    public void rotate() {
        if (format.equals("S")) {
            this.angle -= 90;
            if (this.angle < -270) {
                this.angle = 0;
            }
        } else {
            this.angle -= 60;
            if (this.angle <  -300) {
                this.angle = 0;
            }
        }
        updateGraphics(0, 0, 120, 120, angle);
    }
    public Wifi(int x, int y, int w, int h, boolean isOn, List<String> directions, String gridType) {
        super(x, y);
        this.isOn = isOn;
        this.directions = directions;
        this.format = gridType;
        updateGraphics(x, y, w, h,angle);
    }

    private void createCombinedWifiOff(int x, int y, int w, int h,int angle) {
        if (format.equals("H")){
            x = 480;
        }else{
            x = 120;
        }
        y = 120;
        double anchory;
        if (isFirstRotation) {
            anchory = (double) h / 2;
            isFirstRotation = false;
        } else {
            anchory = (double) h / 2.3;
        }
        off_wifi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        off_wifi.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        ShortPath off_short_path = new ShortPath(0, 0, false, format);

        combined_wifi_off = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_wifi_off = combined_wifi_off.createGraphics();

        g_combined_wifi_off.drawImage(off_wifi, 0, 0, null);
        for (int i=0; i<directions.size(); i++){
            double rotationAngle;
            if(format.equals("H")){
                rotationAngle = Math.toRadians(angle - (Integer.parseInt(directions.get(i)) * 60));
            }else {
                rotationAngle = Math.toRadians(angle - (Integer.parseInt(directions.get(i)) * 90));
            }
            AffineTransform darkShortPathTransform = AffineTransform.getRotateInstance(rotationAngle, (double) w / 2, anchory);
            g_combined_wifi_off.drawImage(off_short_path.getCurrentImage(), darkShortPathTransform, null);
        }
        Empty empty = new Empty(0, 0, format, false);
        g_combined_wifi_off.drawImage(empty.getCurrentImage(), 0, 0, null);
    }

    private void createCombinedWifiOn(int x, int y, int w, int h,int angle) {
        if (format.equals("H")){
            x = 480;
            y = 480;
        }else{
            x = 120;
            y = 480;
        }
        on_wifi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        on_wifi.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        ShortPath on_short_path = new ShortPath(0, 0, true, format);

        combined_wifi_on = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_wifi_on = combined_wifi_on.createGraphics();

        g_combined_wifi_on.drawImage(on_wifi, 0, 0, null);
        for (int i=0; i<directions.size(); i++){
            double rotationAngle;
            if(format.equals("H")){
                rotationAngle = Math.toRadians(angle + (Integer.parseInt(directions.get(i)) * 60));
            }else {
                rotationAngle = Math.toRadians(angle + (Integer.parseInt(directions.get(i)) * 90));
            }
            AffineTransform darkShortPathTransform = AffineTransform.getRotateInstance(rotationAngle, (double) w / 2, (double) h / 2.3);
            g_combined_wifi_on.drawImage(on_short_path.getCurrentImage(), darkShortPathTransform, null);
        }
        Empty empty = new Empty(0, 0, format, true);
        g_combined_wifi_on.drawImage(empty.getCurrentImage(), 0, 0, null);
    }

    private void updateGraphics(int x, int y, int w, int h,int angle) {
        if (isOn) {
            createCombinedWifiOn(x, y, w, h,angle);
            setCurrentImage(combined_wifi_on);
        } else {
            createCombinedWifiOff(x, y, w, h,angle);
            setCurrentImage(combined_wifi_off);
        }
    }
}
