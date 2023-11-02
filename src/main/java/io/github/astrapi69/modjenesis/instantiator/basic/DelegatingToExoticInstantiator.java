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
/*
 * Copyright 2006-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.astrapi69.modjenesis.instantiator.basic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import io.github.astrapi69.modjenesis.ObjenesisException;
import io.github.astrapi69.modjenesis.instantiator.ObjectInstantiator;

/**
 * Helper class extended by instantiators for which the implementation was moved to the exotic
 * project.
 *
 * @param <T>
 *            type of the class instantiated
 * @author Henri Tremblay
 */
public abstract class DelegatingToExoticInstantiator<T> implements ObjectInstantiator<T>
{

	private final ObjectInstantiator<T> wrapped;

	protected DelegatingToExoticInstantiator(String className, Class<T> type)
	{
		Class<ObjectInstantiator<T>> clazz = instantiatorClass(className);
		Constructor<ObjectInstantiator<T>> constructor = instantiatorConstructor(className, clazz);
		wrapped = instantiator(className, type, constructor);
	}

	private ObjectInstantiator<T> instantiator(String className, Class<T> type,
		Constructor<ObjectInstantiator<T>> constructor)
	{
		try
		{
			return constructor.newInstance(type);
		}
		catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
		{
			throw new RuntimeException("Failed to call constructor of " + className, e);
		}
	}

	private Class<ObjectInstantiator<T>> instantiatorClass(String className)
	{
		try
		{
			@SuppressWarnings("unchecked")
			Class<ObjectInstantiator<T>> clazz = (Class<ObjectInstantiator<T>>)Class
				.forName(className);
			return clazz;
		}
		catch (ClassNotFoundException e)
		{
			throw new ObjenesisException(getClass().getSimpleName()
				+ " now requires objenesis-exotic to be in the classpath", e);
		}
	}

	private Constructor<ObjectInstantiator<T>> instantiatorConstructor(String className,
		Class<ObjectInstantiator<T>> clazz)
	{
		try
		{
			return clazz.getConstructor(Class.class);
		}
		catch (NoSuchMethodException e)
		{
			throw new ObjenesisException(
				"Try to find constructor taking a Class<T> in parameter on " + className
					+ " but can't find it",
				e);
		}
	}

	@Override
	public T newInstance(Object... initArgs)
	{
		return wrapped.newInstance(initArgs);
	}
}
