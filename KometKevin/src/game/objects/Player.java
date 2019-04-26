package game.objects;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.Vector2;
import gfx.ImageTile;

/**
 * Ojo que el eje Y esta invertido, asi de guay es java.
 * @author Project Kevin
 */
public abstract class Player extends GameObject {
    
    private float anim = 0;
    private float anim2 = 0;
    
    protected double fowardsAccel;
    protected double backwardsAccel;
    protected double lateralAccel;
    protected double rotationSpeed;
    
    protected double fire1Cd;
    protected double fire2Cd;
    protected double hability1Cd;
    protected double hability2Cd;

    public double[] cds = new double[4];
    
    private boolean dumpers; // Mecanismo automatizado de frenada
    
    public Player(int x, int y) {
        this.tag = "player";
        
        this.dumpers = true;
        this.position = new Vector2(x, y);
        this.center = new Vector2(x + width/2, y + + height/2);
        this.velocity = new Vector2(0,0);
        this.aiming = new Vector2(1,0); // Todas las naves apuntan hacia la der
        
        collCode = CollisionCodes.TEAM1.getValue();
        collides = CollisionCodes.TEAM1_COL.getValue();
        
        for (int i=0; i<cds.length;i++){
            cds[i] = 0;
        }
    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) { 
        super.update(gc, gm, dt);
   
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
        
        // Gestión de las habilidades
        if(gc.getInput().isButton(gc.getConfig().getPrimaryFire()) && cds[0] <= 0 ) {
            cds[0] = fire1(gm);
        }
        if(gc.getInput().isButton(gc.getConfig().getSecondaryFire()) && cds[1] <=0 ) {
            cds[1] = fire2(gm);
        }
        if(gc.getInput().isKey(gc.getConfig().getKeyHability1()) && cds[2] <=0 ) {
            cds[2] = hability1(gm);
        }
        if(gc.getInput().isKey(gc.getConfig().getKeyHability2()) && cds[3] <=0 ) {
            cds[3] = hability2(gm);
        }  
        
        // Gestión de la animación
        anim += dt * 15;
        if (anim >= anim_fin) anim = anim_ini;
        
        // Gestión coldDowns
        for (int i=0; i<cds.length; i++){
            if (cds[i] > 0) cds[i] -= dt;
        }
    }   
    
    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawRotatedImageTile((ImageTile) image, (int)position.x, (int)position.y, (int)anim2, (int)anim, aiming.getAngle());
        
//        // Vertices de los dos rectangulos
//        Vector2 verticesA[] = new Vector2[4];
//        
//        double max = (int) (Math.sqrt(72 * 72 + 46 * 46) / 2);
//        double angle = Math.atan(72 / 46);
//        
//        verticesA[0] = Vector2.toCartesian(max, aiming.getAngle() + angle + Math.PI/2);
//        verticesA[1] = Vector2.toCartesian(max, aiming.getAngle() - angle + Math.PI/2);
//        verticesA[2] = verticesA[0].getReversed();
//        verticesA[3] = verticesA[1].getReversed();
//        
//        for (int i = 0; i < 4; i++) {
//            verticesA[i].add(center);
//            r.setPixel((int)verticesA[i].x, (int)verticesA[i].y, 0xffff0000);
//        }
//        
//        r.drawFillRect(10, 10, 180, 10, 0xffff7d00);
//        r.drawFillRect(10, 10, 180,  6, 0xffffd660);
//        
//        r.drawFillRect(10, 30, 100, 10, 0xff0000ff);
//        r.drawFillRect(10, 30, 100,  6, 0xff6060ff);
    }
    
    @Override
    public void effect(GameObject go) {
        go.setHealthPoints(go.getHealthPoints() - Double.MAX_VALUE);
    }
    
    // Habilidades a implementar en cada una de las naves
    public abstract double fire1(GameManager gm);
    public abstract double fire2(GameManager gm);
    public abstract double hability1(GameManager gm);
    public abstract double hability2(GameManager gm);
    
    // Getters & Setters
    public void setForwardAccel(double value){fowardsAccel = value;}
    public double getForwardAccel(){return fowardsAccel;}
    public void addForwardAccel(double value){fowardsAccel += value;}   
    public void subForwardAccel(double value){fowardsAccel -= value;}
}
