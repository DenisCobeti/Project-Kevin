/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.objects.ships.sekul;

import engine2D.GameContainer;
import engine2D.Renderer;
import game.GameManager;
import game.Vector2;
import game.colliders.CircleCollider;
import game.objects.CollisionCodes;
import game.objects.GameObject;
import game.objects.Player;
import game.objects.ships.AdvancedAbility;
import game.objects.ships.Projectile;
import gfx.ImageTile;
import java.util.LinkedList;

/**
 *
 * @author 
 */

//Si disparas de seguido, la lista de asteroides affectados se renueva antes de que acabe acabe el efecto de la habilidad...
//... y le afecta directamente con el 2º disparo nada mas disparar
 
public class RepulsorWave extends AdvancedAbility {
    
    private int speed;
    private GameObject owner;
    private float time;
    private int range;
    private LinkedList<GameObject> affected;
    
    public RepulsorWave(Player owner, int range){
        this.range = range;
        this.owner = owner;
        this.speed = 5;
        affected = new LinkedList<>();
            
        this.tag = "RepulsorWave";
        image = new ImageTile("/projectiles/shield.png",136,136);
        width = ((ImageTile) image).getTileW();
        height = ((ImageTile) image).getTileH();
        center = owner.getCenter().clone();
        position = owner.getPosition().clone();
        aiming = owner.getAiming().clone();

        this.collider = new CircleCollider(this,68);
        
        collCode = CollisionCodes.FIRE1.getValue();
        collides = CollisionCodes.FIRE1_COL.getValue();
        
        time = 0;
        active = false;
    }
   
    @Override 
    public void update(GameContainer gc, GameManager gm, float dt){
        if(active){
            //super.update(gc, gm, dt);   //para que solo afecte al 1º
            while(!collisions.empty()) {
               effect(collisions.pop());
            }
            this.position.add(velocity);
            center.set(position.x + width/2, position.y + height/2);
            aiming.set(velocity.getNormalized());
            time += dt;
            if(time >= range){
                active = false;
                time = 0;
            }
        }
    }
    
    @Override
    public double activate(double cd){
        if(!active){
            center = owner.getCenter().clone();
            position = owner.getPosition().clone();
            aiming = owner.getAiming().clone();
            this.collider = new CircleCollider(this,68);
            
            setVelocity(owner.getVelocity().getAdded(owner.getAiming().getMultiplied(10)));
            setAiming(owner.getAiming().clone());
            
            affected.clear();
            
            active = true;
        }
        return 0.0;
    }
    
    @Override
    public void effect(GameObject go){
        if(!affected.contains(go)){
            go.setVelocity(go.getVelocity().getMultiplied(-1));
            affected.add(go);
        }
        //go.setHealthPoints(go.getHealthPoints() - 1);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        if (active){
            r.drawRotatedImageTile((ImageTile)image, (int)position.x - 8, (int)position.y - 36, (int)0, 0, aiming.getAngle());
        }
    }
    
}
