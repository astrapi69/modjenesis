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

import java.io.NotSerializableException;
import java.lang.reflect.Constructor;

import io.github.astrapi69.modjenesis.ObjenesisException;
import io.github.astrapi69.modjenesis.instantiator.ObjectInstantiator;
import io.github.astrapi69.modjenesis.instantiator.SerializationInstantiatorHelper;
import io.github.astrapi69.modjenesis.instantiator.annotations.Instantiator;
import io.github.astrapi69.modjenesis.instantiator.annotations.Typology;

/**
 * Instantiates an object using internal sun.reflect.ReflectionFactory - a class only available on
 * JDK's that use Sun's 1.4 (or later) Java implementation. This instantiator will create classes in
 * a way compatible with serialization, calling the first non-serializable superclass' no-arg
 * constructor. This is the best way to instantiate an object without any side effects caused by the
 * constructor - however it is not available on every platform.
 *
 * @author Leonardo Mesquita
 * @see ObjectInstantiator
 */
@Instantiator(Typology.SERIALIZATION)
public class SunReflectionFactorySerializationInstantiator<T> implements ObjectInstantiator<T>
{

	private final Constructor<T> mungedConstructor;

	public SunReflectionFactorySerializationInstantiator(Class<T> type)
	{
		Class<? super T> nonSerializableAncestor = SerializationInstantiatorHelper
			.getNonSerializableSuperClass(type);

		Constructor<? super T> nonSerializableAncestorConstructor;
		try
		{
			nonSerializableAncestorConstructor = nonSerializableAncestor
				.getDeclaredConstructor((Class<?>[])null);
		}
		catch (NoSuchMethodException e)
		{
			throw new ObjenesisException(
				new NotSerializableException(type + " has no suitable superclass constructor"));
		}

		mungedConstructor = SunReflectionFactoryHelper.newConstructorForSerialization(type,
			nonSerializableAncestorConstructor);
		mungedConstructor.setAccessible(true);
	}

	public T newInstance(Object... initArgs)
	{
		try
		{
			return mungedConstructor.newInstance((Object[])null);
		}
		catch (Exception e)
		{
			throw new ObjenesisException(e);
		}
	}
}
