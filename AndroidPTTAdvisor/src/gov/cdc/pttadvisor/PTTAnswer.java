package gov.cdc.pttadvisor;

/**
 * Very basic object, representing an answer to a node's question.
 * @author Will
 *
 */
public class PTTAnswer {
	
	// The nodeID of the node that this answer points to.  When this answer is picked,
	// the nodeId is the next node to show.
	public int nodeId;

	// The answer's string.  Usually "Yes", "No", or "Continue" but could be other things. 
	public String answer;

	/**
	 * Construct a PTTAnswer object.
	 * @param nodeId the node this answer navigates to
	 * @param answer the text on the button for this answer
	 */
	public PTTAnswer(int nodeId, String answer) {
		super();
		this.nodeId = nodeId;
		this.answer = answer;
	}

	//autogen getters and setters
	
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
