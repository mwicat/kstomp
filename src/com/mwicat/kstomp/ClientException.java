/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mwicat.kstomp;

import java.io.IOException;

/**
 *
 * @author mwicat
 */
public class ClientException extends IOException {

    private int priority;

    public ClientException() {
        this("", 0);
    }

    public ClientException(int priority) {
        this("", priority);
    }

    public ClientException(String message) {
        this(message, 0);
    }

    public ClientException(String message, int priority) {
        super(message);
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

}
