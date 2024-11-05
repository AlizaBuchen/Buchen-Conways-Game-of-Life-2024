package buchen.gameoflife;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class GameOfLifeComponentTest {

    private Clipboard clipboard;

    @BeforeEach
    public void setup() {
        clipboard = mock(Clipboard.class);
    }

    @Test
    void toggleCellOn() {

        //given
        GameOfLife model = mock(GameOfLife.class);
        GameOfLifeComponent view = mock(GameOfLifeComponent.class);

        //when
        doReturn(10).when(view).getCellSize();
        doReturn(100).when(model).getWidth();
        doReturn(100).when(model).getHeight();

        GameOfLifeController controller = new GameOfLifeController(model, view);

        controller.toggleCell(50, 100);

        //then
        verify(model).put(5, 10);
        verify(view).repaint();
    }

    @Test
    void toggleCellOff() {

        //given
        GameOfLife model = mock(GameOfLife.class);
        GameOfLifeComponent view = mock(GameOfLifeComponent.class);

        //when
        doReturn(10).when(view).getCellSize();
        doReturn(100).when(model).getWidth();
        doReturn(100).when(model).getHeight();
        doReturn(true).when(model).isAlive(5, 10);

        GameOfLifeController controller = new GameOfLifeController(model, view);

        controller.toggleCell(50, 100);

        //then
        verify(model).remove(5, 10);
        verify(view).repaint();
    }

    @Test
    public void clipboardRleString() throws IOException, UnsupportedFlavorException {
        //given
        GameOfLife board = new GameOfLife(100, 100);
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

    /*
    @Test
    public void pasteRle() throws IOException, UnsupportedFlavorException {
        //given
        GameOfLife model = mock(GameOfLife.class);
        GameOfLifeComponent view = mock(GameOfLifeComponent.class);
        GameOfLifeController controller = new GameOfLifeController(model, view);
        String rleData = """
            #C This is a glider.
            x = 3, y = 3
            bo$2bo$3o!
        """;

        //when
        when(clipboard.getData(DataFlavor.stringFlavor)).thenReturn(rleData);
        controller.paste();

        //then
        verify(view).repaint();
    }
    */
}
