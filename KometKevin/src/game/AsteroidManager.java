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
    
    public AsteroidManager(Camera camera,GameManager gm){  
        this.camera=camera;
        this.gm=gm;
        this.config=Config.getInstance();

        this.random=new Random();
    }
    
    public void generateAsteriods(float dt){   
        if(gm.getObjects().size()<500){
            int horizontal=generarHorizontal();
            int vertical=generarvertical();

            if(horizontal!=-1 && vertical!=-1){
                Asteroid kevin= new Asteroid(horizontal,vertical);
                double x=2+random.nextDouble();
                double y=2+random.nextDouble();
                int direction=random.nextInt(4);
                if(direction==0){
                    x=-x;
                    y=-y;
                }else if(direction==1){
                    x=-x;
                }else if(direction==2){
                    y=-y;
                }
                kevin.setVelocity(new Vector2(x, y));
                gm.getObjects().add(kevin);
            }
        }

    }
    private int generarHorizontal(){
        int x=(int)camera.getOffX();
        int anchura=config.getScreenWidth(); //anchura
        int screenW=gm.getBackground().getW(); //Anchura total de la pantalla 
        int horizontal=random.nextInt(screenW);
        if(horizontal>x-50 && horizontal<x+anchura+50){
            return generarHorizontal();
        }
        return horizontal;
    }
    private int generarvertical(){
        int y=(int)camera.getOffY();
        int altura=config.getScreenHeight(); //altura   
        int screenH=gm.getBackground().getH(); //Altura total de la pantalla
        int vertical=random.nextInt(screenH);
        if(vertical>y-50 && vertical<y+altura+50){
            return generarvertical();
        }
        return vertical;
    }
}
