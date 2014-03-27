package GUI;

import javax.swing.table.DefaultTableModel;

public abstract class Transfers {
	
	DefaultTableModel model;
	int index;
	
	public Transfers(DefaultTableModel model, int index)
	{
		this.model = model;
		this.index = index;
	}
	
	public void execute()
	{
		
	}

}
