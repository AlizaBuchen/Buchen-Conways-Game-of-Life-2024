package buchen.gameoflife;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class GameOfLifeTest {
    private Clipboard clipboard;

    @BeforeEach
    public void setup() {
        clipboard = Mockito.mock(Clipboard.class);
    }

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

    @Test
    public void clipboardRleString() throws IOException, UnsupportedFlavorException {
        //given
        GameOfLife board = new GameOfLife(100,100);
        String rleData = """
            #C This is a glider.
            x = 3, y = 3
            bo$2bo$3o!
        """;

        //when
        when(clipboard.getData(DataFlavor.stringFlavor)).thenReturn(rleData);
        board.loadFromRle(rleData);

        //then
        assertEquals(true, board.isAlive(1,0));
        assertEquals(true, board.isAlive(2,1));
        assertEquals(true, board.isAlive(2,2));
        assertEquals(true, board.isAlive(1,2));
        assertEquals(true, board.isAlive(0,2));
    }
}