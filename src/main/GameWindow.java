package main;

import javax.swing.JFrame;

public class GameWindow {

	private JFrame frame;

	public GameWindow(GamePanel gamePanel, int xSize, int ySize) {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(gamePanel);
		frame.setLocationRelativeTo(null);
		frame.pack();
		frame.setVisible(true);
	}

}
