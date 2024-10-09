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

        int row = 0;
        int col = 0;
        int width = 0, height = 0;
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
                    if(key.equals("rule"))
                    {
                        break;
                    }
                    int value = Integer.parseInt(keyValue[1].trim());
                    if (key.equals("x")) {
                        width = Math.max(value, 100);
                    }
                    else if (key.equals("y")) {
                        height = Math.max(value, 100);
                    }
                }
                game.resize(width, height);
            }
            decodePattern(line, row, col);
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
                    case 'b': // Dead cells
                        col += count;
                        break;

                    case 'o': // Live cells
                        for (int j = 0; j < count; j++) {
                            if (row < game.getHeight() && col < game.getWidth()) {
                                game.put(col, row);
                            }
                            col++;
                        }
                        break;

                    case '$': // New line
                        row += count;
                        col = 0;
                        break;

                    case '!': // End of RLE data
                        return;

                    default:
                        System.out.println("Invalid character: " + c);
                        break;
                }
                count = 0;
            }
        }
    }

    public void loadFromClipboard() {
        String rleData = null;
        try {
            rleData = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (UnsupportedFlavorException | IOException ex) {
            throw new RuntimeException(ex);
        }

        if (rleData.startsWith("http://") || rleData.startsWith("https://")) {
            try (InputStream inputStream = new URL(rleData).openStream()){
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
    }
}