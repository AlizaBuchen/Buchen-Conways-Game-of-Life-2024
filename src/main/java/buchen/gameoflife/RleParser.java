package buchen.gameoflife;

import org.apache.commons.io.IOUtils;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.*;
import java.net.URL;


public class RleParser {
    private final GameOfLife game;

    public RleParser(GameOfLife game) {
        this.game = game;
    }

    public void decodeRle(String rle) {
        String[] lines = rle.split("\n");

        int patternWidth = 0;
        int patternHeight = 0;

        for (String line : lines) {
            line = line.trim();
            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            if (line.startsWith("x") || line.startsWith("y")) {
                String[] parts = line.split(",");
                for (String part : parts) {
                    String[] keyValue = part.split("=");
                    String key = keyValue[0].trim();
                    int value = Integer.parseInt(keyValue[1].trim());

                    if (key.equals("rule")) { break; }
                    if (key.equals("x")) {
                        patternWidth = Math.max(value, 100);
                    } else if (key.equals("y")) {
                        patternHeight = Math.max(value, 100);
                    }
                }
                game.resize(Math.max(patternWidth, 100), Math.max(patternHeight, 100));
                continue;
            }
            int startWidth = (game.getWidth() - patternWidth) / 2;
            int startHeight = (game.getHeight() - patternHeight) / 2;
            decodePattern(line, startWidth, startHeight);
        }
    }

    private void decodePattern(String patternLine, int row, int col) {
        int count = 0;

        for (int i = 0; i < patternLine.length(); i++) {
            char c = patternLine.charAt(i);

            if (Character.isDigit(c)) {
                count = count * 10 + Character.getNumericValue(c);
            } else {
                if (count == 0) {
                    count = 1;
                }
                switch (c) {
                    case 'b' -> // Dead cells
                            col += count;
                    case 'o' -> { // Live cells
                        for (int j = 0; j < count; j++) {
                            if (row < game.getHeight() && col < game.getWidth()) {
                                game.put(col, row);
                            }
                            col++;
                        }
                    }
                    case '$' -> { // New line
                        row += count;
                        col = 0;
                    }
                    case '!' -> { // End of RLE data
                        return;
                    }
                    default -> System.out.println("Invalid character: " + c);
                }
                count = 0;
            }
        }
    }

    public String loadFromClipboard() {
        String rleData;
        try {
            rleData = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException ex) {
            throw new RuntimeException(ex);
        }

        if (rleData.startsWith("http://") || rleData.startsWith("https://")) {
            try (InputStream inputStream = new URL(rleData).openStream()) {
                rleData = IOUtils.toString(inputStream, "UTF-8");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            File file = new File(rleData);
            if (file.exists() && file.isFile()) {
                try (FileInputStream fis = new FileInputStream(file)) {
                    rleData = IOUtils.toString(fis, "UTF-8");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        game.loadFromRle(rleData);
        return rleData;
    }
}