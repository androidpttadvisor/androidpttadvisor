package us.paulbrown;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.util.Log;

public class PTTController {
	
	HashMap<Integer, PTTNode> nodes;
	PTTNode currentNode;
	int historyPosition;
	

	public PTTController() {
		super();
		this.historyPosition = 0;

		//filler code that creates some nodes.
		//TODO: abstract this out into another init method.

        //Node 0
        String node0Question = "Does the patient have prolonged PTT and normal PT?";

		PTTAnswer node0answer0 = new PTTAnswer(1, "YAY");
		PTTAnswer node0answer1 = new PTTAnswer(2, "NAY");
		
		ArrayList<PTTAnswer> answers0 = new ArrayList<PTTAnswer>();
		answers0.add(node0answer0);
		answers0.add(node0answer1);
        //End Node 0

		//Node 1
        String node1Question = "Is the patient older than 6 months?";

		PTTAnswer node1answer0 = new PTTAnswer(3, "Yes");
		PTTAnswer node1answer1 = new PTTAnswer(4, "No");
		
		ArrayList<PTTAnswer> answers1 = new ArrayList<PTTAnswer>();
		answers1.add(node1answer0);
		answers1.add(node1answer1);
        //End Node 1

        //Node 2
        String node2Question = "There is currently no algorithm for a patient meeting this criteria.";

        //TODO: Account for empty answers (i.e. dead ends on the node tree) in the PTTNode contructor
        ArrayList<PTTAnswer> answers2 = new ArrayList<PTTAnswer>();
        //End Node 2
		
		
		
		
		ArrayList<String> footnotes = new ArrayList<String>();
		footnotes.add("none");
		
		PTTNode node0 = new PTTNode(0,node0Question,answers0,footnotes);
		PTTNode node1 = new PTTNode(1,node1Question,answers1,footnotes);
        PTTNode node2 = new PTTNode(2,node2Question,answers2,footnotes);
		
		//end filler code to create nodes
		
		
		//creating the map to hold nodes
		this.nodes = new HashMap<Integer, PTTNode>();
		this.nodes.put(node0.getId(),node0);
		this.nodes.put(node1.getId(),node1);
        this.nodes.put(node2.getId(),node2);
		
		
		this.currentNode = node0;
	}
	
	
	
	

	//this method returns the question for a particular node
	public String getQuestionForNodeNumber(int nodeNumber) {
		PTTNode n = this.nodes.get(nodeNumber);
		return n.getQuestion();
	}
	
	
	//this method returns the text and targets of each answer in a node
	public ArrayList<PTTAnswer> getAnswersForNodeNumber(int nodeNumber) {
		ArrayList<PTTAnswer> answers = new ArrayList<PTTAnswer>();
		
		//TODO: iterate through all answers
		
		PTTNode n = this.nodes.get(nodeNumber);
		answers.add(n.answers.get(0));
		answers.add(n.answers.get(1));
		
		
		return answers;
	}
	
	
	
	
	
	public HashMap<Integer, PTTNode> getNodes() {
		return nodes;
	}




	public int getHistoryPosition() {
		return historyPosition;
	}

	public void setHistoryPosition(int historyPosition) {
		this.historyPosition = historyPosition;
	}
	
	public PTTNode getCurrentNode() {
		return currentNode;
	}





	public void setCurrentNode(int n) {
		this.currentNode = this.nodes.get(n);;
	}

}
