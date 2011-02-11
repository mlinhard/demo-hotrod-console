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
			return "putting value \"" + value + "\" under key \"" + key + "\"\nSTORED";	
		} catch (Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

}
