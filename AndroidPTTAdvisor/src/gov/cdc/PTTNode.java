package gov.cdc;

import java.util.ArrayList;

/**
 * An object representing a node in the decision tree
 * @author Will
 *
 */

public class PTTNode {
	
	// the node's id.  Unique, and id = 0 indicates root node.
	public int id;
	
	// The text of the question to be displayed for this node.
	public String question;
	
	// The possible answers for this node.  Currently only supports 0, 1, or 2 answers
	// (that's how many answer buttons there can be in the MainView
	public ArrayList<PTTAnswer> answers;
	
	// Image to display at the top of the screen for this node
	public String headerImage;
	
	// List of footnotes associated with this node.
	public ArrayList<String> footnotes;

	/**
	 * Create a PTTNode
	 * @param id nodeId.  Must be unique.  0 indicates root node.
	 * @param question Question text to display for this node.
	 * @param answers ArrayList of possible answers/buttons for this node.
	 * @param headerImage name of image to display as a header for this node.
	 * @param footnotes ArrayList of footnotes for this node.
	 */
	public PTTNode(int id, String question, ArrayList<PTTAnswer> answers, String headerImage,
			ArrayList<String> footnotes) {
		super();
		this.id = id;
		this.question = question;
		this.answers = answers;
		this.headerImage = headerImage;
		this.footnotes = footnotes;
	}

	//Autogenerated Getters and Setters.  might not need some (or all) of setters.
	
	public String getHeaderImage() {
		return headerImage;
	}

	public void setHeaderImage(String pathToHeaderImage) {
		this.headerImage = pathToHeaderImage;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public ArrayList<PTTAnswer> getAnswers() {
		return answers;
	}
	public void setAnswers(ArrayList<PTTAnswer> answers) {
		this.answers = answers;
	}
	public ArrayList<String> getFootnotes() {
		return footnotes;
	}
	public void setFootnotes(ArrayList<String> footnotes) {
		this.footnotes = footnotes;
	}

}
