package transfers;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import tables.P2PJTable;
import core.Mediator;

public class TransferManager implements TransferStatusConstans{
	
	/*private P2PJTable trans;
	private DefaultTableModel model;
	private ArrayList<Row> list = new ArrayList<Row>();*/
	private Mediator med;
	private ArrayList<Transfer> transfers =  new ArrayList<Transfer>();
	int selectedTransfer; //TODO check for no row selected 
	
	public TransferManager( Mediator med)
	{
		//trans = table;
		//model = (DefaultTableModel)table.getModel();
		this.med = med;
		transfers = new ArrayList<Transfer>();
	}
	
	public void addNewTransfer(String source, String destination, String file, int type)
	{
		Transfer transfer = new Transfer(file,source,destination, med, type);
		transfer.setIndex(transfers.size());
		transfers.add(transfer);
		transfer.start();
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
		if(transfers.get(selectedTransfer).getStatus() == STARTED)
			transfers.get(selectedTransfer).updateStatus(ACTIVE);
		return transfers.get(selectedTransfer).getType();
	}
	
	public void stop()
	{
		if(transfers.get(selectedTransfer).getStatus() == STARTED 
				|| transfers.get(selectedTransfer).getStatus() == PAUSED
				|| transfers.get(selectedTransfer).getStatus() == ACTIVE)
					transfers.get(selectedTransfer).updateStatus(STOPPED);		
	}
	
	public String resume()
	{
		if(transfers.get(selectedTransfer).getStatus() == PAUSED) 
				transfers.get(selectedTransfer).updateStatus(ACTIVE);
		return transfers.get(selectedTransfer).getType();
	}
	
	public void pause()
	{
		if(transfers.get(selectedTransfer).getStatus() == STARTED ||
			transfers.get(selectedTransfer).getStatus() == ACTIVE) 
				transfers.get(selectedTransfer).updateStatus(PAUSED);		
	}
	
	public String getTransferType()
	{
		return transfers.get(selectedTransfer).getType();
	}

}

