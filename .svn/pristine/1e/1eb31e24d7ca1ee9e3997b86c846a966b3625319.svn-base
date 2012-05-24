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
package org.mapsforge.map.reader;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.mapsforge.core.MercatorProjection;
import org.mapsforge.core.Tag;
import org.mapsforge.core.Tile;
import org.mapsforge.map.reader.DummyMapDatabaseCallback.PointOfInterest;
import org.mapsforge.map.reader.DummyMapDatabaseCallback.Way;
import org.mapsforge.map.reader.header.FileOpenResult;
import org.mapsforge.map.reader.header.MapFileInfo;

/**
 * Tests the {@link MapDatabase} class.
 */
public class MapDatabaseWithDataTest {
	private static final File MAP_FILE = new File("src/test/resources/with_data/with_data.map");
	private static final byte ZOOM_LEVEL_MAX = 11;
	private static final int ZOOM_LEVEL_MIN = 6;

	private static void checkPointOfInterest(PointOfInterest pointOfInterest) {
		Assert.assertEquals(7, pointOfInterest.layer);
		Assert.assertEquals(40000, pointOfInterest.latitude);
		Assert.assertEquals(80000, pointOfInterest.longitude);
		Assert.assertEquals(4, pointOfInterest.tags.size());
		Assert.assertTrue(pointOfInterest.tags.contains(new Tag("place=country")));
		Assert.assertTrue(pointOfInterest.tags.contains(new Tag("name=АБВГДЕЖЗ")));
		Assert.assertTrue(pointOfInterest.tags.contains(new Tag("addr:housenumber=абвгдежз")));
		Assert.assertTrue(pointOfInterest.tags.contains(new Tag("ele=25")));
	}

	private static void checkWay(Way way) {
		Assert.assertEquals(4, way.layer);
		Assert.assertNull(way.labelPosition);
		float[][] wayNodesExpected = new float[][] { { 0, 0, 80000, 40000, 0, 80000 } };
		Assert.assertArrayEquals(wayNodesExpected, way.wayNodes);
		Assert.assertEquals(3, way.tags.size());
		Assert.assertTrue(way.tags.contains(new Tag("highway=motorway")));
		Assert.assertTrue(way.tags.contains(new Tag("name=ÄÖÜ")));
		Assert.assertTrue(way.tags.contains(new Tag("ref=äöü")));
	}

	/**
	 * Tests the {@link MapDatabase#executeQuery(Tile, MapDatabaseCallback)} method.
	 */
	@Test
	public void executeQueryTest() {
		MapDatabase mapDatabase = new MapDatabase();
		FileOpenResult fileOpenResult = mapDatabase.openFile(MAP_FILE);
		Assert.assertTrue(mapDatabase.hasOpenFile());
		Assert.assertTrue(fileOpenResult.getErrorMessage(), fileOpenResult.isSuccess());

		MapFileInfo mapFileInfo = mapDatabase.getMapFileInfo();
		Assert.assertTrue(mapFileInfo.debugFile);

		for (byte zoomLevel = ZOOM_LEVEL_MIN; zoomLevel <= ZOOM_LEVEL_MAX; ++zoomLevel) {
			long tileX = MercatorProjection.longitudeToTileX(0.04, zoomLevel);
			long tileY = MercatorProjection.latitudeToTileY(0.04, zoomLevel);
			Tile tile = new Tile(tileX, tileY, zoomLevel);

			DummyMapDatabaseCallback dummyMapDatabaseCallback = new DummyMapDatabaseCallback();
			mapDatabase.executeQuery(tile, dummyMapDatabaseCallback);

			Assert.assertEquals(1, dummyMapDatabaseCallback.pointOfInterests.size());
			Assert.assertEquals(1, dummyMapDatabaseCallback.ways.size());

			checkPointOfInterest(dummyMapDatabaseCallback.pointOfInterests.get(0));
			checkWay(dummyMapDatabaseCallback.ways.get(0));
		}

		mapDatabase.closeFile();
		Assert.assertFalse(mapDatabase.hasOpenFile());
	}
}
