package fr.ul.maze;

import fr.ul.maze.controller.Direction;
import fr.ul.maze.controller.HeroMoveController;
import fr.ul.maze.model.*;
import fr.ul.maze.model.generator.RandomMazeGenerator;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args) {
        new Main().gameLoop();
    }

    private final AtomicReference<Level> level;
    private final HeroMoveController moveCntrl;

    private static final Scanner in = new Scanner(System.in);

    private Main() {
        this.level = new AtomicReference<>(new RandomMazeGenerator().generateMaze());
        this.moveCntrl = new HeroMoveController(level);
    }

    private void gameLoop() {
        boolean shouldContinue = true;

        while (shouldContinue) {
            showMaze();
            shouldContinue = menu();
        }
    }

    private boolean menu() {
        System.out.println(
                "Main menu (Level " + level.get().getNumber() + "):\n" +
                "1. Move up\n" +
                "2. Move down\n" +
                "3. Move left\n" +
                "4. Move right\n" +
                "0. Quit\n");

        menuLoop: while (true) {
            char c = in.next().charAt(0);
            switch (c) {
                case '1': {
                    this.moveCntrl.moveHero(Direction.UP);
                    break menuLoop;
                }
                case '2': {
                    this.moveCntrl.moveHero(Direction.DOWN);
                    break menuLoop;
                }
                case '3': {
                    this.moveCntrl.moveHero(Direction.LEFT);
                    break menuLoop;
                }
                case '4': {
                    this.moveCntrl.moveHero(Direction.RIGHT);
                    break menuLoop;
                }
                case '0':
                    return false;
                default: {
                    System.out.println("Unknown command \"" + c + "\"");
                }
            }
        }

        return true;
    }

    private void showMaze() {
        this.level.updateAndGet(level -> {
            int currentLine = 0;
            int currentLineLength = 0;

            System.out.println("\033[2J");
            for (Cell c : level) {
                // NOTE: This instancepf chain is ugly

                String cellContent = " "; // nothing
                String cellColor = ""; // none
                if (c instanceof LadderCell) {
                    cellContent = "H";
                    cellColor = "\033[33m";
                } else if (level.getHeroPosition().equals(new Position(currentLineLength, currentLine))) {
                    cellContent = "\uD83E\uDFC5";
                    cellColor = "\033[32m";

                    //System.err.printf("encountered hero at %d-%d", currentLineLength, currentLine);
                }
                if (c instanceof PathCell) {
                    System.out.printf("%s%s\033[0m", cellColor, cellContent);
                } else if (c instanceof WallCell) {
                    System.out.print("\033[40;1m \033[0m");
                } else {
                    System.out.printf("%s%s\033[0m", cellColor, cellContent);
                }

                if (++currentLineLength >= Level.WIDTH) {
                    currentLine++;
                    currentLineLength = 0;
                    System.out.println();
                }
            }

            return level;
        });
    }
}
