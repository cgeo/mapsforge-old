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

import org.junit.Test;

/**
 * Junit tests for the rectangle functions.
 * 
 * @author bross
 */
public class RectTest {

	/**
	 * Tests the computation of the center of a rectangle.
	 */
	@Test
	public void testCenter() {
		Rect r1 = new Rect(0, 1000000, 50000000, 51000000);
		assertEquals(new GeoCoordinate(50500000, 500000), r1.center());
	}

	/**
	 * Tests expansion by inclusion of a coordinate.
	 */
	@Test
	public void testExpandToIncludeGeoCoordinate() {
		Rect r1 = new Rect(0, 1000000, 50000000, 51000000);

		// on boundary
		GeoCoordinate gc1 = new GeoCoordinate(50000000, 0);
		// in boundary
		GeoCoordinate gc2 = new GeoCoordinate(50500000, 500000);
		// left of boundary
		GeoCoordinate gc3 = new GeoCoordinate(50500000, -1000000);
		// right of boundary
		GeoCoordinate gc4 = new GeoCoordinate(50500000, 2000000);
		// top of boundary
		GeoCoordinate gc5 = new GeoCoordinate(52000000, 500000);
		// bottom of boundary
		GeoCoordinate gc6 = new GeoCoordinate(49000000, 500000);

		r1.expandToInclude(gc1);
		assertEquals(new Rect(0, 1000000, 50000000, 51000000), r1);
		r1.expandToInclude(gc2);
		assertEquals(new Rect(0, 1000000, 50000000, 51000000), r1);
		r1.expandToInclude(gc3);
		assertEquals(new Rect(-1000000, 1000000, 50000000, 51000000), r1);
		r1.expandToInclude(gc4);
		assertEquals(new Rect(-1000000, 2000000, 50000000, 51000000), r1);
		r1.expandToInclude(gc5);
		assertEquals(new Rect(-1000000, 2000000, 50000000, 52000000), r1);
		r1.expandToInclude(gc6);
		assertEquals(new Rect(-1000000, 2000000, 49000000, 52000000), r1);
	}

	/**
	 * Tests expansion by inclusion of a rectangle.
	 */
	@Test
	public void testExpandToIncludeRect() {
		Rect r1 = new Rect(0, 1000000, 50000000, 51000000);

		// left overlap
		Rect r2 = new Rect(-1000000, 1000000, 55000000, 56000000);

		// included
		Rect r3 = new Rect(250000, 750000, 50250000, 50750000);

		// same
		Rect r4 = new Rect(0, 1000000, 50000000, 51000000);

		// outside
		Rect r5 = new Rect(-2000000, -1000000, 45000000, 49000000);

		r2.expandToInclude(r1);
		assertEquals(new Rect(-1000000, 1000000, 50000000, 56000000), r2);
		r3.expandToInclude(r1);
		assertEquals(r1, r3);
		r4.expandToInclude(r1);
		assertEquals(r1, r3);
		r5.expandToInclude(r1);
		assertEquals(new Rect(-2000000, 1000000, 45000000, 51000000), r5);
	}

	/**
	 * Tests distance function.
	 */
	@Test
	public void testDistance() {
		Rect r1 = new Rect(0, 1000000, 50000000, 51000000);

		// same
		Rect r2 = new Rect(0, 1000000, 50000000, 51000000);
		// single boundary in common
		Rect r3 = new Rect(-1000000, 0, 45000000, 55000000);
		// inclusion
		Rect r4 = new Rect(-1000000, 2000000, 45000000, 55000000);
		// outside, top-right
		Rect r5 = new Rect(2000000, 3000000, 52000000, 53000000);
		// outside, bottom-left
		Rect r6 = new Rect(-2000000, -1000000, 48000000, 49000000);
		// outside, bottom, larger
		Rect r7 = new Rect(-2000000, 2000000, 48000000, 49000000);

		assertEquals(0, r1.distance(r2), 0);
		assertEquals(0, r1.distance(r3), 0);
		assertEquals(0, r1.distance(r4), 0);

		assertEquals(GeoCoordinate.sphericalDistance(1000000, 51000000, 2000000, 52000000), r1.distance(r5), 0);
		assertEquals(GeoCoordinate.sphericalDistance(0, 50000000, -1000000, 49000000), r1.distance(r6), 0);
		assertEquals(GeoCoordinate.sphericalDistance(0, 50000000, 0, 49000000), r1.distance(r7), 0);
	}

	/**
	 * Tests overlap function.
	 */
	@Test
	public void testOverlapsRect() {
		Rect r1 = new Rect(0, 1000000, 50000000, 51000000);

		// same
		Rect r2 = new Rect(0, 1000000, 50000000, 51000000);
		// single boundary in common
		Rect r3 = new Rect(-1000000, 0, 45000000, 55000000);
		// inclusion
		Rect r4 = new Rect(-1000000, 2000000, 45000000, 55000000);
		// outside
		Rect r5 = new Rect(0, 1000000, 55000000, 56000000);

		assertTrue(r1.overlaps(r2));
		assertTrue(r1.overlaps(r3));
		assertTrue(r1.overlaps(r4));
		assertFalse(r1.overlaps(r5));

	}

}
