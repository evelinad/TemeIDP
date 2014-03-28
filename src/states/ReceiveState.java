package states;

import app.Mediator;

public class ReceiveState extends State{

	public ReceiveState(Mediator med) {
		super(med);
		// TODO Auto-generated constructor stub
	}
	
	public void updateTransfer(String user)
	{
		med.addFilesToModel(user);
		med.setFromValue(user);
		med.setToValue(med.getCurrentUser());
		
	}
}
