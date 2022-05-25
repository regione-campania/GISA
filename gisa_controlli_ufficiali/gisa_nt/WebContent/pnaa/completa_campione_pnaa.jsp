<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="org.aspcfs.modules.campioni.base.Pnaa"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.aspcfs.utils.web.*"%>
<%@ page import="java.awt.*, java.awt.image.*, javax.imageio.ImageIO, java.util.StringTokenizer, com.itextpdf.text.pdf.*, java.io.*, java.util.*,org.aspcfs.utils.web.*, com.itextpdf.text.pdf.codec.*,org.aspcfs.modules.campioni.base.SpecieAnimali" %>



<script>

function checkForm()
{
	var ret = true;
	message = "";

  
  var valdpa = "";
	for (var b=0;  b< document.myform.dpa.length; b++)
	{
	   if (document.myform.dpa[b].checked)
	   {
		   valdpa = document.myform.dpa[b].value;
	   }
	}
	
	if(valdpa == "")	
	{
		message += "- Il campo \"Tipo di ALIMENTO\" e\' obbligatorio.\r\n";
		ret = false;
	}
	
	var vala2 = "";
	for (var b=0;  b< document.myform.a2.length; b++)
	{
	   if (document.myform.a2[b].checked)
	   {
	      	vala2 = document.myform.a2[b].value;
	   }
	}
	
	if(vala2 == "")	
	{
		message += "- Il campo \"Metodo di campionamento (A2)\" e\' obbligatorio.\r\n";
		ret = false;
	}	
	
	
	var vala5 = "";
	for (var r=0;  r< document.myform.a5.length; r++)
	{
	   if (document.myform.a5[r].checked)
	   {
	      	vala5 = document.myform.a5[r].value;
	   }
	}
	
	if(document.getElementById('a4').value == "")	
	{
		message += "- Il campo \"Prelevatore (Nome e Cognome) (A4)\" e\' obbligatorio.\r\n";
		document.myform.a4.readOnly = false;
		document.myform.a4.className = "editField"; 
		ret = false;
	}	
	
	if(vala5 == "")	
	{
		message += "- Il campo \"Luogo del prelievo (A5)\" e\' obbligatorio.\r\n";
		ret = false;
	}	
	
	

	if( document.getElementById('a6').value == '' )		
	{
		message += "- Il campo \"Codice identificativo luogo di prelievo (A6)\" e\' obbligatorio.\r\n";
		document.myform.a6.readOnly = false;
		document.myform.a6.className = "editField"; 
		ret = false;
	}
	
	if( document.getElementById('a8').value == '' )		
	{
		message += "- Il campo \"Indirizzo del luogo di prelievo (A8)\" e\' obbligatorio.\r\n";
		document.myform.a8.readOnly = false;
		document.myform.a8.className = "editField"; 
		ret = false;
	}
	
	if( document.getElementById('a9').value == '' )		
	{
		message += "- Il campo \"Comune (A9)\"  e\' obbligatorio.\r\n";
		document.myform.a9.readOnly = false;
		document.myform.a9.className = "editField"; 
		ret = false;
	}
	
	if( document.getElementById('a10').value == '' )		
	{
		message += "- Il campo \"Provincia (A10)\"  e\' obbligatorio.\r\n";
		document.myform.a10.readOnly = false;
		document.myform.a10.className = "editField"; 
		ret = false;
	}
	
	if( document.getElementById('a11_1').value == '' )		
	{
		message += "- Il campo \"Latitudine (A11)\"  e\' obbligatoria\r\n";
		document.myform.a11_1.readOnly = false;
		document.myform.a11_1.className = "editField"; 
		ret = false;
	}
	else {
		
		//Latitudine inserita...
		if ( (document.getElementById('a11_1').value < 39.988475) || (document.getElementById('a11_1').value > 41.503754)){
	        message += "- Valore errato per il campo Latitudine! Il valore deve essere compreso tra 39.988475 e 41.503754\r\n";
	    	document.myform.a11_1.readOnly = false;
			document.myform.a11_1.className = "editField"; 
	        ret = false;
	    }		 
	
	}

	if( document.getElementById('a11_2').value == '' )		
	{
		message += "- Il campo \"Longitudine (A11)\"  e\' obbligatoria\r\n";
		document.myform.a11_2.readOnly = false;
		document.myform.a11_2.className = "editField"; 
		ret = false;
	}
	
	else {

		if ( (document.getElementById('a11_2').value < 13.7563172) || (document.getElementById('a11_2').value > 15.8032837 ) ){
		   message += "- Valore errato per il campo Longitudine, il valore deve essere compreso tra 13.7563172 e 15.8032837 \r\n";
		   document.myform.a11_2.readOnly = false;
		   document.myform.a11_2.className = "editField"; 
		   ret = false;
	     }		 
		
	}
	
	if( document.getElementById('a12').value == '' )		
	{
		message += "- Il campo \"Ragione sociale (A12)\"  e\' obbligatorio.\r\n";
		document.myform.a12.readOnly = false;
		document.myform.a12.className = "editField"; 
		ret = false;
	}
	
	if( document.getElementById('a12b').value == '' )		
	{
		message += "- Il campo \"Ragione sociale/Proprietario animali (A12.b.)\"  e\' obbligatorio.\r\n";
		document.myform.a12b.readOnly = false;
		document.myform.a12b.className = "editField"; 
		ret = false;
	}
	
	if( document.getElementById('a13').value == '' )		
	{
		message += "- Il campo \"Rappresentante legale (A13)\" e\' obbligatorio.\r\n";
		document.myform.a13.readOnly = false;
		document.myform.a13.className = "editField"; 
		ret = false;
	}

	if( document.getElementById('a14').value == '' )		
	{
		message += "- Il campo \"Codice fiscale (A14)\" e\' obbligatorio.\r\n";
		document.myform.a14.readOnly = false;
		document.myform.a14.className = "editField"; 
		ret = false;
	}
	
	if( document.getElementById('a15').value == '' )		
	{
		message += "- Il campo \"Detentore (A15)\" e\' obbligatorio.\r\n";
		document.myform.a15.readOnly = false;
		document.myform.a15.className = "editField"; 
		ret = false;
	}
	
	if( document.getElementById('a15b').value == '' )		
	{
		message += "- Il campo \"C.F. Ragione sociale/C.F. Detentore (A15.b)\" e\' obbligatorio.\r\n";
		document.myform.a15b.readOnly = false;
		document.myform.a15b.className = "editField"; 
		ret = false;
	}
	
	if( document.getElementById('a16').value == '' )		
	{
		message += "- Il campo \"Telefono (A16)\" e\' obbligatorio.\r\n";
		document.myform.a16.readOnly = false;
		document.myform.a16.className = "editField"; 
		ret = false;
	}

	var valb1 = "";
	for (var p=0;  p< document.myform.b1.length; p++)
	{
	   if (document.myform.b1[p].checked)
	   {
	      	valb1 = document.myform.b1[p].value;
	    }
	}
	
	if(valb1 == "")	
	{
		message += "- Il campo \"Matrice (B1)\" e\' obbligatorio.\r\n";
		ret = false;
	}	
	
	if(valb1 == "m1" && document.getElementById('materia_prima').value == '')	
	{
		message += "- Il campo riservato alla specifica della materia prima e\' obbligatorio.\r\n";
		ret = false;
	}
	
	
	if( document.getElementById('b2').value == '' )		
	{
		message += "- Il campo \"Trattamento al mangime (B2)\" e\' obbligatorio.\r\n";
		//document.myform.a7.readOnly = false;
		//document.myform.a7.className = "editNoBottom"; 
		ret = false;
	}
	
	if( document.getElementById('b4').value == '' )		
	{
		message += "- Il campo \"Ragione sociale ditta produttrice (B4)\" e\' obbligatorio.\r\n";
		//document.myform.a7.readOnly = false;
		//document.myform.a7.className = "editNoBottom"; 
		ret = false;
	}
	
	if( document.getElementById('b5').value == '' )		
	{
		message += "- Il campo \"Indirizzo ditta produttrice (B5)\" e\' obbligatorio.\r\n";
		//document.myform.a7.readOnly = false;
		//document.myform.a7.className = "editNoBottom"; 
		ret = false;
	}
	
	if( document.getElementById('b8').value == '' )		
	{
		message += "- Il campo \"Nome commerciale del mangime (B8)\" e\' obbligatorio.\r\n";
		//document.myform.a7.readOnly = false;
		//document.myform.a7.className = "editNoBottom"; 
		ret = false;
	}

	if( document.getElementById('b12').value == '' )		
	{
		message += "- Il campo \"Paese di produzione (B12)\" e\' obbligatorio.\r\n";
		//document.myform.a7.readOnly = false;
		//document.myform.a7.className = "editNoBottom"; 
		ret = false;
	}
	
	if( document.getElementById('b14').value == '' )		
	{
		message += "- Il campo \"Data di scadenza (B14)\" e\' obbligatorio.\r\n";
		//document.myform.a7.readOnly = false;
		//document.myform.a7.className = "editNoBottom"; 
		ret = false;
	}
	
	
	if( document.getElementById('b16').value == '' )		
	{
		message += "- Il campo \"Dimensione del lotto (B16)\" e\' obbligatorio.\r\n";
		//document.myform.a7.readOnly = false;
		//document.myform.a7.className = "editNoBottom"; 
		ret = false;
	}

	if( document.getElementById('b17').value == '' )		
	{
		message += "- Il campo \"Ingredienti (B17)\" e\' obbligatorio.\r\n";
		//document.myform.a7.readOnly = false;
		//document.myform.a7.className = "editNoBottom"; 
		ret = false;
	}
	
	if (!ret) {
		alert(message);
	}else {
	     
		loadModalWindow();
	    return true;
	}

	return ret;
	
}


</script>

<form method="post" name="myform" action="CampioniReport.do?command=InsertSchedaPnaa&idCampione=<%--=PnaaDetails.getIdCampione()--%>&orgId=<%--=PnaaDetails.getOrgId()--%>&url=<%--=PnaaDetails.getUrl()--%>">

<table width="50%" >
	 <col width="50%">
	<col width="50%"> 
<tr>
<td>
<b>ENTE DI APPARTENENZA</b></td>

<td> <input class="editField" type="text" name="asl" id="asl" size="30" value="<%--= (definitivoDocumentale!=null && definitivoDocumentale.equals("true")) ? valoriScelti.get(z) : PnaaDetails.getAslCampione() --%>"/>
</td> </tr>
<tr>
<td>
<b>UNITA' TERRITORIALE-DISTRETTO</b></td><td> <input class="editField" type="text" size="30" name="distretto" id="distretto" value="<%--=valoriScelti.get(z++) --%>" />
</td></tr>
<tr>
<td colspan="2">
<b>
campioni di ALIMENTO (*):</b> <br/>
<input type="radio" id="dpa" name="dpa" value="001" <%--= (PnaaDetails.getAlimenti().equalsIgnoreCase("001")) ? ("checked=\"checked\"") : ("")  --%>/> per ANIMALI NON DESTINATI alla produzione di alimenti (non DPA)<br/>
<input type="radio" id="dpa" name="dpa" value="002" <%--= (PnaaDetails.getAlimenti().equalsIgnoreCase("002")) ? ("checked=\"checked\"") : ("")  --%> /> per ANIMALI DESTINATI alla produzione di alimenti (DPA)<br/>
</td></tr></table>

<!-- PARTE A -->
<h2>A.PARTE GENERALE</h2>
<table width="50%" >
	 <col width="50%">
	<col width="50%"> 

<tr class="colorcell">
  <td colspan="4" style="border:1px solid black;"><b>A2. Metodo di campionamento (*):</b></td>
</tr>
<tr>
<td><input type="radio"   name="a2" id="a2" value="001" <%--=(PnaaDetails.getA2() != null && PnaaDetails.getA2().equalsIgnoreCase("001")) ? ("checked=\"checked\"") : ("") --%> />Individuale/singolo</td>
 <td><input type="radio"   name="a2" id="a2" value="020" <%--=(PnaaDetails.getA2() != null && PnaaDetails.getA2().equalsIgnoreCase("020")) ? ("checked=\"checked\"") : ("") --%> />Norma di riferimento (solo se trattasi di una norma UE):<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; IN ACCORDO AL REG 152/2009
</tr>
<tr><td><input type="radio"   name="a2" id="a2" value="003"  <%--=(PnaaDetails.getA2() != null && PnaaDetails.getA2().equalsIgnoreCase("003")) ? ("checked=\"checked\"") : ("") --%> />Sconosciuto</td>
<td><input type="radio"   name="a2" id="a2" value="011"  <%--=(PnaaDetails.getA2() != null && PnaaDetails.getA2().equalsIgnoreCase("011")) ? ("checked=\"checked\"") : ("") --%> />Altro metodo di campionamento</td>
</tr>
</table>
<table width="50%" >
	 <col width="50%">
	<col width="50%"> 
 

<tr class="colorcell">
  <td colspan="4" style="border:1px solid black;"><b>A5. Luogo di prelievo (*):</b></td>
</tr>
<tr>
 <tr>
 	<td><input type="radio"   name="a5" id="a5" value="67" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("67")) ? ("checked=\"checked\"") : ("") --%>> Stabilimenti di miscelazione grassi</td>
 	<td><input type="radio"   name="a5" id="a5" value="22" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("22")) ? ("checked=\"checked\"") : ("") --%>> Stabilimento di produzione oli e grassi animali </td>
 </tr>
 <tr>
 	<td><input type="radio"   name="a5" id="a5" value="50" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("50")) ? ("checked=\"checked\"") : ("") --%>> Stabilimento di mangimi composti</td>
 	<td><input type="radio"   name="a5" id="a5" value="52" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("52")) ? ("checked=\"checked\"") : ("") --%>> Stabilimento di mangimi composti per animali da compagnia</td>
 </tr>
 <tr>
 	<td><input type="radio"   name="a5" id="a5" value="42" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("42")) ? ("checked=\"checked\"") : ("") --%>> Mezzo di trasporto su rotaia </td>
    <td><input type="radio"   name="a5" id="a5" value="44" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("44")) ? ("checked=\"checked\"") : ("") --%>> Mezzo di trasporto aereo </td>
 </tr>
 <tr>
    <td><input type="radio"   name="a5" id="a5" value="41" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("41")) ? ("checked=\"checked\"") : ("") --%>> Mezzo di trasporto su strada </td>
    <td><input type="radio"   name="a5" id="a5" value="43" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("43")) ? ("checked=\"checked\"") : ("") --%>> Mezzo di trasporto su acqua </td>
 </tr>
 <tr>
  <td><input type="radio"   name="a5" id="a5" value="49" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("49")) ? ("checked=\"checked\"") : ("") --%>> Deposito/Magazzinaggio </td>
  <td><input type="radio"   name="a5" id="a5" value="55" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("55")) ? ("checked=\"checked\"") : ("") --%>> Impianto che produce grassi vegetali per l'alimentazione animale </td>
 </tr>
 <tr>
 <td><input type="radio"   name="a5" id="a5" value="56" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("56")) ? ("checked=\"checked\"") : ("") --%>> Impianto oleochimico che produce materie prime per l'alimentazione animale </td>
 <td><input type="radio"   name="a5" id="a5" value="47" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("47")) ? ("checked=\"checked\"") : ("") --%>> Vendita al dettaglio </td> 
 </tr>
 <tr>
 <td><input type="radio"   name="a5" id="a5" value="51" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("51")) ? ("checked=\"checked\"") : ("") --%>> Stabilimento di produzione di additivi/premiscele</td>
 <td><input type="radio"   name="a5" id="a5" value="68" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("68")) ? ("checked=\"checked\"") : ("") --%>> Stabilimento per la produzione di BIODIESEL</td>
 </tr>
 <tr>  
 <td><input type="radio"   name="a5" id="a5" value="48" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("48")) ? ("checked=\"checked\"") : ("") --%>> Mulino per la produzione di mangimi semplici</td>
 <td><input type="radio"   name="a5" id="a5" value="53" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("53")) ? ("checked=\"checked\"") : ("") --%>> Vendita all'ingrosso/intermediario di mangimi </td>
 </tr>
 <tr>
    <td><input type="radio"   name="a5" id="a5" value="2" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("2")) ? ("checked=\"checked\"") : ("") --%>> Azienda agricola</td>
    <td><input type="radio"   name="a5" id="a5" value="57" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("57")) ? ("checked=\"checked\"") : ("") --%>> Azienda zootecnica con ruminanti</td>
 </tr>
 <tr>
 	<td><input type="radio"   name="a5" id="a5" value="58" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("58")) ? ("checked=\"checked\"") : ("") --%>> Azienda zootecnica che non detiene ruminanti</td>
    <td><input type="radio"   name="a5" id="a5" value="59" <%--=(PnaaDetails.getA5() != null && PnaaDetails.getA5().equalsIgnoreCase("59")) ? ("checked=\"checked\"") : ("") --%>> Attività di importazione (Primo deposito di materie prime importate) </td>
 </tr>
<tr class="colorcell">
  <td colspan="1" style="border:1px solid black;"><b>A6. Codice identificativo luogo di prelievo (*):</b>&nbsp;&nbsp;&nbsp;</td>

</tr>
<tr>
  <td><input class="editField" type="text"  name="a6" id="a6" size="30" value="<%--= (definitivoDocumentale!=null && definitivoDocumentale.equals("true")) ? valoriScelti.get(z) : PnaaDetails.getA6() --%>"/></td>
  <%--z++; --%>
 
</tr>

</table>


<%-- ArrayList<SpecieAnimali> listaSpeciePnaa =  PnaaDetails.getListaSpecieAnimali();
   ArrayList<String> listaProdottiPnaa =  PnaaDetails.getListaProdottiPnaa();
--%>
<!-- PARTE B-->
<h2>B. INFORMAZIONI SUL CAMPIONE PRELEVATO</h2>
<table width="50%" >
<col width="50%">
	<col width="50%"> 

  

<tr class="colorcell">
  <td colspan="1" style="border:1px solid black;"><b>B2. Trattamento applicato al mangime prelevato (*):</b></td>
 </tr>
<tr>
      <td><input class="editField" type="text" size="30" name="b2" id="b2" value="<%--= (definitivoDocumentale!=null && definitivoDocumentale.equals("true")) ? valoriScelti.get(z) : PnaaDetails.getB2() --%>" /></td>
    </tr>

<tr class="colorcell">
  <td colspan="1" style="border:1px solid black;"><b>B4. Ragione sociale ditta produttrice (*):</b></td>
  <td colspan="3" style="border:1px solid black;"><b>B5. Indirizzo ditta produttrice (*):</b></td>
</tr>
<tr>
 <td><input class="editField" type="text" size="30" name="b4" id="b4" value="<%--= (definitivoDocumentale!=null && definitivoDocumentale.equals("true")) ? valoriScelti.get(z) : PnaaDetails.getB4() --%>"/></td>
 <%--z++; --%>
 <td><input class="editField" type="text" size="30" name="b5" id="b5" value="<%--= (definitivoDocumentale!=null && definitivoDocumentale.equals("true")) ? valoriScelti.get(z) : PnaaDetails.getB5() --%>"/></td>
 <%--z++; --%>
</tr>
</table>
 
 
<table width="50%" >
<col width="50%">
	<col width="50%"> 
<tr class="colorcell">
 
  <td colspan="3" style="border:1px solid black;"><b>B8. Nome commerciale del mangime (*):</b></td>
</tr>
<tr>  
<td><input class="editField" type="text" id="b8" name="b8" size="30" value="<%--= (definitivoDocumentale!=null && definitivoDocumentale.equals("true")) ? valoriScelti.get(z) : PnaaDetails.getB8() --%>"></td></tr>
<%--z++; --%>
<tr>  <td><input type="radio"  id="b7" name="b7" value="1" <%--=(PnaaDetails.getB7() != null && PnaaDetails.getB7().equalsIgnoreCase("convenzionale")) ? ("checked=\"checked\"") : ("") --%> >Convezionale</td></tr>
 <tr> <td><input type="radio"  id="b7" name="b7" value="2" <%--=(PnaaDetails.getB7() != null && PnaaDetails.getB7().equalsIgnoreCase("sconosciuto")) ? ("checked=\"checked\"") : ("") --%> >Sconosciuto (no per OGM)</td></tr>

 <td colspan="8">
 <table>

	
</td>
</table>


<tr class="colorcell">
  <td style="border:1px solid black;"><b>B12. Paese di produzione (*):</b></td>
   <td style="border:1px solid black;"><b>B14. Data di scadenza (*):</b></td>
</tr>
<tr>
  <td><input class="editField" type="text" size="30" name="b12" id="b12" value="<%--=  (definitivoDocumentale!=null && definitivoDocumentale.equals("true")) ? valoriScelti.get(z) : PnaaDetails.getB12() --%>" /></td>
  <%--z++; --%>
   <td><input class="editField" type="text" size="30" name="b14" id="b14" value="<%--=  (definitivoDocumentale!=null && definitivoDocumentale.equals("true")) ? valoriScelti.get(z) : PnaaDetails.getB14() --%>"/></td>
  <%--z++; --%> 
</tr>
<tr class="colorcell">
 
  <td colspan="3" style="border:1px solid black;"><b>B16. Dimensione di lotto (*):</b></td>
</tr>
<tr>
  
   <td><input class="editField" type="text" size=30" name="b16" id="b16" value="<%--=  (definitivoDocumentale!=null && definitivoDocumentale.equals("true")) ? valoriScelti.get(z) : PnaaDetails.getB16() --%>"/></td>
   <%--z++; --%> 
</tr>
<tr class="colorcell">
  <td colspan="1" style="border:1px solid black;"><b>B17. Ingredienti (*):</b></td>
 
</tr>
<tr>
  <td><input class="editField" type="text" size="30" name="b17" id="b17" value="<%--=  (definitivoDocumentale!=null && definitivoDocumentale.equals("true")) ? valoriScelti.get(z) :  PnaaDetails.getB17() --%>"/></td>
  <%--z++; --%>
 
</tr>
</table>

<br/>


<input  type="submit" name="salva" id="sId" class = "buttonClass" onclick="javascript:this.form.tipoAzione.value='salva';if( confirm('Attenzione! Controlla bene tutti i dati inseriti in quanto alla chiusura della finestra, i dati contrassegnati in giallo saranno persi.\nVuoi effettuare il salvataggio della scheda?')){ return checkForm();} else return false;" value="Salva e completa"/>
<input type="hidden" id="tipoAzione" name="tipoAzione" value="" />

</form>


