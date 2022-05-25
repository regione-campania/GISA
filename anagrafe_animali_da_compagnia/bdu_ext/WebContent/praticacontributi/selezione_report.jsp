<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,java.text.DateFormat"%>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="aslRifList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@page import="org.aspcfs.modules.praticacontributi.base.PraticaList"%>
<%@page import="org.aspcfs.modules.praticacontributi.base.Pratica"%>
<script language="JavaScript" TYPE="text/javascript" SRC="dwr/interface/PraticaList.js"> </script>
<script>

var array_pratiche = new Array();


function valorizzaLista(listaPratiche)
{

  var select = document.forms['reportpraticacontributi'].selectProgetto; //Recupero la SELECT

  i = 0;

  //Azzero il contenuto della seconda select
  for (var j = select.length - 1; j >= 0; j--)
  	  	select.remove(j);

	 var NewOpttmp = document.createElement('option');
	 NewOpttmp.value=-1;
	 NewOpttmp.text=" -- Nessuna Pratica --";
	 try{
	 select.add(NewOpttmp, null); //Metodo Standard, non funziona con IE
	 }
	 catch(e){
		 select.add(NewOpttmp); 
	 }	
  while(i < listaPratiche.length){
		
			 array_pratiche[listaPratiche[i].id]=listaPratiche[i];

			 var NewOpt = document.createElement('option');
			 NewOpt.value = listaPratiche[i].id;
		 	 NewOpt.text =  "Decreto numero "+listaPratiche[i].numero_decreto_pratica + " del "+ listaPratiche[i].data_decreto_stringa +" - N° cani padronali restanti "+ listaPratiche[i].cani_restanti_padronali +" - N° cani catturati restanti "+listaPratiche[i].cani_restanti_catturati + " - "+ listaPratiche[i].elenco_comuni;

			//controllo dell'id selezionato nel caso di salva e clona
			 if(document.reportpraticacontributi.oldPratica.value == NewOpt.value){
			 	NewOpt.selected = true;
			 }
			 
			  //Aggiungo l'elemento option
			    try
			    {
			  	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
			    }catch(e){
			  	  select.add(NewOpt); // Funziona solo con IE
			    }
			    i++;						   
	}
    
    if (select.length>0){
        document.getElementById("elenco_progetti").style.visibility="visible";
		}
    
    }


function visualizzaPratiche(i)
{

			//estrae tutte le pratiche per cui esiste aleno una posizione aperta
			PraticaList.getListReport(i,document.reportpraticacontributi.aslRif.value,valorizzaLista);
  
}
</script>

<body <%if ((User.getRoleId()!=24) && (User.getAslRifId()>0)) { %> onload="visualizzaPratiche(3);"<%} %> >

	<form method="post" name="reportpraticacontributi" action="ReportPraticaContributi.do?command=AvviaEstrazione">
		<table class="trails" cellspacing="0">
			<tr>
				<td width="100%">
					  <a href="">Report Pratiche Contributi Sterilizzazione</a> > Estrazione
				</td>
			</tr>
		</table>
		
		
		
		<table class="details"  cellspacing="0" cellpadding="4" border="0" width="100%">
			<tr>
			
				<td nowrap class="formLabel">
           		   <dhv:label name="">Asl di Riferimento</dhv:label>
           		</td>
			
				<td>
					
					<dhv:evaluate if="<%=(User.getRoleId()!= 24) %>">
						<%--  utente dotato di asl con ruolo diverso da veterinario privato--%>
						<dhv:evaluate if="<%=(User.getAslRifId()> 0) %>">
							<%= User.getAslRif() %>
							<input type="hidden" name="aslRif" id="aslRif" value="<%=User.getAslRifId() %>" />
						</dhv:evaluate>
		
						<%--utente amministratore --%>
						<dhv:evaluate if="<%=(User.getAslRifId()<= 0) %>">
						   	<%aslRifList.setJsEvent("onChange=\"visualizzaPratiche(3);\"");%>
  							<%= aslRifList.getHtmlSelect("aslRif",-1) %>
  						</dhv:evaluate>
					</dhv:evaluate>
				
				</td>
            </tr>
            <tr>
            	<td nowrap class="formLabel">
           		   <dhv:label name="">stato progetti</dhv:label>
           		</td>
           		<td>
           		  <div id = "statoprogetti" >
	            	<input type="radio" name="stato" value= "1" onchange="visualizzaPratiche(1);"> Aperti
    	        	<input type="radio" name="stato" value= "2" onchange="visualizzaPratiche(2);"> Chiusi
        	    	<input type="radio" name="stato" value= "3" checked="checked" onchange="visualizzaPratiche(3);"> Tutti
        	  	</div>
        		</td>
			
            </tr>
            <tr>
            <td nowrap class="formLabel">
           		   <dhv:label name="">elenco progetti</dhv:label>
           	</td>
            <td>
            	 <div id ="elenco_progetti" name="elenco_progetti"  style="visibility: hidden">
      	 			<input type = "hidden" name = "oldPratica" value="<%= request.getParameter("selectProgetto") != null ? request.getParameter("selectProgetto") : "" %>">
		    			<select name="selectProgetto" id="selectProgetto" title="Selezionare un progetto" >
						<option value="-1">-- Nessun Progetto --</option>
						</select>
			
      	 		</div>
      	 	</td>
            </tr>
           
              
   	</table>
		<br />
	<%--	<input type="button" value="Estrazione" onclick="this.form.dosubmit.value='true';if(doCheck(this.form)){this.form.submit()};" />--%> 
		<input type="button" value="Estrazione" onclick="this.form.submit();" />
		 <input type="hidden" name="dosubmit" value="true" />
	</form>
</body>