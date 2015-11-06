/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Arrays;

/**
 *
 * @author =)
 */
public class Resposta implements java.io.Serializable{
	
	/**
	 * 
	 */
	//Constantes
	private static final long serialVersionUID = 1L;

    public static final int NEW_MESSAGE_OK    =  1;
    public static final int NEW_MESSAGE_ERROR = -1;
    public static final int GET_FILE_OK    =  2;
    public static final int FILE_NOT_FOUND = -2;
    public static final int FILE_WRITE_OK = 3;
    public static final int FILE_WRITE_ERROR = -3;
    public static final int GET_LIST_OK = 4;
    public static final int GET_LIST_ERROR = -4;
    public static final int NEW_FILE_OK = 5;
    public static final int NEW_FILE_ERROR = -5;
    //End Constantes
    
    //variaveis
    private int messageStatus;
    private String fileContent;
    private String[][] listFiles;
    //End Variaveis
    
    
    //get and sets
    /**
     * @return the messageStatus
     */
    public int getMessageStatus() {
        return messageStatus;
    }

    /**
     * @param messageStatus the messageStatus to set
     */
    public void setMessageStatus(int messageStatus) {
        this.messageStatus = messageStatus;
    }
 
	public String[] getListFiles() {
		System.out.println(Arrays.deepToString(listFiles[0]));
		return listFiles[0];
	}
	
	public String[] getListFilesMD5() {
		return listFiles[1];
	}

	public void setListFiles(String[][] listFiles) {
		this.listFiles = listFiles;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
    
	//Fim gets e Sets
    
}
