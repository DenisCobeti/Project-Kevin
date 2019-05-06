package game.objects.ships;

import game.objects.GameObject;

/**
 * Habilidad que depende de la nave
 * @author KometKevin
 */
public abstract class AdvancedAbility extends GameObject {
    
    public boolean active = false;
    
    /**
     * Función que llama la nave cuando el usuario presiona la tecla corresp.
     * @param cd Coldown que se devolvera
     * @return Devuelve el coldown o un 0
     */
    public abstract double activate(double cd);

    // Getter
    public boolean isActive() {return active;}
}
