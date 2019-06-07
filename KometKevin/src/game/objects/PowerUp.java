package game.objects;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.colliders.BoxCollider;
import gfx.ImageTile;
import game.Vector2;
import game.colliders.CircleCollider;
import gfx.Image;
import javax.swing.text.Position;

/**
 * @author
 * Cambiar el valor total de los cds (cdValues) puede dar problemas
 */

public class PowerUp extends GameObject {
    
    private PowerUpType type;
    private double value;
    private int anim = 0;
    
    private boolean temporal;
    private boolean picked;
    private float duration;
    private float time;
    
    private Player player;
    
    public PowerUp(int x, int y, PowerUpType type, double value, Image image, GameManager gm) {
        this.tag = "asteroid";
        this.image = image;

        this.width = image.getW();
        this.height = image.getH();
        
        this.type = type;
        
        this.position = new Vector2(x, y);
        this.center = new Vector2(x + width/2, y + + height/2);
        this.velocity = new Vector2(0,0);
        
        this.collCode = CollisionCodes.ASTEROID.getValue();
        this.collides = CollisionCodes.ASTEROID_COL.getValue();
        this.collider = new CircleCollider(this, 26);
        
        this.temporal = false;
        this.value = value;
        this.picked = false;
        
        gm.getObjects().add(this);
        
    }
    
    public PowerUp(int x, int y, PowerUpType type, double value, float duration, Image image, GameManager gm) {
        this.tag = "asteroid";
        this.image = image;

        this.width = image.getW();
        this.height = image.getH();
        
        this.type = type;
        
        this.position = new Vector2(x, y);
        this.center = new Vector2(x + width/2, y + + height/2);
        this.velocity = new Vector2(0,0);
        
        this.collCode = CollisionCodes.ASTEROID.getValue();
        this.collides = CollisionCodes.ASTEROID_COL.getValue();
        this.collider = new CircleCollider(this, 26);
        
        this.temporal = true;
        this.value = value;
        this.duration = duration;
        this.picked = false;
        
        gm.getObjects().add(this);
    }
    
    public void setType(PowerUpType type){
        this.type = type;
    }
    
    public void setValue(int value){
        this.value = value;
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        if (!picked){
            r.drawRotatedImage(image, (int)position.x, (int)position.y, 0);
        }
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt){
        while(!collisions.empty() && !picked) {
            effect(collisions.pop());
        }
        
        if(temporal && picked){
            //System.out.println(time);
            time += dt;
            if(time >= duration){
                value = value*(-1);
                temporal = false;
                effect(player);
            }
        }        
    }
    
    @Override
    public void effect(GameObject go) {     
        if (go instanceof Player && !picked){
            player = ((Player)go);
            switch (type) {
                case SCORE:
                    player.setScore(player.getScore()+(int)value);
                    picked = true;
                    break;
                
                case CD0:
                    player.getCdValues()[0]-=player.getCdValues()[0]*(1+value);
                    picked = true;
                    break;
                
                case CD1:
                    player.getCds()[1]-=player.getCds()[1]*(value);
                    picked = true;
                    break;
                
                case CD2:
                    player.getCds()[2]-=player.getCds()[2]*(value);
                    picked = true;
                    break;
                
                case CD3:
                    player.getCds()[3]-=player.getCds()[3]*(value);
                    picked = true;
                    break;

                case VELOCITY:
                    player.setForwardsAccel(player.getForwardsAccel()*(1+value));
                    picked = true;
                    break;

                case LIFE:
                    go.setHealthPoints(go.getHealthPoints()+value);
                    picked = true;
                    break;

                default:
                    picked = true;
                    break;
            }
        }
    }
}
