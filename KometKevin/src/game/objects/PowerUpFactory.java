package game.objects;

import game.AsteroidManager;
import game.GameManager;
import gfx.Image;

/**
 *
 * @author alumno
 */
public class PowerUpFactory {
    
    private static Image scoreImage = new Image("/powerup/Orb of Venom.png");
    private static Image lifeImage = new Image("/powerup/Airless.png");
    private static Image velocityImage = new Image("/powerup/Flameless.png");
    private static Image cd0Image = new Image("/powerup/Blless.png");
    private static Image cd1Image = new Image("/powerup/Blless.png");
    private static Image cd2Image = new Image("/powerup/Blless.png");
    private static Image cd3Image = new Image("/powerup/Blless.png");
    private static Image defaultImage = new Image("/powerup/Airless.png");
    
    private static double scoreValue = 10;
    private static double lifeValue = 5;
    private static double velocityValue = 0.2;
    private static double cd0Value = 0.05;
    private static double cd1Value = 1;
    private static double cd2Value = 1;
    private static double cd3Value = 1;
    

    public static PowerUp getPowerUp(PowerUpType type, int x, int y, GameManager gm){
        PowerUp powerUp;
        powerUp = new PowerUp(x,y,type,getValue(type),getImage(type),gm);
        
        return powerUp;
    }
    
    
    public static PowerUp getPowerUp(int x, int y, GameManager gm){
        PowerUp powerUp;
        PowerUpType type = PowerUpType.getRandom();
        powerUp = new PowerUp(x,y,type,getValue(type),getImage(type),gm);
        
        return powerUp;
    }
    
    public static Image getImage(PowerUpType type){
        switch(type){
            case SCORE:
                return scoreImage;
            case LIFE:
                return lifeImage;
            case VELOCITY:
                return velocityImage;
            case CD0:
                return cd0Image;
            case CD1:
                return cd1Image;
            case CD2:
                return cd2Image;
            case CD3:
                return cd3Image;
            default:
                return defaultImage;
        }
    }
    
    public static double getValue(PowerUpType type){
        switch(type){
            case SCORE:
                return scoreValue;
            case LIFE:
                return lifeValue;
            case VELOCITY:
                return velocityValue;
            case CD0:
                return cd0Value;
            case CD1:
                return cd1Value;
            case CD2:
                return cd2Value;
            case CD3:
                return cd3Value;
            default:
                return 0;
        }
    }

}
