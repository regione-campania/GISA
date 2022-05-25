<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ page
	import="java.io.*,java.util.*,org.aspcfs.utils.web.*,org.aspcfs.modules.opu.base.*,org.aspcfs.modules.registrazioniAnimali.base.*"%>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="EventoInserimentoVaccinazioni"
	class="org.aspcfs.modules.registrazioniAnimali.base.EventoInserimentoVaccinazioni"
	scope="session" />
<jsp:useBean id="animale"
	class="org.aspcfs.modules.anagrafe_animali.base.Animale"
	scope="request" />


<meta charset="utf-8" />
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />

<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>


<script language="javascript" SRC="javascript/CalendarPopup.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/dateControl.js"></script>
<script language="javascript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>

<script type="text/javascript" src="dwr/interface/DwrUtil.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>




<script language="JavaScript" TYPE="text/javascript"
	SRC="gestione_documenti/generazioneDocumentale.js"></script>
	


<%@ include file="../initPage.jsp"%>
<dhv:evaluate if="<%=!isPopup(request)%>">
	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="ProfilassiRabbia.do"><dhv:label
				name="">Profilassi Rabbia</dhv:label></a> > <dhv:label name="">Stampa certificati</dhv:label>
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
</dhv:evaluate>
<body>
<table>
<tr><td><img src="images/icons/stock_print-16.gif" border="0"
				align="absmiddle" height="16" width="16" />
			<!-- a href="#"
				onclick="openCampioniRabbia('<%=animale.getIdAnimale()%>','<%=animale.getIdSpecie()%>');"
				id="" target="_self">Titolazione anticorpi rabbia</a></td></tr-->
				
				<a href="#"
				onclick="openRichiestaPDF('PrintRichiestaCampioniRabbia', '<%=animale.getIdAnimale()%>','<%=animale.getIdSpecie()%>', '-1', '-1', '-1');"
				id="" target="_self">Titolazione anticorpi rabbia</a> </td></tr>
				

<tr><td><img src="images/icons/stock_print-16.gif" border="0"
				align="absmiddle" height="16" width="16" />
			<!--  a href="#"
				onclick="openCertificatoRabbia('<%=animale.getIdAnimale()%>','<%=animale.getIdSpecie()%>');"
				id="" target="_self">Certificazione di avvenuta vaccinazione</a> <%=showWarning(request, "avvisoCertificati", false)%> </td></tr-->
				
				<a href="#"
				onclick="openRichiestaPDF('PrintCertificatoVaccinazioneAntiRabbia', '<%=animale.getIdAnimale()%>','<%=animale.getIdSpecie()%>', '-1', '-1', '-1');"
				id="" target="_self">Certificazione di avvenuta vaccinazione</a> <%=showWarning(request, "avvisoCertificati", false)%> </td></tr>
				

</table>

 </br>
</br>

</br>

