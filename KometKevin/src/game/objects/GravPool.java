package game.objects;

import engine2D.Config;
import engine2D.GameContainer;
import engine2D.Renderer;
import game.colliders.CircleCollider;
import game.Vector2;

/**
 * Pozo gravitatorio
 * 
 * @author Arturo
 */
public class GravPool extends GameObject{
    private double gravity = 100;
    
    public GravPool(int x, int y) {
        this.tag = "sun";
        
        this.position = new Vector2(x, y);
        this.center = new Vector2(x + width/2, y + + height/2);
        this.velocity = new Vector2(0,0);
        
        collCode = CollisionCodes.GRAVPOOL.getValue();
        collides = CollisionCodes.GRAVPOOL_COL.getValue();
        this.collider = new CircleCollider(this, Config.getScreenWidth()/2);
    }
    
    @Override
    public boolean isDead() {
        // La gravedad no puede ser destruida
        return false;
    }
    
    @Override
    public void render(GameContainer gc, Renderer r) {
        
    }

    @Override
    public void effect(GameObject go) {
        Vector2 distance = this.center.getSubtracted(go.center);
        if ((go.collCode & CollisionCodes.TEAMS.getValue()) > 0) {
            go.velocity.add(Vector2.toCartesian(1/distance.getLength() * gravity / 5, distance.getAngle()));
        } else {
            go.velocity.add(Vector2.toCartesian(1/distance.getLength() * gravity, distance.getAngle()));
        }
        if (400 >= distance.getLength()) go.setDispose(true);
    }
}
