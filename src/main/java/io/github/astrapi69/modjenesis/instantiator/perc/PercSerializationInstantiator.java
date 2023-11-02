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
package io.github.astrapi69.modjenesis.instantiator.perc;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import io.github.astrapi69.modjenesis.ObjenesisException;
import io.github.astrapi69.modjenesis.instantiator.ObjectInstantiator;
import io.github.astrapi69.modjenesis.instantiator.annotations.Instantiator;
import io.github.astrapi69.modjenesis.instantiator.annotations.Typology;

/**
 * Instantiates a class by making a call to internal Perc private methods. It is only supposed to
 * work on Perc JVMs. This instantiator will create classes in a way compatible with serialization,
 * calling the first non-serializable superclass' no-arg constructor.
 * <p>
 * Based on code provided by Aonix but <b>doesn't work right now</b>
 *
 * @author Henri Tremblay
 * @see ObjectInstantiator
 */
@Instantiator(Typology.SERIALIZATION)
public class PercSerializationInstantiator<T> implements ObjectInstantiator<T>
{

	private final Object[] typeArgs;

	private final Method newInstanceMethod;

	public PercSerializationInstantiator(Class<T> type)
	{

		// Find the first unserializable parent class
		Class<? super T> unserializableType = type;

		while (Serializable.class.isAssignableFrom(unserializableType))
		{
			unserializableType = unserializableType.getSuperclass();
		}

		try
		{
			// Get the special Perc method to call
			Class<?> percMethodClass = Class.forName("COM.newmonics.PercClassLoader.Method");

			newInstanceMethod = ObjectInputStream.class.getDeclaredMethod("noArgConstruct",
				Class.class, Object.class, percMethodClass);
			newInstanceMethod.setAccessible(true);

			// Create invoke params
			Class<?> percClassClass = Class.forName("COM.newmonics.PercClassLoader.PercClass");
			Method getPercClassMethod = percClassClass.getDeclaredMethod("getPercClass",
				Class.class);
			Object someObject = getPercClassMethod.invoke(null, unserializableType);
			Method findMethodMethod = someObject.getClass().getDeclaredMethod("findMethod",
				String.class);
			Object percMethod = findMethodMethod.invoke(someObject, "<init>()V");

			typeArgs = new Object[] { unserializableType, type, percMethod };

		}
		catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
			| InvocationTargetException e)
		{
			throw new ObjenesisException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public T newInstance()
	{
		try
		{
			return (T)newInstanceMethod.invoke(null, typeArgs);
		}
		catch (IllegalAccessException | InvocationTargetException e)
		{
			throw new ObjenesisException(e);
		}
	}

}
