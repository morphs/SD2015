

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Servidor extends UnicastRemoteObject implements ServerInterface{
		
	/**
	 * 
	 */
	
	//Variáveis 
	private static final long serialVersionUID = 1420785107657269733L;
	private static final String SEP = System.getProperty("file.separator");
    private String diretorio;
    private String svId;

    private File folder;
    //Fim variáveis
  
    //Construtor 
    public Servidor(String id) throws RemoteException{
   	
    	this.setSvId(id);
    	diretorio = System.getProperty("user.home")+SEP+"ServidorDeArquivos"+SEP+id+SEP;
		folder = new File(diretorio);
		System.out.println("Criando diretório:" +folder);
		if(!(folder.exists() && folder.isDirectory())){
			folder.mkdirs();
			System.out.println("Diretório criado "+folder);
		}else{
			System.out.println("Diretório já existe ou caminho inválido");
		}

    }

    //Fim Construtor
	 
    //Gets e sets
	public String getSvId() {
		return svId;
	}
	public void setSvId(String svId) {
		this.svId = svId;
	}
	//Fim gets e sets
	
	//Listar arquivos
	public String[] getList() throws RemoteException {
		File[] vFiles = folder.listFiles();
		String[] fileList = new String[vFiles.length];
        for (int i = 0; i < vFiles.length; i++) {
			fileList[i] = vFiles[i].getName();					
		}		
		return fileList;
	}
	
	//Métodos de arquivos
	public int newFile(String filename) throws RemoteException {
		System.out.println("servidor: newFile");
		File newFile = new File(diretorio+filename);
		if(!newFile.exists()){
			try {
				newFile.createNewFile();
				System.out.println(newFile.getAbsolutePath()+" criado.");
				
			} catch (IOException e) {				
				System.out.println("Erro ao criar novo arquivo:"+e.getMessage());
				return ServerInterface.ERROR;
			}
		}		
		System.out.println("Arquivo criado com sucesso!");
		return ServerInterface.OK;
	}
	
	public int writeFile(String filename, String content) throws RemoteException {
		File newFile = new File(diretorio+filename);
	     System.out.println("Escrevendo no arquivo: "+filename);
		if(newFile.exists()){
			try {
				PrintWriter out = new PrintWriter(newFile);
				out.print(content);
				out.flush();
				out.close();
				System.out.println("Escrita bem-sucedida!");
				
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
		String content ="";	
				try {
					content = new String(Files.readAllBytes(Paths.get(diretorio+filename, null)));
					return content;					
				} catch (IOException e) {					
					System.out.println("Erro ao ler o arquivo:"+e.getMessage());
					return null;
				}
	
	}
// Fim métodos de arquivos

}

