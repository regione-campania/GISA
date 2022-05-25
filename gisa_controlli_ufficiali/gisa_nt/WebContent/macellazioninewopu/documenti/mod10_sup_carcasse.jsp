<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<jsp:useBean id="OrgTamponeMacelli" class="org.aspcf.modules.controlliufficiali.base.ModTamponiMAcellazione" scope="request"/>
<jsp:useBean id="OrgOperatore" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="OrgUtente" class="org.aspcf.modules.controlliufficiali.base.OrganizationUtente" scope="request"/>
<jsp:useBean id="valoriScelti" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="definitivo" class="java.lang.String" scope="request"/>
<jsp:useBean id="definitivoDocumentale" class="java.lang.String" scope="request"/>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
<%@ page import="javax.imageio.ImageIO"%>
<%@ page import="java.awt.*, java.awt.image.*,  java.util.StringTokenizer, com.itextpdf.text.pdf.*, java.io.*, java.util.*,org.aspcfs.utils.web.*, com.itextpdf.text.pdf.codec.*,org.aspcfs.modules.campioni.base.SpecieAnimali" %>


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
	g2d.drawString(s,h+2,34);
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
<%int z = 0; %>

<!-- INIT DOCUMENTALE -->
	<%@ include file="/gestione_documenti/initDocumentale.jsp" %>
<!-- FINE INIT DOCUMENTALE -->

<!-- INIZIO HEADER -->
	<%@ include file="/macellazioni/documenti/header.jsp" %>
<!-- FINE HEADER -->

<div class="boxOrigineDocumento"><%@ include file="../../../hostName.jsp" %></div>

<table><tr>
<TD>
<div id="idbutn" style="display:block;">
<%-- <input type="button" class = "buttonClass" value ="Salva in modalità definitiva" onclick="this.form.bozza.value='false';javascript:checkSubmit();"/>
--%>
</div>
<input id="stampaId" type="button" class = "buttonClass" value ="Stampa" onclick="javascript:if( confirm('Attenzione! Controlla bene tutti i dati inseriti in quanto alla chiusura della finestra, i dati saranno persi.\nVuoi effettuare la stampa?')){return window.print();}else return false;"/>
<input type="hidden" id = "bozza" name = "bozza" value="">
<dhv:permission name="server_documentale-view">
<%if (definitivoDocumentale!=null && definitivoDocumentale.equals("true")){ %>
<!--  BOX DOCUMENTALE -->
	  <jsp:include page="../../gestione_documenti/boxDocumentale.jsp">
    <jsp:param name="altId" value="<%=request.getParameter("altId") %>" />
     <jsp:param name="ticketId" value="0" />
      <jsp:param name="tipo" value="<%=request.getParameter("tipo") %>" />
       <jsp:param name="idCU" value="<%=request.getParameter("altId") %>" />
        <jsp:param name="url" value="<%=request.getParameter("url") %>" />
</jsp:include>
<!--  BOX DOCUMENTALE -->
<% } else {%>
<form method="post" name="form2" action="PrintModulesHTML.do?command=ViewModello10Macelli">
<input id="salvaId" type="button" class = "buttonClass" value ="Genera PDF" onclick="if (confirm ('Nella prossima schermata sarà possibile recuperare l\'ultimo PDF generato a partire dal documento a schermo, \n oppure generarne uno nuovo.')){javascript:salva(this.form)}"/>
<input type="hidden" id="documentale" name ="documentale" value="ok"></input>
<input type="hidden" id="listavalori" name ="listavalori" value=""></input>
 <input type="hidden" id ="altId" name ="altId" value="<%=request.getParameter("altId") %>" />
  <input type="hidden" id ="ticketId" name ="ticketId" value="0" />
   <input type="hidden" id ="comboDateMacellazione" name ="comboDateMacellazione" value="<%=request.getParameter("comboDateMacellazione") %>" />
   <input type="hidden" id ="tipo" name ="tipo" value="<%=request.getParameter("tipo") %>" />
    <input type="hidden" id ="idCU" name ="idCU" value="<%=request.getParameter("altId") %>" />
      <input type="hidden" id ="url" name ="url" value="<%=request.getParameter("url") %>" />
</form>
<% } %>
</dhv:permission>
</TD>
</TABLE>
L'anno <input class="layout" type="text" readonly size="4" value="<%= ( OrgOperatore.getAnnoReferto() != null ) ? OrgOperatore.getAnnoReferto() : "" %>"/>
addì <input class="layout" type="text" readonly size="2" value="<%= (OrgOperatore.getGiornoReferto() != null ) ? OrgOperatore.getGiornoReferto() : ""%>"/>
del mese di <input class="layout" type="text" readonly size="10" value="<%= (OrgOperatore.getMeseReferto() != null ) ? OrgOperatore.getMeseReferto().toUpperCase() : ""%>"/> 
alle ore <input class="editField" type="text" name="ore" id="ore" size="2" maxlength="5" value="<%=valoriScelti.get(z++) %>"/>
i sottoscritti <input class="layout" type="text" readonly size="50" value="<%= (OrgOperatore.getComponente_nucleo() != null) ? OrgOperatore.getComponente_nucleo() : "" %>"/> 
<input class="layout" type="text" readonly size="50" value="<%= (OrgOperatore.getComponente_nucleo_due() != null) ? OrgOperatore.getComponente_nucleo_due() : "" %>"/>  
<br>qualificandosi, si sono presentati presso:<br>
<%-- <% System.out.println("Nome: "+OrgOperatore.getRagione_sociale()); %> --%>

<U><b>Stabilimento/azienda/altro</b>(luogo del controllo):</U> 
Comune di <input class="layout" type="text" readonly size="20" value="<%= (OrgOperatore.getComune() != null ) ? OrgOperatore.getComune().toUpperCase() : "" %>"/>
alla via <input class="layout" type="text" readonly size="80" value="<%= (OrgOperatore.getIndirizzo() != null ) ? OrgOperatore.getIndirizzo().toUpperCase() : "" %>"/>
 ric.CE n° <input class="layout" type="text" readonly size="20" value="<%= (OrgOperatore.getApproval_number() != null && OrgOperatore.getApproval_number() != "") ? OrgOperatore.getApproval_number() : ""%>"/> <br>
regist./cod.az./targa/n.seriale <input class="layout" type="text" readonly size="20" value ="<%= (OrgOperatore.getN_reg() != null) ? OrgOperatore.getN_reg() : "" %>"/>
 linea di attività ispezionata <input class="layout" type="text" readonly size="30" value="<%= (OrgOperatore.getTipologia_att() != null ) ? OrgOperatore.getTipologia_att() : ""%>"/>.<br>

<U><b>Nome/ditta/Ragione/Denominazione sociale: </b></U>
<input class="layout" type="text" readonly name="ragione_sociale" id="ragione_sociale" size="50" value="<%= (OrgOperatore.getRagione_sociale() != null) ? OrgOperatore.getRagione_sociale().toUpperCase() : ""%>"/> 
sede legale in <input class="layout" type="text" readonly size="30" value="<%= ((String )OrgOperatore.getSede_legale()) != null ? OrgOperatore.getSede_legale() : ""%>"/>
alla via <input class="layout" type="text" readonly size="80" value="<%= ((String )OrgOperatore.getIndirizzo_legale()) != null ? OrgOperatore.getIndirizzo_legale().toUpperCase() : "" %>"/>  
PI/CF <input class="layout" type="text" readonly size="20" value="<%= (OrgOperatore.getCodice_fiscale() != null ) ? OrgOperatore.getCodice_fiscale() : "" %>"/> legale rappr. sig. 
<input class="layout" type="text" readonly size="50" value="<%= ((String )OrgOperatore.getLegale_rapp()) !=null ? OrgOperatore.getLegale_rapp().toUpperCase() : "" %>"/> 
nato a <input class="layout" type="text" readonly size="20" value="<%= ((String )OrgOperatore.getLuogo_nascita_rappresentante()) != null ? (String)OrgOperatore.getLuogo_nascita_rappresentante().toUpperCase() :  "" %>">
 il 
<input class="layout" type="text" readonly size="4" value="<%= (OrgOperatore.getGiornoNascita() != null) ? OrgOperatore.getGiornoNascita() : ""%>"/>
/<input class="layout" type="text" readonly size="4" value="<%= (OrgOperatore.getMeseNascita() != null) ? OrgOperatore.getMeseNascita().toUpperCase() : ""%>"/>
/<input class="layout" type="text" readonly size="8" value="<%= (OrgOperatore.getAnnoNascita() != null) ? OrgOperatore.getAnnoNascita() : "" %>"/>
 e residente in <input class="editField" type="text" name="luogo_residenza" id="luogo_residenza"   value="<%=valoriScelti.get(z++) %>"  size="30" maxlength="" /> 
 alla via <input class="editField" type="text"  name="indirizzo_legale_rappresentante" id="indirizzo_legale_rappresentante" size="80" maxlength=""   value="<%=valoriScelti.get(z++) %>"  /> 
 n° <input class="editField" type="text"  name="num_civico_rappresentante" id="num_civico_rappresentante"   value="<%=valoriScelti.get(z++) %>"  size="3" maxlength="6"/> 
 domicilio digitale <input class="editField" type="text" name="domicilio_digitale" id="domicilio_digitale" size="30" maxlength=""   value="<%=valoriScelti.get(z++) %>"  /> <br>

<U><b>Presente all'ispezione:</b></U> sig. <input class="editField" type="text" name="nome_presente_ispezione" id="nome_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="30" maxlength=""/> 
nato a <input class="editField" type="text"  name="luogo_nascita_presente_ispezione" id="luogo_nascita_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="30" maxlength=""/> 
il <input class="editField" type="text" name="giorno_presente_ispezione" id="giorno_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="4" maxlength="2"/>/
<input class="editField" type="text" name="mese_presente_ispezione" id="mese_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="4" maxlength="2"/>/
<input class="editField" type="text" name="anno_presente_ispezione" id="anno_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="8" maxlength="4"/> 
e residente in <input class="editField" type="text" name="luogo_residenza_presente_ispezione" id="luogo_residenza_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="20" maxlength=""/> 
alla via <input class="editField" type="text" name="via_ispezione" id="via_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="80" maxlength=""/>
n° <input class="editField" type="text"  name="num_civico_presente_ispezione" id="num_civico_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="3" maxlength="6"/> 
doc.ident. <input class="editField" type="text" name="doc_identita_presente_ispezione" id="doc_identita_presente_ispezione"   value="<%=valoriScelti.get(z++) %>"  size="50" maxlength="" />.<br>  
I sottoscritti hanno proceduto, in regime di asepsi, al prelievo di un campione per l'esame microbiologico delle superfici di carcasse come appresso specificato: <br>

<TABLE rules="all" border="1px solid;" style="border-collapse: collapse">
<tr>
	<td style="text-align:left;border:1px solid black;">
		<u>Carcasse</u> 
	</td>
	<td style="text-align:left; width:200px; height:100px;border:1px solid black;" colspan="6">
	<%
	if(OrgTamponeMacelli.getIdTipoCarcassa()== 4)
	{
	%>
		 <span class="checkedItem"> &nbsp;bovino<sup>*</sup></span>&nbsp;
	<%}
		else {%>
	 <span class="NocheckedItem"> &nbsp;bovino<sup>*</sup></span>&nbsp;
	 <%} %>
	 	 <span class="NocheckedItem"> &nbsp;ovino<sup>*</sup></span>&nbsp;
	 	 <%
	if(OrgTamponeMacelli.getIdTipoCarcassa()== 2)
	{
	%>
		 <span class="checkedItem"> &nbsp;caprino<sup>*</sup></span>&nbsp;
		<%}
		else {%>
	 <span class="NocheckedItem"> &nbsp;caprino<sup>*</sup></span>&nbsp;
	 <%} %>
	 <%
	if(OrgTamponeMacelli.getIdTipoCarcassa()== 3)
	{
	%>
			 <span class="checkedItem"> &nbsp;equino<sup>*</sup></span>&nbsp;
		<%}
		else {%>
	 <span class="NocheckedItem"> &nbsp;equino<sup>*</sup></span>&nbsp;
	 <%} %>
	 <%
	if(OrgTamponeMacelli.getIdTipoCarcassa()== 1)
	{
	%>
		 <span class="checkedItem"> &nbsp;suino<sup>*</sup></span>&nbsp;
	
	<%}
		else {%>
	 <span class="NocheckedItem"> &nbsp;suino<sup>*</sup></span>&nbsp;
	 <%} %>
	 <%
	if(OrgTamponeMacelli.getIdTipoCarcassa()== 6)
	{
	%>
		 <span class="checkedItem"> &nbsp;broiler<sup>**</sup></span>&nbsp;
		<%}
		else {%>
	 <span class="NocheckedItem"> &nbsp;broiler<sup>**</sup></span>&nbsp;
	 <%} %>
	 <%
	if(OrgTamponeMacelli.getIdTipoCarcassa()== 7)
	{
	%>
		 <span class="checkedItem"> &nbsp;tacchino<sup>**</sup></span>
		<%}
		else {%>
	 <span class="NocheckedItem"> &nbsp;tacchino<sup>**</sup></span>
	 <%} %>
	 <br>
	 *dopo la macellazione, ma prima del raffreddamento<br>
	 **dopo il raffreddamento
	</td>
</tr>
<tr>
	<td style="text-align:left;border:1px solid black;">
		<u>Ricerca</u> 
	</td>
	<td style="text-align:left;border:1px solid black;" colspan="5">
	<%
	if (OrgTamponeMacelli.contieneTipoRiceca(1))
	{
		%>
				<span class="checkedItem"> &nbsp;<b>(A)</b> colonie aerobiche (no carni avicole)</span>&nbsp;&nbsp;
		
		<%
	}
	else
	{
		%>
						<span class="NocheckedItem"> &nbsp;<b>(A)</b> colonie aerobiche (no carni avicole)</span>&nbsp;&nbsp;
		
		<%
	}
	%>
	<%
	if (OrgTamponeMacelli.contieneTipoRiceca(2))
	{
		%>
		<span class="checkedItem"> &nbsp;<b>(B)</b> enterobatteriacee (no carni avicole)</span>				
		
		<%
	}
	else
	{
		%>
		<span class="NocheckedItem"> &nbsp;<b>(B)</b> enterobatteriacee (no carni avicole)</span>				
		
		<%
	}
	%>
	</td>
	<td style="text-align:left;border:1px solid black;">
	<%
	if (OrgTamponeMacelli.contieneTipoRiceca(3))
	{
		%>
		<span class="checkedItem"> &nbsp;<b>(C)</b> salmonella spp.</span>					 
		
		<%
	}
	else
	{
		%>
		<span class="NocheckedItem"> &nbsp;<b>(C)</b> salmonella spp.</span>					 
		
		<%
	}
	%>
	</td>
</tr>
<tr>
	<td style="text-align:left;border:1px solid black;" rowspan="6">
		<u>Metodo</u>	
	</td>
	<td style="text-align:left;border:1px solid black;" rowspan="2">
	<%
	if (OrgTamponeMacelli.isMetodo()==false )
	{
	%>
		<span class="checkedItem"> &nbsp;<u>Non distruttivo</u></span><br>
		<%}
	else
	{
		%>
		<span class="NocheckedItem"> &nbsp;<u>Non distruttivo</u></span><br>
		<%} %>
		volume diluente <input class="editField" type="text" name="" id=""size="3" value="<%=valoriScelti.get(z++) %>">ml<br>
	</td>
	<td style="text-align:left;border:1px solid black;" colspan="5">
		<span class="NocheckedItem"> &nbsp;Per <b>(A) e (B): </b> n. 4 siti ognuno di <input class="editField" type="text" size="3"  value="<%=valoriScelti.get(z++) %>"  />
			di cm<sup>2</sup> (min.50 cm<sup>2</sup> piccoli ruminanti - min. 100 cm<sup>2</sup> 
			bovini-equini-suini.) x n. 5 carcasse (scelte a caso) utilizzando 
		</span> <span class="NocheckedItem"> &nbsp;SPUGNA</span> <span class="NocheckedItem"> &nbsp;GARZA</span>
		<span class="NocheckedItem"> &nbsp;TAMPONE</span>
	</td>
</tr>
<tr>
	<td  style="text-align:left;border:1px solid black;" colspan="5"> 
		<span class="NocheckedItem"> &nbsp;Per <b>(C)</b> n. 4 siti ognuno di <input class="editField" type="text" size="3" value="<%=valoriScelti.get(z++) %>"  />
			di cm<sup>2</sup> (min.100 cm<sup>2</sup>) x n. 5 carcasse (scelte a caso) con SPUGNA ABRASIVA (non applicabile a broiler e tacchini) 
		</span>
	</td>	
</tr>
<tr>
	<td style="text-align:left;border:1px solid black;" rowspan="3">
	<%
	if (OrgTamponeMacelli.isMetodo()==true )
	{
	%>
		<span class="checkedItem"> &nbsp;<u>Distruttivo</u></span>
		<%}
	else
	{
		%>
		<span class="NocheckedItem"> &nbsp;<u>Distruttivo</u></span>
		<%} %>
	</td>	
	<td style="text-align:left;border:1px solid black;" colspan="5">
		<span class="NocheckedItem"> &nbsp;Per <b>(A) e (B): </b> n. 4 pezzi di tessuto ( ognuno di 5 cm<sup>2</sup> spessi almeno 2mm) x n.5 carcasse (scelte a caso)  
	</td>
	
</tr>
<tr>
	<td  style="text-align:left;border:1px solid black;" colspan="5"> 
		<span class="NocheckedItem"> &nbsp;Per <b>(C)</b> nel broiler e tacchini: n.1 frammento di pelle di collo di almeno 10 gr. x n.15 carcasse (scelte a caso)
		</span>
	</td>	
	
</tr>
</table>
<%--P align="center"><b>PER RUMINANTI, EQUINI E SUINI: </b>IDENTIFICAZIONE DELLE CARCASSE PRESCELTE (I COLONNA) E DESCRIZIONE DEI SITI SCELTI PER IL METODO 
		NON DISTRUTTIVO O DAI QUALI SONO STATI PRELEVATI I CAMPIONI DI TESSUTO CON IL METODO DISTRUTTIVO
</P>		
<TABLE rules="all" cellpadding="5" style="border-collapse: collapse">
<%
int i= 0 ;
for (String matricola :OrgTamponeMacelli.getMatricoleCarcasse())
{
i+=1 ;
%>
	<tr>
	<td style="text-align:left; width:200px; height:50px;border:1px solid black;">
		<%=" CARCASSA N. "+i+"<br>"+matricola %>  
	</td>
	<td style="text-align:left; width:200px; height:50px;border:1px solid black;">
		1)
	</td>
	<td style="text-align:left; width:200px; height:50px;border:1px solid black;">
		2)
	</td>
	<td style="text-align:left; width:200px; height:50px;border:1px solid black;">
		3) 
	</td>
	<td style="text-align:left; width:200px; height:50px;border:1px solid black;">
		4)
	</td>
</TR>
<%} %>

</TABLE>
<%--
<P align="center"><b>PER POLLAME </b> IDENTIFICAZIONE DELLE CARCASSE PRESCELTE<br></P>
<TABLE rules="all" cellpadding="5" border="1px solid;">
<tr>
	<tr>
	<td style="text-align:left; width:200px; height:50px;">
		I CARCASSA N. 
	</td>
	<td style="text-align:left; width:200px; height:50px;">
		II CARCASSA N.
	</td>
	<td style="text-align:left; width:200px; height:50px;">
		III CARCASSA N.
	</td>
	<td style="text-align:left; width:200px; height:50px;">
		IV CARCASSA N.
	</td>
	<td style="text-align:left; width:200px; height:50px;">
		V CARCASSA N.
	</td>
</TR>
<TR>
	<td style="text-align:left; width:200px; height:80px;">
		VI CARCASSA N.	
	</td>
	<td style="text-align:left; width:200px; height:80px;">
		VII CARCASSA N.	
	</td>
	<td style="text-align:left; width:200px; height:80px;">
		VIII CARCASSA N.	
	</td>
	<td style="text-align:left; width:200px; height:80px;">
		IX CARCASSA N.	
	</td>
	<td style="text-align:left; width:200px; height:80px;">
		X CARCASSA N.
	</td>
</TR>
<TR>
	<td style="text-align:left; width:200px; height:50px;">
		XI CARCASSA N.	
	</td>
	<td style="text-align:left; width:200px; height:50px;">
		XII CARCASSA N.	
	</td>
	<td style="text-align:left; width:200px; height:50px;">
		XIII CARCASSA N.	
	</td>
	<td style="text-align:left; width:200px; height:50px;">
		XIV CARCASSA N.
	</td>
	<td style="text-align:left; width:200px; height:50px;">
		XV CARCASSA N.	
	</td>
</TR>
</TABLE>

<span class="NocheckedItem"> &nbsp;Le carcasse campionate sono le stesse di quelle campionate dall'OSA.</span>
Le u.c sono state poste in <span class="NocheckedItem"> &nbsp;buste di plastica sterili</span> <span class="NocheckedItem"> &nbsp;recipienti di</span> <input class="editField" type="text" size="30" value="<%=valoriScelti.get(z++) %>"> sterili che vengono sigillati con piombini recanti la dicitura
<input class="editField" type="text" size="30" value="<%=valoriScelti.get(z++) %>"> e muniti di cartellini controfirmati dal presente al campionamento. Esse sono inviate al <input class="editField" type="text" size="30" value="<%=valoriScelti.get(z++) %>">. Le u.c. vengono
conservate e trasferite alla temperatura di <input class="editField" type="text" id="" name="" size="3" value="<%=valoriScelti.get(z++) %>"  /> °C <br/--%>
Letto, confermato e sottoscritto, <br>
<P> IL RAPPRESENTANTE DELL'IMPRESA &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; GLI OPERATORI DEL CONTROLLO UFFICIALE 
<br>


