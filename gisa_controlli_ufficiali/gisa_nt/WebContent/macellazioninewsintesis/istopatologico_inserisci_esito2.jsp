<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>


<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>

<%@page import="javax.imageio.ImageIO"%>
<%@ page import="java.awt.*, java.awt.image.*,  java.util.StringTokenizer, com.itextpdf.text.pdf.*, java.io.*, java.util.*,org.aspcfs.utils.web.*, com.itextpdf.text.pdf.codec.*,
org.aspcfs.modules.campioni.base.SpecieAnimali" %>
<%@page import="org.aspcfs.modules.macellazioninewsintesis.base.Partita"%>
<%@page import="org.aspcfs.modules.macellazioninewsintesis.base.Organi"%>


<jsp:useBean id="Organi" class="org.aspcfs.utils.web.LookupList"
	scope="request"/>


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
%>





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
	class="org.aspcfs.modules.sintesis.base.SintesisStabilimento"
	scope="request" />
<jsp:useBean id="Capo" class="org.aspcfs.modules.macellazioninewsintesis.base.GenericBean"
	scope="request" />
<jsp:useBean id="richiestaIstopatologico"
	class="org.aspcfs.modules.macellazioninewsintesis.base.RichiestaIstopatologico"
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

<script type="text/javascript">


var idCorrente = -1;

function caricaClassificazioneLesione (select)


{
	var a = select.id;	
	//alert(a);
	idCorrente = a.substring(18);
	
	//alert('idCorrente' +idCorrente);
	
		PopolaCombo.getHtmlSelectByIdLookup(select.value,'lookup_associazione_classificazione_tabella_lookup', idCorrente, classificazioneCallBack);
}

function classificazioneCallBack(value)
{

	var idClassificazione = 'classificazione_'+idCorrente;
//	alert (idClassificazione);

	$("#"+idClassificazione).html(value);


	

	
}



function specificaEsito(select)
{
	
	var a = select.id;	
	idCorrente = a.substring(8);
	
	var id = 'specificheEsito' +'_'+ idCorrente;
    //alert(id);
if (select.value == 1)

	$("#"+id).css("visibility", "visible");
else

	$("#"+id).css("visibility", "hidden");
	

	
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
    	//alert('true');
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
Marca auricolare: <%=((Partita)richiestaIstopatologico.getCapo()).getCd_partita() %>

</div>
<br>
<form id="main"  name="main" action="MacellazioniNewSintesis.do?command=SaveEsitoRichiestaIstopatologico&auto-populate=true<%=addLinkParams(request, "popup|popupType|actionId")%>"
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
<table>
<%ArrayList<Organi> lista_organi_richiesta =(ArrayList<Organi>) richiestaIstopatologico.getLista_organi_richiesta();
Iterator i = lista_organi_richiesta.iterator();
while (i.hasNext()){
Organi thisOrgano = (Organi) i.next();
%>
<tr><td><%=Organi.getSelectedValue(thisOrgano.getLcso_organo())%> <br> <img src="<%=createBarcodeImage(thisOrgano.getIdentificativo_campione_organo())%>" /> </td></tr><tr> <td>
<div>
Esito: <%=lookup_esame_istopatologico_tipo_diagnosi.getHtmlSelect("idEsito_"+thisOrgano.getId(), -1) %>
</div></td>
<br><td>
<div id="specificheEsito_<%=thisOrgano.getId() %>" style="visibility:hidden;" >
Seleziona classificazione: <%=lookup_associazione_classificazione_tabella_lookup.getHtmlSelect("idClassificazione_"+thisOrgano.getId(), -1) %>
</div>
</td>
<td>
<div id="classificazione_<%=thisOrgano.getId() %>">
</div>
</td>
</tr>
<%} %>
</table>
<input type="hidden" name="idIstopatologico" id="idIstopatologico" value="<%=richiestaIstopatologico.getId()%>">
<input type="submit" value="Salva Esito" onclick="">

</form>

<script>
      window.onunload = refreshParent;
    function refreshParent() {
        window.opener.location.href='MacellazioniNewSintesis.do?command=List&altId='+<%=richiestaIstopatologico.getCapo().getId_macello()%>+'&tipo=2';
    }  
</script>