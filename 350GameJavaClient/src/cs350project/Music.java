/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs350project;
import javafx.scene.media.AudioClip;
import java.net.URL;

/**
 *
 * @author Mark Masone
 */
public class Music {
    
    private AudioClip music;
    private final String selectionPanelMusicFileName;
    
    public Music() {
        selectionPanelMusicFileName = "the_stage_of_history.mp3";
    }
    
    private void play(String fileName) {
        URL url = Music.class.getResource("/resources/music/" + fileName);
        music = new AudioClip(url.toString());
        //music.play();
    }
    
    public void playSelectionPanelMusic() {
        play(selectionPanelMusicFileName);
    }
    
    public void stop() {
        //music.stop();
    }
}
