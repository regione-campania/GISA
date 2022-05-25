<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<script language="JavaScript" type="text/javascript" src="js/sinantropi/addZoo.js"></script>


<form action="sinantropi.AddZoo.us" name="form" method="post" onsubmit="javascript:return checkform(this);">
	
	<input type="hidden" name="interactiveMode" value="<c:out value="${interactiveMode}"/>"/>
	<h4 class="titolopagina">
	   	Registrazione di un nuovo animale proveniente dallo zoo
	</h4>
		
	<table class="tabella">
		
		<tr>
        	<th colspan="3">
        		Dati generali
        	</th>
        </tr>
		
		
		<tr class="even">
	        <td style="width:30%;">
	       		 Tipo identificativo<font color="red"> *</font>
	        </td>
	        <td colspan="2" style="width:70%;">
	        	<input type="radio" name="numeroAssegnato" value="false" <%=((String)request.getAttribute("interactiveMode") != null && ("Y").equals((String)request.getAttribute("interactiveMode"))) ? "disabled" : "checked" %> onclick="javascript: disableNumeroSin();"   > Identificativo generato dal sistema<br>
	        	<input type="radio" name="numeroAssegnato" value="true"  onclick="javascript: showNumeroSin(${identificativo});"> Numero Ufficiale dell'istituto faunistico <input type="text" <%=((String)request.getAttribute("identificativo") != null && !("").equals((String)request.getAttribute("identificativo"))) ? "disabled" : ""%> id="numeroUfficialeD" style="display:none;" name="numeroUfficiale" maxlength="25" size="25" value="<c:out value="${identificativo}"/>" />	<br>	
	        	<input type="radio" name="numeroAssegnato" value="true"  onclick="javascript: showMc(${identificativo});"> Microchip <input id="mc" style="display:none;" type="text" name="mc" maxlength="15" size="25" value="<c:out value="${identificativo}"/>" <%=((String)request.getAttribute("identificativo") != null && !("").equals((String)request.getAttribute("identificativo"))) ? "disabled" : ""%>/>	<br>	
	        	<input type="radio" name="numeroAssegnato" value="true"  onclick="javascript: showCodiceIspra(${identificativo});"> Codice ISPRA <input type="text" id="codiceIspra" style="display:none;" name="codiceIspra" maxlength="50" size="25" value="<c:out value="${identificativo}"/>" <%=((String)request.getAttribute("identificativo") != null && !("").equals((String)request.getAttribute("identificativo"))) ? "disabled" : ""%>/><br>	
	        </td>
        </tr>
	
		
		<tr>
        	<th colspan="3">
        		Dati dell'animale
        	</th>
        </tr>
	
		<tr class="even">
	        <td>
	       		 Classe<font color="red"> *</font>
	        </td>
	        <td colspan="2">
		        <select name="specieSinantropo" onChange="javascript: chooseSpecie(this.value)" style="width:30%;">
					<option value="0" SELECTED>&lt;--- Selezionare ---&gt;</option>
    				<option value="1" >  Uccello  </option>
            		<option value="2" >  Mammifero </option>
            		<option value="3" >  Rettile/Anfibio    </option>    
	    		</select>      
	        	<select name="tipologiaSinantropoU" id="uccelli" style="display:none;width:50%;">
					<option value="0">&lt;--- Selezionare Genere e Specie ---&gt;</option>
    				<c:forEach var="ss" items="${listUccelli}">
            			<option value="<c:out value="${ss.id}"/>">            				
            				${ss.description}
    					</option>
            		</c:forEach>
    			</select>      
	        	<select name="tipologiaSinantropoM" id="mammiferi" style="display:none;width:50%;">
					<option value="0">&lt;--- Selezionare Genere e Specie ---&gt;</option>
    				<c:forEach var="ss" items="${listMammiferi}">
            			<option value="<c:out value="${ss.id}"/>">            				
            				${ss.description}
    					</option>
            		</c:forEach>
    			</select>      
	        	<select name="tipologiaSinantropoRA" id="rettiliAnfibi" style="display:none;width:50%;">
					<option value="0">&lt;--- Selezionare Genere e Specie ---&gt;</option>
    				<c:forEach var="ss" items="${listRettiliAnfibi}">
            			<option value="<c:out value="${ss.id}"/>">            				
            				${ss.description}
    					</option>
            		</c:forEach>
    			</select>      
	        </td>
        </tr>
                
		<!--tr class="odd">
	        <td>
	       		 Specie
	        </td>
	        <td>
	        	<input type="text" name="razza" maxlength="50" size="50"/>		             
	        </td>
	        <td>
	        </td>
        </tr-->
		
		<tr class="odd">
	        <td>
	       		 Sesso<font color="red"> *</font>
	        </td>
	        <td>
	        	<select name="sesso" style="width:30%;">
					<option value="X" SELECTED>&lt;--- Selezionare ---&gt;</option>
    				<option value="M" >  Maschio  		</option>
            		<option value="F" >  Femmina 		</option>
            		<option value="ND">  Non Definito 	</option>    
	    		</select>      	             
	        </td>
	        <td>	      	  
	        </td>
        </tr>
	
		<tr class="even">
    		<td>
    			 Et&agrave;<font color="red"> *</font>
    		</td>
    		<td>    		
    			  <select name="idEta" style="width:30%;">
					<option value="-1">&lt;-- Selezionare --&gt;</option>
						<c:forEach items="${listEta}" var="temp" >
							<option value="${temp.id }">${temp.description }</option>
						</c:forEach>
				</select>
    		</td>
    		<td>
	        </td>
        </tr>
        
        <tr class="odd">
	        <td>
	       		 Note
	        </td>
	        <td>
		        <TEXTAREA NAME="note" COLS="40" ROWS="6"></TEXTAREA>        
	        </td>
	        <td>
	        </td>
        </tr>
        
        <tr>
        	<th colspan="3">
        		Zoo/Circo di provenienza
        	</th>
        </tr>
        
        
        
        <tr class="even">
    		<td>
    			 Data assegnazione
    		</td>
    		<td>    		
    			 <input type="text" id="dataDetenzioneDa" name="dataDetenzioneDa" maxlength="32" size="50" readonly="readonly"  id="comuneCatturaAV" style="width:30%;"/>
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
    			 Data fine
    		</td>
    		<td>    		
    			 <input type="text" id="dataDetenzioneA" name="dataDetenzioneA" maxlength="32" size="50" readonly="readonly" style="width:30%;"/>
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
    		<td>
    		</td>
        </tr>
        
        
        <tr class="even">
	        <td>
	       		 Tipologia detentore
	        </td>
	        <td>
        		<select name="tipologiaDetentore" onChange="javascript: chooseDetentore(this.value)">
					<option value="0" >&lt;--- Selezionare ---&gt;</option>
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
        
        <tr class="even">
	        <td>
	       		 Informazioni sul Detentore
	        </td>
	        <td>
        		<input type="text" name="detentorePrivatoNome" maxlength="50" size="50"/>      		             
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
						<option value="0" >&lt;--- Selezionare ---&gt;</option>
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
					<option value="X"   SELECTED>&lt;--- Selezionare ---&gt;</option>
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
						<option value="0">&lt;--- Selezionare ---&gt;</option>
	    				<c:forEach var="bn" items="${listComuniBN}">
	            			<option value="<c:out value="${bn.id}"/>">            				
	            				${bn.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneDetenzioneNA" style="display:none;">
		        	<select name="comuneDetenzioneNA">
						<option value="0">&lt;--- Selezionare ---&gt;</option>
	    				<c:forEach var="na" items="${listComuniNA}">
	            			<option value="<c:out value="${na.id}"/>">            				
	            				${na.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneDetenzioneSA" style="display:none;">
		        	<select name="comuneDetenzioneSA">
						<option value="0">&lt;--- Selezionare ---&gt;</option>
	    				<c:forEach var="sa" items="${listComuniSA}">
	            			<option value="<c:out value="${sa.id}"/>">            				
	            				${sa.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneDetenzioneCE" style="display:none;">
		        	<select name="comuneDetenzioneCE">
						<option value="0">&lt;--- Selezionare ---&gt;</option>
	    				<c:forEach var="ce" items="${listComuniCE}">
	            			<option value="<c:out value="${ce.id}"/>">            				
	            				${ce.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneDetenzioneAV" style="display:none;">
		        	<select name="comuneDetenzioneAV">
						<option value="0">&lt;--- Selezionare ---&gt;</option>
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
	
	
    <table class="tabella">
        
        
        
       <tr>
       	<th colspan="3">
       		Informazioni Rilascio
       	</th>
       </tr>
       
       <tr class="even">
    		<td style="width:30%;">
    			 Data
    		</td>
    		<td style="width:70%;">    		
    			 <input type="text" id="dataReimmissione" name="dataReimmissione" maxlength="32" size="50" readonly="readonly" style="width:30%;"/>
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
	       		 Provincia e Comune
	        </td>
	        <td>
	        	<select name="provinciaReimmissione" style="width:30%;" onchange="javascript: chooseProvinciaReimmissione(this.value);">
					<option value="X"   SELECTED>&lt;--- Selezionare ---&gt;</option>
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
						<option value="0">&lt;--- Selezionare ---&gt;</option>
	    				<c:forEach var="bn" items="${listComuniBN}">
	            			<option value="<c:out value="${bn.id}"/>">            				
	            				${bn.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneReimmissioneNA" style="display:none;">
		        	<select name="comuneReimmissioneNA">
						<option value="0">&lt;--- Selezionare ---&gt;</option>
	    				<c:forEach var="na" items="${listComuniNA}">
	            			<option value="<c:out value="${na.id}"/>">            				
	            				${na.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneReimmissioneSA" style="display:none;">
		        	<select name="comuneReimmissioneSA">
						<option value="0">&lt;--- Selezionare ---&gt;</option>
	    				<c:forEach var="sa" items="${listComuniSA}">
	            			<option value="<c:out value="${sa.id}"/>">            				
	            				${sa.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneReimmissioneCE" style="display:none;">
		        	<select name="comuneReimmissioneCE">
						<option value="0">&lt;--- Selezionare ---&gt;</option>
	    				<c:forEach var="ce" items="${listComuniCE}">
	            			<option value="<c:out value="${ce.id}"/>">            				
	            				${ce.description}
	    					</option>
	            		</c:forEach>
	    			</select>      
		        </div>
		        <div id="comuneReimmissioneAV" style="display:none;">
		        	<select name="comuneReimmissioneAV">
						<option value="0">&lt;--- Selezionare ---&gt;</option>
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
	       		 Luogo
	        </td>
	        <td>
	        	<input type="text" name="luogoReimmissione" maxlength="50" size="50"/>		             
	        </td>
	        <td>
	        </td>
        </tr>
             
        
	</table>
		
	<tr class='odd'>   
		<td>
		</td>
		<td>
	 	</td>
		<td>  
			<font color="red">* </font> Campi obbligatori
			<br> 	
			<input type="submit" value="Salva" "/>
			<input type="button" value="Annulla" onclick="attendere(), location.href='Home.us'">
	 	</td>
	 	
 	</tr>


</form>