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
package io.github.astrapi69.modjenesis.instantiator.util;

import java.lang.reflect.Field;

import io.github.astrapi69.modjenesis.ObjenesisException;
import sun.misc.Unsafe;

/**
 * Helper class basically allowing to get access to {@code sun.misc.Unsafe}
 *
 * @author Henri Tremblay
 */
public final class UnsafeUtils
{

	private static final Unsafe unsafe;

	static
	{
		Field f;
		try
		{
			f = Unsafe.class.getDeclaredField("theUnsafe");
		}
		catch (NoSuchFieldException e)
		{
			throw new ObjenesisException(e);
		}
		f.setAccessible(true);
		try
		{
			unsafe = (Unsafe)f.get(null);
		}
		catch (IllegalAccessException e)
		{
			throw new ObjenesisException(e);
		}
	}

	private UnsafeUtils()
	{
	}

	public static Unsafe getUnsafe()
	{
		return unsafe;
	}
}
