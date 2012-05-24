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
 * Tests the {@link GeoPoint} class.
 */
public class GeoPointTest {
	private static final double CONVERSION_FACTOR = 1000000d;
	private static final String GEO_POINT_TO_STRING = "GeoPoint [latitudeE6=1, longitudeE6=2]";
	private static final int LATITUDE = 1;
	private static final int LONGITUDE = 2;

	/**
	 * Tests the {@link GeoPoint#compareTo(GeoPoint)} method.
	 */
	@Test
	public void compareToTest() {
		GeoPoint geoPoint1 = new GeoPoint(LATITUDE, LONGITUDE);
		GeoPoint geoPoint2 = new GeoPoint(LATITUDE, LONGITUDE);
		GeoPoint geoPoint3 = new GeoPoint(0, 0);

		Assert.assertEquals(0, geoPoint1.compareTo(geoPoint2));
		Assert.assertFalse(geoPoint1.compareTo(geoPoint3) == 0);
	}

	/**
	 * Tests the constructors.
	 */
	@Test
	public void constructorTest() {
		GeoPoint geoPoint1 = new GeoPoint(LATITUDE / CONVERSION_FACTOR, LONGITUDE / CONVERSION_FACTOR);
		GeoPoint geoPoint2 = new GeoPoint(LATITUDE, LONGITUDE);

		TestUtils.equalsTest(geoPoint1, geoPoint2);
	}

	/**
	 * Tests the {@link GeoPoint#equals(Object)} and the {@link GeoPoint#hashCode()} method.
	 */
	@Test
	public void equalsTest() {
		GeoPoint geoPoint1 = new GeoPoint(LATITUDE, LONGITUDE);
		GeoPoint geoPoint2 = new GeoPoint(LATITUDE, LONGITUDE);
		GeoPoint geoPoint3 = new GeoPoint(0, 0);

		TestUtils.equalsTest(geoPoint1, geoPoint2);

		Assert.assertFalse(geoPoint1.equals(geoPoint3));
		Assert.assertFalse(geoPoint3.equals(geoPoint1));
		Assert.assertFalse(geoPoint1.equals(new Object()));
	}

	/**
	 * Tests the public fields and the getter-methods.
	 */
	@Test
	public void getterTest() {
		GeoPoint geoPoint = new GeoPoint(LATITUDE, LONGITUDE);

		Assert.assertEquals(LATITUDE, geoPoint.latitudeE6);
		Assert.assertEquals(LONGITUDE, geoPoint.longitudeE6);

		Assert.assertEquals(LATITUDE, geoPoint.getLatitude() * CONVERSION_FACTOR, 0);
		Assert.assertEquals(LONGITUDE, geoPoint.getLongitude() * CONVERSION_FACTOR, 0);
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
		GeoPoint geoPoint = new GeoPoint(LATITUDE, LONGITUDE);
		TestUtils.serializeTest(geoPoint);
	}

	/**
	 * Tests the {@link GeoPoint#toString()} method.
	 */
	@Test
	public void toStringTest() {
		GeoPoint geoPoint = new GeoPoint(LATITUDE, LONGITUDE);
		Assert.assertEquals(GEO_POINT_TO_STRING, geoPoint.toString());
	}
}
