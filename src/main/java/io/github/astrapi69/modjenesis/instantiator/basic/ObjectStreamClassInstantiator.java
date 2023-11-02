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

import java.io.ObjectStreamClass;
import java.lang.reflect.Method;

import io.github.astrapi69.modjenesis.ObjenesisException;
import io.github.astrapi69.modjenesis.instantiator.ObjectInstantiator;
import io.github.astrapi69.modjenesis.instantiator.annotations.Instantiator;
import io.github.astrapi69.modjenesis.instantiator.annotations.Typology;

/**
 * Instantiates a class by using reflection to make a call to private method
 * ObjectStreamClass.newInstance, present in many JVM implementations. This instantiator will create
 * classes in a way compatible with serialization, calling the first non-serializable superclass'
 * no-arg constructor.
 *
 * @author Leonardo Mesquita
 * @see ObjectInstantiator
 * @see java.io.Serializable
 */
@Instantiator(Typology.SERIALIZATION)
public class ObjectStreamClassInstantiator<T> implements ObjectInstantiator<T>
{

	private static Method newInstanceMethod;

	private static void initialize()
	{
		if (newInstanceMethod == null)
		{
			try
			{
				newInstanceMethod = ObjectStreamClass.class.getDeclaredMethod("newInstance");
				newInstanceMethod.setAccessible(true);
			}
			catch (RuntimeException | NoSuchMethodException e)
			{
				throw new ObjenesisException(e);
			}
		}
	}

	private final ObjectStreamClass objStreamClass;

	public ObjectStreamClassInstantiator(Class<T> type)
	{
		initialize();
		objStreamClass = ObjectStreamClass.lookup(type);
	}

	@SuppressWarnings("unchecked")
	public T newInstance()
	{

		try
		{
			return (T)newInstanceMethod.invoke(objStreamClass);
		}
		catch (Exception e)
		{
			throw new ObjenesisException(e);
		}

	}

}
