package gol;

import gol.model.Cell;
import gol.model.ClassicGameOfLife;
import gol.model.World;
import gol.view.GoLFrame;
import gol.view.GoLView;

import java.util.*;

import static gol.model.Cell.cell;

public class Main {
    public static void main(String[] args) {
        GoLView view = new GoLFrame();
        World world = new World(randomCells(), new ClassicGameOfLife());
        while (true) {
            view.draw(world);
            world = world.tick();
            sleep();
        }
    }

    private static Set<Cell> randomCells() {
        Set<Cell> currentState = new HashSet<>();
        final Random rnd = new Random();
        for (int i = 0; i < 2000; i++) {
            currentState.add(cell(rnd.nextInt(100), rnd.nextInt(100)));
        }
        return currentState;
    }

    private static void sleep() {
        try {
            Thread.sleep(90);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }


}
