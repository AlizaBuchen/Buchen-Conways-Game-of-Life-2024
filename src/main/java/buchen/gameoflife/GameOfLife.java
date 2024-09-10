package buchen.gameoflife;

public class GameOfLife {
    private final int[][] field;
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
        int row = field.length;
        int col = field[0].length;
        for (int y = 0; y < row; y++) {
            for (int x = 0; x < col; x++) {
                int numAlive = 0;
                for (int[] direction : directions) {
                    int rowX = x + direction[0];
                    int colY = y + direction[1];
                    if (rowX >= 0 && rowX < row && colY >= 0 && colY < col && Math.abs(field[rowX][colY]) == 1) {
                        numAlive++;
                    }
                }
                if (field[x][y] == 1 && (numAlive < 2 || numAlive > 3)) {
                    field[x][y] = -1; //-1 indicates that this cell was alive but is now dead
                }
                if (field[x][y] == 0 && numAlive == 3) {
                    field[x][y] = 2; //2 indicates cell was dead and is now alive
                }
            }
        }

        //For loop to iterate over board again and make the changes.
        for (int y = 0; y < row; y++) {
            for (int x = 0; x < col; x++) {
                if (field[x][y] == 0 || field[x][y] == -1)
                {
                    field[x][y] = 0;
                } else if (field[x][y] == 1 || field[x][y] == 2)
                {
                    field[x][y] = 1;
                }
            }
        }
    }
}
