<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@page import="it.us.web.bean.vam.Autopsia"%>


<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
<h4 class="titolopagina">
    Dettaglio Decesso
</h4>
	


<form action="" name="form" method="post" id="form" class="marginezero">

<table class="tabella">
    	    	   	
    	
    	<tr class='even'>
    		<td>
    			Data del decesso
    		</td>
    		<td>							
				<c:choose>
	    			<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
	    				<fmt:formatDate type="date" value="${cc.accettazione.animale.dataMorte}" pattern="dd/MM/yyyy" var="dataMorte"/>
	    			</c:when>
	    			<c:otherwise>
	    				<fmt:formatDate type="date" value="${res.dataEvento}" pattern="dd/MM/yyyy" var="dataMorte"/>
	    			</c:otherwise>
	    		</c:choose>
				<c:out value="${dataMorte}"/>
				<input type="hidden" name="dataMorte" value="<c:out value="${dataMorte}"/>"/>
			</td>
			<td>			
				<c:choose>
	    			<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
	    				${cc.accettazione.animale.dataMorteCertezza}
	    			</c:when>
	    			<c:otherwise>
	    				<c:choose>
	    					<c:when test="${res.dataDecessoPresunta}">					
						 		Presunta	   		 
							</c:when>
							<c:otherwise>
								Certa
							</c:otherwise>	
						</c:choose>
	    			</c:otherwise>
	    		</c:choose>
	        </td>
    	</tr>
    	
    	
	    	<tr class='odd'>
		        <td>
		    		Probabile causa del decesso
	    		</td>
	    		<td>  
	    			<c:choose>
	    				<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
	    					${cc.accettazione.animale.causaMorte.description}
	    				</c:when>
	    				<c:otherwise>
	    					${res.decessoValue}
	    				</c:otherwise>
	    			</c:choose>
		        </td>
		        <td>
		        </td>
	        </tr>
       
       
       
        <tr class='odd'> 
			<td align="center">    		   			
				<input type="button" value="Modifica" onclick=" if(${cc.dataChiusura!=null}){ 
    				if(myConfirm('Cartella Clinica chiusa. Vuoi procedere?')){attendere(), location.href='vam.cc.decessi.ToEdit.us'}
					}else{attendere(), location.href='vam.cc.decessi.ToEdit.us'} ">
				<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.Detail.us'">
			</td>
			<td>
	        </td>
	        <td>
	        </td>				
		</tr>
        
        </table>
</form>