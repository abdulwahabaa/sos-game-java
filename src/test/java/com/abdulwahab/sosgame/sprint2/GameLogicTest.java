package com.abdulwahab.sosgame.sprint2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class GameLogicTest {

    private GameLogic game;

    @BeforeEach
    void setUp() {
        game = new GameLogic(4);
    }

    @Test
    void testBoardSize() {
        assertEquals(4, game.getSize());
    }

    @Test
    void testSmallBoardThrowsError() {
        assertThrows(IllegalArgumentException.class, () -> new GameLogic(2));
    }

    @Test
    void testValidMove() {
        boolean result = game.playerMove(0, 0, 'S');
        assertTrue(result);
        assertEquals('S', game.getBoard()[0][0]);
    }

    @Test
    void testInvalidMove_OutOfBounds() {
        boolean result = game.playerMove(-1, 1, 'O');
        assertFalse(result);
    }

    @Test
    void testCannotPlaceOnFilledCell() {
        game.playerMove(1, 1, 'S');
        boolean result = game.playerMove(1, 1, 'O');
        assertFalse(result);
    }

    @Test
    void testTurnSwitchesAfterValidMove() {
        assertEquals(GameLogic.Player.RED, game.getCurrentPlayer());

        game.playerMove(0, 0, 'S'); // valid move

        assertEquals(GameLogic.Player.BLUE, game.getCurrentPlayer());
    }

    @Test
    void testTurnDoesNotChangeAfterInvalidMove() {
        assertEquals(GameLogic.Player.RED, game.getCurrentPlayer());

        game.playerMove(-1, 0, 'S'); // invalid move (out of bounds)

        assertEquals(GameLogic.Player.RED, game.getCurrentPlayer());
    }
}
