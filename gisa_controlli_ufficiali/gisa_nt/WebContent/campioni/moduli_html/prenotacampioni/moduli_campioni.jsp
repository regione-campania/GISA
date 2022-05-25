<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@page import="javax.imageio.ImageIO"%>
<%@ include file="../../../initPage.jsp" %>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv" %>
    <%@ page import=" org.aspcfs.modules.util.imports.ApplicationProperties"%>
    <%@page import="java.net.InetAddress"%>
<%@ page import="java.awt.*, java.awt.image.*,  java.util.StringTokenizer, com.itextpdf.text.pdf.*, java.io.*, java.util.*,org.aspcfs.utils.web.*, com.itextpdf.text.pdf.codec.*,org.aspcfs.modules.campioni.base.SpecieAnimali" %>
<jsp:useBean id="OrgOperatore" class="org.aspcf.modules.controlliufficiali.base.Organization" scope="request"/>
<jsp:useBean id="OrgUtente" class="org.aspcf.modules.controlliufficiali.base.OrganizationUtente" scope="request"/>
<jsp:useBean id="OrgCampione" class="org.aspcf.modules.controlliufficiali.base.ModCampioni" scope="request"/>
<jsp:useBean id="ticketDetails" class="org.aspcfs.modules.campioni.base.Ticket" scope="request"/>
<jsp:useBean id="tipo" class="java.lang.String" scope="request"/>


<jsp:useBean id="valoriScelti" class="java.util.ArrayList" scope="request"/>
<jsp:useBean id="definitivoDocumentale" class="java.lang.String" scope="request"/>

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

<form method="post" name="myform" id="myform" action="PrintModulesHTML.do?command=ViewModules">
	
	<% if(OrgCampione.getTipoModulo().equals("2")) { %>
		
		<%@ include file="/campioni/moduli_html/prenotacampioni/mod2_batter.jsp" %>
	
	<% } if(OrgCampione.getTipoModulo().equals("3")){ %>
	 	
	 	<%@ include file="/campioni/moduli_html/prenotacampioni/mod3_chimico.jsp" %>
	 	
	<% }  if(OrgCampione.getTipoModulo().equals("1")){ %>
	
		<%@ include file="/campioni/moduli_html/prenotacampioni/mod1_molluschi.jsp" %>
		
	<% }  if(OrgCampione.getTipoModulo().equals("19")){ %> 
	
		<%@ include file="/campioni/moduli_html/prenotacampioni/prelievo_pnaa.jsp" %>
		
	<% } %>
	
	
		 
	
</form>

