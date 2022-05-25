<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<%@page import="java.util.*"%>
<%@page import="it.us.web.util.guc.*"%>
<%@page import="it.us.web.bean.guc.*"%>

<script type="text/javascript">

  function trim(str){
    return str.replace(/^\s+|\s+$/g,"");
  } 
  
  function abilitaCampiBDU(form) {
	  if (form.roleIdbdu.value == '24' || form.roleIdbdu.value == '37'){
		  form.id_provincia_iscrizione_albo_vet_privato.disabled = "";
		  form.nr_iscrione_albo_vet_privato.disabled = "";
		  form.id_provincia_iscrizione_albo_vet_privato.value = "-1";
		  form.nr_iscrione_albo_vet_privato.value = "";
	  } else {
		  form.id_provincia_iscrizione_albo_vet_privato.value = "-1";
		  form.nr_iscrione_albo_vet_privato.value = "";
		  form.id_provincia_iscrizione_albo_vet_privato.disabled = "disabled";
		  form.nr_iscrione_albo_vet_privato.disabled = "disabled";
	  }
	  if (form.roleIdbdu.value == '24'){
		  form.numAutorizzazione.disabled="";
		  form.numAutorizzazione.value="";
	  } else {
		  form.numAutorizzazione.value="";
		  form.numAutorizzazione.disabled="disabled";
	  }
  }

  function checkForm(form) {
    formTest = true;
    message = "";

    if (form.roleIdbdu.value == '24'){
		if (form.numAutorizzazione.value==''){
		  message += "- Campo nr. autorizzazione obbligatorio per LP.\r\n";
	      formTest = false;
		}
		if (form.luogo.value=='-1'){
		 	message += "- Campo luogo obbligatorio per LP.\r\n";
	        formTest = false;
		}
		
		if (form.id_provincia_iscrizione_albo_vet_privato.value=='-1'){
			message += "- Campo provincia iscrizione albo obbligatorio per LP.\r\n";
		     formTest = false;
		}
		
		if (form.nr_iscrione_albo_vet_privato.value==''){
			message += "- Campo nr. iscrizione albo veterinari obbligatorio per LP.\r\n";
		     formTest = false;
		}
	}
    
    if (form.roleIdbdu.value == '37'){
    	if (form.id_provincia_iscrizione_albo_vet_privato.value=='-1'){
			message += "- Campo provincia iscrizione albo obbligatorio per Utente Unina.\r\n";
		     formTest = false;
		}
		
		if (form.nr_iscrione_albo_vet_privato.value==''){
			message += "- Campo nr. iscrizione albo veterinari obbligatorio per Utente Unina.\r\n";
		     formTest = false;
		}
    }
    

    if ((  trim(form.nome.value) == "")) {
        message += "- Campo nome obbligatorio.\r\n";
        formTest = false;
    }
    if ((  trim(form.cognome.value) == "")) {
        message += "- Campo cognome obbligatorio.\r\n";
        formTest = false;
    }
    if ((  trim(form.codiceFiscale.value) == "")) {
        message += "- Campo codice fiscale obbligatorio.\r\n";
        formTest = false;
    }
    else if ((  trim(form.codiceFiscale.value).length != 16)) {
    	message += "- Il codice fiscale deve essere di 16 caratteri.\r\n";
        formTest = false;
    }
    if ((  trim(form.username.value) == "")) {
        message += "- Campo username obbligatorio.\r\n";
        formTest = false;
    }
    if ((  trim(form.username.value) != "")) {
    	var usr = trim(form.username.value);
    	if (usr.indexOf(" ")>-1){
	        message += "- Campo username non deve contenere spazi vuoti.\r\n";
	        formTest = false;
        }
    }
    if (( trim(form.password.value) == "") || (  trim(form.password2.value) == "") || (form.password.value != form.password2.value)) {
        message += "- Controllare che entrambe le password siano inserite correttamente.\r\n";
        formTest = false;
    }
    if (form.idAsl.value == "0") {
        message += "- Campo asl obbligatorio.\r\n";
        formTest = false;
    }
    if (form.roleIdImportatori!= null && form.roleIdImportatori.value=='3'){
	    if (form.id_importatore!= null && form.id_importatore.value=='-1'){
	    	 message += "- Campo Importatore Obbligatorio.\r\n";
	         formTest = false;
	    }
    }    
    if (form.roleIdbdu!= null && form.roleIdbdu.value=='26'){
	    if (form.canilebduId!= null && form.canilebduId.value=='-1'){
	    	 message += "- Campo Canile Obbligatorio.\r\n";
	         formTest = false;
	    }
    }
	
    if (formTest == false) {
      alert("La form non puo\' essere salvata, si prega di verificare quanto segue:\r\n\r\n" + message);
      return false;
    }
    else {
      return true;
    }
  }

  function settaRoleDescription(select, endpoint){
	  if(select.options[ select.selectedIndex ].value != -1){
		  document.getElementById('roleDescription' + endpoint).value = select.options[ select.selectedIndex ].text;
	  }
	  else{
		  document.getElementById('roleDescription' + endpoint).value = '';
	  }
	  
  }

  function settaClinicaDescription(select){
	  if(select.options[ select.selectedIndex ].value != -1){
		  if(document.getElementById('clinicaDescription')){
		  	document.getElementById('clinicaDescription').value = select.options[ select.selectedIndex ].text;
		  }
	  }
	  else{
		  if(document.getElementById('clinicaDescription')){
		  	document.getElementById('clinicaDescription').value = '';
		  }
	  }
	  
  }

  function settaCliniche(){

	  var asl = document.getElementById('idAsl').value;
	  var i, a , n;
	  
	  if ( asl > 0) {

		  //nascondi tutte le cliniche
		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableCliniche');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="none";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableCliniche");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='none';
		   		}
		  }

		  //mostra le cliniche dell'asl selezionata
		  if(document.getElementById('gruppoCliniche' + asl)){
		  	document.getElementById('gruppoCliniche' + asl).style.display='';
		  }
		  if(document.getElementById('clinicaId')){
		  	document.getElementById('clinicaId').value = -1;
		  }
		  
	  }
	  else{

		  //mostra tutte le cliniche
		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableCliniche');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableCliniche");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='';
		   		}
		  }

	  }

  }
  
/*  GISA NON e ANCORA SUPPORTATO
  function settaStrutturaDescription(select){
	  if(select.options[ select.selectedIndex ].value != -1){
		  if(document.getElementById('strutturaDescription')){
		  	document.getElementById('strutturaDescription').value = select.options[ select.selectedIndex ].text;
		  }
	  }
	  else{
		  if(document.getElementById('strutturaDescription')){
		  	document.getElementById('strutturaDescription').value = '';
		  }
	  }
	  
  }
  
  function settaStrutture(){

	  var asl = document.getElementById('idAsl').value;
	  var i, a , n;
	  
	  if ( asl > 0) {

		  //nascondi tutti i canili
		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableStrutture');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="none";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableStrutture");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='none';
		   		}
		  }

		  document.getElementById('gruppoStrutture' + asl).style.display='';
		  document.getElementById('strutturaId').value = -1;
		  
	  }
	  else{

		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableStrutture');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableStrutture");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='';
		   		}
		  }

	  }

  }
*/

  function settaCanileDescription(select){
	  if(select.options[ select.selectedIndex ].value != -1){
		  document.getElementById('canileDescription').value = select.options[ select.selectedIndex ].text;
	  }
	  else{
		  document.getElementById('canileDescription').value = '';
	  }

	  
  }


  function settaCanileDescriptionBdu(select){
	


	  if(select.options[ select.selectedIndex ].value != -1){
		  document.getElementById('canilebduDescription').value = select.options[ select.selectedIndex ].text;
	  }
	  else{
		  document.getElementById('canilebduDescription').value = '';
	  }
	  
  }
  

  function settaImportatoriDescription(select){
	  if(select.options[ select.selectedIndex ].value != -1){
		  document.getElementById('importatoriDescription').value = select.options[ select.selectedIndex ].text;
	  }
	  else{
		  document.getElementById('importatoriDescription').value = '';
	  }
	  
  }

  function settaCanili(){

	  var asl = document.getElementById('idAsl').value;
	  var i, a , n;
	  
	  if ( asl > 0) {
		  //nascondi tutti i canili
		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableCanili');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="none";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableCanili");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='none';
		   		}
		  }

		  //mostra i canili dell'asl selezionata
		  //document.getElementById('gruppoCanili' + asl).style.display='';
		  //document.getElementById('canileId').value = -1;

// per la bdu
		//nascondi tutti i canili
		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableCanilibdu');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="none";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableCanilibdu");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='none';
		   		}
		  }
			
		  //mostra i canili dell'asl selezionata
		  if(document.getElementById('gruppoCanilibdu' + asl))
		  	document.getElementById('gruppoCanilibdu' + asl).style.display='';
		  document.getElementById('canilebduId').value = -1;



		  
		  
	  }
	  else{

		  //mostra tutti i canili
		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableCanili');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableCanili");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='';
		   		}
		  }



		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableCanilibdu');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="";
		        }

		  }
		  else{
		   	 	a = document.getElementsByName("displayableCanilibdu");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='';
		   		}
		  }

	  }

  }
 
  function settaImportatori(){

	  var asl = document.getElementById('idAsl').value;
	  var i, a , n;
	  
	  if ( asl > 0) {

		  //nascondi tutti i canili
		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableImportatori');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="none";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableImportatori");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='none';
		   		}
		  }

		  //mostra i canili dell'asl selezionata
		  if (document.getElementById('gruppoImportatori' + asl))
			  document.getElementById('gruppoImportatori' + asl).style.display='';
		  document.getElementById('id_importatore').value = -1;
		  
	  }
	  else{

		  //mostra tutti i canili
		  if(document.getElementsByClassName){
		        n = document.getElementsByClassName('displayableImportatori');
		        for(i=0;i<n.length;i++){
		          n[i].style.display="";
		        }
		  }
		  else{
		   	 	a = document.getElementsByName("displayableImportatori");
		     	for(i = 0; i < a.length; i++){
					a[i].style.display='';
		   		}
		  }

	  }

  }
  

  function gestisciCanili(){

	  //se e selezionato il ruolo "Utente Canile" per l'endpoint Canina 
	  //mostra la sezione relativa ai canili, 
	  //altrimenti nascondila
	  
	  if ( $("#roleIdCanina").val() == 31 ){
		  $(".rigaCanili").show();
	  }
	  else{
		  $(".rigaCanili").hide();
		  $("#canileId").val(-1);
		  $("#canileDescription").val("");

		  
	  }


	  if ( $("#roleIdbdu").val() == 26 ){
		  $(".rigaCanilibdu").show();
	  }
	  else{
		  $(".rigaCanilibdu").hide();
		  $("#canilebduId").val(-1);
		  $("#canilebduDescription").val("");

  }
  }



  function gestisciImportatori(){

	  //se e selezionato il ruolo "Utente Canile" per l'endpoint Canina 
	  //mostra la sezione relativa ai canili, 
	  //altrimenti nascondila
	  
	  if ( $("#roleIdImportatori").val() == 3 ){
		  $(".rigaImportatori").show();
	  }
	  else{
		  $(".rigaImportatori").hide();
		  $("#id_importatore").val(-1);
		  $("#importatoriDescription").val("");
	  }

  }
  

  function svuotaData(input){
		input.value = '';
  }
	
</script>

<div id="content" align="center">

	<div align="center">
		<a href="Home.us" style="margin: 0px 0px 0px 50px"><img src="images/lista.png" height="18px" width="18px" />Lista Utenti</a>
	</div>

	<h4 class="titolopagina">Aggiungi Utente</h4>
 	

<%  TreeSet<String> listaEndpoint = new TreeSet<String>();
    for(GUCEndpoint endpoint : GUCEndpoint.values()){ 
    	listaEndpoint.add(endpoint.toString());
    }
%>

<form name="addUser" action="guc.Add.us" onSubmit="return checkForm(this);" method="post">

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
	  <th colspan="2"><strong>Contatto</strong></th>
	</tr>
	<tr>
	  <td class="formLabel">Nome</td>
	  <td><input type="text" name="nome" value="${UserRecord.nome}"/><font color=red>*</font></td>
	</tr>
	<tr>
	  <td class="formLabel">Cognome</td>
	  <td><input type="text" name="cognome" value="${UserRecord.cognome}"/><font color=red>*</font></td>
	</tr>
	<tr>
	  <td class="formLabel">Codice fiscale</td>
	  <td><input type="text" name="codiceFiscale" value="${UserRecord.codiceFiscale}" maxlength="16" /><font color=red>*</font></td>
	</tr>
	<tr>
	  <td class="formLabel">Luogo</td>
	  <td>
	  <select id="luogo" name="luogo"  >
	  			<option value="-1">COMUNE</option>
	  			<%List<String> comuniList = (List<String>) request.getAttribute("comuniList");
	  		for(String comune : comuniList)
	  		{
	  		%>
	  			
	  				<option value="<%=comune %>"><%=comune %></option>
			<%} %>	  
	  		</select>
	</td>	  
	</tr>
	<tr>
	  <td class="formLabel">Numero Autorizzazione (Solo per Veterinario Privato)</td>
	  <td>
	  <input type="text" id="numAutorizzazione" name="numAutorizzazione" value="${UserRecord.numAutorizzazione}" disabled="disabled"/>
	</tr>
	
	<tr>
	  <td class="formLabel">Provincia Iscrizione Albo (Solo per Unina e Veterinario Privato)</td>
	  <td>	  
	  <select name="id_provincia_iscrizione_albo_vet_privato" id="id_provincia_iscrizione_albo_vet_privato" disabled="disabled">
	  	<option value="-1">-- NESSUNA VOCE SELEZIONATA --</option>
	  	<% HashMap<String, Integer> HP = (HashMap<String,Integer>)request.getAttribute("HashProvince"); 
	  	   if (HP!=null && !HP.isEmpty()) {
	  		   Iterator it = HP.entrySet().iterator();
	  	   		while (it.hasNext()){ 
	  	   			Map.Entry entry = (Map.Entry)it.next();%>
	  	   			<option value="<%=entry.getValue()%>"><%=entry.getKey()%></option>
	  	<% 		}
	  	   }%>
	  </select>
	</tr>
	
	<tr>
	  <td class="formLabel">Nr. Iscrizione Albo (Solo per Unina e Veterinario Privato)</td>
	  <td>	  
	  <input type="text" id="nr_iscrione_albo_vet_privato" name="nr_iscrione_albo_vet_privato" value="${UserRecord.nr_iscrione_albo_vet_privato}" disabled="disabled"/>
	</tr>
	
	<tr>
	  <td class="formLabel">E-mail</td>
	  <td>  
	  <input type="text" name="email" value="${UserRecord.email}"/>
	</tr>
	
	<tr>
	  <td class="formLabel">Note</td>
	  <td><textarea name="note" rows="5" cols="50" >${UserRecord.note}</textarea></td>
	</tr>
	
	
	<tr>
	  <th colspan="2"><strong>Utente</strong></th>
	</tr>
	<tr>
	  <td class="formLabel">Username</td>
	  <td>
	    <input type="text" name="username" value="${UserRecord.username}"><font color=red>*</font>
	  </td>
	</tr>
	<tr>
	  <td class="formLabel">Password</td>
	  <td><input type="password" name="password"><font color=red>*</font></td>
	</tr>
	<tr>
	  <td class="formLabel">Password (di nuovo)</td>
	  <td><input type="password" name="password2"><font color=red>*</font></td>
	</tr>
	<tr>
	  <td class="formLabel">Scadenza login</td>
	  <td>
	  	<input type="text" id="expires" name="expires" maxlength="32" size="50" readonly="readonly" style="width:136px;"/>
 		<img src="images/b_calendar.gif" alt="calendario" id="id_cal_1" />
			<script type="text/javascript">
   					 Calendar.setup({
     					inputField      :    "expires",     // id of the input field
     					ifFormat        :    "%d/%m/%Y",      // format of the input field
    					button          :    "id_cal_1",  // trigger for the calendar (button ID)
     					singleClick     :    true,
     					timeFormat		:    "24",
     					showsTime		:    false
					 });					    
				 </script>
		<a style="cursor: pointer;" onclick="svuotaData(document.forms[0].expires);"><img src="images/delete.gif" /></a>
	  </td>
	</tr>
	
	
	<tr>
  		<th colspan="2"><strong>A.S.L.</strong></th>
	</tr>
	<tr>
	  	<td nowrap class="formLabel">Visibilita</td>
	  	<td>
	  		<select id="idAsl" name="idAsl" onChange="javascript:settaCliniche(); settaCanili(); settaImportatori();" >
	  			<option value="-1">Tutte le ASL</option>
	  			<option value="0" selected="selected"> -- NESSUNA VOCE SELEZIONATA -- </option>
	  			<c:forEach items="${aslList}" var="a">
	  				<option value="${a.id}">${a.nome}</option>
	  			</c:forEach>
	  		</select>
	      <font color=red>*</font>
	  	</td>
	</tr>
	
	
	<tr>
  		<th colspan="2"><strong>Cliniche VAM</strong></th>
	</tr>
	<% 		TreeMap<Integer, ArrayList<Clinica>> clinicheUtenteHash = (TreeMap<Integer, ArrayList<Clinica>>)request.getAttribute("clinicheUtenteHashVam");
			List<Asl> aslList = (List<Asl>)request.getAttribute("aslList");
    %>
   		<tr>
  			<td class="formLabel">Clinica Vam</td>
  			<td>
  			<%if(clinicheUtenteHash != null && clinicheUtenteHash.size() > 0){ %>
  		
	    		<select id="clinicaId" name="clinicaId" onchange="settaClinicaDescription(this);" multiple="multiple">
	    			<option value="-1" selected="selected"> -- NESSUNA VOCE SELEZIONATA -- </option>
	    		<% for(Asl a : aslList ){%>
	    			<optgroup id="gruppoCliniche<%=a.getId() %>" class="displayableCliniche" label="<%= a.getNome() %>">
	    			<%for(Clinica c : clinicheUtenteHash.get(a.getId())){ %>
	    				<option value="<%= c.getIdClinica() %>"><%= c.getDescrizioneClinica()%></option>
	    			<%} %>
	    			</optgroup>
	    		<%}%>
	    		</select>
	    		<input type="hidden" id="clinicaDescription" name="clinicaDescription" value=""></input>
    		<%}else{ %>
    			Lista cliniche non disponibile
    		<%} %>
    		</td>
   		</tr>
   	
 
<%--    	
   	<tr>
  		<th colspan="2"><strong>Strutture GISA</strong></th>
	</tr>
	<% 		TreeMap<Integer, ArrayList<Struttura>> struttureUtenteHash = (TreeMap<Integer, ArrayList<Struttura>>)request.getAttribute("struttureUtenteHashGisa");
    %>
   		<tr>
  			<td class="formLabel">Struttura Gisa</td>
  			<td>
  			<%if(struttureUtenteHash != null && struttureUtenteHash.size() > 0){ %>
  		
	    		<select id="strutturaId" name="strutturaId" onchange="settaStrutturaDescription(this);" multiple="multiple">
	    			<option value="-1" selected="selected"> -- NESSUNA VOCE SELEZIONATA -- </option>
	    		<% for(Asl a : aslList ){%>
	    			<optgroup id="gruppoStrutture<%=a.getId() %>" class="displayableStrutture" label="<%= a.getNome() %>">
	    			<%for(Struttura s : struttureUtenteHash.get(a.getId())){ %>
	    				<option value="<%= s.getIdStruttura() %>"><%= s.getDescrizioneStruttura()%></option>
	    			<%} %>
	    			</optgroup>
	    		<%}%>
	    		</select>
	    		<input type="hidden" id="strutturaDescription" name="strutturaDescription" value=""></input>
    		<%}else{ %>
    			Lista strutture non disponibile.
    		<%} %>
    		</td>
   		</tr> --%>
   		
    <tr class="rigaCanili" style="display: none;">
  		<th colspan="2"><strong>Canili</strong></th>
	</tr>
	<% 		
	TreeMap<Integer, ArrayList<Canile>> caniliUtenteHash = (TreeMap<Integer, ArrayList<Canile>>)request.getAttribute("caniliUtenteHashCanina");
    %>
   		<tr class="rigaCanili" style="display: none;">
  			<td class="formLabel">Canile Canina</td>
  			<td>
  			<%if(caniliUtenteHash != null && caniliUtenteHash.size() > 0){ %>
  		
	    		<select id="canileId" name="canileId" onchange="settaCanileDescription(this);">
	    			<option value="-1" selected="selected"> -- NESSUNA VOCE SELEZIONATA -- </option>
	    		<% for(Asl a : aslList ){%>
	    			<optgroup id="gruppoCanili<%=a.getId() %>" class="displayableCanili" label="<%= a.getNome() %>">
	    			
	    			
	    			<%for(Canile c : caniliUtenteHash.get(a.getId())){ 
	    			
	    			%>
	    				<option value="<%= c.getIdCanile() %>"><%= c.getDescrizioneCanile()%></option>
	    			<%} %>
	    			</optgroup>
	    		<%}%>
	    		</select>
	    		<input type="hidden" id="canileDescription" name="canileDescription" value=""></input>
    		<%}else{ %>
    			Lista canili non disponibile
    		<%} %>
    		</td>
   		</tr>
   		
   		
   		
   		 <tr class="rigaCanilibdu" style="display: none;">
  		<th colspan="2"><strong>Canili BDU</strong></th>
	</tr>
	<% 		
	TreeMap<Integer, ArrayList<Canile>> caniliUtenteHashbdu = (TreeMap<Integer, ArrayList<Canile>>)request.getAttribute("caniliUtenteHashbdu");
	
    %>
   		<tr class="rigaCanilibdu" style="display: none;">
  			<td class="formLabel">Canile bdu</td>
  			<td>
  			<%if(caniliUtenteHashbdu != null && caniliUtenteHashbdu.size() > 0){
  				%>
  	
	    		<select id="canilebduId" name="canilebduId" onchange="settaCanileDescriptionBdu(this);">
	    			<option value="-1" selected="selected"> -- NESSUNA VOCE SELEZIONATA -- </option>
	    		<% for(Asl a : aslList ){
	    		
	    			
	    			
	    			if(caniliUtenteHashbdu.containsKey(a.getId())){ %>
	    			<optgroup id="gruppoCanilibdu<%=a.getId() %>" class="displayableCanilibdu" label="<%= a.getNome() %>">
	    			
	    			
	    			<%for(Canile c : caniliUtenteHashbdu.get(a.getId())){
	    				%>
	    				<option value="<%= c.getIdCanile() %>"><%= c.getDescrizioneCanile()%></option>
	    			<%} %>
	    			</optgroup>
	    		<%}
	    			
	    		}
  			
	    		
	    		%>

	    		
	    		
	    		</select>
	    		<input type="hidden" id="canilebduDescription" name="canilebduDescription" value=""></input>
    		<%}else{ %>
    			Lista canili non disponibile
    		<%} %>
    		</td>
   		</tr>
   		
   		
   		<%
   		%>
   		
   		
   		
   		<tr class="rigaImportatori" style="display: none;">
  		<th colspan="2"><strong>Importatori</strong></th>
	</tr>
	<% 		
	TreeMap<Integer, ArrayList<Importatori>> importatoriUtenteHash = (TreeMap<Integer, ArrayList<Importatori>>)request.getAttribute("ImportatoriUtenteHashImportatori");
    %>
   		<tr class="rigaImportatori" style="display: none;">
  			<td class="formLabel">Importatore BDU</td>
  			<td>
  			<%if(importatoriUtenteHash != null && importatoriUtenteHash.size() > 0){
  				
  				
  				%>
  		
	    		<select id="id_importatore" name="id_importatore" onchange="settaImportatoriDescription(this);">
	    			<option value="-1" selected="selected"> -- NESSUNA VOCE SELEZIONATA -- </option>
	    		<% for(Asl a : aslList ){
	    		if(importatoriUtenteHash.get(a.getId())!=null)
	    		{
	    		%>
	    			<optgroup id="gruppoImportatori<%=a.getId() %>" class="displayableImportatori" label="<%= a.getNome() %>">
	    			<%for(Importatori c : importatoriUtenteHash.get(a.getId())){ %>
	    				<option value="<%= c.getIdImportatore() %>"><%= c.getRagioneSociale()%></option>
	    			<%} %>
	    			</optgroup>
	    		<%}
	    		}
	    		%>
	    		</select>
	    		<input type="hidden" id="importatoriDescription" name="importatoriDescription" value=""></input>
    		<%}else{ %>
    			Lista Importatori non disponibile
    		<%} %>
    		</td>
   		</tr>
   		
	
	
	<tr>
  		<th colspan="2"><strong>Ruoli</strong></th>
	</tr>
	<%  for(String endpoint : listaEndpoint){ 
    		ArrayList<Ruolo> ruoloUtenteList = (ArrayList<Ruolo>)request.getAttribute("ruoloUtenteList" + endpoint);
    %>
    		<tr>
	  			<td class="formLabel">Ruolo <%= endpoint %></td>
	  			<td>
	  			<%if(ruoloUtenteList != null && ruoloUtenteList.size() > 0){ %>
		    		<select id="roleId<%=endpoint%>" name="roleId<%=endpoint%>" onchange="settaRoleDescription(this, '<%= endpoint %>'); gestisciCanili();gestisciImportatori(); abilitaCampiBDU(this.form);">
		    			<option value="-1" selected="selected"> -- DISABILITATO -- </option>
		    		<% for(Ruolo r : ruoloUtenteList){%>
		    			<option value="<%= r.getRuoloInteger() %>">
		    			<%= r.getRuoloString() + ( r.getNote() != null && !r.getNote().trim().equals("") ? " &nbsp;&nbsp;&nbsp;&nbsp;( " + r.getNote() + " ) ":"" ) %>
		    			</option>
		    		<%}%>
		    		</select>
		    		<input type="hidden" id="roleDescription<%=endpoint%>" name="roleDescription<%=endpoint%>" value=""></input>
	    		<%}else{ %>
	    			Lista ruoli non disponibile
	    		<%} %>
	    		</td>
    		</tr>
    <%
    	} 
    %>
	
</table>
<br>
<input type="submit" value="Inserisci">
</form>

</div>
