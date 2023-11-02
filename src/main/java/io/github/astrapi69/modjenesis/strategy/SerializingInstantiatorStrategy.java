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

import java.io.NotSerializableException;
import java.io.Serializable;

import io.github.astrapi69.modjenesis.ObjenesisException;
import io.github.astrapi69.modjenesis.instantiator.ObjectInstantiator;
import io.github.astrapi69.modjenesis.instantiator.android.AndroidSerializationInstantiator;
import io.github.astrapi69.modjenesis.instantiator.basic.ObjectStreamClassInstantiator;
import io.github.astrapi69.modjenesis.instantiator.gcj.GCJSerializationInstantiator;
import io.github.astrapi69.modjenesis.instantiator.perc.PercSerializationInstantiator;
import io.github.astrapi69.modjenesis.instantiator.sun.SunReflectionFactorySerializationInstantiator;

/**
 * Guess the best serializing instantiator for a given class. The returned instantiator will
 * instantiate classes like the genuine java serialization framework (the constructor of the first
 * not serializable class will be called). Currently, the selection doesn't depend on the class. It
 * relies on the
 * <ul>
 * <li>JVM version</li>
 * <li>JVM vendor</li>
 * <li>JVM vendor version</li>
 * </ul>
 * However, instantiators are stateful and so dedicated to their class.
 *
 * @author Henri Tremblay
 * @see ObjectInstantiator
 */
public class SerializingInstantiatorStrategy extends BaseInstantiatorStrategy
{

	/**
	 * Return an {@link ObjectInstantiator} allowing to create instance following the java
	 * serialization framework specifications.
	 *
	 * @param type
	 *            Class to instantiate
	 * @return The ObjectInstantiator for the class
	 */
	public <T> ObjectInstantiator<T> newInstantiatorOf(Class<T> type, Object... initArgs)
	{
		if (!Serializable.class.isAssignableFrom(type))
		{
			throw new ObjenesisException(new NotSerializableException(type + " not serializable"));
		}
		if (PlatformDescription.JVM_NAME.startsWith(PlatformDescription.HOTSPOT)
			|| PlatformDescription.isThisJVM(PlatformDescription.OPENJDK))
		{
			return new SunReflectionFactorySerializationInstantiator<>(type);
		}
		else if (PlatformDescription.JVM_NAME.startsWith(PlatformDescription.DALVIK))
		{
			if (PlatformDescription.isAndroidOpenJDK())
			{
				return new ObjectStreamClassInstantiator<>(type);
			}
			return new AndroidSerializationInstantiator<>(type);
		}
		else if (PlatformDescription.JVM_NAME.startsWith(PlatformDescription.GNU))
		{
			return new GCJSerializationInstantiator<>(type);
		}
		else if (PlatformDescription.JVM_NAME.startsWith(PlatformDescription.PERC))
		{
			return new PercSerializationInstantiator<>(type);
		}

		return new SunReflectionFactorySerializationInstantiator<>(type);
	}

}
