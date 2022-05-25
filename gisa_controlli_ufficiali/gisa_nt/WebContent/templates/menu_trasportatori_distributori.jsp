<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<style>
.selected{
background-color: #4e5873;
}
</style>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<%@ include file="../initPage.jsp" %>
<%

%>

<div id="sidebar-left">

	<div class="mymoduletable">		
		
		<p id="p3_my"> <%= "STABILIMENTO NUM. "+ toHtml(User.getContact().getNumRegistrazioneStabilimentoAssociato().toUpperCase()) %> 
			<br><br>							
			
			
			<br>						
				<br>
			<br>			
		</p>


	<ul id="qm0" class="qmmc">
	<li>
		<a onclick="loadModalWindow();" href="OpuStab.do?command=MyHomePage" accesskey="2"><span>Home</span></a>
	</li>
	
<li>
		<a onclick="loadModalWindow()" href="Login.do?command=Logout" accesskey="2"><span>Esci da Gisa Ext</span></a>
	</li>
	
	<li>
		<a href="man/ManualeOperativoSUAPEstensioni.pdf" style="color:black">Manuale utente</a>
	</li>
	
   </ul>
   
   <p id="p3_my"> <br/>Help Desk:<br/>0810116436 / 0810116437<br/> <br/>  
   </p>
   
</div>
<img id="finemenu" src="css/suap/images/finemenu.jpg"/>	

</div>
	
				
			
			
			
			
	
			