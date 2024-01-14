package utils;

import main.Game;

public class Constants {

	public static class UI {
		public static class Buttons {
			public static final int B_WIDTH_DEFAULT = 140;
			public static final int B_HEIGHT_DEFAULT = 56;
			public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
			public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);
		}

		public static class PauseButtons {
			public static final int SOUND_SIZE_DEFAULT = 42;
			public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
		}

		public static class UrmButtons {
			public static final int URM_DEFAULT_SIZE = 56;
			public static final int URM_SIZE = (int) (URM_DEFAULT_SIZE * Game.SCALE);
		}

		public static class VolumeButtons {
			public static final int VOLUME_DEFAULT_WIDTH = 28;
			public static final int VOLUME_DEFAULT_HEIGHT = 44;
			public static final int SLIDER_DEFAULT_WIDTH = 215;
						
			public static final int VOLUME_WIDTH = (int) (VOLUME_DEFAULT_WIDTH * Game.SCALE);
			public static final int VOLUME_HEIGHT = (int) (VOLUME_DEFAULT_HEIGHT * Game.SCALE);
			public static final int SLIDER_WIDTH = (int) (SLIDER_DEFAULT_WIDTH * Game.SCALE);
		}
	}
	
	
	public static class EnemyConstants {
		public static class ObstacleTree{
			public static final int TREE_DEFAULT_WIDTH = 32;
			public static final int TREE_DEFAULT_HEIGHT = 32;
			public static final int TREE_WIDTH = (int) (TREE_DEFAULT_WIDTH * Game.SCALE);
			public static final int TREE_HEIGHT = (int) (TREE_DEFAULT_HEIGHT * Game.SCALE);
		}
	}

	public static class PlayerConstants {
		public static final int MOVING = 0;
		public static final int IDLE = 1;

		public static int getSpriteAmount(int playerAction) {

			switch (playerAction) {

			case MOVING:
				return 4;
			case IDLE:
				return 1;
			default:
				return 1;
			}

		}

	}
}
