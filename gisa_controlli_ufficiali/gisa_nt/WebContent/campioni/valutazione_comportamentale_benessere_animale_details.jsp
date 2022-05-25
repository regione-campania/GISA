<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@page import="org.aspcfs.modules.campioni.base.Ticket"%>
<%@page import="org.aspcfs.modules.campioni.base.MacroareaAnomalieValutazione"%><jsp:useBean id="DettaglioCampione" class="com.darkhorseventures.framework.beans.GenericBean" scope="request"/>
<jsp:useBean id="SchedaValutazione" class="org.aspcfs.modules.campioni.base.MacroareaValutazioneComportamentale" scope="request"/>
<jsp:useBean id="lookup_stato_generale" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_anomalie" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_tolleranza" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_adottabilita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="layout" class="java.lang.String" scope="request"/>

<%@ include file="../initPage.jsp" %>

<%
Ticket dettCampione = (Ticket) DettaglioCampione ;
%>

<table class="trails" cellspacing="0">
<tr>
<td>


Campione > Scheda Valutazione
</td>
</tr>
</table>

<%
String url = "" ;
String dett = dettCampione.getURlDettaglio() ;
if (dett.equalsIgnoreCase("canipadronali"))
{
	url+= "org.aspcfs.modules.canipadronali.actions."+dett+"Campioni" ;
}
if (dett.equalsIgnoreCase("OperatoriCommerciali"))
{
	url+= "org.aspcfs.modules.operatori_commerciali.actions."+dett+"Campioni" ;
}
if (dett.equalsIgnoreCase("canili"))
{
	url+= "org.aspcfs.modules.canili.actions."+dett+"Campioni" ;
}
	String param1 = "idCampione="+dettCampione.getId()+"&moduloMacroarea="+url;
%>

<% String container = "valutazionemacroarea";
if (layout!=null && layout.equals("style"))
	container = "";
%>

<dhv:container name="<%=container %>" selected="val" object="SchedaValutazione" param='<%= param1%>'>

<h2>Scheda Valutazione Comportamentale Benessere Animale</h2>

<form method="post" action="IuvValutazioneComportamentale.do?command=Modify">
<input type = "hidden" name = "layout" value = "<%=layout%>"/>
<input type = "hidden" name = "idCampione" value = "<%=dettCampione.getId()%>"/>
<!-- PULSANTI DI MODIFICA FINCHE IL CAMPIONE NON è STATO CHIUSO DOPO LA SCHEDA NON SARà MODIFICABILE -->
<%
if (dettCampione.getClosed()==null)
{
	%>
	<input type = "submit" value = "Modifica Scheda"/>
	<%
	
}
else
{
%>
<font color = "red">Scheda non modificabile in quanto il campione e' stato chiuso.</font> 
<%	
}
%>


<br><br>
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
      <%
      String valore="";
      %>
  <tr>
    <th colspan="2">
      <strong>PARAMETRI STRUTTURALI</strong>
    </th>
  </tr>
  <% if(Boolean.parseBoolean(SchedaValutazione.getAdeguatezza_box())){ valore="Adeguato";   }else{	valore="Non Adeguato";  } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      adeguatezza box
    </td>
    <td>
      &nbsp;&nbsp; <%=valore %> 
    </td>
  </tr>
  <% if(Boolean.parseBoolean(SchedaValutazione.getAdeguatezza_sgambamento())){ valore="Adeguato";   }else{	valore="Non Adeguato";  } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      adeguatezza sgambamento
    </td>
    <td>
      &nbsp;&nbsp; <%=valore %>
    </td>
  </tr>
      
  <tr>
    <th colspan="2">
      <strong>PARAMETRI RILEVATI SUL GRUPPO</strong>
    </th>
  </tr>
  <% if(Boolean.parseBoolean(SchedaValutazione.getSegni_di_malessere())){ valore="Presenza";   }else{	valore="Assenza";  } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      segni di malessere<br>Polipnea - Tremore - Ammucchiamento<br>Abbaio continuo - dolore - diarrea
    </td>
    <td>
      &nbsp;&nbsp; <%=valore %>
    </td>
  </tr>
  <% if(Boolean.parseBoolean(SchedaValutazione.getAtti_ripetitivi())){ valore="Presenza";   }else{	valore="Assenza";  } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      atti ripetitivi e/o compulsivi
    </td>
    <td>
      &nbsp;&nbsp; <%=valore %>
    </td>
  </tr>
 
  <tr>
    <th colspan="2">
      <strong>PARAMETRI RILEVATI SUL CAMPIONE</strong>
    </th>
  </tr>
  <% if(Boolean.parseBoolean(SchedaValutazione.getAtti_ripetitivi_campione())){ valore="Presenza";   }else{	valore="Assenza";  } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      atti ripetitivi e/o compulsivi
    </td>
    <td>
      &nbsp;&nbsp; <%=valore %>
    </td>
  </tr>
  <% if(Boolean.parseBoolean(SchedaValutazione.getAggressivita())){ valore="Presenza";   }else{	valore="Assenza";  } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      aggressivita'
    </td>
    <td>
      &nbsp;&nbsp; <%=valore %>
    </td>
  </tr> 
  <% valore=SchedaValutazione.getPaura(); %>
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      test paura
    </td>
    <td>
      &nbsp;&nbsp; <%=toHtml(valore) %>
    </td>
  </tr>  
  <% valore=SchedaValutazione.getSocievolezza(); %> 
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      test socievolezza
    </td>
    <td>
      &nbsp;&nbsp; <%=toHtml(valore) %>
    </td>
  </tr>  
  <% if(Boolean.parseBoolean(SchedaValutazione.getGuinzaglio())){ valore="Adeguato";   }else{	valore="Non Adeguato";  } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      test gestione guinzaglio
    </td>
    <td>
      &nbsp;&nbsp; <%=valore %>
   </td>
  </tr>  
  <% if(Boolean.parseBoolean(SchedaValutazione.getManipolazioni())){ valore="Adeguato";   }else{	valore="Non Adeguato";  } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      test tolleranza manipolazioni
    </td>
    <td>
      &nbsp;&nbsp; <%=valore %>
    </td>
  </tr>  
  <% if(Boolean.parseBoolean(SchedaValutazione.getPrelievo_ematico())){ valore="Adeguato";   }else{	valore="Non Adeguato";  } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      test tolleranza prelievo ematico
    </td>
    <td>
      &nbsp;&nbsp; <%=valore %>
    </td>
  </tr>  
  <% valore=SchedaValutazione.getBcs().replace("_"," "); %> 
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      bcs
    </td>
    <td>
      &nbsp;&nbsp; <%=valore %>
    </td>
  </tr>
  <% if(Boolean.parseBoolean(SchedaValutazione.getPulizia())){ valore="Adeguato";   }else{	valore="Non Adeguato";  } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      pulizia e sanita'<br>cute - mantello - unghie
    </td>
    <td>
      &nbsp;&nbsp; <%=valore %>
    </td>
  </tr>   
  <% if(Boolean.parseBoolean(SchedaValutazione.getZoppia())){ valore="Presente";   }else{	valore="Assente";  } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      zoppia
    </td>
    <td>
      &nbsp;&nbsp; <%=valore %>
    </td>
  </tr>    
  <% if(Boolean.parseBoolean(SchedaValutazione.getTosse())){ valore="Presente";   }else{	valore="Assente";  } %>    
  <tr class="containerBody">
    <td nowrap class="formLabel" >
      tosse
    </td>
    <td>
      &nbsp;&nbsp; <%=valore %>
    </td>
  </tr>    
      
      
      
      
      
      
      
      
      </table>
<!-- PULSANTI DI MODIFICA FINCHE IL CAMPIONE NON è STATO CHIUSO DOPO LA SCHEDA NON SARà MODIFICABILE -->
<%
if (dettCampione.getClosed()==null)
{
	%>
	<input type = "submit" value = "Modifica Scheda"/>
	<%
}
	else
	{
	%>
	<font color = "red">Scheda non modificabile in quanto il campione e' stato chiuso.</font> 
	<%	
	}
	%>
</form>
</dhv:container>