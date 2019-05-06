package game.colliders;

import game.Vector2;
import game.objects.GameObject;

/**
 * Colisionador rectangular con dirección. 
 * Naves y habilidades. Coste computacional alto.
 * 
 * @author Project Kevin
 */
public class BoxCollider extends AbstractCollider {
    private Vector2 aiming; 
    
    private int width, height;
    private int min, max;
    private double angle;
    
    public BoxCollider(GameObject go, int width, int height) {
        this.object = go;
        this.center = go.getCenter();
        this.aiming = go.getAiming();
        this.width  = width;
        this.height = height;
        min = (int) (Math.min(width, height) / 2);
        max = (int) (Math.sqrt(width * width + height * height) / 2);
        angle = Math.atan(width / height);
    }
    
    @Override
    public boolean intersects(CircleCollider bc) {
        return bc.intersects(this);
    }
    
    @Override
    public boolean intersects(BoxCollider bc) {
        double distance = this.center.distance(bc.center);
        
        // Comprobación rápida para saber que no estan demasiado lejos o cerca
        if ((this.max + bc.max) < distance) return false;
        if ((this.min + bc.min) > distance) return true;
        
        // Vertices de los dos rectangulos
        Vector2 verticesA[] = new Vector2[4];
        Vector2 verticesB[] = new Vector2[4];
        
        Vector2 dots[] = new Vector2[4];
        
        double minA, maxA, minB, maxB;
        double auxA, auxB;
        
        verticesA[0] = Vector2.toCartesian(max, aiming.getAngle() + angle + Math.PI/2);
        verticesA[1] = Vector2.toCartesian(max, aiming.getAngle() - angle + Math.PI/2);
        verticesA[2] = verticesA[0].getReversed();
        verticesA[3]= verticesA[1].getReversed();
        
        verticesB[0] = Vector2.toCartesian(bc.max, bc.aiming.getAngle() + bc.angle + Math.PI/2);
        verticesB[1] = Vector2.toCartesian(bc.max, bc.aiming.getAngle() - bc.angle + Math.PI/2);
        verticesB[2] = verticesB[0].getReversed();
        verticesB[3] = verticesB[1].getReversed();
        
        dots[0] = aiming.clone();
        dots[1] = aiming.getPerp();
        dots[2] = bc.aiming.clone();
        dots[3] = bc.aiming.getPerp();
        
        for (int n = 0; n < 4; n++) {
            minA = Integer.MAX_VALUE;
            maxA = Integer.MIN_VALUE;
            minB = Integer.MAX_VALUE;
            maxB = Integer.MIN_VALUE;

            for (int i = 0; i < 4; i++) {
                auxA = verticesA[i].getAdded(center).dot(dots[n]);
                auxB = verticesB[i].getAdded(bc.center).dot(dots[n]);

                if (auxA > maxA) maxA = auxA;
                if (auxA < minA) minA = auxA;
                if (auxB > maxB) maxB = auxB;
                if (auxB < minB) minB = auxB;
            }
            if ((maxA < minB) || (maxB < minA)) return false;
        }
        return true;
    }
    
    @Override
    public boolean intersects(RayCollider bc) {
        return bc.intersects(this);
    }
    
    // Getters y Setters
    public int getMin() {return min;}
    public int getMax() {return max;}

    public Vector2 getAiming() {return aiming;}
    public int getWidth() {return width;}
    public int getHeight() {return height; }
    public double getAngle() {return angle;}
}
    
