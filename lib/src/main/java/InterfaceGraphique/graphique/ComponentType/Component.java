package InterfaceGraphique.graphique.ComponentType;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public abstract class Component {
    private int x=0;
    private int y=0;
    private BufferedImage currentImage;
    private Graphics2D currentGraphics;

    private boolean power_on = false;

    public Component(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getDescription(){
        return "Component";
    }
    public boolean is_powered(){
        return power_on;
    }

    public void power_on(){
        power_on = true;
    }

    public void power_off(){
        power_on = false;
    }

    public void setCurrentImage(BufferedImage currentImage) {
        this.currentImage = currentImage;
    }

    public void draw(Graphics g) {
        if (currentImage != null) {
            g.drawImage(currentImage, x, y, null);
        } else if (currentGraphics != null && g instanceof Graphics2D) {
            Graphics2D g2d = (Graphics2D) g;
//            if (format.equals("S")){
            g2d.drawImage(currentGraphics.getDeviceConfiguration().createCompatibleImage(120, 120), x, y, null);
//            }else{
//                g2d.drawImage(currentGraphics.getDeviceConfiguration().createCompatibleImage(120, 104), x, y, null);
//            }
        }
    }

    protected BufferedImage getCurrentImage() {
        return currentImage;
    }

}
