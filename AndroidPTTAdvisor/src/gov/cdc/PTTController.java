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
	private Context context;

	public PTTController(Context c) {
		super();
		this.historyPosition = 0;
		this.context = c;
	/*	
		//filler code that creates some nodes.
		//TODO: abstract this out into another init method.

        //Node 0
        String node0Question = "Does the patient have prolonged PTT and normal PT?";
        String node0HeaderImage = "to_begin_header";

		PTTAnswer node0answer0 = new PTTAnswer(1, "YAY");
		PTTAnswer node0answer1 = new PTTAnswer(2, "NAY");
		
		ArrayList<PTTAnswer> answers0 = new ArrayList<PTTAnswer>();
		answers0.add(node0answer0);
		answers0.add(node0answer1);
        //End Node 0

		//Node 1
        String node1Question = "Is the patient older than 6 months?";
        String node1HeaderImage = "eval_guide_header";
        
		PTTAnswer node1answer0 = new PTTAnswer(3, "Yes");
		PTTAnswer node1answer1 = new PTTAnswer(4, "No");
		
		ArrayList<PTTAnswer> answers1 = new ArrayList<PTTAnswer>();
		answers1.add(node1answer0);
		answers1.add(node1answer1);
        //End Node 1

        //Node 2
        String node2Question = "There is currently no algorithm for a patient meeting this criteria.";
        String node2HeaderImage = "recommendation_header";

        //TODO: Account for empty answers (i.e. dead ends on the node tree) in the PTTNode contructor
        ArrayList<PTTAnswer> answers2 = new ArrayList<PTTAnswer>();
        //End Node 2
		
		
		
		
		ArrayList<String> footnotes = new ArrayList<String>();
		footnotes.add("none");
		
		PTTNode node0 = new PTTNode(0,node0Question,answers0,node0HeaderImage,footnotes);
		PTTNode node1 = new PTTNode(1,node1Question,answers1,node1HeaderImage,footnotes);
        PTTNode node2 = new PTTNode(2,node2Question,answers2,node2HeaderImage,footnotes);
		
		//end filler code to create nodes

		
		//creating the map to hold nodes
		this.nodes = new HashMap<Integer, PTTNode>();
		this.nodes.put(node0.getId(),node0);
		this.nodes.put(node1.getId(),node1);
        this.nodes.put(node2.getId(),node2);
*/		
		this.nodes = parseJson();
		
		this.currentNode = nodes.get(0);
	}
	
	
    private HashMap<Integer, PTTNode> parseJson() {
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
