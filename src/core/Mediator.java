package core;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

import com.sun.org.apache.xml.internal.serializer.ToUnknownStream;

import radiobuttons.ReceiveRadioButton;
import radiobuttons.SendRadioButton;
import states.ReceiveState;
import states.SendState;
import states.StateManager;
import tables.P2PJTable;
import transfers.Transfer;
import transfers.TransferManager;
import users.User;

public class Mediator {

	private StateManager stateMgr;
	private UserArrayList users;
	private DefaultListModel user_model;
	private DefaultListModel files_model;
	private TransferManager transferManager;
	private DefaultTableModel transfer_model;
	public Mediator() {
		// TODO Auto-generated constructor stub
		stateMgr = new StateManager(this);
		users = new UserArrayList();
		transferManager = new TransferManager(this);
		users.add(new User("user1"));
		users.add(new User("user2"));
		users.add(new User("user3"));
		
		for(User u: users)
		{
			int i=0;
			String filename;
			for(i=0;i<4;i++)
			{
				filename = "file_"+u.getName()+"_"+i;
				u.insertFile(filename);
				
			}
		}
	}
	public void setCurrentUser(String user)
	{
		stateMgr.setCurrentUser(user);
	}
	public String getCurrentUser()
	{
		return stateMgr.getCurrentUser();
	}
	public void addFilesToModel(String userName)
	{
		files_model.removeAllElements();
		User u = users.getUser(userName);
		for(String file: u.getFiles())
			files_model.addElement(file);
	}
	public void addUsersToModel()
	{
		for(User u: users)
			user_model.addElement(u.getName());
	}
	public void setUserModel(DefaultListModel dm)
	{
		this.user_model = dm;
		addUsersToModel();
		
	}
	
	public void setTransferModel(DefaultTableModel dm)
	{
		this.transfer_model = dm;
	}
	
	public void setSelectedTransfer(int index)
	{
		transferManager.setSelectedTransfer(index);
	}
	
	public void stopSelectedTransfer()
	{
		transferManager.stop();
		transfer_model.setValueAt("Stopped", transferManager.getSelectedTransfer(), 4);
	}
	public void startSelectedTransfer()
	{
		transferManager.start();
		transfer_model.setValueAt(transferManager.getTransferType(), transferManager.getSelectedTransfer(), 4);
	}
	
	public void resumeSelectedTransfer()
	{
		transferManager.resume();
		transfer_model.setValueAt(transferManager.getTransferType(), transferManager.getSelectedTransfer(), 4);
	}
	
	public void pauseSelectedTransfer()
	{
		transferManager.pause();
		transfer_model.setValueAt("Paused", transferManager.getSelectedTransfer(), 4);
	}
	public void updateTransferSelectedUser(String user)
	{
		stateMgr.getCurrentState().updateTransferSelectedUser(user);
	}
	
	
	public void setFilesModel(DefaultListModel dm)
	{
		this.files_model = dm;
	}

	public void doTransfer()
	{
		int type = stateMgr.getCurrentState().getType();
		transferManager.addNewTransfer(stateMgr.getFromValue(), stateMgr.getToValue(), stateMgr.getFileValue(), type);
		if (type == 0)
			transfer_model.addRow(new Object[] {
									stateMgr.getFromValue(),
									stateMgr.getToValue(),
									stateMgr.getFileValue(),
									"0%",
									"Downloading"});
		else
			transfer_model.addRow(new Object[] {
									stateMgr.getFromValue(),
									stateMgr.getToValue(),
									stateMgr.getFileValue(),
									"0%",
									"Uploading"});
	}
	
	public void setToValue(String to)
	{
		stateMgr.setToValue(to);
	}
	public void setFromValue(String from)
	{
		stateMgr.setFromValue(from);
	}
	
	public void setFileValue(String file)
	{
		stateMgr.setFileValue(file);
	}

	public void initiateSendFile() {
		stateMgr.setSendState();
		addFilesToModel(getCurrentUser());
	}

	public void initiateReceiveFile() {
		// TODO 2.6
		stateMgr.setReceiveState();
		
	}
	
	public void updateProgress(int progress, int row) {
		transfer_model.setValueAt(progress, row, 3);
	}
	

}
