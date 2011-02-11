package org.jboss.devcon;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.infinispan.client.hotrod.impl.transport.TransportFactory;
import org.infinispan.client.hotrod.impl.transport.tcp.TcpTransportFactory;

/**
 * TcpTransportFactory that will inform listeners about server list change.
 * ugly solution to get hooked to HotRod client's updateServers event.
 */
public class HookedTcpTransportFactory extends TcpTransportFactory {
	
	public static ServerListUpdateNotifier NOTIFIER = new ServerListUpdateNotifier();
	
	public interface ServerListListener {
		   public void updateServers(TransportFactory tf, Collection<InetSocketAddress> currentServerList);
	}
	
	public static class ServerListUpdateNotifier {
		private List<ServerListListener> listeners = new ArrayList<ServerListListener>();
		
		public void addListener(ServerListListener listener) {
			listeners.add(listener);
		}
		
		public void removeListener(ServerListListener listener) {
			listeners.add(listener);
		}
		
		public void notify(TransportFactory tf, Collection<InetSocketAddress> currentServerList) {
			for (ServerListListener listener : listeners) {
				listener.updateServers(tf, currentServerList);
			}
		}
	}

	@Override
	public void updateServers(Collection<InetSocketAddress> newServers) {
		super.updateServers(newServers);
		NOTIFIER.notify(this, getServers());
	}
	
}
