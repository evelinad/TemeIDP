package transfers;

import javax.swing.SwingWorker;

//SwingWorker<Integer, Integer>
public class Transfer implements TransferStatusConstans{

	String file;
	String toUser;
	String fromUser;
	int status;
	int progress;
	
	public Transfer(String file,String fromUser,String toUser) {
			this.file = file;
			this.toUser = toUser;
			this.fromUser = fromUser;
			status = STARTED;
			
	}
	
	public void updateProgress(int unit)
	{
		progress+=unit;
	}
	public void updateStatus(int status)
	{
		this.status = status;
	}
	
	public int getProgress()
	{
		return this.progress;
	}
	public int getStatus()
	{
		return this.status;
	}	
}
