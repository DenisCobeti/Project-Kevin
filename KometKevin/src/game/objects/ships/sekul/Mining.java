package game.objects.ships.sekul;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.objects.GameObject;
import game.objects.Player;
import java.util.LinkedList;

/**
 *
 * @author Ramiro
 */
public class Mining extends GameObject{
    
    private boolean active = false;
    
    private final int MINES_EXPLOSION_RANGE = 100;
    
    private LinkedList<Mine> mines;
    private int minesLimit;
    private Player owner;
    private GameManager gm;
    private double cd;

    public Mining(Player owner, int minesLimit, GameManager gm) {
        this.owner = owner;
        this.mines = new LinkedList<>();
        this.minesLimit = minesLimit;
        this.gm = gm;
        //gm.getObjects().add(this);
    }

    public double activate() {
        if (mines.size()<minesLimit){
            //active = true;
            //this.cd = cd;
            mines.add(new Mine(this, MINES_EXPLOSION_RANGE));
            gm.getObjects().add(mines.getLast());
            //System.out.println("Creando Mina");
        }else {
            //active = true;
            //this.cd = cd;
            mines.getFirst().activate();
            mines.add(new Mine(this, MINES_EXPLOSION_RANGE));
            gm.getObjects().add(mines.getLast());
            //System.out.println("Creando Mina y Eliminando 1a");
        }
        return 0.0;
    }
    

    public void destroyMine(Mine mine){
        if (mines.contains(mine)){
            mines.remove(mines.get(mines.indexOf(mine)));
            gm.getObjects().remove(mine);
        }
    }
    
    public Player getOwner(){
        return owner;
    }
    
    public GameManager getGM(){
        return gm;
    }
    
    @Override
    public void render(GameContainer gc, Renderer r) {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void effect(GameObject go) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
