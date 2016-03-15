package trenton.fox.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Path {
	private String userID, label, description, pathID;
	
	public Path() {
		userID = "";
		label = "";
		description = "";
		pathID = "";
	}
	
	public Path(String userID, String label, String description, String pathID) {
		this.userID = userID;
		this.label = label;
		this.description = description;
		this.pathID = pathID;
	}
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getPathID() {
		return pathID;
	}
	public void setPathID(String pathID) {
		this.pathID = pathID;
	}
}
