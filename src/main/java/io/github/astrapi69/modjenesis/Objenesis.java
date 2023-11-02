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

import io.github.astrapi69.modjenesis.instantiator.ObjectInstantiator;

/**
 * Common interface to all kind of Objenesis objects
 * 
 * @author Henri Tremblay
 */
public interface Objenesis
{

	/**
	 * Will create a new object without any constructor being called
	 *
	 * @param <T>
	 *            Type instantiated
	 * @param clazz
	 *            Class to instantiate
	 * @param initArgs
	 *            an optional array of objects to be passed as arguments to the constructor call
	 * @return New instance of clazz
	 */
	<T> T newInstance(Class<T> clazz, Object... initArgs);

	/**
	 * Will pick the best instantiator for the provided class. If you need to create a lot of
	 * instances from the same class, it is way more efficient to create them from the same
	 * ObjectInstantiator than calling {@link #newInstance(Class, Object...)}.
	 *
	 * @param <T>
	 *            Type to instantiate
	 * @param clazz
	 *            Class to instantiate
	 * @param initArgs
	 *            an optional array of objects to be passed as arguments to the constructor call
	 * @return Instantiator dedicated to the class
	 */
	<T> ObjectInstantiator<T> getInstantiatorOf(Class<T> clazz, Object... initArgs);
}
