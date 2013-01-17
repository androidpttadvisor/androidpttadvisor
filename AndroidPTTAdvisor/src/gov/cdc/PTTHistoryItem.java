package gov.cdc;

public class PTTHistoryItem {
	
	PTTNode node;
	PTTAnswer answerChosen;
	
	
	
	public PTTHistoryItem(PTTNode node, PTTAnswer answerChosen) {
		super();
		this.node = node;
		this.answerChosen = answerChosen;
	}



	public PTTNode getNode() {
		return node;
	}



	public PTTAnswer getAnswerChosen() {
		return answerChosen;
	}
	
	
	
	
	
	

}
