package IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Denis
 */
public class ScoreManager {
    // An arraylist of the type "score" we will use to work with the scores inside the class
    private ArrayList<Score> scores;

    // The name of the file where the highscores will be saved
    private static final String SCORE_FILE = "scores.dat";
    
    private static final int MAX_SCORES = 10;

    ObjectOutputStream outputStream = null;
    ObjectInputStream inputStream = null;

    public ScoreManager() {
        //initialising the scores-arraylist
        scores = new ArrayList<Score>();
    }
    
    //Mejor tratamiento de errores
    public void loadScore() {
        try {
            
            inputStream = new ObjectInputStream(new FileInputStream(SCORE_FILE));
            scores = (ArrayList<Score>) inputStream.readObject();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            File file = new File(SCORE_FILE);
            try {
                file.createNewFile();
            } catch (IOException ex) {}
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void writeScore() {
        try {
            
            outputStream = new ObjectOutputStream(new FileOutputStream(SCORE_FILE));
            outputStream.writeObject(scores);
            
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage() + ",the program will make a new file");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public ArrayList<Score> getScores() {
        loadScore();
        sort();
        return scores;
    }
    
    private void sort() {
        ScoreComparator comparator = new ScoreComparator();
        Collections.sort(scores, comparator);
    }
    
    public void addScore(String name, int scoreNum) {
        loadScore();
        Score newScore = new Score(name, scoreNum);
        
        for(Score score: scores){
            //si el score es mejor que el anterior, se reemplaza
            if(score.getName().equals(name) ){
                if (score.getScore() < scoreNum){
                    scores.remove(score);
                    scores.add(newScore);
                    writeScore();
                    return;
                }else {
                    return;
                }
            }
        }
        scores.add(newScore);
        //limitamos en numero de scores
        if(scores.size() > MAX_SCORES){
            scores.remove(MAX_SCORES);
        }
        writeScore();
        
    }
}
