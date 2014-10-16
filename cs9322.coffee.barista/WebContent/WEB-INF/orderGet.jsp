<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    
    
<c:choose>
	<c:when test="${not empty order}">
		<jsp:include page="/header.jsp">
			<jsp:param name="title" value="Order -  ${order.id}" />	
		</jsp:include>
	</c:when>
	<c:otherwise>
		<jsp:include page="/header.jsp">
			<jsp:param name="title" value="Order - Not Found" />	
		</jsp:include>	</c:otherwise>
</c:choose>

<div class="l-content">
    <div class="information pure-g">
        <div class="pure-u-1-1">
            <div class="l-box">
			<c:choose>
				<c:when test="${not empty order}">
					<h3 class="information-head">Order</h3>
					<table class="pure-table pure-table-horizontal">
					<thead>
						<tr>
							<th>Drink</th>
							<th>Additions</th>
							<th>Status</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${order.drink}</td>
							<td>${order.additions}</td>
							<td>${order.status}</td>
							<td>
									<form action='/cs9322.coffee.barista/barista/prepare' method='POST'class="pure-form pure-form-stacked" >
										<input type="hidden" name="id" value="${order.id}" > 
										<input type='submit' value='Prepare' class="pure-button pure-button-primary"> 
									</form>
									<a href="/cs9322.coffee.barista/barista/payment?id=${order.id}">Check Payment</a>
									<form action='/cs9322.coffee.barista/barista/release' method='POST'class="pure-form pure-form-stacked" >
										<input type="hidden" name="id" value="${order.id}" > 
										<input type='submit' value='Release' class="pure-button pure-button-primary"> 
									</form>
								</td>
						</tr>
					</tbody>
					</table>
				</c:when>
				<c:otherwise>
                	<h3 class="information-head">Order Not Found</h3>
				</c:otherwise>
			</c:choose>
            </div>
        </div>
      </div>
    <br><br><br><br>
</div> <!-- end l-content -->




<%@ include file="/footer.html"%>    
