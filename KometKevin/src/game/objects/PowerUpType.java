package game.objects;

import gfx.Image;

/**
 *
 * @author Ramiro
 */

public enum PowerUpType{

    SCORE    (10,  new Image("/powerup/Orb of Venom.png")),
    LIFE     (5,   new Image("/powerup/Airless.png")), 
    VELOCITY (0.2, new Image("/powerup/Flameless.png")),
    CD1      (1,   new Image("/powerup/Blless.png")),
    CD2      (1,   new Image("/powerup/Orb of Blood.png")),
    CD3      (1,   new Image("/powerup/Orb of Frost.png"));
    
    private final double value;
    private final Image image;

    PowerUpType (double value, Image image){
        this.value = value;
        this.image = image;
    }

    public double getValue(){
        return value;
    }
    
    public Image getImage(){
        return image;
    }
}

