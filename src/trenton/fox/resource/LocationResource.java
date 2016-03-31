package trenton.fox.resource;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import trenton.fox.OracleHelper;
import trenton.fox.model.CustomLocation;

import javax.ws.rs.core.Request;

import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Path("location")
public class LocationResource {
	private final static String LOC_ID = "locID";
	private final static String USER_ID = "userID";
	private final static String TYPE = "type";
	private final static String LABEL = "label";
	private final static String DESCRIPTION = "description";
	private final static String PATH_ID = "pathID";
	private final static String LATITUDE = "lat";
	private final static String LONGITUDE = "lon";
	private final static String TIMESTAMP = "timestamp";
	private final static String POSITION = "position";
	
	private CustomLocation location = new CustomLocation("testID", 0D, 0D, "userID", new Date(),
			"General", "General Location", "This is a general location description", "pathID", -1);
    
    // The @Context annotation allows us to have certain contextual objects
    // injected into this class.
    @Context
    UriInfo uriInfo;
 
    // Another "injected" object. This allows us to use the information that's
    // part of any incoming request.
    @Context
    Request request;
     
    // Basic "is the service running" test
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String respondAsReady() {
        return "Demo service is ready!";
    }
 
    @GET
    @Path("/sample")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomLocation getSampleLocation() { 
        return location;
    }
    
    @POST
    @Path("/returnbyuserid")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomLocation getLocations(String userID) { 
        OracleHelper oh = new OracleHelper();
        try {
			oh.returnLocations("general", userID);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return location;
    }
    
    @PUT
    @Path("/insertLocation")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
	public String doPut(CustomLocation location) {
    	OracleHelper oh = new OracleHelper();
    	try {
			oh.insertLoc(location);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return "failure";
		} catch (SQLException e) {
			e.printStackTrace();
			return "failure";
		}
		return "success";
	}
         
    // Use data from the client source to create a new Location object, returned in JSON format.  
    @SuppressWarnings("deprecation")
	@POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public CustomLocation postLocation(
            MultivaluedMap<String, String> locationParams
            ) {
         
        String locID = locationParams.getFirst(LOC_ID);
        String lat = locationParams.getFirst(LATITUDE);
        String lon = locationParams.getFirst(LONGITUDE);
        String userID = locationParams.getFirst(USER_ID);
        String timestamp = locationParams.getFirst(TIMESTAMP);
        String type = locationParams.getFirst(TYPE);
        String label = locationParams.getFirst(LABEL);
        String description = locationParams.getFirst(DESCRIPTION);
        String pathID = locationParams.getFirst(PATH_ID);
        String position = locationParams.getFirst(POSITION);
        
         
        location.setLocID(locID);
        location.setLat(Double.parseDouble(lat));
        location.setLon(Double.parseDouble(lon));
        location.setUserID(userID);
        location.setTimestamp(new Date(timestamp));
        location.setType(type);
        location.setLabel(label);
        location.setDescription(description);
        location.setPathID(pathID);
        location.setPosition(Integer.parseInt(position));
         
        return location;
                         
    }
}
