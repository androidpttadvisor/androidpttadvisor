package gov.cdc;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import android.content.Context;
import android.util.SparseArray;

public class PTTController {

	// a mapping of nodeIds to PTTNode objects
	SparseArray<PTTNode> nodes;

	// the current node being displayed 
	PTTNode currentNode;
	
	int historyPosition;
	ArrayList<PTTHistoryItem> history;
	private Context context;
	

	public PTTController(Context c) {
		super();
		this.historyPosition = 0;
		this.context = c;
		history = new ArrayList<PTTHistoryItem>();
		
		JsonHandler jsonHandler = new JsonHandler(context);
		this.nodes = jsonHandler.getNodesFromJson();
		this.currentNode = nodes.get(0);
	}
	
	//this method returns the question for a particular node
	public String getQuestionForNodeNumber(int nodeNumber) {
		PTTNode n = this.nodes.get(nodeNumber);
		return n.getQuestion();
	}
	
	//returns the header image for a node
	public String getHeaderImageForNodeNumber(int nodeNumber) {
		PTTNode n = this.nodes.get(nodeNumber);
		return n.getHeaderImage();
	}
	
	
	//this method returns the text and targets of each answer in a node
	public ArrayList<PTTAnswer> getAnswersForNodeNumber(int nodeNumber) {
		ArrayList<PTTAnswer> answers = new ArrayList<PTTAnswer>();
		
		//TODO: iterate through all answers
		
		PTTNode n = this.nodes.get(nodeNumber);
		for (int i = 0; i < n.answers.size(); i++) {
			answers.add(n.answers.get(i));
/*
			answers.add(n.answers.get(0));
			answers.add(n.answers.get(1));
			*/
		}
		
		return answers;
	}
	
	
	// Stores a history item after an answer is selected.
	// history is an array of PTTHistoryItem's
	public void storeHistoryItem(PTTNode node, PTTAnswer answerChosen) {
		PTTHistoryItem historyItem = new PTTHistoryItem(node,answerChosen);
		history.add(historyItem);
		Log.d("HISTORY", "YAY, new history item! Node:" + history.get(history.size()-1).getAnswerChosen().getNodeId() + "Answer: " + history.get(history.size()-1).getAnswerChosen().answer);
	}
	
	public void truncateHistory(int positionToTruncateTo) {
		Log.d("CLEAR POS",Integer.toString(positionToTruncateTo));
		Log.d("HIST LENGTH BEF",Integer.toString(history.size()));
		for (int i = history.size()-1; i >= positionToTruncateTo; i--) {
			history.remove(i);
		}
		Log.d("HIST LENGTH AFT",Integer.toString(history.size()));
	}
	
	
	public void logHistoryItems() {
		for (int i=0; i < history.size(); i++) {
			Log.d("HISTORY_ITEM", "---");
			Log.d("HISTORY_ITEM", "Question " + (i+1) + ": " + history.get(i).getNode().getQuestion());
			Log.d("HISTORY_ITEM", "User Answered: " + history.get(i).getAnswerChosen().answer);
			Log.d("HISTORY_ITEM", "---");
		}
	}
	
	
	
	
	
	public SparseArray<PTTNode> getNodes() {
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
		Log.d("SETCURRNODE","setting current node to: " + Integer.toString(n));
		this.currentNode = this.nodes.get(n);
		Log.d("SETCURRNODE","curent node just set to: " + Integer.toString(this.currentNode.getId()));
	}
	
	public void logCurrentNodeNumber() {
		Log.d("LOGCURRENTNODENUMBER",Integer.toString(this.currentNode.getId()));
	}

	
	
}
