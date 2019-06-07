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
    
    private int velocity = 1;           //Units per frame
    private int cost = 0;
    private int damage = 1;
    private int actualRangeEffect;
    private GameObject support;
    private float anim = 0;
    private int waveCount = 0;
    private int imgSize = 256;          //Units
    private int imgNum = 4;
    
    private int baseW;
    private int baseH;
    private int baseR;
    
    public DetonationWave(GameObject support, int velocity, int cost, int damage){
        
        this.velocity = velocity;
        this.cost = cost;
        this.damage = damage;
        
    	this.tag = "Wave";
        image = new ImageTile("/projectiles/shockwave.png",imgSize,imgSize);
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
        this.collider = new CircleCollider(this,imgSize/imgNum);
        baseR = 0;
        
        actualRangeEffect = 0;
        
        this.healthPoints = 1000;
    }
    
    public void activate() {
        //active = !active;
        if (active) {

        } else {
            active = true;
            support.setEnergyPoints(support.getEnergyPoints() - cost);
        }
    }
	
    public void resetValues(){
        ((CircleCollider)collider).setRadius(baseR);
        actualRangeEffect = 0;
        anim = 0;
        waveCount = 0;
        healthPoints = 1000;
    }
    
    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        if (active) {
            while(!collisions.empty()) {
                effect(collisions.pop());
            }
            actualRangeEffect += velocity;
            if (actualRangeEffect>=imgSize/imgNum-25){
                ((CircleCollider)collider).setRadius(actualRangeEffect*(waveCount+1));
                actualRangeEffect = 0;
                anim = (anim + 1) % 6;
                waveCount++;
                if(waveCount >= imgNum){
                    active = false;
                    resetValues();
                }
            }                           
        } else {
            collisions.removeAllElements();
        }
        if (support.isDispose()) dispose = true;
    }
    
    @Override
    public void render(GameContainer gc, Renderer r) {
        if (active){
            r.drawRotatedImageTile((ImageTile)image, 
                    (int)position.x-(2*imgSize/6)-6, 
                    (int)position.y-(2*imgSize/5)+6, 
                    (int)anim, 0, aiming.getAngle());
        }
    }
    
    @Override
    public void effect(GameObject go) {
        go.setHealthPoints(go.getHealthPoints() - damage);
    }
    
}
