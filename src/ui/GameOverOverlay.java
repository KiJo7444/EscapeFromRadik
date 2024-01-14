package ui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

public class GameOverOverlay {

	private Playing playing;
	private BufferedImage img;

	public GameOverOverlay(Playing playing) {
		this.playing = playing;
		loadImg();
	}

	private void loadImg() {
		BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.GAME_OVER);
		img = temp.getSubimage(0, 0, temp.getWidth(), temp.getHeight());
	}

	public void draw(Graphics g) {
		g.drawImage(img, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			playing.resetAll();
			Gamestate.state = Gamestate.MENU;
		}
	}
}
