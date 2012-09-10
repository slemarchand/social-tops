package social_tops.service;

import org.alfresco.service.cmr.repository.NodeRef;

public class TopItem {
	
	private int position;
	private Score score = new Score();
	private NodeRef node;
	
	public TopItem(int position, int totalScore, NodeRef node) {
		super();
		this.position = position;
		this.score.setTotalScore(totalScore);
		this.node = node;
	}

	public TopItem(int position, int totalScore, String node) {
		this(position, totalScore, new NodeRef(node));
	}


	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public Score getScore() {
		return score;
	}

	public void setScore(Score score) {
		this.score = score;
	}

	public NodeRef getNode() {
		return node;
	}

	public void setNode(NodeRef node) {
		this.node = node;
	}
}
