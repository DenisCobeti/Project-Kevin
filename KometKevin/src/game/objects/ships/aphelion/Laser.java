package game.objects.ships.aphelion;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.colliders.RayCollider;
import game.Vector2;
import game.objects.CollisionCodes;
import game.objects.GameObject;
import game.objects.Player;

/**
 * Esta clase necesita mucho amor, pq este codigo es un asco
 * @author Denis Florin Cobeti
 */
public class Laser extends GameObject {
    public Player support;
    public int num;
    
    private Vector2 hitPoint = new Vector2(0,0);
    private GameObject closer = null;
    private double damage = 0.04;
    
    public Laser(Player support, int num) {
        this.tag = "Laser";
        this.support = support;
        this.num = num;
        
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
    public void update(GameContainer gc, GameManager gm, float dt) {
        hitPoint = Vector2.toCartesian(9999, aiming.getAngle()).getAdded(center); // ese 9999 es mejorable :I
        closer = null;
        // El laser no llama a super.update porque tiene un comportamiento especial
        if (support.getIsActive()[num]) {
            support.setEnergyPoints(support.getEnergyPoints() - support.getEnergyCost()[num] * dt);
            while(!collisions.empty()) {
                effect(collisions.pop());
            }
        } else {
            collisions.clear();
        }
    }
    
    @Override
    public boolean isDead() {
        // No tiene salud asi que no muere si no tiene vida
        return dispose;
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        if (support.getIsActive()[num])
            r.drawLine((int)center.x, (int)center.y,(int)hitPoint.x, (int)hitPoint.y, 0xff1f1fff);
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
