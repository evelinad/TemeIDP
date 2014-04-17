package core;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

import net.ServerPeer;

import org.apache.log4j.Logger;

import states.StateManager;
import transfers.TransferManager;
import users.User;

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
	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(Mediator.class);

	public Mediator() {
		stateMgr = new StateManager(this);
		users = new UserArrayList();
		transferManager = new TransferManager(this);

	}

	/**
	 * set/get current user
	 */
	public void setCurrentUser(String user) {
		stateMgr.setCurrentUser(user);
		User u = users.getUser(user);
		new ServerPeer(u.getPort());
	}

	public String getCurrentUser() {
		return stateMgr.getCurrentUser();
	}

	/**
	 * 
	 * add the files shared bye the user to the file model
	 */
	public void addFilesToModel(String userName) {
		files_model.removeAllElements();
		User u = users.getUser(userName);
		for (String file : u.getFiles())
			files_model.addElement(file);
	}

	public void addFilesToUser(String userName, ArrayList<String> arrayList) {
		users.getUser(userName).addFiles(arrayList);
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

	}

	public void addUser(User user) {
		users.add(user);
		System.out.println("med.addUser " + user.getName() + user.getPort());

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
