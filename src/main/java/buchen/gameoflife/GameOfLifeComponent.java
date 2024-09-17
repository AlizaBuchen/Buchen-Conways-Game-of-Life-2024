package buchen.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class GameOfLifeComponent extends JComponent {

    private final GameOfLife board;


    public GameOfLifeComponent(GameOfLife board) {

        this.board = board;
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX() / 20;
                int y = e.getY() / 20;

                if (!board.isAlive(x, y)) {
                    board.put(x, y);
                } else {
                    board.remove(x, y);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                if (board.isAlive(x, y)) {
                    g.setColor(Color.BLACK);
                    g.fillRect(x * 20, y * 20, 20, 20);
                }
            }
        }
        repaint();
    }
}