package game.objects.ships.hammer;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.Vector2;
import game.colliders.CircleCollider;
import game.objects.CollisionCodes;
import game.objects.GameObject;
import gfx.ImageTile;

/**
 * Escuadra de naves que lanza la hammer.
 * Tras un breve retardo se dirigen a la dirección señalada por el raton
 * 
 * @author Arturo
 */
public class Squad extends GameObject{

    private ImageTile image = new ImageTile("/projectiles/squad.png",22,22);
    
    private boolean aimed = false;
    private Vector2 aim;
    
    private double anim = 0;
    private double damage = 0.05;
    
    protected double rotationTolerance = 0.02;//0.01;
    protected double rotationSpeed = 0.07;
    
    public Squad(int x, int y, Vector2 aim) {
        this.tag = "projectile";
        this.aim = aim;
        
        this.width = image.getTileW();
        this.height = image.getTileH();

        this.position = new Vector2(x - width/2, y - height/2);
        this.center = new Vector2(x, y);
        this.velocity = new Vector2(0,0);
        this.aiming = new Vector2(1,0); // Todas las naves apuntan hacia la der
        
        collCode = CollisionCodes.FIRE1.getValue();
        collides = CollisionCodes.FIRE1_COL.getValue();
        this.collider = new CircleCollider(this, 11);
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {      
        if (!aimed) {
            Vector2 dir = aim.getSubtracted(center);
            dir.rotateBy(Math.PI - aiming.getAngle());
            
            if (Math.abs(dir.getAngle()) < Math.PI - rotationTolerance) {
                if (dir.getAngle() > 0)
                    aiming.rotateBy(-rotationSpeed);
                else if (dir.getAngle() < 0) {
                    aiming.rotateBy(rotationSpeed);
                }
            } else {
                aimed = true;
            }
        }
        if (aimed) velocity.multiply(1.01);
        else velocity.multiply(0.99);
        
        position.add(Vector2.toCartesian(velocity.getLength(), aiming.getAngle()));
        center.set(position.x + width/2, position.y + height/2);
        while(!collisions.empty()) {
            effect(collisions.pop());
        }
        if (healthPoints <= 0) {
            dispose = true;
        }
        
        anim += dt;
        if (anim > 5) anim = 0;
    }

    
    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawRotatedImageTile(image, (int)position.x, (int)position.y, 0, (int)anim, aiming.getAngle());
    }

    @Override
    public void effect(GameObject go) {
        go.setHealthPoints(go.getHealthPoints() - (damage * velocity.getLength()));
    }
    
}
