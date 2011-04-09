/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mwicat.mobistomp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

/**
 *
 * @author mwicat
 */
public interface StreamManager {

    Reader getReader() throws IOException;
    Writer getWriter() throws IOException;
    Channel open() throws IOException, ConnectionException;
    void close();

}
