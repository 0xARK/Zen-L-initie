package zen;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PawnTest {

    Pawn p;

    @BeforeEach
    void setUp() {
        this.p = new Pawn(0, 0, PawnType.BLACK);
    }

    @AfterEach
    void tearDown() {
        this.p = null;
    }

    @Test
    void setX() {

        this.p.setX(5);
        int x = this.p.getX();

        assertEquals(5, x, "La valeur de x n'a pas été changée correctement");

    }

    @Test
    void setY() {

        this.p.setY(3);
        int y = this.p.getY();

        assertEquals(3, y, "La valeur de y n'a pas été changée correctement");

    }
}
