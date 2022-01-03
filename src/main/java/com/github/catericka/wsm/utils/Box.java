package com.github.catericka.wsm.utils;

import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Box
{
	/**
	 * The corner of the box with the numerically smallest values.
	 */
	private int lower_x;
	private int lower_y;
	private int lower_z;

	/**
	 * The corner of the box with the numerically largest values.
	 */
	private int upper_x;
	private int upper_y;
	private int upper_z;

	public Box(Location location) {
		lower_x = upper_x = location.getBlockX();
		lower_y = upper_y = location.getBlockY();
		lower_z = upper_z = location.getBlockZ();
	}

	/**
	 * Add a vector to the Box.
	 * This will determine the new lower and upper corners.
	 */
	void add(int x, int y, int z) {
		if(lower_x > x) lower_x = x;
		if(lower_y > y) lower_y = y;
		if(lower_z > z) lower_z = z;

		if(upper_x < x) upper_x = x;
		if(upper_y < y) upper_y = y;
		if(upper_z < z) upper_z = z;
	}

	/**
	 * @return the lower corner
	 */
	public Vector getLower()
	{
		return new Vector(lower_x, lower_y, lower_z);
	}

	public BlockVector3 getLowerBlockVector3(){
		return BlockVector3.at(lower_x, lower_y, lower_z);
	}

	/**
	 * @return the upper corner
	 */
	public Vector getUpper()
	{
		return new Vector(upper_x, upper_y, upper_z);
	}

	public BlockVector3 getUpperBlockVector3(){
		return BlockVector3.at(upper_x, upper_y, upper_z);
	}

	/**
	 * @return The length on the X axis.
	 */
	int getLengthX() {
		return Math.abs(upper_x - lower_x);
	}


	/**
	 * @return The length on the Y axis.
	 */
	int getLengthY() {
		return Math.abs(upper_y - lower_y);
	}


	/**
	 * @return The length on the Z axis.
	 */
	int getLengthZ() {
		return Math.abs(upper_z - lower_z);
	}
}
