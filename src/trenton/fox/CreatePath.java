package trenton.fox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import trenton.fox.model.CustomLocation;
import trenton.fox.model.CustomPath;

public class CreatePath {
	private List<CustomLocation> locations;
	private CustomPath cp = new CustomPath();
	
	public CreatePath(List<CustomLocation> locations){
		this.locations = locations;
		
		if (locations.size() > 1) {
			//Create path object
			cp.setUserID(locations.get(0).getUserID());
			setType();
	  		new String();
			cp.setPathID(String.valueOf(new Date().getTime()) + cp.getUserID());
			savePath();
			saveLocations();
		}
	}

	public CustomPath GetPath() {
		return cp;
	}
	
	private void setType() {
		//gets recording time in seconds
		long recordingTime = (locations.get(locations.size()-1).getTimestamp().getTime() - locations.get(0).getTimestamp().getTime())/1000;
		double travelDistance = getDistance(locations.get(locations.size()-1), locations.get(0));
		
		double speed = travelDistance / recordingTime;
		
		if (speed < 0.001111) {
			cp.setType("walking");
		} else if (speed < 0.006944) {
			cp.setType("biking");
		} else {
			cp.setType("vehicle");
		}
	}
	
	private double getDistance(CustomLocation current, CustomLocation next) {
		double lat1 = Math.toRadians(current.getLat());
		double lat2 = Math.toRadians(next.getLat());
		double dLat = Math.toRadians(next.getLat() - current.getLat());
		double dLon = Math.toRadians(next.getLon() - current.getLon());
		
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
		double c  = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		double d = 3956 * c; 
		
		return d;
	}
	
	private void savePath() {
		OracleHelper oh = new OracleHelper();
		try {
			oh.insertPath(cp);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void saveLocations() {
		OracleHelper oh = new OracleHelper();
		System.out.println(locations.size());
		for (CustomLocation cl : locations) {
			cl.setPathID(cp.getPathID());
			try {
				oh.insertLoc(cl);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


}
