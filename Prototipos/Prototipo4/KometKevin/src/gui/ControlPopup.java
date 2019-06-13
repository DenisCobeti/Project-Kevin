package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;

/**
 *
 * @author Denis
 */
public class ControlPopup extends GamePopup{
    private static final String CHANGE_TEXT = "Press a key...";
    

    public ControlPopup() {
        super();
        this.setPopupSize(SCREEN_WIDTH/3, SCREEN_HEIGHT/3);
        this.setLayout(new BorderLayout(100, 100));
        
        Dimension buttonSize = new Dimension(SCREEN_WIDTH/10, SCREEN_HEIGHT/20);
        JLabel change = initMenuButton(CHANGE_TEXT, buttonSize, false);
        this.add(change, BorderLayout.CENTER);
        
    }
}
