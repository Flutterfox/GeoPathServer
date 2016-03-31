package trenton.fox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import trenton.fox.model.CustomLocation;

public class Analysis implements Runnable {
	private List<CustomLocation> locations;
	private String pathID;
	
	@Override
	public void run() {
		cleanLocations();
		sortByDate();
		//fixFolds();
		saveLocations();
	}
	
	public void setup (List<CustomLocation> locations, String pathID) {
		this.locations = locations;
		this.pathID = pathID;
	}

	private void sortByDate(){
		Collections.sort(locations);
		int i = 0;
		
		for (CustomLocation cl : locations) {
			cl.setPosition(i++);
		}
	}
	
	private void fixFolds() {
		for (int i = locations.size()-1; i > 2; i--) {
			for (int j = i-1; j > 1; j--) {
				if (checkIfCloser(locations.get(i), locations.get(j), locations.get(j-1))) {
					//changes position of location i to one lower than the checked position
					locations.get(i).setPosition(locations.get(j-1).getPosition() - 1);
				}
			}
			reorderByPosition();
		}
	}
	
	//Checks if the distance from current to check is smaller than then distance from current to next
	private boolean checkIfCloser(CustomLocation current, CustomLocation next, CustomLocation check) {
		double nextLatDistance = Math.abs(current.getLat() - next.getLat());
		double nextLonDistance = Math.abs(current.getLon() - next.getLon());
		
		double checkLatDistance = Math.abs(current.getLat() - check.getLat());
		double checkLonDistance = Math.abs(current.getLon() - check.getLon());
		
		if ((nextLatDistance > checkLatDistance) && (nextLonDistance > checkLonDistance)) {
			return true;
		} else {
			return false;
		}
	}
	
	private void reorderByPosition() {
		locations.sort(Comparator.comparing(CustomLocation::getPosition));
	}
	
	private void cleanLocations() {
		//removes duplicates
		List<Integer> removeThese = new ArrayList<Integer>();
		for (int i = 0; i < locations.size()-1; i++) {
			if (locations.get(i).getLocID().equals(locations.get(i + 1).getLocID())){
				removeThese.add(i);
			}
		}
		
		for (Integer index : removeThese) {
			locations.remove(index);
		}
	}
	
	private void saveLocations() {
		OracleHelper oh = new OracleHelper();
		for (CustomLocation cl : locations) {
			cl.setPathID(pathID);
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
