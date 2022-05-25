<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/ustl.tld" prefix="us" %>
<c:if test="${fcTemp.padre==null}">
	<c:forEach begin="0" end="${livello}" step="1">
		&nbsp;&nbsp;
	</c:forEach>
	<c:out value="${fcTemp.description}"/>
	<br/>
</c:if>
<c:choose>
	<c:when test="${not empty fcTemp.figli}">
		<c:set var="livello" scope="request" value="${livello+1}" />
		<c:forEach items="${fcTemp.figli}" var="fc" >		
    		<c:set var="fcTemp" scope="request" value="${fc}" />
    		<c:import url="../vam/cc/autopsie/ricorsioneFcAdd.jsp"/>						
		</c:forEach>
	</c:when>
	<c:otherwise>
		<c:if test="${fcTemp.sceltaSingola}">
			<c:forEach begin="0" end="${livello}" step="1">
				&nbsp;&nbsp;
			</c:forEach>
			<input type="radio" id="fc_${fcTemp.id}" 
				<c:if test="${fcTemp.padre==null}">
					name="fc_root"
				</c:if>
				<c:if test="${fcTemp.padre!=null}">
					name="fc_${fcTemp.padre.id}"
				</c:if>
			value="${fcTemp.id}" /> 
			<label for="fc_${fcTemp.id}">${fcTemp.description}</label>		
	    	<br/>
		</c:if>
		<c:if test="${!fcTemp.sceltaSingola}">
	    	<c:forEach begin="0" end="${livello}" step="1">
				&nbsp;&nbsp;
			</c:forEach>
			<input type="checkbox" id="fc_${fcTemp.id}" name="fc_${fcTemp.id}" value="${fcTemp.id}" /> <label for="fc_${fcTemp.id}">${fcTemp.description}</label>	
			<br/>
	    </c:if>
	</c:otherwise>
</c:choose>