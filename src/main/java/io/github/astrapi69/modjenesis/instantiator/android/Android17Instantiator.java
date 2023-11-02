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
package io.github.astrapi69.modjenesis.instantiator.android;

import java.io.ObjectStreamClass;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.github.astrapi69.modjenesis.ObjenesisException;
import io.github.astrapi69.modjenesis.instantiator.ObjectInstantiator;
import io.github.astrapi69.modjenesis.instantiator.annotations.Instantiator;
import io.github.astrapi69.modjenesis.instantiator.annotations.Typology;

/**
 * Instantiator for Android API level 11 to 17 which creates objects without driving their
 * constructors, using internal methods on the Dalvik implementation of {@link ObjectStreamClass}.
 *
 * @author Ian Parkinson (Google Inc.)
 */
@Instantiator(Typology.STANDARD)
public class Android17Instantiator<T> implements ObjectInstantiator<T>
{
	private final Class<T> type;
	private final Method newInstanceMethod;
	private final Integer objectConstructorId;

	public Android17Instantiator(Class<T> type)
	{
		this.type = type;
		newInstanceMethod = getNewInstanceMethod();
		objectConstructorId = findConstructorIdForJavaLangObjectConstructor();
	}

	public T newInstance(Object... initArgs)
	{
		try
		{
			return type.cast(newInstanceMethod.invoke(null, type, objectConstructorId));
		}
		catch (Exception e)
		{
			throw new ObjenesisException(e);
		}
	}

	private static Method getNewInstanceMethod()
	{
		try
		{
			Method newInstanceMethod = ObjectStreamClass.class.getDeclaredMethod("newInstance",
				Class.class, Integer.TYPE);
			newInstanceMethod.setAccessible(true);
			return newInstanceMethod;
		}
		catch (RuntimeException | NoSuchMethodException e)
		{
			throw new ObjenesisException(e);
		}
	}

	private static Integer findConstructorIdForJavaLangObjectConstructor()
	{
		try
		{
			Method newInstanceMethod = ObjectStreamClass.class.getDeclaredMethod("getConstructorId",
				Class.class);
			newInstanceMethod.setAccessible(true);

			return (Integer)newInstanceMethod.invoke(null, Object.class);
		}
		catch (RuntimeException | NoSuchMethodException | InvocationTargetException
			| IllegalAccessException e)
		{
			throw new ObjenesisException(e);
		}
	}
}
