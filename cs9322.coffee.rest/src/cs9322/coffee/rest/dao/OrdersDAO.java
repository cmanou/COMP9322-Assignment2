package cs9322.coffee.rest.dao;

import java.sql.*;
import java.util.Map;
import java.util.logging.Logger;

import javax.naming.*;
import javax.sql.*;

import cs9322.coffee.rest.models.*;


public enum OrdersDAO {
    instance;

    private OrdersDAO() {
		Logger myLogger = Logger.getLogger("test");

		//TODO set up db connection properly
        
        Connection conn = null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/sqlite");
            conn = ds.getConnection();
            Statement statement = conn.createStatement();
	        try {
	            statement.executeUpdate("create table thing(x integer)");
	        } catch (Exception e) { 
	             // Could well be already there
	        }
	        statement.executeUpdate("insert into thing values(42)");
	        ResultSet rs = statement.executeQuery("select * from thing");
	        while (rs.next()) {
	        	myLogger.info("id = " + rs.getInt(1));
	        } 
        } catch (Exception e) {
        	myLogger.info(e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
            	myLogger.info(e.getMessage());
            }
        }
        
    }
    
    public Map<String, Order> getOrders(){
    	//TODO: Return a map of current orders by id from db
        return null;
    }
    
    public Order getOrder(String id){
    	//TODO: Return order by id from db
        return null;
    }

	public void insertOrder(String id, Order o) {
		// TODO Insert order into db
		
	}

	public boolean removeOrder(String id) {
		// TODO Auto-generated method stub
		return true;
	}

	public void updateOrder(String id, Order b) {
		// TODO Auto-generated method stub
		
	}

	public boolean validOrder(String id) {
		// TODO Auto-generated method stub
		return false;
	}

}