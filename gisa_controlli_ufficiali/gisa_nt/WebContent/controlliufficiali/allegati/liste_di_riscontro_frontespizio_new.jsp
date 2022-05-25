<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>

<%!
	
	public static String fixValore(String code) {
	if (code ==null || code.equals("null") || code.equals(""))
		return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	else
		return code.toUpperCase();
}
%>

<!-- Per modello 1 ALTRE SPECIE-->
<%  if(numAllegato.equals("1")) { %>
Codice Azienda <input type="text" class="layout" size = "15" readonly value="<%=Allevamento.getN_reg() %>"> Ragione Sociale <input type="text" class="layout" size="70" readonly value="<%= (Allevamento.getName() != null && !Allevamento.getName().equals("") && !Allevamento.getName().equals("null")) ? Allevamento.getName().replace('"',' ').toUpperCase() : ""%>"><br>
Indirizzo sede allevamento <input type="text" class="layout" size="100" readonly value="<%= ( !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("") && !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("null null null (null)")) ? (Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").toUpperCase() : "" %>"><br>
Indirizzo sede legale <input type="text" class="layout" size="100" readonly value="<%=(Allevamento.getIndirizzo_legale() != null && !Allevamento.getIndirizzo_legale().equals("")) ? Allevamento.getIndirizzo_legale().toUpperCase() : ""%>"><br>
Responsabile legale <input type="text" class="layout" size="70"  readonly value="<%=  (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"> 
Proprietario degli animali <input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"><br> 
codice fiscale <input stype="text" class="layout" ize="27" readonly value="<%= (Allevamento.getCf_prop() != null && !Allevamento.getCf_prop().equals("")) ? Allevamento.getCf_prop().toUpperCase() : "" %>"><br>
Conduttore/Detentore degli animali <input type="text" class="layout" size="100" readonly value="<%= ( Allevamento.getDet() != null && !Allevamento.getDet().equals("") ? Allevamento.getDet().toUpperCase() : "" ) %>"><br>
codice fiscale <input type="text" class="layout" size="20" readonly value="<%= (Allevamento.getCf_det() != null && !Allevamento.getCf_det().equals("") ) ? Allevamento.getCf_det().toUpperCase() : "" %>"> Tel. <input type="text" class="layout" readonly value=""><br>
Data di costruzione o di inizio attività <input type="text" class="layout" readonly value="<%=Allevamento.getGiornoNascita()+ " " +Allevamento.getMeseNascita()+" " +Allevamento.getAnnoNascita() %>">  <br/>
Orientamento produttivo dell'allevamento <input type="text" class="layout" size="50" readonly value="<%=Allevamento.getTipologia_att() %>"> 
<!-- Tecnica produttiva <input size="40" type="text" value="" class="layout"> -->
<br/>

Data dell'ultima ristrutturazione <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("data_ultima_ristrutturazione").get("data_ultima_ristrutturazione"))%></label> <br/>
n. totale capannoni <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_totale_capannoni").get("num_totale_capannoni"))%></label> 
n. totale capannoni attivi all'atto dell'ispezione <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_totale_capannoni_attivi").get("num_totale_capannoni_attivi"))%></label> <br/>
<% for (int j=1; j<4; j++) { %>
<li> capannone n.  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("capannone_num_"+j).get("capannone_num_"+j))%></label> 
capacità massima   <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("cap_massima_"+j).get("cap_massima_"+j))%></label> 
animali presenti all'atto dell'ispezione    <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("animali_presenti_"+j).get("animali_presenti_"+j))%></label>     <br/>
n. totale box   <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_totale_box_"+j).get("num_totale_box_"+j))%></label> 
n.totale box attivi all'atto dell'ispezione   <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_attivi_box_"+j).get("num_attivi_box_"+j))%></label> 
ispezionato  <% if (fixValore(Modulo.getListaCampiModulo().get("ispezionato_"+j).get("ispezionato_"+j)).equals("ON")) {%> SI [X] NO [ ]<%} else { %> SI [ ] NO [X] <%} %>
</li>
<%} %>
n. totale animali presenti <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_totale_animali").get("num_totale_animali"))%></label> 
suddivisi per categorie di età <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("categorie_eta").get("categorie_eta"))%></label> 
<br/>
capacità massima  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("cap_massima").get("cap_massima"))%></label>
<br/>
Veterinario aziendale (se presente): Dott.  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("veterinario_aziendale").get("veterinario_aziendale"))%></label>
 <br/>
 
<% } else if(numAllegato.equals("2")) { %>
<!-- Per modello 2 -->
Codice Azienda <input type="text" class="layout" size = "15" readonly value="<%=Allevamento.getN_reg() %>"> Ragione Sociale <input type="text" class="layout" size="70" readonly value="<%= (Allevamento.getName() != null && !Allevamento.getName().equals("") && !Allevamento.getName().equals("null")) ? Allevamento.getName().replace('"',' ').toUpperCase() : ""%>"><br>
Sede allevamento <input type="text" class="layout" size="100" readonly value="<%= ( !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("") && !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("null null null (null)")) ? (Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").toUpperCase() : "" %>"><br>
Sede legale <input type="text" class="layout" size="100" readonly value="<%=(Allevamento.getIndirizzo_legale() != null && !Allevamento.getIndirizzo_legale().equals("")) ? Allevamento.getIndirizzo_legale().toUpperCase() : ""%>"><br>
Responsabile legale <input type="text" class="layout" size="70"  readonly value="<%=  (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"> 
Proprietario degli animali <input type="text" class="layout" size="100"  readonly value="<%= (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"><br>
Codice fiscale <input type="text" class="layout" size="20" readonly value="<%= (Allevamento.getCf_prop() != null && !Allevamento.getCf_prop().equals("")) ? Allevamento.getCf_prop().toUpperCase() : ""%>"> Tel. <input type="text" class="layout" readonly value=""><br>
Conduttore/Detentore degli animali <input type="text" class="layout" size="100" readonly value="<%= ( Allevamento.getDet() != null && !Allevamento.getDet().equals("") ? Allevamento.getDet().toUpperCase() : "" ) %>"><br>
Codice fiscale <input type="text" class="layout"  size="20" readonly value="<%= (Allevamento.getCf_det() != null && !Allevamento.getCf_det().equals("") ) ? Allevamento.getCf_det().toUpperCase() : "" %>"> Tel. <input type="text" class="layout" readonly value=""><br>
Data di costruzione o di inizio attività <input type="text" class="layout" readonly value="<%=Allevamento.getGiornoNascita()+ " " +Allevamento.getMeseNascita()+" " +Allevamento.getAnnoNascita() %>"><br>

Tipologia produttivo allenamento <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("tipologia_allevamento").get("tipologia_allevamento"))%></label> <br/>
Numero uova anno <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_uova_anno").get("num_uova_anno"))%></label> <br/>
Selezione/imballaggio presso l'allevamento <label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("selezione_imballaggio").get("selezione_imballaggio")).equals("ON")) {%> SI<%} else { %>NO<%} %> </label> indicare la destinazione <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("selezione_imballaggio_destinazione").get("selezione_imballaggio_destinazione"))%></label> <br/>
n. totale capannoni <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_totale_capannoni").get("num_totale_capannoni"))%></label> 
n. totale capannoni attivi all'atto dell'ispezione <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_totale_capannoni_attivi").get("num_totale_capannoni_attivi"))%></label> <br/>
<% for (int j=1; j<6; j++) { %>
<li> capannone n.  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("capannone_num_"+j).get("capannone_num_"+j))%></label> 
capacità massima   <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("cap_massima_"+j).get("cap_massima_"+j))%></label> 
presenti all'atto dell'ispezione    <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("animali_presenti_"+j).get("animali_presenti_"+j))%></label>     <br/>
ispezionato  <% if (fixValore(Modulo.getListaCampiModulo().get("ispezionato_"+j).get("ispezionato_"+j)).equals("ON")) {%> SI [X] NO [ ]<%} else { %> SI [ ] NO [X] <%} %>
</li>
<%} %>
In allevamento si pratica la "muta" <label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("muta").get("muta")).equals("ON")) {%> SI<%} else { %>NO<%} %> </label> (vedi nota Ministero salute prot. n. 23052 del 03/12/2013 e check list di seguito riportata)<br/>
Veterinario aziendale (se presente): Dott.  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("veterinario_aziendale").get("veterinario_aziendale"))%></label>
 <br/>
 
  <table width="100%" style="border:1px solid black"><tr><td>Metodo di allevamento</td></tr>
  
  <tr> <td> (barrare una o più caselle) 
  <label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("metodo_aperto").get("metodo_aperto")).equals("ON")) {%> [X] <%} else { %>[ ]<%} %> all'aperto </label>
  <label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("metodo_terra").get("metodo_terra")).equals("ON")) {%> [X] <%} else { %>[ ]<%} %> a terra </label>
  <label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("metodo_biologico").get("metodo_biologico")).equals("ON")) {%> [X] <%} else { %>[ ]<%} %> biologico </label>
  <label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("metodo_gabbia").get("metodo_gabbia")).equals("ON")) {%> [X] <%} else { %>[ ]<%} %> in gabbia </label>
  <label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("metodo_voliera").get("metodo_voliera")).equals("ON")) {%> [X] <%} else { %>[ ]<%} %> in voliera </label>
  <br/>
   
Se in batteria le gabbie sono disposte: 

<label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("gabbie_piano").get("gabbie_piano")).equals("ON")) {%> [X] <%} else { %>[ ]<%} %> su un unico piano </label>
  <label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("gabbie_sfasati").get("gabbie_sfasati")).equals("ON")) {%> [X] <%} else { %>[ ]<%} %> su piani sfasati </label>
  <label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("gabbie_sovrapposti").get("gabbie_sovrapposti")).equals("ON")) {%> [X] <%} else { %>[ ]<%} %> su piani sovrapposti </label>
<br/>
  n. piani di gabbie  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("gabbie_num_piani").get("gabbie_num_piani"))%></label>
  n. galline per gabbia  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("gabbie_num_galline").get("gabbie_num_galline"))%></label>   
   </table>
  

 
<% } else if(numAllegato.equals("3") ) { %>
<!-- Per modello 3 SUINI -->
Codice Azienda <input type="text" class="layout" size = "15" readonly value="<%=Allevamento.getN_reg() %>">Ragione Sociale <input type="text" class="layout" size="70" readonly value="<%= (Allevamento.getName() != null && !Allevamento.getName().equals("") && !Allevamento.getName().equals("null")) ? Allevamento.getName().replace('"',' ').toUpperCase() : ""%>"><br>
Indirizzo allevamento <input type="text" class="layout" size="100" readonly value="<%= ( !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("") && !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("null null null (null)")) ? (Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").toUpperCase() : "" %>"><br>
Indirizzo sede legale <input type="text" class="layout" size="100" readonly value="<%=(Allevamento.getIndirizzo_legale() != null && !Allevamento.getIndirizzo_legale().equals("")) ? Allevamento.getIndirizzo_legale().toUpperCase() : ""%>"><br>
Responsabile legale <input type="text" class="layout" size="70"  readonly value="<%=  (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"> 
Proprietario degli animali <input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"><br>
Codice fiscale <input type="text" class="layout" size="20" readonly value="<%= (Allevamento.getCf_prop() != null && !Allevamento.getCf_prop().equals("")) ? Allevamento.getCf_prop().toUpperCase() : ""%>"> Tel. <input type="text" class="layout" readonly value=""><br>
Conduttore/Detentore degli animali <input type="text" class="layout" size="100" readonly value="<%= ( Allevamento.getDet() != null && !Allevamento.getDet().equals("") ? Allevamento.getDet().toUpperCase() : "" ) %>"><br>
Codice fiscale <input type="text" class="layout" size="20" readonly value="<%= (Allevamento.getCf_det() != null && !Allevamento.getCf_det().equals("") ) ? Allevamento.getCf_det().toUpperCase() : "" %>"> Tel. <input type="text" class="layout" readonly value=""><br>
Data di costruzione o di inizio attività <input type="text" class="layout" readonly value="<%=Allevamento.getGiornoNascita()+ " " +Allevamento.getMeseNascita()+" " +Allevamento.getAnnoNascita() %>"> 

Data dell'ultima ristrutturazione <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("data_ultima_ristrutturazione").get("data_ultima_ristrutturazione"))%></label> <br/>
indirizzo produttivo dell'allevamento <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("tipologia_allevamento").get("tipologia_allevamento"))%></label> <br/>
Tecnica produttiva <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("tecnica_produttiva").get("tecnica_produttiva"))%></label> <br/>
n. totale capannoni <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_totale_capannoni").get("num_totale_capannoni"))%></label> 
n. totale capannoni attivi all'atto dell'ispezione <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_totale_capannoni_attivi").get("num_totale_capannoni_attivi"))%></label> <br/>
<% for (int j=1; j<6; j++) { %>
<li> capannone n.  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("capannone_num_"+j).get("capannone_num_"+j))%></label> 
capacità massima   <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("cap_massima_"+j).get("cap_massima_"+j))%></label> 
presenti all'atto dell'ispezione    <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("animali_presenti_"+j).get("animali_presenti_"+j))%></label>     <br/>
n° box   <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_totale_box_"+j).get("num_totale_box_"+j))%></label> 
categoria animali presenti (verri, scrofe, suinetti, ecc...)  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("categoria_presenti_"+j).get("categoria_presenti_"+j))%></label> 
capannone ispezionato  <% if (fixValore(Modulo.getListaCampiModulo().get("ispezionato_"+j).get("ispezionato_"+j)).equals("ON")) {%> SI [X] NO [ ]<%} else { %> SI [ ] NO [X] <%} %>
</li>
<%} %>
Nell'allevamento si pratica la Fecondazione Artificiale <label class="layout"> <% if (fixValore(Modulo.getListaCampiModulo().get("artificiale").get("artificiale")).equals("ON")) {%> SI<%} else { %>NO<%} %> </label><br/>
Responsabile dell'attuazione del Piano Aujeszky: Dott. <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("responsabile_piano").get("responsabile_piano"))%></label> 
<br/>
Veterinario aziendale (se presente): Dott.  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("veterinario_aziendale").get("veterinario_aziendale"))%></label>
 <br/>
 
<!-- Per modello 4 VITELLI -->
<% } else if(numAllegato.equals("4")) { %> 
Codice Azienda <input type="text" class="layout" size = "15" readonly value="<%=Allevamento.getN_reg() %>"> Ragione Sociale <input size="70" type="text" class="layout" readonly value="<%= (Allevamento.getName() != null && !Allevamento.getName().equals("") && !Allevamento.getName().equals("null")) ? Allevamento.getName().replace('"',' ').toUpperCase() : ""%>"><br>
Indirizzo allevamento <input type="text" class="layout" size="100" readonly value="<%= ( !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("") && !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("null null null (null)")) ? (Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").toUpperCase() : "" %>"><br>
Indirizzo sede legale <input type="text" class="layout" size="100" readonly value="<%=(Allevamento.getIndirizzo_legale() != null && !Allevamento.getIndirizzo_legale().equals("")) ? Allevamento.getIndirizzo_legale().toUpperCase() : ""%>"><br>
Responsabile legale <input type="text" class="layout" size="70" readonly value="<%=  (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"> codice fiscale <input type="text" class="layout" size="27" readonly value="<%= (Allevamento.getCf_prop() != null && !Allevamento.getCf_prop().equals("")) ? Allevamento.getCf_prop().toUpperCase() : "" %>"><br>
Proprietario degli animali <input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"><br>
Codice fiscale <input type="text" class="layout" size="20" readonly value="<%= (Allevamento.getCf_prop() != null && !Allevamento.getCf_prop().equals("")) ? Allevamento.getCf_prop().toUpperCase() : ""%>"> Tel. <input type="text" class="layout" readonly value=""><br>
Conduttore/Detentore degli animali <input type="text" class="layout" size="100" readonly value="<%= ( Allevamento.getDet() != null && !Allevamento.getDet().equals("") ? Allevamento.getDet().toUpperCase() : "" ) %>"><br>
Codice fiscale <input type="text" class="layout" size="20" readonly value="<%= (Allevamento.getCf_det() != null && !Allevamento.getCf_det().equals("") ) ? Allevamento.getCf_det().toUpperCase() : "" %>"> Tel. <input type="text" class="layout" readonly value=""><br>
Data di costruzione o di inizio attività <input type="text" class="layout" readonly value="<%=Allevamento.getGiornoNascita()+ " " +Allevamento.getMeseNascita()+" " +Allevamento.getAnnoNascita() %>"><br>
Orientamento produttivo <input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getTipologia_att() !=null && !Allevamento.getTipologia_att().equals("")) ? Allevamento.getTipologia_att().toUpperCase() : "" %>"><br>

Data dell'ultima ristrutturazione <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("data_ultima_ristrutturazione").get("data_ultima_ristrutturazione"))%></label> <br/>
n. totale capannoni <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_totale_capannoni").get("num_totale_capannoni"))%></label> 
n. totale capannoni attivi all'atto dell'ispezione <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_totale_capannoni_attivi").get("num_totale_capannoni_attivi"))%></label> <br/>
<% for (int j=1; j<4; j++) { %>
<li> capannone n.  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("capannone_num_"+j).get("capannone_num_"+j))%></label> 
capacità massima   <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("cap_massima_"+j).get("cap_massima_"+j))%></label> 
animali presenti all'atto dell'ispezione    <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("animali_presenti_"+j).get("animali_presenti_"+j))%></label>     <br/>
n. totale box   <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_totale_box_"+j).get("num_totale_box_"+j))%></label> 
n.totale box attivi all'atto dell'ispezione   <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_attivi_box_"+j).get("num_attivi_box_"+j))%></label> 
ispezionato  <% if (fixValore(Modulo.getListaCampiModulo().get("ispezionato_"+j).get("ispezionato_"+j)).equals("ON")) {%> SI [X] NO [ ]<%} else { %> SI [ ] NO [X] <%} %>
</li>
<%} %>
n. totale dei vitelli presenti <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_totale_vitelli").get("num_totale_vitelli"))%></label> 
n. totale dei di età < a 8 settimane <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_vitelli_inf_8").get("num_vitelli_inf_8"))%></label> 
<br/>
capacità massima vitelli  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("cap_massima_vitelli").get("cap_massima_vitelli"))%></label>
<br/>
Veterinario aziendale (se presente): Dott.  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("veterinario_aziendale").get("veterinario_aziendale"))%></label>
 <br/>
<% } else if(numAllegato.equals("5")) { %>

Normativa di riferimento: 
<ul>
<li>Decreto  Legislativo  27  settembre  2010,  n.181 - <i>" Attuazione  della  direttiva  2007/43/CE  che stabilisce norme minime per la protezione di polli allevati per la produzione di carne"</i></li>
<li>Decreto  Legislativo 26  marzo  2001,  n.  146 - <i>" Attuazione  della  direttiva  98/58/CE  relativa alla protezione degli animali negli allevamenti"</i></li>
<li>Decreto Ministeriale 4 febbraio 2013 <i>"disposizioni attuative in materia di protezione di polli allevati per la produzione di carne, ai sensi degli articoli 3,4,6 e 8 del decreto legislativo 27 settembre 2010, n. 181."</i></li> 
<li>Decisione  (CE) 2006/778/CE  del  14  novembre  2006 <i>"relativa  ai  requisiti  applicabili  alla raccolta  di  informazioni  durante  le ispezioni  effettuate nei  luoghi  di  produzione  in  cui  sono allevate alcune specie di animali"</i></li>
</ul>


Codice Azienda <input type="text" class="layout" size = "15" readonly value="<%=Allevamento.getN_reg() %>"> Ragione Sociale <input type="text" class="layout" size="70" readonly value="<%= (Allevamento.getName() != null && !Allevamento.getName().equals("") && !Allevamento.getName().equals("null")) ? Allevamento.getName().replace('"',' ').toUpperCase() : ""%>"><br>
Sede allevamento <input type="text" class="layout" size="100" readonly value="<%= ( !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("") && !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("null null null (null)")) ? (Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").toUpperCase() : "" %>"><br>
Sede legale <input type="text" class="layout" size="100" readonly value="<%=(Allevamento.getIndirizzo_legale() != null && !Allevamento.getIndirizzo_legale().equals("")) ? Allevamento.getIndirizzo_legale().toUpperCase() : ""%>"><br>
Responsabile legale <input type="text" class="layout" size="70"  readonly value="<%=  (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"><br/> 
Proprietario degli animali <input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"><br> 
codice fiscale <input stype="text" class="layout" ize="27" readonly value="<%= (Allevamento.getCf_prop() != null && !Allevamento.getCf_prop().equals("")) ? Allevamento.getCf_prop().toUpperCase() : "" %>"><br>
Conduttore/Detentore degli animali <input type="text" class="layout" size="100" readonly value="<%= ( Allevamento.getDet() != null && !Allevamento.getDet().equals("") ? Allevamento.getDet().toUpperCase() : "" ) %>"><br>
codice fiscale <input type="text" class="layout" size="20" readonly value="<%= (Allevamento.getCf_det() != null && !Allevamento.getCf_det().equals("") ) ? Allevamento.getCf_det().toUpperCase() : "" %>"> <br>
Data di costruzione o di inizio attività <input type="text" class="layout" readonly value="<%=Allevamento.getGiornoNascita()+ " " +Allevamento.getMeseNascita()+" " +Allevamento.getAnnoNascita() %>">  <br/>
Data dell'ultima ristrutturazione <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("data_ultima_ristrutturazione").get("data_ultima_ristrutturazione"))%></label> <br/>
n. totale capannoni <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_totale_capannoni").get("num_totale_capannoni"))%></label> 
n. totale capannoni attivi all'atto dell'ispezione <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_totale_capannoni_attivi").get("num_totale_capannoni_attivi"))%></label> <br/>
superficie allevabile totale m2 <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("superficie_allevabile").get("superficie_allevabile"))%></label> 
n. totale animali presenti <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_totale_animali_presenti").get("num_totale_animali_presenti"))%></label><br/> 
Densità attuale <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("densita_attuale").get("densita_attuale"))%></label> 
Densità prevista <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("densita_prevista").get("densita_prevista"))%></label> 
<br/>
Numero massimo di soggetti allevabile a 33Kg m2  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_max_33").get("num_max_33"))%></label><br/>
Numero massimo di soggetti allevabile a 39Kg m2  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_max_39").get("num_max_39"))%></label><br/>
Numero massimo di soggetti allevabile a 42Kg m2  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("num_max_42").get("num_max_42"))%></label><br/>
<br/>
Veterinario aziendale (se presente): Dott.  <label class="layout"><%= fixValore(Modulo.getListaCampiModulo().get("veterinario_aziendale").get("veterinario_aziendale"))%></label>
 <br/>
 
 <%} %>