package transfers;

public class ResumeState extends TransferState implements TransferStatusConstans {

	public ResumeState(Transfer transfer) {
		super(transfer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		this.transfer.setTransferState(ACTIVE);
		this.transfer.resume();		
	}

}
