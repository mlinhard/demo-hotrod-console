package org.jboss.devcon.cmd;

import org.infinispan.Cache;

public class PutCommand extends Command {

	private String key;
	private String value;
	
	public PutCommand(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public String execute(Cache<String, String> cache) {
		try {
			cache.put(key, value);
			StringBuffer sb = new StringBuffer("STORED value \"");
			sb.append(value);
			sb.append("\" under key \"");
			sb.append(key);
			sb.append("\"");
			return sb.toString();	
		} catch (Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

}
