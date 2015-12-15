package br.edu.ufabc.sd2015.projeto.principal;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

import br.edu.ufabc.sd2015.projeto.server.Arrumar_Controle;
import br.edu.ufabc.sd2015.projeto.server.ServerInterface;
import br.edu.ufabc.sd2015.projeto.server.Arrumar_Servidor;

public class ServerMain {
	public static void main(String[] args) {
		try {
			int clientnumber = 5;
			//Faz a função do rmiregistry, na porta default 1099
			LocateRegistry.createRegistry(1099);
			//Cria o servidor
			Naming.rebind("rmi://localhost/Servidor/Server"+1+"/", (ServerInterface) new Arrumar_Servidor("Servidor"+1));

			//Define o controller
			ServerInterface controller  = new Arrumar_Controle(clientnumber);
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
