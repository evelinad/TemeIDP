package net;

import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public abstract class AbstractServerPeer{
	
	protected abstract void acceptOP(SelectionKey key, Selector selector);
	protected abstract void readOP(SelectionKey key);
	protected abstract void writeOP(SelectionKey key);	
}
