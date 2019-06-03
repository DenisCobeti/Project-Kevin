package game.objects;

import gfx.Image;

/**
 * Enumerado que establece los tipos de asteroides
 * @author Neblis
 */
public enum AsteroidType {
    
    Silice  (0,  1, 80, new Image("/space/asteroidL.png")), 
    Carbon  (1,  4, 20, new Image("/space/asteroidB.png")),  
    Metal   (2, 20,  2, new Image("/space/asteroidK.png")),
    Vestoid (0,0,0,null),
    Organic (0,0,0,null);
    
    private final int num;
    private final int health;
    private final int max;
    private final Image img;
    
    AsteroidType(int num, int health, int max, Image img){
        this.num = num;
        this.health = health;
        this.max = max;
        this.img = img;
    }
    
    public int getNum(AsteroidType type){
        return num;
    }

    public int getHealth() {
        return health;
    }

    public int getMax() {
        return max;
    }

    public Image getImg() {
        return img;
    }
    
}
