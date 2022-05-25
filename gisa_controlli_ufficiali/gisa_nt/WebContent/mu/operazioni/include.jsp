<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ page import="java.util.*" %>
<%@page import="org.aspcfs.utils.web.LookupElement"%>
<%@page import="com.itextpdf.text.log.SysoLogger"%>
<%@page import="org.exolab.castor.jdo.oql.NoMoreTokensException"%>
<%@page import="org.aspcfs.modules.macellazioninew.utils.ReflectionUtil"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Partita"%>
<%@page import="com.darkhorseventures.database.ConnectionPool"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Esito"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>
<%@page import="org.aspcfs.modules.macellazioninew.utils.MacelliUtil"%>
<%@page import="java.util.ArrayList"%> 

<%@page import="org.aspcfs.utils.DateUtils"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.NonConformita"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.PatologiaRilevata"%>
<%@page import="org.aspcfs.utils.web.LookupList"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Casl_Non_Conformita_Rilevata"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.ProvvedimentiCASL"%>
<%@page import="org.aspcfs.modules.macellazioninew.base.Organi"%>
<%@page import="org.aspcfs.modules.contacts.base.Contact"%>
<%@page import="java.util.Date"%>
<%@page import="org.aspcfs.modules.mu_wkf.base.*" %>

<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>

<jsp:useBean id="seduta"				class="org.aspcfs.modules.mu.base.SedutaUnivoca"				scope="request" />
<jsp:useBean id="OrgDetails"		class="org.aspcfs.modules.stabilimenti.base.Organization"	scope="request" />
<jsp:useBean id="Speditore" class="org.aspcfs.modules.speditori.base.Organization" 	scope="request" />
<jsp:useBean id="SpeditoreAddress" class="org.aspcfs.modules.speditori.base.OrganizationAddress" 	scope="request" />
<!--<jsp:useBean id="Campione"			class="org.aspcfs.modules.macellazioninew.base.Campione"		scope="request" />-->
<!--<jsp:useBean id="Organo"			class="org.aspcfs.modules.macellazioninew.base.Organi"			scope="request" />-->

<jsp:useBean id="NCVAM"				class="java.util.ArrayList"				scope="request" />
<jsp:useBean id="Campioni"			class="java.util.ArrayList"				scope="request" />
<jsp:useBean id="OrganiList" 		class="java.util.ArrayList"				scope="request" />
<jsp:useBean id="OrganiListNew" 	class="java.util.TreeMap"				scope="request" />
<jsp:useBean id="PatologieRilevate"	class="java.util.ArrayList"				scope="request" />
<jsp:useBean id="Esiti"	            class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="EsitiId"	            class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="CategorieId"	            class="java.util.ArrayList"							scope="request" />
<jsp:useBean id="Provvedimenti"		class="java.util.ArrayList"				scope="request" />
<jsp:useBean id="casl_NCRilevate"	class="java.util.ArrayList"				scope="request" />

<jsp:useBean id="casl_Provvedimenti_effettuati"	class="java.util.ArrayList"				scope="request" />

<jsp:useBean id="Nazioni"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Matrici"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ASL"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="LuoghiVerifica"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Regioni"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Razze"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Specie"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="CategorieBovine"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="CategorieBufaline"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PianiRisanamento"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="BseList"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiMacellazione"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EsitiVpm"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Patologie"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="PatologieOrgani"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiEsame"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Stadi"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Organi"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiAnalisi"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Molecole"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiNonConformita"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MotiviASL"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="ProvvedimentiVAM"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="look_ProvvedimentiCASL"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Veterinari"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="EsitiCampioni"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MotiviCampioni"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MolecolePNR"				class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MolecoleBatteriologico"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="MolecoleParassitologico"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="TipiNonConformita_Gruppo"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="esitoVisitaAm"	class="org.aspcfs.utils.web.LookupList" scope="request" />



<jsp:useBean id="lookup_lesione_generici"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_altro"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_milza"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_cuore"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean  id="lookup_lesione_polmoni"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_visceri"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_fegato"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_rene"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_mammella"		class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_apparato_genitale"	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_stomaco"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_intestino"			class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_lesione_osteomuscolari"		class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="User"		class="org.aspcfs.modules.login.beans.UserBean" scope="session" />

<%@ include file="../../initPage.jsp"%>
<script>
//Setta il valore dei campi veterinari
function set_vet( select, campo )
{
	var value_to_set = "";
	if( select.value != "-1" )
	{
		value_to_set = select.options[ select.selectedIndex ].text;
	}
	document.getElementById( campo ).value = value_to_set;
	toggleVetDiv( campo );
};


function toggleVetDiv( nomeCampo )
{
	var value = document.getElementById( nomeCampo ).value;
 
	if( value.length > 0 )
	{
		if(document.getElementById( nomeCampo + "_toggle" )){
			document.getElementById( nomeCampo + "_toggle" ).style.display = "block";
		}
	}
};


function displayVPMpatologie()

//alert(document.getElementById('idEsitoPm').value);
{
	if( (document.getElementById('idEsitoPm').value == 4) || (document.getElementById('idEsitoPm').value == 2) )
	{
		
		alert(document.getElementById( 'vpm_riga_patologie' ).style.display);
		document.getElementById( 'vpm_riga_patologie' ).style.display = "table-row";
	}
	else
		
	{
		var element =  document.getElementById('vpm_patologie_rilevate');
		if (typeof(element) != 'undefined' && element != null)
		{
			document.getElementById( "vpm_patologie_rilevate").value = -1;
		}		
		document.getElementById( "vpm_riga_patologie" ).style.display = "none";
	}
}


function resetSelect(){

	for (i=1; i<=3; i++){
		if(document.getElementById("lcso_organo_"+i)){
			document.getElementById("lcso_organo_"+i).value = -1;
		}
		if(document.getElementById("lesione_milza_"+i)){
			document.getElementById("lesione_milza_"+i).value = -1;
		}
		if(document.getElementById("lesione_cuore_"+i)){
			document.getElementById("lesione_cuore_"+i).value = -1;
		}
		if(document.getElementById("lesione_polmoni_"+i)){
			document.getElementById("lesione_polmoni_"+i).value = -1;	
		}
		if(document.getElementById("lesione_visceri_"+i)){
			document.getElementById("lesione_visceri_"+i).value = -1;	
		}
		if(document.getElementById("lesione_fegato_"+i)){
			document.getElementById("lesione_fegato_"+i).value = -1;
		}
		if(document.getElementById("lesione_rene_"+i)){
			document.getElementById("lesione_rene_"+i).value = -1;
		}
		if(document.getElementById("lesione_mammella_"+i)){
			document.getElementById("lesione_mammella_"+i).value = -1;
		}
		if(document.getElementById("lesione_apparato_genitale_"+i)){
			document.getElementById("lesione_apparato_genitale_"+i).value = -1;
		}
		if(document.getElementById("lesione_stomaco_"+i)){
			document.getElementById("lesione_stomaco_"+i).value = -1;
		}
		if(document.getElementById("lesione_intestino_"+i)){
			document.getElementById("lesione_intestino_"+i).value = -1;
		}
		if(document.getElementById("lesione_osteomuscolari_"+i)){
			document.getElementById("lesione_osteomuscolari_"+i).value = -1;
		}
		if(document.getElementById("lesione_generici_"+i)){
			document.getElementById("lesione_generici_"+i).value = -1;
		}
		if(document.getElementById("lesione_altro_"+i)){
			document.getElementById("lesione_altro_"+i).value = -1;
		}
	}
}


function displayLCSO()
{
	
	//alert(document.getElementById('idEsitoPm').value);
	if(document.getElementById('idEsitoPm').value == 3 )
	{
		
		document.getElementById("lcso").style.display="";
		document.getElementById("lcsobutton").style.display="";
		document.getElementById("lcpr").style.display="none";
		
		
	}
	else if( document.getElementById('idEsitoPm').value == 4 )
	{
		/* vecchia versione, nella nuova se 4 si visualizzano anche i campi di 3
		document.getElementById("lcpr").style.display="";
		document.getElementById("lcso").style.display="none";
		document.getElementById("lcsobutton").style.display="none";
		*/
		document.getElementById("lcpr").style.display="";
		document.getElementById("lcso").style.display="";
		document.getElementById("lcsobutton").style.display="";
	}
	else
	{
		document.getElementById("lcso").style.display="none";
		document.getElementById("lcsobutton").style.display="none";
		document.getElementById("lcpr").style.display="none";
/*
		if( document.main.vpm_esito.value==2 )
		{
			document.getElementById("tr_vpm_abb_dist_carcassa").style.display="";
			document.getElementById("vpm_abb_dist_carcassa").checked=true;
		}
		else
		{
			document.getElementById("tr_vpm_abb_dist_carcassa").style.display="none";
			document.getElementById("vpm_abb_dist_carcassa").checked=false;
		}
*/
	}
}


function vpm_resetta_lookup_patologia_organo(indice_riga, value_organo, value_patologia)
{

	try {
		var MILZA 			= 1;
		var CUORE 			= 2;
		var POLMONI 		= 3;
		var FEGATO 			= 4;
		var RENE 			= 5;
		var MAMMELLA 		= 6;
		var GENITALE 		= 7;
		var STOMACO 		= 8;
		var INTESTINO 		= 9;
		var OSTEOMUSCOLARI	= 10;
	
		var ALTRO = 11;
	
		document.getElementById('lesione_milza_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_milza_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_cuore_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_cuore_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_polmoni_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_polmoni_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_fegato_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_fegato_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_rene_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_rene_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_mammella_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_mammella_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_apparato_genitale_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_apparato_genitale_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_stomaco_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_stomaco_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_intestino_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_intestino_'+String(indice_riga)).value = -1;
		document.getElementById('lesione_osteomuscolari_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_osteomuscolari_'+String(indice_riga)).value = -1;
		//document.getElementById('lesione_visceri_'+String(indice_riga)).style.display = "none";
		//document.getElementById('lesione_visceri_'+String(indice_riga)).value = -1;
		
	
		document.getElementById('lesione_generici_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_generici_'+String(indice_riga)).value = -1;
	
		document.getElementById('lesione_altro_'+String(indice_riga)).style.display = "none";
		document.getElementById('lesione_altro_'+String(indice_riga)).value = -1;
		
		if (value_organo == MILZA)  {
					document.getElementById('lesione_milza_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == CUORE)  {
					document.getElementById('lesione_cuore_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == POLMONI){
					document.getElementById('lesione_polmoni_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == FEGATO)	{
					document.getElementById('lesione_fegato_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == RENE)	{
					document.getElementById('lesione_rene_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == MAMMELLA){
					document.getElementById('lesione_mammella_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == GENITALE){
					document.getElementById('lesione_apparato_genitale_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == STOMACO){
					document.getElementById('lesione_stomaco_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == INTESTINO){
					document.getElementById('lesione_intestino_'+String(indice_riga)).style.display = "";
		}
		else if (value_organo == OSTEOMUSCOLARI){
					document.getElementById('lesione_osteomuscolari_'+String(indice_riga)).style.display = "";
		}
		else if ( (value_organo == 13) || (value_organo == 14) || (value_organo == 15) || (value_organo == 16) || (value_organo == 17) || (value_organo == 18)
				|| (value_organo == 19) || (value_organo == 20) || (value_organo == 21) || (value_organo == 22) || (value_organo == 23) || (value_organo == 24) || (value_organo == 25) || (value_organo == 26)) {
			document.getElementById('lesione_generici_'+String(indice_riga)).style.display = "";
		}
		else{
			document.getElementById('lesione_altro_'+String(indice_riga)).style.display = "";
		}
	}
	catch(err)
	{
		alert(err.description);
	}
	
}


function mostraLcsoPatologiaAltro(select,index){

	var ALTRO = 5;
	if(select.value == ALTRO){
		document.getElementById('lcso_patologiaaltro_' + index).style.display='';
	}
	else{
		document.getElementById('lcso_patologiaaltro_' + index).style.display='none';
		document.getElementById('lcso_patologiaaltro_' + index).value = '';
	}
}

</script>