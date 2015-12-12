import java.io.File;

public class Job {
       File executable;
       boolean hasFile;
       String command;
       String args;
       long time;
       int group;
       int groupOrder;
       int priority;
       
public Job(String filePath, String args, int priority, int time, int group, int groupOrder){
	hasFile = true;
	executable = new File(filePath);
	this.args = args;
	this.priority = priority;
	this.time = time;
	this.group = group;
	this.groupOrder = groupOrder;
}

public Job(boolean hasFile, String command, String args, int priority, int time, int group, int groupOrder){
	hasFile = false;
	this.command = command;
	this.args = args;
	this.priority = priority;
	this.time = time;
	this.group = group;
	this.groupOrder = groupOrder;
}
       
}
