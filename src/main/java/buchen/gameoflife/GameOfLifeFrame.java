package buchen.gameoflife;

import javax.swing.*;
import java.awt.*;

public class GameOfLifeFrame extends JFrame {
    //unicode for play button \u25B6
    private static final String PLAY_BUTTON = "▶";
    // unicode for the pause button (\u23F8)
    private static final String STOP_BUTTON = "⏸";
    //use gameStatus to toggle between the play and pause button symbol
    private boolean gameStatus = true;
    private final GameOfLifeController controller;

    public GameOfLifeFrame() {
        setSize(1500, 1500);
        setTitle("Game Of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        GameOfLife board = new GameOfLife(100, 100);
        GameOfLifeComponent gameOfLifeComponent = new GameOfLifeComponent(board);

        controller = new GameOfLifeController(board, gameOfLifeComponent);
        gameOfLifeComponent.setController(controller);

        add(gameOfLifeComponent, BorderLayout.CENTER);

        JButton nextGen = new JButton("Next");
        JButton play = new JButton(PLAY_BUTTON);
        JButton clear = new JButton("Clear");
        JButton paste = new JButton("Paste");

        JPanel actionBar = new JPanel(new BorderLayout());
        actionBar.add(nextGen, BorderLayout.WEST);
        actionBar.add(play, BorderLayout.CENTER);
        actionBar.add(clear, BorderLayout.EAST);
        actionBar.add(paste, BorderLayout.SOUTH);
        add(actionBar, BorderLayout.SOUTH);


        nextGen.addActionListener(e -> {
            controller.nextGen();
            gameStatus = true;
            play.setText(PLAY_BUTTON);
        });

        play.addActionListener(e -> {
            if (gameStatus) {
                controller.startTimer();
                gameStatus = false;
                play.setText(STOP_BUTTON);
            } else {
                controller.stopTimer();
                gameStatus = true;
                play.setText(PLAY_BUTTON);
            }
        });

        clear.addActionListener(e -> {
            controller.clear();
            gameStatus = true;
            play.setText(PLAY_BUTTON);
        });

        paste.addActionListener(e -> controller.paste());

    }
}
