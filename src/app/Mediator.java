package app;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import com.sun.org.apache.xml.internal.serializer.ToUnknownStream;

import radiobuttons.ReceiveRadioButton;
import radiobuttons.SendRadioButton;
import states.ReceiveState;
import states.SendState;
import states.StateManager;
import transfers.Transfer;
import users.User;

public class Mediator {

	private ReceiveRadioButton receiveRadioButton;
	private SendRadioButton sendRadioButton;
	private StateManager stateMgr;
	private UserArrayList users;
	private ArrayList<Transfer> transfers;
	private DefaultListModel user_model;
	private DefaultListModel files_model;
	public Mediator() {
		// TODO Auto-generated constructor stub
		stateMgr = new StateManager(this);
		users = new UserArrayList();
		transfers = new ArrayList<Transfer>();
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
	public void updateTransfer(String user)
	{
		stateMgr.getCurrentState().updateTransfer(user);
	}
	public void setFilesModel(DefaultListModel dm)
	{
		this.files_model = dm;
	}
	public void registerReceiveRadioButton(ReceiveRadioButton rb) {
		receiveRadioButton = rb;
	}
	public void doTransfer()
	{
		Transfer transfer = new Transfer(stateMgr.getFileValue(),stateMgr.getToValue(),stateMgr.getFromValue());
		transfers.add(transfer);
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
	public void registerSendRadioButton(SendRadioButton cb) {
		sendRadioButton = cb;
	}

	public void initiateSendFile() {
		stateMgr.setSendState();
		addFilesToModel(getCurrentUser());
	}

	public void initiateReceiveFile() {
		// TODO 2.6
		stateMgr.setReceiveState();
		
	}

}
