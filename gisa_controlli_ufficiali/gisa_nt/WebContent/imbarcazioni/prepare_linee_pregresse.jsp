<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.opu.base.LineaProduttiva"%>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoStruttura" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ListaStati" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@page import="org.aspcfs.modules.lineeattivita.base.*"%>
<jsp:useBean id="idImpresa" class="java.lang.String" scope="request"/>

<%@ include file="../initPage.jsp"%>

<script src='javascript/modalWindow.js'></script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<script> function checkForm(form){
	 
		loadModalWindow();
		form.submit();
	}

</script>
	
	<table width="100%" style="border:1px solid black">
	<tr><th></th></tr>
	<tr><td>
	
<form name="searchAccount" id = "searchAccount" action="Imbarcazioni.do?command=UpdateLineePregresse" method="post">	
 <input type="hidden" name="orgId" id="orgId" value=<%=idImpresa%>>
  <table width="100%"><col width="50%">
    <tr><td valign="top">
    <table cellpadding='4' cellspacing="0" border="0" width="100%" class="details">
	  <tr><th colspan='2'>Vecchia Linea</th></tr>
      <tr>
	    <td nowrap class="formLabel">
      	  <dhv:label name="">Linea attuale</dhv:label>
		</td>
		<td>
	      	PRODUZIONE PRIMARIA -> IMBARCAZIONI
		</td>
  	  </tr>
  	
    </table>
	</td><td valign="top">
     <!-- LINEA ATTIVITA -->
       <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">    
         <tr><th colspan="2"><strong><dhv:label name="">Nuova Linea </dhv:label></strong> </th> </tr>
         <tr>
           <td nowrap class="formLabel" >
     	     <dhv:label name="">Linea</dhv:label>
   		   </td> 
    	   <td>   
            <input type="radio" name="codiceLinea" value="MS.000-MS.000.200-852IT1A101" checked>PRODUZIONE PRIMARIA -> IMBARCAZIONI -> NAVI DA PESCA REGISTRATE<br>
			<input type="radio" name="codiceLinea" value="MS.000-MS.000.200-852IT1A201">PRODUZIONE PRIMARIA -> IMBARCAZIONI -> NAVI PER LA RACCOLTA DI MOLLUSCHI BIVALVI
			</td>
  		 </tr>
       </table>
    </td>
    </tr>
	</table><br/>	
	

  <center>
  <input class="yellowBigButton" type="button" value="AGGIORNA LINEE" onClick="checkForm(this.form)"/>
  </center>	
			
</form>			
</td></tr></table>	
</dhv:evaluate>
	 
	













  	
   