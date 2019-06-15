package game.objects;

import gfx.Image;

/**
 * Enumerado que establece los tipos de asteroides
 * 
 * @author Denis y Arturo
 */
public enum AsteroidType {
    
    Little (0,  1, 400, 0, 2,  new Image("/space/asteroidL.png")),
    Big    (1,  4, 200, 2, 8,  new Image("/space/asteroidB.png")),
    Kevin  (2, 20, 2,   3, 50, new Image("/space/asteroidK.png"));
    
    private final int size;     // identificador
    private final int health;   // salud maxima
    private final int max;      // maximo de asteroides en partida
    private final int divides;  // fragmentos del subtipo en los que se divide
    private final int points;   // puntos que da destruir uno de estos
    private final Image img;    // imagen precargada en memoria
    
    AsteroidType(int num, int health, int max, int divides, int points, Image img){
        this.size = num;
        this.health = health;
        this.max = max;
        this.divides = divides;
        this.points = points;
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

    public int getPoints() {
        return points;
    }
    
    public Image getImg() {
        return img;
    }
}
