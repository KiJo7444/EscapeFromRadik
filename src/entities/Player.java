package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utils.Constants.PlayerConstants;
import utils.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniTick;
	private int aniIndex;
	private int aniSpeed = 15;
	private int playerSpeed;
	private boolean left = false, right = false;
	private boolean keyPressed, keyReleased;
	private int playerAction = PlayerConstants.MOVING;

	public Player(float x, float y, int witdth, int height) {
		super(x, y, witdth, height);
		loadAnimations();
	}

	public void update() {
		updatePos();
		updateHitbox();
		updateAnimationTick();
	}

	private void updatePos() {
		if ((playerDir() == 0) && (getXPosition() > 0)) {
			keyPressed = true;
			x -= playerSpeed;
		}
		if ((playerDir() == 1) && ((getXPosition() < Game.GAME_WIDTH - Game.PLAYER_SIZE * Game.SCALE))) {
			x += playerSpeed;
			keyPressed = true;
		}
	}

	private int playerDir() {
		int dir = -1;
		if (left == true) {
			dir = 0;
		}
		if (right == true) {
			dir = 1;
		}
		return dir;
	}

	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, width, height, null);
//		drawHitbox(g);
	}

	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= PlayerConstants.getSpriteAmount(playerAction)) {
				aniIndex = 0;
			}
		}

	}

	private void loadAnimations() {
		BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);
		animations = new BufferedImage[2][4];
		for (int j = 0; j < animations.length; j++) {
			for (int i = 0; i < animations[j].length; i++) {
				animations[j][i] = img.getSubimage(i * 32, j * 32, 32, 32);
			}
		}
	}

	public void resetBooleans() {
		right = false;
		left = false;
		keyPressed = false;
		keyReleased = false;
	}

	public int getPlayerSpeed() {
		return playerSpeed;
	}

	public void setPlayerSpeed(int playerSpeed) {
		this.playerSpeed = playerSpeed;
	}

	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isKeyPressed() {
		return keyPressed;
	}

	public boolean isKeyReleased() {
		return keyReleased;
	}

	public void resetAll() {
		resetBooleans();
		hitbox.x = (int) x;
		hitbox.y = (int) y;
	}

}
