package webservice;

import java.io.File;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import users.User;
import core.Mediator;

public class Comunicator extends Thread {
	
	private Mediator med;
	private String crtUser;
	private WSClient wsClient;
	private Logger log = Logger.getLogger(Comunicator.class);
	
	public Comunicator(Mediator med, String userName) {
		this.med = med;
		crtUser = userName;
		wsClient = new WSClient();
	}
	
	public void setFilesForUser() {
		File folder = new File("downloads" + "/" + crtUser);
		ArrayList<String> files = new ArrayList<>();
		ArrayList<String> transfers = med.getActiveTransfers();
	    for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        } else {
	        	if (transfers.contains(fileEntry.getName()))
	        	{
		            files.add(fileEntry.getName());	        		
	        	}
	        }
	    }
	    ArrayList<String> crtFiles = med.getFilesFromUser(crtUser);
	    if (crtFiles.size() < files.size())
	    {
	    	for(String s: files)
	    	{
	    		if(!crtFiles.contains(s))
	    		{
	    			med.addFileToCurrentUser(s);
	    		}
	    	}
	    }
	    else
	    {
	    	for (String s: crtFiles)
	    	{
	    		if(!files.contains(s))
	    		{
	    			med.removeFileFromCurrentUser(s);
	    		}
	    	}
	    }
	}

	@Override
	public void run() {
		log.info("started web service communication");
		ArrayList<String> crtUsers = new ArrayList<>();
		/*for (User u: med.getUsers())
		{
			crtUsers.add(u.getName());
		}
		crtUsers.remove(crtUser);*/
		ArrayList<String> newUsers = new ArrayList<>();
		ArrayList<String> aux = new ArrayList<String>();
		int j = 0;
		while(true)
		{
			for (User u: med.getUsers())
			{
				crtUsers.add(u.getName());
			}
			crtUsers.remove(crtUser);
			String response;
			log.info("getting users' info from service");
			response = wsClient.getUsers();
			if (j % 10 == 0)
			{
				j = 0;
				System.out.println(response);
			}
			j++;
			StringTokenizer st = new StringTokenizer(response, "]");
			int i = 0;
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
				if(st2.hasMoreTokens())
				{
					userName = st2.nextToken();
					aux.add(userName);
				}
				else
				{
					break;
				}
				if (!userName.equals(crtUser))
				{
					if(st2.hasMoreTokens())
					{
						port = st2.nextToken();
					}
					else
					{
						break;
					}
					if(st2.hasMoreTokens())
					{
						IP = st2.nextToken();
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
	
					if (!crtUsers.contains(userName))
					{
						med.addUserToModel(userName);
						med.addUser(new User(userName,Integer.parseInt(port), IP));
						log.info("added user " + userName);
					}
					newUsers.add(userName);
					//System.out.println(userFiles);
					med.addFilesToUser(userName, userFiles);
				}
			}
			if (i % 10 == 0)
			{
				System.out.println("new:" + newUsers + " " + "crt:" + crtUsers + " rcvd:" + aux);
				i = 1;
			}
			i++;
			if (newUsers.size() < crtUsers.size())
			{
				for (String str: crtUsers)
				{
					if(!newUsers.contains(str))
					{
						med.removeUser(str);
						log.info("removed user " + str);
					}
				}
			}
			/*aux = crtUsers;
			crtUsers = newUsers;
			newUsers = aux;*/
			newUsers.clear();
			crtUsers.clear();
			aux.clear();
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.err.println("Comunicator can't sleep, he's too busy doing"
						+ " Emperor knows what");
				e.printStackTrace();
			}
		}

	}

}
