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
package io.github.astrapi69.modjenesis.strategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import io.github.astrapi69.modjenesis.ObjenesisException;
import io.github.astrapi69.modjenesis.instantiator.ObjectInstantiator;

/**
 * Strategy returning only one instantiator type. Useful if you know on which JVM Objenesis will be
 * used and want to specify it explicitly.
 *
 * @author Henri Tremblay
 */
public class SingleInstantiatorStrategy implements InstantiatorStrategy
{

	private final Constructor<?> constructor;

	/**
	 * Create a strategy that will return always the same instantiator type. We assume this
	 * instantiator has one constructor taking the class to instantiate in parameter.
	 *
	 * @param <T>
	 *            the type we want to instantiate
	 * @param instantiator
	 *            the instantiator type
	 */
	public <T extends ObjectInstantiator<?>> SingleInstantiatorStrategy(Class<T> instantiator)
	{
		try
		{
			constructor = instantiator.getConstructor(Class.class);
		}
		catch (NoSuchMethodException e)
		{
			throw new ObjenesisException(e);
		}
	}

	/**
	 * Return an instantiator for the wanted type and of the one and only type of instantiator
	 * returned by this class.
	 *
	 * @param <T>
	 *            the type we want to instantiate
	 * @param type
	 *            Class to instantiate
	 * @param initArgs
	 *            an optional array of objects to be passed as arguments to the constructor call
	 * @return The ObjectInstantiator for the class
	 */
	@SuppressWarnings("unchecked")
	public <T> ObjectInstantiator<T> newInstantiatorOf(Class<T> type, Object... initArgs)
	{
		try
		{
			return (ObjectInstantiator<T>)constructor.newInstance(type);
		}
		catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
		{
			throw new ObjenesisException(e);
		}
	}
}
