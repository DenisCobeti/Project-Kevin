package game.objects.ships;

import game.GameManager;
import game.objects.Player;
import game.objects.ships.aphelion.Aphelion;
import game.objects.ships.bartleby.Bartleby;
import game.objects.ships.sekul.SekulIX;
import game.objects.ships.hammer.HammerHead;

/**
 *
 * @author Denis Florin Cobeti
 */
public class ShipFactory {
    
    private final static int START_POSITION = 500; 
    public static Player getPlayer(Ships ship, GameManager game){
        Player player;
        
        switch(ship){
            case Hammer:
                player = new HammerHead(START_POSITION,START_POSITION, game);
                break;
            case Aphelion:
                player = new Aphelion(START_POSITION,START_POSITION, game);
                break;
            case Bartleby:
                player = new Bartleby(START_POSITION,START_POSITION, game);
                break;
            case Sekul:
                player = new SekulIX(START_POSITION,START_POSITION, game);
                break;
            default:
                player = new HammerHead(START_POSITION,START_POSITION, game);
                break;
        }
        
        return player;
    }
}
