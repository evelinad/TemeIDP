package states;

import core.Mediator;

public abstract class State {
	Mediator med;
	State(Mediator med){
		this.med = med;
	}
	
	public abstract void updateTransferSelectedUser(String user);
	
	
}