package game.objects.ships.sekul;

import engine2D.GameContainer;
import game.GameManager;
import game.objects.Player;

/**
 *
 * @author
 */
public class SekulIX extends Player {
    
    public SekulIX(int x, int y, GameManager gm) {
        super(x, y);
    }

    @Override
    protected void abilitiesCode(GameContainer gc, GameManager gm, float dt) {
        if(gc.getInput().isButtonDown(gc.getConfig().getPrimaryFire()) && cds[0] <= 0 ) {
            cds[0] = 0;
        }
        if(gc.getInput().isButton(gc.getConfig().getSecondaryFire()) && cds[1] <=0 ) {
            cds[1] = 0;
        }
        if(gc.getInput().isKey(gc.getConfig().getKeyHability1()) && cds[2] <=0 ) {
            cds[2] = 0;
        }
        if(gc.getInput().isKeyDown(gc.getConfig().getKeyHability2()) && cds[3] <=0 ) {
            cds[3] = 0;
        }  
    }
}
