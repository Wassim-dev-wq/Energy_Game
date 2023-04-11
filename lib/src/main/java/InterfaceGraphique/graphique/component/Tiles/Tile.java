package InterfaceGraphique.graphique.component.Tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public abstract class Tile {
    protected int x, y;
    protected int width, height;
    protected BufferedImage image;

    public Tile(int x, int y, BufferedImage image) {
        this.x = x;
        this.y = y;
        this.width = 120;
        this.height = 120;
        this.image = image;
    }

    public void draw(Graphics g) {
        g.drawImage(image, x * width, y * height, null);
    }
}


