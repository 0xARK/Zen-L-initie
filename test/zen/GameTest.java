package zen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game g;

    @BeforeEach
    void setUp() {
        this.g = new Game("p1", "p2", true, true);
    }

    @AfterEach
    void tearDown() {
        this.g = null;
    }

    @Test
    void changePlayer() {

        boolean saved = this.g.saveMyGame();

        assertTrue(saved, "Game - saveMyGame() : La partie n'est pas bien sauvegardée");

    }
}