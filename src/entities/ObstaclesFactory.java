package entities;

import static utils.Constants.EnemyConstants.ObstacleTree.*;

import java.awt.Graphics;
import java.util.Random;

import gamestates.Playing;
import main.Game;

public class ObstaclesFactory {

	private Enemy obstacle;
	private Player player;
	private Playing playing;
	private static final int OBSTACLES_ARR_SIZE = 10;

	public ObstaclesFactory(Player player, Playing playing) {
		this.player = player;
		this.playing = playing;
		obstacle = generateObject();
	}

	private Enemy generateObject() {
		int xPos = new Random().nextInt(Game.GAME_WIDTH - TREE_WIDTH);
		int vertSpeed = 4;
		obstacle = new ObstacleTree(xPos, -250, (int) (TREE_WIDTH * Game.SCALE * 2),
				(int) (TREE_HEIGHT * Game.SCALE * 2), vertSpeed);
//		System.out.println("Created a tree at " + xPos + " with vertSpeed of " + vertSpeed);
		return obstacle;
	}

	public Enemy getObstacle() {
		return obstacle;
	}

	public void updateObstacle() {
		if (obstacle != null) {
			obstacle.update();
			if (obstacle.getHitbox().intersects(player.getHitbox())) {
				playing.resetAll();
				playing.setGameOver(true);				
				return;
			}

			if (obstacle.getYPosition() > Game.GAME_HEIGHT) {
				obstacle = generateObject();
			}
		}
	}

	public void renderObstacle(Graphics g) {
		for (int i = 0; i < OBSTACLES_ARR_SIZE; i++) {
			if (obstacle != null) {
				obstacle.render(g);
			}
		}

	}
	
	public void resetAll() {
		obstacle = null;
		obstacle = generateObject();
	}
}
