package game.objects;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.AsteroidManager;
import game.GameManager;
import game.Vector2;
import game.colliders.CircleCollider;
import game.objects.CollisionCodes;
import game.objects.GameObject;
import gfx.Image;
import java.util.Random;

/**
 *
 * @author 
 */
public class Asteroid extends GameObject {
    
    private double angle = 0;
    private double damage = 2;
    private int baseVelocity;
    private boolean divided = true;
    private int size;
    private double childAngle;
    
    private AsteroidType type;
    private AsteroidManager am;
    
    
    public Asteroid(double x, double y, int size, int damage, int baseVelocity, 
                    AsteroidType type, Image image, AsteroidManager am) {
        this.tag = "asteroid";
        this.image = image;
        this.type = type;

        this.width = this.image.getW();
        this.height = this.image.getH();
        
        this.position = new Vector2(x, y);
        this.center = new Vector2(x + width/2, y + + height/2);
        this.velocity = new Vector2(0,0);
        
        collCode = CollisionCodes.ASTEROID.getValue();
        collides = CollisionCodes.ASTEROID_COL.getValue();
        this.collider = new CircleCollider(this, 26);
        
        this.size = size;
        this.damage = damage;
        this.baseVelocity = baseVelocity;
        this.divided = true;
        
        this.am = am;
        
    }
    
    public Asteroid(Asteroid father, Image image, AsteroidManager am) {
        this.tag = "asteroid";
        this.image = new Image("/space/asteroid.png");
        this.type = father.type;

        this.width = image.getW();
        this.height = image.getH();
        
        Random random = new Random();
        int newSent = random.nextInt(10);
        int newDir = random.nextInt(2);

        if(newDir<1){newDir = -1;}
        
        this.position = father.position.clone();
        this.center = father.center.clone();

        this.velocity = father.velocity.clone();
        //this.velocity.rotateTo(father.childAngle+(newDir*3.14));
        this.velocity.rotateTo(father.childAngle+(newSent*newDir));
        this.velocity = velocity.getDivided(2);

        
        collCode = CollisionCodes.ASTEROID.getValue();
        collides = CollisionCodes.ASTEROID_COL.getValue();
        this.collider = new CircleCollider(this, 26);
        
        this.size = father.size-1;
        
        divided = true;
        
        this.am = am;
    }
    
    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        super.update(gc, gm, dt);
        angle+=0.01;
        
        position.add(velocity);
        center.set(position.x + width/2, position.y + height/2);
        
        if(this.healthPoints <= 0 && !divided && size>1){
            System.out.println("pum");
            divided = true;
            gm.getStack().push(AsteroidFactory.getChildAsteroid(this, image, am));
            gm.getStack().push(AsteroidFactory.getChildAsteroid(this, image, am));
            //gm.getStack().push(new Asteroid(this,image,am));
            //gm.getStack().push(new Asteroid(this,image,am));
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawRotatedImage(image, (int)position.x, (int)position.y, angle);
        
    }
    
    @Override
    public void effect(GameObject go) {
        //childAngle = go.velocity.clone().getAngle();
        go.setHealthPoints(go.getHealthPoints() - damage);
        divided = false;
        am.subAsteroid(type);
    }  
    
    
}
