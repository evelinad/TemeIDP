package transfers;

import javax.swing.table.DefaultTableModel;

public class Row {
	
	private DefaultTableModel model;
	private int index;
	private int progress = 0;
	private String operation;
	/**
	 * 0 - stopped;
	 * 1 - downloading;
	 * 2 - paused;
	 * 3 - completed;
	 */
	private int state = 0;
	
	public Row(DefaultTableModel model, int index, String type)
	{
		this.model = model;
		this.index = index;
		this.operation = new String(type + "ing");
	}
	
	public void start()
	{
		if (state == 0)
			{
			state = 1;
			model.setValueAt(operation, index, 4);
			}
	}
	
	public void stop()
	{
		state = 0;
		model.setValueAt("Stopped", index, 4);
	}
	
	public void resume()
	{
		if (state == 2)
		{
			state = 1;
			model.setValueAt(operation, index, 4);
		}
		
	}
	
	public void pause()
	{
		if (state == 1)
		{
			state = 2;
			model.setValueAt("Paused", index, 4);
		}
	}
	
	public boolean isDownloading() {
		if (state == 1)
			return true;
		return false;
	}
	
	public boolean isPaused() {
		if (state == 2)
			return true;
		return false;
	}
	
	public boolean isStopped() {
		if (state == 0)
			return true;
		return false;
	}
	
	public boolean isCompleted() {
		if (state == 3) {
			return true;
		}
		return false;
	}
	
	
	
	public void update(int aValue)
	{
		progress += aValue;
		if (progress >= 100)
		{
			progress = 100;
			state = 3;
		}
		model.setValueAt(new String(Integer.toString(progress) + '%'), index, 3);
	}

}
