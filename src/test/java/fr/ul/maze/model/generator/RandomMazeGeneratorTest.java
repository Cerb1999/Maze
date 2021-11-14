package fr.ul.maze.model.generator;

import fr.ul.maze.model.Cell;
import fr.ul.maze.model.Level;
import fr.ul.maze.model.PathCell;
import fr.ul.maze.model.WallCell;

public class RandomMazeGeneratorTest {
    public static void main(String[] args) {
        checkMazeValidity();
    }

    private static void checkMazeValidity() {
        AbstractMazeGenerator gen = new RandomMazeGenerator();
        Level lvl = gen.generateMaze(1);

        int lineLength = 0;

        System.out.print("\n\n\033[40;1m \033[0m");
        for (Cell cell : lvl) {
            // NOTE: This is ugly but only used for simplicity's sake
            if (cell instanceof WallCell) {
                System.out.print("\033[40;1m \033[0m");
            } else if (cell instanceof PathCell) {
                System.out.print(" ");
            }

            if (++lineLength >= Level.WIDTH) {
                lineLength = 0;
                System.out.print("\033[40;1m \033[0m\n\033[40;1m \033[0m");
            }
        }

        System.out.println("\033[2K\n\n");
    }
}
