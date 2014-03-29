package states;

import core.Mediator;

public class SendState extends State{

	public SendState(Mediator med) {
		// TODO Auto-generated constructor stub
		super(med);
		type = 1;
	}

	@Override
	public void updateTransferSelectedUser(String user) {
		// TODO Auto-generated method stub
		med.setFromValue(med.getCurrentUser());
		med.setToValue(user);
		
	}

}
