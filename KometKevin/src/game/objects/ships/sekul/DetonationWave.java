package game.objects.ships.sekul;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.colliders.CircleCollider;
import game.objects.CollisionCodes;
import game.objects.GameObject;
import gfx.ImageTile;

/**
 *
 * @author 
 */
 
//Fallos al cambiar el tamaño de la imagen

public class DetonationWave extends GameObject {

    private boolean active = false;
    
    private int range = 250;		//Units
    private int velocity = 1;           //Units per frame
    private int cost = 20;
    private int damage = 1;
    private int actualRangeEffect;
    public GameObject support;
    
    public int baseW;
    public int baseH;
    public int baseR;
    
    public DetonationWave(GameObject support, int range, int velocity, int cost, int damage){
        
        this.range = range;
        this.velocity = velocity;
        this.cost = cost;
        this.damage = damage;
        
    	this.tag = "Wave";
        image = new ImageTile("/projectiles/shield.png",136,136);
        width = ((ImageTile) image).getTileW();
        baseW = width;
        height = ((ImageTile) image).getTileH();
        baseH = height;
        this.support = support;
        
        // Ojo con estos vectores que son punteros a los de la nave
        this.center = support.getCenter();
        this.position = support.getPosition();
        this.aiming = support.getAiming();
        
        collCode = CollisionCodes.FIRE1.getValue();
        collides = CollisionCodes.FIRE1_COL.getValue();
        this.collider = new CircleCollider(this,68);
        baseR = 0;
        
        actualRangeEffect = 0;
    }
    
    public double activate(double cd) {
        //active = !active;
        if (active) {
            return 0;
        } else {
            active = true;
            support.setEnergyPoints(support.getEnergyPoints() - cost);
            return cd;
        }
    }
	
    public void resetValues(){
        ((CircleCollider)collider).setRadius(baseR);
        //image.setH(baseH);
        //image.setW(baseW);
        width = baseW;
        height = baseH;
        actualRangeEffect = 0;
    }
    
    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        //System.out.println(active);
        if (active) {
            while(!collisions.empty()) {
                effect(collisions.pop());
            }
            //anim = (anim + dt * 10) % 6;
            actualRangeEffect += velocity;
            if (actualRangeEffect < range) {
                ((CircleCollider)collider).setRadius(actualRangeEffect+velocity);
                //image = new ImageTile("/projectiles/shield.png",136,136);
                width += velocity;
                height += velocity;
                                
            } else {
                    active = false;
                    resetValues();
            }
        } else {
            collisions.removeAllElements();
        }
        if (support.isDispose()) dispose = true;
    }
    
    @Override
    public void render(GameContainer gc, Renderer r) {
        if (active){
            r.drawRotatedImageTile((ImageTile)image, (int)position.x - 8, (int)position.y - 36, (int)0, 0, aiming.getAngle());
        }
    }
    
    @Override
    public void effect(GameObject go) {
        go.setHealthPoints(go.getHealthPoints() - damage);
    }
    
}
