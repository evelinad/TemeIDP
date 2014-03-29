package transfers;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import tables.P2PJTable;
import core.Mediator;

public class TransferManager implements TransferStatusConstans{
	
	private P2PJTable trans;
	private DefaultTableModel model;
	private ArrayList<Row> list = new ArrayList<Row>();
	private Mediator med;
	private ArrayList<Transfer> transfers;
	int selectedTransfer; //TODO check for no row selected 
	
	public TransferManager( Mediator med)
	{
		//trans = table;
		//model = (DefaultTableModel)table.getModel();
		this.med = med;
		transfers = new ArrayList<Transfer>();
	}
	
	public void addNewTransfer(String source, String destination, String file)
	{
		Transfer transfer = new Transfer(file,source,destination);
		transfers.add(transfer);
		
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

	public void start()
	{
		if(transfers.get(selectedTransfer).getStatus() == STARTED)
			transfers.get(selectedTransfer).updateStatus(DOWNLOADING);
		
	}
	
	public void stop()
	{
		if(transfers.get(selectedTransfer).getStatus() == STARTED 
				|| transfers.get(selectedTransfer).getStatus() == PAUSED)		
					transfers.get(selectedTransfer).updateStatus(STOPPED);		
	}
	
	public void resume()
	{
		if(transfers.get(selectedTransfer).getStatus() == PAUSED) 
				transfers.get(selectedTransfer).updateStatus(DOWNLOADING);
	
	}
	
	public void pause()
	{
		if(transfers.get(selectedTransfer).getStatus() == STARTED ||
			transfers.get(selectedTransfer).getStatus() == DOWNLOADING) 
				transfers.get(selectedTransfer).updateStatus(PAUSED);		
	}

}

