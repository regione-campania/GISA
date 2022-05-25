<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>

<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>	

<%@ include file="../initPage.jsp"%>

<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>
<script language="JavaScript" TYPE="text/javascript"
	SRC="dwr/interface/Operatore.js"> </script>

<script type="text/javascript">

var array_canili = new Array();

function visualizzaListaCanili()
{
					
			//estrae tutte i canili
			var idAsl = -1;
			if(document.selcanile.aslRif!=null)
					idAsl = document.selcanile.aslRif.value;
			Operatore.getListaDWR(idAsl,5, <%=User.getUserRecord().getIdLineaProduttivaRiferimento()%>, valorizzaListaCanili);

  
}


function valorizzaListaCanili(listaCanili)
{

  var select = document.forms['selcanile'].selectCanili; //Recupero la SELECT

  i = 0;
  //indice utilizzato per i canili
  k = 0;

  //Azzero il contenuto della seconda select
  for (var j = select.length - 1; j >= 0; j--)
  	  	select.remove(j);

	 var NewOpttmp = document.createElement('option');
	 NewOpttmp.value=-1;
	 NewOpttmp.text=" -- Nessuna Canile --";
	 try{
	 select.add(NewOpttmp, null); //Metodo Standard, non funziona con IE
	 }
	 catch(e){
		 select.add(NewOpttmp); 
	 }	

	// alert(listaCanili.length);
  while(i < listaCanili.length){

	 // alert(listaCanili[0][0]); rag sociale

			 array_canili[listaCanili[i][1]]=listaCanili[i][0];

			 var NewOpt = document.createElement('option');
			 NewOpt.value =listaCanili[i][1];
			 //alert(NewOpt.value);
		 	 NewOpt.text =  ""+listaCanili[i][0];
			
			  //Aggiungo l'elemento option
			    try
			    {
			  	  select.add(NewOpt, null); //Metodo Standard, non funziona con IE
			    }catch(e){
			  	  select.add(NewOpt); // Funziona solo con IE
			    }
			    i++;						   
	}
    
    
    }


function checkForm(form) {
	 
	//verificaContributo();
	
  	//checkMorsicatore();
   

formTest = true;
message = "";
    if ( form.selectCanili.value == '-1'){

 
    		   	message += label("","- Seleziona un canile \r\n");
		       	formTest = false;
	    }   

    if (formTest == false) {
        alert(label("check.form", "Attenzione:\r\n\r\n") + message);
        return false;
      }
      else
      {
      	
      	
      	return true;
      }	
    }
  
function checkFormLeishmania(form) {
	 
	//verificaContributo();
	
  	//checkMorsicatore();
   

formTest = true;
message = "";
    if ( form.selectCanili.value == '-1'){

 
    		   	message += label("","- Seleziona un canile \r\n");
		       	formTest = false;
	    }   

    if (formTest == false) {
        alert(label("check.form", "Attenzione:\r\n\r\n") + message);
        return false;
      }
      else
      {
      	document.getElementById("leish").value = "true";
      	return true;
      }	
    }
  
   
</script>
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0"> 
<tr>
<td width="100%">
  <a href="BarcodeCanili.do"><dhv:label name="barcode.canili">Barcode cani in canile</dhv:label></a> >
  <dhv:label name="barcode.canili.lista">Canili</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<body>

<form name="selcanile" id="selcanile" method="POST" action="BarcodeCanili.do?command=ListaCani&auto-populate=true<%=addLinkParams(request, "popup|popupType|actionId")%>">
		<table class="details"  cellspacing="0" cellpadding="4" border="0" width="100%">
			<tr>
			
				<td nowrap class="formLabel">
           		   <dhv:label name="">Asl di Riferimento</dhv:label>
           		</td>
			<td>
			<%--utente veterinario LP O AMMINISTRATORE--%>
			<dhv:evaluate if="<%=(User.getRoleId()== 24 || User.getRoleId()== 0) %>">
			    <% AslList.setJsEvent("onChange=\"javascript:visualizzaListaCanili();\"");%>
  				<%= AslList.getHtmlSelect("aslRif", User.getSiteId()) %>
  			</dhv:evaluate>
		
			<dhv:evaluate if="<%=(User.getRoleId()!= 24) %>">
					<%--  utente dotato di asl con ruolo diverso da veterinario privato--%>
					<dhv:evaluate if="<%=(User.getSiteId()> 0) %>">
						<%= AslList.getSelectedValue(User.getSiteId()) %>
						<input type="hidden" name="aslRif" id="aslRif" value="<%=User.getSiteId() %>" />
					</dhv:evaluate>
			</dhv:evaluate>

			
				
  				</td>
            </tr>
            
			<tr>
				<td nowrap class="formLabel"> Canili </td>
				<td>
						<select name="selectCanili" id="selectCanili" title="Selezionare un canile" >
						<option value="-1">-- Nessun Canile --</option>
						</select>
			
				</td>
			</tr>
		</table>
</br></br>
	<input type="button" value="Estrazione di tutti gli animali presenti" onClick="if(checkForm(this.form)){this.form.submit()};" />
	
	<input type="hidden" id="leish" name="leish" value=""/>
	<input type="button" style="background-color: green !important;" value="Estrazione per piano Leishmania" onClick="if(checkFormLeishmania(this.form)){this.form.submit()};" />
</form>

<script>
if ( <%=User.getRoleId()==31%> || document.selcanile.aslRif.value > -1 ){
	visualizzaListaCanili();
}

</script>