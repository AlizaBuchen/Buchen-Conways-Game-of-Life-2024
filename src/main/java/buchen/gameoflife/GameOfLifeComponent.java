package buchen.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class GameOfLifeComponent extends JComponent {

    private final GameOfLife board;
    private GameOfLifeController controller;
    private final int CELL_SIZE = 20;

    public GameOfLifeComponent(GameOfLife board) {
        this.board = board;

        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                controller.toggleCell(x, y);
                repaint();
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

    public void setController(GameOfLifeController controller) { // Setter for controller
        this.controller = controller;
    }

    public int getCellSize() {
        return CELL_SIZE;
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
                    g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                }
            }
        }
    }
}