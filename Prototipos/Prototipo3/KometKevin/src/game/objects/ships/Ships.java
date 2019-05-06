package game.objects.ships;

/**
 *
 * @author Project Kevin
 */
public enum Ships {
    /**
     *
     */
    Hammer("/ships/hammerShip.png", 120, 64,
            "resources/menu/backgroundHammer.png"),
    
    Aphelion("/ships/aphelionShip.png", 90, 92,
            "resources/menu/backgroundAphelion.png"),
    
    Bartleby("/ships/bartlebyShip.png", 56, 48,
            "resources/menu/backgroundBartleby.png"),
    
    Sekul("/ships/bartlebyShip.png", 90, 92,
            "resources/menu/backgroundSekul.png");
    
    private final String sprite;
    private final int sizeX, sizeY;
    private final String selectMenu;
    
    Ships(String sprite, int sizeX, int sizeY, String selectMenu){
        this.sprite = sprite;
        this.sizeX = sizeX; 
        this.sizeY = sizeY;
        this.selectMenu = selectMenu;
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

    public String getSelectMenu() {
        return selectMenu;
    }
    
}
