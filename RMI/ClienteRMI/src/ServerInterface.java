

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
	
	public String[] getList() throws RemoteException;
   
	public int newFile(String filename) throws RemoteException;
	
	public int writeFile(String filename, String content) throws RemoteException;	
	
	public String readFile(String filename) throws RemoteException;
	
	public String delteFile(String filename) throws RemoteException;
	

	
	
 int OK    =  1;
 int ERROR =  0;

		
	
}
