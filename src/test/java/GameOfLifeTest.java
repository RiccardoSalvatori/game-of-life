import gol.model.Cell;
import gol.model.ClassicGameOfLife;
import gol.model.GameOfLife;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static gol.model.Cell.cell;
import static gol.model.CellState.ALIVE;
import static gol.model.CellState.DEAD;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static util.CollectionUtils.setOf;

class GameOfLifeTest {
    private final GameOfLife gol = new ClassicGameOfLife();
@Test
    void testRules() {
        assertAll(
                () -> assertEquals(DEAD, gol.nextState(ALIVE, 0)),
                () -> assertEquals(DEAD, gol.nextState(ALIVE, 1)),
                () -> assertEquals(ALIVE, gol.nextState(ALIVE, 2)),
                () -> assertEquals(ALIVE, gol.nextState(ALIVE, 3)),
                () -> assertEquals(DEAD, gol.nextState(ALIVE, 4)),
                () -> assertEquals(DEAD, gol.nextState(ALIVE, 99))
        );
    }

    @Test
    void testOscillator() {
        final Set<Cell> vertical = setOf(
                cell(3, 2),
                cell(3, 3),
                cell(3, 4)
        );
        final Set<Cell> horizontal = setOf(
                cell(2, 3), cell(3, 3), cell(4, 3)
        );
        assertAll(
                () -> assertEquals(vertical, gol.nextGeneration(horizontal)),
                () -> assertEquals(horizontal, gol.nextGeneration(vertical)),
                () -> assertEquals(horizontal, gol.nextGeneration(gol.nextGeneration(horizontal))),
                () -> assertEquals(vertical, gol.nextGeneration(gol.nextGeneration(vertical)))
        );
    }
}