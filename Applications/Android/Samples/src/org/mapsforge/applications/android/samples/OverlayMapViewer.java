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
package org.mapsforge.applications.android.samples;

import java.io.File;

import org.mapsforge.android.maps.MapActivity;
import org.mapsforge.android.maps.MapView;
import org.mapsforge.android.maps.overlay.ArrayCircleOverlay;
import org.mapsforge.android.maps.overlay.ArrayItemizedOverlay;
import org.mapsforge.android.maps.overlay.ArrayWayOverlay;
import org.mapsforge.android.maps.overlay.ItemizedOverlay;
import org.mapsforge.android.maps.overlay.OverlayCircle;
import org.mapsforge.android.maps.overlay.OverlayItem;
import org.mapsforge.android.maps.overlay.OverlayWay;
import org.mapsforge.core.GeoPoint;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

/**
 * An application which demonstrates how to use different types of overlays.
 */
public class OverlayMapViewer extends MapActivity {
	private static class MyItemizedOverlay extends ArrayItemizedOverlay {
		private final Context context;

		/**
		 * Constructs a new MyItemizedOverlay.
		 * 
		 * @param defaultMarker
		 *            the default marker (may be null).
		 * @param context
		 *            the reference to the application context.
		 */
		MyItemizedOverlay(Drawable defaultMarker, Context context) {
			super(defaultMarker);
			this.context = context;
		}

		/**
		 * Handles a tap event on the given item.
		 */
		@Override
		protected boolean onTap(int index) {
			OverlayItem item = createItem(index);
			if (item != null) {
				Builder builder = new AlertDialog.Builder(this.context);
				builder.setIcon(android.R.drawable.ic_menu_info_details);
				builder.setTitle(item.getTitle());
				builder.setMessage(item.getSnippet());
				builder.setPositiveButton("OK", null);
				builder.show();
			}
			return true;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MapView mapView = new MapView(this);
		mapView.setClickable(true);
		mapView.setBuiltInZoomControls(true);
		mapView.setMapFile(new File("/sdcard/berlin.map"));
		setContentView(mapView);

		// create some points to be used in the different overlays
		GeoPoint geoPoint1 = new GeoPoint(52.514446, 13.350150); // Berlin Victory Column
		GeoPoint geoPoint2 = new GeoPoint(52.516272, 13.377722); // Brandenburg Gate
		GeoPoint geoPoint3 = new GeoPoint(52.525, 13.369444); // Berlin Central Station
		GeoPoint geoPoint4 = new GeoPoint(52.52, 13.369444); // German Chancellery

		// create the default paint objects for overlay circles
		Paint circleDefaultPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
		circleDefaultPaintFill.setStyle(Paint.Style.FILL);
		circleDefaultPaintFill.setColor(Color.BLUE);
		circleDefaultPaintFill.setAlpha(64);

		Paint circleDefaultPaintOutline = new Paint(Paint.ANTI_ALIAS_FLAG);
		circleDefaultPaintOutline.setStyle(Paint.Style.STROKE);
		circleDefaultPaintOutline.setColor(Color.BLUE);
		circleDefaultPaintOutline.setAlpha(128);
		circleDefaultPaintOutline.setStrokeWidth(3);

		// create an individual paint object for an overlay circle
		Paint circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		circlePaint.setStyle(Paint.Style.FILL);
		circlePaint.setColor(Color.MAGENTA);
		circlePaint.setAlpha(96);

		// create the CircleOverlay and add the circles
		ArrayCircleOverlay circleOverlay = new ArrayCircleOverlay(circleDefaultPaintFill, circleDefaultPaintOutline);
		OverlayCircle circle1 = new OverlayCircle(geoPoint3, 200, null);
		OverlayCircle circle2 = new OverlayCircle(geoPoint4, 150, circlePaint, null, null);
		circleOverlay.addCircle(circle1);
		circleOverlay.addCircle(circle2);

		// create the default paint objects for overlay ways
		Paint wayDefaultPaintFill = new Paint(Paint.ANTI_ALIAS_FLAG);
		wayDefaultPaintFill.setStyle(Paint.Style.STROKE);
		wayDefaultPaintFill.setColor(Color.BLUE);
		wayDefaultPaintFill.setAlpha(160);
		wayDefaultPaintFill.setStrokeWidth(7);
		wayDefaultPaintFill.setStrokeJoin(Paint.Join.ROUND);
		wayDefaultPaintFill.setPathEffect(new DashPathEffect(new float[] { 20, 20 }, 0));

		Paint wayDefaultPaintOutline = new Paint(Paint.ANTI_ALIAS_FLAG);
		wayDefaultPaintOutline.setStyle(Paint.Style.STROKE);
		wayDefaultPaintOutline.setColor(Color.BLUE);
		wayDefaultPaintOutline.setAlpha(128);
		wayDefaultPaintOutline.setStrokeWidth(7);
		wayDefaultPaintOutline.setStrokeJoin(Paint.Join.ROUND);

		// create an individual paint object for an overlay way
		Paint wayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		wayPaint.setStyle(Paint.Style.FILL);
		wayPaint.setColor(Color.YELLOW);
		wayPaint.setAlpha(192);

		// create the WayOverlay and add the ways
		ArrayWayOverlay wayOverlay = new ArrayWayOverlay(wayDefaultPaintFill, wayDefaultPaintOutline);
		OverlayWay way1 = new OverlayWay(new GeoPoint[][] { { geoPoint1, geoPoint2 } });
		OverlayWay way2 = new OverlayWay(new GeoPoint[][] { { geoPoint1, geoPoint3, geoPoint4, geoPoint1 } }, wayPaint,
				null);
		wayOverlay.addWay(way1);
		wayOverlay.addWay(way2);

		// create the default marker for overlay items
		Drawable itemDefaultMarker = getResources().getDrawable(R.drawable.marker_red);

		// create an individual marker for an overlay item
		Drawable itemMarker = getResources().getDrawable(R.drawable.marker_green);

		// create the ItemizedOverlay and add the items
		ArrayItemizedOverlay itemizedOverlay = new MyItemizedOverlay(itemDefaultMarker, this);
		OverlayItem item1 = new OverlayItem(geoPoint1, "Berlin Victory Column",
				"The Victory Column is a monument in Berlin, Germany.");
		OverlayItem item2 = new OverlayItem(geoPoint2, "Brandenburg Gate",
				"The Brandenburg Gate is one of the main symbols of Berlin and Germany.",
				ItemizedOverlay.boundCenterBottom(itemMarker));
		itemizedOverlay.addItem(item1);
		itemizedOverlay.addItem(item2);

		// add all overlays to the MapView
		mapView.getOverlays().add(wayOverlay);
		mapView.getOverlays().add(circleOverlay);
		mapView.getOverlays().add(itemizedOverlay);
	}
}
