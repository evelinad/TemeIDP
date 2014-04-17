package transfers;

public class PauseState extends TransferState implements TransferStatusConstans {

	public PauseState(Transfer transfer) {
		super(transfer);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("deprecation")
	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		//this.transfer.se
		this.transfer.setTransferState(PAUSED);
		this.transfer.suspend();
	}

}
