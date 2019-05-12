package game.objects.ships.hammer;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.colliders.CircleCollider;
import game.objects.GameObject;
import game.objects.Player;
import gfx.ImageTile;

/**
 * Cuarta habilidad de la HammerHead
 * @author ProjectKevin
 */
public class Shield extends GameObject {
    public Player support;
    public boolean active = false;
    public double anim = 0;

    public Shield(Player support) {
        this.tag = "Shield";
        image = new ImageTile("/projectiles/shield.png",136,136);
        width = ((ImageTile) image).getTileW();
        height = ((ImageTile) image).getTileH();
        this.support = support;
        
        // Ojo con estos vectores que son punteros a los de la nave
        this.center = support.getCenter();
        this.position = support.getPosition();
        this.aiming = support.getAiming();
        
        collCode = (0b00111111100);
        collides = (0b00111111100);
        this.collider = new CircleCollider(this,68);
    }
    
    public void activate(double[] cd, boolean[] isActive, int numHab) {
        isActive[numHab] = !isActive[numHab];
        active = isActive[numHab];
        if (!active) {
            cd[numHab] = support.getCds()[numHab];
        }
    }
    
    @Override
    public void update(GameContainer gc, GameManager gm, float dt) { 
        if (active) {
            while(!collisions.empty()) {
                effect(collisions.pop());
            }
            anim = (anim + dt * 10) % 6;
            if (support.getEnergyPoints() > 0) {
                support.setEnergyPoints(support.getEnergyPoints() - 2 * dt);
            } else {
//                active = false;
            }
        } else {
            collisions.removeAllElements();
        }
        if (support.isDispose()) dispose = true;
    }
    
    @Override
    public void render(GameContainer gc, Renderer r) {
        if (active)
            r.drawRotatedImageTile((ImageTile)image, (int)position.x - 8, (int)position.y - 36, (int)anim, 0, aiming.getAngle());
    }

    @Override
    public void effect(GameObject go) {
        double aux = go.getHealthPoints() - support.getEnergyPoints();
        go.setHealthPoints(go.getHealthPoints() - aux);
    }
    
}
