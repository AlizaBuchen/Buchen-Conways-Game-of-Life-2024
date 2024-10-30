package buchen.gameoflife;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
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
    private final GameOfLifeController controller;
//    private final GameOfLife board;
//    Timer timer = new Timer(1000, new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            board.nextGen();
//        }
//    });

    public GameOfLifeFrame() {
        setSize(1500, 1500);
        setTitle("Game Of Life");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        GameOfLife board = new GameOfLife(100, 100);
        GameOfLifeComponent gameOfLifeComponent = new GameOfLifeComponent(board);

        controller = new GameOfLifeController(board, gameOfLifeComponent);

//        RleParser parser = new RleParser(board);
//        GameOfLifeController controller = new GameOfLifeController(board, gameOfLifeComponent);

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
//            controller.nextGeneration();
            board.nextGen();
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
            // controller.clear();
            board.clear();
            gameStatus = true;
            play.setText(PLAY_BUTTON);
        });

        paste.addActionListener(e -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//            String data = null;
            if(clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor))
            {
                Transferable transferable = clipboard.getContents(null);
                if(transferable != null) {
//                    try {
//                        data = (String) transferable.getTransferData(DataFlavor.stringFlavor);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
                }
            }
            controller.paste();
        });

    }
}
