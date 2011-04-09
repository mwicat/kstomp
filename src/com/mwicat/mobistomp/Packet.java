/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mwicat.mobistomp;

import java.util.Hashtable;

import com.mwicat.mobile.util.Util;

/**
 *
 * @author mwicat
 */
public class Packet {

    private Hashtable headers;
    private String type;
    private String body;

    public Packet(String type, Hashtable attributes, String body) {
        this.type = type;
        this.headers = attributes;
        this.body = body;
    }

    public Packet(String type, Hashtable attributes) {
        this(type, attributes, "");
    }
    
    public Hashtable getAttributes() {
        return headers;
    }

    public String getType() {
        return type;
    }

    public String getHeader(String name) {
        return (String)headers.get(name);
    }

    public int getSize() {
        return headers.size();
    }
    
    

    public String getBody() {
		return body;
	}

	public static Hashtable createHeaders(Object[] objs) {
        int n = objs.length / 2;
        Hashtable ht = new Hashtable(n);
        for (int i = 0; i < objs.length; i+=2) {
            ht.put(objs[i], objs[i+1]);
        }
        return ht;
    }

	public boolean equals(Object o) {
		if (!(o instanceof Packet)) {
			return false;
		}
		Packet po = (Packet)o;
		return Util.equals(getAttributes(), po.getAttributes()) &&
		getBody().equals(po.getBody()) && 
		getType().equals(po.getType());
	}
}
