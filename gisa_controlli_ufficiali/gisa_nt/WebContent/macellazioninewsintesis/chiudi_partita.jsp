<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="org.aspcfs.modules.macellazioninewsintesis.base.Tipo"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page import="org.aspcfs.modules.stabilimenti.base.SottoAttivita,java.util.*,java.text.DateFormat,org.aspcfs.modules.stabilimenti.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.base.Constants"%>
<%@ page  import="java.util.Date"%>
<%@ include file="../initPage.jsp"%>

<link href="css/nonconformita.css" rel="stylesheet" type="text/css" />

<jsp:useBean id="OrgCategoriaRischioList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.sintesis.base.SintesisStabilimento" scope="request" />
<jsp:useBean id="SICCodeList" class="org.aspcfs.modules.admin.base.SICCodeList" scope="request" />
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application" />
<jsp:useBean id="Audit" class="org.aspcfs.modules.audit.base.Audit" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="statiStabilimenti" class="org.aspcfs.utils.web.LookupList" 	scope="request" />
<jsp:useBean id="LookupClassificazione" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LookupProdotti" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" 	scope="request" />
<jsp:useBean id="TitoloList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="impianto" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="statoLab" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="statoLabImpianti" class="org.aspcfs.utils.web.LookupList"	scope="request" />
<jsp:useBean id="RatingList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="categoriaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="imballataList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="tipoAutorizzazioneList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session" />
<jsp:useBean id="refreshUrl" class="java.lang.String" scope="request" />
<jsp:useBean id="elencoSottoAttivita" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="impreseAssociateMercatoIttico" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="stabilimentiAssociateMercatoIttico" class="java.util.ArrayList" scope="request" />
<jsp:useBean id="ControlloDocumentale" class="org.aspcfs.modules.stabilimenti.base.ControlloDocumentale" scope = "request"></jsp:useBean>
<script language="JavaScript" TYPE="text/javascript" SRC="gestione_documenti/generazioneDocumentale.js"></script>

<% 
	java.util.Date datamio = new java.util.Date(System.currentTimeMillis());
	Timestamp d = new Timestamp (datamio.getTime());
	String id = (String)request.getAttribute("id");
%>

	<input type="hidden" name="altId" value="<%= OrgDetails.getAltId() %>">
	
	
	<input type = "hidden" name = "altId" value = "<%=OrgDetails.getAltId() %>">
	<%-- STABILIMENTO IN ISTRUTTORIA PRELIMINARE 	PERMESSI DA ATTIVARE SOLO ALL'ASL --%>
	
		
	
	
		
	<!-- se l'esito del controllo documentale � 
		 andato a buon fine l'asl invia una richiesta 
		 condizionata o definitiva 
		 
	-->
	
	
		
	<%
	boolean dataisnull = false ;
	if(elencoSottoAttivita.size()>0 && elencoSottoAttivita.get(0)!=null )
	{
		Iterator it2 = elencoSottoAttivita.iterator();
		while (it2.hasNext())
		{
			SottoAttivita sa = (SottoAttivita) it2.next();	
			if (sa.getData_inizio_attivita()==null)
			{
				dataisnull = true ;
				break ;
			}
		}
	}
%>
<div id="tab1" class="tab">

	<table cellspacing="0" border="0" width="100%" class="details">
		<tr>
			<th colspan="2">
				Chiusura Partita
			</th>
		</tr>
		<tr>
			<td>
				Note
			</td>
			<td>
				<textarea style="width:100%;" rows="10" name="cd_motivazione_chiusura" id="cd_motivazione_chiusura"></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" value="Invia" onclick="checkForm();"></input>
				<input type="button" value="Chiudi" onclick="window.close();"></input>
			</td>
		</tr>
	</table>
	
		</div>
		<br/>
		
			<div id="tab3" class="tab">
		</div>
		<br>

<script type="text/javascript">
var tabber=new Yetii('tabcont1',1);
tabber.init();

function checkForm()
{
	var cd_motivazione_chiusuraNote = document.getElementById('cd_motivazione_chiusura').value;
	if(cd_motivazione_chiusuraNote=='')
	{
		alert('Inserire delle note');
		return false;
	}
	else
	{
		window.close();
		window.opener.location.href='MacellazioniNewSintesis.do?command=ChiusuraPartita&idPartita=<%=id%>&altId=<%=OrgDetails.getAltId()%>&tipo=2&cd_motivazione_chiusura=' + cd_motivazione_chiusuraNote;	
	}
}
</script>