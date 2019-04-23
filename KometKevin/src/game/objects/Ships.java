/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.objects;

/**
 *
 * @author 
 */
public enum Ships {
    /**
     *
     */
    Hammerhead("resources/ships/hammerShip.png", 115, 64),
    Aphelion("resources/ships/aphelionShip.png", 90, 92),
    Bartleby("resources/ships/bartlebyShip.png", 55, 49),
    Ramiro("resources/ships/bartlebyShip.png", 90, 92);
    
    private String sprite;
    private int sizeX, sizeY;
    
    Ships(String sprite, int sizeX, int sizeY){
        this.sprite = sprite;
        this.sizeX = sizeX; 
        this.sizeY = sizeY;
    }

    public String getSprite() {
        return sprite;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    
}
