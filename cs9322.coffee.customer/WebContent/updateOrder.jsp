<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create Order</title>
</head>
<body>
	<h1>Update Order</h1>
	
	<form method="post" action="Controller">
		<table>
			<tr>
				<td>Change to Drink: </td>
				<td><input type="text" id="aDrink" name="aDrink"/></td>
			</tr>
			<tr>
				<td >Change to Additions (comma separated values): </td>
				<td>
					<input type="text" id="aAdditions" name="aAdditions" value=""/>
				
				</td>
			</tr>
			<tr>
				<td>
					<input type="hidden" id="aAction" name="aAction" value="updateOrder"/>
					<input type="hidden" id="orderId" name="aOrderId" value="${myOrder.id}"/>
				</td>
				<td><input type="submit" value="Update"/></td>
			</tr>
		</table>
	</form>

</body>
</html>