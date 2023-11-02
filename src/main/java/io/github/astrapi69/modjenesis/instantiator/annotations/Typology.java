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
package io.github.astrapi69.modjenesis.instantiator.annotations;

/**
 * Possible types of instantiator
 * 
 * @author Henri Tremblay
 */
public enum Typology
{
	/**
	 * Mark an instantiator used for standard instantiation (not calling a constructor).
	 */
	STANDARD,

	/**
	 * Mark an instantiator used for serialization.
	 */
	SERIALIZATION,

	/**
	 * Mark an instantiator that doesn't behave like a {@link #STANDARD} nor a
	 * {@link #SERIALIZATION} (e.g. calls a constructor, fails all the time, etc.)
	 */
	NOT_COMPLIANT,

	/**
	 * No type specified on the instantiator class
	 */
	UNKNOWN
}
