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
    
    
    protected double MaxHealthPoints = 1;
    protected double MaxEnergyPoints = 1;
    protected double energyRegen = 1;
    protected double rotationTolerance;
    
    private float animY = 0;
    private float animX = 0;
    
    protected double fowardsAccel;
    protected double backwardsAccel;
    protected double lateralAccel;
    protected double rotationSpeed;
    
    protected double fire1Cd;
    protected double fire2Cd;
    protected double ability1Cd;
    protected double ability2Cd;

    protected double[] cds = new double[4];
    
    private boolean dumpers; // Mecanismo automatizado de frenada
    
    public Player(int x, int y) {
        this.tag = "";
        
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
        if (Math.abs(dir.getAngle()) < Math.PI - rotationTolerance) {
            if (dir.getAngle() > 0)
                aiming.rotateBy(-rotationSpeed);
            else if (dir.getAngle() < 0) {
                aiming.rotateBy(rotationSpeed);
            }
        }
       
        // Animación por defecto
        int anim_ini = 0;
        int anim_fin = 2;  
        
        if(gc.getInput().isKeyDown(gc.getConfig().getKeyDumpers())) {
            dumpers = !dumpers;
        }
        
        // Gestión de los propulsores por teclado
        boolean up    = gc.getInput().isKey(gc.getConfig().getKeyFoward());
        boolean down  = gc.getInput().isKey(gc.getConfig().getKeyBackward());
        boolean right = gc.getInput().isKey(gc.getConfig().getKeyRight());
        boolean left  = gc.getInput().isKey(gc.getConfig().getKeyLeft());
        
        // Gestión de la animación
        if (up || down || left || right) {
            anim_ini = 2;
            anim_fin = 4;
        }
        animY += dt * 15;
        if (animY >= anim_fin) animY = anim_ini;
        
        // Gestión de velocidad y frenada
        if(up) velocity.add(Vector2.toCartesian(dt * fowardsAccel, aiming.getAngle()));
        if(down) velocity.subtract(Vector2.toCartesian(dt * backwardsAccel, aiming.getAngle()));
        if(left) velocity.subtract(Vector2.toCartesian(dt * lateralAccel, aiming.getPerp().getAngle()));
        if(right) velocity.add(Vector2.toCartesian(dt * lateralAccel, aiming.getPerp().getAngle()));
        if (dumpers || gc.getInput().isKey(gc.getConfig().getKeybrake())) {
            if (!up && !down && !right && !left && velocity.getLength() < 0.04) {
                velocity.set(0, 0);
            }
            if (velocity.project(aiming) > 0 && !up)
                velocity.subtract(Vector2.toCartesian(dt * backwardsAccel, aiming.getAngle()));
            if (velocity.project(aiming) < 0 && !down)
                velocity.add(Vector2.toCartesian(dt * fowardsAccel, aiming.getAngle()));
            if (velocity.project(aiming.getPerp()) > 0 && !right)
                velocity.subtract(Vector2.toCartesian(dt * lateralAccel, aiming.getPerp().getAngle()));
            if (velocity.project(aiming.getPerp()) < 0 && !left)
                velocity.add(Vector2.toCartesian(dt * lateralAccel, aiming.getPerp().getAngle()));
        }
        
        position.add(velocity);
        center.set(position.x + width/2, position.y + height/2);
        
        // Gestión de las habilidades
        abilitiesCode(gc, gm, dt); 
        
        // Recarga de energia
        energyPoints += energyRegen * dt;
        if (energyPoints > MaxEnergyPoints) energyPoints = MaxEnergyPoints;
        
        // Gestión coldDowns
        for (int i=0; i<cds.length; i++){
            if (cds[i] > 0) cds[i] -= dt;
        }
    }   
    
    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawRotatedImageTile((ImageTile) image, (int)position.x, (int)position.y, (int)animX, (int)animY, aiming.getAngle());
    }
    
    @Override
    public void effect(GameObject go) {
        go.setHealthPoints(go.getHealthPoints() - healthPoints);
    }
    
    /**
     * Codigo de gestión de las habilidades a implementar para cada nave
     * @param gc GameContainer, permite el acceso a los objetos del motor
     * @param gm GameManager, permite acceso a todos los objetos del juego
     * @param dt deltaTime, referencia al tiempo de simulación 
     */
    protected abstract void abilitiesCode(GameContainer gc, GameManager gm, float dt); 
    
    // Getters & Setters
    public double getForwardAccel(){return fowardsAccel;}
    public double[] getCds() {return cds;}
    public boolean getDumpers() {return dumpers;}
    
    public void setForwardAccel(double value){fowardsAccel = value;}
}
