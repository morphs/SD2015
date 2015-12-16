package br.edu.ufabc.sd2015.projeto.principal;

import java.rmi.RemoteException;

import br.edu.ufabc.sd2015.projeto.clients.CJob;
import br.edu.ufabc.sd2015.projeto.comuns.Job;

public class Teste {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] vec =  { "/bin/sh"," -c", "ls -l" } ;
		Job j = new Job(vec,1,20,20,10,10);
		CJob cj;
		try {
			cj = new CJob("io");
			System.out.println(cj.runJob(j).getOutput());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
