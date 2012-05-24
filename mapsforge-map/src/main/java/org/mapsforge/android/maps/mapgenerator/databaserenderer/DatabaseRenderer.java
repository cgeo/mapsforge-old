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
package org.mapsforge.android.maps.mapgenerator.databaserenderer;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;

import org.mapsforge.android.maps.mapgenerator.JobTheme;
import org.mapsforge.android.maps.mapgenerator.MapGenerator;
import org.mapsforge.android.maps.mapgenerator.MapGeneratorJob;
import org.mapsforge.android.maps.rendertheme.RenderCallback;
import org.mapsforge.android.maps.rendertheme.RenderTheme;
import org.mapsforge.android.maps.rendertheme.RenderThemeHandler;
import org.mapsforge.core.GeoPoint;
import org.mapsforge.core.MercatorProjection;
import org.mapsforge.core.Tag;
import org.mapsforge.core.Tile;
import org.mapsforge.map.reader.MapDatabase;
import org.mapsforge.map.reader.MapDatabaseCallback;
import org.mapsforge.map.reader.header.MapFileInfo;
import org.xml.sax.SAXException;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * A DatabaseRenderer renders map tiles by reading from a {@link MapDatabase}.
 */
public class DatabaseRenderer implements MapGenerator, RenderCallback, MapDatabaseCallback {
	private static final Byte DEFAULT_START_ZOOM_LEVEL = Byte.valueOf((byte) 12);
	private static final byte LAYERS = 11;
	private static final Logger LOG = Logger.getLogger(DatabaseRenderer.class.getName());
	private static final Paint PAINT_WATER_TILE_HIGHTLIGHT = new Paint(Paint.ANTI_ALIAS_FLAG);
	private static final double STROKE_INCREASE = 1.5;
	private static final byte STROKE_MIN_ZOOM_LEVEL = 12;
	private static final Tag TAG_NATURAL_WATER = new Tag("natural", "water");
	private static final float[][] WATER_TILE_COORDINATES = new float[][] { { 0, 0, Tile.TILE_SIZE, 0, Tile.TILE_SIZE,
			Tile.TILE_SIZE, 0, Tile.TILE_SIZE, 0, 0 } };
	private static final byte ZOOM_MAX = 22;

	private static RenderTheme getRenderTheme(JobTheme jobTheme) {
		InputStream inputStream = null;
		try {
			inputStream = jobTheme.getRenderThemeAsStream();
			return RenderThemeHandler.getRenderTheme(inputStream);
		} catch (ParserConfigurationException e) {
			LOG.log(Level.SEVERE, null, e);
		} catch (SAXException e) {
			LOG.log(Level.SEVERE, null, e);
		} catch (IOException e) {
			LOG.log(Level.SEVERE, null, e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				LOG.log(Level.SEVERE, null, e);
			}
		}
		return null;
	}

	private static byte getValidLayer(byte layer) {
		if (layer < 0) {
			return 0;
		} else if (layer >= LAYERS) {
			return LAYERS - 1;
		} else {
			return layer;
		}
	}

	private final List<PointTextContainer> areaLabels;
	private final CanvasRasterer canvasRasterer;
	private float[][] coordinates;
	private Tile currentTile;
	private List<List<ShapePaintContainer>> drawingLayer;
	private final LabelPlacement labelPlacement;
	private MapDatabase mapDatabase;
	private List<PointTextContainer> nodes;
	private final List<SymbolContainer> pointSymbols;
	private float poiX;
	private float poiY;
	private JobTheme previousJobTheme;
	private float previousTextScale;
	private byte previousZoomLevel;
	private RenderTheme renderTheme;
	private ShapeContainer shapeContainer;
	private final List<Tag> tagList;
	private final List<WayTextContainer> wayNames;
	private final List<List<List<ShapePaintContainer>>> ways;
	private final List<SymbolContainer> waySymbols;

	/**
	 * Constructs a new DatabaseRenderer.
	 */
	public DatabaseRenderer() {
		this.canvasRasterer = new CanvasRasterer();
		this.labelPlacement = new LabelPlacement();

		this.ways = new ArrayList<List<List<ShapePaintContainer>>>(LAYERS);
		this.wayNames = new ArrayList<WayTextContainer>(64);
		this.nodes = new ArrayList<PointTextContainer>(64);
		this.areaLabels = new ArrayList<PointTextContainer>(64);
		this.waySymbols = new ArrayList<SymbolContainer>(64);
		this.pointSymbols = new ArrayList<SymbolContainer>(64);
		this.tagList = new ArrayList<Tag>(2);

		PAINT_WATER_TILE_HIGHTLIGHT.setStyle(Paint.Style.FILL);
		PAINT_WATER_TILE_HIGHTLIGHT.setColor(Color.CYAN);
	}

	@Override
	public void cleanup() {
		if (this.renderTheme != null) {
			this.renderTheme.destroy();
		}
	}

	@Override
	public boolean executeJob(MapGeneratorJob mapGeneratorJob, Bitmap bitmap) {
		this.currentTile = mapGeneratorJob.tile;

		JobTheme jobTheme = mapGeneratorJob.jobParameters.jobTheme;
		if (!jobTheme.equals(this.previousJobTheme)) {
			this.renderTheme = getRenderTheme(jobTheme);
			if (this.renderTheme == null) {
				this.previousJobTheme = null;
				return false;
			}
			createWayLists();
			this.previousJobTheme = jobTheme;
			this.previousZoomLevel = Byte.MIN_VALUE;
		}

		byte zoomLevel = this.currentTile.zoomLevel;
		if (zoomLevel != this.previousZoomLevel) {
			setScaleStrokeWidth(zoomLevel);
			this.previousZoomLevel = zoomLevel;
		}

		float textScale = mapGeneratorJob.jobParameters.textScale;
		if (Float.compare(textScale, this.previousTextScale) != 0) {
			this.renderTheme.scaleTextSize(textScale);
			this.previousTextScale = textScale;
		}

		if (this.mapDatabase != null) {
			this.mapDatabase.executeQuery(this.currentTile, this);
		}

		this.nodes = this.labelPlacement.placeLabels(this.nodes, this.pointSymbols, this.areaLabels, this.currentTile);

		this.canvasRasterer.setCanvasBitmap(bitmap);
		this.canvasRasterer.fill(this.renderTheme.getMapBackground());
		this.canvasRasterer.drawWays(this.ways);
		this.canvasRasterer.drawSymbols(this.waySymbols);
		this.canvasRasterer.drawSymbols(this.pointSymbols);
		this.canvasRasterer.drawWayNames(this.wayNames);
		this.canvasRasterer.drawNodes(this.nodes);
		this.canvasRasterer.drawNodes(this.areaLabels);

		if (mapGeneratorJob.debugSettings.drawTileFrames) {
			this.canvasRasterer.drawTileFrame();
		}

		if (mapGeneratorJob.debugSettings.drawTileCoordinates) {
			this.canvasRasterer.drawTileCoordinates(this.currentTile);
		}

		clearLists();

		return true;
	}

	@Override
	public GeoPoint getStartPoint() {
		if (this.mapDatabase != null && this.mapDatabase.hasOpenFile()) {
			MapFileInfo mapFileInfo = this.mapDatabase.getMapFileInfo();
			if (mapFileInfo.startPosition != null) {
				return mapFileInfo.startPosition;
			} else if (mapFileInfo.mapCenter != null) {
				return mapFileInfo.mapCenter;
			}
		}

		return null;
	}

	@Override
	public Byte getStartZoomLevel() {
		if (this.mapDatabase != null && this.mapDatabase.hasOpenFile()) {
			MapFileInfo mapFileInfo = this.mapDatabase.getMapFileInfo();
			if (mapFileInfo.startZoomLevel != null) {
				return mapFileInfo.startZoomLevel;
			}
		}

		return DEFAULT_START_ZOOM_LEVEL;
	}

	@Override
	public byte getZoomLevelMax() {
		return ZOOM_MAX;
	}

	@Override
	public void renderArea(Paint paint, int level) {
		this.drawingLayer.get(level).add(new ShapePaintContainer(this.shapeContainer, paint));
	}

	@Override
	public void renderAreaCaption(String caption, float verticalOffset, Paint paint, Paint stroke) {
		float[] centerPosition = GeometryUtils.calculateCenterOfBoundingBox(this.coordinates[0]);
		this.areaLabels.add(new PointTextContainer(caption, centerPosition[0], centerPosition[1], paint, stroke));
	}

	@Override
	public void renderAreaSymbol(Bitmap symbol) {
		float[] centerPosition = GeometryUtils.calculateCenterOfBoundingBox(this.coordinates[0]);
		this.pointSymbols.add(new SymbolContainer(symbol, centerPosition[0] - (symbol.getWidth() >> 1),
				centerPosition[1] - (symbol.getHeight() >> 1)));
	}

	@Override
	public void renderPointOfInterest(byte layer, int latitude, int longitude, List<Tag> tags) {
		this.drawingLayer = this.ways.get(getValidLayer(layer));
		this.poiX = scaleLongitude(longitude);
		this.poiY = scaleLatitude(latitude);
		this.renderTheme.matchNode(this, tags, this.currentTile.zoomLevel);
	}

	@Override
	public void renderPointOfInterestCaption(String caption, float verticalOffset, Paint paint, Paint stroke) {
		this.nodes.add(new PointTextContainer(caption, this.poiX, this.poiY + verticalOffset, paint, stroke));
	}

	@Override
	public void renderPointOfInterestCircle(float radius, Paint outline, int level) {
		this.drawingLayer.get(level).add(
				new ShapePaintContainer(new CircleContainer(this.poiX, this.poiY, radius), outline));
	}

	@Override
	public void renderPointOfInterestSymbol(Bitmap symbol) {
		this.pointSymbols.add(new SymbolContainer(symbol, this.poiX - (symbol.getWidth() >> 1), this.poiY
				- (symbol.getHeight() >> 1)));
	}

	@Override
	public void renderWaterBackground() {
		this.tagList.clear();
		this.tagList.add(TAG_NATURAL_WATER);
		this.coordinates = WATER_TILE_COORDINATES;
		this.renderTheme.matchClosedWay(this, this.tagList, this.currentTile.zoomLevel);
	}

	@Override
	public void renderWay(byte layer, float[] labelPosition, List<Tag> tags, float[][] wayNodes) {
		this.drawingLayer = this.ways.get(getValidLayer(layer));
		// TODO what about the label position?

		this.coordinates = wayNodes;
		for (int i = 0; i < this.coordinates.length; ++i) {
			for (int j = 0; j < this.coordinates[i].length; j += 2) {
				this.coordinates[i][j] = scaleLongitude(this.coordinates[i][j]);
				this.coordinates[i][j + 1] = scaleLatitude(this.coordinates[i][j + 1]);
			}
		}
		this.shapeContainer = new WayContainer(this.coordinates);

		if (GeometryUtils.isClosedWay(this.coordinates[0])) {
			this.renderTheme.matchClosedWay(this, tags, this.currentTile.zoomLevel);
		} else {
			this.renderTheme.matchLinearWay(this, tags, this.currentTile.zoomLevel);
		}
	}

	@Override
	public void renderWay(Paint paint, int level) {
		this.drawingLayer.get(level).add(new ShapePaintContainer(this.shapeContainer, paint));
	}

	@Override
	public void renderWaySymbol(Bitmap symbolBitmap, boolean alignCenter, boolean repeatSymbol) {
		WayDecorator.renderSymbol(symbolBitmap, alignCenter, repeatSymbol, this.coordinates, this.waySymbols);
	}

	@Override
	public void renderWayText(String textKey, Paint paint, Paint outline) {
		WayDecorator.renderText(textKey, paint, outline, this.coordinates, this.wayNames);
	}

	@Override
	public boolean requiresInternetConnection() {
		return false;
	}

	/**
	 * @param mapDatabase
	 *            the MapDatabase from which the map data will be read.
	 */
	public void setMapDatabase(MapDatabase mapDatabase) {
		this.mapDatabase = mapDatabase;
	}

	private void clearLists() {
		for (int i = this.ways.size() - 1; i >= 0; --i) {
			List<List<ShapePaintContainer>> innerWayList = this.ways.get(i);
			for (int j = innerWayList.size() - 1; j >= 0; --j) {
				innerWayList.get(j).clear();
			}
		}

		this.areaLabels.clear();
		this.nodes.clear();
		this.pointSymbols.clear();
		this.wayNames.clear();
		this.waySymbols.clear();
	}

	private void createWayLists() {
		int levels = this.renderTheme.getLevels();
		this.ways.clear();

		for (byte i = LAYERS - 1; i >= 0; --i) {
			List<List<ShapePaintContainer>> innerWayList = new ArrayList<List<ShapePaintContainer>>(levels);
			for (int j = levels - 1; j >= 0; --j) {
				innerWayList.add(new ArrayList<ShapePaintContainer>(0));
			}
			this.ways.add(innerWayList);
		}
	}

	/**
	 * Converts a latitude value into an Y coordinate on the current tile.
	 * 
	 * @param latitude
	 *            the latitude value.
	 * @return the Y coordinate on the current tile.
	 */
	private float scaleLatitude(float latitude) {
		return (float) (MercatorProjection.latitudeToPixelY(latitude / (double) 1000000, this.currentTile.zoomLevel) - this.currentTile
				.getPixelY());
	}

	/**
	 * Converts a longitude value into an X coordinate on the current tile.
	 * 
	 * @param longitude
	 *            the longitude value.
	 * @return the X coordinate on the current tile.
	 */
	private float scaleLongitude(float longitude) {
		return (float) (MercatorProjection.longitudeToPixelX(longitude / (double) 1000000, this.currentTile.zoomLevel) - this.currentTile
				.getPixelX());
	}

	/**
	 * Sets the scale stroke factor for the given zoom level.
	 * 
	 * @param zoomLevel
	 *            the zoom level for which the scale stroke factor should be set.
	 */
	private void setScaleStrokeWidth(byte zoomLevel) {
		int zoomLevelDiff = Math.max(zoomLevel - STROKE_MIN_ZOOM_LEVEL, 0);
		this.renderTheme.scaleStrokeWidth((float) Math.pow(STROKE_INCREASE, zoomLevelDiff));
	}
}
