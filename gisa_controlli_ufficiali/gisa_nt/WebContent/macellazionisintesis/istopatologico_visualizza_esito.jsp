<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>


<%@page
	import="org.aspcfs.modules.macellazionisintesis.base.Casl_Non_Conformita_Rilevata"%>
<%@page import="org.aspcfs.modules.macellazionisintesis.base.ProvvedimentiCASL"%>
<%@page import="org.aspcfs.modules.macellazionisintesis.base.NonConformita"%>
<%@page import="org.aspcfs.modules.macellazionisintesis.base.Campione"%><%@page
	import="org.aspcfs.modules.macellazionisintesis.base.Organi"%>
	<jsp:useBean id="Organi" class="org.aspcfs.utils.web.LookupList"
	scope="request"/>
<%@page import="org.aspcfs.modules.macellazionisintesis.base.PatologiaRilevata"%>

<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>

<%@page import="org.aspcfs.modules.macellazionisintesis.base.TipoRicerca"%>
<%@page import="javax.imageio.ImageIO"%>
<%@ page import="java.awt.*, java.awt.image.*,  java.util.StringTokenizer, com.itextpdf.text.pdf.*, java.io.*, java.util.*,org.aspcfs.utils.web.*, com.itextpdf.text.pdf.codec.*,
org.aspcfs.modules.campioni.base.SpecieAnimali" %>

<jsp:useBean
	id="Tampone" class="org.aspcfs.modules.macellazionisintesis.base.Tampone"
	scope="request" />


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
<jsp:useBean id="Capo" class="org.aspcfs.modules.macellazionisintesis.base.Capo"
	scope="request" />
<jsp:useBean id="richiestaIstopatologico"
	class="org.aspcfs.modules.macellazionisintesis.base.RichiestaIstopatologico"
	scope="request" />
<jsp:useBean id="lookup_esame_istopatologico_tipo_diagnosi"
	class="org.aspcfs.utils.web.LookupList"
	scope="request" />	
<jsp:useBean id="lookup_associazione_classificazione_tabella_lookup"
	class="org.aspcfs.utils.web.LookupList"
	scope="request" />	
<jsp:useBean id="lookup_who_umana"
	class="org.aspcfs.utils.web.LookupList"
	scope="request" />	
	


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

<div>
			Data esito: <%=DateUtils.timestamp2string(richiestaIstopatologico.getDataEsito())%>
</div>
<br>
<div>
			Codice Sigla: <%=richiestaIstopatologico.getCodiceSiglaEsito() %>
</div>

<br>
<div>
			Descrizione esito: <%=richiestaIstopatologico.getDescrizioneEsito() %>
</div>


</br></br>
<%ArrayList<Organi> organi =  (ArrayList<Organi>) richiestaIstopatologico.getLista_organi_richiesta();
Iterator it = organi.iterator();
while (it.hasNext()){
	Organi thisOrgano = (Organi)it.next();
%>
<table>
<tr><td><%=Organi.getSelectedValue(thisOrgano.getLcso_organo())%> <br> <img src="<%=createBarcodeImage(thisOrgano.getIdentificativo_campione_organo())%>" /> </td></tr>
<tr><td>
<div>
			Esito: <%=lookup_esame_istopatologico_tipo_diagnosi.getSelectedValue(thisOrgano.getId_esito_istopatologico()) %>
</div>
</br></br>
<dhv:evaluate if ="<%=thisOrgano.getId_classificazione_istopatologico() > 0 %>">
<div id="specificheEsito" >
		Classificazione utilizzata: <%=lookup_associazione_classificazione_tabella_lookup.getSelectedValue(thisOrgano.getId_classificazione_istopatologico()) %>
</div>
</br></br>
<div>
Valore classificazione: <%=lookup_who_umana.getSelectedValue(thisOrgano.getId_valore_classificazione_istopatologico()) %>

</div>
</dhv:evaluate></td></tr>
</table>
<%} %>
<br></br>


<div id="classificazione">

</div>

<input type="hidden" name="idIstopatologico" id="idIstopatologico" value="<%=richiestaIstopatologico.getId()%>">


</form>