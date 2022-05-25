<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.dpat2019.base.Indicatore"%>
<%@page import="org.aspcfs.modules.dpat2019.base.Sezione"%>
<%@page import="org.aspcfs.modules.dpat2019.base.PianoAttivita"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatIndicatoreNewBeanAbstract"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@page import="org.aspcfs.modules.dpat2019.base.PianoMonitoraggio"%> 
<% 
	
	Indicatore IndicatoreNewDPat = request.getAttribute("IndicatoreNewDPat") != null ? (Indicatore)request.getAttribute("IndicatoreNewDPat") : null;
	 
	PianoAttivita PianoAttivitaNewDPat = request.getAttribute("PianoAttivitaNewDPat") != null ? (PianoAttivita)request.getAttribute("PianoAttivitaNewDPat") : null;
	Sezione SezioneNewDpat = request.getAttribute("SezioneNewDpat") != null ? (Sezione)request.getAttribute("SezioneNewDpat") : null;
	 
	int anno = request.getAttribute("anno") != null ? Integer.parseInt((String)request.getAttribute("anno")) : -1;
	System.out.println("///////"+request.getAttribute("anno"));
	Boolean congelato = request.getAttribute("congelato") != null && Boolean.parseBoolean((String)request.getAttribute("congelato"));
 %>
 

<jsp:useBean id="lookup_asl" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
	var cal19 = new CalendarPopup();
	cal19.showYearNavigation();
	cal19.showYearNavigationInput();
	cal19.showNavigationDropdowns();
</SCRIPT>
 <script>
 
 
 
 function checkForm()
 {
	 flag = true ;	
	 msg = '' ;
	
	 if (document.addPiano.descrizione.value == '')
	 {
		flag = false ;
		msg += " - Attenzione ! Inserire la descrizione del piano che si intende inserire \n ";
	 }

	 if (document.addPiano.sezione.value == '-1')
	 {
		flag = false ;
		msg += " - Attenzione ! Inserire la sezione del piano che si intende inserire \n ";
	 }
	 if (flag ==false)
		 alert(msg) ;
	 return flag ;
	 
}

 function abilitaAsl()
 {
	 if (document.addPiano.sezione.value=='10')
	 {
		document.getElementById("asl").style.display="";
	 }
	 else
	 {
		 document.getElementById("asl").style.display="none";
	}
 }

 function chiudiPopUp(flagInserimento)
 {
	  if(flagInserimento != null && flagInserimento != 'null')
	  {
		  //window.opener.document.forms[0].action = ;
		  //window.opener.document.forms[0].submit();
		  window.opener.location.href='dpat2019.do?command=SearchPianiMonitoraggioNewDpat&anno=<%=anno%>&congelato=<%=congelato%>';
		  window.close();
	  }

	}
 </script>
 <body  onblur="if(focus_==true){window.focus();}"  onload = "chiudiPopUp('<%=request.getAttribute("inserito")%>')" onmouseout="focus_=true;" onmouseover="focus_=false;" >
 <form name="addPiano" action="dpat2019.do?command=DisattivaEffettivo" method="post">
<%-- Trails --%>
<input type ="hidden" name = "anno" value = "<%=anno%>">
<input type="hidden" name="congelato" value="<%=congelato %>" />
<%-- End Trails --%>
<input type="submit" value="<%=request.getAttribute("Cessazione") != null ? "Disabilita" : "Aggiorna" %>"  name="Save" onClick="return checkForm(this.form)">
<input type="button" value="Annulla"  onClick="window.close()">
<dhv:formMessage />


<%
	if (request.getAttribute("inserito")!=null)
{
%>
<font color = "red">modifica effettuata in maniera corretta.</font>
<a href = "#" >Torna Indietro</a>

<%
	}
%>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">DISABILITA PIANO</dhv:label></strong>
    </th>
	</tr>
	
	
	<%
	 if(request.getAttribute("inserito")==null)
	 {
	%>
	<tr>
      <td nowrap class="formLabel">
       SEZIONE
      </td>
      <td>
     
    	<%=SezioneNewDpat.getDescrizione() %>
    	<input type = "hidden" name = "root" value="<%=SezioneNewDpat.getOid() %>">
       </td>
    </tr>
    
    
  
	
	
	
	
	<tr>
      <td nowrap class="formLabel">
       <img title="Si Tratta della descrizione breve presente dopo la riga della sezione nel foglio allegato 5-foglio delle attivita" class="masterTooltip" src="images/questionmark.png" width="20"/> 
       ALIAS [ES. A1 PER ATTIVITA DELLA SEZIONE ,<br> A1_A PER INDICATORE DELL'ATTIVITA]
      </td>
      <td>
   	<%
	 
   	%>
    	<%= PianoAttivitaNewDPat != null ? PianoAttivitaNewDPat.getAliasPiano() : IndicatoreNewDPat.getAliasIndicatore() %>
    	
       </td>
    </tr>
   
    

      <%
    String tipologia = "" ;
    if (PianoAttivitaNewDPat != null && PianoAttivitaNewDPat.getTipoAttivita()!=null)
    	tipologia = PianoAttivitaNewDPat.getTipoAttivita() ;
    else
    	tipologia = IndicatoreNewDPat.getTipoAttivita();
    
    %>
    <tr>
      <td nowrap class="formLabel">
      TIPO
       </td>
       
     
      <td>
        <%
      if(tipologia!=null)
      {
    	  %>
    	  
    	  <%=tipologia.toUpperCase() %>
    	  <%
      }
      %>
   
       </td>
    </tr>
    
   <tr>
      <td nowrap class="formLabel">
      Codice Esame
       </td>
      <td>
    	<%=PianoAttivitaNewDPat != null ? toHtml(PianoAttivitaNewDPat.getCodiceEsame()) : toHtml(IndicatoreNewDPat.getCodiceEsame()) %>
   
       </td>
    </tr>
  
  
    
	<tr>
      <td nowrap class="formLabel">
      Descrizione
      </td>
      <td>
			<div>
				<%=PianoAttivitaNewDPat != null ? PianoAttivitaNewDPat.getDescrizione() : IndicatoreNewDPat.getDescrizione() %>
			</div>
       </td>
    </tr>
     <%
    }
    %>
 
       </table>
     
<br />


<br>

<%
 	if(request.getAttribute("inserito")==null)
 	{
%>
<input type="hidden" name="cessazione" value="<%=request.getAttribute("Cessazione") %>">

<input type = "hidden" name = "id" value = "<%=PianoAttivitaNewDPat  != null ? PianoAttivitaNewDPat.getOid() : IndicatoreNewDPat.getOid() %>">
<input type = "hidden" name = "tipoClasse" value = "<%=PianoAttivitaNewDPat  != null ? "dpat_attivita" : "dpat_indicatore" %>">
<input type = "hidden" name = "disabilita" value = "">
<input type = "hidden" name="cessato" value ="<%=request.getAttribute("Cessazione") %>">


<input type="submit" value="<%=request.getAttribute("Cessazione") != null ? "Disabilita" : "Aggiorna" %>" name="Save" onClick="return checkForm(this.form)">
<input type="button" value="Annulla"  onClick="window.close()">

<%
 	}
%>
</form>

</body>