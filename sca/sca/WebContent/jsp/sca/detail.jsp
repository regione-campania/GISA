<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>


<%@page import="java.util.Iterator"%>
<%@page import="it.us.web.bean.guc.Ruolo"%>
<%@page import="it.us.web.bean.guc.Clinica"%>

<jsp:useBean id="UserRecord" class="it.us.web.bean.guc.Utente" scope="request"/>

<div id="content" align="center">

<div align="center">
	<a href="Home.us" style="margin: 0px 0px 0px 50px"><img src="images/lista.png" height="18px" width="18px" />Lista Utenti</a>
	<a href="guc.ToEdit.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/edit.gif" height="18px" width="18px" />Modifica Utente</a>
<!--  	<a href="guc.ToEnable.us?id=${UserRecord.id}" style="margin: 0px 0px 0px 50px"><img src="images/enable.gif" height="18px" width="18px" />Attiva/Disattiva Utente</a> -->
	<a href="guc.ToAdd.us" style="margin: 0px 0px 0px 50px"><img src="images/add.png" height="18px" width="18px" />Aggiungi Utente</a>
</div>

	<h4 class="titolopagina">Dettaglio Utente</h4>
	
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th class="title" colspan="2">
        <strong>Contatto</strong>&nbsp;
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">Nome</td>
      <td>${UserRecord.nome}</td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">Cognome</td>
      <td>${UserRecord.cognome}</td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">Codice Fiscale</td>
      <td>${UserRecord.codiceFiscale}</td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">E-Mail</td>
      <td>${UserRecord.email}</td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">Note</td>
      <td>${UserRecord.note}</td>
    </tr>
    <tr>
      <th class="title" colspan="2">
        <strong>Utente</strong>&nbsp;
      </th>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">Username</td>
      <td>${UserRecord.username}</td>
    </tr>
    <tr class="containerBody">
      <td class="formLabel">Scadenza login</td>
      <td><fmt:formatDate value="${UserRecord.expires}" pattern="dd/MM/yyyy"/> </td>
    </tr>
    <tr>
      <th class="title" colspan="2">
        <strong>A.S.L.</strong>&nbsp;
      </th>
    </tr>
    <tr class="containerBody">
      <td nowrap class="formLabel">Asl</td>
    <td><%= UserRecord.getAsl().getId() == -1 ? "TUTTE LE ASL" : UserRecord.getAsl().getNome() != null && !UserRecord.getAsl().getNome().equals("") ? UserRecord.getAsl().getNome() : "Nessuna" %></td>
    </tr>
    
    <tr>
      <th class="title" colspan="2">
        <strong>Cliniche</strong>&nbsp;
      </th>
    </tr>
   <%for(Clinica c : UserRecord.getClinicheVam()){ %>
    <tr class="containerBody">
      <td class="formLabel">Clinica </td>
      <td><%=c.getDescrizioneClinica() %></td>
    </tr>
    <%} %>
    
    <tr>
      <th class="title" colspan="2">
        <strong>Canili</strong>&nbsp;
      </th>
    </tr>
    <% if(UserRecord.getCanileDescription() != null && !UserRecord.getCanileDescription().equals("")){ %>
    <tr class="containerBody">
      <td class="formLabel">Canile Canina</td>
      <td>${UserRecord.canileDescription}</td>
    </tr>
    <%} %>
    
     <tr>
      <th class="title" colspan="2">
        <strong>Canili di BDU</strong>&nbsp;
      </th>
    </tr>
    <% if(UserRecord.getCaniliBdu()!=null && UserRecord.getCaniliBdu().size()>0 && UserRecord.getCaniliBdu().get(0)!=null && UserRecord.getCaniliBdu().get(0).getDescrizioneCanile()!=null && !UserRecord.getCaniliBdu().get(0).getDescrizioneCanile().equals("")){ %>
    <tr class="containerBody">
      <td class="formLabel">Canile bdu</td>
      <td><%=UserRecord.getCaniliBdu().get(0).getDescrizioneCanile()%></td>
    </tr>
    <%} %>
    
    <tr>
      <th class="title" colspan="2">
        <strong>Importatori di BDU</strong>&nbsp;
      </th>
    </tr>
    <% if(UserRecord.getImportatori()!=null && UserRecord.getImportatori().size()>0 && UserRecord.getImportatori().get(0)!=null && UserRecord.getImportatori().get(0).getRagioneSociale()!=null && !UserRecord.getImportatori().get(0).getRagioneSociale().equals("")){ %>
    <tr class="containerBody">
      <td class="formLabel">Importatori bdu</td>
      <td><%=UserRecord.getImportatori().get(0).getRagioneSociale()%></td>
    </tr>
    <%} %>
    
    
    <tr>
      <th class="title" colspan="2">
        <strong>Ruoli</strong>&nbsp;
      </th>
    </tr>
    <%for(String endpoint : UserRecord.getHashRuoli().keySet()){ %>
    <tr class="containerBody">
      <td class="formLabel">Ruolo <%= endpoint %></td>
      <td><%= UserRecord.getHashRuoli().get(endpoint)!=null && UserRecord.getHashRuoli().get(endpoint).getRuoloInteger() > 0 ? UserRecord.getHashRuoli().get(endpoint).getRuoloString() : "N.D." %></td>
    </tr>
    <%} %>
    
  </table>
 	
</div>

