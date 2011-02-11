package org.jboss.devcon.cmd;

import org.infinispan.Cache;

public class GetCommand extends Command {

	private String key;
	
	public GetCommand(String key) {
		super();
		this.key = key;
	}

	public String execute(Cache<String, String> cache) {
		try {
			String value = cache.get(key);
			if (value == null) {
				return "no result";
			} else {
				return "result=\"" + value + "\"";
			}
		} catch (Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

}
