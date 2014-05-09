package core;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

import net.ServerPeer;

import org.apache.log4j.Logger;

import states.StateManager;
import transfers.TransferManager;
import users.User;
import webservice.Comunicator;
import webservice.WSClient;

/**
 * 
 * Mediator class
 * 
 */
public class Mediator {

	private StateManager stateMgr;
	private UserArrayList users;
	private DefaultListModel<String> user_model;
	private DefaultListModel<String> files_model;
	private TransferManager transferManager;
	private DefaultTableModel transfer_model;
	private WSClient wsClient;
	private Comunicator com;
	ServerPeer serverPeer;
	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(Mediator.class);

	public Mediator() {
		stateMgr = new StateManager(this);
		users = new UserArrayList();
		transferManager = new TransferManager(this);
		wsClient = new WSClient();
		
		//TODO create polling thread(should check local folder for any files added/removed & ask ws for available users)

	}

	/**
	 * set/get current user
	 */
	public void setCurrentUser(String user, int port, ArrayList<String> files) {
		
		stateMgr.setCurrentUser(user);
		addUser(new User(user,port, "localhost"));
		addFilesToUser(user, files);
		addUserToModel(user);		
		serverPeer = new ServerPeer(port);
		loginCurrentUser();
		com = new Comunicator(this, user);
		new Thread(com).start();
	}

	public String getCurrentUser() {
		return stateMgr.getCurrentUser();
	}
	
	
	private void loginCurrentUser()
	{
		String currentUser = stateMgr.getCurrentUser();
		ArrayList<String> files = users.getUser(currentUser).getFiles();
		String serverPort = Integer.toString(users.getUser(currentUser).getPort());
		String result = wsClient.login(currentUser, serverPort, new LinkedList<>(files));
		//System.out.println("login");
		//System.out.println(result);
		StringTokenizer st = new StringTokenizer(result, "]");
		while(st.hasMoreTokens())
		{
			String userData = st.nextToken();
			//System.out.println(userData);
			userData = userData.replace("[", "");
			//System.out.println(userData);
			StringTokenizer st2  = new StringTokenizer(userData, "|");
			String userName = "";
			String port  = "";
			String IP ="";
			//System.out.println("current user:" + currentUser + ";");
			if(st2.hasMoreTokens())
			{
				userName = st2.nextToken();
				//System.out.println("user:" + userName + ";");
			}
			else
			{
				break;
			}
			if(st2.hasMoreTokens())
			{
				port = st2.nextToken();
				//System.out.println("port " + port);
			}
			else
			{
				break;
			}if(st2.hasMoreTokens())
			{
				IP = st2.nextToken();
				//System.out.println("IP " + IP);
			}
			else
			{
				break;
			}
			ArrayList<String> userFiles = new ArrayList<String>();
			while(st2.hasMoreTokens())
			{
				userFiles.add(st2.nextToken());
			}
			//System.out.println("user: " + userName + " files: " + userFiles);
			//System.out.println("!userName.contentEquals(currentUser) " + !userName.contentEquals(currentUser));
			if (!userName.equals(currentUser))
			{
				addUserToModel(userName);
				addUser(new User(userName,Integer.parseInt(port), IP));
				//System.out.println(userFiles);
				addFilesToUser(userName, userFiles);
			}
			
		}
		
	}
	public void logoutCurrentUser()
	{
		wsClient.logout(stateMgr.getCurrentUser());
		transferManager.stopAllTransfers();
		try {
			com.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			serverPeer.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 * add the files shared by the user to the file model
	 */
	public void addFilesToModel(String userName) {
		files_model.removeAllElements();
		User u = users.getUser(userName);
		for (String file : u.getFiles())
			files_model.addElement(file);
	}

	public void addFileToCurrentUser(String file) {
		users.getUser(stateMgr.getCurrentUser()).insertFile(file);
		transferManager.removeFile(file);
		wsClient.addFile(stateMgr.getCurrentUser(), file);
	}
	
	public void removeFileFromCurrentUser(String file) {
		users.getUser(stateMgr.getCurrentUser()).removeFile(file);
		if (stateMgr.getCurrentState().getType() == 1)
		{
			files_model.removeElement(file);	
		}
		
		wsClient.removeFile(stateMgr.getCurrentUser(), file);
	}
	
	public void addFilesToUser(String userName, ArrayList<String> arrayList) {
		users.getUser(userName).addFiles(arrayList);
		if (userName.equals(stateMgr.getFromValue()) && (stateMgr.getCurrentState().getType() == 0))
		{
			addFilesToModel(userName);
		}
	}
	
	public ArrayList<String> getActiveTransfers()
	{
		return transferManager.getFiles();
	}
	
	public ArrayList<String> getFilesFromUser(String userName)
	{
		return users.getUser(userName).getFiles();
	}

	public void addUserToModel(String user) {
		user_model.addElement(user);
	}

	/**
	 * 
	 * remove a user from user model
	 */
	public void removeUserFromModel(String user) {
		user_model.removeElement(user);
	}

	public void removeUser(String user) {
		users.removeUser(user);
		removeUserFromModel(user);

	}

	public void addUser(User user) {
		users.add(user);
		//System.out.println("med.addUser " + user.getName() + user.getPort());

	}
	
	public UserArrayList getUsers()
	{
		return users;
	}

	/**
	 * 
	 * get reference to user model
	 */
	public void setUserModel(DefaultListModel<String> dm) {
		this.user_model = dm;

	}

	/**
	 * 
	 * set reference to transfer model
	 */
	public void setTransferModel(DefaultTableModel dm) {
		this.transfer_model = dm;
	}

	/**
	 * 
	 * get reference to selected transfer
	 */
	public void setSelectedTransfer(int index) {
		if (index >= 0) {
			System.out.println("index " + index);
			transferManager.setSelectedTransfer(index);
		}
	}

	/**
	 * operations on selected transfer
	 */
	public void stopSelectedTransfer() {
		if (transferManager.stop())
			transfer_model.setValueAt("Stopped",
					transferManager.getSelectedTransferIndex(), 4);
	}

	public void startSelectedTransfer() {
		String type = transferManager.start();
		if (type != null)
			transfer_model.setValueAt(transferManager.getTransferType(),
					transferManager.getSelectedTransferIndex(), 4);
	}

	public void resumeSelectedTransfer() {
		String type = transferManager.resume();
		if (type != null)
			transfer_model.setValueAt(type,
					transferManager.getSelectedTransferIndex(), 4);
	}

	public void pauseSelectedTransfer() {
		if (transferManager.pause())
			transfer_model.setValueAt("Paused",
					transferManager.getSelectedTransferIndex(), 4);
	}

	public void updateTransferSelectedUser(String user) {
		stateMgr.getCurrentState().updateTransferSelectedUser(user);
	}

	public void setFilesModel(DefaultListModel<String> dm) {
		this.files_model = dm;
	}

	/**
	 * transfer is ready to start
	 */
	public void doTransfer() {
		states.State currentState = stateMgr.getCurrentState();
		if (currentState == null)
			return;
		int type = currentState.getType();
		for (User u : users) {
			System.out.println("med.doTransfer" + u.getName() + u.getPort());
		}
		System.out.println("doTransfer2"
				+ users.getUser(stateMgr.getFromValue()).getName()
				+ users.getUser(stateMgr.getFromValue()).getPort());
		if (!(transferManager.addNewTransfer(stateMgr.getFromValue(), stateMgr
				.getToValue(), stateMgr.getFileValue(), type,
				users.getUser(stateMgr.getFromValue()).getPort(), 0)))
			return;
		if (type == 0)
			transfer_model.addRow(new Object[] { stateMgr.getFromValue(),
					stateMgr.getToValue(), stateMgr.getFileValue(), "0%",
					"Pending" });
		else
			transfer_model.addRow(new Object[] { stateMgr.getFromValue(),
					stateMgr.getToValue(), stateMgr.getFileValue(), "0%",
					"Pending" });
	}

	public void doTransferTest() {
		states.State currentState = new states.ReceiveState(this);
		int type = currentState.getType();
		for (User u : users) {
			System.out.println("med.doTransfer" + u.getName() + u.getPort());
		}
		System.out.println("doTransfer2"
				+ users.getUser(stateMgr.getFromValue()).getName()
				+ users.getUser(stateMgr.getFromValue()).getPort());
		if (!(transferManager.addNewTransfer(stateMgr.getFromValue(), stateMgr
				.getToValue(), stateMgr.getFileValue(), type,
				users.getUser(stateMgr.getFromValue()).getPort(), 0)))
			return;
		if (type == 0)
			transfer_model.addRow(new Object[] { stateMgr.getFromValue(),
					stateMgr.getToValue(), stateMgr.getFileValue(), "0%",
					"Pending" });
		else
			transfer_model.addRow(new Object[] { stateMgr.getFromValue(),
					stateMgr.getToValue(), stateMgr.getFileValue(), "0%",
					"Pending" });
	}

	/**
	 * update new transfer parameters
	 */
	public void setToValue(String to) {
		stateMgr.setToValue(to);
	}

	public void setFromValue(String from) {
		stateMgr.setFromValue(from);
	}

	public void setFileValue(String file) {
		stateMgr.setFileValue(file);
	}

	/**
	 * Initiate a start/send transfer when a new operation is selected from the
	 * radio buttons
	 */
	public void initiateSendFile() {
		stateMgr.setSendState();
		addFilesToModel(getCurrentUser());
	}

	public void initiateReceiveFile() {
		stateMgr.setReceiveState();

	}

	public void updateProgress(long progress, int row) {
		transfer_model.setValueAt(Long.toString(progress) + '%', row, 3);
	}

}
