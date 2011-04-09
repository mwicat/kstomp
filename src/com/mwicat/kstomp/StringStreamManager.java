/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mwicat.kstomp;


import java.io.IOException;
import de.enough.polish.io.StringReader;
import java.io.Reader;
import java.io.Writer;

import com.mwicat.mobile.util.StringWriter;

/**
 *
 * @author mwicat
 */
public class StringStreamManager implements StreamManager {

    private String input;
    private Reader reader;
    private Writer writer;

    public StringStreamManager(String input) {
        this.input = input;
    }

    public Reader getReader() throws IOException {
        return reader;
    }

    public Writer getWriter() throws IOException {
        return writer;
    }

    public String getData() {
        return writer.toString();
    }

    public Channel open() throws IOException, ConnectionException {
        reader = new StringReader(input);
        writer = new StringWriter();
        return new Channel(reader, writer);
    }

    public void close() {
    }
}
