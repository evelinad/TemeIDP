package transfers;

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
	
	RandomAccessFile cfgFile;
	//BufferedReader cfgReader;
	String downFolder, aux = "aux";
	String[] users = new String[4];
	int noUsers;
	HashMap<String, ArrayList<String>> fileList;
	String currentUser;
	Mediator med;
	Logger log = Logger.getLogger(Configure.class);
	
	public Configure(String user, Mediator med)
	{
		currentUser = user;
		this.med = med;
		PropertyConfigurator.configure(currentUser + ".properties");
		System.out.println("luat logger file");
	}
	
	public void init()
	{
		try {
			log.info("init config");
			cfgFile = new RandomAccessFile("test.cfg", "r");
			System.out.println(currentUser);
			downFolder = cfgFile.readLine();
			System.out.println("user folder: " + downFolder);
			fileList = new HashMap<String, ArrayList<String>>();
			log.debug("finished init");
			
			log.info("finding users");
			//add user names
			for (noUsers = 0; noUsers < 4; noUsers++) {
				aux = cfgFile.readLine();
				System.out.println("added user: " + aux);
				users[noUsers] = new String(aux);
			}
			log.debug("finished user counter: " + noUsers);
			
			log.info("adding files to each user");
			//add files for each user
			for (String str: users)
			{
				setFilesForUser(str);
			}
			log.debug("files added");
			
		} catch (IOException e) {
			e.printStackTrace();
			log.debug("opening config file", e);
		}
	}
	
	public void setFilesForUser(String user) {
		File folder = new File(downFolder + "/" + user);
		ArrayList<String> files = new ArrayList<>();
	    for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	System.out.println("not handling folders, please archive or move to home");
	        } else {
	            System.out.println(fileEntry.getName());
	            files.add(fileEntry.getName());
	        }
	    }
	    fileList.put(user, files);
	}
	
	private void checkMap() {
		System.out.println("map checking");
		for (String user : fileList.keySet())
		{
			System.out.println(user + " : " + fileList.get(user));
		}
	}
	
	public void setUpUsers() {
		
		init();
		checkMap();
		
		log.info("adding & setting current user");
		med.addUserToModel(new User(currentUser));
		med.setCurrentUser(currentUser);
		med.addFilesToUser(currentUser, fileList.get(currentUser));
		fileList.remove(currentUser);
		log.debug("user added to model and removed from map");
		
		checkMap();
		
		log.info("adding the rest");
		Set<String> keys = fileList.keySet();
		for (String userName : keys) {
			log.info("adding " + userName + " to list");
			med.addUserToModel(new User(userName));
			med.addFilesToUser(userName, fileList.get(userName));
			log.debug("added " + userName);
		}
		log.debug("configuration complete");
	}

}
