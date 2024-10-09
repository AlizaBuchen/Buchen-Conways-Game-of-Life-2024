package buchen.gameoflife;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameOfLifeTest {
    private Clipboard clipboard;

    @BeforeEach
    public void setup() {
        clipboard = mock(Clipboard.class);
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
        when(clipboard.getData(DataFlavor.stringFlavor)).thenReturn(rleData);

        //when
        board.loadFromRle(rleData);

        //then
        assertTrue(board.isAlive(1, 0));
        assertTrue(board.isAlive(2, 1));
        assertTrue(board.isAlive(2, 2));
        assertTrue(board.isAlive(1, 2));
        assertTrue(board.isAlive(0, 2));
    }
}