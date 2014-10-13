package cs9322.coffee.barista.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import cs9322.coffee.barista.actions.*;


@WebServlet( name="MainServlet", displayName="Main Servlet", urlPatterns = {"/barista/*"}, loadOnStartup=1)
public class mainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Map<String, Action> actions;
	static Logger logger = Logger.getLogger(mainController.class.getName());

	
	@Override
	public void init() throws ServletException {
		   super.init();
		   
	       logger.info("Loading Barista Client");
	       
	       actions = new HashMap<String, Action>();
	       
	       actions.put("GET/", new OrderList());


	       logger.info("Finished Barista Loading");
	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Action action = getAction(request);
	    String view;
	    
	    if(action != null) {
	    	view = action.execute(request, response);
	    } else {
	    	view = new OrderList().execute(request, response);
	    }
		
		RequestDispatcher rd = request.getRequestDispatcher(view);
		rd.forward(request, response);

	}
	
	public static Action getAction(HttpServletRequest request) {
		return actions.get(request.getMethod() + request.getPathInfo());
	}
	
}
