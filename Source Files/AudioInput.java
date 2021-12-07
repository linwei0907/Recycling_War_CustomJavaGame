import javax.sound.sampled.*;
import java.io.*;

 
public class AudioInput {
    // name of wav file
    File wavFile;
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    TargetDataLine line;

    public AudioInput(String filename){
        wavFile = new File(filename);
    }

    public AudioFormat getAudioFormat() {
        // define format of the audio
        float sampleRate = 8000;
        int sampleSizeInBits = 8;
        int channels = 1;
        boolean signed = true;
        boolean bigEndian = false;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
        return format;
    }

    public void start() {
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
 
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();
            AudioInputStream ais = new AudioInputStream(line);
 
            System.out.println("Start recording...");
 
            // start recording
            AudioSystem.write(ais, fileType, wavFile);
 
        } catch (LineUnavailableException ex) {
            ex.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void record(){
        Thread stopper = new Thread(new Runnable() {
            public void run() {
                try {
                    // sleep until recording finish
                    Thread.sleep(2000);
                 } catch (InterruptedException ex) {
                     ex.printStackTrace();
                 }
                 this.finish();
             }

            private void finish() {
                line.stop();
                line.close();
                System.out.println("Finished");
            }
         });
  
        stopper.start();
        this.start();
    }
}