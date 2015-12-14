package br.edu.ufabc.sd2015.projeto.comuns;
import java.io.File;

public class Job {

	//Vars
	private  String name;
	private  File executable;
	private   boolean hasFile;
	private   String[] command;
	
	private   long time;
	private   int group;
	private   int groupOrder;
	private   int priority;
	private   String output;
	private   File outputFile;
	//End Vars


	//Constructors

	public Job(String name, File file, String[] cmd, int priority, long time, int group, int groupOrder){
		hasFile = true;
		this.name = name;
		executable = file;
		this.command = cmd;
		this.priority = priority;
		this.setTime(time);
		this.group = group;
		this.groupOrder = groupOrder;
	}
	public Job(String name, File file, String[] cmd, int priority, long time, int group, int groupOrder,String output, File outputfile){
		hasFile = true;
		this.name = name;
		executable = file;
		this.command = cmd;
		this.priority = priority;
		this.setTime(time);
		this.group = group;
		this.groupOrder = groupOrder;
		this.output = output;
		this.outputFile = outputfile;
	}


	public Job(String name, String[] command, int priority, long time, int group, int groupOrder){
		this.command = command;
		this.name = name;
		this.priority = priority;
		this.setTime(time);
		this.group = group;
		this.groupOrder = groupOrder;
		hasFile = false;


	}
	//End constructors

	//Gets e Sets
	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	
	public File getExecutable() {
		return executable;
	}

	public void setExecutable(File executable) {
		this.executable = executable;
	}

	public boolean isHasFile() {
		return hasFile;
	}

	public void setHasFile(boolean hasFile) {
		this.hasFile = hasFile;
	}

	public String[] getCommand() {
		return command;
	}

	public void setCommand(String[] command) {
		this.command = command;
	}


	public int getGroup() {
		return group;
	}

	public void setGroup(int group) {
		this.group = group;
	}

	public int getGroupOrder() {
		return groupOrder;
	}

	public void setGroupOrder(int groupOrder) {
		this.groupOrder = groupOrder;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//End Gets e Sets



}
