package transfers;

public class StopState extends TransferState implements TransferStatusConstans {
	public StopState(Transfer transfer) {
		super(transfer);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("deprecation")
	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		this.transfer.setTransferState(STOPPED);
		this.transfer.stop();

	}

}
