package com.mwicat.kstomp;

public class StompSession {
	private StompProtocol protocol;
	private ConnectionState state = new ConnectionState();
	private StreamManager manager;	

	public StompSession(StreamManager manager, StompProtocol protocol, ConnectionState state) {
		super();
		this.manager = manager;
		this.protocol = protocol;
		this.state = state;
	}
	
	public int getReadCount() {
		return protocol.getReadCount();
	}	
	
	

	public int getConnectionState() {
		return state.getState();
	}

	public void startListening() throws ClientException {
		protocol.startListening();
	}

	public void stopListening() {
		protocol.stopListening();
	}	

	public void subscribe(String dest, MessageHandler handler) throws ClientException {
		protocol.subscribe(dest, handler);
	}

	public void unsubscribe(String dest) throws ClientException {
		protocol.unsubscribe(dest);
	}

	public void disconnect() {
		if (state.getState() < ConnectionState.STATE_CONNECTING) {
			return;
		}
		state.setState(ConnectionState.STATE_DISCONNECTING);
		disconnectProto();
		manager.close();
		state.setState(ConnectionState.STATE_DISCONNECTED);
	}
	
	private void disconnectProto() {
		try {
			protocol.disconnect();
		} catch (ConnectionException e) {
			//#debug info
			System.out.println("exception when disconnecting: " + e);
		}		
	}
	
}
