/*
 * Copyright 2010, 2011, 2012 mapsforge.org
 *
 * This program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.mapsforge.map.writer.model;

/**
 * This class represents a bounding box.
 */
public class Rect {
	/**
	 * Checks if two rectangles overlap.
	 * 
	 * @param minLon1
	 *            bound of rectangle 1.
	 * @param maxLon1
	 *            bound of rectangle 1.
	 * @param minLat1
	 *            bound of rectangle 1.
	 * @param maxLat1
	 *            bound of rectangle 1.
	 * @param minLon2
	 *            bound of rectangle 2.
	 * @param maxLon2
	 *            bound of rectangle 2.
	 * @param minLat2
	 *            bound of rectangle 2.
	 * @param maxLat2
	 *            bound of rectangle 2.
	 * @return true if rectangles overlap.
	 */
	public static boolean overlaps(int minLon1, int maxLon1, int minLat1, int maxLat1, int minLon2, int maxLon2,
			int minLat2, int maxLat2) {
		return !(minLon1 > maxLon2 || minLon2 > maxLon1 || minLat1 > maxLat2 || minLat2 > maxLat1);
	}

	/**
	 * The maximum latitude of this rectangle.
	 */
	public int maxLatitudeE6;
	/**
	 * The maximum longitude of this rectangle.
	 */
	public int maxLongitudeE6;
	/**
	 * The minimum latitude of this rectangle.
	 */
	public int minLatitudeE6;
	/**
	 * The minimum longitude of this rectangle.
	 */
	public int minLongitudeE6;

	/**
	 * Used to compare this Rect with others in the {@link #equals(Object)} method.
	 */
	private Rect other;

	/**
	 * Constructs a rectangle defined by a single coordinate, i.e. minLongitudeE6 = maxLongitudeE6 and minLatitudeE6 =
	 * maxLatitudeE6.
	 * 
	 * @param gc
	 *            the coordinate to define the rectangle
	 */
	public Rect(GeoCoordinate gc) {
		this.minLatitudeE6 = gc.getLatitudeE6();
		this.maxLatitudeE6 = gc.getLatitudeE6();
		this.minLongitudeE6 = gc.getLongitudeE6();
		this.maxLongitudeE6 = gc.getLongitudeE6();
	}

	/**
	 * Constructs a rectangle, it is not checked if given values are valid coordinates.
	 * 
	 * @param minLongitudeE6
	 *            bound of the rectangle.
	 * @param maxLongitudeE6
	 *            bound of the rectangle.
	 * @param minLatitudeE6
	 *            bound of the rectangle.
	 * @param maxLatitudeE6
	 *            bound of the rectangle.
	 */
	public Rect(int minLongitudeE6, int maxLongitudeE6, int minLatitudeE6, int maxLatitudeE6) {
		this.minLongitudeE6 = minLongitudeE6;
		this.maxLongitudeE6 = maxLongitudeE6;
		this.minLatitudeE6 = minLatitudeE6;
		this.maxLatitudeE6 = maxLatitudeE6;
		validate();
	}

	/**
	 * Constructs a rectangle, it is not checked if given values are valid coordinates.
	 * 
	 * @param minLongitude
	 *            bound of the rectangle.
	 * @param maxLongitude
	 *            bound of the rectangle.
	 * @param minLatitude
	 *            bound of the rectangle.
	 * @param maxLatitude
	 *            bound of the rectangle.
	 */
	public Rect(double minLongitude, double maxLongitude, double minLatitude, double maxLatitude) {
		this.minLongitudeE6 = GeoCoordinate.doubleToInt(minLongitude);
		this.maxLongitudeE6 = GeoCoordinate.doubleToInt(maxLongitude);
		this.minLatitudeE6 = GeoCoordinate.doubleToInt(minLatitude);
		this.maxLatitudeE6 = GeoCoordinate.doubleToInt(maxLatitude);
		validate();
	}

	/**
	 * Creates a new rectangle from an existing one.
	 * 
	 * @param rect
	 *            the existing rectangle
	 */
	public Rect(Rect rect) {
		this.minLatitudeE6 = rect.minLatitudeE6;
		this.maxLatitudeE6 = rect.maxLatitudeE6;
		this.minLongitudeE6 = rect.minLongitudeE6;
		this.maxLongitudeE6 = rect.maxLongitudeE6;
	}

	/**
	 * Creates a new rectangle from a String containing comma-separated coordinates in the order minLat, minLon, maxLat,
	 * maxLon.
	 * 
	 * @param rectString
	 *            the String that describes the rectangle
	 * @return a new rectangle
	 * @throws IllegalArgumentException
	 *             if input String cannot be parsed, or coordinates describe an invalid rectangle
	 */
	public static Rect fromString(String rectString) {
		String[] splitted = rectString.split(",");
		if (splitted.length != 4) {
			throw new IllegalArgumentException(
					"expects 4 comma-separated values that define a bounding box, only found " + splitted.length);
		}
		GeoCoordinate upperLeft = GeoCoordinate.fromString(splitted[2] + "," + splitted[1]);
		GeoCoordinate bottomRight = GeoCoordinate.fromString(splitted[0] + "," + splitted[3]);
		return new Rect(upperLeft.getLongitudeE6(), bottomRight.getLongitudeE6(), bottomRight.getLatitudeE6(),
				upperLeft.getLatitudeE6());
	}

	private void validate() {
		if (this.minLatitudeE6 > this.maxLatitudeE6) {
			throw new IllegalArgumentException("minLat is greater than maxLat");
		}
		if (this.minLongitudeE6 > this.maxLongitudeE6) {
			throw new IllegalArgumentException("minLon is greater than maxLon");
		}
	}

	/**
	 * @return the minLongitudeE6
	 */
	public int getMinLongitudeE6() {
		return this.minLongitudeE6;
	}

	/**
	 * @return the maxLongitudeE6
	 */
	public int getMaxLongitudeE6() {
		return this.maxLongitudeE6;
	}

	/**
	 * @return the minLatitudeE6
	 */
	public int getMinLatitudeE6() {
		return this.minLatitudeE6;
	}

	/**
	 * @return the maxLatitudeE6
	 */
	public int getMaxLatitudeE6() {
		return this.maxLatitudeE6;
	}

	/**
	 * Computes the center of this rectangle.
	 * 
	 * @return the center of this rectangle as new GeoCoordinate
	 */
	public GeoCoordinate center() {
		return new GeoCoordinate(this.minLatitudeE6
				+ (int) Math.round((this.maxLatitudeE6 - this.minLatitudeE6) / 2.0d), this.minLongitudeE6
				+ (int) Math.round((this.maxLongitudeE6 - this.minLongitudeE6) / 2.0d));
	}

	/**
	 * Computes the distance between this and another rectangle. The distance between overlapping rectangles is 0.
	 * Otherwise, the distance is the spherical distance between the closest points.
	 * 
	 * @param otherRect
	 *            the rectangle to compute the distance
	 * @return the distance between this rectangle and the given one
	 */
	public double distance(Rect otherRect) {
		if (overlaps(otherRect)) {
			return 0;
		}

		int dLat1, dLat2;
		int dLon1, dLon2;

		if (this.maxLatitudeE6 < otherRect.minLatitudeE6) {
			dLat1 = this.maxLatitudeE6;
			dLat2 = otherRect.minLatitudeE6;
		} else if (this.minLatitudeE6 > otherRect.maxLatitudeE6) {
			dLat1 = this.minLatitudeE6;
			dLat2 = otherRect.maxLatitudeE6;
		} else {
			dLat1 = Math.min(this.maxLatitudeE6, otherRect.maxLatitudeE6);
			dLat2 = Math.min(this.maxLatitudeE6, otherRect.maxLatitudeE6);
		}

		if (this.maxLongitudeE6 < otherRect.minLongitudeE6) {
			dLon1 = this.maxLongitudeE6;
			dLon2 = otherRect.minLongitudeE6;
		} else if (this.minLongitudeE6 > otherRect.maxLongitudeE6) {
			dLon1 = this.minLongitudeE6;
			dLon2 = otherRect.maxLongitudeE6;
		} else {
			dLon1 = Math.min(this.maxLongitudeE6, otherRect.maxLongitudeE6);
			dLon2 = Math.min(this.maxLongitudeE6, otherRect.maxLongitudeE6);
		}

		return GeoCoordinate.sphericalDistance(dLon1, dLat1, dLon2, dLat2);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (!(obj instanceof Rect)) {
			return false;
		} else {
			this.other = (Rect) obj;
			if (this.maxLatitudeE6 != this.other.maxLatitudeE6) {
				return false;
			} else if (this.maxLongitudeE6 != this.other.maxLongitudeE6) {
				return false;
			} else if (this.minLatitudeE6 != this.other.minLatitudeE6) {
				return false;
			} else if (this.minLongitudeE6 != this.other.minLongitudeE6) {
				return false;
			}
			return true;
		}
	}

	/**
	 * Enlarges the boundary of the Rect so that it contains the coordinate. The special case occurring at longitude
	 * +180° and -180° is not considered.
	 * 
	 * @param gc
	 *            the coordinate which should be included in the rectangle
	 */
	public void expandToInclude(GeoCoordinate gc) {
		expandToInclude(gc.getLatitudeE6(), gc.getLongitudeE6());
	}

	/**
	 * Enlarges the boundary of the Rect so that it contains the coordinate. The special case occurring at longitude
	 * +180° and -180° is not considered.
	 * 
	 * @param latE6
	 *            the latitude of the coordinate
	 * @param lonE6
	 *            the longitude of the coordinate
	 */
	public void expandToInclude(int latE6, int lonE6) {
		this.minLatitudeE6 = Math.min(this.minLatitudeE6, latE6);
		this.maxLatitudeE6 = Math.max(this.maxLatitudeE6, latE6);
		this.minLongitudeE6 = Math.min(this.minLongitudeE6, lonE6);
		this.maxLongitudeE6 = Math.max(this.maxLongitudeE6, lonE6);
	}

	/**
	 * Enlarges the boundary of the Rect so that it contains the rectangle. The special case occurring at longitude
	 * +180° and -180° is not considered.
	 * 
	 * @param otherRect
	 *            the other rectangle which should be included in the rectangle
	 */
	public void expandToInclude(Rect otherRect) {
		expandToInclude(otherRect.minLatitudeE6, otherRect.minLongitudeE6);
		expandToInclude(otherRect.maxLatitudeE6, otherRect.maxLongitudeE6);
	}

	/**
	 * @return Returns the coordinate lying in the middle of this rectangle.
	 */
	public GeoCoordinate getCenter() {
		return new GeoCoordinate((this.minLatitudeE6 + this.maxLatitudeE6) / 2,
				(this.minLongitudeE6 + this.maxLongitudeE6) / 2);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.maxLatitudeE6;
		result = prime * result + this.maxLongitudeE6;
		result = prime * result + this.minLatitudeE6;
		result = prime * result + this.minLongitudeE6;
		return result;
	}

	/**
	 * Checks if the coordinate lies within this rectangle.
	 * 
	 * @param c
	 *            coordinate to check.
	 * @return Returns true if the given coordinate lies within this rectangle.
	 */
	public boolean includes(GeoCoordinate c) {
		return includes(c.getLatitudeE6(), c.getLongitudeE6());
	}

	/**
	 * Checks if the coordinate lies within this rectangle.
	 * 
	 * @param latitudeE6
	 *            coordinate to check.
	 * @param longitudeE6
	 *            coordinate to check.
	 * @return Returns true if the given coordinate lies within this rectangle.
	 */
	public boolean includes(int latitudeE6, int longitudeE6) {
		return latitudeE6 >= this.minLatitudeE6 && latitudeE6 <= this.maxLatitudeE6
				&& longitudeE6 >= this.minLongitudeE6 && longitudeE6 <= this.maxLongitudeE6;
	}

	/**
	 * Checks if this rectangle. overlaps another rectangle.
	 * 
	 * @param r
	 *            other rectangle to be tested against overlap.
	 * @return true if rectangles overlap.
	 */
	public boolean overlaps(Rect r) {
		return overlaps(this.minLongitudeE6, this.maxLongitudeE6, this.minLatitudeE6, this.maxLatitudeE6,
				r.minLongitudeE6, r.maxLongitudeE6, r.minLatitudeE6, r.maxLatitudeE6);
	}

	@Override
	public String toString() {
		return "[ (" + this.minLongitudeE6 + "," + this.minLatitudeE6 + ") (" + this.maxLongitudeE6 + ","
				+ this.maxLatitudeE6 + ") ]";
	}
}
