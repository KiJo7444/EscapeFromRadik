package entities;

import utils.LoadSave;

public class ObstacleTree extends Enemy {

	public ObstacleTree(float x, float y, int width, int height, int verticalSpeed) {
		super(x, y, width, height, verticalSpeed);
		loadImg();
	}

	@Override
	protected void loadImg() {
		img = LoadSave.getSpriteAtlas(LoadSave.OBSTACLE_TREE);
	};

}
