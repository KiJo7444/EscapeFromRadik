package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import utils.LoadSave;
import static utils.Constants.UI.PauseButtons.*;

public class SoundButton extends PauseButton {

	private BufferedImage[][] soundImgs;
	private boolean mouseOver, mousePressed;
	private boolean muted;
	private int rowIndex, columnIndex;

	public SoundButton(int x, int y, int width, int height) {
		super(x, y, width, height);
		loadSoundImgs();
	}

	private void loadSoundImgs() {
		BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.SOUND_BUTTONS);
		soundImgs = new BufferedImage[2][3];
		for (int j = 0; j < soundImgs.length; j++) {
			for (int i = 0; i < soundImgs[j].length; i++) {
				soundImgs[j][i] = temp.getSubimage(i * SOUND_SIZE_DEFAULT, j * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT,
						SOUND_SIZE_DEFAULT);
			}
		}
	}

	public void update() {
		if (muted) {
			rowIndex = 1;
		} else {
			rowIndex = 0;
		}
		
		columnIndex = 0;
		if(mouseOver) {
			columnIndex = 1;
		}
		if(mousePressed) {
			columnIndex = 2;
		}
	}
	
	public void resetBooleans() {
		mouseOver = false;
		mousePressed = false;
	}
	public void draw(Graphics g) {
		g.drawImage(soundImgs[rowIndex][columnIndex], x, y, width, height, null);
	}

	public boolean isMouseOver() {
		return mouseOver;
	}

	public void setMouseOver(boolean mouseOver) {
		this.mouseOver = mouseOver;
	}

	public boolean isMousePressed() {
		return mousePressed;
	}

	public void setMousePressed(boolean mousePressed) {
		this.mousePressed = mousePressed;
	}

	public boolean isMuted() {
		return muted;
	}

	public void setMuted(boolean muted) {
		this.muted = muted;
	}

}
