package gol.model;

import java.util.Objects;

public final class Cell {
    private final long x;
    private final long y;

    public Cell(long x, long y) {
        this.x = x;
        this.y = y;
    }

    public static Cell cell(long x, long y) {
        return new Cell(x, y);
    }

    public long x() {
        return x;
    }

    public long y() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Cell that = (Cell) obj;
        return this.x == that.x &&
                this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "["+ x + ", " + y + ']';
    }


}
