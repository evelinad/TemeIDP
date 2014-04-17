package transfers;

/**
 *  Put the selected transfer in resume state, after selecting resume button
 *
 */
public class ResumeState extends TransferState implements TransferStatusConstans {

	public ResumeState(Transfer transfer) {
		super(transfer);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void doAction() {
		this.transfer.setTransferState(ACTIVE);
		this.transfer.resume();		
	}

}
