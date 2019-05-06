/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    public static Player getPlayer(Ships ship, GameManager game){
        Player player;
        
        switch(ship){
            case Hammer:
                player = new HammerHead(0,0, game);
                break;
            case Aphelion:
                player = new Aphelion(0,0, game);
                break;
            case Bartleby:
                player = new Bartleby(0,0, game);
                break;
            case Sekul:
                player = new SekulIX(0,0, game);
                break;
            default:
                player = new HammerHead(0,0, game);
                break;
        }
        
        return player;
    }
}
