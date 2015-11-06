/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author =)
 */
public class Requisicao implements java.io.Serializable{
    
   

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// tipos de mensagem
    public static final int NEW_FILE = 0;
    public static final int GET_LIST = 1;
    public static final int READ_FILE = 2;
    public static final int WRITE_FILE = 3;
    
    //Variáveis
    private int    messageType;
    private String fileContent;
    private String fileName;
    //Fim variáveis
    
    //Gets e sets
    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
    public int getMessageType() {
        return messageType;
    }

    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
    //Fim Gets e sets
    
}
