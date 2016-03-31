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
	  		new String();
			cp.setPathID(String.valueOf(new Date().getTime()) + cp.getUserID());
			savePath();
		}
	}

	public CustomPath GetPath() {
		return cp;
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
	


}
