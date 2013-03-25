package gov.cdc;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.widget.Toast;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.util.SparseArray;

/**
 * A class to handle most JSON interactions
 * @author wm122
 *
 */

public class JsonHandler {

	private String FILENAME = "DTNode.json";
	private Context context;
	
	public JsonHandler (Context context) {
		this.context = context;
		
		/* if the JSON file is not already in the app's local storage, copy it over there from /assets/ */
		if (!jsonInStorage()) {
			if (moveJsonToStorage()) {
				Toast toast = Toast.makeText(context, "JSON moved to local storage", Toast.LENGTH_SHORT);
				toast.show();
			} else {
				Toast toast = Toast.makeText(context, "Couldn't move JSON to local storage!", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
		new JsonUpdaterTask(context).execute();
	}
	
	/**
	 * Check to see if the DTNode.json file has been moved from /assets/ to the app's local storage
	 * @return whether or not DTNode.json is in local storage and has non-zero size
	 */
	private boolean jsonInStorage() {
		try {
			FileInputStream fs = context.openFileInput(FILENAME);
			int size = fs.available();
			fs.close();
			if (size > 0) {
				return true;
			} else {
				fs.close();
				return false;
			}
		} catch (IOException e) {
			return false;
		}
	}
	
	/**
	 * Copy the DTNode.json from /assets/ to the app's local storage.  This should really only ever
	 * happen one time:  on first running of the app after install.
	 * @return true on success, false on failure
	 */
	private boolean moveJsonToStorage() {
		InputStream inFile;
		FileOutputStream outFile;
        try {
            inFile = this.context.getAssets().open("DTNode.json");
            outFile = this.context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            byte[] data = new byte[inFile.available()];
            inFile.read(data);
            outFile.write(data);
            inFile.close();
            return true;
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return false;
        }
	}
	
	/**
	 * Get the decision tree/algorithm JSON file and return it as a String.  It will try to return the 
	 * copy from the app's local storage, but if that fails, it will return the one from /assets/
	 * @return the Decision Tree JSON string
	 */
	public String getJsonString() {
		/* try to use the one from "local storage" first */
		String jsonString = getLocalJsonString();
		
		/* if that failed, fall-back to the one from /assets/ */
		if (jsonString == null) {
			jsonString = getAssetsJsonString();
		}
		
		return jsonString;
	}
	
	/**
	 * Get the JSON string from local/app storage, or return null
	 * @return the JSON string from local/app storage, or null on failure
	 */
	public String getLocalJsonString() {
		InputStream inFile;
		try {
    		inFile = context.openFileInput(FILENAME);
    		byte[] data = new byte[inFile.available()];
            inFile.read(data);
    		inFile.close();
    		return new String(data);
    	} catch (IOException e) {
            Log.d("JSON Handler", "Couldn't read DTNode.json from local storage!");
    		e.printStackTrace();
    		return null;
    	}		
	}
	
	/**
	 * Get JSON String from /assets/
	 * @return the JSON string from /assets/
	 */
	public String getAssetsJsonString() {
		InputStream inFile;
        try {
            inFile = this.context.getAssets().open("DTNode.json");
            byte[] data = new byte[inFile.available()];
            inFile.read(data);
            inFile.close();
            return new String(data);
        } catch (IOException e) {
        	Log.d("JSON Handler", "Couldn't read /assets/DTNode.json!");
            e.printStackTrace();
            return null;
        }
	}

	/**
	 * Get the JSON String and parse it into a PTT Node tree for PTTController
	 * @return SparseArray of PTT nodes for PTT Controller's Decision Tree
	 */
	public SparseArray<PTTNode> getNodesFromJson() {
		String jsonString = getJsonString();
    	SparseArray<PTTNode> pttnodes = new SparseArray<PTTNode>();
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
    			question = "Node " + Integer.toString(id) + ":\n\n" + question;
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
	
}
