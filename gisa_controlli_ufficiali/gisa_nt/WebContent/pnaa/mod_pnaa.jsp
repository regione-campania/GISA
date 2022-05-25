<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="Mod" class="org.aspcfs.modules.pnaa.base.ModPnaa" scope="request"/>
<jsp:useBean id="Campione" class="org.aspcfs.modules.campioni.base.Ticket" scope="request"/>
<jsp:useBean id="DpaList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StrategiaCampionamentoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="MetodoCampionamentoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ProgrammaControlloList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PrincipiAdditiviList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PrincipiAdditiviCOList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ContaminantiList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="LuogoPrelievoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="MatriceCampioneList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SpecieVegetaleList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="MetodoProduzioneList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StatoProdottoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SpecieAlimentoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PremiscelaAdditiviList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="MangimeCompostoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiNoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CampioneFinaleList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ConfezionamentoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CgRidottoList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CgCrList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SequestroPartitaList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SottoprodottiList" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<%@ page import="org.aspcfs.utils.web.*" %>
<%@ page import="java.awt.*, java.awt.image.*,  java.util.StringTokenizer, com.itextpdf.text.pdf.*, java.io.*, java.util.*,org.aspcfs.utils.web.*, com.itextpdf.text.pdf.codec.*,org.aspcfs.modules.campioni.base.SpecieAnimali" %>
<%@page import="javax.imageio.ImageIO"%>

<%@ include file="../initPage.jsp" %>

<link rel="stylesheet" type="text/css" media="screen" documentale_url="" href="pnaa/css/screen.css" />
<link rel="stylesheet" type="text/css" media="print" documentale_url="" href="pnaa/css/print.css" />

<script src='javascript/modalWindow.js'></script>
<script src="javascript/jquery-1.8.2.js"></script>
<script src="javascript/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css" href="css/modalWindow.css"></link>

<DIV ID='modalWindow' CLASS='unlocked'><P CLASS='wait'>Attendere il completamento dell'operazione...</P></DIV>

<script type="text/javascript" src="dwr/engine.js"> </script>
<script type="text/javascript" src="dwr/util.js"></script>

<%!

public static String createBarcodeImage(String code) {

Barcode128 code128 = new Barcode128();
code128.setCode(code);
java.awt.Image im = code128.createAwtImage(Color.BLACK, Color.WHITE);
int w = im.getWidth(null);
int h = im.getHeight(null);
BufferedImage img = new BufferedImage(w, h+12, BufferedImage.TYPE_INT_ARGB);
Graphics2D g2d = img.createGraphics();
g2d.drawImage(im, 0, 0, null);
g2d.drawRect(0, h, w, 12);
g2d.fillRect(0, h+1, w, 12);
g2d.setColor(Color.WHITE);
String s = code128.getCode();
g2d.setColor(Color.BLACK);
//g2d.drawString(s,h+2,34);
g2d.drawString(s,0,34);
g2d.dispose();

ByteArrayOutputStream out = new ByteArrayOutputStream();
try {
   ImageIO.write(img, "PNG", out);
} catch (IOException e) {
  e.printStackTrace();
}
byte[] bytes = out.toByteArray();

String base64bytes = com.itextpdf.text.pdf.codec.Base64.encodeBytes(bytes);
String src = "data:image/png;base64," + base64bytes;

return src;
}; 

	public static String fixValore(String code) {
	if (code ==null || code.equals("null") || code.equals(""))
		return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	else
		return code.toUpperCase();
	
}

public static String fixValoreLong(String code) {
	if (code ==null || code.equals("null") || code.equals(""))
		return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	else
		return code.toUpperCase();
	
}

public static String fixValoreShort(String code) {
	if (code ==null || code.equals("null") || code.equals(""))
		return "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	else
		return code.toUpperCase();
	
}

public static boolean contiene(String[] array, int value) {
	
	for (int i = 0; i<array.length;i++){
		if (array[i].equals(value+""))
			return true;
	}
	return false;
}

%>

<script>

function verificaStatoCampione(dataChiusura){
	
	if(dataChiusura !=null && dataChiusura != '' && dataChiusura!="null"){
		var f = document.forms['addAccount'];
		for(var i=0,fLen=f.length;i<fLen;i++){

			if (f.elements[i].type == 'radio' || f.elements[i].type=='checkbox')
		    { 
		          f.elements[i].disabled = true;
		    } 
			else if (f.elements[i].type == 'submit')
		    { 
		          
		    } 
		    else {
			    
		  		f.elements[i].readOnly = true;
		  		f.elements[i].className = 'layout';
		    }
		}
		var g = document.forms['addAccount'].getElementsByTagName("textarea");
		for(var j=0; j < g.length; j++)
			 g.item(j).className = '';

		document.getElementById('salva').style.display = 'none';
	}
// 	else
// 		document.getElementById('generaPDF').style.display = 'none';

}

function setMatriceCampione(name, form){
	
	
	if (name=="mangimeComposto"){
		form.matriceCampione.value = 2;
	}
	if (name=="premiscelaAdditivi"){
		form.matriceCampione.value = 5;
	}
	if (name=="mangimeSempliceSpecifica"){
		form.matriceCampione.value = 1;
	}
	if (name=="categoriaSottoprodotti"){
		form.matriceCampione.value = 7;
	}
	
	var matriceCampione = document.getElementsByName("matriceCampione");
	for (var i=0; i<matriceCampione.length; i++)
		if (matriceCampione[i].checked){
			matriceCampione[i].click();
			break;
		}
	
	
}

function resetMatriceCampione(radio, form){
	var val = radio.value;
	
	if (val!="1") //materia prima
		form.mangimeSempliceSpecifica.value = "";
	
	if (val!="2"){ //mangime composto
		var mangimeComposto = document.getElementsByName("mangimeComposto");
		for (var i=0; i<mangimeComposto.length; i++)
			mangimeComposto[i].checked=false;
	}
	
	if (val!="5"){ //premiscela additivi
		var premiscelaAdditivi = document.getElementsByName("premiscelaAdditivi");
		for (var i=0; i<premiscelaAdditivi.length; i++)
			premiscelaAdditivi[i].checked=false;
	}
	
	if (val!="7"){ //categoria sottoprodotti
		var categoriaSottoprodotti = document.getElementsByName("categoriaSottoprodotti");
		for (var i=0; i<categoriaSottoprodotti.length; i++)
			categoriaSottoprodotti[i].checked=false;
	}
	
}


function resetRadio(radio){
	var x = document.getElementsByName(radio);
	for (var i = 0; i<x.length; i++)
		x[i].checked = false;
}

function checkForm(form){
	
	var formTest = true;
	var messaggio = "";
	
	if (form.dpa.value==''){
		formTest = false;
		messaggio += "Campioni di alimento.\n";
	}
	if (form.metodoCampionamento.value==''){
		formTest = false;
		messaggio += "A2. Metodo di campionamento\n";
	}
	if (form.programmaControllo.value=='3' || form.programmaControllo.value=='4' || form.programmaControllo.value=='5'){
		
		if (form.principiAdditivi.value==''){
			formTest = false;
			messaggio += "A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti - Principi additivi.\n"
		}
		
		if (form.principiAdditivi.value=='1' && form.programmaControlloFASpecifica.value == ''){
			formTest = false;
			messaggio += "A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti - Principi additivi - FA additivi (Specifica).\n"
		}
		if (form.principiAdditivi.value=='2' && form.programmaControlloANSpecifica.value == ''){
			formTest = false;
			messaggio += "A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti - Principi additivi - AN additivi (Specifica).\n"
		}
		if (form.principiAdditivi.value=='3' && form.programmaControlloCISpecifica.value == ''){
			formTest = false;
			messaggio += "A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti - Principi additivi - CI additivi (Specifica).\n"
		}
		if (form.principiAdditivi.value=='4' && form.programmaControlloATSpecifica.value == ''){
			formTest = false;
			messaggio += "A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti - Principi additivi - AT additivi (Specifica).\n"
		}
		if (form.principiAdditivi.value=='5' && form.programmaControlloAOSpecifica.value == ''){
			formTest = false;
			messaggio += "A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti - Principi additivi - AO additivi (Specifica).\n"
		}
		if (form.principiAdditivi.value=='6' && form.programmaControlloAZSpecifica.value == ''){
			formTest = false;
			messaggio += "A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti - Principi additivi - AZ additivi (Specifica).\n"
		}
	
	}
	
	if (form.programmaControllo.value=='6' ){
		
		if (form.micotossineSpecifica.value==''){
			formTest = false;
			messaggio += "A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti - Micotossine (Specifica)\n"
		}

	}
	
	if (form.programmaControllo.value=='10'){
		
		if (form.principiAdditiviCO.value==''){
			formTest = false;
			messaggio += "A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti - Principi additivi CARRY OVER.\n"
		}
		
		if (form.principiAdditiviCO.value=='1' && form.programmaControlloCOFASpecifica.value == ''){
			formTest = false;
			messaggio += "A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti - Principi additivi CARRY OVER - FA additivi (Specifica).\n"
		}
		if (form.principiAdditiviCO.value=='2' && form.programmaControlloCOCISpecifica.value == ''){
			formTest = false;
			messaggio += "A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti - Principi additivi CARRY OVER - CI additivi (Specifica).\n"
		}
		
	}
	
	if (form.programmaControllo.value=='11'){
		
		if (form.contaminanti.value==''){
			formTest = false;
			messaggio += "A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti - Contaminanti.\n"
		}
		
		if (form.contaminanti.value=='1' && form.programmaControlloINCISpecifica.value == ''){
			formTest = false;
			messaggio += "A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti - Contaminanti - CI additivi (Specifica).\n"
		}
		if (form.contaminanti.value=='2' && form.programmaControlloINRASpecifica.value == ''){
			formTest = false;
			messaggio += "A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti - Concaminanti - RA additivi (Specifica).\n"
		}
		if (form.contaminanti.value=='3' && form.programmaControlloINPESpecifica.value == ''){
			formTest = false;
			messaggio += "A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti - Contaminanti - PE additivi (Specifica).\n"
		}
		
	}
	
	if (form.programmaControllo.value=='12'){
		
		if (form.altroSpecifica.value==''){
			formTest = false;
			messaggio += "A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti - Altro (Specifica).\n"
		}
			
	}
	
	if (form.luogoPrelievo.value==''){
		formTest = false;
		messaggio += "A5. Luogo di prelievo\n"
	}
	
	if (form.telefono.value==''){
		formTest = false;
		messaggio += "A16. Telefono\n"
	}
	
	if (form.matriceCampione.value==''){
		formTest = false;
		messaggio += "B1. Matrice del campione\n";
	}
	if (form.matriceCampione.value=='1' && form.mangimeSempliceSpecifica.value==''){
		formTest = false;
		messaggio += "B1. Matrice del campione - Materia prima/mangime semplice\n";
	}
	if (form.matriceCampione.value=='2' && form.mangimeComposto.value==''){
		formTest = false;
		messaggio += "B1. Matrice del campione - Mangime composto\n";
	}
	if (form.matriceCampione.value=='5' && form.premiscelaAdditivi.value==''){
		formTest = false;
		messaggio += "B1. Matrice del campione - Premiscela di additivi\n";
	}
	if (form.matriceCampione.value=='7' && form.categoriaSottoprodotti.value==''){
		formTest = false;
		messaggio += "B1. Matrice del campione - Categoria sottoprodotti\n";
	}
	
	if (form.trattamentoMangime.value==''){
		formTest = false;
		messaggio += "B2. Trattamento applicato al mangime prelevato\n"
	}
	if (form.confezionamento.value==''){
		formTest = false;
		messaggio += "B3. Confezionamento\n"
	}
	if (form.ragioneSocialeDittaProduttrice.value==''){
		formTest = false;
		messaggio += "B4. Ragione sociale ditta produttrice\n"
	}
	if (form.indirizzoDittaProduttrice.value==''){
		formTest = false;
		messaggio += "B5. Indirizzo ditta produttrice\n"
	}
	
	var specieAlimentoDestinatoCheck = false;
	var checkboxesSpecieAlimentoDestinato = form.querySelectorAll('input[type=checkbox]');
	for (var i=0; i<checkboxesSpecieAlimentoDestinato.length; i++){
		if (checkboxesSpecieAlimentoDestinato[i].name.startsWith("specieAlimentoDestinato") && checkboxesSpecieAlimentoDestinato[i].checked){
			specieAlimentoDestinatoCheck=true;
			break;
		}
	}
	
	if (!specieAlimentoDestinatoCheck){
		formTest = false;
		messaggio += "B6. Specie e categoria animale a cui l'alimento e' destinato\n"
	}
	if (form.metodoProduzione.value==''){
		formTest = false;
		messaggio += "B7. Metodo di produzione\n"
	}
	if (form.nomeCommercialeMangime.value==''){
		formTest = false;
		messaggio += "B8. Nome commerciale del mangime\n"
	} 
	
	var statoProdottoCheck = false;
	var checkboxesStatoProdotto = form.querySelectorAll('input[type=checkbox]');
	for (var i=0; i<checkboxesStatoProdotto.length; i++){
		if (checkboxesStatoProdotto[i].name.startsWith("statoProdottoPrelievo") && checkboxesStatoProdotto[i].checked){
			statoProdottoCheck=true;
			break;
		}
	}
	
	if (!statoProdottoCheck){
		formTest = false;
		messaggio += "B9. Stato del prodotto al momento del prelievo\n"
	}			
	if (form.responsabileEtichettatura.value==''){
		formTest = false;
		messaggio += "B10. Ragione sociale responsabile etichettatura\n"
	}
	if (form.indirizzoResponsabileEtichettatura.value==''){
		formTest = false;
		messaggio += "B11. Indirizzo responsabile etichettatura\n"
	}
	if (form.paeseProduzione.value==''){
		formTest = false;
		messaggio += "B12. Paese di produzione\n"
	}
	if (form.dataProduzione.value==''){
		formTest = false;
		messaggio += "B13. Data di produzione\n"
	}
	if (form.dataScadenza.value==''){
		formTest = false;
		messaggio += "B14. Data di scadenza\n"
	}
	if (form.numLotto.value==''){
		formTest = false;
		messaggio += "B15. Numero di lotto\n"
	}
	if (form.dimensioneLotto.value==''){
		formTest = false;
		messaggio += "B16. Dimensione di lotto\n"
	}
	if (form.ingredienti.value==''){
		formTest = false;
		messaggio += "B17. Ingredienti\n"
	}
	
	
	if (!formTest){
		alert("I dati non possono essere salvati. Controllare i seguenti errori o campi non valorizzati: \n\n" + messaggio);
		return false;
	}
	
	if (confirm('Salvare i dati inseriti?')){
		loadModalWindow();
		form.submit();
	}
					
}

</script>


<form name="addAccount"	action="GestionePnaa.do?command=Save&auto-populate=true" method="post">

<!-- INTESTAZIONE -->

<%@ include file="mod_pnaa_header.jsp" %>

<!-- DETTAGLIO CAMPIONE -->

L'anno <label class="layout"><%= fixValoreShort(Mod.getCampioneAnno())%></label> addi' <label class="layout"><%= fixValoreShort(Mod.getCampioneGiorno())%></label> del mese di <label class="layout"><%= fixValoreShort(Mod.getCampioneMese())%></label> alle ore <input type="text" class="editField" size="5" id="ore" name="ore" value="<%= toHtml(Mod.getCampioneOre())%>"/> alla presenza del Sig. <input type="text" class="editField" name="campionePresente" id="campionePresente" value="<%=toHtml(Mod.getCampionePresente())%>"/>, nella sua qualita' di titolare/rappresentante/detentore della merce, i sottoscritti dr. <label class="layout"><%= fixValore(Mod.getCampioneSottoscritto())%></label>, dopo essersi qualificati e aver fatto conoscere lo scopo della visita, hanno proceduto al prelievo di n. <input type="number" class="editField" size="4" min="0" step="1" name="numPrelevati" id="numPrelevati" value="<%= fixValore(Mod.getCampioneNumPrelevati())%>"/> campioni di ALIMENTO (*):<br/> 

<% for (int i = 0; i<DpaList.size(); i++){ 
LookupElement thisElement = (LookupElement) DpaList.get(i);%>
<input type="radio" name="dpa" id="dpa<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>"
<%=(Mod.getIdDpa() == thisElement.getCode() ) ? "checked" : ""  %>> <%=thisElement.getDescription() %> <br/>
<% } %>

<br/><br/>

<!-- SEZIONE A -->

<b>A. PARTE GENERALE</b><br/><br/>

<table width="100%">
<col width="50%">
<tr><th colspan="2"><b>A1. Strategia di campionamento</b></th></tr>
<% for (int i = 0; i<StrategiaCampionamentoList.size(); i++){ 
LookupElement thisElement = (LookupElement) StrategiaCampionamentoList.get(i);%>
<%=(i==0) ? "<tr>" : "" %>
<td>
<input type="radio" disabled name="strategiaCampionamento" id="strategiaCampionamento<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>"  
<%=(Mod.getIdStrategiaCampionamento() == thisElement.getCode() ) ? "checked" : ""  %>/>
<%=thisElement.getDescription() %> </td> 
<%=(i%2==1) ? "</tr><tr>" : "" %>
<% } %>
</tr></table>


<table width="100%">
<col width="50%">
<tr><th colspan="2"><b>A2. Metodo di campionamento (*)</b></th></tr>
<% for (int i = 0; i<MetodoCampionamentoList.size(); i++){ 
LookupElement thisElement = (LookupElement) MetodoCampionamentoList.get(i);%>
<%=(i==0) ? "<tr>" : "" %>
<td>
<input type="radio" name="metodoCampionamento" id="metodoCampionamento<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>" 
<%=(Mod.getIdMetodoCampionamento() == thisElement.getCode() ) ? "checked" : ""  %>> <%=thisElement.getDescription() %></td>
<%=(i%2==1) ? "</tr><tr>" : "" %>
<% } %>
</tr></table>

<table width="100%">
<col width="33%"><col width="33%">
<tr><th colspan="3"><b>A3. Programma di controllo nell'ambito del Pnaa e accertamenti richiesti (*)</b></th></tr>
<% for (int i = 0; i<ProgrammaControlloList.size(); i++){ 
LookupElement thisElement = (LookupElement) ProgrammaControlloList.get(i);%>
<%=(i==0) ? "<tr>" : "" %>
<td valign="top">

<% if (thisElement.getCode()==3) {  //Titolo, uso illecito, uso improprio %> 
<input type="checkbox" disabled <%= (Mod.getIdProgrammaControllo()==3 || Mod.getIdProgrammaControllo()==4 || Mod.getIdProgrammaControllo()==5) ? "checked" : "" %>/> <b>Principi farmacologicamente attivi e additivi</b>
<%} %>

<input type="radio" disabled name="programmaControllo" id="programmaControllo<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>" 
<%=(Mod.getIdProgrammaControllo() == thisElement.getCode() ) ? "checked" : ""  %>> <b><%=thisElement.getDescription() %></b> 
<% if ((thisElement.getCode()==3 || thisElement.getCode()==4 || thisElement.getCode()==5) && Mod.getIdProgrammaControllo()==thisElement.getCode()) { //Titolo, uso illecito, uso improprio %> 

<table width="100%">
<col width="50%">
<% for (int j = 0; j<PrincipiAdditiviList.size(); j++){ 
LookupElement thisElement2 = (LookupElement) PrincipiAdditiviList.get(j);%>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;
<input type="radio" name="principiAdditivi" id="principiAdditivi<%=thisElement2.getCode() %>" value="<%=thisElement2.getCode() %>" 
<%=(Mod.getIdPrincipiAdditivi() == thisElement2.getCode() ) ? "checked" : ""  %>> <%=thisElement2.getDescription() %>

<% if (thisElement2.getCode()==1) { //Principi farm. attivi %> 
<b>( Specificare: </b> <input type="text" class="editField" name="programmaControlloFASpecifica" id="programmaControlloFASpecifica" value="<%=toHtml(Mod.getProgrammaControlloFASpecifica())%>"/> )
<%} %> 
<% if (thisElement2.getCode()==2) { //additivi nutrizionali %> 
<b>( Specificare: </b> <input type="text" class="editField" name="programmaControlloANSpecifica" id="programmaControlloANSpecifica" value="<%=toHtml(Mod.getProgrammaControlloANSpecifica())%>"/> )
<%} %> 
<% if (thisElement2.getCode()==3) { //cocciodiostatici/istomonostatici %> 
<b>( Specificare: </b> <input type="text" class="editField" name="programmaControlloCISpecifica" id="programmaControlloCISpecifica" value="<%=toHtml(Mod.getProgrammaControlloCISpecifica())%>"/> )
<%} %> 
<% if (thisElement2.getCode()==4) { //additivi tecnologici %> 
<b>( Specificare: </b> <input type="text" class="editField" name="programmaControlloATSpecifica" id="programmaControlloATSpecifica" value="<%=toHtml(Mod.getProgrammaControlloATSpecifica())%>"/> )
<%} %> 
<% if (thisElement2.getCode()==5) { //additivi organolettici %> 
<b>( Specificare: </b> <input type="text" class="editField" name="programmaControlloAOSpecifica" id="programmaControlloAOSpecifica" value="<%=toHtml(Mod.getProgrammaControlloAOSpecifica())%>"/> )
<%} %> 
<% if (thisElement2.getCode()==6) { //additivi zootecnici %> 
<b>( Specificare: </b> <input type="text" class="editField" name="programmaControlloAZSpecifica" id="programmaControlloAZSpecifica" value="<%=toHtml(Mod.getProgrammaControlloAZSpecifica())%>"/> )
<%} %> 

</td>
</tr>
<% } %>
</table>

<%} %> 
<% if (thisElement.getCode()==6 && Mod.getIdProgrammaControllo()==thisElement.getCode()) { // Micotossine %> 
<b>( Specificare: </b> <input type="text" class="editField" name="micotossineSpecifica" id="micotossineSpecifica" value="<%=toHtml(Mod.getMicotossineSpecifica())%>"/> )
<%} %> 
<% if (thisElement.getCode()==10 && Mod.getIdProgrammaControllo()==thisElement.getCode()) { //Principi farmacologicamente attivi e additivi per CARRY OVER %> 

<table width="100%">
<col width="50%">
<% for (int j = 0; j<PrincipiAdditiviCOList.size(); j++){ 
LookupElement thisElement2 = (LookupElement) PrincipiAdditiviCOList.get(j);%>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;
<input type="radio" name="principiAdditiviCO" id="principiAdditiviCO<%=thisElement2.getCode() %>" value="<%=thisElement2.getCode() %>" 
<%=(Mod.getIdPrincipiAdditiviCO() == thisElement2.getCode() ) ? "checked" : ""  %>> <%=thisElement2.getDescription() %>

<% if (thisElement2.getCode()==1) { //Principi farm. attivi %> 
<b>( Specificare: </b> <input type="text" class="editField" name="programmaControlloCOFASpecifica" id="programmaControlloCOFASpecifica" value="<%=toHtml(Mod.getProgrammaControlloCOFASpecifica())%>"/> )
<%} %> 
<% if (thisElement2.getCode()==2) { //cocciodiostatici/istomonostatici %> 
<b>( Specificare: </b> <input type="text" class="editField" name="programmaControlloCOCISpecifica" id="programmaControlloCOCISpecifica" value="<%=toHtml(Mod.getProgrammaControlloCOCISpecifica())%>"/> )
<%} %> 

</td>
</tr>

<% } %>

<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;Quantita' di P.A./Coccidiostatico aggiunta in produzione nel lotto precedente</td></tr>
<tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" class="editField" name="quantitaPa" id="quantitaPa" value="<%= toHtml(Mod.getQuantitaPa())%>"/></td></tr>

</table>

<%} %> 
<% if (thisElement.getCode()==11 && Mod.getIdProgrammaControllo()==thisElement.getCode()) { //Contaminanti inorganici e composti azotati,composti organoclorurati, radionuclidi %> 

<table width="100%">
<col width="50%">
<% for (int j = 0; j<ContaminantiList.size(); j++){ 
LookupElement thisElement2 = (LookupElement) ContaminantiList.get(j);%>
<tr>
<td>
<input type="radio" name="contaminanti" id="contaminanti<%=thisElement2.getCode() %>" value="<%=thisElement2.getCode() %>" 
<%=(Mod.getIdContaminanti() == thisElement2.getCode() ) ? "checked" : ""  %>> <%=thisElement2.getDescription() %>

<% if (thisElement2.getCode()==1) { // contaminanti inorganici e composti azotati %> 
<b>( Specificare: </b> <input type="text" class="editField" name="programmaControlloINCISpecifica" id="programmaControlloINCISpecifica" value="<%=toHtml(Mod.getProgrammaControlloINCISpecifica())%>"/> )
<%} %> 
<% if (thisElement2.getCode()==2) { //radionuclidi %> 
<b>( Specificare: </b> <input type="text" class="editField" name="programmaControlloINRASpecifica" id="programmaControlloINRASpecifica" value="<%=toHtml(Mod.getProgrammaControlloINRASpecifica())%>"/> )
<%} %> 
<% if (thisElement2.getCode()==3) { //pesticidi %> 
<b>( Specificare: </b> <input type="text" class="editField" name="programmaControlloINPESpecifica" id="programmaControlloINPESpecifica" value="<%=toHtml(Mod.getProgrammaControlloINPESpecifica())%>"/> )
<%} %> 

</td>
</tr>
<% } %>
</table>

<%} %> 
<% if (thisElement.getCode()==12 && Mod.getIdProgrammaControllo()==thisElement.getCode()) { //Altro %> 
<b>( Specificare: </b> <input type="text" class="editField" name="altroSpecifica" id="altroSpecifica" value="<%=toHtml(Mod.getAltroSpecifica())%>"/> )
<%} %> 
</td>

<% if (thisElement.getCode()==2){ %></tr><tr><%} %>
<% if (thisElement.getCode()==6){ %></tr><tr><%} %>
<% if (thisElement.getCode()==9){ %></tr><tr><%} %>
<% if (thisElement.getCode()==5){ %></tr><tr><%} %>
<% if (thisElement.getCode()==10){ %></tr><tr><%} %>
<% if (thisElement.getCode()==11){ %></tr><tr><%} %>

<% } %>
</tr></table>

<table width="100%">
<tr><th><b>A4. Prelevatore (Nome e Cognome) (*)</b></th></tr>
<tr><td><label class="layout"><%= toHtml(Mod.getPrelevatore())%></label></td></tr>
</table>

<table width="100%">
<col width="50%">
<tr><th colspan="2"><b>A5. Luogo di prelievo (*)</b></th></tr>
<% for (int i = 0; i<LuogoPrelievoList.size(); i++){ 
LookupElement thisElement = (LookupElement) LuogoPrelievoList.get(i);%>
<%=(i==0) ? "<tr>" : "" %>
<td>
<input type="radio" name="luogoPrelievo" id="luogoPrelievo<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>" 
<%=(Mod.getIdLuogoPrelievo() == thisElement.getCode() ) ? "checked" : ""  %>> <%=thisElement.getDescription() %></td>
<%=(i%2==1) ? "</tr><tr>" : "" %>
<% } %>
</tr></table>

<table width="100%">
<col width="33%">
<tr><th><b>A6. Codice identificativo luogo di prelievo (*)</b></th> <th><b>Codice SINVSA</b></th>  <th><b>A7. Targa mezzo di trasporto</b></th></tr>
<tr><td><label class="layout"><%= fixValore(Mod.getCodiceLuogoPrelievo())%></label></td>
<td><label class="layout"><%= fixValore(Mod.getCampioneCodiceSinvsa())%></label></td>
<td><input type="text" class="editField" name="targaMezzo" id="targaMezzo" value="<%= toHtml(Mod.getTargaMezzo())%>"/></td></tr>
</table>

<table width="100%">
<col width="33%">
<tr><th><b>A8. Indirizzo del luogo di prelievo (*)</b></th> <th><b>A9. Comune (*)</b> </th> <th><b>A10. Provincia (*)</b></th></tr>
<tr><td><label class="layout"><%= fixValore(Mod.getIndirizzoLuogoPrelievo())%></label></td>
<td><label class="layout"><%= fixValore(Mod.getComuneLuogoPrelievo())%></label><br/></td>
<td><label class="layout"><%= fixValore(Mod.getProvinciaLuogoPrelievo())%></label></td></tr>
</table>

<table width="100%">
<col width="50%">
<tr><th colspan="2"><b>A11. Localizzazione geografica del punto di prelievo (WGS84-Formato decimale)</b></th></tr>
<tr><td> Latitudine <label class="layout"><%= fixValoreShort(Mod.getLatLuogoPrelievo())%></label></td>
<td>Longitudine <label class="layout"><%= fixValoreShort(Mod.getLonLuogoPrelievo())%></label></td></tr>
</table>

<table width="100%">
<col width="50%">
<tr><th><b>A12. Ragione sociale</b></th> <th><b>A13. Rappresentante legale (*)</b></th></tr>
<tr><td><label class="layout"><%= fixValore(Mod.getRagioneSociale())%></label></td>
<td><label class="layout"><%= fixValore(Mod.getRappresentanteLegale())%></label></td></tr>
</table>

<table width="100%">
<col width="50%">
<tr><th><b>A14. Codice fiscale (*)</b></th> <th><b>A15. Detentore (*)</b> </th></tr>
<tr><td><label class="layout"><%= fixValore(Mod.getCodiceFiscale())%></label></td>
<td><label class="layout"><%= fixValore(Mod.getDetentore())%></label></td></tr>
</table>

<table width="100%">
<tr><th>A16. Telefono (*)</b></th></tr>
<tr><td><input type="text" class="editField" name="telefono" id="telefono" value="<%= toHtml(Mod.getTelefono())%>"/></td></tr>
</table>

<br/><br/>

<div style="page-break-before:always">&nbsp; </div>

<!-- SEZIONE B -->

<b>B. INFORMAZIONI SUL CAMPIONE PRELEVATO</b><br/><br/>

<table width="100%">
<col width="33%"><col width="33%">
<tr><th colspan="3"><b>B1. Matrice del campione (*):</b></th></tr>
<% for (int i = 0; i<MatriceCampioneList.size(); i++){ 
LookupElement thisElement = (LookupElement) MatriceCampioneList.get(i);%>
<%=(i==0) ? "<tr>" : "" %>
<td>
<input type="radio" name="matriceCampione" id="matriceCampione<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>" onClick="resetMatriceCampione(this, this.form)"
<%=(Mod.getIdMatriceCampione() == thisElement.getCode() ) ? "checked" : ""  %>> <b><%=thisElement.getDescription() %></b>
<% if (thisElement.getCode()==1) {  //Materia prima/mangime semplice %> 

<b><input type="text" class="editField" name="mangimeSempliceSpecifica" id="mangimeSempliceSpecifica" value="<%=toHtml(Mod.getMangimeSempliceSpecifica())%>" onChange="setMatriceCampione(this.name, this.form)"/>

<%} %> 

<% if (thisElement.getCode()==2) { //Mangime composto %> 
<table width="100%">
<col width="50%">
<% for (int j = 0; j<MangimeCompostoList.size(); j++){ 
LookupElement thisElement2 = (LookupElement) MangimeCompostoList.get(j);%>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;
<input type="radio" name="mangimeComposto" id="mangimeComposto<%=thisElement2.getCode() %>" value="<%=thisElement2.getCode() %>" onClick="setMatriceCampione(this.name, this.form)"
<%=(Mod.getIdMangimeComposto() == thisElement2.getCode() ) ? "checked" : ""  %>> <%=thisElement2.getDescription() %>
</td>
</tr>
<% } %>
</table>

<%} %> 

<% if (thisElement.getCode()==7) { //Categoria sottoprodotti %> 
(solo per la ricerca del GTH)
<table width="100%">
<col width="50%">
<% for (int j = 0; j<SottoprodottiList.size(); j++){ 
LookupElement thisElement2 = (LookupElement) SottoprodottiList.get(j);%>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;
<input type="radio" name="categoriaSottoprodotti" id="categoriaSottoprodotti<%=thisElement2.getCode() %>" value="<%=thisElement2.getCode() %>" onClick="setMatriceCampione(this.name, this.form)"
<%=(Mod.getIdCategoriaSottoprodotti() == thisElement2.getCode() ) ? "checked" : ""  %>> <%=thisElement2.getDescription() %>
</td>
</tr>
<% } %>
</table>

<%} %> 

<% if (thisElement.getCode()==5) { //Premiscela di additivi (indicare le categorie di additivi che costituiscono la premiscela): %> 
(indicare le categorie di additivi che costituiscono la premiscela)
<table width="100%">
<col width="50%">
<% for (int j = 0; j<PremiscelaAdditiviList.size(); j++){ 
LookupElement thisElement2 = (LookupElement) PremiscelaAdditiviList.get(j);%>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;
<input type="radio" name="premiscelaAdditivi" id="premiscelaAdditivi<%=thisElement2.getCode() %>" value="<%=thisElement2.getCode() %>" onClick="setMatriceCampione(this.name, this.form)"
<%=(Mod.getIdPremiscelaAdditivi() == thisElement2.getCode() ) ? "checked" : ""  %>> <%=thisElement2.getDescription() %>
</td>
</tr>
<% } %>
</table>

<%} %>

</td>

<% if (thisElement.getCode()==2){ %></tr><tr><%} %>
<% if (thisElement.getCode()==4){ %></tr><tr><%} %>

<% } %>
</tr>
</table>

<table width="100%">
<tr><th><b>Prelievo campioni piano OGM</b> Specie vegetale dichiarata</th> </tr>
<tr><td>
<% 
for (int i = 0; i<SpecieVegetaleList.size(); i++){ 
LookupElement thisElement = (LookupElement) SpecieVegetaleList.get(i);%>
<input type="checkbox" name="specieVegetaleDichiarata<%=thisElement.getCode() %>" id="specieVegetaleDichiarata<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>" 
<%=(Mod.getListaSpecieVegetaleDichiarata()!=null && contiene(Mod.getListaSpecieVegetaleDichiarata().split(","), thisElement.getCode()) ) ? "checked" : ""  %>> <%=thisElement.getDescription() %>
<% } %>
</td>
</tr>
</table>

<table width="100%">
<col width="50%">
<tr><th><b>B2. Trattamento applicato al mangime prelevato (*):</b></th> <th><b>B3. Confezionamento (*): </b></th> </tr>
<tr><td><input type="text" class="editField" style="width: 100%"  name="trattamentoMangime" id="trattamentoMangime" value="<%= toHtml(Mod.getTrattamentoMangime())%>"/></td>
<td><% for (int i = 0; i<ConfezionamentoList.size(); i++){ 
LookupElement thisElement = (LookupElement) ConfezionamentoList.get(i);%>
<input type="radio" name="confezionamento" id="confezionamento<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>" 
<%=(Mod.getIdConfezionamento() == thisElement.getCode() ) ? "checked" : ""  %>> <%=thisElement.getDescription() %>
<% } %>
</td>
</tr>
</table>

<table width="100%">
<col width="50%">
<tr><th><b>B4. Ragione sociale ditta produttrice (*):</b></th> <th><b>B5. Indirizzo ditta produttrice (*):</b></th> </tr>
<tr><td><input type="text" class="editField" style="width: 100%" name="ragioneSocialeDittaProduttrice" id="ragioneSocialeDittaProduttrice" value="<%= toHtml(Mod.getRagioneSocialeDittaProduttrice())%>"/></td>
<td><input type="text" class="editField" style="width: 100%" name="indirizzoDittaProduttrice" id="indirizzoDittaProduttrice" value="<%= toHtml(Mod.getIndirizzoDittaProduttrice())%>"/></td>
</tr>
</table>

<table width="100%">
<tr><th><b>B6. Specie e categoria animale a cui l'alimento e' destinato (*):</b></th></tr>
<tr><td>
<% for (int i = 0; i<SpecieAlimentoList.size(); i++){ 
LookupElement thisElement = (LookupElement) SpecieAlimentoList.get(i);%>
<input type="checkbox" name="specieAlimentoDestinato<%=thisElement.getCode() %>" id="specieAlimentoDestinato<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>" 
<%=(Mod.getListaSpecieAlimentoDestinato()!=null && contiene(Mod.getListaSpecieAlimentoDestinato().split(","), thisElement.getCode()) ) ? "checked" : ""  %>> <%=thisElement.getDescription() %>
<% } %>
</td>
</tr>
</table>

<table width="100%">
<col width="50%">
<tr><th><b>B7. Metodo di produzione (*):</b></th> <th><b>B8. Nome commerciale del mangime (*):</b></th> </tr>
<tr><td>
<% for (int i = 0; i<MetodoProduzioneList.size(); i++){ 
LookupElement thisElement = (LookupElement) MetodoProduzioneList.get(i);%>
<input type="radio" name="metodoProduzione" id="metodoProduzione<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>" 
<%=(Mod.getIdMetodoProduzione() == thisElement.getCode() ) ? "checked" : ""  %>> <%=thisElement.getDescription() %>
<% } %>
</td>
<td><input type="text" class="editField" style="width: 100%"  name="nomeCommercialeMangime" id="nomeCommercialeMangime" value="<%= toHtml(Mod.getNomeCommercialeMangime())%>"/></td>
</tr>
</table>

<table width="100%">
<tr><th><b>B9. Stato del prodotto al momento del prelievo (*):</b></th></tr>
<tr><td>
<% for (int i = 0; i<StatoProdottoList.size(); i++){ 
LookupElement thisElement = (LookupElement) StatoProdottoList.get(i);%>
<input type="checkbox" name="statoProdottoPrelievo<%=thisElement.getCode() %>" id="statoProdottoPrelievo<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>" 
<%=(Mod.getListaStatoProdottoPrelievo()!=null && contiene(Mod.getListaStatoProdottoPrelievo().split(","), thisElement.getCode()) ) ? "checked" : ""  %>> <%=thisElement.getDescription() %>
<% } %>
</td>
</tr>
</table>

<table width="100%">
<col width="50%">
<tr><th><b>B10. Ragione sociale responsabile etichettatura (*): </b></th> <th><b>B11. Indirizzo responsabile etichettatura (*):</b></th> </tr>
<tr><td><input type="text" class="editField" style="width: 100%"  name="responsabileEtichettatura" id="responsabileEtichettatura" value="<%= toHtml(Mod.getResponsabileEtichettatura())%>"/></td>
<td><input type="text" class="editField" style="width: 100%"  name="indirizzoResponsabileEtichettatura" id="indirizzoResponsabileEtichettatura" value="<%= toHtml(Mod.getIndirizzoResponsabileEtichettatura())%>"/></td>
</tr>
</table>

<table width="100%">
<col width="33%">
<tr><th><b>B12. Paese di produzione (*):</b></th> <th><b>B13. Data di produzione (*):</b> </th> <th><b>B14. Data di scadenza (*):</b></th></tr>
<tr><td><input type="text" class="editField" name="paeseProduzione" id="paeseProduzione" value="<%= toHtml(Mod.getPaeseProduzione())%>"/></td>
<td><input type="date" class="editField" min="1990-01-01" max="3000-12-31" onkeydown="return false" name="dataProduzione" id="dataProduzione" value="<%= toHtml(Mod.getDataProduzione())%>"/></td>
<td><input type="date" class="editField" name="dataScadenza" id="dataScadenza" min="1990-01-01" max="3000-12-31" onkeydown="return false" value="<%= toHtml(Mod.getDataScadenza())%>"/></td>
</tr>
</table>

<table width="100%">
<col width="50%">
<tr><th><b>B15. Numero di lotto (*): </b></th> <th><b>B16. Dimensione di lotto (*):</b></th> </tr>
<tr><td><input type="text" class="editField" style="width: 100%"  name="numLotto" id="numLotto" value="<%= toHtml(Mod.getNumLotto())%>"/></td>
<td><input type="text" class="editField" style="width: 100%"  name="dimensioneLotto" id="dimensioneLotto" value="<%= toHtml(Mod.getDimensioneLotto())%>"/></td>
</tr>
</table>

<table width="100%">
<col width="50%">
<tr><th><b>B17. Ingredienti (*): </b></th> <th><b>B18. Ulteriori commenti relativi al mangime prelevato:</b></th> </tr>
<tr><td><input type="text" class="editField" style="width: 100%"  name="ingredienti" id="ingredienti" value="<%= toHtml(Mod.getIngredienti())%>"/></td>
<td><input type="text" class="editField" style="width: 100%"  name="commentiMangimePrelevato" id="commentiMangimePrelevato" value="<%= toHtml(Mod.getCommentiMangimePrelevato())%>"/></td>
</tr>
</table>  

<div style="page-break-before:always">&nbsp; </div>  

<!-- SEZIONE C -->

<b>C. LABORATORIO</b><br/><br/>

<table width="100%">
<tr><th><b>C1. Laboratorio di destinazione del campione (Specificare): </b></th> </tr>
<tr><td><input type="text" class="editField" style="width: 100%"  name="laboratorioDestinazione" id="laboratorioDestinazione" value="<%= toHtml(Mod.getLaboratorioDestinazione())%>"/></td>
</table>

<br/><br/>

<!-- SEZIONE D -->

<b>D. ULTERIORI INFORMAZIONI RELATIVE AL CAMPIONAMENTO:</b><br/><br/>

Si allega il cartellino (*) o la sua fotocopia o il documento commerciale: <br/>

<% for (int i = 0; i<SiNoList.size(); i++){ 
LookupElement thisElement = (LookupElement) SiNoList.get(i);%>
<input type="radio" name="allegaCartellino" id="allegaCartellino<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>"
<%=(Mod.getIdAllegaCartellino() == thisElement.getCode() ) ? "checked" : ""  %>> <%=thisElement.getDescription() %> <br/>
<% } %>

(*) sempre obbligatorio per ricerca OGM<br/><br/>

Si riportano di seguito, cosi' come previsto dalla normativa vigente, le modalita' di esecuzione del campionamento, atte a garantirne la rappresentativita' e l'assenza di contaminazioni, nonche' la descrizione delle attrezzature e dei contenitori utilizzati puliti, asciutti e di materiale inerte:<br/>
<input type="text" class="editField" style="width:100%" name="descrizioneAttrezzature" id="descrizioneAttrezzature" size="150" value="<%= toHtml(Mod.getDescrizioneAttrezzature())%>"/><br/><br/>
<label class="layout" style="width:100%"><%= fixValoreLong("")%></label><br/><br/>
<label class="layout" style="width:100%"><%= fixValoreLong("")%></label><br/><br/>
 
Sono stati prelevati a caso da n. <input type="number" class="editField" size="4" min="0" step="1" name="numPunti" id="numPunti" value="<%= toHtml(Mod.getNumPunti())%>"/>  punti/sacchi n. <input type="text" class="editField" name="numCE" id="numCE" value="<%= toHtml(Mod.getNumCE())%>"/> CE del peso/volume di <input type="number" class="editField" size="4" min="0" step="1" name="volume" id="volume" value="<%= toHtml(Mod.getVolume())%>"/> kg/lt.<br/><br/>

Dall'unione dei campioni elementari e' stato formato il campione globale mediante le seguenti operazioni <br/>
<input type="text" class="editField" style="width:100%" name="operazioni" id="operazioni" size="150" value="<%= toHtml(Mod.getOperazioni())%>"/><br/><br/>
<label class="layout" style="width:100%"><%= fixValoreLong("")%></label><br/><br/>
<label class="layout" style="width:100%"><%= fixValoreLong("")%></label><br/><br/>

Campione Globale di peso/volume di: <input type="number" class="editField" size="4" min="0" step="1" name="volume3" id="volume3" value="<%= toHtml(Mod.getVolume3())%>"/> kg/lt.<br/><br/>
 
Il CG <b>dopo opportuna miscelazione</b>

<% for (int i = 0; i<CgRidottoList.size(); i++){ 
LookupElement thisElement = (LookupElement) CgRidottoList.get(i);%>
<input type="radio" name="cgRidotto" id="cgRidotto<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>"
<%=(Mod.getIdCgRidotto() == thisElement.getCode() ) ? "checked" : ""  %>> <%=thisElement.getDescription() %> 
<% } %> (barrare la voce non pertinente) a CR del peso/volume di <input type="number" class="editField" size="4" min="0" step="1" name="volume2" id="volume2" value="<%= toHtml(Mod.getVolume2())%>"/> kg/lt.<br/><br/>

Il 

<% for (int i = 0; i<CgCrList.size(); i++){ 
LookupElement thisElement = (LookupElement) CgCrList.get(i);%>
<input type="radio" name="cgCr" id="cgCr<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>"
<%=(Mod.getIdCgCr() == thisElement.getCode() ) ? "checked" : ""  %>> <%=thisElement.getDescription() %> 
<% } %> <label class="documentaleNonStampare"><a href="#" onClick="resetRadio('cgCr'); return false;"><i><font size="2px">[Reset]</font></i></a></label> (barrare la voce non pertinente) e' stato sigillato e identificato con apposito cartellino e inviato per la successiva macinazione.<br/> 

Dal campione globale sono stati ottenuti i campioni finali mediante le seguenti operazioni<br/>
<input type="text" class="editField" style="width:100%" name="operazioni2" id="operazioni2" size="150" value="<%= toHtml(Mod.getOperazioni2())%>"/><br/><br/>
<label class="layout" style="width:100%"><%= fixValoreLong("")%></label><br/><br/>
<label class="layout" style="width:100%"><%= fixValoreLong("")%></label><br/><br/>
 
Dal CG sono stati ottenuti n. <input type="text" class="editField" name="numeroCF" id="numeroCF" value="<%= toHtml(Mod.getNumeroCF())%>"/> CF ognuno dei quali del peso/volume non inferiore a <input type="number" class="editField" size="4" min="0" step="1" name="quantitaGML" id="quantitaGML" value="<%= toHtml(Mod.getQuantitaGML())%>"/> g/ml, ogni CF viene sigillato e identificato con apposito cartellino.<br/>
Dichiarazioni del proprietario o detentore:<br/>
<input type="text" class="editField" style="width:100%" name="dichiarazione" id="dichiarazione" value="<%= toHtml(Mod.getDichiarazione())%>"/><br/><br/>
<label class="layout" style="width:100%"><%= fixValoreLong("")%></label><br/><br/>
<label class="layout" style="width:100%"><%= fixValoreLong("")%></label><br/><br/>
 
N <input type="number" class="editField" size="4" min="0" step="1" name="numCampioniFinaliInvio" id="numCampioniFinaliInvio" value="<%= toHtml(Mod.getNumCampioniFinaliInvio())%>"/> Campioni Finali unitamente a n. <input type="number" class="editField" size="4" min="0" step="1" name="numCopieInvio" id="numCopieInvio" value="<%= toHtml(Mod.getNumCopieInvio())%>"/> copie del presente verbale vengono inviate al <input type="text" class="editField" name="destinazioneInvio" id="destinazioneInvio" value="<%= toHtml(Mod.getDestinazioneInvio())%>"/> in data <input type="text" class="editField" name="dataInvio" id="dataInvio" value="<%= toHtml(Mod.getDataInvio())%>"/><br/><br/>
 
Conservazione del campione:  <br/>
<input type="text" class="editField" style="width:100%" name="conservazioneCampione" id="conservazioneCampione" value="<%= toHtml(Mod.getConservazioneCampione())%>"/><br/>
N. .<input type="number" class="editField" size="4" min="0" step="1" name="numCopie" id="numCopie" value="<%= toHtml(Mod.getNumCopie())%>"/> copia/e del presente verbale con n. <input type="number" class="editField" size="4" min="0" step="1" name="numCampioniFinali" id="numCampioniFinali" value="<%= toHtml(Mod.getNumCampioniFinali())%>"/> Campioni Finale/i viene/vengono consegnate al Sig <input type="text" class="editField" name="custode" id="custode" value="<%= toHtml(Mod.getCustode())%>"/> il quale custodisce:<br/>

<% for (int i = 0; i<CampioneFinaleList.size(); i++){ 
LookupElement thisElement = (LookupElement) CampioneFinaleList.get(i);%>
<input type="radio" name="campioneFinale" id="campioneFinale<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>"
<%=(Mod.getIdCampioneFinale() == thisElement.getCode() ) ? "checked" : ""  %>> <%=thisElement.getDescription() %> <br/>
<% } %>

<br/><br/>

L'operatore responsabile della partite/lotto dichiara di rinunciare ai campioni finali risultati conformi all'analisi e non utilizzati per l'analisi, che al termine del periodo di conservazione previsto dalla normativa, saranno gestiti dal Laboratorio per fini di studio e ricerca scientifici.<br/>
L'operare rinuncia ai Campioni Finali da destinare all'eventuale controperizia e controversia di cui al Regolamento (UE) n. 2017/625 <br/>

<% for (int i = 0; i<SiNoList.size(); i++){ 
LookupElement thisElement = (LookupElement) SiNoList.get(i);%>
<input type="radio" name="rinunciaCampione" id="rinunciaCampione<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>"
<%=(Mod.getIdRinunciaCampione() == thisElement.getCode() ) ? "checked" : ""  %>> <%=thisElement.getDescription() %> <br/>
<% } %>

<br/><br/>
La partita/lotto relativa al campione prelevato 
<% for (int i = 0; i<SequestroPartitaList.size(); i++){ 
LookupElement thisElement = (LookupElement) SequestroPartitaList.get(i);%>
<input type="radio" name="sequestroPartita" id="sequestroPartita<%=thisElement.getCode() %>" value="<%=thisElement.getCode() %>"
<%=(Mod.getIdSequestroPartita() == thisElement.getCode() ) ? "checked" : ""  %>> <%=thisElement.getDescription() %>
<% } %> fino all'esito dell'esame,<br/><br/>

<!-- FOOTER -->

Fatto, letto e sottoscritto. <br/><br/>

FIRMA DEL PROPRIETARIO/DETENTORE &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  VERBALIZZANTI 

<br/><br/>

<%@ include file="mod_pnaa_tabella_barcode.jsp" %>

<br/><br/>

<%@ include file="/gestione_documenti/gdpr_footer.jsp" %>

<input type="hidden" name="idCampione" id="idCampione" value="<%=Mod.getIdCampione()%>"/>

<center><input type="button" id="salva" value="SALVA" onClick="checkForm(this.form)"/></center>


</form>


<div id="stampa">
<center>
<jsp:include page="/gestione_documenti/boxDocumentaleNoAutomatico.jsp">
<jsp:param name="orgId" value="<%=request.getParameter("orgId") %>" />
<jsp:param name="stabId" value="<%=request.getParameter("idStabilimento") %>" />
<jsp:param name="tipo" value="19" />
<jsp:param name="idCU" value="<%=request.getParameter("idControllo") %>" />
<jsp:param name="url" value="<%=request.getParameter("url") %>" />
</jsp:include>
</center>
</div>
</form>

</dhv:permission>

<!--  PER SALVA & STAMPA -->
<div style="display:none"><iframe name="response" height="0" width="0"></iframe>
</div>





<script>

function rispondiCaso() {
	
	 var nomi = ["Rita", "Paolo", "Stefano", "Alessandro", "Uolter", "Antonio", "Carmela", "Viviana", "Valentino", "Riviezzo", "Preaccettazione", "Sintesis", "Miriam", "Gianluca", "Lorenza", "Masardona", "Battilocchio", "Rischio", "Impresa", "Vittoria", "Mandarino", "Ext", "US", "Caffe", "Altrove", "SPA", "Food", "Privata", "Coffee", "Angolo", "Bar"];
	 var inputs = document.getElementsByTagName('input');
	 var inputNamePrecedente="";
    for (i = 0; i < inputs.length; i++) {
    	    	
        if (inputs[i].type == 'radio' || inputs[i].type == 'checkbox') {
        	var random = Math.floor(Math.random() * 11);
          	 	if (random>5 || inputNamePrecedente!=inputs[i].name)
           			inputs[i].click();
        	}
        else if (inputs[i].type == 'text') {
        	if($(inputs[i]).attr("onkeyup")=='filtraInteri(this)'){
           		inputs[i].value = Math.floor((Math.random() * nomi.length-1) + 1) +''+ Math.floor((Math.random() * nomi.length-1) + 1) +''+ Math.floor((Math.random() * nomi.length-1) + 1);
        	}
        	else
           		inputs[i].value = nomi[Math.floor((Math.random() * nomi.length-1) + 1)] + " " + nomi[Math.floor((Math.random() * nomi.length-1) + 1)];
    	}
        else if (inputs[i].type == 'number') {
        	var random1 = Math.floor(Math.random() * 11);
        	var random2 = Math.floor(Math.random() * 11);

        	if($(inputs[i]).attr("step")=='.01')
	        	inputs[i].value = random1+'.'+random2;
	        else
	        	inputs[i].value = random1;
    	}
        
        else if (inputs[i].type == 'date') {
        	
        	var date = new Date();
        	var currentDate = date.toISOString().slice(0,10);
			inputs[i].value = currentDate;
    	}
        
        inputNamePrecedente = inputs[i].name;
          }
   		
}</script>

<%UserBean user = (UserBean) session.getAttribute("User");
if (user.getUserId()==5885) { %>	
<input type="button" id="caso" name="caso" style="color: black; background-color:yellow;" value="rispondi a caso a tutta la checklist (TEST)" onClick="rispondiCaso()"/>
<% } %>

<script>
verificaStatoCampione('<%=Campione.getClosed()%>');
</script>
