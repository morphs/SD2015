/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ufabc.sd2015.projeto1;

/**
 *
 * @author isidro
 */
public class Requisicao implements java.io.Serializable{
    
   

	// tipos de mensagem
    public static final int NEW_FILE = 0;
    public static final int GET_LIST = 1;
    public static final int READ_FILE = 2;
    public static final int WRITE_FILE = 3;
    
    private int    messageType;
    private String fileContent;
    private String fileName;

    public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
    public int getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
    
    
}
