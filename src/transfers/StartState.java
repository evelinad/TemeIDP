package transfers;

import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;

public class StartState extends TransferState implements TransferStatusConstans {

	public StartState(Transfer transfer) {
		super(transfer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		this.transfer.setState(ACTIVE);
		this.transfer.start();		

	}

}
