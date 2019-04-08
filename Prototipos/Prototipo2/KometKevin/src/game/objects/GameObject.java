package game.objects;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.Vector2;
import game.colliders.*;
import gfx.Image;
import java.util.Stack;

/**
 * Objeto del motor gráfico. Todo objeto en GameManager hereda de esta clase.
 * 
 * @author Project Kevin
 */
public abstract class GameObject {
    protected String tag;
    
    protected Vector2 position; // Vector que indica la posición de dibujado
    protected Vector2 center;   // Vector que indica el centro del objeto
    protected Vector2 velocity; // Vector con la velocidad del objeto
    protected Vector2 aiming;   // Vector normalizado que indica la dirección

    protected Image image = null;
    protected int width, height;
    
    protected int collCode;     // Tipo de colisionador
    protected int collides;     // Tipos con los que colisiona
    protected AbstractCollider collider;   
    protected Stack<GameObject> collisions = new Stack<>();
    
    protected double healthPoints = 0;
    protected double energyPoints = 0;
    protected boolean dispose = false;
    
    public abstract void update(GameContainer gc, GameManager gm, float dt);
    public abstract void render(GameContainer gc, Renderer r);
    
    /**
     * Informa si el objeto colisiona con otro
     * @param go otro GameObject
     * @return coinciden tipo y código de colisión
     */
    public boolean intersects(GameObject go) {
        if (((this.collCode & go.collides) == 0) || 
            ((go.collCode & this.collides) == 0)) return false;
        
        if (go.collider instanceof CircleCollider) {
            return this.collider.intersects((CircleCollider)go.collider);
        } else {
            return this.collider.intersects((BoxCollider)go.collider);
        }
    }
    
    // Getters / setters
    public String getTag() {return tag;}
    public Vector2 getPosition() {return position;}
    public Vector2 getCenter() {return center;}
    public Vector2 getVelocity() {return velocity;}
    public Vector2 getAiming() {return aiming;}
    public int getWidth() {return width;}
    public int getHeight() {return height;}
    public int getCollCode() {return collCode;}
    public int getCollides() {return collides;}
    public AbstractCollider getCollider() {return collider;}
    public Stack<GameObject> getCollisions() {return collisions;}
    public double getHealthPoints() {return healthPoints;}
    public double getEnergyPoints() {return energyPoints;}
    public boolean isDispose() {return dispose;}
    
    public void setTag(String tag) {this.tag = tag;}
    public void setCenter(Vector2 center) {this.center = center;}
    public void setPosition(Vector2 position) {this.position = position;}
    public void setVelocity(Vector2 velocity) {this.velocity = velocity;}
    public void setAiming(Vector2 aiming) {this.aiming = aiming;}
    public void setWidth(int width) {this.width = width;}
    public void setHeight(int height) {this.height = height;}
    public void setCollCode(int collCode) {this.collCode = collCode;}
    public void setCollides(int collides) {this.collides = collides;}
    public void setCollider(AbstractCollider collider) {this.collider = collider;}
    public Image getImage() {return image;}
    public void setImage(Image image) {this.image = image;}
    public void setHealthPoints(double healthPoints) {this.healthPoints = healthPoints;}
    public void setEnergyPoints(double energyPoints) {this.energyPoints = energyPoints;}
    public void setDispose(boolean dispose) {this.dispose = dispose;}
}
