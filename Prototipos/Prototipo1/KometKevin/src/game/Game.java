package game;

import engine2D.AbstractGame;
import engine2D.GameContainer;
import engine2D.Renderer;
import gfx.Image;
import gfx.ImageTile;
import java.awt.event.KeyEvent;

/**
 * Futura clase GameManager.
 * 
 * @author Project Kevin
 */
public class Game extends AbstractGame {

    Image testA = new Image("/resources/basicTest.png");
    Image testB = new Image("/resources/alphaTest.png");
    ImageTile testTA = new ImageTile("/resources/basicTest.png",64,64);
    ImageTile testTB = new ImageTile("/resources/alphaTest.png",64,64);
    
    int testMouseX;
    int testMouseY;
    
    int testAnim = 0;
    double testAngle = 0;
    
    public Game() {
        
    }

    @Override
    public void update(GameContainer gc, float dt) {
        testMouseX = gc.getInput().getMouseX();
        testMouseY = gc.getInput().getMouseY();
        
        testAnim = (testAnim + 1) % 4;
        

        if (gc.getInput().isKeyDown(KeyEvent.VK_ESCAPE)) {
            System.exit(0);
        }
        
        if (gc.getInput().isKey(KeyEvent.VK_SPACE)) {
            testAngle += 0.01;
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawRect(200, 200, 30, 70, 0xffff0000);
        r.drawFillRect(200, 400, 30, 20, 0xff0000ff);
        r.drawFillCircle(100, 200, 20, 0xff00ff00);
        
        r.drawImage(testA, 0, 0);
        r.drawImage(testB, 0, 64);
        
        r.drawRotatedImage(testA, testMouseX + 64, testMouseY, testAngle);
        r.drawRotatedImageTile(testTB, testMouseX, testMouseY, testAnim, 0, testAngle);
        
        r.drawLine(0, 0, testMouseX, testMouseY, 0xffff0000);
    }

    public static void main(String args[]) {
        GameContainer gc = new GameContainer(new Game());
        gc.start();
    }
}
