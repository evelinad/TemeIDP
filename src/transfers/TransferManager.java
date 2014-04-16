package transfers;

import java.util.ArrayList;

import core.Mediator;

/**
 * 
 * keeps evidence of all ongoing transfers
 * 
 */
public class TransferManager implements TransferStatusConstans {

	private Mediator med;
	private ArrayList<Transfer> transfers = new ArrayList<Transfer>();
	int selectedTransfer = -1;

	public TransferManager(Mediator med) {
		this.med = med;
		transfers = new ArrayList<Transfer>();
	}

	public boolean addNewTransfer(String source, String destination,
			String file, int type) {
		Transfer transfer = new Transfer(file, source, destination, med, type);
		if (source == null || destination == null || file == null)
			return false;
		transfer.setIndex(transfers.size());
		transfers.add(transfer);
		transfer.setState(STARTED);
		return true;
	}

	/**
	 * 
	 * get/set selected transfer from JTable
	 */
	public void setSelectedTransfer(int index) {
		this.selectedTransfer = index;
	}

	public int getSelectedTransfer() {
		return this.selectedTransfer;
	}

	public void setStatusSelectedTransfer(int status) {
		transfers.get(selectedTransfer).setState(status);
	}

	/**
	 * start/stop/pause a selected transfer
	 */
	public String start() {
		if (selectedTransfer >= 0
				&& transfers.get(selectedTransfer).getTransferState() == STARTED) {
			transfers.get(selectedTransfer).start();
			transfers.get(selectedTransfer).setState(ACTIVE);
			return transfers.get(selectedTransfer).getType();
		}
		return null;
	}

	public boolean stop() {
		if (selectedTransfer >= 0
				&& (transfers.get(selectedTransfer).getTransferState() == STARTED
						|| transfers.get(selectedTransfer).getTransferState() == PAUSED || transfers
						.get(selectedTransfer).getTransferState() == ACTIVE)) {
			transfers.get(selectedTransfer).setState(STOPPED);
			return true;
		}
		return false;
	}

	public String resume() {
		if (selectedTransfer >= 0
				&& transfers.get(selectedTransfer).getTransferState() == PAUSED) {
			transfers.get(selectedTransfer).setState(ACTIVE);
			return transfers.get(selectedTransfer).getType();
		}
		return null;
	}

	public boolean pause() {
		if (selectedTransfer >= 0
				&& (transfers.get(selectedTransfer).getTransferState() == STARTED || transfers
						.get(selectedTransfer).getTransferState() == ACTIVE)) {
			transfers.get(selectedTransfer).setState(PAUSED);
			return true;
		}
		return false;
	}

	/**
	 * 
	 * get/set transfer type
	 */
	public String getTransferType() {
		if (selectedTransfer >= 0 && !transfers.isEmpty())
			return transfers.get(selectedTransfer).getType();
		return null;
	}

}
