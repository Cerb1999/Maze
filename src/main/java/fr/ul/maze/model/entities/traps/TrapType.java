package fr.ul.maze.model.entities.traps;

public enum TrapType {
    SPIKES,
    HOLE,
    WOLFTRAP;

    public String toLowerCase(){
        return name().toLowerCase();
    }
}
