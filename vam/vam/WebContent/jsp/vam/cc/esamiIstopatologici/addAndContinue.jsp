<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="java.util.Date"%>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/esamiIstopatologici/addAndContinue.js"></script>


<form action="vam.cc.esamiIstopatologici.Add.us" name="form" id="form" method="post" class="marginezero" onsubmit="javascript:return checkform(this)">    

    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    
    <h4 class="titolopagina">
		<c:if test="${!modify }" >     
			Nuova Richiesta Esame Istopatologico
		</c:if>
    </h4>	 
    
    <table class="tabella">
        <tr>
        	<th colspan="2">
        		Richiesta
        	</th>
        </tr>
    	<tr class="odd">
    		<td>
    			Data Richiesta<font color="red"> *</font>
    		</td>
    		<td style="width:50%">    			 
    			 <fmt:formatDate type="date" value="${esame.dataRichiesta}" pattern="dd/MM/yyyy" var="data"/>
    			 ${data}
    			 <input type="hidden" name="dataRichiesta" value="${data}" /> 
    		</td>    		
        </tr>
        
        <tr class='odd'>
    		<td>
    			 Laboratorio di destinazione
    		</td>
    		<td>
    			 ${esame.lass.description}
    			 <input type="hidden" name="lookupAutopsiaSalaSettoria" value="${esame.lass.id}" />
    		</td>
        </tr> 
        
        <tr class="even">
    		<td>
    			 Numero rif. Mittente
    		</td>
    		<td>
				 ${esame.numeroAccettazioneSigla}
				 <input type="hidden" name="tipoAccettazione" value="${esame.tipoAccettazione}" />
				 <input type="hidden" name="numeroAccettazioneSigla" value="${esame.numeroAccettazioneSigla}" />
    		</td>
    		<td>
    		</td>
        </tr>
        
		<tr class="odd">
    		<td style="width:30%">
    			 Tipo Prelievo
    		</td>
    		<td style="width:70%">  
    			${esame.tipoPrelievo.description }
    			<input type="hidden" name="idTipoPrelievo" value="${esame.tipoPrelievo.id}" />
    		</td>
        </tr>       

       <tr>
    		<td style="width:30%">
    			 Tumori Precedenti
    		</td>
    		<td style="width:70%"> 
    			${esame.tumoriPrecedenti.description}
    			<input type="hidden" name="idTumoriPrecedenti" value="${esame.tumoriPrecedenti.id}" /> 
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
    
    		<input type="hidden" name="t" value="${esame.t }"/>
    		<input type="hidden" name="n" value="${esame.n }"/>
    		<input type="hidden" name="m" value="${esame.m }"/>
        
         <tr class="odd">
    		<td style="width:30%" >
    			 Dimensione (centimetri)
    		</td>
    		<td style="width:70%"> 
    			${esame.dimensione }
    			<input type="hidden" name="dimensione" id="dimensione" value="${esame.dimensione }"/>
    		</td>
        </tr> 
        
        <tr>
    		<td style="width:30%">
    			 Interessamento Linfonodale
    		</td>
    		<td style="width:70%">  
    			${esame.interessamentoLinfonodale.description }  
    			<input type="hidden" name="idInteressamentoLinfonodale" id="idInteressamentoLinfonodale" value="${esame.interessamentoLinfonodale.id}"/>
    		</td>
        </tr> 
        
        <tr class="odd">
    		<td style="width:30%">
    			 Sede Lesione e Sottospecifica<font color="red"> *</font> 
    		</td>
    		<td style="width:70%">  
    			 <select name="padreSedeLesione" id="padreSedeLesione" onchange="selezionaDivSedeLesione(this.value)">
    			 	<c:forEach items="${sediLesioniPadre }" var="temp">
    			 		<option value="${temp.id }">${temp.codice } - ${temp.description }</option>
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
			    			 			<option value="${figlio.id }">${figlio.codice } - ${figlio.description }</option>
			    			 		</c:if>
			    			 		<c:if test="${not empty figlio.figli}">
			    			 			<optgroup label="${figlio.description }">
			    			 				<c:forEach items="${figlio.figli }" var="nipote" >
			    			 					<option value="${nipote.id }">${nipote.codice } - ${nipote.description }</option>
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

<tr>
        	<th colspan="2">
        		Risultato
        	</th>
        </tr>
        <tr class="odd">
    		<td style="width:30%">
    			 Data Esito
    		</td>
    		<td style="width:70%">  
    			<fmt:formatDate type="date" value="${esame.dataEsito}" pattern="dd/MM/yyyy" />   
    		</td>
        </tr>
        <tr>
    		<td style="width:30%">
    			 Descrizione Morfologica
    		</td>
    		<td style="width:70%">  
    			${esame.descrizioneMorfologica }  
    		</td>
        </tr>
        
        <tr class="odd">
    		<td style="width:30%">
    			 Diagnosi
    		</td>
    		<td style="width:70%">  
    			${esame.tipoDiagnosi.description }: ${esame.whoUmana } ${esame.diagnosiNonTumorale } 
    		</td>
        </tr> 
        
                
        <tr>
    		<td colspan="2">    
    			<font color="red">* </font> Campi obbligatori
				<br/>
				<c:if test="${!modify }" >
					<input type="submit" value="Salva"  />
					<input type="button" value="Salva e Continua" onclick="if(checkform(document.getElementById('form'))==true){document.getElementById('form').action='vam.cc.esamiIstopatologici.AddAndContinue.us';document.getElementById('form').submit();}" />
				</c:if>
				<c:if test="${modify }" >
		    		<input type="submit" value="Salva"  />	
				</c:if>
    			
    			<!-- input
				type="button" value="Immagini"
				onclick="javascript:avviaPopup( 'vam.cc.esamiIstopatologici.GestioneImmagini.us?id=${esame.id }&cc=${cc.id}' );" / -->
    			<input type="button" value="Annulla" onclick="location.href='vam.cc.esamiIstopatologici.List.us'">
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
