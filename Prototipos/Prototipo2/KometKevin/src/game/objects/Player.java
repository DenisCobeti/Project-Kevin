package game.objects;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.Vector2;
import game.colliders.BoxCollider;
import gfx.ImageTile;

/**
 * Ojo que el eje Y esta invertido, asi de guay es java.
 * 
 * @author Project Kevin
 */
public class Player extends GameObject {
    private float anim = 0;
    private float anim2 = 0;
    
    private double fowardsAccel = 2.9;
    private double backwardsAccel = 1.9;
    private double lateralAccel = 1.1;
    private double rotationSpeed = 0.015;
    
    private double coldDownF1 = 0;
    private double coldDownF2 = 0;
    private double coldDownH1 = 0;
    private double coldDownH2 = 0;
    
    private boolean dumpers; // Mecanismo automatizado de frenada
    
    public Player(int x, int y) {
        this.tag = "player";
        this.image = new ImageTile("/ships/hammerShip.png",120,64);

        this.width = ((ImageTile) image).getTileW();
        this.height = ((ImageTile) image).getTileH();
        
        this.dumpers = true;
        this.position = new Vector2(x, y);
        this.center = new Vector2(x + width/2, y + + height/2);
        this.velocity = new Vector2(0,0);
        this.aiming = new Vector2(1,0); // Todas las naves apuntan hacia la der
        
        collCode = CollisionCodes.TEAM1.getValue();
        collides = CollisionCodes.TEAM1_COL.getValue();
        this.collider = new BoxCollider(this, 112, 52);
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {       
        // Gestión del autoaiming al raton
        Vector2 dir = new Vector2(gc.getInput().getMouseX(), gc.getInput().getMouseY());
        dir.subtract(center);
        dir.rotateBy(Math.PI - aiming.getAngle());
        if (Math.abs(dir.getAngle()) < Math.PI - 0.01) {
            if (dir.getAngle() > 0)
                aiming.rotateBy(-rotationSpeed);
            else if (dir.getAngle() < 0) {
                aiming.rotateBy(rotationSpeed);
            }
        }
       
        // Animación por defecto
        int anim_ini = 0;
        int anim_fin = 2;  
        
        // Gestión de los propulsores por teclado
        double aux = 0;
        
        if(gc.getInput().isKey(gc.getConfig().getKeyFoward())) {
            velocity.add(Vector2.toCartesian(
                dt * fowardsAccel, aiming.getAngle()));    
            anim_ini = 2;
            anim_fin = 4;
        } else if(dumpers && velocity.getLength() > 0) {
//            aux = velocity.project(aiming.getPerp());
//            if (aux > dt * fowardsAccel) aux = dt * fowardsAccel;
//            velocity.add(Vector2.toCartesian(aux, aiming.getAngle())); 
        }
        if(gc.getInput().isKey(gc.getConfig().getKeyBackward())) {
            velocity.subtract(Vector2.toCartesian(
                dt * backwardsAccel, aiming.getAngle()));
            anim_ini = 2;
            anim_fin = 4;
        } else if(dumpers && velocity.getLength() > 0) {
            
        }
        if(gc.getInput().isKey(gc.getConfig().getKeyLeft())) {
            velocity.subtract(Vector2.toCartesian(
                dt * lateralAccel, aiming.getPerp().getAngle()));
             anim_ini = 2;
            anim_fin = 4;
        } else if(dumpers && velocity.getLength() > 0) {
            
        }
        if(gc.getInput().isKey(gc.getConfig().getKeyRight())) {
            velocity.add(Vector2.toCartesian(
                dt * lateralAccel, aiming.getPerp().getAngle()));
            anim_ini = 2;
            anim_fin = 4;
        } else if(dumpers && velocity.getLength() > 0) {
            
        }
        
        if(gc.getInput().isKey(gc.getConfig().getKeyDumpers())) {
            velocity.set(0, 0);
        }
        
        position.add(velocity);
        center.set(position.x + width/2, position.y + height/2);
        
        if(gc.getInput().isButton(gc.getConfig().getPrimaryFire())){
            if (coldDownF1 <= 0) {
                Projectile fire = new Projectile((int)center.x, (int)center.y);
                fire.setVelocity(velocity.getAdded(aiming.getMultiplied(14)));
                fire.setAiming(aiming.clone());
                gm.getObjects().add(0,fire);
                coldDownF1 = 0.25;
            }
        }
        
        if(gc.getInput().isButton(gc.getConfig().getSecondaryFire())){
            // TODO: piun piun
        }
        
        if(gc.getInput().isKey(gc.getConfig().getKeyHability1())) {
            // TODO: piun piun
            anim2=1;
        }
        
        if(gc.getInput().isKey(gc.getConfig().getKeyHability2())) {
            // TODO: piun piun
            anim2=2;
        }

        // Gestión de la animación
        anim += dt * 15;
        if (anim >= anim_fin) anim = anim_ini;
        
        // Gestión coldDowns
        if (coldDownF1 > 0) coldDownF1 -= dt;
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawRotatedImageTile((ImageTile) image, (int)position.x, (int)position.y, (int)anim2, (int)anim, aiming.getAngle());
        
        // Vertices de los dos rectangulos
        Vector2 verticesA[] = new Vector2[4];
        
        double max = (int) (Math.sqrt(112 * 112 + 52 * 52) / 2);
        double angle = Math.atan(112 / 52);
        
        verticesA[0] = Vector2.toCartesian(max, aiming.getAngle() + angle + Math.PI/2);
        verticesA[1] = Vector2.toCartesian(max, aiming.getAngle() - angle + Math.PI/2);
        verticesA[2] = verticesA[0].getReversed();
        verticesA[3] = verticesA[1].getReversed();
        
        for (int i = 0; i < 4; i++) {
            verticesA[i].add(center);
            r.setPixel((int)verticesA[i].x, (int)verticesA[i].y, 0xffff0000);
        }
        
        r.drawFillRect(10, 10, 180, 10, 0xffff7d00);
        r.drawFillRect(10, 10, 180,  6, 0xffffd660);
        
        r.drawFillRect(10, 30, 100, 10, 0xff0000ff);
        r.drawFillRect(10, 30, 100,  6, 0xff6060ff);
    }
}
