package game.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {

	public static int direction = 1;
	public static boolean reset = false;

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) direction = 0;
		if (e.getKeyCode() == KeyEvent.VK_D) direction = 1;
		if (e.getKeyCode() == KeyEvent.VK_S) direction = 2;
		if (e.getKeyCode() == KeyEvent.VK_A) direction = 3;
		if (e.getKeyCode() == KeyEvent.VK_R) reset = true;
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

}
