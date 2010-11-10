package com.googlecode.android4cs2.gofish;

import java.util.Iterator;

import android.util.Log;

public class MyGoFishHand extends GoFishHand {

	public void give(Card c, MyGoFishHand taker) {
		Iterator<Card> it = iterator();
		while (it.hasNext()) {
			Card next = it.next();
			if (next.getRank() == c.getRank() && next.getSuit() == c.getSuit()) {
				it.remove();
				taker.add(next);
			}
		}
	}
	
	public String toString() {
		Iterator<Card> it = iterator();
		String output = "";

		while (it.hasNext()) {
			Card next = it.next();
			output += next.getRank() + " of " + next.getSuit() + "\n";
		}
		output += "***********\n";
		return output;
	}
}
