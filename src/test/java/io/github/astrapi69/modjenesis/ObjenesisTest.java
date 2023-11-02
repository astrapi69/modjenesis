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
package io.github.astrapi69.modjenesis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import io.github.astrapi69.modjenesis.Objenesis;
import io.github.astrapi69.modjenesis.ObjenesisStd;
import io.github.astrapi69.modjenesis.instantiator.ObjectInstantiator;
import io.github.astrapi69.modjenesis.strategy.InstantiatorStrategy;

/**
 * @author Henri Tremblay
 */
public class ObjenesisTest
{

	@Test
	public final void testObjenesis()
	{
		Objenesis o = new ObjenesisStd();
		assertEquals(
			"io.github.astrapi69.modjenesis.ObjenesisStd using io.github.astrapi69.modjenesis.strategy.StdInstantiatorStrategy with caching",
			o.toString());
	}

	@Test
	public final void testObjenesis_WithoutCache()
	{
		Objenesis o = new ObjenesisStd(false);
		assertEquals(
			"io.github.astrapi69.modjenesis.ObjenesisStd using io.github.astrapi69.modjenesis.strategy.StdInstantiatorStrategy without caching",
			o.toString());

		assertEquals(o.getInstantiatorOf(getClass()).newInstance().getClass(), getClass());
	}

	@Test
	public final void testNewInstance()
	{
		Objenesis o = new ObjenesisStd();
		assertEquals(getClass(), o.newInstance(getClass()).getClass());
	}

	@Test
	public final void testGetInstantiatorOf()
	{
		Objenesis o = new ObjenesisStd();
		ObjectInstantiator<?> i1 = o.getInstantiatorOf(getClass());
		// Test instance creation
		assertEquals(getClass(), i1.newInstance().getClass());

		// Test caching
		ObjectInstantiator<?> i2 = o.getInstantiatorOf(getClass());
		assertSame(i1, i2);
	}

	@Test
	public final void testGetInstantiatorOf_primitive()
	{
		Objenesis o = new ObjenesisStd();
		assertThrows(IllegalArgumentException.class, () -> o.getInstantiatorOf(long.class));
	}

	@Test
	public final void testToString()
	{
		Objenesis o = new ObjenesisStd()
		{
		};
		assertEquals(
			"io.github.astrapi69.modjenesis.ObjenesisTest$1 using io.github.astrapi69.modjenesis.strategy.StdInstantiatorStrategy with caching",
			o.toString());
	}
}

class MyStrategy implements InstantiatorStrategy
{
	public <T> ObjectInstantiator<T> newInstantiatorOf(Class<T> type, Object... initArgs)
	{
		return null;
	}
}
