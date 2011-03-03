package my.topspin;

public class Node {
	
	// Data members
	private int value; // number value of this Node
	private float t;
	
	// Constructors
	public Node(int value, float t) {
		this.value = value;
		this.t = t;
	}
	
	// Access Methods
	public int getValue() {
		return value;
	}
	
	public float getT() {
		return t;
	}

	// Set methods
	public void setValue(int value) {
		this.value = value;
	}
	
	public void setT(float t) {
		this.t = t;
	}
	
	public String toString() {
		return new String(value + "\n" + t + "\n");
	}
}
