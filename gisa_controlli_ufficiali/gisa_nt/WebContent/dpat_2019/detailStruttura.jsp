<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.eclipse.jdt.internal.compiler.ast.ForeachStatement"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatSezione"%>
<%@page import="org.aspcfs.modules.dpat2019.base.DpatStrutturaIndicatore"%>
<%@page import="org.aspcfs.modules.dpat2019.base.*"%>
<%@ page import="com.darkhorseventures.framework.actions.ActionContext"%>
<jsp:useBean id="elenco" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="sezioni" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="dpat" class="org.aspcfs.modules.dpat2019.base.Dpat" scope="request"/>
<jsp:useBean id="ds" class="org.aspcfs.modules.dpat2019.base.DpatStruttura" scope="request"/>
<%@ include file="../initPage.jsp"%>


<script>
	function abilitaElemento(id){
		document.getElementById(id).style.display='block';
	}
	
	function aggiungiSommaUI(id,val){
		var txt = document.getElementById(id).innerHTML;
		if (txt.indexOf('SOMMA UI ATTIVIT&Agrave')<0){
			txt = txt+'<br><br><b>SOMMA UI ATTIVIT&Agrave '+val+"</b></br></br>";
			document.getElementById(id).innerHTML=txt;
		}
	}
	
	function aggiungiUI(id,val){
		var txt = document.getElementById(id).innerHTML;
		if (txt.indexOf('VALORE UI INDICATORE')<0){
			txt = txt+'<br><br><b>VALORE UI INDICATORE '+val+"</b></br></br>";
			document.getElementById(id).innerHTML=txt;
		}
	}
	
	function settaDescrizione(id,val){
		document.getElementById(id).innerHTML=val;
	}
</script>


<b><%=ds.getDescrizione_lunga().toUpperCase()%></br>
CARICO DI LAVORO EFFETTIVO ANNUALE MINIMO DI STRUTTURA IN U.I. = <%=request.getAttribute("carico")%></b>
<br><br>


<% 
	for(int i=0; i<sezioni.size(); i++) {
	DpatSezione sez = (DpatSezione)sezioni.get(i);%>
  	<table id="s_<%=sez.getId()%>" border="1" style="display:none">
  	<tr><td id="s_<%=sez.getId()+"_d"%>"><%=sez.getDescription().toUpperCase()%></td></tr>
  		<% for(int j=0;j<sez.getElencoPiani().size();j++){ 
  			DpatPiano piano = sez.getElencoPiani().get(j); %>
  			<tr id="s_<%=sez.getId()%>_p_<%=piano.getId()%>" style="display:none">
  				<td id="s_<%=sez.getId()%>_p_<%=piano.getId()+"_d"%>"><%=piano.getDescription()%></td>
  				<td><table border="1">
  				<% for(int k=0;k<sez.getElencoPiani().get(j).getElencoAttivita().size();k++) {
  					DpatAttivita attivita = sez.getElencoPiani().get(j).getElencoAttivita().get(k);%>
  					<tr id="s_<%=sez.getId()%>_p_<%=piano.getId()%>_a_<%=attivita.getId()%>" style="display:none">
  						<td id="s_<%=sez.getId()%>_p_<%=piano.getId()%>_a_<%=attivita.getId()+"_d"%>"><%=attivita.getDescription()%></td>
  						<td><table border="1">
  							<% for(int l=0;l<sez.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().size();l++){ 
  								DpatIndicatore indicatore = sez.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().get(l);%>
  								<tr id="s_<%=sez.getId()%>_p_<%=piano.getId()%>_a_<%=attivita.getId()%>_i_<%=indicatore.getId()%>" style="display:none">
  									<td id="s_<%=sez.getId()%>_p_<%=piano.getId()%>_a_<%=attivita.getId()%>_i_<%=indicatore.getId()+"_d"%>"><%=indicatore.getDescription()%></td>
  								</tr>
  							<%} %>
  						</table></td>
  					</tr>
  				<%}%></table></td>
  			</tr>
  		<%} %>
	</table>
	<br><br>
<% } %>

<% for (int i=0;i<elenco.size();i++){
	DpatStrutturaIndicatore dsi = (DpatStrutturaIndicatore)elenco.get(i);%>
	<script>
		<% String s = dsi.getDescrSezione().replaceAll("'", " ");%>
		var descr='<%=s%>';
		abilitaElemento('<%="s_"+dsi.getIdSezione()%>');
		settaDescrizione('<%="s_"+dsi.getIdSezione()+"_d"%>',descr);
		
		<%s = dsi.getDescrPiano().replaceAll("'", " ");%>
		descr='<%=s%>';
		abilitaElemento('<%="s_"+dsi.getIdSezione()+"_p_"+dsi.getIdPiano()%>');
		settaDescrizione('<%="s_"+dsi.getIdSezione()+"_p_"+dsi.getIdPiano()+"_d"%>', descr);
		
		<%s = dsi.getDescrAttivita().replaceAll("'", " ");%>
		descr='<%=s%>';
		abilitaElemento('<%="s_"+dsi.getIdSezione()+"_p_"+dsi.getIdPiano()+"_a_"+dsi.getIdAttivita()%>');
		settaDescrizione('<%="s_"+dsi.getIdSezione()+"_p_"+dsi.getIdPiano()+"_a_"+dsi.getIdAttivita()+"_d"%>', descr);
		aggiungiSommaUI('<%="s_"+dsi.getIdSezione()+"_p_"+dsi.getIdPiano()+"_a_"+dsi.getIdAttivita()+"_d"%>','<%=dsi.getSomma_ui()%>');

		<%s = dsi.getDescrIndicatore().replaceAll("'", " ");%>
		descr='<%=s%>';
		abilitaElemento('<%="s_"+dsi.getIdSezione()+"_p_"+dsi.getIdPiano()+"_a_"+dsi.getIdAttivita()+"_i_"+dsi.getIdIndicatore()%>');
		abilitaElemento('<%="s_"+dsi.getIdSezione()+"_p_"+dsi.getIdPiano()+"_a_"+dsi.getIdAttivita()+"_i_"+dsi.getIdIndicatore()+"_d"%>');
		settaDescrizione('<%="s_"+dsi.getIdSezione()+"_p_"+dsi.getIdPiano()+"_a_"+dsi.getIdAttivita()+"_i_"+dsi.getIdIndicatore()+"_d"%>', descr);
		aggiungiUI('<%="s_"+dsi.getIdSezione()+"_p_"+dsi.getIdPiano()+"_a_"+dsi.getIdAttivita()+"_i_"+dsi.getIdIndicatore()+"_d"%>','<%=dsi.getUi()%>');

	</script>
<% } %> 



<%-- 
<table border="1">
<tr>
<% 
	for(int i=0; i<sezioni.size(); i++) {
		DpatSezione sez = (DpatSezione)sezioni.get(i);%>
		<td><%=sez.getDescription()%><br>
			<table border="1">
				<tr>
				<% for(int j=0;j<sez.getElencoPiani().size();j++){ 
					DpatPiano piano = sez.getElencoPiani().get(j);%>
					<td><%=piano.getDescription()%><br>
						<table border="1">
							<tr>
								<% for(int k=0;k<sez.getElencoPiani().get(j).getElencoAttivita().size();k++) {
									DpatAttivita attivita = sez.getElencoPiani().get(j).getElencoAttivita().get(k);%>
									<td><%=attivita.getDescription()%><br>
										<table border="1">
											<tr>
												<% for(int l=0;l<sez.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().size();l++){
													DpatIndicatore indicatore = sez.getElencoPiani().get(j).getElencoAttivita().get(k).getElencoIndicatori().get(l);%>
													<td><%=indicatore.getDescription()%></td><br>
													<input type="text" value="0"/>
												<%} %>
											<%} %>
											</tr>
										</table>
									</td>
								<%} %>
							</tr>
						</table>	
					</td>
				<%} %>
				</tr>
			</table>
		</td>
	<%}%>
</tr>
</table>

 --%>


<b>SALDO = <%=request.getAttribute("saldo")%></b><br><br>
<input type="button" value="indietro" onclick="window.location='dpat2019.do?command=AddDatiPerStruttura&idAsl=<%=ds.getIdAsl()%>&idStrutt=<%=ds.getId()%>'"/>
