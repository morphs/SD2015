package br.edu.ufabc.sd2015.projeto.principal;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import br.edu.ufabc.sd2015.projeto.clients.CJob;
import br.edu.ufabc.sd2015.projeto.comuns.ClientInterface;
import br.edu.ufabc.sd2015.projeto.comuns.ServerInterface;
import br.edu.ufabc.sd2015.projeto.server.Controller;
import br.edu.ufabc.sd2015.projeto.server.FJob_ServerGUI;
import br.edu.ufabc.sd2015.projeto.server.Servidor;
import soa.atomicrmi.TransactionsLock;

public class Principal{
	
	public static void main(String[] args) {
		int clientsnumber = 3;
		int serversnumber = 1;
		Servidor[] svs = new Servidor[serversnumber];
		
		try {
			
			//Faz a função do rmiregistry, na porta default 1099
			Registry reg = LocateRegistry.createRegistry(1099);
			//Cria os  servidores
			for (int i = 0; i < serversnumber; i++) {
				svs[i] = new Servidor("Server"+i);
				Naming.rebind("rmi://localhost/Servers/Server"+i+"/", (ServerInterface) svs[i]);
				System.out.println("Rodando sv"+i+" ...");
			}
	
			//Cria os clientes
			for (int i = 0; i < clientsnumber; i++) {
				Naming.rebind("rmi://localhost/Clients/Client"+i+"/", (ClientInterface) new CJob("Client"+i));
				System.out.println("Rodando Client"+i+" ...");
				
			}
			
			//Define o controller
			ServerInterface controller  = new Controller(serversnumber,clientsnumber);
			Naming.rebind("rmi://localhost/Servidor/Controller/", controller);
			System.out.println("Rodando controle...");
			
			TransactionsLock.initialize(reg);
			
			//Executa a interface do servidor
			for (int i = 0; i < serversnumber; i++) {
				Servidor sv = svs[i];
				new Thread(() -> new FJob_ServerGUI(sv,(Controller)controller)).start();
			}
			
			
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
	}
}
