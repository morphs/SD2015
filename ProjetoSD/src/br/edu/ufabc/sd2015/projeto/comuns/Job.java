package br.edu.ufabc.sd2015.projeto.comuns;
import java.io.File;

public class Job {

	//Vars
	private  int id;
	private  File executable;
	private   boolean hasFile;
	private   String command;
	
	private   int time;
	private   int group;
	private   int groupOrder;
	private   int priority;
	private   String output;
	private   File outputFile;
	//End Vars


	//Constructors

	public Job(File executable, int id, String cmd, int priority, int time, int group, int groupOrder){
		hasFile = true;
		this.setId(id);
		this.executable = executable;
		this.command = cmd;
		this.priority = priority;
		this.setTime(time);
		this.group = group;
		this.groupOrder = groupOrder;
	}
	public Job(File executable,String cmd, int priority, int time, int group, int groupOrder){
		hasFile = true;
		this.setId((int)Math.round(Math.random()*10000));
		this.executable = executable;
		this.command = cmd;
		this.priority = priority;
		this.setTime(time);
		this.group = group;
		this.groupOrder = groupOrder;
	}
	public Job(File executable, int id, String cmd, int priority, int time, int group, int groupOrder,String output, File outputfile){
		hasFile = true;
		this.setId(id);
		this.executable = executable;
		this.command = cmd;
		this.priority = priority;
		this.setTime(time);
		this.group = group;
		this.groupOrder = groupOrder;
		this.output = output;
		this.outputFile = outputfile;
	}


	public Job(String cmd, int id, int priority, int time, int group, int groupOrder){
		this.command = cmd;
		this.priority = priority;
		this.setTime(time);
		this.group = group;
		this.groupOrder = groupOrder;
		hasFile = false;


	}
	public Job(String cmd, int priority, int time, int group, int groupOrder){
		this.command = cmd;
		this.setId((int)Math.round(Math.random()*10000));
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

	public void setTime(int time) {
		this.time = time;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}


	@Override
	public String toString() {
		return "Job [" + (id != 0 ? "id=" + id + ", " : "")
				+ (executable != null ? "executable=" + executable.getName() + ", " : "") + "hasFile=" + hasFile + ", "
				+ (command != null ? "command=" + command + ", " : "") + "time=" + time + ", group=" + group
				+ ", groupOrder=" + groupOrder + ", priority=" + priority + ", "
				+ (output != null ? "output=" + output + ", " : "")
				+ (outputFile != null ? "outputFile=" + outputFile : "") + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	//End Gets e Sets



}
