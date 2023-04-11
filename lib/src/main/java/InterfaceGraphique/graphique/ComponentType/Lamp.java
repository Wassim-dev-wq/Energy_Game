package InterfaceGraphique.graphique.ComponentType;


import java.awt.*;
import java.awt.image.BufferedImage;

public class Lamp extends Component {
    private static BufferedImage dark_lamp;
    private static BufferedImage light_lamp;
    private static BufferedImage combined_lamp_dark;
    private static BufferedImage combined_lamp_light;

    public static BufferedImage src = ImageLoader.getSrc();


    private boolean isOn;

    public Lamp(int x, int y, int w,int h,boolean isOn) {
        super(x, y);
        this.isOn = isOn;
        updateGraphics(x,y,w,h);
    }

    private void createCombinedLampDark(int x,int y,int w,int h) {
        if (combined_lamp_dark == null) {
            x = 240;
            y = 120;
            dark_lamp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            dark_lamp.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
            ShortPath dark_short_path = new ShortPath(0, 0,false);
            combined_lamp_dark = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g_combined_lamp_dark = combined_lamp_dark.createGraphics();
            g_combined_lamp_dark.drawImage(dark_lamp, 0, 0, null);
            g_combined_lamp_dark.drawImage(dark_short_path.getCurrentImage(), 0, 0, null);
        }
    }

    private void createCombinedLampLight(int x,int y,int w,int h) {
        if (combined_lamp_light == null) {
            x = 240;
            y = 480;
            light_lamp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            light_lamp.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
            ShortPath light_short_path = new ShortPath(0, 0,true);
            combined_lamp_light = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g_combined_lamp_light = combined_lamp_light.createGraphics();
            g_combined_lamp_light.drawImage(light_lamp, 0, 0, null);
            g_combined_lamp_light.drawImage(light_short_path.getCurrentImage(), 0, 0, null);
        }
    }

    private void updateGraphics(int x, int y, int w,int h) {
        if (isOn) {
            createCombinedLampLight(x, y, w, h);
            setCurrentImage(combined_lamp_light);
        } else {
            createCombinedLampDark(x, y, w, h);
            setCurrentImage(combined_lamp_dark);
        }
    }
}
