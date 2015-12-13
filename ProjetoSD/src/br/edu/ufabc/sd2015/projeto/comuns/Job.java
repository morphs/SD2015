package br.edu.ufabc.sd2015.projeto.comuns;
import java.io.File;

public class Job {

	//Vars
	private  File executable;
	private   boolean hasFile;
	private   String command;
	private   String args;
	
	private   long time;
	private   int group;
	private   int groupOrder;
	private   int priority;
	private   String output;
	private   File outputFile;
	//End Vars


	//Constructors
	public Job(File file, String args, int priority, int time, int group, int groupOrder){
		hasFile = true;
		executable = file;
		this.args = args;
		this.priority = priority;
		this.setTime(time);
		this.group = group;
		this.groupOrder = groupOrder;
	}

	public Job(File file, String cmd, String args, int priority, int time, int group, int groupOrder){
		hasFile = true;
		executable = file;
		this.command = cmd;
		this.args = args;
		this.priority = priority;
		this.setTime(time);
		this.group = group;
		this.groupOrder = groupOrder;
	}

	public Job(String command, String args, int priority, int time, int group, int groupOrder){
		this.command = command;
		this.args = args;
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

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
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

	//End Gets e Sets



}
