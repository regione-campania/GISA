<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ page import="java.util.ArrayList" %>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/esamiObiettivo/add.js"></script>

<%  
 ArrayList<String> listDescrizioni = (ArrayList<String>)  request.getAttribute("descrizioniEOTipo"); 
 int countDescrizioni = 0;
%>

<form action="vam.cc.esamiObiettivo.Add.us" method="post" onsubmit="javascript:return checkform(this);">

	
	<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
	<h4 class="titolopagina">
	   	Nuovo esame obiettivo
	</h4>
	
	
	<table class="tabella">
		<tr>
    		<td>
    			 Data dell'esame
    		</td>
    		<td>    		
    			 <input type="text" id="dataEsameObiettivo" name="dataEsameObiettivo" maxlength="32" size="50" readonly="readonly" style="width:246px;"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataEsameObiettivo",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_1",  // trigger for the calendar (button ID)
       						// align          :    "Tl",           // alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>
        </tr>       
	</table>
	
	<table class="tabella">
		
		<tr>
			<th colspan="3">
	       		Esame Obiettivo
	       	</th> 	       	     
		</tr>		
	
	
		<c:set var="i" value='1'/>	
		<c:forEach items="${superList}" var="sl" >	
		
		<tr class="${i % 2 == 0 ? 'even' : 'odd'}">
		
			<td style="width:40%">				
				<%= listDescrizioni.get(countDescrizioni) %>     
			</td>
			<input type="hidden" name="descrizione_${i}" value="<%=listDescrizioni.get(countDescrizioni)%>"/>
	    
			<td style="width:20%">
				<input type="radio" name="_${i}" id="_N${i}" value="Normale" onclick="javascript: hiddenDiv('_${i}');" > <label for="_N${i}">Normale</label><br>
				<input type="radio" name="_${i}" id="_A${i}" value="Anormale" onclick="javascript: toggleGroup('_${i}');" > <label for="_A${i}">Anormale</label><br>
				<input type="radio" name="_${i}" id="_NE${i}" value="NE" checked="checked" onclick="javascript: hiddenDiv('_${i}');" > <label for="_NE${i}">Non esaminato</label><br>
			</td>			
			<td style="width:40%">
				<div id="_${i }" style="display:none;">	
					<c:forEach items="${sl}" var="temp" >
						
						<%-- Variabile per l'individuazione dei figli di un esame --%>
						<c:set var="isIn" value="NO"/>	
						
						<%-- Ciclo preventivo per controllare se un esame ha figli --%>
						<c:forEach items="${listEsameObiettivoFigli}" var="tempFiglio" >							
							<c:if test="${tempFiglio.lookupEsameObiettivoEsito.id == temp.id}">
								<c:set var="isIn" value="YES"/>	
							</c:if>																
						</c:forEach>
						
						<%-- Se un esame non ha figli allora checkbox normale,
						se invece posside figli il padre viene messo non checkabile
						in rosso e immediatamente sotto viene presentata la lista dei figli --%>
						<c:choose>
							<c:when test="${ isIn == 'NO'}">																	
									<input type="checkbox" id="op_${temp.id }" name="op_${temp.id }" onclick="esitiMutuamenteEsclusivi(${temp.esitiMutuamenteEsclusiviString})"/> <label for="op_${temp.id }" >${temp.description}</label>	
							</c:when>							
							<c:otherwise>
									<label><b><i>${temp.description}</i></b></label> <br/>
								
									<c:forEach items="${listEsameObiettivoFigli}" var="tempFiglio" >	
										
										<c:if test="${tempFiglio.lookupEsameObiettivoEsito.id == temp.id}">
											<c:set var="isIn" value="YES"/>											
											&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="op_${tempFiglio.id}" name="op_${tempFiglio.id }" onclick="esitiMutuamenteEsclusivi(${tempFiglio.esitiMutuamenteEsclusiviString})" /> <label for="op_${tempFiglio.id}">${tempFiglio.description }</label>	
											<br>
										</c:if>				
																											
									</c:forEach>
									
							</c:otherwise>
						</c:choose>
												
						<br>
																	
					</c:forEach>
				</div>
			</td>			
		</tr>
		
		<c:set var="i" value='${i+1}'/>
		<% countDescrizioni++; %>		   

	    </c:forEach>
	
		<input type="hidden" name="numeroElementi" value="${i}"/>
		
	</table>
		
	
	<input type="submit" value="Salva" />
	<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.Detail.us'">

</form>

