package game.objects;

/**
 *
 * @author Ramiro
 */
public enum PowerUpType {
    SCORE, LIFE, VELOCITY, CD1, CD2, CD3;
    
    public static PowerUpType getRandom(){
        return PowerUpType.values()[(int)(Math.random()*
                                    PowerUpType.values().length)];
    }

}
