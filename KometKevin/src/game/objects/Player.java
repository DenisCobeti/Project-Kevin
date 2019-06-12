package game.objects;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.Vector2;
import game.objects.ships.Ships;
import gfx.ImageTile;

/**
 * Clase player. Incluye la movilidad comun a todas las naves.
 * 
 * @author Arturo
 */
public abstract class Player extends GameObject {
    
    public static final int NUM_ABILITIES = 4;
    
    protected int id = 0;
    protected int color = 0xffffffff;
    
    protected double maxHealthPoints = 1;
    protected double maxEnergyPoints = 1;
    protected double energyRegen = 1;
    protected double rotationTolerance;
    
    protected float animY = 0;
    protected float animX = 0;
    
    protected double fowardsAccel;
    protected double backwardsAccel;
    protected double lateralAccel;
    protected double rotationSpeed;
    
    protected boolean[] isActive = new boolean[NUM_ABILITIES];
    protected double[] energyCost = new double[NUM_ABILITIES];
    protected double[] cdValues = new double[NUM_ABILITIES];
    protected double[] cds = new double[NUM_ABILITIES];
    
    private double score = 0;
    private boolean dumpers; // Mecanismo automatizado de frenada
    
    /**
     * Constructor de la clases
     * @param x posición x
     * @param y posición y
     */
    public Player(int x, int y) {
        this.tag = "";
        
        this.dumpers = true;
        this.position = new Vector2(x, y);
        this.center = new Vector2(x + width/2, y + + height/2);
        this.velocity = new Vector2(0,0);
        this.aiming = new Vector2(1,0); // Todas las naves apuntan hacia la der
        
        collCode = CollisionCodes.TEAM1.getValue();
        collides = CollisionCodes.TEAM1_COL.getValue();
        
        for (int i = 0; i < NUM_ABILITIES; i++){
            isActive[i] = false;
            energyCost[i] = 1;
            cdValues[i] = 1;
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
        score += dt;
        energyPoints += energyRegen * dt;
        if (energyPoints > maxEnergyPoints) energyPoints = maxEnergyPoints;
        
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
    public double getHealthPercentage() {
        return healthPoints / maxHealthPoints;
    }
    
    public double getEnergyPercentage() {
        return energyPoints / maxEnergyPoints;
    }
    
    public double getAbilityCdPercentage(int index) {
        return cds[index] / cdValues[index];
    }

    public int getId() {return id;}
    public int getColor() {return color;}
    
    public double getForwardsAccel(){return fowardsAccel;}
    public double getBackwardsAccel() {return backwardsAccel;}
    public double getLateralAccel() {return lateralAccel;}
    public double getMaxEnergyPoints() {return maxEnergyPoints;}
    public double getMaxHealthPoints() {return maxHealthPoints;}
    
    
    public boolean[] getIsActive() {return isActive;}
    public double[] getEnergyCost() {return energyCost;}
    public double[] getCds() {return cds;}
    public double[] getCdValues() {return cdValues;}
    
    public boolean getDumpers() {return dumpers;}
    public int getScore() {return (int)score;}
    
    public void setForwardsAccel(double value){this.fowardsAccel = value;}
    public void setBackwardsAccel(double backwardsAccel) {this.backwardsAccel = backwardsAccel;}
    public void setLateralAccel(double lateralAccel) {this.lateralAccel = lateralAccel;}
    
    public void setScore(int score) {this.score = score;}

    
}
