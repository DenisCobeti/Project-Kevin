package game.objects;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.colliders.BoxCollider;
import gfx.ImageTile;
import game.Vector2;

/**
 * El codigo de esta clase esta mal revisar a fondo
 * @author
 */
public class PowerUp extends GameObject {
    private enum PowerUpTypes { TYPE1, TYPE2, TYPE3 };
    
    private PowerUpTypes tipo;
    private int anim = 0;
    
    public PowerUp(int x, int y /*, stipo*/) {
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
    public void render(GameContainer gc, Renderer r) {
        r.drawRotatedImageTile((ImageTile)image, (int)position.x, (int)position.y, anim ,0, aiming.getAngle());
    }

    @Override
    public void effect(GameObject go) {
        anim = (anim + 1)%4;
        /* 
        TODO: aqui pues se podria hacer un switch con efectos, estableciendo 
        un enum en esta clase con todos ellos
        */
//        switch (tipo) {
//            // TODO Lo que sea
//        }
    }
}
