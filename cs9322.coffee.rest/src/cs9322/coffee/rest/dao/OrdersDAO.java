package cs9322.coffee.rest.dao;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.naming.*;
import javax.sql.*;

import cs9322.coffee.rest.models.*;


public enum OrdersDAO {
    instance;
    
    private DataSource ds;
    private Connection conn;
    
   //TODO: Additions work out how to store
    private OrdersDAO() {
		Logger myLogger = Logger.getLogger("test");
  
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/sqlite");
            conn = ds.getConnection();
            Statement statement = conn.createStatement();
            try {
                statement.executeUpdate("create table orders(id integer PRIMARY KEY, drink text, cost double, status text )");
            } catch (Exception e) { 
                 // Could well be already there
            }
        } catch (Exception e) {
        	myLogger.info(e.getMessage());
        } 
        
    }
    
    public Map<Integer, Order> getOrders(){
    	Map<Integer, Order> orders = new HashMap<Integer, Order>();
    	try {

	    	String query = "SELECT * FROM ORDERS";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	        ResultSet rs = stmnt.executeQuery();
	        
	        while(rs.next()) {
		        int id = rs.getInt("id");
		        String drink = rs.getString("drink");
		        //TODO: Additions
		        double cost  = rs.getDouble("cost");
	        	String status = rs.getString("status");
	        	
	        	Order o = new Order(id, drink, null, cost, status);
	        	orders.put(id, o);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return orders;
    }
    
    public Order getOrder(int id){
    	Order o = null;
    	try {

	    	String query = "SELECT * FROM ORDERS WHERE ID = ?";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	        stmnt.setInt(1,id);
	        ResultSet rs = stmnt.executeQuery();
	        
	        rs.first();
	        int _id = rs.getInt("id");
	        String drink = rs.getString("drink");
	        //TODO: Additions
	        double cost  = rs.getDouble("cost");
        	String status = rs.getString("status");
        
        	o = new Order(_id, drink, null, cost, status);
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return o;
    }

	public int insertOrder(Order o) {
		// TODO Insert order into db
		return -1;
	}

	public boolean removeOrder(int id) {
		// TODO Auto-generated method stub
		return true;
	}

	public void updateOrder(int id, Order o) {
		// TODO Auto-generated method stub
		
	}

	public boolean validOrder(int id) {
    	try {
	    	String query = "SELECT * FROM ORDERS WHERE ID = ?";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	        stmnt.setInt(1,id);
	        ResultSet rs = stmnt.executeQuery();
	        rs.last();
	        return rs.getRow() == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}		
    	return false;
	}

}