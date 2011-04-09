/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mwicat.kstomp;


/**
 *
 * @author mwicat
 */
public class DecoderError extends ConnectionException {

    public DecoderError(String msg) {
        super(msg);
    }

    public DecoderError() {
        super();
    }

}
