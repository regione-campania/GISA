<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%@page import="java.util.Vector"%>
<%@page import="org.aspcfs.modules.meeting.base.Rilascio"%>
<jsp:useBean id="Rilascio" class="org.aspcfs.modules.meeting.base.Rilascio" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="listaContesti" class="org.aspcfs.utils.web.LookupList" scope="request"/>



<link rel="stylesheet" href="css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="css/jquery.calendars.picker.css">
<link href="javascript/datepicker/jquery.datepick.css" rel="stylesheet">
<script type="text/javascript" src="javascript/jquery.calendars.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.plus.js"></script>
<script type="text/javascript" src="javascript/jquery.plugin.js"></script>
<script type="text/javascript" src="javascript/jquery.calendars.picker.js"></script>
<script src="javascript/parsedate.js"></script>
<script src="javascript/jquery-ui.js"></script>
<script src="javascript/datepicker/jquery.plugin.js"></script>
<script src="javascript/datepicker/jquery.datepick.js"></script>

<%@ include file="../initPage.jsp" %>



<script>
$(function() {
	$('#dataFrom').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: 0,  showOnFocus: false, showTrigger: '#calImg'});
	$('#dataTo').datepick({dateFormat: 'dd/mm/yyyy',  maxDate: 0,  showOnFocus: false, showTrigger: '#calImg'});
});



</script>
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="GestioneRiunioni.do?command=SearchForm">Gestione Riunioni</a> > 
Ricerca
</td>
</tr>
</table>



<form action="GestioneRiunioni.do?command=SearchRilasci" method="post">
 <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
 <col width="10%">
        <tr>
          <th colspan="2">
            Ricerca rilascio
          </th>
        </tr>
        
        <tr >
          <td class="formLabel">	
           Data Da
          </td>
          <td>
          <input type = "text" id = "dataFrom" name = "searchtimestampDataFrom">
          <div style="display: none;"> 
            &nbsp;&nbsp;<img id="calImg" src="images/cal.gif" alt="Popup" class="trigger"> 
        </div>
          </td>
        </tr>
          <tr >
          <td class="formLabel">	
           Data A
          </td>
          <td>
          <input type = "text" id = "dataTo" name = "searchtimestampDataTo">
          </td>
        </tr>
        
       <tr>
      <td nowrap class="formLabel">
        Contesto
      </td>
      <td>
     
        <%=listaContesti.getHtmlSelect("searchcodeidContesto", -1) %>
       
      </td>
    </tr>
    
    
        <tr >
          <td class="formLabel">	
           Oggetto
          </td>
          <td>
          <input type = "text" name = "searchOggetto">
          </td>
        </tr>
        
         <tr >
          <td class="formLabel">	
           Modulo/Note
          </td>
          <td>
          <input type = "text" name = "searchAltro">
          </td>
        </tr>
        
</table>
<input type = "submit" value = "Ricerca">
</form>
       
       
      
	