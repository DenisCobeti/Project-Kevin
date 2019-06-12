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
            "resources/menu/backgroundHammer.png"),
    
    Aphelion("/ships/aphelionShip.png", 90, 92, "Aphelion",
            "resources/menu/backgroundAphelion.png"),
    
    Bartleby("/ships/bartlebyShip.png", 56, 48, "Bartleby",
            "resources/menu/backgroundBartleby.png"),
    
    Sekul("/ships/sekulShip.png", 76, 58, "Sekul", 
            "resources/menu/backgroundSekul.png");
    
    private final String sprite, name;
    private final int sizeX, sizeY;
    private final String selectMenu;
    
    Ships(String sprite, int sizeX, int sizeY,String name, String selectMenu){
        this.sprite = sprite;
        this.sizeX = sizeX; 
        this.sizeY = sizeY;
        this.name = name;
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

    public String getName() {
        return name;
    }
    
    public String getSelectMenu() {
        return selectMenu;
    }
    
}
