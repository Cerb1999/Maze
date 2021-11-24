package fr.ul.maze;

import com.badlogic.gdx.ApplicationLogger;

/**
 * A colored Gdx logger which logs to {@link System#err}.
 *
 * <ul>
 * <li>Log messages are colored in Blue</li>
 * <li>Debug messages are colored in Magenta</li>
 * <li>Error messages are colored in Red</li>
 * </ul>
 *
 * @apiNote This uses ANSI color codes, which should now be supported on all major
 * modern OSes (Linux, macOS and Windows).
 * In case your terminal does not support ANSI color codes, the output should contain
 * junk characters (e.g. {@code ☐[32m}).
 */
public class ColoredStderrLogger implements ApplicationLogger {
    public ColoredStderrLogger() {

    }

    @Override
    public void log(String s, String s1) {
        System.err.printf("\u001B[32m[LOG   %s]\u001B[0m %s%n", s, s1);
    }

    @Override
    public void log(String s, String s1, Throwable throwable) {
        System.err.printf("\u001B[32m[LOG   %s]\u001B[0m %s%n\t%s%n", s, s1, throwable);
    }

    @Override
    public void error(String s, String s1) {
        System.err.printf("\u001B[31m[ERROR %s]\u001B[0m %s%n", s, s1);
    }

    @Override
    public void error(String s, String s1, Throwable throwable) {
        System.err.printf("\u001B[31m[ERROR %s]\u001B[0m %s%n\t%s%n", s, s1, throwable);
    }

    @Override
    public void debug(String s, String s1) {
        System.err.printf("\u001B[36m[DEBUG %s]\u001B[0m %s%n", s, s1);
    }

    @Override
    public void debug(String s, String s1, Throwable throwable) {
        System.err.printf("\u001B[36m[DEBUG %s]\u001B[0m %s%n\t%s%n", s, s1, throwable);
    }
}
