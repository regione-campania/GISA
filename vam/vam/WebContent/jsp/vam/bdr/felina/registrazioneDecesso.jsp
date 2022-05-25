<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@page import="it.us.web.bean.BUtente"%>
<%@page import="java.util.Date"%>

<form action="vam.bdr.felina.Decesso.us" method="post">

	<input type="submit" value="Procedi" onclick="return checkForm() && myConfirm('Sicuro di voler procedere col salvataggio della registrazione di decesso in BDR?');" />
	
	<fieldset>
		<legend>Registrazione Decesso ${accettazione.animale.lookupSpecie.description } ${accettazione.animale.identificativo }</legend>
		
		<input type="hidden" name="idAccettazione" value="${accettazione.id }" />
		
		<table class="tabella">
			<tr>
				<td>Data</td>
				<td>
					<input 
						type="text" 
						value="<fmt:formatDate type="date" value="<%=new Date() %>" pattern="dd/MM/yyyy" />"
						id="dataMorte" 
						name="dataMorte" 
						maxlength="10" 
						size="10" 
						readonly="readonly" />
						<img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
						<script type="text/javascript">
							Calendar.setup({
								inputField  :    "dataMorte",      // id of the input field
								ifFormat    :    "%d/%m/%Y",  // format of the input field
								button      :    "id_img_1",  // trigger for the calendar (button ID)
								// align    :    "rl,      // alignment (defaults to "Bl")
								singleClick :    true,
								timeFormat	:   "24",
								showsTime	:   false
							});					    
						</script>
				</td>
				<td>
					<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false"/> 
					<label for="dataMorteCertaT">Presunta</label>	
					<br>
					<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true" checked/> <label for="dataMorteCertaF">Certa</label>				
	    		</td>
			</tr>
			<tr>
				<td>Probabile causa</td>
				<td>
			        <select name="causaMorteIniziale">
			        	 <c:forEach items="${listCMI}" var="temp" >	
			        	 	<option value="${temp.id }">${temp.description }</option>	        	 				
						</c:forEach>
			        </select>
				</td>
			</tr>
			<tr>
		        <td>
		       		 Provincia e Comune<font color="red"> *</font>
		        </td>
		        <td>
		        	<select id="provincia"  name="provincia" onChange="javascript: chooseProvincia(this.value)">
						<option value=""  SELECTED>&lt;--- Selezionare la provincia ---&gt;</option>
	    				<option value="AV" >  Avellino  	</option>
	    				<option value="BN" >  Benevento  </option>
	            		<option value="CE" >  Caserta 	</option>
	            		<option value="NA" >  Napoli 	</option>  
	            		<option value="SA" >  Salerno 	</option>  
		    		</select>      		             
		        </td>
		        <td>    
		        	<div id="comuneBN" style="display:none;">
			        	<select name="comuneBN" id="comuneChooserBN">
							<option value="0">&lt;--- Selezionare il comune BN  ---&gt;</option>
		    				<c:forEach var="bn" items="${listComuniBN}">
		            			<option value="<c:out value="${bn.codiceIstat}"/>">            				
		            				${bn.description}
		    					</option>
		            		</c:forEach>
		    			</select>      
			        </div>
			        <div id="comuneNA" style="display:none;">
			        	<select name="comuneNA" id="comuneChooserNA">
							<option value="0">&lt;--- Selezionare il comune NA  ---&gt;</option>
		    				<c:forEach var="na" items="${listComuniNA}">
		            			<option value="<c:out value="${na.codiceIstat}"/>">            				
		            				${na.description}
		    					</option>
		            		</c:forEach>
		    			</select>      
			        </div>
			        <div id="comuneSA" style="display:none;">
			        	<select name="comuneSA" id="comuneChooserSA">
							<option value="0">&lt;--- Selezionare il comune SA  ---&gt;</option>
		    				<c:forEach var="sa" items="${listComuniSA}">
		            			<option value="<c:out value="${sa.codiceIstat}"/>">            				
		            				${sa.description}
		    					</option>
		            		</c:forEach>
		    			</select>      
			        </div>
			        <div id="comuneCE" style="display:none;">
			        	<select name="comuneCE" id="comuneChooserCE">
							<option value="0">&lt;--- Selezionare il comune CE  ---&gt;</option>
		    				<c:forEach var="ce" items="${listComuniCE}">
		            			<option value="<c:out value="${ce.codiceIstat}"/>">            				
		            				${ce.description}
		    					</option>
		            		</c:forEach>
		    			</select>      
			        </div>
			        <div id="comuneAV" style="display:none;">
			        	<select name="comuneAV" id="comuneChooserAV">
							<option value="0">&lt;--- Selezionare il comune AV  ---&gt;</option>
		    				<c:forEach var="av" items="${listComuniAV}">
		            			<option value="<c:out value="${av.codiceIstat}"/>">            				
		            				${av.description}
		    					</option>
		            		</c:forEach>
		    			</select>      
			        </div>
		        </td>
        	</tr>
        
        
	       	<tr >
	        	<td>
	        		Indirizzo<font color="red"> *</font>
	        	</td>
	        	<td>
	        		<input type="text" name="indirizzo" id="indirizzo" maxlength="25" size="25"/>
	        	</td>
	        	<td>
	        	</td>
	       	</tr>	
       	
	       	<tr>
	        	<td>
	        		Note 
	        	</td>
	        	<td>
	        		<TEXTAREA NAME="note" COLS=40 ROWS=3></TEXTAREA>         		
	        	</td>
	        	<td>
	        	</td>
	       	</tr>  
		</table>
	</fieldset>
	
	<br/>
	<input type="submit" value="Procedi" onclick="return checkForm() && myConfirm('Sicuro di voler procedere col salvataggio della registrazione di decesso in BDR?');" />
	
</form>


<script type="text/javascript">
function chooseProvincia(provincia) {
	if (provincia == 'BN') {
		document.getElementById( 'comuneBN' ).style.display = "";
		document.getElementById( 'comuneNA' ).style.display = "none";
		document.getElementById( 'comuneSA' ).style.display = "none";
		document.getElementById( 'comuneCE' ).style.display = "none";
		document.getElementById( 'comuneAV' ).style.display = "none";
		
	}
	else if (provincia == 'NA') {
		document.getElementById( 'comuneBN' ).style.display = "none";
		document.getElementById( 'comuneNA' ).style.display = "";
		document.getElementById( 'comuneSA' ).style.display = "none";
		document.getElementById( 'comuneCE' ).style.display = "none";
		document.getElementById( 'comuneAV' ).style.display = "none";
	}	
	else if (provincia == 'AV') {
		document.getElementById( 'comuneBN' ).style.display = "none";
		document.getElementById( 'comuneNA' ).style.display = "none";
		document.getElementById( 'comuneSA' ).style.display = "none";
		document.getElementById( 'comuneCE' ).style.display = "none";
		document.getElementById( 'comuneAV' ).style.display = "";
	}
	else if (provincia == 'SA') {
		document.getElementById( 'comuneBN' ).style.display = "none";
		document.getElementById( 'comuneNA' ).style.display = "none";
		document.getElementById( 'comuneSA' ).style.display = "";
		document.getElementById( 'comuneCE' ).style.display = "none";
		document.getElementById( 'comuneAV' ).style.display = "none";
	}
	else if (provincia == 'CE') {
		document.getElementById( 'comuneBN' ).style.display = "none";
		document.getElementById( 'comuneNA' ).style.display = "none";
		document.getElementById( 'comuneSA' ).style.display = "none";
		document.getElementById( 'comuneCE' ).style.display = "";
		document.getElementById( 'comuneAV' ).style.display = "none";
	}
	else if (provincia == 'X') {
		document.getElementById( 'comuneBN' ).style.display = "none";
		document.getElementById( 'comuneNA' ).style.display = "none";
		document.getElementById( 'comuneSA' ).style.display = "none";
		document.getElementById( 'comuneCE' ).style.display = "none";
		document.getElementById( 'comuneAV' ).style.display = "none";
	}
}

function checkForm()
{
	var provincia = document.getElementById("provincia");
	if(provincia.value=="")
	{
		alert("Selezionare la provincia");
		document.getElementById('provincia').focus();
		return false;	
	}
	var comune = document.getElementById("comuneChooser"+provincia.value);
	if(provincia.value!="" && comune.value=="0")
	{
		alert("Selezionare il comune");
		document.getElementById('provincia').focus();
		return false;	
	}
	if(document.getElementById("indirizzo").value=="")
	{
		alert("Inserire l'indirizzo");
		document.getElementById('indirizzo').focus();
		return false;	
	}
	return true;
}
</script>
