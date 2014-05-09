package webservice;
import org.apache.axis.client.*;

import javax.xml.namespace.*;
import javax.xml.rpc.ServiceException;

import java.rmi.RemoteException;
import java.util.*;
import java.net.*;

public class WSClient {
	private URL endpoint ;
	private Service service = new Service();
	private Call echoCall ;	
	private static final String address= "http://192.168.0.43:8080/axis/Server.jws"; 

	
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
		
	}
	
	public Object invoke(String operationName, Object[] params) throws Exception {
		// TODO 4.2
		
		return null; 
	}
	public String login(String userName, String serverPort, LinkedList<String> files)
	{
		String result;
		String request;
		echoCall.setOperationName(new QName("login")); // operation name
		request = userName + "|"+serverPort+"|"+files.toString().replace("[","").replace("]","").replace(",","|").replace(" ","");
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
