package game.objects;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.AsteroidManager;
import game.GameManager;
import game.Vector2;
import game.colliders.CircleCollider;
import java.util.Random;

/**
 * Clase asteroide
 * @author Ramiro
 */
public class Asteroid extends GameObject {
    
    private static final int POWERUP_PROBABILITY = 50;
    
    private double angle = 0;
    private double damage = 2;
    private boolean divided = true;
    private double childAngle;
    
    private AsteroidType type;
    private AsteroidManager am;
    
    boolean hasPowerUp;
    
    public Asteroid(double x, double y, AsteroidType type, AsteroidManager am) {
        this.tag = "asteroid";
        this.type = type;
        this.image = type.getImg();

        this.width = image.getW();
        this.height = image.getH();
        
        this.position = new Vector2(x, y);
        this.center = new Vector2(x + width/2, y + + height/2);
        this.velocity = new Vector2(0,0);
        
        collCode = CollisionCodes.ASTEROID.getValue();
        collides = CollisionCodes.ASTEROID_COL.getValue();
        this.collider = new CircleCollider(this, width/2 - 2);
        
        this.damage = type.getHealth();
        this.healthPoints = type.getHealth();
        
        this.am = am;
    }
    
    public Asteroid(Asteroid father, AsteroidManager am) {
        this.tag = "asteroid";
        this.type = father.type.getSubtype();
        this.image = type.getImg();
        
        System.out.println(type.getSize());

        this.width = image.getW();
        this.height = image.getH();
        
        Random random = new Random();
        int newSent = random.nextInt(10);
        int newDir = random.nextInt(2);

        if(newDir < 1 ) newDir = -1;
        
        this.position = father.position.clone();
        this.center = father.center.clone();

        this.velocity = father.velocity.clone();
        this.velocity.rotateTo(father.childAngle + (newSent * newDir));
        this.velocity = velocity.getDivided(2);

        collCode = CollisionCodes.ASTEROID.getValue();
        collides = CollisionCodes.ASTEROID_COL.getValue();
        this.collider = new CircleCollider(this, width/2 - 2);
        
        this.damage = type.getHealth();
        this.healthPoints = type.getHealth();
        
        this.am = am;
        
        if (this.type.getSize() < 1){
           if((int)(Math.random() * 100 + 1) < POWERUP_PROBABILITY) {
                hasPowerUp = true;
            }
        }
    }
    
    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {
        super.update(gc, gm, dt);
        angle += 0.01;
        
        position.add(velocity);
        center.set(position.x + width/2, position.y + height/2);
        
        if(this.healthPoints <= 0 && !divided) {
            if (type.getSize() >= 1) {
                divided = true;
                for (int i = 0; i < type.getDivides(); i++) {
                    am.getStack().push(new Asteroid(this, am));
                }
            } else if (type.equals(AsteroidType.Little) && hasPowerUp) {
                new PowerUp((int)this.getPosition().clone().x, 
                            (int)this.getPosition().clone().y, 
                            PowerUpType.values()[(int) (Math.random() * PowerUpType.values().length)],
                            gm);
            }
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawRotatedImage(image, (int)position.x, (int)position.y, angle);
        //r.drawFillCircle((int)center.x, (int)center.y, width/2 - 3, 0xffff0000);
    }
    
    @Override
    public void effect(GameObject go) {
        go.setHealthPoints(go.getHealthPoints() - damage);
        divided = false;
        am.subAsteroid(type);
    }
}
