/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IO;

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
 * @author Neblis
 */
public class ScoreManager {
    // An arraylist of the type "score" we will use to work with the scores inside the class
    private ArrayList<Score> scores;

    // The name of the file where the highscores will be saved
    private static final String SCORE_FILE = "scores.dat";

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
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
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
            System.out.println( e.getMessage() + ",the program will try and make a new file");
        } catch (IOException e) {
            System.out.println("[Update] IO Error: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("[Update] Error: " + e.getMessage());
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
    
    public void addScore(String name, int score) {
        loadScore();
        scores.add(new Score(name, score));
        writeScore();
    }
}
