package gamestates;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import audio.AudioPlayer;
import entities.Enemy;
import entities.ObstacleTree;
import entities.ObstaclesFactory;
import entities.Player;
import levels.LevelManager;
import main.Game;
import main.GamePanel;
import ui.GameOverOverlay;
import ui.PauseOverlay;
import static utils.Constants.EnemyConstants.ObstacleTree.*;

public class Playing extends State implements StateMethods {

	private static final int PLAYER_SPEED = Game.GAME_WIDTH / 200;
	
	private Player player;
	private LevelManager levelManager;
	private PauseOverlay pauseOverlay;
	private GamePanel gamePanel;
	private GameOverOverlay gameOverOverlay;
	private ObstaclesFactory factory1, factory2, factory3;
	private AudioPlayer audioPlayer;
	private boolean paused = false;
	private boolean keyLeftPressed, keyRightPressed;
	private boolean gameOver;

	public Playing(Game game, GamePanel gamePanel) {
		super(game);
		this.gamePanel = gamePanel;
		initEntities();
	}

	private void initEntities() {
		levelManager = new LevelManager(game);
		player = new Player(Game.startPosX, Game.startPosy, (int) (Game.PLAYER_SIZE * Game.SCALE),
				(int) (Game.PLAYER_SIZE * Game.SCALE));
		pauseOverlay = new PauseOverlay(this, game);
		gameOverOverlay = new GameOverOverlay(this);
		factory1 = new ObstaclesFactory(player, this);
		factory2 = new ObstaclesFactory(player, this);
		factory3 = new ObstaclesFactory(player, this);
		audioPlayer = new AudioPlayer();
		audioPlayer.stopSong();
	}

	@Override
	public void update() {
		if (!paused && !gameOver) {
			if (game.getCount() == 1) {
				levelManager.update();
				gamePanel.requestFocus();
			}
			player.update();
			factory1.updateObstacle();
			factory2.updateObstacle();
			factory3.updateObstacle();
		} else {
			pauseOverlay.update();
			audioPlayer.stopCarEffects();
		}

	}

	@Override
	public void draw(Graphics g) {
		levelManager.draw(g);
		player.render(g);
		factory1.renderObstacle(g);
		factory2.renderObstacle(g);
		factory3.renderObstacle(g);
		if (paused) {
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		} else if (gameOver) {
			gameOverOverlay.draw(g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!gameOver)
			if (paused) {
				pauseOverlay.mousePressed(e);
			}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!gameOver)
			if (paused) {
				pauseOverlay.mouseReleased(e);				
			}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!gameOver)
			if (paused) {
				pauseOverlay.mouseMoved(e);
			}

	}

	public void mouseDragged(MouseEvent e) {
		if (!gameOver)
			if (paused) {
				pauseOverlay.mouseDragged(e);
			}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gameOver)
			gameOverOverlay.keyPressed(e);
		else
			switch (e.getKeyCode()) {
			case KeyEvent.VK_A:
				player.setRight(false);
				player.setLeft(true);
				player.setPlayerSpeed(PLAYER_SPEED);
				keyLeftPressed = true;
				break;
			case KeyEvent.VK_D:
				player.setLeft(false);
				player.setRight(true);
				player.setPlayerSpeed(PLAYER_SPEED);
				keyRightPressed = true;
				break;
			case KeyEvent.VK_ESCAPE:
				paused = !paused;
				break;
			default:
				break;
			}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (!gameOver) {
			if (e.getKeyCode() == 65) {
				keyLeftPressed = false;
			}
			if (e.getKeyCode() == 68) {
				keyRightPressed = false;
			}
			if ((!keyLeftPressed) && (!keyRightPressed)) {
				player.resetBooleans();
			}
		}
	}

	public void unpauseGame() {
		paused = false;
	}

	public void resetAll() {
		gameOver = false;
		paused = false;
		player.resetAll();
		factory1.resetAll();
		factory2.resetAll();
		factory3.resetAll();
	}

	public void setGameOver(boolean gameOver) {
		audioPlayer.stopEffects();
		audioPlayer.crashedCar();
		this.gameOver = gameOver;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}

	public void setAudioPlayer(AudioPlayer audioPlayer) {
		this.audioPlayer = audioPlayer;
	}

	public boolean isPaused() {
		return paused;
	}


	
}
