<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<meta http-equiv="refresh" content="10" >
    
    
<jsp:include page="/header.jsp">
	<jsp:param name="title" value="Orders" />	
</jsp:include>

<div class="l-content">
    <div class="information pure-g">
        <div class="pure-u-1-1">
            <div class="l-box">
            
            <c:if test="${not empty response}">
			<aside>
		        <p>
					${response.statusCode} - ${response.reasonPhrase}
		        </p>
		    </aside>
		    		    <br>
		    
            </c:if>
            
        	<a href="/cs9322.coffee.barista/barista" class="pure-button pure-button-primary">Refresh</a>
        
			<c:choose>
				<c:when test="${not empty orders}">
					<h3 class="information-head">Orders</h3>
					<table class="pure-table pure-table-horizontal">
					<thead>
						<tr>
							<th>Drink</th>
							<th>Additions</th>
							<th>Status</th>
							<th>Payment Status</th>
							<th>Cost</th>
							<th colspan="3">Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="order" items="${orders}">
							<c:if test="${not order.finished}">
							<tr>
								<td><a href="/cs9322.coffee.barista/barista/order?id=${order.id}">${order.drink}</a></td>
								<td>${order.additions}</td>
								<td>${order.status}</td>
								<td>${order.paymentStatus}</td>
								<td><fmt:formatNumber value="${order.cost}" type="currency" /></td>
								<td>
									<form action='/cs9322.coffee.barista/barista/prepare' method='POST'class="pure-form pure-form-stacked" >
										<input type="hidden" name="id" value="${order.id}" > 
										<input type='submit' value='Prepare' class="pure-button"> 
									</form>
								</td>
								<td>
									<a href="/cs9322.coffee.barista/barista/payment?id=${order.id}" class="pure-button ">Check Payment</a>
								</td>
								<td>
									<form action='/cs9322.coffee.barista/barista/release' method='POST'class="pure-form pure-form-stacked" >
										<input type="hidden" name="id" value="${order.id}" > 
										<input type='submit' value='Release' class="pure-button "> 
									</form>
								</td>
							</tr>
							</c:if>
						</c:forEach>
					</tbody>
					</table>
				</c:when>
				<c:otherwise>
                	<h3 class="information-head">No Orders</h3>
				</c:otherwise>
			</c:choose>
            </div>
        </div>
      </div>
    <br><br><br><br>
</div> <!-- end l-content -->




<%@ include file="/footer.html"%>    
