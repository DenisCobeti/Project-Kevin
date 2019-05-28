package game;

import game.objects.Asteroid;
import engine2D.AbstractGame;
import engine2D.GameContainer;
import engine2D.Renderer;

import game.objects.GameObject;
import game.objects.*;
import game.objects.ships.hammer.HammerHead;
import gfx.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Clase que contiene los objetos del videojuego.
 * Colección de todo lo que se ha de computar y mostrar por pantalla.
 * 
 * @author Prokect Kevin
 */
public class GameManager extends AbstractGame {
    private final Camera camera;
    private final AsteroidManager am;
    private final HUD hud;
    private Player player;
    private Stack<Asteroid> delayedAsteroids;
    
    /**
     * Constructor de la clase
     */
    public GameManager() {
        background = new Image("/space/background.png");
        
        objects = new ArrayList<>();
        objects.add(new GravPool(background.getW()/2, background.getH()/2));
        
        camera = new Camera();
        am = new AsteroidManager(camera,this);    
        hud = new HUD(player);

        delayedAsteroids = new Stack<>(); 
    }

    @Override
    public void update(GameContainer gc, float dt) {
        GameObject obj;
        int x, y, i;

        // Se actualizan todos los tipos de objetos
        for (i = 0; i < objects.size(); i++) {
            obj = objects.get(i);
            obj.update(gc, this, dt);

            // Seguidamente se eliminan aquellos indicados o fuera de la escena
            x = (int) obj.getCenter().x;
            y = (int) obj.getCenter().y;
            if (obj.isDispose() || x < 0 || x > background.getW()
                    || y < 0 || y > background.getH()) {
                if(obj instanceof Player){
                    gc.pause();
                    gc.getWindow().deadPLayer((Player)obj);
                }
                objects.remove(i);
                
                i--;
            }
        }

        // Se calculan los offsets de la camara
        camera.update(gc, this, dt);
        // Se calcula el posicionamiento de nuevos asteroides
        am.update(dt);

        // Se calculan las colisiones para la proxima iteración del motor
        for (i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                if (objects.get(i).intersects(objects.get(j))) {
                    objects.get(i).getCollisions().push(objects.get(j));
                    objects.get(j).getCollisions().push(objects.get(i));
                }
            }
        }
  
        // Pause el motor y muestra el menu de juego
        if (gc.getInput().isKeyDown(KeyEvent.VK_P)) {
            gc.pause();
        }
        
        // Se para todo
        if (gc.getInput().isKeyDown(KeyEvent.VK_ESCAPE)) {
            System.exit(0); // BOOM
        }
        
//        while(!delayedAsteroids.isEmpty()){
//            objects.add(delayedAsteroids.pop());
//        }
        
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        for (GameObject obj : objects) {
            obj.render(gc, r);
        }
        hud.render(gc, r);
    }

    public void addPlayer(Player player) {
        this.player = player;
        objects.add(0, this.player);
        camera.setTarget(player);
        hud.setTarget(player);
    }
    
    public Stack<Asteroid> getStack(){return delayedAsteroids;}
    
}
