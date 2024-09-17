package buchen.gameoflife;

import java.util.Arrays;

public class GameOfLife {
    private final int[][] field;
    private static final int WAS_ALIVE = -1;
    private static final int ALIVE = 1;
    private static final int DEAD = 0;
    private static final int WAS_DEAD = 2;
    //list all the 8 possible directions
    private final int[][] directions = {
            {1, 0}, //right neighbor
            {-1, 0}, // left neighbor
            {0, 1}, //up neighbor
            {0, -1}, //down neighbor
            {1, -1}, //bottom left diagonal
            {1, 1}, //bottom right diagonal neighbor
            {-1, -1}, // upper left diagonal neighbor
            {-1, 1} // upper right diagonal neighbor
    };

    public GameOfLife(int width, int height) {
        field = new int[height][width];
    }


    public int getWidth() {
        return field[0].length;
    }

    public int getHeight() {
        return field.length;
    }
    public void clear() {
        for (int[] ints : field) Arrays.fill(ints, 0);
    }

    public boolean isAlive(int x, int y) {
        return field[y][x] == 1;
    }

    public void put(int x, int y) {
        field[y][x] = 1;
    }
    public void remove(int x, int y) {
        field[y][x] = 0;
    }


    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int[] ints : field) {
            for (int anInt : ints) {
                builder.append(anInt);
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public void nextGen() {
        int width = field.length;
        int height = field[0].length;
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                int numAlive = 0;
                for (int[] direction : directions) {
                    int rowX = x + direction[0];
                    int colY = y + direction[1];
                    if (rowX >= 0 && rowX < width && colY >= 0 && colY < height
                            && Math.abs(field[rowX][colY]) == ALIVE) {
                        numAlive++;
                    }
                }
                if (field[x][y] == ALIVE && (numAlive < 2 || numAlive > 3)) {
                    field[x][y] = WAS_ALIVE;
                }
                if (field[x][y] == DEAD && numAlive == 3) {
                    field[x][y] = WAS_DEAD;
                }
            }
        }

        //For loop to iterate over board again and make the changes.
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                if (field[x][y] == WAS_ALIVE)
                {
                    field[x][y] = DEAD;
                } else if (field[x][y] == WAS_DEAD)
                {
                    field[x][y] = ALIVE;
                }
            }
        }
    }
}
