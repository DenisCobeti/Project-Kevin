package game.objects;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.Vector2;
import game.colliders.CircleCollider;
import gfx.Image;

/**
 *
 * @author 
 */
public class Asteroid extends GameObject {
    private boolean borrar = false;
    private double angle = 0;
    
    public Asteroid(int x, int y) {
        this.tag = "asteroid";
        image = new Image("/space/asteroid.png");

        this.width = image.getW();
        this.height = image.getH();
        
        this.position = new Vector2(x, y);
        this.center = new Vector2(x + width/2, y + + height/2);
        this.velocity = new Vector2(0,0);
        
        collCode = CollisionCodes.ASTEROID.getValue();
        collides = CollisionCodes.ASTEROID_COL.getValue();
        this.collider = new CircleCollider(this, 26);
    }
    
    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        while(!collisions.empty()) {
            collisions.pop();//.setDead(true);
            borrar = true;
        }
        angle+=0.01;
        
        position.add(velocity);
        center.set(position.x + width/2, position.y + height/2);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawRotatedImage(image, (int)position.x, (int)position.y, angle);
        if (borrar) {
            r.drawFillCircle((int)center.x, (int)center.y, 26, 0xffff0000);
        }
        borrar = false;
    }
    
}
