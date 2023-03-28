package InterfaceGraphique.graphique;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class test_png extends JPanel{
    public test_png(){
        setSize(500, 500);
        setBackground(Color.black);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Image src = null;
        try {
            src = ImageIO.read(new File("./src/tuiles.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int x = 0, y = 0, w = 120, h = 120;
        BufferedImage empty = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        empty.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 120;
        y = 240;
        BufferedImage dark_curved_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        dark_curved_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 120;
        y = 120;
        BufferedImage dark_wifi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        dark_wifi.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 240;
        y = 120;
        BufferedImage dark_lamp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        dark_lamp.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 0;
        y = 240;
        BufferedImage short_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        short_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 240;
        y = 240;
        BufferedImage dark_long_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        dark_long_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 0;
        y = 480;
        BufferedImage energy = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        energy.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 120;
        BufferedImage light_wifi = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        light_wifi.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 240;
        BufferedImage light_lamp = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        light_lamp.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 0;
        y = 600;
        BufferedImage light_short_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        light_short_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 120;
        BufferedImage light_curved_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        light_curved_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        x = 240;
        BufferedImage light_long_path = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        light_long_path.getGraphics().drawImage(src, 0, 0, w, h, x, y, x + w, y + h, null);
        g.drawImage(empty, 0, 0, this);
        g.drawImage(dark_curved_path, 120, 0, this);
        g.drawImage(dark_lamp, 240, 0, this);
        g.drawImage(energy, 0, 120, this);
        g.drawImage(dark_curved_path, 120, 120, this);
        g.drawImage(empty,240, 120, this);
    }

    public static void main(String[] args) throws IOException {
        JFrame f = new JFrame();
        f.add(new test_png());

//        ImageIO.write(dst, "png", new File("duke_cropped.png"));

        f.setSize(500, 500);
        f.setBackground(Color.BLACK);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setVisible(true);
    }
}

