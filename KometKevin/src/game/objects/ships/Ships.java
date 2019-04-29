package game.objects.ships;

/**
 *
 * @author Project Kevin
 */
public enum Ships {
    /**
     *
     */
    Hammer("/ships/hammerShip.png", 120, 64),
    Aphelion("resources/ships/aphelionShip.png", 90, 92),
    Bartleby("resources/ships/bartlebyShip.png", 56, 48),
    Sekul("resources/ships/bartlebyShip.png", 90, 92);
    
    private String sprite;
    private int sizeX, sizeY;
    
    Ships(String sprite, int sizeX, int sizeY){
        this.sprite = sprite;
        this.sizeX = sizeX; 
        this.sizeY = sizeY;
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

    
}
