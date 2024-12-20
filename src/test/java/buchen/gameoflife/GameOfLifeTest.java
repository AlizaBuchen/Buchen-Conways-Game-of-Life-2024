package buchen.gameoflife;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeTest {

    @Test
    public void nextGen()
    {
        //given
        GameOfLife board = new GameOfLife(5, 5);
        board.put(1, 2);
        board.put(1, 3);
        board.put(1, 4);
        //when
        board.nextGen();
        //then
        assertEquals("00000\n00000\n00000\n11100\n00000\n", board.toString());
    }

}