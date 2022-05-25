<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<% if (1==1) { %>
<%@ include file="/ricercaunica/ricercaDismessa.jsp" %>
<%} else { %>

<%-- 
  - Copyright(c) 2004 Dark Horse Ventures LLC (http://www.centriccrm.com/) All
  - rights reserved. This material cannot be distributed without written
  - permission from Dark Horse Ventures LLC. Permission to use, copy, and modify
  - this material for internal use is hereby granted, provided that the above
  - copyright notice and this permission notice appear in all copies. DARK HORSE
  - VENTURES LLC MAKES NO REPRESENTATIONS AND EXTENDS NO WARRANTIES, EXPRESS OR
  - IMPLIED, WITH RESPECT TO THE SOFTWARE, INCLUDING, BUT NOT LIMITED TO, THE
  - IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR ANY PARTICULAR
  - PURPOSE, AND THE WARRANTY AGAINST INFRINGEMENT OF PATENTS OR OTHER
  - INTELLECTUAL PROPERTY RIGHTS. THE SOFTWARE IS PROVIDED "AS IS", AND IN NO
  - EVENT SHALL DARK HORSE VENTURES LLC OR ANY OF ITS AFFILIATES BE LIABLE FOR
  - ANY DAMAGES, INCLUDING ANY LOST PROFITS OR OTHER INCIDENTAL OR CONSEQUENTIAL
  - DAMAGES RELATING TO THE SOFTWARE.
  - 
  - Version: $Id: accounts_search.jsp 18543 2007-01-17 02:55:07Z matt $
  - Description: 
  --%>
  <head>
  	<style type="text/css">
		  p { border-width: 1px;
		  	  border-color: #7F9DB9;
			  border-style: solid;
			  width: 85px;
			  padding: 1px;
			  }
			  
		.input {
				border: 0px;
			  }
  	</style>
  </head>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="org.aspcfs.modules.base.Constants,org.aspcfs.utils.web.*" %>
<jsp:useBean id="SearchOrgListInfo" class="org.aspcfs.utils.web.PagedListInfo" scope="session"/>
<jsp:useBean id="AccountStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="ContactStateSelect" class="org.aspcfs.utils.web.StateSelect" scope="request"/>
<jsp:useBean id="CountrySelect" class="org.aspcfs.utils.web.CountrySelect" scope="request"/>
<jsp:useBean id="SegmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ComuniList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="impiantoZ" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TypeSelect" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="StageList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="CategoriaList" class="org.aspcfs.utils.web.LookupList" scope="request" />
<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<%@ include file="../initPage.jsp" %>
<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/interface/CountView.js"> </script>
        <script type="text/javascript" src="dwr/engine.js"> </script>
        <script type="text/javascript" src="dwr/util.js"></script>
        <script type="text/javascript">
        function popolaComboComuni(idAsl)
		{
			idAsl = document.searchAccount.searchcodeOrgSiteId.value;
			PopolaCombo.getValoriComboComuniAsl(idAsl,setComuniComboCallback);
			CountView.getProvincia(idAsl,setProv);		
		}

        function setComuniComboCallback(returnValue)
        {	        
        	  var select_el = document.forms['searchAccount'].searchAccountCity; //Recupero la SELECT
              //Azzero il contenuto della seconda select
              
              for (var i = select_el.length - 1; i >= 0; i--)
            	  select_el.remove(i);

              indici = returnValue [0];
              valori = returnValue [1];
              //Popolo la seconda Select
              for(j =0 ; j<indici.length; j++){
              //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
              var NewOpt = document.createElement('option');
              NewOpt.value = indici[j]; // Imposto il valore
              NewOpt.text = valori[j]; // Imposto il testo

              //Aggiungo l'elemento option
              try
              {
            	  select_el.add(NewOpt, null); //Metodo Standard, non funziona con IE
              }catch(e){
            	 select_el.add(NewOpt); // Funziona solo con IE
              }
              }


          }

        function setProv(returnValue){
        	<%
			  if(User.getContact().getState()==null || User.getContact().getState().equals("")){
			%>
	    	  var select_el = document.forms['searchAccount'].searchAccountOtherState; //Recupero la SELECT
	    	  <%}%>
	    	  var idAsl = document.searchAccount.searchcodeOrgSiteId.value;
			
			  if(idAsl == -1){

				  <%
				  if(User.getContact().getState()!=null){
				  if(User.getContact().getState().equals("AV")){
				  %>
				  document.searchAccount.searchAccountOtherState.value = "";
				 <%}else if(User.getContact().getState().equals("BN")){%>
				 document.searchAccount.searchAccountOtherState.value = "";
				 <%}else if(User.getContact().getState().equals("CE")){%>
				 document.searchAccount.searchAccountOtherState.value = "";
				 <%}else if(User.getContact().getState().equals("NA")){%>
				 document.searchAccount.searchAccountOtherState.value = "";
				 <%}else if(User.getContact().getState().equals("SA")){%>
				 document.searchAccount.searchAccountOtherState.value = "";
				 <%}}else{%>
				  document.searchAccount.searchAccountOtherState.options[0]=new Option("-- SELEZIONA VOCE --", "", true, false);
				  document.searchAccount.searchAccountOtherState.options[1]=new Option("Avellino", "AV", true, false);
				  document.searchAccount.searchAccountOtherState.options[2]=new Option("Benevento", "BN", true, false);
				  document.searchAccount.searchAccountOtherState.options[3]=new Option("Caserta", "CE", true, false);
				  document.searchAccount.searchAccountOtherState.options[4]=new Option("Napoli", "NA", true, false);
				  document.searchAccount.searchAccountOtherState.options[5]=new Option("Salerno", "SA", true, false);
				  <%}%>
				} 
			  	else{
			  		 <%
			  		  if(User.getContact().getState()!=null){
					  if(User.getContact().getState().equals("AV")){
					  %>
					  document.searchAccount.searchAccountOtherState.value = "";
				 <%}else if(User.getContact().getState().equals("BN")){%>
				 document.searchAccount.searchAccountOtherState.value = "";
				 <%}else if(User.getContact().getState().equals("CE")){%>
				 document.searchAccount.searchAccountOtherState.value = "";
				 <%}else if(User.getContact().getState().equals("NA")){%>
				 document.searchAccount.searchAccountOtherState.value = "";
				 <%}else if(User.getContact().getState().equals("SA")){%>
				 document.searchAccount.searchAccountOtherState.value = "";					 <%}}else{%>
					 
			  		for (var i = select_el.length - 1; i >= 0; i--)
			  			select_el.remove(i);

			    	  
		          	  valori = returnValue;
	          	  	
		              //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
		              var NewOpt = document.createElement('option');
		              

		              if(valori == "AV"){
			              
			              NewOpt.value = "";
		              }

		              if(valori == "BN"){
			              NewOpt.value = "";
		              }

		              if(valori == "CE"){
			              NewOpt.value = "";
		              }

		              if(valori == "NA"){
			              NewOpt.value = "";
		              }

		              if(valori == "SA"){
			              NewOpt.value = "";
		              }	

		              NewOpt.text = valori; // Imposto il testo

		              //Aggiungo l'elemento option
		              try
		              {
		            	  select_el.add(NewOpt, null); //Metodo Standard, non funziona con IE
		              }catch(e){
		            	  select_el.add(NewOpt); // Funziona solo con IE
		              }
		            <%}%>
			    	 
			  	}

		  }

          function popolaAsl(){
	          comune = document.searchAccount.searchAccountCity.value;
	          
	          PopolaCombo.getValoriComuniASL(comune,setAslCallback) ;
	          
		  }

		  function setAslCallback(returnValue){
		  
			  document.searchAccount.searchcodeOrgSiteId.value = returnValue[0];
			  idAsl = document.searchAccount.searchcodeOrgSiteId.value;
			  CountView.getProvincia(idAsl,setProv);
		  }

		  function popolaComboProvincia(provincia){
			  
				provincia = document.searchAccount.searchAccountOtherState.value;
				
				if(document.getElementById("searchAccountOtherState1")!=null)
				var p=document.searchAccount.searchAccountOtherState.value;
				if(document.getElementById("searchAccountOtherState2")!=null)
			    var p=document.searchAccount.searchAccountOtherState.value;
			    if(p=="")
				    p=-1;
				if(p=="AV")
					p=1;
				else if(p=="BN")
					p=2;
				else if(p=="CE")
					p=3;
				else if(p=="NA")
					p=4;
				else if(p=="SA")
					p=5;
				//PopolaComuni a partire dalla provincia
				PopolaCombo.getValoriComboComuniProvinciaOSM(document.searchAccount.searchAccountOtherState.value,setComuniComboCallback);
				//PopolaAsl a partire dalla provincia
				PopolaCombo.getValoriAslProvincia(document.searchAccount.searchAccountOtherState.value,setAsl);
			}

		   		function setAsl(returnValue){
			   		
		   			var select_el = document.forms['searchAccount'].searchcodeOrgSiteId; //Recupero la SELECT
					//Azzero il contenuto della seconda select
	                for (var i = select_el.length - 1; i >= 0; i--)
	            	  select_el.remove(i);

	              	indici = returnValue [0];
	              	valori = returnValue [1];
	              	//Popolo la seconda Select
	              	for(j =0 ; j<indici.length; j++){
	              	//Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
	              var NewOpt = document.createElement('option');
	              NewOpt.value = indici[j]; // Imposto il valore
	              NewOpt.text = valori[j]; // Imposto il testo
	              //Aggiungo l'elemento option
	              try
	              {
	            	  select_el.add(NewOpt, null); //Metodo Standard, non funziona con IE
	              }catch(e){
	            	  select_el.add(NewOpt); // Funziona solo con IE
	              }
	              }

						
			   		
				}

		   		function popolaComuniAsl2()
				{
					if(document.searchAccount.searchAccountOtherState.value==""){
					}
					else{
					PopolaCombo.getValoriComboComuniProvinciaOSM(document.searchAccount.searchAccountOtherState.value,setComuniCallback) ;
					PopolaCombo.getValoriAslProvincia(document.searchAccount.searchAccountOtherState.value,setAslCallbackOnload) ;
					}
									
				}

		   		function setAslCallbackOnload(returnValue)
		          {

					var select_el = document.forms['searchAccount'].searchcodeOrgSiteId; //Recupero la SELECT
					
		              //Azzero il contenuto della seconda select
		              for (var i = select_el.length - 1; i >= 0; i--)
		            	  select_el.remove(i);

		              indici = returnValue [0];
		              valori = returnValue [1];
		              //Popolo la seconda Select
		              for(j =0 ; j<indici.length; j++){
		              //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
		              var NewOpt = document.createElement('option');
		              NewOpt.value = indici[j]; // Imposto il valore
		              NewOpt.text = valori[j]; // Imposto il testo

		              //Aggiungo l'elemento option
		              try
		              {
		            	  select_el.add(NewOpt, null); //Metodo Standard, non funziona con IE
		              }catch(e){
		            	  select_el.add(NewOpt); // Funziona solo con IE
		              }
		              }
					  }

		   		function setComuniCallback(returnValue)
		          {

					var select_el = document.forms['searchAccount'].searchAccountCity; //Recupero la SELECT
		              

		              //Azzero il contenuto della seconda select
		              for (var i = select_el.length - 1; i >= 0; i--)
		            	  select_el.remove(i);

		              indici = returnValue [0];
		              valori = returnValue [1];
		              //Popolo la seconda Select
		              for(j =0 ; j<indici.length; j++){
		              //Creo il nuovo elemento OPTION da aggiungere nella seconda SELECT
		              var NewOpt = document.createElement('option');
		              NewOpt.value = indici[j]; // Imposto il valore
		              NewOpt.text = valori[j]; // Imposto il testo

		              //Aggiungo l'elemento option
		              try
		              {
		            	  select_el.add(NewOpt, null); //Metodo Standard, non funziona con IE
		              }catch(e){
		            	  select_el.add(NewOpt); // Funziona solo con IE
		              }
		              }
					  }

				  
			
</script>
<script language="JavaScript">
  function clearForm() {
    <%-- Account Filters --%>
    //document.forms['searchAccount'].searchcodiceAllerta.value="";  
    document.forms['searchAccount'].searchcodeImpianto.value="-1";
    document.forms['searchAccount'].searchcodeStatoLab.value="0";
    //document.forms['searchAccount'].searchcodecategoriaRischio.value="-1";
    document.forms['searchAccount'].searchAccountName.value="";
    
    document.forms['searchAccount'].searchAccountCity.value="";
    continueUpdateState('2','true');
    if(document.getElementById("searchAccountOtherState1")!=null)
    document.getElementById("searchAccountOtherState1").value = '';
    if(document.getElementById("searchAccountOtherState2")!=null)
        document.getElementById("searchAccountOtherState2").value = '';
    document.forms['searchAccount'].searchAccountName.focus();
   
    // document.forms['searchAccount'].listFilter2.options.selectedIndex = 0;
    
      document.forms['searchAccount'].searchcodeOrgSiteId.options.selectedIndex = -1;
   
     <%-- Contact Filters --%>
    
    
      document.forms['searchAccount'].searchAccountNumber.value="";

      }
      
      
  function fillAccountSegmentCriteria(){
    var index = document.forms['searchAccount'].viewOnlySegmentId.selectedIndex;
    var text = document.forms['searchAccount'].viewOnlySegmentId.options[index].text;
    if (index == 0){
      text = "";
    }
    document.forms['searchAccount'].searchAccountSegment.value = text;
  }


  function cambiaImpianto(){


  	valore = document.forms['searchAccount'].searchcodeCodiceSezione.value;
  	
  	if(valore==-1){
  		document.getElementById("impianto_0").style.display="none";
  		document.getElementById("impianto_1").style.display="none";
  		document.getElementById("impianto_2").style.display="none";
  		document.getElementById("impianto_3").style.display="none";
  		document.getElementById("impianto_4").style.display="none";
  		document.getElementById("impianto_5").style.display="none";
  		document.getElementById("impianto_6").style.display="none";
  		document.getElementById("impianto_7").style.display="none";
  		document.getElementById("impianto_8").style.display="none";
  		document.getElementById("impianto_9").style.display="none";
  		document.getElementById("impianto_10").style.display="none";
  		document.getElementById("impianto_11").style.display="none";
  		document.getElementById("impianto_12").style.display="none";
  		document.getElementById("impianto_13").style.display="none";
  		document.getElementById("impianto_14").style.display="none";
  		document.getElementById("impianto_15").style.display="none";
  		
  	}

  	else if(valore==0){
  		document.getElementById("impianto_0").style.display="";
  		document.getElementById("impianto_1").style.display="none";
  		document.getElementById("impianto_2").style.display="none";
  		document.getElementById("impianto_3").style.display="none";
  		document.getElementById("impianto_4").style.display="none";
  		document.getElementById("impianto_5").style.display="none";
  		document.getElementById("impianto_6").style.display="none";
  		document.getElementById("impianto_7").style.display="none";
  		document.getElementById("impianto_8").style.display="none";
  		document.getElementById("impianto_9").style.display="none";
  		document.getElementById("impianto_10").style.display="none";
  		document.getElementById("impianto_11").style.display="none";
  		document.getElementById("impianto_12").style.display="none";
  		document.getElementById("impianto_13").style.display="none";
  		document.getElementById("impianto_14").style.display="none";
  		document.getElementById("impianto_15").style.display="none";


  	}else if(valore==1){
  		document.getElementById("impianto_0").style.display="none";
  		document.getElementById("impianto_1").style.display="";
  		document.getElementById("impianto_2").style.display="none";
  		document.getElementById("impianto_3").style.display="none";
  		document.getElementById("impianto_4").style.display="none";
  		document.getElementById("impianto_5").style.display="none";
  		document.getElementById("impianto_6").style.display="none";
  		document.getElementById("impianto_7").style.display="none";
  		document.getElementById("impianto_8").style.display="none";
  		document.getElementById("impianto_9").style.display="none";
  		document.getElementById("impianto_10").style.display="none";
  		document.getElementById("impianto_11").style.display="none";
  		document.getElementById("impianto_12").style.display="none";
  		document.getElementById("impianto_13").style.display="none";
  		document.getElementById("impianto_14").style.display="none";
  		document.getElementById("impianto_15").style.display="none";
 	}else if(valore==2){
 		document.getElementById("impianto_0").style.display="none";
  		document.getElementById("impianto_1").style.display="none";
  		document.getElementById("impianto_2").style.display="";
  		document.getElementById("impianto_3").style.display="none";
  		document.getElementById("impianto_4").style.display="none";
  		document.getElementById("impianto_5").style.display="none";
  		document.getElementById("impianto_6").style.display="none";
  		document.getElementById("impianto_7").style.display="none";
  		document.getElementById("impianto_8").style.display="none";
  		document.getElementById("impianto_9").style.display="none";
  		document.getElementById("impianto_10").style.display="none";
  		document.getElementById("impianto_11").style.display="none";
  		document.getElementById("impianto_12").style.display="none";
  		document.getElementById("impianto_13").style.display="none";
  		document.getElementById("impianto_14").style.display="none";
  		document.getElementById("impianto_15").style.display="none";
		  //document.getElementById("impianto_1").style.display="none";
	}else if(valore==3){
		document.getElementById("impianto_0").style.display="none";
  		document.getElementById("impianto_1").style.display="none";
  		document.getElementById("impianto_2").style.display="none";
  		document.getElementById("impianto_3").style.display="";
  		document.getElementById("impianto_4").style.display="none";
  		document.getElementById("impianto_5").style.display="none";
  		document.getElementById("impianto_6").style.display="none";
  		document.getElementById("impianto_7").style.display="none";
  		document.getElementById("impianto_8").style.display="none";
  		document.getElementById("impianto_9").style.display="none";
  		document.getElementById("impianto_10").style.display="none";
  		document.getElementById("impianto_11").style.display="none";
  		document.getElementById("impianto_12").style.display="none";
  		document.getElementById("impianto_13").style.display="none";
  		document.getElementById("impianto_14").style.display="none";
  		document.getElementById("impianto_15").style.display="none";
	}else if(valore==4){
		document.getElementById("impianto_0").style.display="none";
  		document.getElementById("impianto_1").style.display="none";
  		document.getElementById("impianto_2").style.display="none";
  		document.getElementById("impianto_3").style.display="none";
  		document.getElementById("impianto_4").style.display="";
  		document.getElementById("impianto_5").style.display="none";
  		document.getElementById("impianto_6").style.display="none";
  		document.getElementById("impianto_7").style.display="none";
  		document.getElementById("impianto_8").style.display="none";
  		document.getElementById("impianto_9").style.display="none";
  		document.getElementById("impianto_10").style.display="none";
  		document.getElementById("impianto_11").style.display="none";
  		document.getElementById("impianto_12").style.display="none";
  		document.getElementById("impianto_13").style.display="none";
  		document.getElementById("impianto_14").style.display="none";
  		document.getElementById("impianto_15").style.display="none";
	}else if(valore==5){
		document.getElementById("impianto_0").style.display="none";
  		document.getElementById("impianto_1").style.display="none";
  		document.getElementById("impianto_2").style.display="none";
  		document.getElementById("impianto_3").style.display="none";
  		document.getElementById("impianto_4").style.display="none";
  		document.getElementById("impianto_5").style.display="";
  		document.getElementById("impianto_6").style.display="none";
  		document.getElementById("impianto_7").style.display="none";
  		document.getElementById("impianto_8").style.display="none";
  		document.getElementById("impianto_9").style.display="none";
  		document.getElementById("impianto_10").style.display="none";
  		document.getElementById("impianto_11").style.display="none";
  		document.getElementById("impianto_12").style.display="none";
  		document.getElementById("impianto_13").style.display="none";
  		document.getElementById("impianto_14").style.display="none";
  		document.getElementById("impianto_15").style.display="none";
	}else if(valore==6){
		document.getElementById("impianto_0").style.display="none";
  		document.getElementById("impianto_1").style.display="none";
  		document.getElementById("impianto_2").style.display="none";
  		document.getElementById("impianto_3").style.display="none";
  		document.getElementById("impianto_4").style.display="none";
  		document.getElementById("impianto_5").style.display="none";
  		document.getElementById("impianto_6").style.display="";
  		document.getElementById("impianto_7").style.display="none";
  		document.getElementById("impianto_8").style.display="none";
  		document.getElementById("impianto_9").style.display="none";
  		document.getElementById("impianto_10").style.display="none";
  		document.getElementById("impianto_11").style.display="none";
  		document.getElementById("impianto_12").style.display="none";
  		document.getElementById("impianto_13").style.display="none";
  		document.getElementById("impianto_14").style.display="none";
  		document.getElementById("impianto_15").style.display="none";
	}else if(valore==7){
		document.getElementById("impianto_0").style.display="none";
  		document.getElementById("impianto_1").style.display="none";
  		document.getElementById("impianto_2").style.display="none";
  		document.getElementById("impianto_3").style.display="none";
  		document.getElementById("impianto_4").style.display="none";
  		document.getElementById("impianto_5").style.display="none";
  		document.getElementById("impianto_6").style.display="none";
  		document.getElementById("impianto_7").style.display="";
  		document.getElementById("impianto_8").style.display="none";
  		document.getElementById("impianto_9").style.display="none";
  		document.getElementById("impianto_10").style.display="none";
  		document.getElementById("impianto_11").style.display="none";
  		document.getElementById("impianto_12").style.display="none";
  		document.getElementById("impianto_13").style.display="none";
  		document.getElementById("impianto_14").style.display="none";
  		document.getElementById("impianto_15").style.display="none";
	}else if(valore==8){
		document.getElementById("impianto_0").style.display="none";
  		document.getElementById("impianto_1").style.display="none";
  		document.getElementById("impianto_2").style.display="none";
  		document.getElementById("impianto_3").style.display="none";
  		document.getElementById("impianto_4").style.display="none";
  		document.getElementById("impianto_5").style.display="none";
  		document.getElementById("impianto_6").style.display="none";
  		document.getElementById("impianto_7").style.display="none";
  		document.getElementById("impianto_8").style.display="";
  		document.getElementById("impianto_9").style.display="none";
  		document.getElementById("impianto_10").style.display="none";
  		document.getElementById("impianto_11").style.display="none";
  		document.getElementById("impianto_12").style.display="none";
  		document.getElementById("impianto_13").style.display="none";
  		document.getElementById("impianto_14").style.display="none";
  		document.getElementById("impianto_15").style.display="none";
	}else if(valore==9){
		document.getElementById("impianto_0").style.display="none";
  		document.getElementById("impianto_1").style.display="none";
  		document.getElementById("impianto_2").style.display="none";
  		document.getElementById("impianto_3").style.display="none";
  		document.getElementById("impianto_4").style.display="none";
  		document.getElementById("impianto_5").style.display="none";
  		document.getElementById("impianto_6").style.display="none";
  		document.getElementById("impianto_7").style.display="none";
  		document.getElementById("impianto_8").style.display="none";
  		document.getElementById("impianto_9").style.display="";
  		document.getElementById("impianto_10").style.display="none";
  		document.getElementById("impianto_11").style.display="none";
  		document.getElementById("impianto_12").style.display="none";
  		document.getElementById("impianto_13").style.display="none";
  		document.getElementById("impianto_14").style.display="none";
  		document.getElementById("impianto_15").style.display="none";
	}else if(valore==10){
		document.getElementById("impianto_0").style.display="none";
  		document.getElementById("impianto_1").style.display="none";
  		document.getElementById("impianto_2").style.display="none";
  		document.getElementById("impianto_3").style.display="none";
  		document.getElementById("impianto_4").style.display="none";
  		document.getElementById("impianto_5").style.display="none";
  		document.getElementById("impianto_6").style.display="none";
  		document.getElementById("impianto_7").style.display="none";
  		document.getElementById("impianto_8").style.display="none";
  		document.getElementById("impianto_9").style.display="none";
  		document.getElementById("impianto_10").style.display="";
  		document.getElementById("impianto_11").style.display="none";
  		document.getElementById("impianto_12").style.display="none";
  		document.getElementById("impianto_13").style.display="none";
  		document.getElementById("impianto_14").style.display="none";
  		document.getElementById("impianto_15").style.display="none";
	}else if(valore==11){
		document.getElementById("impianto_0").style.display="none";
  		document.getElementById("impianto_1").style.display="none";
  		document.getElementById("impianto_2").style.display="none";
  		document.getElementById("impianto_3").style.display="none";
  		document.getElementById("impianto_4").style.display="none";
  		document.getElementById("impianto_5").style.display="none";
  		document.getElementById("impianto_6").style.display="none";
  		document.getElementById("impianto_7").style.display="none";
  		document.getElementById("impianto_8").style.display="none";
  		document.getElementById("impianto_9").style.display="none";
  		document.getElementById("impianto_10").style.display="none";
  		document.getElementById("impianto_11").style.display="";
  		document.getElementById("impianto_12").style.display="none";
  		document.getElementById("impianto_13").style.display="none";
  		document.getElementById("impianto_14").style.display="none";
  		document.getElementById("impianto_15").style.display="none";
	}else if(valore==12){
		document.getElementById("impianto_0").style.display="none";
  		document.getElementById("impianto_1").style.display="none";
  		document.getElementById("impianto_2").style.display="none";
  		document.getElementById("impianto_3").style.display="none";
  		document.getElementById("impianto_4").style.display="none";
  		document.getElementById("impianto_5").style.display="none";
  		document.getElementById("impianto_6").style.display="none";
  		document.getElementById("impianto_7").style.display="none";
  		document.getElementById("impianto_8").style.display="none";
  		document.getElementById("impianto_9").style.display="none";
  		document.getElementById("impianto_10").style.display="none";
  		document.getElementById("impianto_11").style.display="none";
  		document.getElementById("impianto_12").style.display="";
  		document.getElementById("impianto_13").style.display="none";
  		document.getElementById("impianto_14").style.display="none";
  		document.getElementById("impianto_15").style.display="none";
	}else if(valore==13){
		document.getElementById("impianto_0").style.display="none";
  		document.getElementById("impianto_1").style.display="none";
  		document.getElementById("impianto_2").style.display="none";
  		document.getElementById("impianto_3").style.display="none";
  		document.getElementById("impianto_4").style.display="none";
  		document.getElementById("impianto_5").style.display="none";
  		document.getElementById("impianto_6").style.display="none";
  		document.getElementById("impianto_7").style.display="none";
  		document.getElementById("impianto_8").style.display="none";
  		document.getElementById("impianto_9").style.display="none";
  		document.getElementById("impianto_10").style.display="none";
  		document.getElementById("impianto_11").style.display="none";
  		document.getElementById("impianto_12").style.display="none";
  		document.getElementById("impianto_13").style.display="";
  		document.getElementById("impianto_14").style.display="none";
  		document.getElementById("impianto_15").style.display="none";
	}else if(valore==14){
		document.getElementById("impianto_0").style.display="none";
  		document.getElementById("impianto_1").style.display="none";
  		document.getElementById("impianto_2").style.display="none";
  		document.getElementById("impianto_3").style.display="none";
  		document.getElementById("impianto_4").style.display="none";
  		document.getElementById("impianto_5").style.display="none";
  		document.getElementById("impianto_6").style.display="none";
  		document.getElementById("impianto_7").style.display="none";
  		document.getElementById("impianto_8").style.display="none";
  		document.getElementById("impianto_9").style.display="none";
  		document.getElementById("impianto_10").style.display="none";
  		document.getElementById("impianto_11").style.display="none";
  		document.getElementById("impianto_12").style.display="none";
  		document.getElementById("impianto_13").style.display="none";
  		document.getElementById("impianto_14").style.display="";
  		document.getElementById("impianto_15").style.display="none";
	}else if(valore==15){
		document.getElementById("impianto_0").style.display="none";
  		document.getElementById("impianto_1").style.display="none";
  		document.getElementById("impianto_2").style.display="none";
  		document.getElementById("impianto_3").style.display="none";
  		document.getElementById("impianto_4").style.display="none";
  		document.getElementById("impianto_5").style.display="none";
  		document.getElementById("impianto_6").style.display="none";
  		document.getElementById("impianto_7").style.display="none";
  		document.getElementById("impianto_8").style.display="none";
  		document.getElementById("impianto_9").style.display="none";
  		document.getElementById("impianto_10").style.display="none";
  		document.getElementById("impianto_11").style.display="none";
  		document.getElementById("impianto_12").style.display="none";
  		document.getElementById("impianto_13").style.display="none";
  		document.getElementById("impianto_14").style.display="none";
  		document.getElementById("impianto_15").style.display="";
	}
  	
  }
  
  function updateContacts(countryObj, stateObj, selectedValue) {

    var country = document.forms['searchAccount'].elements[countryObj].value;
    var url = "ExternalContacts.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=searchAccount&stateObj=searchcodeContactState";
    window.frames['server_commands'].location.href=url;
  }

  function updateAccounts(countryObj, stateObj, selectedValue) {
    var country = document.forms['searchAccount'].elements[countryObj].value;
    var url = "Osm.do?command=States&country="+country+"&obj="+stateObj+"&selected="+selectedValue+"&form=searchAccount&stateObj=searchcodeAccountState";
    window.frames['server_commands'].location.href=url;
  }

  function continueUpdateState(stateObj, showText) {
	switch(stateObj){
      case '2':
        if(showText == 'true'){
          hideSpan('state31');
          showSpan('state41');
         /* document.forms['searchAccount'].searchcodeAccountState.options.selectedIndex = 0;*/
        } else {
          hideSpan('state41');
          showSpan('state31');
          if(document.getElementById("searchAccountOtherState1")!=null)
        	    document.getElementById("searchAccountOtherState1").value = '';
        }
        break;
	  case '1':
      default:
        if(showText == 'true'){
          hideSpan('state11');
          showSpan('state21');
          document.forms['searchAccount'].searchcodeContactState.options.selectedIndex = 0;
        } else {
          hideSpan('state21');
          showSpan('state11');
          document.forms['searchAccount'].searchContactOtherState.value = '';
        }
        break;
    }
  }

</script>
<dhv:include name="osm-search-name" none="true">
  <body onLoad="javascript:document.searchAccount.searchAccountName.focus();clearForm(); popolaComuniAsl2();">
</dhv:include>
<form name="searchAccount" action="Osm.do?command=Search" method="post">
<%-- Trails --%>
<table class="trails" cellspacing="0">
	<tr>
		<td>
			<a href="Osm.do?command=DashboardScelta">OSM</a> >
<!--			<a href="Osm.do"><dhv:label name="osm.osm">OSM Riconusciti</dhv:label></a> > -->
				Cerca OSM Riconosciuti
		</td>
	</tr>
</table>
<%-- End Trails --%>

<%-- Link aggiungi,ricerca, importa --%>
<table class="" cellspacing="0" >
	<tr>
		<td>
			<dhv:permission name="osm-osm-add">
				<a href="Osm.do?command=Add">Aggiungi OSM Riconosciuti</a>
			</dhv:permission>
			&nbsp;
		</td>
		<%-- %><td>
			<dhv:permission name="osm-osm-view">
				<a href="Osm.do?command=SearchForm">Ricerca OSM Riconosciuti</a>
			</dhv:permission>
			&nbsp;
		</td>--%>
		<td>
			<dhv:permission name="osm-upload-view">
				<a href="Osm.do?command=OsmUpload">Importa OSM Riconosciuti</a>
			</dhv:permission>
		</td>
		<td>
	
		</td>
	</tr>
</table>
<%-- End link aggiungi,ricerca, importa --%>

<% %>

<table cellpadding="2" cellspacing="2" border="0" width="100%">
  <tr>
    <td width="50%" valign="top">

      <table cellpadding="4" cellspacing="0" border="0" width="100%" class="details">
        <tr>
          <th colspan="2">
            <strong><dhv:label name="osm.accountInformationFilters">Account Information Filters</dhv:label></strong>
          </th>
        </tr>
        <tr>
          <td class="formLabel">
            <dhv:label name="organization.name">Account Name</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchAccountName" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountName") %>">
          </td>
        </tr>
        <dhv:include name="osm-search-number" none="true">
        <tr>
          <td class="formLabel">
            <dhv:label name="organizationaa.accountNumber">Numero di Riconoscimento</dhv:label>
          </td>
          <td>
            <p>&alpha;IT<input type="text" class="input" size="8" maxlength="8" name="searchAccountNumber" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountNumber") %>"></p>
          </td>
        </tr>
        </dhv:include>
        
         
        <dhv:include name="accounts-search-segment" none="true">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="osm.osm_search.accountSegment">Account Segment</dhv:label>
          </td>
          <td>
            <input type="text" size="23" name="searchAccountSegment" value="<%= SearchOrgListInfo.getSearchOptionValue("searchAccountSegment") %>">
            <dhv:evaluate if="<%= SegmentList.size() > 1 %>">
              <% SegmentList.setJsEvent("onchange=\"javascript:fillAccountSegmentCriteria();\"");%>
              <%= SegmentList.getHtmlSelect("viewOnlySegmentId", -1) %>
            </dhv:evaluate>
          </td>
        </tr>
        </dhv:include>
        <dhv:include name="osm-search-source" none="true">
        <tr style="display: none">
          <td class="formLabel">
            <dhv:label name="stabilimenti.accountSource">Account Source</dhv:label>
          </td>
          <td align="left" valign="bottom">
            <select size="1" name="listView">
              <option <%= SearchOrgListInfo.getOptionValue("all") %>><dhv:label name="accounts.all.accounts">All Accounts</dhv:label></option>
              <option <%= SearchOrgListInfo.getOptionValue("my") %>><dhv:label name="accounts.my.accounts">My Accounts</dhv:label></option>
            </select>
          </td>
        </tr>
        </dhv:include>
        <tr>
          <td class="formLabel">
            <dhv:label name="osm.accountStatus">Account Status</dhv:label>
          </td>
          <td align="left" valign="bottom">
          <select size="1" name="searchcodeStatoLab">
              <%--<option value="-1" <%=(SearchOrgListInfo.getFilterKey("statoLab") == -1)?"selected":""%>><dhv:label name="accounts.any">Any</dhv:label></option>--%>
              <option value="0" <%=(SearchOrgListInfo.getFilterKey("statoLab") == 0)?"selected":""%>><dhv:label name="">Autorizzato</dhv:label></option>
              <option value="1" <%=(SearchOrgListInfo.getFilterKey("statoLab") == 1)?"selected":""%>><dhv:label name="">Revocato</dhv:label></option>
              <option value="2" <%=(SearchOrgListInfo.getFilterKey("statoLab") == 2)?"selected":""%>><dhv:label name="">Sospeso</dhv:label></option>
              <option value="3" <%=(SearchOrgListInfo.getFilterKey("statoLab") == 3) ?"selected":""%>><dhv:label name="">In Domanda</dhv:label></option>
            </select>
            <%--<select size="1" name="listFilter2">
              <option value="-1" <%=(SearchOrgListInfo.getFilterKey("listFilter2") == -1)?"selected":""%>><dhv:label name="stabilimenti.any">Any</dhv:label></option>
              <option value="1" <%=(SearchOrgListInfo.getFilterKey("listFilter2") == 1)?"selected":""%>><dhv:label name="osm.active.osm">Active</dhv:label></option>
              <option value="0" <%=(SearchOrgListInfo.getFilterKey("listFilter2") == 0)?"selected":""%>><dhv:label name="osm.disabled.osm">Inactive</dhv:label></option>
            </select>--%>
          </td>
        </tr>
        <dhv:include name="organization.stage" none="true">
        <dhv:evaluate if="<%= StageList.getEnabledElementCount() > 1 %>">
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="account.stage">Stage</dhv:label>
          </td>
          <td>
            <%= StageList.getHtmlSelect("searchcodeStageId", SearchOrgListInfo.getSearchOptionValueAsInt("searchcodeStageId")) %>
          </td>
        </tr>
      </dhv:evaluate>  
      </dhv:include>
      <dhv:evaluate if="<%= SiteList.getEnabledElementCount() <= 1 %>">
        <input type="hidden" name="searchcodeStageId" id="searchcodeStageId" value="-1" />
      </dhv:evaluate>
      
      <% ComuniList.setJsEvent("onChange=popolaAsl();");%>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="stabilimenti.stabilimenti_add.City">City</dhv:label>
          </td>
          <td>
          
            <%= ComuniList.getHtmlSelectText("searchAccountCity",SearchOrgListInfo.getSearchOptionValue("searchAccountCity")) %>
            
           
            </td>
        </tr>
        <tr class="containerBody">
          <td nowrap class="formLabel">
            <dhv:label name="stabilimenti.stabilimenti_add.Stateince">Provincia</dhv:label>
          </td>
          <td>
            <%-- %><span name="state31" ID="state31" style="<%= AccountStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry"))? "" : " display:none" %>">
              <%= AccountStateSelect.getHtmlSelect("searchcodeAccountState", SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry"),SearchOrgListInfo.getSearchOptionValue("searchcodeAccountState")) %>
            </span>
            <span name="state41" ID="state41" style="<%= !AccountStateSelect.hasCountry(SearchOrgListInfo.getSearchOptionValue("searchcodeAccountCountry")) ? "" : " display:none" %>">
              <input type="text" size="23" name="searchAccountOtherState"  value="<%= toHtmlValue(SearchOrgListInfo.getSearchOptionValue("searchAccountOtherState")) %>">
            </span>--%>
            <%if(User.getContact().getState()!=null){
            if(User.getContact().getState().equals("AV")){ 
            %>
            <%--select name="searchAccountOtherState"  id="searchAccountOtherState3" onchange="javascript:popolaComboProvincia();">
               
            	<option value="AV" selected="selected">Avellino</option>
            	
            </select--%>
            
            
            	<input type="text" readonly="readonly" size="5" maxlength="5" name="searchAccountOtherState" value="AV">
            <%}else if(User.getContact().getState().equals("BN")){  %>
            <%--select name="searchAccountOtherState"  id="searchAccountOtherState4" onchange="javascript:popolaComboProvincia();">
                
            	<option value="BN" selected="selected">Benevento</option>
            	
            </select--%>
            
            	<input type="text" readonly="readonly" size="5" maxlength="5" name="searchAccountOtherState" value="BN">
            <%}else if(User.getContact().getState().equals("CE")){  %>
            <%-- %>select name="searchAccountOtherState" id="searchAccountOtherState5"  onchange="javascript:popolaComboProvincia();">
                <option value="CE" selected="selected">Caserta</option>
            </select--%>
            	<input type="text" readonly="readonly" size="7" maxlength="7" name="searchAccountOtherState" value="">
            <%}else if(User.getContact().getState().equals("NA")){  %>
             <%-- %>select name="searchAccountOtherState" id="searchAccountOtherState6" onchange="javascript:popolaComboProvincia();">
               
            	<option value="NA" selected="selected">Napoli</option>
            	
            </select--%>
            	<input type="text" readonly="readonly" size="5" maxlength="5" name="searchAccountOtherState" value="NA">
            <%}else if(User.getContact().getState().equals("SA")){  %>
            <%-- %>select name="searchAccountOtherState"  id="searchAccountOtherState7" onchange="javascript:popolaComboProvincia();">
                
            	<option value="SA" selected="selected">Salerno</option>
            </select--%>
            	<input type="text" readonly="readonly" size="5" maxlength="5" name="searchAccountOtherState" value="SA">
            <%}else{%>
            <select name="searchAccountOtherState" id="searchAccountOtherState1" onchange="javascript:popolaComboProvincia();">
                <option value=""></option>
            	<option value="AV">Avellino</option>
            	<option value="BN">Benevento</option>
            	<option value="CE">Caserta</option>
            	<option value="NA">Napoli</option>
            	<option value="SA">Salerno</option>
            </select>
            <%}}else{ %>
            <select name="searchAccountOtherState" id="searchAccountOtherState2" onchange="javascript:popolaComboProvincia();">
                <option value=""></option>
            	<option value="AV">Avellino</option>
            	<option value="BN">Benevento</option>
            	<option value="CE">Caserta</option>
            	<option value="NA">Napoli</option>
            	<option value="SA">Salerno</option>
            </select>
            <%} %>
          </td>
        </tr>
       
      <% SiteList.setJsEvent("onChange=popolaComboComuni()");%>
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="accounts.site">Site</dhv:label>
          </td>
          <td>
           <%= SiteList.getHtmlSelect("searchcodeOrgSiteId",SearchOrgListInfo.getSearchOptionValue("searchcodeOrgSiteId")) %>
            
          </td>
        </tr>
     <%--
        <tr>
          <td nowrap class="formLabel">
            <dhv:label name="">Categoria Rischio</dhv:label>
          </td>
          <td>
           <select name="searchcodecategoriaRischio">
           		<option value="-1">-- Tutte --</option>
           		<option value="1">1</option>
           		<option value="2">2</option>
           		<option value="3">3</option>
           		<option value="4">4</option>
           		<option value="5">5</option>
           </select>
          </td>
        </tr>
        
          <tr>
          <td nowrap class="formLabel">
            <dhv:label name="">Codice Allerta</dhv:label>
          </td>
          <td>
          <input type="hidden" id="ticketid" value="0" name="ticketidd">
   
           <input style="background-color: lightgray" readonly="readonly" type="text" size="20" value="<%= SearchOrgListInfo.getSearchOptionValue("searchcodiceAllerta") %>" id="id_allerta" name="searchcodiceAllerta" >
      &nbsp;[<a href="javascript:popLookupSelectorAllertaRicerca('id_allerta','name','ticket','');"><dhv:label name="accounts.accounts_add.select">Select</dhv:label></a>]
       </td>
     </tr>
     --%>
        <tr id="impianto_0">
          <td class="formLabel">
            Attività
          </td>
          <td>
         
            <%= impiantoZ.getHtmlSelect("searchcodeImpianto", SearchOrgListInfo.getSearchOptionValue("searchcodeImpianto")) %>
           </td>
        </tr>

      </table>
    </td>
  </tr>

        
</table>
<dhv:include name="osm-search-contacts" none="false">
  <input type="checkbox" name="searchContacts" value="true" <%= "true".equals(SearchOrgListInfo.getCriteriaValue("searchContacts"))? "checked":""%> />
  <dhv:label name="osm.search.includeContactsInSearchResults">Include contacts in search results</dhv:label><br />
  <br />
</dhv:include>
<input type="submit" value="<dhv:label name="button.search">Search</dhv:label>">
<input type="button" value="<dhv:label name="button.clear">Clear</dhv:label>" onClick="javascript:clearForm();">
<input type="hidden" name="source" value="searchForm">
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<script type="text/javascript">
  </script>
</form>
</body>

<% } %>
