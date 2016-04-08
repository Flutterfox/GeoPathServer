package trenton.fox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import trenton.fox.model.CustomLocation;
import trenton.fox.model.CustomPath;

public class Analysis {
	private List<CustomLocation> locations;

	public List<CustomLocation> run() {
		//cleanLocations();
		//sortByDate();
		sortList();
		new Thread(() -> saveLocations()).start();
		return locations;
	}

	public void setup(CustomPath path) {
		// add wait to PathResource
		// change toast on android suitably

		OracleHelper oh = new OracleHelper();
		try {
			oh.updatePath(path);
			locations = oh.returnLocations(path.getPathID());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void sortByDate() {
		Collections.sort(locations);
		assignPositionbyIndex();
	}
	
	private void sortList() {
		CustomLocation corner = getSouthwestLocation();
		
		for (CustomLocation cl : locations) {
			cl.setDistance(getDistance(corner,cl));
		}

		sortByDistance();
		assignPositionbyIndex();
	}
	
	private void assignPositionbyIndex() {
		int i = 1;

		for (CustomLocation cl : locations) {
			cl.setPosition(i++);
		}
	}
	
	private CustomLocation getSouthwestLocation() {
		CustomLocation corner = locations.get(0);
		CustomLocation southPole = new CustomLocation();
		southPole.setLat(-90);
		southPole.setLon(-180);
		double winningDistance = getDistance(southPole, corner);
		
		for (CustomLocation cl : locations) {
			double check = getDistance(southPole, cl);
			if (winningDistance > check) {
				winningDistance = check;
				corner = cl;
			}
		}
		
		return corner;
	}
	
	//Returns the haversine distance
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
	
	private void sortByDistance() {
		locations.sort(Comparator.comparing(CustomLocation::getDistance));
	}

	private void cleanLocations() {
		// removes duplicates
		List<CustomLocation> newList = new ArrayList<CustomLocation>();
		for (int i = 0; i < locations.size() - 1; i++) {
			if (!(locations.get(i).getLat() == locations.get(i+1).getLat())
					&& !(locations.get(i).getLon() == locations.get(i+1).getLon())) {
				newList.add(locations.get(i));
			}
		}

		locations.clear();
		locations = newList;
	}

	private void saveLocations() {
		OracleHelper oh = new OracleHelper();
		System.out.println("New Path: " + new Date());
		for (CustomLocation cl : locations) {
			try {
				oh.updateLoc(cl);
				System.out.println("distance: " + cl.getDistance());
				System.out.println(cl.getLocID() + " " + cl.getPosition());
				System.out.println(cl.getLat() + ", " + cl.getLon());
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
