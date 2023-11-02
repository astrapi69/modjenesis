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
package io.github.astrapi69.modjenesis.instantiator;

import java.io.Serializable;

/**
 * Helper for common serialization-compatible instantiation functions
 * 
 * @author Leonardo Mesquita
 */
public class SerializationInstantiatorHelper
{

	/**
	 * Returns the first non-serializable superclass of a given class. According to Java Object
	 * Serialization Specification, objects read from a stream are initialized by calling an
	 * accessible no-arg constructor from the first non-serializable superclass in the object's
	 * hierarchy, allowing the state of non-serializable fields to be correctly initialized.
	 *
	 * @param <T>
	 *            Type to instantiate
	 * @param type
	 *            Serializable class for which the first non-serializable superclass is to be found
	 * @return The first non-serializable superclass of 'type'.
	 * @see java.io.Serializable
	 */
	public static <T> Class<? super T> getNonSerializableSuperClass(Class<T> type)
	{
		Class<? super T> result = type;
		while (Serializable.class.isAssignableFrom(result))
		{
			result = result.getSuperclass();
			if (result == null)
			{
				throw new Error("Bad class hierarchy: No non-serializable parents");
			}
		}
		return result;

	}
}
