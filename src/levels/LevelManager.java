package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.LoadSave;

public class LevelManager {

	private Game game;
	private BufferedImage[] levelSprite;
	private Level levelOne;
	private boolean first = true;

	public LevelManager(Game game) {
		this.game = game;
		importOutsideSprites();
		levelOne = new Level(LoadSave.getLevelData(LoadSave.LEVEL_ONE_DATA));
	}

	private void importOutsideSprites() {
		levelSprite = new BufferedImage[2];
		BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS_1);
		for (int j = 0; j < 2; j++) {
			int index = j;
			levelSprite[index] = img.getSubimage(j * 32, 0, 32, 32);
		}
	}

	public void draw(Graphics g) {
		for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
			for (int i = 0; i < Game.TILES_IN_WIDTH; i++) {
				int index = levelOne.getSpriteIndex(i, j);
				g.drawImage(levelSprite[index], Game.TILES_SIZE * i, Game.TILES_SIZE * j, Game.TILES_SIZE,
						Game.TILES_SIZE, null);
			}
		}

	}

	public void update() {
		BufferedImage img;
		if (first) {
			img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS_2);
			first = false;
		} else {
			img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS_1);
			first = true;
		}

		for (int j = 0; j < 2; j++) {
			int index = j;
			levelSprite[index] = img.getSubimage(j * 32, 0, 32, 32);
		}
	}
}
