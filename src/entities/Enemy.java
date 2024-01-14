package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public abstract class Enemy extends Entity {

	private int verticalSpeed;
	BufferedImage img;

	public Enemy(float x, float y, int width, int height, int verticalSpeed) {
		super(x, y, width, height);
		this.verticalSpeed = verticalSpeed;
		hitbox.x = (int) (x);
		hitbox.y = (int) (y);
		hitbox.width = (int) (width - 30 * Game.SCALE);
		hitbox.height = (int) (height - 30 * Game.SCALE);
	}

	protected abstract void loadImg();

	public void render(Graphics g) {
//		drawHitbox(g);
		g.drawImage(img, (int) x, (int) y, width, height, null);
	}

	public void update() {
		y += verticalSpeed;
		updateHitbox();
	}

	public int getVertSpeed() {
		return verticalSpeed;
	}

	@Override
	protected void updateHitbox() {
		hitbox.x = (int) (x + 30 * Game.SCALE / 2);
		hitbox.y = (int) (y + 30 * Game.SCALE);
	}
}
