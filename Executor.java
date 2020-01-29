
import java.util.*;

public class Executor {

	static ArrayList<Node> graph;
	static ArrayList<Node> queue;
	// Number of Processors
	static int np=2;
	public static void main(String[] s) {
		// Executor Instance
		Executor e = new Executor();
//===============Graph Creation==========================
		// Graph instance
		graph = new ArrayList<Node>();
		// Add Nodes to the graph
		e.addNodes();
		// Completes the graph
		e.addLinks();
		// Prints the Graph
		e.printGraph();
		// Phase 1 update graph
		
//**************PHASE 1************************
		
		
//==============Modify DeadLines================
		//Step 1 : Compute modified deadline
		e.updateGraph();
		//Step 2 : Assign static space time(Di-Xi)
		e.assignStaticSpaceTime();
		// Prints the Graph
		e.printGraph();
		// Step 3 : Construct Ready Queue
		queue = new ArrayList<Node>();
		e.getQueueWithSortedSpaceTime();
 
//**************PHASE 2************************
		e.assignProcessors();
			
	}
	
	public void assignProcessors() {
		int[] pi = new int[np];
		Node[] taskToProcess = new Node[np];
		int k=0;
		int time=0;
		while(k<(queue.size())) {
			for(int i =0;i<np&&k<(queue.size());i++) {
				
				if(pi[i]<=0) {
					boolean assign=true;
					for(Integer m : queue.get(k).pre) {
						for(Node e : graph) {
							if(e.getName()==m) {
								if(!e.executed)
									assign=false;
								break;
							}
						}
					}
					
					
					if(assign) {
						pi[i]=queue.get(k).getExecutionTime();
						System.out.println("Task T"+queue.get(k).getName()+" is assigned to processor P"+i+" at time= "+time);
						taskToProcess[i]=queue.get(k);
						k++;
						
							
					}
				}
					
			}
			time++;
			for(int i =0;i<np;i++) {
				pi[i]--;
				if(pi[i]<=0)
					taskToProcess[i].executed=true;
			}
		}
	}
	
	public void getQueueWithSortedSpaceTime() {
		int n = graph.size();
		for(Node e : graph)
			queue.add(e);
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if (queue.get(j).spaceTime > queue.get(j+1).spaceTime){
                    Node temp = queue.get(j);
                    queue.set(j,queue.get(j+1));
                    queue.set(j+1,temp);
                }
        System.out.println("******************** PHASE I COMPLETED *************************");
        System.out.println("TASK\t SPACE-TIME\t EXECUTION TIME");
        for(Node e : queue)
        	System.out.println(e.name +"\t\t" +e.spaceTime+"\t\t"+e.getExecutionTime());
        
	}
	
	
	public void assignStaticSpaceTime() {
		for(Node e : graph) {
			e.spaceTime=e.getDeadline()-e.getExecutionTime();
		}
	}
	
	public void updateGraph() {
		Node leaf=null;
		// Get leaf Node
		for(Node e : graph) {
			if(e.desc.isEmpty())
				leaf = e;
		}
		
		recursiveUpdate(leaf);
	}
	
	public void recursiveUpdate(Node leaf) {
		if(leaf==null)
			return;
		if(leaf.pre.isEmpty())
			return;
		Node par=null;
		ArrayList<Node> recall = new ArrayList<Node>();
		for(Integer i : leaf.pre) {
			for(Node e : graph) {
				if(e.name==i) {
					par=e;
					break;
				}
			}
			if(!par.updated) {
				changeDeadline(par);
				par.updated=true;
				if(!recall.contains(par))
					recall.add(par);
			}
		}
		for(Node e : recall) {
			recursiveUpdate(e);
		}
		
	}
	
	public void changeDeadline(Node par) {
		int min = par.getDeadline();
		for(Integer i: par.desc) {
			for(Node e : graph) {
				if(e.name == i) {
					if((e.getDeadline()-e.getExecutionTime())<min) {
						min = e.getDeadline()-e.getExecutionTime();
					}
				}
			}
		}
		par.changeDeadline(min);
	}
	
	public void printGraph() {
		for(Node e : graph) {
			e.printNodeConfig();
		}
	}
	
	public void addNodes() {
		graph.add(new Node(0,3,10));
		graph.add(new Node(1,5,19));
		graph.add(new Node(2,2,14));
		graph.add(new Node(3,7,19));
		graph.add(new Node(4,8,20));
		graph.add(new Node(5,5,20));
	}
	
	public void addLinks() {
		// Node 0 
		graph.get(0).addDescendants(2);
		graph.get(0).addDescendants(4);
		// Node 1
		graph.get(1).addDescendants(2);
		graph.get(1).addDescendants(3);
		// Node 2
		graph.get(2).addDescendants(5);
		graph.get(2).addPredecessors(0);
		// Node 3
		graph.get(3).addDescendants(5);
		graph.get(3).addPredecessors(1);
		// Node 4
		graph.get(4).addDescendants(5);
		graph.get(4).addPredecessors(0);
		// Node 5
		graph.get(5).addPredecessors(2);
		graph.get(5).addPredecessors(3);
		graph.get(5).addPredecessors(4);
	}
}
