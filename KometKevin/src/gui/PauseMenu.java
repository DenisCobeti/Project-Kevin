package gui;

import engine2D.Window;
import static gui.MainMenu.FONT_SIZE;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author Denis
 */
public class PauseMenu extends GamePopup{
    
    private final int SPACE_BETWEEN_MENUS = SCREEN_HEIGHT/50;
    
    private static final String EXIT_TEXT = "Exit game";
    private static final String EXIT_MENU = "Exit to menu";
    private static final String RESUME = "Resume";
    
    public JLabel resume;
    public JLabel exit;
    public JLabel exitMenu;

    public PauseMenu(Window window) {
        super();
        this.setSize(SCREEN_WIDTH/3, SCREEN_HEIGHT/5);
        
        Box box = Box.createVerticalBox();
        Dimension buttonSize = new Dimension(SCREEN_WIDTH/3, SCREEN_HEIGHT/20);
        
        exit = initMenuButton(EXIT_TEXT, buttonSize);
        resume = initMenuButton(RESUME, buttonSize);
        exitMenu = initMenuButton(EXIT_MENU, buttonSize);
        
        exitMenu.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                window.returnMenu(get());
            }
        });
        resume.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                window.unpauseGame();
            }
        });
        exit.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                window.exitGame();
            }
        });
        box.add(resume);
        box.add(Box.createVerticalStrut(SPACE_BETWEEN_MENUS));
        box.add(exitMenu);
        box.add(Box.createVerticalStrut(SPACE_BETWEEN_MENUS));
        box.add(exit);
        this.setLocationRelativeTo(window);
        this.add(box);
    }
    private JLabel initMenuButton(String text, Dimension size){
        
        JLabel label = new JLabel(text);
        //Dimension dimension = new Dimension(SCREEN_WIDTH/5, SCREEN_HEIGHT/20);
        //Color labelBackground = new Color(0, 0, 0, 220);
        
        label.setPreferredSize(size);
        label.setMaximumSize(size);
        label.setMinimumSize(size);
        
        label.setFont(MainMenu.FONT.deriveFont(FONT_SIZE)); // NOI18N
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(Color.BLACK);
        
        //Hover listeners
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
            }
            @Override
            public void mouseExited(MouseEvent evt) {
                label.setBackground(Color.BLACK);
                label.setForeground(Color.WHITE);
            }
        });
        
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        
        return label;
    }
    
    private PauseMenu get(){
        return this;
    }
}
