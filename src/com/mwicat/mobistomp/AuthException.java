/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mwicat.mobistomp;


/**
 *
 * @author mwicat
 */
public class AuthException extends ClientException {
    
    public AuthException() {
        super(1);
    }

    public AuthException(String msg) {
        super(msg, 1);
    }

}
