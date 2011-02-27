package edu.centenary.topspin;

public interface TopSpin {

    // Shift the pieces one space to the left
    public void shiftLeft();

    // Shift the pieces one space to the right
    public void shiftRight();

    // Reverses the pieces currently inside the spin mechanism, such that
    // the first and last are swapped, etc.
    public void spin();

    // Returns true if the puzzle is solved, such that the pieces are in numerical order.
    public boolean isSolved();

    // Retrieves the number in the ith spot of the cycle
    public int get(int i);
    
    // Either shift left, right, or spin the object for moves times
    public void mixup(int moves);
    
}

