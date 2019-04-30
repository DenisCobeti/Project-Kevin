package game.colliders;

import game.objects.GameObject;

/**
 * Colisionador circular. 
 * Asteroides y habilidades. Coste computacional muy bajo.
 * 
 * @author Project Kevin 
 */
public class CircleCollider extends AbstractCollider {
    private int radius;
    
    public CircleCollider(GameObject go, int radius) {
        this.object = go;
        this.center = go.getCenter();
        this.radius = radius;
    }
    
    @Override
    public boolean intersects(CircleCollider bc) {
        return radius + bc.radius >= center.distance(bc.center);
    }

    @Override
    public boolean intersects(BoxCollider bc) {       
        double distance = this.center.distance(bc.center);
        
        // Comprobación rápida para saber que no estan demasiado lejos o cerca
        if ((radius + bc.getMax()) < distance) return false;
        if ((radius + bc.getMin()) > distance) return true;
        
        double angle = -bc.getAiming().getAngle();
        double closestX, closestY;
        double dX, dY;
        
	// Rotamos el punto central del circulo
	double unrotatedCircleX = Math.cos(angle) * (center.x - bc.center.x) - 
                                  Math.sin(angle) * (center.y - bc.center.y) + 
                                  bc.center.x;
	double unrotatedCircleY = Math.sin(angle) * (center.x - bc.center.x) + 
                                  Math.cos(angle) * (center.y - bc.center.y) + 
                                  bc.center.y;

	// Buscamos el punto más cercano al centro del circulo     
	if ( unrotatedCircleX < (bc.center.x - (bc.getWidth()/2.0f))) {
		closestX = bc.center.x - (bc.getWidth()/2.0f);
	} else if ( unrotatedCircleX > (bc.center.x + (bc.getWidth()/2.0f))) {
		closestX = bc.center.x + (bc.getWidth()/2.0f);
	} else {
		closestX = unrotatedCircleX;
	}
	if ( unrotatedCircleY < (bc.center.y - (bc.getHeight()/2.0f))) {
		closestY = bc.center.y - (bc.getHeight()/2.0f);
	} else if ( unrotatedCircleY > (bc.center.y + (bc.getHeight()/2.0f))) {
		closestY = bc.center.y + (bc.getHeight()/2.0f);
	} else {
		closestY = unrotatedCircleY;
	}

	dX = unrotatedCircleX - closestX;
	dY = unrotatedCircleY - closestY;
	return Math.sqrt((dX * dX) + (dY * dY)) < radius;   
    }
    
    @Override
    public boolean intersects(RayCollider bc) {
        return bc.intersects(this);
    }
    
    // Getters
    public int getRadius() {return radius;}
    public void setRadius(int radius) {this.radius = radius;}
    
}
