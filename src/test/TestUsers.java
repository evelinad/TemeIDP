package test;

import java.util.TreeSet;

public class TestUsers {
	
	private String user;
	private TreeSet<String> fl = new TreeSet<String>();
	
	public TestUsers(String user)
	{
		this.user = new String(user);
	}
	
	public void add(String str) 
	{
		fl.add(str);
	}
	
	public String name()
	{
		return user;
	}
	
	public void rm(int index)
	{
		fl.remove(index);
	}
	
	public int size()
	{
		return fl.size();
	}
	
	public String get(int index)
	{
		return (String) fl.toArray()[index];
	}
	
	public String[] toArray()
	{
		return (String[]) fl.toArray(new String[fl.size()]);
	}
	
	public void printFiles() {
		System.out.println((String[]) fl.toArray(new String[fl.size()]));
	}

}
