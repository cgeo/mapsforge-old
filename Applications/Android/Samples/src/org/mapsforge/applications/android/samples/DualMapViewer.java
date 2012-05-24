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
import org.mapsforge.android.maps.MapScaleBar;
import org.mapsforge.android.maps.MapView;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * An application which demonstrates how to use two MapView instances at the same time.
 */
public class DualMapViewer extends MapActivity {
	private static final File MAP_FILE = new File("/sdcard/berlin.map");

	private MapView mapView1;
	private MapView mapView2;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			return true;
		}
		// forward the event to both MapViews for simultaneous movement
		return this.mapView1.onKeyDown(keyCode, event) | this.mapView2.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// forward the event to both MapViews for simultaneous movement
		return this.mapView1.onKeyUp(keyCode, event) | this.mapView2.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onTrackballEvent(MotionEvent event) {
		// forward the event to both MapViews for simultaneous movement
		return this.mapView1.onTrackballEvent(event) | this.mapView2.onTrackballEvent(event);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.mapView1 = new MapView(this);
		this.mapView1.setClickable(true);
		this.mapView1.setBuiltInZoomControls(true);
		this.mapView1.setMapFile(MAP_FILE);
		this.mapView1.getMapMover().setMoveSpeedFactor(1);
		MapScaleBar mapScaleBar1 = this.mapView1.getMapScaleBar();
		mapScaleBar1.setImperialUnits(false);
		mapScaleBar1.setShowMapScaleBar(true);

		this.mapView2 = new MapView(this);
		this.mapView2.setClickable(true);
		this.mapView2.setBuiltInZoomControls(true);
		this.mapView2.setMapFile(MAP_FILE);
		this.mapView2.getMapMover().setMoveSpeedFactor(1);
		MapScaleBar mapScaleBar2 = this.mapView2.getMapScaleBar();
		mapScaleBar2.setImperialUnits(true);
		mapScaleBar2.setShowMapScaleBar(true);

		// create a LineaLayout that contains both MapViews
		LinearLayout linearLayout = new LinearLayout(this);

		// if the device orientation is portrait, change the orientation to vertical
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			linearLayout.setOrientation(LinearLayout.VERTICAL);
		}

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, 1);
		this.mapView1.setLayoutParams(layoutParams);
		this.mapView2.setLayoutParams(layoutParams);

		// add both MapViews to the LinearLayout
		linearLayout.addView(this.mapView1);
		linearLayout.addView(this.mapView2);
		setContentView(linearLayout);
	}
}
