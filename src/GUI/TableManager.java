package GUI;

import javax.swing.table.DefaultTableModel;

public class TableManager {
	
	private P2PJTable trans;
	private DefaultTableModel model;
	
	public TableManager(P2PJTable table)
	{
		trans = table;
		model = (DefaultTableModel)table.getModel();
	}
	
	public void addFile(String[] row)
	{
		model.addRow(row);
		//TODO: actually add file to transfer manager
	}
	
	public void start()
	{
		int row = trans.getSelectedRow();
		if (row >= 0)
		{
			//TODO: start download somehow
		}
		else
		{
			//print error or just relax and have a drink
		}
	}
	
	public void stop()
	{
		int row = trans.getSelectedRow();
		if (row >= 0)
		{
			//TODO: stop download by closing connection
		}
		else
		{
			//print error or just relax and have a drink
		}
	}
	
	public void resume()
	{
		int row = trans.getSelectedRow();
		if (row >= 0)
		{
			//TODO: add socket to active list
		}
		else
		{
			//print error or just relax and have a drink
		}
	}
	
	public void pause()
	{
		int row = trans.getSelectedRow();
		if (row >= 0)
		{
			//TODO: remove socket from active list
		}
		else
		{
			//print error or just relax and have a drink
		}
	}

}
