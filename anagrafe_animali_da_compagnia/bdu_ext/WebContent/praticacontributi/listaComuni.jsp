<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="java.util.*,org.aspcfs.modules.accounts.base.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.utils.web.*,org.aspcfs.modules.assets.base.*,org.aspcfs.modules.servicecontracts.base.*,java.text.DateFormat" %>

<%@page import="org.aspcfs.modules.praticacontributi.base.Pratica"%>
<jsp:useBean id="Asl" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="applicationPrefs" class="org.aspcfs.controller.ApplicationPrefs" scope="application"/>
<jsp:useBean id="pratica" class="org.aspcfs.modules.praticacontributi.base.Pratica" scope="request"/>
<jsp:useBean id="comuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ include file="../initPage.jsp" %>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popURL.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/dateControl.js"></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>

<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>
<SCRIPT>

function compareDates(date1,dateformat1,date2,dateformat2){
	var d1=getDateFromFormat(date1,dateformat1);
	var d2=getDateFromFormat(date2,dateformat2);
	if (d1==0 || d2==0) {
		return -1;
		}
	else if (d1 > d2) {
		return 1;
		}
	return 0;
	}

function doCheck(form) {
    if (form.dosubmit.value == "false") {     
      return true;
    } else {
      return(checkForm(form));
    }
   }


 function checkForm(form) {
    formTest = true;
    message = "";

    
    if (form.dataProroga.value==""){
    	 message += label("", "-E' obbligatorio settare la data della proroga\r\n");
	     formTest = false;
    }
       
    if (compareDates( form.dataProroga.value,"d/MM/y",form.oldData.value,"d/MM/y")<1) { 
   	 message += label("", "-La nuova data deve essere successiva a quella attuale\r\n");
	     formTest = false;
   }
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    else
    {
  	return true;
    }
  }

 function attivaProroga(){
  mostraBox('proroga');
	 }

 function confermaChiusura(){
	  mostraBox('chiusura');
		 }
	
 
 function mostraBox(id) {
	 $id(id).style.display = 'block';
}

 function $id(id) { 
		return (document.getElementById)? document.
		getElementById(id) : document.all[id];
	}
</SCRIPT>



<form name="" action="PraticaContributi.do" method="post">

<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">

<%
	int i=0;
	while(i<pratica.getComuniElencoNome().size())
	{
		String comune = pratica.getComuniElencoNome().get(i);
%>
		
		<tr class="containerBody">
			<td colspan="2"><%=comune%></td>
		</tr>
	
<%
	i++;
	}
%>

	
	

</table>

<br>

</form>



