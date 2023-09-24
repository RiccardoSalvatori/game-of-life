package gol.view;

import gol.model.Cell;
import gol.model.World;

import java.util.List;

import static gol.model.Cell.cell;

public class ConsoleView implements GoLView {
    @Override
    public void draw(World liveCells) {
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (liveCells.isAlive(x, y)) {
                    System.out.print("[O]");
                } else {
                    System.out.print("[-]");
                }
            }
            System.out.println();
        }
    }
}
