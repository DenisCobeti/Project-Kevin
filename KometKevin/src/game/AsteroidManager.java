package game;

import engine2D.Config;
import engine2D.GameContainer;
import game.objects.Asteroid;
import game.objects.AsteroidFactory;
import game.objects.AsteroidType;
import gfx.Image;
import java.util.Random;
import java.util.Stack;

/**
 *
 * @author alumno
 */
public class AsteroidManager {
    private Camera camera;
    private GameManager gm;
    private Config config;
    private Random random;
    private Stack<Asteroid> delayedAsteroids;
    private int[] maxAsteroidTypes;
    private int[] countAsteroidTypes; 
    
    private Image image = new Image("/space/asteroid.png");
    
    private double counter = 0;
    
    public AsteroidManager(Camera camera,GameManager gm){  
        this.camera=camera;
        this.gm=gm;
        this.config=Config.getInstance();
        this.random=new Random();
        delayedAsteroids = new Stack<>();
        
        maxAsteroidTypes = new int[AsteroidType.values().length];
        maxAsteroidTypes[0] = 300;
        maxAsteroidTypes[1] = 0;
        
        countAsteroidTypes = new int[AsteroidType.values().length];
    }
    
    public void update(float dt){
        while(!delayedAsteroids.isEmpty()){
            gm.getObjects().add(delayedAsteroids.pop());
            System.out.println("poping");
        }
        
        if(gm.getObjects().size()<490){
            for(int i=0; i<maxAsteroidTypes.length;i++){
                if(countAsteroidTypes[i] < maxAsteroidTypes[i]){
                    //generateAsteriods(dt, AsteroidFactory.getAsteroid(AsteroidType.values()[i], 0, 0, image, this));
                    if(generateAsteriods(dt, AsteroidType.values()[i])){
                        countAsteroidTypes[i]++;
                    }
                }
            }
        }
        
        counter += dt;
    }
    
    public boolean generateAsteriods(float dt, AsteroidType type){   
      
        //if(gm.getObjects().size()<500){ 
            int x=(int)camera.getOffX();
            int horizontal=random.nextInt(gm.getBackground().getW());
            if(horizontal>x-50 && horizontal<x+config.getScreenWidth()+50){
                return false;
            }
            int y=(int)camera.getOffY();
            int vertical=random.nextInt(gm.getBackground().getH());
            if(vertical>y-50 && vertical<y+config.getScreenHeight()+50){
                return false;
            } 
            Asteroid kevin = AsteroidFactory.getAsteroid(type, horizontal, vertical, this);
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
        countAsteroidTypes[type.getNum(type)]--;
    }
}
    

