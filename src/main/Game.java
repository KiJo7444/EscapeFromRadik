package main;

import java.awt.Graphics;

import audio.AudioPlayer;
import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;

public class Game implements Runnable {

	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private AudioPlayer audioPlayer;
	
	private final int FPS_SET = 120;
	private final int UPS_SET = 100;

	private Playing playing;
	private Menu menu;

	public static final int TILES_DEFAULT_SIZE = 32;
	public static final int PLAYER_SIZE = TILES_DEFAULT_SIZE * 2;
	public static final float SCALE = 1.4f;
	public static final int TILES_IN_WIDTH = 14;
	public static final int TILES_IN_HEIGHT = 18;
	public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
	public static final int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
	public static final int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;
	public static final int startPosX = (int) (GAME_WIDTH / 2 - (PLAYER_SIZE * SCALE / 2));
	public static final int startPosy = (int) (GAME_HEIGHT - PLAYER_SIZE * SCALE - GAME_HEIGHT / 12);

	private int count = 0;

	public Game() {
		initEntities();
		gamePanel.requestFocus();
		startGameLoop();
	}

	private void initEntities() {
		gamePanel = new GamePanel(this);
		audioPlayer = new AudioPlayer();
		menu = new Menu(this);
		gameWindow = new GameWindow(gamePanel, GAME_WIDTH, GAME_HEIGHT);
		playing = new Playing(this, gamePanel);


	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void update(int count) {

		switch (Gamestate.state) {
		case MENU:
			menu.update();
			audioPlayer.stopEffects();
			break;
		case PLAYING:
			playing.update();
			if (playing.isGameOver())
				audioPlayer.stopEffects();
			else if (playing.isPaused()) {
				audioPlayer.stopEffects();
			} else if (!audioPlayer.isEffectMute()) {
				audioPlayer.playEffect(AudioPlayer.DRIVE);
			}
			break;			
		case QUIT:
		default:
			System.exit(0);
			break;

		}
	}

	public void render(Graphics g) {

		switch (Gamestate.state) {
		case MENU:
			menu.draw(g);
			break;
		case PLAYING:
			playing.draw(g);
			break;
		default:
			break;
		}
	}

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;

		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;

		count = 0;
		while (true) {
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;

			if (deltaU >= 1) {
				count++;
				update(count);
				updates++;
				deltaU--;
				if (count > 20) {
					count = 0;
				}
			}

			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
//				System.out.println("FPS: " + frames + " | UPS " + updates);
				frames = 0;
				updates = 0;
			}

		}

	}

	public int getCount() {
		return count;
	}

	public Menu getMenu() {
		return menu;
	}

	public Playing getPlaying() {
		return playing;
	}
	
	public AudioPlayer getAudioPlayer() {
		return audioPlayer;
	}
}

