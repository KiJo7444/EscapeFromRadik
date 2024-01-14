package audio;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {

	public static int DIE = 0;
	public static int GAME_OVER = 1;
	public static int DRIVE = 2;

	private Clip[] song, effects;
	private float volume = 0.7f;
	private boolean songMute, effectMute;

	public AudioPlayer() {
		loadSongs();
		loadEffects();
		playSong();
	}

	private void loadSongs() {
		String name = "music";
		song = new Clip[1];
		song[0] = getClip(name);
	}

	private void loadEffects() {
		String[] effectNames = { "car_crash", "game_over", "car_driving" };
		effects = new Clip[effectNames.length];
		for (int i = 0; i < effects.length; i++)
			effects[i] = getClip(effectNames[i]);
		
		updateEffectsVolume();
	}

	private Clip getClip(String name) {
		URL url = getClass().getResource("/audio/" + name + ".wav");
		AudioInputStream audio;

		try {
			audio = AudioSystem.getAudioInputStream(url);
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void playEffect(int effect) {
		if(effect == 2) {
			effects[effect].loop(Clip.LOOP_CONTINUOUSLY);
		} else {
			effects[effect].setMicrosecondPosition(0);
			effects[effect].start();
		}				
	}
	
	public void playSong() {
		updateSongVolume();
		this.song[0].setMicrosecondPosition(0);
		this.song[0].loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void toggleSongMute() {
		this.songMute = !songMute;
		for (Clip c : song) {
			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(songMute);
		}
	}

	public void toggleEffectMute() {
		this.effectMute = !effectMute;
		for (Clip c : effects) {
			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(effectMute);
		}
	}

	private void updateSongVolume() {

		FloatControl gainControl = (FloatControl) song[0].getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * volume) + gainControl.getMinimum();
		gainControl.setValue(gain);
	}

	private void updateEffectsVolume() {
		for (Clip c : effects) {
			FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (range * volume) + gainControl.getMinimum();
			gainControl.setValue(gain);
		}
	}
	
	public void stopSong() {
		if(song[0].isActive()) {
			song[0].stop();
		}
			
	}
	
	public void crashedCar() {
		stopSong();
		stopEffects();
		playEffect(DIE);
	}

	public void stopEffects() {
		effects[0].stop();
		effects[1].stop();
		effects[2].stop();
	}

	public float getVolume() {
		return volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
		updateSongVolume();
		updateEffectsVolume();
	}
	
	public void playMovingCar() {
		effects[2].loop(DRIVE);
	}
	
	public void stopCarEffects() {
		effects[2].stop();
	}

	public boolean isSongMute() {
		return songMute;
	}

	public void setSongMute(boolean songMute) {
		this.songMute = songMute;
	}

	public boolean isEffectMute() {
		return effectMute;
	}

	public void setEffectMute(boolean effectMute) {
		this.effectMute = effectMute;
	}
	
	
}
