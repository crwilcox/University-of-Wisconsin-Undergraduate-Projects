import java.io.*;
import java.util.*;


public class Cycles {

	Stack<Integer> path = new Stack();
	ArrayList<Node> nodes = new ArrayList<Node>();

	public Cycles(String netlistPath){
		BuildDataStructure(netlistPath);
	}

	private void BuildDataStructure(String netlistPath){
		//need to add to nodes all of the data we have
		//get line from file
		//split on ' '
		//first element (0) is the name
		//next is the type
		//everything else are children.
		//stop parsing when an element is the semicolon and go to the next line
		try{
			File outFile = new File(netlistPath);
			FileReader rstream = new FileReader(outFile);
			BufferedReader outReader = new BufferedReader(rstream);
			String line;
			while(outReader.ready()){
				line = outReader.readLine();
				try{
					String[] lineElements = line.split("\\s+");
					int name = Integer.parseInt(lineElements[0]);
					String type = lineElements[1];
					ArrayList<Integer> children = new ArrayList<Integer>();
					for(int i = 2; i < lineElements.length - 1; i++){
						if(lineElements[i].equals(";")) break;
						children.add(Integer.parseInt(lineElements[i]));
					}
					Node myNode = new Node(name, children, type);
					nodes.add(myNode);

					//System.out.println(String.format("Name: %d Type: %s Children: %s", name, type, children.toArray().toString()));
				}
				catch(Exception e){
					System.out.println("Failed to parse line: " + line);
				}
			}
		}
		catch(Exception e){
			System.err.println("Something went wrong checking the output file");
		}

	}

	public ArrayList<Integer[]> getCycles() {
		ArrayList<Integer[]> returnArray = new ArrayList<Integer[]>();

		//Find all latches
		ArrayList<Node> latches = new ArrayList<Node>();
		for(Node node : nodes){
			if(node.type.equals("LATCH")) latches.add(node);
		}

		//foreach latch, get cycles
		for(Node latch : latches){
			//System.out.println("Finding if there is a cycle for: " + String.format("%s %s", latch.name, latch.type));
			Stack<Integer> ret = getCycleOfNodes(latch, latch, new Stack<Integer>());
			
			if(ret == null ){}
			else{
				Integer[] returnInts = new Integer[ret.size()];
				int count = 0;
				while(!ret.isEmpty()){
					returnInts[count] = ret.pop();
					count++;
				}
				returnArray.add(returnInts);
			}
		}

		return returnArray;
	}

	/* RECURSIVE METHOD */
	private Stack<Integer> getCycleOfNodes(Node currNode, Node endNode, Stack<Integer> stack){
		//push currentNode onto the stack
		
		//if the end of the stack is our end node (and stack is larger than one item), just return the current stack
		//else if the last node is on the stack already, we have caused a cycle, but not including the latch, return null
		//else
			// we need to look at all children, and try to get the cycles from there
			//foreach child
				// find the child node in our list of nodes
				// getCycleOfNodes(childNode, endNode)
				// if return is non null, we are fine, return the stack returned from child
				// else repeat/continue
		/*****************/
		//push currentNode onto the stack
		stack.push(currNode.name);

		//if the end of the stack is our end node (and stack is larger than one item), just return the current stack
		if(stack.peek() == endNode.name && stack.size() > 1){
			return stack;
		}
		//else if the last node is on the stack already, we have caused a cycle, but not including the latch, return null
		else if(Arrays.asList(stack.toArray()).indexOf(currNode.name) != stack.size() - 1){
			return null;
		}
		//else
		else{
			// we need to look at all children, and try to get the cycles from there
			//foreach child
			for(int childInt : currNode.children){
				// find the child node in our list of nodes
				Node child = findNode(childInt);
				// getCycleOfNodes(child, endNode)
				Stack<Integer> childStack = getCycleOfNodes(child, endNode, stack);
				// if return is non null, we are fine, return the stack returned from child
				if(childStack != null) return childStack;
				// else repeat/continue
			}

			return null;
		}
	}

	private Node findNode(int name) {		
		for(Node node: nodes){
			if(node.name == name) return node;
		}
		
		return null;
	}
}

