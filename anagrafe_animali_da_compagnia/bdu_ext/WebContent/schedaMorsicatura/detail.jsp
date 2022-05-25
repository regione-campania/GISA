<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.schedaMorsicatura.base.Valutazione"%>
<%@page import="org.aspcfs.modules.schedaMorsicatura.base.SchedaMorsicaturaRecords"%>
<%@page import="org.aspcfs.modules.schedaMorsicatura.base.Indice"%>
<%@page import="org.aspcfs.modules.schedaMorsicatura.base.Criterio"%>
<%@page import="org.aspcfs.modules.schedaMorsicatura.base.SchedaMorsicatura"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@page import="org.aspcfs.modules.anagrafe_animali.base.Animale"%>

<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="gestione_documenti/generazioneDocumentale.js"></script>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="valutazione" class="org.aspcfs.modules.schedaMorsicatura.base.Valutazione" scope="request" />
<jsp:useBean id="animaleDettaglio" class="org.aspcfs.modules.anagrafe_animali.base.Animale" scope="request" />

<%
String param1 = "idAnimale=" + animaleDettaglio.getIdAnimale() + "&idSpecie=" + animaleDettaglio.getIdSpecie();
%>

<input type="hidden" name="idAnimale" value="<%=animaleDettaglio.getIdAnimale()%>" />


<%@ include file="../initPage.jsp"%>

	<%
		SchedaMorsicatura scheda = ((SchedaMorsicatura)request.getAttribute("scheda"));
		if(scheda==null)
		{
			out.println("Non sono presenti schede di morsicatura pregresse per questo animale");
			
		}
		else
		{
	%>	
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		
		<%
	if(request.getAttribute("fromRegistrazione")==null || ((String)request.getAttribute("fromRegistrazione")).equals("") || ((String)request.getAttribute("fromRegistrazione")).equals("null"))
	{
%>	
		<tr class="containerBody">
				<th>CRITERIO</th>
				<th>INDICE</th>
				<th>VALORE</th>
			</tr>
<%
	}
		ArrayList<SchedaMorsicaturaRecords> records = scheda.getRecords();		
		int i=0;

		while(i<records.size())
		{
			SchedaMorsicaturaRecords record = (SchedaMorsicaturaRecords)records.get(i);
			Criterio criterio = record.getIndice().getCriterio();
			Indice indice = record.getIndice();
			SchedaMorsicaturaRecords recordSuccesivo = null;
%>
			<tr 
<%
	if(request.getAttribute("fromRegistrazione")==null || ((String)request.getAttribute("fromRegistrazione")).equals("") || ((String)request.getAttribute("fromRegistrazione")).equals("null"))
	{
%>
			
			class="containerBody"
<%
	}
%>			>
				<td
				<%
	if(request.getAttribute("fromRegistrazione")!=null && !((String)request.getAttribute("fromRegistrazione")).equals("") && !((String)request.getAttribute("fromRegistrazione")).equals("null") && ((String)request.getAttribute("fromRegistrazione")).equals("true"))
	{
%>
			
			class="formLabel"
<%
	}
%>		

				><dhv:label name=""><%=criterio.getNome().toUpperCase()%></dhv:label></td>
				<td>
					<%=indice.getNome().toUpperCase() + ((indice.isValoreManuale())?(": " + record.getValoreManuale()):(""))%>
<%
				if(criterio.getFormulaCalcoloPunteggio().equals("divisione_indice"))
				{
						i++;
						recordSuccesivo = (SchedaMorsicaturaRecords)records.get(i);
						Criterio criterioSuccessivo = recordSuccesivo.getIndice().getCriterio();
						while(record.getIndice().getCriterio().getId()==criterioSuccessivo.getId())
						{
							recordSuccesivo = (SchedaMorsicaturaRecords)records.get(i);
							criterioSuccessivo = recordSuccesivo.getIndice().getCriterio();
							Indice indiceSuccessivo = recordSuccesivo.getIndice();
							if(record.getIndice().getCriterio().getId()==criterioSuccessivo.getId())
							{
								out.println("<br/>" + indiceSuccessivo.getNome().toUpperCase() + ": " + recordSuccesivo.getValoreManuale());
								i++;
							}
							else
							{
								i--;
								recordSuccesivo = (SchedaMorsicaturaRecords)records.get(i);
								criterioSuccessivo = recordSuccesivo.getIndice().getCriterio();
								break;
							}
						}
						
				}
%>
				</td>
				<td>
<%
					if(criterio.getFormulaCalcoloPunteggio().equals("divisione_indice"))
					{
						//Moltiplicazione *4 aggiunta come da richiesta della Pompameo - riferimento tt: 013398, Mail "scheda morsicatore: errore TT 013398" del 28/02/2019
						out.println(Float.parseFloat(record.getValoreManuale())/Float.parseFloat(recordSuccesivo.getValoreManuale())*4);
					}
					else
					{
						out.println(indice.getPunteggio());
					}
					
%>
				</td>
			</tr>
<%
			i++;
		}
%>
			
		<tr class="containerBody">
			<td colspan="2"></td>
			<td>TOTALE: <%=((Valutazione)request.getAttribute("valutazione")).getPunteggio()%></td>
		</tr>	
		<tr class="containerBody">
			<td></td>
			<td>RISCHIO: <%=((Valutazione)request.getAttribute("valutazione")).getRischio().toUpperCase()%></td>
			<td>CONSIGLIO: <%=((Valutazione)request.getAttribute("valutazione")).getConsiglio().toUpperCase()%></td>
		</tr>
		<tr class="containerBody">
			<td colspan="3">&nbsp;</td>
		</tr>
		
		<tr class="containerBody">
			<td colspan="3">&nbsp;</td>
		<td colspan="3">&nbsp;</td>
		<tr class="containerBody">
				<th colspan="3">INFORMAZIONI INSERIMENTO</th>
			</tr>
		<tr class="containerBody">
			<td colspan="3">
				Inserito da <dhv:username id="<%=scheda.getEnteredBy()%>" /> il <%=toHtmlValue(toDateasString(scheda.getEntered()))%>
			</td>
		</tr>
		<tr class="containerBody">
			<td colspan="3">
				Modificato da <dhv:username id="<%=scheda.getModifiedBy()%>" /> il <%=toHtmlValue(toDateasString(scheda.getModified()))%>
			</td>
		</tr>
		
		<!--  tr class="containerBody">
			<td colspan="3">
				<input value="Stampa"                 type="button" onclick="openRichiestaPDF('PrintSchedaMorsicatura','<%=animaleDettaglio.getIdAnimale()%>','<%=animaleDettaglio.getIdSpecie()%>', '-1', '-1', '-1');" id="" target="_self" />
				<input value="Inserisci nuova scheda" type="button" onclick="location.href='SchedaMorsicaturaAction.do?command=ToAdd&idAnimale=<%=animaleDettaglio.getIdAnimale()%>';" id="" target="_self" />
			</td>
		</tr-->			

		</table>
		
		<%
		}
		%>
		<script type="text/javascript">
		<%
			if(request.getAttribute("stampaAutomatica")!=null && ((Boolean)request.getAttribute("stampaAutomatica")))
			{
		%>
				var url = "GestioneDocumenti.do?command=GeneraPDF&IdAnimale=<%=animaleDettaglio.getIdAnimale()%>&IdSpecie=<%=animaleDettaglio.getIdSpecie()%>&tipo=PrintSchedaMorsicatura&generazionePulita=si&generaNonLista=ok";
				var result = window.open(url,
						'popupSelect',
						'height=200px,width=842px,left=200px, top=200px,toolbar=no,directories=no,status=no,continued from previous linemenubar=no,scrollbars=yes,resizable=yes ,modal=yes');
				var text = document.createTextNode('GENERAZIONE PDF IN CORSO.');
				span = document.createElement('span');
				span.style.fontSize = "30px";
				span.style.fontWeight = "bold";
				span.style.color ="#ff0000";
				span.appendChild(text);
				var br = document.createElement("br");
				var text2 = document.createTextNode('Attendere la chiusura di questa finestra entro qualche secondo...');
				span2 = document.createElement('span');
				span2.style.fontSize = "20px";
				span2.style.fontStyle = "italic";
				span2.style.color ="#000000";
				span2.appendChild(text2);
				result.document.body.appendChild(span);
				result.document.body.appendChild(br);
				result.document.body.appendChild(span2);
				result.focus();
		<%
			}
		%>	
		</script>