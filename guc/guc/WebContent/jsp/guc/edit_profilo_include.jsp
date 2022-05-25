<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@page import="it.us.web.util.json.JSONObject"%>

<%@page import="it.us.web.bean.guc.*"%>
<%@page import="java.util.*"%>
<%@page import="it.us.web.util.guc.GUCEndpoint"%>



<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/Encrypt.js"> </script>
<script language="JavaScript" TYPE="text/javascript" SRC="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<jsp:useBean id="UserRecord" class="it.us.web.bean.guc.Utente" scope="request"/>

<script>

function testSharedKey()
{
	
	sharedKey = document.getElementById("sharedKeySuap").value;
	var textToDecrypt = "Welcome to Gisa";
	Encrypt.validaSharedKey(textToDecrypt,sharedKey,testSharedKeyCallback);
}
function testSharedKeyCallback(value)
{
	if (value==true)
		{
			alert('Token Valido');
			
			document.getElementById("esitoTest").innerHTML="<font color='green'>Key  "+document.getElementById("sharedKeySuap").value+"  Valida</font>";
		}
	else
		{
		document.getElementById("esitoTest").innerHTML="<font color='red'>Key "+document.getElementById("sharedKeySuap").value+" Non Valida</font>";
		document.getElementById("sharedKeySuap").value="";
		
		}
	
	
	
	
}

function checkData(check, data){
	if (check.checked && data.value ==''){
		alert('ATTENZIONE! Per disabilitare un utente e\' necessario selezionare prima la data scadenza. ')
		check.checked=false;
	}
}
function settaCliniche(){

	  var asl = document.getElementById('idAsl').value;
	  var i, a , n;
	//  alert(asl);
	  
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

$('.header').click(function(){
    $(this).toggleClass('expand').nextUntil('tr.header').slideToggle(100);
});



</script>

<style>

table, tr, td, th
{
    border: 1px solid black;
    border-collapse:collapse;
}

</style>

<h4 class="titolopagina">PROFILO</h4>
<% if ( UserRecord != null && UserRecord.getId() > 0){  %>	
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	
	<tr>
  		<td width="30%">Data scadenza Profilo/Inizio Validit&agrave; Nuovo Profilo</td>
  		<td>
  		<input type="text" id="dataScadenza" name="dataScadenza" value="<fmt:formatDate value="${UserRecord.dataScadenza}" pattern="dd/MM/yyyy"/>" maxlength="32" size="50" readonly="readonly" style="width:136px;"/>
 		<img src="images/b_calendar.gif" alt="calendario" id="id_cal_1" />
			<script type="text/javascript">
   					 Calendar.setup({
     					inputField      :    "dataScadenza",     // id of the input field
     					ifFormat        :    "%d/%m/%Y",      // format of the input field
    					button          :    "id_cal_1",  // trigger for the calendar (button ID)
     					singleClick     :    true,
     					timeFormat		:    "24",
     					showsTime		:    false
					 });					    
				 </script>
		<a style="cursor: pointer;" onclick="svuotaData(document.forms[0].dataScadenza);"><img src="images/delete.gif" /></a>
		&nbsp;&nbsp;&nbsp; DISABILITA UTENTE SU TUTTI GLI END POINT (utilizzando la data scadenza profilo settata) <input type = "checkbox" checked onClick="return false" value="1" name="cessazione" onChange="checkData(this, document.forms[0].dataScadenza)">
		</td>
	</tr>
	
<%} %>	
	<tr style="display:none">
	  	<td width="30%">VISIBILITA ASL</td>
	  	<td>
	  	<% List<Asl> aslList = (List<Asl>)request.getAttribute("aslList"); %>
	  	<%
	  	if(UserRecord.getAsl()!=null)
	  	{
	  	%>
	  	<%=UserRecord.getAsl().getNome() %>
	  	<input type = "hidden" id="idAsl" name="idAsl" value="<%=UserRecord.getAsl().getId() %>">
<!-- 	  	<script> -->

<!-- 	  	</script> -->
	  	<%}
	  	else{
	  	%>
	  		<select id="idAsl" name="idAsl" onChange="javascript:settaCliniche(true); settaCanili(true); settaImportatori(); resetIdCanileImportatore();">
	  			<option value="-1" <%if(UserRecord.getAsl() != null && UserRecord.getAsl().getId() == -1){ %>selected="selected"<% } %> >Tutte le ASL</option>
	  			<option value="0"> -- NESSUNA VOCE SELEZIONATA -- </option>
	  			<%for(Asl a : aslList){ %>
	  				<option id="<%= a.getId() %>" value="<%= a.getId() %>" <%if(UserRecord.getAsl()!=null && UserRecord.getAsl().getId() == a.getId() ){ %>selected="selected"<% } %> ><%= a.getNome() %></option>
	  			<%} %>
	  		</select>
	      <font color=red>*</font>
	      <%} %>
	  	</td>
	</tr>
	
</table>
   		<br><br>
   		<table class="test" cellpadding="4" cellspacing="0" border="0" width="100%" class="details" style="display:none">
	
	
	
	
    <%  TreeSet<String> listaEndpoint = new TreeSet<String>();
    	for(GUCEndpoint endp : GUCEndpoint.values()){ 
    		listaEndpoint.add(endp.toString());
    	}
    	it.us.web.constants.ExtendedOptions e = new it.us.web.constants.ExtendedOptions();
    	
    	for(String endpoint : listaEndpoint){ 
    		ArrayList<Ruolo> ruoloUtenteList = (ArrayList<Ruolo>)request.getAttribute("ruoloUtenteList" + endpoint);
    %>
	<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details" style="display:none">
    <tr class="header expand">
  		<th colspan="3"><strong>PROFILO <%=endpoint %></strong></th>
	</tr>
    		<tr>
	  			<td width="30%">RUOLO</td>
	  			<td >
	  			<%if(ruoloUtenteList != null && ruoloUtenteList.size() > 0){ %>
		    		<select  id="roleId<%=endpoint%>" style="width:400px" name="roleId<%=endpoint%>" onchange="javascript:settaRoleDescription(this, '<%= endpoint %>');gestisciInfoGestoreStabilimento();gestisciInfoSuap(); gestisciCanili();gestisciGestoreAcque();gestisciApicoltore(); gestisciTrasportatore(); gestisciImportatori();abilitaCampiBDU(this.form,'<%=endpoint%>');abilitaCampiVAM(this.form,'<%=endpoint%>');">
		    			<option value="-1" onclick="abilitaExtOpt(this,'<%= endpoint %>', -1);abilitaCampoAccess(this.form,'<%=endpoint%>')"> -- SELEZIONA RUOLO -- </option>
		    		<%
		    		Ruolo rr = new Ruolo();
		    		for(Ruolo r : ruoloUtenteList){
		    			JSONObject jo = new JSONObject(r.getExtOpt()); 
		    			String s = jo.toString().replaceAll("\"","££");%>
		    			<option value="<%= r.getRuoloInteger() %>" onclick="abilitaExtOpt(this,'<%= endpoint %>', '<%=s%>');abilitaCampoAccess(this.form,'<%=endpoint%>')" <%if(UserRecord.getHashRuoli()!=null && UserRecord.getHashRuoli().get(endpoint)!=null && r.getRuoloInteger().equals(UserRecord.getHashRuoli().get(endpoint).getRuoloInteger()) ){ rr = r;%> selected="selected" <%} %> >
		    			<%= r.getRuoloString() + ( r.getNote() != null && !r.getNote().trim().equals("") ? " &nbsp;&nbsp;&nbsp;&nbsp;( " + r.getNote() + " ) ":"" ) %>
		    			</option>
		    		<%}%>
		    		
		    		<%
		    		if(UserRecord.getHashRuoli()!=null && UserRecord.getHashRuoli().get(endpoint)!=null)
		    		{
		    			%>
		    			<script>
		    			gestisciInfoSuap();
		    			  gestisciInfoGestoreStabilimento();
		    			  </script>
		    			  <%
		    		}
		    		%>
		    		</select>
		    		<input type="hidden" id="roleDescription<%=endpoint%>" name="roleDescription<%=endpoint%>" value="<%=UserRecord.getHashRuoli().get(endpoint)!=null ?  UserRecord.getHashRuoli().get(endpoint).getRuoloString() :""%>"></input>
		    		</td>
		    		
		    		
		    		
		    		<%
		    		if(endpoint.equalsIgnoreCase("vam"))
		    		{
		    		
		    			%>
		    			
		    			
						<tr>
						<td width="30%">Lista Cliniche da Associare</td>
						<td>
		    			<%
		    			TreeMap<Integer, ArrayList<Clinica>> clinicheUtenteHash = (TreeMap<Integer, ArrayList<Clinica>>)request.getAttribute("clinicheUtenteHashVam");
  			if(clinicheUtenteHash != null && clinicheUtenteHash.size() > 0){ %>
						
					
						<select  id="clinicaId" style="width:800px" name="clinicaId" size="10"
						onchange="settaClinicaDescription(this);" multiple="multiple">
							<option id="idClinica-1" value="-1"
								<% if( UserRecord.getClinicheVam().size() <= 0 ){ %>
								selected="selected" <%} %>>-- NESSUNA VOCE SELEZIONATA
								--</option>
							
						
							
							<% for(Asl a : aslList ){%>
							<optgroup id="gruppoCliniche<%= a.getId() %>"
								class="displayableCliniche" label="<%= a.getNome() %>">
								<%for(Clinica c : clinicheUtenteHash.get(a.getId())){
	    				boolean sel = false ;
	    				
	    				
	    				for(Clinica csel : UserRecord.getClinicheVam())
	    			{
	    					
	    				if(csel.getIdClinica()==c.getIdClinica())
	    				{
	    					sel = true ;
	    					System.out.println("e\' stata selezionataaaaaaaaaaaaa !!!!!");
	    				}
	    			}
	    			%>
								<option id="idClinica<%=c.getIdClinica()%>" value="<%= c.getIdClinica() %>" <%=sel==true ? "selected": "" %>><%= c.getDescrizioneClinica()%></option>
								<%} %>
							</optgroup>
							<%}%>
					</select> <input type="hidden" id="clinicaDescription"
						name="clinicaDescription"
						value="<%=UserRecord.getClinicaDescription() %>"></input> <%}else{ %>
						Lista cliniche non disponibile <%} %> 
		    			</td></tr>
		    			
		    			
	<tr class="rigaVetPRivVam"  style="display:none">
	  <td width="30%">Luogo</td>
	  <td>
	  <select id="luogoVam" name="luogoVam"  >
	  			<option value="-1">COMUNE</option>
	  			<%List<String> comuniList = (List<String>) request.getAttribute("comuniList");
	  		for(String comune : comuniList)
	  		{
	  		%>
	  			
	  				<option value="<%=comune %>"  <%=( UserRecord.getLuogoVam() != null && (comune).equals( UserRecord.getLuogoVam())) ?  "selected" : ""  %>><%=comune %></option>
			<%} %>	  
	  		</select>
	</td>	  
	</tr>
	<tr class="rigaVetPRivVam" style="display:none">
	  <td width="30%">Provincia Iscrizione Albo (Solo per Veterinario Privato)</td>
	  <td>	  
	  <select name="id_provincia_iscrizione_albo_vet_privato_vam" id="id_provincia_iscrizione_albo_vet_privato_vam" >
	  	<option value="-1">-- NESSUNA VOCE SELEZIONATA --</option>
	  	<% HashMap<String, Integer> HP = (HashMap<String,Integer>)request.getAttribute("HashProvince"); 
	  	   if (HP!=null && !HP.isEmpty()) {
	  		   Iterator it = HP.entrySet().iterator();
	  	   		while (it.hasNext()){ 
	  	   			Map.Entry entry = (Map.Entry)it.next();%>
	  	   		<%-- 	<script>
	  	   			alert( <%=(UserRecord.getId_provincia_iscrizione_albo_vet_privato() == ((Integer)entry.getValue()).intValue())%>);
	  	   			</script> --%>
	  	   			<option value="<%=entry.getValue()%>" <%=(UserRecord.getId_provincia_iscrizione_albo_vet_privato_vam() == ((Integer)entry.getValue()).intValue()) ?  "selected='selected'" : ""  %>><%=entry.getKey()%></option>
	  	<% 		}
	  	   }%>
	  </select></td>
	</tr>
	
	<tr class="rigaVetPRivVam" style="display:none">
	  <td width="30%">Nr. Iscrizione Albo (Solo per Veterinario Privato)</td>
	  <td>	  
	  <input type="text" id="nr_iscrione_albo_vet_privato_vam" name="nr_iscrione_albo_vet_privato_vam" value="${UserRecord.nr_iscrione_albo_vet_privato_vam}" />
	</tr>
		    			
		    			<%
		    		}
		    		else
		    		{
		    			if ("bdu".equalsIgnoreCase(endpoint))
		    			{
		    				
		    	  			%>
		    	  			
		    	  			
		    	  			<tr class="rigaCanilibdu" >
		    	  			<td width="30%">Lista Canili</td>
		    	  			<td>
		    	  			<%
		    	  			TreeMap<Integer, ArrayList<Canile>> caniliUtenteHashbdu = (TreeMap<Integer, ArrayList<Canile>>)request.getAttribute("caniliUtenteHashbdu");
		    	  			if(caniliUtenteHashbdu != null && caniliUtenteHashbdu.size() > 0){
		    	  				%>
		    	  	
		    		    		<select  id="canilebduId" style="width:400px" name="canilebduId" onchange="settaCanileDescriptionBdu(this);">
		    		    			<option value="-1" selected="selected"> -- SELEZIONA CANILE ASSOCIATO -- </option>
		    		    		<% for(Asl a : aslList ){
		    		    			if(caniliUtenteHashbdu.containsKey(a.getId())){ %>
		    		    			<optgroup id="gruppoCanilibdu<%=a.getId() %>" class="displayableCanilibdu" label="<%=a.getNome() %>">
		    		    			
		    		    			
		    		    			<%for(Canile c : caniliUtenteHashbdu.get(a.getId())){
		    		    				%>
		    		    				<option value="<%= c.getIdCanile() %>"  <%if(UserRecord.getCaniliBdu()!=null && UserRecord.getCaniliBdu().size()>0 && UserRecord.getCaniliBdu().get(0).getIdCanile()==c.getIdCanile()){ %>selected="selected" <%} %> ><%= c.getDescrizioneCanile()%></option>
		    		    			<%} %>
		    		    			</optgroup>
		    		    		<%}
		    		    			
		    		    		}

		    		    		%>
		    	    		</select>
		    		    		<input type="hidden" id="canilebduDescription" name="canilebduDescription" value="${UserRecord.canilebduDescription}"></input>
		    	    		<%}else{ %>
		    	    			Lista canili non disponibile
		    	    		<%}%>
		    	    		</td>
		    	    		<tr class="rigaVetPRivbdu" id="rigaLuogo">
	  <td width="30%">Luogo</td>
	  <td>
	  <select id="luogo" name="luogo"  >
	  			<option value="-1">COMUNE</option>
	  			<%List<String> comuniList = (List<String>) request.getAttribute("comuniList");
	  		for(String comune : comuniList)
	  		{
	  		%>
	  			
	  				<option value="<%=comune %>"  <%=( UserRecord.getLuogo() != null && (comune).equals( UserRecord.getLuogo())) ?  "selected" : ""  %>><%=comune %></option>
			<%} %>	  
	  		</select>
	</td>	  
	</tr>
	<tr class="rigaVetPRivbdu" id="rigaAutorizzazione">
	  <td width="30%">Numero Autorizzazione (Solo per Veterinario Privato)</td>
	  <td>
	  <input type="text" id="numAutorizzazione" name="numAutorizzazione" value="<%=UserRecord.getNumAutorizzazione()%>" />
	</tr>
	
	<tr class="rigaVetPRivbdu">
	  <td width="30%">Provincia Iscrizione Albo (Solo per Unina e Veterinario Privato)</td>
	  <td>	  
	  <select name="id_provincia_iscrizione_albo_vet_privato" id="id_provincia_iscrizione_albo_vet_privato" >
	  	<option value="-1">-- NESSUNA VOCE SELEZIONATA --</option>
	  	<% HashMap<String, Integer> HP = (HashMap<String,Integer>)request.getAttribute("HashProvince"); 
	  	   if (HP!=null && !HP.isEmpty()) {
	  		   Iterator it = HP.entrySet().iterator();
	  	   		while (it.hasNext()){ 
	  	   			Map.Entry entry = (Map.Entry)it.next();%>
	  	   		<%-- 	<script>
	  	   			alert( <%=(UserRecord.getId_provincia_iscrizione_albo_vet_privato() == ((Integer)entry.getValue()).intValue())%>);
	  	   			</script> --%>
	  	   			<option value="<%=entry.getValue()%>" <%=(UserRecord.getId_provincia_iscrizione_albo_vet_privato() == ((Integer)entry.getValue()).intValue()) ?  "selected='selected'" : ""  %>><%=entry.getKey()%></option>
	  	<% 		}
	  	   }%>
	  </select></td>
	</tr>
	
	<tr class="rigaVetPRivbdu">
	  <td width="30%">Nr. Iscrizione Albo (Solo per Unina e Veterinario Privato)</td>
	  <td>	  
	  <input type="text" id="nr_iscrione_albo_vet_privato" name="nr_iscrione_albo_vet_privato" value="${UserRecord.nr_iscrione_albo_vet_privato}" />
	</tr>
	
	
	
	
		    			
		    			
		    			
		 	
		    	    		
		    	    		<%
		    			}
		    			else
		    			{
		    				if("importatori".equalsIgnoreCase(endpoint))
		    				{
		    					
		    		  			TreeMap<Integer, ArrayList<Importatori>> importatoriUtenteHash = (TreeMap<Integer, ArrayList<Importatori>>)request.getAttribute("ImportatoriUtenteHashImportatori");
		    		  			if(importatoriUtenteHash != null && importatoriUtenteHash.size() > 0){
		    		  				
		    		  				
		    		  				%>
		    		  				<tr class="rigaImportatori">
		    		  				<td>Lista Operatori Commerciali</td>
		    		  			<td>
		    			    		<select  style="width:400px"  id="id_importatore" name="id_importatore" onchange="settaImportatoriDescription(this);">
		    			    			<option value="-1" selected="selected"> -- SELEZIONA OPERATORE COMMERCIALE -- </option>
		    			    		<% for(Asl a : aslList ){
		    			    		if(importatoriUtenteHash.get(a.getId())!=null)
		    			    		{
		    			    		%>
		    			    			<optgroup id="gruppoImportatori<%=a.getId() %>" class="displayableImportatori" label="<%= a.getNome() %>">
		    			    			<%for(Importatori c : importatoriUtenteHash.get(a.getId())){ %>
		    			    				<option value="<%= c.getIdImportatore() %>"  <%if(UserRecord.getImportatori()!=null && UserRecord.getImportatori().size()>0 && UserRecord.getImportatori().get(0).getIdImportatore()==c.getIdImportatore()){ %>selected="selected" <%} %> ><%= c.getRagioneSociale()%></option>
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
		    		    		<%
		    				}
		    			}
		    		}
		    		%>
		    		
		    		
		    		
		    		
		    		
		    		<% 
		    				    				
		   		 		ArrayList opt = e.getListOptions(endpoint);
		   		   		if (opt!=null && opt.size()>0){ %>
		   		   			
		   		   			<tr>
		   		   			<td width="30%">Visibilita in</td>
		   		   			<td>
		   		   		<%	for (int ind=0;ind<opt.size();ind++){ %>
		   		   			<%=opt.get(ind).toString().toUpperCase()%>
		   		   					<input type="checkbox" 
		   		   						   id="<%=endpoint+"_"+opt.get(ind) %>" 
		    							   name="<%=endpoint+"_"+opt.get(ind) %>"
		    							   class="<%=endpoint%>" 
		    							   value="<%=UserRecord.getExtOption()!= null && UserRecord.getExtOption().get(endpoint) != null && UserRecord.getExtOption().get(endpoint).size()>0 ? UserRecord.getExtOption().get(endpoint).get(endpoint+"_"+opt.get(ind)) : (UserRecord.getHashRuoli().get(endpoint)!= null && UserRecord.getHashRuoli().get(endpoint).getRuoloInteger()>0 ? rr.getExtOpt().get(endpoint+"_"+opt.get(ind)) : "false")  %>" 
		    							   onclick="extOption(this)" 
		    					 	      <% if ((UserRecord.getExtOption().size()>0 && UserRecord.getExtOption().get(endpoint).get(endpoint+"_"+opt.get(ind))!=null && UserRecord.getExtOption().get(endpoint).get(endpoint+"_"+opt.get(ind)).equals("true") || (rr.getExtOpt().get(endpoint+"_"+opt.get(ind))!=null && rr.getExtOpt().get(endpoint+"_"+opt.get(ind)).equals("true")) )  ){ %>
		    							   		checked="checked"
		    							   <% } else if (UserRecord.getHashRuoli().get(endpoint)!= null &&UserRecord.getHashRuoli().get(endpoint).getRuoloInteger()==-1 ){ %>
		    							   		disabled="disabled"
		    							   <% } %> 
		    							   />  
		    							   
		    						<input type="hidden" 
		    					   		id="<%="hidden_"+endpoint+"_"+opt.get(ind) %>" 
		    					   		name="<%="hidden_"+endpoint+"_"+opt.get(ind) %>" 
		    					   		value="<%=UserRecord.getExtOption().get(endpoint)!= null && UserRecord.getExtOption().get(endpoint).get(endpoint+"_"+opt.get(ind))!=null ? (UserRecord.getExtOption().get(endpoint)!=null ? UserRecord.getExtOption().get(endpoint).get(endpoint+"_"+opt.get(ind)) : "false") : (UserRecord.getHashRuoli().get(endpoint)!= null && UserRecord.getHashRuoli().get(endpoint).getRuoloInteger()>0 ? rr.getExtOpt().get(endpoint+"_"+opt.get(ind)) : "false")%>"
		    					   		class="<%="hidden_"+endpoint%>"/>
		    					   		
		    				   			
		   		   	<%		}	%></td></tr>
<%	 		
		   		   		}%>
		   		   		
		   		   		
		   		   		
		    		<%
		    		if(endpoint.equalsIgnoreCase("gisa_ext"))
		    		{
		    			%>
		    			
		    			<tr style="display: none;" class="gestoreDisp" id="gestoreDispId" >
		    	  			<td width="30%">Numero Registrazione Stabilimento</td>
		    	  			<td>
		    	  			<input type = "text" name="numRegStab"  id ="numRegStab"  width="350px;" value="" class="inputGestore" value="<%=UserRecord.getNumRegStab() != null ? UserRecord.getNumRegStab() :"" %>" ><font color=red> *</font><br/>
		    	  			<label class="warningGiava" style="display:none">Attenzione. In caso di ALLEVAMENTO utilizzare questa sintassi: <font color="red">CODICE AZIENDA</font>-<font color="green">ID FISCALE PROPRIETARIO</font></label>
		    	  			</td>
		    	  			
		    	  			</tr>
		    			
		    			
		    			<tr class="suapDisp" class="inputSuap">
		    	  			<td width="30%">LIVELLO ACCREDITAMENTO</td>
		    	  			<td>
		    	  				<select name = "livelloAccreditamentoSuap" id = "livelloSuap" class="inputSuap" onchange="gestisciInfoSuap();">
		    	  				<option value = "" >SELEZIONA LIVELLO</option>
		    	  				<option value = "1" <%=UserRecord.getLivelloAccreditamentoSuap()==1 ? "selected" : "" %>>I LIVELLO - SOLO SUPPORTO</option>
		    	  				<option value = "2" <%=UserRecord.getLivelloAccreditamentoSuap()==2 ? "selected" : "" %>>II LIVELLO - INTEROPERATIBILITA</option>
		    	  				</select>
		    	  			</td>
		    	  		</tr>
		    	  			
		    			<tr class="suapDisp" >
		    	  			<td width="30%">Scegli Comune</td>
		    	  			
		    	  			 <td>
	  			<select id="comune" name="comuneSuap"  class="inputSuap"  >
	  			<option value="">COMUNE</option>
	  			<%List<String> comuniList = (List<String>) request.getAttribute("comuniList");
	  		for(String comune : comuniList)
	  		{
	  		%>
	  			
	  				<option value="<%=comune %>"  <%=( UserRecord.getLuogo() != null && (comune).equals( UserRecord.getComuneSuap())) ?  "selected" : ""  %>><%=comune %></option>
			<%} %>	  
	  		</select>
	</td>	</tr>
	
<!-- 					<tr class="suapDisp"  > -->
<!-- 		    	  			<td width="30%">TELEFONO</td> -->
<!-- 		    	  			<td> -->
<%-- 		    	  				<input type = "text"name = "telefonoSuap" class="inputSuap" value = "<%=UserRecord.getTelefonoSuap()!=null && !"".equals(UserRecord.getTelefonoSuap()) ? UserRecord.getTelefonoSuap() :"" %>"> --%>
<!-- 		    	  			</td> -->
<!-- 		    	  		</tr> -->
		    	  		
		    	  			
		    	  		
		    	  			<tr class="suapDisp"  >
		    	  			<td width="30%">Indirizzo Ip Comune Suap</td>
		    	  			<td>
		    	  			<input type = "text" name="ipSuap" placeholder="192.144.1.11"  width="150px;" value="<%= UserRecord.getIpSuap()%>" class="inputSuapII" >
		    	  			</td>
		    	  			
		    	  			</tr>
		    	  			
		    	  			
		    	  			<tr class="suapDisp"  >
		    	  			<td width="30%">Indirizzo Pec Suap</td>
		    	  			<td>
		    	  			<input  type = "text" name="pecSuap" placeholder="suap@pec.it"  value="<%=UserRecord.getPecSuap()%>" class="inputSuapII">
		    	  			</td>
		    	  			
		    	  			</tr>
		    	  			
		    	  			<tr class="suapDisp"  >
		    	  			<td width="30%">Indirizzo di Callback</td>
		    	  			<td>
		    	  			<input  type = "text" class="inputSuapII" name="callbackSuap" placeholder="http://www.comune.it/callback.php"  value="<%=UserRecord.getCallbackSuap()%>" >
		    	  			</td>
		    	  			
		    	  			</tr>
		    	  			
		    	  			<tr class="suapDisp"  >
		    	  			<td width="30%">Indirizzo di Callback 2 (Opzionale)</td>
		    	  			<td>
		    	  			<input type = "text" name="callbackSuap_ko" placeholder="http://www.comune.it/callback.php"  value="<%=UserRecord.getCallbackSuap()%>">
		    	  			</td>
		    	  			
		    	  			</tr>
		    	  			
		    	  			<tr class="suapDisp"  >
		    	  			<td width="30%">Shered Key (*) indica la cchiave con cui criptare e decriptare le informazioni che servono a riconoscere un comune in gisa</td>
		    	  			<td>
		    	  			<input  type = "text" class="inputSuapII" name="sharedKeySuap" id = "sharedKeySuap" placeholder=""  value="<%=UserRecord.getSharedKeySuap()%>" >
		    	  			<div id="esitoTest"></div>
		    	  			<input type = "button" value = "Verifica Validita" onclick="testSharedKey()">
		    	  			</td>
		    	  			
		    	  			</tr>
	    					<tr style="display:none" class="rigaGestoreAcque" id="rigaGestore">
	    					  <td width="30%">Gestore Acque</td>
	    					  <td>
	    					  <select id="gestore" name="gestore"  >
	    					  		<option value="-1">&lt;-- Selezionare voce --&gt;</option>
<%	
								    ArrayList<GestoreAcque> gestoriList = (ArrayList<GestoreAcque>) request.getAttribute("gestoriList");
	    					  		for(GestoreAcque gestore : gestoriList)
	    					  		{
%>
	    					  			
	    					  		    <option value="<%=gestore.getId()%>"  <%=( UserRecord.getGestore()>0 && gestore.getId()==UserRecord.getGestore()) ?  "selected" : ""  %>><%=gestore.getNome()%></option>
<%
									} 
%>	  
	    					  		</select><font color=red> *</font>
	    					</td>	  
	    					</tr>
	    					<tr style="display:none" class="rigaComuneGestoreAcque" id="rigaComuneGestore">
	    					  <td width="30%">Comune Gestore Acque</td>
	    					  <td>
	    					  <select id="comuneGestore" name="comuneGestore"  >
	    					  		<option value="-1">&lt;-- Selezionare voce --&gt;</option>
<%	
								    ArrayList<Comune> comuni = (ArrayList<Comune>) request.getAttribute("comuni");
	    					  		for(Comune comune : comuni)
	    					  		{
%>
	    					  			
	    					  		    <option value="<%=comune.getId()%>"  <%=( UserRecord.getComuneGestore()>0 && comune.getId()==UserRecord.getComuneGestore()) ?  "selected" : ""  %>><%=comune.getNome()%></option>
<%
									} 
%>	  
	    					  		</select><font color=red> *</font>
	    					</td>	  
	    					</tr>
	    					
	    					<tr style="display: none;" class="rigaTipoAttivitaApicoltore" id="rigaTipoAttivitaApicoltore">
	    					  <td width="30%">Tipo attivita' apicoltore</td>
	    					  <td>
	    					  		<input type="radio" id="tipoAttivitaApicoltoreA" name="tipoAttivitaApicoltore" value="A" onClick="gestisciApicoltore()" <%=UserRecord.getTipoAttivitaApicoltore() != null && UserRecord.getTipoAttivitaApicoltore().equals("A") ? "checked" : "" %>> Per autoconsumo <input type="radio" id="tipoAttivitaApicoltoreC" name="tipoAttivitaApicoltore" value="C" onClick="gestisciApicoltore()" <%=UserRecord.getTipoAttivitaApicoltore() != null && UserRecord.getTipoAttivitaApicoltore().equals("C") ? "checked" : "" %>> Per commercio <font color=red> *</font>
	    					</td>	  
	    					</tr>
	    					
	    					<tr style="display: none;" class="rigaDelegatoApicoltore" id="rigaDelegatoApicoltore">
	    					  <td width="30%">Partita iva associazione/attivita' apicoltura</td>
	    					  <td>
	    					  		<input pattern="[0-9]{11}" type="text" name="piva" id="piva" width="150px;" class="inputDelegato" value="<%=UserRecord.getPiva() != null ? UserRecord.getPiva() :"" %>" maxlength="11" ><font color=red> *</font>
	    					</td>	  
	    					</tr>
	    					
	    					
	    					<tr style="display:none" class="rigaComuneApicoltore" id="rigaComuneApicoltore">
	    					  <td width="30%">Comune Apicoltore</td>
	    					  <td>
	    					  <select id="comuneApicoltore" name="comuneApicoltore"  >
	    					  		<option value="-1">&lt;-- Selezionare voce --&gt;</option>
<%	
								    ArrayList<Comune> comuniApicoltore = (ArrayList<Comune>) request.getAttribute("comuni");
	    					  		for(Comune comune : comuniApicoltore)
	    					  		{
%>
	    					  			
	    					  		    <option value="<%=comune.getId()%>" <%=( UserRecord.getComuneApicoltore()>0 && comune.getId()==UserRecord.getComuneApicoltore()) ?  "selected" : ""  %>><%=comune.getNome()%></option>
<%
									} 
%>	  
	    					  		</select><font color=red> *</font>
	    					</td>	  
	    					</tr>
	    					
	    					<tr style="display: none;" class="rigaIndirizzoApicoltore" id="rigaIndirizzoApicoltore">
	    					  <td width="30%">Indirizzo Apicoltore</td>
	    					  <td>
	    					  		<input type="text" name="indirizzoApicoltore" id="indirizzoApicoltore" width="150px;" value="<%=UserRecord.getIndirizzoApicoltore() != null ? UserRecord.getIndirizzoApicoltore() :"" %>" maxlength="50" ><font color=red> *</font>
	    					</td>	  
	    					</tr>
	    					
	    					<tr style="display: none;" class="rigaCapIndirizzoApicoltore" id="rigaCapIndirizzoApicoltore">
	    					  <td width="30%">CAP Indirizzo Apicoltore</td>
	    					  <td>
	    					  		<input type="text" name="capIndirizzoApicoltore" id="capIndirizzoApicoltore" size="5" value="<%=UserRecord.getCapIndirizzoApicoltore() != null ? UserRecord.getCapIndirizzoApicoltore() :"" %>" maxlength="5" ><font color=red> *</font>
	    					</td>	  
	    					</tr>
	    					
	    		
	    		<tr style="display:none" class="rigaComuneTrasportatore" id="rigaComuneTrasportatore">
	    					  <td width="30%">Comune Trasportatore/Distributore</td>
	    					  <td>
	    					  <select id="comuneTrasportatore" name="comuneTrasportatore"  >
	    					  		<option value="-1">&lt;-- Selezionare voce --&gt;</option>
<%	
								    ArrayList<Comune> comuniTrasportatore = (ArrayList<Comune>) request.getAttribute("comuni");
	    					  		for(Comune comune : comuniTrasportatore)
	    					  		{
%>
	    					  			
	    					  		    <option value="<%=comune.getId()%>" <%=( UserRecord.getComuneTrasportatore()>0 && comune.getId()==UserRecord.getComuneTrasportatore()) ?  "selected" : ""  %>><%=comune.getNome()%></option>
<%
									} 
%>	  
	    					  		</select><font color=red> *</font>
	    					</td>	  
	    					</tr>
	    					
	    					<tr style="display: none;" class="rigaIndirizzoTrasportatore" id="rigaIndirizzoTrasportatore">
	    					  <td width="30%">Indirizzo Trasportatore/Distributore</td>
	    					  <td>
	    					  		<input type="text" name="indirizzoTrasportatore" id="indirizzoTrasportatore" width="150px;" value="<%=UserRecord.getIndirizzoTrasportatore() != null ? UserRecord.getIndirizzoTrasportatore() :"" %>" maxlength="50" ><font color=red> *</font>
	    					</td>	  
	    					</tr>
	    					
	    					<tr style="display: none;" class="rigaCapIndirizzoTrasportatore" id="rigaCapIndirizzoTrasportatore">
	    					  <td width="30%">CAP Indirizzo Trasportatore/Distributore</td>
	    					  <td>
	    					  		<input type="text" name="capIndirizzoTrasportatore" id="capIndirizzoTrasportatore" size="5" value="<%=UserRecord.getCapIndirizzoTrasportatore() != null ? UserRecord.getCapIndirizzoTrasportatore() :"" %>" maxlength="5" ><font color=red> *</font>
	    					</td>	  
	    					</tr>			
<%				
		    		}
%>
		   		   		
		   		   		<%  	
	    		  }else{ %>
	    			Lista ruoli non disponibile
	    			
	    			<input type="hidden"  id="<%="roleId"+endpoint %>"  name="<%="roleId"+endpoint %>" value="-1">
		    					   		
	    		<%} %>
	    		</td>
    		</tr>
    		</table>
    		<br><br>
    <%
    	} 
    %>
  

  
</table>



<%	if(UserRecord.getAsl()!=null){%>
<script>
try {settaCliniche(true);} catch(err){}
try {settaImportatori(true);} catch(err){}
if(document.getElementById('roleIdbdu')!=null && document.getElementById('roleIdbdu').value!='31')
{
	try {resetIdCanileImportatore(true);} catch(err){}
}

</script>
<%}

for(Clinica csel : UserRecord.getClinicheVam())
{
%>	
	<script>
	    document.getElementById('idClinica-1').selected=false;
		document.getElementById('idClinica<%=csel.getIdClinica()%>').selected="selected";
	</script>
<%
}
%>
