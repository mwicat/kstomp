/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mwicat.mobistomp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.SocketConnection;


import com.mwicat.mobile.util.ConnectorHelper;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;





/**
 *
 * @author mwicat
 */
public class TCPStreamManager implements StreamManager {

    private SocketConnection connection;
    
    private String url;
    private Reader reader;
    private Writer writer;
    private InputStream input;
    private OutputStream output;
    private final int connectTimeout;


    public TCPStreamManager(String url, int connectTimeout) {
        this.url = url;
        this.connectTimeout = connectTimeout;
    }

    public Reader getReader() throws IOException {
        return reader;
    }

    public Writer getWriter() throws IOException {
        return writer;
    }

    public Channel open() throws IOException, ConnectionException {
        connection = (SocketConnection) ConnectorHelper.open("socket://" + url, Connector.READ_WRITE, connectTimeout);
        connection.setSocketOption(SocketConnection.KEEPALIVE, 1);
        input = connection.openInputStream();
        output = connection.openOutputStream();
        reader = new InputStreamReader(input);
        writer = new OutputStreamWriter(output);
        return new Channel(reader, writer);
    }

    public void close() {
        IOUtils.closeStream(input);
        IOUtils.closeStream(output);
        IOUtils.closeConnection(connection);
    }
}
