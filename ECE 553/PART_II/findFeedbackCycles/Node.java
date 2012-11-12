import java.util.*;

public class Node {
	public int name;
	public ArrayList<Integer> children;
	public String type;
	
	public Node(int name, ArrayList<Integer> children, String type){
		this.name = name;
		this.children = children;
		this.type = type;
	}
}
