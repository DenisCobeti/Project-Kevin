package game;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.objects.Player;
import gfx.ImageTile;
import gfx.MonoFont;

/**
 * Esto sera un gameObjet especial como es el caso de Camera
 * @author Project Kevin
 */
public class HUD {
    private static final int MARGIN_X = 10;
    private static final int MARGIN_Y = 1;
    private static final int ICON_SEPARATION = 1;
    private static final int BARS_SEPARATION = 1;
    private static final int ICON_BARS_SEPARATION = 1;
    private static final int TEXT_OFFSET_X = 1;
    private static final int TEXT_OFFSET_Y = 1;
    
    private static final int BAR_LENGHT = 1;
    
    private Player target = null;
    private ImageTile image = new ImageTile("/ships/abilityIcons.png",64,64);
    
    /**
     * Constructor de la clase
     * @param target Objeto del que mostrar el interfaz
     */
    public HUD(Player target){
        this.target = target;
    }

    public void update(GameContainer gc, GameManager gm, float dt) {

    }
    
    public void render(GameContainer gc, Renderer r) {
        // Se resetean los valores de la camara para dibujado fijado
        r.setCamX(0);
        r.setCamY(0);
        
        // Barra de Salud
        r.drawFillRect(MARGIN_X, 10, 180, 10, 0xffff7d00);
        r.drawFillRect(MARGIN_X, 10, 180,  6, 0xffffd660);
        
        // Barra de Energia
        r.drawFillRect(MARGIN_X, 30, 100, 10, 0xff0000ff);
        r.drawFillRect(MARGIN_X, 30, 100,  6, 0xff6060ff);
        
        r.drawImageTile(image, MARGIN_X, 50,  0, 0);
        if (target != null) 
            if (target.getCds()[0] > 0) r.drawFillRect(MARGIN_X + 2, 50 + 2, 59, 59, 0xa0ffffff);
            
        r.drawImageTile(image, MARGIN_X, 130, 1, 0);
        r.drawImageTile(image, MARGIN_X, 210, 2, 0);
        r.drawImageTile(image, MARGIN_X, 290, 3, 0);
        
        if (target != null) 
        r.drawText("Speed:" + String.valueOf((int)(target.getVelocity().getLength() * 25)), 
                   MonoFont.STANDARD, 2, gc.getConfig().getScreenHeight() - 42, 0xffffffff);
        
        String dumpers = "OFF";
        if (target != null) 
            if (target.getDumpers()) dumpers = "ON";
        
        r.drawText("Dumps:" + dumpers, MonoFont.STANDARD, 2, gc.getConfig().getScreenHeight() - 20, 0xffff9900);
    }   
}
