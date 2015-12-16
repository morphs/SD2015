package br.edu.ufabc.sd2015.projeto.comuns;
import java.io.File;
import java.util.Arrays;

public class Job implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Vars
	private  long id;
	private  File executable;
	private   boolean hasFile;
	private   String[] command;
	
	private   long time;
	private   long group;
	private   long groupOrder;
	private   long priority;
	private   String output;
	private   File outputFile;
	//End Vars


	//Constructors

	public Job(File executable, long id, String[] cmd, long priority, long time, long group, long groupOrder){
		hasFile = true;
		this.setId(id);
		this.executable = executable;
		this.command = cmd;
		this.priority = priority;
		this.setTime(time);
		this.group = group;
		this.groupOrder = groupOrder;
	}
	public Job(File executable,String[] cmd, long priority, long time, long group, long groupOrder){
		hasFile = true;
		this.id = (long) Math.round(Math.random()*10000);
		this.executable = executable;
		this.command = cmd;
		this.priority = priority;
		this.setTime(time);
		this.group = group;
		this.groupOrder = groupOrder;
	}
	public Job(File executable, long id, String[] cmd, long priority, long time, long group, long groupOrder,String output, File outputfile){
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
	
	public Job(String[] cmd, long priority, long time, long group, long groupOrder,String output, File outputfile){
		hasFile = false;
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


	public Job(String[] cmd, long id, long priority, long time, long group, long groupOrder){
		this.command = cmd;
		this.id = id;
		this.priority = priority;
		this.setTime(time);
		this.group = group;
		this.groupOrder = groupOrder;
		hasFile = false;


	}
	public Job(String[] cmd, long priority, long time, long group, long groupOrder){
		this.command = cmd;
		this.id = (long) Math.round(Math.random()*10000);
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


	public long getGroup() {
		return group;
	}

	public void setGroup(long group) {
		this.group = group;
	}

	public long getGroupOrder() {
		return groupOrder;
	}

	public void setGroupOrder(long groupOrder) {
		this.groupOrder = groupOrder;
	}

	public long getPriority() {
		return priority;
	}

	public void setPriority(long priority) {
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


	@Override
	public String toString() {
		return "Job [" + (id != 0 ? "id=" + id + ", " : "")
				+ (executable != null ? "executable=" + executable.getName() + ", " : "") + "hasFile=" + hasFile + ", "
				+ (command != null ? "command=" + Arrays.toString(command) + ", " : "") + "time=" + time + ", group=" + group
				+ ", groupOrder=" + groupOrder + ", priority=" + priority + ", "
				+ (output != null ? "output=" + output + ", " : "")
				+ (outputFile != null ? "outputFile=" + outputFile : "") + "]";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	//End Gets e Sets



}
