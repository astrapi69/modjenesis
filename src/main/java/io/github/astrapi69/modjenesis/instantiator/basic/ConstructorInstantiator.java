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
package io.github.astrapi69.modjenesis.instantiator.basic;

import java.lang.reflect.Constructor;

import io.github.astrapi69.modjenesis.ObjenesisException;
import io.github.astrapi69.modjenesis.instantiator.ObjectInstantiator;
import io.github.astrapi69.modjenesis.instantiator.annotations.Instantiator;
import io.github.astrapi69.modjenesis.instantiator.annotations.Typology;
import io.github.astrapi69.modjenesis.instantiator.util.ClassUtils;

/**
 * Instantiates a class by grabbing the no args constructor and calling Constructor.newInstance().
 * This can deal with default public constructors, but that's about it.
 *
 * @author Joe Walnes
 * @param <T>
 *            Type instantiated
 * @see ObjectInstantiator
 */
@Instantiator(Typology.NOT_COMPLIANT)
public class ConstructorInstantiator<T> implements ObjectInstantiator<T>
{

	protected Constructor<T> constructor;

	private final Class<T> type;
	private Object[] initArgs;

	public ConstructorInstantiator(Class<T> type, Object... initArgs)
	{
		try
		{
			this.type = type;
			this.initArgs = initArgs;
			if (initArgs != null && 0 < initArgs.length)
			{
				constructor = type.getDeclaredConstructor(ClassUtils.getParameterTypes(initArgs));
			}
			else
			{
				constructor = type.getDeclaredConstructor((Class<?>[])null);
			}
		}
		catch (Exception e)
		{
			throw new ObjenesisException(e);
		}
	}

	public T newInstance(Object... initArgs)
	{
		try
		{
			if (initArgs != null && 0 < initArgs.length)
			{
				if (this.initArgs == null || !this.initArgs.equals(initArgs))
				{
					constructor = this.type
						.getDeclaredConstructor(ClassUtils.getParameterTypes(initArgs));
				}
			}
			return constructor.newInstance(initArgs);
		}
		catch (Exception e)
		{
			throw new ObjenesisException(e);
		}
	}

}
