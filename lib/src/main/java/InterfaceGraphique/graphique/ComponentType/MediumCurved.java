package InterfaceGraphique.graphique.ComponentType;

import java.awt.image.BufferedImage;

public class MediumCurved extends Component {
    private static BufferedImage off_medium_curved;
    private static BufferedImage on_medium_curved;

    public static BufferedImage src = ImageLoader.getSrc();

    private void createMediumCurvedOn(int x, int y, int w, int h) {
        if (on_medium_curved == null) {
            x = 0;
            y = 600;
            on_medium_curved = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            on_medium_curved.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        }
    }

    private void createMediumCurvedOff(int x, int y, int w, int h) {
        if (off_medium_curved == null) {
            x = 0;
            y = 240;
            off_medium_curved = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            off_medium_curved.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        }
    }

    public MediumCurved(int x, int y, boolean isOn) {
        super(x, y);
        updateGraphics(x, y, 120, 120, isOn);
    }

    private void updateGraphics(int x, int y, int w, int h, boolean isOn) {
        if (isOn) {
            createMediumCurvedOn(x, y, w, h);
            setCurrentImage(on_medium_curved);
        } else {
            createMediumCurvedOff(x, y, w, h);
            setCurrentImage(off_medium_curved);
        }
    }
}
