package fr.ul.maze.model.entities.items;

public enum ItemType {
    LADDER,
    KEY,
    LIFEUP,
    SLOWHERO,
    SLOWMOB,
    SPEEDMOB,
    NOATTACK,
    ;

    public String toLowerCase(){
        return name().toLowerCase();
    }
    public String toLowerCaseButFirst(){
        return name().charAt(0) + name().toLowerCase();
    }
}
