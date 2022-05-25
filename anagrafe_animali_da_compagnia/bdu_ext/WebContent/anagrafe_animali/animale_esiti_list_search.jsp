<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*, org.aspcfs.modules.beans.ModuleBean" %>

<jsp:useBean id="EsitiLeishList" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AslList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="annoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="esitoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<%@ include file="../initPage.jsp" %>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
  
  <script language="JavaScript">
  function clearForm() {
    <%-- Account Filters --%>
   
    var frm_elements = searchRegistrazioni.elements;
   // frm_elements.searchcodeId.value="";
    //frm_elements.searchcodeidTipologiaEvento.value="-1";
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
form.searchcodeidTipologiaEvento.value=36;
</SCRIPT>

    
    <%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
<!--a href="RegistrazioniAnimale.do"><dhv:label name="">Registrazioni</dhv:label></a> --> 
<dhv:label name="">Ricerca esiti esami leishmaniosi</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
<% ModuleBean thisModule = (ModuleBean) request.getAttribute("ModuleBean");
String action = thisModule.getCurrentAction();
%>
<form name="searchRegistrazioni" action="<%=action%>.do?command=SearchEsitiLeish" method="post" onsubmit="return true;">
<table cellpadding="2" cellspacing="2" border="0" width="100%">
	<tr>
		<td width="50%" valign="top">
<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
       			<tr>
				<th colspan="2"><strong>Parametri ricerca</strong>
				</th>
			</tr>
<%--       <tr>
        <td class="formLabel">
            <dhv:label name="">Specie animale</dhv:label>
          </td>
          <td>
          <%=specieList.getHtmlSelect("searchcodeidSpecieAnimale",  EsitiLeishList.getSearchOptionValue("searchcodeidSpecieAnimale")) %>
          </td>
        </tr> --%>
        
       <tr>
        <td class="formLabel">
            <dhv:label name="">Valore esito</dhv:label>
          </td>
          <td>
          <%=esitoList.getHtmlSelect("searchcodeidEsito",  EsitiLeishList.getSearchOptionValue("searchcodeidEsito")) %>
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
            <dhv:label name="">Data esame</dhv:label>
          </td>
          <td>Da:
         <input  readonly type="text" id="searchtimestampdataInizioEsito" name="searchtimestampdataInizioEsito" size="10" value="" nomecampo="searchtimestampdataInizioEsito" labelcampo="searchtimestampdataInizioEsito"/>&nbsp;
         <a href="#" onClick="cal19.select(document.forms[0].searchtimestampdataInizioEsito,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>&nbsp;&nbsp;
		A:
		<input  readonly type="text" id="searchtimestampdataFineEsito" name="searchtimestampdataFineEsito" size="10" value="" nomecampo="searchtimestampdataFineEsito" 
		labelcampo="searchtimestampdataFineEsito" />&nbsp;
         <a href="#" onClick="cal19.select(document.forms[0].searchtimestampdataFineEsito,'anchor19','dd/MM/yyyy'); return false;" NAME="anchor19" ID="anchor19">
 		<img src="images/icons/stock_form-date-field-16.gif" border="0" align="absmiddle"></a>
          </td>
       </tr>
<tr>
        <td class="formLabel">
            <dhv:label name="">Asl</dhv:label>
          </td>
          <td>
          <%=AslList.getHtmlSelect("searchcodeidAsl",  EsitiLeishList.getSearchOptionValue("idAsl")) %>
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
