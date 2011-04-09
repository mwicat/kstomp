/*
 * TestRunner.java
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

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.midlet.MIDlet;

import jmunit.framework.cldc11.Test;
import jmunit.framework.cldc11.TestResult;

public class TestRunner extends MIDlet {

	private AllTests allTests;

	public TestRunner() {
		Test.initialize(this);
		this.allTests = new AllTests();
	}

	protected void destroyApp(boolean b) {
	}

	protected void pauseApp() {
	}

	protected void startApp() {
		Form form = new Form("Running tests...");
		Display.getDisplay(this).setCurrent(form);
		this.allTests.test();
		//this.notifyDestroyed();
	}

}
