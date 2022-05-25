<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<!-- Per modello 1 -->
<%  if(numAllegato.equals("1")) { %>
Codice Azienda <input type="text" class="layout" size = "15" readonly value="<%=Allevamento.getN_reg() %>">Denominazione <input type="text" class="layout" size="70" readonly value="<%= (Allevamento.getName() != null && !Allevamento.getName().equals("") && !Allevamento.getName().equals("null")) ? Allevamento.getName().replace('"',' ').toUpperCase() : ""%>"><br>
Indirizzo azienda <input type="text" class="layout" size="100" readonly value="<%= ( !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("") && !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("null null null (null)")) ? (Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").toUpperCase() : "" %>"><br>
Indirizzo sede legale <input type="text" class="layout" size="100" readonly value="<%=(Allevamento.getIndirizzo_legale() != null && !Allevamento.getIndirizzo_legale().equals("")) ? Allevamento.getIndirizzo_legale().toUpperCase() : ""%>"><br>
Proprietario struttura <input type="text" class="layout" size="70"  readonly value="<%=  (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"> codice fiscale <input stype="text" class="layout" ize="27" readonly value="<%= (Allevamento.getCf_prop() != null && !Allevamento.getCf_prop().equals("")) ? Allevamento.getCf_prop().toUpperCase() : "" %>"><br>
Proprietario degli animali <input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"><br>
Codice fiscale <input type="text" class="layout" size="20" readonly value="<%= (Allevamento.getCf_prop() != null && !Allevamento.getCf_prop().equals("")) ? Allevamento.getCf_prop().toUpperCase() : ""%>"> Tel. <input type="text" class="layout" readonly value=""><br>
Detentore <input type="text" class="layout" size="100" readonly value="<%= ( Allevamento.getDet() != null && !Allevamento.getDet().equals("") ? Allevamento.getDet().toUpperCase() : "" ) %>"><br>
Codice fiscale <input type="text" class="layout" size="20" readonly value="<%= (Allevamento.getCf_det() != null && !Allevamento.getCf_det().equals("") ) ? Allevamento.getCf_det().toUpperCase() : "" %>"> Tel. <input type="text" class="layout" readonly value=""><br>
Data inizio attività <input type="text" class="layout" readonly value="<%=Allevamento.getGiornoNascita()+ " " +Allevamento.getMeseNascita()+" " +Allevamento.getAnnoNascita() %>"><br>
N. ovaiole: capacità massima <input type="text" class="layout" readonly value="<%=AzFields.getCap_max_animali()%>"> presenti all'atto dell'ispezione <input type="text" class="layout" readonly value="<%=AzFields.getNum_animali_isp()%>"><br>
N. totale capannoni <input type="text" class="layout" readonly value="<%=AzFields.getNum_tot_capannoni()%>"> n. totali capannoni attivi all'atto dell'ispezione <input type="text" class="layout" readonly value="<%=AzFields.getNum_tot_capannoni_isp()%>"><br> 
Metodo di allevamento: <% if(AzFields.getMetodo_allevamento()!=null &&  !AzFields.getMetodo_allevamento().equals("") && !AzFields.getMetodo_allevamento().equalsIgnoreCase("null") && AzFields.getMetodo_allevamento().contains("aperto")) { %>
<span class="CheckedItem"> all'aperto&nbsp;&nbsp;&nbsp;&nbsp;</span>
<% } else { %>
<span class="NocheckedItem">all'aperto&nbsp;&nbsp;</span>
<% } %>
<% if(AzFields.getMetodo_allevamento()!=null &&  !AzFields.getMetodo_allevamento().equals("") && !AzFields.getMetodo_allevamento().equalsIgnoreCase("null") && AzFields.getMetodo_allevamento().contains("terra")) { %>
<span class="CheckedItem"> a terra &nbsp;&nbsp;&nbsp;&nbsp;</span>
<% } else { %>
<span class="NocheckedItem">a terra&nbsp;&nbsp;</span>
<% } %>
<% if(AzFields.getMetodo_allevamento()!=null &&  !AzFields.getMetodo_allevamento().equals("") && !AzFields.getMetodo_allevamento().equalsIgnoreCase("null") && AzFields.getMetodo_allevamento().contains("iologico")) { %>
<span class="CheckedItem"> biologico &nbsp;&nbsp;&nbsp;&nbsp;</span>
<% } else { %>
<span class="NocheckedItem">biologico&nbsp;&nbsp;</span>
<% } %>
<% if(AzFields.getMetodo_allevamento()!=null &&  !AzFields.getMetodo_allevamento().equals("") && !AzFields.getMetodo_allevamento().equalsIgnoreCase("null") && AzFields.getMetodo_allevamento().contains("Gabbia")) { %>
<span class="CheckedItem"> in gabbia&nbsp;&nbsp;&nbsp;&nbsp;</span> n. capannoni con gabbie modificate <input type="text" value="<%=AzFields.getNum_capannoni_con_gabbie() %>" class="layout"><br>
<div style="padding-left: 152px"> - n.capannoni con gabbie NON modificate <input type="text" value="<%=AzFields.getNum_capannoni_non_gabbie() %>" class="layout"/>
</div>
<% } else { %>
<span class="NocheckedItem">in gabbia&nbsp;&nbsp;</span>
<div style="padding-left: 152px"> - n.capannoni con gabbie modificate <input type="text" value="<%=AzFields.getNum_capannoni_con_gabbie() %>" class="layout"/> <br>- n.capannoni con gabbie NON modificate <input type="text" value="<%=AzFields.getNum_capannoni_non_gabbie() %>" class="layout"/>
<% } %>
</div>
<% } else if(numAllegato.equals("2")) { %>
<!-- Per modello 2 -->
Codice Azienda <input type="text" class="layout" size = "15" readonly value="<%=Allevamento.getN_reg() %>">Denominazione <input type="text" class="layout" size="70" readonly value="<%= (Allevamento.getName() != null && !Allevamento.getName().equals("") && !Allevamento.getName().equals("null")) ? Allevamento.getName().replace('"',' ').toUpperCase() : ""%>"><br>
Indirizzo azienda <input type="text" class="layout" size="100" readonly value="<%= ( !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("") && !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("null null null (null)")) ? (Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").toUpperCase() : "" %>"><br>
Indirizzo sede legale <input type="text" class="layout" size="100" readonly value="<%=(Allevamento.getIndirizzo_legale() != null && !Allevamento.getIndirizzo_legale().equals("")) ? Allevamento.getIndirizzo_legale().toUpperCase() : ""%>"><br>
Proprietario struttura <input type="text" class="layout" size="70" readonly value="<%=  (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"> codice fiscale <input type="text" class="layout" size="27" readonly value="<%= (Allevamento.getCf_prop() != null && !Allevamento.getCf_prop().equals("")) ? Allevamento.getCf_prop().toUpperCase() : "" %>"><br>
Proprietario degli animali <input type="text" class="layout" size="100"  readonly value="<%= (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"><br>
Codice fiscale <input type="text" class="layout" size="20" readonly value="<%= (Allevamento.getCf_prop() != null && !Allevamento.getCf_prop().equals("")) ? Allevamento.getCf_prop().toUpperCase() : ""%>"> Tel. <input type="text" class="layout" readonly value=""><br>
Detentore <input type="text" class="layout" size="100" readonly value="<%= ( Allevamento.getDet() != null && !Allevamento.getDet().equals("") ? Allevamento.getDet().toUpperCase() : "" ) %>"><br>
Codice fiscale <input type="text" class="layout"  size="20" readonly value="<%= (Allevamento.getCf_det() != null && !Allevamento.getCf_det().equals("") ) ? Allevamento.getCf_det().toUpperCase() : "" %>"> Tel. <input type="text" class="layout" readonly value=""><br>
Data inizio attività <input type="text" class="layout" readonly value="<%=Allevamento.getGiornoNascita()+ " " +Allevamento.getMeseNascita()+" " +Allevamento.getAnnoNascita() %>"><br>
Tipologia produttiva: <input type="text" class="layout" size="100" readonly value=""><br>
Orientamento produttivo <input type="text" class="layout" size="50" readonly value="<%=Allevamento.getTipologia_att() %>"> Tecnica produttiva <input size="40" type="text" value="" class="layout"><br>
Modalità di allevamento: <input type="text" class="layout" size="100" readonly value=""> <br>
N. totale capannoni <input type="text" class="layout" readonly value="<%=AzFields.getNum_tot_capannoni() %>"> n. totale capannoni attivi all'atto dell'ispezione <input type="text" class="layout" readonly value="<%=AzFields.getNum_tot_capannoni_isp() %>"><br>
N. totale box <input type="text" class="layout" readonly value="<%=AzFields.getNum_tot_box() %>"> n. totale box attivi all'atto dell'ispezione <input type="text" class="layout" readonly value="<%=AzFields.getNum_tot_box_isp()%>"><br>
<br>
verri: <input type="text" readonly size="15" style="padding-left:10em; border: none;">capacità massima <input type="text" class="layout" readonly value="<%=AzFields.getCap_max_verri()%>"> presenti all'atto dell'ispezione <input type="text" class="layout" readonly value="<%=AzFields.getNum_verri_isp()%>"><br> 
scrofe e scrofette: <input type="text" readonly size="15" style="padding-left: 4.7em;  border: none;"> capacità massima <input type="text" class="layout" readonly value="<%=AzFields.getCap_max_scrofe_scrofette()%>"> presenti all'atto dell'ispezione <input type="text" class="layout" readonly value="<%=AzFields.getNum_scrofe_scrofette_isp()%>"><br>
lattonzoli: <input type="text" readonly size="15" style="padding-left:8em;  border: none;"> capacità massima <input type="text" class="layout" readonly value="<%=AzFields.getCap_max_lattonzoli() %>"> presenti all'atto dell'ispezione <input type="text" class="layout" readonly value="<%=AzFields.getNum_lattonzoli_isp()%>"><br>
suinetti: <input type="text" readonly size="15" style="padding-left:8.8em;  border: none;"> capacità massima <input type="text" class="layout" readonly value="<%=AzFields.getCap_max_suinetti()%>"> presenti all'atto dell'ispezione <input type="text" class="layout" readonly value="<%=AzFields.getNum_suinetti_isp()%>"><br>
suini al grasso: <input type="text" readonly style="padding-left:4.2em;  border: none;"> capacità massima <input type="text" class="layout" readonly value="<%=AzFields.getCap_max_suini_al_grasso() %>"> presenti all'atto dell'ispezione <input type="text" class="layout" readonly value="<%=AzFields.getNum_suini_al_grasso_isp()%>"><br>
<% } else if(numAllegato.equals("3") ) { %>
<!-- Per modello 3 -->
Codice Azienda <input type="text" class="layout" size = "15" readonly value="<%=Allevamento.getN_reg() %>">Denominazione <input type="text" class="layout" size="70" readonly value="<%= (Allevamento.getName() != null && !Allevamento.getName().equals("") && !Allevamento.getName().equals("null")) ? Allevamento.getName().replace('"',' ').toUpperCase() : ""%>"><br>
Indirizzo azienda <input type="text" class="layout" size="100" readonly value="<%= ( !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("") && !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("null null null (null)")) ? (Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").toUpperCase() : "" %>"><br>
Indirizzo sede legale <input type="text" class="layout" size="100" readonly value="<%=(Allevamento.getIndirizzo_legale() != null && !Allevamento.getIndirizzo_legale().equals("")) ? Allevamento.getIndirizzo_legale().toUpperCase() : ""%>"><br>
Proprietario struttura <input type="text" class="layout" size="70" readonly value="<%=  (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"> codice fiscale <input size="27" type="text" class="layout" readonly value="<%= (Allevamento.getCf_prop() != null && !Allevamento.getCf_prop().equals("")) ? Allevamento.getCf_prop().toUpperCase() : "" %>"><br>
Proprietario degli animali <input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"><br>
Codice fiscale <input type="text" class="layout" size="20" readonly value="<%= (Allevamento.getCf_prop() != null && !Allevamento.getCf_prop().equals("")) ? Allevamento.getCf_prop().toUpperCase() : ""%>"> Tel. <input type="text" class="layout" readonly value=""><br>
Detentore <input type="text" class="layout" size="100" readonly value="<%= ( Allevamento.getDet() != null && !Allevamento.getDet().equals("") ? Allevamento.getDet().toUpperCase() : "" ) %>"><br>
Codice fiscale <input type="text" class="layout" size="20" readonly value="<%= (Allevamento.getCf_det() != null && !Allevamento.getCf_det().equals("") ) ? Allevamento.getCf_det().toUpperCase() : "" %>"> Tel. <input type="text" class="layout" readonly value=""><br>
Data inizio attività <input type="text" class="layout" readonly value="<%=Allevamento.getGiornoNascita()+ " " +Allevamento.getMeseNascita()+" " +Allevamento.getAnnoNascita() %>"><br>
Orientamento produttivo <input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getTipologia_att() !=null && !Allevamento.getTipologia_att().equals("")) ? Allevamento.getTipologia_att().toUpperCase() : "" %>"><br>
N. totale dei vitelli presenti <input type="text" class="layout" readonly value="<%=AzFields.getNum_tot_animali_presenti() %>" > n. dei vitelli di età &lt; a 8 settimane  <input type="text" class="layout" readonly value="<%=AzFields.getNum_tot_vitelli_inf_8_settimane() %>" >
Capacità massima vitelli <input type="text" class="layout" readonly value="<%=AzFields.getCap_max_animali() %>"><br>
<% } else if(numAllegato.equals("4") ) {%>  
Codice Azienda <input type="text" class="layout" size = "15" readonly value="<%=Allevamento.getN_reg() %>">Denominazione <input size="70" type="text" class="layout" readonly value="<%= (Allevamento.getName() != null && !Allevamento.getName().equals("") && !Allevamento.getName().equals("null")) ? Allevamento.getName().replace('"',' ').toUpperCase() : ""%>"><br>
Indirizzo azienda <input type="text" class="layout" size="100" readonly value="<%= ( !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("") && !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("null null null (null)")) ? (Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").toUpperCase() : "" %>"><br>
Indirizzo sede legale <input type="text" class="layout" size="100" readonly value="<%=(Allevamento.getIndirizzo_legale() != null && !Allevamento.getIndirizzo_legale().equals("")) ? Allevamento.getIndirizzo_legale().toUpperCase() : ""%>"><br>
Proprietario struttura <input type="text" class="layout" size="70" readonly value="<%=  (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"> codice fiscale <input type="text" class="layout" size="27" readonly value="<%= (Allevamento.getCf_prop() != null && !Allevamento.getCf_prop().equals("")) ? Allevamento.getCf_prop().toUpperCase() : "" %>"><br>
Proprietario degli animali <input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"><br>
Codice fiscale proprietario <input type="text" class="layout" size="20" readonly value="<%= (Allevamento.getCf_prop() != null && !Allevamento.getCf_prop().equals("")) ? Allevamento.getCf_prop().toUpperCase() : ""%>"> Tel. <input type="text" class="layout" readonly value=""><br>
Detentore <input type="text" class="layout" size="100" readonly value="<%= ( Allevamento.getDet() != null && !Allevamento.getDet().equals("") ? Allevamento.getDet().toUpperCase() : "" ) %>"><br>
Codice fiscale <input type="text" class="layout" size="20" readonly value="<%= (Allevamento.getCf_det() != null && !Allevamento.getCf_det().equals("") ) ? Allevamento.getCf_det().toUpperCase() : "" %>"> Tel. <input type="text" class="layout" readonly value=""><br>
Data inizio attività <input type="text" class="layout" readonly value="<%=Allevamento.getGiornoNascita()+ " " +Allevamento.getMeseNascita()+" " +Allevamento.getAnnoNascita() %>"><br>
Tipologia struttura <input type="text" class="layout" size="70" readonly value="<%= (Allevamento.getTipologia_struttura() !=null && !Allevamento.getTipologia_struttura().equals("")) ? Allevamento.getTipologia_struttura().toUpperCase() : "" %>"><br>
Orientamento produttivo <input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getTipologia_att() !=null && !Allevamento.getTipologia_att().equals("")) ? Allevamento.getTipologia_att().toUpperCase() : "" %>"><br>
<% }
else if(numAllegato.equals("5") ) { %> 
<!-- Per modello 5 -->
Codice Azienda <input type="text" class="layout" size = "15" readonly value="<%=Allevamento.getN_reg() %>">Denominazione <input type="text" class="layout" size="70" readonly value="<%= (Allevamento.getName() != null && !Allevamento.getName().equals("") && !Allevamento.getName().equalsIgnoreCase("null")) ? Allevamento.getName().replace('"',' ').toUpperCase(): ""%>"><br>
Indirizzo azienda <input type="text" class="layout" size="100" readonly value="<%= ( !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("") && !(Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").equals("null null null (null)")) ? (Allevamento.getIndirizzo()+ " " + Allevamento.getCap() + " " +Allevamento.getComune() +" ("+Allevamento.getProv()+")").toUpperCase() : "" %>"><br>
Indirizzo sede legale <input type="text" class="layout" size="100" readonly value="<%=(Allevamento.getIndirizzo_legale() != null && !Allevamento.getIndirizzo_legale().equals("")) ? Allevamento.getIndirizzo_legale().toUpperCase() : ""%>"><br>
Proprietario struttura <input type="text" class="layout" size="70" readonly value="<%=  (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"> codice fiscale <input size="27" type="text" class="layout" readonly value="<%= (Allevamento.getCf_prop() != null && !Allevamento.getCf_prop().equals("")) ? Allevamento.getCf_prop().toUpperCase() : "" %>"><br>
Proprietario degli animali <input type="text" class="layout" size="100" readonly value="<%= (Allevamento.getProp() != null && !Allevamento.getProp().equals("") )? Allevamento.getProp().toUpperCase() : "" %>"><br>
Codice fiscale proprietario <input type="text" class="layout" size="20" readonly value="<%= (Allevamento.getCf_prop() != null && !Allevamento.getCf_prop().equals("")) ? Allevamento.getCf_prop().toUpperCase() : ""%>"> Tel. <input type="text" class="layout" readonly value=""><br>
Detentore <input type="text" class="layout" size="100"  readonly value="<%= ( Allevamento.getDet() != null && !Allevamento.getDet().equals("") ? Allevamento.getDet().toUpperCase() : "" ) %>"><br>
Codice fiscale <input type="text" class="layout" size="20" readonly value="<%= (Allevamento.getCf_det() != null && !Allevamento.getCf_det().equals("") ) ? Allevamento.getCf_det().toUpperCase() : "" %>"> Tel. <input type="text" class="layout" readonly value=""><br>
Data inizio attività <input type="text" class="layout" readonly value="<%=Allevamento.getGiornoNascita()+ " " +Allevamento.getMeseNascita()+" " +Allevamento.getAnnoNascita() %>"><br>
N. totale capannoni <input type="text" class="layout" readonly value="<%=AzFields.getNum_tot_capannoni() %>"> n. totali capannoni attivi all'atto dell'ispezione  <input type="text" class="layout" readonly value="<%=AzFields.getNum_tot_capannoni_isp() %>"><br>  
N. totale animali presenti <input type="text" class="layout" readonly value="<%=AzFields.getNum_tot_animali_presenti() %>"> 
Capacità massima di allevamento autorizzata dalla ASL competente per il territorio: <br>
<% if(AzFields.getCap_max_animali() == 33) { %>
<span class="CheckedItem"> 33 kg/mq&nbsp;&nbsp;&nbsp;&nbsp;</span>
<% } else { %>
<span class="NocheckedItem">33 kg/mq&nbsp;&nbsp;</span>
<% } %>
<% if(AzFields.getCap_max_animali() == 39) { %>
<span class="CheckedItem"> 39 kg/mq&nbsp;&nbsp;&nbsp;&nbsp;</span>
<% } else { %>
<span class="NocheckedItem">39 kg/mq&nbsp;&nbsp;</span>
<% } %>
<% if(AzFields.getCap_max_animali() == 42) {%>
<span class="CheckedItem"> 42 kg/mq&nbsp;&nbsp;&nbsp;&nbsp;</span>
<% } else { %>
<span class="NocheckedItem">42 kg/mq&nbsp;&nbsp;</span>
<% } %>   
<% } %>  