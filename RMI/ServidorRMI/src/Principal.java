

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;



public class Principal {

	public static void main(String[] args) {
		int serversNumber = 5;
	//	ArrayList<Servidor> serversList = new ArrayList<>();
		
	//	Client cli;
		
		try {
			
			for (int i = 0; i < serversNumber; i++) {
				Naming.rebind("rmi://localhost/Servidor/Server"+i+"/", (ServerInterface) new Servidor("Servidor"+i));
				
			}
			
			ServerInterface controller  = new Controller(serversNumber);
			Naming.rebind("rmi://localhost/Servidor/Controller/", controller);
			System.out.println("rodando");
			
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			
		
		
		
		
		
	//	cli = new Client();
		
	

	  //  cli.run();

	}

}
