package fr.ul.maze.controller;

import java.util.ArrayDeque;
import java.util.Deque;

public final class Box2DTaskQueue {
    private static final Deque<Runnable> queue = new ArrayDeque<>();

    public static synchronized Deque<Runnable> getQueue() {
        return queue;
    }
}
