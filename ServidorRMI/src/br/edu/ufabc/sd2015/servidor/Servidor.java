package br.edu.ufabc.sd2015.servidor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import br.edu.ufabc.sd2015.comuns.Requisicao;
import br.edu.ufabc.sd2015.comuns.Resposta;
import br.edu.ufabc.sd2015.comuns.ServerInterface;

public class Servidor extends UnicastRemoteObject implements ServerInterface{
	
	
	private static final String SEP = System.getProperty("file.separator");
    private String diretorio;
    private String svId;

    private File folder;

    public Servidor(String id) throws RemoteException{
    	
    	
    	this.setSvId(id);
    	diretorio = System.getProperty("user.home")+SEP+"ServidorDeArquivos"+SEP+id+SEP;
		folder = new File(diretorio);
		System.out.println("Criando pasta:" +folder);
		if(!(folder.exists() && folder.isDirectory())){
			folder.mkdirs();
			System.out.println("Diretório criado "+folder);
		}else{
			System.out.println("Diretório já existe ou caminho inválido");
		}

    }

	private  String[][] getFileList(){
		File[] vFiles = folder.listFiles();
		System.out.println(folder.toString()+ "   "+vFiles.length);
		String[][] retorno = new String[2][vFiles.length];
		for(int i =0;i<vFiles.length;i++){
			retorno[0][i] = vFiles[i].getName();
			retorno[1][i] = MD5(vFiles[i]);
		}
		return retorno;
	}

	
	public String MD5(File file){
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			 FileInputStream fis = new FileInputStream(file);
			    byte[] dataBytes = new byte[1024];
			 
			    int nread = 0; 
			 
			    while ((nread = fis.read(dataBytes)) != -1) {
			      md.update(dataBytes, 0, nread);
			    };
			 
			    byte[] mdbytes = md.digest();
			 
			    
			    StringBuffer sb = new StringBuffer("");
			    for (int i = 0; i < mdbytes.length; i++) {
			    	sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
			    }
			    fis.close();
			    return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	  
        catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	 
	   
	}
	
	
	public String getSvId() {
		return svId;
	}
	public void setSvId(String svId) {
		this.svId = svId;
	}
	
	public String[] getList() throws RemoteException {
		File[] vFiles = folder.listFiles();
		String[] fileList = new String[vFiles.length];
        for (int i = 0; i < vFiles.length; i++) {
			fileList[i] = vFiles[i].getName();					
		}		
		return fileList;
	}
	
	public int newFile(String filename) throws RemoteException {
		File newFile = new File(diretorio+filename);
		if(!newFile.exists()){
			try {
				newFile.createNewFile();
				
			} catch (IOException e) {				
				System.out.println("Erro ao criar novo arquivo:"+e.getMessage());
				return ServerInterface.ERROR;
			}
		}		
		System.out.println("Arquivo criado com sucesso");
		return ServerInterface.OK;
	}
	
	public int writeFile(String filename, String content) throws RemoteException {
		File newFile = new File(diretorio+filename);
	     System.out.println("Escrevendo no arquivo "+filename);
		if(newFile.exists()){
			try {
				PrintWriter out = new PrintWriter(newFile);
				out.print(content);
				out.flush();
				out.close();
				  System.out.println("Escrita certa");
				
			} catch (IOException e) {
				System.out.println("Erro ao escrever no arquivo:"+e.getMessage());
				return ServerInterface.ERROR;
			}
		}else{
			System.out.println("Arquivo não existe!");
			return ServerInterface.ERROR;
		}
		return ServerInterface.OK;
	}
	
	public String readFile(String filename) throws RemoteException {
		File newFile  = new File(diretorio+filename);
		String content ="";	
				try {
					content = new String(Files.readAllBytes(Paths.get(diretorio+filename)));
					return content;					
				} catch (IOException e) {					
					System.out.println("Erro ao ler o arquivo:"+e.getMessage());
					return null;
				}
	
	}

	@Override
	public String delteFile(String filename) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
