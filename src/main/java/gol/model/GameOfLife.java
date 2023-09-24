package gol.model;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;

public interface GameOfLife {

    CellState nextState(CellState current, long numberOfAliveNeighbours);

    List<Cell> signals(Cell cell);


    default List<Cell> allSignals(Set<Cell> cells) {
        return cells.stream().flatMap(c -> signals(c).stream()).collect(Collectors.toList());
    }


    default Map<Cell, Long> countSignals(List<Cell> signals) {
        return signals.stream().collect(Collectors.groupingBy(c -> c, counting()));
    }


    default Set<Cell> nextGeneration(Set<Cell> liveCells) {
        return countSignals(allSignals(liveCells))
                .entrySet().stream()
                .filter(e -> {
                    CellState nextState;
                    if(liveCells.contains(e.getKey())) {
                        nextState = nextState(CellState.ALIVE, e.getValue());
                    } else {
                        nextState = nextState(CellState.DEAD, e.getValue());
                    }
                    return CellState.ALIVE.equals(nextState);
                })
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
