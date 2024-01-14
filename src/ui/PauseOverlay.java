package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;
import static utils.Constants.UI.PauseButtons.*;
import static utils.Constants.UI.UrmButtons.*;
import static utils.Constants.UI.VolumeButtons.*;

public class PauseOverlay {

	private BufferedImage backgroundImg;
	private int bgX, bgY, bgW, bgH;

	private SoundButton musicButton, sfxButton;
	private UrmButton menuB, replayB, unpauseB;
	private Playing playing;
	private VolumeButton volumeButton;
	private Game game;
	
	public PauseOverlay(Playing playing, Game game) {
		this.playing = playing;
		this.game = game;
		loadBackground();
		createSoundButtons();
		createUrmButtons();
		createVolumeButton();
	}

	private void createVolumeButton() {
		int volumeX = (int) (Game.GAME_WIDTH / 2  - SLIDER_WIDTH / 2);
		int volumeY = (int) (278 * Game.SCALE);
		volumeButton = new VolumeButton(volumeX, volumeY, SLIDER_WIDTH, VOLUME_HEIGHT);
	}

	private void createUrmButtons() {
		int menuX = (int) (Game.GAME_WIDTH / 2 - URM_SIZE / 2 - 80 * Game.SCALE);
		int replayX = (int) (Game.GAME_WIDTH / 2 - URM_SIZE / 2);
		int unpauseX = (int) (Game.GAME_WIDTH / 2 - URM_SIZE / 2 + 80 * Game.SCALE);
		int buttonY = (int) (325 * Game.SCALE);

		menuB = new UrmButton(menuX, buttonY, URM_SIZE, URM_SIZE, 2);
		replayB = new UrmButton(replayX, buttonY, URM_SIZE, URM_SIZE, 1);
		unpauseB = new UrmButton(unpauseX, buttonY, URM_SIZE, URM_SIZE, 0);
	}

	private void createSoundButtons() {
		int soundX = (int) (Game.GAME_WIDTH / 2 + 30 * Game.SCALE);
		int musicY = (int) (140 * Game.SCALE);
		int sfxY = (int) (186 * Game.SCALE);
		musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
		sfxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
	}

	private void loadBackground() {
		backgroundImg = LoadSave.getSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
		bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
		bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
		bgX = Game.GAME_WIDTH / 2 - bgW / 2;
		bgY = (int) (25 * Game.SCALE);
	}

	public void update() {
		
		musicButton.update();
		sfxButton.update();

		menuB.update();
		replayB.update();
		unpauseB.update();

		volumeButton.update();
	}

	public void draw(Graphics g) {
		g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

		musicButton.draw(g);
		sfxButton.draw(g);

		menuB.draw(g);
		replayB.draw(g);
		unpauseB.draw(g);

		volumeButton.draw(g);
	}

	public void mouseDragged(MouseEvent e) {
		if(volumeButton.isMousePressed()) {
			float valueBefore = volumeButton.getFloatValue();
			volumeButton.changeX(e.getX());
			float valueAfter = volumeButton.getFloatValue();
			if(valueBefore != valueAfter) {
				playing.getAudioPlayer().setVolume(valueAfter);
				game.getAudioPlayer().setVolume(valueAfter);
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		if (isIn(e, musicButton)) {
			musicButton.setMousePressed(true);
		} else if (isIn(e, sfxButton)) {
			sfxButton.setMousePressed(true);
		} else if (isIn(e, menuB)) {
			menuB.setMousePressed(true);
		} else if (isIn(e, replayB)) {
			replayB.setMousePressed(true);
		} else if (isIn(e, unpauseB)) {
			unpauseB.setMousePressed(true);
		} else if (isIn(e, volumeButton)) {
			volumeButton.setMousePressed(true);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (isIn(e, musicButton)) {
			if (musicButton.isMousePressed()) {
				musicButton.setMuted(!musicButton.isMuted());
				game.getAudioPlayer().toggleSongMute();
				playing.getAudioPlayer().toggleSongMute();
			}
		} else if (isIn(e, sfxButton)) {
			if (sfxButton.isMousePressed()) {
				sfxButton.setMuted(!sfxButton.isMuted());
				game.getAudioPlayer().toggleEffectMute();
				playing.getAudioPlayer().toggleEffectMute();
			}
		} else if (isIn(e, menuB)) {
			if (menuB.isMousePressed()) {
				Gamestate.state = Gamestate.MENU;
				playing.unpauseGame();
			}
		} else if (isIn(e, replayB)) {
			if (replayB.isMousePressed()) {
				playing.resetAll();
				Gamestate.state = Gamestate.PLAYING;
			}
		} else if (isIn(e, unpauseB)) {
			if (unpauseB.isMousePressed()) {
				playing.unpauseGame();
			}
		}

		musicButton.resetBooleans();
		sfxButton.resetBooleans();
		menuB.resetBooleans();
		replayB.resetBooleans();
		unpauseB.resetBooleans();
		volumeButton.resetBooleans();
	}

	public void mouseMoved(MouseEvent e) {
		musicButton.setMouseOver(false);
		sfxButton.setMouseOver(false);
		menuB.setMouseOver(false);
		replayB.setMouseOver(false);
		unpauseB.setMouseOver(false);
		volumeButton.setMouseOver(false);

		if (isIn(e, musicButton)) {
			musicButton.setMouseOver(true);
		} else if (isIn(e, sfxButton)) {
			sfxButton.setMouseOver(true);
		} else if (isIn(e, menuB)) {
			menuB.setMouseOver(true);
		} else if (isIn(e, replayB)) {
			replayB.setMouseOver(true);
		} else if (isIn(e, unpauseB)) {
			unpauseB.setMouseOver(true);
		} else if (isIn(e, volumeButton)) {
			volumeButton.setMouseOver(true);
		}

	}

	private boolean isIn(MouseEvent e, PauseButton b) {
		return (b.getBounds().contains(e.getX(), e.getY()));
	}
}
