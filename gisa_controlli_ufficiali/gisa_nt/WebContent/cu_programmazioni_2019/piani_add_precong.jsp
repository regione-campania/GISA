<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@page import="org.aspcfs.modules.dpat2019.base.PianoMonitoraggio"%>
 
<!-- nuova gestione dpat -->
<jsp:useBean id="PianoAttivitaNewDPat" class = "org.aspcfs.modules.dpat2019.base.DpatPianoAttivitaNewBean" scope = "request"/>
<jsp:useBean id="IndicatoreNewDPat" class = "org.aspcfs.modules.dpat2019.base.DpatIndicatoreNewBean" scope = "request"/>
 

<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>
<link rel="stylesheet" type="text/css" href="css/capitalize.css"></link>

<script src='javascript/modalWindow.js' type="text/javascript" ></script>


<jsp:useBean id="lookup_sezioni_piani" class = "org.aspcfs.utils.web.LookupList" scope = "request">
</jsp:useBean>

<jsp:useBean id="lookup_piani" class = "org.aspcfs.utils.web.LookupList" scope = "request">
</jsp:useBean>
<jsp:useBean id="lookup_asl" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>


 <script>
 
 function verificaEsistenzaCodiceCallback(ret)
 {
	 if (ret[0]=="true")
		 {
	 	flag=false;
	 	msg+=" - Attenzione Il codice Univoco Inserito è Occupato. ";
	 	if(ret[1]!="ko")
	 		{
	 		
	 		msg+="Il prossimo codice libero è il seguente : "+ret[1];
	 		}
	 	
		 }
 }
 
 function verificaEsistenzaCodice(codiceAttivita, anno, idpianoatt, tipologia)
 {
	 if(codiceAttivita!='')
	 {
		 if(tipologia == 'piano_attivita')
			 PopolaCombo.verificaEsistenzaCodiceAttivitaNEW(codiceAttivita, anno, idpianoatt,{callback:verificaEsistenzaCodiceCallback,async:false})
		else
			 PopolaCombo.verificaEsistenzaCodiceIndicatoreNEW(codiceAttivita,  anno, idpianoatt, {callback:verificaEsistenzaCodiceCallback,async:false})
	 }
		 
 }
 
 

 
 var flag = true ;	
 var msg = '' ;
 function checkForm(form,tipologia)
 {
	 flag = true ;
	 msg = '' ;
	
	 verificaEsistenzaCodice(document.getElementById("cup").value,document.getElementById("anno").value,document.getElementById("idPianoRiferimento").value,tipologia);
	 
	 
	 if (document.addPiano.descrizione.value == '')
	 {
		flag = false ;
		msg += " - Attenzione ! Inserire la descrizione del piano che si intende inserire \n ";
	 }

	/* if (document.addPiano.root.value == '-1')
	 {
		flag = false ;
		msg += " - Attenzione ! Inserire la sezione del piano che si intende inserire \n ";
	 }*/
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
		  //window.opener.document.forms[0].action = 'dpat2019.do?command=SearchPianiMonitoraggioNewDpat';
		  //window.opener.loadModalWindowCustom("Ricaricamento in Corso");
		  //window.opener.document.forms[0].submit();
		  
		  window.opener.location.href='dpat2019.do?command=SearchPianiMonitoraggioNewDpat';
		  window.close();
	  }

	}
 </script>
 <body  onblur="if(focus_==true){window.focus();}"  onload = "chiudiPopUp('<%=request.getAttribute("inserito")%>')" onmouseout="focus_=true;" onmouseover="focus_=false;" >
 <form name="addPiano" action="dpat2019.do?command=InsertEffettivo" method="post" onsubmit="return checkForm(this.form,'<%=PianoAttivitaNewDPat != null ? "piano_attivita" : "indicatore"%>')">
   
 <input type = "hidden" id="anno" name = "anno" value="<%=PianoAttivitaNewDPat.getOid() != null ? PianoAttivitaNewDPat.getAnno(): IndicatoreNewDPat.getAnno() %>">
<%-- Trails --%>

<%-- End Trails --%>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save"  >
<input type="button" value="Annulla"  onClick="window.close()">
<dhv:formMessage />


<%
	if (request.getAttribute("inserito")!=null)
{
%>
<font color = "red">Piano Inserito in maniera corretta.</font>
<a href = "#" >Torna Indietro</a>

<%
	}
	else
	{
%>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name=""><%=(PianoAttivitaNewDPat.getDescrizione()!=null && !PianoAttivitaNewDPat.getTipoInserimento().equals("firstchild") ) ? "NUOVO PIANO/ATTIVITA" : "NUOVO INDICATORE" %></dhv:label></strong>
    </th>
	</tr>
	
	
	 
	
	 
    
      <tr>
      <td nowrap class="formLabel">
       Tipo Inserimento
      </td>
      <td>
      
         <%=(PianoAttivitaNewDPat.getDescrizione()!=null)? PianoAttivitaNewDPat.getTipoInserimento() : IndicatoreNewDPat.getTipoInserimento() %>
     	<input type ="hidden" name="tipoInserimento" value="<%=(PianoAttivitaNewDPat.getTipoInserimento()!=null)? PianoAttivitaNewDPat.getTipoInserimento() : IndicatoreNewDPat.getTipoInserimento()  %>">
       </td>
    </tr>
    
    
    <tr>
      <td nowrap class="formLabel">
       RIFERIMENTO <%=(PianoAttivitaNewDPat.getDescrizione()!=null) ?  "AL PIANO/ATTIVITA" : "ALL'INDICATORE" %>
      </td>
      <td>
            <%=(PianoAttivitaNewDPat.getDescrizione()!=null)? PianoAttivitaNewDPat.getDescrizione() : IndicatoreNewDPat.getDescrizione() %>
      
     	 <input type="hidden" name="tipoInserimento" value="<%=(PianoAttivitaNewDPat.getTipoInserimento()!=null) ? PianoAttivitaNewDPat.getTipoInserimento():IndicatoreNewDPat.getTipoInserimento()  %>"/>
     	<input type="hidden" id="idPianoRiferimento" name="idPianoRiferimento" value="<%=(PianoAttivitaNewDPat.getOid() != null)?PianoAttivitaNewDPat.getOid() : IndicatoreNewDPat.getOid() %>"/>
     	
       </td>
    </tr>
    
    
   <tr>
      <td nowrap class="formLabel">
       
       CODICE UNIVOCO
      </td>
      <td>
     <input type = "text" name="cup" required id="cup" >
   
       </td>
    </tr>
	
	
	<tr>
      <td nowrap class="formLabel">
       <img title="Si Tratta della descrizione breve presente dopo la riga della sezione nel foglio allegato 5-foglio delle attivita" class="masterTooltip" src="images/questionmark.png" width="20"/> 
       
        ALIAS [ES. A1 PER ATTIVITA DELLA SEZIONE , A1_A PER INDICATORE DELL'ATTIVITA]
      </td>
      <td>
     <input type = "text" name="alias" required >
   
       </td>
    </tr>
    
    
    <%
    String tipologia = "" ;
    if (PianoAttivitaNewDPat.getTipoAttivita()!=null)
    	tipologia = PianoAttivitaNewDPat.getTipoAttivita() ;
    else
    	tipologia = IndicatoreNewDPat.getTipoAttivita();
    
    if (tipologia == null)
    	tipologia = "" ;
    
    %>
    <tr>
      <td nowrap class="formLabel">
      Tipo
       </td>
      <td>
      
      <%
      if(tipologia!=null && !"".equalsIgnoreCase(tipologia) && (PianoAttivitaNewDPat.getOid() == null || PianoAttivitaNewDPat.getTipoInserimento().equalsIgnoreCase("firstchild")) )
      {
    	  %>
    	  
    	   <%--=tipologia.toUpperCase() --%><%="INDICATORE ("+tipologia.toUpperCase()+")" %>
    	  <input type = "hidden" name = "tipoAttivita" value = "<%=tipologia.toUpperCase() %>">
    	 
    	  <%
    	  
      }
      else
      {
      %>
      <select name="tipoAttivita" required>
      <option <%=tipologia.equals("") ? "selected" : "" %> value="">Seleziona Tipo </option>
      <option <%=tipologia.equalsIgnoreCase("piano") ? "selected" : "" %> value="PIANO">PIANO</option>
      <option <%=tipologia.equalsIgnoreCase("ATTIVITA-AUDIT") ? "selected" : "" %> value="ATTIVITA-AUDIT">ATTIVITA-AUDIT</option>
       <option <%=tipologia.equalsIgnoreCase("ATTIVITA-SORVEGLIANZA") ? "selected" : "" %> value="ATTIVITA-SORVEGLIANZA">ATTIVITA-SORVEGLIANZA</option>
       <option <%=tipologia.equalsIgnoreCase("ATTIVITA-ISPEZIONE") ? "selected" : "" %> value="ATTIVITA-ISPEZIONE">ATTIVITA-ISPEZIONE</option>
      </select>
   <%} %>
       </td>
    </tr>
      <tr>
      <td nowrap class="formLabel">
      Codice Esame
       </td>
      <td>
     <input type = "text" name="codice_esame" value="<%= PianoAttivitaNewDPat.getCodiceEsame()!= null ? toHtml(PianoAttivitaNewDPat.getCodiceEsame()) : toHtml(IndicatoreNewDPat.getCodiceEsame())  %>">
   
       </td>
    </tr>
    
   
	<tr>
      <td nowrap class="formLabel">
      Descrizione
      </td>
      <td>
     <textarea rows="6" cols="75" name="descrizione" required></textarea>
       </td>
    </tr>
    
  
    
  <input type ="hidden" name="tipoPianoAttInd" value = "<%=(PianoAttivitaNewDPat.getOid() != null) ? "dpat_attivita" : "dpat_indicatore" %>">
 
       </table>
       <%} %>
     
<br />


<br>
<input type="submit" value="<dhv:label name="button.insert">Insert</dhv:label>" name="Save"  >
<input type="button" value="Annulla"  onClick="window.close()">
</form>

</body>