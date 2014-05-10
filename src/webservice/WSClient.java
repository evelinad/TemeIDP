package webservice;
import org.apache.axis.client.*;
import org.apache.log4j.Logger;

import javax.xml.namespace.*;
import javax.xml.rpc.ServiceException;

import java.rmi.RemoteException;
import java.util.*;
import java.net.*;

public class WSClient {
	private URL endpoint ;
	private Service service = new Service();
	private Call echoCall ;
	private static final String address= "http://localhost:8080/axis/Server.jws";
	private Logger log = Logger.getLogger(WSClient.class);

	
	public WSClient() {
		try {
			endpoint = new URL(address);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		service = new Service();
		try {
			echoCall = (Call)service.createCall();
		} catch (ServiceException e) {
			e.printStackTrace();
		}			
		echoCall.setTargetEndpointAddress(endpoint);
		log.info("created connection to service");
		
	}
	
	public Object invoke(String operationName, Object[] params) throws Exception {
		return null; 
	}

	/*
	 * function called for sending login info to the web service
	 * */
	public String login(String userName, String serverPort, LinkedList<String> files)
	{
		log.info("logging in to service");
		String result;
		String request;
		echoCall.setOperationName(new QName("login"));
		request = userName + "|"+serverPort+"|"+files.toString().replace("[","").replace("]","").replace(",","|").replace(" ","");
		System.out.println(request);
		Object[] params = new Object[] { request };
		
		try {
			result=(String) echoCall.invoke(params);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
		return result;		
	
	}
	
	/*
	 * function called for sending logout info to the web service
	 * */	
	public  void logout(String userName)
	{
		log.info("logout");
		String request;
		echoCall.setOperationName(new QName("login")); 
		request = userName;
		Object[] params = new Object[] { request }; 
		
		try {
			 echoCall.invoke(params);
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
	}
	
	/*
	 * function called for sending info about  a new file to the web service
	 * */
	public void addFile(String userName, String file)
	{
		log.info("adding file " + file + " to list on service");
		echoCall.setOperationName(new QName("addFile")); 
		Object[] params = new Object[] { userName, file };
		
		try {
			 echoCall.invoke(params);
		} catch (RemoteException e) {
			e.printStackTrace();
		}				
	}
	/*
	 * function called for sending  info about a removed file to the web service
	 * */	
	public void removeFile(String userName, String file)
	{
		log.info("removing file " + file + " from list on service");
		echoCall.setOperationName(new QName("removeFile"));
		Object[] params = new Object[] { userName, file }; 
		
		try {
			 echoCall.invoke(params);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
	}
	/*
	 * function called for retrieving other users
	 * */	
	public String getUsers()
	{
		log.info("getting users and their info from service");
		String result;
		echoCall.setOperationName(new QName("getUsers")); 
		Object[] params = new Object[] {  };
		
		try {
			result=(String) echoCall.invoke(params);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
		return result;		
		
	}
	


}
