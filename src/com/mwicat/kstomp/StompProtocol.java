/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mwicat.kstomp;

import java.util.Hashtable;


/**
 * 
 * @author mwicat
 */
public class StompProtocol {

	private Channel channel;
	private boolean listening = false;
	private ConnectionState state;
	public static char FRAME_SEP = '\0';
	private Hashtable<String,MessageHandler> msgHandlers;
	
	private static String TYPE_MESSAGE = "MESSAGE";
	private static String TYPE_ERROR = "ERROR";
	
	
	

	public StompProtocol(ConnectionState state, Channel channel) {
		this.channel = channel;
		this.state = state;
		this.msgHandlers = new Hashtable<String,MessageHandler>();
	}

	public void startListening() throws ClientException {
		state.setState(ConnectionState.STATE_LISTENING);
		listening = true;
		while (listening) {
			processIncoming();
		}
	}
	
	private void processIncoming() throws ClientException {
		String respData = read();
		if (state.getState() > ConnectionState.STATE_DISCONNECTING) {
			handleResponseData(respData);		
		}
	}
	
	private void handleMessage(Packet pckt)  {
		String msg = pckt.getBody();
		String dest = pckt.getHeader("destination");
		msgHandlers.get(dest).onMessage(msg);
	}
	
	public void stopListening() {
		listening = false;
	}

	private void send(Packet pckt) throws ConnectionException {
		channel.send(PacketSerializer.encode(pckt) + FRAME_SEP);
	}
	
	private void send(String command, Hashtable headers) throws ConnectionException {
		send(new Packet(command, headers));		
	}

	private void send(String command) throws ConnectionException {
		send(command, new Hashtable());		
	}	
	
	private String read() throws ConnectionException {
		return channel.readUntil(FRAME_SEP);
	}

	public void subscribe(String dest, MessageHandler handler) throws ClientException {
		Hashtable headers = Packet.createHeaders(new Object[] {"destination", dest});
		msgHandlers.put(dest, handler);
		send("SUBSCRIBE", headers);
	}
	
	public void unsubscribe(String dest) throws ClientException {
		Hashtable headers = Packet.createHeaders(new Object[] { 
				"destination", dest, 
				 });
		msgHandlers.remove(dest);
		send("UNSUBSCRIBE", headers);
	}
	

	public void connect(String username, String password) throws ClientException {
		Hashtable headers = Packet.createHeaders(new Object[] { 
				"login", username, 
				"passcode", password });
		Packet loginPacket = new Packet("CONNECT", headers);
		state.setState(ConnectionState.STATE_AUTHENTICATING);
		send(loginPacket);
		processIncoming();
		state.setState(ConnectionState.STATE_CONNECTED);
	}
	
	private void handleResponseData(String data) throws ClientException {
		Packet resp = PacketSerializer.decode(data);
		if (resp.getType().equals(TYPE_MESSAGE)) {
			handleMessage(resp);
		} else if (resp.getType().equals(TYPE_ERROR)) {
			handleError(resp);
		}
	}
	
	private void handleError(Packet resp) throws ClientException {
		String msg = resp.getBody();
		throw new ClientException(msg);
	}

	public void disconnect() throws ConnectionException {
		state.setState(ConnectionState.STATE_DISCONNECTING);
		send("DISCONNECT");
		state.setState(ConnectionState.STATE_DISCONNECTED);
	}

	public int getReadCount() {
		return channel.getReadCount();
	}
	
	

}
