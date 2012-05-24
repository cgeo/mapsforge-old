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
import org.mapsforge.core.Tile;
import org.mapsforge.map.reader.header.FileOpenResult;

/**
 * Tests the {@link MapDatabase} class.
 */
public class MapDatabaseEmptyTest {
	private static final File MAP_FILE = new File("src/test/resources/empty/empty.map");
	private static final byte ZOOM_LEVEL_MAX = 25;

	/**
	 * Tests the {@link MapDatabase#executeQuery(Tile, MapDatabaseCallback)} method.
	 */
	@Test
	public void executeQueryTest() {
		MapDatabase mapDatabase = new MapDatabase();
		FileOpenResult fileOpenResult = mapDatabase.openFile(MAP_FILE);
		Assert.assertTrue(mapDatabase.hasOpenFile());
		Assert.assertTrue(fileOpenResult.getErrorMessage(), fileOpenResult.isSuccess());

		for (byte zoomLevel = 0; zoomLevel <= ZOOM_LEVEL_MAX; ++zoomLevel) {
			long tileX = MercatorProjection.longitudeToTileX(1, zoomLevel);
			long tileY = MercatorProjection.latitudeToTileY(1, zoomLevel);
			Tile tile = new Tile(tileX, tileY, zoomLevel);

			DummyMapDatabaseCallback dummyMapDatabaseCallback = new DummyMapDatabaseCallback();
			mapDatabase.executeQuery(tile, dummyMapDatabaseCallback);

			Assert.assertTrue(dummyMapDatabaseCallback.pointOfInterests.isEmpty());
			Assert.assertTrue(dummyMapDatabaseCallback.ways.isEmpty());
		}

		mapDatabase.closeFile();
		Assert.assertFalse(mapDatabase.hasOpenFile());
	}
}
