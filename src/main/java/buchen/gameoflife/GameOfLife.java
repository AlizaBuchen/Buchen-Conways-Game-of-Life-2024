package buchen.gameoflife;

public class GameOfLife {
    private final int[][] field;
    private final int wasAlive = -1;
    private final int alive = 1;
    private final int dead = 0;
    private final int wasDead = 2;
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

    public void put(int x, int y) {
        field[x][y] = 1;
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
                            && Math.abs(field[rowX][colY]) == alive) {
                        numAlive++;
                    }
                }
                if (field[x][y] == alive && (numAlive < 2 || numAlive > 3)) {
                    field[x][y] = wasAlive;
                }
                if (field[x][y] == dead && numAlive == 3) {
                    field[x][y] = wasDead;
                }
            }
        }

        //For loop to iterate over board again and make the changes.
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                if (field[x][y] == wasAlive)
                {
                    field[x][y] = dead;
                } else if (field[x][y] == wasDead)
                {
                    field[x][y] = alive;
                }
            }
        }
    }
}
