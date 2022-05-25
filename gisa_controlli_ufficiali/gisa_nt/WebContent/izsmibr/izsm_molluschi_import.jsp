<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="com.sun.webkit.ContextMenu.ShowContext"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<dhv:container name="inviocamoll" selected="Invia" object="">
<%@ include file="../initPage.jsp"%>

<script>
function rimuoviFile(id){
	 document.getElementById("file"+id).value="";
	
}


$(function (){
    $('form[name=InvioMolluschi]').submit(function(e){
   	 
   	alert("Attenzione! L'invio potrebbe richiedere alcuni minuti");
   	
   	 var data = new FormData(jQuery('form')[0]);
//   	 var data =   $(this).serialize();
           
            e.preventDefault();
            $.ajax({
           	 
           	 url: 'GestioneInvioMolluschi.do?command=ImportMolluschi',
                type: 'POST',
                data: data ,//''+$(this).serialize(),
                async: true,
                cache: false,
                contentType: false,
                processData: false,
           	
                success: function(msg) {
                	if(msg=='Esecuzione')
                		alert("Attendere! E' in corso un precedente invio.");
                	else if(msg.indexOf('Errore_Tracciato_Record')>0)
                		alert(msg.replace("___Errore_Tracciato_Record___", ""));
                	else
                		alert("Esecuzione Invio Molluschi Terminata.");
            },
            error:function()
            {
            	
            }
          
    });
});
});



function apriTemplate()
{
	var urlTemplate = "izsmibr/template_invio_campioni.xls";
	window.open(urlTemplate);
}
</script>

<%=request.getAttribute("ImportKoError")!=null ? "<font color='red'>"+request.getSession().getAttribute("ImportKoError")+"</font>"  : ""%>

<%=request.getSession().getAttribute("MolluschiImport")!=null ? "<font color='red'>Attenzione E'' in corso un precedente invio Attendere Alcuni minuti..</font>"  : ""%>
<form method="post" id="InvioMolluschi" name="InvioMolluschi" action="GestioneInvioMolluschi.do?command=ImportMolluschi" enctype="multipart/form-data">
<table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
    <tr>
      <th colspan="2">
        <img border="0" src="images/file.gif" align="absmiddle"><b>File da Inviare</b>
      </th>
    </tr>
   
      <tr class="containerBody">
      	<td class="formLabel">
       		File
      	</td>
     	<td>
        	<input type="file" id="file1" name="file1" size="45"  required="required">  <a href="#" onclick="rimuoviFile(1); return false;"><img src="images/delete.gif"></a>
      		<%=showError(request, "ImportKoError") %>
      		<input type="button" value="file d'esempio" onclick="apriTemplate()"/>
      	</td>
      </tr>
  </table>
<input type ="submit" value="Invia File a BDN" onclick="">
</form>
</dhv:container>