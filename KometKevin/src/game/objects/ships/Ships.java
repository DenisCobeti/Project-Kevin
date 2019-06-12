package game.objects.ships;

/**
 *
 * @author Denis
 */
public enum Ships {
    /**
     *
     */
    Hammer("/ships/hammerShip.png", 120, 64, "Hammer",
            "resources/menu/backgroundHammer.png", 10),
    
    Aphelion("/ships/aphelionShip.png", 90, 92, "Aphelion",
            "resources/menu/backgroundAphelion.png", 5),
    
    Bartleby("/ships/bartlebyShip.png", 56, 48, "Bartleby",
            "resources/menu/backgroundBartleby.png", 0),
    
    Sekul("/ships/sekulShip.png", 76, 58, "Sekul", 
            "resources/menu/backgroundSekul.png", 0);
    
    private final String sprite, name;
    private final int sizeX, sizeY;
    private final String selectMenu;
    private final double health;
    
    Ships(String sprite, int sizeX, int sizeY, String name, String selectMenu, 
                double health){
        this.sprite = sprite;
        this.sizeX = sizeX; 
        this.sizeY = sizeY;
        this.name = name;
        this.selectMenu = selectMenu;
        this.health = health;
    }

    public String getSprite() {
        return sprite;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public String getName() {
        return name;
    }

    public double getHealth() {
        return health;
    }
    
    public String getSelectMenu() {
        return selectMenu;
    }
    
}
