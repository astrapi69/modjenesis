/**
 * The MIT License
 *
 * Copyright (C) 2022 Asterios Raptis
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package io.github.astrapi69.modjenesis.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;

import io.github.astrapi69.modjenesis.strategy.PlatformDescription;

/**
 * Currently the test just check nothing is crashing. A more complex test should play with class
 * loading and properties.
 *
 * @author Henri Tremblay
 */
public class PlatformDescriptionTest
{

	@Test
	public void isJvmName()
	{
		PlatformDescription.isThisJVM(PlatformDescription.HOTSPOT);
	}

	@Test
	public void test()
	{
		if (!PlatformDescription.isThisJVM(PlatformDescription.DALVIK))
		{
			assertEquals(0, PlatformDescription.ANDROID_VERSION);
		}
	}

	@Test
	public void testAndroidVersion() throws Exception
	{
		Method m = PlatformDescription.class.getDeclaredMethod("getAndroidVersion0");
		m.setAccessible(true);
		int actual = (Integer)m.invoke(null);
		assertEquals(42, actual);
	}

	@Test
	public void isAfterJigsaw()
	{
		PlatformDescription.isAfterJigsaw(); // just make sure it doesn't crash
	}

	@Test
	public void isAfterJava11()
	{
		PlatformDescription.isAfterJava11(); // just make sure it doesn't crash
	}
}
