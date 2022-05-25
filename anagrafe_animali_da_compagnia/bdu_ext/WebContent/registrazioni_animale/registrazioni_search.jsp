<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*" %>


<%@page import="org.aspcfs.modules.registrazioniAnimali.base.EventoRegistrazioneBDU"%><jsp:useBean id="eventiListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="specieList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="registrazioniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="annoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="daDefault" class="java.lang.String" scope="request"/>
<jsp:useBean id="aDefault" class="java.lang.String" scope="request"/>
<%@ include file="../initPage.jsp" %>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>

  <script language="JavaScript">
  function clearForm() {
    <%-- Account Filters --%>
   
    var frm_elements = searchRegistrazioni.elements;
    frm_elements.searchcodeId.value="";
    frm_elements.searchcodeidTipologiaEvento.value="-1";
    frm_elements.searchcodeidSpecieAnimale.value="-1";
    frm_elements.searchtimestampBDUda.value="";
    frm_elements.searchtimestampBDUa.value="";
    frm_elements.searchtimestampeventoda.value="";
    frm_elements.searchtimestampeventoa.value="";
 
   }
</script>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
</SCRIPT>
    
<script type="text/javascript">
function checkForm(form) {
	   
	 if (form.searchcodeidTipologiaEvento.value == '-1' && (form.searchtimestampeventoda.value != "" || form.searchtimestampeventoa.value != "") ) { 
	        alert("Se desideri specificare un intervallo per la data della registrazione, inserisci una tipologia di registrazione");
	        return false;
	 }

	 return true;

		
	  }

</script>

    
    <%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="RegistrazioniAnimale.do"><dhv:label name="">Registrazioni</dhv:label></a> > 
<dhv:label name="">Ricerca</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>


<form name="searchRegistrazioni" action="RegistrazioniAnimale.do?command=Search" method="post" onsubmit="javascript:return checkForm(this);">
<table cellpadding="2" cellspacing="2" border="0" width="100%">
	<tr>
		<td width="50%" valign="top">
<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong>Informazioni Registrazioni</strong>
				</th>
			</tr>
			<tr>
          <td class="formLabel">
            <dhv:label name="">Tipologia registrazione</dhv:label>
          </td>
          <td>
          <%=registrazioniList.getHtmlSelect("searchcodeidTipologiaEvento",  EventoRegistrazioneBDU.idTipologiaDB) %>
          </td>
        </tr>

        <tr>
          <td class="formLabel">
            <dhv:label name="">Identificativo registrazione</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchcodeId" value="<%= eventiListInfo.getSearchOptionValue("searchcodeId") %>">
          </td>
        </tr>
       
        <td class="formLabel">
            <dhv:label name="">Specie animale</dhv:label>
          </td>
          <td>
          <%=specieList.getHtmlSelect("searchcodeidSpecieAnimale",  eventiListInfo.getSearchOptionValue("searchcodeidSpecieAnimale")) %>
          </td>
        </tr>
        
        
	</table>
		
</td>

<td width="50%" valign="top">

<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong>Informazioni temporali</strong>
				</th>
			</tr>
	   <tr>
          <td class="formLabel">
            <dhv:label name="">Data inserimento in BDU</dhv:label>
          </td>
          <td>Da:
         <input  readonly type="text" id="searchtimestampBDUda" name="searchtimestampBDUda" size="10" value="<%= daDefault%>" nomecampo="searchtimestampBDUda" labelcampo="searchtimestampBDUda"/>&nbsp;
         <a href="#" onClick="cal19.select(document.forms[0].searchtimestampBDUda,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>&nbsp;&nbsp;
		A:
		<input  readonly type="text" id="searchtimestampBDUa" name="searchtimestampBDUa" size="10" value="<%=aDefault %>" nomecampo="searchtimestampBDUa" labelcampo="searchtimestampBDUa" />&nbsp;
         <a href="#" onClick="cal19.select(document.forms[0].searchtimestampBDUa,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
          </td>
       </tr>
       
       
       	<tr>
          <td class="formLabel">
            <dhv:label name="">Data della registrazione</dhv:label>
          </td>
          <td>Da:
         <input  readonly type="text" id="searchtimestampeventoda" name="searchtimestampeventoda" size="10" value="" nomecampo="searchtimestampeventoda" labelcampo="searchtimestampeventoda"/>&nbsp;
         <a href="#" onClick="cal19.select(document.forms[0].searchtimestampeventoda,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>&nbsp;&nbsp;
		A:
		<input  readonly type="text" id="searchtimestampeventoa" name="searchtimestampeventoa" size="10" value="" nomecampo="searchtimestampeventoa" labelcampo="searchtimestampeventoa" />&nbsp;
         <a href="#" onClick="cal19.select(document.forms[0].searchtimestampeventoa,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
          </td>
       </tr>


         
         
		
	</table>		
		
</td>
</tr>
</table>
<input type="hidden" name="popup" id="popup" value="">
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="source" value="searchForm">

</form>
</body>
