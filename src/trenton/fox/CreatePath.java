package trenton.fox;

import java.sql.SQLException;
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
		
		cp.setUserID(locations.get(0).getUserID());
  		new String();
		cp.setPathID(String.valueOf(new Date().getTime()));
		
		sortByDate();
		saveLocations();
	}

	public CustomPath GetPath() {
		return cp;
	}
	
	private void sortByDate(){
		Collections.sort(locations);
	}
	
	private void saveLocations() {
		OracleHelper oh = new OracleHelper();
		for (CustomLocation cl : locations) {
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
