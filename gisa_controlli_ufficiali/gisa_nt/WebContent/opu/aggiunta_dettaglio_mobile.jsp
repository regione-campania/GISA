<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ include file="../initPage.jsp"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttivaList"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="org.aspcfs.modules.opu.base.LineeMobiliHtmlFields"%>
<%@page import="java.util.*"%>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/suapUtil.js"></script>

<jsp:useBean id="messaggioOk" class="java.lang.String" scope="request"/>

<jsp:useBean id="campiHash" class="java.util.LinkedHashMap" scope="request"/>
<jsp:useBean id="campiHashSenzaValore" class="java.util.LinkedHashMap" scope="request"/>
<jsp:useBean id="campiHashVisualize" class="java.util.LinkedHashMap" scope="request"/>
<jsp:useBean id="Operatore" class="org.aspcfs.modules.opu.base.Operatore" scope="request"/>
<jsp:useBean id="StabilimentoDettaglio" class="org.aspcfs.modules.opu.base.Stabilimento" scope="request"/>
<jsp:useBean id="TipoAlimentoDistributore" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoMobili" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<%
	boolean consentiUploadFile = (Boolean)request.getAttribute("consentiUploadFile");
	boolean consentiValoriMultipli = (Boolean)request.getAttribute("consentiValoriMultipli");
%>


	<%if (messaggioOk!=null && !messaggioOk.equals("")){ %>
	<script>alert('<%=messaggioOk%>');
	//window.close();
	window.location.href='OpuStab.do?command=GestioneMobile&stabId=<%=StabilimentoDettaglio.getIdStabilimento()%>&opId=<%=Operatore.getIdOperatore()%>';
	</script>
	<font color="green"><b><%=messaggioOk %></b></font>
	<%}else{ %>
	
	
	
<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>
</div>

<input type="button" value="ESCI" onClick="$( '#dialogMOBILE' ).dialog('close');"/>

<font color="red">
<br/>
Si ricorda che la funzionalita' di ELIMINA e' disponibile solo per gli amministratori di sistema.<br/>
La funzionalita' di DISMISSIONE e' disponibile per tutti gli utenti e consente di disabilitare un automezzo/distributore <br/>
per poterlo spostare o reinserire con i dati aggiornati.<br/>
</font>


<!-- -----------FORM VISUALIZZAZIONE----------- -->
<% 
int numCol=	(Integer)request.getAttribute("numeroCampi");
Set<Entry> entries = campiHash.entrySet();

ArrayList<LineeMobiliHtmlFields> elementi=(ArrayList<LineeMobiliHtmlFields>) request.getAttribute("listaElementi");


if (!entries.isEmpty())
{ 

%>
<br><br>


<%
	if(consentiValoriMultipli)
	{
%>
		<table width="100%">
			<th align="center" colspan="<%=(numCol+2) %>" bgcolor="#5CB8E6">ELEMENTI GIA' PRESENTI</th>
			<tr>
<% 
			// Costruisco intestazione tabella elemtni presenti
			for (Entry entry : entries) 
			{ 
%>
				<td align="center" style="border:1px solid black;"> 
					<b><%=entry.getKey().toString().toUpperCase() %></b>
				</td>
<% 
			} 
%>
				<td align="center" style="border:1px solid black;"><b>DATI INSERIMENTO </b></td>
				
				<td align="center" style="border:1px solid black;"><b>OPERAZIONE </b></td>
			</tr>
<%
			int i=1;
			String indice="";
			// Costruisco tabella elementi presenti
			Iterator<LineeMobiliHtmlFields> elem =  elementi.iterator();
			boolean containsDismissione = false;

			while(elem.hasNext())
			{ 
				containsDismissione = false;
				LineeMobiliHtmlFields elemento = (LineeMobiliHtmlFields) elem.next();
				if (elemento.getNome_campo().equalsIgnoreCase("data_dismissione")) { containsDismissione = true;}
				if(i==1)
				{
%>
					<tr>
<% 
				} 
%>
					<td style="border:1px solid black;" bgcolor="#E6E6E6">&nbsp;
<%
					if(elemento.getTabella_lookup()==null || elemento.getTabella_lookup().equals(""))
					if(elemento.getTipo_campo().equals("checkbox"))
					{
						out.print((elemento.getValore_campo()!=null && elemento.getValore_campo().equalsIgnoreCase("true")) ? "SI": "NO");
					}
					else
						out.print((elemento.getValore_campo()!=null) ? elemento.getValore_campo().toUpperCase(): "");
					else
					{
						if(elemento.getTabella_lookup().contains("mobili"))
							out.print(TipoMobili.getSelectedValue(elemento.getValore_campo()).toUpperCase());
						else
							out.print(elemento.getLookupCampo().getSelectedValue(elemento.getValore_campo()).toUpperCase());
					}
%>
					</td>
<% 
					i++;
					if(i==numCol+1)
					{
						i=1;
						indice=elemento.getIndice();
%>
					<td align="center" style="border:1px solid black;" bgcolor="#E6E6E6"><dhv:username id="<%= elemento.getIdUtenteInserimento() %>" /> il <%=toDateasString(elemento.getDataInserimento()) %>    </td>
					<td align="center" style="border:1px solid black;" bgcolor="#E6E6E6">
					
					
					<% if (containsDismissione){ %>
					<dhv:permission name="datiaggiuntivi-edit">
					<input type="button" name="button_dismissione_<%=indice %>" id="button_dismissione_<%=indice %>" value="Dismetti" onclick="if ( document.getElementById('note_dismissione_<%=indice %>').style.display=='none'){document.getElementById('note_dismissione_<%=indice %>').style.display = 'block';document.getElementById('data_dismissione_<%=indice %>').style.display = 'block';document.getElementById('button_dismissione_ok_<%=indice %>').style.display = 'block'; document.getElementById('button_dismissione_<%=indice %>').style.display = 'none';	}"/>
					<input type="button" name="button_dismissione_ok_<%=indice %>" id="button_dismissione_ok_<%=indice %>" style="display:none" value="Conferma dismissione" onclick="if(document.getElementById('note_dismissione_<%=indice %>').value=='' || document.getElementById('data_dismissione_<%=indice %>').value=='') {alert('Attenzione! Inserire le note e la data.'); return false; } if(confirm('Attenzione. Procedere alla DISMISSIONE di questo dato aggiuntivo?')) { document.getElementById('label_dismissione_attendere_<%=indice %>').style.display = 'block'; var i =document.getElementsByTagName('input'); for( var n in i){if(i[n].type == 'button'){i[n].style.visibility = 'hidden';}}; location.href='OpuStab.do?command=DismettiMobileDaLinea&indice=<%=indice%>&ldaStabId=<%=request.getParameter("ldaStabId") %>&stabId=<%=request.getParameter("stabId") %>&note='+document.getElementById('note_dismissione_<%=indice %>').value+'&data='+document.getElementById('data_dismissione_<%=indice %>').value}"/>
					<label name="label_dismissione_attendere_<%=indice %>" id="label_dismissione_attendere_<%=indice %>" style="display:none" >ATTENDERE. OPERAZIONE IN CORSO.</label>
					<input type="date" name="data_dismissione_<%=indice %>" id="data_dismissione_<%=indice %>" style="display:none" value=""/><br/>
					<input type="text" name="note_dismissione_<%=indice %>" id="note_dismissione_<%=indice %>" style="display:none" value="" maxlength="20" onkeyup="this.value=this.value.replace(/[^0-9 a-zA-z]+/,'')"/><br/>
					</dhv:permission>
					<%} %>
					<dhv:permission name="datiaggiuntivi-delete">
					<input type="button" value="Elimina" onclick="if(confirm('Attenzione. Procedere alla CANCELLAZIONE di questo dato aggiuntivo?')) { location.href='OpuStab.do?command=EliminaMobileDaLinea&indice=<%=indice%>&ldaStabId=<%=request.getParameter("ldaStabId") %>&stabId=<%=request.getParameter("stabId") %>'}"/>
					</dhv:permission>
					</td></tr>
<%
					}
				} 
%>
</table>
<%
	}
	else
	{
%>
		<table width="100%">
			<th align="center" colspan="<%=(numCol+2) %>" bgcolor="#5CB8E6">ELEMENTI GIA' PRESENTI</th>
			<tr>
				<td align="center" style="border:1px solid black;"> 
					NOME CAMPO
				</td>
				<td align="center" style="border:1px solid black;"> 
					VALORE
				</td>
				<td align="center" style="border:1px solid black;"> 
					DATI MODIFICA
				</td>
			</tr>
<% 
			Iterator<LineeMobiliHtmlFields> elem =  elementi.iterator();
			for (Entry entry : entries) 
			{ 
				LineeMobiliHtmlFields elemento = (LineeMobiliHtmlFields) elem.next();
%>
				<tr>
				<td style="border:1px solid black;" bgcolor="#E6E6E6">
					<%=entry.getKey().toString().toUpperCase()%>
				</td>
				<td style="border:1px solid black;" bgcolor="#E6E6E6">
<%
				if(elemento.getTabella_lookup()==null || elemento.getTabella_lookup().equals(""))
					if(elemento.getTipo_campo().equals("checkbox"))
					{
						out.print((elemento.getValore_campo()!=null && elemento.getValore_campo().equalsIgnoreCase("true")) ? "SI": "NO");
					}
					else
						out.print((elemento.getValore_campo()!=null) ? elemento.getValore_campo().toUpperCase(): "");
				else
				{
					if(elemento.getTabella_lookup().contains("mobili"))
						out.print(TipoMobili.getSelectedValue(elemento.getValore_campo()).toUpperCase());
					else if (elemento.isMultiple())
					{
						String[] valoriSplittati = elemento.getValore_campo().split(";");
						for(int i=0;i<valoriSplittati.length;i++)
						{
							out.print(elemento.getLookupCampo().getSelectedValue(valoriSplittati[i]).toUpperCase());
							if(i<valoriSplittati.length-1)
								out.print(", ");
						}
					}
						
					else
						out.print(elemento.getLookupCampo().getSelectedValue(elemento.getValore_campo()).toUpperCase());
				}
%>
				</td>
				<td style="border:1px solid black;" bgcolor="#E6E6E6">
					Inserito da <dhv:username id="<%= elemento.getIdUtenteInserimento() %>" /> il <%=toDateasString(elemento.getDataInserimento()) %>
<%
					if(elemento.getDataModifica()!=null)
					{
%>
						<br/>Modificato da <dhv:username id="<%= elemento.getIdUtenteModifica() %>" /> il <%=toDateasString(elemento.getDataModifica()) %>
<%
					}
%>
				</td>
				</tr>
<%
			}
%>
		</table>
<%
	} 
}
%>
	
<%
	if(consentiValoriMultipli)
	{
%>
<!-- 		<font color="red"><b>Attenzione! Per l'aggiornamento del singolo automezzo bisogna eliminare e reinserire il dato.</b></font> -->
<%
	}
%>
<!-- ------------------------------------------ -->

<br><br>


<%
	if(consentiValoriMultipli || entries.isEmpty())
	{
%>
<form method="post" name="modificaModulo" id="modificaModulo" action="OpuStab.do?command=InserisciDettaglioMobile">

<input type="hidden" id="ldaMacroId" name="ldaMacroId" value="<%=request.getParameter("ldaMacroId") %>"/>
<input type="hidden" id="ldaStabId" name="ldaStabId" value="<%=request.getParameter("ldaStabId") %>"/>
<input type="hidden" id="stabId" name="stabId" value="<%=request.getParameter("stabId") %>"/>

<input type="hidden" id="impresa_hid" name="impresa_hid" value="<%=StabilimentoDettaglio.getName() %>"/>
<input type="hidden" id="indirizzo_hid" name="indirizzo_hid" value="<%=	(String)request.getAttribute("indirizzohid") %>"/>
<input type="hidden" id="comune_hid" name="comune_hid" value="<%=(String)request.getAttribute("comunehid") %>"/>
<input type="hidden" id="provincia_hid" name="provincia_hid" value="<%=(String)request.getAttribute("provinciahid") %>"/>
<input type="hidden" id="asl_hid" name="asl_hid" value="<%=(String)request.getAttribute("aslhid") %>"/>
<input type="hidden" id="cap_hid" name="cap_hid" value="<%=(String)request.getAttribute("caphid") %>"/>
<input type="hidden" id="asl_hid" name="asl_hid" value="<%=(String)request.getAttribute("aslhid") %>"/>
<table width="100%">
<th align="center" colspan="2" bgcolor="#5CB8E6">INSERIMENTO DETTAGLI LINEA ATTIVITA'</th>

<%
Set<Entry> entriesSenzaValore = campiHashSenzaValore.entrySet();
for (Entry elementoSenzaValore : entriesSenzaValore) 
{
		
		
%>
<tr style="border:1px solid black;"><td style="border:1px solid black;"> 
&nbsp;&nbsp;<b><%=elementoSenzaValore.getKey().toString().toUpperCase() %></b>
</td>

<td style="border:1px solid black;" bgcolor="#E6E6E6">
<%=elementoSenzaValore.getValue() %>
</td></tr>

<% } %>

</table>

<input type="submit" value="SALVA" />




&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<input type="button" value="PULISCI" onClick="pulisciCampi()"/>

<%
	}
	else
	{
%>
	<form method="post" name="modificaModulo" id="modificaModulo" action="OpuStab.do?command=ModificaDettaglioMobile">
	<input type="hidden" id="ldaMacroId" name="ldaMacroId" value="<%=request.getParameter("ldaMacroId") %>"/>
<input type="hidden" id="ldaStabId" name="ldaStabId" value="<%=request.getParameter("ldaStabId") %>"/>
<input type="hidden" id="stabId" name="stabId" value="<%=request.getParameter("stabId") %>"/>

<input type="hidden" id="impresa_hid" name="impresa_hid" value="<%=StabilimentoDettaglio.getName() %>"/>
<input type="hidden" id="indirizzo_hid" name="indirizzo_hid" value="<%=	(String)request.getAttribute("indirizzohid") %>"/>
<input type="hidden" id="comune_hid" name="comune_hid" value="<%=(String)request.getAttribute("comunehid") %>"/>
<input type="hidden" id="provincia_hid" name="provincia_hid" value="<%=(String)request.getAttribute("provinciahid") %>"/>
<input type="hidden" id="asl_hid" name="asl_hid" value="<%=(String)request.getAttribute("aslhid") %>"/>
<input type="hidden" id="cap_hid" name="cap_hid" value="<%=(String)request.getAttribute("caphid") %>"/>
<input type="hidden" id="asl_hid" name="asl_hid" value="<%=(String)request.getAttribute("aslhid") %>"/>

		<table width="100%">
		<th align="center" colspan="2" bgcolor="#5CB8E6">MODIFICA DETTAGLI LINEA ATTIVITA'</th>
<%
		for (Entry elemento : entries) 
		{
%>
			<tr style="border:1px solid black;">
				<td style="border:1px solid black;"> 
					&nbsp;&nbsp;<b><%=elemento.getKey().toString().toUpperCase() %></b>
				</td>
				<td style="border:1px solid black;" bgcolor="#E6E6E6">
					<%=elemento.getValue() %>
				</td></tr>
	
<% 
		} 
%>

		</table>

		<input type="submit" value="AGGIORNA" />

		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="PULISCI" onClick="pulisciCampi()"/>

<%
	}
%>
<script>


inizializzaForm();

</script>



</form>

<br><br><br>
<form method="post" action="OpuStab.do?command=ImportDistributori" enctype="multipart/form-data">
<input type="hidden" id="ldaMacroId" name="ldaMacroId" value="<%=request.getParameter("ldaMacroId") %>"/>
<input type="hidden" id="ldaStabId" name="ldaStabId" value="<%=request.getParameter("ldaStabId") %>"/>
<input type="hidden" id="stabId" name="stabId" value="<%=request.getParameter("stabId") %>"/>


<%


if(consentiUploadFile)
{
%>
<table width="100%">
<tr>
<th align="center" colspan="2" bgcolor="#5CB8E6">IMPORTA DISTRIBUTORI DA FILE</th>
</tr>
<tr style="border:1px solid black;">
<td style="border:1px solid black;">  Seleziona File</td>

<td><input type="file" name="file1" required="required">
</td>
<td><a download href="images/esempio_import_distributori.csv">SCARICA FILE ESEMPIO <br>(eliminare la intestazione nel file)</a></td> 

</tr>
<tr><td colspan="2"><input type="submit" value="SALVA"></td></tr>
</table>
</form>
<%} %>
<input type="button" value="ESCI" onClick="$( '#dialogMOBILE' ).dialog('close');"/>


<%
	}
%>












