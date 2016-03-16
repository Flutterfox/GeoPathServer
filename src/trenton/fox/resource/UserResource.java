package trenton.fox.resource;

import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import trenton.fox.OracleHelper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Path("user")
public class UserResource {
       
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
    @Produces(MediaType.TEXT_PLAIN)
    public String getSamplePath() { 
        return "SampleUser";
    }

    @POST
    @Path("/insert")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
	public String doPut(String userID) {
    	OracleHelper oh = new OracleHelper();
    	try {
			oh.insertUser(userID);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return "failure";
		} catch (SQLException e) {
			e.printStackTrace();
			return "failure";
		}
		return "success";
	}

}
