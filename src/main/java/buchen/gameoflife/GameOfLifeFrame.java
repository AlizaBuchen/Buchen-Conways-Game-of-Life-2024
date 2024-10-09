package buchen.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameOfLifeFrame extends JFrame {
    //unicode for play button \u25B6
    private static final String PLAY_BUTTON = "▶";
    // unicode for the pause button (\u23F8)
    private static final String STOP_BUTTON = "⏸";
    //use gameStatus to toggle between the play and pause button symbol
    private boolean gameStatus = true;
    private final GameOfLife board;

    Timer timer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            board.nextGen();
        }
    });

    public GameOfLifeFrame() {
        setSize(800, 800);
        setTitle("Game Of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        board = new GameOfLife(100, 100);
        GameOfLifeComponent gameOfLifeComponent = new GameOfLifeComponent(board);

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
            board.nextGen();
            gameStatus = true;
            play.setText(PLAY_BUTTON);
        });

        play.addActionListener(e -> {
            if (gameStatus) {
                timer.start();
                gameStatus = false;
                play.setText(STOP_BUTTON);
            } else {
                timer.stop();
                gameStatus = true;
                play.setText(PLAY_BUTTON);
            }
        });

        clear.addActionListener(e -> {
            board.clear();
            gameStatus = true;
            play.setText(PLAY_BUTTON);
        });

        paste.addActionListener(e -> {
            RleParser decoder = new RleParser(board);
            decoder.loadFromClipboard();
        });

    }
}
