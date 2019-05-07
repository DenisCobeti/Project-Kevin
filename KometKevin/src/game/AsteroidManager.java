/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import engine2D.Config;
import engine2D.GameContainer;
import game.objects.Asteroid;
import java.util.Random;

/**
 *
 * @author alumno
 */
public class AsteroidManager {
    private Camera camera;
    private GameManager gm;
    private Config config;
    private Random random;
    
    private double counter = 0;
    
    public AsteroidManager(Camera camera,GameManager gm){  
        this.camera=camera;
        this.gm=gm;
        this.config=Config.getInstance();
        this.random=new Random();
    }
    
    public void generateAsteriods(float dt){   
        if(gm.getObjects().size()<500){ 
            int x=(int)camera.getOffX();
            int horizontal=random.nextInt(gm.getBackground().getW());
            if(horizontal>x-50 && horizontal<x+config.getScreenWidth()+50){
                horizontal= -1;
            }
            int y=(int)camera.getOffY();
            int vertical=random.nextInt(gm.getBackground().getH());
            if(vertical>y-50 && vertical<y+config.getScreenHeight()+50){
                vertical=-1;
            }  
            if(horizontal!=-1 && vertical!=-1){
                Asteroid kevin= new Asteroid(horizontal,vertical);
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
            }
        }
    counter += dt;
    } 
}
    

