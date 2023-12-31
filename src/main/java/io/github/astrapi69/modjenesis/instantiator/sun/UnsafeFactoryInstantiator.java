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
package io.github.astrapi69.modjenesis.instantiator.sun;

import io.github.astrapi69.modjenesis.ObjenesisException;
import io.github.astrapi69.modjenesis.instantiator.ObjectInstantiator;
import io.github.astrapi69.modjenesis.instantiator.annotations.Instantiator;
import io.github.astrapi69.modjenesis.instantiator.annotations.Typology;
import io.github.astrapi69.modjenesis.instantiator.util.UnsafeUtils;
import sun.misc.Unsafe;

/**
 * Instantiates an object, WITHOUT calling it's constructor, using
 * {@code sun.misc.Unsafe.allocateInstance()}. Unsafe and its methods are implemented by most modern
 * JVMs.
 *
 * @author Henri Tremblay
 * @see ObjectInstantiator
 */
@SuppressWarnings("restriction")
@Instantiator(Typology.STANDARD)
public class UnsafeFactoryInstantiator<T> implements ObjectInstantiator<T>
{

	private final Unsafe unsafe;
	private final Class<T> type;

	public UnsafeFactoryInstantiator(Class<T> type)
	{
		this.unsafe = UnsafeUtils.getUnsafe(); // retrieve it to fail right away at instantiator
												// creation if not there
		this.type = type;
	}

	public T newInstance(Object... initArgs)
	{
		try
		{
			return type.cast(unsafe.allocateInstance(type));
		}
		catch (InstantiationException e)
		{
			throw new ObjenesisException(e);
		}
	}
}
