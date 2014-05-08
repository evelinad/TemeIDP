package conf;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import core.Mediator;

public class Configure {
	
	private HashMap<String, ArrayList<String>> fileList;
	private String currentUser;
	private Mediator med;
	private Logger log = Logger.getLogger(Configure.class);
	private int port; /* reserved for future use */
	
	public Configure(String user, Mediator med, int port)
	{
		currentUser = user;
		this.med = med;
		this.port = port;
		fileList = new HashMap<String, ArrayList<String>>();
	}

	/*load files for a specific user*/
	public void setFilesForUser(String user) {
		File folder = new File("downloads" + "/" + user);
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
		//med.addUserToModel(currentUser);
		//med.addUser(new User(currentUser,this.port));
		med.setCurrentUser(currentUser, this.port, fileList.get(currentUser));
		//med.addFilesToUser(currentUser, fileList.get(currentUser));
	}

}
