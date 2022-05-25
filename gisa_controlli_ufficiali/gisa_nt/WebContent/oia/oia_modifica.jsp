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


<%@page import="org.aspcfs.modules.oia.base.ResponsabileNodo"%><jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="lookupASL" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_vUserInfo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_vUserInfoDelegati" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<jsp:useBean id="lookup_comuni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookupTipologia" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="oOiaNodo" class="org.aspcfs.modules.oia.base.OiaNodo" scope="request"/>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script type="text/javascript">

	function gestisci_label_cu_max(livello) {
		if ( livello != '1' ) {
			document.getElementById('label_intervallo_cu').style.display='';
			document.getElementById('label_intervallo_cu_campioni').style.display='';
		} else {
			document.getElementById('label_intervallo_cu').style.display='none';
			document.getElementById('label_intervallo_cu_campioni').style.display='none';
		}		
	}
	
	function controllaForm()
	{
		var ret = true;
		message = "";

		
		
		if( document.getElementById('codiceASL').value == '-1' )
		{
			message += "\"A.S.L.\" obbligatoria\r\n";
			ret = false;
		}

		if( document.getElementById('responsabile').value == '-1' )		
		{
			message += "\"Responsabile\" obbligatorio\r\n";
			ret = false;
		}

		if(document.getElementById('responsabile').value!='')
		controllo_esistenza_area (document.getElementById('responsabile').value);
		
		

		if( document.getElementById('comune').value == '-1' || document.getElementById('comune').value == '' )	
		{
			message += "\"Comune\" obbligatorio\r\n";
			ret = false;
		}
		if( document.getElementById('campo_descrizione').value == '' )	
		{
			message += "\"Nome Struttura\" obbligatorio\r\n";
			ret = false;
		}
		
  		
  		esistenza_asl = document.getElementById("esistenza_asl").value ;
  		
  		if(esistenza_asl == 'true')
  		{
  			message += "Questa Asl risulta gia essere inserita\r\n";
			ret = false;
  	  	}
  		esistenza_area = document.getElementById("esistenza_area").value;
  		if(esistenza_area == 'true')
  		{
  			message += "Responsabile dipartimento gia presente\r\n";
			ret = false;
  	  	}
  	  	
  		
		if (!ret) {
			alert(message);
		}
		
		return ret;
	}
	

	function costruisci_responsabile_da_asl_callback(returnValue) {
		  campo_combo_da_costruire = returnValue [2];
		  var select = document.getElementById(campo_combo_da_costruire); 
		
		//Azzero il contenuto della seconda select
		for (var i = select.length - 1; i >= 0; i--)
		  select.remove(i);

		indici = returnValue [0];
		valori = returnValue [1];
		//Popolo la seconda Select
		for(j =0 ; j<indici.length; j++){
			  //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
			  var NewOpt = document.createElement('option');
			  NewOpt.value = indici[j]; // Imposto il valore
			  NewOpt.text = valori[j]; // Imposto il testo
		
			  //Aggiungo l'elemento option
			  try
			  {
				  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
			  } catch(e){
				  select.add(NewOpt); // Funziona solo con IE
			  }
		}
		  var NewOpt = document.createElement('option');
		  NewOpt.value = '-1'; // Imposto il valore
		  NewOpt.text = 'Seleziona Tipologia'; // Imposto il testo
		  NewOpt.selected = true ;
		  //Aggiungo l'elemento option
		  try
		  {
			  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
		  } catch(e){
			  select.add(NewOpt); // Funziona solo con IE
		  }
	}
	function costruisci_responsabile_da_asl( id_asl, id_ruoli, campo_combo_da_costruire ) {
		  PopolaCombo.costruisci_responsabile_da_asl(id_asl, id_ruoli , campo_combo_da_costruire, costruisci_responsabile_da_asl_callback)
	}

	function onChangeASL() {


		
		id_asl = document.aggiungiOia.codiceASL.value;

		tiponodo = document.getElementById('tipologia').value ;
		if(tiponodo=='8')
		{
			
			document.getElementById('tr_descrizione').style.display='' ; 
		}
		else
		{

			document.getElementById('tr_descrizione').style.display='none' ; 
			document.getElementById('campo_descrizione').value = document.getElementById('tipologia').options[document.getElementById('tipologia').selectedIndex].text ;
		}
		
		if ( tiponodo == '2' || tiponodo == '5'  )
			ruoli = '19,43'
		if ( tiponodo == '3' || tiponodo == '4'  )
					ruoli = '21,42'				
					
						if ( tiponodo == '1'  )
							ruoli = '16'			
		
		costruisci_responsabile_da_asl( id_asl, ruoli, 'responsabile');
	}

	function onChangeDiscriminante( ) {

		if (document.aggiungiOia.discriminante.value == '1' ) {
			document.getElementById('tr_campo_comune').style.display='';
			document.getElementById('tr_campo_macrocategorie').style.display='none';
		} else if (document.aggiungiOia.discriminante.value == '2' ) {
			document.getElementById('tr_campo_macrocategorie').style.display='';
			document.getElementById('tr_campo_comune').style.display='none';
		} else {
			document.getElementById('tr_campo_comune').style.display='none';
			document.getElementById('tr_campo_macrocategorie').style.display='none';
		}		
	}

	// Mostra/nasconde la combo tipologia di competenza
	// Se il nodo è == livello 3 -> la combo è presente
	// Se il nodo è != livello 3 -> la combo è assente
	function gestisci_tipologia_competenza(livello) {

		if ( livello == '3' ) {
			document.getElementById('tr_campo_discriminante').style.display='';
		} else {
			document.getElementById('tr_campo_discriminante').style.display='none';
		}		
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


<body onLoad="gestisci_tipologia_competenza('<%= oOiaNodo.getN_livello() %>');
			  onChangeDiscriminante();
			  gestisci_label_cu_max('<%= oOiaNodo.getN_livello() %>');
			  onChangeASL();" 
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
		  action="Oia.do?command=Update" 
		  method="post"
		  >
		<input id="campo_id" type="hidden" name="id" value="<%= oOiaNodo.getId() %>" >
		<input id="campo_id_padre" type="hidden" name="id_padre" value="<%= oOiaNodo.getId_padre() %>" >
		<input id="campo_livello" type="hidden" name="livello" value="<%= oOiaNodo.getN_livello() %>" >
		<input id="campo_ruoli" type="hidden" name="ruoli" value="<%= request.getAttribute("ruoli") %>" >
		<input id="campo_tipologia" type="hidden" name="tipologia" value="<%= oOiaNodo.getTipologia_struttura() %>" >
		<input id="campo_n_CU_max" type="hidden" name="n_CU_max" value="<%= request.getAttribute("n_CU_max") %>" >
		<input id="campo_n_CU_campioni_max" type="hidden" name="n_CU_campioni_max" value="<%= request.getAttribute("n_CU_campioni_max") %>" >
		<input id="campo_click_annulla" type="hidden" name="click_annulla" value="0" >
		
		<input id="campo_n_CU_figli" type="hidden" name=n_CU_figli value="<%= request.getAttribute("n_CU_figli") %>" >
		<input id="campo_n_CU_campioni_figli" type="hidden" name="n_CU_campioni_figli" value="<%= request.getAttribute("n_CU_campioni_figli") %>" >
		
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
<%--		<tr>
				<td id="td_campo_codiceASL" nowrap class="formLabel">A.S.L.</td>
				<td>
					<%  String on_change = "onchange=\"onChangeASL()\""; %>
					<%  lookupASL.setJsEvent(on_change); %>
					<%= lookupASL.getHtmlSelect( "codiceASL", oOiaNodo.getId_asl() ) %>
				</td>
	        </tr>
--%>

			<tr>
					<td id="td_campo_codiceASL" nowrap class="formLabel">A.S.L.</td>
					<td>
						<input id="codiceASL" type="hidden" name="codiceASL" value="<%= oOiaNodo.getId_asl() %>" >
						<input disabled id="campo_disabled_asl_stringa" type="text" size="30" name="disabled_asl_stringa" maxlength="30" value="<%= lookupASL.getSelectedValue(oOiaNodo.getId_asl()) %>"></input>
					</td>
		    </tr>				
	        
	        
			<tr <%if(oOiaNodo.getTipologia_struttura()!=8 && oOiaNodo.getTipologia_struttura()!=4){%> style="display: none"<%} %>>
	  			<td id="td_campo_descrizione" nowrap class="formLabel">Denominazione</td>
	  			<td>
					<input id="campo_descrizione" type="text" size="30" name="descrizione" maxlength="30" value="<%= oOiaNodo.getDescrizione_lunga() %>"></input>
	  			</td>
			</tr>
			
			
			
			<tr <%if(oOiaNodo.getN_livello()==3){%>style="display: none"<%} %>>
	  			<td id="td_campo_disabled_tipologia" nowrap class="formLabel">Struttura</td>
	  			<td>
					<input disabled id="campo_disabled_tipologia" type="text" size="30" name="disabled_tipologia" maxlength="30" value="<%=lookupTipologia.getSelectedValue(oOiaNodo.getTipologia_struttura()) %>"></input>
	  			</td>
			</tr>
			
			<tr>
	  			<td id="td_campo_responsabile" nowrap class="formLabel">Responsabile</td>
	  			<td>
	  			
	  			<%
	  			LookupList listaResponsabili = new LookupList();
	  			ArrayList<ResponsabileNodo> lista = oOiaNodo.getListaResponsabili();
	  			for(ResponsabileNodo r : lista)
	  			{
	  				LookupElement el = new LookupElement();
	  				el.setCode(r.getId_utente()+"");
	  				el.setDescription(r.getNome_responsabile());
	  				listaResponsabili.add(el);
	  			}
	  			
	  			%>
	  				<%= lookup_vUserInfo.getHtmlSelect( "responsabile", listaResponsabili ) %>
				</td>
			</tr>
			
			
			
			<tr>
	  			<td id="td_campo_responsabile" nowrap class="formLabel">Delegato</td>
	  			<td>
	  			<%
	  			lookup_vUserInfoDelegati.addItem(-1,"SELEZIONA DELEGATO");
	  			%>
	  			
					<%= lookup_vUserInfoDelegati.getHtmlSelect( "delegato", oOiaNodo.getDelegato()) %>
				</td>
			</tr>
			
			
		<tr>
	  			<td id="td_campo_indirizzo" nowrap class="formLabel">Indirizzo</td>
	  			<td>
					<input id="campo_indirizzo" type="text" size="30" name="indirizzo" maxlength="30" value="<%=oOiaNodo.getIndirizzo() %>"></label> 

					
	  			</td>
			</tr>
			
			<tr>
	  			<td id="td_campo_comune" nowrap class="formLabel">Comune</td>
	  			<td>
	  			<%lookup_comuni.setSelectSize(4);
	  			lookup_comuni.addItem(-1,"Seleziona Comune");
	  			
	  			%>
					<%=lookup_comuni.getHtmlSelect("comune",oOiaNodo.getComune()) %>
	  			</td>
			</tr>
			
			
			<tr>
	  			<td id="td_campo_mail" nowrap class="formLabel">Mail Certificata</td>
	  			<td>
	  			<input id="campo_mail" type="text" size="30" name="mail" maxlength="30" value="<%=oOiaNodo.getMail() %>"></label> 
					
	  			</td>
			</tr>
			
			
	        		
		</table>
		
		<input type="submit" value="Modifica" onclick="javascript:return controllaForm()"/>
		<input type="button" value="Annulla" onclick="javascript:annulla()">
		
	</form>
	

</body>