package game.objects;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.colliders.BoxCollider;
import gfx.ImageTile;
import game.Vector2;

/**
 * El codigo de esta clase esta mal es pa pruebas
 * @author
 */
public class PowerUp extends GameObject {
    private int anim = 0;
    
    public PowerUp(int x, int y) {
        this.tag = "player";
        this.image = new ImageTile("/ships/hammerIcon.png", 64, 64);
        
        this.width = ((ImageTile)image).getTileW();
        this.height = ((ImageTile)image).getTileH();
        
        this.position = new Vector2(x, y);
        this.center = new Vector2(x + width/2, y + + height/2);
        this.velocity = new Vector2(0,0);
        this.aiming = new Vector2(0.5,0.5);
        
        collCode = CollisionCodes.ASTEROID.getValue();
        collides = CollisionCodes.ASTEROID_COL.getValue();
        this.collider = new BoxCollider(this, 64, 64);
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        while (!collisions.empty()) {
            collisions.pop();
            anim = (anim + 1)%4;
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawRotatedImageTile((ImageTile)image, (int)position.x, (int)position.y, anim ,0, aiming.getAngle());
    }
}
