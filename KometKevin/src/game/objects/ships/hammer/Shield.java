package game.objects.ships.hammer;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.colliders.CircleCollider;
import game.objects.CollisionCodes;
import game.objects.GameObject;
import game.objects.Player;

/**
 * Cuarta habilidad de la HammerHead
 * @author ProjectKevin
 */
public class Shield extends GameObject {
    public Player support;

    public Shield(Player support) {
        this.tag = "Shields";
        this.support = support;
        
        // Ojo con estos vectores que son punteros a los de la nave
        this.position = support.getPosition();
        this.center = support.getCenter();
        this.aiming = support.getAiming();
        
        collCode = (0b00111111100);
        collides = (0b00111111100);
        this.collider = new CircleCollider(this,3 /*Aqui tamaño escudo*/);
    }
    
    @Override
    public void update(GameContainer gc, GameManager gm, float dt) { 
        while(!collisions.empty()) {
            effect(collisions.pop());
        }
        if (support.getEnergyPoints() > 0) {
            support.setEnergyPoints(support.getEnergyPoints() - 2 * dt);
        } else {
            //TODO: se desactiva el escudo
        }
    }
    
    @Override
    public void render(GameContainer gc, Renderer r) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void effect(GameObject go) {
        double aux = go.getHealthPoints() - support.getEnergyPoints();
        go.setHealthPoints(go.getHealthPoints() - aux);
    }
    
}
