package transfers;

/**
 * Put the selected transfer in start state, after selecting start button
 * 
 */
public class StartState extends TransferState implements TransferStatusConstans {

	public StartState(Transfer transfer) {
		super(transfer);
	}

	@Override
	public void doAction() {
		this.transfer.setTransferState(ACTIVE);
		this.transfer.start();

	}

}
