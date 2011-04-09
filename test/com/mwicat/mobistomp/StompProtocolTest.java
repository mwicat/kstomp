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

package com.mwicat.mobistomp;

import jmunit.framework.cldc11.TestCase;

import com.hammingweight.hammock.*;
import com.mwicat.mobistomp.mocks.MockChannel;
import com.mwicat.mobistomp.*;

class PacketArgumentMatcher implements IArgumentMatcher {
	public boolean areArgumentsEqual(Object exp, Object curr) {
		try {
		Packet p1 = PacketSerializer.decode((String)exp);
		Packet p2 = PacketSerializer.decode((String)curr);
		return p1.equals(p2);
		} catch (DecoderError derr) {
			return false;
		}
	}
	
}

public class StompProtocolTest extends TestCase {

	private Hammock hammock;
	private MockChannel mockChannel;
	private String username;
	private String password;
	private MessageHandler handler;

	public void setUp() {
		username = "marek";
		password = "pass";
		handler = new MessageHandler() {
			public void onMessage(String msg) {
				
			}
		};
		this.hammock = new Hammock();
		try {
			this.mockChannel = new MockChannel(null, null, this.hammock);
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void tearDown() {

	}

	// If we're marshalling a marshaller we should ask the MarshallerRegistry
	// for a marshaller if the MarshallerRegistry associates a class name
	// with the serialized name.
	public void testAuthError() throws Throwable {
		String req = "CONNECT\n" +
		"login:marek\n" +
		"passcode:pass\n" +
		"\n\0";
		String resp = "ERROR\n" +
		"message: bad login\n" +
		"\n\0";
		
		
		this.hammock
		.setExpectation(MockChannel.MTHD_SEND_$_STRING,
				new Object[] { req })
		.setArgumentMatcher(0, new PacketArgumentMatcher());
		
		this.hammock
		.setExpectation(MockChannel.MTHD_READ_UNTIL_$_CHAR)
		.setReturnValue(resp);
		
		
		ConnectionState state = new ConnectionState();
		StompProtocol cl = new StompProtocol(state, mockChannel);
		try {
			cl.connect(username, password);
			fail();
		} catch (ClientException e) {

		}
		this.hammock.verify();
	}

	public void testAuthOk() throws Throwable {
		String req = "CONNECT\n" +
		"login: marek\n" +
		"passcode: pass\n" +
		"\n\0";
		String resp = "CONNECTED\n" +
		"session: 1\n" +
		"\n\0";
		
		this.hammock
		.setExpectation(MockChannel.MTHD_SEND_$_STRING,
				new Object[] { req })
		.setArgumentMatcher(0, new PacketArgumentMatcher());
		this.hammock.setExpectation(MockChannel.MTHD_READ_UNTIL_$_CHAR).setReturnValue(resp);
		
		
		ConnectionState state = new ConnectionState();
		StompProtocol cl = new StompProtocol(state, mockChannel);
		try {
			cl.connect(username, password);
		} catch (ClientException e) {
			fail();
		}
		this.hammock.verify();
	}

	public StompProtocolTest() {
		super(2, "StompProtocolTest");
	}

	public void test(int testNum) throws Throwable {
		switch (testNum) {

		case 0:
			testAuthError();
			break;
		case 1:
			testAuthOk();
			break;

		default:
			fail("No such test.");
		}
	}

}
