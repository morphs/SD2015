package br.edu.ufabc.sd2015.projeto.clients;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import br.edu.ufabc.sd2015.projeto.comuns.Job;

public class CJob {
	
	public Job runCmd(Job j){
		try{
		//process construction
		ProcessBuilder procbuilder = new ProcessBuilder( j.getCommand() );
		if(j.isHasFile()){
			procbuilder.directory(j.getExecutable());
		}else{
			procbuilder.directory(new File(System.getProperty("user.home")));
		}
		
		procbuilder.redirectErrorStream(true);
		//Process execution
		Process pro = procbuilder.start();
		//Read output
		
	    InputStream is = pro.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String output = "";
        String line;
        System.out.printf("Output of running %s is:\n",j.getCommand());
        while ((line = br.readLine()) != null) {
        	output += line;
        }
        
        int exitValue = pro.waitFor();
            System.out.println("\n\nExit Value is " + exitValue);
            j.setOutput(output);
            //j.setOutputFile(outputFile);
            return j;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("The execution was interrupted:"+e.getMessage());
            return null;
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 System.out.println("Input or output error:"+e.getMessage());
			return null;
		}
		
    }
		
}
