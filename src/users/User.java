package users;

import java.util.ArrayList;
/**
 * 
 * User class for keeping information related to a user
 * eg: user name, user files
 *
 */
public class User {

	private String name;
	private ArrayList<String> files;
	private int serverPort;
	
	public User(String name, int port) {
		this.name = name;
		this.serverPort = port;
		System.out.println("in user"+this.serverPort);
		files = new ArrayList<String>();
	}
	
	public void insertFile(String file)
	{
		files.add(file);
	}
	public String getName()
	{
		return this.name;	
	}
	public ArrayList<String> getFiles()
	{
		return files;
	}
	
	public void addFiles(ArrayList<String> files)
	{
		this.files.clear();
		if (files == null || files.isEmpty())
			return;
		for (String file : files) {
			this.files.add(file);
		}
	}
	

	
	public int getPort()
	{
		return this.serverPort;
	}

}
