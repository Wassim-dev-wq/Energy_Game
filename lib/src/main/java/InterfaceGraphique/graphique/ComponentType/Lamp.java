package InterfaceGraphique.graphique.ComponentType;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class Lamp extends Component {
    private static BufferedImage lamp;
    private BufferedImage combined_lamp;
    public static BufferedImage src = ImageLoader.getSrc();
    private AffineTransform darkShortPathTransform;
    private String gridType;
    private int angle ;
    private boolean isOn;
    private List<String> directions;
    private boolean isFirstRotation = true;

    @Override
    public void rotate() {
        if (gridType.equals("S")) {
            this.angle += 90;
            if (this.angle >  270) { // to reset the angle to 0 when it goes back to the source
                this.angle = 0;
            }
        } else {
            this.angle += 60;
            if (this.angle >  300) {
                this.angle = 0;
            }
        }
        updateDirections();
        updateGraphics(0, 0, 120, 120, gridType, angle );
    }

    @Override
    public void updates() {
        System.out.println("update-lamp");
        updateGraphics(0,0,120,120,gridType,angle);
    }

    public Lamp(int x, int y, int w, int h, boolean isOn, List<String> directions, String gridType) {
        super(x, y);
        this.isOn = isOn;
        this.directions = directions;
        this.gridType = gridType;
        updateGraphics(x, y, w, h, gridType, angle);
    }
    @Override
    public List<String> getDirections() {
        return this.directions;
    }
    private void createCombinedLamp(int x, int y, int w, int h, String gridType,int angle) {
        if (isOn) y = 480;
        else y = 120;
        if (gridType.equals("S")){
            x = 240;
        }else{
            x = 600;
        }
        double anchory;
        if (isFirstRotation) {
            anchory = (double) h / 2;
            isFirstRotation = false;
        } else {
            anchory = (double) h / 2.3;
        }
        lamp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        lamp.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        ShortPath shortPath = new ShortPath(0, 0, isOn, gridType);

        combined_lamp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_lamp_dark = combined_lamp.createGraphics();
        g_combined_lamp_dark.drawImage(lamp, 0, 0, null);
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

    private void updateGraphics(int x, int y, int w, int h, String gridType,int angle) {
        createCombinedLamp(x, y, w, h, gridType,angle);
        setCurrentImage(combined_lamp);
    }
    private void updateDirections() {
        List<String> newDirections = new ArrayList<>();
        for (String direction : this.directions) {
            int newDirection = (Integer.parseInt(direction) + 1) % 4; // for square grid
            if (this.gridType.equals("H")) {
                newDirection = (Integer.parseInt(direction) + 1) % 6;
            }
            newDirections.add(String.valueOf(newDirection));
        }
        this.directions = newDirections;
    }
    public int getAngle() {
        return angle;
    }
    @Override
    public void setOn(boolean isOn) {
        this.isOn = isOn;
    }

    @Override
    public boolean getElectric() {
        return this.isOn;
    }

    @Override
    public boolean getIsOn() {
        return this.isOn;
    }
}
