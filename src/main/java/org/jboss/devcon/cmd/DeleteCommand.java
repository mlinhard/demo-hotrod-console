package org.jboss.devcon.cmd;

import org.infinispan.Cache;

public class DeleteCommand extends Command {

	private String key;
	
	public DeleteCommand(String key) {
		super();
		this.key = key;
	}

	public String execute(Cache<String, String> cache) {
		try {
			cache.remove(key);
			return "DELETED \"" + key + "\"";
		} catch (Exception e) {
			return "ERROR: " + e.getMessage();
		}
	}

}
