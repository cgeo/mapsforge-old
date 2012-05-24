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
 * Tests the {@link MapPosition} class.
 */
public class MapPositionTest {
	private static final GeoPoint GEO_POINT = new GeoPoint(1, 2);
	private static final String MAP_POSITION_TO_STRING = "MapPosition [geoPoint=GeoPoint [latitudeE6=1, longitudeE6=2], zoomLevel=3]";
	private static final byte ZOOM_LEVEL = 3;

	/**
	 * Tests the {@link MapPosition#equals(Object)} and the {@link MapPosition#hashCode()} method.
	 */
	@Test
	public void equalsTest() {
		MapPosition mapPosition1 = new MapPosition(GEO_POINT, ZOOM_LEVEL);
		MapPosition mapPosition2 = new MapPosition(GEO_POINT, ZOOM_LEVEL);
		MapPosition mapPosition3 = new MapPosition(GEO_POINT, (byte) 0);

		TestUtils.equalsTest(mapPosition1, mapPosition2);

		Assert.assertFalse(mapPosition1.equals(mapPosition3));
		Assert.assertFalse(mapPosition3.equals(mapPosition1));
		Assert.assertFalse(mapPosition1.equals(new Object()));
	}

	/**
	 * Tests the public fields and the getter-methods.
	 */
	@Test
	public void getterTest() {
		MapPosition mapPosition = new MapPosition(GEO_POINT, ZOOM_LEVEL);

		Assert.assertEquals(GEO_POINT, mapPosition.geoPoint);
		Assert.assertEquals(ZOOM_LEVEL, mapPosition.zoomLevel);
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
		MapPosition mapPosition = new MapPosition(GEO_POINT, ZOOM_LEVEL);
		TestUtils.serializeTest(mapPosition);
	}

	/**
	 * Tests the {@link MapPosition#toString()} method.
	 */
	@Test
	public void toStringTest() {
		MapPosition mapPosition = new MapPosition(GEO_POINT, ZOOM_LEVEL);
		Assert.assertEquals(MAP_POSITION_TO_STRING, mapPosition.toString());
	}
}
