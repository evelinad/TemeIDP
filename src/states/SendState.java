package states;

import app.Mediator;

public class SendState extends State{

	public SendState(Mediator med) {
		// TODO Auto-generated constructor stub
		super(med);
	}

	@Override
	public void updateTransfer(String user) {
		// TODO Auto-generated method stub
		med.setFromValue(med.getCurrentUser());
		med.setToValue(user);
		
	}

}
