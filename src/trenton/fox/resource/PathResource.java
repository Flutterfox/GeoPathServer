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

import trenton.fox.CreatePath;
import trenton.fox.OracleHelper;
import trenton.fox.model.Location;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Path("path")
public class PathResource {
	private final static String USER_ID = "userID";
	private final static String LABEL = "label";
	private final static String DESCRIPTION = "description";
	private final static String PATH_ID = "pathID";
	
	private trenton.fox.model.Path path = new trenton.fox.model.Path("userID", "General Location", "This is a general location description", "pathID");
    
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
    public trenton.fox.model.Path getSamplePath() { 
        return path;
    }
    
    @GET
    @Path("/returnbyuserid")
    @Produces(MediaType.APPLICATION_JSON)
    public trenton.fox.model.Path getPaths(String userID) { 
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
    
    @PUT
    @Path("/insertPath")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
	public String doPut(trenton.fox.model.Path path) {
    	OracleHelper oh = new OracleHelper();
    	try {
			oh.insertPath(path);
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
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public trenton.fox.model.Path doPost(List<Location> locations) throws ServletException, IOException {
		CreatePath cp = new CreatePath(locations);

    	return cp.GetPath();
    }
}
