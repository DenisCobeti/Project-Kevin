package game.objects.ships.sekul;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.objects.GameObject;
import game.objects.Player;
import java.util.LinkedList;

/**
 *
 * @author alumno
 */
public class Mining extends GameObject{
    
    private boolean active = false;
    
    private final int MINES_EXPLOSION_RANGE = 200;
    
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

    public double activate(double cd) {
        if (active){
            return 0;
        }
        else if (mines.size()<minesLimit){
            active = true;
            this.cd = cd;
            mines.add(new Mine(this, MINES_EXPLOSION_RANGE));
            gm.getObjects().add(mines.getLast());
            System.out.println("Creando Mina");
        }else{
            active = true;
            this.cd = cd;
            mines.getFirst().effect(this);
            mines.add(new Mine(this, MINES_EXPLOSION_RANGE));
            gm.getObjects().add(mines.getLast());
            System.out.println("Creando Mina y Eliminando 1a");
        }
        return 0.0;
    }
    
    @Override
    public void update(GameContainer gc, GameManager gm, float dt){  
        cd -= dt;
        System.out.println(cd);
        if(cd <= 0){
            active = false;
        }
                
    }

    public void destroyMine(Mine mine){
        if (mines.contains(mine)){
            mines.remove(mines.get(mines.indexOf(mine)));
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
