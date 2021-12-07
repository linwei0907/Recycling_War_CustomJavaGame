import java.io.File;
import javax.sound.sampled.*;
import javax.swing.*;

public class Audio {

    String audioPath;
    public Audio(String audioPath){
        this.audioPath = audioPath;
    }

    public void play() {
        try{
            File path = new File(audioPath);
            AudioInputStream audio = AudioSystem.getAudioInputStream(path);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
            // this is so label is just there so that this object wouldn't
            // get killed before audio finishes playing
            JLabel l = new JLabel();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}