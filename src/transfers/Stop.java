package transfers;

public class Stop extends TransferState implements TransferStatusConstans {

	public Stop(Transfer transfer) {
		super(transfer);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		transfer.setState(STOPPED);
		try {
			transfer.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
