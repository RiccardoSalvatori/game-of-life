package gol.model;

import java.util.List;

import static gol.model.Cell.cell;
import static util.CollectionUtils.listOf;

public class ClassicGameOfLife implements GameOfLife {
    @Override
    public CellState nextState(CellState current, long numberOfAliveNeighbours) {
        switch (current) {
            case ALIVE:
                if (numberOfAliveNeighbours < 2) return CellState.DEAD;
                if (numberOfAliveNeighbours <= 3) return CellState.ALIVE;
                return CellState.DEAD;
            case DEAD:
                if (numberOfAliveNeighbours == 3) return CellState.ALIVE;
                return CellState.DEAD;
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    public List<Cell> signals(Cell cell) {
        return listOf(
                cell(cell.x() - 1, cell.y() - 1), cell(cell.x(), cell.y() - 1), cell(cell.x() + 1, cell.y() - 1),
                cell(cell.x() - 1, cell.y()), cell(cell.x() + 1, cell.y()),
                cell(cell.x() - 1, cell.y() + 1), cell(cell.x(), cell.y() + 1), cell(cell.x() + 1, cell.y() + 1)
        );
    }

}
