import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;

public class Music {
    Clip clip;
    long clipTimePosition=0;
    public boolean isPlaying=false;

    public void startMusic() {
        try {
            File musicPath = new File("rnb.wav");
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
            this.clip = AudioSystem.getClip();

            if(!musicPath.exists()) {
                System.out.println("Cannot find music file");
            }
            else if(!isPlaying) {
                this.clip.open(audioInput);
                this.clip.start();
                this.clip.loop(Clip.LOOP_CONTINUOUSLY);
                this.isPlaying = true;
            }
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public void musicButton() {
        if(this.isPlaying) {
            this.clipTimePosition = clip.getMicrosecondPosition();
            this.clip.stop();
            this.isPlaying=false;
        }
        else {
            this.clip.setMicrosecondPosition(clipTimePosition);
            this.clip.start();
            this.clip.loop(Clip.LOOP_CONTINUOUSLY);
            this.isPlaying = true;
        }

    }



}