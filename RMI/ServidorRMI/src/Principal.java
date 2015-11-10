

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;



public class Principal {

	public static void main(String[] args) {
		int serversNumber = 5;
			
		try {
			//Faz a função do rmiregistry, na porta default 1099
			LocateRegistry.createRegistry(1099);
			//Cria os servidores
			for (int i = 0; i < serversNumber; i++) {
				Naming.rebind("rmi://localhost/Servidor/Server"+i+"/", (ServerInterface) new Servidor("Servidor"+i));
				
			}
			//Define o controller
			ServerInterface controller  = new Controller(serversNumber);
			Naming.rebind("rmi://localhost/Servidor/Controller/", controller);
			System.out.println("Rodando...");
			
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			System.out.println("Erro de conexão remota:"+e1.getMessage());
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			System.out.println("Erro de URL mal formada:"+e1.getMessage());
			e1.printStackTrace();
		} catch (NotBoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("Erro de conexão não completada:"+e1.getMessage());
			e1.printStackTrace();
		}
	
		
	//	cli = new Client();
		
	

	  //  cli.run();

	}

}
