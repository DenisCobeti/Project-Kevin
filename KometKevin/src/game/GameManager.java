package game;

import engine2D.AbstractGame;
import engine2D.GameContainer;
import engine2D.Renderer;

import game.objects.GameObject;
import game.objects.*;
import gfx.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Clase que contiene los objetos del videojuego.
 * Colección de todo lo que se ha de computar y mostrar por pantalla.
 * 
 * @author Project Kevin
 */
public class GameManager extends AbstractGame {
    private final Camera camera;
    private final AsteroidManager am;
    private final AsteroidManagerbckup am2;
    private final HUD hud;
    private Player player;
    
    /**
     * Constructor de la clase
     */
    public GameManager() {
        background = new Image("/space/background.png");
        
        objects = new ArrayList<>();
        objects.add(new GravPool(background.getW()/2, background.getH()/2));
        
        camera = new Camera();
        am = new AsteroidManager(camera,this);
        am2 = new AsteroidManagerbckup(camera, this);
        hud = new HUD(player);
        
        PowerUpFactory.getPowerUp(PowerUpType.LIFE, 150, 150, this);
        
    }

    @Override
    public void update(GameContainer gc, float dt) {
        GameObject obj;
        int x, y, i;

        // Se actualizan todos los tipos de objetos
        for (i = 0; i < objects.size(); i++) {
            objects.get(i).update(gc, this, dt);
        }

        // Seguidamente se eliminan aquellos indicados o fuera de la escena
        for (i = 0; i < objects.size(); i++) {
            obj = objects.get(i);
            x = (int) obj.getCenter().x;
            y = (int) obj.getCenter().y;
            if (obj.isDead() || x < 0 || x > background.getW()
                             || y < 0 || y > background.getH()) {
                objects.remove(i);
                i--;
                if(obj instanceof Player) {
                    player = null;
                    gc.pause();
                    gc.getWindow().deadPLayer((Player)obj);
                }
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
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        for (GameObject obj : objects) {
            obj.render(gc, r);
        }
        hud.render(gc, r);
    }

    /**
     * Añade un jugador al juego
     * @param player nave a añadir
     */
    public void addPlayer(Player player) {
        this.player = player;
        objects.add(0, this.player);
        camera.setTarget(player);
        hud.setTarget(player);
    }
}
