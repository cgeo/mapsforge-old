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

import java.util.ArrayList;
import java.util.List;

import org.mapsforge.core.Tag;

class DummyMapDatabaseCallback implements MapDatabaseCallback {
	static class PointOfInterest {
		final int latitude;
		final byte layer;
		final int longitude;
		final List<Tag> tags;

		PointOfInterest(byte layer, int latitude, int longitude, List<Tag> tags) {
			this.layer = layer;
			this.latitude = latitude;
			this.longitude = longitude;
			this.tags = tags;
		}
	}

	static class Way {
		final float[] labelPosition;
		final byte layer;
		final List<Tag> tags;
		final float[][] wayNodes;

		Way(byte layer, float[] labelPosition, List<Tag> tags, float[][] wayNodes) {
			this.layer = layer;
			this.labelPosition = labelPosition;
			this.tags = tags;
			this.wayNodes = wayNodes;
		}
	}

	final List<PointOfInterest> pointOfInterests = new ArrayList<PointOfInterest>();
	int waterBackground;
	final List<Way> ways = new ArrayList<Way>();

	@Override
	public void renderPointOfInterest(byte layer, int latitude, int longitude, List<Tag> tags) {
		PointOfInterest pointOfInterest = new PointOfInterest(layer, latitude, longitude, tags);
		this.pointOfInterests.add(pointOfInterest);
	}

	@Override
	public void renderWaterBackground() {
		++this.waterBackground;
	}

	@Override
	public void renderWay(byte layer, float[] labelPosition, List<Tag> tags, float[][] wayNodes) {
		Way way = new Way(layer, labelPosition, tags, wayNodes);
		this.ways.add(way);
	}
}
