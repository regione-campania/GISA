<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="it.us.web.bean.vam.Animale"%>
<%@page import="it.us.web.constants.SpecieAnimali"%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="java.util.Date"%>
<script language="JavaScript" type="text/javascript" src="js/vam/richiesteIstopatologici/add.js"></script>

<%
	Animale animale = (Animale)request.getAttribute("animale");
	String proprietarioCognome			= (String)request.getAttribute("proprietarioCognome");
	String proprietarioNome 			= (String)request.getAttribute("proprietarioNome");
	String proprietarioCodiceFiscale	= (String)request.getAttribute("proprietarioCodiceFiscale");
	String proprietarioDocumento		= (String)request.getAttribute("proprietarioDocumento");
	String proprietarioIndirizzo 		= (String)request.getAttribute("proprietarioIndirizzo");
	String proprietarioCap 				= (String)request.getAttribute("proprietarioCap");
	String proprietarioComune 			= (String)request.getAttribute("proprietarioComune");
	String proprietarioProvincia 		= (String)request.getAttribute("proprietarioProvincia");
	String proprietarioTelefono 		= (String)request.getAttribute("proprietarioTelefono");
	String proprietarioTipo 			= (String)request.getAttribute("proprietarioTipo");
	String nomeColonia 					= (String)request.getAttribute("nomeColonia");
	Boolean randagio 					= (Boolean)request.getAttribute("randagio");
	if(randagio==null)
		randagio = false;
	SpecieAnimali specie				= (SpecieAnimali)request.getAttribute("specie");
%>

<form action="vam.richiesteIstopatologici.AddLLPP.us" name="form" id="form" method="post" class="marginezero" onsubmit="javascript:return checkform(this)">    
<input type="hidden" name="idAnimale" id="idAnimale" value="${animale.id}" /> 
	
	<fmt:formatDate value="${animale.dataSmaltimentoCarogna}" pattern="dd/MM/yyyy" var="dataSmaltimento"/>
    <input type="hidden" name="dataSmaltimento" id="dataSmaltimento" value="${dataSmaltimento}" />  
    
    <h4 class="titolopagina">
			Nuova richiesta Esame Istopatologico
    </h4>	 
    
    <table class="tabella">
    <tr>
    	<th colspan="2">
    		Dati Animale
    	</th>
    </tr>
	<tr class='even'>
		<td>
			Identificativo
		</td>
		<td>
			<%=animale.getIdentificativo()%>
		</td>
	</tr>
	<tr class='even'>
		<td>
			Tatuaggio / II MC
		</td>
		<td>
<%				
			if(animale.getTatuaggio()!=null)
			{
%> 
				<%=animale.getTatuaggio()%> 
<%
			}
%>
		</td>
	</tr>
	<c:choose>
		<c:when test="<%=animale.getLookupSpecie().getId()==specie.getSinantropo()%>">
			<fmt:formatDate type="date" value="<%=animale.getDataNascita()%>" pattern="dd/MM/yyyy" var="dataNascita" />
			<tr class='odd'>
				<td>
					Et&agrave;
				</td>
				<td>
					${animale.eta.description}
					<c:if test="<%=animale.getDataNascita()!=null %>">
						(<%=animale.getDataNascita() %>)
					</c:if>
				</td>
			</tr>
		</c:when>
		<c:otherwise>
			<fmt:formatDate type="date" value="<%=animale.getDataNascita()%>" pattern="dd/MM/yyyy" var="dataNascita"/>
			<tr class='odd'>
				<td>
					Data nascita
				</td>
				<td>
<%
				if (animale.getDataNascita()!=null)
				{
%>
					<%=animale.getDataNascita()%>
<%
				} 
%>
				</td>
			</tr>
		</c:otherwise>
	</c:choose>
	
<!--  	<c:set scope="request" var="anagraficaAnimale" value="${anagraficaAnimale}"/> -->
    <c:import url="../vam/richiesteIstopatologici/anagraficaAnimaleDetail.jsp"/>
	
		<c:if test="<%=animale.getClinicaChippatura()!=null%>">
			<tr class='even'>
				<td>
					Microchippato nella clinica 
				</td>
				<td>
					<%=animale.getClinicaChippatura().getNome()%>
				</td>
			</tr>
		</c:if>
		<c:if test="${animale.dataMorte!=null || res.dataEvento!=null}">
        	<tr class='even'>
   				<td>
   					Data del decesso
   				</td>
   				<td> 
					${dataMorte} - 
					<c:choose>
						<c:when test="<%=animale.getDecedutoNonAnagrafe()==true %>">
							<%=animale.getDataMorteCertezza()%>
						</c:when>
						<c:otherwise>
							${res.dataMorteCertezza}
						</c:otherwise>	
					</c:choose>	 
        		</td>
   			</tr>
    	
   			<tr class='odd'>
      	  		<td>
    				Probabile causa del decesso
   				</td>
   				<td>    
   					<c:choose>
    				<c:when test="<%=animale.getDecedutoNonAnagrafe()%>">
<% 
					if (animale.getCausaMorte().getDescription()!=null)
					{ 
%>
						<%=animale.getCausaMorte().getDescription()%>
<%
					} 
					else 
					{
%>
    					<%=""%>
<%
					}
%>
    				</c:when>
    				<c:otherwise>
    					${res.decessoValue}
    				</c:otherwise>
    				</c:choose>	        	        
        		</td>
       		</tr>
       	</c:if>

	<!--  c:if test="${!animale.decedutoNonAnagrafe }" -->
		<c:choose>
			<c:when test="<%=animale.getLookupSpecie().getId()==specie.getSinantropo()%>">
				<tr>
					<th colspan="2">
						Detentore
					</th>
				</tr>
			</c:when>
			<c:otherwise>
				<c:set var="proprietarioCognome" value="<%=proprietarioCognome%>"/>
				<c:choose>
					<c:when test="<%=proprietarioCognome!=null && proprietarioCognome.startsWith(\"<b>\")%>">
						<th colspan="2">Colonia</th>
					</c:when>
					<c:otherwise>
						<th colspan="2">Proprietario <%=(proprietarioTipo==null || proprietarioTipo.equals("null"))?(""):(proprietarioTipo)%></th>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>

		<c:choose>
			<c:when test="<%=proprietarioCognome!=null && proprietarioCognome.startsWith(\"<b>\")%>">
				<tr class='even'>
					<td>
						Colonia
					</td>
					<td colspan="2">
						<%=nomeColonia%>
					</td>
				</tr>
				<tr class='odd'>
					<td>
						Indirizzo
					</td>
					<td>
						<%=proprietarioIndirizzo%>, <%=proprietarioComune%>
						<c:if test="<%=!proprietarioProvincia.equals(\"\") %>">
							(<%=proprietarioProvincia%>)						
						</c:if>
						<c:if test="<%=!proprietarioCap.equals(\"\")%>">
							- <%=proprietarioCap%>						
						</c:if>
					</td>
				</tr>
				<tr class='even'>
					<td>
						Nominativo Referente
					</td>
					<td>
						<%=proprietarioNome%>
					</td>
				</tr>
				<tr class='odd'>
					<td>
						Codice fiscale Referente
					</td>
					<td>
						<%=proprietarioCodiceFiscale%>
					</td>
				</tr>
				<tr class='even'>
					<td>
						Documento Identità Referente
					</td>
					<td>
						<%=proprietarioDocumento%>
					</td>
				</tr>
				<tr class='odd'>
					<td>
						Telefono Referente
					</td>
					<td>
						<%=proprietarioTelefono%>
					</td>
				</tr>
			</c:when>
			<c:when test="${proprietarioTipo=='Importatore' || proprietarioTipo=='Operatore Commerciale'}">
				<tr class='even'><td>Ragione Sociale:</td><td><%=proprietarioNome%></td></tr>
				<tr class='odd'><td>Partita Iva:</td><td><%=proprietarioCognome%></td></tr>
				<tr class='even'><td>Rappr. Legale:</td><td><%=proprietarioCodiceFiscale%></td></tr>
				<tr class='odd'><td>Telefono struttura(principale):</td><td><%=proprietarioTelefono%></td></tr>
				<tr class='even'><td>Telefono struttura(secondario):</td><td><%=proprietarioDocumento%></td></tr>
				<tr class='odd'><td>Indirizzo sede operativa:</td><td><%=proprietarioIndirizzo%></td></tr>
				<tr class='even'><td>CAP sede operativa:</td><td><%=proprietarioCap%></td></tr>
				<tr class='odd'><td>Comune sede operativa:</td><td><%=proprietarioComune%></td></tr>
				<tr class='even'><td>Provincia sede operativa:</td><td><%=proprietarioProvincia%></td></tr>
			</c:when>
			<c:otherwise>
				<c:if test="<%=proprietarioTipo!=null && proprietarioTipo.equals(\"Canile\") %>">
					<tr class='even'><td>Ragione Sociale:</td><td><%if (proprietarioNome!=null){%><%=proprietarioNome %><%} %></td></tr>
					<tr class='odd'><td>Partita Iva:</td><td><%=proprietarioCognome%></td></tr>
					<tr class='even'><td>Rappr. Legale:</td><td><%=proprietarioCodiceFiscale%></td></tr>
				</c:if>
				<c:if test="<%=proprietarioTipo!=null && !proprietarioTipo.equals(\"Canile\") %>">
					<c:if test="<%=!randagio%>">
						<tr class='even'><td>Cognome:</td><td><%if (proprietarioCognome!=null){%><%=proprietarioCognome%><%} %></td></tr>
					</c:if>
					<tr class='odd'><td>Nome:</td><td><%if (proprietarioNome!=null){%><%=proprietarioNome %><%} %></td></tr>
				</c:if>	
				<c:if test="<%=!randagio%>">
				<c:if test="<%=proprietarioTipo!=null && !proprietarioTipo.equals(\"Canile\") %>">
					<tr class='even'><td>Codice Fiscale:</td><td><% if (proprietarioCodiceFiscale!=null){%><%=proprietarioCodiceFiscale%><%} %></td></tr>
				</c:if>
					<tr class='odd'><td>Documento:</td><td><% if (proprietarioDocumento!=null){%><%=proprietarioDocumento%><%} %></td></tr>
					<tr class='even'><td>Indirizzo:</td><td><% if (proprietarioIndirizzo!=null){%><%=proprietarioIndirizzo%><%} %></td></tr>
					<tr class='odd'><td>CAP:</td><td><% if (proprietarioCap!=null){%><%=proprietarioCap%><%} %></td></tr>
					<tr class='even'><td>Comune:</td><td><% if (proprietarioComune!=null){%><%=proprietarioComune%><%} %></td></tr>
					<tr class='odd'><td>Provincia:</td><td><% if (proprietarioProvincia!=null){%><%=proprietarioProvincia%><%} %></td></tr>
					<tr class='even'><td>Telefono:</td><td><% if (proprietarioTelefono!=null){%><%=proprietarioTelefono%><%} %></td></tr>
				</c:if>
			</c:otherwise>
		</c:choose>
    </table>
    
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
					value="<fmt:formatDate type="date" value="<%=new Date() %>" pattern="dd/MM/yyyy" />" 
				  />
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
    			 Laboratorio di destinazione<font color="red"> *</font>
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
    		<td colspan="2">    
    			<font color="red">* </font> Campi obbligatori
				<br/>
					<input type="submit" value="Salva" />
    			<!--  input
				type="button" value="Immagini"
				onclick="javascript:avviaPopup( 'vam.cc.esamiIstopatologici.GestioneImmagini.us?id=${esame.id }&cc=${cc.id}' );" /-->
    			<input type="button" value="Annulla" onclick="attendere();location.href='vam.richiesteIstopatologici.ToFindAnimaleLLPP.us'">
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
