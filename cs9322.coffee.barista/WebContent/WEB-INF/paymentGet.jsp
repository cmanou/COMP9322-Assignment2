<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    
    
<c:choose>
	<c:when test="${not empty payment}">
		<jsp:include page="/header.jsp">
			<jsp:param name="title" value="Payment -  ${id}" />	
		</jsp:include>
	</c:when>
	<c:otherwise>
		<jsp:include page="/header.jsp">
			<jsp:param name="title" value="Payment - ${id} - Not Found" />	
		</jsp:include>	</c:otherwise>
</c:choose>

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
            
			<c:choose>
				<c:when test="${not empty payment}">
					<h3 class="information-head">Payment - Order ${id}</h3>
					<table class="pure-table pure-table-horizontal">
					<thead>
						<tr>
							<th>Amount</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${payment.amount}</td>
						</tr>
					</tbody>
					</table>
				</c:when>
				<c:otherwise>
                	<h3 class="information-head">Payment for Order ${id} Not Found</h3>
				</c:otherwise>
			</c:choose>
            </div>
        </div>
      </div>
    <br><br><br><br>
</div> <!-- end l-content -->




<%@ include file="/footer.html"%>    
