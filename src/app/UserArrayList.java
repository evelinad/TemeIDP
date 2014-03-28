package app;

import java.util.ArrayList;

import users.User;

public class UserArrayList extends ArrayList<User> {
	public User getUser(String user)
	{
		for(User u:this)
		{
			if(u.getName().equals(user)) 
				return u;
		}
		return null;
	}
}
