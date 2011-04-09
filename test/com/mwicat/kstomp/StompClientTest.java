/*
 * SerializableOutputStreamTest.java
 *  
 * Copyright 2008 C.A. Meijer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mwicat.kstomp;

import jmunit.framework.cldc11.TestCase;

import com.hammingweight.hammock.Hammock;
import com.mwicat.kstomp.AuthException;
import com.mwicat.kstomp.ConnectionException;
import com.mwicat.kstomp.ConnectionState;
import com.mwicat.kstomp.StompProtocol;

public class StompClientTest extends TestCase {

	private Hammock hammock;
	private String user;
	private String password;

	public void setUp() {
		user = "marek";
		password = "pass";
		this.hammock = new Hammock();
	}

	public void tearDown() {

	}

	// If we're marshalling a marshaller we should ask the MarshallerRegistry
	// for a marshaller if the MarshallerRegistry associates a class name
	// with the serialized name.
	public void testAuthError() throws Throwable {
		
	}

	public StompClientTest() {
		super(1, "StompClientTest");
	}

	public void test(int testNum) throws Throwable {
		switch (testNum) {

		case 0:
			testAuthError();
			break;

		default:
			fail("No such test.");
		}
	}

}
