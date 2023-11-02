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
package io.github.astrapi69.modjenesis.instantiator.gcj;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.reflect.Method;

import io.github.astrapi69.modjenesis.ObjenesisException;
import io.github.astrapi69.modjenesis.instantiator.ObjectInstantiator;

/**
 * Base class for GCJ-based instantiators. It initializes reflection access to method
 * ObjectInputStream.newObject, as well as creating a dummy ObjectInputStream to be used as the
 * "this" argument for the method.
 * 
 * @author Leonardo Mesquita
 */
public abstract class GCJInstantiatorBase<T> implements ObjectInstantiator<T>
{
	static Method newObjectMethod = null;
	static ObjectInputStream dummyStream;

	private static class DummyStream extends ObjectInputStream
	{
		public DummyStream() throws IOException
		{
		}
	}

	private static void initialize()
	{
		if (newObjectMethod == null)
		{
			try
			{
				newObjectMethod = ObjectInputStream.class.getDeclaredMethod("newObject",
					Class.class, Class.class);
				newObjectMethod.setAccessible(true);
				dummyStream = new DummyStream();
			}
			catch (RuntimeException | NoSuchMethodException | IOException e)
			{
				throw new ObjenesisException(e);
			}
		}
	}

	protected final Class<T> type;

	public GCJInstantiatorBase(Class<T> type)
	{
		this.type = type;
		initialize();
	}

	public abstract T newInstance();
}
