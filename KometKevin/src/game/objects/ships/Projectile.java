package game.objects.ships;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.Vector2;
import game.colliders.CircleCollider;
import game.objects.CollisionCodes;
import game.objects.GameObject;
import gfx.Image;

/**
 * Clase proyectil genérico.
 * 
 * @author Arturo
 */
public class Projectile extends GameObject {
    private Image image; 
    private double damage = 1;
    
    // Esto habra que borrarlo
    public Projectile(int x, int y, Image image) {
        this.tag = "projectile";
        this.image = image;
        
        this.width = image.getW();
        this.height = image.getH();

        this.position = new Vector2(x - width/2, y - height/2);
        this.center = new Vector2(x, y);
        this.velocity = new Vector2(0,0);
        this.aiming = new Vector2(1,0); // Todas las naves apuntan hacia la der
        
        collCode = CollisionCodes.FIRE1.getValue();
        collides = CollisionCodes.FIRE1_COL.getValue();
        this.collider = new CircleCollider(this, 4);
    }
    
    public Projectile(int x, int y, Image image, int radius, double damage) {
        this.tag = "projectile";
        this.image = image;
        
        this.width = image.getW();
        this.height = image.getH();

        this.position = new Vector2(x - width/2, y - height/2);
        this.center = new Vector2(x, y);
        this.velocity = new Vector2(0,0);
        this.aiming = new Vector2(1,0); // Todas las naves apuntan hacia la der
        
        collCode = CollisionCodes.FIRE1.getValue();
        collides = CollisionCodes.FIRE1_COL.getValue();
        this.collider = new CircleCollider(this, radius);
        
        this.damage = damage;
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        super.update(gc, gm, dt);
        position.add(velocity);
        center.set(position.x + width/2, position.y + height/2);
        aiming.set(velocity.getNormalized());
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawRotatedImage(image, (int)position.x, (int)position.y, aiming.getAngle());
    }

    @Override
    public void effect(GameObject go) {
        go.setHealthPoints(go.getHealthPoints() - damage);
    }
}
