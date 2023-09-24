package gol.model;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("FieldCanBeLocal")
public class World {
    private final Set<Cell> liveCells;
    private final LongSummaryStatistics xSummary;
    private final LongSummaryStatistics ySummary;
    private final GameOfLife gol;
    private final long generation;

    private final long timeToComputeLastGeneration;

    public World(Set<Cell> cells) {
        this(cells, new ClassicGameOfLife(), 0L, 0L);
    }

    public World(Set<Cell> liveCells, GameOfLife gol) {
        this(liveCells, gol, 0L, 0L);
    }

    public World(Set<Cell> liveCells, GameOfLife gol, long generation, long timeToComputeLastGeneration) {
        this.liveCells = new HashSet<>(liveCells);
        this.generation = generation;
        this.timeToComputeLastGeneration = timeToComputeLastGeneration;
        this.xSummary = this.liveCells.stream().mapToLong(Cell::x).summaryStatistics();
        this.ySummary = this.liveCells.stream().mapToLong(Cell::y).summaryStatistics();
        this.gol = gol;
    }

    public World shiftNegativeToZero() {
        return this.shiftBy(-xSummary.getMin(), -ySummary.getMin());
    }

    public long getTimeToComputeLastGeneration() {
        return timeToComputeLastGeneration;
    }

    public World shiftBy(long x, long y) {
        return new World(this.liveCells.stream()
                .map(c -> Cell.cell(c.x() + x, c.y() + y))
                .collect(Collectors.toSet())
        );

    }


    public boolean isAlive(int i, int j) {
        return this.liveCells.contains(Cell.cell(i,j));
    }

    public long getLiveCellsCount() {
        return this.liveCells.size();
    }

    public long getGeneration() {
        return this.generation;
    }

    public World tick() {
        long start = System.currentTimeMillis();
        final Set<Cell> liveCells = gol.nextGeneration(this.liveCells);
        return new World(liveCells, gol, generation+1,
                System.currentTimeMillis() - start);
    }

}
