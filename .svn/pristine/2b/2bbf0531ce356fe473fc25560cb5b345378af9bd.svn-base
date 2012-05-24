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
 * Tests the {@link Tag} class.
 */
public class TagTest {
	private static final String KEY = "foo";
	private static final String TAG_TO_STRING = "Tag [key=foo, value=bar]";
	private static final String VALUE = "bar";

	/**
	 * Tests the constructors.
	 */
	@Test
	public void constructorTest() {
		Tag tag1 = new Tag(KEY + '=' + VALUE);
		Tag tag2 = new Tag(KEY, VALUE);

		TestUtils.equalsTest(tag1, tag2);
	}

	/**
	 * Tests the {@link Tag#equals(Object)} and the {@link Tag#hashCode()} method.
	 */
	@Test
	public void equalsTest() {
		Tag tag1 = new Tag(KEY, VALUE);
		Tag tag2 = new Tag(KEY, VALUE);
		Tag tag3 = new Tag(KEY, KEY);

		TestUtils.equalsTest(tag1, tag2);

		Assert.assertFalse(tag1.equals(tag3));
		Assert.assertFalse(tag3.equals(tag1));
		Assert.assertFalse(tag1.equals(new Object()));
	}

	/**
	 * Tests the public fields.
	 */
	@Test
	public void fieldTest() {
		Tag tag = new Tag(KEY, VALUE);

		Assert.assertEquals(KEY, tag.key);
		Assert.assertEquals(VALUE, tag.value);
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
		Tag tag = new Tag(KEY, VALUE);
		TestUtils.serializeTest(tag);
	}

	/**
	 * Tests the {@link Tag#toString()} method.
	 */
	@Test
	public void toStringTest() {
		Tag tag = new Tag(KEY, VALUE);
		Assert.assertEquals(TAG_TO_STRING, tag.toString());
	}
}
