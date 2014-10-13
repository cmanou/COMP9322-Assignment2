package cs9322.coffee.barista.actions;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.logging.Logger;

import javax.servlet.ServletException;


import java.util.List;

public class OrderList implements Action {
	static Logger logger = Logger.getLogger(OrderList.class.getName());

	
	public OrderList() throws ServletException {
		super();
		//TODO set it up
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//TODO get data
		//request.setAttribute("orders", orders);
		
		
		return "/WEB-INF/orderList.jsp";
	}

}
