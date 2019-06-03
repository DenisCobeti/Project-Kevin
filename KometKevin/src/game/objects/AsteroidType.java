package game.objects;

import gfx.Image;

/**
 * Enumerado que establece los tipos de asteroides
 * @author Komet Kevin
 */
public enum AsteroidType {
    
    Little (0,  1, 40, 0, new Image("/space/asteroidL.png")), 
    Big    (1,  4, 4, 2, new Image("/space/asteroidB.png")),  
    Kevin  (2, 20,  1, 3, new Image("/space/asteroidK.png"));
    
    private final int size;     // identificador
    private final int health;   // salud maxima
    private final int max;      // maximo de asteroides en partida
    private final int divides;  // fragmentos del subtipo en los que se divide
    private final Image img;    // imagen precargada en memoria
    
    AsteroidType(int num, int health, int max, int divides, Image img){
        this.size = num;
        this.health = health;
        this.max = max;
        this.divides = divides;
        this.img = img;
    }
    
    public AsteroidType getSubtype() {
        if (size < 1) {
            return this;
        }
        return AsteroidType.values()[size - 1];
    }
    
    // Getters
    public int getSize(){
        return size;
    }

    public int getHealth() {
        return health;
    }

    public int getMax() {
        return max;
    }

    public int getDivides() {
        return divides;
    }

    public Image getImg() {
        return img;
    }
    
}
