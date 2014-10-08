package cs9322.coffee.rest.dao;

import java.sql.*;
import java.util.Arrays;
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
    
    private OrdersDAO() {
		Logger myLogger = Logger.getLogger("test");
  
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/sqlite");
            conn = ds.getConnection();
            Statement statement = conn.createStatement();
            try {
                statement.executeUpdate("create table orders(id integer PRIMARY KEY, drink text, additions text, cost double, status text )");
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
		        List<String> additions = Arrays.asList(rs.getString("additions").split("\\s*,\\s*"));
		        double cost  = rs.getDouble("cost");
	        	String status = rs.getString("status");
	        	
	        	Order o = new Order(id, drink, additions, cost, status);
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
	        List<String> additions = Arrays.asList(rs.getString("additions").split("\\s*,\\s*"));
	        double cost  = rs.getDouble("cost");
        	String status = rs.getString("status");
        
        	o = new Order(_id, drink, additions, cost, status);
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return o;
    }

	public int insertOrder(Order o) {
    	try {

	    	String query = "INSERT INTO ORDERS (drink, additions, cost,status) VALUES (?, ?, ?, ?)";
	        PreparedStatement stmnt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        stmnt.setString(1,o.getDrink());
	        stmnt.setString(2,o.getAdditions().toString().substring(1, o.getAdditions().toString().length()-1));
	        stmnt.setDouble(3,o.getCost());
	        stmnt.setString(4,o.getStatus());
	        stmnt.executeUpdate();
	        ResultSet rs = stmnt.getGeneratedKeys();
	        if (rs != null && rs.next()) {
		        int _id = rs.getInt("id"); //TODO: WOrk out if this is right;
		        return _id;
	        }

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public boolean removeOrder(int id) {
    	try {
	    	String query = "DELETE FROM ORDERS WHERE ID = ?";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	        stmnt.setInt(1,id);
	        stmnt.executeUpdate();
	        return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}		
    	return false;
	}

	public void updateOrder(int id, Order o) {
    	try {

	    	String query = "UPDATE ORDERS SET drink = ?, additions = ?, cost = ?, status = ? WHERE id = ?";
	        PreparedStatement stmnt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        stmnt.setString(1,o.getDrink());
	        stmnt.setString(2,o.getAdditions().toString().substring(1, o.getAdditions().toString().length()-1));
	        stmnt.setDouble(3,o.getCost());
	        stmnt.setString(4,o.getStatus());
	        stmnt.setInt(5,o.getId());
	        stmnt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
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