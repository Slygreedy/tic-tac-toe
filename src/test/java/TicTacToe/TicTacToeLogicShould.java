package TicTacToe;

import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertArrayEquals;

public class TicTacToeLogicShould {

    //arguably this should be a TicTacToeBoardShould test?
    @Test
    public void testNew() throws Exception {
        TicTacToeLogic ticTacToeLogic = new TicTacToeLogic();
        assertEquals("         ", ticTacToeLogic.toString());
        assertEquals('x', ticTacToeLogic.turn);

    }

    //what does the second assertEquals do?
    @Test
    public void testMove() throws Exception {
        TicTacToeLogic ticTacToeLogic = new TicTacToeLogic().move(1);
        assertEquals(" x       ", ticTacToeLogic.toString());
        assertEquals('o', ticTacToeLogic.turn);
    }

    //I think this is just 'removing spaces from being possible moves once used
    @Test
    public void testPossibleMoves() throws Exception {
        TicTacToeLogic ticTacToeLogic = new TicTacToeLogic().move(1).move(3).move(4);
        assertArrayEquals(new Integer[] {0,2,5,6,7,8}, ticTacToeLogic.possibleMoves());
    }

    @Test
    public void testWin() throws Exception {
        assertFalse(new TicTacToeLogic().win('x'));
        assertTrue(new TicTacToeLogic("xxx      ").win('x'));
        assertTrue(new TicTacToeLogic("   ooo   ").win('o'));
        assertTrue(new TicTacToeLogic("x  x  x  ").win('x'));
        assertTrue(new TicTacToeLogic("  x x x  ").win('x'));
        assertTrue(new TicTacToeLogic("x   x   x").win('x'));
    }

    @Test
    public void testMiniMax() throws Exception {
        assertEquals( 100, new TicTacToeLogic("xxx      ").minimax());
        assertEquals(-100, new TicTacToeLogic("ooo      ").minimax());
        assertEquals(   0, new TicTacToeLogic("xoxoxooxo").minimax());
        assertEquals(  99, new TicTacToeLogic(" xx      ").minimax());
        assertEquals( -99, new TicTacToeLogic(" oo      ", 'o').minimax());
    }

    //why am I not having to do if and when i.e. just a then i.e. assertEquals? - actually think I am?
    //what is the point of the second assertion including turn?
    @Test
    public void testBestMove() throws Exception {
        assertEquals( 0, new TicTacToeLogic(" xx      ").bestMove());
        assertEquals( 1, new TicTacToeLogic("o o      ", 'o').bestMove());
    }

    @Test
    public void testGameEnd() throws Exception {
        assertFalse(new TicTacToeLogic().gameEnd());
        assertTrue(new TicTacToeLogic("xxx      ").gameEnd());
    }
}
