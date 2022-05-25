<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="java.util.Date"%>
<script language="JavaScript" type="text/javascript" src="js/vam/cc/esamiIstopatologici/add.js"></script>


<form action="vam.cc.esamiIstopatologici.Add.us" name="form" id="form" method="post" class="marginezero" onsubmit="javascript:return checkform(this)">    
	
	<fmt:formatDate value="${cc.accettazione.animale.dataSmaltimentoCarogna}" pattern="dd/MM/yyyy" var="dataSmaltimento"/>
    <input type="hidden" name="dataSmaltimento" id="dataSmaltimento" value="${dataSmaltimento}" />  
    
    <jsp:include page="/jsp/vam/cc/menuCC.jsp"/>
    
    <h4 class="titolopagina">
		<c:if test="${!modify }" >     
			Nuova richiesta Esame Istopatologico
		</c:if>
		<c:if test="${modify }" >
			Modifica Richiesta Esame Istopatologico numero ${esame.numero} richiesto dal Dott. ${esame.enteredBy.nome} ${esame.enteredBy.cognome} 
    			
    			<input type="hidden" name="modify" value="on" />
    			<input type="hidden" name="id" value="${esame.id }" />
		</c:if>
    </h4>	 
    
       
    
    
    
    <table class="tabella">
        <tr>
        	<th colspan="2">
        		Richiesta
        	</th>
        </tr>
    	<tr class="even">
    		<td>
    			Data Richiesta<font color="red"> *</font>
    		</td>
    		<td style="width:50%">    			 
    			 <input 
    			 	type="text" 
    			 	id="dataRichiesta" 
    			 	name="dataRichiesta" 
    			 	maxlength="32" 
    			 	size="50" 
    			 	readonly="readonly" 
    			 	style="width:246px;" 
					<c:if test="${modify }"> value="<fmt:formatDate type="date" value="${esame.dataRichiesta }" pattern="dd/MM/yyyy" />" </c:if>
					<c:if test="${!modify }"> value="<fmt:formatDate type="date" value="<%=new Date() %>" pattern="dd/MM/yyyy" />" </c:if> />
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
        
        <tr class='odd'>
    		<td>
    			 Laboratorio di destinazione
    		</td>
    		<td>
    			 <select name="lookupAutopsiaSalaSettoria">
    			 	<option value="">&lt;---Selezionare---&gt;</option>
				 	<c:forEach items="${listAutopsiaSalaSettoria}" var="t" >
				 		<c:if test="${q.esterna && viewOptEsterna=='true'}">
				 			<optgroup label="<------- Esterna ------->" style="font-style: italic">
							<c:set value="false" var="viewOptEsterna"/>
				 		</c:if>
				 		<c:if test="${!q.esterna && viewOptInterna=='true'}">
				 			<optgroup label="<------- Interna ------->" style="font-style: italic">
				 			<c:set value="false" var="viewOptInterna"/>
				 		</c:if>
				 		<c:if test="${t.id==7}">
				 		<optgroup label="Universit&agrave;">
				 	</c:if>
				 	<c:if test="${t.id==6}">
				 		</optgroup>
				 		<optgroup label="IZSM">
				 	</c:if>	
				    	<option value="${t.id }" <c:if test="${t.id==esame.lass.id}">selected="selected"</c:if> >${t.description }</option>
					</c:forEach>
					</optgroup>
		      	</select>
    		</td>
        </tr>  
        
        <tr class="even">
    		<td>
    			Numero rif. Mittente</br>
    			<input type="radio" name="tipoAccettazione" id="idTipoAccettazione1" value="IZSM" <c:if test="${esame.tipoAccettazione=='IZSM' || esame==null}">checked="checked"</c:if><c:if test="${cc.autopsia.tipoAccettazione!=null}">disabled="disabled"</c:if>/>
			    <label for="idTipoAccettazione1">IZSM</label>
				<input type="radio" name="tipoAccettazione" id="idTipoAccettazione2" value="Unina" <c:if test="${esame.tipoAccettazione=='Unina'}">checked="checked"</c:if><c:if test="${cc.autopsia.tipoAccettazione!=null}">disabled="disabled"</c:if>/>
				<label for="idTipoAccettazione2">Unina</label>
				<input type="radio" name="tipoAccettazione" id="idTipoAccettazione3" value="Asl" <c:if test="${esame.tipoAccettazione=='Asl' || esame==null}">checked="checked"</c:if><c:if test="${cc.autopsia.tipoAccettazione!=null}">disabled="disabled"</c:if>/>
			    <label for="idTipoAccettazione1">Asl</label>
				<input type="radio" name="tipoAccettazione" id="idTipoAccettazione4" value="Criuv" <c:if test="${esame.tipoAccettazione=='Criuv'}">checked="checked"</c:if><c:if test="${cc.autopsia.tipoAccettazione!=null}">disabled="disabled"</c:if>/>
				<label for="idTipoAccettazione2">Criuv</label>
				<c:if test="${cc.autopsia.tipoAccettazione!=null}">
					<input type="hidden" name="tipoAccettazione"  value="${cc.autopsia.tipoAccettazione}"/>
				</c:if>
    		</td>
    		<td>
				<input type="text" name="numeroAccettazioneSigla" size="50" style="width:246px;" 
				<c:choose>
					<c:when test="${modify}">
						value="${esame.numeroAccettazioneSigla}"
					</c:when>
					<c:otherwise>
						value="${cc.accettazione.animale.numAccNecroscopicoIstoPrecedente}"
					</c:otherwise>
				</c:choose>
				
				<c:if test="${cc.autopsia.numeroAccettazioneSigla!=null}">
					readonly="readonly" 
				</c:if>
				
				/>
			</td>
    		<td>
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
    			 Sede Lesione e Sottospecifica<font color="red"> *</font> 
    		</td>
    		<td style="width:70%">  
    			 <select name="padreSedeLesione" id="padreSedeLesione" onchange="selezionaDivSedeLesione(this.value)">
    			 	<c:forEach items="${sediLesioniPadre }" var="temp">
    			 		<option value="${temp.id }" <c:if test="${temp.id == esame.sedeLesione.padre.padre.id || temp.id == esame.sedeLesione.padre.id || temp.id == esame.sedeLesione.id }">selected="selected"</c:if> >${temp.codice } - ${temp.description }</option>
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
					<input type="submit" value="Salva" />
					<input type="button" value="Salva e Continua" onclick="if(checkform(document.getElementById('form'))==true){document.getElementById('form').action='vam.cc.esamiIstopatologici.AddAndContinue.us';document.getElementById('form').submit();attendere();}" />
				</c:if>
				<c:if test="${modify }" >
		    		<input type="submit" value="Salva" />	
				</c:if>
    			<!--  input
				type="button" value="Immagini"
				onclick="javascript:avviaPopup( 'vam.cc.esamiIstopatologici.GestioneImmagini.us?id=${esame.id }&cc=${cc.id}' );" /-->
    			<input type="button" value="Annulla" onclick="attendere();location.href='vam.cc.esamiIstopatologici.List.us'">
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
	<c:if test="${modify && esame.sedeLesione.padre != null && esame.sedeLesione.padre.padre == null }" >
		setTimeout( 'selezionaDivSedeLesione( ${esame.sedeLesione.padre.id } )', 600 );
	</c:if>
	<c:if test="${modify && esame.sedeLesione.padre.padre != null }" >
	setTimeout( 'selezionaDivSedeLesione( ${esame.sedeLesione.padre.padre.id } )', 600 );
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
