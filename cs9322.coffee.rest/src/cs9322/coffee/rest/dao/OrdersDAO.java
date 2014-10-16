package cs9322.coffee.rest.dao;

import java.sql.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.naming.*;
import javax.sql.*;
import javax.ws.rs.core.UriInfo;

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
                statement.executeUpdate("create table orders(id integer PRIMARY KEY, drink varchar(100), additions varchar(100), cost real, status varchar(100) )");
            } catch (Exception e) { 
                 // Could well be already there
            }
        } catch (Exception e) {
        	myLogger.info(e.getMessage());
        } 
        
    }
    
    public Map<Integer, Order> getOrders(UriInfo aUriInfo){
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
	        	o.generateLinks(aUriInfo);
	        	orders.put(id, o);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return orders;
    }
    
    public Order getOrder(int id, UriInfo aUriInfo){
    	Order o = null;
    	try {

	    	String query = "SELECT * FROM ORDERS WHERE ID = ?";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	        stmnt.setInt(1,id);
	        ResultSet rs = stmnt.executeQuery();
	        
	        if(rs.next())  {
		        int _id = rs.getInt("id");
		        String drink = rs.getString("drink");
		        List<String> additions = Arrays.asList(rs.getString("additions").split("\\s*,\\s*"));
		        double cost  = rs.getDouble("cost");
	        	String status = rs.getString("status");
	        	o = new Order(_id, drink, additions, cost, status);
	        	o.generateLinks(aUriInfo);

	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return o;
    }

	public int insertOrder(Order o) {
		int id = -1;
    	try {
	        
	        conn.setAutoCommit(false); // Starts transaction.
	    	String query = "INSERT INTO ORDERS (drink, additions, cost,status) VALUES (?, ?, ?, ?)";
	        PreparedStatement stmnt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        stmnt.setString(1,o.getDrink());
	        stmnt.setString(2,o.getAdditions().toString().substring(1, o.getAdditions().toString().length()-1));
	        stmnt.setDouble(3,o.getCost());
	        stmnt.setString(4,o.getStatus());
	        
	        stmnt.executeUpdate();
	        Statement s = conn.createStatement();
	        ResultSet rs = s.executeQuery("SELECT last_insert_rowid()");
	        if (rs.next()) {
	            id = rs.getInt(1);
	        }
	        conn.commit(); // Commits transaction.
	        conn.setAutoCommit(true); 
	        
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
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
	    	String query = "SELECT COUNT(*) AS COUNT FROM ORDERS WHERE ID = ?";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	        stmnt.setInt(1,id);
	        ResultSet rs = stmnt.executeQuery();
	        rs.next();
	        return rs.getInt("COUNT") == 1;

		} catch (SQLException e) {
			e.printStackTrace();
		}		
    	return false;
	}

}