package states;

import core.Mediator;

public class ReceiveState extends State{

	public ReceiveState(Mediator med) {
		super(med);
		// TODO Auto-generated constructor stub
		type = 0;
	}
	
	public void updateTransferSelectedUser(String user)
	{
		med.addFilesToModel(user);
		med.setFromValue(user);
		med.setToValue(med.getCurrentUser());
		
	}
}
