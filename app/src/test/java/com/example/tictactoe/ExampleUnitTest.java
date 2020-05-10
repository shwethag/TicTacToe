package com.example.tictactoe;

import com.example.tictactoe.bo.TicTacToeBO;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void isCompleteRowTest() {
        TicTacToeBO ticTacToeBO = new TicTacToeBO();
        assertFalse("Game not complete" , ticTacToeBO.isComplete());
        ticTacToeBO.set(1,0, TicTacToeBO.Cell.X);
        ticTacToeBO.set(1, 1 , TicTacToeBO.Cell.X);
        ticTacToeBO.set(1,2, TicTacToeBO.Cell.X);

        assertTrue("Game is complete", ticTacToeBO.isComplete());

        TicTacToeBO.Result result = ticTacToeBO.getResult();

        assertTrue("Index should be 1", result.getIndex() == 1);
        assertTrue("Dimension is row", result.getDimension() == TicTacToeBO.Dimension.ROW);
        assertTrue("isWon is true", result.isWon());
    }

    @Test
    public void isCompleteColTest() {
        TicTacToeBO ticTacToeBO = new TicTacToeBO();
        assertFalse("Game not complete" , ticTacToeBO.isComplete());
        ticTacToeBO.set(0,2, TicTacToeBO.Cell.X);
        ticTacToeBO.set(1, 2 , TicTacToeBO.Cell.X);
        ticTacToeBO.set(2,2, TicTacToeBO.Cell.X);

        assertTrue("Game is complete", ticTacToeBO.isComplete());
    }
}