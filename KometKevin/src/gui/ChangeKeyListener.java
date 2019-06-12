package gui;

import engine2D.Config;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Denis
 */
public class ChangeKeyListener implements KeyListener {

    String control;
    MainMenu menu;
    
    public ChangeKeyListener(String control, MainMenu menu) {
        super();
        this.control = control;
        this.menu = menu;
    }
    
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        Config.changeKey(control, e.getKeyCode());
        menu.changeKey();
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    
}
