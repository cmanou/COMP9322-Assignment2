package cs9322.coffee.rest.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.*;
import javax.sql.*;
import javax.ws.rs.core.UriInfo;


import cs9322.coffee.rest.models.*;


public enum DatabaseDAO {
    instance;
    
    private DataSource ds;
    private Connection conn;
    
    private DatabaseDAO() {
		Logger myLogger = Logger.getLogger("test");
  
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/sqlite");
            conn = ds.getConnection();
            Statement statement = conn.createStatement();
            try {
                statement.executeUpdate("create table orders(id integer PRIMARY KEY, drink text, additions text, cost real, status text, payment_status text)");
            } catch (Exception e) { 
                 // Could well be already there
            }
            
            try {
                statement.executeUpdate("create table payments(id integer PRIMARY KEY, type text, amount real, card_number text, card_name text, card_cvc text)");
            } catch (Exception e) { 
                 // Could well be already there
            }
            conn.close();
        } catch (Exception e) {
        	myLogger.info(e.getMessage());
        } 
        
    }
    
    public OrdersList getOrders(UriInfo aUriInfo){
    	
    	List<Order> newList = new ArrayList<Order>();
    	
    	try {
    		conn = ds.getConnection();
	    	String query = "SELECT * FROM ORDERS";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	        ResultSet rs = stmnt.executeQuery();
	        
	        while(rs.next()) {
		        int id = rs.getInt("id");
		        String drink = rs.getString("drink");
		        List<String> additions = Arrays.asList(rs.getString("additions").split("\\s*,\\s*"));
		        double cost  = rs.getDouble("cost");
	        	String status = rs.getString("status");
	        	String paymentStatus = rs.getString("payment_status");
	        	
	        	Order o = new Order(id, drink, additions, cost, status, paymentStatus);
	        	o.generateLinks(aUriInfo);
	        	newList.add(o);
	        }
	    	conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	OrdersList newOrdersList = new OrdersList(newList);
    	
        return newOrdersList;
    }
    
    public Order getOrder(int id, UriInfo aUriInfo){
    	Order o = null;
    	try {
    		conn = ds.getConnection();
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
	        	String paymentStatus = rs.getString("payment_status");
	        	
	        	o = new Order(_id, drink, additions, cost, status, paymentStatus);
	        	o.generateLinks(aUriInfo);

	        }
	        conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return o;
    }

	public int insertOrder(Order o) {
		int id = -1;
    	try {
    		conn = ds.getConnection();
	        conn.setAutoCommit(false); // Starts transaction.
	    	String query = "INSERT INTO ORDERS (drink, additions, cost,status, payment_status) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement stmnt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        stmnt.setString(1,o.getDrink());
	        stmnt.setString(2,o.getAdditions().toString().substring(1, o.getAdditions().toString().length()-1));
	        stmnt.setDouble(3,o.getCost());
	        stmnt.setString(4,o.getStatus());
	        stmnt.setString(5,o.getPaymentStatus());
	        
	        stmnt.executeUpdate();
	        Statement s = conn.createStatement();
	        ResultSet rs = s.executeQuery("SELECT last_insert_rowid()");
	        if (rs.next()) {
	            id = rs.getInt(1);
	        }
	        conn.commit(); // Commits transaction.
	        conn.setAutoCommit(true); 
	        conn.close();
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public boolean removeOrder(int id) {
    	try {
    		conn = ds.getConnection();
	    	String query = "DELETE FROM ORDERS WHERE ID = ?";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	        stmnt.setInt(1,id);
	        stmnt.executeUpdate();
	        conn.close();
	        return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}		
    	return false;
	}

	public void updateOrder(int id, Order o) {
    	try {
    		conn = ds.getConnection();

	    	String query = "UPDATE ORDERS SET drink = ?, additions = ?, cost = ?, status = ?, payment_status = ? WHERE id = ?";
	        PreparedStatement stmnt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	        stmnt.setString(1,o.getDrink());
	        stmnt.setString(2,o.getAdditions().toString().substring(1, o.getAdditions().toString().length()-1));
	        stmnt.setDouble(3,o.getCost());
	        stmnt.setString(4,o.getStatus());
	        stmnt.setString(5,o.getPaymentStatus());
	        stmnt.setInt(6,o.getId());
	        stmnt.executeUpdate();

	        conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public boolean validOrder(int id) {
    	try {
    		conn = ds.getConnection();

	    	String query = "SELECT * FROM ORDERS WHERE id = ?";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	        stmnt.setInt(1,id);
	        ResultSet rs = stmnt.executeQuery();
	        
	        if(rs.next()) {
	        	return rs.getRow() == 1;
	        }
	        
	        conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
    	return false;
	}
	
	public PaymentList getPayments(UriInfo aUriInfo)
    {
    	List<Payment> newList = new ArrayList<Payment>();
    	
    	try {
    		conn = ds.getConnection();

	    	String query = "SELECT * FROM PAYMENTS";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	        ResultSet rs = stmnt.executeQuery();
	        while(rs.next()) {
		        int id = rs.getInt("id");
		        String type = rs.getString("type");
		        double amount  = rs.getDouble("amount");
	        	String card_number = rs.getString("card_number");
	        	String card_name = rs.getString("card_name");
	        	String card_cvc = rs.getString("card_cvc");
	        	
	        	Payment p = new Payment(id, type, amount, card_number, card_name, card_cvc);
	        	p.generateLinks(aUriInfo);
	        	
	        	newList.add(p);
	        }
	        conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	PaymentList newPaymentsList = new PaymentList(newList);
    	
        return newPaymentsList;
    }
    
    public Payment getPayment(int id, UriInfo aUriInfo){
    	Payment p = null;
    	try {
    		conn = ds.getConnection();

	    	String query = "SELECT * FROM PAYMENTS WHERE ID = ?";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	        stmnt.setInt(1,id);
	        ResultSet rs = stmnt.executeQuery();
	        
	        if(rs.next())  {
		        int _id = rs.getInt("id");
		        String type = rs.getString("type");
		        double amount  = rs.getDouble("amount");
	        	String card_number = rs.getString("card_number");
	        	String card_name = rs.getString("card_name");
	        	String card_cvc = rs.getString("card_cvc");
	        	
	        	p = new Payment(_id, type, amount, card_number, card_name, card_cvc);
	        	p.generateLinks(aUriInfo);

	        }
	        conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return p;
    }

	public boolean insertPayment(Payment p) {
    	try {
    		conn = ds.getConnection();

	    	String query = "INSERT INTO PAYMENTS (id, type, amount, card_number, card_name, card_cvc) VALUES (?, ?, ?, ?, ?, ?)";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	        stmnt.setInt(1,p.getId());
	        stmnt.setString(2,p.getType());
	        stmnt.setDouble(3,p.getAmount());
	        if(p.getType().equals(Payment.CARD)) {
		        stmnt.setString(4,p.getCardNumber());
		        stmnt.setString(5,p.getCardName());
		        stmnt.setString(6,p.getCardCVC());
	        }else {
		        stmnt.setString(4,"");
		        stmnt.setString(5,"");
		        stmnt.setString(6,"");        	
	        }
	        
	        stmnt.executeUpdate();
	        conn.close();
	        return true;	       
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updatePayment(Payment p) {
    	try {
    		conn = ds.getConnection();

	    	String query = "UPDATE PAYMENTS SET type = ?, amount = ?, card_number = ?, card_name = ? , card_cvc = ? WHERE id = ?";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	       
	        stmnt.setString(1,p.getType());
	        stmnt.setDouble(2,p.getAmount());
	        if(p.getType().equals(Payment.CARD)) {
		        stmnt.setString(3,p.getCardNumber());
		        stmnt.setString(4,p.getCardName());
		        stmnt.setString(5,p.getCardCVC());
	        }else {
		        stmnt.setString(3,"");
		        stmnt.setString(4,"");
		        stmnt.setString(5,"");        	
	        }
	        stmnt.setInt(6,p.getId());

	        stmnt.executeUpdate();
	        conn.close();
	        return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
		
	}

	public boolean paymentExits(int id) {
    	try {
    		conn = ds.getConnection();

	    	String query = "SELECT * FROM PAYMENTS WHERE ID = ?";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	        stmnt.setInt(1,id);
	        ResultSet rs = stmnt.executeQuery();
	        
	        if(rs.next())
	        {
	        	return rs.getRow() == 1;
	        }
	        conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
    	return false;
	}
	
	public boolean removePayment(int id) {
    	try {
    		conn = ds.getConnection();
	    	String query = "DELETE FROM PAYMENTS WHERE ID = ?";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	        stmnt.setInt(1,id);
	        stmnt.executeUpdate();
	        conn.close();
	        return true;

		} catch (SQLException e) {
			e.printStackTrace();
		}		
    	return false;
	}
	
	public void close() 
	{
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}