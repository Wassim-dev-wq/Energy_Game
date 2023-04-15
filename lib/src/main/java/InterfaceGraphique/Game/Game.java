package InterfaceGraphique.Game;

import InterfaceGraphique.graphique.component.Board;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;

public class Game extends JFrame {

    public void updateWindowSize(int height, int length) {
        setSize(length * 120 + 10, height * 120 + 40);
    }

    public static void main(String[] args) {
        Game game = new Game();
        Board board = new Board(game);
        board.setBackground(Color.BLACK);
        game.add(board);
//        InputStream levelStream = Game.class.getResourceAsStream("/Levels/level1.nrg");   // SQUARE
//        InputStream levelStream = Game.class.getResourceAsStream("/Levels/level2.nrg");   // SQUARE
//        InputStream levelStream = Game.class.getResourceAsStream("/Levels/level3.nrg");   // HEXAGON
        InputStream levelStream = Game.class.getResourceAsStream("/Levels/level4.nrg");   // HEXAGON
//        InputStream levelStream = Game.class.getResourceAsStream("/Levels/level5.nrg");   // SQUARE
//        InputStream levelStream = Game.class.getResourceAsStream("/Levels/level6.nrg");   // SQUARE
//        InputStream levelStream = Game.class.getResourceAsStream("/Levels/level7.nrg");   // HEXAGON
//        InputStream levelStream = Game.class.getResourceAsStream("/Levels/level8.nrg");   // HEXAGON
//        InputStream levelStream = Game.class.getResourceAsStream("/Levels/level9.nrg");   // SQUARE
//        InputStream levelStream = Game.class.getResourceAsStream("/Levels/level10.nrg");  // HEXAGON
        board.loadLevel(levelStream);
        game.setTitle("Energy Game");
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }
}
