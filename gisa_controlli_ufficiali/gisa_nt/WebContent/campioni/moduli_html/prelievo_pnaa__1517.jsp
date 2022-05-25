<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>


<jsp:useBean id="SpecieCategoria" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StatoProdotti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Pnaa" class="org.aspcfs.modules.campioni.base.Pnaa" scope="request"/>




<P style="text-align:center;">
<font size="2pt">Ministero della Salute&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; PNAA 2015/2017</font><br>
<br>
<font size="4pt"><b>VERBALE DI PRELIEVO (PNAA)</b></font>
</P>


<P style="text-align:right;"><b>
ALLEGATO 1<br><br>
</b>
</P>
<P style="text-align:right;">
<b>Verbale n.  <input type="text" class="editField" size="10" name="" id="" /> &nbsp;Data  <input type="text" class="editField" size="2" name="" id="" />/ <input type="text" class="editField" size="2" name="ente" id="ente" />/ <input type="text" class="editField" size="4" name="ente" id="ente" /> 
</P>
<b>ENTE DI APPARTENENZA</b> <input type="text" class="editField"  name="ente" id="ente" /></P>
<P align="left">
<b>UNITA' TERRITORIALE-DISTRETTO</b><input type="text" class="editField"  name="distretto" id="distretto" />
<P>
</b>
L'anno duemila <input class="editField" name="comuneprelievo" id="comuneprelievo" type="text"> 
addì <input class="editField" name="comuneprelievo" id="comuneprelievo" type="text">  
del mese di <input class="editField" name="comuneprelievo" id="comuneprelievo" type="text"> 
alle ore <input class="editField" size="5" name="ore" id="ore" type="text"> alla presenza del Sig. 
<input class="editField" name="comuneprelievo" id="comuneprelievo" type="text">  <input class="editField" name="comuneprelievo" id="comuneprelievo" type="text"> 
nella sua qualità di titolare/rappresentante/detentore della merce, i sottoscritti dr. <input class="editField" name="nomeprelevatore1" id="nomeprelevatore1" type="text">,
dopo essersi qualificati e aver fatto conoscere lo scopo della visita,  hanno proceduto al prelievo di n. <input class="editField" size="4" name="numcampioni" id="numcampioni" type="text"><br> 
campioni di ALIMENTO: <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="nondpa" name="nondpa"/> per ANIMALI NON DESTINATI alla produzione di alimenti (non DPA)<br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="dpa" name="dpa" /> per ANIMALI DESTINATI alla produzione di alimenti (DPA)<br/>
</P>

<!-- PARTE A -->
<h2>A.PARTE GENERALE</h2>
<table width="100%" cellpadding="2" cellspacing="2">
	 <col width="50%">
	<col width="50%"> 
<tr class="colorcell">
  <td colspan="4"><b>A1. Strategia di campionamento (*)</b></td>
</tr>
<tr>
  <td><input type="radio"  id="a1_1" name="a1_1" value="0"> Piano Monitoraggio</td> 
  <td><input type="radio"  id="a1_1" name="a1_1" value="3" >Extra-Piano Monitoraggio</td> 
  <td><input type="radio"  id="a1_1" name="a1_1" value="1" > Sospetto </td>
   </tr>
  <tr> <td><input type="radio"  id="a1_1" name="a1_1" value="2" > Piano Sorveglianza </td>
	<td><input type="radio"  id="a1_1" name="a1_1" value="3" >Extra-Piano Sorveglianza</td>
 </tr>
 
<tr class="colorcell">
  <td colspan="4"><b>A2. Metodo di campionamento (*):</b></td>
</tr>
<tr>
  <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a2_1" id="a2_1" value="83" size="60" maxlength="60" />Individuale/singolo</td>
   <td><input type="radio" class="editNoBottom"  name="a2_3" id="a2_3" readonly="readonly" value="" size="60" maxlength="60"  />Norma di riferimento (solo se
  norma UE): <input type="text" class="editField"  name="norma" id="norma" /></td>
  </tr>
<tr><td><input type="radio" class="editNoBottom" readonly="readonly"  name="a2_2" id="a2_2" value="83" size="60" maxlength="60"/>Sconosciuto</td>
</tr>
<tr class="colorcell">
  <td colspan="4"><b>A3. Programma di controllo nell'ambito del PNAA e accertamenti richiesti (*):</b></td>
</tr>
<tr>
  <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_1" id="a3_1" ><b> Costituenti di origine animale vietati</b></td>
  <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_2" id="a3_2"><b>Diossine e PCB</b></td>
  </tr>
<tr>
  <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_1" id="a3_1" ><b> Salmonella</b></td>
  <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_10" id="a3_10" ><b>Microtossine</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="microtossine" id="microtossine" />)</td>
  </tr>
<tr>
  <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_1" id="a3_1"><b> OGM Autorizzato</b></td>
  <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_10" id="a3_10"><b>OGM Non Autorizzato</b></td>
</tr>
<tr> 
  <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_3" id="a3_3" ><b> Principi farmacologicamente attivi e additivi</b></td>
  <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_10" id="a3_10" ><b>Principi farmacologicamente attivi e additivi per Carry Over</b></td>
</tr>

  <tr>
  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_4" id="a3_4"> Principi farm. attivi<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="principi" id="principi" />)</td>
  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_4" id="a3_4"> Principi farm. attivi<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="principi" id="principi" />)</td>
  
  </tr>
   <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_5" id="a3_5"> cocciodiostatici/istomonostatici<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="cocciodiostatici" id=cocciodiostatici />)</td>
   <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_5" id="a3_5"> cocciodiostatici/istomonostatici<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="cocciodiostatici" id=cocciodiostatici />)</td>
   </tr>
  <tr>
    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_6" id="a3_6"> additivi tecnologici <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="tecnologici" id="tecnologici" />)</td>
 	<td><b>Quantità aggiunta di P.A./ Coccidiostatico in produzione del lotto precedente:</b> <input type="text" class="editField" size="50" name="a3_altro" id="a3_altro" /></td>
 	</tr>

    <tr>
    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_7" id="a3_7"> additivi organolettici</br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="organolettici" id="organolettici" />)</td>
    <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_13" id="a3_13"><b>Uso improprio</b></td>
    </tr>
 
 <tr>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_8" id="a3_8"> additivi nutrizionali</br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="nutrizionali" id="nutrizionali" />)</td>
 <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_12" id="a3_12" ><b>Presenza</b></td> 
 </tr>
 
 <tr>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_9" id="a3_9">  additivi zootecnici</br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="zootecnici" id="zootecnici" />)</td>
 <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_4" id="a3_4"> <b>Titolo</b></td> 	
 </tr>
 
 <tr>
 <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_15" id="a3_15" disabled="disabled"><b>Altro<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(specificare <input type="text" class="editField"  name="a3_altro" id="a3_altro" /></b></td>
  </tr>

 <tr><td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_16" id="a3_16"  disabled="disabled"><b>Contaminanti inorganici e composti azotati,<br/> composti organoclorurati, radionuclidi</b></td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_17" id="a3_17">contaminanti inorganici e composti azotati<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="inorganici" id="inorganici" />)</td></tr>
 <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_18" id="a3_18">composti organoclorurati<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="organoclorurati" id="organoclorurati" />)</td></tr>
 <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_19" id="a3_19">radionuclidi<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="radionuclidi" id="radionuclidi" />)</td></tr>  
<tr class="colorcell">
  <td ><b>A4. Prelevatore (Nome e Cognome)(*):</b></td>
</tr>
<tr>
  <td>
    <!-- <input type="text" class="editField"  name="nomeprelevatore2" id="nomeprelevatore2" />  --> 
    <input type="text" class="editField"  name="comuneprelievo" id="comuneprelievo" /> 
    <input type="text" class="editField"  name="comuneprelievo" id="comuneprelievo" /> 
   </td> 
</tr>
<tr class="colorcell">
  <td colspan="4"><b>A5. Luogo di prelievo (*):</b></td>
</tr>
<tr>
 <tr>
 	<td><input type="radio" class="editNoBottom"  name="a5" id="a5" > Stabilimenti di miscelazione grassi</td>
 	<td><input type="radio" class="editNoBottom"  name="a5" id="a5" > Stabilimento di produzione oli e grassi animali </td>
 </tr>
 <tr>
 	<td><input type="radio" class="editNoBottom"  name="a5" id="a5" > Stabilimento di mangimi composti</td>
 	<td><input type="radio" class="editNoBottom"  name="a5" id="a5" > Stabilimento di mangimi composti per animali da compagnia</td>
 </tr>
 <tr>
 	<td><input type="radio" class="editNoBottom"  name="a5" id="a5" > Stabilimento di produzione di additivi/premiscele</td>
 	<td><input type="radio" class="editNoBottom"  name="a5" id="a5"> Stabilimento per la produzione di BIODIESEL</td>  
 </tr>
 <tr>
 	<td><input type="radio" class="editNoBottom"  name="a5" id="a5" > Mezzo di trasporto su rotaia </td>
    <td><input type="radio" class="editNoBottom"  name="a5" id="a5" > Mezzo di trasporto aereo </td>
 </tr>
 <tr>
    <td><input type="radio" class="editNoBottom"  name="a5" id="a5" > Mezzo di trasporto su strada </td>
    <td><input type="radio" class="editNoBottom"  name="a5" id="a5" > Mezzo di trasporto su acqua </td>
 </tr>
 <tr>
  <td><input type="radio" class="editNoBottom"  name="a5" id="a5" > Deposito/Magazzinaggio </td>
  <td><input type="radio" class="editNoBottom"  name="a5" id="a5" > Impianto che produce grassi vegetali per l'alimentazione animale </td>
  </tr>
 <tr>
 <td><input type="radio" class="editNoBottom"  name="a5" id="a5"> Impianto oleochimico che produce materie prime per l'alimentazione animale </td>
 <td><input type="radio" class="editNoBottom"  name="a5" id="a5"> Vendita al dettaglio </td> 
 </tr>
 <tr>
 <td><input type="radio" class="editNoBottom"  name="a5" id="a5" > Mulino per la produzione di mangimi semplici</td>
 <td><input type="radio" class="editNoBottom"  name="a5" id="a5"> Vendita all'ingrosso/intermediario di mangimi </td>
 </tr>
 <tr>
    <td><input type="radio" class="editNoBottom"  name="a5" id="a5" > Azienda agricola</td>
    <td><input type="radio" class="editNoBottom"  name="a5" id="a5" > Azienda zootecnica che non detiene ruminanti</td>
 </tr>
 <tr>
 	<td><input type="radio" class="editNoBottom"  name="a5" id="a5"> Azienda zootecnica che detiene ruminanti</td>
    <td><input type="radio" class="editNoBottom"  name="a5" id="a5" > Attività di importazione (Primo deposito di materie prime importate) </td>
 </tr>
<tr class="colorcell">
  <td colspan="1"><b>A6. Codice identificativo luogo di prelievo (*):</b>&nbsp;&nbsp;&nbsp;</td>
  <td colspan="3"><b>A7. Targa mezzo di trasporto:</b></td>
</tr>
<tr>
  <td><input type="text" class="editField"  name="idluogoprelievo" id="idluogoprelievo" /></td>
   <td><input type="text" class="editField"  name="targamezzo" id="targamezzo" /></td> 
</tr>
<tr class="colorcell">
  <td><b>A8. Indirizzo del luogo di prelievo (*):</b></td>
  <td><b>A9. Comune (*):</b></td>
  <td><b>A10. Provincia (*):</b></td>
</tr>
<tr>
  <td>
  	<!-- <input type="text" class="editField"  name="indirizzoluogoprelievo" id="indirizzoluogoprelievo" />  -->
    <input type="text" class="editField"  name="comuneprelievo" id="comuneprelievo" /> 
  </td>
   <td>
   		<input type="text" class="editField"  name="comuneprelievo" id="comuneprelievo" /> 
   	</td>
   <td>
   		<input type="text" class="editField"  name="provinciaprelievo" id="provinciaprelievo" />		
   </td>
</tr>
<tr class="colorcell">
  <td colspan="4"><b>A11. Locacalizzazione geografica del punto di prelievo (WGS84 - Formato decimale):</b></td>
</tr>
<tr>
  <td>Latitudine: <input type="text" class="editField"  name="latitudineluogoprelievo" id="latitudineluogoprelievo" value=""/></td>
  <td>Longitudine: <input type="text" class="editField"  name="longitudineluogoprelievo" id="longitudineluogoprelievo" value=""/></td> 
</tr>
<tr class="colorcell">
  <td colspan="1"><b>A12. Ragione sociale (*):</b></td>
  <td colspan="3"><b>A13. Rappresentante legale (*):</b></td>
</tr>
<tr>
  <td>
  <input type="text" class="editField"  name="comuneprelievo" id="comuneprelievo" /> 
  </td>
  <td>
  <input type="text" class="editField"  name="comuneprelievo" id="comuneprelievo" /> 
  </td> 
</tr>
<tr class="colorcell">
  <td colspan="1"><b>A14. Codice fiscale (*):</b></td>
  <td colspan="4"><b>A15. Detentore (*):</b></td>
</tr>
<tr>
  <td>
  	<input type="text" class="editField"  name="codfiscale" id="codfiscale" />
  	</td>
   <td><input type="text" class="editField"  name="detentore" id="detentore" /></td> 
</tr>
<tr class="colorcell">
  <td><b>A16. Telefono (*):</b></td>
</tr>
<tr>
  <td><input type="text" class="editField"  name="" id="" /></td> 
</tr>
</table>
<br>
<br>
<br>

<% ArrayList<SpecieAnimali> listaSpecieSelezionate =  Pnaa.getListaSpecieAnimali();
   ArrayList<String> listaProdottiPnaa =  Pnaa.getListaProdottiPnaa();
%>
<!-- PARTE B-->
<h2>B. INFORMAZIONI SUL CAMPIONE PRELEVATO</h2>
<table width="100%" cellpadding="2" cellspacing="2">
<col width="50%">
	<col width="50%"> 
	<tr class="colorcell"> 
		<td colspan="4"><b>B1. Matrice del campione (*):</b></td> 
	</tr>
  <tr>

	<td><input type="radio"  readonly="readonly"  name="b1" id="b1"  value="m1" <b>Materia prima/mangime semplice
	&nbsp;&nbsp;&nbsp;<input class="editField" type="text" name="materia_prima" id="materia_prima" size="30" value="" /></td>
	<td><input type="radio"  readonly="readonly"  name="b1" id="b1" value="m5" ><b>Additivo per mangimi</b></td>
</tr>

<tr><td><input type="radio"  readonly="readonly"  name="b1" id="b1"  value="m2"  > <b>Mangime composto</b></td>
 	<td><input type="radio"  readonly="readonly"  name="b1" id="b1" value="m6" ><b>Premiscela di additivi (indicare le gategorie di additivi che costituiscono la premiscela):</b></td> 
</tr> 
<tr>
     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  readonly="readonly"  name="b1_4_1" id="b1_4_1"><i>Mangime completo</i></td>
    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly"  name="b1_6_1" id="b1_6_1"> Additivi tecnologici</td>
</tr>
 <tr>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  readonly="readonly"  name="b1_4_2" id="b1_4_2" > <i> Mangime complementare </i></td>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly"  name="b1_6_2" id="b1_6_2"> Additivi nutrizionali</td> 
 </tr>    
   <tr>
   <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  readonly="readonly"  name="b1_4_3" id="b1_4_3" > <i> Mangime d'allattamento </i></td>
    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly"  name="b1_6_3" id="b1_6_3"> Additivi organolettici</td> 
 </tr> 
 <tr>
<td></td>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly"  name="b1_6_4" id="b1_6_4" > Additivi zootecnici</td>
 </tr> 
   <tr><td><input type="radio"  readonly="readonly"  name="b1" id="b1" value="m7" > <b>Acqua di abbeverata</b></td>
   <td><input type="radio"  readonly="readonly"  name="b1" id="b1" value="m9"> <b>Mangime medicato/Prodotto intermendio</b></td>
   </tr>
  <tr>
  <td><b>Prelievo campioni piano</b> Specie vegetale dichiarata</td>
  <td>
  <input type="checkbox" readonly="readonly">Mais&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly">Soia&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly">Colza
  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly">Cotone&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly">Lino&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly">Riso
  <br><input type="checkbox"  readonly="readonly">Patata &nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly">Barbabietola da zucchero
  </td>
  </tr>  
  
<tr class="colorcell">
  <td colspan="1"><b>B2. Trattamento applicato al mangime prelevato (*):</b></td>
  <td colspan="3"><b>B3. Confezionamento: </b></td>
</tr>
<tr>
      <td><input type="text" class="editField"  name="trattamento" id="trattamento" /></td>
      <td><input type="text" class="editField"  name="confezionamento" id="confezionamento" /></td> 
</tr>
<tr class="colorcell">
  <td colspan="1"><b>B4. Ragione sociale ditta produttrice (*):</b></td>
  <td colspan="3"><b>B5. Indirizzo ditta produttrice (*):</b></td>
</tr>
<tr>
 <td><input type="text" class="editField"  name="ragionesocialeditta" id="ragionesocialeditta" /></td>
 <td><input type="text" class="editField"  name="indirizzoditta" id="indirizzoditta" /></td>
</tr>

 <table width="100%" cellpadding="2" cellspacing="2">
<col width="50%">
	<col width="50%"> 
<tr class="colorcell">

  <td colspan="4"><b>B6. Specie e categoria animale a cui l'alimento è destinato (*):</b></td>
</tr>
</table></td></tr>

  <td colspan="4"><table width="100%" cellpadding="2" cellspacing="2">
<col width="25%">
	<col width="25%"><col width="25%"><col width="25%"> 
   <% int count = -1; %>
		 <%
		  
		
 	  		 Iterator itMc = SpecieCategoria.iterator();
		        while (itMc.hasNext() ){
		     	   LookupElement el = (LookupElement)itMc.next();
		     	   count++;
		     	
		    %>
		    
		    <%if(count == 0) { %>
		    <tr>
		    <% } %>
		    <td>
		     	  <input type="checkbox"  id="<%=el.getCode() %>" name="<%=el.getCode() %>" ><%=el.getDescription() %>
		    </td>
		     <%if(count == 3) { %>
		    </tr>
		    <%
		    count=-1;
		     }
		    
		    	}//chiudo while	
 	  	   
		    %>

</td></table>
</table>
<table width="100%" cellpadding="2" cellspacing="2">
<col width="50%">
	<col width="50%"> 
<tr class="colorcell">
  <td colspan="1"><b>B7. Metodo di produzione (*):</b></td>
  <td colspan="3"><b>B8. Nome commerciale del mangime (*):</b></td>
</tr>
<tr> <td><input type="radio"  id="b7" name="b7" value="" >Biologico</td> <td><input type="text" class="editField"   id="nomemangime" name="nomemangime"></td></tr>
<tr>  <td><input type="radio"  id="b8_2" name="b8_2" value="" >Convezionale</td></tr>
 <tr> <td><input type="radio"  id="b8_3" name="b8_3" value=""  >Sconosciuto (no per OGM)</td></tr>
 <td colspan="8"><table>
<tr class="colorcell">
  <td><b>B9. Stato del prodotto al momento del prelievo (*):</b></td>
</tr>
 <% int count2 = -1; %>
		 <%
		   
		 
			   Iterator itP = StatoProdotti.iterator();
	   			while (itP.hasNext() ){
	     	  	 LookupElement el2 = (LookupElement)itP.next();
	     	   	count2++;
	     	 
	    %>
	    
	    <%if(count2 == 0) { %>
	    <tr>
	    <% } %>
	    <td>
	     	  <input type="checkbox"  id="<%=el2.getCode() %>" name="<%=el2.getCode() %>"><%=el2.getDescription() %>
	    </td>
	     <%if(count2 == 2) { %>
	    </tr>
	    <%
	    count2=-1;
	     }
	    
	     	}//chiudo while
		
		    %>
</td></table>

<tr class="colorcell">
  <td colspan="1"><b>B10. Ragione sociale responsabile etichettatura (*):</b></td>
  <td colspan="3"><b>B11. Indirizzo responsabile etichettatura (*):</b></td>
</tr>
<tr>
  <td><input type="text" class="editField"  name="ragionesocialerespetichettatura" id="ragionesocialerespetichettatura" /></td>
  <td><input type="text" class="editField"  name="indirizzorespetichettatura" id="indirizzorespetichettatura" /></td> 
</tr>
<tr class="colorcell">
  <td><b>B12. Paese di produzione (*):</b></td>
  <td><b>B13. Data di produzione:</b></td>
  <td><b>B14. Data di scadenza (*):</b></td>
</tr>
<tr>
  <td><input type="text" class="editField"  name="paeseproduzione" id="paeseproduzione" /></td>
  <td><input type="text" class="editField"  name="dataproduzione" id="dataproduzione" /></td>
  <td><input type="text" class="editField"  name="datascadenza" id="datascadenza" /></td> 
</tr>
<tr class="colorcell">
  <td colspan="1"><b>B15. Numero di lotto (*):</b></td>
  <td colspan="3"><b>B16. Dimensione di lotto (*):</b></td>
</tr>
<tr>
  <td><input type="text" class="editField"  name="numlotto" id="numlotto" /></td>
   <td><input type="text" class="editField"  name="dimlotto" id="dimlotto" /></td> 
</tr>
<tr class="colorcell">
  <td colspan="1"><b>B17. Ingredienti (*):</b></td>
  <td colspan="3"><b>B18. Ulteriori commenti relativi al mangime prelevato:</b></td>
</tr>
<tr>
  <td><input type="text" class="editField"  name="ingredienti" id="ingredienti" /></td>
   <td><input type="text" class="editField"  name="commenti" id="commenti" /></td> 
</tr>
</table>
<!-- <div style="page-break-before: always">&nbsp;</div>  -->
<!-- PARTE C-->
<h2>C. LABORATORIO </h2>
<table width="100%" cellpadding="2" cellspacing="2">
	
<tr class="colorcell">
  <td><b>C1. Laboratorio di destinazione del campione (Specificare):</b>
</tr>
<tr>
  <td>Specificare <input type="text" class="editField"   id="lab_destinazione" name="lab_destinazione" value=""></td>
</tr>
<tr class="colorcell">
  <td><b>D. ULTERIORI INFORMAZIONI RELATIVE AL CAMPIONAMENTO: </b></td>
</tr>
<tr><td>
 Si allega il cartellino (*) o la sua fotocopia o il documento commerciale: <input type="radio" name="allega_cart" id="allega_cart" value="1"><b>SI</b><input type="radio" name="allega_cart" id="allega_cart" value=""><b>NO</b> (*) sempre obbligatorio per ricerca OGM. 
Con le modalità riportate nell'allegato VOPE, atte a garantirne la rappresentatività e l'assenza di contaminazioni, utilizzando attrezzature e contenitori puliti, asciutti e di materiale inerte sono stati prelevati a caso da n. <input type="text" class="editField"  size="4" name="numpunti" id="numpunti"> punti/sacchi n. <input type="text" class="editField"  size="4" name="mumcampioni" id="numcampioni"> CE del 
peso/volume di <input type="text" class="editField"  size="4" name="peso" id="peso"> kg/lt.<br>
   Dall'unione dei campioni elementari è stato formato il CG del peso/volume di <input type="text" class="editField"  size="4" name="pesocampione" id="pesocampione"> kg/lt.<br>
   Il CG <b>dopo opportuna miscelazione</b> è stato ridotto/non è stato ridotto (barrare la voce non pertinente) a CR del peso/volume di <input type="text" class="editField"  size="4" name="peso" id="peso"> kg/lt.<br>
   <input type="checkbox" name="d1_1" id="d1_1"> dal CG/CR (barrare la voce non pertinente) sono stati ottenuti n. <input type="text" class="editField"  size="4" name="peso" id="peso"> CF ognuno dei quali del peso/volume non inferiore
   a <input type="text" class="editField"  size="4" name="peso" id="peso"> g/ml, ogni CF viene sigillato e identificato con apposito cartellino.
   <br/>OPPURE<br>
    <input type="checkbox" name="d1_1" id="d1_1"> Il CG/CR (barrare la voce non pertinente) <b>è stato sigillato</b> e identificato con apposito cartellino e inviato per la successiva macinazione.<br>
   Dichiarazioni del proprietario o detentore: <textarea rows="3" cols="3"></textarea>
 
   N. <input type="text" class="editField"  size="4" name="dich_numcampioni" id="dich_numcampioni"> Campioni finali unitamente a n. <input type="text" class="editField"  size="4" name="dich_numcopie" id="dich_numcopie"> copie del presente verbale vengono inviate al 
  <input type="text" class="editField"  name="dich_invio" id="dich_invio">  in data <input type="text" class="editField"  name="dich_data" id="dich_data"><br>
   Conservazione del campione <input type="text" class="editField"  size="80" name="dich_conservazione" id="dich_conservazione"><br>
   N. <input type="text" class="editField"  size="4" name="dich_numcopie" id="dich_numcopie"> copia/e del presente verbale con n. <input type="text" class="editField"  size="4" name="dich_numcampionifinali" id="dich_numcampionifinali"> campioni finale/i viene/vengono consegnate al Sig. 
   <input type="text" class="editField"  name="dich_consegnate" id="dich_consegnate"> il quale custodisce:<br/>
   <input type="radio" name="d1_5" id="d1_5"> un campione finale per conto del produttore<br/>
   <input type="radio" name="d1_5" id="d1_5"> un campione finale per conto proprio<br/>
   La partita/lotto relativa al campione prelevato viene/o non viene posta in sequestro fino all'esito dell'esame.<br><br>
   Fatto, letto e sottoscritto.
   <br><br/>
  <tr><td>FIRMA DEL PROPRIETARIO/DETENTORE</td>
   <td>VERBALIZZANTI</td></tr>
   </td>
</tr>

</table>
