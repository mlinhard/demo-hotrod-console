package org.jboss.devcon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.infinispan.Cache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.impl.transport.TransportFactory;
import org.jboss.devcon.HookedTcpTransportFactory.ServerListListener;
import org.jboss.devcon.cmd.Command;
import org.jboss.devcon.cmd.ExitCommand;

/**
 * Simple HotRod console
 * 
 */
public class HotRodConsole implements ServerListListener {
	
	private String server;
	private String port;
	private String namedCache;
	private RemoteCacheManager cacheManager;
	private Cache<String, String> cache;
	
	public HotRodConsole(String server, String port, String namedCache) {
		this.server = server;
		this.port = port;
		this.namedCache = namedCache;
		Properties p = new Properties();
		HookedTcpTransportFactory.NOTIFIER.addListener(this);
		p.put("infinispan.client.hotrod.server_list", server + ":" + port);
		p.put("infinispan.client.hotrod.transport_factory", "org.jboss.devcon.HookedTcpTransportFactory");
			
		Logger.getLogger("org.infinispan").setLevel(Level.OFF);

		cacheManager = new RemoteCacheManager(p);
		cache =  namedCache == null ? cacheManager.<String, String>getCache() : cacheManager.<String, String>getCache(namedCache);
	}

	public static void main(String[] args) {
		String server = "localhost";
		String port = "11222";
		String namedCache = null;
		if (args.length > 0) {
			server = args[0];
		}
		if (args.length > 1) {
			port = args[1];
		}
		if (args.length > 2) {
			namedCache = args[2];
		}
		new HotRodConsole(server, port, namedCache).run();
	}
	
	private void p(String msg) {
		System.out.println(msg);
	}
	
	public void run() {
		p("Welcome to HotRod console");
		p("Connected to " + server + ":" + port);
		BufferedReader cmdReader = new BufferedReader(new InputStreamReader(System.in));
		boolean exit = false;
		while (!exit) {
			try {
				System.out.print("> ");
				Command cmd = Command.parse(cmdReader.readLine());
				if (cmd != null) {
					p(cmd.execute(cache));
					exit = cmd instanceof ExitCommand;
				}
			} catch (IOException e) {
				p("ERROR: " + e.getMessage());
				return;
			}
		}
		cacheManager.stop();
	}

	@Override
	public void updateServers(TransportFactory tf, Collection<InetSocketAddress> currentServerList) {
		p("UPDATED SERVER LIST: " + currentServerList);
	}
}
