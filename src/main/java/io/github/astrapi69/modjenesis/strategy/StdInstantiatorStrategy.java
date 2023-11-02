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

import io.github.astrapi69.modjenesis.instantiator.ObjectInstantiator;
import io.github.astrapi69.modjenesis.instantiator.android.Android10Instantiator;
import io.github.astrapi69.modjenesis.instantiator.android.Android17Instantiator;
import io.github.astrapi69.modjenesis.instantiator.android.Android18Instantiator;
import io.github.astrapi69.modjenesis.instantiator.gcj.GCJInstantiator;
import io.github.astrapi69.modjenesis.instantiator.perc.PercInstantiator;
import io.github.astrapi69.modjenesis.instantiator.sun.SunReflectionFactoryInstantiator;
import io.github.astrapi69.modjenesis.instantiator.sun.UnsafeFactoryInstantiator;

/**
 * Guess the best instantiator for a given class. The instantiator will instantiate the class
 * without calling any constructor. Currently, the selection doesn't depend on the class. It relies
 * on the
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
public class StdInstantiatorStrategy extends BaseInstantiatorStrategy
{

	/**
	 * Return an {@link ObjectInstantiator} allowing to create instance without any constructor
	 * being called.
	 *
	 * @param type
	 *            Class to instantiate
	 * @return The ObjectInstantiator for the class
	 */
	public <T> ObjectInstantiator<T> newInstantiatorOf(Class<T> type, Object... initArgs)
	{

		if (PlatformDescription.isThisJVM(PlatformDescription.HOTSPOT)
			|| PlatformDescription.isThisJVM(PlatformDescription.OPENJDK))
		{
			// The UnsafeFactoryInstantiator would also work. But according to benchmarks, it is 2.5
			// times slower. So I prefer to use this one
			return new SunReflectionFactoryInstantiator<>(type);
		}
		else if (PlatformDescription.isThisJVM(PlatformDescription.DALVIK))
		{
			if (PlatformDescription.isAndroidOpenJDK())
			{
				// Starting at Android N which is based on OpenJDK
				return new UnsafeFactoryInstantiator<>(type);
			}
			if (PlatformDescription.ANDROID_VERSION <= 10)
			{
				// Android 2.3 Gingerbread and lower
				return new Android10Instantiator<>(type);
			}
			if (PlatformDescription.ANDROID_VERSION <= 17)
			{
				// Android 3.0 Honeycomb to 4.2 Jelly Bean
				return new Android17Instantiator<>(type);
			}
			// Android 4.3 until Android N
			return new Android18Instantiator<>(type);
		}
		else if (PlatformDescription.isThisJVM(PlatformDescription.GNU))
		{
			return new GCJInstantiator<>(type);
		}
		else if (PlatformDescription.isThisJVM(PlatformDescription.PERC))
		{
			return new PercInstantiator<>(type);
		}

		// Fallback instantiator, should work with most modern JVM
		return new UnsafeFactoryInstantiator<>(type);

	}
}
