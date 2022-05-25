<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/sinantropi/reimmissioni/add.js"></script>


<form action="sinantropi.reimmissioni.Add.us" name="form" method="post" onsubmit="javascript:return checkform(this);">

	<input type="hidden" name="idCattura" value="${c.id}"/>
	<input type="hidden" name="oldCodiceIspra" id="oldCodiceIspra" value="${c.sinantropo.codiceIspra}"/>

	<h4 class="titolopagina">
	   	Registrazione Rilascio
	</h4>
	<jsp:include page="/jsp/sinantropi/menuCatture.jsp"/>
	<script type="text/javascript">
			ddtabmenu.definemenu("ddtabs2",3);
	</script> 
		
	<table class="tabella">
	      
        
       <tr>
       	<th colspan="3">
       		Informazioni Rilascio
       	</th>
       </tr>
       
       <tr class="even">
    		<td>
    			 Data<font color="red"> *</font>
    		</td>
    		<td>    		
    			 <input type="text" id="dataReimmissione" name="dataReimmissione" maxlength="32" size="50" readonly="readonly" style="width:246px;"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_5" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataReimmissione",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_5",  // trigger for the calendar (button ID)
       						// align          :    "Tl",           // alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>
        </tr>
        
        <tr class="odd">
	        <td>
	       		 Provincia e Comune Rilascio<font color="red"> *</font>
	        </td>
	        <td>
	        	<select name="provinciaReimmissione" onChange="javascript: chooseProvinciaReimmissione(this.value)">>
					<option value="X"   SELECTED>&lt;--- Selezionare la provincia ---&gt;</option>
    				<option value="AV" >  Avellino  	</option>
    				<option value="BN" >  Benevento  </option>
            		<option value="CE" >   Caserta 	</option>
            		<option value="NA" >  Napoli 	</option>  
            		<option value="SA" >  Salerno 	</option>  
	    		</select>      		             
	        </td>
	        <td>    
	        	<div id="comuneReimmissioneBN" style="display:none;">
		        	<select name="comuneReimmissioneBN">
						<option value="0">&lt;--- Selezionare il comune BN  ---&gt;</option>
	    				<c:forEach var="bn" items="${listComuniBN}">
	            			<option value="<c:out value="${bn.id}"/>">            				
	            				${bn.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneReimmissioneNA" style="display:none;">
		        	<select name="comuneReimmissioneNA">
						<option value="0">&lt;--- Selezionare il comune NA  ---&gt;</option>
	    				<c:forEach var="na" items="${listComuniNA}">
	            			<option value="<c:out value="${na.id}"/>">            				
	            				${na.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneReimmissioneSA" style="display:none;">
		        	<select name="comuneReimmissioneSA">
						<option value="0">&lt;--- Selezionare il comune SA  ---&gt;</option>
	    				<c:forEach var="sa" items="${listComuniSA}">
	            			<option value="<c:out value="${sa.id}"/>">            				
	            				${sa.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneReimmissioneCE" style="display:none;">
		        	<select name="comuneReimmissioneCE">
						<option value="0">&lt;--- Selezionare il comune CE  ---&gt;</option>
	    				<c:forEach var="ce" items="${listComuniCE}">
	            			<option value="<c:out value="${ce.id}"/>">            				
	            				${ce.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneReimmissioneAV" style="display:none;">
		        	<select name="comuneReimmissioneAV">
						<option value="0">&lt;--- Selezionare il comune AV  ---&gt;</option>
	    				<c:forEach var="av" items="${listComuniAV}">
	            			<option value="<c:out value="${av.id}"/>">            				
	            				${av.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
	        </td>
        </tr>
                        
        
        <tr class="even">
	        <td>
	       		 Luogo Rilascio<font color="red"> *</font>
	        </td>
	        <td>
	        	<input type="text" name="luogoReimmissione" maxlength="50" size="50"/>		             
	        </td>
	        <td>
	        </td>
        </tr>
        
        <tr class="odd">
	        <td>
	       		 Codice ISPRA
	        </td>
	        <td>
	        	<input type="text" name="codiceIspra" id="codiceIspra" value="${c.sinantropo.codiceIspra}" maxlength="50" size="50" />		             
	        </td>
	        <td>
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