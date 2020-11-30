package src.domain;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {

  private Clip clip;

  public Audio(String name) {
    try {
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/music/" + name).getAbsoluteFile());

      clip = AudioSystem.getClip();

      // Open audioInputStream to the clip
      clip.open(audioInputStream);

      // Loop clip
      clip.loop(Clip.LOOP_CONTINUOUSLY);
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      e.printStackTrace();
    }
  }

  public void play() {
    clip.loop(Clip.LOOP_CONTINUOUSLY);
  }

  public void pause() {
    clip.stop();
  }

  public void setVolume(float db) {
    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    gainControl.setValue(db);
  }

  public boolean isPlaying() {
    return clip.isRunning();
  }
}
