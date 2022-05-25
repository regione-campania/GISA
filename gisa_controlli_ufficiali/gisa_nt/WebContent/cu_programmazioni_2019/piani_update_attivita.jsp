<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.dpat2019.base.Sezione"%>
<%@page import="org.aspcfs.modules.dpat2019.base.PianoAttivita"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatWrapperSezioniNewBeanAbstract"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatSezioneNewBeanInterface"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatSezione"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>

<%@page import="org.aspcfs.modules.dpat2019.base.PianoMonitoraggio"%>

<%-- <jsp:useBean id="PianoAttivitaNewDPat" class = "org.aspcfs.modules.dpatnew.base.DpatPianoAttivitaNewBean" scope = "request"/>
<jsp:useBean id="SezioneNewDPat" class = "org.aspcfs.modules.dpatnew.base.DpatSezioneNewBean" scope = "request"/>
<jsp:useBean id="ListaSezioniNewDPat" class = "org.aspcfs.modules.dpatnew.base.DpatWrapperSezioniBean" scope = "request"/>
 --%>

<%
boolean congelato = Boolean.parseBoolean((String)request.getAttribute("congelato"));
PianoAttivita PianoAttivitaNewDPat = request.getAttribute("PianoAttivitaNewDPat") != null ? (PianoAttivita) request.getAttribute("PianoAttivitaNewDPat") : null;
System.out.println("ID  asdasd"+PianoAttivitaNewDPat.getOid()); 
Sezione SezioneNewDPat = request.getAttribute("SezioneNewDPat") != null ? (Sezione) request.getAttribute("SezioneNewDPat") : null;
DpatWrapperSezioniNewBeanAbstract ListaSezioniNewDPat = request.getAttribute("ListaSezioniNewDPat") != null ? (DpatWrapperSezioniNewBeanAbstract)request.getAttribute("ListaSezioniNewDPat") : null;
int anno = Integer.parseInt((String)request.getAttribute("anno"));


%>


<jsp:useBean id="lookup_sezioni_piani" class = "org.aspcfs.utils.web.LookupList" scope = "request">
</jsp:useBean>

<jsp:useBean id="lookup_piani" class = "org.aspcfs.utils.web.LookupList" scope = "request">
</jsp:useBean>
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
 
<script type="text/javascript" src="javascript/jquery.miny_1.7.2.js"></script>

<script src="javascript/jquery-ui.js" type="text/javascript" ></script>
 

 
 <script>
 
 
 
 function verificaEsistenzaCodiceCallback(ret)
 {	 
	 
	 //alert("ret[0]:"+ret[0]+" - ret[1]:"+ret[1]);
	 
	 if (ret[0]=="true")
	{
	 	flag=false;
	 	msg+=" - Attenzione Il codice Univoco Inserito è Occupato. ";
	 	if(ret[1]!="ko")
	 	{
	 		
	 		msg+="Il prossimo codice libero è il seguente : "+ret[1];
	 		$("#cup").val(ret[1]);
	 	}
	 	 
	 }
	 	 
 }
 
 
 
 
 
 function verificaEsistenzaCodice(codiceAttivita)
 {
	 //alert("codiceAttivita: "+codiceAttivita+" -cupIniziale: "+cupIniziale);
	 
	 codiceAttivita = codiceAttivita.trim();	 
	 cupIniziale = document.getElementById("cupIniziale").value.trim();
	 if(cupIniziale!=codiceAttivita && codiceAttivita!=''){
	 	//PopolaCombo.verificaEsistenzaCodiceAttivitaNEW(codiceAttivita,  {callback:verificaEsistenzaCodiceCallback,async:false})
	 	
	 	var anno = document.getElementById("anno").value;
	 	var idpianoatt = document.getElementById("idPianoRiferimento").value;
	 	
		PopolaCombo.verificaEsistenzaCodiceAttivitaNEW(codiceAttivita, anno, idpianoatt, {callback:verificaEsistenzaCodiceCallback,async:false})
	 }
	 
 }
 
 
 /*
 function setIdPianoAttivita()
 {
	 var idpiano_ =$('#piano option:selected').attr("chiave"); 
    //$("#idPiano_").val(idpiano_);
    
   
 }
 */
 
 
 function setPianiAttivitaCombo(valori)
 {

	 if(valori != undefined && valori.length > 0)
	 {
		
		 $("select#piano option").remove(); 
		 for ( i = 0 ; i <valori.length; i++)
		 {
			 
				 $("#piano").append('<option value="'+valori[i].oid+'" chiave="'+valori[i].oid+'" >'+valori[i].descrizione+'</option>');
				 if(i == 0)
				 {
					 $("#piano option:first-of-type").prop("selected",true);
				 }
		 }
		 
		 $("#idPianoAttivita").val(valori[0].oid);
	 
	 }
	 
	 abilitaAggiornamento($("#idPianoAttivita").val(), $("#idPianoRiferimento").val());
 }
 
 
 
 
 
 
 function setListaPianiAttivitaFromSezione(idSezione,anno,congelato)
{
	 //alert("idSezione: "+idSezione);
	 
	 console.log("set lista piani attivita from sezione "+idSezione);
	 PopolaCombo.getListaPianiAttivitaNewDpat(idSezione,anno,congelato,{callback:setPianiAttivitaCombo,async:false});
	 //setListaIndicatoriFromPianoAttivita(document.getElementById("idPianoAttivita").value,anno);
	// setIdPianoAttivita();
} 
 

 
  
 /*
 function setIndicatoriCombo(valori)
 {
	
	 $("select#indicatoreRiferimento option").remove(); 
	 for ( i = 0 ; i <valori.length; i++)
		 {
		 if (i==0)
			 $("#indicatoreRiferimento").append('<option value="'+valori[i].oid+'" selected="selected">'+valori[i].aliasIndicatore+'</option>');
		 else
			 $("#indicatoreRiferimento").append('<option value="'+valori[i].oid+'">'+valori[i].aliasIndicatore+'</option>');
		 }
 }
 
 function setListaIndicatoriFromPianoAttivita(idPianoAttivita,anno)
 {
	 console.log("set lista indicatori from piano attivita "+idPianoAttivita);
	 PopolaCombo.getListaIndicatoriNewDpat(idPianoAttivita,anno,{callback:setIndicatoriCombo,async:false});
 	 
 } */
 
 

 function reCheck()
 {
	 if (!document.getElementById("posizioneInizio").checked)
	 {
	 document.getElementById("posizioneFine").checked = false;
	 document.getElementById("posizioneInizio").checked = true;
	 
	 }
 }
 
 function sceltaOperazione(anno,scelta,sezione,piano,attivita,idsez,idpiano,idatt,congelato)
 {
	 //alert(scelta);
	 
	 $("#operazione").val(scelta);
	 $(".rbOperazione").prop('disabled', true);	 
	 
	 if (scelta=='3')
	 {
		 $(".enabledCombo").prop('disabled', false);
		 		 
		 $(".btnAggiorna").prop('disabled', true);
		 $(".btnAggiorna").css({'background-color':'lightgrey'});
		 
		 setListaPianiAttivitaFromSezione(idsez,anno,congelato);
		 //setListaAttivitaFromPiano(document.getElementById("piano").value,anno);
		
		document.getElementById("posizioneIndicatore").style.display="";

<%
      	String codiceEsame = PianoAttivitaNewDPat.getCodiceEsame()!=null && PianoAttivitaNewDPat.getCodiceEsame().equalsIgnoreCase("null") ? PianoAttivitaNewDPat.getCodiceEsame() : "";
%>
		 $("#codice_esame").val('<%=codiceEsame%>');
		 $("#codice_esame").prop('readonly', true);
		 $("#cup").val('<%=toHtml((PianoAttivitaNewDPat.getCodiceAliasAttivita()+"")  ).replaceAll("'"," ").replaceAll("\""," ") %>');
		 $("#cup").prop('readonly', true);
		 $("#alias").val('<%=(""+PianoAttivitaNewDPat.getAliasPiano()).replaceAll("'"," ").replaceAll("\""," ") %>');
		 $("#alias").prop('readonly', true);
		 $("#descrizione").val('<%=(PianoAttivitaNewDPat.getDescrizione()+"").replaceAll("'"," ").replaceAll("\""," ") %>');		 
		 $("#descrizione").prop('readonly', true);
		 
		 
		 
		 
		 $("#codice_esame").css({"background-color" : "lightgray"});
		 $("#cup").css({"background-color" : "lightgray"});
		 $("#alias").css({"background-color" : "lightgray"});
		 $("#descrizione").css({"background-color" : "lightgray"});
	 }
	 else
		 {
		 $(".enabledCombo").prop('disabled', true);
		 
		 $("select#sezione").val(idsez); 
		 $("select#piano option").remove(); 
		 $("select#root option").remove(); 
		 //$("#root_").val(idatt); 
		 //$("#idPiano_").val(""); 
		 $("#piano").append('<option value="'+idpiano+'">'+piano+'</option>');
		 $("#root").append('<option value="'+idatt+'">'+attivita+'</option>');
		 document.getElementById("posizioneIndicatore").style.display="none";
		 $("#codice_esame").prop('readonly', false);
		 $("#cup").prop('readonly', false);
		 $("#alias").prop('readonly', false);
		 $("#descrizione").prop('readonly', false);
		 
		 $("#codice_esame").css({"background-color" : "white"});
		 $("#cup").css({"background-color" : "white"});
		 $("#alias").css({"background-color" : "white"});
		 $("#descrizione").css({"background-color" : "white"});
		 
		 
		 }
 }
 
 flag = true ;	
 msg = '' ;

 
 function checkForm()
 {
	 flag = true ;	
	 msg = '' ;
	
	/* if(/^[0-9]{5}$/.test(document.getElementById("cup").value) == false)
	 {
		alert("Formato codice univoco non valido. Formato di esempio: 00010");
		flag = false;
		return flag;
	 }*/
	 
	 verificaEsistenzaCodice(document.getElementById("cup").value); 
	 
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
		  //window.opener.document.forms[0].action = 
		  //window.opener.document.forms[0].onsubmit = '';
		  //window.opener.document.forms[0].submit();
		  window.opener.location.href = 'dpat2019.do?command=SearchPianiMonitoraggioNewDpat&anno=<%=anno%>&congelato=<%=congelato%>';
		  window.close();
	  }

}
 
 
 function abilitaAggiornamento(pianoSelezionato, pianoCorrente){	 
	 if (pianoSelezionato == pianoCorrente){
		 $(".btnAggiorna").prop('disabled', true);
		 $(".btnAggiorna").css({'background-color':'lightgrey'});
	 } else {
		 $(".btnAggiorna").prop('disabled', false);
		 $(".btnAggiorna").css({'background-color':'#4477AA'});
	 }
	 
 }
 
 </script>
 
 
 <body  onblur="if(focus_==true){window.focus();}"  onload = "chiudiPopUp('<%=request.getAttribute("inserito")%>')" onmouseout="focus_=true;" onmouseover="focus_=false;" >
 <form name="addPiano" action="dpat2019.do?command=ModificaAttivitaEffettivo" method="post" onsubmit="return checkForm(this)">
<%-- Trails --%>
<input type ="hidden" name = "anno" value = "<%=ListaSezioniNewDPat.getAnno() %>">
<%-- End Trails --%>
<input type="submit" value="Aggiorna" name="Save" class="btnAggiorna">
<input type="button" value="Annulla"  onClick="window.close()">
<dhv:formMessage />


<%
	if (request.getAttribute("inserito")!=null)
{
%>
<font color = "red">Indicatore modificato con successo</font>
<a href = "#" >Torna Indietro</a>

<%
}
%>

<table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">	
      <strong><dhv:label name="">VARIAZIONE PIANO-ATTIVITA</dhv:label></strong>
    </th>
	</tr>
	
	<tr>
      <td nowrap class="formLabel">
      OPERAZIONE (*)
      </td>
      <td>
     MODIFICA PIANO-ATTIVITA &nbsp;<input type="radio" name="operazione" class="rbOperazione" checked="checked" value="1" onclick="sceltaOperazione(<%=ListaSezioniNewDPat.getAnno() %>,'1','<%=(""+SezioneNewDPat.getDescrizione() ).replaceAll("'"," ").replaceAll("\""," ") %>','<%=PianoAttivitaNewDPat.getDescrizione().replaceAll("'","").replaceAll("\""," ")%>','<%=PianoAttivitaNewDPat.getDescrizione() != null ? PianoAttivitaNewDPat.getDescrizione().replaceAll("'", " ").replaceAll("\""," ") : ""%>',<%=SezioneNewDPat.getOid()%>,<%=PianoAttivitaNewDPat.getOid()%>,<%=PianoAttivitaNewDPat.getOid()%>,<%=congelato%>)">  |  
     <%--
     SOSTITUISCI PIANO-ATTIVITA CON NUOVO (**) <input type="radio" name="operazione" value="2" onclick="sceltaOperazione(<%=ListaSezioniNewDPat.getAnno() %>,'2','<%=SezioneNewDPat.getDescrizione()%>','<%=PianoAttivitaNewDPat.getDescrizione()%>','<%=PianoAttivitaNewDPat.getDescrizione() != null ? PianoAttivitaNewDPat.getDescrizione().replaceAll("'", " ") : ""%>',<%=SezioneNewDPat.getOid()%>,<%=PianoAttivitaNewDPat.getOid()%>,<%=PianoAttivitaNewDPat.getOid()%>)">
      --%>
     SPOSTA PIANO-ATTIVITA &nbsp;<input type="radio" name="operazione" class="rbOperazione" value="3" onclick="sceltaOperazione(<%=ListaSezioniNewDPat.getAnno() %>,'3','<%=(""+SezioneNewDPat.getDescrizione()).replaceAll("'"," ").replaceAll("\""," ")%>','<%=(""+PianoAttivitaNewDPat.getDescrizione()).replaceAll("'"," ").replaceAll("\""," ")%>','<%=PianoAttivitaNewDPat.getDescrizione() != null ? PianoAttivitaNewDPat.getDescrizione().replaceAll("'", " ").replaceAll("\""," ") : ""%>',<%=SezioneNewDPat.getOid()%>,<%=PianoAttivitaNewDPat.getOid()%>,<%=PianoAttivitaNewDPat.getOid()%>,<%=congelato%>)">
     
     <input type="hidden" value="1" name="operazione" id="operazione">
       </td>
    </tr>
	
	<tr>
      <td nowrap class="formLabel">
      CODICE UNIVOCO
      </td>
      <td>
     <input type = "text" name = "cup" id = "cup" value="<%=toHtml((PianoAttivitaNewDPat.getCodiceAliasAttivita()+"")  ).replaceAll("'"," ")%>" pattern="\d*" title="deve contenere un numero" required>
       </td>
    </tr>
   
    
    <tr>
      <td nowrap class="formLabel">
       <img title="Si Tratta della descrizione breve presente dopo la riga della sezione nel foglio allegato 5-foglio delle attivita" class="masterTooltip" src="images/questionmark.png" width="20"/> 
       ALIAS [ES. A1 PER ATTIVITA DELLA SEZIONE , A1_A PER INDICATORE DELL'ATTIVITA]
      </td>
      <td>
        <input type = "text" name="alias" id = "alias"    value="<%=(""+PianoAttivitaNewDPat.getAliasPiano()).replaceAll("'"," ") %>" pattern="[A-Za-z0-9_].{0,}" title=" ALIAS [ES. A1 PER ATTIVITA DELLA SEZIONE , A1_A PER INDICATORE DELL'ATTIVITA]" required>
   
    
       </td>
    </tr>
    <tr>
      <td nowrap class="formLabel">
      Descrizione
      </td>
      <td>
         <textarea rows="6" cols="75" required name="descrizione" id="descrizione" value ="<%=(PianoAttivitaNewDPat.getDescrizione()+"").replaceAll("'"," ").replaceAll("\""," ") %>"><%=(""+PianoAttivitaNewDPat.getDescrizione()).replaceAll("'"," ").replaceAll("\""," ") %></textarea>

       </td>
    </tr>
    
   
	
	<tr>
      <td nowrap class="formLabel">
      SEZIONE
      </td>
      <td>
     
     <select name = "sezione" id = "sezione" disabled="disabled" class="enabledCombo" onchange="setListaPianiAttivitaFromSezione(this.value,<%=ListaSezioniNewDPat.getAnno()%>,<%=congelato%>)">
     <%for (Object seza : ListaSezioniNewDPat.getSezioni()) 
     {
    	 DpatSezioneNewBeanInterface sez = (DpatSezioneNewBeanInterface)seza;
    	 %>
    	 <option value = "<%=sez.getOid()%>" <%=sez.getOid().intValue()==SezioneNewDPat.getOid().intValue() ? "selected" : "" %>><%=(""+sez.getDescrizione()).replaceAll("'"," ").replaceAll("\""," ") %></option>
    	 <%
    	 
     }
     %>
     </select>
       </td>
    </tr>
    
    <tr id = "posizioneIndicatore" style="display: none">
      <td nowrap class="formLabel">
       SCEGLI LA POSIZIONE
      </td>
      <td>
      INSERISCI PRIMA DI&nbsp;&nbsp;<input type = "radio" name = "posizione" value="1" checked="checked" id = "posizioneInizio"> 
     <select  id = "piano" name="pianoAttivitaRiferimento" disabled="disabled" class="enabledCombo" onchange="abilitaAggiornamento(this.value, <%=PianoAttivitaNewDPat.getOid() %>);">
    
     </select>
      <br>
     INSERISCI ALLA FINE <input type = "radio" name = "posizione" id = "posizioneFine" value="2" onclick="$('#pianoAttivitaRiferimento option:last').attr('selected','selected');">
       </td>
    </tr>
    
    
   
    
    
    

      
    
   <tr>
      <td nowrap class="formLabel">
      Codice Esame
       </td>
      <td>
<%
      	codiceEsame = PianoAttivitaNewDPat.getCodiceEsame()!=null && PianoAttivitaNewDPat.getCodiceEsame().equalsIgnoreCase("null") ? PianoAttivitaNewDPat.getCodiceEsame() : "";
%>
     <input type = "text" name="codice_esame" id = "codice_esame" value="<%=codiceEsame%>">
   
       </td>
    </tr>
  
   
   
    
           </table>
     <input type="submit" value="Aggiorna" name="Save" onClick="return checkForm(this.form)"  class="btnAggiorna">
<input type="button" value="Annulla"  onClick="window.close()">
<br />
<br />
<br />
<br />
(*) 	

CON TALI OPERAZIONI SI AVRA IL MANTENIMENTO DELLA STORIA DI TUTTI I CU ASSOCIATI ALLE DIFFERENTI VERSIONI DEL PIANO/ATTIVITA <BR>
<!-- (**) 	

CON TALE OPERAZIONE NON SI AVRA IL MANTENIMENTO DELLA STORIA DI TUTTI I CU DI TUTTI I PIANI CHE CONDIVIDONO ATTUALMENTE LO STESSO CODICE UNIVOCO<BR> -->


<!-- (**) 	

CON TALE OPERAZIONE SI HA LO SPOSTAMENTO DELL'INDICATORE MANTENENDO LA STORIA DI TUTTI I CU DI TUTTI I PIANI CHE CONDIVIDONO ATTUALMENTE LO STESSO CODICE UNIVOCO<BR> -->


<br>
<input type="hidden" name="cessazione" value="<%=request.getAttribute("Cessazione") %>">

<input type = "hidden" name = "id" id="idPianoRiferimento" value = "<%=PianoAttivitaNewDPat.getOid() %>">
<input type = "hidden" name = "tipoClasse" value = "<%="dpat_attivita" %>">
<!-- <input type = "hidden" name = "disabilita" value = ""> -->
<input type = "hidden" name="cessato" value ="no">
<input type = "hidden" name="cupIniziale" id = "cupIniziale" value ="<%=(""+PianoAttivitaNewDPat.getCodiceAliasAttivita()).replaceAll("'"," ")%>">
<input type="hidden" name="congelato" value="<%=congelato %>" />
<input type="hidden" name="anno" id="anno" value="<%=anno %>" />
<!-- <input type = "hidden" id="idPiano_" name="idPiano_" value =""> -->
<%-- <input type = "hidden" id="root_" name="root_" value ="<%=PianoAttivitaNewDPat.getOid()%>"> --%>



</form>

</body>