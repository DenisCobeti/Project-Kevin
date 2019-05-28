/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.objects;

import game.AsteroidManager;
import game.GameManager;
import gfx.Image;

/**
 *
 * @author alumno
 */
public class PowerUpFactory {
    
    private static Image lifeImage = new Image("/powerup/Airless.png");
    private static Image defaultImage = new Image("/powerup/Airless.png");
    
    private static double lifeValue = 5;

    public static PowerUp getPowerUp(PowerUp.PowerUpType type, int x, int y, GameManager gm){
        PowerUp powerUp;
        
        powerUp = new PowerUp(x,y,type,getValue(type),getImage(type),gm);
        
        return powerUp;
    }
    
    public static Image getImage(PowerUp.PowerUpType type){
        switch(type){
            case LIFE:
                return lifeImage;
            default:
                return defaultImage;
        }
    }
    
    public static double getValue(PowerUp.PowerUpType type){
        switch(type){
            case LIFE:
                return lifeValue;
            default:
                return 0;
        }
    }

}
