package cs9322.coffee.barista.actions;

import java.net.URI;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.UriBuilder;


public class Action {
    public String execute(HttpServletRequest request, HttpServletResponse response){
    	return null;
    };
    
	public static URI getBaseURI() {
		String appConfigPath = "";
		try {
			appConfigPath = InitialContext.doLookup("java:comp/env/restPath");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return UriBuilder.fromUri(appConfigPath).build();
	}
}
