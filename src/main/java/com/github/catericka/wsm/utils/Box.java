package com.github.catericka.wsm.utils;

import com.sk89q.worldedit.math.Vector3;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class Box
{
	/**
	 * The corner of the box with the numerically smallest values.
	 */
	private Vector lower;

	/**
	 * The corner of the box with the numerically largest values.
	 */
	private Vector upper;


	/**
	 * Constructs and initializes a Box given min,max in x,y,z.
	 * @param lower the "small" corner
	 * @param upper the "large" corner
	 */
	public Box(Vector lower, Vector upper) {
		this.lower = Vector.getMinimum(lower, upper);
		this.upper = Vector.getMaximum(lower, upper);
	}

	public Box(Location location) {
		this(location.toVector(), location.toVector());
	}

	/**
	 * Add a vector to the Box.
	 * This will determine the new lower and upper corners.
	 */
	void add(Vector3 vec) {
		if(lower.getBlockX() > vec.getBlockX()) lower.setX(vec.getBlockX());
		if(lower.getBlockY() > vec.getBlockY()) lower.setY(vec.getBlockY());
		if(lower.getBlockZ() > vec.getBlockZ()) lower.setZ(vec.getBlockZ());
		if(upper.getBlockX() < vec.getBlockX()) upper.setX(vec.getBlockX());
		if(upper.getBlockY() < vec.getBlockY()) upper.setY(vec.getBlockY());
		if(upper.getBlockZ() < vec.getBlockZ()) upper.setZ(vec.getBlockZ());
	}

	void add(Vector vec) {
		if(lower.getBlockX() > vec.getBlockX()) lower.setX(vec.getBlockX());
		if(lower.getBlockY() > vec.getBlockY()) lower.setY(vec.getBlockY());
		if(lower.getBlockZ() > vec.getBlockZ()) lower.setZ(vec.getBlockZ());
		if(upper.getBlockX() < vec.getBlockX()) upper.setX(vec.getBlockX());
		if(upper.getBlockY() < vec.getBlockY()) upper.setY(vec.getBlockY());
		if(upper.getBlockZ() < vec.getBlockZ()) upper.setZ(vec.getBlockZ());
	}

	/**
	 * @return the lower corner
	 */
	public Vector getLower()
	{
		return lower;
	}


	/**
	 * @return the upper corner
	 */
	public Vector getUpper()
	{
		return upper;
	}

	/**
	 * @return The length on the X axis.
	 */
	int getLengthX() {
		return Math.abs(upper.getBlockX() - lower.getBlockX());
	}


	/**
	 * @return The length on the Y axis.
	 */
	int getLengthY() {
		return Math.abs(upper.getBlockY() - lower.getBlockY());
	}


	/**
	 * @return The length on the Z axis.
	 */
	int getLengthZ() {
		return Math.abs(upper.getBlockZ() - lower.getBlockZ());
	}
}
