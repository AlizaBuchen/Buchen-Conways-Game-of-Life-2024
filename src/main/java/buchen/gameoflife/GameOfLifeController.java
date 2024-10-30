package buchen.gameoflife;

import javax.swing.*;

public class GameOfLifeController {

    private final GameOfLife model;
    private final GameOfLifeComponent view;
    private final RleParser parser;
    private final Timer timer;

    public GameOfLifeController(GameOfLife model, GameOfLifeComponent view) {
        this.model = model;
        this.view = view;
        this.parser = new RleParser(model);
        timer = new Timer(1000, e -> {
            model.nextGen();
            view.repaint();
        });
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public void paste() {
        String rleData = parser.loadFromClipboard();
        if (rleData != null && !rleData.isEmpty()) {
            model.loadFromRle(rleData); // Load RLE data into the model
            view.repaint(); // Repaint the view after loading
        } else {
            JOptionPane.showMessageDialog(view, "No RLE data found in clipboard.");
        }
    }

    public void toggleCell(int x, int y) {
        int positionX = x / view.getCellSize();
        int positionY = y / view.getCellSize();
        if (model.isAlive(positionX, positionY)) {
            model.remove(positionX, positionY);
        } else {
            model.put(positionX, positionY);
        }
        view.repaint();
    }

    public void clear() {
        model.clear();
        view.repaint();
    }

    public void nextGen() {
        model.nextGen();
        view.repaint();
    }

}