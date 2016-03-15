package trenton.fox;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import trenton.fox.model.Location;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GeoPathServlet
 */
@Path("user")
public class GeoPathServlet {
       
	// The @Context annotation allows us to have certain contextual objects
    // injected into this class.
    @Context
    UriInfo uriInfo;
 
    // Another "injected" object. This allows us to use the information that's
    // part of any incoming request.
    @Context
    Request request;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GeoPathServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @PUT
    @Path("/insertUser")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
	protected String doPut(String userID) {
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
