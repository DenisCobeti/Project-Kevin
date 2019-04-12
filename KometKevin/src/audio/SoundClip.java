package audio;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Objeto para la reproducción de sonidos.
 * 
 * @author Project Kevin
 */
public class SoundClip {
    
    private Clip clip = null;
    private FloatControl volume;
    
    /**
     * Construtor de la clase
     * @param path ruta del archivo de audio en la carpeta /resources  
     */
    public SoundClip(String path){
        try {
            InputStream audioSrc = SoundClip.class.getResourceAsStream(path);
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream ais = AudioSystem.getAudioInputStream(bufferedIn);
            AudioFormat baseFormat = ais.getFormat();
            AudioFormat decodeFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
                                                       baseFormat.getSampleRate(), 
                                                       16, 
                                                       baseFormat.getChannels(), 
                                                       baseFormat.getChannels() * 2,
                                                       baseFormat.getSampleRate(), 
                                                       false);
            AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
            
            clip = AudioSystem.getClip();
            clip.open(dais);
            
            volume = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Reproduce el clip de audio.
     * @param frame en el que comienza la reproducción
     */
    public void play(int frame) {
        if (clip == null) {
            return;
        }
        
        stop();
        clip.setFramePosition(frame);
        while(!clip.isRunning()) {  // Se fuerza la reproduccion
            clip.start();
        }
    }
    
    /**
     * Para la reproducción del clip.
     */
    public void stop() {
        if(clip.isRunning()) {
            clip.stop();
        }
    }
    
    /**
     * Reproduce el clip de forma continua.
     */
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    

    /**
     * Deconstructor de la clase.
     */    
    public void dispose() {
        stop();
        clip.drain();
        clip.close();
    }
    
    /**
     * Establece el volumen del clip.
     * @param value Decibelios, numeros negativos para poco volumen
     */
    public void setVolume(float value) {
        volume.setValue(value);
    }
    
    // Getters
    public boolean isRunning() {return clip.isRunning();}
}
