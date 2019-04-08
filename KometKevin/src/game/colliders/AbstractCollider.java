package game.colliders;

import game.Vector2;
import game.objects.GameObject;

/**
 * Colisionador abstracto.
 * Tiene tantos metodos como colisionadores implementados
 * 
 * @author Project Kevin
 */
public abstract class AbstractCollider {    
    protected GameObject object;
    protected Vector2 center;
    
    public abstract boolean intersects(CircleCollider bc);
    public abstract boolean intersects(BoxCollider bc);
    public abstract boolean intersects(RayCollider bc);
}
