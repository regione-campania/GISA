<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!-- GESTIONE ORIGINE ANIMALE -->
<div class="clear1"></div>
<table width="100%"><tr><td>
<div><br>Dichiara le seguenti informazioni sulla provenienza animale:</div>
<% 
boolean or=false;
if (thisAnimale.isFlagMancataOrigine()){ or=false; } 
if (eventoF.getProvenienza_origine()!=null && !eventoF.getProvenienza_origine().equals("")){  or=true; %>
	<div class="clear1"></div>
	<div class="nodott" style="margin-top: 0px;">- che l'animale ha origine <%=(eventoF.getProvenienza_origine().contains("in") ? "in regione" : "fuori regione")%>,</div>
<% } %>
			
<% if(eventoF.getIdProprietarioProvenienza()>0){  or=true; %>
	<div class="clear1"></div>
	<div class="nodott" style="margin-top: 0px;">- che l'animale e' proveniente da proprietario:&nbsp;</div>
	<div class="dott" style="margin-top: 0px;">
		<% Operatore proprietarioProvenienza = eventoF.getIdProprietarioProvenienzaOp();
		if (proprietarioProvenienza != null) {
			out.print(toHtml(proprietarioProvenienza.getRagioneSociale())); 
		}%></div>
<% } %>

<% if (eventoF.getData_ritrovamento()!= null && !eventoF.getData_ritrovamento().equals("")){  or=true; %>
	<div class="clear1"></div>
	<div class="nodott" style="margin-top: 0px;">- che l'animale e' proveniente da ritrovamento in&nbsp;</div>
	<div class="dott_long" style="margin-top: 0px;"><% out.print(""+eventoF.getIndirizzo_ritrovamento()+", "+comuniList.getSelectedValue(Integer.parseInt(eventoF.getComune_ritrovamento()))+" - "+provinceList.getSelectedValue(Integer.parseInt(eventoF.getProvincia_ritrovamento()))); %></div>

	<div class="nodott" style="margin-top: 0px;"> in data </div>
	<div class="dott" style="margin-top: 0px;"><% out.print(eventoF.getData_ritrovamento()); %></div>
<% } %>

<% if(eventoF.isFlag_anagrafe_estera()){ or=true; %>
	<div class="clear1"></div>
	<div class="nodott" style="margin-top: 0px;">- che l'animale e' proveniente da nazione estera:&nbsp;</div>
	<div class="dott" style="margin-top: 0px;"><% out.print(nazioniList.getSelectedValue(Integer.parseInt(eventoF.getNazione_estera()))); %></div>
	<% if(eventoF.getNazione_estera_note()!=null && !eventoF.getNazione_estera_note().equals("")){ %>
		<div class="clear1"></div>
		<div class="nodott" style="margin-top: 0px;">Note:&nbsp;</div><div class="dott" style="margin-top: 0px;"><%	out.print(""+eventoF.getNazione_estera_note());%></div>
<% 		}
	}%>
			
<% if(eventoF.isFlag_anagrafe_fr()){ or=true;  %>
	<div class="clear1"></div>
	<div class="nodott" style="margin-top: 0px;">- che l'animale e' proveniente da anagrafe di altra regione:&nbsp;</div>
	<div class="dott" style="margin-top: 0px;"><% out.print(""+regioniList.getSelectedValue(Integer.parseInt(eventoF.getRegione_anagrafe_fr()))); %></div>
	<% if(eventoF.getRegione_anagrafe_fr_note()!=null && !eventoF.getRegione_anagrafe_fr_note().equals("")){ %>
		<div class="clear1"></div>
		<div class="nodott" style="margin-top: 0px;">Note:&nbsp;</div><div class="dott" style="margin-top: 0px;"><%	out.print(""+eventoF.getRegione_anagrafe_fr_note());%></div>
<% 		}
	} %>

<% if(eventoF.isFlag_acquisto_online()){ or=true;  %>
	<div class="clear1"></div>
	<div class="nodott" style="margin-top: 0px;">- che l'animale e' stato pervenuto tramite sito web:&nbsp;</div>
	<div class="dott" style="margin-top: 0px;"><% out.print(eventoF.getSito_web_acquisto());	%></div>
	<% if(eventoF.getSito_web_acquisto_note()!=null && !eventoF.getSito_web_acquisto_note().equals("")){ %>
		<div class="clear1"></div>
		<div class="nodott" style="margin-top: 0px;">Note:&nbsp;</div><div class="dott" style="margin-top: 0px;"><%	out.print(""+eventoF.getSito_web_acquisto_note());%></div>
<% 	}
	} %> 
			
<!-- GESTIONE VECCHIA PROVENIENZA -->
<%
if (!or){
	if(thisAnimale.getIdSpecie() == Cane.idSpecie){   
		if(Cane.isFlagFuoriRegione() && Cane.getIdRegioneProvenienza() > -1){	or=true; %>
			<div class="clear1"></div>
			<div class="nodott" style="margin-top: 0px;">- che l'animale e' proveniente da anagrafe altra regione:&nbsp;</div>
			<div class="dott" style="margin-top: 0px;"><% out.print(""+regioniList.getSelectedValue(Cane.getIdRegioneProvenienza())); %></div>
			<% if(Cane.isFlagSindacoFuoriRegione()){ %>
			<div class="clear1"></div>
			<div class="nodott" style="margin-top: 0px;">Note:&nbsp;</div><div class="dott" style="margin-top: 0px;"><%=(Cane.isFlagSindacoFuoriRegione()) ? "Proprietario Sindaco" : ""%></div>
		<%}
		}
	} 
}%>

<% if(!or){ %>
	<script>document.getElementById("pr").style.display="none";</script>
	<div class="clear1"></div>
	<div class="nodott" style="margin-top: 0px;">di non conoscere le informazioni complete sull'origine dell'animale di sua proprieta', identificato 
	dal MC </div><div class="dott" style="margin-top: 0px;">&nbsp;  <%=value_microchip%></div>
	<div class="nodott" style="margin-top: 0px;">  e inserito nella Anagrafe Regionale della Regione Campania in data odierna.</div>
	<div class="clear1"></div>
<% } %> 
<br>
<div class="clear1"></div>

</td></tr></table>
