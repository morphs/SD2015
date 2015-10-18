package br.edu.ufabc.sd2015.projeto1;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Servidor extends Thread {
	
	
	private static final String SEP = System.getProperty("file.separator");
    private  String diretorio;
    private String svId;
    private int porta;
    //static HashMap<Integer, File> mapa = new HashMap<Integer, File>();
    private File folder;

    public Servidor(String id,int porta){
    	this.setSvId(id);
    	this.porta = porta;
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
	public  void run() {		
		//Pasta do usuário
		//               --ServidorDeArquivos
	    //                                 --servidor (thread) 1,2 e 3....
		//test
//		File teste = new File(diretorio+"arquivoteste");
//		if(!teste.exists()){try {
//			teste.createNewFile();
//		} catch (IOException e) {		
//		}}
//		//test
//		
//		File[] Files = Folder.listFiles();
//	   for (int i = 0; i < Files.length; i++) {
//		mapa.put(i, Files[i]);
//			}
//	   
		//mapa.forEach((i,f) -> System.out.println(String.valueOf(i)+" : "+f.getAbsolutePath()));  //test
	
		ServerSocket Server;
		try {
			System.out.println("Cria socket do server "+svId);
			Server = new ServerSocket(porta);
			do{
				
				Socket client = Server.accept();
				System.out.println("Conexão1: "+client);
				ObjectInputStream ServerInput = new ObjectInputStream(client.getInputStream());
				Requisicao request = (Requisicao)ServerInput.readObject();
				Resposta response = new Resposta();
				
				//Case da requisicao
				File novoArquivo;
                switch (request.getMessageType()) {
                //
                //CASO LISTA DE ARQUIVOS
				//
                case Requisicao.GET_LIST:
					//String[] filesList = new String[mapa.size()];
					//mapa.forEach((i,f) -> FilesList[i] = f.getName());
					response.setListFiles(getFileList());
			    	response.setMessageStatus(Resposta.GET_LIST_OK);
					System.out.println("oks");
					break;
					
					//
	                //CASO NOVO ARQUIVO
					//
				case Requisicao.NEW_FILE:
					novoArquivo = new File(diretorio+request.getFileName());
					if(!novoArquivo.exists()){
						try {
							novoArquivo.createNewFile();
							
						} catch (IOException e) {
							response.setMessageStatus(Resposta.FILE_WRITE_ERROR);
							System.out.println("Erro ao criar novo arquivo:"+e.getMessage());
						}
					}
					response.setListFiles(getFileList());
					response.setMessageStatus(Resposta.FILE_WRITE_OK);
					System.out.println("Arquivo criado com sucesso");
					break;
					
					//
	                //CASO ESCRITA DE ARQUIVO
					//	
				case Requisicao.WRITE_FILE:
					novoArquivo = new File(diretorio+request.getFileName());
				     System.out.println("Escrevendo no arquivo:"+request.getFileName());
					if(novoArquivo.exists()){
						try {
							PrintWriter out = new PrintWriter(novoArquivo);
							out.print(request.getFileContent());
							out.flush();
							out.close();
							  System.out.println("Escrita certa");
							response.setMessageStatus(Resposta.FILE_WRITE_OK);
						} catch (IOException e) {
							response.setMessageStatus(Resposta.FILE_WRITE_ERROR);
							System.out.println("Erro ao escrever no arquivo:"+e.getMessage());
						}
					}else{
						response.setMessageStatus(Resposta.FILE_NOT_FOUND);
						System.out.println("Arquivo não existe!");
					}
					
					break;
					//
	                //CASO LEITURA DE ARQUIVOS
					//
				case Requisicao.READ_FILE:
					novoArquivo = new File(diretorio+request.getFileName());
					String content ="";
					if(!novoArquivo.exists()){
						response.setMessageStatus(Resposta.FILE_NOT_FOUND);
					}else{
						content = new String(Files.readAllBytes(Paths.get(diretorio+request.getFileName())));
						response.setMessageStatus(Resposta.GET_FILE_OK);
					}
					response.setFileContent(content);
					
					break;
				default:
					System.out.println("Requisição inválida");
					break;
				}
                
                //Fechando tudo e enviando dados
                 System.out.println("enviando...");
				 ObjectOutputStream serverOutput = new ObjectOutputStream(client.getOutputStream());
				 serverOutput.writeObject(response);	                
				 
				 serverOutput.close();
				 ServerInput.close();
	             client.close();
			}while(true);

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
					
	}

	private  String[] getFileList(){
		File[] vFiles = folder.listFiles();
		System.out.println(folder.toString()+ "   "+vFiles.length);
		String[] retorno = new String[vFiles.length];
		for(int i =0;i<retorno.length;i++){
			retorno[i]= vFiles[i].getName();
		}
		return retorno;
}
	public String getSvId() {
		return svId;
	}
	public void setSvId(String svId) {
		this.svId = svId;
	}

}

