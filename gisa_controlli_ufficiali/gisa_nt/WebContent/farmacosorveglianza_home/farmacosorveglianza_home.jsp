<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%--Pagina JSP creata da Alberto --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>

<%@ include file="../initPage.jsp" %>
<body>

<table class="trails" >
	<tr > 
		<td width="100%">
			Seleziona operazione 
		</td> 
	</tr>
</table>

<form name="scelta" action="" method="post">
	<table >
	  <tr>
	    <td>
	    	<input	type="submit"
      				name="Farmacie/Grossisti"
	      			value="Farmacie/Grossisti"
    	  			onclick="javascript:this.form.action='Farmacosorveglianza.do?command=SearchFormFcie'"
      		/>
	    </td>
	  
	    <td>
	    	<input type="submit" 
	    		   name="Parafarmacie/Farmacie" 
	    		   value="Parafarmacie/Farmacie"
    	  		   onclick="javascript:this.form.action='Parafarmacie.do?command=SearchFormFcie'"
        	/>
	    </td>
	  </tr>
	
	</table>
</form>

<br/>

</body>