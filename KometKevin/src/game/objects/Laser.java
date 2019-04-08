package game.objects;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.colliders.RayCollider;
import game.Vector2;

/**
 *
 * @author
 */
public class Laser extends GameObject {
    private Vector2 hitPoint = new Vector2(0,0);
    
    public Laser(Player support) {
        this.tag = "Laserrrrr";
        
        this.position = support.position;
        this.center = support.center;
        this.aiming = support.aiming;
        
        collCode = CollisionCodes.FIRE1.getValue();
        collides = CollisionCodes.FIRE1_COL.getValue();
        collides &= ~CollisionCodes.GRAVPOOL.getValue(); // Se desactiva la colision con el pozo gravitatorio
        this.collider = new RayCollider(this);
    }
    
    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        GameObject closer = null;
        GameObject aux = null;
        
        hitPoint = Vector2.toCartesian(9999, aiming.getAngle()).getAdded(center);
        while (!collisions.empty()) {
            aux = collisions.pop();
            if (closer == null)
                closer = aux;
            else if (aux.center.distance(center) < closer.center.distance(center)) {
                closer = aux;
            }
            hitPoint = center.getSubtracted(closer.center);
            hitPoint = Vector2.toCartesian(hitPoint.getLength(), aiming.getAngle()).getAdded(center);
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawLine((int)center.x, (int)center.y,(int)hitPoint.x, (int)hitPoint.y, 0xffff0000);
    }
    
}
