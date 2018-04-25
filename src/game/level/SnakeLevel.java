package game.level;

import java.util.Random;

import game.Game;
import game.graphics.Screen;
import game.input.Keyboard;

public class SnakeLevel implements Level {

	private final int width, height, startingScore;
	private int score, previousScore, highScore;
	private int[] field;
	private int x, y;
	private boolean gameOver;
	private int foodCol = 0x00ff00;
	private int bgCol = 0xffffff;
	private int snakeCol = 0x000000;
	private Random random;

	public SnakeLevel(int width, int height, int startingScore) {
		this.width = width;
		this.height = height;
		this.startingScore = startingScore;
		field = new int[width * height];
		random = new Random();
		reset();
	}

	public void update() {
		if (!gameOver) {
			for (int i = 0; i < field.length; i++) {
				if (field[i] > 0) field[i]--;
			}

			int dir = Keyboard.direction;
			if (dir == 0) y--;
			if (dir == 1) x++;
			if (dir == 2) y++;
			if (dir == 3) x--;

			if (x < 0) x += width;
			if (x >= width) x -= width;
			if (y < 0) y += height;
			if (y >= height) y -= height;

			if (field[x + y * width] <= 0) {
				if (field[x + y * width] == -1) {
					score++;
					for (int i = 0; i < field.length; i++) {
						if (field[i] > 0) field[i]++;
					}
					field[random.nextInt(field.length)] = -1;
				}
				field[x + y * width] = score;
			} else {
				gameOver = true;
				snakeCol = 0xff0000;
				Keyboard.reset = false;
			}
		} else {
			if (Keyboard.reset) reset();
		}
		Game.title = "Snake | Score: " + score + ", Previous: " + previousScore + ", HighScore: " + highScore;
	}

	public void render(Screen screen) {
		for (int i = 0; i < field.length; i++) {
			int color = 0;
			if (field[i] == -1) color = foodCol;
			if (field[i] == 0) color = bgCol;
			if (field[i] > 0) color = snakeCol;
			screen.pixels[i] = color;
		}
	}

	private void reset() {
		Keyboard.direction = 1;
		previousScore = score;
		if (score > highScore) highScore = score;
		score = startingScore;
		for (int i = 0; i < field.length; i++) {
			field[i] = 0;
		}
		field[random.nextInt(field.length)] = -1;
		gameOver = false;
		snakeCol = 0x000000;
		x = 0;
		y = 0;
	}

}
