package br.edu.ufabc.sd2015.projeto.clients;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.crypto.SealedObject;

import br.edu.ufabc.sd2015.projeto.comuns.ClientInterface;
import br.edu.ufabc.sd2015.projeto.comuns.Job;
import br.edu.ufabc.sd2015.projeto.comuns.KeyManager;
import soa.atomicrmi.Transaction;
import soa.atomicrmi.TransactionalUnicastRemoteObject;

public class CJob extends TransactionalUnicastRemoteObject implements ClientInterface {
	
	
	private static final String SEP = System.getProperty("file.separator");
	private final String pw = "SD2015";
    private String diretorio;
    private String clId;
    private File folder;
    private int status;
    
	public CJob(String id) throws RemoteException{
		   	status = 0;
	    	this.setClId(id);
	    	diretorio = System.getProperty("user.home")+SEP+"ServidorDeJobs"+SEP+id+SEP;
			folder = new File(diretorio);
			System.out.println("Criando diretório:" +folder);
		
			if(!(folder.exists() && folder.isDirectory())){
				folder.mkdirs();
				System.out.println("Diretório criado "+folder);
			}else{
				System.out.println("Diretório já existe ou caminho inválido");
			}
	    }


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<String> runJob(SealedObject so){
		status = 1;
		try{
		//process construction	
			KeyManager km = new KeyManager();
			Job j = km.decrypt(so, pw);
		StringBuilder sb = new StringBuilder();
		Arrays.asList(j.getCommand()).forEach(s -> sb.append(s+" "));
		sb.deleteCharAt(sb.length()-1);
		String cmd = sb.toString();
		ProcessBuilder procbuilder;
		if(j.isHasFile()){
			procbuilder = new ProcessBuilder("/bin/bash","-c","nice -n "+j.getPriority()+" "+ cmd+" "+j.getExecutable().toString());
		}else{
			procbuilder = new ProcessBuilder("/bin/bash","-c","nice -n "+j.getPriority()+" "+ cmd);
		}
		
		System.out.println("cooooo::: "+procbuilder.command().toString());
		if(j.isHasFile()){
			procbuilder.directory(j.getExecutable().getParentFile());
		}else{
			procbuilder.directory(new File(System.getProperty("user.home")));
		}		
		procbuilder.redirectErrorStream(true);
		//Process execution
		//Read output
		
		File tempFile = new File("/tmp/job").createTempFile("job", "tmp");
		StringBuilder sb2 = new StringBuilder();
		procbuilder.redirectOutput(tempFile);
		//Process execution
		Process pro = procbuilder.start();
		boolean exitValue = pro.waitFor(j.getTime(),TimeUnit.SECONDS);
		if(!exitValue){
			pro.destroy();
			status = 0;
			List<String> retorno = new ArrayList<String>();
	         return retorno;
		}
		List<String> text = Files.readAllLines(tempFile.toPath());
		text.forEach(S -> sb2.append(S+"\n"));
		String output = sb2.toString();

            System.out.println("\n\nExit Value is " + exitValue);
            System.out.println("\nsdtout:\n"+output);
            j.setOutput(output);
            //j.setOutputFile(outputFile);
            status = 0;
            return text;
           
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("The execution was interrupted:"+e.getMessage());
            List<String> retorno = new ArrayList<String>();
            return retorno;
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 System.out.println("Input or output error:"+e.getMessage());
			 List<String> retorno = new ArrayList<String>();
	         return retorno;
		}
		
    }

	public String getClId() {
		return clId;
	}

	public void setClId(String clId) {
		this.clId = clId;
	}

	@Override
	public String getClientName() throws RemoteException {
		return this.clId;
	}

	@Override
	public int getStatus() throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}


	
	
}
