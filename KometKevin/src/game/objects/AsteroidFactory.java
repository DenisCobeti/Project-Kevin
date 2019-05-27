package game.objects;

import game.AsteroidManager;
import gfx.Image;

/**
 *
 * @author alumno
 */

public abstract class AsteroidFactory {
    
    private static Image siliceImage = new Image("/space/asteroid.png");
    private static Image carbonImage = new Image("/space/sun.png");
    private static Image defaultImage = new Image("/space/asteroid.png");

    public static Asteroid getAsteroid(AsteroidType type, int x, int y, AsteroidManager am){
        Asteroid asteroid;
        
        switch(type){
            case Silice:
                asteroid = new Asteroid(x,y,2,1,9,type,siliceImage,am);
                break;
                
            case Carbon:
                asteroid = new Asteroid(x,y,3,1,5,type,carbonImage,am);
                break;
                
  
            default:
                asteroid = new Asteroid(x,y,2,1,9,type,defaultImage,am);
                break;
        }
        
        return asteroid;
    }
    
    public static Asteroid getChildAsteroid(Asteroid father, Image image, AsteroidManager am){
        Asteroid asteroid;
        
        asteroid = new Asteroid(father, image, am);
        
        return asteroid;
    }
    
}
