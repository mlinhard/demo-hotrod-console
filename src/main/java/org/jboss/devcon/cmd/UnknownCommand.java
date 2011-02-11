package org.jboss.devcon.cmd;

import org.infinispan.Cache;

public class UnknownCommand extends Command {

	@Override
	public String execute(Cache<String, String> cache) {
		return "unknown command";
	}

}
