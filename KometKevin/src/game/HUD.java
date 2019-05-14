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
    private static final int MARGIN_Y = 10;
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
            int sH = gc.getConfig().getScreenHeight();
            int sW = gc.getConfig().getScreenWidth();
            
            // Se resetean los valores de la camara para dibujado fijado
            r.setCamX(0);
            r.setCamY(0);

            // Dibujado de iconos
            for (int i = 0; i < Player.NUM_ABILITIES; i++) {
                if (target.getIsActive()[i]) 
                    r.drawFillRect(MARGIN_X + CD_OFFSET, MARGIN_Y + CD_OFFSET + i * ICON_SEPARATION, CD_SIZE, CD_SIZE, 0xffffffff);
                r.drawImageTile(image, MARGIN_X, MARGIN_Y + i * ICON_SEPARATION, i, 0);
                if (target.getCds()[i] > 0) 
                    r.drawFillRect(MARGIN_X + CD_OFFSET, MARGIN_Y + CD_OFFSET + i * ICON_SEPARATION, (int)(CD_SIZE * target.getAbilityCdPercentage(i)), CD_SIZE, CD_COLOR);
            }
            
            // Dibujao de barras
            drawBar(gc, r, target.getHealthPercentage(), sW / 2 - sW/4, sH - 42, 0xffff7d00, 0xffffd660);
            drawBar(gc, r, target.getEnergyPercentage(), sW / 2 - sW/4, sH - 20, 0xff0000ff, 0xff6060ff);
            
            // Dibujado de textos
            r.drawText("Speed:" + String.valueOf((int)(target.getVelocity().getLength() * 25)), 
                       MonoFont.STANDARD, 2, sH - 42, 0xffffffff);

            String dumpers = "OFF";
            if (target.getDumpers()) dumpers = "ON";

            r.drawText("Dumps:" + dumpers, MonoFont.STANDARD, 2, sH - 20, 0xffff9900);
            
            // Dibujado de score
            String score = "" + target.getScore();
            int aux = 8 - score.length();
            for (int i = 0; i < aux; i++) {
                score = "0" + score;
            }
            
            r.drawText(score, MonoFont.STANDARD, sW - 150, MARGIN_Y, 0xffffffff);
        }
    }
    
    public void drawBar(GameContainer gc, Renderer r, double percentage, int offX, int offY, int color, int color2) {
        int y = 0;
        int x = 0;
        
        int width = gc.getConfig().getScreenWidth()/2;
        int height = 15;        
        
        while(y <= height) {
            x = 0;
            while (x <= width) {
                if ((x % 8 < 5 || x % 8 > 7) && x <= width * percentage) {
                    if (y > 6)
                        r.setPixel(x + offX, y + offY, color);
                    else
                        r.setPixel(x + offX, y + offY, color2);
                }
                x++;
            } 
            y++;
        }
    }
    
    // Getter & Setter
    public Player getTarget() {return target;}
    public void setTarget(Player target) {this.target = target;}
}
