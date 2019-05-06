package game.objects.ships.hammer;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.colliders.CircleCollider;
import game.objects.GameObject;
import game.objects.Player;
import game.objects.ships.AdvancedAbility;

/**
 * Segunda habilidad de la HammerHead
 * @author
 */
public class FlakSwarm extends AdvancedAbility{
    
    public Player support;    
    
    public FlakSwarm(Player support) {
        this.tag = "Shield";
        this.support = support;
        
        // Ojo con estos vectores que son punteros a los de la nave
        this.center = support.getCenter();
        this.position = support.getPosition();
        this.aiming = support.getAiming();
        
        collCode = 0;
        collides = 0;
        this.collider = new CircleCollider(this,0);
    }
    
    @Override
    public double activate(double cd) {
        active = true;
        
        return 0.0;
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) { 
        if (active) {
            while(!collisions.empty()) {
                effect(collisions.pop());
            }
            if (support.getEnergyPoints() > 0) {
                support.setEnergyPoints(support.getEnergyPoints() - 2 * dt);
            } else {
//                active = false;
            }
        }
        if (support.isDispose()) dispose = true;
    }
    
    @Override
    public void render(GameContainer gc, Renderer r) {}

    @Override
    public void effect(GameObject go) {}
}
