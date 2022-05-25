<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/jmesa.tld" prefix="jmesa" %>
<%@page import="java.util.Date"%>

<script language="JavaScript" type="text/javascript" src="js/vam/cc/dimissioni/add.js"></script>

<c:choose>
	<c:when test="${cc.accettazione.animale.decedutoNonAnagrafe}">
		<fmt:formatDate value="${cc.accettazione.animale.dataMorte}" pattern="dd/MM/yyyy" var="dataMorte"/> 
	</c:when>
	<c:otherwise>
		<fmt:formatDate value="${res.dataEvento}" pattern="dd/MM/yyyy" var="dataMorte"/> 
	</c:otherwise>
</c:choose>

<form action="vam.cc.dimissioni.Add.us" name="form" method="post" id="form" class="marginezero" onsubmit="javascript:return checkform(this);">
           
    <fmt:formatDate type="date" value="${cc.dataApertura}" pattern="dd/MM/yyyy" var="dataAperturaCc"/> 
    <input type="hidden" name="dataApertura" id="dataApertura" value="${dataAperturaCc}">
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>   
    <h4 class="titolopagina">
     		Registrazione Dimissioni
    </h4>
    <table class="tabella">
    
    <tr class='even'>
    		<td>
    			 Data Dimissione
    		</td>
    		<td>    		
    			 <fmt:formatDate type="date" value="<%=new Date() %>" pattern="dd/MM/yyyy" var="dataOdierna"/> 	    			
    			 <input type="text" id="dataChiusura" name="dataChiusura" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${dataOdierna}"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataChiusura",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      	// format of the input field
       						button         :    "id_img_1",  		// trigger for the calendar (button ID)
       						// align          :    "Tl",           	// alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>    		
        </tr>
                
             
        <tr class='odd'>
	        <td>
	    		Destinazione dell'animale
    		</td>
    		<td>
		        <select name="destinazioneAninale" onchange="javascript: return abilitaDisabilitaDatiMorte('${dataMorte}','${cc.accettazione.animale.decedutoNonAnagrafe}');">
		        	 <c:forEach items="${destinazioneAnimale}" var="da" >
		        	 	<c:if test="${dataMorte==null || da.id==2}">	
		        	 		<option value="${da.id }">
		        	 			<c:if test="${cc.accettazione.animale.lookupSpecie.id==1 or cc.accettazione.animale.lookupSpecie.id==2}">
		        	 				${da.description }
		        	 			</c:if>
		        	 			<c:if test="${cc.accettazione.animale.lookupSpecie.id==3}">
		        	 				${da.descriptionSinantropo }
		        	 			</c:if>
		        	 		</option>	        	 				
						</c:if>
					</c:forEach>
		        </select>
	        </td>
	        <td>
	        </td>
        </tr>
        
        <tr>
        	<th colspan="2">
        		Informazioni Decesso
        	</th>
        </tr>
        
        <tr class='even'>
    		<td>
    			 Data<font color="red"> *</font>
    		</td>
    		<td>  
    			 <fmt:formatDate type="date" value="${cc.accettazione.animale.dataMorte}" pattern="dd/MM/yyyy" var="data"/>   		
    			 <input type="text" id="dataMorte" name="dataMorte" maxlength="32" size="50" readonly="readonly" style="width:246px;" disabled="disabled"/>
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
			<input type="radio" name="dataMorteCerta" id="dataMorteCertaT" value="false" disabled="disabled"/><label for="dataMorteCertaT">Presunta</label>	
			<br>
			<input type="radio" name="dataMorteCerta" id="dataMorteCertaF" value="true" checked disabled="disabled"/> <label for="dataMorteCertaF">Certa</label>				
    		</td>
        </tr>
                
             
        <tr class='odd'>
	        <td>
	    		Probabile causa<font color="red"> *</font>
    		</td>
    		<td>
		        <select name="causaMorteIniziale" disabled="disabled">
		        	<option value="">&lt;--Selezionare un valore--&gt;</option>	
		        	 <c:forEach items="${listCMI}" var="temp" >	
		        	 	<option value="${temp.id }">${temp.description }</option>	        	 				
					</c:forEach>
		        </select>
	        </td>
	        <td>
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
        
        
        
        <tr class='even' style="display:none;">
	        <td>
	    		<!-- <input type="checkbox" name="op_${opRichieste.esameNecroscopico}"  id="esameNecroscopico"  disabled="disabled"/> Esame Necroscopico</br>  -->
	    		<input type="checkbox" name="op_${opRichieste.smaltimentoCarogna}" id="smaltimentoCarogna" disabled="disabled"/> Trasporto Spoglie
    		</td>
    		<td>
	        </td>
	        <td>
	        </td>
        </tr>
        
        
        <tr class='even'>
        	<td align="center">
        		<input type="submit" value="Salva" />
    			<input type="button" value="Annulla" onclick="attendere(), location.href='vam.cc.Detail.us'">
        	</td>
    		<td>    			
    		</td>  
    		<td>    			
    		</td>    		
        </tr>
	</table>
</form>

<script type="text/javascript">
	if(form.destinazioneAninale.value=2)
	{
		abilitaDisabilitaDatiMorte('${dataMorte}','${cc.accettazione.animale.decedutoNonAnagrafe}');
	}
</script>