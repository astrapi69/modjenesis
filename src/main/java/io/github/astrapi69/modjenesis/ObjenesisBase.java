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

import java.util.concurrent.ConcurrentHashMap;

import io.github.astrapi69.modjenesis.instantiator.ObjectInstantiator;
import io.github.astrapi69.modjenesis.strategy.InstantiatorStrategy;

/**
 * Base class to extend if you want to have a class providing your own default strategy. Can also be
 * instantiated directly.
 *
 * @author Henri Tremblay
 */
public class ObjenesisBase implements Objenesis
{

	/** Strategy used by this Objenesis implementation to create classes */
	protected final InstantiatorStrategy strategy;

	/** Strategy cache. Key = Class, Value = InstantiatorStrategy */
	protected ConcurrentHashMap<String, ObjectInstantiator<?>> cache;

	/**
	 * Constructor allowing to pick a strategy and using cache
	 *
	 * @param strategy
	 *            Strategy to use
	 */
	public ObjenesisBase(InstantiatorStrategy strategy)
	{
		this(strategy, true);
	}

	/**
	 * Flexible constructor allowing to pick the strategy and if caching should be used
	 *
	 * @param strategy
	 *            Strategy to use
	 * @param useCache
	 *            If {@link ObjectInstantiator}s should be cached
	 */
	public ObjenesisBase(InstantiatorStrategy strategy, boolean useCache)
	{
		if (strategy == null)
		{
			throw new IllegalArgumentException("A strategy can't be null");
		}
		this.strategy = strategy;
		this.cache = useCache ? new ConcurrentHashMap<>() : null;
	}

	@Override
	public String toString()
	{
		return getClass().getName() + " using " + strategy.getClass().getName()
			+ (cache == null ? " without" : " with") + " caching";
	}

	/**
	 * Will create a new object without any constructor being called
	 *
	 * @param clazz
	 *            Class to instantiate
	 * @return New instance of clazz
	 */
	public <T> T newInstance(Class<T> clazz, Object... initArgs)
	{
		return getInstantiatorOf(clazz).newInstance();
	}

	/**
	 * Will pick the best instantiator for the provided class. If you need to create a lot of
	 * instances from the same class, it is way more efficient to create them from the same
	 * ObjectInstantiator than calling {@link #newInstance(Class)}.
	 *
	 * @param clazz
	 *            Class to instantiate
	 * @return Instantiator dedicated to the class
	 */
	@SuppressWarnings("unchecked")
	public <T> ObjectInstantiator<T> getInstantiatorOf(Class<T> clazz, Object... initArgs)
	{
		if (clazz.isPrimitive())
		{
			throw new IllegalArgumentException("Primitive types can't be instantiated in Java");
		}
		if (cache == null)
		{
			return strategy.newInstantiatorOf(clazz);
		}
		ObjectInstantiator<?> instantiator = cache.get(clazz.getName());
		if (instantiator == null)
		{
			ObjectInstantiator<?> newInstantiator = strategy.newInstantiatorOf(clazz);
			instantiator = cache.putIfAbsent(clazz.getName(), newInstantiator);
			if (instantiator == null)
			{
				instantiator = newInstantiator;
			}
		}
		return (ObjectInstantiator<T>)instantiator;
	}
}
