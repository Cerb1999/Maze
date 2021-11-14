package fr.ul.maze.model.generator;

import fr.ul.maze.model.*;

import java.io.*;
import java.util.LinkedList;
import java.util.Objects;

/**
 * A maze generator whose input is a file containing:
 * - {@code #} is a wall
 * - a simple space is a path
 * - {@code X} is where the hero spawns (there must be only one)
 * - {@code H} is the end ladder
 */
public class SpecialRoomGenerator extends AbstractMazeGenerator {
    private final File mazeFile;

    /**
     * Creates a maze generator from the given file path.
     *
     * @param filePath the file which stores information about the maze
     */
    public SpecialRoomGenerator(String filePath) {
        this.mazeFile = new File(filePath);
    }

    @Override
    public Level generateMaze(int numberLevel) {
        try (
                final FileInputStream fis = new FileInputStream(this.mazeFile);
                final BufferedReader reader = new BufferedReader(new InputStreamReader(fis))
        ) {
            String line;
            int numberOfLines = 0;
            LinkedList<Cell[]> lines = new LinkedList<>();
            Position heroPos = null;

            while (!Objects.isNull(line = reader.readLine())) {
                final int lineNumber = ++numberOfLines;

                assert (line.length() == Level.WIDTH) : "Inconsistent file size: line " + lineNumber + " should have " + Level.WIDTH + " characters";

                int charNumber = 0;
                LinkedList<Cell> currentLine = new LinkedList<>();
                for (char c : line.toCharArray()) {
                    switch (c) {
                        case '#': {
                            currentLine.addLast(new WallCell());
                            break;
                        }
                        case ' ': {
                            currentLine.addLast(new PathCell());
                            break;
                        }
                        case 'H': {
                            currentLine.addLast(new LadderCell());
                            break;
                        }
                        case 'X': {
                            assert Objects.isNull(heroPos) : "there can be only one 'X'";
                            heroPos = new Position(charNumber, lineNumber);
                            currentLine.addLast(new PathCell());
                            break;
                        }
                        default:
                            assert false : "Unknown cell type \"" + c + "\" encountered at line " + lineNumber;
                    }

                    charNumber++;
                }

                lines.addLast(currentLine.toArray(new Cell[0]));
            }

            assert (numberOfLines == Level.HEIGHT) : "inconsistent file size: should contain " + Level.HEIGHT + " lines";

            return new Level(lines.toArray(new Cell[0][]), Objects.requireNonNull(heroPos, "there must be a starting positon for the hero"));
        } catch (IOException exn) {
            exn.printStackTrace();

            assert false : "Failure to generate a maze due to previous exception";
            return null;
        }
    }
}
