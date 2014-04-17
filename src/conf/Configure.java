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
	private int port; /* reserved for future use */
	
	public Configure(String user, Mediator med, int port)
	{
		currentUser = user;
		this.med = med;
		this.port = port;
		PropertyConfigurator.configure(currentUser + ".properties");
	}
	/*read configuration file*/
	public void init()
	{
		try {
			cfgFile = new RandomAccessFile("test.cfg", "r");
			log.info("Loaded configuration file ...");
			downFolder = cfgFile.readLine();
			fileList = new HashMap<String, ArrayList<String>>();
			ports = new HashMap<String, Integer>();
			log.info("Loading users ...");
			/* add user names */
			for (noUsers = 0; noUsers < 4; noUsers++) {
				aux = cfgFile.readLine();
				users[noUsers] = new String(aux);
				ports.put(aux, Integer.parseInt(cfgFile.readLine()));
				log.debug("Load online user: " + aux + " with port " + ports.get(aux));
			}
			log.debug("User counter " + noUsers);
			
			/* add files for each user */
			for (String str: users)
			{
				setFilesForUser(str);
			}
			log.debug("Finished loading files");
			
		} catch (IOException e) {
			log.error("Opening config file", e);
		}
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
	public void setUpUsers() {
		
		init();
		log.info("Setting current user "+currentUser);
		med.addUserToModel(currentUser);
		med.addUser(new User(currentUser,ports.get(currentUser)));
		med.setCurrentUser(currentUser);
		med.addFilesToUser(currentUser, fileList.get(currentUser));
		fileList.remove(currentUser);
		Set<String> keys = fileList.keySet();
		log.info("Setting other users: ");		
		for (String userName : keys) {
			med.addUserToModel(userName);
			int port = ports.get(userName);
			User user = new User(userName,port);
			med.addUser(user);
			med.addFilesToUser(userName, fileList.get(userName));
			log.info(user + " "+ "with port "+port + fileList);
		}
		log.info("Configuration complete");
	}

}
