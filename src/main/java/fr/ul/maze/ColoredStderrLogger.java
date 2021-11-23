package fr.ul.maze;

import com.badlogic.gdx.ApplicationLogger;

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
