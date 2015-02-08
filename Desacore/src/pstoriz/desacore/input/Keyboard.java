package pstoriz.desacore.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	
	private boolean[] keys = new boolean[120];
	public boolean up, down, left, right, shift, space, info, r;
	public boolean etoggle = false;
	
	public static final int E = KeyEvent.VK_E;
	
	public void update() {
		//Matches up the keys to the right value
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		shift = keys[KeyEvent.VK_SHIFT];
		space = keys[KeyEvent.VK_SPACE];
		info = keys[KeyEvent.VK_CONTROL];
		r = keys[KeyEvent.VK_R];
		
		//Scans through each key
		for (int i = 0; i < keys.length; i++) {
			if (keys[i]) {
				//System.out.println("KEY : " + i);
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		isKeyReleased(E);
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
	
	}
	
	public boolean isKeyReleased(int key) {
		if (isKeyPressed(key)) {
			etoggle = !etoggle;
		}
		return etoggle;
	}
	
	public boolean isKeyPressed(int key) {
	    return keys[key];
	}
}
