<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@ page import="java.util.*,
				 java.text.DateFormat,
				 org.aspcfs.controller.*,
				 org.aspcfs.utils.*,
				 org.aspcfs.utils.HTTPUtils ,
				 org.aspcfs.utils.web.*,
				 org.aspcfs.modules.contacts.base.*,
				 org.aspcfs.controller.SystemStatus" %>


<%@page import="org.aspcfs.modules.oia.base.UserInfo"%><jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="lookupASL" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_vUserInfo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_discriminante" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_comuni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_macrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_comuni_selezionati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_macrocategorie_selezionati" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="oOiaNodo" class="org.aspcfs.modules.oia.base.OiaNodo" scope="request"/>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script type="text/javascript">

	function gestisci_label_cu_max(livello) {
		
			document.getElementById('label_intervallo_cu').style.display='';
			document.getElementById('label_intervallo_cu_campioni').style.display='';
			
	}
	
	function controllaForm()
	{
		var ret = true;
		message = "";

		if( document.getElementById('codiceASL').value == -1 )
		{
			message += "\"A.S.L.\" obbligatoria\r\n";
			ret = false;
		}

		if( document.getElementById('responsabile').value == -1 )		
		{
			message += "\"Responsabile\" obbligatorio\r\n";
			ret = false;
		}
		
		

		if( document.getElementById('campo_n_cu').value == '' )	
		{
			message += "\"N°. CU Assegnati\" obbligatorio\r\n";
			ret = false;
		} else {
  			n_cu 		= parseInt(document.getElementById('campo_n_cu').value);
  			n_cu_max	= parseInt(document.getElementById('campo_n_CU_max').value);
  			n_cu_figli	= parseInt(document.getElementById('campo_n_CU_figli').value);
  			
  			if( n_cu > n_cu_max )
  			{
  				message += "\"Il valore dei \" C.U. Assegnati\" deve essere compreso tra 0 e " + n_cu_max + "  \r\n";
  				ret = false;
  			}

  			/*if( n_cu < n_cu_figli )
  			{
  				message += "\"Il valore dei \" C.U. Assegnati\" deve essere maggiore di " + n_cu_figli + "\r\n";
  				ret = false;
  			}*/
  			
  		}
  
  		if( document.getElementById('campo_n_cu_campioni').value == '' )	
    		{
    			message += "\"N°. CU Campioni\" obbligatorio\r\n";
    			ret = false;
    		} else {
    			n_cu_campioni 		= parseInt(document.getElementById('campo_n_cu_campioni').value);
    			n_cu_max_campioni	= parseInt(document.getElementById('campo_n_CU_campioni_max').value);
    			n_CU_campioni_figli	= parseInt(document.getElementById('campo_n_CU_campioni_figli').value);
    			
    			if( n_cu_campioni > n_cu_max_campioni )
    			{
    				message += "\"Il valore dei \" C.U. Campioni\" deve essere compreso tra 0 e " + n_cu_max_campioni + "\r\n";
    				ret = false;
    			}
/*
    			if( n_cu_campioni < n_CU_campioni_figli )
      			{
      				message += "\"Il valore dei \" C.U. Campioni\" deve essere maggiore di " + n_CU_campioni_figli + "  \r\n";
      				ret = false;
      			}*/
    		}
  
		if (!ret) {
			alert(message);
		}

		return ret;
	}



	

	
	

	function annulla() {
		document.aggiungiOia.click_annulla.value = '1' ;
		window.close();
	}

	function controlla_modifica() {
		if (document.aggiungiOia.click_annulla.value == '0' ) {
			window.opener.location.reload ();
		}
	}

</script>


<body onLoad="gestisci_label_cu_max('<%= oOiaNodo.getN_livello() %>');" 
	  onunload="javascript:controlla_modifica();">

	<%-- Trails --%>
	<table class="trails" cellspacing="0">
		<tr>
			<td width="100%"><a href="Oia.do">Modellatore Organizzazione ASL</a>  
			</td>
		</tr>
	</table>
	<%-- End Trails --%>
	
	
	<form id="aggiungiOia" 
		  name="aggiungiOia" 
		  action="Oia.do?command=Inserisci" 
		  method="post"
		  >
		  <%if(oOiaNodo.getId()>0)
		  {
			  %>
		  		<input id="campo_id_padre" type="hidden" name="id" value="<%= oOiaNodo.getId_padre() %>" >
		  <%} %>
		
		<input id="campo_id_padre" type="hidden" name="id_padre" value="<%= oOiaNodo.getId_padre() %>" >
		<input id="campo_id_padre" type="hidden" name="id_padre" value="<%= oOiaNodo.getId_padre() %>" >
		<input id="campo_livello" type="hidden" name="livello" value="<%= oOiaNodo.getN_livello() %>" >
		<input id="campo_ruoli" type="hidden" name="ruoli" value="<%= request.getAttribute("ruoli_sian_vet") %>" >
				
		
		<input id="campo_tipologia" type="hidden" name="tipologia" value="<%= oOiaNodo.getTipologia_nodo() %>" >
		<input id="campo_n_CU_max" type="hidden" name="n_CU_max" value="<%= request.getAttribute("n_CU_max") %>" >
		<input id="campo_n_CU_campioni_max" type="hidden" name="n_CU_campioni_max" value="<%= request.getAttribute("n_CU_campioni_max") %>" >
		<input id="campo_click_annulla" type="hidden" name="click_annulla" value="0" >
		
		<input id="campo_n_CU_figli" type="hidden" name=n_CU_figli value="<%= request.getAttribute("n_CU_figli") %>" >
		<input id="campo_n_CU_campioni_figli" type="hidden" name="n_CU_campioni_figli" value="<%= request.getAttribute("n_CU_campioni_figli") %>" >
		
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">


			<tr>
					<td id="td_campo_codiceASL" nowrap class="formLabel">A.S.L.</td>
					<td>
						<input id="codiceASL" type="hidden" name="codiceASL" value="<%= oOiaNodo.getId_asl() %>" >
						<input disabled id="campo_disabled_asl_stringa" type="text" size="30" name="disabled_asl_stringa" maxlength="30" value="<%= oOiaNodo.getAsl_stringa() %>"></input>
					</td>
		    </tr>				

			
			
			<tr>
	  			<td id="td_campo_responsabile" nowrap class="formLabel">Persona</td>
	  			<td>
	  			<select name = "responsabile">
					<%
					
					HashMap<String,ArrayList<UserInfo>> listA_utenti = (HashMap<String,ArrayList<UserInfo>>) request.getAttribute("oOiaNodoUtenti");
					Iterator<String> itKey = listA_utenti.keySet().iterator();
					
					while (itKey.hasNext())
					{
						String key =itKey.next();
						ArrayList<UserInfo> lista = listA_utenti.get(key);
						
						%>
						<optgroup label="<%= key %>">
						<%
						for(UserInfo utente : lista)
						{
							
								
						%>
						<option  <%if(utente.getId_utente().equals(oOiaNodo.getId_utente())){%> selected="selected" <%} %> value = "<%=utente.getId_utente() %>">
						<%=utente.getNome_utente()+" "+utente.getCognome_utente() %>
						</option>
						<%} %>
						</optgroup>
						<%
						
					}
					
					%>
					</select>
					
				</td>
			</tr>
			
			
			<tr>
	  			<td id="td_campo_n_cu" nowrap class="formLabel">N°. C.U. Assegnati</td>
	  			<td>
					<input id="campo_n_cu" type="text" size="30" name="n_cu" maxlength="30" value="<%= oOiaNodo.getN_controlli_ufficiali() %>"></input><font color="red">*</font> <label id="label_intervallo_cu">Il valore deve essere compreso tra 0 e <%= request.getAttribute("n_CU_max") %></label> 
	  			</td>
			</tr>
			
			<tr>
	  			<td id="td_campo_n_cu_campioni" nowrap class="formLabel">N°. C.U. Campioni</td>
	  			<td>
					<input id="campo_n_cu_campioni" type="text" size="30" name="n_cu_campioni" maxlength="30" value="<%= oOiaNodo.getN_cu_campioni() %>"></input><font color="red">*</font> <label id="label_intervallo_cu_campioni">Il valore deve essere compreso tra 0 e <%= request.getAttribute("n_CU_campioni_max") %></label> 
	  			</td>
			</tr>
			
		
			
		
	        		
		</table>
		
		<input type="submit" value="Modifica" onclick="javascript:return controllaForm()"/>
		<input type="button" value="Annulla" onclick="javascript:annulla()">
		
	</form>
	

</body>