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
package io.github.astrapi69.modjenesis.instantiator.util;

import io.github.astrapi69.modjenesis.ObjenesisException;

/**
 * Helper class for to play with classes.
 *
 * @author Henri Tremblay
 */
public final class ClassUtils
{

	private ClassUtils()
	{
	}

	/**
	 * Will convert a class name to its name in the class definition format (e.g
	 * {@code org.objenesis.EmptyClass} becomes {@code org/objenesis/EmptyClass})
	 *
	 * @param className
	 *            full class name including the package
	 * @return the internal name
	 */
	public static String classNameToInternalClassName(String className)
	{
		return className.replace('.', '/');
	}

	/**
	 * Will convert a class name to its class loader resource name (e.g
	 * {@code org.objenesis.EmptyClass} becomes {@code org/objenesis/EmptyClass.class})
	 *
	 * @param className
	 *            full class name including the package
	 * @return the resource name
	 */
	public static String classNameToResource(String className)
	{
		return classNameToInternalClassName(className) + ".class";
	}

	/**
	 * Check if this class already exists in the class loader and return it if it does
	 *
	 * @param <T>
	 *            type of the class returned
	 * @param classLoader
	 *            Class loader where to search the class
	 * @param className
	 *            Class name with full path
	 * @return the class if it already exists or null
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getExistingClass(ClassLoader classLoader, String className)
	{
		try
		{
			return (Class<T>)Class.forName(className, true, classLoader);
		}
		catch (ClassNotFoundException e)
		{
			return null;
		}
	}

	@SuppressWarnings("deprecation")
	public static <T> T newInstance(Class<T> clazz)
	{
		try
		{
			return clazz.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new ObjenesisException(e);
		}
	}
}
