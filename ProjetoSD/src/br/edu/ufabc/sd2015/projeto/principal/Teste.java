package br.edu.ufabc.sd2015.projeto.principal;

import java.rmi.RemoteException;

import br.edu.ufabc.sd2015.projeto.clients.CJob;
import br.edu.ufabc.sd2015.projeto.comuns.Job;
import br.edu.ufabc.sd2015.projeto.comuns.KeyManager;

public class Teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] vec =  { "pico /home/morps/desktop/motog.txt" } ;
		Job j = new Job(vec,1,20,20,10,10);
		CJob cj;
		try {
			KeyManager km = new KeyManager();
			cj = new CJob("io");
		
		//	System.out.println(cj.runJob(j).getOutput());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
