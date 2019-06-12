package IO;

/**
 * Clase puntuación. Dupla con nombre y puntos.
 * 
 * @author Denis
 */
import java.io.Serializable;

public class Score  implements Serializable {
    private int score;
    private String name;
    
    public Score(String name, int score) {
        this.score = score;
        this.name = name;
    }
    
    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.score;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Score other = (Score) obj;
        if (this.score != other.score) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name + ": " + score;
    }
    
    
}
