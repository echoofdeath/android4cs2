package edu.centenary.places;

import java.util.ArrayList;

public interface SpatialDB<E> {

	/**
	 * Adding items to the Spatial DB.
	 * @param where the Lat/Lon of the item
	 * @param item what to add
	 */
	public void add(GeoPoint where, E item);
	
	/**
	 * Remove the closest item to the given location
	 * @param where the place to find the closest item
	 * @return the closest item
	 */
	E remove(GeoPoint where);
	
	/**
	 * Find the k closest items to the location where
	 * @param where the location to search around
	 * @param k how many items to return at most
	 * @return an ArrayList of at most k items closest to where
	 */
	ArrayList<QTLocation<E>> proximitySearch(GeoPoint where, int k);
}
