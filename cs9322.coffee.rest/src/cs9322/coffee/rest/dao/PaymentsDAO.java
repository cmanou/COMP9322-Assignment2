package cs9322.coffee.rest.dao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.naming.*;
import javax.sql.*;


import cs9322.coffee.rest.models.*;


public enum PaymentsDAO {
    instance;
    
    private DataSource ds;
    private Connection conn;
    
    private PaymentsDAO() {
		Logger myLogger = Logger.getLogger("test");
  
        try {
            Context ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/sqlite");
            conn = ds.getConnection();
            Statement statement = conn.createStatement();
            try {
                statement.executeUpdate("create table payments(id integer PRIMARY KEY, type text, amount double, card_number text, card_name text, card_cvc text )");
            } catch (Exception e) { 
                 // Could well be already there
            }
        } catch (Exception e) {
        	myLogger.info(e.getMessage());
        } 
        
    }
    
    public Map<Integer, Payment> getPayments(){
    	Map<Integer, Payment> payments = new HashMap<Integer, Payment>();
    	try {

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
	        	p.generateLinks();
	        	payments.put(id, p);
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return payments;
    }
    
    public Payment getPayment(int id){
    	Payment p = null;
    	try {

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
	        	p.generateLinks();

	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}

        return p;
    }

	public boolean insertPayment(Payment p) {
    	try {
	        
	    	String query = "INSERT INTO PAYMENTS (id, type, amount, card_number, card_name, card_cvc) VALUES (?, ?, ?, ?, ?, ?)";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	        stmnt.setInt(1,p.getId());
	        stmnt.setString(2,p.getType());
	        stmnt.setDouble(3,p.getAmount());
	        if(p.getType().equals("CARD")) {
		        stmnt.setString(4,p.getCard().getNumber());
		        stmnt.setString(5,p.getCard().getName());
		        stmnt.setString(6,p.getCard().getCvc());
	        }else {
		        stmnt.setString(4,"");
		        stmnt.setString(5,"");
		        stmnt.setString(6,"");        	
	        }
	        
	        stmnt.executeUpdate();
	        
	        return true;	       
	        
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean updateOrder(int id, Payment p) {
    	try {

	    	String query = "UPDATE PAYMENTS SET type = ?, amount = ?, card_number = ?, card_name = ? , card_cvc = ? WHERE id = ?";
	        PreparedStatement stmnt = conn.prepareStatement(query);
	       
	        stmnt.setString(1,p.getType());
	        stmnt.setDouble(2,p.getAmount());
	        if(p.getType().equals("CARD")) {
		        stmnt.setString(3,p.getCard().getNumber());
		        stmnt.setString(4,p.getCard().getName());
		        stmnt.setString(5,p.getCard().getCvc());
	        }else {
		        stmnt.setString(3,"");
		        stmnt.setString(4,"");
		        stmnt.setString(5,"");        	
	        }
	        stmnt.setInt(6,p.getId());

	        stmnt.executeUpdate();
	        return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return false;
		
	}

}