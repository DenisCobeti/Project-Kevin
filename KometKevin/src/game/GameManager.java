package game;

import game.objects.ships.aphelion.Laser;
import game.objects.GameObject;
import engine2D.AbstractGame;
import engine2D.GameContainer;
import engine2D.Renderer;
import game.objects.*;
import game.objects.ships.hammer.*;
import gfx.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Clase que contiene los objetos del videojuego.
 * Colección de todo lo que se ha de computar y mostrar por pantalla.
 * 
 * @author Prokect Kevin
 */
public class GameManager extends AbstractGame {
    private Camera camera;
    private AsteroidManager am;
    private HUD hud;
    
    /**
     * Constructor de la clase
     */
    public GameManager() {
        background = new Image("/space/background.png");

        objects = new ArrayList<>();
        Player player = new HammerHead(100, 100, this);

//        for (int i = 0; i < 25; i++) /* Test de eficiencia */
        objects.add(0,player);

        objects.add(new Asteroid(350, 350));
        objects.add(new PowerUp(200, 100));
        objects.add(new PowerUp(300, 50));
        objects.add(new GravPool(background.getW()/2, background.getH()/2));
                
        camera = new Camera(player);
        hud = new HUD(player);
        am = new AsteroidManager(camera,this);
    }

    @Override
    public void update(GameContainer gc, float dt) {
        GameObject obj;
        int x, y, i;

        am.generateAsteriods(dt);
        // Se actualizan todos los tipos de objetos
        for (i = 0; i < objects.size(); i++) {
            obj = objects.get(i);
            obj.update(gc, this, dt);

            // Seguidamente se eliminan aquellos indicados o fuera de la escena
            x = (int) obj.getCenter().x;
            y = (int) obj.getCenter().y;
            if (obj.isDispose() || x < 0 || x > background.getW()
                    || y < 0 || y > background.getH()) {
                objects.remove(i);
                i--;
            }
        }

        // Se calculan los offsets de la camara
        camera.update(gc, this, dt);

        // Se calculan las colisiones para la proxima iteración del motor
        for (i = 0; i < objects.size(); i++) {
            for (int j = i + 1; j < objects.size(); j++) {
                if (objects.get(i).intersects(objects.get(j))) {
                    objects.get(i).getCollisions().push(objects.get(j));
                    objects.get(j).getCollisions().push(objects.get(i));
                }
            }
        }
        
        if (gc.getInput().isKeyDown(KeyEvent.VK_P)) {
            //TODO: Parar el motor y mostrar el menu de juego
            gc.pause(); // BOOM
        }
        if (gc.getInput().isKeyDown(KeyEvent.VK_ESCAPE)) {
            //TODO: Parar el motor y mostrar el menu de juego
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
}
