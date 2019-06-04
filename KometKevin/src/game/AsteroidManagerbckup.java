package game;

import engine2D.Config;
import game.objects.AsteroidBckup;
import gfx.Image;
import java.util.Random;

/**
 *
 * @author Project Kevin
 */
public class AsteroidManagerbckup {
    private Camera camera;
    private GameManager gm;
    private Random random;
    
    private double counter = 0;
    private Image image = new Image("/space/asteroid.png");
    
    public AsteroidManagerbckup(Camera camera,GameManager gm){  
        this.camera=camera;
        this.gm=gm;
        this.random=new Random();
    }
    
    public void generateAsteriods(float dt){   
        if(gm.getObjects().size()<500){ 
            int x=(int)camera.getOffX();
            int horizontal=random.nextInt(gm.getBackground().getW());
            if(horizontal>x-50 && horizontal<x+Config.getScreenWidth()+50){
                horizontal= -1;
            }
            int y=(int)camera.getOffY();
            int vertical=random.nextInt(gm.getBackground().getH());
            if(vertical>y-50 && vertical<y+Config.getScreenHeight()+50){
                vertical=-1;
            }  
            if(horizontal!=-1 && vertical!=-1){
                AsteroidBckup kevin= new AsteroidBckup(horizontal,vertical,image);
                if (counter %(3*dt)==0) {
                    Vector2 aux= new Vector2(x,y);
                    aux.subtract(kevin.getCenter());
                    //aux = kevin.getCenter().getSubtracted(aux)
                    kevin.setVelocity(Vector2.toCartesian(9, aux.getAngle()));
                } else {
                    double velx=2+random.nextDouble();
                    double vely=2+random.nextDouble();
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
            }
        }
    counter += dt;
    } 
}
    

