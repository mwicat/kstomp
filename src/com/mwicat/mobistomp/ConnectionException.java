/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mwicat.mobistomp;

import java.io.IOException;

/**
 *
 * @author mwicat
 */
public class ConnectionException extends ClientException {

    public ConnectionException() {
        
    }

    public ConnectionException(String message) {
        super(message);
    }

}
