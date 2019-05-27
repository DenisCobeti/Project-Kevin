/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game.objects;

/**
 *
 * @author alumno
 */
public enum AsteroidType {
    Silice, Carbon, Metal, Vestoid, Organic;
    
    public int getNum(AsteroidType type){
        switch(type){
            case Silice: return 0;
            case Carbon: return 1;
            case Metal: return 2;
            case Vestoid: return 3;
            case Organic: return 4;
        
        }
        return 0;
    }
    
}
