<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Pay Order</title>
</head>
<body>
	<h1>Pay Order</h1>
	
	<form method="post" action="Controller">
		<table>
			<tr>
				<td>*Type (CARD/CASH): </td>
				<td><input type="text" id="aType" name="aType"/></td>
			</tr>
			<tr>
				<td>Card Name: </td>
				<td><input type="text" id="aCardName" name="aCardName" /></td>
			</tr>
			<tr>
				<td>Card Number: </td>
				<td><input type="text" id="aCardNumber" name="aCardNumber" /></td>
			</tr>
			<tr>
				<td>Card CVC: </td>
				<td><input type="text" id="aCardCVC" name="aCardCVC" /></td>
			</tr>
			<tr>
				<td>
					<input type="hidden" id="aAction" name="aAction" value="payOrder"/>
					<input type="hidden" id="orderId" name="aOrderId" value="${myOrder.id}"/>
				</td>
				<td><input type="submit" value="Pay"/></td>
			</tr>
		</table>
	</form>

</body>
</html>