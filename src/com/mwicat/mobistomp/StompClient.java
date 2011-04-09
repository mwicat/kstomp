package com.mwicat.mobistomp;

import java.io.IOException;


public class StompClient {
	private StreamManager manager;
	private ConnectionState state = new ConnectionState();
	private int timeoutInterval;


	public StompClient(StreamManager manager, int timeoutInterval) {
		this.manager = manager;
		this.timeoutInterval = timeoutInterval;
	}
	
	
	
	public ConnectionState getConnectionState() {
		return state;
	}

	public StompSession connect(String username, String password) throws ClientException {
		try {
			state.setState(ConnectionState.STATE_CONNECTING);
			Channel channel = manager.open();
			StompProtocol protocol = new StompProtocol(state, channel);
			protocol.connect(username, password);
			StompSession session = new StompSession(manager, protocol, state);
			HeartbeatMonitor monitor = new HeartbeatMonitor(session, timeoutInterval);
			monitor.start();
			return session;
		} catch (IOException ex) {
			throw new ConnectionException(ex.getMessage());
		}
	}


}
