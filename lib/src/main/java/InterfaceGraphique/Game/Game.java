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
        InputStream levelStream = Game.class.getResourceAsStream("/Levels/level1.nrg");
        board.loadLevel(levelStream);
        game.setTitle("Energy Game");
        game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setLocationRelativeTo(null);
        game.setVisible(true);
    }
}
