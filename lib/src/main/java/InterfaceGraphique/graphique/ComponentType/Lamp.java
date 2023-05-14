package InterfaceGraphique.graphique.ComponentType;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.geom.AffineTransform;
import java.util.List;

public class Lamp extends Component {
    private static BufferedImage lamp;
    private static BufferedImage light_lamp;
    private BufferedImage combined_lamp_dark;
    private BufferedImage combined_lamp;
    private BufferedImage combined_lamp_light;

    public static BufferedImage src = ImageLoader.getSrc();

    private static int angle_turn = 0;

    private String format;

    private boolean isOn;
    private List<String> directions;
    //TODO
    @Override
    public void rotate() {
        System.out.println("Lamp");
        System.out.println("format -> " + format);
        if (format.equals("S")){
            angle_turn += 90;
        }else{
            angle_turn += 60;
        }
        System.out.println("angle_turn ->" + angle_turn);
        updateGraphics(0, 0, 120, 120, format);
    }
    public Lamp(int x, int y, int w, int h, boolean isOn, List<String> directions, String format) {
        super(x, y);
        this.isOn = isOn;
        this.directions = directions;
        this.format = format;
        updateGraphics(x, y, w, h, format);
    }


    private void createCombinedLamp(int x, int y, int w, int h, String format) {
        int angle = 90;
        if (isOn) y = 480;
        else y = 120;
        if (format.equals("S")){
            x = 240;
        }else{
            x = 600;
            angle = 60;
        }
        lamp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        lamp.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        ShortPath shortPath = new ShortPath(0, 0, isOn, format);

        combined_lamp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g_combined_lamp_dark = combined_lamp.createGraphics();

        g_combined_lamp_dark.drawImage(lamp, 0, 0, null);
        for (int i=0; i<directions.size(); i++){
            double rotationAngle = Math.toRadians((angle * Integer.parseInt(directions.get(i)))+angle_turn);
            AffineTransform darkShortPathTransform = AffineTransform.getRotateInstance(rotationAngle, (double) w / 2, (double) h / 2);
            g_combined_lamp_dark.drawImage(shortPath.getCurrentImage(), darkShortPathTransform, null);
        }
        Empty empty = new Empty(0, 0, format, isOn);
        g_combined_lamp_dark.drawImage(empty.getCurrentImage(), 0, 0, null);
    }

//    private void createCombinedLampLight(int x, int y, int w, int h, String format) {
//        int angle = 90;
//        if (format.equals("S")){
//            x = 240;
//            y = 480;
//        }else{
//            x = 600;
//            y = 600;
//            angle = 60;
//        }
//        light_lamp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//        light_lamp.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
//        ShortPath light_short_path = new ShortPath(0, 0, true, format);
//
//        combined_lamp_light = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
//        Graphics2D g_combined_lamp_light = combined_lamp_light.createGraphics();
//        g_combined_lamp_light.drawImage(light_lamp, 0, 0, null);
//
//        for (int i=0; i<directions.size(); i++){
//            double rotationAngle = Math.toRadians(angle * Integer.parseInt(directions.get(i)));
//            AffineTransform darkShortPathTransform = AffineTransform.getRotateInstance(rotationAngle, (double) w / 2, (double) h / 2);
//            g_combined_lamp_light.drawImage(light_short_path.getCurrentImage(), darkShortPathTransform, null);
//        }
//        Empty empty = new Empty(0, 0, format, true);
//        g_combined_lamp_light.drawImage(empty.getCurrentImage(), 0, 0, null);
//    }

    private void updateGraphics(int x, int y, int w, int h, String format) {
        createCombinedLamp(x, y, w, h, format);
        setCurrentImage(combined_lamp);
//        if (isOn) {
//            createCombinedLampLight(x, y, w, h, format);
//            setCurrentImage(combined_lamp_dark);
//            setCurrentImage(combined_lamp_light);
//        } else {
//            createCombinedLampDark(x, y, w, h, format);
//            setCurrentImage(combined_lamp_light);
//            setCurrentImage(combined_lamp_dark);
//        }
    }
}
