package game;

import engine2D.Config;
import game.objects.Asteroid;
import game.objects.AsteroidType;
import gfx.Image;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Controla la entrada de asteroides al juego.
 * 
 * @author Sergio y Arturo
 */
public class AsteroidManager {    
    private Camera camera;
    private GameManager gm;
    private Config config;
    private ThreadLocalRandom random;
    private Stack<Asteroid> delayedAsteroids;
    private static int[] countAsteroidTypes; 
    
    private double counter = 0;
    
    public AsteroidManager(Camera camera,GameManager gm){  
        this.camera = camera;
        this.gm = gm;
        this.config = Config.getInstance();
        this.random = ThreadLocalRandom.current();
        delayedAsteroids = new Stack<>();
        
        countAsteroidTypes = new int[AsteroidType.values().length];
        for (int i = 0; i < countAsteroidTypes.length; i++) {
            countAsteroidTypes[i] = 0; 
        }
    }
    
    public void update(float dt){
        while(!delayedAsteroids.isEmpty()){
            gm.getObjects().add(delayedAsteroids.pop());
        }
        
        for (int i = 0; i < countAsteroidTypes.length; i++) {
            if (countAsteroidTypes[i] < AsteroidType.values()[i].getMax()) {
                if (generateAsteriods(dt, AsteroidType.values()[i])) {
                    countAsteroidTypes[i]++;
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
            double velx=3+random.nextDouble();
            double vely=3+random.nextDouble();
            int direction=random.nextInt(4);
            switch (direction) {
                case 0:
                    velx=-velx;
                    vely=-vely;
                    break;
                case 1:
                    velx=-velx;
                    break;
                case 2:
                    vely=-vely;
                    break;
                default:
                    break;
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
    

