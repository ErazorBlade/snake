package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import game.graphics.Screen;
import game.input.Keyboard;
import game.level.Level;
import game.level.SnakeLevel;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	public static String title = "Snake";
	public final int updatesPerSecond = 8;

	public static final int scale = 20;
	public static final int width = 20;
	public static final int height = 20;

	private boolean running = false;

	private Thread thread;
	private JFrame frame;
	private Level level;
	private Screen screen;
	private Keyboard key;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Game(Level level) {
		this.level = level;
		screen = new Screen(width, height);
		key = new Keyboard();
	}

	public synchronized void start() {
		if (running) return;
		running = true;
		setPreferredSize(new Dimension(width * scale, height * scale));
		addKeyListener(key);
		frame = new JFrame(title);
		frame.add(this);
		frame.pack();
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		requestFocus();
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		final double ns = 1000000000.0 / updatesPerSecond;
		long lastTime = System.nanoTime();
		double delta = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			render();
		}
	}

	private void update() {
		level.update();
		frame.setTitle(title);
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}

		screen.render(level);
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		Game game = new Game(new SnakeLevel(width, height, 3));
		game.start();
	}

}
