package game.colliders;

import game.objects.GameObject;
import game.Vector2;

/**
 * Colisionador de recta.
 * Habilidades especiales basadas en lasers. Coste computacional bajo.
 * 
 * @author Arturo
 */
public class RayCollider extends AbstractCollider {
    private Vector2 aiming; 
    
    public RayCollider(GameObject go) {
        this.object = go;
        this.center = go.getCenter();
        this.aiming = go.getAiming();
    }

    @Override
    public boolean intersects(CircleCollider bc) {
        Vector2 dir = bc.center.getSubtracted(center);

        if (dir.getRotatedBy(Math.PI/2 - aiming.getAngle()).getAngle() > 0)
            return aiming.getProjectedVector(dir).distance(dir) <= bc.getRadius();
        else
            return false;
    }

    @Override
    public boolean intersects(BoxCollider bc) {       
        // Vertices de los dos "rectangulos"
        Vector2 verticesA[] = new Vector2[4];
        Vector2 verticesB[] = new Vector2[4];
        
        Vector2 dots[] = new Vector2[4];
        
        double minA, maxA, minB, maxB;
        double auxA, auxB;
        
        verticesA[0] = center.getAdded(aiming.getMultiplied(8000));
        verticesA[1] = verticesA[0];
        verticesA[2] = center;
        verticesA[3]= center;
        
        verticesB[0] = Vector2.toCartesian(bc.getMax(), bc.getAiming().getAngle() + bc.getAngle() + Math.PI/2);
        verticesB[1] = Vector2.toCartesian(bc.getMax(), bc.getAiming().getAngle() - bc.getAngle() + Math.PI/2);
        verticesB[2] = verticesB[0].getReversed();
        verticesB[3] = verticesB[1].getReversed();
        
        dots[0] = aiming.clone();
        dots[1] = aiming.getPerp();
        dots[2] = bc.getAiming().clone();
        dots[3] = bc.getAiming().getPerp();
        
        for (int n = 0; n < 4; n++) {
            minA = Integer.MAX_VALUE;
            maxA = Integer.MIN_VALUE;
            minB = Integer.MAX_VALUE;
            maxB = Integer.MIN_VALUE;

            for (int i = 0; i < 4; i++) {
                auxA = verticesA[i].dot(dots[n]);
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
        return false; // Los laseres no colisionan entre si
    }
}
