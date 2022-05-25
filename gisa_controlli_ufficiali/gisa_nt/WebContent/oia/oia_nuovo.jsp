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


<%@page import="org.aspcfs.modules.oia.base.OiaNodo"%><jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="lookupASL" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_vUserInfo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_vUserInfoDelegati" class="org.aspcfs.utils.web.LookupList" scope="request"/>


<jsp:useBean id="lookup_discriminante" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_comuni" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="lookup_macrocategorie" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="lookupTipologia" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<script type="text/javascript">




function get_numero_campioni_controlli_pianificati (idAsl)
{
	 PopolaCombo.getNumeroCuCampioniPianificati(idAsl, get_numero_campioni_controlli_pianificati_callback)

}

function get_numero_campioni_controlli_pianificati_callback (returnValue)
{
	document.getElementById("campo_n_cu").value = returnValue[0] ; 
	document.getElementById("campo_n_cu_campioni").value = returnValue[1] ; 
}

/*
	function gestisci_tipologia_competenza(livello) {
		if ( livello == '3' ) {
			document.getElementById('tr_campo_discriminante').style.display='';
		} else {
			document.getElementById('tr_campo_discriminante').style.display='none';
		}		
	}*/

	function controllo_esistenza_asl (idAsl)
	{
		
		 PopolaCombo.controlloEsuistenzaAsl(idAsl, controllo_esistenza_asl_callback)

	}

	function controllo_esistenza_asl_callback(returnValue)
	{
	
		document.getElementById('esistenza_asl').value = returnValue ;
		
	}

	function controllo_esistenza_area (idAsl)
	{
		 PopolaCombo.controlloEsuistenzaAreaAsl(idAsl, controllo_esistenza_area_callback)

	}

	function controllo_esistenza_area_callback(returnValue)
	{
		
		document.getElementById('esistenza_area').value = returnValue ;
		
	}
	
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

		
		
		if( document.getElementById('codiceASL').value == -1 )
		{
			message += "\"A.S.L.\" obbligatoria\r\n";
			ret = false;
		}
		if( document.getElementById('campo_descrizione').value == '' )	
		{
			message += "\"Denominazione\" obbligatorio\r\n";
			ret = false;
		}

		if( document.getElementById('delegato').value == '-1' )		
		{
			message += "\"Delegato\" obbligatorio\r\n";
			ret = false;
		}

		if( document.getElementById('tipologia')!=null && document.getElementById('tipologia').value == '-1' )		
		{
			message += "\"Struttura\" obbligatorio\r\n";
			ret = false;
		}

		if( document.getElementById('tipologia')!=null && (document.getElementById('tipologia').value == '8' || document.getElementById('tipologia').value == '4' )	)	
		{
			if( document.getElementById('descrizione')=='')
			{
			message += "\"Denominazione\" obbligatorio\r\n";
			ret = false;
			}
		}

		

		


		
		

		if( document.getElementById('comune').value == '-1' ||document.getElementById('comune').value == '' )	
		{
			message += "\"Comune\" obbligatorio\r\n";
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
  			message += "Delegato dipartimento gia presente\r\n";
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
		  PopolaCombo.costruisci_responsabile_da_asl(id_asl, document.getElementById('tipologia').value , campo_combo_da_costruire, costruisci_responsabile_da_asl_callback)
	}

	function onChangeASL() {


		
		id_asl = document.aggiungiOia.codiceASL.value;

		tiponodo = document.getElementById('tipologia').value ;
if(tiponodo=='8' || tiponodo=='4')
{
	
	document.getElementById('tr_descrizione').style.display='' ; 
	document.getElementById('campo_descrizione').value ='';
}
else
{

	document.getElementById('tr_descrizione').style.display='none' ; 
	document.getElementById('campo_descrizione').value = document.getElementById('tipologia').options[document.getElementById('tipologia').selectedIndex].text ;
}
		
		
		//costruisci_responsabile_da_asl( id_asl, document.getElementById('tipologia').value, 'responsabile');
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

	function annulla() {
		document.aggiungiOia.click_annulla.value = '1' ;
		window.close();
	}

	function controlla_inserimento() {
		if (document.aggiungiOia.click_annulla.value == '0' ) {
			window.opener.location.reload ();
			window.opener.location.reload ();
		}
	}

function setResponsabiliLivello2(tipoStruttura,idasl)
{
	
	costruisci_responsabile_da_asl(idasl,tipoStruttura, 'responsabile');

}


	
</script>
	<%	 
						OiaNodo oNodoPadre = null ;
						oNodoPadre = OiaNodo.load( request.getParameter("id_padre") ); %>

<body onLoad="javascript:gestisci_label_cu_max('<%= request.getParameter("livello")%>');onChangeASL()
						 " 
   	  onunload="javascript:controlla_inserimento();" 
	  >

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
		 <input type = "hidden" name = "esistenza_asl" id = "esistenza_asl" value = "false">
		 <input type = "hidden" name = "esistenza_area" id = "esistenza_area" value = "false">
		 <input id="campo_livello" type="hidden" name="livello" value="<%= request.getParameter("livello") %>" >
		 <input id="campo_id_padre" type="hidden" name="id_padre" value="<%= request.getParameter("id_padre") %>" >
		 <input id="campo_ruoli" type="hidden" name="ruoli" value="<%= request.getAttribute("ruoli") %>" >
<!--		 <input id="campo_tipologia" type="hidden" name="tipologia" value="<%=  request.getParameter("tipologia")   %>" >-->
		 <input id="campo_n_CU_max" type="hidden" name="n_CU_max" value="<%= request.getAttribute("n_CU_max") %>" >
		 <input id="campo_n_CU_campioni_max" type="hidden" name="n_CU_campioni_max" value="<%= request.getAttribute("n_CU_campioni_max") %>" >
		 <input id="campo_click_annulla" type="hidden" name="click_annulla" value="0" >
		
		<input id="campo_n_CU_figli" type="hidden" name=n_CU_figli value="<%= request.getAttribute("n_CU_figli") %>" >
		<input id="campo_n_CU_campioni_figli" type="hidden" name="n_CU_campioni_figli" value="<%= request.getAttribute("n_CU_campioni_figli") %>" >
	
		<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
		
			<% 
			
			if ( Integer.parseInt( request.getParameter("livello") ) == 1 ) { %>
				<tr>
					<td id="td_campo_codiceASL" nowrap class="formLabel">A.S.L.</td>
					<td>
						<%  String on_change = "onchange=\"onChangeASL();controllo_esistenza_asl(document.getElementById('codiceASL').value);get_numero_campioni_controlli_pianificati(document.getElementById('codiceASL').value)\""; %>
						<%  lookupASL.setJsEvent(on_change); %>
						<%= lookupASL.getHtmlSelect( "codiceASL", -1) %>
					</td>
		        </tr>	
		    <% } else { %>
				<tr>
					<td id="td_campo_codiceASL" nowrap class="formLabel">A.S.L.</td>
					<td>
					
						<input id="codiceASL" type="hidden" name="codiceASL" value="<%= oNodoPadre.getId_asl() %>" >
						<input disabled id="campo_disabled_asl_stringa" type="text" size="30" name="disabled_asl_stringa"  value="<%= lookupASL.getSelectedValue(oNodoPadre.getId_asl()) %>"></input>
					</td>
		        </tr>			    	
		    <% } %>
	        
	     
			
			<tr style="display: none" id = "tr_descrizione">
	  			<td id="td_campo_descrizione" nowrap class="formLabel">Denominazione</td>
	  			<td>
					<input id="campo_descrizione" type="text" size="30" name="descrizione" maxlength="500" value=""></input>
	  			</td>
			</tr>
			
			<tr style="display: none">
	  			<td id="td_campo_n_cu" nowrap class="formLabel">N°. C.U. Assegnati</td>
	  			<td>
					<input id="campo_n_cu" type="text" size="30" name="n_cu"  value=""></input><font color="red">*</font> <label id="label_intervallo_cu">Il valore deve essere compreso tra 0 e <%= request.getAttribute("n_CU_max") %></label> 
	  			</td>
			</tr>
			
			<tr style="display: none">
	  			<td id="td_campo_n_cu_campioni" nowrap class="formLabel">N°. C.U. Campioni</td>
	  			<td>
					<input id="campo_n_cu_campioni" type="text" size="30" name="n_cu_campioni"  value=""></input><font color="red">*</font> <label id="label_intervallo_cu_campioni">Il valore deve essere compreso tra 0 e <%= request.getAttribute("n_CU_campioni_max") %></label> 
	  			</td>
			</tr>
			
			<tr <%if(oNodoPadre.getN_livello()==2){%> style="display: none"<%} %>>
	  			<td id="td_campo_disabled_tipologia" nowrap class="formLabel">Struttura</td>
	  			<td>
	  				<%  String on_change1 = "onchange=\"onChangeASL();\""; %>
						<%  
						
						lookupTipologia.setJsEvent(on_change1); %>
	  			<%=lookupTipologia.getHtmlSelect("tipologia",4) %>
					
	  			</td>
			</tr>
			
			<tr>
	  			<td id="td_campo_responsabile" nowrap class="formLabel">Responsabile/direttore</td>
	  			<td>
	  			<%
	  			lookup_vUserInfo.addItem(-1,"SELEZIONA TIPOLOGIA");
	  			%>
	  			
					<%= lookup_vUserInfo.getHtmlSelect( "responsabile", -1) %>
				</td>
			</tr>
			
			<tr>
	  			<td id="td_campo_responsabile" nowrap class="formLabel">Delegato</td>
	  			<td>
	  			<%
	  			lookup_vUserInfoDelegati.addItem(-1,"SELEZIONA DELEGATO");
	  			%>
	  			
					<%= lookup_vUserInfoDelegati.getHtmlSelect( "delegato", oNodoPadre.getDelegato()) %>
				</td>
			</tr>
			
			
			<tr>
	  			<td id="td_campo_indirizzo" nowrap class="formLabel">Indirizzo</td>
	  			<td>
					<input id="campo_indirizzo" type="text" size="30" name="indirizzo"  value=""></label> 

					
	  			</td>
			</tr>
			
			<tr>
	  			<td id="td_campo_comune" nowrap class="formLabel">Comune</td>
	  			<td>
	  			<%lookup_comuni.setSelectSize(4);
	  			lookup_comuni.addItem(-1,"SELEZIONA COMUNE");
	  			%>
	  			<%=lookup_comuni.getHtmlSelect("comune",-1) %>
					
	  			</td>
			</tr>
			
			<tr>
	  			<td id="td_campo_mail" nowrap class="formLabel">Mail Certificata</td>
	  			<td>
	  			<input id="campo_mail" type="text" size="30" name="mail"  value=""></label> 
					
	  			</td>
			</tr>
			
		
	        		
		</table>
		
 		<input type="submit" value="Inserisci" onclick="javascript: return controllaForm()"/>
		<input type="button" value="Annulla" onclick="javascript:annulla()">
	</form>


</body>