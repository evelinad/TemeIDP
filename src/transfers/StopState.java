package transfers;
/**
 *  Put the selected transfer in stop state, after selecting stop button
 *
 */
public class StopState extends TransferState implements TransferStatusConstans {
	public StopState(Transfer transfer) {
		super(transfer);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void doAction() {
		this.transfer.setTransferState(STOPPED);
		this.transfer.stop();

	}

}
