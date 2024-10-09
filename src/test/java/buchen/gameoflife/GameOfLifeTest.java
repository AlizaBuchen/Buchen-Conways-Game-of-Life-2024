package buchen.gameoflife;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameOfLifeTest {
//    @Test
//    public void loadFromRle() {
//        //given
//        GameOfLife game = new GameOfLife(5, 5);
//        String rleContent = """
//            #C This is a glider.
//            x = 3, y = 3
//            bo$2bo$3o!
//        """;
//
//        //when
//        game.loadFromRle(rleContent);
//
//        //then
//        assertEquals("010\n001\n111\n", game.toString());
//    }

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