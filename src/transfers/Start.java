package transfers;

import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;

public class Start extends TransferState implements TransferStatusConstans {

	public Start(Transfer transfer) {
		super(transfer);
		// TODO Auto-generated constructor stub
	}


	Transfer transfer;
	
	
	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		transfer.start();
		transfer.setState(ACTIVE);
	}

}
