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
    public int num;
    
    public double anim = 0;

    public Shield(Player support, int num) {
        this.tag = "Shield";
        image = new ImageTile("/projectiles/shield.png",136,136);
        width = ((ImageTile) image).getTileW();
        height = ((ImageTile) image).getTileH();
        this.support = support;
        this.num = num;
        
        // Ojo con estos vectores que son punteros a los de la nave
        this.center = support.getCenter();
        this.position = support.getPosition();
        this.aiming = support.getAiming();
        
        collCode = (0b00111111100);
        collides = (0b00111111100);
        this.collider = new CircleCollider(this,68);
    }
    
    public void activate() {
        support.getIsActive()[num] = !support.getIsActive()[num];
        if (!support.getIsActive()[num]) {
            support.getCds()[num] = support.getCdValues()[num];
        }
    }
    
    @Override
    public void update(GameContainer gc, GameManager gm, float dt) { 
        if (support.getIsActive()[num]) {
            while(!collisions.empty()) {
                effect(collisions.pop());
            }
            anim = (anim + dt * 10) % 6;
            if (support.getEnergyPoints() > 0) {
                support.setEnergyPoints(support.getEnergyPoints() - 1.2 * dt);
            } else {
                activate();
            }
        } else {
            collisions.removeAllElements();
        }
        if (support.isDispose()) dispose = true;
    }
    
    @Override
    public void render(GameContainer gc, Renderer r) {
        if (support.getIsActive()[num])
            r.drawRotatedImageTile((ImageTile)image, (int)position.x - 8, (int)position.y - 36, (int)anim, 0, aiming.getAngle());
    }

    @Override
    public void effect(GameObject go) {
        double aux = go.getHealthPoints() - support.getEnergyPoints();
        go.setHealthPoints(go.getHealthPoints() - aux);
    }
    
}
