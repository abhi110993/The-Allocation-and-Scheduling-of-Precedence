

import java.util.*;

public class Node {
int name;
int x,d;
boolean updated=false;
int spaceTime;
boolean executed=false;
// Predecessors and descendents
ArrayList<Integer> pre, desc;

Node(int name, int x, int d){
	this.name = name;
	this.x = x;
	this.d = d;
	pre= new ArrayList<Integer>();
	desc = new ArrayList<Integer>();
}

public int getName() {
	return this.name;
}

public int getExecutionTime() {
	return this.x;
}

public int getDeadline() {
	return this.d;
}

public void changeDeadline(int d) {
	this.d = d;
}

public void addPredecessors(int name) {
	pre.add(name);
}

public void addDescendants(int name) {
	desc.add(name);
}

public void printNodeConfig() {
	System.out.println("---------------------Node : '"+this.name+"' ---------------------");
	System.out.println("Execution Time X : "+this.getExecutionTime());
	System.out.println("DeadLine Time X  : "+this.getDeadline());
	if(updated)
		System.out.println("Space Time  : "+this.spaceTime);
	System.out.println("Descendents:");
	for(Integer i : this.desc) {
		System.out.print(i+",");
	}
	System.out.println();
	System.out.println("Predecessors:");
	for(Integer i : this.pre) {
		System.out.print(i+",");
	}
	System.out.println();
}	

}
