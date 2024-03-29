package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import utils.LoadSave;

public class Menu extends State implements StateMethods {

	private MenuButton[] buttons = new MenuButton[2];
	private BufferedImage backgroundImage;
	private BufferedImage backgroundImagePink;
	private int menuX;
	private int menuY;
	private int menuWidth;
	private int menuHeight;

	public Menu(Game game) {
		super(game);
		loadBackground();
		loadButtons();
		backgroundImagePink = LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
	}

	private void loadBackground() {
		backgroundImage = LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth = (int) (backgroundImage.getWidth() * Game.SCALE);
		menuHeight = (int) (backgroundImage.getHeight() * Game.SCALE);
		menuX = Game.GAME_WIDTH / 2 - menuWidth / 2;
		menuY = (int) (145 * Game.SCALE);
	}

	private void loadButtons() {
		buttons[0] = new MenuButton(Game.GAME_WIDTH / 2, (int) (270 * Game.SCALE), 0, Gamestate.PLAYING);
		buttons[1] = new MenuButton(Game.GAME_WIDTH / 2, (int) (360 * Game.SCALE), 2, Gamestate.QUIT);
//		buttons[2] = new MenuButton(Game.GAME_WIDTH / 2, (int) (390 * Game.SCALE), 2, Gamestate.QUIT);
	}

	@Override
	public void update() {
		for (MenuButton button : buttons) {
			button.update();
		}

	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundImagePink, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.drawImage(backgroundImage, menuX, menuY, menuWidth, menuHeight, null);
		for (MenuButton button : buttons) {
			button.draw(g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (MenuButton button : buttons) {
			if (isIn(e, button)) {
				button.setMousePressed(true);
				break;
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (MenuButton button : buttons) {
			if (isIn(e, button)) {
				if (button.isMousePressed()) 
					button.applyGameState();
				if (button.getState() == Gamestate.PLAYING)	
					game.getAudioPlayer().playEffect(2);
				break;
			}
		}
		resetButtons();
	}

	private void resetButtons() {
		for (MenuButton button : buttons) {
			button.resetBooleans();
		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (MenuButton button : buttons) {
			button.setMouseOver(false);
		}

		for (MenuButton button : buttons) {
			if (isIn(e, button)) {
				button.setMouseOver(true);
				break;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			Gamestate.state = Gamestate.PLAYING;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
