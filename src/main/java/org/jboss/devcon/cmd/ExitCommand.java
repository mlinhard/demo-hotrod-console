package org.jboss.devcon.cmd;

import org.infinispan.Cache;

public class ExitCommand extends Command {

	@Override
	public String execute(Cache<String, String> cache) {
		return "good bye!";
	}

}
