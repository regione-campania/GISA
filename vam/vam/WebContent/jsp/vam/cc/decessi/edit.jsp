<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>

<script language="JavaScript" type="text/javascript" src="js/vam/cc/decessi/edit.js"></script>

<form action="vam.cc.decessi.Edit.us" name="form" method="post" id="form" class="marginezero" onsubmit="javascript:return checkform(this);">
           
    <input type="hidden" name="idAutopsia" value="<c:out value="${a.id}"/>"/>
        
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/> 
    <h4 class="titolopagina">
     		Modifica Decesso
    </h4>  
    	 
    
    <table class="tabella">
    	  
       <tr class='even'>
    		<td>
    			Data del decesso<font color="red"> *</font>
    		</td>
    		<td>
    			<c:choose>
	    			<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
	    				<fmt:formatDate value="${cc.accettazione.animale.dataMorte}" pattern="dd/MM/yyyy" var="data"/> 
	    			</c:when>
	    			<c:otherwise>
	    				<fmt:formatDate value="${res.dataEvento}" pattern="dd/MM/yyyy" var="data"/> 
	    			</c:otherwise>
	    		</c:choose>
				<input type="text" id="dataMorte" name="dataMorte" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="<c:out value="${data}"></c:out>"/>
		    		 <img src="images/b_calendar.gif" alt="calendario" id="id_img_" />
		 				<script type="text/javascript">
		      				 Calendar.setup({
		        				inputField     :    "dataMorte",     // id of the input field
		        				ifFormat       :    "%d/%m/%Y",      // format of the input field
		       					button         :    "id_img_",  // trigger for the calendar (button ID)
		       					// align          :    "Tl",           // alignment (defaults to "Bl")
		        				singleClick    :    true,
		        				timeFormat		:   "24",
		        				showsTime		:   false
		   						 });					    
		  				 </script>   
			</td>
			<td>						
				<c:choose>
	    			<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
	    				<c:if test="${cc.accettazione.animale.dataMortePresunta!=null}">
	    					<c:choose>	    			
								<c:when test="${cc.accettazione.animale.dataMortePresunta == true}">					
									<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false" checked/><label for="dataMorteCertaT">Presunta</label>	
									<br>
									<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true"/> <label for="dataMorteCertaF">Certa</label>				
								</c:when>
								<c:otherwise>
									<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false"/><label for="dataMorteCertaT">Presunta</label>	
									<br>
									<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true" checked/> <label for="dataMorteCertaF">Certa</label>					   		 
								</c:otherwise>	
							</c:choose>
						</c:if>
						<c:if test="${cc.accettazione.animale.dataMortePresunta==null}">
							<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false" checked/><label for="dataMorteCertaT">Presunta</label>	
							<br>
							<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true"/> <label for="dataMorteCertaF">Certa</label>				
						</c:if>
	    			</c:when>
	    			<c:otherwise>
	    				<c:choose>	    			
							<c:when test="${res.dataDecessoPresunta == true}">					
								<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false" checked/><label for="dataMorteCertaT">Presunta</label>	
								<br>
								<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true"/> <label for="dataMorteCertaF">Certa</label>				
							</c:when>
							<c:otherwise>
								<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false"/><label for="dataMorteCertaT">Presunta</label>	
								<br>
								<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true" checked/> <label for="dataMorteCertaF">Certa</label>					   		 
							</c:otherwise>	
						</c:choose>
	    			</c:otherwise>
	    		</c:choose>
	    		
	    			
			
			
	        </td>
    	</tr>
    	
    	<tr class='odd'>
	        <td>
	    		Probabile causa del decesso<font color="red"> *</font>
    		</td>
    		<td> 
    			<select name="causaMorteIniziale">
		        	 <c:forEach items="${listCMI}" var="cmi" >					        	 	
						<c:choose>
	    					<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
	    						<c:choose>
	  								<c:when test="${cmi.description == cc.accettazione.animale.causaMorte.description}">			
		        	 					<option value="${cmi.id }" selected>${cmi.description }</option>	        	 				
									</c:when>
									<c:otherwise>
										<option value="${cmi.id }">${cmi.description }</option>
									</c:otherwise>
								</c:choose>	
	    					</c:when>
	    					<c:otherwise>
	    						<c:choose>
	  								<c:when test="${cmi.description == res.decessoValue}">			
		        	 					<option value="${cmi.id }" selected>${cmi.description }</option>	        	 				
									</c:when>
									<c:otherwise>
										<option value="${cmi.id }">${cmi.description }</option>
									</c:otherwise>
								</c:choose>	
	    					</c:otherwise>
	    				</c:choose>
					</c:forEach>
		      	</select>	    		    
	        </td>
	        <td>
	        </td>
        </tr>
         
     </table>
     
	<table class="tabella">
		<tr class='odd'> 
			<td>
				<font color="red">* </font> Campi obbligatori
			</td>
			<td align="center">     		   			
				<input type="submit" value="Modifica" />
	    		<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.Detail.us'">
	    	</td>
	    	<td>    			
    		</td>  				
		</tr>
	</table>
		
</form>

 
