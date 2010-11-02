package com.googlecode.android4cs2.gofish;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class CardAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Card> hand;
	
	public CardAdapter(Context c, ArrayList<Card> h) {
		this.context = c;
		this.hand = h;
	}
	
	@Override
	public int getCount() {
		return hand.size();
	}

	@Override
	public Object getItem(int arg0) {
		return hand.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView i = new ImageView(context);
		Card c = hand.get(position);
		if (c.getRank() == 1) {
			switch (c.getSuit()) {
			case Card.SPADES:
				i.setImageResource(R.drawable.x2);
				break;
			case Card.HEARTS:
				i.setImageResource(R.drawable.x3);
				break;
			case Card.DIAMONDS:
				i.setImageResource(R.drawable.x4);
				break;
			case Card.CLUBS:
				i.setImageResource(R.drawable.x1);
				break;
			default:
				i.setImageResource(R.drawable.background);
				break;
			}
		} else {
			i.setImageResource(CardView.cardIDs[52 - (c.getRank() - 1)*4 + (c.getSuit()+1)%4]);
		}
		i.setLayoutParams(new Gallery.LayoutParams(72, 96));

		return i;
	}

}
