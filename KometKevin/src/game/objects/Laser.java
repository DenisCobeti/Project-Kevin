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
    private GameObject closer = null;
    private double damage = 0;
    
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
        hitPoint = Vector2.toCartesian(9999, aiming.getAngle()).getAdded(center);
        closer = null;
        // El laser no puede ser destruido, no se llama a super update
        while(!collisions.empty()) {
            effect(collisions.pop());
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawLine((int)center.x, (int)center.y,(int)hitPoint.x, (int)hitPoint.y, 0xffff0000);
    }

    @Override
    public void effect(GameObject go) {
        if (closer == null)
            closer = go;
        else if (go.center.distance(center) < closer.center.distance(center)) {
            closer = go;
        }
        hitPoint = center.getSubtracted(closer.center);
        hitPoint = Vector2.toCartesian(hitPoint.getLength(), aiming.getAngle()).getAdded(center);
        go.setHealthPoints(go.getHealthPoints() - damage);
    }
}
