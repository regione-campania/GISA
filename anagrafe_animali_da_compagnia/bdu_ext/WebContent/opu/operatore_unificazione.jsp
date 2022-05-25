<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.*"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/taglib/systemcontext-taglib.tld" prefix="sc"%>
<%@ include file="../initPage.jsp"%>
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<jsp:useBean id="Proprietario1"
	class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="Proprietario2"
	class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
	
<script language="Javascript">
function doCheck(form) {
    if (form.dosubmit.value == "false") {     
      return true;
    } else {
      return(checkForm(form));
    }
   }
   
   
   
function checkForm(form) {
	 
	//verificaContributo();
	
  	//checkMorsicatore();
  	formTest = true;
	message = "";


    //controllo sul proprietario se non è stato selezionato
    if (form.idProprietario.value == "-1") { 
        message += label("","- Il primo proprietario è un informazione richiesta\r\n");
              formTest = false;
  
    }
    
    if (form.idDetentore.value == "-1") { 
        message += label("","- Il secondo proprietario è un informazione richiesta\r\n");
              formTest = false;
  
    }
    
    if ((form.idProprietario.value != "-1" && form.idDetentore.value != "-1") && (form.idProprietario.value == form.idDetentore.value) ) { 
        message += label("","- I due proprietari devono corrispondere a due soggetti diversi\r\n");
              formTest = false;
  
    }
    
    
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    
    

    else
    {
    	form.doContinue.value='true'
    	loadModalWindow(); //ATTENDERE PREGO
    	return true;
    }
	
  }
</script>
<form  name="unificaAnimale"
	action="OperatoreAction.do?command=UnificazioneProprietari&auto-populate=true&<%=addLinkParams(request, "popup|popupType|actionId")%>"
	method="post">
<table>
<tr><td><a onclick="window.open('OperatoreAction.do?command=SearchForm&tipologiaSoggetto=1&popup=true&operazioneUnificazione=true','','width=800,height=600');return false;"
							href="OperatoreAction.do?command=SearchForm&tipologiaSoggetto=1&popup=true&operazioneUnificazione=true">Seleziona il proprietario da eliminare</a></td><td><dhv:evaluate if="<%=(Proprietario1 != null && Proprietario1.getIdOperatore() > 0 )%>"><%=Proprietario1.getRagioneSociale() %></dhv:evaluate></td></tr>
							
<tr><td><a onclick="window.open('OperatoreAction.do?command=SearchForm&tipologiaSoggetto=2&popup=true&operazioneUnificazione=true','','width=800,height=600');return false;"
							href="OperatoreAction.do?command=SearchForm&tipologiaSoggetto=2&popup=true&operazioneUnificazione=true">Seleziona il proprietario in sostituzione</a></td><td><dhv:evaluate if="<%=(Proprietario2 != null && Proprietario2.getIdOperatore() > 0 )%>"><%=Proprietario2.getRagioneSociale() %></dhv:evaluate></td></tr>		

<%
int idProprietario = -1;
int idDetentore = -1;
if (Proprietario1 != null && Proprietario1.getIdOperatore() > 0){
	Stabilimento stab = (Stabilimento) Proprietario1.getListaStabilimenti().get(0);
	LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
	idProprietario = lp.getId();
}

if (Proprietario2 != null && Proprietario2.getIdOperatore() > 0){
	Stabilimento stab = (Stabilimento) Proprietario2.getListaStabilimenti().get(0);
	LineaProduttiva lp = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
	idDetentore = lp.getId();
}
	
	%>
	
</table>
	
	
	<input type="button"
		value="<dhv:label name="global.button.save">Save</dhv:label>"
		onClick="this.form.dosubmit.value='true';if(doCheck(this.form)){this.form.submit()};" />
<input type="hidden" name="idProprietario" id="idProprietario" value="<%=idProprietario%>"/>
<input type="hidden" name="idDetentore" id="idDetentore" value="<%=idDetentore%>"/>
<input type="hidden" name="doContinue" id="doContinue" value=""/>
<input type="hidden" name="dosubmit" id="dosubmit" value="false"/>
</form>
</body>