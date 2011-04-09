/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mwicat.kstomp;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import com.mwicat.mobile.util.Util;

/**
 * 
 * @author mwicat
 */
public class Channel {

	private Reader reader;
	private Writer writer;
	private int readCount;

	public Channel(Reader reader, Writer writer) throws ConnectionException {
		this.reader = reader;
		this.writer = writer;
	}

	public String readUntil(char ch) throws ConnectionException {		
		String msg;
		try {
			msg = readUntilInternal(ch);
			System.out.println("RECV {\n" + msg + ch + "\n}");
		} catch (IOException e) {
			throw new ConnectionException("readLine exception");
		}
		return msg;
	}

	public int getReadCount() {
		return readCount;
	}

	private String readUntilInternal(char ch) throws IOException {
		final StringBuffer sb = new StringBuffer();
		int c = reader.read();
		while (!(c == -1 || c == ch)) {
			sb.append((char) c);
			c = reader.read();
			readCount++;
		}
		return sb.toString();
	}

	public String readLine() throws ConnectionException {
		return readUntil(Util.NEWLINE);
	}

	public void sendLine(String msg) throws ConnectionException {
		send(msg + Util.NEWLINE);
	}

	public void send(String msg) throws ConnectionException {
		try {
			// #debug info
			System.out.println("SEND {\n" + msg + "\n}");
			writer.write(msg);
			writer.flush();
		} catch (IOException ex) {
			throw new ConnectionException(ex.getMessage());
		}
	}

}
