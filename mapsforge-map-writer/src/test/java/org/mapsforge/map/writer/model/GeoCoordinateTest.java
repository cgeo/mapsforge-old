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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Test GeoCoordinate Class with right and wrong values for the constructor and distance measurement.
 * 
 * @author Eike
 */
public class GeoCoordinateTest {

	/**
	 * See if the GeoCoordinate constructors work and if the equals comparison works.
	 */
	@Test
	public void geoCoordinateConstructorsAndEqualsTest() {
		GeoCoordinate alex = new GeoCoordinate(52.52235, 13.4125);
		assertTrue(alex.getLatitude() == 52.52235);
		assertTrue(alex.getLongitude() == 13.4125);

		GeoCoordinate bundestag = new GeoCoordinate(52518590, 13375400);
		assertTrue(bundestag.getLatitude() == 52.518590);
		assertTrue(bundestag.getLongitude() == 13.3754);

		GeoCoordinate bundestag2 = new GeoCoordinate(52.518590, 13.3754);
		assertEquals(bundestag, bundestag);
		assertEquals(bundestag, bundestag2);
		assertFalse(bundestag.equals(alex));
	}

	/**
	 * Tests if the WKT constructor works as expected.
	 */
	@Test
	public void geoCoordinateFromWellKnownTextTest() {
		GeoCoordinate alex = new GeoCoordinate("POINT(13.4125 52.52235)");
		assertTrue(alex.getLatitude() == 52.52235);
		assertTrue(alex.getLongitude() == 13.4125);
	}

	/**
	 * See if an exception is thrown if a invalid longitude is supplied.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void geoCoordinateIllegalLongitudeTest() {
		GeoCoordinate invalidCoordinate = new GeoCoordinate(0d, 181d);
		fail("invalid coordinate must throw exception: " + invalidCoordinate.toString());
	}

	/**
	 * See if an exception is thrown if a invalid latitude is supplied.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void geoCoordinateIllegalLatitudeTest() {
		GeoCoordinate invalidCoordinate = new GeoCoordinate(91d, 0d);
		fail("invalid coordinate must throw exception: " + invalidCoordinate.toString());
	}

	/**
	 * Run tests to see if the distance calculation methods work as expected.
	 */
	@Test
	public void geoCoordinateDistancesTest() {
		// This is the origin of the WGS-84 reference system
		GeoCoordinate zeroZero = new GeoCoordinate(0d, 0d);

		// This is the length of earth's equatorial circumference in meters
		double earthEquatorCircumference = WGS84.EQUATORIALRADIUS * 2 * Math.PI;

		// Here is a fun fact:
		// The original meter was defined as 1/10.000.000 the distance between the north pole
		// and the equator on the Paris meridian. The prototype which created turned out to be
		// very good, only hundreds of years later, after the meter definition had changed, it
		// was found out, that the distance measured in this meter was actually more than
		// 10.000.000 meters
		double distancePoleToEquator = 10001966.0; // 10.001,966 km

		// These coordinates are 1/4 Earth circumference from zeroZero on the equator
		GeoCoordinate nearSriLanka = new GeoCoordinate(0d, 90d);
		assertTrue(Math.abs(GeoCoordinate.sphericalDistance(zeroZero, nearSriLanka) - (earthEquatorCircumference / 4)) < 0.01);
		assertTrue(Math.abs(GeoCoordinate.vincentyDistance(zeroZero, nearSriLanka) - (earthEquatorCircumference / 4)) < 1);
		// On the equator the result of the different distance calculation methods should be
		// about the same
		assertTrue(Math.abs(GeoCoordinate.sphericalDistance(zeroZero, nearSriLanka)
				- GeoCoordinate.vincentyDistance(zeroZero, nearSriLanka)) < 0.01);

		// These coordinates are also 1/4 Earth circumference from zero on the equator
		GeoCoordinate islaGenovesa = new GeoCoordinate(0d, -90d);
		assertTrue(Math.abs(GeoCoordinate.sphericalDistance(zeroZero, islaGenovesa) - (earthEquatorCircumference / 4)) < 0.01);
		// These points are as far apart as they could be, half way around the earth
		assertTrue(Math.abs(GeoCoordinate.sphericalDistance(nearSriLanka, islaGenovesa)
				- (earthEquatorCircumference / 2)) < 0.01);

		// Calculating the distance between the north pole and the equator
		GeoCoordinate northPole = new GeoCoordinate(90d, 0d);
		assertTrue(Math.abs(GeoCoordinate.sphericalDistance(zeroZero, northPole) - (earthEquatorCircumference / 4)) < 0.01);
		assertTrue(Math.abs(GeoCoordinate.vincentyDistance(zeroZero, northPole) - distancePoleToEquator) < 1);

		// Check if the distance from pole to pole works as well in the vincentyDistance
		GeoCoordinate southPole = new GeoCoordinate(-90d, 0d);
		assertTrue(Math.abs(GeoCoordinate.vincentyDistance(southPole, northPole) - 2 * distancePoleToEquator) < 1);
	}
}
