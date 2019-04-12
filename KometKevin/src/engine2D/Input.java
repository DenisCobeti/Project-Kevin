package engine2D;

import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

/**
 * Clase encargada de todo el control de los perifericos de entrada.
 * 
 * @author Project Kevin
 */
public class Input implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {   
    private int camX, camY;
    
    private int NUM_KEYS = 256;
    private boolean[] keys = new boolean[NUM_KEYS];
    private boolean[] keysLast = new boolean[NUM_KEYS];
    
    private int NUM_BUTTONS = 5;
    private boolean[] buttons = new boolean[NUM_BUTTONS];
    private boolean[] buttonsLast = new boolean[NUM_BUTTONS];
    
    private float scale;
    private int mouseX, mouseY;
    private int scroll;
    
    public Input(Canvas canvas) {
        scale = Config.getInstance().getScale();
        mouseX = 0;
        mouseY = 0;
        scroll = 0;
        
        canvas.addKeyListener(this);
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addMouseWheelListener(this);
    }
    
    public void update() {
        System.arraycopy(keys, 0, keysLast, 0, NUM_KEYS);
        System.arraycopy(buttons, 0, buttonsLast, 0, NUM_BUTTONS);
    }
    
    public boolean isKey(int keyCode) {
        return keys[keyCode];
    }
    
    public boolean isKeyUp(int keyCode) {
        return !keys[keyCode] && keysLast[keyCode];
    }
    
    public boolean isKeyDown(int keyCode) {
        return keys[keyCode] && !keysLast[keyCode];
    }
    
    public boolean isButton(int Button) {
        return buttons[Button];
    }
    
    public boolean isButtonUp(int Button) {
        return !buttons[Button] && buttonsLast[Button];
    }
    
    public boolean isButtonDown(int Button) {
        return !buttons[Button] && !buttonsLast[Button];
    }


    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        buttons[e.getButton()] = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        buttons[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = (int)(e.getX() / scale);
        mouseY = (int)(e.getY() / scale);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = (int)(e.getX() / scale);
        mouseY = (int)(e.getY() / scale);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll = e.getWheelRotation();
    }

    // Getters & Setters
    public int getMouseX() {return mouseX + camX;}
    public int getMouseY() {return mouseY + camY;}
    public int getScroll() {return scroll;}

    public void setCamX(int camX) {this.camX = camX;}
    public void setCamY(int camY) {this.camY = camY;}
}
