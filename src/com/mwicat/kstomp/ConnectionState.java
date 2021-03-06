package com.mwicat.kstomp;

import de.enough.polish.event.EventManager;

public class ConnectionState {
	
    public static final int STATE_DISCONNECTED = 0;
    public static final int STATE_DISCONNECTING = 1;
    public static final int STATE_CONNECTING = 2;
    public static final int STATE_CONNECTED = 3;
    public static final int STATE_AUTHENTICATING = 4;
    public static final int STATE_AUTHENTICATED = 5;
    public static final int STATE_LISTENING = 6;
	private int connectionState = STATE_DISCONNECTED;

    public int getState() {
    	return connectionState;
    }

    public void setState(int state) {
        connectionState = state;
        EventManager.fireEvent("state", this, new Integer(state));
    }

}
