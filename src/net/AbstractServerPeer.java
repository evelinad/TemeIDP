package net;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

/**
 * Abstract class for server peer
 * 
 */
public abstract class AbstractServerPeer{
	
	protected abstract void acceptOP(SelectionKey key, Selector selector) throws IOException;
	protected abstract void readOP(SelectionKey key);
	protected abstract void writeOP(SelectionKey key);	
}
