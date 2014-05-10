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
	//private static final String address= "http://localhost:8080/axis/Server.jws";
	private static final String address= "http://localhost:8080/axis/Server.jws";
	private Logger log = Logger.getLogger(WSClient.class);

	
	public WSClient() {
		try {
			endpoint = new URL(address);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		service = new Service();
		try {
			echoCall = (Call)service.createCall();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		echoCall.setTargetEndpointAddress(endpoint);
		log.info("created connection to service");
		
	}
	
	public Object invoke(String operationName, Object[] params) throws Exception {
		// TODO 4.2
		
		return null; 
	}
	public String login(String userName, String serverPort, LinkedList<String> files)
	{
		log.info("logging in to service");
		String result;
		String request;
		echoCall.setOperationName(new QName("login")); // operation name
		request = userName + "|"+serverPort+"|"+files.toString().replace("[","").replace("]","").replace(",","|").replace(" ","");
		System.out.println(request);
		Object[] params = new Object[] { request }; // operation parameters
		
		try {
			result=(String) echoCall.invoke(params);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return result;		
	
	}
	
	public  void logout(String userName)
	{
		log.info("logout");
		String request;
		echoCall.setOperationName(new QName("login")); // operation name
		request = userName;
		Object[] params = new Object[] { request }; // operation parameters
		
		try {
			 echoCall.invoke(params);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public void addFile(String userName, String file)
	{
		log.info("adding file " + file + " to list on service");
		echoCall.setOperationName(new QName("addFile")); // operation name
		Object[] params = new Object[] { userName, file }; // operation parameters
		
		try {
			 echoCall.invoke(params);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	public void removeFile(String userName, String file)
	{
		log.info("removing file " + file + " from list on service");
		echoCall.setOperationName(new QName("removeFile")); // operation name
		Object[] params = new Object[] { userName, file }; // operation parameters
		
		try {
			 echoCall.invoke(params);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}					
	}
	public String getUsers()
	{
		log.info("getting users and their info from service");
		String result;
		echoCall.setOperationName(new QName("getUsers")); // operation name
		Object[] params = new Object[] {  }; // operation parameters
		
		try {
			result=(String) echoCall.invoke(params);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return result;		
		
	}
	


}
