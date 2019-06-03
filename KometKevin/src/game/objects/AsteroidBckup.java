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
public class AsteroidBckup extends GameObject {
    
    private double angle = 0;
    private double damage = 2;
    
    public AsteroidBckup(int x, int y, Image image) {
        this.tag = "asteroid";
        this.image = image;

        this.width = image.getW();
        this.height = image.getH();
        
        this.position = new Vector2(x, y);
        this.center = new Vector2(x + width/2, y + + height/2);
        this.velocity = new Vector2(0,0);
        
        collCode = CollisionCodes.ASTEROID.getValue();
        collides = CollisionCodes.ASTEROID_COL.getValue();
        this.collider = new CircleCollider(this, 26);
    }
    
    public AsteroidBckup(int x, int y) {
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
        super.update(gc, gm, dt);
        angle+=0.01;
        
        position.add(velocity);
        center.set(position.x + width/2, position.y + height/2);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawRotatedImage(image, (int)position.x, (int)position.y, angle);
        
    }
    
    @Override
    public void effect(GameObject go) {
        go.setHealthPoints(go.getHealthPoints() - damage);
    }  
}
