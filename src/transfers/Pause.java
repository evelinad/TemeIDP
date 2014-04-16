package transfers;

public class Pause extends TransferState implements TransferStatusConstans {

	public Pause(Transfer transfer) {
		super(transfer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		transfer.setState(PAUSED);
	}

}
