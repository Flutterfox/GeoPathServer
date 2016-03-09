package trenton.fox;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

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
    @Path("/{userID}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
	protected String doPut(@PathParam("userID") String userID) {
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
	
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 
        try {
            int length = request.getContentLength();
            byte[] input = new byte[length];
            ServletInputStream sin = request.getInputStream();
            int c, count = 0 ;
            while ((c = sin.read(input, count, input.length-count)) != -1) {
                count +=c;
            }
            sin.close();
 
            String recievedString = new String(input);
            response.setStatus(HttpServletResponse.SC_OK);
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
 
            Integer doubledValue = Integer.parseInt(recievedString) * 2;
 
            //Send response
            writer.write(doubledValue.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            try{
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(e.getMessage());
                response.getWriter().close();
            } catch (IOException ioe) {
            }
        }   
    }

}
