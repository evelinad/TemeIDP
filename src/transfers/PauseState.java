package transfers;

/**
 *  Put the selected transfer in the pause stat, after selecting pause button 
 *
 */
public class PauseState extends TransferState implements TransferStatusConstans {

	public PauseState(Transfer transfer) {
		super(transfer);
	}

	@Override
	public void doAction() {
		this.transfer.setTransferState(PAUSED);
		this.transfer.suspend();
	}

}
