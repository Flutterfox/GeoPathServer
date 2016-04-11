package trenton.fox;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Types;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import oracle.jdbc.OracleTypes;
import trenton.fox.model.CustomLocation;
import trenton.fox.model.CustomPath;

public class OracleHelper {
	private Connection getConnection() throws ClassNotFoundException, SQLException{
		//String driver_class = "oracle.jdbc.driver.OracleDriver";
		//Class.forName (driver_class);
		DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "SYSTEM";
		String pass = "livestrong";

		Connection conn = DriverManager.getConnection(url,user,pass);
		
		//Connection connection = DriverManager.getConnection(url, user, pass);
		return conn;
		
	}
	
	public void insertLoc(CustomLocation location
			) throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();

		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.INSERTLOC(?,?,?,?,?,?,?,?,?,?)}");

			storedproc.setString(1, location.getLocID());
			storedproc.setDouble(2, location.getLat());
			storedproc.setDouble(3, location.getLon());
			storedproc.setString(4, location.getUserID());
			storedproc.setTimestamp(5, new java.sql.Timestamp(location.getTimestamp().getTime()));
			storedproc.setString(6, location.getType());
			storedproc.setString(7, location.getLabel());
			storedproc.setString(8, location.getDescription());
			storedproc.setString(9, location.getPathID());
			storedproc.setInt(10, location.getPosition());

			storedproc.execute();
		} catch (SQLException e) {
			updateLoc(location);
		}
	}
	
	public void updateLoc(CustomLocation location
			) throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();

		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.UPDATELOC(?,?,?,?,?,?,?,?,?,?)}");

			storedproc.setString(1, location.getLocID());
			storedproc.setDouble(2, location.getLat());
			storedproc.setDouble(3, location.getLon());
			storedproc.setString(4, location.getUserID());
			storedproc.setTimestamp(5, new java.sql.Timestamp(location.getTimestamp().getTime()));
			storedproc.setString(6, location.getType());
			storedproc.setString(7, location.getLabel());
			storedproc.setString(8, location.getDescription());
			storedproc.setString(9, location.getPathID());
			storedproc.setInt(10, location.getPosition());

			storedproc.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertUser(String userID) throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();

		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.INSERTUSER(?)}");

			storedproc.setString(1, userID);

			storedproc.execute();
		} catch (SQLIntegrityConstraintViolationException e) {
	    	//Does nothing!
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertPath(CustomPath path) throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();

		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.INSERTPATH(?,?,?,?,?)}");

			storedproc.setString(1, path.getPathID());
			storedproc.setString(2, path.getUserID());
			storedproc.setString(3, path.getLabel());
			storedproc.setString(4, path.getDescription());
			storedproc.setString(5, path.getType());
			
			storedproc.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updatePath(CustomPath path) throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();

		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.UPDATEPATH(?,?,?,?,?)}");

			storedproc.setString(1, path.getPathID());
			storedproc.setString(2, path.getUserID());
			storedproc.setString(3, path.getLabel());
			storedproc.setString(4, path.getDescription());
			storedproc.setString(5, path.getType());
			
			storedproc.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removeLoc(String locID) throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();

		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.REMOVE_LOC(?)}");

			storedproc.setString(1, locID);
			
			storedproc.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void removePath(String pathID) throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();

		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.REMOVE_PATH(?)}");

			storedproc.setString(1, pathID);
			
			storedproc.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public CustomLocation returnLocation(String locID) throws SQLException, ClassNotFoundException {
		Connection conn = getConnection();
		CustomLocation location = new CustomLocation();
		
		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.RETURNLOCATION(?,?,?,?,?,?,?,?,?,?,?)}");
			storedproc.registerOutParameter(2, Types.VARCHAR);
			storedproc.registerOutParameter(3, Types.NUMERIC);
			storedproc.registerOutParameter(4, Types.NUMERIC);
			storedproc.registerOutParameter(5, Types.VARCHAR);
			storedproc.registerOutParameter(6, Types.TIMESTAMP);
			storedproc.registerOutParameter(7, Types.VARCHAR);
			storedproc.registerOutParameter(8, Types.VARCHAR);
			storedproc.registerOutParameter(9, Types.VARCHAR);
			storedproc.registerOutParameter(10, Types.VARCHAR);
			storedproc.registerOutParameter(11, Types.INTEGER);
			
			storedproc.setString(1, locID);
			
			storedproc.execute();
			
			location.setLocID(storedproc.getString(2));
			location.setLat(storedproc.getDouble(3));
			location.setLon(storedproc.getDouble(4));
			location.setUserID(storedproc.getString(5));
			location.setTimestamp(storedproc.getDate(6));
			location.setType(storedproc.getString(7));
			location.setLabel(storedproc.getString(8));
			location.setDescription(storedproc.getString(9));
			location.setPathID(storedproc.getString(10));
			location.setPosition(storedproc.getInt(11));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return location;
	}

	public List<CustomLocation> returnLocations(String type, String userID) throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		CustomLocation location;
		List<CustomLocation> locList = new ArrayList<>();
		
		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.RETURNLOCATIONBYTYPEUSER(?,?,?,?,?,?,?,?,?,?,?,?)}");
			storedproc.registerOutParameter(3, Types.VARCHAR);
			storedproc.registerOutParameter(4, Types.NUMERIC);
			storedproc.registerOutParameter(5, Types.NUMERIC);
			storedproc.registerOutParameter(6, Types.VARCHAR);
			storedproc.registerOutParameter(7, Types.TIMESTAMP_WITH_TIMEZONE);
			storedproc.registerOutParameter(8, Types.VARCHAR);
			storedproc.registerOutParameter(9, Types.VARCHAR);
			storedproc.registerOutParameter(10, Types.VARCHAR);
			storedproc.registerOutParameter(11, Types.VARCHAR);
			storedproc.registerOutParameter(12, Types.INTEGER);
			
			storedproc.setString(1, type);
			storedproc.setString(2, userID);
			
		ResultSet rs = storedproc.executeQuery();
			
			while (rs.next()) {
				location = new CustomLocation();
				location.setLocID(storedproc.getString(2));
				location.setLat(storedproc.getDouble(3));
				location.setLon(storedproc.getDouble(4));
				location.setUserID(storedproc.getString(5));
				location.setTimestamp(storedproc.getTimestamp(6));
				location.setType(storedproc.getString(7));
				location.setLabel(storedproc.getString(8));
				location.setDescription(storedproc.getString(9));
				location.setPathID(storedproc.getString(10));
				location.setPosition(storedproc.getInt(11));
				locList.add(location);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locList;
	}
	
	public List<CustomLocation> returnLocations(String pathID) throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		CustomLocation location;
		List<CustomLocation> locList = new ArrayList<>();
		
		try {
			CallableStatement storedproc = conn.prepareCall("{call GEOPATH.RETURNLOCATIONBYTYPEPATH(?,?,?)}");
			
			storedproc.setString(1, "general");
			storedproc.setString(2, pathID);
			storedproc.registerOutParameter(3, OracleTypes.CURSOR);
			
			storedproc.execute();
			ResultSet rs = (ResultSet) storedproc.getObject(3);
			
			while (rs.next()) {
				location = new CustomLocation();
				location.setLocID(rs.getString(1));
				location.setLat(rs.getDouble(2));
				location.setLon(rs.getDouble(3));
				location.setUserID(rs.getString(4));
				location.setTimestamp(rs.getTimestamp(5));
				location.setType(rs.getString(6));
				location.setLabel(rs.getString(7));
				location.setDescription(rs.getString(8));
				location.setPathID(rs.getString(9));
				location.setPosition(rs.getInt(10));
				locList.add(location);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return locList;
	}
}
