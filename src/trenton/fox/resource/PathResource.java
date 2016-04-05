package trenton.fox.resource;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.servlet.ServletException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Request;

import trenton.fox.Analysis;
import trenton.fox.CreatePath;
import trenton.fox.OracleHelper;
import trenton.fox.model.CustomLocation;
import trenton.fox.model.CustomPath;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Path("path")
public class PathResource {
	private final static String USER_ID = "userID";
	private final static String LABEL = "label";
	private final static String DESCRIPTION = "description";
	private final static String PATH_ID = "pathID";
	
	private trenton.fox.model.CustomPath path = new trenton.fox.model.CustomPath("userID", "General Location", "This is a general location description", "pathID");
    
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
    @Path("sample")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomPath getSamplePath() { 
        return path;
    }
    
    @POST
    @Path("returnbyuserid")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomPath getPaths(String userID) { 
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
    	return path;
    }
    
    @POST
    @Path("update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public List<CustomLocation> doUpdate(trenton.fox.model.CustomPath path) {
    	List<CustomLocation> locList = null;
    	
    	OracleHelper oh = new OracleHelper();
    	try {
			oh.updatePath(path);
			locList = oh.returnLocations(path.getPathID());
			
			System.out.println("New Path");
			for (CustomLocation cl : locList) {
				System.out.println(cl.getLocID() + " " + cl.getPosition());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return locList;
	}
         
    // Use data from the client source to create a new Location object, returned in JSON format.  
    @POST
    @Path("create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public CustomPath doPost(List<CustomLocation> locations) throws ServletException, IOException {
		CreatePath createPath = new CreatePath(locations);
		CustomPath cp = createPath.GetPath();
		
		Analysis analysis = new Analysis();
		analysis.setup(locations, cp.getPathID());
		new Thread(analysis).start();
		
    	return cp;
    }
}
