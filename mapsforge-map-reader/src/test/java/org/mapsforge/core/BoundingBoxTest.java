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
package org.mapsforge.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Tests the {@link BoundingBox} class.
 */
public class BoundingBoxTest {
	private static final String BOUNDING_BOX_TO_STRING = "BoundingBox [minLatitudeE6=1, minLongitudeE6=0, maxLatitudeE6=3, maxLongitudeE6=2]";
	private static final double CONVERSION_FACTOR = 1000000d;
	private static final int MAX_LATITUDE = 3;
	private static final int MAX_LONGITUDE = 2;
	private static final int MIN_LATITUDE = 1;
	private static final int MIN_LONGITUDE = 0;

	/**
	 * Tests the {@link BoundingBox#contains(GeoPoint)} method.
	 */
	@Test
	public void containsTest() {
		BoundingBox boundingBox = new BoundingBox(MIN_LATITUDE, MIN_LONGITUDE, MAX_LATITUDE, MAX_LONGITUDE);
		GeoPoint geoPoint1 = new GeoPoint(MIN_LATITUDE, MIN_LONGITUDE);
		GeoPoint geoPoint2 = new GeoPoint(0, 0);

		Assert.assertTrue(boundingBox.contains(geoPoint1));
		Assert.assertFalse(boundingBox.contains(geoPoint2));
	}

	/**
	 * Tests the {@link BoundingBox#equals(Object)} and the {@link BoundingBox#hashCode()} method.
	 */
	@Test
	public void equalsTest() {
		BoundingBox boundingBox1 = new BoundingBox(MIN_LATITUDE, MIN_LONGITUDE, MAX_LATITUDE, MAX_LONGITUDE);
		BoundingBox boundingBox2 = new BoundingBox(MIN_LATITUDE, MIN_LONGITUDE, MAX_LATITUDE, MAX_LONGITUDE);
		BoundingBox boundingBox3 = new BoundingBox(0, 0, 0, 0);

		TestUtils.equalsTest(boundingBox1, boundingBox2);

		Assert.assertFalse(boundingBox1.equals(boundingBox3));
		Assert.assertFalse(boundingBox3.equals(boundingBox1));
		Assert.assertFalse(boundingBox1.equals(new Object()));
	}

	/**
	 * Tests the public fields and the getter-methods.
	 */
	@Test
	public void getterTest() {
		BoundingBox boundingBox = new BoundingBox(MIN_LATITUDE, MIN_LONGITUDE, MAX_LATITUDE, MAX_LONGITUDE);

		Assert.assertEquals(MIN_LATITUDE, boundingBox.minLatitudeE6);
		Assert.assertEquals(MIN_LONGITUDE, boundingBox.minLongitudeE6);
		Assert.assertEquals(MAX_LATITUDE, boundingBox.maxLatitudeE6);
		Assert.assertEquals(MAX_LONGITUDE, boundingBox.maxLongitudeE6);

		Assert.assertEquals(MIN_LATITUDE, boundingBox.getMinLatitude() * CONVERSION_FACTOR, 0);
		Assert.assertEquals(MIN_LONGITUDE, boundingBox.getMinLongitude() * CONVERSION_FACTOR, 0);
		Assert.assertEquals(MAX_LATITUDE, boundingBox.getMaxLatitude() * CONVERSION_FACTOR, 0);
		Assert.assertEquals(MAX_LONGITUDE, boundingBox.getMaxLongitude() * CONVERSION_FACTOR, 0);
	}

	/**
	 * Tests the serialization and deserialization methods.
	 * 
	 * @throws IOException
	 *             see {@link ObjectOutputStream#writeObject(Object)}
	 * @throws ClassNotFoundException
	 *             see {@link ObjectInputStream#readObject()}
	 */
	@Test
	public void serializeTest() throws IOException, ClassNotFoundException {
		BoundingBox boundingBox = new BoundingBox(MIN_LATITUDE, MIN_LONGITUDE, MAX_LATITUDE, MAX_LONGITUDE);
		TestUtils.serializeTest(boundingBox);
	}

	/**
	 * Tests the {@link BoundingBox#toString()} method.
	 */
	@Test
	public void toStringTest() {
		BoundingBox boundingBox = new BoundingBox(MIN_LATITUDE, MIN_LONGITUDE, MAX_LATITUDE, MAX_LONGITUDE);
		Assert.assertEquals(BOUNDING_BOX_TO_STRING, boundingBox.toString());
	}
}
