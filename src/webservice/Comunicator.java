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
	
	
	/*
	 * function for periodically polling info about other users
	 * 
	 * */

	@Override
	public void run() {
		log.info("started web service communication");
		ArrayList<String> crtUsers = new ArrayList<>();
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
				userData = userData.replace("[", "");
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
