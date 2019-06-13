package game;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.objects.Player;
import gfx.ImageTile;
import gfx.MonoFont;

/**
 * GameObjet especial como es el caso de Camera. Targetea a player para mostrar 
 * datos relevantes
 * 
 * @author Arturo
 */
public class HUD {
    private static final int MARGIN_X = 10;
    private static final int MARGIN_Y = 10;
    private static final int ICON_SEPARATION = 80;
    
    private static final int CD_OFFSET = 2;
    private static final int CD_SIZE = 59;
    private static final int CD_COLOR = 0xa0ffffff;
    private static final int ACTIVE_COLOR = 0xffffffff;
    
    private static final int BOTTOM1_OFFSET = 20;
    private static final int BOTTOM1_COLOR = 0xff0000ff;
    private static final int BOTTOM1_SHADOW = 0xff6060ff;
    
    private static final int BOTTOM2_OFFSET = 42;
    private static final int BOTTOM2_SHADOW = 0xffffffff;
    
    private static final int SCORE_MAX_CHARS = 8;
    
    private static final int SCORE_MARGIN_X = 150;
    private static final int STATICS_MARGIN_X = 115;
    
    private Player target = null;
    private ImageTile image = new ImageTile("/ships/abilityIcons.png",64,64);
    
    /**
     * Constructor de la clase
     * @param target Objeto del que mostrar el interfaz
     */
    public HUD(Player target) {
        this.target = target;
    }
    
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
                    r.drawFillRect(MARGIN_X + CD_OFFSET, 
                                   MARGIN_Y + CD_OFFSET + i * ICON_SEPARATION, 
                                   CD_SIZE, CD_SIZE, ACTIVE_COLOR);
                r.drawImageTile(image, MARGIN_X, MARGIN_Y + i * ICON_SEPARATION,
                                                            i, target.getId());
                if (target.getCds()[i] > 0) 
                    r.drawFillRect(MARGIN_X + CD_OFFSET, 
                                   MARGIN_Y + CD_OFFSET + i * ICON_SEPARATION, 
                                   (int)(CD_SIZE * target.getAbilityCdPercentage(i)), 
                                   CD_SIZE, CD_COLOR);
            }
            
            // Dibujado de barras de salud y energia
            drawBar(gc, r, target.getHealthPercentage(), 
                    sW / 2 - sW/4, sH - BOTTOM2_OFFSET , 
                    target.getColor(), BOTTOM2_SHADOW);
            drawBar(gc, r, target.getEnergyPercentage(), sW / 2 - sW/4, 
                    sH - BOTTOM1_OFFSET , BOTTOM1_COLOR, BOTTOM1_SHADOW);
            
            // Dibujado de textos
            r.drawText("Speed:" + String.valueOf((int)(target.getVelocity().getLength() * 25)), 
                       MonoFont.STANDARD, 2, sH - BOTTOM2_OFFSET, BOTTOM2_SHADOW);

            String dumpers = "OFF";
            if (target.getDumpers()) dumpers = "ON";

            r.drawText("Dumps:" + dumpers, MonoFont.STANDARD, 2,
                                        sH - BOTTOM1_OFFSET, target.getColor());
            
            // Dibujado fps y ups
            r.drawText("ups:" + gc.ups, MonoFont.STANDARD, sW - STATICS_MARGIN_X,
                                           sH - BOTTOM2_OFFSET, BOTTOM2_SHADOW);
            r.drawText("fps:" + gc.fps, MonoFont.STANDARD, sW - STATICS_MARGIN_X,
                                        sH - BOTTOM1_OFFSET, target.getColor());
            
            // Dibujado de score
            String score = "" + target.getScore();
            int aux = SCORE_MAX_CHARS - score.length();
            for (int i = 0; i < aux; i++) {
                score = "0" + score;
            }
            
            r.drawText(score, MonoFont.STANDARD, sW - SCORE_MARGIN_X, MARGIN_Y, BOTTOM2_SHADOW);
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
