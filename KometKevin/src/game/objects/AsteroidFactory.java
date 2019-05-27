package game.objects;

import game.AsteroidManager;
import gfx.Image;

/**
 *
 * @author alumno
 */

public abstract class AsteroidFactory {
    
    private static Image[] siliceImage = {new Image("/space/asteroid.png"),new Image("/space/AsteroidM.png")};
    private static Image[] carbonImage = {new Image("/space/AsteroidM.png")};
    private static Image[] defaultImage = {new Image("/space/asteroid.png")};
    
    private static int[] siliceHealthPoints = {1,4};
    private static int[] carbonHealthPoints = {1,3,5};

    public static Asteroid getAsteroid(AsteroidType type, int x, int y, AsteroidManager am){
        Asteroid asteroid;
        
        switch(type){
            case Silice:
                asteroid = new Asteroid(x,y,2,1,9,
                        siliceHealthPoints[siliceHealthPoints.length-1],type,
                        siliceImage[siliceImage.length-1],am);
                break;
                
            case Carbon:
                asteroid = new Asteroid(x,y,3,1,4,
                        getHealthPoints(type)[getHealthPoints(type).length-1]
                        ,type,getImage(type)[getImage(type).length-1],am);
                break;
                
  
            default:
                asteroid = new Asteroid(x,y,2,1,9,1,type,defaultImage[defaultImage.length-1],am);
                break;
        }
        
        return asteroid;
    }
    
    public static Asteroid getChildAsteroid(Asteroid father, AsteroidManager am){
        Asteroid asteroid;
        
        asteroid = new Asteroid(father, 
                        getHealthPoints(father.getType())[father.getSize()-2],
                        getImage(father.getType())[father.getSize()-2], am);
        
        return asteroid;
    }
    
    public static Image[] getImage(AsteroidType type){
        switch(type){
            case Silice:
                return siliceImage;
            case Carbon:
                return carbonImage;
        }
        return null;
    }
    
    public static int[] getHealthPoints(AsteroidType type){
        switch(type){
            case Silice:
                return siliceHealthPoints;
            case Carbon:
                return carbonHealthPoints;
        }
        return null;
    }
    
}
