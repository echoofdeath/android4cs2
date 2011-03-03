package my.topspin;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class Game {
	
	private NodeList<Node> numbers;
	private int index;
	private boolean reversed;
	private int k = 5;
	private int moves;
	
	public Game(int initPerm) {
		numbers = new NodeList<Node>();
		index = 0;
		moves = 0;
		reversed = false;
		
		for (int i = 0; i < 20; i++) {
			numbers.add(new Node(i+1, (float)(2*Math.PI/20*i)));
		}
		Log.d("Game: ", "" + numbers);
		mix(initPerm);
	}
	
	public Game(NodeList<Node> numbers) {
		this.numbers = numbers;
		index = 0;
		moves = 0;
		Log.d("Game: ", "Successfully loaded.");
		Log.d("Game: ", "" + numbers);
	}
	
	public Game() {
		index = 0;
		moves = 0;
		numbers = new NodeList<Node>();
	}
	
	// Returns true if the pieces are all in the correct spots--i.e., that they are organized numerically
/* 	public boolean isSolved() {
		for (int i = 0; i < 19; i++) {

		}
		return true;
	} */

	// Methods involving animation
	// Shift the numbers left
	public void shiftLeft(int shifts) {
		
		if ((moves++ % k) == 0) {
			index = ((index + shifts) + 20) % 20;
		}
		
		float dT = (float)(2*Math.PI/(20*k));
		
		// Update positions
		
			for (int i = 0; i < 20; i++) {
				Node n = numbers.get(i);
				float t = n.getT();
				n.setT(t-dT);
				numbers.set(i, n);
			}
	}

	// Shift the numbers right
	public void shiftRight(int shifts) {
		if ((moves++ % k) == 0) {
			index = ((index - shifts) + 20) % 20;
		}
		
		float dT = (float)(2*Math.PI/(20*k));
		
		 // Update positions
		 
		 for (int i = 0; i < 20; i++) {
			 Node n = numbers.get(i);
			 float t = n.getT();
			 n.setT(t+dT);
			 numbers.set(i, n);
		 } 
	}

	// Reverses the pieces inside the spin mechanism; first and last are switched, etc.
	public void spin() {
		// Get the front four nodes
		Node a = numbers.get(index % 20);
		Node b = numbers.get((index+1) % 20);
		Node c = numbers.get((index+2) % 20);
		Node d = numbers.get((index+3) % 20);
		
		// Reassign positions around the ellipse
		float aT = a.getT();
		float bT = b.getT();
		float cT = c.getT();
		float dT = d.getT();
		
		a.setT(dT);
		b.setT(cT);
		c.setT(bT);
		d.setT(aT);
		
		// Reassign list order
		numbers.set(index % 20, d);
		numbers.set((index+1) % 20, c);
		numbers.set((index+2) % 20, b);
		numbers.set((index+3) % 20, a);
/*		if (isSolved()) {
			System.exit(0);
		} */
	}
	// This ends the animated methods
	
	public Node get(int i) {
		return numbers.get(i % 20);
	}
	
	public int getIndex() {
		return index;
	}
	
	public void save(Context context) {
		String FILENAME = "savedGame";
		try {
			FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_WORLD_READABLE);
			String numb = numbers.toString();
			fos.write(numb.getBytes());
			fos.close();
			numb = null;
			fos = null;
			Log.d("Save: ", "Saved without exceptions.");
		} catch (FileNotFoundException e) {
			Log.d("Save: ", "File not found.");
		} catch (IOException e) {
			Log.d("Save: ", "IO Problem.");
		}
	}
	
	public void resume(Context context) {
		String FILENAME = "savedGame";
		try {
			FileInputStream fis = context.openFileInput(FILENAME);
			Scanner in = new Scanner(fis);
			
			while (in.hasNextLine()) {
				numbers.add(new Node(Integer.parseInt(in.nextLine()), Float.parseFloat(in.nextLine())));
			}
			
			in = null;
			fis.close();
			fis = null;
		} catch (FileNotFoundException e) {
			Log.d("Load: ", "File not found.");
		} catch (IOException e) {
			Log.d("Load: ", "IO Problem.");
		}
	}

	// Mixes the populated chain of numbers according to the number of random moves specified by the user
	private void mix(int initPerm) {
		for (int i = 0; i < initPerm; i++) {
			int chaos = (int) (Math.random() * 19);
			for (int k = 0; k < chaos; k++) {
				if (chaos > 9) {
					shiftLeft(1);
				} else {
					shiftRight(1);
				}
			}
			spin();
		}
	}
}

class NodeList<E> extends ArrayList<E> {
	private static final long serialVersionUID = 1L;

	public String toString() {
		String msg = new String("");
		for (int i = 0; i < size(); i++) {
			msg += get(i);
		}
		return msg;
	}
	
}
