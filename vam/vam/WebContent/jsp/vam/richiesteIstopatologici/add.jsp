<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="java.util.Date"%>

<script language="JavaScript" type="text/javascript" src="js/vam/cc/esamiIstopatologici/add.js"></script>


<form action="vam.richiesteIstopatologici.Add.us" name="form" method="post" class="marginezero" onsubmit="javascript:return checkform(this);">    

    <h4 class="titolopagina">
		<c:if test="${!modify }" >     
			Richiesta Esame Istopatologico
		</c:if>
		<c:if test="${modify }" >
			Modifica Esame Istopatologico richiesto da ${esame.enteredBy.nome} ${esame.enteredBy.cognome} 
    			
    			<input type="hidden" name="modify" value="on" />
    			<input type="hidden" name="id" value="${esame.id }" />
		</c:if>
    </h4>
    
    
    <input type="hidden" name="idAnimale" value="${animale.id }" />
    <input type="hidden" name="liberoProfessionista" value="${liberoProfessionista }" />
    
    <table class="tabella">
        
        <tr>
        	<th colspan="3">
        		Dati generici dell'animale
        	</th>
        </tr>
    
    	<tr class='even'>
    		<td>
    			Peso dell'animale (in Kg)
    		</td>
    		<td>
    			 <input type="text" name="peso" maxlength="6" size="6"/>
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='odd'>
    		<td>
    			Habitat 
    		</td>
    		<td>
    		
    			<c:forEach items="${listHabitat}" var="h" >									
							<input type="checkbox" name="oph_${h.id }" id="oph_${h.id }"/> ${h.description} 	
							<br>				
				</c:forEach>
    		
    		</td>
    		<td>
    		</td>
        </tr>
        
        <tr class='even'>
    		<td>
    			Alimentazione
    		</td>
    		<td>
    			<c:forEach items="${listAlimentazioni}" var="a" >									
							<input type="checkbox" name="opa_${a.id }" id="opa_${a.id }" /> ${a.description} 	
							<br>				
				</c:forEach> 
    		</td>
    		<td>
    		</td>
        </tr>
        
        
        
        <tr>
        	<th colspan="2">
        		Richiesta
        	</th>
        </tr>
        <tr>
	        <td>
	         	Data Richiesta<font color="red"> *</font>
	        </td>    	
    		<td style="width:50%">    			
    			 <fmt:formatDate type="date" value="<%=new Date() %>" pattern="dd/MM/yyyy" var="dataOdierna"/> 		
    			 <input type="text" id="dataRichiesta" name="dataRichiesta" maxlength="32" size="50" readonly="readonly" style="width:246px;" value="${dataOdierna}"/>
    			 <img src="images/b_calendar.gif" alt="calendario" id="id_img_1" />
 					<script type="text/javascript">
      					 Calendar.setup({
        					inputField     :    "dataRichiesta",     // id of the input field
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
          

        

		<tr class="odd">
    		<td style="width:30%">
    			 Tipo Prelievo
    		</td>
    		<td style="width:70%">  
    			 <select name="idTipoPrelievo" id="idTipoPrelievo">
    			 	<c:forEach items="${tipoPrelievos }" var="temp">
    			 		<option value="${temp.id }" <c:if test="${temp == esame.tipoPrelievo }">selected="selected"</c:if>>${temp.description }</option>
    			 	</c:forEach>
    			 </select>
    		</td>
        </tr>     

        <tr>
    		<td style="width:30%">
    			 Tumori Precedenti
    		</td>
    		<td style="width:70%">  
    			<select name="idTumoriPrecedenti" id="idTumoriPrecedenti" onchange="updateTNM(this.value);">
    			 	<c:forEach items="${tumoriPrecedentis }" var="temp">
    			 		<option value="${temp.id }" <c:if test="${temp == esame.tumoriPrecedenti }">selected="selected"</c:if> >${temp.description }</option>
    			 	</c:forEach>
    			 </select>
    			 <div id="tnm" name="tnm" style="display: none">
    			 	T <input type="text" maxlength="1" name="t" id="t"  size="5"  value="${esame.t }"/>&nbsp;&nbsp;&nbsp;&nbsp;
    			 	N <input type="text" maxlength="1" name="n" id="n"  size="5"  value="${esame.n }"/>&nbsp;&nbsp;&nbsp;&nbsp;
    			 	M <input type="text" maxlength="1" name="m" id="m"  size="5"  value="${esame.m }"/>
    			 </div>
    		</td>
        </tr>    
        
        <tr class="odd">
    		<td style="width:30%">
    			 Dimensione (centimetri)
    		</td>
    		<td style="width:70%"> 
    			<input maxlength="3" type="text" name="dimensione" id="dimensione" size="5" value="${esame.dimensione }"/>
    		</td>
        </tr> 
        
        <tr>
    		<td style="width:30%">
    			 Interessamento Linfonodale
    		</td>
    		<td style="width:70%">  
    			<select name="idInteressamentoLinfonodale" id="idInteressamentoLinfonodale">
    			 	<c:forEach items="${interessamentoLinfonodales }" var="temp">
    			 		<option value="${temp.id }" <c:if test="${temp == esame.interessamentoLinfonodale }">selected="selected"</c:if> >${temp.description }</option>
    			 	</c:forEach>
    			 </select>
       		</td>
        </tr> 
        
        <tr class="odd">
    		<td style="width:30%">
    			 Sede Lesione e Sottospecifica <font color="red">*</font>
    		</td>
    		<td style="width:70%">  
    			 <select name="padreSedeLesione" id="padreSedeLesione" onchange="selezionaDivSedeLesione(this.value)">
    			 	<c:forEach items="${sediLesioniPadre }" var="temp">
    			 		<option value="${temp.id }" <c:if test="${temp == esame.sedeLesione.padre || temp == esame.sedeLesione }">selected="selected"</c:if> >${temp.codice } - ${temp.description }</option>
    			 	</c:forEach>
    			 </select>
    			 <br/>
    			 <c:forEach items="${sediLesioniPadre }" var="temp">
    			 	<div <c:if test="${temp.id > 0 }"> style="display: none;" </c:if> id="div_sedi_${temp.id }" name="div_sedi_${temp.id }">
    			 		<c:if test="${empty temp.figli }">
    			 			<input type="hidden" name="idSedeLesione" id="idSedeLesione" value="${temp.id }" <c:if test="${temp.id > 0 }">disabled="disabled"</c:if> />
    			 		</c:if>
    			 		<c:if test="${not empty temp.figli }">
    			 			 <select name="idSedeLesione" id="idSedeLesione" disabled="disabled">
			    			 	<c:forEach items="${temp.figli }" var="figlio">
			    			 		<c:if test="${empty figlio.figli }">
			    			 			<option value="${figlio.id }" <c:if test="${figlio == esame.sedeLesione }">selected="selected"</c:if> >${figlio.codice } - ${figlio.description }</option>
			    			 		</c:if>
			    			 		<c:if test="${not empty figlio.figli}">
			    			 			<optgroup label="${figlio.description }">
			    			 				<c:forEach items="${figlio.figli }" var="nipote" >
			    			 					<option value="${nipote.id }" <c:if test="${nipote == esame.sedeLesione }">selected="selected"</c:if> >${nipote.codice } - ${nipote.description }</option>
			    			 				</c:forEach>
			    			 			</optgroup>
			    			 		</c:if>
			    			 	</c:forEach>
			    			 </select>
    			 		</c:if>
    			 	</div>
    			 </c:forEach>
    		</td>
        </tr> 
        
        <c:if test="${liberoProfessionista == false }">
        
        <tr>
        	<th colspan="2">
        		Risultato
        	</th>
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
        </c:if>
        <tr>
    		<td colspan="2">    
    			<font color="red">* </font> Campi obbligatori
				<br/>			
    			
    			
    			<c:if test="${!modify }" >     
					<input type="submit" value="Richiedi" />
				</c:if>
				<c:if test="${modify }" >
					<input type="submit" value="Modifica" />
				</c:if>
    			
    			
    			<input type="button" value="Torna alla Home" onclick="location.href='Home.us'">
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


	<c:if test="${modify }">
		setTimeout( 'updateTNM(${esame.tumoriPrecedenti.id}),selezionaDivTipoDiagnosi("${esame.tipoDiagnosi.id}")', 500 );
	</c:if>
	<c:if test="${modify && esame.sedeLesione.padre != null }" >
		setTimeout( 'selezionaDivSedeLesione( ${esame.sedeLesione.padre.id } )', 600 );
	</c:if>
	<c:if test="${modify && esame.sedeLesione.padre == null }" >
		setTimeout( 'selezionaDivSedeLesione( ${esame.sedeLesione.id } )', 600 );
	</c:if>
	<c:if test="${modify && esame.whoUmana.padre != null}">
		setTimeout( 'selezionaDivWhoUmana( ${esame.whoUmana.padre.id } )', 700 );
	</c:if>
	<c:if test="${modify && esame.whoUmana.padre == null && esame.whoUmana != null}">
		setTimeout( 'selezionaDivWhoUmana( ${esame.whoUmana.id } )', 700 );
	</c:if>
</script>
