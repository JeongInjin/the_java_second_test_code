package me.injin.cleancodetdd.bowlinggame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void canRoll() {
        Game game = new Game();
        game.roll(0);
    }

    private void rollMany(int frames, int pins) {
        for (int i = 0; i < frames; i++)
            game.roll(pins);
    }

    private void rollSpare() {
        game.roll(5);
        game.roll(5);
    }

    private void rollStrike() {
        game.roll(10);
    }

    @Test
    void gutterGame() {
        rollMany(20, 0);
        assertThat(game.getScore()).isEqualTo(0);
    }

    @Test
    void allOnes() {
        rollMany(20, 1);
        assertThat(game.getScore()).isEqualTo(20);
    }

    @Test
    void oneSpare() {
        rollSpare();
        game.roll(3);
        rollMany(17, 0);
        assertThat(game.getScore()).isEqualTo(16);
    }
    @Test
    void oneStrike() {
        rollStrike();
        game.roll(5);
        game.roll(3);
        rollMany(16, 0);
        assertThat(game.getScore()).isEqualTo(26);
    }

    @Test
    void perfectGame() {
        rollMany(10, 10);
        game.roll(10);
        game.roll(10);
        assertThat(game.getScore()).isEqualTo(300);
    }

}


























