<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@ page
	import="java.util.*,java.text.DateFormat,org.aspcfs.modules.accounts.base.*,org.aspcfs.controller.SystemStatus"%>
<%@ page
	import="org.aspcfs.controller.*,org.aspcfs.utils.*,org.aspcfs.utils.web.*,org.aspcfs.modules.contacts.base.*,org.aspcfs.modules.opu.base.*"%>

<jsp:useBean id="Operatore"
	class="org.aspcfs.modules.opu.base.Operatore" scope="request" />
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean"
	scope="session" />
<jsp:useBean id="soggettoAdded"
	class="org.aspcfs.modules.opu.base.SoggettoFisico" scope="request" />
<jsp:useBean id="indirizzoAdded"
	class="org.aspcfs.modules.opu.base.Indirizzo" scope="request" />
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="regioniList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="provinceList" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
	
<!--  in caso di errore -->
<jsp:useBean id="soggettoEsistente"
	class="org.aspcfs.modules.opu.base.SoggettoFisico" scope="request" />
	<jsp:useBean id="Errore"
	class="java.lang.String" scope="request" />	
	
<link rel="stylesheet"
	href="http://code.jquery.com/ui/1.9.1/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/gestoreCodiceFiscale.js"></script>

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>


<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();

var cal17 = new CalendarPopup(); //qui c'è un controllo sulla maggiore età
cal17.showYearNavigation();
cal17.showYearNavigationInput();
var anni18fa = new Date();
anni18fa.setFullYear(anni18fa.getFullYear()-18);
cal17.addDisabledDates(formatDate(anni18fa,"yyyy-MM-dd"), null);
</SCRIPT>

<style>
.ui-combobox {
	position: relative;
	display: inline-block;
}

.ui-combobox-toggle {
	position: absolute;
	top: 0;
	bottom: 0;
	margin-left: -1px;
	padding: 0;
	/* adjust styles for IE 6/7 */ *
	height: 1.7em; *
	top: 0.1em;
}

.ui-combobox-input {
	margin: 0;
	padding: 0.3em;
}
</style>

<%
	Stabilimento temp = (Stabilimento) Operatore.getListaStabilimenti().get(0);
	LineaProduttiva temp1 = (LineaProduttiva) temp.getListaLineeProduttive().get(0);
	int id = temp1.getIdRelazioneAttivita();
	boolean Privato = false;
	boolean OperatoreCommerciale = false;
	boolean Canile = false;
	boolean Colonia = false;
	boolean Sindaco = false;
	boolean SindacoFR = false;
	boolean Importatore = false;
	
	System.out.println("ID LINEA: " + id);
	switch (id) {
	case LineaProduttiva.idAggregazionePrivato:
		Privato = true;
		break;
	case LineaProduttiva.idAggregazioneCanile:
		Canile = true;
		break;
	case LineaProduttiva.idAggregazioneSindaco:
		Sindaco = true;
		break;
	case LineaProduttiva.idAggregazioneSindacoFR:
		SindacoFR = true;
		break;
	case LineaProduttiva.idAggregazioneColonia:
		Colonia = true;
		break;
	case LineaProduttiva.IdAggregazioneOperatoreCommerciale:
		OperatoreCommerciale = true;
		break;
	case LineaProduttiva.idAggregazioneImportatore:
		Importatore=true;
		break;
	}

	Stabilimento stab = (Stabilimento) Operatore.getListaStabilimenti().get(0);
	LineaProduttiva linea = (LineaProduttiva) stab.getListaLineeProduttive().get(0);
	int idLinea = linea.getId();
	Indirizzo sedeOperativa = temp.getSedeOperativa();
	
	int sedelegale = -1;
	int sedeoperativa = -1;
	int responsabile = -1;
	if (Operatore.getSedeLegale() != null)
		sedelegale = Operatore.getSedeLegale().getIdIndirizzo();
	if (sedeOperativa != null)
		sedeoperativa = sedeOperativa.getIdIndirizzo();
	if (temp.getRappLegale() != null)
		responsabile = temp.getRappLegale().getIndirizzo().getIdIndirizzo();
%>

<script>

//recupero informazioni sui cf

function recuperaInfoSoggetto(idsoggetto){
		PopolaCombo.recuperaInfoSoggetto(idsoggetto,{callback:recuperaInfoSoggettoCallback,async:false});
}

function recuperaInfoSoggettoCallback(value)
{
document.getElementById("soggettoInfo").value = value;	
}



















//Controllo se i soggetti fisici hanno provenienza estera per rendere modificabile il cf
function checkEstero(tipo){
	if (tipo==1){ //rappresentante legale

	var cb = document.getElementById('estero');
	var CF = document.getElementById('codFiscaleSoggettoTesto');
	var CFbutton  = document.getElementById('codFiscaleSoggettoTestoButton');
	if (cb.checked==true){
		CF.readOnly=false;
		if (CFbutton!=null)
		CFbutton.style.display='none';
	}
	else{
		CF.readOnly=true;
		if (CFbutton!=null)
		CFbutton.style.display='inline';
		CF.value=null;
		}
	
	}
	else if (tipo==2){ //responsabile stabilimento
		
		var cb = document.getElementById('esteroResp');
		var CF = document.getElementById('codFiscaleSoggettoTestoResp');
		var CFbutton  = document.getElementById('codFiscaleSoggettoTestoRespButton');
		if (cb.checked==true){
			CF.readOnly=false;
			if (CFbutton!=null)
			CFbutton.style.display='none';
		}
		else{
			CF.readOnly=true;
			if (CFbutton!=null)
			CFbutton.style.display='inline';
			CF.value=null;
			}
}

}

// Controllo se i soggetti fisici hanno il cf liberamente modificabile. Disabilito il bottone di calcolacf e la checkbox di provenienza estera

function checkCfLibero(tipo){
	if (tipo==1){ //rappresentante legale

	var CFbutton  = document.getElementById('codFiscaleSoggettoTestoButton');
	if (<%=Operatore.getRappLegale().isCfLibero()%>){
		if (CFbutton!=null)
			CFbutton.disabled='disabled';
		var cb_estero = document.getElementById('estero');
		var cb_estero_label = document.getElementById('esteroLabel');
		if (cb_estero!=null){
			cb_estero.style.display='none';
			cb_estero_label.style.display='none';
		}
	}
		
	}
	else if (tipo==2){ //responsabile stabilimento
		
		var CFbutton  = document.getElementById('codFiscaleSoggettoTestoRespButton');
		if (<%=temp.getRappLegale().isCfLibero()%>){
			if (CFbutton!=null)
				CFbutton.disabled='disabled';
			var cb_estero = document.getElementById('esteroResp');
			var cb_estero_label = document.getElementById('esteroRespLabel');
			if (cb_estero!=null){
				cb_estero.style.display='none';
				cb_estero_label.style.display='none';
			}
		}
			
		}

}

//Calcolo del codice fiscale. Il parametro set serve a settare automaticamente il codice fiscale nel campo di input. In assenza, viene solo restituito il cf
function CalcolaCF(set) {
	
  		var nomeCalc=""; var cognomeCalc=""; var comuneCalc=""; var nascitaCalc ="";
  		var giorno=""; var mese=""; var anno=""; var sesso="";var comuneResidenza= "" ;
  
  		if ( document.forms[0].sesso[0].checked )
  			sesso = "M";
  		else
  			sesso = "F";
			
  		if ( document.forms[0].nome.value != "" ) {
  			nomeCalc =  document.forms[0].nome.value;
			nomeCalc=nomeCalc.replace(/^\s+|\s+$/g,"").replace(/'/g,"");
  		}
  	
  		if ( document.forms[0].cognome.value  != "" ) {
  			cognomeCalc = document.forms[0].cognome.value;
  			cognomeCalc=cognomeCalc.replace(/^\s+|\s+$/g,"").replace(/'/g,"");
  		}    
  
  		if ( document.forms[0].comuneNascita.value != "" ) {
  			comuneCalc = document.forms[0].comuneNascita.value;
  			comuneCalc=comuneCalc.replace(/^\s+|\s+$/g,"");//.replace(/'/g,"");
  			document.forms[0].comuneNascita.value=comuneCalc;
  		}  
	
  		if ( document.forms[0].dataNascita.value != "" ) {
  			nascitaCalc = document.forms[0].dataNascita.value;
  			giorno = nascitaCalc.substring(0,2);
  			mese = nascitaCalc.substring(3,5);
  			anno = nascitaCalc.substring(6,10);
  		}  
		

  	if (cognomeCalc!="" && nomeCalc!="" && giorno!= "" && mese!="" && anno!= "" && sesso!= "" && comuneCalc!=""){
  	  	codCF= CalcolaCodiceFiscaleCompleto(cognomeCalc, nomeCalc, giorno, mese, anno, sesso, comuneCalc);
  	  	if (codCF=='[Comune non presente in banca dati]'){
  	  	  	alert('[Comune di nascita non presente in banca dati. Se non è possibile modificarlo, contattare HelpDesk.]');
  	  	  	return -1;}
  	  	else
  	  	  	if (set!=null)
  	  	  	  	setCodiceFiscale(codCF);
		}
  	else{
  	  	alert('Inserire tutti i dati anagrafici del soggetto. \n-Nome\n-Cognome\n-Sesso\n-Data di nascita\n-Comune di nascita');
  	  	return -1;
  	  	}
  		return 1;
  	}
function CalcolaCFResp(set) {
	
		var nomeCalc=""; var cognomeCalc=""; var comuneCalc=""; var nascitaCalc ="";
		var giorno=""; var mese=""; var anno=""; var sesso="";var comuneResidenza= "" ;

		if ( document.forms[0].sessoResp[0].checked )
			sesso = "M";
		else
			sesso = "F";
		
		if ( document.forms[0].nomeResp.value != "" ) {
			nomeCalc =  document.forms[0].nomeResp.value;
		nomeCalc=nomeCalc.replace(/^\s+|\s+$/g,"").replace(/'/g,"");
		}
	
		if ( document.forms[0].cognomeResp.value  != "" ) {
			cognomeCalc = document.forms[0].cognomeResp.value;
			cognomeCalc=cognomeCalc.replace(/^\s+|\s+$/g,"").replace(/'/g,"");
		}    

		if ( document.forms[0].comuneNascitaResp.value != "" ) {
			comuneCalc = document.forms[0].comuneNascitaResp.value;
			comuneCalc=comuneCalc.replace(/^\s+|\s+$/g,"").replace(/'/g,"");
  			document.forms[0].comuneNascitaResp.value=comuneCalc;
		}  

		if ( document.forms[0].dataNascitaResp.value != "" ) {
			nascitaCalc = document.forms[0].dataNascitaResp.value;
			giorno = nascitaCalc.substring(0,2);
			mese = nascitaCalc.substring(3,5);
			anno = nascitaCalc.substring(6,10);
		}  
	

	if (cognomeCalc!="" && nomeCalc!="" && giorno!= "" && mese!="" && anno!= "" && sesso!= "" && comuneCalc!=""){
	  	codCF= CalcolaCodiceFiscaleCompleto(cognomeCalc, nomeCalc, giorno, mese, anno, sesso, comuneCalc);
	  	if (codCF=='[Comune non presente in banca dati]'){
	  		alert('[Comune di nascita responsabile non presente in banca dati. Se non è possibile modificarlo, contattare HelpDesk.]');
	  	  	  return -1;}
	  	else
  	  	  	if (set!=null)
  	  	  	  	setCodiceFiscaleResponsabile(codCF);
	}
	else{
		alert('Inserire tutti i dati anagrafici del responsabile stabilimento. \n-Nome\n-Cognome\n-Sesso\n-Data di nascita\n-Comune di nascita');
		return -1;}
	return 1;
	}
 
function setCodiceFiscale(cf){
	document.getElementById("codFiscaleSoggettoTesto").value=cf;
}
function setCodiceFiscaleResponsabile(cf){
	document.getElementById("codFiscaleSoggettoTestoResp").value=cf;
	
	if (cf != '<%=temp.getRappLegale().getCodFiscale()%>' ){
		document.getElementById("idSoggettoResp").value = '-1';
	}
}

function checkDatiAnagrafici(tipo){
	if (tipo==1)
		if (document.getElementById("nome").value=='' || document.getElementById("cognome").value==''  || document.getElementById("dataNascita").value=='' || document.getElementById("codFiscaleSoggettoTesto").value==''){
			alert('Dati anagrafici obbligatori.\n-Nome\n-Cognome\n-Data di nascita\n-Comune di nascita\n-Codice fiscale');
			return -1;
		}
		else if (tipo==2)
			if (document.getElementById("nomeResp").value=='' || document.getElementById("cognomeResp").value==''  || document.getElementById("dataNascitaResp").value=='' || document.getElementById("codFiscaleSoggettoTestoResp").value==''){
				alert('Dati anagrafici responsabile stabilimento obbligatori.\n-Nome\n-Cognome\n-Data di nascita\n-Comune di nascita\n-Codice fiscale');
				return -1;
			}
	return 1;
}



function checkForm(legale_originale, operativa_originale, responsabile_originale)
    {
	loadModalWindow();
		var ok = 1;
		var controlliCf=1;
		
    	  var cb_estero = document.getElementById("estero");
          var cb_esteroResp= document.getElementById("esteroResp");
          if (cb_estero != null && cb_estero.checked)
              cb_estero.value="true";

          if (cb_esteroResp != null && cb_esteroResp.checked){
	      		cb_esteroResp.value="true";          
          }

			//RICALCOLA CODICE FISCALE
			
			//RAPPRESENTANTE LEGALE
   		if (<%=!Colonia%> && <%=!Sindaco%> && <%=!SindacoFR%>  && cb_estero!=null && cb_estero.checked==false){
			if (<%=!Operatore.getRappLegale().isCfLibero()%>)
				controlliCf = CalcolaCF('set'); //se il cf non è libero, ricalcolalo e settalo
			else
				controlliCf = CalcolaCF();

			if (controlliCf == -1)
				ok=-1;
			
				}

		//RESPONSABILE STABILIMENTO
		if (<%=!Privato%> && <%=!Sindaco%> && <%=!SindacoFR%>  &&  cb_esteroResp!=null && cb_esteroResp.checked==false && (<%=Operatore.getRappLegale().getIdSoggetto()!=temp.getRappLegale().getIdSoggetto()%> || <%=Colonia%>) ){
			if (<%=!temp.getRappLegale().isCfLibero()%>)
				controlliCf = CalcolaCFResp('set');
			else
				controlliCf=CalcolaCFResp();
			if (controlliCf == -1)
				ok=-1;	
		}
	    
        var cb_legale = document.getElementById("edit_legale");
        var cb_operativa= document.getElementById("edit_operativa");
        var cb_responsabile= document.getElementById("edit_responsabile");
        var cb_rappresentante= document.getElementById("edit_rappresentante");

        if (cb_legale!= null)
      		if (document.getElementById("edit_legale").checked == false){
    			document.getElementById("via").value='<%=(Operatore.getSedeLegale()!=null)?(Operatore.getSedeLegale().getIdIndirizzo()):("")%>';
    			document.getElementById("searchcodeIdComuneSelect").value='<%=(Operatore.getSedeLegale()!=null)?(Operatore.getSedeLegale().getComune()):("")%>';
    			if(document.getElementById("searchcodeIdComune")!=null)
    				document.getElementById("searchcodeIdComune").value='<%=(Operatore.getSedeLegale()!=null)?(Operatore.getSedeLegale().getComune()):("")%>';
    			document.getElementById("searchcodeIdprovincia").value='<%=(Operatore.getSedeLegale()!=null)?(Operatore.getSedeLegale().getIdProvincia()):("")%>';
    			}
		if (cb_operativa!=null)
    		if (document.getElementById("edit_operativa").checked == false){
        		document.getElementById("idViaSedeOperativa").value='<%=sedeOperativa.getIdIndirizzo()%>';
        		document.getElementById("idComuneSedeOperativaSelect").value='<%=sedeOperativa.getComune()%>';
        		if(document.getElementById("idComuneSedeOperativa")!=null)
        			document.getElementById("idComuneSedeOperativa").value='<%=sedeOperativa.getComune()%>';
        		document.getElementById("idProvinciaSedeOperativa").value='<%=sedeOperativa.getIdProvincia()%>';
        		document.getElementById("idRegioneSedeOperativa").selectedIndex=-1;
        		
        	          	   }
		if (cb_responsabile!=null)
        	if (document.getElementById("edit_responsabile").checked == false){
            		document.getElementById("idViaResponsabile").value='<%=temp.getRappLegale().getIndirizzo().getIdIndirizzo()%>';
            		document.getElementById("idProvinciaResponsabile").value='<%=temp.getRappLegale().getIndirizzo().getIdProvincia()%>';
            		document.getElementById("idComuneResponsabile").value='<%=temp.getRappLegale().getIndirizzo().getComune()%>';
            		}	
		if (cb_rappresentante!=null)
        	if (document.getElementById("edit_rappresentante").checked == false){
            		document.getElementById("idViaRappresentante").value='<%=Operatore.getRappLegale().getIndirizzo().getIdIndirizzo()%>';
            		document.getElementById("idProvinciaRappresentante").value='<%=Operatore.getRappLegale().getIndirizzo().getIdProvincia()%>';
            		document.getElementById("idComuneRappresentante").value='<%=Operatore.getRappLegale().getIndirizzo().getComune()%>';
            		}	
		
		
		if (cb_legale!=null)
			{
			if (document.getElementById("edit_legale").checked == true){
				if (<%=!Privato%> && <%=!Sindaco%> && <%=!SindacoFR%>){ //SE NON E'UN PRIVATO SEDE LEGALE PUO' ESSERE FUORI ASL
				if (document.getElementById("viaTesto").value=='' || document.getElementById("cap").value==''
				|| document.getElementById("idRegione").selectedIndex ==0 || document.getElementById("searchcodeIdprovincia").selectedIndex ==0
				|| document.getElementById("searchcodeIdComuneSelect").selectedIndex ==0	){
				alert('ATTENZIONE! Hai selezionato -Cambia indirizzo sede legale- senza compilare tutti i campi!');
				ok=-1;
				}
				}
				else {
					if (document.getElementById("viaTesto").value==''  || document.getElementById("cap").value==''
					|| document.getElementById("searchcodeIdComuneSelect").selectedIndex ==0)	
					{
					alert('ATTENZIONE! Hai selezionato -Cambia indirizzo sede legale- senza compilare tutti i campi!');
					ok=-1;
					}
					}
				
				if(document.getElementById("cap").value=='80100')
					{
						alert('ATTENZIONE! Il cap generico 80100 non è ammesso!');
						ok=-1;
					}
				}
			}			
		else if (cb_operativa!=null)
			{
			if (document.getElementById("edit_operativa").checked == true){
				if (document.getElementById("viaSedeOperativa").value=='' || document.getElementById("cap").value==''
					|| document.getElementById("idRegioneSedeOperativa").selectedIndex ==0 || document.getElementById("idProvinciaSedeOperativa").selectedIndex ==0
					|| document.getElementById("idComuneSedeOperativaSelect").selectedIndex ==0	){
				alert('ATTENZIONE! Hai selezionato -Cambia indirizzo sede operativa- senza compilare tutti i campi!');
				ok=-1;}}
			if(document.getElementById("cap").value=='80100')
			{
				alert('ATTENZIONE! Il cap generico 80100 non è ammesso!');
				ok=-1;
			}
			}

		if (cb_responsabile!=null)
			{
				if (document.getElementById("edit_responsabile").checked == true)
				{
					if(document.getElementById("viaResponsabile").value=='' || document.getElementById("capResponsabile").value==''
						|| document.getElementById("idRegioneResponsabile").selectedIndex ==0 || document.getElementById("idProvinciaResponsabile").selectedIndex ==0
							|| document.getElementById("idComuneResponsabile").selectedIndex ==0	)
					{
						alert('ATTENZIONE! Hai selezionato -Cambia indirizzo responsabile stabilimento- senza compilare tutti i campi!');
						ok=-1;
					}
					if(document.getElementById("capResponsabile").value=='80100')
					{
						alert('ATTENZIONE! Il cap generico 80100 non è ammesso!');
						ok=-1;
					}	
				}
			}
			else if (cb_rappresentante!=null)
				if (document.getElementById("edit_rappresentante").checked == true){
					if(document.getElementById("viaRappresentante").value==''
						|| document.getElementById("idRegioneRappresentante").selectedIndex ==0 || document.getElementById("idProvinciaRappresentante").selectedIndex ==0
							|| document.getElementById("idComuneRappresentante").selectedIndex ==0	){
					alert('ATTENZIONE! Hai selezionato -Cambia indirizzo rappresentante legale- senza compilare tutti i campi!');
					ok=-1;}}			

		//INIZIO CONTROLLO DATI ANAGRAFICI
		
		if (<%=Privato%> || <%=Sindaco%> || <%=SindacoFR%>){
		//SE PRIVATO/SINDACO/SINDACOFR : telefono1, rappresentante legale
		if (document.getElementById("telefono1").value==''){
			alert('Numero di telefono obbligatorio.');
			ok=-1;
		}
			if (checkDatiAnagrafici(1)==-1)
				ok=-1;
		}
		else if (<%=Colonia%>){
		//SE COLONIA : telefono1Resp, responsabile stabilimento
		if (document.getElementById("telefono1Resp").value==''){
			alert('Numero di telefono tutore obbligatorio.');
			ok=-1;
		}
		if (checkDatiAnagrafici(2)==-1)
			ok=-1;
		}
		else if (<%=Canile%> || <%=OperatoreCommerciale%> || <%=Importatore%>){
		//SE CANILE/OPCOMM/IMP : telefono1_lp, rappresentante legale, responsabile stabilimento
		if (document.getElementById("telefono1_lp").value==''){
			alert('Numero di telefono struttura obbligatorio');
			ok=-1;
		}
		
		if (document.getElementById("mqDisponibili").value==''){
			alert('Informazione superfice disponibile per il ricovero obbligatorio');
			ok=-1;
		}else {
			if	( !(/^[0-9]+$/i.test( $("#mqDisponibili").val())) ){
        		alert('Informazione sulla superficie disponibile al ricovero deve contenere solo numeri');
        		ok=-1;
        	}
			
		}
		
		if (checkDatiAnagrafici(1)==-1)
			ok=-1;
		if (checkDatiAnagrafici(2)==-1 && <%=Operatore.getRappLegale().getIdSoggetto()!=temp.getRappLegale().getIdSoggetto()%>) //Se i due corrispondono, controlla solo i dati del rapp legale
			ok=-1;
		}
		//FINE CONTROLLO DATI ANAGRAFICI
		
		if (ok>0){
		//gestisco eventuali errori per comuni non presenti per errato import
			if (document.getElementById("searchcodeIdComuneSelect")!=null)
				if (document.getElementById("searchcodeIdComuneSelect").value=="Tutti")
					document.getElementById("searchcodeIdComuneSelect").value=-1;
			if (document.getElementById("idComuneSedeOperativaSelect")!=null)
				if (document.getElementById("idComuneSedeOperativaSelect").value=="Tutti")
					document.getElementById("idComuneSedeOperativaSelect").value=-1;
			if (document.getElementById("idComuneResponsabile")!=null)
				if (document.getElementById("idComuneResponsabile").value=="Tutti")
					document.getElementById("idComuneResponsabile").value=-1;
			if (document.getElementById("idComuneRappresentante")!=null)
				if (document.getElementById("idComuneRappresentante").value=="Tutti")
					document.getElementById("idComuneRappresentante").value=-1;
			

			
			if (document.getElementById("idRegioneSedeOperativa")!=null)
				if (document.getElementById("idRegioneSedeOperativa").value==-1)
					document.getElementById("idRegioneSedeOperativa").value='<%=sedeOperativa.getIdRegione()%>';
					
			var cfNuovo ="";
			var cfOriginale = "";
			var soggettoInfo = "";		
			var idsoggetto = "";
			
			if (document.getElementById("codFiscaleSoggettoTesto")!=null){
				cfNuovo=document.getElementById("codFiscaleSoggettoTesto").value;
				cfOriginale = document.getElementById("codFiscaleSoggettoTesto_Originale").value;
				idsoggetto = document.getElementById("idSoggetto").value;
				
				if (cfNuovo!=cfOriginale){
					recuperaInfoSoggetto(idsoggetto);
					soggettoInfo = document.getElementById("soggettoInfo").value;
					if (!confirm("Attenzione! Si sta per salvare una modifica al codice fiscale della persona fisica ("+cfOriginale+" -> "+cfNuovo+"). \n\n La modifica avra' impatto sui seguenti proprietari, collegati alla persona:\n "+soggettoInfo)) {
						loadModalWindowUnlock();
						return false;	
					}
				}
			}
			
			if (document.getElementById("codFiscaleSoggettoTestoResp")!=null){
				cfNuovo=document.getElementById("codFiscaleSoggettoTestoResp").value;
				cfOriginale = document.getElementById("codFiscaleSoggettoTestoResp_Originale").value;
				idsoggetto = document.getElementById("idSoggettoResp").value;
				if (cfNuovo!=cfOriginale){
					recuperaInfoSoggetto(idsoggetto);
					soggettoInfo = document.getElementById("soggettoInfo").value;
					if (!confirm("Attenzione! Si sta per salvare una modifica al codice fiscale della persona fisica ("+cfOriginale+" -> "+cfNuovo+"). \n\n La modifica avra' impatto sui seguenti proprietari, collegati alla persona:\n "+soggettoInfo)) {
						loadModalWindowUnlock();
						return false;	
					}
				}
			}
					
		
		if (document.getElementById("codFiscaleSoggettoResp")!=null && document.getElementById("codFiscaleSoggetto")!=null){
			if (document.getElementById("codFiscaleSoggetto").value == document.getElementById("codFiscaleSoggettoResp").value){
				//var answer=confirm("Attenzione! Il rappresentante legale ed il responsabile stabilimento coincidono. Saranno accettate solo modifiche al modulo dei dati anagrafici del rappresentante legale e replicate sul responsabile stabilimento.");
				//if (answer==true)
					document.forms[0].submit();
			}
			else 
		        document.forms[0].submit();
	        }
		else 
			 document.forms[0].submit();
        }
		loadModalWindowUnlock();
}</script>

<script>
	
	function nascondi(tipo){

     	var idRegione;
     	var searchcodeIdprovincia;
     	var searchcodeIdComuneSelect;
     	var selezionaIndirizzoLink;
     	var viaTesto;
     	var capCampo;

        if (tipo=='1') {     	
            idRegione=document.getElementById('idRegione');
    		searchcodeIdprovincia= document.getElementById('searchcodeIdprovincia');
    		searchcodeIdComuneSelect= document.getElementById('searchcodeIdComuneSelect');
    		selezionaIndirizzoLink= document.getElementById('selezionaIndirizzoLink');
    		viaTesto= document.getElementById('viaTesto');
    		capCampo= document.getElementById('cap');}
        else  if (tipo=='2') {     	
         	//idRegione=document.getElementById('idRegioneSedeOperativa');
        	//searchcodeIdprovincia= document.getElementById('idProvinciaSedeOperativa');
        	searchcodeIdComuneSelect= document.getElementById('idComuneSedeOperativaSelect');
        	selezionaIndirizzoLink= document.getElementById('selezionaIndirizzoLink2');
        	viaTesto= document.getElementById('viaSedeOperativa');
        	capCampo= document.getElementById('cap');}

        else if (tipo=='3') {     	
         	idRegione=document.getElementById('idRegioneResponsabile');
        	searchcodeIdprovincia= document.getElementById('idProvinciaResponsabile');
        	searchcodeIdComuneSelect= document.getElementById('idComuneResponsabile');
        	selezionaIndirizzoLink= document.getElementById('selezionaIndirizzoLink');
        	viaTesto= document.getElementById('viaResponsabile');
        	capCampo= document.getElementById('capResponsabile');}
        else if (tipo=='4') {     	
         	idRegione=document.getElementById('idRegioneRappresentante');
        	searchcodeIdprovincia= document.getElementById('idProvinciaRappresentante');
        	searchcodeIdComuneSelect= document.getElementById('idComuneRappresentante');
        	selezionaIndirizzoLink= document.getElementById('selezionaIndirizzoLink');
        	viaTesto= document.getElementById('viaRappresentante');}


    	
		if ((tipo=='1'&& <%=Privato%> ||<%=Sindaco%> || <%=SindacoFR%>) || tipo=='2')
		{ //se sto modificando un privato posso modificare la sede legale
		    if (tipo!='2' && !<%=Privato%> && !<%=Sindaco%> && !<%=SindacoFR%>)
		    {
			    	if (idRegione.style.display=='none')
						idRegione.style.display='inline';
			    	else
			        	idRegione.style.display='none';
			
			    	if (searchcodeIdprovincia.style.display=='none')
			   		searchcodeIdprovincia.style.display='inline';
			    	else
			   		searchcodeIdprovincia.style.display='none';
			 }
	       
			    	if (searchcodeIdComuneSelect.style.display=='none')
			   			searchcodeIdComuneSelect.style.display='inline';
			    	else
			   			searchcodeIdComuneSelect.style.display='none';
			    	
			    	if (selezionaIndirizzoLink.style.display=='none')
			    		selezionaIndirizzoLink.style.display='inline';
			    	else
			    		selezionaIndirizzoLink.style.display='none';
			    	
			    	if (viaTesto.style.display=='none')
			    		viaTesto.style.display='inline';
			    	 else
			    		viaTesto.style.display='none';
			    	
			    	if (capCampo!=null && capCampo.style.display=='none')
			    		capCampo.style.display='inline';
			    	 else if(capCampo!=null)
			    		 capCampo.style.display='none';

						
		}

		else if (tipo!='2')
		{

	        if (!<%=Privato%> && !<%=Sindaco%> && !<%=SindacoFR%>)
	        {
	    		if (idRegione.style.display=='none')
					idRegione.style.display='inline';
	    		else
	        		idRegione.style.display='none';
	
				   	if (searchcodeIdprovincia.style.display=='none')
				    	searchcodeIdprovincia.style.display='inline';
				    	else
				   		searchcodeIdprovincia.style.display='none';
		    }
			   	if (searchcodeIdComuneSelect.style.display=='none')
			    		searchcodeIdComuneSelect.style.display='inline';
			    	else
			    		searchcodeIdComuneSelect.style.display='none';
			   	
			   	if (selezionaIndirizzoLink.style.display=='none')
			   		selezionaIndirizzoLink.style.display='inline';
		    	else
		    		selezionaIndirizzoLink.style.display='none';
			   	
			    	if (viaTesto.style.display=='none')
			    		viaTesto.style.display='inline';
			    	 else
			    		viaTesto.style.display='none';
    	
    		if (capCampo!=null && capCampo.style.display=='none')
    			capCampo.style.display='inline';
    	 	else if(capCampo!=null)
    		 	capCampo.style.display='none';
	}
		
 
  }

    
</script>


<script>
$(document).ready(function(){


//CONTROLLO SE CI SONO ERRORI


<%if (Errore!=null && !Errore.equals(""))
{%>
	alert('<%=Errore%>');
	<%}%>
	
	if (<%=!Colonia%>){
	document.getElementById("via").value='<%=(Operatore.getSedeLegale()!=null)?(Operatore.getSedeLegale().getIdIndirizzo()):("")%>';
	document.getElementById("searchcodeIdComuneSelect").value='<%=(Operatore.getSedeLegale()!=null)?(Operatore.getSedeLegale().getComune()):("")%>';
	document.getElementById("searchcodeIdprovincia").value='<%=(Operatore.getSedeLegale()!=null)?(Operatore.getSedeLegale().getIdProvincia()):("")%>';
	document.getElementById("idRegione").value='<%=(Operatore.getSedeLegale()!=null)?(Operatore.getSedeLegale().getIdRegione()):("")%>';
	}
if (<%=!Privato%> && <%=!Sindaco%> && <%=!SindacoFR%>){
	document.getElementById("idViaSedeOperativa").value='<%=sedeOperativa.getIdIndirizzo()%>';
	document.getElementById("idComuneSedeOperativaSelect").value='<%=sedeOperativa.getComune()%>';
	document.getElementById("idProvinciaSedeOperativa").value='<%=sedeOperativa.getIdProvincia()%>';
	document.getElementById("idRegioneSedeOperativa").value='<%=sedeOperativa.getIdRegione()%>';
}
if (<%=!Privato%> && <%=!Sindaco%> && <%=!SindacoFR%>){
	document.getElementById("idViaResponsabile").value='<%=temp.getRappLegale().getIndirizzo().getIdIndirizzo()%>';
	document.getElementById("idComuneResponsabile").value='<%=temp.getRappLegale().getIndirizzo().getComune()%>';
	document.getElementById("idProvinciaResponsabile").value='<%=temp.getRappLegale().getIndirizzo().getIdProvincia()%>';
	document.getElementById("idRegioneResponsabile").value='<%=temp.getRappLegale().getIndirizzo().getIdRegione()%>';
}
if (<%=!Privato%> && <%=!Sindaco%> && <%=!SindacoFR%> && <%=!Colonia%>){
	document.getElementById("idViaRappresentante").value='<%=Operatore.getRappLegale().getIndirizzo().getIdIndirizzo()%>';
	document.getElementById("idComuneRappresentante").value='<%=Operatore.getRappLegale().getIndirizzo().getComune()%>';
	document.getElementById("idProvinciaRappresentante").value='<%=Operatore.getRappLegale().getIndirizzo().getIdProvincia()%>';
	document.getElementById("idRegioneRappresentante").value='<%=Operatore.getRappLegale().getIndirizzo().getIdRegione()%>';
}

	var cb_legale= document.getElementById("edit_legale");
	if (cb_legale!=null){
			cb_legale.checked=false;
			//cb_legale.style.display='none';
			}
	var cb_operativa = document.getElementById("edit_operativa");
	if (cb_operativa!=null){
		cb_operativa.checked = false;
		//cb_operativa.style.display='none';
	}
	var cb_responsabile =  document.getElementById("edit_responsabile");
	if (cb_responsabile!=null){
		cb_responsabile.checked = false;
		//cb_responsabile.style.display='none';
	}
	var cb_rappresentante =  document.getElementById("edit_rappresentante");
	if (cb_rappresentante!=null){
		cb_rappresentante.checked = false;
		//cb_responsabile.style.display='none';
	}
	if (<%=Privato%> || <%=Sindaco%> || <%=SindacoFR%>){
		cb_legale.style.display='inline';}
	else{
		cb_operativa.style.display='inline';}
	
	
	//NASCONDO LE CASELLE PER LA MODIFICA DELL'INDIRIZZO IN CARICAMENTO
	var idRegione = document.getElementById('idRegione');
	var searchcodeIdprovincia = document.getElementById('searchcodeIdprovincia');
	var searchcodeIdComuneSelect = document.getElementById('searchcodeIdComuneSelect');
	var selezionaIndirizzoLink = document.getElementById('selezionaIndirizzoLink');
	var selezionaIndirizzoLink2 = document.getElementById('selezionaIndirizzoLink2');
	var viaTesto = document.getElementById('viaTesto');
	var capCampo = document.getElementById('cap');
	var capCampoResponsabile = document.getElementById('capResponsabile');
	if (idRegione!=null)
		idRegione.style.display='none';
	if (searchcodeIdprovincia!=null)
		searchcodeIdprovincia.style.display='none';
	if (searchcodeIdComuneSelect!=null)
		searchcodeIdComuneSelect.style.display='none';
	if (selezionaIndirizzoLink!=null)
		selezionaIndirizzoLink.style.display='none';
	if (selezionaIndirizzoLink2!=null)
		selezionaIndirizzoLink2.style.display='none';
	if (viaTesto!=null)
		viaTesto.style.display='none';
	if(capCampo!=null)
		capCampo.style.dispaly='none';
	if(capCampoResponsabile!=null)
		capCampoResponsabile.style.dispaly='none';
	
	idRegione = document.getElementById('idRegioneSedeOperativa');
	searchcodeIdprovincia= document.getElementById('idProvinciaSedeOperativa');
	searchcodeIdComuneSelect= document.getElementById('idComuneSedeOperativaSelect');
	selezionaIndirizzoLink= document.getElementById('selezionaIndirizzoLink');
	selezionaIndirizzoLink2= document.getElementById('selezionaIndirizzoLink2');
	viaTesto= document.getElementById('viaSedeOperativa');
	capCampo= document.getElementById('cap');
	capCampoResponsabile= document.getElementById('capResponsabile');
	if (idRegione!=null)
		idRegione.style.display='none';
	if (searchcodeIdprovincia!=null)
		searchcodeIdprovincia.style.display='none';
	if (searchcodeIdComuneSelect!=null)
		searchcodeIdComuneSelect.style.display='none';
	if (selezionaIndirizzoLink!=null)
		selezionaIndirizzoLink.style.display='none';
	if (selezionaIndirizzoLink2!=null)
		selezionaIndirizzoLink2.style.display='none';
	if (viaTesto!=null)
		viaTesto.style.display='none';
	if (capCampo!=null)
		capCampo.style.display='none';
	if (capCampoResponsabile!=null)
		capCampoResponsabile.style.display='none';
		
 	var idRegione = document.getElementById('idRegioneResponsabile');
	var searchcodeIdprovincia= document.getElementById('idProvinciaResponsabile');
	var searchcodeIdComuneSelect= document.getElementById('idComuneResponsabile');
	var selezionaIndirizzoLink= document.getElementById('selezionaIndirizzoLink');
	var selezionaIndirizzoLink2= document.getElementById('selezionaIndirizzoLink2');
	var viaTesto= document.getElementById('viaResponsabile');
	if (idRegione!=null)
		idRegione.style.display='none';
	if (searchcodeIdprovincia!=null)
		searchcodeIdprovincia.style.display='none';
	if (searchcodeIdComuneSelect!=null)
		searchcodeIdComuneSelect.style.display='none';
	if (selezionaIndirizzoLink!=null)
		selezionaIndirizzoLink.style.display='none';
	if (selezionaIndirizzoLink2!=null)
		selezionaIndirizzoLink2.style.display='none';
	if (viaTesto!=null)
		viaTesto.style.display='none';

	var idRegione = document.getElementById('idRegioneRappresentante');
	var searchcodeIdprovincia= document.getElementById('idProvinciaRappresentante');
	var searchcodeIdComuneSelect= document.getElementById('idComuneRappresentante');
	var selezionaIndirizzoLink= document.getElementById('selezionaIndirizzoLink');
	var viaTesto= document.getElementById('viaRappresentante');
	if (idRegione!=null)
		idRegione.style.display='none';
	if (searchcodeIdprovincia!=null)
		searchcodeIdprovincia.style.display='none';
	if (searchcodeIdComuneSelect!=null)
		searchcodeIdComuneSelect.style.display='none';
	if (selezionaIndirizzoLink!=null)
		selezionaIndirizzoLink.style.display='none';
	if (viaTesto!=null)
		viaTesto.style.display='none';

	//INIZIALIZZO LE CHECKBOX PER LA PROVENIENZA ESTERA
	if (<%=!Colonia%>){
	if (<%=Operatore.getRappLegale().isProvenienzaEstera()==true%>){
		document.getElementById("estero").checked=true;
		checkEstero(1);
		}
	}
	if (<%=!Privato%> && <%=!Sindaco%> && <%=!SindacoFR%>){
		if (<%=Operatore.getRappLegale().isProvenienzaEstera()==true%>){
		document.getElementById("esteroResp").checked=true;
		checkEstero(2);
		}
	}

	//CONTROLLO SE SONO PRESENTI CF LIBERI
	if (<%=!Colonia%>)
		checkCfLibero(1);
	if (<%=!Privato%> && <%=!Sindaco%> && <%=!SindacoFR%>)
		checkCfLibero(2);
	
});</script>

<script>
function disabilitaResp(){
	//DISABILITO LE INFO RESPONSABILE STABILIMENTO SE E' UGUALE AL RAPP. LEGALE

	if (document.getElementById('idResp')!=null)
		if( document.getElementById('idRappLegale')!=null)
			if (document.getElementById('idResp').value== document.getElementById('idRappLegale').value){
				var nomeResp = document.getElementById('nomeResp');
				var cognomeResp = document.getElementById('cognomeResp');
				var sesso1Resp = document.getElementById('sessoResp1');
				var sesso2Resp = document.getElementById('sessoResp2');
				var codFiscaleResp = document.getElementById('codFiscaleSoggettoTestoResp');
				var dataNascitaResp = document.getElementById('dataNascitaResp');
				var comuneNascitaResp = document.getElementById('comuneNascitaResp');
				var provinciaNascitaResp = document.getElementById('provinciaNascitaResp');
				var mailResp = document.getElementById('emailResp');
				var telefonoResp = document.getElementById('telefono1Resp');
				var telefono2Resp = document.getElementById('telefono2Resp');
				var faxResp = document.getElementById('faxResp');
				var cb_responsabile = document.getElementById('edit_responsabile');
							
				
				nomeResp.readOnly = "readonly";
				cognomeResp.readOnly = "readonly";
				sesso1Resp.readOnly = "readonly";
				sesso2Resp.readOnly = "readonly";
				dataNascitaResp.readOnly = "readonly";
				codFiscaleResp.readOnly = "readonly";
				comuneNascitaResp.readOnly = "readonly";
				provinciaNascitaResp.readOnly = "readonly";
				mailResp.readOnly = "readonly";
				telefonoResp.readOnly = "readonly";
				telefono2Resp.readOnly = "readonly";
				faxResp.readOnly = "readonly";
				cb_responsabile.style.display='none';

				
}}

</script>
<script>
//SEDE LEGALE
$(document).ready(function(){
	if (<%=Privato || Sindaco || SindacoFR%>){
		
		  $.ajax({
		   type: 'POST',
		   url:'ServletRegioniComuniProvince',
		   dataType: 'json',
		   data: {'idAslSedePrivato':<%=sedeOperativa.getIdAsl()%>, 'tipoRichiesta':3, 'tipoSede':1},
		   success: function(res){
		    $('#searchcodeIdComuneSelect option').each(function(){$(this).remove()});
		    $("#viaTesto").val('');
		    $("#cap").val('');
		    $("#capResponsabile").val('');
		    $("#via").val('-1');
		    $('#searchcodeIdComuneSelect').append('<option selected="selected">Tutti</option>');
		    $.each(res, function(i, e){
		     $('#searchcodeIdComuneSelect').append('<option value="' + e.codice + '">' + e.descrizione + '</option>');
		     $('#searchcodeIdComuneSelect').prop('disabled', true);
		    });
		   }
		  });
		
	}
	else{	
 $('#idRegione').change(function(){
  var elem = $(this).val();
  // alert(elem);
  $.ajax({
   type: 'POST',
   url:'ServletRegioniComuniProvince',
   dataType: 'json',
   data: {'idRegione':elem, 'tipoRichiesta':1},
   success: function(res){
	//   alert(res);
    $('#searchcodeIdprovincia option').each(function(){$(this).remove()});
    $('#searchcodeIdprovincia').append('<option selected="selected">Tutti</option>');
    $('#searchcodeIdComuneSelect option').each(function(){$(this).remove()});
    $("#viaTesto").val('');
    $("#cap").val('');
    $("#capResponsabile").val('');
    $("#via").val('-1');
    
    $('#searchcodeIdComuneSelect').append('<option selected="selected">Tutti</option>');
    $.each(res, function(i, e){
       // alert(e.codice);
     $('#searchcodeIdprovincia').append('<option value="' + e.codice + '">' + e.descrizione + '</option>');
    });
   }
  });
 });
  
 $('#searchcodeIdprovincia').change(function(){
  var elem = $(this).val();
   
  $.ajax({
   type: 'POST',
   url:'ServletRegioniComuniProvince',
   dataType: 'json',
   data: {'searchcodeIdprovincia':elem, 'tipoRichiesta':2, 'tipoSede':1},
   success: function(res){
    $('#searchcodeIdComuneSelect option').each(function(){$(this).remove()});
    $("#viaTesto").val('');
    $("#cap").val('');
    $("#capResponsabile").val('');
    $("#via").val('-1');
    $('#searchcodeIdComuneSelect').append('<option selected="selected">Tutti</option>');
    $.each(res, function(i, e){
     $('#searchcodeIdComuneSelect').append('<option value="' + e.codice + '">' + e.descrizione + '</option>');
    });
   }
  });
 });
	}

 $('#searchcodeIdComuneSelect').change(function(){


	    $("#viaTesto").val('');
	    $("#cap").val('');
	    $("#capResponsabile").val('');
	    $("#via").val('-1');


	 });
  



var via = { 
		source: function (request, response) {
    $.post("ServletRegioniComuniProvince?tipoRichiesta=4_text&idprovincia="+$("#searchcodeIdprovincia").val()+"&idcomune="+$("#searchcodeIdComuneSelect").val(), request, response);
  }, 


change: function(event, ui){ 	
//	alert((ui.item ? ui.item.idindirizzo : 'sdf'));	

	$("#via").val(ui.item ? ui.item.idindirizzo : '-1' );  }, 
	minLength:3 }; 


$("#viaTesto").autocomplete(via); 

disabilitaResp();
}); 

</script>


<script>
//SEDE OPERATIVA
$(document).ready(function(){
    $.ajax({
   type: 'POST',
   url:'ServletRegioniComuniProvince',
   dataType: 'json',
   data: {'idAslSedeOperativa':<%=sedeOperativa.getIdAsl()%>, 'tipoRichiesta':2, 'tipoSede':2},
   success: function(res){
    $('#idComuneSedeOperativaSelect option').each(function(){$(this).remove()});
    $("#viaSedeOperativa").val('');
    $("#idViaSedeOperativa").val('-1');
    $('#idComuneSedeOperativaSelect').append('<option selected="selected">Tutti</option>');
    $.each(res, function(i, e){
     $('#idComuneSedeOperativaSelect').append('<option value="' + e.codice + '">' + e.descrizione + '</option>');
     $('#idComuneSedeOperativaSelect').prop('disabled', true);
    });
   }

 });


 $('#idComuneSedeOperativaSelect').change(function(){


	    $("#viaSedeOperativa").val('');
	    $("#idViaSedeOperativa").val('-1');


	 });
  



var via = { 
		source: function (request, response) {
    $.post("ServletRegioniComuniProvince?tipoRichiesta=4_text&idprovincia="+$("#idProvinciaSedeOperativa").val()+"&idcomune="+$("#idComuneSedeOperativaSelect").val(), request, response);
  }, 


change: function(event, ui){ 	
//	alert((ui.item ? ui.item.idindirizzo : 'sdf'));	

	$("#idViaSedeOperativa").val(ui.item ? ui.item.idindirizzo : '-1' );  }, 
	minLength:3 }; 


$("#viaSedeOperativa").autocomplete(via); 
}); 

</script>

<script>
//INDIRIZZO RESPONSABILE
$(document).ready(function(){
 $('#idRegioneResponsabile').change(function(){
	 
  var elem = $(this).val();
  // alert(elem);
  $.ajax({
   type: 'POST',
   url:'ServletRegioniComuniProvince',
   dataType: 'json',
   data: {'idRegioneResponsabile':elem, 'tipoRichiesta':1, 'tipoSede':3},
   success: function(res){
	//   alert(res);
    $('#idProvinciaResponsabile option').each(function(){$(this).remove()});
    $('#idProvinciaResponsabile').append('<option selected="selected">Tutti</option>');
    $('#idComuneResponsabile option').each(function(){$(this).remove()});
    $("#viaResponsabile").val('');
    $("#idViaResponsabile").val('-1');
    
    $('#idComuneResponsabile').append('<option selected="selected">Tutti</option>');
    $.each(res, function(i, e){
       // alert(e.codice);
     $('#idProvinciaResponsabile').append('<option value="' + e.codice + '">' + e.descrizione + '</option>');
    });
   }
  });
 });
  
 $('#idProvinciaResponsabile').change(function(){
  var elem = $(this).val();
   
  $.ajax({
   type: 'POST',
   url:'ServletRegioniComuniProvince',
   dataType: 'json',
   data: {'idProvinciaResponsabile':elem, 'tipoRichiesta':2, 'tipoSede':3},
   success: function(res){
    $('#idComuneResponsabile option').each(function(){$(this).remove()});
    $("#viaResponsabile").val('');
    $("#idViaResponsabile").val('-1');
    $('#idComuneResponsabile').append('<option selected="selected">Tutti</option>');
    $.each(res, function(i, e){
     $('#idComuneResponsabile').append('<option value="' + e.codice + '">' + e.descrizione + '</option>');
    });
   }
  });
 });


 $('#idComuneResponsabile').change(function(){


	    $("#viaResponsabile").val('');
	    $("#idViaResponsabile").val('-1');


	 });
  



var via = { 
		source: function (request, response) {
    $.post("ServletRegioniComuniProvince?tipoRichiesta=4_text&idprovincia="+$("#idProvinciaResponsabile").val()+"&idcomune="+$("#idComuneResponsabile").val(), request, response);
  }, 


change: function(event, ui){ 	
//	alert((ui.item ? ui.item.idindirizzo : 'sdf'));	

	$("#idViaResponsabile").val(ui.item ? ui.item.idindirizzo : '-1' );  }, 
	minLength:3 }; 


$("#viaResponsabile").autocomplete(via); 
}); 

</script>

<script>
//INDIRIZZO RAPPRESENTANTE
$(document).ready(function(){
 $('#idRegioneRappresentante').change(function(){
	 
  var elem = $(this).val();
  // alert(elem);
  $.ajax({
   type: 'POST',
   url:'ServletRegioniComuniProvince',
   dataType: 'json',
   data: {'idRegioneRappresentante':elem, 'tipoRichiesta':1, 'tipoSede':4},
   success: function(res){
	//   alert(res);
    $('#idProvinciaRappresentante option').each(function(){$(this).remove()});
    $('#idProvinciaRappresentante').append('<option selected="selected">Tutti</option>');
    $('#idComuneRappresentante option').each(function(){$(this).remove()});
    $("#viaRappresentante").val('');
    $("#idViaRappresentante").val('-1');
    
    $('#idComuneRappresentante').append('<option selected="selected">Tutti</option>');
    $.each(res, function(i, e){
       // alert(e.codice);
     $('#idProvinciaRappresentante').append('<option value="' + e.codice + '">' + e.descrizione + '</option>');
    });
   }
  });
 });
  
 $('#idProvinciaRappresentante').change(function(){
  var elem = $(this).val();
   
  $.ajax({
   type: 'POST',
   url:'ServletRegioniComuniProvince',
   dataType: 'json',
   data: {'idProvinciaRappresentante':elem, 'tipoRichiesta':2, 'tipoSede':4},
   success: function(res){
    $('#idComuneRappresentante option').each(function(){$(this).remove()});
    $("#viaRappresentante").val('');
    $("#idViaRappresentante").val('-1');
    $('#idComuneRappresentante').append('<option selected="selected">Tutti</option>');
    $.each(res, function(i, e){
     $('#idComuneRappresentante').append('<option value="' + e.codice + '">' + e.descrizione + '</option>');
    });
   }
  });
 });


 $('#idComuneRappresentante').change(function(){


	    $("#viaRappresentante").val('');
	    $("#idViaRappresentante").val('-1');


	 });
  



var via = { 
		source: function (request, response) {
    $.post("ServletRegioniComuniProvince?tipoRichiesta=4_text&idprovincia="+$("#idProvinciaRappresentante").val()+"&idcomune="+$("#idComuneRappresentante").val(), request, response);
  }, 


change: function(event, ui){ 	
//	alert((ui.item ? ui.item.idindirizzo : 'sdf'));	

	$("#idViaRappresentante").val(ui.item ? ui.item.idindirizzo : '-1' );  }, 
	minLength:3 }; 


$("#viaRappresentante").autocomplete(via); 
}); 



function cleanRappresentante(){
	alert ('clean');
}


function modifyRappresentante(){
	alert('modify');
}

</script>

<%@ include file="../initPage.jsp"%>





<form name="operatoreModify" id="operatoreModify"
	action="OperatoreAction.do?command=UpdateTotale&idLineaProduttiva=<%=id%>&opId=<%=idLinea%>&auto-populate=true"
	method="post"><input type="button"
	value="<dhv:label name="global.button.update"></dhv:label>" name="Save"
	onClick="javascript:checkForm(<%=sedelegale%>, <%=sedeoperativa%>,<%=responsabile%>)" />
	
	<input type="button"
		value="<dhv:label name="global.button.cancel">Cancel</dhv:label>"
		onClick="window.location.href='OperatoreAction.do?command=Details&opId=<%=idLinea%>';this.form.dosubmit.value='false';" />
	
<br />
<br />

		<input type="hidden" name="soggettoInfo" id = "soggettoInfo" value =""></input> 

<!--  TIPOLOGIA PROPRIETARIO -->
<table
	cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
	<tr>
		<th colspan="2"><dhv:label
			name="opu.stabilimento.linea_produttiva">
			<strong></strong>
		</dhv:label></th>
	</tr>
	<tr>
		<td colspan="2"><%=linea.getAttivita()%> <dhv:evaluate
			if="<%=(SiteList.size() > 0)%>">  / Asl <%=SiteList.getSelectedValue(stab
									.getIdAsl())%>
			<%=(stab.isFlagFuoriRegione()) ? " - Fuori regione - "
							: ""%>

		</dhv:evaluate>
	</tr>

</table>
<br/>
<!-- INIZIO DATI LEGALI -->

<table cellpadding="4" cellspacing="0" border="0" width="100%"
	class="details">
	<input type="hidden" name="doContinue" id="doContinue" value="">
	
		<%
				String label = "opu.operatore_" + id;
			%> 
			<dhv:evaluate if="<%=(!Privato) && (!Sindaco) && (!SindacoFR) &&(!Colonia)%>">
			<tr>
		<th colspan="2"><strong><dhv:label
			name="<%=label%>">Modifica informazione primaria</dhv:label></strong></th>
	</tr></dhv:evaluate>

<%
	label = "opu.operatore.ragione_sociale_" + id;
%>
	<!-- SE NON E' UN PRIVATO, PUOI MODIFICARE LA RAGIONE SOCIALE -->
		<dhv:evaluate if="<%=(!Privato) && (!Sindaco) && (!SindacoFR) && (!Colonia)%>">
		<tr>
		<td nowrap class="formLabel">
		 <dhv:label
			name="<%=label%>">Ragione sociale</dhv:label></td>
		<td>
			<input type="text" size="20" maxlength="200" id="ragioneSociale"
				name="ragioneSociale"
				value="<%=(Operatore.getRagioneSociale() != null) ? Operatore
						.getRagioneSociale() : ""%>">
			<font color="red">*</font>
			
			<tr>
		<td nowrap class="formLabel">
		<%
			label = "opu.operatore.cf";
			if (id > 1)
				label = label + "_impresa";
		%> <dhv:label name="<%=label%>">Codice fiscale impresa</dhv:label>
		</td>
		<td><input type="text" size="20" maxlength="16" id="codFiscale"
			name="codFiscale"
			value="<%=(Operatore.getCodFiscale() != null) ? Operatore
					.getCodFiscale() : ""%>">

		</td>
	</tr>
		</dhv:evaluate> <!-- SE E' UN PRIVATO, NON PUOI MODIFICARE NE' VEDERE LA RAGIONE SOCIALE --> <dhv:evaluate
			if="<%=(Privato || Sindaco || SindacoFR)%>">
			<input disabled
				style="background: none repeat scroll 0% 0% rgb(204, 204, 204);"
				type="hidden" size="20" maxlength="200" id="ragioneSociale"
				name="ragioneSociale"
				value="<%=(Operatore.getRagioneSociale() != null) ? Operatore
						.getRagioneSociale() : ""%>">
		
		</dhv:evaluate></td>
	</tr>

	


	<!-- SE NON E' UN PRIVATO,  PUOI MODIFICARE LA PARTITA IVA -->
		<dhv:evaluate if="<%=(!Privato) && (!Sindaco) && (!SindacoFR) && (!Colonia)%>">
		<tr>
		<td nowrap class="formLabel"><dhv:label name="opu.operatore.piva">Partita iva</dhv:label>
		</td>
		<td>
			<input type="text" size="20" maxlength="11" id="partitaIva"
				name="partitaIva"
				value="<%=(Operatore.getPartitaIva() != null) ? Operatore
						.getPartitaIva() : ""%>">
			<font color="red">*</font>
		</dhv:evaluate> <!-- SE E' UN PRIVATO, NON PUOI MODIFICARE LA PARTITA IVA --> 
<dhv:evaluate if="<%=(Privato || Sindaco || SindacoFR || Colonia)%>">
		<input disabled
				style="background: none repeat scroll 0% 0% rgb(204, 204, 204);"
				type="hidden" size="20" maxlength="11" id="partitaIva"
				name="partitaIva"
				value="<%=(Operatore.getPartitaIva() != null) ? Operatore
						.getPartitaIva() : ""%>">
			</dhv:evaluate></td>
	</tr>
	<input type="hidden" name="idOperatore" id="idOperatore"
		value="<%=Operatore.getIdOperatore()%>" />

<dhv:evaluate if="<%=(OperatoreCommerciale || Importatore)%>">
<tr><td class="formLabel" nowrap><dhv:label
				name="opu.stabilimento.autorizzazione">Autorizzazione</dhv:label></td>
			<td><input type="text" size="20" id="autorizzazione"
				name="autorizzazione"
				value="<%=(linea.getAutorizzazione() != null) ? linea.getAutorizzazione() : ""%>">
			</td>
		</tr>
		
		
</dhv:evaluate>

<dhv:evaluate if="<%=(!Privato && !Sindaco && !SindacoFR && !Colonia)%>">
	<tr>
			<td class="formLabel" nowrap><dhv:label
				name="">Telefono struttura (principale)</dhv:label></td>
			<td><input type="text" size="30" maxlength="50" id="telefono1_lp"
				name="telefono1_lp"
				value="<%=(linea.getTelefono1()!=null) ? linea.getTelefono1() : ""%>" /><font color="red">*</font></td>

		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="">Telefono struttura (secondario)</dhv:label>
			</td>
			<td><input type="text" size="30" maxlength="50" id="telefono2_lp"
				name="telefono2_lp"
				value="<%=(linea.getTelefono1()!=null) ? linea.getTelefono2() : ""%>" /></td>

		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="">Fax</dhv:label>
			</td>
			<td><input type="text" size="30" maxlength="50" id="fax_lp"
				name="fax_lp"
				value="<%=(linea.getFax()!=null) ? linea.getFax() : ""%>" /></td>

		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="">Mail</dhv:label>
			</td>
			<td><input type="text" size="30" maxlength="50" id="email_lp"
				name="email_lp"
				value="<%=(linea.getMail1()!=null) ? linea.getMail1() : ""%>" /></td>

		</tr>
</dhv:evaluate>




</table>
</br>
</br>
<dhv:evaluate if="<%=(!Colonia)%>">
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<%
			label = "opu.soggetto_fisico_" + id;
		%>
		<tr>
			<th colspan="2"><strong><dhv:label name="<%=label%>">Rappresentante legale</dhv:label></strong>
			<input type="hidden" name="idRappLegale" id="idRappLegale"
				value="<%=Operatore.getRappLegale().getIdSoggetto()%>"></input></th>
		</tr>


		
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.nome">Nome</dhv:label></td>
			<td><input type="text" size="30" maxlength="50" id="nome"
				name="nome" value="<%=Operatore.getRappLegale().getNome()%>"><font
				color="red">*</font></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.cognome">Cognome</dhv:label></td>
			<td><input type="text" size="30" maxlength="50" id="cognome"
				name="cognome" value="<%=Operatore.getRappLegale().getCognome()%>"><font
				color="red">*</font></td>
		</tr>
		
			
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.sesso">Sesso</dhv:label></td>
			<td><input type="radio" name="sesso" id="sesso1" value="M"
				<%=(Operatore.getRappLegale().getSesso()
						.equalsIgnoreCase("m")) ? "checked=\"checked\"" : ""%>>M
			<input type="radio" name="sesso" id="sesso2" value="F"
				<%=(Operatore.getRappLegale().getSesso()
						.equalsIgnoreCase("f")) ? "checked=\"checked\"" : ""%>>F
			</td>
		</tr>
		


		<tr>
			<td nowrap class="formLabel"><dhv:label
				name="opu.soggetto_fisico.data_nascita">Data di nascita</dhv:label>
			</td>
			<td><input class="todisable" readonly type="text" id="dataNascita"
			name="dataNascita" size="10" value="<%=Operatore.getRappLegale().getDataNascitaString()%>" /> 
			
			
			<a href="#"
			onClick="cal17.select(document.forms[0].dataNascita,'anchor19','dd/MM/yyyy',  formatDate(anni18fa,'dd/MM/yyyy')    ); return false;"
			NAME="anchor19" ID="anchor19"> <img
			src="images/icons/stock_form-date-field-16.gif" border="0"
			align="absmiddle"></a>
			
			
			</td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.comune_nascita">Comune nascita</dhv:label>
			</td>
			<td><input type="text" size="30" maxlength="50"
				id="comuneNascita" name="comuneNascita"
				value="<%=Operatore.getRappLegale().getComuneNascita()%>" /></td>
		</tr>
		
	<dhv:evaluate if="<%=(!Sindaco) && (!SindacoFR)%>">
		<tr><td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.cf">Codice fiscale</dhv:label> </td>
		<td><input readonly type="text" name="codFiscaleSoggettoTesto" id = "codFiscaleSoggettoTesto" value ="<%=Operatore.getRappLegale().getCodFiscale()%>"></input> 
		
		<input type="hidden" name="codFiscaleSoggettoTesto_Originale" id = "codFiscaleSoggettoTesto_Originale" value ="<%=Operatore.getRappLegale().getCodFiscale()%>"></input> 
		
		<input type="button" name="codFiscaleSoggettoTestoButton" id= "codFiscaleSoggettoTestoButton" value ="Calcola Codice Fiscale" onClick="javascript:CalcolaCF('set')"/>
		
		<dhv:evaluate if="<%=Operatore.getRappLegale().isCfLibero()%>">
		<font color="red">Codice fiscale non calcolabile</font>
		</dhv:evaluate>
		
		<input type="checkbox" name="estero" id="estero" value="NO" onClick="checkEstero(1)" /> <label for="estero" id="esteroLabel">Provenienza Estera</label></td></tr>
		
		</dhv:evaluate>
		<dhv:evaluate if="<%=(Sindaco) || (SindacoFR)%>">
		<tr><td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.cf">Codice fiscale</dhv:label> </td>
		<td><input type="text" name="codFiscaleSoggettoTesto" id = "codFiscaleSoggettoTesto" value ="<%=Operatore.getRappLegale().getCodFiscale()%>"></input> 
		</td> </tr>
		</dhv:evaluate>
		
		<input type="hidden" name="idSoggetto" id="idSoggetto" value="<%=Operatore.getRappLegale().getIdSoggetto()%>" /></td> 
		
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.didentita">Documento identità</dhv:label></td>
			<td><input type="text" size="30" maxlength="50" id="documentoIdentita"
				name="documentoIdentita" value="<%=(Operatore.getRappLegale().getDocumentoIdentita()!=null) ? Operatore.getRappLegale().getDocumentoIdentita() : ""%>"></td>
		</tr>

		
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.provincia_nascita">Provincia nascita</dhv:label>
			</td>
			<td><input type="text" size="30" maxlength="50"
				id="provinciaNascita" name="provinciaNascita"
				value="<%=Operatore.getRappLegale().getProvinciaNascita()%>" /></td>
		</tr>


		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.mail">Email</dhv:label></td>
			<td><input type="text" size="30" maxlength="50" id="email"
				name="email" value="<%=Operatore.getRappLegale().getEmail()%>" /></td>

		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.telefono">Telefono</dhv:label></td>
			<td><input type="text" size="30" maxlength="50" id="telefono1"
				name="telefono1"
				value="<%=(Operatore.getRappLegale().getTelefono1()!=null) ? Operatore.getRappLegale().getTelefono1() : ""%>" /> <font color="red">*</font></td>

		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.telefono2">Telefono secondario</dhv:label>
			</td>
			<td><input type="text" size="30" maxlength="50" id="telefono2"
				name="telefono2"
				value="<%=(Operatore.getRappLegale().getTelefono1()!=null) ? Operatore.getRappLegale().getTelefono2() : ""%>" /></td>

		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.soggetto_fisico.fax">Fax</dhv:label></td>
			<td><input type="text" size="30" maxlength="50" id="fax"
				name="fax" value="<%=(Operatore.getRappLegale().getFax()!=null) ? Operatore.getRappLegale().getFax() : ""%>" /></td>

		</tr>
		
		<dhv:evaluate if="<%=(!Colonia && !Privato && !Sindaco && !SindacoFR)%>">
			<tr>
			<td class="formLabel" nowrap=""><dhv:label
				name="opu.soggetto_fisico.provincia_residenza"></dhv:label></td>
			<td><span class="ui-helper-hidden-accessible" aria-live="polite"
				role="status"></span> <label><%=Operatore.getRappLegale().getIndirizzo()
						.getDescrizione_provincia()%></label></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap=""><dhv:label
				name="opu.soggetto_fisico.comune_residenza"></dhv:label></td>
			<td><span class="ui-helper-hidden-accessible" aria-live="polite"
				role="status"></span> <label><%=Operatore.getRappLegale().getIndirizzo()
						.getDescrizioneComune()%></label></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap=""><dhv:label
				name="opu.soggetto_fisico.indirizzo">INDIRIZZO</dhv:label></td>
			<td><span class="ui-helper-hidden-accessible" aria-live="polite"
				role="status"></span> <label><%=Operatore.getRappLegale().getIndirizzo().getVia()%></label></td>
		</tr>

	<tr>
			<td class="formLabel" nowrap="">Cambia indirizzo  <input
				type="checkbox" name="edit_rappresentante" id="edit_rappresentante"
				value="NO" onClick="nascondi(4)" /></td>
			<td>
			<%=regioniList
						.getHtmlSelect("idRegioneRappresentante", "")%>
			<%=provinceList.getHtmlSelect("idProvinciaRappresentante",
						"")%> <%=ComuniList.getHtmlSelect("idComuneRappresentante", "")%>
			<input type="text" id="viaRappresentante" name="viaRappresentante"
				value="" placeholder="Inserire le prime tre lettere"></input> 
				<input type="hidden" id="idViaRappresentante" name="idViaRappresentante"
				value="<%=Operatore.getRappLegale().getIndirizzo().getIdIndirizzo()%>"></input></td>
				
				
				
			</tr>
		</dhv:evaluate>
		
		
		
		</table>

</dhv:evaluate> <dhv:evaluate if="<%=(!Colonia)%>">
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2">
			<%
				label = "opu.sede_legale_" + id;
			%> <strong><dhv:label
				name="<%=label%>">Sede Legale/Residenza </dhv:label></strong></th>
		</tr>
		<jsp:useBean id="stabilimento"
			class="org.aspcfs.modules.opu.base.Stabilimento" scope="request" />
		</br>
		</br>

		<!-- td class="formLabel" nowrap=""><dhv:label name="opu.sede_legale.inregione"></dhv:label></td>
	<td><span class="ui-helper-hidden-accessible" aria-live="polite" role="status"></span>
	<label><%=(Operatore.getSedeLegale()!=null)?(Operatore.getSedeLegale().getNomeRegione()):("")%></label></td></tr-->
		<tr>
			<td class="formLabel" nowrap=""><dhv:label
				name="opu.sede_legale.provincia"></dhv:label></td>
			<td><span class="ui-helper-hidden-accessible" aria-live="polite"
				role="status"></span> <label><%=(Operatore.getSedeLegale()!=null)?(Operatore.getSedeLegale().getDescrizione_provincia()):("")%></label></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap=""><dhv:label
				name="opu.sede_legale.comune"></dhv:label></td>
			<td><span class="ui-helper-hidden-accessible" aria-live="polite"
				role="status"></span> <label><%=(Operatore.getSedeLegale()!=null)?(Operatore.getSedeLegale().getDescrizioneComune()):("")%></label></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap=""><dhv:label
				name="requestor.requestor_add.AddressLine1">INDIRIZZO</dhv:label></td>
			<td><span class="ui-helper-hidden-accessible" aria-live="polite"
				role="status"></span> <label><%=(Operatore.getSedeLegale()!=null)?(Operatore.getSedeLegale().getVia()):("")%>
			</label></td>
		</tr>


		<tr>
			<td class="formLabel" nowrap="">Cambia indirizzo<input
				type="checkbox" name="edit_legale" id="edit_legale" value="NO"
				onClick="nascondi(1)" /></td>
			<td>
			<a style="text-decoration:underline; color:#006b9c;" onmouseover="this.style.textDecoration='none'; this.style.color='black';" onmouseout="this.style.textDecoration='underline'; this.style.color='#006b9c';" id="selezionaIndirizzoLink" onclick="selezionaIndirizzo('106','callbackResidenzaProprietarioModifica','','','<%=sedeOperativa.getIdAsl()%>')">Seleziona</a> <br/>
			<%=regioniList.getHtmlSelect("idRegione", "")%> <%=provinceList.getHtmlSelect("searchcodeIdprovincia","")%>
			<%=ComuniList.getHtmlSelect("searchcodeIdComuneSelect", "")%> 
			<input type="hidden" id="searchcodeIdComune" name="searchcodeIdComune"/>
			<input type="text" id="viaTesto" name="viaTesto" value="" readonly="readonly" />
			<input type="text" id="cap" name="cap" readonly="readonly" value="" />
			<input type="hidden" id="via" name="via" value="<%=(Operatore.getSedeLegale()!=null)?(Operatore.getSedeLegale().getIdIndirizzo()):("")%>"></input></td>
		</tr>


	</table><br></br>
</dhv:evaluate> <!-- FINE DATI LEGALI -->
<!-- INIZIO DATI SEDE OPERATIVA -->

<dhv:evaluate if="<%=(Canile)%>"> 
	<%
		CanileInformazioni canileInfo = (CanileInformazioni) temp1;
		String abusivo_check = "";
		String sterilizzazione_check = "";
		String municipale_check = "";
		if (canileInfo.isAbusivo())
			abusivo_check = " checked=\"checked\"";
		if (canileInfo.isCentroSterilizzazione())
			sterilizzazione_check = " checked=\"checked\"";
		if (canileInfo.isMunicipale())
			municipale_check = " checked=\"checked\"";
	%><br></br>
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2">
			<%
				label = "opu.operatore_" + id;
			%> <strong><dhv:label
				name="">INFORMAZIONI AGGIUNTIVE</dhv:label></strong></th>
		</tr>
		<tr>
			<td class="formLabel" nowrap>MUNICIPALE
		</td>
		<td><input type="checkbox" id="municipale"
			name="municipale" <%=municipale_check%>></td>
		</tr>
			<td class="formLabel" nowrap><dhv:label
			name="opu.stabilimento.sterilizzazione">Centro di sterilizzazione</dhv:label>
		</td>
		<td><input type="checkbox" id="centroSterilizzazione"
			name="centroSterilizzazione" <%=sterilizzazione_check%>></td>
		</tr>
		
			<td class="formLabel" nowrap><dhv:label
				name="opu.stabilimento.abusivo">Abusivo</dhv:label></td>
			<td><input type="checkbox" id="abusivo" name="abusivo"
				<%=abusivo_check%>></td>
		</tr>
	
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.stabilimento.autorizzazione">Autorizzazione</dhv:label></td>
			<td><input type="text" size="30" maxlength="50"
				id="autorizzazione" name="autorizzazione"
				value="<%=((((CanileInformazioni) temp1).getAutorizzazione()) != null) ? (((CanileInformazioni) temp1)
							.getAutorizzazione())
							: ""%>">

			</td>
		</tr>

		<tr>
			<td nowrap class="formLabel"><dhv:label
				name="opu.stabilimento.data_autorizzazione">Data autorizzazione</dhv:label>
			</td>
			<td><input readonly type="text" name="dataAutorizzazione"
				size="10"
				value="<%=toDateString((((CanileInformazioni) temp1)
					.getDataAutorizzazione()))%>"
				nomecampo="dataAutorizzazione" tipocontrollo="T2,T6,T7" />&nbsp;
			<a href="#"
				onClick="cal19.select(document.forms[0].dataAutorizzazione,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a></td>
		</tr>
		
		
		
				<tr>
			<td class="formLabel" nowrap><dhv:label
				name="">SUPERFICIE DESTINATA AL RICOVERO ANIMALI</dhv:label></td>
			<td><input type="text" size="30" maxlength="50"
				id="mqDisponibili" name="mqDisponibili"
				value="<%=((((CanileInformazioni) temp1).getMqDisponibili()) > 0) ? (((CanileInformazioni) temp1)
							.getMqDisponibili())
							: ""%>">

			</td>
		</tr>


			<tr>
				<td nowrap class="formLabel"><dhv:label name="">CLINICA / OSPEDALE</dhv:label>
				</td>
				<td><input type="checkbox" id="flagClinicaOspedale"
					name="flagClinicaOspedale" <%=(((CanileInformazioni) temp1).isFlagClinicaOspedale()) ? "checked" : "" %>></td>

			</tr>

		</table>
	<br>
	<br>
</dhv:evaluate> <dhv:evaluate if="<%=(Colonia) %>">
	<%
		ColoniaInformazioni coloniaInfo = (ColoniaInformazioni) temp1;
	%>
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2">
			<%
				label = "opu.operatore_" + id;
			%> <strong><label>Informazioni
			colonia</label></strong></th>
		</tr>
		<tr>
			<td class="formLabel" nowrap><label>Nr. Protocollo</label></td>
			<td><input type="text" id="nrProtocollo" name="nrProtocollo"
				value="<%=coloniaInfo.getNrProtocollo()%>" /></td>
		</tr>

		<tr>
			<td nowrap class="formLabel"><label>Data registrazione
			colonia<label></td>
			<td><input readonly type="text" name="dataRegistrazioneColonia"
				size="10"
				value="<%=toDateString(coloniaInfo
						.getDataRegistrazioneColonia())%>"
				nomecampo="dataRegistrazioneColonia" tipocontrollo="T2,T6,T7" />&nbsp;
			<a href="#"
				onClick="cal19.select(document.forms[0].dataRegistrazioneColonia,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"><img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a></td>
		</tr>
	</table>
	<br>
	<br>
</dhv:evaluate> <dhv:evaluate if="<%=!(Privato) && !(Sindaco) && !(SindacoFR) %>">
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2">
			<%
				label = "opu.stabilimento.sede_" + id;
			%> <strong><dhv:label
				name="<%=label %>">Indirizzo Sede Operativa</dhv:label></strong></th>
		</tr>

<tr>
			<td class="formLabel" nowrap=""><dhv:label
				name="opu.stabilimento.asl"></dhv:label></td>
			<td><span class="ui-helper-hidden-accessible" aria-live="polite"
				role="status"></span> <label><%=SiteList.getSelectedValue(sedeOperativa.getIdAsl())%></label></td>
		</tr>
	
		<tr>
			<td class="formLabel" nowrap=""><dhv:label
				name="opu.stabilimento.provincia"></dhv:label></td>
			<td><span class="ui-helper-hidden-accessible" aria-live="polite"
				role="status"></span> <label><%=sedeOperativa.getDescrizione_provincia()%></label></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap=""><dhv:label
				name="opu.stabilimento.comune"></dhv:label></td>
			<td><span class="ui-helper-hidden-accessible" aria-live="polite"
				role="status"></span> <label><%=sedeOperativa.getDescrizioneComune()%></label></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap=""><dhv:label
				name="requestor.requestor_add.AddressLine1">INDIRIZZO</dhv:label></td>
			<td><span class="ui-helper-hidden-accessible" aria-live="polite"
				role="status"></span> <label><%=sedeOperativa.getVia()%></label></td>
		</tr>


		</tbody>


		<tr>
			<td class="formLabel" nowrap=""> Cambia indirizzo 
		
			<input
				type="checkbox" name="edit_operativa" id="edit_operativa" value="NO"
				onClick="nascondi(2)" 
				/>
		
		</td>
			<td>
			<a style="text-decoration:underline; color:#006b9c;" onmouseover="this.style.textDecoration='none'; this.style.color='black';" onmouseout="this.style.textDecoration='underline'; this.style.color='#006b9c';" id="selezionaIndirizzoLink2" onclick="selezionaIndirizzo('106','callbackResidenzaProprietarioModificaColonia','','','<%=sedeOperativa.getIdAsl()%>')">Seleziona</a> <br/>
			
			<%=regioniList.getHtmlSelect("idRegioneSedeOperativa",
						"")%>
			<%=provinceList.getHtmlSelect(
						"idProvinciaSedeOperativa", "")%> <%=ComuniList.getHtmlSelect(
								"idComuneSedeOperativaSelect", "")%>
			<input type="text" id="viaSedeOperativa" name="viaSedeOperativa"
				value="" readonly="readonly"></input> <input type="hidden" id="idViaSedeOperativa"
				name="idViaSedeOperativa"
				value="<%=sedeOperativa.getIdIndirizzo()%>"></input>
				<input type="text" id="cap" name="cap" readonly="readonly"></input>
				<input type="hidden" id="idComuneSedeOperativa" name="idComuneSedeOperativa" ></input>
				</td>
		</tr>




	</table>
	<br></br>

	<!-- FINE DATI SEDE OPERATIVA -->

	<!-- INIZIO DATI RESPONSABILE STABILIMENTO -->
<!-- <input type = "radio" value="1" name="a" onclick="javascript:modifyRappresentante();" checked="checked">Modifica dati del responsabile 
<input type = "radio" value="2" name="a" onclick="javascript:cleanRappresentante();">Sostituisci responsabile colonia  -->
	<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2">
			<%
				label = "opu.stabilimento.soggetto_fisico_" + id;
			%> <strong><dhv:label
				name="<%=label %>">RESPONSABILE STABILIMENTO</dhv:label></strong> <input
				type="hidden" name="idResp" id="idResp"
				value="<%=temp.getRappLegale().getIdSoggetto()%>"></input></th>
		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.stabilimento.soggetto_fisico.nome">Nome</dhv:label></td>
			<td><input type="text" size="30" maxlength="50" id="nomeResp"
				name="nomeResp" value="<%=temp.getRappLegale().getNome()%>"><font
				color="red">*</font></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.stabilimento.soggetto_fisico.cognome">Cognome</dhv:label>
			</td>
			<td><input type="text" size="30" maxlength="50" id="cognomeResp"
				name="cognomeResp" value="<%=temp.getRappLegale().getCognome()%>"><font
				color="red">*</font></td>
		</tr>



<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.stabilimento.soggetto_fisico.cf">Codice fiscale</dhv:label> </td>
			<td>
		<input readonly type="text" name="codFiscaleSoggettoTestoResp" id = "codFiscaleSoggettoTestoResp" value ="<%=temp.getRappLegale().getCodFiscale()%>"></input> 
		
		<input type="hidden" name="codFiscaleSoggettoTestoResp_Originale" id = "codFiscaleSoggettoTestoResp_Originale" value ="<%=temp.getRappLegale().getCodFiscale()%>"></input> 
		
		
		<input type="button" id="codFiscaleSoggettoTestoRespButton" name="codFiscaleSoggettoTestoRespButton" value ="Calcola Codice Fiscale" onClick="javascript:CalcolaCFResp('set')"/>
		
		
		<input type="checkbox" name="esteroResp" id="esteroResp" value="NO" onClick="checkEstero(2)" />
			<label for="esteroResp" id="esteroRespLabel" >Provenienza Estera</label>
		<input type="text" name="idSoggettoResp" id="idSoggettoResp" value="<%=temp.getRappLegale().getIdSoggetto()%>" /></td> 
		
		</td> 
		</tr>
		

	<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.stabilimento.soggetto_fisico.didentita">Documento identità</dhv:label></td>
			<td><input type="text" size="30" maxlength="50" id="documentoIdentitaResp"
				name="documentoIdentitaResp" value="<%=temp.getRappLegale().getDocumentoIdentita()%>"></td>
		</tr>
		
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.stabilimento.soggetto_fisico.sesso">Sesso</dhv:label></td>
			<td><input type="radio" name="sessoResp" id="sessoResp1" value="M"
				<%=(temp.getRappLegale().getSesso()
						.equalsIgnoreCase("m")) ? "checked=\"checked\"" : ""%>>M
			<input type="radio" name="sessoResp" id="sessoResp2" value="F"
				<%=(temp.getRappLegale().getSesso()
						.equalsIgnoreCase("f")) ? "checked=\"checked\"" : ""%>>F
						
			</td>
		</tr>



	<tr>
			<td nowrap class="formLabel"><dhv:label
				name="opu.stabilimento.soggetto_fisico.data_nascita">Data di nascita</dhv:label>
			</td>
			<td><input class="todisable" readonly type="text" id="dataNascitaResp"
			name="dataNascitaResp" size="10" value="<%=temp.getRappLegale().getDataNascitaString()%>" /> 
			
			
			<a href="#"
			onClick="cal19.select(document.forms[0].dataNascitaResp,'anchor19','dd/MM/yyyy'); return false;"
			NAME="anchor19" ID="anchor19"> <img
			src="images/icons/stock_form-date-field-16.gif" border="0"
			align="absmiddle"></a>
				
			</td>
		</tr>
		
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.stabilimento.soggetto_fisico.comune_nascita">Comune Nascita</dhv:label>
			</td>
			<td><input type="text" size="30" maxlength="50"
				id="comuneNascitaResp" name="comuneNascitaResp"
				value="<%=temp.getRappLegale().getComuneNascita()%>"></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.stabilimento.soggetto_fisico.provincia_nascita">Provincia Nascita</dhv:label>
			</td>
			<td><input type="text" size="30" maxlength="50"
				id="provinciaNascitaResp" name="provinciaNascitaResp"
				value="<%=temp.getRappLegale().getProvinciaNascita()%>"></td>
		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.stabilimento.soggetto_fisico.mail">Email</dhv:label></td>
			<td><input type="text" size="30" maxlength="50" id="emailResp"
				name="emailResp" value="<%=temp.getRappLegale().getEmail()%>">
			</td>

		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.stabilimento.soggetto_fisico.telefono">Telefono</dhv:label>
			</td>
			<td><input type="text" size="30" maxlength="50"
				id="telefono1Resp" name="telefono1Resp"
				value="<%=temp.getRappLegale().getTelefono1()%>"> (<font color="red">*</font>) Campo obbligatorio.</td>

		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.stabilimento.soggetto_fisico.telefono2">Telefono secondario</dhv:label>
			</td>
			<td><input type="text" size="30" maxlength="50"
				id="telefono2Resp" name="telefono2Resp"
				value="<%=temp.getRappLegale().getTelefono2()%>"></td>

		</tr>

		<tr>
			<td class="formLabel" nowrap><dhv:label
				name="opu.stabilimento.soggetto_fisico.fax">Fax</dhv:label></td>
			<td><input type="text" size="30" maxlength="50" id="faxResp"
				name="faxResp" value="<%=temp.getRappLegale().getFax()%>">
			</td>

		</tr>

	<!--  /table-->
</dhv:evaluate> <dhv:evaluate if="<%=!(Privato) && !(Sindaco) && !(SindacoFR)%>">
	<!-- table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong><dhv:label
				name="opu.stabilimento.soggetto_fisico.indirizzo_residenza">Indirizzo </dhv:label></strong>
			</th>
		</tr-->

		<!-- td class="formLabel" nowrap=""><dhv:label name="opu.stabilimento.inregione"></dhv:label></td>
	<td><span class="ui-helper-hidden-accessible" aria-live="polite" role="status"></span>
	<label><%=temp.getRappLegale().getIndirizzo().getNomeRegione()%></label></td></tr-->
		<tr>
			<td class="formLabel" nowrap=""><dhv:label
				name="opu.soggetto_fisico.provincia_residenza"></dhv:label></td>
			<td><span class="ui-helper-hidden-accessible" aria-live="polite"
				role="status"></span> <label><%=temp.getRappLegale().getIndirizzo()
						.getDescrizione_provincia()%></label></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap=""><dhv:label
				name="opu.soggetto_fisico.comune_residenza"></dhv:label></td>
			<td><span class="ui-helper-hidden-accessible" aria-live="polite"
				role="status"></span> <label><%=temp.getRappLegale().getIndirizzo()
						.getDescrizioneComune()%></label></td>
		</tr>
		<tr>
			<td class="formLabel" nowrap=""><dhv:label
				name="opu.soggetto_fisico.indirizzo">INDIRIZZO</dhv:label></td>
			<td><span class="ui-helper-hidden-accessible" aria-live="polite"
				role="status"></span> <label><%=temp.getRappLegale().getIndirizzo().getVia()%></label></td>
		</tr>



		</span>

		<tr>
			<td class="formLabel" nowrap="">Cambia indirizzo  <input
				type="checkbox" name="edit_responsabile" id="edit_responsabile"
				value="NO" onClick="nascondi(3)" 
					<%
					if(Colonia)
					{
				%>	
						disabled="disabled" 
				<%
			}
					else
					{
		%>	
				onClick="nascondi(3)" 	
			<%
			}
		%>		
				/></td>
			<td><%=regioniList
						.getHtmlSelect("idRegioneResponsabile", "")%>
			<%=provinceList.getHtmlSelect("idProvinciaResponsabile",
						"")%> <%=ComuniList.getHtmlSelect("idComuneResponsabile", "")%>
			<input type="text" id="viaResponsabile" name="viaResponsabile"
				value="" placeholder="Inserire le prime tre lettere"></input> <input type="hidden" id="idViaResponsabile"
				name="idViaResponsabile"
				value="<%=temp.getRappLegale().getIndirizzo().getIdIndirizzo()%>"></input>
				<input type="text" id="capResponsabile" name="cap" value="" placeholder="Inserire il cap"></input>
				</td>
			</tr>
	</table>




	<!-- FINE DATI RESPONSABILE STABILIMENTO -->
</dhv:evaluate> <br></br>
<table cellpadding="4" cellspacing="0" border="0" width="100%"
		class="details">
		<tr>
			<th colspan="2"><strong><dhv:label name="note">NOTE</dhv:label></strong>
			</th>
		</tr>

		<tr>
			<td valign="top" nowrap class="formLabel"></td>
			<td><TEXTAREA NAME="note" id ="note" ROWS="3" COLS="50"><%=toString(Operatore.getNote())%></TEXTAREA></td>
		</tr>

	</table>
	
		<dhv:permission name="anagrafe_canina-note_internal_use_only-add">
		<table cellpadding="4" cellspacing="0" border="0" width="100%"
			class="details">
			<tr>
				<th colspan="2"><strong>NOTE USO INTERNO</strong></th>
			</tr>
			<tr class="containerBody">
				<td valign="top" class="formLabel"><dhv:label name="">Note</dhv:label>
				</td>
				<td><textarea name="noteInternalUseOnly" rows="3" cols="50"><%=toString(linea.getNoteInternalUseOnly())%></textarea>
				</td>
			</tr>
		</table>
	</dhv:permission>
	
</form>
