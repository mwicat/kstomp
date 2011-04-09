/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mwicat.kstomp;

import de.enough.polish.util.Arrays;
import de.enough.polish.util.TextUtil;

import java.util.Enumeration;
import java.util.Hashtable;

import com.mwicat.mobile.util.Util;

/**
 *
 * @author mwicat
 */
public class PacketSerializer {
	
	public static char KEY_VAL_SEP = ':';
	public static char COMMAND_ENTRY_SEP = '\n';
	public static char ENTRY_SEP = '\n';
	public static String HEAD_BODY_SEP = "\n\n";

    public static Packet decode(String data) throws DecoderError {
    	data = Util.ltrim(data);
    	int bodyPos = data.indexOf(HEAD_BODY_SEP);
    	validateSplit(data, bodyPos);
    	String head = data.substring(0, bodyPos);
    	String body = data.substring(bodyPos + HEAD_BODY_SEP.length());
        String[] lines = TextUtil.split(head, ENTRY_SEP);
        String cmd = lines[0];
        String[] headerLines = getHeaders(lines);
        Hashtable headers = decodeHeaders(headerLines);
        return new Packet(cmd, headers, body);
    }
    
    public static Hashtable decodeHeaders(String[] headers) throws DecoderError {
    	Hashtable headersMap = new Hashtable();
    	for (int i = 0; i < headers.length; i++) {
    		String hd = headers[i];
    		int splitPos = hd.indexOf(KEY_VAL_SEP);
    		validateSplit(hd, splitPos);
    		String k = hd.substring(0, splitPos).trim();
    		String v = hd.substring(splitPos+1).trim();
    		headersMap.put(k, v);
    	}
    	return headersMap;
    }
    
    private static void validateSplit(String data, int splitPos) throws DecoderError {
    	if (splitPos == -1) {
    		throw new DecoderError("split failed on " + data);
    	}
	}

	public static String encodeHeaders(Hashtable headers) {
    	StringBuffer sb = new StringBuffer();
    	for (Enumeration e = headers.keys() ; e.hasMoreElements() ;) {
            String k = (String)e.nextElement();
            String v = (String)headers.get(k);
            String line = k + KEY_VAL_SEP + v + ENTRY_SEP;
            sb.append(line);
        }
    	return sb.toString();
    }

    public static String encode(Packet pckt) {
    	String cmd = pckt.getType();
    	String headersStr = encodeHeaders(pckt.getAttributes());
    	String body = pckt.getBody();
    	return cmd + ENTRY_SEP + headersStr + ENTRY_SEP + body;
    }

    private static String[] getHeaders(String[] fields) {
        String[] params = new String[fields.length - 1];
    	Arrays.arraycopy(fields, 1, params, 0, params.length);
        return params;
    }

}
