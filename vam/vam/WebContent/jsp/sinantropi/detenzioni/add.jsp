<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/sinantropi/detenzioni/add.js"></script>


<form action="sinantropi.detenzioni.Add.us" name="form" method="post" onsubmit="javascript:return checkform(this);">

	<input type="hidden" name="idSinantropo" value="${s.id}"/>
	<input type="hidden" name="idCattura" 	 value="${c.id}"/>

	<h4 class="titolopagina">
	   	Registrazione di una nuova Detenzione
	</h4>
	<jsp:include page="/jsp/sinantropi/menuCatture.jsp"/>
	<script type="text/javascript">
			ddtabmenu.definemenu("ddtabs2",2);
	</script> 
		
	<table class="tabella">
		        
        
        <tr>
        	<th colspan="3">
        		Informazioni su Detenzione
        	</th>
        </tr>
        
        
        
        <tr class="even">
    		<td>
    			 Data di assegnazione in detenzione<font color="red"> *</font>
    		</td>
    		<td>    		
    			 <input type="text" id="dataDetenzioneDa" name="dataDetenzioneDa" maxlength="32" size="50" readonly="readonly" style="width:246px;"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_4" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataDetenzioneDa",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_4",  // trigger for the calendar (button ID)
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
    			 Data di fine detenzione
    		</td>
    		<td>    		
    			 <input type="text" id="dataDetenzioneA" name="dataDetenzioneA" maxlength="32" size="50" readonly="readonly" style="width:246px;"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_6" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataDetenzioneA",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_6",  // trigger for the calendar (button ID)
       						// align          :    "Tl",           // alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>
        </tr>
        
        
        <tr class="even">
	        <td>
	       		 Tipologia di detentore<font color="red"> *</font>
	        </td>
	        <td>
        		<select name="tipologiaDetentore" onChange="javascript: chooseDetentore(this.value)">
					<option value="0" >&lt;--- Selezionare la tipologia di detentore  ---&gt;</option>
    				<c:forEach var="det" items="${listDetentori}">
            			<option value="<c:out value="${det.id}"/>" >            				
            				${det.description}
    					</option>
            		</c:forEach>
    			</select>      		             
	        </td>
	        <td>		       
	        </td>
        </tr>
        
    
        
        
        
       </table>
                
        
       <div id="detentorePrivato" style="display:none;">
		 <table class="tabella">
		 <tr>
        	<th colspan="3">
        		Informazioni sul Detentore privato
        	</th>
        </tr>
        		    
		    <tr class="even">
		        <td>
		       		 Nome<font color="red"> *</font>
		        </td>
		        <td>
		        	<input type="text" name="detentorePrivatoNome" maxlength="50" size="50"/>		             
		        </td>
		        <td>
		        </td>
            </tr>        
        	<tr class="odd">
		        <td>
		       		 Cognome<font color="red"> *</font>
		        </td>
		        <td>
		        	<input type="text" name="detentorePrivatoCognome" maxlength="50" size="50"/>		             
		        </td>
		        <td>
		        </td>
       		 </tr>        
	        <tr class="even">
		        <td>
		       		 Codice Fiscale<font color="red"> *</font>
		        </td>
		        <td>
		        	<input type="text" name="detentorePrivatoCodiceFiscale" maxlength="16" size="50"/>		             
		        </td>
		        <td>
		        </td>
	        </tr>        
	        <tr class="odd">
		        <td>
		       		 Tipologia Documento
		        </td>
		        <td>
		        	<select name="tipologiaDocumento">
						<option value="0" >&lt;--- Selezionare la tipologia di documento ---&gt;</option>
	    				<c:forEach var="td" items="${listTipologiaDocumenti}">
	            			<option value="<c:out value="${td.id}"/>" >            				
	            				${td.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </td>	       
		        <td>
			         <div id="numeroDocumento">			         
			        	Numero Documento <input type="text" name="detentorePrivatoNumeroDocumento" maxlength="20" size="20"/>		             
			        </div>
		        </td>	        
        	</tr> 
        	<tr class="even">
		        <td>
		       		 Email
		        </td>
		        <td>
		        	<input type="text" name="detentorePrivatoEmail" maxlength="16" size="50"/>		             
		        </td>
		        <td>
		        </td>
	        </tr>        
	        <tr class="odd">
		        <td>
		       		 Telefono
		        </td>
		        <td>
		        	<input type="text" name="detentorePrivatoTelefono" maxlength="16" size="50"/>		             
		        </td>
		        <td>
		        </td>
	        </tr> 
	        
	        <tr class="even">
	        <td>
	       		 Provincia e Comune di detenzione<font color="red"> *</font>
	        </td>
	        <td>
	        	<select name="provinciaDetenzione" onChange="javascript: chooseProvinciaDetenzione(this.value)">
					<option value="X"   SELECTED>&lt;--- Selezionare la provincia ---&gt;</option>
    				<option value="AV" >  Avellino  	</option>
    				<option value="BN" >  Benevento  </option>
            		<option value="CE" >   Caserta 	</option>
            		<option value="NA" >  Napoli 	</option>  
            		<option value="SA" >  Salerno 	</option>  
	    		</select>      		             
	        </td>
	        <td>    
	        	<div id="comuneDetenzioneBN" style="display:none;">
		        	<select name="comuneDetenzioneBN">
						<option value="0">&lt;--- Selezionare il comune BN  ---&gt;</option>
	    				<c:forEach var="bn" items="${listComuniBN}">
	            			<option value="<c:out value="${bn.id}"/>">            				
	            				${bn.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneDetenzioneNA" style="display:none;">
		        	<select name="comuneDetenzioneNA">
						<option value="0">&lt;--- Selezionare il comune NA  ---&gt;</option>
	    				<c:forEach var="na" items="${listComuniNA}">
	            			<option value="<c:out value="${na.id}"/>">            				
	            				${na.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneDetenzioneSA" style="display:none;">
		        	<select name="comuneDetenzioneSA">
						<option value="0">&lt;--- Selezionare il comune SA  ---&gt;</option>
	    				<c:forEach var="sa" items="${listComuniSA}">
	            			<option value="<c:out value="${sa.id}"/>">            				
	            				${sa.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneDetenzioneCE" style="display:none;">
		        	<select name="comuneDetenzioneCE">
						<option value="0">&lt;--- Selezionare il comune CE  ---&gt;</option>
	    				<c:forEach var="ce" items="${listComuniCE}">
	            			<option value="<c:out value="${ce.id}"/>">            				
	            				${ce.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneDetenzioneAV" style="display:none;">
		        	<select name="comuneDetenzioneAV">
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
                        
        
        <tr class="odd">
	        <td>
	       		 Luogo di detenzione<font color="red"> *</font>
	        </td>
	        <td>
	        	<input type="text" name="luogoDetenzione" maxlength="50" size="50"/>		             
	        </td>
	        <td>
	        </td>
        </tr>
	                      
		</table>
	</div>  
        
        
    
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


</form>