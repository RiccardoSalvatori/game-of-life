package gol.model;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("FieldCanBeLocal")
public class World {
    private final Set<Cell> liveCells;
    private final GameOfLife gol;
    private final long generation;
    private final long timeToComputeLastGeneration;

    public World(Set<Cell> cells) {
        this(cells, new ClassicGameOfLife(), 0L, 0L);
    }

    public World(Set<Cell> liveCells, GameOfLife gol) {
        this(liveCells, gol, 0L, 0L);
    }

    public World(Set<Cell> liveCells,
                 GameOfLife gol,
                 long generation,
                 long timeToComputeLastGeneration) {
        this.liveCells = new HashSet<>(liveCells);
        this.generation = generation;
        this.timeToComputeLastGeneration = timeToComputeLastGeneration;
        this.gol = gol;
    }

    public long getTimeToComputeLastGeneration() {
        return timeToComputeLastGeneration;
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
