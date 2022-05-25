<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@page import="java.util.Date"%>

<script language="JavaScript" type="text/javascript" src="js/vam/cc/decessi/add.js"></script>

<form action="vam.cc.decessi.Add.us" name="form" method="post" id="form" class="marginezero" onsubmit="javascript:return checkform(this);">
           
  
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    <h4 class="titolopagina">
     		Registrazione Decesso
    </h4>   
       
    
    <table class="tabella">
    
    <tr class='even'>
    		<td>
    			 Data Decesso<font color="red"> *</font>
    		</td>
    		<td>    		
    			 <input type="text" id="dataMorte" name="dataMorte" maxlength="32" size="50" readonly="readonly" style="width:246px;"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataMorte",     // id of the input field
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
			<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false"/><label for="dataMorteCertaT">Presunta</label>	
			<br>
			<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true" checked/> <label for="dataMorteCertaF">Certa</label>				
    		</td>
        </tr>
                
             
        <tr class='odd'>
	        <td>
	    		Probabile causa del decesso<font color="red"> *</font>
    		</td>
    		<td>
		        <select name="causaMorteIniziale">
		        	 <c:forEach items="${listCMI}" var="temp" >	
		        	 	<option value="${temp.id }">${temp.description }</option>	        	 				
					</c:forEach>
		        </select>
	        </td>
	        <td>
	        </td>
        </tr>
        
        <tr class='even'>
	        <td>
	    		Successiva necroscopia
    		</td>
    		<td>
		        <input type="checkbox" name="successivaNecroscopia" id="successivaNecroscopia" />
	        </td>
	        <td>
	        </td>
        </tr>
        
        
        <tr class='odd'>
        	<td>
				<font color="red">* </font> Campi obbligatori
			</td>
        	<td align="center">
        		<input type="submit" value="Salva" />
    			<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.Detail.us'">
        	</td>
    		<td>    			
    		</td>     		   		
        </tr>
	</table>
</form>
        