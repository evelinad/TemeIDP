package states;

import core.Mediator;

public abstract class State {
	protected Mediator med;
	/**
	 * 0 - Downloading;
	 * 1 - Uploading
	 */
	protected int type;
	State(Mediator med){
		this.med = med;
	}
	
	public abstract void updateTransferSelectedUser(String user);
	public int getType()
	{
		return type;
	}
	
	
}