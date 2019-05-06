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
    private static final int MARGIN_Y = 50;
    private static final int ICON_SEPARATION = 80;
    
    private static final int CD_OFFSET = 2;
    private static final int CD_SIZE = 59;
    private static final int CD_COLOR = 0xa0ffffff;
    
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
    public HUD(Player target) {
        this.target = target;
    }
    
    public HUD() {}

    
    /**
     * Metodo para gestionar la toma de datos de la nave
     * @param dt deltaTime, referencia al tiempo de simulación 
     */
    public void update(float dt) {

    }
    
    /**
     * Metodo para dibujar el hud en pantalla.
     * @param gc GameContainer, permite el acceso a los objetos del motor
     * @param r Renderer, contiene todos los metodos de dibujado del motor
     */
    public void render(GameContainer gc, Renderer r) {
        if (target != null) { 
            // Se resetean los valores de la camara para dibujado fijado
            r.setCamX(0);
            r.setCamY(0);

            // Barra de Salud
            r.drawFillRect(MARGIN_X, 10, 180, 10, 0xffff7d00);
            r.drawFillRect(MARGIN_X, 10, 180,  6, 0xffffd660);

            // Barra de Energia
            r.drawFillRect(MARGIN_X, 30, 100, 10, 0xff0000ff);
            r.drawFillRect(MARGIN_X, 30, 100,  6, 0xff6060ff);

            // Dibujado de iconos
            for (int i = 0; i < Player.NUM_ABILITIES; i++) {
                if (target.getIsActive()[i]) 
                    r.drawFillRect(MARGIN_X + CD_OFFSET, MARGIN_Y + CD_OFFSET + i * ICON_SEPARATION, CD_SIZE, CD_SIZE, 0xffffffff);
                r.drawImageTile(image, MARGIN_X, MARGIN_Y + i * ICON_SEPARATION, i, 0);
                if (target.getCds()[i] > 0) 
                    r.drawFillRect(MARGIN_X + CD_OFFSET, MARGIN_Y + CD_OFFSET + i * ICON_SEPARATION, (int)(CD_SIZE * target.getAbilityCdPercentage(i)), CD_SIZE, CD_COLOR);
            }

            r.drawFillRect(240, gc.getConfig().getScreenHeight() - 42, 180, 15, 0xffff7d00);
            
            r.drawText("Speed:" + String.valueOf((int)(target.getVelocity().getLength() * 25)), 
                       MonoFont.STANDARD, 2, gc.getConfig().getScreenHeight() - 42, 0xffffffff);

            String dumpers = "OFF";
            if (target.getDumpers()) dumpers = "ON";

            r.drawText("Dumps:" + dumpers, MonoFont.STANDARD, 2, gc.getConfig().getScreenHeight() - 20, 0xffff9900);
        }
    }
    
    // Getter & Setter
    public Player getTarget() {return target;}
    public void setTarget(Player target) {this.target = target;}
}
