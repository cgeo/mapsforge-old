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
package org.mapsforge.android.maps.rendertheme;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 * Tests the {@link RenderThemeHandler} class.
 */
public class RenderThemeHandlerTest {
	/**
	 * Tests the {@link RenderThemeHandler#getRenderTheme} method.
	 * 
	 * @throws SAXException
	 *             see {@link RenderThemeHandler#getRenderTheme}
	 * @throws ParserConfigurationException
	 *             see {@link RenderThemeHandler#getRenderTheme}
	 * @throws IOException
	 *             see {@link RenderThemeHandler#getRenderTheme}
	 */
	@Ignore
	@Test
	public void getRenderThemeTest() throws SAXException, ParserConfigurationException, IOException {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("src/test/resources/test_render_theme.xml");
			RenderTheme renderTheme = RenderThemeHandler.getRenderTheme(inputStream);
			Assert.assertEquals(1, renderTheme.getMapBackground());
			// TODO extend test
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}
}
