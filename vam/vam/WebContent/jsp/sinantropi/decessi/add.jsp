<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/sinantropi/decessi/add.js"></script>


<form action="sinantropi.decessi.Add.us" name="form" method="post" onsubmit="javascript:return checkform(this);">

	<input type="hidden" name="idSinantropo" value="${s.id}"/>

	<h4 class="titolopagina">
	   	Registrazione di Decesso
	</h4>
	<jsp:include page="/jsp/sinantropi/menuSin.jsp"/>  
	<script type="text/javascript">
				ddtabmenu.definemenu("ddtabs2",2);
	</script> 
	
		
	<table class="tabella">
		
		<tr>
        	<th colspan="3">
        		Dati generali
        	</th>
        </tr>
        
        <tr class="even">
    		<td>
    			 Data Decesso<font color="red"> *</font>
    		</td>
    		<td>    		
    			 <input type="text" id="dataDecesso" name="dataDecesso" maxlength="32" size="50" readonly="readonly" style="width:246px;"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_3" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataDecesso",     // id of the input field
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
        
        <tr class='odd'>
	        <td>
	    		Causa del decesso<font color="red"> *</font>
    		</td>
    		<td>
    			<select name="causaMorte">
    				<option value="0" SELECTED>&lt;--- Selezionare la causa del decesso ---&gt;</option>
    						        
		        	 <c:forEach items="${listCMI}" var="temp" >	
		        	 	<option value="${temp.id }">${temp.description }</option>	        	 				
					</c:forEach>
		        </select>
	        </td>	        
	      </tr>
        
        <tr class='odd'>   
		<td>
		</td>
		<td>  
			<font color="red">* </font> Campi obbligatori
			<br> 	
			<input type="submit" value="Salva" />			
	 	</td>
	 	<td>
	 	</td>
 	</tr>

</table>

</form>