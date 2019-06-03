package game;

import engine2D.Config;
import game.objects.Asteroid;
import game.objects.AsteroidType;
import gfx.Image;
import java.util.Random;
import java.util.Stack;

/**
 *
 * @author alumno
 */
public class AsteroidManager {
    private static final int MAX_ASTEROIDS_IN_GAME = 490;
    
    private Camera camera;
    private GameManager gm;
    private Config config;
    private Random random;
    private Stack<Asteroid> delayedAsteroids;
    private int[] countAsteroidTypes; 
    
    private double counter = 0;
    
    public AsteroidManager(Camera camera,GameManager gm){  
        this.camera=camera;
        this.gm=gm;
        this.config=Config.getInstance();
        this.random=new Random();
        delayedAsteroids = new Stack<>();
        
        countAsteroidTypes = new int[AsteroidType.values().length];
    }
    
    public void update(float dt){
        while(!delayedAsteroids.isEmpty()){
            gm.getObjects().add(delayedAsteroids.pop());
        }
        
        if(gm.getObjects().size() < MAX_ASTEROIDS_IN_GAME) {
            for(int i = 0; i < countAsteroidTypes.length; i++){
                if(countAsteroidTypes[i] < AsteroidType.values()[i].getMax()){
                    if(generateAsteriods(dt, AsteroidType.values()[i])){
                        countAsteroidTypes[i]++;
                    }
                }
            }
        }
        counter += dt;
    }
    
    public boolean generateAsteriods(float dt, AsteroidType type){   
        int x, y;
        int horizontal, vertical;
        
        x = (int)camera.getOffX();
        horizontal = random.nextInt(gm.getBackground().getW());
        if(horizontal > x - 50 && horizontal < x + config.getScreenWidth() + 50) {
            return false;
        }
        y = (int)camera.getOffY();
        vertical = random.nextInt(gm.getBackground().getH());
        if(vertical > y - 50 && vertical < y + config.getScreenHeight() + 50) {
            return false;
        } 
        Asteroid kevin = new Asteroid(horizontal, vertical, type, this);
        
        if (counter %(3*dt)==0) {
            Vector2 aux= new Vector2(x,y);
            aux.subtract(kevin.getCenter());
            //aux = kevin.getCenter().getSubtracted(aux)
            kevin.setVelocity(Vector2.toCartesian(9, aux.getAngle()));
        } else {
            double velx=2+random.nextDouble();
            double vely=2+random.nextDouble();
            int direction=random.nextInt(4);
            if(direction==0){
                velx=-velx;
                vely=-vely;
            }else if(direction==1){
                velx=-velx;
            }else if(direction==2){
                vely=-vely;
            }
            kevin.setVelocity(new Vector2(velx, vely));
            //gm.getObjects().add(kevin);
        }
        gm.getObjects().add(kevin);
        return true;
            

    } 
    
    public Stack<Asteroid> getStack(){return delayedAsteroids;}
    
    public Image getImage(){return null;}
    
    public void subAsteroid(AsteroidType type){
        countAsteroidTypes[type.getSize()]--;
    }
}
    

