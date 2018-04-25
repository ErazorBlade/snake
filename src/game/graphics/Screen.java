package game.graphics;

import game.level.Level;

public class Screen {

	public int[] pixels;

	public final int width, height;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void render(Level level) {
		level.render(this);
	}

}
