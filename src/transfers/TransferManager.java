package transfers;

import java.util.ArrayList;

import core.Mediator;

public class TransferManager implements TransferStatusConstans{
	
	/*private P2PJTable trans;
	private DefaultTableModel model;
	private ArrayList<Row> list = new ArrayList<Row>();*/
	private Mediator med;
	private ArrayList<Transfer> transfers =  new ArrayList<Transfer>();
	int selectedTransfer = -1; //TODO check for no row selected 
	
	public TransferManager( Mediator med)
	{
		//trans = table;
		//model = (DefaultTableModel)table.getModel();
		this.med = med;
		transfers = new ArrayList<Transfer>();
	}
	
	public boolean addNewTransfer(String source, String destination, String file, int type)
	{
		Transfer transfer = new Transfer(file,source,destination, med, type);
		if (source == null || destination == null || file == null)
			return false;
		transfer.setIndex(transfers.size());
		transfers.add(transfer);
		transfer.start();
		transfer.updateStatus(ACTIVE);
		return true;
	}
	public void setSelectedTransfer(int index)
	{
		this.selectedTransfer = index;
	}
	
	public int getSelectedTransfer()
	{
		return this.selectedTransfer;
	}
	
	public void updateStatusSelectedTransfer(int status)
	{
		transfers.get(selectedTransfer).updateStatus(status);
	}

	public String start()
	{
		if(selectedTransfer >= 0
				&& (transfers.get(selectedTransfer).getStatus() == STARTED
				|| transfers.get(selectedTransfer).getStatus() == STOPPED))
		{
			transfers.get(selectedTransfer).updateStatus(ACTIVE);
			return transfers.get(selectedTransfer).getType();
		}
		return null;
	}
	
	public boolean stop()
	{
		if(selectedTransfer >= 0
				&& (transfers.get(selectedTransfer).getStatus() == STARTED 
				|| transfers.get(selectedTransfer).getStatus() == PAUSED
				|| transfers.get(selectedTransfer).getStatus() == ACTIVE))
		{
			transfers.get(selectedTransfer).updateStatus(STOPPED);
			return true;
		}
		return false;		
	}
	
	public String resume()
	{
		if(selectedTransfer >= 0
				&& transfers.get(selectedTransfer).getStatus() == PAUSED)
		{
			transfers.get(selectedTransfer).updateStatus(ACTIVE);
			return transfers.get(selectedTransfer).getType();
		}
		return null;
	}
	
	public boolean pause()
	{
		if(selectedTransfer >= 0
				&& (transfers.get(selectedTransfer).getStatus() == STARTED ||
				transfers.get(selectedTransfer).getStatus() == ACTIVE))
		{
			transfers.get(selectedTransfer).updateStatus(PAUSED);
			return true;
		}
		return false;		
	}
	
	public String getTransferType()
	{
		if (selectedTransfer >= 0 && !transfers.isEmpty())
			return transfers.get(selectedTransfer).getType();
		return null;
	}

}

