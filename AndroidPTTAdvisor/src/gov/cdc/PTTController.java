package gov.cdc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.content.Context;
import java.io.IOException;
import java.io.InputStream;

public class PTTController {
	
	HashMap<Integer, PTTNode> nodes;
	PTTNode currentNode;
	int historyPosition;
	ArrayList<PTTHistoryItem> history;
	private Context context;
	

	public PTTController(Context c) {
		super();
		this.historyPosition = 0;
		this.context = c;
		history = new ArrayList<PTTHistoryItem>();
		
		
		this.nodes = parseJson();
		
		this.currentNode = nodes.get(0);
	}
	
	/**
	 * Read in the local DTNode.json file, parse it into PTTNode objects, and return them all in a HashMap.
	 * @return a HashMap of the PTTNodes defined in the /assets/DTNode.json
	 */
    private HashMap<Integer, PTTNode> parseJson() {
    	/**
    	 * Read in the local JSON file from assets/DTNode.json and convert it to a string.
    	 */
    	String jsonString = "";
        InputStream file;
        try {
            file = this.context.getAssets().open("DTNode.json");
            byte[] data = new byte[file.available()];
            file.read(data);
            file.close();
            jsonString = new String(data);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } 
        
    	HashMap<Integer, PTTNode> pttnodes = new HashMap<Integer, PTTNode>();
    	try {
    		JSONObject jsonObj = new JSONObject(jsonString);
    		JSONArray nodes = jsonObj.getJSONArray("nodes");
    		for (int i = 0; i < nodes.length(); i++) {
    			// extract node i from the array of nodes
    			JSONObject node = nodes.getJSONObject(i);
    		
    			// Pull out the strings
    			int id = node.getInt("id");
    			String question = node.getString("question");
    			// Add the node id to the question string for editing/debugging purposes
    			// REMOVE THIS LATER
    			//question = "Node " + Integer.toString(id) + ":\n\n" + question;
    			String image = node.getString("image");
    		
    			//  Pull out the array of answers
    			JSONArray answers = node.getJSONArray("answers");

    			// for each answer, get the string/id pair and create an ArrayList of answers
    			ArrayList<PTTAnswer> pttAnswers = new ArrayList<PTTAnswer>();
    			for (int j = 0; j < answers.length(); j++) {
    				JSONObject answer = answers.getJSONObject(j);
    				int nodeId = answer.getInt("nodeId");
    				String answerStr = answer.getString("answer");
    			
    				// create a new pttAnswer and add it to the list
    				PTTAnswer pttAnswer = new PTTAnswer(nodeId, answerStr);
    				pttAnswers.add(pttAnswer);
    			}
    		
    			// Ditto for the footnotes
    			JSONArray fnJson = node.getJSONArray("footnotes");
    			ArrayList<String> footnotes = new ArrayList<String>();
    			for (int j = 0; j < fnJson.length(); j++) {
    				footnotes.add(fnJson.getString(j));
    			}
    		
    			// create the PTTNode and add it to the ArrayList of them
    			PTTNode pttNode = new PTTNode(id, question, pttAnswers, image, footnotes);
    			Log.d("JSON Parser", "Made node: " + id);
    			pttnodes.put(id, pttNode);
    		} // done with nodes loop
    	} catch (JSONException e) {
    		Log.d("JSON error: ", e.getMessage());
    	}
    	Log.d("JSON", "Finished parsing");
    	return pttnodes;
    }
	

	//this method returns the question for a particular node
	public String getQuestionForNodeNumber(int nodeNumber) {
		PTTNode n = this.nodes.get(nodeNumber);
		return n.getQuestion();
	}
	
	//returns the header image for a node
	public String getHeaderImageForNodeNumber(int nodeNumber) {
		PTTNode n = this.nodes.get(nodeNumber);
		return n.getPathToHeaderImage();
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
		Log.d("SETCURRNODE","setting current node to: " + Integer.toString(n));
		this.currentNode = this.nodes.get(n);
		Log.d("SETCURRNODE","curent node just set to: " + Integer.toString(this.currentNode.getId()));
	}
	
	public void logCurrentNodeNumber() {
		Log.d("LOGCURRENTNODENUMBER",Integer.toString(this.currentNode.getId()));
	}

	
	
}
