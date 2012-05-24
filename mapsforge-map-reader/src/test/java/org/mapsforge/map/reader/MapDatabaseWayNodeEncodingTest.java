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
import org.mapsforge.map.reader.DummyMapDatabaseCallback.Way;
import org.mapsforge.map.reader.header.FileOpenResult;

/**
 * Tests the {@link MapDatabase} class.
 */
public class MapDatabaseWayNodeEncodingTest {
	private static final File MAP_FILE_DOUBLE_DELTA = new File("src/test/resources/way_node_encoding/double_delta.map");
	private static final File MAP_FILE_SINGLE_DELTA = new File("src/test/resources/way_node_encoding/single_delta.map");
	private static final byte ZOOM_LEVEL = 8;

	private static void runTest(File mapFile) {
		MapDatabase mapDatabase = new MapDatabase();
		FileOpenResult fileOpenResult = mapDatabase.openFile(mapFile);
		Assert.assertTrue(fileOpenResult.getErrorMessage(), fileOpenResult.isSuccess());

		long tileX = MercatorProjection.longitudeToTileX(0, ZOOM_LEVEL);
		long tileY = MercatorProjection.latitudeToTileY(0, ZOOM_LEVEL);
		Tile tile = new Tile(tileX, tileY, ZOOM_LEVEL);

		DummyMapDatabaseCallback dummyMapDatabaseCallback = new DummyMapDatabaseCallback();
		mapDatabase.executeQuery(tile, dummyMapDatabaseCallback);
		mapDatabase.closeFile();

		Assert.assertTrue(dummyMapDatabaseCallback.pointOfInterests.isEmpty());
		Assert.assertEquals(1, dummyMapDatabaseCallback.ways.size());

		Way way = dummyMapDatabaseCallback.ways.get(0);
		float[][] wayNodesExpected = new float[][] { { 0, 0, 100000, 0, 100000, -100000, 0, -100000, 0, 0 } };
		Assert.assertArrayEquals(wayNodesExpected, way.wayNodes);
	}

	/**
	 * Tests the {@link MapDatabase#executeQuery(Tile, MapDatabaseCallback)} method.
	 */
	@Test
	public void executeQueryTest() {
		runTest(MAP_FILE_SINGLE_DELTA);
		runTest(MAP_FILE_DOUBLE_DELTA);
	}
}
