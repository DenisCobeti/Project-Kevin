package game.objects;

/**
 *
 * @author Ramiro
 */
public enum PowerUpType {
    SCORE, LIFE, VELOCITY, CD0, CD1, CD2, CD3;

    public static int getNum(PowerUpType type){
        switch(type){
            case SCORE: return 0;
            case LIFE: return 1;
            case VELOCITY: return 2;
            case CD0: return 3;
            case CD1: return 4;
            case CD2: return 5;
            case CD3: return 6;
        }
        return 0;
    }
    
    public static PowerUpType getRandom(){
        return PowerUpType.values()[(int)(Math.random()*
                                    PowerUpType.values().length)];
    }

}
