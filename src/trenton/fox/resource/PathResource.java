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
import java.util.Date;
import java.util.List;

@Path("path")
public class PathResource {
	private final static String USER_ID = "userID";
	private final static String LABEL = "label";
	private final static String DESCRIPTION = "description";
	private final static String PATH_ID = "pathID";
	private final static String TYPE = "type";

	private trenton.fox.model.CustomPath path = new trenton.fox.model.CustomPath("userID", "General Location",
			"This is a general location description", "pathID", "walking");

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
	public List<CustomLocation> doUpdate(CustomPath path) {
		System.out.println("calculating path: " + path.getLabel());
		
		Analysis analysis = new Analysis();
		analysis.setup(path);

		return analysis.run();
	}

	// Use data from the client source to create a new Location object, returned
	// in JSON format.
	@POST
	@Path("create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public CustomPath doPost(List<CustomLocation> locations) throws ServletException, IOException {
		System.out.println("Recieved path: " + path.getLabel());
		CreatePath createPath = new CreatePath(locations);

		return createPath.GetPath();
	}
}
