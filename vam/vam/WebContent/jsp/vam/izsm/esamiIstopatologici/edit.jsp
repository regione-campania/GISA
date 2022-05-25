<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="java.util.Date"%>
<script type="text/javascript" src="js/vam/cc/esamiIstopatologici/add.js"></script>

<form action="vam.izsm.esamiIstopatologici.Edit.us" name="form" method="post" class="marginezero" onsubmit="javascript:return checkformIzsm(this);">    

    <h4 class="titolopagina">		
		Gestione Esame Istopatologico 
		
		<input type="hidden" name="idEsame" value="${esame.id }" />	
		<input type="hidden" name="idDiagnosiHidden" id="idDiagnosiHidden" value="${esame.tipoDiagnosi.id}" />
		<input type="hidden" name="whoUmanaPadreHidden" id="whoUmanaPadreHidden" value="${esame.whoUmana.padre.id }" />	
		<input type="hidden" name="whoUmanaHidden" id="whoUmanaHidden" value="${esame.whoUmana.id }" />
		<input type="hidden" name="idCc" value="${esame.cartellaClinica.id }" />	
    </h4>
    
    <br>
    <c:set var="tipo" scope="request" value="stampaIstoSingolo"/>
	<c:set var="idEsame" scope="request" value="${esame.id }"/>
	<c:import url="../../jsp/documentale/home.jsp"/>

	<input type="button" value="Immagini" onclick="javascript:avviaPopup( 'vam.cc.esamiIstopatologici.GestioneImmagini.us?id=${esame.id }&cc=${cc.id}' );" />
	<c:if test="${esame.outsideCC==false}">
	<c:choose>
		<c:when test="${cc!=null}">
			<input type="button" value="Necroscopia" onclick="javascript:if(${cc.autopsia!=null}){avviaPopup( 'vam.cc.autopsie.DetailDaIsto.us?idCc=${cc.id}' );}else{if(${cc.accettazione.animale.necroscopiaNonEffettuabile==false}){alert('Necroscopia non inserita');}else{alert('Necroscopia dichiarata come non effettuabile');}}" />
		</c:when>
		<c:otherwise>
			<input type="button" value="Necroscopia" onclick="javascript:if(${esame.cartellaClinica.autopsia!=null}){avviaPopup( 'vam.cc.autopsie.DetailDaIsto.us?idCc=${esame.cartellaClinica.id}' );}else{if(${esame.cartellaClinica.accettazione.animale.necroscopiaNonEffettuabile==false}){alert('Necroscopia non inserita');}else{alert('Necroscopia dichiarata come non effettuabile');}}" />			
		</c:otherwise>
	</c:choose>
	</c:if>

	<table class="tabella">
		<tr>
    		<th colspan="3">
    			Anagrafica Animale
    		</th>
    	</tr>
    	
    	<tr class='even'>
    		<td style="width:30%">
    			Tipologia
    		</td>
    		<td style="width:70%">
    			<c:out value="${animale.lookupSpecie.description}"/>
    		</td>  
    		<td>
    		</td>  		
        </tr>
    	
    	<tr class='odd'>
    		<td>
    			Identificativo
    		</td>
    		<td>
    			<c:out value="${animale.identificativo}"/>
    		</td>  
    		<td>
    		</td>  		
        </tr>
        
        <tr class='even'>
    		<c:choose>
				<c:when test="${animale.lookupSpecie.id==3}">
					<td>
						Et&agrave;
					</td>
					<td>
						<fmt:formatDate type="date" value="${animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita" />
						${animale.eta} <c:if test="${dataNascita}">(${dataNascita})</c:if>
					</td>
				</c:when>
				<c:otherwise>
					<td>
						Data nascita 
					</td>
					<td>
						<fmt:formatDate type="date" value="${animale.dataNascita }" pattern="dd/MM/yyyy" var="dataNascita"  />
  						${dataNascita}
					</td>
				</c:otherwise>
			</c:choose>  
    		<td>
    		</td> 		
        </tr>
        
        <c:set scope="request" var="anagraficaAnimale" value="${anagraficaAnimale}"/>
        <c:import url="../vam/anagraficaAnimale.jsp"/>
        
       <c:if test="${animale.dataMorte!=null || res.dataEvento!=null}">
       <c:choose>
        	<c:when test="${animale.decedutoNonAnagrafe == true}">						
				<fmt:formatDate type="date" value="${animale.dataMorte}" pattern="dd/MM/yyyy" var="dataMorte"/>
			</c:when>
			<c:otherwise>
				<fmt:formatDate type="date" value="${res.dataEvento}" pattern="dd/MM/yyyy" var="dataMorte"/>
			</c:otherwise>	
		</c:choose>	 
        
        <tr class='odd'>
   			<td>
   				Data del decesso
   			</td>
   			<td>${dataMorte}
				<c:choose>
					<c:when test="${animale.decedutoNonAnagrafe == true}">&nbsp;				
						<c:out value="${cc.accettazione.animale.dataMorteCertezza}"/> 
					</c:when>
					<c:otherwise>
						<c:out value="${res.dataMorteCertezza}"/>&nbsp;
					</c:otherwise>	
				</c:choose>	 
        	</td>
   		</tr>
    	
   		<tr class='even'>
      	  <td>
    			Probabile causa del decesso
   			</td>
   			<td>    
   				<c:choose>
    				<c:when test="${animale.decedutoNonAnagrafe}">
    					<c:out value="${cc.accettazione.animale.causaMorte.description}"/>
    				</c:when>
    				<c:otherwise>
    					<c:out value="${res.decessoValue}"/>
    				</c:otherwise>
    			</c:choose>	        	        
        	</td>
       		<td>
        	</td>
       	</tr>
       	</c:if>
       	
       	<c:if test="${animale.clinicaChippatura!=null}">
			<tr class='odd'>
				<td>Microchippato nella clinica </td>
				<td>${cc.accettazione.animale.clinicaChippatura.nome}</td>
				<td></td>
			</tr>
		</c:if> 
	</table>

	<c:if test="${cc!=null}">
	<table class="tabella">
		<tr>
    		<th colspan="3">
    			Dettagli Cartella Clinica
    		</th>
    	</tr>
	     <tr class='even'>
    		<td style="width:30%">
    			Numero cartella clinica
    		</td>
    		<td style="width:70%">
    			<c:out value="${cc.numero}"/>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Fascicolo Sanitario
    		</td>
    		<td>
    			<c:out value="${cc.fascicoloSanitario.numero}"/>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Numero di riferimento accettazione
    		</td>
    		<td>
	    		${cc.accettazione.progressivoFormattato}    			
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			 Data di apertura
    		</td>
    		<td>
    			 <fmt:formatDate pattern="dd/MM/yyyy" var="dataApertura" value="${cc.dataApertura}"/>
    			 ${dataApertura}
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			<c:choose>
    				<c:when test="${cc.dataChiusura==null}">
    					Data di chiusura
    				</c:when>
    				<c:otherwise>
    					<b>Data di chiusura</b>
    				</c:otherwise>
    			</c:choose>
    		</td>
    		<td>
    			 <fmt:formatDate pattern="dd/MM/yyyy" var="dataChiusura" value="${cc.dataChiusura}"/>
    			 <b>${dataChiusura}</b>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Inserita da
    		</td>
    		<td>
    			 ${cc.enteredBy}
    		</td>
    		<td>
    		</td>
        </tr>
        
        <c:choose> 
			<c:when test="${cc.ccMorto}"> 
				<tr class='even'>
    				<td>
    					Tipologia di cartella clinica
    				</td>
    				<td>
    					Necroscopica
    				</td>
   				</tr> 
	    	</c:when>
	    	<c:otherwise>
		    	<tr class='even'>
    				<td>
    					Tipologia di cartella clinica
    				</td>
    				<td>
    					<c:choose> 
    						<c:when test="${cc.dayHospital == true}"> 
    							Day Hospital
    						</c:when>    				
		    				<c:otherwise> 
    							Degenza 
    						</c:otherwise>    				
		    			</c:choose> 
    				</td>
    				<td></td>
		        </tr> 
			</c:otherwise>
		</c:choose>	 
		</table>
    </c:if>
	
    <table class="tabella">
        <tr class="even">
    		<th colspan="3">
    			Esame Istopatologico numero ${esame.numero} , richiesto  
    			in data <fmt:formatDate type="date" value="${esame.dataRichiesta}" pattern="dd/MM/yyyy" />
    		</th>
    	</tr>
    	
    	<tr class='odd'>
    		<td>
    			 Laboratorio di destinazione 
    		</td>
    		<td>
				${esame.lass.description}
    		</td>
    		<td>
    		</td>
        </tr>
    	
    	<tr class="even">
    		<td style="width:30%">
    			 Numero rif. Mittente
    		</td>
    		<td style="width:70%">  
    			${esame.numeroAccettazioneSigla}
    		</td>
        </tr>

		<tr class="odd">
    		<td style="width:30%">
    			 Tipo Prelievo
    		</td>
    		<td style="width:70%">  
    			${esame.tipoPrelievo.description }
    		</td>
        </tr>     
        
        <tr>
    		<td style="width:30%">
    			 Tumori Precedenti
    		</td>
    		<td style="width:70%"> 
    			${esame.tumoriPrecedenti.description }  
    		</td>
        </tr>    
        
    <c:if test="${esame.tumoriPrecedenti.id == 2 }">
        <tr>
    		<td style="width:30%">
    			 T
    		</td>
    		<td style="width:70%"> 
    			${esame.t }  
    		</td>
        </tr>    
        
        <tr>
    		<td style="width:30%">
    			 N
    		</td>
    		<td style="width:70%"> 
    			${esame.n }  
    		</td>
        </tr>  
        
        <tr>
    		<td style="width:30%">
    			 M
    		</td>
    		<td style="width:70%">  
    			${esame.m }  
    		</td>
        </tr> 
    </c:if>
        
        <tr class="odd">
    		<td style="width:30%" >
    			 Dimensione (centimetri)
    		</td>
    		<td style="width:70%"> 
    			${esame.dimensione }
    		</td>
        </tr> 
        
        <tr>
    		<td style="width:30%">
    			 Interessamento Linfonodale
    		</td>
    		<td style="width:70%">  
    			${esame.interessamentoLinfonodale.description }  
    		</td>
        </tr> 
        
        <tr class="odd">
    		<td style="width:30%">
    			 Sede Lesione e Sottospecifica
    		</td>
    		<td style="width:70%">  
    			${esame.sedeLesione }  
    		</td>
        </tr> 
        
        <tr>
        	<th colspan="3">
        		Risultato
        	</th>
        </tr>
        
        
        <tr>
    		<td style="width:30%">
    			  Data Esito <font color="red"> *</font>
    		</td>
    		<td style="width:70%">      			
    			 <input 
    			 	type="text" 
    			 	id="dataEsito" 
    			 	name="dataEsito" 
    			 	maxlength="32" 
    			 	size="50" 
    			 	readonly="readonly" 
    			 	style="width:246px;" 
					value="<fmt:formatDate type="date" value="${esame.dataEsito }" pattern="dd/MM/yyyy" />"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_2" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataEsito",     // id of the input field
        					ifFormat       :    "%d/%m/%Y",      // format of the input field
       						button         :    "id_img_2",  // trigger for the calendar (button ID)
       						// align          :    "Tl",           // alignment (defaults to "Bl")
        					singleClick    :    true,
        					timeFormat		:   "24",
        					showsTime		:   false
   							 });					    
  					 </script>   
    		</td>
        </tr>
        
        
        <tr>
    		<td style="width:30%">
    			  Descrizione Morfologica
    		</td>
    		<td style="width:70%">  
    			 <textarea rows="5" cols="60" name="descrizioneMorfologica" id="descrizioneMorfologica" >${esame.descrizioneMorfologica }</textarea>
    		</td>
        </tr>
        
        <tr class="odd">
    		<td style="width:30%">
    			 Diagnosi
    		</td>
    		<td style="width:70%">  
    			 <select name="idTipoDiagnosi" id="idTipoDiagnosi" onchange="selezionaDivTipoDiagnosi(this.value)">
    			 	<c:forEach items="${tipoDiagnosis }" var="temp">
    			 		<option value="${temp.id }" <c:if test="${temp == esame.tipoDiagnosi }">selected="selected"</c:if>>${temp.description }</option>
    			 	</c:forEach>
    			 </select>
    			 
    			 <div id="tipoDiagnosi-1" name="tipoDiagnosi-1">
    			 	<input type="hidden" name="idWhoUmana" value="-1" />
    			 </div>
    			 
    			 
    			 <div id="whoUmanaDiv" name="whoUmanaDiv" style="display: none;">	
    			 	<input type="hidden" name="idWhoUmana" id="idWhoUmana" value="${esame.whoUmana.id }"/>
    			 	
    			 	 <select name="padreWhoUmana" id="padreWhoUmana" onchange="selezionaDivWhoUmana(this.value),updateIdWhoUmana()">
	    			 	<c:forEach items="${whoUmanaPadre }" var="temp">
	    			 		<option value="${temp.id }" <c:if test="${temp == esame.whoUmana.padre || temp == esame.whoUmana }">selected="selected"</c:if> >${temp.codice } - ${temp.description }</option>
	    			 	</c:forEach>
	    			 </select>
	    			 <c:forEach items="${whoUmanaPadre }" var="temp">
    			 	 <div <c:if test="${temp.id > 1 }"> style="display: none;" </c:if> id="div_who_umana_${temp.id }" name="div_who_umana_${temp.id }">
    			 		<c:if test="${empty temp.figli }">
    			 			<input type="hidden" name="idWhoUmana" id="idWhoUmana" value="${temp.id }" <c:if test="${temp.id > 0 }">disabled="disabled"</c:if> />
    			 		</c:if>
    			 		<c:if test="${not empty temp.figli }">
    			 			 <select name="idWhoUmana${temp.id }" id="idWhoUmana${temp.id }" disabled="disabled" onchange="updateIdWhoUmana()">
			    			 	<c:forEach items="${temp.figli }" var="figlio">
			    			 		<c:if test="${empty figlio.figli }">
			    			 			<option value="${figlio.id }" <c:if test="${figlio == esame.whoUmana }">selected="selected"</c:if> >${figlio.codice } - ${figlio.description }</option>
			    			 		</c:if>
			    			 		<c:if test="${not empty figlio.figli}">
			    			 			<optgroup label="${figlio.description }">
			    			 				<c:forEach items="${figlio.figli }" var="nipote" >
			    			 					<option value="${nipote.id }" <c:if test="${nipote == esame.whoUmana }">selected="selected"</c:if> >${nipote.codice } - ${nipote.description }</option>
			    			 				</c:forEach>
			    			 			</optgroup>
			    			 		</c:if>
			    			 	</c:forEach>
			    			 </select>
    			 		</c:if>
    			 	 </div>
    			  </c:forEach>
    			 </div>
    			 
    			 <div id="whoAnimaleDiv" name="whoAnimaleDiv" style="display: none;">
    			 	Who animale
    			 </div>
    			 
    			 <div id="nonTumoraleDiv" name="nonTumoraleDiv" style="display: none;">
    			 	<textarea rows="5" cols="60" name="diagnosiNonTumorale" id="diagnosiNonTumorale" >${esame.diagnosiNonTumorale }</textarea>
    			 </div>
    			 
    			 
    		</td>
        </tr>  
        
        <tr>
    		<td colspan="2">    
    			<font color="red">* </font> Campi obbligatori
				<br/>	
				<c:choose>
					<c:when test="${esame.dataEsito!=null}">
						<input type="submit" value="Modifica"/>
					</c:when>
    				<c:otherwise>
    					<input type="submit" value="Salva"/>
    				</c:otherwise>
				</c:choose>
    			<input type="button" value="Torna alla Home" onclick="attendere();location.href='vam.izsm.esamiIstopatologici.ToFind.us'">
    		</td>
        </tr>
	</table>
</form>

<script type="text/javascript">

var padreSedeLesionePrecedente = -1;

function selezionaDivSedeLesione( idPadre )
{
	toggleDiv( "div_sedi_" + padreSedeLesionePrecedente );
	toggleDiv( "div_sedi_" + idPadre );

	padreSedeLesionePrecedente = idPadre;
}

function updateTNM( idTumPrec )
{
	var div = document.getElementById( "tnm" );

	if( idTumPrec == 2 )
	{
		div.style.display = "block";
		protect( div, false );
	}
	else
	{
		div.style.display = "none";
		protect( div, true );
	}
}

var padreWhoUmanaPrecedente = 1;

function selezionaDivWhoUmana( idPadre )
{
	toggleDiv( "div_who_umana_" + padreWhoUmanaPrecedente );
	toggleDiv( "div_who_umana_" + idPadre );

	padreWhoUmanaPrecedente = idPadre;
}


function updateIdWhoUmana()
{
	var indiceDiv = document.getElementById( 'padreWhoUmana' ).value;
	document.getElementById( 'idWhoUmana' ).value = document.getElementById( 'idWhoUmana' + indiceDiv ).value;
}

var padreTipoDiagnosiPrecedente = "tipoDiagnosi-1";

function selezionaDivTipoDiagnosi( idDiagnosi )
{
	var divx = "tipoDiagnosi-1";
	
	switch ( idDiagnosi )
	{
	case "1":
		divx = "whoUmanaDiv";
		break;
	case "2":
		divx = "whoAnimaleDiv";
		break;
	case "3":
		divx = "nonTumoraleDiv";
		break;
	}

	//document.getElementById( padreTipoDiagnosiPrecedente ).style.display = "none";
	//document.getElementById( divx ).style.display = "block";
	toggleDiv( padreTipoDiagnosiPrecedente );
	toggleDiv( divx );

	switch ( idDiagnosi )
	{
	case "1":
		updateIdWhoUmana();
		break;
	case "2":
		
		break;
	case "3":
		
		break;
	}

	padreTipoDiagnosiPrecedente = divx;
}
	
</script>



<script type="text/javascript">
	selezionaDivTipoDiagnosi(document.getElementById('idDiagnosiHidden').value);
</script>

<c:if test="${esame.whoUmana.padre != null}">
	<script type="text/javascript">
		setTimeout( 'selezionaDivWhoUmana( document.getElementById("whoUmanaPadreHidden").value)', 700 );
	</script>
</c:if>
<c:if test="${esame.whoUmana.padre == null && esame.whoUmana != null}">
	<script type="text/javascript">
		setTimeout( 'selezionaDivWhoUmana( document.getElementById("whoUmanaHidden").value)', 700 );
	</script>
</c:if>
