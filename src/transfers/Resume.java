package transfers;

public class Resume extends TransferState implements TransferStatusConstans {

	public Resume(Transfer transfer) {
		super(transfer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		transfer.setState(ACTIVE);
	}

}
