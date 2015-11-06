

import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;


public class Controller extends UnicastRemoteObject implements ServerInterface{
    private int serversNumber;
    ArrayList<ServerInterface> servers = new ArrayList<>();
	public Controller(int serversNumber) throws RemoteException, MalformedURLException, NotBoundException{
		for (int i = 0; i < serversNumber; i++) {
			System.out.println("oasijdiajudoijasoidjoisajdoisajdiojsi");
			servers.add((ServerInterface) Naming.lookup("rmi://localhost/Servidor/Server"+i+"/"));
		}
		this.serversNumber = serversNumber;
		
	}
	
	public String[] getList() throws RemoteException {
		
		return null;
	}
	
	public int newFile(String filename) throws RemoteException {
		System.out.println("Controller: newFile");
		int resultado = 1;
		for (int i = 0; i < servers.size(); i++) {
			resultado *= servers.get(i).newFile(filename);			
		}
		return resultado;
		
	}
	
	public int writeFile(String filename, String content) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public String readFile(String filename) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String delteFile(String filename) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
