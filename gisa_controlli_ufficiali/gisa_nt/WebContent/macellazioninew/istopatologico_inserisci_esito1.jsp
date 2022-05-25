<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>


<%@page
	import="org.aspcfs.modules.macellazioninew.base.Organi"%>

<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>

<%@page import="javax.imageio.ImageIO"%>
<%@ page import="java.awt.*, java.awt.image.*,  java.util.StringTokenizer, com.itextpdf.text.pdf.*, java.io.*, java.util.*,org.aspcfs.utils.web.*, com.itextpdf.text.pdf.codec.*,
org.aspcfs.modules.campioni.base.SpecieAnimali" %>




<style>
<!--
.Section1 {
	color: black;
	position: relative;
	border: 0px solid red;
}
-->
</style>

<jsp:useBean id="OrgDetails"
	class="org.aspcfs.modules.stabilimenti.base.Organization"
	scope="request" />
<jsp:useBean id="Capo" class="org.aspcfs.modules.macellazioninew.base.GenericBean"
	scope="request" />
<jsp:useBean id="richiestaIstopatologico"
	class="org.aspcfs.modules.macellazioninew.base.RichiestaIstopatologico"
	scope="request" />
<jsp:useBean id="lookup_esame_istopatologico_tipo_diagnosi"
	class="org.aspcfs.utils.web.LookupList"
	scope="request" />	
<jsp:useBean id="lookup_associazione_classificazione_tabella_lookup"
	class="org.aspcfs.utils.web.LookupList"
	scope="request" />	

<script type="text/javascript" src="dwr/interface/PopolaCombo.js"> </script>
<script type="text/javascript" src="dwr/engine.js"> </script>
<script src="http://code.jquery.com/jquery-1.8.2.js"></script>
<script src="http://code.jquery.com/ui/1.9.1/jquery-ui.js"></script>

<SCRIPT LANGUAGE="JavaScript" SRC="javascript/CalendarPopup.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/common.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="javascript/jquery.popupWindow.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">document.write(getCalendarStyles());</SCRIPT>
<SCRIPT LANGUAGE="JavaScript" ID="js19">
var cal19 = new CalendarPopup();
cal19.showYearNavigation();
cal19.showYearNavigationInput();
cal19.showNavigationDropdowns();
</SCRIPT>

<style type="text/css">
	
/* Vertical Tabs
----------------------------------*/
.ui-tabs-vertical { /*width: 55em; */}
.ui-tabs-vertical .ui-tabs-nav { padding: .2em .1em .2em .2em; float: left; width: 12em; }
.ui-tabs-vertical .ui-tabs-nav li { clear: left; width: 100%; border-bottom-width: 1px !important; border-right-width: 0 !important; margin: 0 -1px .2em 0; }
.ui-tabs-vertical .ui-tabs-nav li a { display:block; }
.ui-tabs-vertical .ui-tabs-nav li.ui-tabs-selected { padding-bottom: 0; padding-right: .1em; border-right-width: 1px; border-right-width: 1px; }
.ui-tabs-vertical .ui-tabs-panel { padding: 1em; float: left; width: 50em;}

</style>

<script type="text/javascript">

function caricaClassificazioneLesione (campoIn)
{
	
	alert(campoIn);
	
		PopolaCombo.getHtmlSelectByIdLookup(campoIn,'lookup_associazione_classificazione_tabella_lookup',classificazioneCallBack);
}

function classificazioneCallBack(value)
{

alert(value);


	$("#classificazione").html(value);


	

	
}



function specificaEsito(value)
{


if (value == 1)

	$("#specificheEsito").css("visibility", "visible");
else

	$("#specificheEsito").css("visibility", "hidden");
	

	
}

function checkForm(form) {
	 
	//verificaContributo();
	
  	//checkMorsicatore();
  	formTest = true;
	message = "";
   


	
	 
	 
    if (formTest == false) {
      alert(label("check.form", "Form could not be saved, please check the following:\r\n\r\n") + message);
      return false;
    }
    
    

    else
    {
    	alert('true');
    	return true;
    }
	
  }
  
  
$(document).ready(function() {
	

	$("#main").submit(function(e){
		//alert('sd');
	    e.preventDefault();
	    $.ajax({
	        type     : "POST",
	        cache    : false,
	        url      : $(this).attr('action'),
	        data     : $(this).serialize(),
	         success  : function(data) {
	        	
			    window.close();
	        } 
	    })

	});

});
  

  
  


</script>	





<%@ include file="../../initPage.jsp"%>

<link type="text/css" href="css/ui.all.css" rel="stylesheet" />
<link type="text/css" href="css/demos.css" rel="stylesheet" />
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popCalendar.js"></script>
<script type="text/javascript" src="javascript/ui.core.js"></script>
<div>
Identificativo istopatologico: <%=richiestaIstopatologico.getIdentificativoRichiesta() %> <br></br>
Marca auricolare: <%=richiestaIstopatologico.getCapo().getCd_matricola() %>

</div>
<br>
<form id="main"  name="main" action="Macellazioni.do?command=SaveEsitoRichiestaIstopatologico&auto-populate=true<%=addLinkParams(request, "popup|popupType|actionId")%>"
		method="post" onsubmit="return checkForm();"> 
<div>
			Data esito: <input readonly type="text"
				name="dataEsito" size="10"
				value="<%=DateUtils.timestamp2string(richiestaIstopatologico.getDataEsito())%>" id="dataEsito" />&nbsp;
			<a href="#"
				onClick="cal19.select(document.forms[0].dataEsito,'anchor19','dd/MM/yyyy'); return false;"
				NAME="anchor19" ID="anchor19"> <img
				src="images/icons/stock_form-date-field-16.gif" border="0"
				align="absmiddle"></a> <a style="cursor: pointer;"
				onclick="svuotaData(document.forms[0].dataEsito);"><img
				src="images/delete.gif" align="absmiddle" /></a> <font color="red">*</font>
</div>
<br>
<div>
			Codice Sigla: <input type="text" name="codiceSiglaInterno" id="codiceSiglaInterno" value="" />
</div>

<br>
<div>
			Descrizione esito:<br> <textarea name="descrizioneEsito" id="descrizioneEsito" cols="20" rows="10" ></textarea>
</div>


</br></br>

<div>
			Esito: <%=lookup_esame_istopatologico_tipo_diagnosi.getHtmlSelect("idEsito", -1) %>
</div>
<br>
<div id="specificheEsito" style="visibility:hidden;" >
		Seleziona classificazione: <%=lookup_associazione_classificazione_tabella_lookup.getHtmlSelect("idClassificazione", -1) %>
</div>


<br></br>
<div id="classificazione">

</div>

<input type="hidden" name="idIstopatologico" id="idIstopatologico" value="<%=richiestaIstopatologico.getId()%>">
<input type="submit" value="Salva Esito" onclick="">

</form>

<script>
      window.onunload = refreshParent;
    function refreshParent() {
        window.opener.location.href='MacellazioniNew.do?command=List&orgId='+<%=richiestaIstopatologico.getCapo().getId_macello()%>+'&tipo=1';
    }  
</script>