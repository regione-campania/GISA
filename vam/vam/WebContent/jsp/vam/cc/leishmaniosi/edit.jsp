<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="java.util.Date"%>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/leishmaniosi/edit.js"></script>


<form action="vam.cc.leishmaniosi.Edit.us" method="post" onsubmit="javascript:return checkform(this);">

	<input type="hidden" name="idLeishmaniosi" value="<c:out value="${l.id}"/>"/>
	
	
	
	<jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
	<h4 class="titolopagina">
	   	Modifica Leishmaniosi
	</h4>
	
	<table class="tabella">
		
		<tr>
        	<th colspan="3">
        		Dati Leishmaniosi
        	</th>
        </tr>
			
		<tr class="even">
    		<td>
    			 Data Prelievo Leishmaniosi
    		</td>
    		<td>    		
    			 <fmt:formatDate value="${l.dataPrelievoLeishmaniosi}" pattern="dd/MM/yyyy" var="dataPrelievo"/>   			 
    			 <input type="text" id="dataPrelievoLeishmaniosi" name="dataPrelievoLeishmaniosi" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${dataPrelievo}"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataPrelievoLeishmaniosi",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_1",  // trigger for the calendar (button ID)
       						// align          :    "Tl",           // alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class="odd">
    		<td>
    			 Data Esito Leishmaniosi
    		</td>
    		<td>    		
    			 <fmt:formatDate value="${l.dataEsitoLeishmaniosi}" pattern="dd/MM/yyyy" var="dataEsito"/>   			 
    			 <input type="text" id="dataEsitoLeishmaniosi" name="dataEsitoLeishmaniosi" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${dataEsito}"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_2" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataEsitoLeishmaniosi",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_2",  // trigger for the calendar (button ID)
       						// align          :    "Tl",           // alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>
    		<td>
    		</td>
        </tr>
        
        
        <tr class="even">
	        <td>
	       		 Esito
	        </td>
        <td>
	        <select name="esito">				
   				<c:forEach var="listE" items="${listEsiti}">
           			<option value="<c:out value="${listE.id}"/>"           			
	           			<c:if test="${listE.id == l.lle.id}">
	  		          		<c:out value="selected=selected"></c:out> 
	    				</c:if>              			
           			
           			>            				
           				${listE.description}
   					</option>
           		</c:forEach>
	    	</select>              
        </td>
        </tr>
        
        
        <tr class="odd">
	        <td>
	       		 Ordinanza Sindaco
	        </td>
        <td>
	        <input type=text name="ordinanzaSindaco" maxlength="12" size="50" value="${l.ordinanzaSindaco }"/>  
        </td>
        <td>
        	Del        
   		 	<fmt:formatDate value="${l.dataOrdinanzaSindaco}" pattern="dd/MM/yyyy" var="dataOrdinanzaSindaco"/>   			 
    		<input type="text" id="dataOrdinanzaSindaco" name="dataOrdinanzaSindaco" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${dataOrdinanzaSindaco}"/>
			<img src="images/b_calendar.gif" alt="calendario" id="id_img_3" />
			<script type="text/javascript">
					Calendar.setup({
 					inputField     :    "dataOrdinanzaSindaco",     // id of the input field
 					ifFormat       :    "%d/%m/%Y",      // format of the input field
					button         :    "id_img_3",  // trigger for the calendar (button ID)
					// align          :    "Tl",           // alignment (defaults to "Bl")
 					singleClick    :    true,
 					timeFormat		:   "24",
 					showsTime		:   false
					 });					    
				</script>           
        </td>
        </tr>
	
	<tr>
		<td>
		</td>
		<td>	
			<input type="submit" value="Modifica"/>
			<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.leishmaniosi.List.us'">
		 </td>
	 </tr>
 	</table>

</form>
