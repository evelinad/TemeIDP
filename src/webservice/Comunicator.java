package webservice;

import java.util.ArrayList;
import java.util.StringTokenizer;

import users.User;
import core.Mediator;
import core.UserArrayList;

public class Comunicator extends Thread {
	
	Mediator med;
	String crtUser;
	UserArrayList users;
	WSClient wsClient;
	
	public Comunicator(Mediator med, String userName) {
		this.med = med;
		crtUser = userName;
		users = med.getUsers();
		wsClient = new WSClient();
	}

	@Override
	public void run() {
		ArrayList<String> crtUsers = new ArrayList<>();
		for (User u: users)
		{
			crtUsers.add(u.getName());
		}
		ArrayList<String> newUsers = new ArrayList<>();
		ArrayList<String> aux;
		while(true)
		{
			String response;
			response = wsClient.getUsers();
			StringTokenizer st = new StringTokenizer(response, "]");
			while(st.hasMoreTokens())
			{
				String userData = st.nextToken();
				System.out.println(userData);
				userData = userData.replace("[", "");
				System.out.println(userData);
				StringTokenizer st2  = new StringTokenizer(userData, "|");
				String userName = "";
				String port  = "";
				String IP ="";
				if(st2.hasMoreTokens())
				{
					userName = st2.nextToken();
				}
				else
				{
					break;
				}
				if (userName != crtUser)
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
	
					if (!users.contains(userName))
					{
						med.addUserToModel(userName);
						med.addUser(new User(userName,Integer.parseInt(port), IP));	
					}
					newUsers.add(userName);
					System.out.println(userFiles);
					med.addFilesToUser(userName, userFiles);
				}
			}
			if (newUsers.size() < crtUsers.size())
			{
				for (String str: crtUsers)
				{
					if(!newUsers.contains(str))
					{
						med.removeUser(str);
					}
				}
			}
			aux = crtUsers;
			crtUsers = newUsers;
			newUsers = aux;
			newUsers.clear();
			
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
