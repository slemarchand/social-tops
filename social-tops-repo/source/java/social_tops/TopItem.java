package social_tops;

import org.alfresco.service.cmr.repository.NodeRef;

public class TopItem {
	
	public TopItem(int position, int score, NodeRef node) {
		super();
		this.position = position;
		this.score = score;
		this.node = node;
	}
	
	public TopItem(int position, int score, String node) {
		this(position, score, new NodeRef(node));
	}
	
	private int position;
	private int score;
	private NodeRef node;
	
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public NodeRef getNode() {
		return node;
	}
	public void setNode(NodeRef node) {
		this.node = node;
	}	
}
