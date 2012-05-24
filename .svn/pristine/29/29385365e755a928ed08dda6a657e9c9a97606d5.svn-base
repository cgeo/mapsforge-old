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
package org.mapsforge.android.maps.mapgenerator.tiledownloader;

import junit.framework.Assert;

import org.junit.Test;
import org.mapsforge.core.Tile;

/**
 * Tests the {@link OpenCycleMapTileDownloader} class.
 */
public class OpenCycleMapTileDownloaderTest {
	/**
	 * Tests the {@link OpenCycleMapTileDownloader#getTilePath} method.
	 */
	@Test
	public void getTilePathTest() {
		TileDownloader tileDownloader = new OpenCycleMapTileDownloader();
		Tile tile = new Tile(1, 2, (byte) 3);
		String tilePath = tileDownloader.getTilePath(tile);
		Assert.assertEquals("/cycle/3/1/2.png", tilePath);
	}
}
