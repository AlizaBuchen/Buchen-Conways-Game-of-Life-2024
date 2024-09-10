package buchen.gameoflife;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameOfLifeTest {
    @Test
    public void nextGen()
    {
        //given
        GameOfLife board = new GameOfLife(5, 5);
        board.put(0, 4);
        board.put(1, 2);
        board.put(1, 3);
        board.put(1, 4);
        board.put(2, 4);
        //when
        board.nextGen();
        //then
        assertEquals("00001\n00001\n00001\n00000\n00000\n", board.toString());
    }
}