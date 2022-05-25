<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
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
  - Version: $Id: accounts_tickets_modify.jsp 18488 2007-01-15 20:12:32Z matt $
  - Description:
  --%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio" %>
<%@page import="org.aspcfs.modules.tamponi.base.Tampone" %>

<jsp:useBean id="RicercaTamponi_1" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_3" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_4" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_5" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_6" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_7" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_8" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_9" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi_10" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="Tamponi" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_1" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_2" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_3" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_4" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_5" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_6" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_7" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_8" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_9" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="RicercaTamponi2_10" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="OrgDetails" class="org.aspcfs.modules.osmregistrati.base.Organization" scope="request"/>
<jsp:useBean id="TicketDetails" class="org.aspcfs.modules.tamponi.base.Ticket" scope="request"/>
<jsp:useBean id="ConseguenzePositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ResponsabilitaPositivita" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleo" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoDue" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoTre" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoQuattro" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoCinque" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoSei" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoSette" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoOtto" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoNove" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TitoloNucleoDieci" class="org.aspcfs.utils.web.LookupList" scope="request"/>

<jsp:useBean id="EsitoTampone" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiNonTrasformatiValori" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiTrasformati" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="AlimentiVegetali" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="TipoTampone" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SiteIdList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DepartmentList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolvedByDeptList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SeverityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="SourceList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="PriorityList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="DestinatarioTampone" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="causeList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="resolutionList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="ticketStateList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="EscalationList" class="org.aspcfs.utils.web.LookupList" scope="request"/>
<jsp:useBean id="CategoryList" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="UserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="resolvedUserList" class="org.aspcfs.modules.admin.base.UserList" scope="request"/>
<jsp:useBean id="SubList1" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList2" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="SubList3" class="org.aspcfs.modules.troubletickets.base.TicketCategoryList" scope="request"/>
<jsp:useBean id="actionPlans" class="org.aspcfs.modules.actionplans.base.ActionPlanList" scope="request"/>
<jsp:useBean id="insertActionPlan" class="java.lang.String" scope="request"/>
<jsp:useBean id="ContactList" class="org.aspcfs.modules.contacts.base.ContactList" scope="request"/>
<jsp:useBean id="User" class="org.aspcfs.modules.login.beans.UserBean" scope="session"/>
<jsp:useBean id="defectSelect" class="org.aspcfs.utils.web.HtmlSelect" scope="request"/>
<jsp:useBean id="defectCheck" class="java.lang.String" scope="request"/>
<jsp:useBean id="TimeZoneSelect" class="org.aspcfs.utils.web.HtmlSelectTimeZone" scope="request"/>
<%@ include file="../initPage.jsp" %>
<body onload="abilitaTamponi(details)">
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popServiceContracts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popAssets.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popCalendar.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popProducts.js"></script>
<script language="JavaScript" TYPE="text/javascript" SRC="javascript/popLookupSelect.js"></script>
<script language="JavaScript">


function abilitaTamponi(form)
{
 if(form.check1_1.checked==true)
     {
  
	 showRow1_tampone1();

		if(document.details.RicercaTamponi_1.lenght!=0){

			showEsitiTampone1();
			//mostraNextTampone2();
			}

	 
     }else{ 
         if(form.check2_1.checked==true)
     {
        	
    	 showRow1_tampone1();

    		if(document.details.RicercaTamponi2_1.lenght!=0){

    			showEsitiTipo2_tampone1();
    			//mostraNextTampone2();
    			}

     }
     }




 if(form.check1_2.checked==true)
 {
	 showRow1_tampone2();
	if(document.details.RicercaTamponi_2.lenght!=0){
		
		showEsitiTampone2();
		mostraNextTampone3();
		}


 }
 else{ 
	
     if(form.check2_2.checked==true)
 	{
    	
	 showRow1_tampone2();
	
		if(document.details.RicercaTamponi2_2.lenght!=0){
		
			showEsitiTipo2_tampone2();
			mostraNextTampone3();
			
			}

 }
 }





 if(form.check1_3.checked==true)
 {
	
 showRow1_tampone3();

	if(document.details.RicercaTamponi_3.lenght!=0){

		showEsitiTampone3();
		mostraNextTampone4();
		}

 
 }else{ 
     if(form.check2_3.checked==true)
 {
    
	 showRow1_tampone3();

		if(document.details.RicercaTamponi2_3.lenght!=0){

			showEsitiTipo2_tampone3();
			mostraNextTampone4();
			}

 }
 }


 if(form.check1_4.checked==true)
 {
	
 showRow1_tampone4();

	if(document.details.RicercaTamponi_4.lenght!=0){

		showEsitiTampone4();
		mostraNextTampone5();
		}

 
 }else{ 
     if(form.check2_4.checked==true)
 {
    	
	 showRow1_tampone4();

		if(document.details.RicercaTamponi2_4.lenght!=0){

			showEsitiTipo2_tampone4();
			mostraNextTampone5();
			}

 }
 }





 if(form.check1_5.checked==true)
 {
	
 showRow1_tampone5();

	if(document.details.RicercaTamponi_5.lenght!=0){

		showEsitiTampone5();
		mostraNextTampone6();
		}

 
 }else{ 
     if(form.check2_5.checked==true)
 {
    	
	 showRow1_tampone5();

		if(document.details.RicercaTamponi2_5.lenght!=0){

			showEsitiTipo2_tampone5();
			mostraNextTampone6();
			}

 }
 }





 if(form.check1_6.checked==true)
 {
 showRow1_tampone6();

	if(document.details.RicercaTamponi_6.lenght!=0){

		showEsitiTampone6();
		mostraNextTampone7();
		}

 
 }else{ 
     if(form.check2_6.checked==true)
 {
	 showRow1_tampone6();

		if(document.details.RicercaTamponi2_6.lenght!=0){

			showEsitiTipo2_tampone6();
			mostraNextTampone7();
			}

 }
 }





 if(form.check1_7.checked==true)
 {
 showRow1_tampone7();

	if(document.details.RicercaTamponi_7.lenght!=0){

		showEsitiTampone7();
		mostraNextTampone8();
		}

 
 }else{ 
     if(form.check2_7.checked==true)
 {
	 showRow1_tampone7();

		if(document.details.RicercaTamponi2_7.lenght!=0){

			showEsitiTipo2_tampone7();
			mostraNextTampone8();
			}

 }
 }




 if(form.check1_8.checked==true)
 {
 showRow1_tampone8();

	if(document.details.RicercaTamponi_8.lenght!=0){

		showEsitiTampone8();
		mostraNextTampone9();
		}

 
 }else{ 
     if(form.check2_8.checked==true)
 {
	 showRow1_tampone8();

		if(document.details.RicercaTamponi2_8.lenght!=0){

			showEsitiTipo2_tampone8();
			mostraNextTampone9();
			}

 }
 }




 if(form.check1_9.checked==true)
 {
 showRow1_tampone9();

	if(document.details.RicercaTamponi_9.lenght!=0){

		showEsitiTampone9();
		mostraNextTampone10();
		}

 
 }else{ 
     if(form.check2_9.checked==true)
 {
	 showRow1_tampone9();

		if(document.details.RicercaTamponi2_9.lenght!=0){

			showEsitiTipo2_tampone9();
			mostraNextTampone10();
			}

 }
 }




 if(form.check1_10.checked==true)
 {
 showRow1_tampone10();

	if(document.details.RicercaTamponi_1.lenght!=0){

		showEsitiTampone10();
		
		}

 
 }else{ 
     if(form.check2_10.checked==true)
 {
	 showRow1_tampone10();

		if(document.details.RicercaTamponi2_10.lenght!=0){

			showEsitiTipo2_tampone10();
		
			}

 }
 }


}


function showRow1_tampone1(){



	if(document.getElementById("check1_1").checked==true){
	document.getElementById("row1tampone1").style.display="block";
	document.getElementById("check2_1").disabled=true;
	}else{
		document.getElementById("check2_1").disabled=false;
		document.getElementById("row1tampone1").style.display="none";
		if(document.getElementById("check2_1").checked==true){
			document.getElementById("check1_1").disabled=true;
			document.getElementById("row2tampone1").style.display="block";
		}
		else{
			document.getElementById("check1_1").disabled=false;
			document.getElementById("row2tampone1").style.display="none";
		}
		}
	
	
	


	
}

function showRow1_tampone2(){

	if(document.getElementById("check1_2").checked==true){
		document.getElementById("row1tampone2").style.display="block";
		document.getElementById("check2_2").disabled=true;
		}else{
			document.getElementById("check2_2").disabled=false;
			document.getElementById("row1tampone2").style.display="none";
			if(document.getElementById("check2_2").checked==true){
				document.getElementById("check1_2").disabled=true;
				document.getElementById("row2tampone2").style.display="block";
			}
			else{
				document.getElementById("check1_2").disabled=false;
				document.getElementById("row2tampone2").style.display="none";
			}
			}
	
}

function showRow1_tampone3(){

	if(document.getElementById("check1_3").checked==true){
		document.getElementById("row1tampone3").style.display="block";
		document.getElementById("check2_3").disabled=true;
		}else{
			document.getElementById("check2_3").disabled=false;
			document.getElementById("row1tampone3").style.display="none";
			if(document.getElementById("check2_3").checked==true){
				document.getElementById("check1_3").disabled=true;
				document.getElementById("row2tampone3").style.display="block";
			}
			else{
				document.getElementById("check1_3").disabled=false;
				document.getElementById("row2tampone3").style.display="none";
			}
			}
	
}


function showRow1_tampone4(){

	if(document.getElementById("check1_4").checked==true){
		document.getElementById("row1tampone4").style.display="block";
		document.getElementById("check2_4").disabled=true;
		}else{
			document.getElementById("check2_4").disabled=false;
			document.getElementById("row1tampone4").style.display="none";
			if(document.getElementById("check2_4").checked==true){
				document.getElementById("check1_4").disabled=true;
				document.getElementById("row2tampone4").style.display="block";
			}
			else{
				document.getElementById("check1_4").disabled=false;
				document.getElementById("row2tampone4").style.display="none";
			}
			}
	
}


function showRow1_tampone5(){

	if(document.getElementById("check1_5").checked==true){
		document.getElementById("row1tampone5").style.display="block";
		document.getElementById("check2_5").disabled=true;
		}else{
			document.getElementById("check2_5").disabled=false;
			document.getElementById("row1tampone5").style.display="none";
			if(document.getElementById("check2_5").checked==true){
				document.getElementById("check1_5").disabled=true;
				document.getElementById("row2tampone5").style.display="block";
			}
			else{
				document.getElementById("check1_5").disabled=false;
				document.getElementById("row2tampone5").style.display="none";
			}
			}
	
}


function showRow1_tampone6(){

	if(document.getElementById("check1_6").checked==true){
		document.getElementById("row1tampone6").style.display="block";
		document.getElementById("check2_6").disabled=true;
		}else{
			document.getElementById("check2_6").disabled=false;
			document.getElementById("row1tampone6").style.display="none";
			if(document.getElementById("check2_6").checked==true){
				document.getElementById("check1_6").disabled=true;
				document.getElementById("row2tampone6").style.display="block";
			}
			else{
				document.getElementById("check1_6").disabled=false;
				document.getElementById("row2tampone6").style.display="none";
			}
			}
	
}


function showRow1_tampone7(){

	if(document.getElementById("check1_7").checked==true){
		document.getElementById("row1tampone7").style.display="block";
		document.getElementById("check2_7").disabled=true;
		}else{
			document.getElementById("check2_7").disabled=false;
			document.getElementById("row1tampone7").style.display="none";
			if(document.getElementById("check2_7").checked==true){
				document.getElementById("check1_7").disabled=true;
				document.getElementById("row2tampone7").style.display="block";
			}
			else{
				document.getElementById("check1_7").disabled=false;
				document.getElementById("row2tampone7").style.display="none";
			}
			}
	
}


function showRow1_tampone8(){

	if(document.getElementById("check1_8").checked==true){
		document.getElementById("row1tampone8").style.display="block";
		document.getElementById("check2_8").disabled=true;
		}else{
			document.getElementById("check2_8").disabled=false;
			document.getElementById("row1tampone8").style.display="none";
			if(document.getElementById("check2_8").checked==true){
				document.getElementById("check1_8").disabled=true;
				document.getElementById("row2tampone8").style.display="block";
			}
			else{
				document.getElementById("check1_8").disabled=false;
				document.getElementById("row2tampone8").style.display="none";
			}
			}
	
}


function showRow1_tampone9(){

	if(document.getElementById("check1_9").checked==true){
		document.getElementById("row1tampone9").style.display="block";
		document.getElementById("check2_9").disabled=true;
		}else{
			document.getElementById("check2_9").disabled=false;
			document.getElementById("row1tampone9").style.display="none";
			if(document.getElementById("check2_9").checked==true){
				document.getElementById("check1_9").disabled=true;
				document.getElementById("row2tampone9").style.display="block";
			}
			else{
				document.getElementById("check1_9").disabled=false;
				document.getElementById("row2tampone9").style.display="none";
			}
			}
	
}


function showRow1_tampone10(){

	if(document.getElementById("check1_10").checked==true){
		document.getElementById("row1tampone10").style.display="block";
		document.getElementById("check2_10").disabled=true;
		}else{
			document.getElementById("check2_10").disabled=false;
			document.getElementById("row1tampone10").style.display="none";
			if(document.getElementById("check2_10").checked==true){
				document.getElementById("check1_10").disabled=true;
				document.getElementById("row2tampone10").style.display="block";
			}
			else{
				document.getElementById("check1_10").disabled=false;
				document.getElementById("row2tampone10").style.display="none";
			}
			}
	
}


function showEsitiTampone1(){
	
	var aa=document.forms['details'].RicercaTamponi_1;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;

	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone1_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			document.getElementById("esitoTampone1_"+aa[i].value).style.display="none";
			
			}
		
}
	mostraNextTampone2();
	
}


function showEsitiTampone2(){
	
	var aa=document.forms['details'].RicercaTamponi_2;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;
	
	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone2_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			document.getElementById("esitoTampone2_"+aa[i].value).style.display="none";
			
			}
		
}
	mostraNextTampone3();
	
}

function showEsitiTampone3(){
	a=0;
	var aa=document.forms['details'].RicercaTamponi_3;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;
	
	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone3_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			document.getElementById("esitoTampone3_"+aa[i].value).style.display="none";
			
			}
		
}
	mostraNextTampone4();
}

function showEsitiTampone4(){

	
	
	var aa=document.forms['details'].RicercaTamponi_4;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;
	
	if(selected==true){
		
		a=i+1;
		
		document.getElementById("esitoTampone4_"+aa[i].value).style.display="block";
		
		}else{
			a=i+1;
		
			document.getElementById("esitoTampone4_"+aa[i].value).style.display="none";
			
			
			}
		
}

	mostraNextTampone5();

}

function showEsitiTampone5(){
	
	var aa=document.forms['details'].RicercaTamponi_5;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;
	
	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone5_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			document.getElementById("esitoTampone5_"+aa[i].value).style.display="none";
			
			}
		
}
	mostraNextTampone6();
}

function showEsitiTampone6(){
	
	var aa=document.forms['details'].RicercaTamponi_6;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;
	
	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone6_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			document.getElementById("esitoTampone6_"+aa[i].value).style.display="none";
			
			}
		
}
	mostraNextTampone7();
}

function showEsitiTampone7(){
	
	var aa=document.forms['details'].RicercaTamponi_7;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;
	
	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone7_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			document.getElementById("esitoTampone7_"+aa[i].value).style.display="none";
			
			}
		
}
	mostraNextTampone8();
}

function showEsitiTampone8(){
	
	var aa=document.forms['details'].RicercaTamponi_8;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;
	
	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone8_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			document.getElementById("esitoTampone8_"+aa[i].value).style.display="none";
			
			}
		
}
	mostraNextTampone9();
}

function showEsitiTampone9(){
	
	var aa=document.forms['details'].RicercaTamponi_9;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;
	
	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone9_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			document.getElementById("esitoTampone9_"+aa[i].value).style.display="none";
			
			}
		
}
	mostraNextTampone10();
}

function showEsitiTampone10(){
	
	var aa=document.forms['details'].RicercaTamponi_10;

	for(i=0;i<aa.length;i++){
	var selected=	aa[i].selected;
	
	if(selected==true){
		
		a=i+1;
		document.getElementById("esitoTampone10_"+aa[i].value).style.display="block";
		}else{
			a=i+1;
			document.getElementById("esitoTampone10_"+aa[i].value).style.display="none";
		
			}
		
}
}


function showEsitiTipo2_tampone1(){
	
	var bb=document.forms['details'].RicercaTamponi2_1;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;
	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone1").style.display="block";
		}else{
			b=c+1;
			document.getElementById("esito_"+bb[c].value+"_tampone1").style.display="none";
			
			}
		
}
	mostraNextTampone2();
}
function showEsitiTipo2_tampone2(){
	b=0;
	var bb=document.forms['details'].RicercaTamponi2_2;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;
	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone2").style.display="block";
		}else{
			b=c+1;
			document.getElementById("esito_"+bb[c].value+"_tampone2").style.display="none";
			
			}
		
}
	mostraNextTampone3();
}
function showEsitiTipo2_tampone3(){
	
	var bb=document.forms['details'].RicercaTamponi2_3;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;
	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone3").style.display="block";
		}else{
			b=c+1;
			document.getElementById("esito_"+bb[c].value+"_tampone3").style.display="none";
			
			}
		
}
	mostraNextTampone4();
}

function showEsitiTipo2_tampone4(){
	
	var bb=document.forms['details'].RicercaTamponi2_4;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;
	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone4").style.display="block";
		}else{
			b=c+1;
			document.getElementById("esito_"+bb[c].value+"_tampone4").style.display="none";
			
			}
		
}
	mostraNextTampone5();
}

function showEsitiTipo2_tampone5(){
	
	var bb=document.forms['details'].RicercaTamponi2_5;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;
	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone5").style.display="block";
		}else{
			b=c+1;
			document.getElementById("esito_"+bb[c].value+"_tampone5").style.display="none";
			
			}
		
}
	mostraNextTampone6();
}

function showEsitiTipo2_tampone6(){
	
	var bb=document.forms['details'].RicercaTamponi2_6;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;
	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone6").style.display="block";
		}else{
			b=c+1;
			document.getElementById("esito_"+bb[c].value+"_tampone6").style.display="none";
			
			}
		
}
	mostraNextTampone7();
}

function showEsitiTipo2_tampone7(){
	
	var bb=document.forms['details'].RicercaTamponi2_7;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;
	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone7").style.display="block";
		}else{
			b=c+1;
			document.getElementById("esito_"+bb[c].value+"_tampone7").style.display="none";
			
			}
		
}
	mostraNextTampone8();
}

function showEsitiTipo2_tampone8(){
	
	var bb=document.forms['details'].RicercaTamponi2_8;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;
	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone8").style.display="block";
		}else{
			b=c+1;
			document.getElementById("esito_"+bb[c].value+"_tampone8").style.display="none";
			
			}
		
}
	mostraNextTampone9();
}

function showEsitiTipo2_tampone9(){
	
	var bb=document.forms['details'].RicercaTamponi2_9;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;
	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone9").style.display="block";
		}else{
			b=c+1;
			document.getElementById("esito_"+bb[c].value+"_tampone9").style.display="none";
			
			}
		
}
	mostraNextTampone10();
}

function showEsitiTipo2_tampone10(){
	
	var bb=document.forms['details'].RicercaTamponi2_10;

	for(c=0;c<bb.length;c++){

	var selected1=	bb[c].selected;
	if(selected1==true){
		
		b=c+1;
		
		document.getElementById("esito_"+bb[c].value+"_tampone10").style.display="block";
		}else{
			b=c+1;
			document.getElementById("esito_"+bb[c].value+"_tampone10").style.display="none";
			
			}
		
}
}

function mostraNextTampone2(){

	document.getElementById("tampone2").style.display="block";
	}

	function mostraNextTampone3(){

		document.getElementById("tampone3").style.display="block";
		}


	function mostraNextTampone4(){

		document.getElementById("tampone4").style.display="block";
		
	}

	function mostraNextTampone5(){

		document.getElementById("tampone5").style.display="block";
	
		}


	function mostraNextTampone6(){

		document.getElementById("tampone6").style.display="";
		}


	function mostraNextTampone7(){

		document.getElementById("tampone7").style.display="";
		}


	function mostraNextTampone8(){

		document.getElementById("tampone8").style.display="";
		}


	function mostraNextTampone9(){

		document.getElementById("tampone9").style.display="";
		}


	function mostraNextTampone10(){

		document.getElementById("tampone10").style.display="";
		}


function abilita2(){
document.getElementById("nucleodue").style.display="";

}

function abilita3(){
document.getElementById("nucleotre").style.display="";
}

function abilita4(){
document.getElementById("nucleoquattro").style.display="";
}

function abilita5(){
document.getElementById("nucleocinque").style.display="";
}

function abilita6(){
document.getElementById("nucleosei").style.display="";
}

function abilita7(){
document.getElementById("nucleosette").style.display="";
}

function abilita8(){
document.getElementById("nucleootto").style.display="";
}

function abilita9(){
document.getElementById("nucleonove").style.display="";
}

function abilita10(){
document.getElementById("nucleodieci").style.display="";
}

//aggiunto da d.dauria
function abilitaNucleoIspettivo(form)
{
 if(form.nucleoIspettivo.value != -1)
     {
        sel3 = document.getElementById("nucleouno").style.display="";
     }
 if(form.nucleoIspettivoDue.value != -1)
     {
        sel4 = document.getElementById("nucleodue").style.display="";
     }  
  if(form.nucleoIspettivoTre.value != -1)
     {
        document.getElementById("nucleotre").style.display="";
     }        
  if(form.nucleoIspettivoQuattro.value != -1)
     {
        document.getElementById("nucleoquattro").style.display="";
     }         
   if(form.nucleoIspettivoCinque.value != -1)
     {
        document.getElementById("nucleocinque").style.display="";
     }           
   if(form.nucleoIspettivoSei.value != -1)
     {
        document.getElementById("nucleosei").style.display="";
     }           
   if(form.nucleoIspettivoSette.value != -1)
     {
        document.getElementById("nucleosette").style.display="";
     }           
    if(form.nucleoIspettivoOtto.value != -1)
     {
        document.getElementById("nucleootto").style.display="";
     }          
    if(form.nucleoIspettivoNove.value != -1)
     {
        document.getElementById("nucleonove").style.display="";
     }          
    if(form.nucleoIspettivoDieci.value != -1)
     {
        document.getElementById("nucleodieci").style.display="";
     }          

}


/*function abilitaTestoAlimentoComposto()
{
 alimenti = document.getElementById("alimentiComposti");
 testo = document.getElementById("testoAlimentoComposto"); 
 if (alimenti.checked)
 {
  testo.style.visibility = "visible";
 }
 else
 {
  testo.style.visibility = "hidden";
 } 
}*/
/*
function abilitaCheckSegnalazione()
{
   allerta = document.getElementById("allerta");
   nonConformita = document.getElementById("nonConformita");
   if(nonConformita.checked)
    { allerta.checked = false;
    }
}
*/
/*
function abilitaCheckAllerta()
{
   allerta = document.getElementById("allerta");
   nonConformita = document.getElementById("nonConformita");
   if(allerta.checked)
    { nonConformita.checked = false;
    }
}

*/
/*
function disabilitaNonTrasformati(form)
{
   
   
   if(form.alimentiOrigineAnimaleTrasformati.value == -1)
     {
        sel3 = document.getElementById("lookupNonTrasformati");
        sel3.style.visibility = "visible";
     }
     else
     {
      sel3 = document.getElementById("lookupNonTrasformati");
      sel3.style.visibility = "hidden";
      form.alimentiOrigineAnimaleNonTrasformati.value = -1;
      sel4 = document.getElementById("lookupNonTrasformatiValori");
      sel4.style.visibility = "hidden";
      form.alimentiOrigineAnimaleNonTrasformatiValori.value = -1;
     }  

}
*/
//aggiunto per positività
/*function abilitaNote(form)
{
    
   
   if(form.conseguenzePositivita.value == 4)
   {
    note=document.getElementById("note");
    note.style.visibility="visible";
    note2=document.getElementById("note_etichetta");
    note2.style.visibility="visible";
   } 
   else
   {
    note=document.getElementById("note");
    note.style.visibility="hidden";
    note2=document.getElementById("note_etichetta");
    note2.style.visibility="hidden";
   
   }
}
*/
/*
function controlloLookup(form)
{
    //aggiunto da d.dauria
    sel = document.getElementById("lookupNonTrasformati");
    sel.style.visibility = "hidden"; 
     sel2 = document.getElementById("lookupNonTrasformatiValori");
    sel2.style.visibility = "hidden";
     sel3 = document.getElementById("lookupTrasformati");
    sel3.style.visibility = "hidden"; 
     sel4 = document.getElementById("lookupVegetale");
    sel4.style.visibility = "hidden";
    
}

function abilitaLookupOrigineAnimale()
{
    alimentiOrigine = document.getElementById("alimentiOrigineAnimale");
    sel = document.getElementById("lookupNonTrasformati");
    sel2 = document.getElementById("lookupNonTrasformatiValori");
    sel3 = document.getElementById("lookupTrasformati");
    if(alimentiOrigine.checked)
    { sel.style.visibility = "visible";
      sel3.style.visibility = "visible";
    }
    else
    { sel.style.visibility = "hidden";
      sel2.style.visibility = "hidden";
      sel3.style.visibility = "hidden";
    }  
}

function abilitaSpecie(form)
{
    if((form.alimentiOrigineAnimaleNonTrasformati.value >= 1) && (form.alimentiOrigineAnimaleNonTrasformati.value <= 4))
     {
      sel2 = document.getElementById("lookupNonTrasformatiValori");
      sel2.style.visibility = "visible";
     } 
    else
     {
      sel2 = document.getElementById("lookupNonTrasformatiValori");
      form.alimentiOrigineAnimaleNonTrasformatiValori.value= -1;
      sel2.style.visibility = "hidden";
     } 
    if(form.alimentiOrigineAnimaleNonTrasformati.value == -1)
     {
        sel3 = document.getElementById("lookupTrasformati");
        sel3.style.visibility = "visible";
        sel2 = document.getElementById("lookupNonTrasformatiValori");
        sel2.style.visibility = "hidden";
     }
     else
     {
      sel3 = document.getElementById("lookupTrasformati");
      sel3.style.visibility = "hidden";
      form.alimentiOrigineAnimaleTrasformati.value = -1;
      
     }   
}


function abilitaLookupOrigineVegetale()
{
    alimentiOrigine = document.getElementById("alimentiOrigineVegetale");
    sel2 = document.getElementById("lookupVegetale");
    if(alimentiOrigine.checked)
    { sel2.style.visibility = "visible";
    }
    else
    { 
     sel2.style.visibility = "hidden";
    }  
}

*/

//fine delle modifiche

/*
function updateSubList1() {
  var orgId = document.forms['details'].orgId.value;
  if(orgId != '-1'){
    var sel = document.forms['details'].elements['catCode'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTicketsTamponi.do?command=CategoryJSList&form=details&catCode=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  } else {
    var sel = document.forms['details'].elements['catCode'];
    sel.options.selectedIndex = 0;
    alert(label("select.account.first",'You have to select an Account first'));
    return;
  }
}
function updateSubList2() {
  var orgId = document.forms['details'].orgId.value;
  var sel = document.forms['details'].elements['subCat1'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTicketsTamponi.do?command=CategoryJSList&form=details&subCat1=" + escape(value)+'&orgId='+orgId;
  window.frames['server_commands'].location.href=url;
}
<dhv:include name="ticket.subCat2" none="true">
function updateSubList3() {
  var orgId = document.forms['details'].orgId.value;
  var sel = document.forms['details'].elements['subCat2'];
  var value = sel.options[sel.selectedIndex].value;
  var url = "TroubleTicketsTamponi.do?command=CategoryJSList&form=details&subCat2=" + escape(value)+'&orgId='+orgId;
  window.frames['server_commands'].location.href=url;
}
</dhv:include>
<dhv:include name="ticket.subCat3" none="true">
  function updateSubList4() {
    var orgId = document.forms['details'].orgId.value;
    var sel = document.forms['details'].elements['subCat3'];
    var value = sel.options[sel.selectedIndex].value;
    var url = "TroubleTicketsTamponi.do?command=CategoryJSList&form=details&subCat3=" + escape(value)+'&orgId='+orgId;
    window.frames['server_commands'].location.href=url;
  }
</dhv:include>
function updateUserList() {
  var sel = document.forms['details'].elements['departmentCode'];
  var value = sel.options[sel.selectedIndex].value;
  var orgSite = document.forms['details'].elements['orgSiteId'].value;
  var url = "TroubleTicketsTamponi.do?command=DepartmentJSList&form=details&dept=Assigned&orgSiteId="+ orgSite +"&populateResourceAssigned=true&resourceAssignedDepartmentCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
function updateResolvedByUserList() {
  var sel = document.forms['details'].elements['resolvedByDeptCode'];
  var value = sel.options[sel.selectedIndex].value;
  var orgSite = document.forms['details'].elements['orgSiteId'].value;
  var url = "TroubleTicketsTamponi.do?command=DepartmentJSList&form=details&dept=Resolved&orgSiteId="+ orgSite + "&populateResolvedBy=true&resolvedByDepartmentCode=" + escape(value);
  window.frames['server_commands'].location.href=url;
}
  function changeDivContent(divName, divContents) {
  if(document.layers){
    // Netscape 4 or equiv.
    divToChange = document.layers[divName];
    divToChange.document.open();
    divToChange.document.write(divContents);
    divToChange.document.close();
  } else if(document.all){
    // MS IE or equiv.
    divToChange = document.all[divName];
    divToChange.innerHTML = divContents;
  } else if(document.getElementById){
    // Netscape 6 or equiv.
    divToChange = document.getElementById(divName);
    divToChange.innerHTML = divContents;
  }
}*/
function resetNumericFieldValue(fieldId){
  document.getElementById(fieldId).value = -1;
}
function checkForm(form) {
  formTest = true;
  message = "";
  
  //aggiunto da d.dauria
    //alimentiOrigine = document.getElementById("alimentiOrigineAnimale");
    //sel = document.getElementById("lookupNonTrasformati");
    //sel2 = document.getElementById("lookupNonTrasformatiValori");
    //sel3 = document.getElementById("lookupTrasformati");
    //if(((alimentiOrigine.checked) && (form.alimentiOrigineAnimaleNonTrasformati.value == "-1")) && ((alimentiOrigine.checked) && (form.alimentiOrigineAnimaleTrasformati.value == "-1")))
    //{ message +=label("TESTO1","Controllare che il campo Alimenti Origine Animale sia stato riempito\n"); formTest = false; }
  /*  if((alimentiOrigine.checked) &&(form.alimentiOrigineAnimaleTrasformati.value == "-1"))
    { message +=label("TESTO2","Controllare che il campo Alimenti Origine Animale Trasformati sia stato selezionato\n"); formTest = false; }
    */
if (form.assignedDate.value == "") { 
    message += label("check.ticket.dataRichiesta.entered","- Controlla che \"Data Prelievo\" sia stata selezionata\r\n");
    formTest = false;
  }
 
  <dhv:include name="ticket.resolution" none="false">
  if (form.closeNow.checked && form.solution.value == "") { 
    message += label("check.ticket.resolution.atclose","- Resolution needs to be filled in when closing a ticket\r\n");
    formTest = false;
  }
  </dhv:include>
  <dhv:include name="ticket.actionPlans" none="false">
    if (form.insertActionPlan.checked && form.assignedTo.value <= 0) {
      message += label("check.ticket.assignToUser","- Please assign the ticket to create the related action plan.\r\n");
      formTest = false;
    }
    if (form.insertActionPlan.checked && form.actionPlanId.value <= 0) {
      message += label("check.actionplan","- Please select an action plan to be inserted.\r\n");
      formTest = false;
    }
  </dhv:include>
  if (formTest == false) {
    alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
    return false;
  } else {
    return true;
  }
}

function setAssignedDate(){
  resetAssignedDate();
  if (document.forms['details'].assignedTo.value > 0){
    document.forms['details'].assignedDate.value = document.forms['details'].currentDate.value;
  }
}

function resetAssignedDate(){
  document.forms['details'].assignedDate.value = '';
}  

function setField(formField,thisValue,thisForm) {
  var frm = document.forms[thisForm];
  var len = document.forms[thisForm].elements.length;
  var i=0;
  for( i=0 ; i<len ; i++) {
    if (frm.elements[i].name.indexOf(formField)!=-1) {
      if(thisValue){
        frm.elements[i].value = "1";
      } else {
        frm.elements[i].value = "0";
      }
    }
  }
}

function selectUserGroups() {
  var siteId = document.forms['details'].orgSiteId.value;
  if ('<%= OrgDetails.getOrgId() %>' != '-1') {
    popUserGroupsListSingle('userGroupId','changeUserGroup', '&userId=<%= User.getUserRecord().getId() %>&siteId='+siteId);
  } else {
    alert(label("select.account.first",'You have to select an Account first'));
    return;
  }
}

function popKbEntries() {
  var siteId = document.forms['details'].orgSiteId.value;
  var form = document.forms['details'];
  var catCode = form.elements['catCode'];
  var catCodeValue = catCode.options[catCode.selectedIndex].value;
  if (catCodeValue == '0') {
    alert(label('','Please select a category first'));
    return;
  }
  var subCat1 = form.elements['subCat1'];
  var subCat1Value = subCat1.options[subCat1.options.selectedIndex].value;
<dhv:include name="ticket.subCat2" none="true">
  var subCat2 = form.elements['subCat2'];
  var subCat2Value = subCat2.options[subCat2.options.selectedIndex].value;
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  var subCat3 = form.elements['subCat3'];
  var subCat3Value = subCat3.options[subCat3.options.selectedIndex].value;
</dhv:include>
  var url = 'KnowledgeBaseManager.do?command=Search&popup=true&searchcodeSiteId='+siteId+'&searchcodeCatCode='+catCodeValue;
  url = url + '&searchcodeSubCat1='+ subCat1Value;
<dhv:include name="ticket.subCat2" none="true">
  url = url + '&searchcodeSubCat2='+ subCat2Value;
</dhv:include>
<dhv:include name="ticket.subCat2" none="true">
  url = url + '&searchcodeSubCat3='+ subCat3Value;
</dhv:include>
  popURL(url, 'KnowledgeBase','600','550','yes','yes');
}
</script>
 <% String esito1="";
	  String esito2="";
	  String esito3="";
	  String esito4="";
	  
	  String esito1_t2="";
	  String esito2_t2="";
	  String esito3_t2="";
	  String esito4_t2="";
	  
	  String esito1_t3="";
	  String esito2_t3="";
	  String esito3_t3="";
	  String esito4_t3="";
	  
	  String esito1_t4="";
	  String esito2_t4="";
	  String esito3_t4="";
	  String esito4_t4="";
	  
	  String esito1_t5="";
	  String esito2_t5="";
	  String esito3_t5="";
	  String esito4_t5="";
	  
	  String esito1_t6="";
	  String esito2_t6="";
	  String esito3_t6="";
	  String esito4_t6="";
	  
	  String esito1_t7="";
	  String esito2_t7="";
	  String esito3_t7="";
	  String esito4_t7="";
	  
	  String esito1_t8="";
	  String esito2_t8="";
	  String esito3_t8="";
	  String esito4_t8="";
	  
	  String esito1_t9="";
	  String esito2_t9="";
	  String esito3_t9="";
	  String esito4_t9="";
	  
	  String esito1_t10="";
	  String esito2_t10="";
	  String esito3_t10="";
	  String esito4_t10="";
	
  
  
  
   
     Tampone t1=TicketDetails.getListaTamponi().get(1);
   Tampone t2=TicketDetails.getListaTamponi().get(2);
   Tampone t10=TicketDetails.getListaTamponi().get(10);
   Tampone t9=TicketDetails.getListaTamponi().get(9);
   Tampone t8=TicketDetails.getListaTamponi().get(8);
      Tampone t7=TicketDetails.getListaTamponi().get(7);
  Tampone t6=TicketDetails.getListaTamponi().get(6);
     Tampone t5=TicketDetails.getListaTamponi().get(5);
     Tampone t4=TicketDetails.getListaTamponi().get(4);
     Tampone t3=TicketDetails.getListaTamponi().get(3);
    
     
   
 Iterator<Integer> it=t1.getRicerca().keySet().iterator();
  while(it.hasNext()){
  int kiaveRicerca=it.next();
  if(kiaveRicerca==1){
	  if(t1.getEsiti().get(kiaveRicerca)!=null)
	  esito1=t1.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t1.getEsiti().get(kiaveRicerca)!=null)
	  esito2=t1.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t1.getEsiti().get(kiaveRicerca)!=null)
	  esito3=t1.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t1.getEsiti().get(kiaveRicerca)!=null)
	  esito4=t1.getEsiti().get(kiaveRicerca);
  }
  }
  
  if(t2!=null){
  Iterator<Integer> it2=t2.getRicerca().keySet().iterator();
  while(it2.hasNext()){
  int kiaveRicerca=it2.next();
  if(kiaveRicerca==1){
	  if(t2.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t2=t2.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t2.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t2=t2.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t2.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t2=t2.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t2.getEsiti().get(kiaveRicerca)!=null)
	  esito4=t2.getEsiti().get(kiaveRicerca);
  }
  }}
  
  if(t3!=null){
  
  Iterator<Integer> it3=t3.getRicerca().keySet().iterator();
  while(it3.hasNext()){
  int kiaveRicerca=it3.next();
  if(kiaveRicerca==1){
	  if(t3.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t3=t1.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t3.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t3=t3.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t3.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t3=t3.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t3.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t3=t3.getEsiti().get(kiaveRicerca);
  }
  }
  }
  if(t4!=null){
  
  Iterator<Integer> it4=t4.getRicerca().keySet().iterator();
  while(it4.hasNext()){
  int kiaveRicerca=it4.next();
  if(kiaveRicerca==1){
	  if(t4.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t4=t4.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t4.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t4=t4.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t4.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t4=t4.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t4.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t4=t4.getEsiti().get(kiaveRicerca);
  }
  }
  }
  if(t5!=null){
	  
  Iterator<Integer> it5=t5.getRicerca().keySet().iterator();
  while(it5.hasNext()){
  int kiaveRicerca=it5.next();
  if(kiaveRicerca==1){
	  if(t5.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t5=t5.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t5.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t5=t5.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t5.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t5=t5.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t5.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t5=t5.getEsiti().get(kiaveRicerca);
  }
  }}
  
	  if(t6!=null){
  Iterator<Integer> it6=t6.getRicerca().keySet().iterator();
  while(it6.hasNext()){
  int kiaveRicerca=it6.next();
  if(kiaveRicerca==1){
	  if(t6.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t6=t6.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t6.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t6=t6.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t6.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t6=t6.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t6.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t6=t6.getEsiti().get(kiaveRicerca);
  }
  }}
	  
	  if(t7!=null){
  
  
  Iterator<Integer> it7=t7.getRicerca().keySet().iterator();
  while(it7.hasNext()){
  int kiaveRicerca=it7.next();
  if(kiaveRicerca==1){
	  if(t7.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t7=t7.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t7.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t7=t7.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t7.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t7=t7.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t7.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t7=t7.getEsiti().get(kiaveRicerca);
  }
  }
	  }
	  if(t8!=null){
  
  Iterator<Integer> it8=t8.getRicerca().keySet().iterator();
  while(it8.hasNext()){
  int kiaveRicerca=it8.next();
  if(kiaveRicerca==1){
	  if(t8.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t8=t8.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t8.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t8=t8.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t8.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t8=t8.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t8.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t8=t8.getEsiti().get(kiaveRicerca);
  }
  }}
	  if(t9!=null){
  
  
  Iterator<Integer> it9=t9.getRicerca().keySet().iterator();
  while(it9.hasNext()){
  int kiaveRicerca=it9.next();
  if(kiaveRicerca==1){
	  if(t9.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t9=t9.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t9.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t9=t9.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t9.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t9=t9.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t9.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t9=t9.getEsiti().get(kiaveRicerca);
  }
  }}
	  
	  if(t10!=null){
  
  
  Iterator<Integer> it10=t10.getRicerca().keySet().iterator();
  while(it10.hasNext()){
  int kiaveRicerca=it10.next();
  if(kiaveRicerca==1){
	  if(t10.getEsiti().get(kiaveRicerca)!=null)
	  esito1_t10=t10.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==2){
	  if(t10.getEsiti().get(kiaveRicerca)!=null)
	  esito2_t10=t10.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==3){
	  if(t10.getEsiti().get(kiaveRicerca)!=null)
	  esito3_t10=t10.getEsiti().get(kiaveRicerca);
  }
  if(kiaveRicerca==4){
	  if(t10.getEsiti().get(kiaveRicerca)!=null)
	  esito4_t10=t10.getEsiti().get(kiaveRicerca);
  }
  }}
  
  
  %>

<form name="details" action="OsmRegistratiTamponi.do?command=UpdateTicket&auto-populate=true<%= addLinkParams(request, "popup|popupType|actionId") %>" onSubmit="return checkForm(this);" method="post" >


<input type="hidden" name = "idControlloUfficiale" value ="<%= request.getAttribute("idC")%>">
<dhv:evaluate if="<%= !isPopup(request) %>">
<%-- Trails --%>
<table class="trails" cellspacing="0">
<tr>
<td>
  <a href="OsmRegistrati.do">OSM Registrati</a> >
  <a href="OsmRegistrati.do?command=Search"><dhv:label name="accounts.SearchResults">Search Results</dhv:label></a> >
  <a href="OsmRegistrati.do?command=Details&orgId=<%=OrgDetails.getOrgId()%>"><dhv:label name="osm.details">Account Details</dhv:label></a> >
 <a href="OsmRegistrati.do?command=ViewVigilanza&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Controlli Ufficiali</dhv:label></a> >
  <a href="OsmRegistratiVigilanza.do?command=TicketDetails&id=<%= request.getAttribute("idC")%>&orgId=<%=TicketDetails.getOrgId()%>"><dhv:label name="">Controllo Ufficiale</dhv:label></a> >
   <% if (request.getParameter("return") == null) {%>
  <a href="OsmRegistratiTamponi.do?command=TicketDetails&id=<%=TicketDetails.getId()%>"><dhv:label name="tamponi.dettagli">Scheda Tampone</dhv:label></a> >
  <%}%>
  <dhv:label name="tamponi.modify">Modifica Tampone</dhv:label>
</td>
</tr>
</table>
<%-- End Trails --%>
</dhv:evaluate>
<iframe src="empty.html" name="server_commands" id="server_commands" style="visibility:hidden" height="0"></iframe>
<dhv:container name="osm" selected="tamponi" object="OrgDetails" param='<%= "orgId=" + OrgDetails.getOrgId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
  <dhv:container name="stabilimentitamponi" selected="details" object="TicketDetails" param='<%= "id=" + TicketDetails.getId() %>' appendToUrl='<%= addLinkParams(request, "popup|popupType|actionId") %>'>
    <%--@ include file="accounts_ticket_header_include_tamponi.jsp" --%>
     <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
      <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
          <dhv:permission name="osmregistrati-osmregistrati-tamponi-edit">
            <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='OsmRegistratiTamponi.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
           </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='OsmRegistrati.do?command=ViewTamponi&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='OsmRegistratiTamponi.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
      </dhv:evaluate>
      <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
          <dhv:permission name="osmregistrati-osmregistrati-tamponi-edit">
            <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)" />
          </dhv:permission>
           <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='OsmRegistrati.do?command=ViewTamponi&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
           <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
            <input type="submit" value="Annulla" onClick="javascript:this.form.action='OsmRegistratiTamponi.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
           </dhv:evaluate>
          <%= showAttribute(request, "closedError") %>
       </dhv:evaluate>
      </dhv:evaluate>
    <br />
    <dhv:formMessage />
    <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
      <th colspan="2">
        <strong><dhv:label name="Tamponi.information">Informazioni Tampone</dhv:label></strong>
      </th>
	</tr>
	 <dhv:include name="osmregistrati-sites" none="true">
 <%--  <dhv:evaluate if="<%= SiteIdList.size() > 1 %>"> --%>
    <tr class="containerBody">
      <td nowrap class="formLabel">
        <dhv:label name="stabilimenti.site">Site</dhv:label>
      </td>
      <td>
       <%=SiteIdList.getSelectedValue(OrgDetails
										.getSiteId())%>
          <input type="hidden" name="siteId" value="<%=OrgDetails.getSiteId()%>" >
      
      </td>
    </tr>
<%--</dhv:evaluate>  --%>
  <dhv:evaluate if="<%= SiteIdList.size() <= 1 %>">
    <input type="hidden" name="siteId" id="siteId" value="-1" />
  </dhv:evaluate>
 </dhv:include>
	<% if (!"true".equals(request.getParameter("contactSet"))) { %>
   <%--<tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzioni.richiedente">OSM</dhv:label>
    </td>
   
     
     <td>
        <%= toHtml(OrgDetails.getName()) %>
        <input type="hidden" name="orgId" value="<%=OrgDetails.getOrgId()%>">
        <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  OrgDetails.getSiteId() %>" />
      </td>--%>
    
  </tr>
  <% }else{ %>
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getId() > 0 ? TicketDetails.getOrgSiteId() : User.getSiteId()%>" />
    <input type="hidden" name="orgId" value="<%= toHtmlValue(request.getParameter("orgId")) %>">
    <input type="hidden" name="contactId" value="<%= request.getParameter("contactId") %>">
  <% }
	
	%>
    
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
 <%-- 
<tr class="containerBody" >
    <td valign="top" class="formLabel">
      Tamponi
    </td>
    
    
      <td>
    <table>
    <tr>
    <td>Tampone 1</td>
     <td>
     <table>
     <tr><td><input type="checkbox" value="1" name="check1_1" id="check1_1"  <%if(t1!=null){ if(TicketDetails.getListaTamponi().get(1).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone1()" ></td></tr>
     <tr><td><input type="checkbox" value="2" name="check2_1" id="check2_1"  <%if(t1!=null){ if(TicketDetails.getListaTamponi().get(1).getTipo()==2){ %> checked="checked" <%}} %> onclick="showRow1_tampone1()" ></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_1.setJsEvent("onChange=showEsitiTampone1()"); %>
    <td>
    <table >
    <tr valign="top" id="row1tampone1" style="display: none">
   <%
   int idsuperfice=-1;
   String superfice="";
   if(t1!=null){
	   idsuperfice=t1.getSuperfice();
	superfice=t1.getSuperficeStringa();   
   }
	   %>
    <td><%=Tamponi.getHtmlSelect("Tamponi1",idsuperfice)  %></td>
    <td><%=RicercaTamponi_1.getHtmlSelect("RicercaTamponi_1",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esitiTampone1">
   <tr id="esitoTampone1_1" style="display: none"><td> 1. <input type="text" name="esitoTampone1_1" <%if(t1!=null){if(t1.getTipo()==1){ %> value="<%=esito1 %>" <%}} %>></td></tr>
    <tr id="esitoTampone1_2" style="display: none"><td>2. <input type="text" name="esitoTampone1_2" <%if(t1!=null){if(t1.getTipo()==1){ %> value="<%=esito2 %>" <%}} %>></td></tr>
    <tr id="esitoTampone1_3" style="display: none"><td>3. <input type="text" name="esitoTampone1_3" <%if(t1!=null){if(t1.getTipo()==1){ %> value="<%=esito3 %>" <%}} %>></td></tr>
    <tr id="esitoTampone1_4" style="display: none"><td>4. <input type="text" name="esitoTampone1_4" <%if(t1!=null){if(t1.getTipo()==1){ %> value="<%=esito4 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone1" style="display: none">
    <%RicercaTamponi2_1.setJsEvent("onChange=showEsitiTipo2_tampone1()"); %>
  
    <td> <textarea rows="5" cols="30" name="superfice1"><%=superfice %></textarea> </td>
    <td><%=RicercaTamponi2_1.getHtmlSelect("RicercaTamponi2_1",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone1" style="display: none"><td>1. <input type="text" name="esito_1_tampone1" <%if(t1!=null){if(t1.getTipo()==2){ %> value="<%=esito1 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone1" style="display: none"><td>2. <input type="text" name="esito_2_tampone1" <%if(t1!=null){if(t1.getTipo()==2){ %> value="<%=esito2 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone1" style="display: none"><td>3. <input type="text" name="esito_3_tampone1" <%if(t1!=null){if(t1.getTipo()==2){ %> value="<%=esito3 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone1" style="display: none"><td>4. <input type="text" name="esito_4_tampone1" <%if(t1!=null){if(t1.getTipo()==2){ %> value="<%=esito4 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
   
     
    
    
    
    
    
     <tr id="tampone2" style="display: none">
    <td>Tampone 2</td>
     <td>
     <table>
     
     <tr><td><input type="checkbox" value="1" name="check1_2" id="check1_2"  <%if(t2!=null){ if(TicketDetails.getListaTamponi().get(2).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone2()"></td></tr>
     <tr><td><input type="checkbox" value="2" name="check2_2" id="check2_2"  <%if(t2!=null){ if(TicketDetails.getListaTamponi().get(2).getTipo()==2){ %> checked="checked" <%}} %> onclick="showRow1_tampone2()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_2.setJsEvent("onChange=showEsitiTampone2()"); %>
    <td>
    <table >
   
    
    
    
    
    <tr valign="top" id="row1tampone2" style="display: none">
   <%
   int idsuperfice2=-1;
   String superfice2="";
   if(t2!=null){
	   idsuperfice2=t2.getSuperfice();
	superfice2=t2.getSuperficeStringa();   
   }
	   %>
    <td><%=Tamponi.getHtmlSelect("Tamponi2",idsuperfice2) %></td>
    <td><%=RicercaTamponi_2.getHtmlSelect("RicercaTamponi_2",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esitiTampone1">
    <tr id="esitoTampone2_1" style="display: none"><td>1. <input type="text" name="esitoTampone2_1" <%if(t2!=null){if(t2.getTipo()==1){ %> value="<%=esito1_t2 %>" <%}} %>></td></tr>
    <tr id="esitoTampone2_2" style="display: none"><td>2. <input type="text" name="esitoTampone2_2" <%if(t2!=null){if(t2.getTipo()==1){ %> value="<%=esito2_t2 %>" <%}} %>></td></tr>
    <tr id="esitoTampone2_3" style="display: none"><td>3. <input type="text" name="esitoTampone2_3" <%if(t2!=null){if(t2.getTipo()==1){ %> value="<%=esito3_t2 %>" <%}} %>></td></tr>
    <tr id="esitoTampone2_4" style="display: none"><td>4. <input type="text" name="esitoTampone2_4" <%if(t2!=null){if(t2.getTipo()==1){ %> value="<%=esito4_t2 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone2" style="display: none">
    <%RicercaTamponi2_2.setJsEvent("onChange=showEsitiTipo2_tampone2()"); %>
   
    <td> <textarea rows="5" cols="30" name="superfice2"><%=superfice2 %></textarea> </td>
    <td><%=RicercaTamponi2_2.getHtmlSelect("RicercaTamponi2_2",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone2" style="display: none"><td>1. <input type="text" name="esito_1_tampone2" <%if(t2!=null){if(t2.getTipo()==2){ %> value="<%=esito1_t2 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone2" style="display: none"><td>2. <input type="text" name="esito_2_tampone2" <%if(t2!=null){if(t2.getTipo()==2){ %> value="<%=esito2_t2 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone2" style="display: none"><td>3. <input type="text" name="esito_3_tampone2" <%if(t2!=null){if(t2.getTipo()==2){ %> value="<%=esito3_t2 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone2" style="display: none"><td>4. <input type="text" name="esito_4_tampone2" <%if(t2!=null){if(t2.getTipo()==2){ %> value="<%=esito4_t2 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
    
    
    

    
      <tr id="tampone3" style="display: none">
    <td>Tampone 3</td>
    <td>
     	<table>
     	<tr><td><input type="checkbox" value="1" name="check1_3" id="check1_3" <%if(t3!=null){ if(TicketDetails.getListaTamponi().get(3).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone3()"></td></tr>
     	<tr><td><input type="checkbox" value="2" name="check2_3" id="check2_3" <%if(t3!=null){ if(TicketDetails.getListaTamponi().get(3).getTipo()==2){ %> checked="checked" <%}} %> onclick="showRow1_tampone3()"></td></tr>
     	</table>
   	</td>
     
    <%RicercaTamponi_3.setJsEvent("onChange=showEsitiTampone3()"); %>
    <td>
    	<table >
   
    	<tr valign="top" id="row1tampone3" style="display: none">
   <%
    idsuperfice=-1;
    superfice="";
   if(t3!=null){
	   idsuperfice=t3.getSuperfice();
	superfice=t3.getSuperficeStringa();   
   }
	   %>
    	<td><%=Tamponi.getHtmlSelect("Tamponi3",idsuperfice) %></td>
    	<td><%=RicercaTamponi_3.getHtmlSelect("RicercaTamponi_3",TicketDetails.getRicercaTamponi()) %></td>
    	<td>
    		<table id="esitiTampone1">
    			<tr id="esitoTampone3_1" style="display: none"><td>1. <input type="text" name="esitoTampone3_1" <%if(t3!=null){if(t3.getTipo()==1){ %> value="<%=esito1_t3 %>" <%}} %>></td></tr>
    			<tr id="esitoTampone3_2" style="display: none"><td>2. <input type="text" name="esitoTampone3_2" <%if(t3!=null){if(t3.getTipo()==1){ %> value="<%=esito2_t3 %>" <%}} %>></td></tr>
    			<tr id="esitoTampone3_3" style="display: none"><td>3. <input type="text" name="esitoTampone3_3" <%if(t3!=null){if(t3.getTipo()==1){ %> value="<%=esito3_t3 %>" <%}} %>></td></tr>
    			<tr id="esitoTampone3_4" style="display: none"><td>4. <input type="text" name="esitoTampone3_4" <%if(t3!=null){if(t3.getTipo()==1){ %> value="<%=esito4_t3 %>" <%}} %>></td></tr>
    		</table>
    	</td>
    	</tr>
    
    	<tr valign="top" id="row2tampone3" style="display: none">
    <%RicercaTamponi2_3.setJsEvent("onChange=showEsitiTipo2_tampone3()"); %>
   		<td> <textarea rows="5" cols="30" name="superfice3"><%=superfice %></textarea> </td>
    	<td><%=RicercaTamponi2_3.getHtmlSelect("RicercaTamponi2_3",TicketDetails.getRicercaTamponi()) %></td>
    	<td>
   			 <table id="esiti_Tampone1">
    			<tr id="esito_1_tampone3" style="display: none"><td>1. <input type="text" name="esito_1_tampone3" <%if(t3!=null){if(t3.getTipo()==2){ %> value="<%=esito1_t3 %>" <%}} %>></td></tr>
   				<tr id="esito_2_tampone3" style="display: none"><td>2. <input type="text" name="esito_2_tampone3" <%if(t3!=null){if(t3.getTipo()==2){ %> value="<%=esito2_t3 %>" <%}} %>></td></tr>
   				<tr id="esito_3_tampone3" style="display: none"><td>3. <input type="text" name="esito_3_tampone3" <%if(t3!=null){if(t3.getTipo()==2){ %> value="<%=esito3_t3 %>" <%}} %>></td></tr>
    			<tr id="esito_4_tampone3" style="display: none"><td>4. <input type="text" name="esito_4_tampone3" <%if(t3!=null){if(t3.getTipo()==2){ %> value="<%=esito4_t3 %>" <%}} %>></td></tr>
   			 </table>
    	</td>
    
    	</tr>
    
    </table>
    
    </td>
    
    </tr>
    
   
   
    
     <tr id="tampone4" style="display: none">
    	<td>Tampone 4</td>
     <td>
     <table>
    	<tr><td><input type="checkbox" name="check1_4" value="1" id="check1_4" <%if(t4!=null){ if(TicketDetails.getListaTamponi().get(4).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone4()"></td></tr>
     	<tr><td><input type="checkbox" name="check2_4" value="2" id="check2_4" <%if(t4!=null){ if(TicketDetails.getListaTamponi().get(4).getTipo()==2){ %> checked="checked" <%}} %> onclick="showRow1_tampone4()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_4.setJsEvent("onChange=showEsitiTampone4()"); %>
    <td>
    <table >
    
 
    
   
    <tr valign="top" id="row1tampone4" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t4!=null){
	   idsuperfice=t4.getSuperfice();
	superfice=t4.getSuperficeStringa();   
   }
	   %>
    <td><%=Tamponi.getHtmlSelect("Tamponi4",idsuperfice) %></td>
    <td><%=RicercaTamponi_4.getHtmlSelect("RicercaTamponi_4",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esitiTampone1">
  
    
    <tr id="esitoTampone4_1" style="display: none"><td>1. <input type="text" name="esitoTampone4_1" <%if(t4!=null){if(t4.getTipo()==1){ %> value="<%=esito1_t4 %>" <%}} %>></td></tr>
    <tr id="esitoTampone4_2" style="display: none"><td>2. <input type="text" name="esitoTampone4_2" <%if(t4!=null){if(t4.getTipo()==1){ %> value="<%=esito2_t4 %>" <%}} %>></td></tr>
    <tr id="esitoTampone4_3" style="display: none"><td>3. <input type="text" name="esitoTampone4_3" <%if(t4!=null){if(t4.getTipo()==1){ %> value="<%=esito3_t4 %>" <%}} %>></td></tr>
    <tr id="esitoTampone4_4" style="display: none"><td>4. <input type="text" name="esitoTampone4_4" <%if(t4!=null){if(t4.getTipo()==1){ %> value="<%=esito4_t4 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone4" style="display: none">
    <%RicercaTamponi2_4.setJsEvent("onChange=showEsitiTipo2_tampone4()"); %>
   
    <td> <textarea rows="5" cols="30" name="superfice4"><%=superfice %></textarea> </td>
    <td><%=RicercaTamponi2_4.getHtmlSelect("RicercaTamponi2_4",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone4" style="display: none"><td>1. <input type="text" name="esito_1_tampone4" <%if(t4!=null){if(t4.getTipo()==2){ %> value="<%=esito1_t4 %> <%}} %>"> </td></tr>
    <tr id="esito_2_tampone4" style="display: none"><td>2. <input type="text" name="esito_2_tampone4" <%if(t4!=null){if(t4.getTipo()==2){ %> value="<%=esito2_t4 %> <%}} %>"></td></tr>
    <tr id="esito_3_tampone4" style="display: none"><td>3. <input type="text" name="esito_3_tampone4" <%if(t4!=null){if(t4.getTipo()==2){ %> value="<%=esito3_t4%>" <%}} %>></td></tr>
    <tr id="esito_4_tampone4" style="display: none"><td>4. <input type="text" name="esito_4_tampone4" <%if(t4!=null){if(t4.getTipo()==2){ %> value="<%=esito4_t4 %> <%}} %>"></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
     <tr id="tampone5" style="display: none">
    <td>Tampone 5</td>
     <td>
     <table>
     
     <tr><td><input type="checkbox" name="check1_5" value="1" id="check1_5" <%if(t5!=null){ if(TicketDetails.getListaTamponi().get(5).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone5()"></td></tr>
     <tr><td><input type="checkbox" name="check2_5" value="2" id="check2_5" <%if(t5!=null){ if(TicketDetails.getListaTamponi().get(5).getTipo()==2){ %> checked="checked" <%} }%> onclick="showRow1_tampone5()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_5.setJsEvent("onChange=showEsitiTampone5()"); %>
    <td>
    <table >
    <tr valign="top" id="row1tampone5" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t5!=null){
	   idsuperfice=t5.getSuperfice();
	superfice=t5.getSuperficeStringa();   
   }
	   %>
    <td><%=Tamponi.getHtmlSelect("Tamponi5",idsuperfice) %></td>
    <td><%=RicercaTamponi_5.getHtmlSelect("RicercaTamponi_5",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esitiTampone1">
  

    <tr id="esitoTampone5_1" style="display: none"><td>1. <input type="text" name="esitoTampone5_1" <%if(t5!=null){if(t5.getTipo()==1){ %> value="<%=esito1_t5 %>" <%}} %>></td></tr>
    <tr id="esitoTampone5_2" style="display: none"><td>2. <input type="text" name="esitoTampone5_2" <%if(t5!=null){if(t1.getTipo()==1){ %> value="<%=esito2_t5 %>"> <%}} %></td></tr>
    <tr id="esitoTampone5_3" style="display: none"><td>3. <input type="text" name="esitoTampone5_3" <%if(t5!=null){if(t5.getTipo()==1){ %> value="<%=esito3_t5%>" <%}} %>></td></tr>
    <tr id="esitoTampone5_4" style="display: none"><td>4. <input type="text" name="esitoTampone5_4" <%if(t5!=null){if(t5.getTipo()==1){ %> value="<%=esito4_t5%>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone5" style="display: none">
    <%RicercaTamponi2_5.setJsEvent("onChange=showEsitiTipo2_tampone5()"); %>
   
    <td> <textarea rows="5" cols="30" name="superfice5"><%=superfice %></textarea> </td>
    <td><%=RicercaTamponi2_5.getHtmlSelect("RicercaTamponi2_5",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone5" style="display: none"><td>1. <input type="text" name="esito_1_tampone5" <%if(t5!=null){if(t5.getTipo()==2){ %> value="<%=esito1_t5 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone5" style="display: none"><td>2. <input type="text" name="esito_2_tampone5" <%if(t5!=null){if(t5.getTipo()==2){ %> value="<%=esito2_t5 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone5" style="display: none"><td>3. <input type="text" name="esito_3_tampone5" <%if(t5!=null){if(t5.getTipo()==2){ %> value="<%=esito3_t5 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone5" style="display: none"><td>4. <input type="text" name="esito_4_tampone5" <%if(t5!=null){if(t5.getTipo()==2){ %> value="<%=esito4_t5%>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
      
    
    
     <tr id="tampone6" style="display: none">
    <td>Tampone 6</td>
     <td>
     <table>
    
     <tr><td><input type="checkbox" name="check1_6" value="1" <%if(t6!=null){ if(TicketDetails.getListaTamponi().get(6).getTipo()==1){ %> checked="checked" <%} }%> id="check1_6" onclick="showRow1_tampone6()"></td></tr>
     <tr><td><input type="checkbox" name="check2_6" value="2" <%if(t6!=null){ if(TicketDetails.getListaTamponi().get(6).getTipo()==2){ %> checked="checked" <%} }%> id="check2_6" onclick="showRow1_tampone6()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_6.setJsEvent("onChange=showEsitiTampone6()"); %>
    <td>
    <table >
    
    
    <tr valign="top" id="row1tampone6" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t6!=null){
	   idsuperfice=t6.getSuperfice();
	superfice=t6.getSuperficeStringa();   
   }
	   %>
    <td><%=Tamponi.getHtmlSelect("Tamponi6",idsuperfice) %></td>
    <td><%=RicercaTamponi_6.getHtmlSelect("RicercaTamponi_6",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esitiTampone1">
    
    
    <tr id="esitoTampone6_1" style="display: none"><td>1. <input type="text" name="esitoTampone6_1" <%if(t6!=null){if(t6.getTipo()==1){ %> value="<%=esito1_t6 %>" <%}} %>></td></tr>
    <tr id="esitoTampone6_2" style="display: none"><td>2. <input type="text" name="esitoTampone6_2" <%if(t6!=null){if(t6.getTipo()==1){ %> value="<%=esito2_t6 %>" <%}} %>></td></tr>
    <tr id="esitoTampone6_3" style="display: none"><td>3. <input type="text" name="esitoTampone6_3" <%if(t6!=null){if(t6.getTipo()==1){ %> value="<%=esito3_t6 %>" <%}} %>></td></tr>
    <tr id="esitoTampone6_4" style="display: none"><td>4. <input type="text" name="esitoTampone6_4" <%if(t6!=null){if(t6.getTipo()==1){ %> value="<%=esito4_t6 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone6" style="display: none">
    <%RicercaTamponi2_6.setJsEvent("onChange=showEsitiTipo2_tampone6()"); %>
   
    <td> <textarea rows="5" cols="30" name="superfice6"><%=superfice %></textarea> </td>
    <td><%=RicercaTamponi2_6.getHtmlSelect("RicercaTamponi2_6",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone6" style="display: none"><td>1. <input type="text" name="esito_1_tampone6" <%if(t6!=null){if(t6.getTipo()==2){ %> value="<%=esito1_t6 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone6" style="display: none"><td>2. <input type="text" name="esito_2_tampone6"  <%if(t6!=null){if(t6.getTipo()==2){ %>value="<%=esito2_t6 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone6" style="display: none"><td>3. <input type="text" name="esito_3_tampone6" <%if(t6!=null){if(t6.getTipo()==2){ %> value="<%=esito3_t6 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone6" style="display: none"><td>4. <input type="text" name="esito_4_tampone6" <%if(t6!=null){if(t6.getTipo()==2){ %> value="<%=esito4_t6 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
   
    
    
     
     <tr id="tampone7" style="display: none">
    <td>Tampone 7</td>
     <td>
     <table>
     <%RicercaTamponi_7.setJsEvent("onChange=showEsitiTampone7()");  %>
     <tr><td><input type="checkbox" name="check1_7" value="1" <%if(t7!=null){ if(TicketDetails.getListaTamponi().get(7).getTipo()==1){ %> checked="checked" <%}} %> id="check1_7" onclick="showRow1_tampone7()"></td></tr>
     <tr><td><input type="checkbox" name="check2_7" value="2" <%if(t7!=null){ if(TicketDetails.getListaTamponi().get(7).getTipo()==2){ %> checked="checked" <%} }%> id="check2_7" onclick="showRow1_tampone7()"></td></tr>
     </table>
   </td>
     
    
    <td>
    <table >
    <tr valign="top" id="row1tampone7" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t7!=null){
	   idsuperfice=t7.getSuperfice();
	superfice=t7.getSuperficeStringa();   
   }
	   %>
    <td><%=Tamponi.getHtmlSelect("Tamponi7",idsuperfice) %></td>
    <td><%=RicercaTamponi_7.getHtmlSelect("RicercaTamponi_7",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    
    
    <table id="esitiTampone1">
    
    
    <tr id="esitoTampone7_1" style="display: none"><td>1. <input type="text" name="esitoTampone7_1" <%if(t7!=null){if(t7.getTipo()==1){ %> value="<%=esito1_t7 %>" <%}} %>></td></tr>
    <tr id="esitoTampone7_2" style="display: none"><td>2. <input type="text" name="esitoTampone7_2" <%if(t7!=null){if(t7.getTipo()==1){ %> value="<%=esito2_t7 %>" <%}} %>></td></tr>
    <tr id="esitoTampone7_3" style="display: none"><td>3. <input type="text" name="esitoTampone7_3" <%if(t7!=null){if(t1.getTipo()==1){ %> value="<%=esito3_t7 %>" <%}} %>></td></tr>
    <tr id="esitoTampone7_4" style="display: none"><td>4. <input type="text" name="esitoTampone7_4" <%if(t7!=null){if(t1.getTipo()==1){ %> value="<%=esito4_t7 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone7" style="display: none">
    <%RicercaTamponi2_7.setJsEvent("onChange=showEsitiTipo2_tampone7()"); %>
   
    <td> <textarea rows="5" cols="30" name="superfice7"><%=superfice %></textarea> </td>
    <td><%=RicercaTamponi2_7.getHtmlSelect("RicercaTamponi2_7",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone7" style="display: none"><td>1. <input type="text" name="esito_1_tampone7" <%if(t7!=null){if(t7.getTipo()==2){ %> value="<%=esito1_t7 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone7" style="display: none"><td>2. <input type="text" name="esito_2_tampone7" <%if(t7!=null){if(t7.getTipo()==2){ %> value="<%=esito2_t7 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone7" style="display: none"><td>3. <input type="text" name="esito_3_tampone7" <%if(t7!=null){if(t7.getTipo()==2){ %> value="<%=esito3_t7 %>" <%}} %>> </td></tr>
    <tr id="esito_4_tampone7" style="display: none"><td>4. <input type="text" name="esito_4_tampone7" <%if(t7!=null){if(t7.getTipo()==2){ %> value="<%=esito4_t7%>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
 
    
   
    
     <tr id="tampone8" style="display: none">
    <td>Tampone 8</td>
     <td>
     <table>
    
     <tr><td><input type="checkbox" name="check1_8" value="1" id="check1_8" <%if(t8!=null){ if(TicketDetails.getListaTamponi().get(8).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone8()"></td></tr>
     <tr><td><input type="checkbox" name="check2_8" value="2" id="check2_8" <%if(t8!=null){ if(TicketDetails.getListaTamponi().get(8).getTipo()==2){ %> checked="checked" <%}} %> onclick="showRow1_tampone8()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_8.setJsEvent("onChange=showEsitiTampone8()"); %>
    <td>
    <table >
    
       
    <tr valign="top" id="row1tampone8" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t8!=null){
	   idsuperfice=t8.getSuperfice();
	superfice=t8.getSuperficeStringa();   
   }
	   %>
    <td><%=Tamponi.getHtmlSelect("Tamponi8",idsuperfice) %></td>
    <td><%=RicercaTamponi_8.getHtmlSelect("RicercaTamponi_8",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esitiTampone1">
     
    
    <tr id="esitoTampone8_1" style="display: none"><td>1. <input type="text" name="esitoTampone8_1" <%if(t8!=null){if(t8.getTipo()==1){ %> value="<%=esito1_t8 %>" <%}} %>></td></tr>
    <tr id="esitoTampone8_2" style="display: none"><td>2. <input type="text" name="esitoTampone8_2" <%if(t8!=null){if(t8.getTipo()==1){ %> value="<%=esito2_t8 %>" <%}} %>></td></tr>
    <tr id="esitoTampone8_3" style="display: none"><td>3. <input type="text" name="esitoTampone8_3" <%if(t8!=null){if(t8.getTipo()==1){ %> value="<%=esito3_t8 %>" <%}} %>></td></tr>
    <tr id="esitoTampone8_4" style="display: none"><td>4. <input type="text" name="esitoTampone8_4" <%if(t8!=null){if(t8.getTipo()==1){ %> value="<%=esito4_t8 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone8" style="display: none">
    <%RicercaTamponi2_8.setJsEvent("onChange=showEsitiTipo2_tampone8()"); %>
   
    <td> <textarea rows="5" cols="30" name="superfice8"><%=superfice %></textarea> </td>
    <td><%=RicercaTamponi2_8.getHtmlSelect("RicercaTamponi2_8",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone8" style="display: none"><td>1. <input type="text" name="esito_1_tampone8" <%if(t8!=null){if(t8.getTipo()==2){ %> value="<%=esito1_t8 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone8" style="display: none"><td>2. <input type="text" name="esito_2_tampone8" <%if(t8!=null){if(t8.getTipo()==2){ %> value="<%=esito2_t8 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone8" style="display: none"><td>3. <input type="text" name="esito_3_tampone8" <%if(t8!=null){if(t8.getTipo()==2){ %> value="<%=esito3_t8 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone8" style="display: none"><td>4. <input type="text" name="esito_4_tampone8" <%if(t8!=null){if(t8.getTipo()==2){ %> value="<%=esito4_t8%>" <%}} %> ></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
     <tr id="tampone9" style="display: none">
    <td>Tampone 9</td>
     <td>
     <table>
     
     <tr><td><input type="checkbox" name="check1_9" value="1" id="check1_9" <%if(t9!=null){ if(TicketDetails.getListaTamponi().get(9).getTipo()==1){ %> checked="checked" <%} }%> onclick="showRow1_tampone9()"></td></tr>
     <tr><td><input type="checkbox" name="check2_9" value="2" id="check2_9" <%if(t9!=null){if(TicketDetails.getListaTamponi().get(9).getTipo()==2){ %> checked="checked" <%} }%> onclick="showRow1_tampone9()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_9.setJsEvent("onChange=showEsitiTampone9()"); %>
    <td>
    <table >
    <tr valign="top" id="row1tampone9" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t9!=null){
	   idsuperfice=t9.getSuperfice();
	superfice=t9.getSuperficeStringa();   
   }
	   %>
    <td><%=Tamponi.getHtmlSelect("Tamponi9",idsuperfice) %></td>
    <td><%=RicercaTamponi_9.getHtmlSelect("RicercaTamponi_9",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esitiTampone1">
     
    
    <tr id="esitoTampone9_1" style="display: none"><td>1. <input type="text" name="esitoTampone9_1" <%if(t9!=null){if(t9.getTipo()==1){ %> value="<%=esito1_t9 %>" <%}} %>></td></tr>
    <tr id="esitoTampone9_2" style="display: none"><td>2. <input type="text" name="esitoTampone9_2" <%if(t9!=null){if(t9.getTipo()==1){ %> value="<%=esito2_t9 %>" <%}} %>></td></tr>
    <tr id="esitoTampone9_3" style="display: none"><td>3. <input type="text" name="esitoTampone9_3" <%if(t9!=null){if(t9.getTipo()==1){ %> value="<%=esito3_t9 %>" <%}} %>></td></tr>
    <tr id="esitoTampone9_4" style="display: none"><td>4. <input type="text" name="esitoTampone9_4" <%if(t9!=null){if(t9.getTipo()==1){ %> value="<%=esito4_t9 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone9" style="display: none">
    <%RicercaTamponi2_9.setJsEvent("onChange=showEsitiTipo2_tampone9()"); %>
   
    <td> <textarea rows="5" cols="30" name="superfice9"><%=superfice %></textarea> </td>
    <td><%=RicercaTamponi2_9.getHtmlSelect("RicercaTamponi2_9",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone9" style="display: none"><td>1. <input type="text" name="esito_1_tampone9" <%if(t9!=null){if(t9.getTipo()==2){ %> value="<%=esito1_t9 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone9" style="display: none"><td>2. <input type="text" name="esito_2_tampone9" <%if(t9!=null){if(t9.getTipo()==2){ %> value="<%=esito2_t9 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone9" style="display: none"><td>3. <input type="text" name="esito_3_tampone9" <%if(t9!=null){if(t9.getTipo()==2){ %> value="<%=esito3_t9 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone9" style="display: none"><td>4. <input type="text" name="esito_4_tampone9" <%if(t9!=null){if(t9.getTipo()==2){ %> value="<%=esito4_t9 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
   
  
    
    
    
     <tr id="tampone10" style="display: none">
     	<td>Tampone10</td>
     	<td>
     		<table>
    
    			 <tr><td><input type="checkbox" name="check1_10" value="1" id="check1_10" <%if(t10!=null){if(TicketDetails.getListaTamponi().get(10).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone10()"></td></tr>
     			<tr><td><input type="checkbox" name="check2_10" value="2" id="check2_10" <%if(t10!=null){if(TicketDetails.getListaTamponi().get(10).getTipo()==2){ %> checked="checked" <%} }%> onclick="showRow1_tampone10()"></td></tr>
     		</table>
  		 </td>
     
    <%RicercaTamponi_10.setJsEvent("onChange=showEsitiTampone10()"); %>
    	<td>
    		<table >
     
    		<tr valign="top" id="row1tampone10" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t10!=null){
	   idsuperfice=t10.getSuperfice();
	superfice=t10.getSuperficeStringa();   
   }
	   %>
    <td><%=Tamponi.getHtmlSelect("Tamponi10",idsuperfice) %></td>
    <td><%=RicercaTamponi_10.getHtmlSelect("RicercaTamponi_10",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    			<table id="esitiTampone1">
     
    
    <tr id="esitoTampone10_1" style="display: none"><td>1. <input type="text" name="esitoTampone10_1" <%if(t10!=null){if(t10.getTipo()==1){ %> value="<%=esito1_t10 %>" <%}} %>></td></tr>
    <tr id="esitoTampone10_2" style="display: none"><td>2. <input type="text" name="esitoTampone10_2" <%if(t10!=null){if(t10.getTipo()==1){ %> value="<%=esito2_t10 %>" <%}} %>></td></tr>
    <tr id="esitoTampone10_3" style="display: none"><td>3. <input type="text" name="esitoTampone10_3" <%if(t10!=null){if(t10.getTipo()==1){ %> value="<%=esito3_t10 %>" <%}} %>></td></tr>
    <tr id="esitoTampone10_4" style="display: none"><td>4. <input type="text" name="esitoTampone10_4" <%if(t10!=null){if(t10.getTipo()==1){ %> value="<%=esito4_t10 %>" <%}} %>></td></tr>
  			  </table>
   	 
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone10" style="display: none">
    <%RicercaTamponi2_10.setJsEvent("onChange=showEsitiTipo2_tampone10()"); %>
   
    <td> <textarea rows="5" cols="30" name="superfice10"><%=superfice %></textarea> </td>
    <td><%=RicercaTamponi2_10.getHtmlSelect("RicercaTamponi2_10",TicketDetails.getRicercaTamponi()) %></td>
    <td>
   	 <table id="esiti_Tampone1">
    <tr id="esito_1_tampone10" style="display: none"><td>1. <input type="text" name="esito_1_tampone10" <%if(t10!=null){if(t10.getTipo()==2){ %> value="<%=esito1_t10 %>"<%}} %>></td></tr>
    <tr id="esito_2_tampone10" style="display: none"><td>2. <input type="text" name="esito_2_tampone10" <%if(t10!=null){if(t10.getTipo()==2){ %> value="<%=esito2_t10 %>"<%}} %>></td></tr>
    <tr id="esito_3_tampone10" style="display: none"><td>3. <input type="text" name="esito_3_tampone10" <%if(t10!=null){if(t10.getTipo()==2){ %> value="<%=esito3_t10 %>"<%}} %>></td></tr>
    <tr id="esito_4_tampone10" style="display: none"><td>4. <input type="text" name="esito_4_tampone10" <%if(t10!=null){if(t10.getTipo()==2){ %> value="<%=esito4_t10 %>" <%}} %>></td></tr>
  	  </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
  
    </table>
   
    </td>
    </tr>
  
  
  --%>
  
  
  
     <tr class="containerBody" >
    <td valign="top" class="formLabel">
      Tamponi<br><br>
      
      (* In caso di selezione multipla tenere premuto il tasto Ctrl durante la Selezione)
      
    </td>
    
    
    <td>
    <table class="noborder">
     <tr>
    <td><b>Tampone 1 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td><input type="checkbox" value="1" name="check1_1" id="check1_1"  <%if(t1!=null){ if(TicketDetails.getListaTamponi().get(1).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone1()" ></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td><input type="checkbox" value="2" name="check2_1" id="check2_1"  <%if(t1!=null){ if(TicketDetails.getListaTamponi().get(1).getTipo()==2){ %> checked="checked" <%}} %> onclick="showRow1_tampone1()" ></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_1.setJsEvent("onChange=showEsitiTampone1()"); %>
    <td>
    <table >
    <tr valign="top" id="row1tampone1" style="display: none">
   <%
   int idsuperfice=-1;
   String superfice="";
   if(t1!=null){
	   idsuperfice=t1.getSuperfice();
	superfice=t1.getSuperficeStringa();   
   }
	   %>
    <td>Carcassa</br><%=Tamponi.getHtmlSelect("Tamponi1",idsuperfice)  %></td>
    <td>Tipo di Ricerca<br><%=RicercaTamponi_1.getHtmlSelect("RicercaTamponi_1",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esitiTampone1">
    <label id="etichetta10"  style="display: none">Esiti</label>
   <tr id="esitoTampone1_1" style="display: none"><td> 1. <input type="text" name="esitoTampone1_1" <%if(t1!=null){if(t1.getTipo()==1){ %> value="<%=esito1 %>" <%}} %>></td></tr>
    <tr id="esitoTampone1_2" style="display: none"><td>2. <input type="text" name="esitoTampone1_2" <%if(t1!=null){if(t1.getTipo()==1){ %> value="<%=esito2 %>" <%}} %>></td></tr>
    <tr id="esitoTampone1_3" style="display: none"><td>3. <input type="text" name="esitoTampone1_3" <%if(t1!=null){if(t1.getTipo()==1){ %> value="<%=esito3 %>" <%}} %>></td></tr>
    <tr id="esitoTampone1_4" style="display: none"><td>4. <input type="text" name="esitoTampone1_4" <%if(t1!=null){if(t1.getTipo()==1){ %> value="<%=esito4 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone1" style="display: none">
    <%RicercaTamponi2_1.setJsEvent("onChange=showEsitiTipo2_tampone1()"); %>
  
    <td>Superfice Testata<br> <textarea rows="5" cols="30" name="superfice1"><%=superfice %></textarea> </td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi2_1.getHtmlSelect("RicercaTamponi2_1",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta10"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone1" style="display: none"><td>1. <input type="text" name="esito_1_tampone1" <%if(t1!=null){if(t1.getTipo()==2){ %> value="<%=esito1 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone1" style="display: none"><td>2. <input type="text" name="esito_2_tampone1" <%if(t1!=null){if(t1.getTipo()==2){ %> value="<%=esito2 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone1" style="display: none"><td>3. <input type="text" name="esito_3_tampone1" <%if(t1!=null){if(t1.getTipo()==2){ %> value="<%=esito3 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone1" style="display: none"><td>4. <input type="text" name="esito_4_tampone1" <%if(t1!=null){if(t1.getTipo()==2){ %> value="<%=esito4 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
   
    
    
     <tr id="tampone2" style="display: none">
    <td><b>Tampone 2 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td><input type="checkbox" value="1" name="check1_2" id="check1_2"  <%if(t2!=null){ if(TicketDetails.getListaTamponi().get(2).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone2()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td><input type="checkbox" value="2" name="check2_2" id="check2_2"  <%if(t2!=null){ if(TicketDetails.getListaTamponi().get(2).getTipo()==2){ %> checked="checked" <%}} %> onclick="showRow1_tampone2()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_2.setJsEvent("onChange=showEsitiTampone2()"); %>
    <td>
    <table >
   
    
    
    
    
    <tr valign="top" id="row1tampone2" style="display: none">
   <%
   int idsuperfice2=-1;
   String superfice2="";
   if(t2!=null){
	   idsuperfice2=t2.getSuperfice();
	superfice2=t2.getSuperficeStringa();   
   }
	   %>
    <td>Cercassa <br><%=Tamponi.getHtmlSelect("Tamponi2",idsuperfice2) %></td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi_2.getHtmlSelect("RicercaTamponi_2",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esitiTampone1">
    <label id="etichetta10"  style="display: none">Esiti</label>
    <tr id="esitoTampone2_1" style="display: none"><td>1. <input type="text" name="esitoTampone2_1" <%if(t2!=null){if(t2.getTipo()==1){ %> value="<%=esito1_t2 %>" <%}} %>></td></tr>
    <tr id="esitoTampone2_2" style="display: none"><td>2. <input type="text" name="esitoTampone2_2" <%if(t2!=null){if(t2.getTipo()==1){ %> value="<%=esito2_t2 %>" <%}} %>></td></tr>
    <tr id="esitoTampone2_3" style="display: none"><td>3. <input type="text" name="esitoTampone2_3" <%if(t2!=null){if(t2.getTipo()==1){ %> value="<%=esito3_t2 %>" <%}} %>></td></tr>
    <tr id="esitoTampone2_4" style="display: none"><td>4. <input type="text" name="esitoTampone2_4" <%if(t2!=null){if(t2.getTipo()==1){ %> value="<%=esito4_t2 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone2" style="display: none">
    <%RicercaTamponi2_2.setJsEvent("onChange=showEsitiTipo2_tampone2()"); %>
   
    <td>>Superfice Testata <br> <textarea rows="5" cols="30" name="superfice2"><%=superfice2 %></textarea> </td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi2_2.getHtmlSelect("RicercaTamponi2_2",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <table id="esiti_Tampone1">
    <label id="etichetta10"  style="display: none">Esiti</label>
    <tr id="esito_1_tampone2" style="display: none"><td>1. <input type="text" name="esito_1_tampone2" <%if(t2!=null){if(t2.getTipo()==2){ %> value="<%=esito1_t2 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone2" style="display: none"><td>2. <input type="text" name="esito_2_tampone2" <%if(t2!=null){if(t2.getTipo()==2){ %> value="<%=esito2_t2 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone2" style="display: none"><td>3. <input type="text" name="esito_3_tampone2" <%if(t2!=null){if(t2.getTipo()==2){ %> value="<%=esito3_t2 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone2" style="display: none"><td>4. <input type="text" name="esito_4_tampone2" <%if(t2!=null){if(t2.getTipo()==2){ %> value="<%=esito4_t2 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
     
    
      <tr id="tampone3" style="display: none">
    <td><b>Tampone 3 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
    <td>
     	<table>
     	<tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     	<tr><td><input type="checkbox" value="1" name="check1_3" id="check1_3" <%if(t3!=null){ if(TicketDetails.getListaTamponi().get(3).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone3()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     	<tr><td><input type="checkbox" value="2" name="check2_3" id="check2_3" <%if(t3!=null){ if(TicketDetails.getListaTamponi().get(3).getTipo()==2){ %> checked="checked" <%}} %> onclick="showRow1_tampone3()"></td></tr>
     	</table>
   	</td>
     
    <%RicercaTamponi_3.setJsEvent("onChange=showEsitiTampone3()"); %>
    <td>
    	<table >
   
    	<tr valign="top" id="row1tampone3" style="display: none">
   <%
    idsuperfice=-1;
    superfice="";
   if(t3!=null){
	   idsuperfice=t3.getSuperfice();
	superfice=t3.getSuperficeStringa();   
   }
	   %>
    	<td>Carcassa <br><%=Tamponi.getHtmlSelect("Tamponi3",idsuperfice) %></td>
    	<td>Tipo di Ricerca <br><%=RicercaTamponi_3.getHtmlSelect("RicercaTamponi_3",TicketDetails.getRicercaTamponi()) %></td>
    	<td>
    	<label id="etichetta10"  style="display: none">Esiti</label>
    		<table id="esitiTampone1">
    		
    			<tr id="esitoTampone3_1" style="display: none"><td>1. <input type="text" name="esitoTampone3_1" <%if(t3!=null){if(t3.getTipo()==1){ %> value="<%=esito1_t3 %>" <%}} %>></td></tr>
    			<tr id="esitoTampone3_2" style="display: none"><td>2. <input type="text" name="esitoTampone3_2" <%if(t3!=null){if(t3.getTipo()==1){ %> value="<%=esito2_t3 %>" <%}} %>></td></tr>
    			<tr id="esitoTampone3_3" style="display: none"><td>3. <input type="text" name="esitoTampone3_3" <%if(t3!=null){if(t3.getTipo()==1){ %> value="<%=esito3_t3 %>" <%}} %>></td></tr>
    			<tr id="esitoTampone3_4" style="display: none"><td>4. <input type="text" name="esitoTampone3_4" <%if(t3!=null){if(t3.getTipo()==1){ %> value="<%=esito4_t3 %>" <%}} %>></td></tr>
    		</table>
    	</td>
    	</tr>
    
    	<tr valign="top" id="row2tampone3" style="display: none">
    <%RicercaTamponi2_3.setJsEvent("onChange=showEsitiTipo2_tampone3()"); %>
   		<td>Superfice Testata <br> <textarea rows="5" cols="30" name="superfice3"><%=superfice %></textarea> </td>
    	<td>Tipo di Ricerca <br><%=RicercaTamponi2_3.getHtmlSelect("RicercaTamponi2_3",TicketDetails.getRicercaTamponi()) %></td>
    	<td>
    	<label id="etichetta10"  style="display: none">Esiti</label>
   			 <table id="esiti_Tampone1">
    			<tr id="esito_1_tampone3" style="display: none"><td>1. <input type="text" name="esito_1_tampone3" <%if(t3!=null){if(t3.getTipo()==2){ %> value="<%=esito1_t3 %>" <%}} %>></td></tr>
   				<tr id="esito_2_tampone3" style="display: none"><td>2. <input type="text" name="esito_2_tampone3" <%if(t3!=null){if(t3.getTipo()==2){ %> value="<%=esito2_t3 %>" <%}} %>></td></tr>
   				<tr id="esito_3_tampone3" style="display: none"><td>3. <input type="text" name="esito_3_tampone3" <%if(t3!=null){if(t3.getTipo()==2){ %> value="<%=esito3_t3 %>" <%}} %>></td></tr>
    			<tr id="esito_4_tampone3" style="display: none"><td>4. <input type="text" name="esito_4_tampone3" <%if(t3!=null){if(t3.getTipo()==2){ %> value="<%=esito4_t3 %>" <%}} %>></td></tr>
   			 </table>
    	</td>
    
    	</tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    <tr id="tampone4" style="display: none">
    <td><b>Tampone 4 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
    <td>
     	<table>
     	<tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     	<tr><td><input type="checkbox" value="1" name="check1_4" id="check1_4" <%if(t4!=null){ if(TicketDetails.getListaTamponi().get(4).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone4()"></td></tr>
     	<tr><td align="middle"><label>Altro</label></td></tr>
     	<tr><td><input type="checkbox" value="2" name="check2_4" id="check2_4" <%if(t4!=null){ if(TicketDetails.getListaTamponi().get(4).getTipo()==2){ %> checked="checked" <%}} %> onclick="showRow1_tampone4()"></td></tr>
     	</table>
   	</td>
     
    <%RicercaTamponi_4.setJsEvent("onChange=showEsitiTampone3()"); %>
    <td>
    	<table >
   
    	<tr valign="top" id="row1tampone4" style="display: none">
   <%
    idsuperfice=-1;
    superfice="";
   if(t4!=null){
	   idsuperfice=t4.getSuperfice();
	superfice=t4.getSuperficeStringa();   
   }
	   %>
    	<td>Carcassa <br><%=Tamponi.getHtmlSelect("Tamponi4",idsuperfice) %></td>
    	<td>Tipo di Ricerca <br> <%=RicercaTamponi_4.getHtmlSelect("RicercaTamponi_4",TicketDetails.getRicercaTamponi()) %></td>
    	<td>
    	<label id="etichetta10"  style="display: none">Esiti</label>
    		<table id="esitiTampone1">
    			<tr id="esitoTampone4_1" style="display: none"><td>1. <input type="text" name="esitoTampone3_1" <%if(t4!=null){if(t4.getTipo()==1){ %> value="<%=esito1_t4 %>" <%}} %>></td></tr>
    			<tr id="esitoTampone4_2" style="display: none"><td>2. <input type="text" name="esitoTampone3_2" <%if(t4!=null){if(t4.getTipo()==1){ %> value="<%=esito2_t4 %>" <%}} %>></td></tr>
    			<tr id="esitoTampone4_3" style="display: none"><td>3. <input type="text" name="esitoTampone3_3" <%if(t4!=null){if(t4.getTipo()==1){ %> value="<%=esito3_t4 %>" <%}} %>></td></tr>
    			<tr id="esitoTampone4_4" style="display: none"><td>4. <input type="text" name="esitoTampone3_4" <%if(t4!=null){if(t4.getTipo()==1){ %> value="<%=esito4_t4 %>" <%}} %>></td></tr>
    		</table>
    	</td>
    	</tr>
    
    	<tr valign="top" id="row2tampone4" style="display: none">
    <%RicercaTamponi2_4.setJsEvent("onChange=showEsitiTipo2_tampone4()"); %>
   		<td>Superfice Testata <br> <textarea rows="5" cols="30" name="superfice3"><%=superfice %></textarea> </td>
    	<td>Tipo di Ricerca <br><%=RicercaTamponi2_3.getHtmlSelect("RicercaTamponi2_4",TicketDetails.getRicercaTamponi()) %></td>
    	<td>
    	<label id="etichetta10"  style="display: none">Esiti</label>
   			 <table id="esiti_Tampone1">
    			<tr id="esito_1_tampone4" style="display: none"><td>1. <input type="text" name="esito_1_tampone4" <%if(t4!=null){if(t4.getTipo()==2){ %> value="<%=esito1_t4 %>" <%}} %>></td></tr>
   				<tr id="esito_2_tampone4" style="display: none"><td>2. <input type="text" name="esito_2_tampone4" <%if(t4!=null){if(t4.getTipo()==2){ %> value="<%=esito2_t4 %>" <%}} %>></td></tr>
   				<tr id="esito_3_tampone4" style="display: none"><td>3. <input type="text" name="esito_3_tampone4" <%if(t4!=null){if(t4.getTipo()==2){ %> value="<%=esito3_t4 %>" <%}} %>></td></tr>
    			<tr id="esito_4_tampone4" style="display: none"><td>4. <input type="text" name="esito_4_tampone4" <%if(t4!=null){if(t4.getTipo()==2){ %> value="<%=esito4_t4 %>" <%}} %>></td></tr>
   			 </table>
    	</td>
    
    	</tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
     <tr id="tampone5" style="display: none">
    <td><b>Tampone 5 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td><input type="checkbox" name="check1_5" value="1" id="check1_5" <%if(t5!=null){ if(TicketDetails.getListaTamponi().get(5).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone5()"></td></tr>
    <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td><input type="checkbox" name="check2_5" value="2" id="check2_5" <%if(t5!=null){ if(TicketDetails.getListaTamponi().get(5).getTipo()==2){ %> checked="checked" <%} }%> onclick="showRow1_tampone5()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_5.setJsEvent("onChange=showEsitiTampone5()"); %>
    <td>
    <table >
    <tr valign="top" id="row1tampone5" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t5!=null){
	   idsuperfice=t5.getSuperfice();
	superfice=t5.getSuperficeStringa();   
   }
	   %>
    <td>Carcassa <br><%=Tamponi.getHtmlSelect("Tamponi5",idsuperfice) %></td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi_5.getHtmlSelect("RicercaTamponi_5",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta10"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
  

    <tr id="esitoTampone5_1" style="display: none"><td>1. <input type="text" name="esitoTampone5_1" <%if(t5!=null){if(t5.getTipo()==1){ %> value="<%=esito1_t5 %>" <%}} %>></td></tr>
    <tr id="esitoTampone5_2" style="display: none"><td>2. <input type="text" name="esitoTampone5_2" <%if(t5!=null){if(t1.getTipo()==1){ %> value="<%=esito2_t5 %>"> <%}} %></td></tr>
    <tr id="esitoTampone5_3" style="display: none"><td>3. <input type="text" name="esitoTampone5_3" <%if(t5!=null){if(t5.getTipo()==1){ %> value="<%=esito3_t5%>" <%}} %>></td></tr>
    <tr id="esitoTampone5_4" style="display: none"><td>4. <input type="text" name="esitoTampone5_4" <%if(t5!=null){if(t5.getTipo()==1){ %> value="<%=esito4_t5%>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone5" style="display: none">
    <%RicercaTamponi2_5.setJsEvent("onChange=showEsitiTipo2_tampone5()"); %>
   
    <td>Superfice Testata <br> <textarea rows="5" cols="30" name="superfice5"><%=superfice %></textarea> </td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi2_5.getHtmlSelect("RicercaTamponi2_5",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta10"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone5" style="display: none"><td>1. <input type="text" name="esito_1_tampone5" <%if(t5!=null){if(t5.getTipo()==2){ %> value="<%=esito1_t5 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone5" style="display: none"><td>2. <input type="text" name="esito_2_tampone5" <%if(t5!=null){if(t5.getTipo()==2){ %> value="<%=esito2_t5 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone5" style="display: none"><td>3. <input type="text" name="esito_3_tampone5" <%if(t5!=null){if(t5.getTipo()==2){ %> value="<%=esito3_t5 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone5" style="display: none"><td>4. <input type="text" name="esito_4_tampone5" <%if(t5!=null){if(t5.getTipo()==2){ %> value="<%=esito4_t5%>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
      
    
    
     <tr id="tampone6" style="display: none">
    <td><b>Tampone 6 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
    <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td><input type="checkbox" name="check1_6" value="1" <%if(t6!=null){ if(TicketDetails.getListaTamponi().get(6).getTipo()==1){ %> checked="checked" <%} }%> id="check1_6" onclick="showRow1_tampone6()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td><input type="checkbox" name="check2_6" value="2" <%if(t6!=null){ if(TicketDetails.getListaTamponi().get(6).getTipo()==2){ %> checked="checked" <%} }%> id="check2_6" onclick="showRow1_tampone6()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_6.setJsEvent("onChange=showEsitiTampone6()"); %>
    <td>
    <table >
    
    
    <tr valign="top" id="row1tampone6" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t6!=null){
	   idsuperfice=t6.getSuperfice();
	superfice=t6.getSuperficeStringa();   
   }
	   %>
    <td>Carcassa <br><%=Tamponi.getHtmlSelect("Tamponi6",idsuperfice) %></td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi_6.getHtmlSelect("RicercaTamponi_6",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta10"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
    
    
    <tr id="esitoTampone6_1" style="display: none"><td>1. <input type="text" name="esitoTampone6_1" <%if(t6!=null){if(t6.getTipo()==1){ %> value="<%=esito1_t6 %>" <%}} %>></td></tr>
    <tr id="esitoTampone6_2" style="display: none"><td>2. <input type="text" name="esitoTampone6_2" <%if(t6!=null){if(t6.getTipo()==1){ %> value="<%=esito2_t6 %>" <%}} %>></td></tr>
    <tr id="esitoTampone6_3" style="display: none"><td>3. <input type="text" name="esitoTampone6_3" <%if(t6!=null){if(t6.getTipo()==1){ %> value="<%=esito3_t6 %>" <%}} %>></td></tr>
    <tr id="esitoTampone6_4" style="display: none"><td>4. <input type="text" name="esitoTampone6_4" <%if(t6!=null){if(t6.getTipo()==1){ %> value="<%=esito4_t6 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone6" style="display: none">
    <%RicercaTamponi2_6.setJsEvent("onChange=showEsitiTipo2_tampone6()"); %>
   
    <td>Superfice Testata <br> <textarea rows="5" cols="30" name="superfice6"><%=superfice %></textarea> </td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi2_6.getHtmlSelect("RicercaTamponi2_6",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta10"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone6" style="display: none"><td>1. <input type="text" name="esito_1_tampone6" <%if(t6!=null){if(t6.getTipo()==2){ %> value="<%=esito1_t6 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone6" style="display: none"><td>2. <input type="text" name="esito_2_tampone6"  <%if(t6!=null){if(t6.getTipo()==2){ %>value="<%=esito2_t6 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone6" style="display: none"><td>3. <input type="text" name="esito_3_tampone6" <%if(t6!=null){if(t6.getTipo()==2){ %> value="<%=esito3_t6 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone6" style="display: none"><td>4. <input type="text" name="esito_4_tampone6" <%if(t6!=null){if(t6.getTipo()==2){ %> value="<%=esito4_t6 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
   
    
    
     
     <tr id="tampone7" style="display: none">
    <td><b>Tampone 7 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <%RicercaTamponi_7.setJsEvent("onChange=showEsitiTampone7()");  %>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td><input type="checkbox" name="check1_7" value="1" <%if(t7!=null){ if(TicketDetails.getListaTamponi().get(7).getTipo()==1){ %> checked="checked" <%}} %> id="check1_7" onclick="showRow1_tampone7()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td><input type="checkbox" name="check2_7" value="2" <%if(t7!=null){ if(TicketDetails.getListaTamponi().get(7).getTipo()==2){ %> checked="checked" <%} }%> id="check2_7" onclick="showRow1_tampone7()"></td></tr>
     </table>
   </td>
     
    
    <td>
    <table >
    <tr valign="top" id="row1tampone7" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t7!=null){
	   idsuperfice=t7.getSuperfice();
	superfice=t7.getSuperficeStringa();   
   }
	   %>
    <td>Carcassa <br><%=Tamponi.getHtmlSelect("Tamponi7",idsuperfice) %></td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi_7.getHtmlSelect("RicercaTamponi_7",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta10"  style="display: none">Esiti</label>
    
    <table id="esitiTampone1">
    
    
    <tr id="esitoTampone7_1" style="display: none"><td>1. <input type="text" name="esitoTampone7_1" <%if(t7!=null){if(t7.getTipo()==1){ %> value="<%=esito1_t7 %>" <%}} %>></td></tr>
    <tr id="esitoTampone7_2" style="display: none"><td>2. <input type="text" name="esitoTampone7_2" <%if(t7!=null){if(t7.getTipo()==1){ %> value="<%=esito2_t7 %>" <%}} %>></td></tr>
    <tr id="esitoTampone7_3" style="display: none"><td>3. <input type="text" name="esitoTampone7_3" <%if(t7!=null){if(t1.getTipo()==1){ %> value="<%=esito3_t7 %>" <%}} %>></td></tr>
    <tr id="esitoTampone7_4" style="display: none"><td>4. <input type="text" name="esitoTampone7_4" <%if(t7!=null){if(t1.getTipo()==1){ %> value="<%=esito4_t7 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone7" style="display: none">
    <%RicercaTamponi2_7.setJsEvent("onChange=showEsitiTipo2_tampone7()"); %>
   
    <td>Superfice Testata <br> <textarea rows="5" cols="30" name="superfice7"><%=superfice %></textarea> </td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi2_7.getHtmlSelect("RicercaTamponi2_7",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta10"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone7" style="display: none"><td>1. <input type="text" name="esito_1_tampone7" <%if(t7!=null){if(t7.getTipo()==2){ %> value="<%=esito1_t7 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone7" style="display: none"><td>2. <input type="text" name="esito_2_tampone7" <%if(t7!=null){if(t7.getTipo()==2){ %> value="<%=esito2_t7 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone7" style="display: none"><td>3. <input type="text" name="esito_3_tampone7" <%if(t7!=null){if(t7.getTipo()==2){ %> value="<%=esito3_t7 %>" <%}} %>> </td></tr>
    <tr id="esito_4_tampone7" style="display: none"><td>4. <input type="text" name="esito_4_tampone7" <%if(t7!=null){if(t7.getTipo()==2){ %> value="<%=esito4_t7%>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
 
    
   
    
     <tr id="tampone8" style="display: none">
    <td><b>Tampone 8 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
    <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td><input type="checkbox" name="check1_8" value="1" id="check1_8" <%if(t8!=null){ if(TicketDetails.getListaTamponi().get(8).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone8()"></td></tr>
     <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td><input type="checkbox" name="check2_8" value="2" id="check2_8" <%if(t8!=null){ if(TicketDetails.getListaTamponi().get(8).getTipo()==2){ %> checked="checked" <%}} %> onclick="showRow1_tampone8()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_8.setJsEvent("onChange=showEsitiTampone8()"); %>
    <td>
    <table >
    
       
    <tr valign="top" id="row1tampone8" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t8!=null){
	   idsuperfice=t8.getSuperfice();
	superfice=t8.getSuperficeStringa();   
   }
	   %>
    <td>Carcassa <br><%=Tamponi.getHtmlSelect("Tamponi8",idsuperfice) %></td>
    <td>Tipo di Ricerca <br> <%=RicercaTamponi_8.getHtmlSelect("RicercaTamponi_8",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta10"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
     
    
    <tr id="esitoTampone8_1" style="display: none"><td>1. <input type="text" name="esitoTampone8_1" <%if(t8!=null){if(t8.getTipo()==1){ %> value="<%=esito1_t8 %>" <%}} %>></td></tr>
    <tr id="esitoTampone8_2" style="display: none"><td>2. <input type="text" name="esitoTampone8_2" <%if(t8!=null){if(t8.getTipo()==1){ %> value="<%=esito2_t8 %>" <%}} %>></td></tr>
    <tr id="esitoTampone8_3" style="display: none"><td>3. <input type="text" name="esitoTampone8_3" <%if(t8!=null){if(t8.getTipo()==1){ %> value="<%=esito3_t8 %>" <%}} %>></td></tr>
    <tr id="esitoTampone8_4" style="display: none"><td>4. <input type="text" name="esitoTampone8_4" <%if(t8!=null){if(t8.getTipo()==1){ %> value="<%=esito4_t8 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone8" style="display: none">
    <%RicercaTamponi2_8.setJsEvent("onChange=showEsitiTipo2_tampone8()"); %>
   
    <td> Superfice Testata <br><textarea rows="5" cols="30" name="superfice8"><%=superfice %></textarea> </td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi2_8.getHtmlSelect("RicercaTamponi2_8",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta10"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone8" style="display: none"><td>1. <input type="text" name="esito_1_tampone8" <%if(t8!=null){if(t8.getTipo()==2){ %> value="<%=esito1_t8 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone8" style="display: none"><td>2. <input type="text" name="esito_2_tampone8" <%if(t8!=null){if(t8.getTipo()==2){ %> value="<%=esito2_t8 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone8" style="display: none"><td>3. <input type="text" name="esito_3_tampone8" <%if(t8!=null){if(t8.getTipo()==2){ %> value="<%=esito3_t8 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone8" style="display: none"><td>4. <input type="text" name="esito_4_tampone8" <%if(t8!=null){if(t8.getTipo()==2){ %> value="<%=esito4_t8%>" <%}} %> ></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
    
    
    
     <tr id="tampone9" style="display: none">
    <td><b>Tampone 9 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     <td>
     <table>
     <tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
     <tr><td><input type="checkbox" name="check1_9" value="1" id="check1_9" <%if(t9!=null){ if(TicketDetails.getListaTamponi().get(9).getTipo()==1){ %> checked="checked" <%} }%> onclick="showRow1_tampone9()"></td></tr>
    <tr><td align="middle"><label>Altro</label></td></tr>
     <tr><td><input type="checkbox" name="check2_9" value="2" id="check2_9" <%if(t9!=null){if(TicketDetails.getListaTamponi().get(9).getTipo()==2){ %> checked="checked" <%} }%> onclick="showRow1_tampone9()"></td></tr>
     </table>
   </td>
     
    <%RicercaTamponi_9.setJsEvent("onChange=showEsitiTampone9()"); %>
    <td>
    <table >
    <tr valign="top" id="row1tampone9" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t9!=null){
	   idsuperfice=t9.getSuperfice();
	superfice=t9.getSuperficeStringa();   
   }
	   %>
    <td>Carcassa <br><%=Tamponi.getHtmlSelect("Tamponi9",idsuperfice) %></td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi_9.getHtmlSelect("RicercaTamponi_9",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta10"  style="display: none">Esiti</label>
    <table id="esitiTampone1">
     
    
    <tr id="esitoTampone9_1" style="display: none"><td>1. <input type="text" name="esitoTampone9_1" <%if(t9!=null){if(t9.getTipo()==1){ %> value="<%=esito1_t9 %>" <%}} %>></td></tr>
    <tr id="esitoTampone9_2" style="display: none"><td>2. <input type="text" name="esitoTampone9_2" <%if(t9!=null){if(t9.getTipo()==1){ %> value="<%=esito2_t9 %>" <%}} %>></td></tr>
    <tr id="esitoTampone9_3" style="display: none"><td>3. <input type="text" name="esitoTampone9_3" <%if(t9!=null){if(t9.getTipo()==1){ %> value="<%=esito3_t9 %>" <%}} %>></td></tr>
    <tr id="esitoTampone9_4" style="display: none"><td>4. <input type="text" name="esitoTampone9_4" <%if(t9!=null){if(t9.getTipo()==1){ %> value="<%=esito4_t9 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone9" style="display: none">
    <%RicercaTamponi2_9.setJsEvent("onChange=showEsitiTipo2_tampone9()"); %>
   
    <td>Superfice Testata <br> <textarea rows="5" cols="30" name="superfice9"><%=superfice %></textarea> </td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi2_9.getHtmlSelect("RicercaTamponi2_9",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta10"  style="display: none">Esiti</label>
    <table id="esiti_Tampone1">
    <tr id="esito_1_tampone9" style="display: none"><td>1. <input type="text" name="esito_1_tampone9" <%if(t9!=null){if(t9.getTipo()==2){ %> value="<%=esito1_t9 %>" <%}} %>></td></tr>
    <tr id="esito_2_tampone9" style="display: none"><td>2. <input type="text" name="esito_2_tampone9" <%if(t9!=null){if(t9.getTipo()==2){ %> value="<%=esito2_t9 %>" <%}} %>></td></tr>
    <tr id="esito_3_tampone9" style="display: none"><td>3. <input type="text" name="esito_3_tampone9" <%if(t9!=null){if(t9.getTipo()==2){ %> value="<%=esito3_t9 %>" <%}} %>></td></tr>
    <tr id="esito_4_tampone9" style="display: none"><td>4. <input type="text" name="esito_4_tampone9" <%if(t9!=null){if(t9.getTipo()==2){ %> value="<%=esito4_t9 %>" <%}} %>></td></tr>
    </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    
    
   
  
    
    
    
     <tr id="tampone10" style="display: none">
     	<td><b>Tampone 10 &nbsp;&nbsp;&nbsp;&nbsp;</b></td>
     	<td>
     		<table>
    		<tr><td align="middle"><label>Criteri di Igiene</br>di Processo</br>Reg. CE 2073/'05</label></td></tr>
    			 <tr><td><input type="checkbox" name="check1_10" value="1" id="check1_10" <%if(t10!=null){if(TicketDetails.getListaTamponi().get(10).getTipo()==1){ %> checked="checked" <%}} %> onclick="showRow1_tampone10()"></td></tr>
     			<tr><td align="middle"><label>Altro</label></td></tr>
     			<tr><td><input type="checkbox" name="check2_10" value="2" id="check2_10" <%if(t10!=null){if(TicketDetails.getListaTamponi().get(10).getTipo()==2){ %> checked="checked" <%} }%> onclick="showRow1_tampone10()"></td></tr>
     		</table>
  		 </td>
     
    <%RicercaTamponi_10.setJsEvent("onChange=showEsitiTampone10()"); %>
    	<td>
    		<table >
     
    		<tr valign="top" id="row1tampone10" style="display: none">
    <%
    idsuperfice=-1;
    superfice="";
   if(t10!=null){
	   idsuperfice=t10.getSuperfice();
	superfice=t10.getSuperficeStringa();   
   }
	   %>
    <td>Carcassa <br><%=Tamponi.getHtmlSelect("Tamponi10",idsuperfice) %></td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi_10.getHtmlSelect("RicercaTamponi_10",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta10"  style="display: none">Esiti</label>
    			<table id="esitiTampone1">
     
    
    <tr id="esitoTampone10_1" style="display: none"><td>1. <input type="text" name="esitoTampone10_1" <%if(t10!=null){if(t10.getTipo()==1){ %> value="<%=esito1_t10 %>" <%}} %>></td></tr>
    <tr id="esitoTampone10_2" style="display: none"><td>2. <input type="text" name="esitoTampone10_2" <%if(t10!=null){if(t10.getTipo()==1){ %> value="<%=esito2_t10 %>" <%}} %>></td></tr>
    <tr id="esitoTampone10_3" style="display: none"><td>3. <input type="text" name="esitoTampone10_3" <%if(t10!=null){if(t10.getTipo()==1){ %> value="<%=esito3_t10 %>" <%}} %>></td></tr>
    <tr id="esitoTampone10_4" style="display: none"><td>4. <input type="text" name="esitoTampone10_4" <%if(t10!=null){if(t10.getTipo()==1){ %> value="<%=esito4_t10 %>" <%}} %>></td></tr>
  			  </table>
   	 
    
    </td>
    
    </tr>
    
    <tr valign="top" id="row2tampone10" style="display: none">
    <%RicercaTamponi2_10.setJsEvent("onChange=showEsitiTipo2_tampone10()"); %>
   
    <td>Superfice Testata <br> <textarea rows="5" cols="30" name="superfice10"><%=superfice %></textarea> </td>
    <td>Tipo di Ricerca <br><%=RicercaTamponi2_10.getHtmlSelect("RicercaTamponi2_10",TicketDetails.getRicercaTamponi()) %></td>
    <td>
    <label id="etichetta10"  style="display: none">Esiti</label>
   	 <table id="esiti_Tampone1">
    <tr id="esito_1_tampone10" style="display: none"><td>1. <input type="text" name="esito_1_tampone10" <%if(t10!=null){if(t10.getTipo()==2){ %> value="<%=esito1_t10 %>"<%}} %>></td></tr>
    <tr id="esito_2_tampone10" style="display: none"><td>2. <input type="text" name="esito_2_tampone10" <%if(t10!=null){if(t10.getTipo()==2){ %> value="<%=esito2_t10 %>"<%}} %>></td></tr>
    <tr id="esito_3_tampone10" style="display: none"><td>3. <input type="text" name="esito_3_tampone10" <%if(t10!=null){if(t10.getTipo()==2){ %> value="<%=esito3_t10 %>"<%}} %>></td></tr>
    <tr id="esito_4_tampone10" style="display: none"><td>4. <input type="text" name="esito_4_tampone10" <%if(t10!=null){if(t10.getTipo()==2){ %> value="<%=esito4_t10 %>" <%}} %>></td></tr>
  	  </table>
    
    
    </td>
    
    </tr>
    
    </table>
    
    </td>
    
    </tr>
    

    
    </table>
    
    </td>
   </tr>
  
  
  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
    Numero Verbale
    </td>
    <td>
	<%if((TicketDetails.getLocation() != "" && TicketDetails.getLocation() != null)){ %>
      <input type="text" name="location" id="location" value="<%= toHtmlValue(TicketDetails.getLocation()) %>" size="20" maxlength="256" />
    <%}else{%>
          <input type="text" name="location" id="location" value="" size="20" maxlength="256" />
    <%} %>
    </td>
  </tr>
 <tr class="containerBody">
      <td nowrap class="formLabel">
       Data Prelievo
      </td>
      <td>
        <zeroio:dateSelect form="details" field="assignedDate" timestamp="<%= TicketDetails.getAssignedDate() %>"  timeZone="<%= TicketDetails.getAssignedDateTimeZone() %>" showTimeZone="false" />
        <font color="red">*</font> <%= showAttribute(request, "assignedDateError") %>
      </td>
    </tr>
	
  <%--
  <tr class="containerBody">
    <td class="formLabel">
      <dhv:label name="sanzioni.data_ispezione">Data Macellazione</dhv:label>
    </td>
    <td>
      <zeroio:dateSelect form="addticket" field="estimatedResolutionDate" timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"  showTimeZone="false" />
      <%= showAttribute(request, "estimatedResolutionDateError") %>
    </td>
  </tr>--%>
  
<dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td name="destinatarioTampone1" id="destinatarioTampone1" nowrap class="formLabel">
        <dhv:label name="">Laboratorio di Destinazione</dhv:label>
      </td>
    <td>
      <%= DestinatarioTampone.getHtmlSelect("DestinatarioTampone",TicketDetails.getDestinatarioTampone()) %>
  <%--   <input type="hidden" name="destinatarioTampone" value="<%=TicketDetails.getDestinatarioTampone()%>" > --%>
    </td>
  </tr>
</dhv:include>

 <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Codice Accettazione</dhv:label>
    </td>
    <td>
      <input type="text" name="cause" id="cause" value="<%= toHtmlValue(TicketDetails.getCause()) %>" size="20" maxlength="256" />
    </td>
  </tr>
<tr class="containerBody">
    <td nowrap class="formLabel">
      <dhv:label name="">Data Accettazione</dhv:label>
    </td>
    <td>
        <zeroio:dateSelect form="details" field="dataAccettazione" timestamp="<%= TicketDetails.getDataAccettazione() %>"  timeZone="<%= TicketDetails.getDataAccettazioneTimeZone() %>" showTimeZone="false" />
    </td>
  </tr>
  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.azioni">Punteggio</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <select name="punteggio">
            <option value="0" <%if(TicketDetails.getPunteggio()==0){ %> selected="selected" <%} %> >   0 </option>
            <option value="2" <%if(TicketDetails.getPunteggio()==2) {%> selected="selected" <%} %>>   2 </option>
            <option value="4" <%if(TicketDetails.getPunteggio()==4){ %> selected="selected" <%} %>>   4 </option>
            <option value="6" <%if(TicketDetails.getPunteggio()==6) {%> selected="selected" <%} %>>   6 </option>
            <option value="8" <%if(TicketDetails.getPunteggio()==8) {%> selected="selected" <%} %>>   8 </option>
            <option value="10" <%if(TicketDetails.getPunteggio()==10){ %> selected="selected" <%} %>> 10 </option> 
            </select>
            (Punteggio a scelta dell'Ispettore in funzione della gravità della n.c. rilevata)
          </td>
         
        </tr>
   </table></td></tr>
  
  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzioni.note">Note</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="problem" cols="55" rows="8"><%= toString(TicketDetails.getProblem()) %></textarea>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
      </table>
    </td>
	</tr>
 
  
  
  
 <!-- modifiche aggiunto da d.dauria  -->
 
 <!--  alimenti di origine  animale -->
 <!-- <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Alimenti di origine animale</dhv:label>
    </td>
    <td>    
    <table>
    <tr>
     <td>
     <% //if( TicketDetails.getAlimentiOrigineAnimale() == true ){%>
       <input type="checkbox" name="alimentiOrigineAnimale" id="alimentiOrigineAnimale" size="20" maxlength="256"  "checked" id="alimentiOrigineAnimale" onclick="abilitaLookupOrigineAnimale()"/>
       <%//} else {%>
       <input type="checkbox" name="alimentiOrigineAnimale" id="alimentiOrigineAnimale" size="20" maxlength="256" onclick="abilitaLookupOrigineAnimale()"/>
       <%//} %>
     </td>
      <td id="lookupNonTrasformati">
       <% //AlimentiNonTrasformati.setJsEvent("onchange=\"javascript:abilitaSpecie(this.form);\"");%>
      <%//= AlimentiNonTrasformati.getHtmlSelect("alimentiOrigineAnimaleNonTrasformati",TicketDetails.getAlimentiOrigineAnimaleNonTrasformati()) %>
      </td> 
     <td id="lookupNonTrasformatiValori">
      <%//= AlimentiNonTrasformatiValori.getHtmlSelect("alimentiOrigineAnimaleNonTrasformatiValori",TicketDetails.getAlimentiOrigineAnimaleNonTrasformatiValori()) %>
     </td>
     <td id="lookupTrasformati">
     <%  //AlimentiTrasformati.setJsEvent("onchange=\"javascript:disabilitaNonTrasformati(this.form);\"");%>
     <%//= AlimentiTrasformati.getHtmlSelect("alimentiOrigineAnimaleTrasformati",TicketDetails.getAlimentiOrigineAnimaleTrasformati()) %>
     </td>
    </tr>
    </table>
    </td> 
  </tr>
  -->
  <!-- chiusura tabella interna -->
  
  <!-- alimenti origine vegetale -->
 <!--  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Alimenti di origine vegetale</dhv:label>
    </td>
     <td>    
    <table>
    <tr>
     <td>
     <% //if( TicketDetails.getAlimentiOrigineVegetale() == true ){%>
       <input type="checkbox" name="alimentiOrigineVegetale" id="alimentiOrigineVegetale" size="20" maxlength="256" "checked"  onclick="abilitaLookupOrigineVegetale()"/>
       <%//} else {%>
              <input type="checkbox" name="alimentiOrigineVegetale" id="alimentiOrigineVegetale" onclick="abilitaLookupOrigineVegetale()" size="20" maxlength="256" />
       
       <%//} %>
     </td>
      <td id="lookupVegetale">
      <%//= AlimentiVegetali.getHtmlSelect("alimentiOrigineVegetaleValori",TicketDetails.getAlimentiOrigineVegetaleValori()) %>
      </td> 
      </tr>
      </table> <!--  chiusura tabella alimenti vegetali -->
     <!-- </td>
     </tr> --> 
  
   <!-- alimenti composti -->
<!--   <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Alimenti composti</dhv:label>
    </td>
    <td>
      <%// if( TicketDetails.getAlimentiComposti() == true ){%>
       <input type="checkbox" name="alimentiComposti" id="alimentiComposti" size="20" maxlength="256" onchange="abilitaTestoAlimentoComposto()" "checked"/>
       <input type="text" name="testoAlimentoComposto" id="testoAlimentoComposto"  value="<%= toHtmlValue(TicketDetails.getTestoAlimentoComposto()) %>" size="20" maxlength="256" />
       <%//} else {%>
          <input type="checkbox" name="alimentiComposti" id="alimentiComposti" size="20" maxlength="256" onchange="abilitaTestoAlimentoComposto()" />    
          <input type="text" name="testoAlimentoComposto" id="testoAlimentoComposto"  value="<%= toHtmlValue(TicketDetails.getTestoAlimentoComposto()) %>" size="20" maxlength="256" />
     
       <%//} %>
     </td>
     </tr>
     -->
       <!-- nucleo ispettivo -->
 <!--   <tr class="containerBody" >
    <td valign="top" class="formLabel">
      <dhv:label name="nucleo.ispettivo">Nucleo Ispettivo</dhv:label>
    </td>
  <td>
     <table> 
     <div id="nucleouno">
      <tr >
        <td>
            <%= TitoloNucleo.getHtmlSelect("nucleoIspettivo",TicketDetails.getNucleoIspettivo()) %>   
        </td>
        <td>
              <input type="text" name="componenteNucleo" value="<%=TicketDetails.getComponenteNucleo()%>" onclick="abilita2()"  size="20" maxlength="256" onchange="abilita2()"/>
        </td>
      </tr>  
     </div>
     <div > 
      <tr id="nucleodue" style="display: none">   
        <td>
            <%= TitoloNucleoDue.getHtmlSelect("nucleoIspettivoDue",TicketDetails.getNucleoIspettivoDue()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoDue" value="<%=TicketDetails.getComponenteNucleoDue()%>" onclick="abilita3()" size="20" maxlength="256" onchange="abilita3()"/>
        </td>
      </tr>
     </div> 
     <div >
      <tr id="nucleotre" style="display: none">  
        <td>
            <%= TitoloNucleoTre.getHtmlSelect("nucleoIspettivoTre",TicketDetails.getNucleoIspettivoTre()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoTre" value="<%=TicketDetails.getComponenteNucleoTre()%>" onclick="abilita4()"  size="20" maxlength="256" onchange="abilita4()"/>
        </td>
      </tr>
      </div>
      <div >
      <tr id="nucleoquattro" style="display: none">  
         <td>
            <%= TitoloNucleoQuattro.getHtmlSelect("nucleoIspettivoQuattro",TicketDetails.getNucleoIspettivoQuattro()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoQuattro" value="<%=TicketDetails.getComponenteNucleoQuattro()%>" onclick="abilita5()" size="20" maxlength="256" onchange="abilita5()"/>
        </td>
      </tr>
      </div>
     <div > 
      <tr id="nucleocinque" style="display: none">  
         <td>
            <%= TitoloNucleoCinque.getHtmlSelect("nucleoIspettivoCinque",TicketDetails.getNucleoIspettivoCinque()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoCinque" value="<%=TicketDetails.getComponenteNucleoCinque()%>" onclick="abilita6()" size="20" maxlength="256" onchange="abilita6()"/>
        </td>
      </tr>
     </div>
     <div > 
     <tr id="nucleosei" style="display: none">  
         <td>
            <%= TitoloNucleoSei.getHtmlSelect("nucleoIspettivoSei",TicketDetails.getNucleoIspettivoSei()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoSei" value="<%=TicketDetails.getComponenteNucleoSei()%>" onclick="abilita7()"  size="20" maxlength="256" onchange="abilita7()"/>
        </td>
      </tr>
      </div>
      <div  >
      <tr id="nucleosette" style="display: none">  
         <td>
            <%= TitoloNucleoSette.getHtmlSelect("nucleoIspettivoSette",TicketDetails.getNucleoIspettivoSette()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoSette"  value="<%=TicketDetails.getComponenteNucleoSette()%>" onclick="abilita8()" size="20" maxlength="256" onchange="abilita8()"/>
        </td>
      </tr>
      </div>
      <div >
      <tr id="nucleootto"  style="display: none">  
         <td>
            <%= TitoloNucleoOtto.getHtmlSelect("nucleoIspettivoOtto",TicketDetails.getNucleoIspettivoOtto()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoOtto"  value="<%=TicketDetails.getComponenteNucleoOtto()%>" size="20" maxlength="256" onclick="abilita9()" onchange="abilita9()"/>
        </td>
      </tr>
      </div>
      <div >
      <tr id="nucleonove" style="display: none">  
         <td>
            <%= TitoloNucleoNove.getHtmlSelect("nucleoIspettivoNove",TicketDetails.getNucleoIspettivoNove()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoNove" value="<%=TicketDetails.getComponenteNucleoNove()%>"  size="20" onclick="abilita10()" maxlength="256" onchange="abilita10()"/>
        </td>
      </tr>
      </div>
      <div >
      <tr id="nucleodieci" style="display:none">  
         <td>
            <%= TitoloNucleoDieci.getHtmlSelect("nucleoIspettivoDieci",TicketDetails.getNucleoIspettivoDieci()) %>
        </td>
        <td>
              <input type="text" name="componenteNucleoDieci"  value="<%=TicketDetails.getComponenteNucleoDieci()%>" size="20" maxlength="256" />
        </td>
      </tr>
      </div>
      
     </table>  
     
      
    </td>
   </tr> -->
     
   </table> <!--  chiusura tabella generale -->
   </br>
   
   
     <!-- d.dauria Positività -->
  <!--  <table cellpadding="4" cellspacing="0" width="100%" class="details">
	 <tr>
       <th colspan="2">
         <strong><dhv:label name="">Positivita'</dhv:label></strong>
       </th>
	</tr>-->
	 <!-- conseguenze positività -->
    <!-- <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importox">Conseguenze positività</dhv:label>
    	</td>
     <td>    
       
        <table>
         <tr>
           <td>
              <% //ConseguenzePositivita.setJsEvent("onchange=\"javascript:abilitaNote(this.form);\"");%>
             <%//= ConseguenzePositivita.getHtmlSelect("conseguenzePositivita",TicketDetails.getConseguenzePositivita()) %>
           </td>
           <td id="note_etichetta">Note per altro </td>
           <td id="note"><input type="text" name="noteEsito" id="noteEsito" value="<%= toHtmlValue(TicketDetails.getNoteEsito()) %>" size="60" maxlength="256" /></td>
         </tr>
         </table>
      </td>
     </tr>    -->  
     
     
      <!--responsabilità positività -->
  <!--   <tr class="containerBody">
        <td valign="top" class="formLabel">
          <dhv:label name="sanzionia.importox">Responsabilità positività</dhv:label>
    	</td>
    	 <td><%//= ResponsabilitaPositivita.getHtmlSelect("responsabilitaPositivita",TicketDetails.getResponsabilitaPositivita()) %></td>
    </tr> -->
    
    <!-- allerta -->
  <!-- <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Allerta</dhv:label>
    </td>
    <td>
     <% //if( TicketDetails.getAllerta() == true ){%>
       <input type="checkbox" name="allerta" id="allerta"  size="20" maxlength="256" checked onchange="abilitaCheckAllerta()" />
       <%//}else {%>
         <input type="checkbox" name="allerta" id="allerta" size="20" maxlength="256" onchange="abilitaCheckAllerta()" />
        <%//} %> 
     </td>
  </tr> -->
         
 
	<!-- non conformità -->
 <!--  <tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.importo">Segnalazione di non conformità</dhv:label>
    </td>
    <td>
        <% //if( TicketDetails.getNonConformita() == true ){%>
         <input type="checkbox" name="nonConformita" id="nonConformita" size="20" maxlength="256" checked onchange="abilitaCheckSegnalazione()" />
        <%//}else{ %>
          <input type="checkbox" name="nonConformita" id="nonConformita"  size="20" maxlength="256" onchange="abilitaCheckSegnalazione()" />
         <%//} %> 
     </td>
  </tr>
   
	
	
   </table>  -->
   
   
   
 <!-- <table cellpadding="4" cellspacing="0" width="100%" class="details">
	<tr>
    <th colspan="2">
      <strong><dhv:label name="">Esito Tampone</dhv:label></strong>
    </th>
	</tr>
   <tr class="containerBody">
      <td class="formLabel">
        <dhv:label name="">Data</dhv:label>
      </td>
      <td>
        <zeroio:dateSelect form="details" field="estimatedResolutionDate" timestamp="<%= TicketDetails.getEstimatedResolutionDate() %>" timeZone="<%= TicketDetails.getEstimatedResolutionDateTimeZone() %>"  showTimeZone="false" />
        <%= showAttribute(request, "estimatedResolutionDateError") %>
      </td>
    </tr>
  <dhv:include name="organization.source" none="true">
   <tr class="containerBody">
      <td name="esitoTampone1" id="esitoTampone1" nowrap class="formLabel">
        <dhv:label name="">Esito</dhv:label>
      </td>
    <td>
      <%= EsitoTampone.getHtmlSelect("EsitoTampone",TicketDetails.getEsitoTampone()) %>
      <input type="hidden" name="esitoTampone" value="<%=TicketDetails.getEsitoTampone()%>" >
    </td>
  </tr>
</dhv:include>
<tr class="containerBody">
    <td valign="top" class="formLabel">
      <dhv:label name="sanzionia.azioni">Ulteriori Note</dhv:label>
    </td>
    <td>
      <table border="0" cellspacing="0" cellpadding="0" class="empty">
        <tr>
          <td>
            <textarea name="solution" cols="55" rows="8"><%= toString(TicketDetails.getSolution()) %></textarea>
          </td>
          <td valign="top">
            <%= showAttribute(request, "problemError") %>
          </td>
        </tr>
    </table>
    </td>
    </tr>
    

  </table>-->
        &nbsp;<br>
   <dhv:evaluate if="<%= !TicketDetails.isTrashed() %>" >
    <dhv:evaluate if="<%= TicketDetails.getClosed() != null %>" >
        <dhv:permission name="osmregistrati-osmregistrati-tamponi-edit">
          <input type="submit" value="<dhv:label name="button.reopen">Reopen</dhv:label>" onClick="javascript:this.form.action='OsmRegistratiTamponi.do?command=ReopenTicket&id=<%=TicketDetails.getId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'">
         </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='OsmRegistrati.do?command=ViewTamponi&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='OsmRegistratiTamponi.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
    </dhv:evaluate>
    <dhv:evaluate if="<%= TicketDetails.getClosed() == null %>" >
        <dhv:permission name="osmregistrati-osmregistrati-tamponi-edit">
          <input type="submit" value="<dhv:label name="global.button.update">Update</dhv:label>" onClick="return checkForm(this.form)" />
        </dhv:permission>
         <dhv:evaluate if='<%= "list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='OsmRegistrati.do?command=ViewTamponi&orgId=<%=OrgDetails.getOrgId()%><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
         <dhv:evaluate if='<%= !"list".equals(request.getParameter("return"))%>' >
          <input type="submit" value="Annulla" onClick="javascript:this.form.action='OsmRegistratiTamponi.do?command=TicketDetails&id=<%= TicketDetails.getId() %><%= addLinkParams(request, "popup|popupType|actionId") %>'" />
         </dhv:evaluate>
        <%= showAttribute(request, "closedError") %>
     </dhv:evaluate>
    </dhv:evaluate>
    <input type="hidden" name="modified" value="<%= TicketDetails.getModified() %>">
    <input type="hidden" name="orgId" value="<%=TicketDetails.getOrgId()%>">
    <input type="hidden" name="orgSiteId" id="orgSiteId" value="<%=  TicketDetails.getOrgSiteId() %>" />
    <input type="hidden" name="id" value="<%= TicketDetails.getId() %>">
    <input type="hidden" name="companyName" value="<%= toHtml(TicketDetails.getCompanyName()) %>">
    <input type="hidden" name="statusId" value="<%=  TicketDetails.getStatusId() %>" />
    <input type="hidden" name="trashedDate" value="<%=  TicketDetails.getTrashedDate() %>" />
    <input type="hidden" name="close" value="">
    <input type="hidden" name="refresh" value="-1">
    <input type="hidden" name="currentDate" value="<%=  request.getAttribute("currentDate") %>" />
  </dhv:container>
</dhv:container>
</form>
</body>
