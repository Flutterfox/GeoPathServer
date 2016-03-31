package trenton.fox.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomPath {
	private String userID, label, description, pathID;
	
	public CustomPath() {
		userID = "";
		label = "";
		description = "";
		pathID = "";
	}
	
	public CustomPath(String userID, String label, String description, String pathID) {
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
		if (label == "") {
			return "null";
		}
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getDescription() {
		if (description == "") {
			return "null";
		}
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
