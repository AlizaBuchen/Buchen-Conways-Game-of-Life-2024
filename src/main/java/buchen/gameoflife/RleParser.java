package buchen.gameoflife;

public class RleParser {
    private final GameOfLife game;

    public RleParser(GameOfLife game) {
        this.game = game;
    }

    public void decodeRle(String rle) {
        String[] lines = rle.split("\n");

        int row = 0;
        int col = 0;
        int width = 0;
        int height = 0;
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
                    if (key.equals("x")) {
                        width = value;
                    } else if (key.equals("y")) {
                        height = value;
                    }
                }
                game.resize(width, height);
                continue;
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
}
