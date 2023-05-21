package InterfaceGraphique.graphique.component;


import InterfaceGraphique.Game.Game;
import InterfaceGraphique.algorithm.ElectricityHandler;
import InterfaceGraphique.graphique.ComponentType.Component;
import InterfaceGraphique.graphique.ComponentType.Empty;
import InterfaceGraphique.graphique.ComponentType.Source;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static InterfaceGraphique.algorithm.ElectricityHandler.LOGGER;

public class Empty_Board extends JPanel {
    private Game game;
    private int width;
    private int height;
    private String format;
    private Component[][] components;
    public Empty_Board(Game game, int width, int height, String format){
        this.game = game;
        this.width = width;
        this.height = height;
        if (format.equals("Square")){
            this.format = "S";
        }else{
            this.format = "H";
        }
        System.out.println(this.width);
        System.out.println(this.height);
        System.out.println(this.format);
        this.components = new Component[height][width];
        setLayout(new BorderLayout());
        add(new ControlPanel(game, -1), BorderLayout.NORTH);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Mouse clicked at: (" + e.getX() + ", " + e.getY() + ")");
                handleClick(e.getX(), e.getY());
            }
        });
    }

    private void handleClick(int x, int y) {
    }

    private void createComponents() {
        int horizontalAlignment = (getWidth() - this.width * 120) / 2;
        int verticalAlignment = (getHeight() - this.height * 120) / 2;
        for (int j = 0; j < this.height; j++) {
            for (int k = 0; k < this.width; k++) {
                int y_value = 0;
                int x_value = 0;
                int width = 120;
                int height = 0;
                if (this.format.equals("S")) {
                    x_value = k * 120 + horizontalAlignment;
                    y_value = j * 120 + verticalAlignment;
                    height = 120;
                } else {
                    int y = 0;
                    if (k % 2 == 1) y = 52;
                    x_value = k * 90 + horizontalAlignment;
                    y_value = (j * 104) + y + verticalAlignment;
                    height = 104;
                }
                Component component = Components.createComponent(".", x_value, y_value, width, height, true, new ArrayList<>(), format);
                components[j][k] = component;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.updateWindowSize(this.height, this.width);
        createComponents();
        for (int i = 0; i < components.length; i++) {
            for (int j = 0; j < components[i].length; j++) {
                Component component = components[i][j];
                if (component != null) {
                    component.draw(g);
                }
            }
        }
    }
}
