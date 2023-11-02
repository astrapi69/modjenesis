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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.github.astrapi69.modjenesis.ObjenesisException;
import io.github.astrapi69.modjenesis.instantiator.ObjectInstantiator;


/**
 * Helper methods providing access to {@link sun.reflect.ReflectionFactory} via reflection, for use
 * by the {@link ObjectInstantiator}s that use it.
 * 
 * @author Henri Tremblay
 */
@SuppressWarnings("restriction")
class SunReflectionFactoryHelper
{

	@SuppressWarnings("unchecked")
	public static <T> Constructor<T> newConstructorForSerialization(Class<T> type,
		Constructor<?> constructor)
	{
		Class<?> reflectionFactoryClass = getReflectionFactoryClass();
		Object reflectionFactory = createReflectionFactory(reflectionFactoryClass);

		Method newConstructorForSerializationMethod = getNewConstructorForSerializationMethod(
			reflectionFactoryClass);

		try
		{
			return (Constructor<T>)newConstructorForSerializationMethod.invoke(reflectionFactory,
				type, constructor);
		}
		catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e)
		{
			throw new ObjenesisException(e);
		}
	}

	private static Class<?> getReflectionFactoryClass()
	{
		try
		{
			return Class.forName("sun.reflect.ReflectionFactory");
		}
		catch (ClassNotFoundException e)
		{
			throw new ObjenesisException(e);
		}
	}

	private static Object createReflectionFactory(Class<?> reflectionFactoryClass)
	{
		try
		{
			Method method = reflectionFactoryClass.getDeclaredMethod("getReflectionFactory");
			return method.invoke(null);
		}
		catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException
			| IllegalArgumentException e)
		{
			throw new ObjenesisException(e);
		}
	}

	private static Method getNewConstructorForSerializationMethod(Class<?> reflectionFactoryClass)
	{
		try
		{
			return reflectionFactoryClass.getDeclaredMethod("newConstructorForSerialization",
				Class.class, Constructor.class);
		}
		catch (NoSuchMethodException e)
		{
			throw new ObjenesisException(e);
		}
	}
}
