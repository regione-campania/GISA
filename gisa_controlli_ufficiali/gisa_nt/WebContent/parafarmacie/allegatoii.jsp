<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.modules.login.beans.UserBean"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.modules.parafarmacie.base.RigaAllegatoI"%>
<%@page import="org.aspcfs.modules.parafarmacie.base.RigaAllegatoII"%>
<%@ include file="../initPage.jsp" %>
<jsp:useBean id="RigaAllegatoII" class="org.aspcfs.modules.parafarmacie.base.RigaAllegatoII" scope="request"/>
<script>
function calcolaTotale()
{

	
	a = document.allegato2.animali_reddito_tot_a.value;
	b = document.allegato2.mangimi_medicati_prod_intermedi_tot_b.value;
	c = document.allegato2.scorte_vet_tot_c.value;
	d = document.allegato2.scorte_allev_tot_d.value;
	document.allegato2.totale.value = parseInt(a)+parseInt(b)+parseInt(c)+parseInt(d);
}

function check(form,stato)
{
flag = true ;
if (form.animali_reddito_art_4_5.value == "" || isNaN(form.animali_reddito_art_4_5.value))
{
	flag = false ;
}
if (form.animali_reddito_art_11.value == "" || isNaN(form.animali_reddito_art_11.value))
{
	flag = false ;
}
if (form.animali_reddito_tot_a.value == "" || isNaN(form.animali_reddito_tot_a.value)) 
{
	flag = false ;
}
if (form.mangimi_medicati_prod_intermedi_art_16.value == "" || isNaN(form.mangimi_medicati_prod_intermedi_art_16.value))
{
	flag = false ;
}
if (form.mangimi_medicati_prod_intermedi_tot_b.value == "" || isNaN(form.mangimi_medicati_prod_intermedi_tot_b.value))
{
	flag = false ;
}
if (form.scorte_vet_art_84.value == "" || isNaN(form.scorte_vet_art_84.value))
{
	flag = false ;
}
if (form.scorte_vet_tot_c.value == "" || isNaN(form.scorte_vet_tot_c.value))
{
	flag = false ;
}
if (form.scorte_allev_da_reddito.value == "" || isNaN(form.scorte_allev_da_reddito.value))
{
	flag = false ;
}
if (form.scorte_allev_da_compagnia.value == "" || isNaN(form.scorte_allev_da_compagnia.value))
{
	flag = false ;
}
if (form.scorte_allev_da_ippodromi.value == "" || isNaN(form.scorte_allev_da_ippodromi.value))
{
	flag = false ;
}
if (form.scorte_allev_tot_d.value == "" || isNaN(form.scorte_allev_tot_d.value))
{
	flag = false ;
}

if (form.bovina.value == "" || isNaN(form.bovina.value))
{
	flag = false ;
}
if (form.suina.value == "" || isNaN(form.suina.value))
{
	flag = false ;
}
if (form.avicola.value == "" || isNaN(form.avicola.value))
{
	flag = false ;
}
if (form.ovicaprina.value == "" || isNaN(form.ovicaprina.value))
{
	flag = false ;
}
if (form.acquicoltura.value == "" || isNaN(form.acquicoltura.value))
{
	flag = false ;
}
if (form.apiario.value == "" || isNaN(form.apiario.value))
{
	flag = false ;
}
if (form.cunicola.value == "" || isNaN(form.cunicola.value))
{
	flag = false ;
}
if (form.equina.value == "" || isNaN(form.equina.value))
{
	flag = false ;
}
if (form.bufalina.value == "" || isNaN(form.bufalina.value))
{
	flag = false ;
}



if (flag==false)
{
	document.getElementById('msgerror').style.display="";
	if (document.getElementById('msgok'))
		document.getElementById('msgok').style.display="none";
	
}
else
{
	
	document.getElementById('msgerror').style.display="none";
	document.allegato2.stato.value =stato;
	
	document.allegato2.submit();
	
}
return flag;
}
</script>
<%

LookupList asl = (LookupList)request.getAttribute("aslList");
HashMap<Integer,String> aslListMap = (HashMap<Integer,String>)request.getAttribute("aslListMap");
LookupList righeAllegato = (LookupList)request.getAttribute("righeAllegato");
%>	
<table class="trails" cellspacing="0">
<tr>
<td>
<a href="Parafarmacie.do?command=SearchFormFcie"><dhv:label name="">Farmacie / Grossisti / Parafarmacie</dhv:label></a> > 
<a href="Parafarmacie.do?command=SelectAllegatoII"><dhv:label name="">Allegato II</dhv:label></a> >
SCHEDA PRESCRIZIONI FARMACO VETERINARIO

</td>
</tr>
</table>
<br>

		<%
		UserBean user =(UserBean)session.getAttribute("User");
		%>
	
		<%if (user.getSiteId()!=-1)
		{
			boolean readonly1 = false;
			if (RigaAllegatoII.getStato()==1)
				readonly1 = true ;
			boolean readonly = true;
			if (request.getAttribute("salvataggio")!=null)
			{
				%>
					<label style="display: block" id = "msgok"><font color = "green">I dati Sono stati Salvati Correttamente</font></label>
				<%
				}
			%>
		
		<label style="display: none" id = "msgerror"><font color = "red">Attenzione controlla che i dati inseriti siano corretti</font></label>
		<form method="post" name="allegato2" action = "Parafarmacie.do?command=AllegatoII&asl=<%=user.getSiteId() %>&anno=<%=request.getAttribute("anno") %>" ">
		<table>
		<tr>
		<td>
		<table align="left">
			<tr><td><a href = "Parafarmacie.do?command=StampaAllegatoII&asl=<%=user.getSiteId() %>&anno=<%=request.getAttribute("anno") %>">Stampa Report</a></td></tr>
			</table>
			
			
			<br/>
			<table align="left">
		<tr><td colspan="2">
		<h2><center>Allegato II</center></h2> <br>
		</td></tr>
		</table>
		<br><br><br>
		<table align="left">
		<tr style= "background-color: #E3E3E3">
		<td colspan="118">
		
		<table align = "left" width="100%" border="1px;">
		
		<tr><td colspan="2">
		<h3><center>SCHEDA PRESCRIZIONI FARMACO VETERINARIO </center></h3>
		</td></tr>
		<tr><td colspan="1"><b>REGIONE: CAMPANIA</br>
		ANNO: <%=request.getAttribute("anno") %></br>
		ENTE: <%=asl.getSelectedValue(Integer.parseInt((String)request.getAttribute("idAsl"))) %></b></td>
		<td>
		
		</td></tr>		
		</table>
		
		</td>
		</tr>
		<tr>
		<td>
		<table border="1" width="40%">
		<tr style= "background-color: #E3E3E3">
		<th  width="15">&nbsp;</th>
		<th colspan="3" width="15"><h3>PER ANIMALI DA <br>REDDITO</h3></th>
		<th colspan="3" width="15"><h3>PER MANGIMI MEDICATI E <br>PRODOTTI INTERMEDI</h3></th>
		<th colspan="2" width="10"><h3>PER SCORTE PROPRIE DEL VETERINARIO<br> (ambulatori, cliniche e attività zooiatrica)</h3></th>
		<th colspan="4" width="20"><h3>PER SCORTE DI IMPIANTO di<br> allevamento e custodia di animali</h3></th>
		<th colspan="4" width="20"><h3>Totale </h3></th>
		</tr>
		
		
		<tr>
		<th>&nbsp;</th>
		<th width="8">
		<b>D.lgs 158/2006 (artt. 4 e 5)</b>
		</th>
		<th width="8">
		<b>D.lgs 193/2006 (art. 11)</b>
		</th>
		<th width="8">
		<b>Tot A</b>
		</th>
		<th width="8">
		<b>D.lgs 90/93 (art. 3 c. 4</b>
		</th>
		<th width="8">
		<b>D.M. 16/11/93 (art. 16c.1)</b>
		</th>
		<th width="8">
		<b>Tot B</b>
		</th>
		<th width="8">
		<b>D.lgs 193/2006 (art. 84 comma 7</b>
		</th>
		<th width="8">
		<b>Tot C</b>
		</th>
		<th width="8">
		<b>Da Reddito</b>
		</th>
		<th width="8">
		<b>Da compagnia</b>
		</th>
		<th width="8">
		<b>ippodromi,<br>maneggi<br>scuderie</b>
		</th>
		<th width="8">
		<b>Tot D</b>
		</th>
		<th colspan="4" width="20"><h3>Totale (a+b+c+d)</h3></th>
		</tr>
		
		<dhv:permission name="parafarmacie-parafarmacie-allegatoII-edit">
		<%
			readonly = false ;
		%>
		</dhv:permission>

		<tr>
		<td width="10"><b><%=asl.getSelectedValue(Integer.parseInt((String)request.getAttribute("idAsl"))) %></b></td>
		<td width="5">
		<input type = "text" <%if(readonly1){%>disabled="disabled"  <%} %>  align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'"  name = "animali_reddito_art_4_5" value = "<%=RigaAllegatoII.getAnimali_reddito_art_4_5() %>" <%if (readonly){ %>readonly="readonly"<%} %>  >
		</td>
		<td width="5">
		<input type = "text" <%if(readonly1){%>disabled="disabled"  <%} %>  align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "animali_reddito_art_11" value = "<%=RigaAllegatoII.getAnimali_reddito_art_11() %>"  <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		<td width="5">
		<input type = "text" <%if(readonly1){%>disabled="disabled"  <%} %>  align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "animali_reddito_tot_a" onchange="calcolaTotale()" value = "<%=RigaAllegatoII.getAnimali_reddito_tot_a() %>" <%if (readonly){ %>readonly="readonly"<%} %>  >
		</td>
		<td width="5">
		<input type = "text" <%if(readonly1){%>disabled="disabled"  <%} %>  align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "mangimi_medicati_prod_intermedi_art_3_4" value = "<%=RigaAllegatoII.getMangimi_medicati_prod_intermedi_art_3_4() %>" <%if (readonly){ %>readonly="readonly"<%} %>  >
		</td>
		<td width="5">
		<input type = "text"  <%if(readonly1){%>disabled="disabled"  <%} %> align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "mangimi_medicati_prod_intermedi_art_16" value = "<%=RigaAllegatoII.getMangimi_medicati_prod_intermedi_art_16() %>" <%if (readonly){ %>readonly="readonly"<%} %>  >
		</td>
		<td width="5">
		<input type = "text" <%if(readonly1){%>disabled="disabled"  <%} %>  align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "mangimi_medicati_prod_intermedi_tot_b" onchange="calcolaTotale()" value = "<%=RigaAllegatoII.getMangimi_medicati_prod_intermedi_tot_b() %>"  <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		<td width="5">
		<input type = "text" <%if(readonly1){%>disabled="disabled"  <%} %>  align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "scorte_vet_art_84" value = "<%=RigaAllegatoII.getScorte_vet_art_84() %>" <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		<td width="5">
		<input type = "text" <%if(readonly1){%>disabled="disabled"  <%} %>  align="center"  onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'"name = "scorte_vet_tot_c" onchange="calcolaTotale()" value = "<%=RigaAllegatoII.getScorte_vet_tot_c() %>" <%if (readonly){ %>readonly="readonly"<%} %>>
		</td>
		<td width="5">
		<input type = "text" <%if(readonly1){%>disabled="disabled"  <%} %>  align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "scorte_allev_da_reddito" value = "<%=RigaAllegatoII.getScorte_allev_da_reddito() %>" <%if (readonly){ %>readonly="readonly"<%} %>  >
		</td>
		<td width="5">
		<input type = "text" <%if(readonly1){%>disabled="disabled"  <%} %>  align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "scorte_allev_da_compagnia" value = "<%=RigaAllegatoII.getScorte_allev_da_compagnia() %>" <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		<td width="5">
		<input type = "text" <%if(readonly1){%>disabled="disabled"  <%} %>  align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "scorte_allev_da_ippodromi" value = "<%=RigaAllegatoII.getScorte_allev_da_ippodromi() %>" <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		<td width="5">
		<input type = "text" <%if(readonly1){%>disabled="disabled"  <%} %>  align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "scorte_allev_tot_d" onchange="calcolaTotale()" value = "<%=RigaAllegatoII.getScorte_allev_tot_d() %>" <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		<td width="5">
		<input type = "text" title="Questo valore non è modificabile. Comprende la somma dei totali a,b,c,d"  align="center" style="background-color: yellow;" name = "totale" value = "<%=(RigaAllegatoII.getAnimali_reddito_tot_a()+RigaAllegatoII.getMangimi_medicati_prod_intermedi_tot_b()+RigaAllegatoII.getScorte_vet_tot_c()+RigaAllegatoII.getScorte_allev_tot_d()) %>" readonly="readonly" >
		</td>
		</tr>
		</table></td></tr>
		</table>
		</td></tr>
		
		</table>
		<br>
		<table border="1">
		<tr style= "background-color: #E3E3E3"><td colspan="2"><h3>INDICATORI DI<br> FARMACOSORVEGLIANZA</h3></td></tr>
		<tr style= "background-color: #E3E3E3"><td colspan="2"><h4>n. medio prescrizioni/anno<br>per allevamento:</h4></td></tr>
		<tr>
		<td style= "background-color: #E3E3E3"><b>BOVINA</b></td>
		<td><input type = "text"  <%if(readonly1){%>disabled="disabled"  <%} %> align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "bovina" value = "<%=RigaAllegatoII.getBovina() %>"  <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		</tr>
		<tr>
		<td style= "background-color: #E3E3E3"><b>SUINA</b></td>
		<td><input type = "text"  <%if(readonly1){%>disabled="disabled"  <%} %> align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "suina" value = "<%=RigaAllegatoII.getSuina() %>"  <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		</tr>
		<tr>
		<td style= "background-color: #E3E3E3"><b>AVICOLA</b></td>
		<td><input type = "text"  <%if(readonly1){%>disabled="disabled"  <%} %> align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "avicola" value = "<%=RigaAllegatoII.getAvicola() %>" <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		</tr>
		<tr>
		<td style= "background-color: #E3E3E3"><b>OVICAPRINA</b></td>
		<td><input type = "text"  <%if(readonly1){%>disabled="disabled"  <%} %> align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "ovicaprina" value = "<%=RigaAllegatoII.getOvicaprina() %>" <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		</tr>
		<tr>
		<td style= "background-color: #E3E3E3"><b>CUNICOLA</b></td>
		<td><input type = "text"  <%if(readonly1){%>disabled="disabled"  <%} %> align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "cunicola" value = "<%=RigaAllegatoII.getCunicola() %>"  <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		</tr>
		<tr>
		<td style= "background-color: #E3E3E3"><b>EQUINA</b></td>
		<td><input type = "text"  <%if(readonly1){%>disabled="disabled"  <%} %> align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "equina" value = "<%=RigaAllegatoII.getEquina() %>"  <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		</tr>
		<tr>
		<td style= "background-color: #E3E3E3"><b>ACQUICOLTURA</b></td>
		<td><input type = "text"  <%if(readonly1){%>disabled="disabled"  <%} %> align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "acquicoltura" value = "<%=RigaAllegatoII.getAcquicoltura() %>" <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		</tr>
		<tr>
		<td style= "background-color: #E3E3E3"><b>APIARIO</b></td>
		<td><input type = "text"  <%if(readonly1){%>disabled="disabled"  <%} %> align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "apiario" value = "<%=RigaAllegatoII.getApiario() %>"  <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		</tr>
		<tr>
		<td style= "background-color: #E3E3E3"><b>BUFALINA</b></td>
		<td><input type = "text"  <%if(readonly1){%>disabled="disabled"  <%} %> align="center" onfocus="this.style.backgroundColor='#FFDEAD'" onblur="this.style.backgroundColor='white'" name = "bufalina" value = "<%=RigaAllegatoII.getBufalina() %>"  <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		</tr>
		
		</table>
		 * dal numero di allevamenti conteggiati vanno esclusi gli allevamenti per autoconsumo
		<br>
		<br>
		<dhv:permission name="parafarmacie-parafarmacie-allegatoII-edit">
		<input type ="hidden" name = "stato" value = "0">
		
		<%if(RigaAllegatoII.getStato()!=1)
		{%>
		<input type ="hidden" name = "button" value = "aggiorna" >
		<input type = "button" value = "Salvataggio Temporaneo" onclick="return check(document.allegato2,0)">
			<input type = "button" value = "Salvataggio Definitivo" onclick="return check(document.allegato2,1)" title="Il salvataggio definitivo non consentirà piu la modifica dei dati">
		<input type = "submit" value = "Annulla" >
		<%}
		else{
			out.print("E' stata salvata la verisione definitiva. I dati non sono piu modificabili.");
		}
		%>
		</dhv:permission>
		
		
		</form>
		
		<%}
		else
		{
			boolean readonly = true ;
		HashMap<Integer,RigaAllegatoII> collezione = (HashMap<Integer,RigaAllegatoII>) request.getAttribute("ListaAllegatoII");
		%>
		<table>
		<tr>
		<td>
		<table align="left">
			<tr><td><a href = "Parafarmacie.do?command=StampaAllegatoII&asl=<%=user.getSiteId() %>&anno=<%=request.getAttribute("anno") %>">Stampa Report</a></td></tr>
			</table>
			
			
			<br/>
			<table align="left">
		<tr><td colspan="2">
		<h2><center>Allegato II</center></h2> <br>
		</td></tr>
		</table>
		<br><br><br>
		<table align="left">
		<tr style= "background-color: #E3E3E3">
		<td colspan="118">
		
		<table align = "left" width="100%" border="1px;">
		
		<tr><td colspan="2">
		<h3><center>SCHEDA PRESCRIZIONI FARMACO VETERINARIO </center></h3>
		</td></tr>
		<tr><td colspan="1"><b>REGIONE: CAMPANIA</br>
		ANNO: <%=request.getAttribute("anno") %></br>
		ENTE: Regione</b></td>
		<td>
		</td></tr>		
		</table>
		
		</td>
		</tr>
		<tr>
		<td>
		<table border="1" width="40%">
		<tr style= "background-color: #E3E3E3">
		<th  width="15">&nbsp;</th>
		<th colspan="3" width="15"><h3>PER ANIMALI DA <br>REDDITO</h3></th>
		<th colspan="3" width="15"><h3>PER MANGIMI MEDICATI E <br>PRODOTTI INTERMEDI</h3></th>
		<th colspan="2" width="10"><h3>PER SCORTE PROPRIE DEL VETERINARIO<br> (ambulatori, cliniche e attività zooiatrica)</h3></th>
		<th colspan="4" width="20"><h3>PER SCORTE DI IMPIANTO di<br> allevamento e custodia di animali</h3></th>
		<th colspan="1" width="20"><h3>Totale </h3></th>
		<th colspan="9" width="20"><h3>INDICATORI DI <br>FARMACOSORVEGLIANZA </h3></th>
		<th><h3>Stato</h3></th> 
		</tr>
		
		
		<tr>
		<th>&nbsp;</th>
		<th width="8">
		<b>D.lgs 158/2006 (artt. 4 e 5)</b>
		</th>
		<th width="8">
		<b>D.lgs 193/2006 (art. 11)</b>
		</th>
		<th width="8">
		<b>Tot A</b>
		</th>
		<th width="8">
		<b>D.lgs 90/93 (art. 3 c. 4</b>
		</th>
		<th width="8">
		<b>D.M. 16/11/93 (art. 16c.1)</b>
		</th>
		<th width="8">
		<b>Tot B</b>
		</th>
		<th width="8">
		<b>D.lgs 193/2006 (art. 84 comma 7</b>
		</th>
		<th width="8">
		<b>Tot C</b>
		</th>
		<th width="8">
		<b>Da Reddito</b>
		</th>
		<th width="8">
		<b>Da compagnia</b>
		</th>
		<th width="8">
		<b>ippodromi,<br>maneggi<br>scuderie</b>
		</th>
		<th width="8">
		<b>Tot D</b>
		</th>
		<th  width="20"><h3>Totale (a+b+c+d)</h3></th>
		<th  width="10"><h3>Bovina</h3></th>
		<th  width="10"><h3>Suina</h3></th>
		<th  width="10"><h3>Avicola</h3></th>
		<th  width="10"><h3>Ovicaprina</h3></th>
		<th  width="10"><h3>Cunicola</h3></th>
		<th  width="10"><h3>Equina</h3></th>
		<th  width="10"><h3>Acquicoltura</h3></th>
		<th  width="10"><h3>Apiario</h3></th>
		<th  width="10"><h3>Bufalina</h3></th>
		<th  width="10"><h3>&nbsp;</h3></th>
		
		
		</tr>
		
		<%
		Iterator<Integer> itKiavi = collezione.keySet().iterator();
		while(itKiavi.hasNext())
		{
			RigaAllegatoII  = collezione.get(itKiavi.next());
		
		%>
		
		<tr>
		<td width="10"><b><%=RigaAllegatoII.getDescrizioneAsl() %></b></td>
		<td width="5">
		<input type = "text" align="center" name = "animali_reddito_art_4_5" value = "<%=RigaAllegatoII.getAnimali_reddito_art_4_5() %>" <%if (readonly){ %>readonly="readonly"<%} %>  >
		</td>
		<td width="5">
		<input type = "text" align="center" name = "animali_reddito_art_11" value = "<%=RigaAllegatoII.getAnimali_reddito_art_11() %>"  <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		<td width="5">
		<input type = "text" align="center" name = "animali_reddito_tot_a" value = "<%=RigaAllegatoII.getAnimali_reddito_tot_a() %>" <%if (readonly){ %>readonly="readonly"<%} %>  >
		</td>
		<td width="5">
		<input type = "text" align="center" name = "mangimi_medicati_prod_intermedi_art_3_4" value = "<%=RigaAllegatoII.getMangimi_medicati_prod_intermedi_art_3_4() %>" <%if (readonly){ %>readonly="readonly"<%} %>  >
		</td>
		<td width="5">
		<input type = "text" align="center" name = "mangimi_medicati_prod_intermedi_art_16" value = "<%=RigaAllegatoII.getMangimi_medicati_prod_intermedi_art_16() %>" <%if (readonly){ %>readonly="readonly"<%} %>  >
		</td>
		<td width="5">
		<input type = "text" align="center" name = "mangimi_medicati_prod_intermedi_tot_b" value = "<%=RigaAllegatoII.getMangimi_medicati_prod_intermedi_tot_b() %>"  <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		<td width="5">
		<input type = "text" align="center" name = "scorte_vet_art_84" value = "<%=RigaAllegatoII.getScorte_vet_art_84() %>" <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		<td width="5">
		<input type = "text" align="center" name = "scorte_vet_tot_c" value = "<%=RigaAllegatoII.getScorte_vet_tot_c() %>" <%if (readonly){ %>readonly="readonly"<%} %>>
		</td>
		<td width="5">
		<input type = "text" align="center" name = "scorte_allev_da_reddito" value = "<%=RigaAllegatoII.getScorte_allev_da_reddito() %>" <%if (readonly){ %>readonly="readonly"<%} %>  >
		</td>
		<td width="5">
		<input type = "text" align="center" name = "scorte_allev_da_compagnia" value = "<%=RigaAllegatoII.getScorte_allev_da_compagnia() %>" <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		<td width="5">
		<input type = "text" align="center" name = "scorte_allev_da_ippodromi" value = "<%=RigaAllegatoII.getScorte_allev_da_ippodromi() %>" <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		<td width="5">
		<input type = "text" align="center" name = "scorte_allev_tot_d" value = "<%=RigaAllegatoII.getScorte_allev_tot_d() %>" <%if (readonly){ %>readonly="readonly"<%} %> >
		</td>
		<td width="5">
		<input type = "text"  name = "totale" value = "<%=RigaAllegatoII.getAnimali_reddito_tot_a()+RigaAllegatoII.getMangimi_medicati_prod_intermedi_tot_b()+RigaAllegatoII.getScorte_vet_tot_c()+RigaAllegatoII.getScorte_allev_tot_d() %>" readonly="readonly">
		</td>
		<td width="5">
		<input type = "text"  name = "bovina" value = "<%=RigaAllegatoII.getBovina() %>" readonly="readonly">		</td>
		<td width="5">
		<input type = "text"  name = "suina" value = "<%=RigaAllegatoII.getSuina() %>" readonly="readonly">		</td>
		<td width="5">
		<input type = "text"  name = "avicola" value = "<%=RigaAllegatoII.getAvicola() %>" readonly="readonly">		</td>
		<td width="5">
		<input type = "text"  name = "ovicaprina" value = "<%=RigaAllegatoII.getOvicaprina() %>" readonly="readonly">		</td>
		<td width="5">
		<input type = "text"  name = "cunicola" value = "<%=RigaAllegatoII.getCunicola() %>" readonly="readonly">		</td>
		<td width="5">
		<input type = "text"  name = "equina" value = "<%=RigaAllegatoII.getEquina() %>" readonly="readonly">		</td>
		<td width="5">
		<input type = "text"  name = "acquicoltura" value = "<%=RigaAllegatoII.getAcquicoltura() %>" readonly="readonly">		</td>
		<td width="5">
		<input type = "text"  name = "apiario" value = "<%=RigaAllegatoII.getApiario() %>" readonly="readonly">		</td>
		<td width="5">
		<input type = "text"  name = "bufalina" value = "<%=RigaAllegatoII.getBufalina() %>" readonly="readonly">
		</td>
		<td width="5">
		<%if (RigaAllegatoII.getStato()==1)
			{%>
			<font color="red"> Salvataggio Definitivo</font>
			<%}
		else
		{ 
			%>
			<font color="green"> Salvataggio Temporaneo</font>
			<%
		}%>
		</td>
		</tr>
		<%}
		%>
		</table></td></tr>
		</table>
		</td></tr>
		
		</table>
		 * dal numero di allevamenti conteggiati vanno esclusi gli allevamenti per autoconsumo
		<br>
		
		
			<%} %>
		<br>
		
		