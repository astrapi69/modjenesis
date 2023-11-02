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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.Externalizable;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import org.junit.jupiter.api.Test;

import io.github.astrapi69.modjenesis.ObjenesisHelper;

/**
 * This test makes sure issue #33 is not occurring.
 *
 * @author Henri Tremblay
 */
public class ExternalizableTest
{

	public static class C
	{

		public int val = 33;

		protected C()
		{
		}
	}

	public static class B extends C implements Serializable
	{
		public B()
		{
			fail("B constructor shouldn't be called");
		}
	}

	public static class A extends B implements Externalizable
	{

		public A()
		{
			fail("A constructor shouldn't be called");
		}

		public void writeExternal(ObjectOutput out)
		{

		}

		public void readExternal(ObjectInput in)
		{

		}
	}

	@Test
	public void test()
	{
		A a = ObjenesisHelper.newSerializableInstance(A.class);
		// The constructor from C should have been called
		assertEquals(33, a.val);
	}
}
