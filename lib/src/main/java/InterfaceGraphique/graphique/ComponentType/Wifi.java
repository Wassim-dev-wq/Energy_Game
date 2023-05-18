package InterfaceGraphique.graphique.ComponentType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Wifi extends Component {
    private static BufferedImage wifi;
    private BufferedImage combined_wifi;
    private static BufferedImage off_wifi;
    private static BufferedImage on_wifi;
    private BufferedImage combined_wifi_off;
    private BufferedImage combined_wifi_on;

    public static BufferedImage src = ImageLoader.getSrc();
    private AffineTransform darkShortPathTransform;
    private String format;
    private int angle;
    private boolean isOn;
    private List<String> directions;
    private boolean isFirstRotation = true;


    @Override
    public void rotate() {
        if (format.equals("S")) {
            this.angle += 90;
            if (this.angle >  270) {
                this.angle = 0;
            }
        } else {
            this.angle += 60;
            if (this.angle >  300) {
                this.angle = 0;
            }
        }
        updateDirections();
        updateGraphics(0, 0, 120, 120, angle);
    }
    public Wifi(int x, int y, int w, int h, boolean isOn, List<String> directions, String gridType) {
        super(x, y);
        this.isOn = isOn;
        this.directions = directions;
        this.format = gridType;
        updateGraphics(x, y, w, h,angle);
    }

    private void createCombinedWifi(int x, int y, int w, int h, String gridType){
        if (isOn) y = 480;
        else y = 120;
        if (gridType.equals("S")) x = 120;
        else x = 480;
        double anchory;
        if (isFirstRotation){
            anchory = (double) h / 2;
            isFirstRotation = false;
        }else{
            anchory = (double) h / 2.3;
        }
        wifi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        wifi.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        ShortPath shortPath = new ShortPath(0, 0, isOn, gridType);

        combined_wifi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_lamp_dark = combined_wifi.createGraphics();
        g_combined_lamp_dark.drawImage(wifi, 0, 0, null);
        for (int i=0; i<directions.size(); i++){
            double rotationAngle;
            if(gridType.equals("S")){
                rotationAngle = Math.toRadians((Integer.parseInt(directions.get(i))*90));
                darkShortPathTransform = AffineTransform.getRotateInstance(rotationAngle, (double) w / 2, (double) h / 2);
            }else {
                rotationAngle = Math.toRadians((Integer.parseInt(directions.get(i))*60));
                darkShortPathTransform = AffineTransform.getRotateInstance(rotationAngle, (double) w / 2, anchory);
            }
            g_combined_lamp_dark.drawImage(shortPath.getCurrentImage(), darkShortPathTransform, null);
        }
        Empty empty = new Empty(0, 0, gridType, isOn);
        g_combined_lamp_dark.drawImage(empty.getCurrentImage(), 0, 0, null);
    }
    public int getAngle() {
        return angle;
    }
    private void updateGraphics(int x, int y, int w, int h, int angle) {
        createCombinedWifi(x, y, w, h, format);
        setCurrentImage(combined_wifi);
    }
    private void updateDirections() {
        List<String> newDirections = new ArrayList<>();
        for (String direction : this.directions) {
            int newDirection = (Integer.parseInt(direction) + 1) % 4; // for square grid
            if (this.format.equals("H")) {
                newDirection = (Integer.parseInt(direction) + 1) % 6;
            }
            newDirections.add(String.valueOf(newDirection));
        }
        this.directions = newDirections;
    }
}
