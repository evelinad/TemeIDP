package conf;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import users.User;
import core.Mediator;

public class Configure {
	
	private RandomAccessFile cfgFile;
	private String downFolder, aux = "aux";
	private String[] users = new String[4];
	private int noUsers;
	private HashMap<String, ArrayList<String>> fileList;
	private HashMap<String, Integer> ports;
	private String currentUser;
	private Mediator med;
	private Logger log = Logger.getLogger(Configure.class);
	@SuppressWarnings("unused")
	private int port; /* reserved for future use */
	
	public Configure(String user, Mediator med, int port)
	{
		currentUser = user;
		this.med = med;
		this.port = port;
	}

	/*load files for a specific user*/
	public void setFilesForUser(String user) {
		File folder = new File(downFolder + "/" + user);
		ArrayList<String> files = new ArrayList<>();
	    for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	log.error("Not handling folders, please archive or move to home");
	        } else {
	            files.add(fileEntry.getName());
	        }
	    }
	    fileList.put(user, files);
	}
	
	/* add users to GUI */
	public void setUpCurrentUser() {
		PropertyConfigurator.configure(currentUser + ".properties");
		setFilesForUser(currentUser);
		log.info("Setting current user "+currentUser);
		med.addUserToModel(currentUser);
		med.addUser(new User(currentUser,ports.get(currentUser)));
		med.setCurrentUser(currentUser);
		med.addFilesToUser(currentUser, fileList.get(currentUser));
	
	}

}
