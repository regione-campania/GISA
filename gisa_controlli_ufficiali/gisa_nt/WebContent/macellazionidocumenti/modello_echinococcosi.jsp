<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@page import="java.net.InetAddress"%>
  <%@ page import="java.util.*"%>
  <%@page import="org.aspcfs.modules.accounts.base.OrganizationAddress"%>
 <jsp:useBean id="mod" class="org.aspcfs.modules.macellazionidocumenti.base.ModelloGenerico" scope="request"/>
 <jsp:useBean id="macello" class="org.aspcfs.modules.stabilimenti.base.Organization" scope="request"/>
 <jsp:useBean id="partita" class="org.aspcfs.modules.macellazioninew.base.Partita" scope="request"/>
 <jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList"	scope="request" />
 <jsp:useBean id="organo" class="java.lang.String"	scope="request" />
   
   <jsp:useBean id="nomeMacello" class="java.lang.String" scope="request"/>
<jsp:useBean id="approvalNumber" class="java.lang.String" scope="request"/>
<jsp:useBean id="comuneMacello" class="java.lang.String" scope="request"/>

<link rel="stylesheet" documentale_url="" href="css/moduli_print.css" type="text/css" media="print" />
<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="css/moduli_screen.css">
	
   
<table width="100%"">
<col width="33%"> <col width="33%">
<tr>
<td>
<div class="boxIdDocumento"></div><br/>
<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>
</td>
<td></td>
<td><div align="right"><img style="text-decoration: none;" height="80" documentale_url="" src="gestione_documenti/schede/images/<%=SiteList.getSelectedValue(mod.getAslMacello()).toLowerCase() %>.jpg" /></div></td>
</tr>

</table>

<br/>

<br/>



<center><b>ECHINOCOCCOSI / IDATIDOSI</b></center>
<center><b>SCHEDA DI SEGNALAZIONE AL MACELLO</b></center>
<br/><br/>

<table>
<tr><td> <b>DATA</b> </td> <td>........................................</td></tr>
<tr><td> <b>DR</b> </td> <td>........................................</td></tr>
<tr><td> <b>ASL N.</b> </td> <td><%=SiteList.getSelectedValue(mod.getAslMacello()).toUpperCase() %></td></tr>
<tr><td> <b>MACELLO</b> </td> <td><%=nomeMacello %></td> <TD> <b>TEL</b></TD> <TD> ......................... </TD></tr>
<tr><td></td> <td><b> COMUNE</b> </td> <td><%=comuneMacello %></td> <td></td></tr>
</table>
<br/><br/>

<b>ALLEVAMENTO DI PROVENIENZA</b><BR/>
<B>Allevamento</B>
<table>
<tr><td> <b>Matricola animale</b> </td> <td>........................................</td></tr>
<tr><td> <b>Codice identificativo azienda</b> </td> <td><%=partita.getCd_codice_azienda_provenienza() %></td></tr>
<tr><td> <b>Denominazione azienda</b> </td> <td><%=partita.getCd_info_azienda_provenienza() %></td></tr>
<tr><td> <b>Proprietario</b> </td> <td></td></tr>
<tr><td> <b>Via</b> </td> <td>............................................ </td> <TD><b>N.</b></TD> <TD> .......................................</TD></tr>
<tr><td> <b>Comune</b> </td> <td>.........................................</td> <td> <b>Prov.</b> </td> <td>...............................</td> </tr>
<tr><td> <b>ASL</b> </td> <td>........................................</td></tr> 
</table>
<br/><br/>

<B>Specie animali esaminati</B>
<table>
<tr><td> [ ] Ovini </td> </tr>
<tr><td> [ ] Caprini </td> </tr>
</table>
<br/><br/>

<B>Sesso</B>
<table>
<tr><td> [ ] M </td> </tr>
<tr><td> [ ] F </td> </tr>
<tr><td> Età in mesi </td> <td>........................................</td> </tr>
</table>
<br/><br/>

<B>REPERTO ISPETTIVO</B>
<% String nomeOrgano = "";
if (organo!=null){
	if (organo.equalsIgnoreCase("fegato"))
		nomeOrgano = "fegato";
	else if (organo.equalsIgnoreCase("milza"))
		nomeOrgano = "milza";
	else if (organo.equalsIgnoreCase("polmoni"))
		nomeOrgano = "polmoni";
	else if (organo.equalsIgnoreCase("cuore"))
		nomeOrgano = "cuore";
	else if (organo.equalsIgnoreCase("intestino"))
		nomeOrgano = "intestino";
	else 
		nomeOrgano = "altro";
}
%>


<table>
<tr><td> Localizzazione cisti </td>  <td> Fegato </td> <td> [ <%= (nomeOrgano.equalsIgnoreCase("fegato")) ? "X" : "" %>] </td></tr>
<tr><td>  </td>  <td> Milza </td> <td> [ <%= (nomeOrgano.equalsIgnoreCase("milza")) ? "X" : "" %>] </td></tr>
<tr><td>  </td>  <td> Polmone </td> <td> [ <%= (nomeOrgano.equalsIgnoreCase("polmoni")) ? "X" : "" %>] </td></tr>
<tr><td>  </td>  <td> Cuore </td> <td> [<%= (nomeOrgano.equalsIgnoreCase("cuore")) ? "X" : "" %> ] </td></tr>
<tr><td>  </td>  <td> Pacchetto intestinale </td> <td> [<%= (nomeOrgano.equalsIgnoreCase("intestino")) ? "X" : "" %> ] </td></tr>
<tr><td>  </td>  <td> Altri organi </td> <td> [ <%= (nomeOrgano.equalsIgnoreCase("altro")) ? "X" : "" %>] </td></tr>
</table>
<br><br><br>
<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>
<br/><br>

