<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<%@page import="java.util.Vector"%>
<%@page import="org.aspcfs.modules.devdoc.base.Flusso"%>
<%@page import="org.aspcfs.modules.devdoc.base.FlussoNota"%>
<%@page import="org.aspcfs.modules.devdoc.base.Modulo"%>
<%@page import="org.aspcfs.modules.devdoc.base.ModuloList"%>

<jsp:useBean id="Flusso" class="org.aspcfs.modules.devdoc.base.Flusso"
	scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />


<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>

<%@page
	import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoModulo"%>
<%@page
	import="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoModuloList"%>
<jsp:useBean id="listaAllegati"
	class="org.aspcfs.modules.gestioneDocumenti.base.DocumentaleAllegatoModuloList"
	scope="request" />

<jsp:useBean id="listaTipiPriorita"
	class="org.aspcfs.utils.web.LookupList" scope="request" />


<%!public static String fixData(String timestring) {
		String toRet = "";
		if (timestring == null || timestring.equals("null"))
			return toRet;
		String anno = timestring.substring(0, 4);
		String mese = timestring.substring(5, 7);
		String giorno = timestring.substring(8, 10);
		String ora = timestring.substring(11, 13);
		String minuto = timestring.substring(14, 16);
		String secondi = timestring.substring(17, 19);
		toRet = giorno + "/" + mese + "/" + anno + " " + ora + ":" + minuto;
		return toRet;

	}%>
<%!public static String fixStringa(String nome) {
		String toRet = nome;
		if (nome == null || nome.equals("null"))
			return toRet;
		toRet = nome.replaceAll("'", "");
		toRet = toRet.replaceAll(" ", "_");
		toRet = toRet.replaceAll("\\?", "");

		return toRet;

	}

	public static String zeroPad(int id) {
		String toRet = String.valueOf(id);
		while (toRet.length() < 3)
			toRet = "0" + toRet;
		return toRet;

	}%>



<%@ include file="../initPage.jsp"%>


<table class="trails" cellspacing="0">
	<tr>
		<td><a href="GestioneFlussoSviluppo.do?command=Dashboard">Richiesta
				documentale sviluppo software</a> > Dettaglio Richiesta</td>
	</tr>
</table>

<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<tr>
		<th colspan="2"><strong>Informazioni Richiesta</strong></th>
	</tr>

	<tr class="containerBody">
		<td nowrap class="formLabel">Richiesta</td>
		<td><%=zeroPad(Flusso.getIdFlusso())%>&nbsp;</td>
	</tr>



	<tr class="containerBody">
		<td nowrap class="formLabel">Descrizione</td>
		<td><%=Flusso.getDescrizione()%>&nbsp;</td>
	</tr>

	<tr class="containerBody">
		<td nowrap class="formLabel">Priorita'</td>
		<td>
			<%
				boolean hasPermission = false;
			%> <dhv:permission
				name="devdoc-priorita-view">
				<%
					hasPermission = true;
				%>
			</dhv:permission> <%
 	//2 � il tipo del Modulo C
 	if (
 			hasPermission && 
 			!Flusso.hasModulo(2) && 
 			Flusso.getDataAnnullamento() == null &&
 			Flusso.getDataConsegna() == null &&
 			Flusso.getDataStandby() == null
 		) { 
 %>
			<form id="update-priorita-form" method="post"
				action="GestioneFlussoSviluppo.do?command=UpdatePriorita&idFlusso=<%=Flusso.getIdFlusso()%>">
				<%=listaTipiPriorita.getHtmlSelect("idPriorita", Flusso.getIdPriorita())%>&nbsp;
				<button type="submit">SALVA</button>
			</form> <%
 	} else {
 %> <%=listaTipiPriorita.getSelectedValue(Flusso.getIdPriorita())%>
			<%
				}
			%>
		</td>
	</tr>

	<tr class="containerBody">
		<td nowrap class="formLabel">Tags</td>
		<td><%=toHtml(Flusso.getTags())%>&nbsp;</td>
	</tr>

	<tr class="containerBody">
		<td nowrap class="formLabel">Data ultima modifica</td>
		<td><%=(Flusso.getData() != null) ? toDateWithTimeasString(Flusso.getData()) : ""%>&nbsp;
		</td>
	</tr>
	<%
		if (Flusso.getDataConsegna() != null) {
	%>
	<tr class="containerBody">
		<td nowrap class="formLabel">Data consegna</td>
		<td><%=toDateWithTimeasString(Flusso.getDataConsegna())%>&nbsp;
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Note consegna</td>
		<td><%=toHtml(Flusso.getNoteConsegna())%>&nbsp;</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Utente consegna</td>
		<td><dhv:username id="<%=Flusso.getIdUtenteConsegna()%>" />
			&nbsp;</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Allegato consegna</td>
		<td>
			<%
				if (listaAllegati.size() > 0)
						for (int i = 0; i < listaAllegati.size(); i++) {
							DocumentaleAllegatoModulo doc = (DocumentaleAllegatoModulo) listaAllegati.get(i);
			%> <a
			href="GestioneAllegatiUpload.do?command=DownloadPDF&codDocumento=<%=doc.getIdHeader()%>&idDocumento=<%=doc.getIdDocumento()%>&tipoDocumento=<%=doc.getEstensione()%>&nomeDocumento=<%=fixStringa(doc.getNomeClient())%>">
				<%=doc.getNomeClient()%></a> <%
 	}
 %>
		</td>
	</tr>
	<%
		}
	%>

	<%
		if (Flusso.getDataStandby() != null) {
	%>
	<tr class="containerBody">
		<td nowrap class="formLabel">Data standby</td>
		<td><%=toDateWithTimeasString(Flusso.getDataStandby())%>&nbsp;
		</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Note standby</td>
		<td><%=toHtml(Flusso.getNoteStandby())%>&nbsp;</td>
	</tr>
	<tr class="containerBody">
		<td nowrap class="formLabel">Utente standby</td>
		<td><dhv:username id="<%=Flusso.getIdUtenteStandby()%>" />
			&nbsp;</td>
	</tr>
	<%
		}
	%>

</table>

<br />
<br />

<table cellpadding="4" cellspacing="0" border="0" class="details">
	<tr>
		<th colspan="4"><strong>Moduli</strong></th>
	</tr>

	<tr>
		<th><strong>Tipo</strong></th>
		<th><strong>Caricato da</strong></th>
		<th><strong>Caricato il</strong></th>
		<th><strong>Dettaglio</strong></th>
	</tr>

	<%
		ModuloList listaModuli = Flusso.getModuli();

		Modulo modB = null;
		Modulo modC = null;
		Modulo modCH = null;
		Modulo modD = null;
		Modulo modA = null;
		Modulo modVCE = null;

		for (int k = 0; k < listaModuli.size(); k++) {
			Modulo mod = (Modulo) listaModuli.get(k);
			switch (mod.getIdTipo()) {
			case 1:
				modB = mod;
				break;
			case 2:
				modC = mod;
				break;
			case 3:
				modD = mod;
				break;
			case 4:
				modCH = mod;
				break;
			case 5:
				modA = mod;
				break;
			case 6:
				modVCE = mod;
				break;
			default:
				break;
			}
		}
	%>

	<dhv:permission name="devdoc-mod-a-view">
		<%
			if (modA != null) {
		%>
		<tr>
			<td>Modulo A</td>
			<td><dhv:username id="<%=modA.getIdUtente()%>" /></td>
			<td><%=toDateasString(modA.getData())%></td>
			<td><a href="#"
				onClick="apriDettaglioModulo('<%=modA.getId()%>'); return false;">Visualizza</a></td>
		</tr>
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="devdoc-mod-b-view">
		<%
			if (modB != null) {
		%>
		<tr>
			<td>Modulo B</td>
			<td><dhv:username id="<%=modB.getIdUtente()%>" /></td>
			<td><%=toDateasString(modB.getData())%></td>
			<td><a href="#"
				onClick="apriDettaglioModulo('<%=modB.getId()%>'); return false;">Visualizza</a></td>
		</tr>
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="devdoc-mod-c-view">
		<%
			if (modC != null) {
		%>
		<tr>
			<td>Modulo C</td>
			<td><dhv:username id="<%=modC.getIdUtente()%>" /></td>
			<td><%=toDateasString(modC.getData())%></td>
			<td><a href="#"
				onClick="apriDettaglioModulo('<%=modC.getId()%>'); return false;">Visualizza</a></td>
		</tr>
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="devdoc-mod-ch-view">
		<%
			if (modCH != null) {
		%>
		<tr>
			<td>Modulo CH</td>
			<td><dhv:username id="<%=modCH.getIdUtente()%>" /></td>
			<td><%=toDateasString(modCH.getData())%></td>
			<td><a href="#"
				onClick="apriDettaglioModulo('<%=modCH.getId()%>'); return false;">Visualizza</a></td>
		</tr>
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="devdoc-mod-d-view">
		<%
			if (modD != null) {
		%>
		<tr>
			<td>Modulo D</td>
			<td><dhv:username id="<%=modD.getIdUtente()%>" /></td>
			<td><%=toDateasString(modD.getData())%></td>
			<td><a href="#"
				onClick="apriDettaglioModulo('<%=modD.getId()%>'); return false;">Visualizza</a></td>
		</tr>
		<%
			}
		%>
	</dhv:permission>
	<dhv:permission name="devdoc-vce-view">
		<%
			if (modVCE != null) {
		%>
		<tr>
			<td>Modulo VCE</td>
			<td><dhv:username id="<%=modVCE.getIdUtente()%>" /></td>
			<td><%=toDateasString(modVCE.getData())%></td>
			<td><a href="#"
				onClick="apriDettaglioModulo('<%=modVCE.getId()%>'); return false;">Visualizza</a></td>
		</tr>
		<%
			}
		%>
	</dhv:permission>
</table>
<br>
<br>
<form id="form-aggiungi-nota" name="form-aggiungi-nota" method="post"
	action="GestioneFlussoSviluppo.do?command=AggiungiNota&idFlusso=<%=Flusso.getIdFlusso()%>"
	onsubmit="checkFormNoteFlusso(event)">
	<table class="details" cellpadding="4">
		<thead>
			<tr>
				<th colspan="3">NOTE</th>
			</tr>
			<%
				if (!Flusso.getNote().isEmpty()) {
			%>
			<tr>
				<th>NOTA</th>
				<th>INSERITA DA</th>
				<th>INSERITA IL</th>
			</tr>
			<%
				}
			%>
		</thead>
		<tbody>
			<%
				if (!Flusso.getNote().isEmpty()) {
			%>
			<%
				for (FlussoNota nota : Flusso.getNote()) {
			%>
			<tr>
				<td><%=nota.getNota()%></td>
				<td><dhv:username id="<%=nota.getIdUtente()%>" /></td>
				<td><%=toDateasStringWitTime(nota.getDataInserimento())%></td>
			</tr>
			<%
				}
			%>
			<%
				}
			%>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="2"><textarea id="nuova-nota" name="nuova-nota"
						required style="width: 100%;"></textarea></td>
				<td style="text-align: center;"><input id="submit-button"
					name="submit-button" type="submit" value="INSERISCI"></td>
			</tr>
		</tfoot>
	</table>
</form>

