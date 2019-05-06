package game.objects.ships.aphelion;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.colliders.RayCollider;
import game.Vector2;
import game.objects.CollisionCodes;
import game.objects.GameObject;
import game.objects.Player;
import game.objects.ships.AdvancedAbility;

/**
 * Esta clase necesita mucho amor, pq este codigo es un asco
 * @author ProjectKevin
 */
public class Laser extends AdvancedAbility {
    private Vector2 hitPoint = new Vector2(0,0);
    private GameObject closer = null;
    private double damage = 1;
    
    public Laser(Player support) {
        this.tag = "Laserrrrr";
        
        // Ojo con estos vectores que son punteros a los de la nave
        this.position = support.getPosition();
        this.center = support.getCenter();
        this.aiming = support.getAiming();
        
        collCode = CollisionCodes.FIRE1.getValue();
        collides = CollisionCodes.FIRE1_COL.getValue();
        collides &= ~CollisionCodes.GRAVPOOL.getValue(); // Se desactiva la colision con el pozo gravitatorio
        this.collider = new RayCollider(this);
    }
    
    @Override
    public double activate(double cd) {
        return 0.0;
    }
    
    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        hitPoint = Vector2.toCartesian(9999, aiming.getAngle()).getAdded(center);
        closer = null;
        // El laser no puede ser destruido, no se llama a super.update
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
        else if (go.getCenter().distance(center) < closer.getCenter().distance(center)) {
            closer = go;
        }
        hitPoint = center.getSubtracted(closer.getCenter());
        hitPoint = Vector2.toCartesian(hitPoint.getLength(), aiming.getAngle()).getAdded(center);
        go.setHealthPoints(go.getHealthPoints() - damage);
    }
}
