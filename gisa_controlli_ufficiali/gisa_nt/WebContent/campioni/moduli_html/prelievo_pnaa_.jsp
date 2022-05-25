<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.awt.*, java.awt.image.*,  java.util.StringTokenizer, com.itextpdf.text.pdf.*, java.io.*, java.util.*,org.aspcfs.utils.web.*, com.itextpdf.text.pdf.codec.*,org.aspcfs.modules.campioni.base.SpecieAnimali" %>

<jsp:useBean id="SpecieCategoria" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StatoProdotti" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Pnaa" class="org.aspcfs.modules.campioni.base.Pnaa" scope="request"/>

<P style="text-align:left;">
<i>ALLEGATO 1: Verbale di prelievo (PNAA)</i>
</P>
<input id="stampaId" type="button" class = "buttonClass" value ="Stampa" onclick="javascript:if( confirm('Attenzione! Controlla bene tutti i dati inseriti in quanto alla chiusura della finestra, i dati saranno persi.\nVuoi effettuare la stampa?')){return window.print();}else return false;"/>
<P style="text-align:right;"><b>
SCHEDA N.<br><br>
<br> 
<br>
</b>
</P>
<P style="text-align:center;">
<font size="4pt"><b>VERBALE DI PRELIEVO (PNAA)</b></font>
</P>
<P style="text-align:right;">
Verbale n.  <input type="text" class="editField" size="10" name="" id="" /> &nbsp;Data  <input type="text" class="editField" size="2" name="" id="" />/ <input type="text" class="editField" size="2" name="ente" id="ente" />/ <input type="text" class="editField" size="4" name="ente" id="ente" /> 
</P>
<b>ENTE DI APPARTENENZA</b> <input type="text" class="editField"  name="ente" id="ente" /></P>
<P align="left">
<b>UNITA' TERRITORIALE-DISTRETTO</b><input type="text" class="editField"  name="distretto" id="distretto" />
<P>
L'anno duemila<input class="editField" name="comuneprelievo" id="comuneprelievo" type="text"> 
addì <input class="editField" name="comuneprelievo" id="comuneprelievo" type="text">  
del mese di <input class="editField" name="comuneprelievo" id="comuneprelievo" type="text"> 
alle ore <input class="editField" size="5" name="ore" id="ore" type="text"> 
i sottoscritti dr. <input class="editField" name="nomeprelevatore1" id="nomeprelevatore1" type="text">, <br>
dopo essersi qualificati e aver fatto conoscere lo scopo della visita, alla presenza del Sig. 
<input class="editField" name="comuneprelievo" id="comuneprelievo" type="text">  <input class="editField" name="comuneprelievo" id="comuneprelievo" type="text"> 
nella sua qualità di titolare/rappresentante/detentore della merce, hanno proceduto al prelievo di n. <input class="editField" size="4" name="numcampioni" id="numcampioni" type="text"><br> 
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
  <td colspan="4"><b>A1. Strategia di campionamento</b></td>
</tr>
<tr>
  <td><input type="radio"  id="a1_1" name="a1_1" value="0"> Monitoraggio</td> 
  <td><input type="radio"  id="a1_1" name="a1_1" value="1" > Sospetto </td> </tr>
  <tr> <td><input type="radio"  id="a1_1" name="a1_1" value="2" > Sorveglianza </td>
	<td><input type="radio"  id="a1_1" name="a1_1" value="3" >EXTRAPIANO: Sorveglianza</td>
 </tr>
 <tr>
  <td><input type="radio"  id="a1_1" name="a1_1" value="3" >EXTRAPIANO: Monitoraggio</td>     
</tr>
<tr class="colorcell">
  <td colspan="4"><b>A2. Metodo di campionamento (*):</b></td>
</tr>
<tr>
  <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a2_1" id="a2_1" value="83" size="60" maxlength="60" />Individuale/singolo</td>
   <td><input type="radio" class="editNoBottom"  name="a2_3" id="a2_3" readonly="readonly" value="" size="60" maxlength="60"  />Norma di riferimento (solo se trattasi
  di una norma UE): <input type="text" class="editField"  name="norma" id="norma" /></td>
  </tr>
<tr><td><input type="radio" class="editNoBottom" readonly="readonly"  name="a2_2" id="a2_2" value="83" size="60" maxlength="60"/>Sconosciuto</td>
</tr>
<tr class="colorcell">
  <td colspan="4"><b>A3. Programma di controllo nell'ambito del PNAA e accertamenti richiesti (*):</b></td>
</tr>
<tr>
  <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_1" id="a3_1" ><b> Costituenti di origine animale vietati (BSE)</b></td>
  <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_2" id="a3_2"><b>Diossine e PCB</b></td>
  </tr>

<tr> 
  <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_3" id="a3_3" ><b> Principi farmacologicamente attivi e additivi</b></td>
  <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_10" id="a3_10" ><b>Microtossine</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="microtossine" id="microtossine" /> </td>
</tr>

  <tr>
  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_4" id="a3_4"> Principi farmacologicamente attivi<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="principi" id="principi" />)</td>
  <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_11" id="a3_11"><b>Salmonella</b></td></tr>
  </tr>
  
   <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_5" id="a3_5"> cocciodiostatici/istomonostatici<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="cocciodiostatici" id=cocciodiostatici />)</td>
   <td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_12" id="a3_12" ><b>OGM</b></td>
   </tr>
   
    <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_6" id="a3_6"> additivi tecnologici <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="tecnologici" id="tecnologici" />)</td>
    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_13" id="a3_13"> OGM autorizzato/qualitativo <br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(specificare <input type="text" class="editField"  name="ogmqual" id="ogmqual" />)</td>
    </tr>
 
 <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_7" id="a3_7"> additivi organolettici</br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="organolettici" id="organolettici" />)</td>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_14" id="a3_14"> OGM non autorizzato/quantitativo<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="ogmquant" id="ogmquant" />)</td>
 </tr>
 
 <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_8" id="a3_8"> additivi nutrizionali</br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="nutrizionali" id="nutrizionali" />)</td>
	<td><input type="radio" class="editNoBottom" readonly="readonly"  name="a3_15" id="a3_15" disabled="disabled"><b>Altro<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(specificare <input type="text" class="editField"  name="a3_altro" id="a3_altro" /></b></td>
 </tr>
 
 <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="editNoBottom" readonly="readonly"  name="a3_9" id="a3_9">  additivi zootecnici</br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (specificare <input type="text" class="editField"  name="zootecnici" id="zootecnici" />)</td>
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
  <td colspan="3"><b>A7. Targa mezzo di trasporto (*):</b></td>
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
  <td colspan="4"><b>A11. Codice identificativo luogo di prelievo (*):</b></td>
</tr>
<tr>
  <td>Latitudine: <input type="text" class="editField"  name="latitudineluogoprelievo" id="latitudineluogoprelievo" value=""/></td>
  <td>Longitudine: <input type="text" class="editField"  name="longitudineluogoprelievo" id="longitudineluogoprelievo" value=""/></td> 
</tr>
<tr class="colorcell">
  <td colspan="1"><b>A12. Ragione sociale/Proprietario animali (*):</b></td>
  <td colspan="3"><b>A13. Rappresentante legale/Proprietario animali (*):</b></td>
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
  <td colspan="1"><b>A14. Codice fiscale/Proprietario animali (*):</b></td>
  <td colspan="3"><b>A12.b. Denominazione Allevamento (*):</b></td>
</tr>
<tr>
  <td>
  	<input type="text" class="editField"  name="codfiscale" id="codfiscale" />
  	</td>
   <td><input type="text" class="editField"  name="detentore" id="detentore" /></td> 
</tr>
<tr class="colorcell">
  <td><b>A16. Telefono (*):</b></td>
  <td><b>A15.b: C.F.Ragione Sociale/C.F.Detentore (*):</b></td>
  <td><b>A15. Detentore/Responsabile sede produttiva (*):</b></td>
</tr>
<tr>
  <td><input type="text" class="editField"  name="" id="" /></td> 
  <td><input type="text" class="editField"  name="" id="" /></td> 
  <td><input type="text" class="editField"  name="" id="" /></td> 
</tr>
</table>
<br>
<br>
<br>
Note per la compilazione del verbale:<br>
1) In caso di allevamenti zootecnici i campi A12, A13 e A14 sono relativi al proprietario degli animali<br>
2) In caso di allevamenti zootecnici i campi A12.b e A15 si riferiscono al detentore o soccidario degli animali<br> 

<!-- <div style="page-break-before: always">&nbsp;</div>  -->

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

	<td><input type="radio"  readonly="readonly"  name="b1" id="b1"  value="m1" <b>Materia prima/mangime semplice<br>(matrice da catalogo REG.68/2013)</b><br/>
	&nbsp;&nbsp;&nbsp;<input class="editField" type="text" name="materia_prima" id="materia_prima" size="30" value="" /></td>
	<td><input type="radio"  readonly="readonly"  name="b1" id="b1" value="m5" ><b>Additivo per mangimi</b></td>
</tr>

<tr><td><input type="radio"  readonly="readonly"  name="b1" id="b1"  value="m2"  > <b>Mangime composto</b></td>
  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly"  name="b1_5_1" id="b1_5_1" > Additivo tecnologico (specificare) <input class="editField_fac" type="text" name="" id="" size="20" value=""/></td> 

</tr> 
<tr>
   <td><input type="radio"  readonly="readonly"  name="b1" id="b1" value="m3" > <b>Mangime completo</b></td>
   <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly"  name="b1_5_2" id="b1_5_2"> Additivo nutrizionale (specificare) 
<input class="editField_fac" type="text" name="" size="20" value="" id="" /></td>
  </tr>
  <tr>
   <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  readonly="readonly"  name="b2_2" id="b2_2"> <i>d'allattamento</i></td>
    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly"  name="b1_5_3" id="b1_5_3"> Additivo organolettico (specificare) <input class="editField_fac" type="text" name="" value="" id="" size="20" /></td> 
  </tr>
  <tr>
  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  readonly="readonly"  name="b2_3" id="b2_3" > <i> medicato </i></td>
  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly"  name="b1_5_4" id="b1_5_4" > Additivo zootecnico (specificare) <input class="editField_fac" type="text" name="" value="" id="" size="20" /></td>
  </tr>
  <tr>
  <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  readonly="readonly"  name="b2_4" id="b2_4" > <i> con coccidiostatici/istomonostatici </i></td>
     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly"  name="b1_5_5" id="b1_5_5"> Coccidiostatico/istomonostaico 
    <br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(specificare)<input class="editField_fac" type="text" name="" value="" id="" size="30" /></td>
  </tr>
 <tr>
 <td><input type="radio"  readonly="readonly"  name="b1" id="b1" value="m4" > <b>Mangime complementare</b></td>
 <td><input type="radio"  readonly="readonly"  name="b1" id="b1" value="m6" ><b>Premiscela di additivi (indicare le gategorie di additivi che costituiscono la premiscela):</b></td>
 </tr> 
     <tr>
     <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  readonly="readonly"  name="b1_4_1" id="b1_4_1"> <i>d'allattamento</i></td>
    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly"  name="b1_6_1" id="b1_6_1"> Additivo tecnologico</td>
    </tr>
 <tr>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  readonly="readonly"  name="b1_4_2" id="b1_4_2" > <i> medicato </i></td>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly"  name="b1_6_2" id="b1_6_2"> Additivo nutrizionale</td> 
 </tr>    
   <tr>
   <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  readonly="readonly"  name="b1_4_3" id="b1_4_3" > <i> con coccidiostatici/istomonostatici </i></td>
    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly"  name="b1_6_3" id="b1_6_3"> Additivo organolettico</td> 
 </tr> 
 <tr>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio"  readonly="readonly"  name="b1_4_4" id="b1_4_4" > <i> minerale </i></td>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly"  name="b1_6_4" id="b1_6_4" > Additivo zootecnico</td>
 </tr> 
 <tr>
 <td></td>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox"  readonly="readonly"  name="b1_6_5" id="b1_6_5"> Coccidiostatico/istomonostaico</td>
 </tr>  

   <tr><td><input type="radio"  readonly="readonly"  name="b1" id="b1" value="m7" > <b>Acqua di abbeverata</b></td>
   <td><input type="radio"  readonly="readonly"  name="b1" id="b1" value="m8" > <b>Articoli da masticare</b></td>
   </tr>
   <tr><td><input type="radio"  readonly="readonly"  name="b1" id="b1" value="m9"> <b>Prodotto intermendio</b></td>
   </tr>
  <tr>
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
  <td><b>B13. Data di produzione (*):</b></td>
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
  <td><b>C1. Laboratorio di destinazione del campione (*):</b>
</tr>
<tr>
  <td>Specificare <input type="text" class="editField"   id="lab_destinazione" name="lab_destinazione" value=""></td>
</tr>
<tr class="colorcell">
  <td><b>D. ULTERIORI INFORMAZIONI RELATIVE AL CAMPIONAMENTO: </b></td>
</tr>
<tr><td>
 Si allega il cartellino (*) o la sua fotocopia o il documento commerciale: <input type="radio" name="allega_cart" id="allega_cart" value="1">SI <input type="radio" name="allega_cart" id="allega_cart" value="">NO  <br>
 <p align="right">(*) sempre obbligatorio per ricerca OGM</p>
   Con le modalità riportate nell'allegato Verbale delle Operazioni di Prelievo Campioni Effettuate (VOPE) atte a
   garantire la rappresentatività e l'assenza di contaminazioni, utilizzando attrezzature e contenitori puliti, asciutti e <br>
   di materiale inerte sono stati prelevati a caso da n. <input type="text" class="editField"  size="4" name="numpunti" id="numpunti"> punti/sacchi n. <input type="text" class="editField"  size="4" name="mumcampioni" id="numcampioni"> campioni elementari del 
   peso/volume di <input type="text" class="editField"  size="4" name="peso" id="peso"> kg/lt.<br>
   Dall'unione dei campioni elementari è stato formato il campione globale del peso/volume di <input type="text" class="editField"  size="4" name="pesocampione" id="pesocampione"> kg/lt.<br>
   <input type="radio" name="d1_1" id="d1_1"> Dopo opportuna miscelazione<br/>
   <input type="radio" name="d1_1" id="d1_1"> Dopo opportuna macinazione<br/>
   <br/>
   <input type="radio" name="d1_4" id="d1_4">è stato ridotto a CG del peso/volume di kg/lt <input type="text" class="editField"  size="4" name="cgpeso" id="cgpeso"> e dal CG sono stati ottenuti n. <input type="text" class="editField"  size="4" name="cgcampioni" id="cgcampioni"> campioni 
   finali (campione di laboratorio) ognuno dei quali del peso/volume non inferiore a 500g/500ml, ogni
   campione finale viene sigillato e identificato con apposito cartellino. <br>
   <input type="radio" name="d1_4" id="d1_4"> è stato sigillato e identificato con apposito cartellino e inviato per la successiva macinazione.<br/><br/>
   Dichiarazioni del proprietario o detentore:<br/>
   <input type="text" class="editField"  size="100" name="dich_numcampioni" id="dich_numcampioni"><br>
   <input type="text" class="editField"  size="100" name="dich_numcampioni" id="dich_numcampioni"><br>
   <input type="text" class="editField"  size="100" name="dich_numcampioni" id="dich_numcampioni"><br>
   N. <input type="text" class="editField"  size="4" name="dich_numcampioni" id="dich_numcampioni"> Campioni finali unitamente a n. <input type="text" class="editField"  size="4" name="dich_numcopie" id="dich_numcopie"> copie del presente verbale vengono inviate al 
  <input type="text" class="editField"  name="dich_invio" id="dich_invio">  in data <input type="text" class="editField"  name="dich_data" id="dich_data"><br>
   Conservazione del campione <input type="text" class="editField"  size="80" name="dich_conservazione" id="dich_conservazione"><br>
   N. <input type="text" class="editField"  size="4" name="dich_numcopie" id="dich_numcopie"> copia/e del presente verbale con n. <input type="text" class="editField"  size="4" name="dich_numcampionifinali" id="dich_numcampionifinali"> campioni finale/i viene/vengono consegnate al Sig. 
   <input type="text" class="editField"  name="dich_consegnate" id="dich_consegnate"> il quale custodisce:<br/>
   <input type="radio" name="d1_5" id="d1_5"> un campione finale per conto del produttore<br/>
   <input type="radio" name="d1_5" id="d1_5"> un campione finale per conto proprio<br/>
   <br>
   <br>
   La partita/lotto relativa al campione prelevato viene/o non viene posta in sequestro fino all'esito dell'esame.
   Fatto, letto e sottoscritto.
   <br><br/>
  <tr><td>FIRMA DEL PROPRIETARIO/DETENTORE</td>
   <td>VERBALIZZANTI</td></tr>
   </td>
</tr>

</table>



