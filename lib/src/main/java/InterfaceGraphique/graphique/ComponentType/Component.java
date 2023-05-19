package InterfaceGraphique.graphique.ComponentType;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Component {
    private int x = 0;
    private int y = 0;
    private BufferedImage currentImage;
    private Graphics2D currentGraphics;
    protected int width = 120;
    protected int height = 120;
    public abstract int getAngle();

    boolean isOn;

    public void setOn(boolean isOn) {
    }
    public abstract boolean getElectric();
    public abstract boolean getIsOn();
    public Component(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean containsPoint(int pointX, int pointY) {
        return pointX >= x && pointX <= x + width && pointY >= y && pointY <= y + height;
    }

    public abstract void rotate();

    public abstract void updates();

    public void setCurrentImage(BufferedImage currentImage) {
        this.currentImage = currentImage;
    }

    public void draw(Graphics g) {
        if (currentImage != null) {
            g.drawImage(currentImage, x, y, null);
        } else if (currentGraphics != null && g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(currentGraphics.getDeviceConfiguration().createCompatibleImage(120, 120), x, y, null);
        }
    }

    protected BufferedImage getCurrentImage() {
        return currentImage;
    }


    public List<String> getDirections() {
        return new ArrayList<>();
    }
}
