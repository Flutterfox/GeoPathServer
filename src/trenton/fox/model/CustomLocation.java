package trenton.fox.model;

import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

@XmlRootElement
public class CustomLocation implements Comparable<CustomLocation> {
	private String locID, userID, type, label, description, pathID;
	private double lat, lon;
	private Date timestamp;
	private int position;

	public CustomLocation() {
		locID = "";
		userID = "";
		type = "";
		label = "";
		description = "";
		pathID = "";
		lat = -1D;
		lon = -1D;
		timestamp = new Date();
		setPosition(-1);
	}

	public CustomLocation(String locID, Double lat, Double lon, String userID, Date timestamp, String type, String label,
			String description, String pathID, int position) {
		this.locID = locID;
		this.lat = lat;
		this.lon = lon;
		this.userID = userID;
		this.timestamp = timestamp;
		this.type = type;
		this.label = label;
		this.description = description;
		this.pathID = pathID;
		this.position = position;
	}

	public String getLocID() {
		return locID;
	}

	public void setLocID(String locID) {
		this.locID = locID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
		if (label == "") {
			return "null";
		}
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPathID() {
		if (pathID == "") {
			return "null";
		}
		return pathID;
	}

	public void setPathID(String pathID) {
		this.pathID = pathID;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	@JsonDeserialize(using=CustomDateDeserializer.class)
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public int compareTo(CustomLocation o) {
		return getTimestamp().compareTo(o.getTimestamp());
	}

}
