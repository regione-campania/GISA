<%--
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.--%>
<%@ taglib uri="/WEB-INF/dhv-taglib.tld" prefix="dhv"%>
<%@ taglib uri="/WEB-INF/zeroio-taglib.tld" prefix="zeroio"%>


<%@page
	import="org.aspcfs.modules.macellazioni.base.Casl_Non_Conformita_Rilevata"%>
<%@page import="org.aspcfs.modules.macellazioni.base.ProvvedimentiCASL"%>
<%@page import="org.aspcfs.modules.macellazioni.base.NonConformita"%>
<%@page import="org.aspcfs.modules.macellazioni.base.Campione"%><%@page
	import="org.aspcfs.modules.macellazioni.base.Organi"%>
<%@page import="org.aspcfs.modules.macellazioni.base.PatologiaRilevata"%>

<%@page import="org.aspcfs.modules.util.imports.ApplicationProperties"%>

<%@page import="org.aspcfs.modules.macellazioni.base.TipoRicerca"%>
<%@page import="javax.imageio.ImageIO"%>
<%@ page import="java.awt.*, java.awt.image.*,  java.util.StringTokenizer, com.itextpdf.text.pdf.*, java.io.*, java.util.*,org.aspcfs.utils.web.*, com.itextpdf.text.pdf.codec.*,
org.aspcfs.modules.campioni.base.SpecieAnimali" %>

<jsp:useBean
	id="Tampone" class="org.aspcfs.modules.macellazioni.base.Tampone"
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
	class="org.aspcfs.modules.stabilimenti.base.Organization"
	scope="request" />
<jsp:useBean id="Capo" class="org.aspcfs.modules.macellazioni.base.Capo"
	scope="request" />

<jsp:useBean id="richiestaIstopatologico"
	class="org.aspcfs.modules.macellazioni.base.RichiestaIstopatologico"
	scope="request" />

<jsp:useBean id="ASL" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Razze" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="Specie" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="CategorieBovine"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="CategorieBufaline"
	class="org.aspcfs.utils.web.LookupList" scope="request" />

<jsp:useBean id="Patologie" class="org.aspcfs.utils.web.LookupList"
	scope="request" />
<jsp:useBean id="PatologieOrgani"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="Azioni" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<jsp:useBean id="Organi" class="org.aspcfs.utils.web.LookupList"
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



<!-- Lookup istopatologico -->
<jsp:useBean id="listaDestinatariRichiestaIstopatologico"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_alimentazione"
	class="org.aspcfs.utils.web.LookupList" scope="request" />
<jsp:useBean id="lookup_habitat" class="org.aspcfs.utils.web.LookupList"
	scope="request" />

<%@ include file="../../initPage.jsp"%>

<link type="text/css" href="css/ui.all.css" rel="stylesheet" />
<link type="text/css" href="css/demos.css" rel="stylesheet" />
<script language="JavaScript" TYPE="text/javascript"
	SRC="javascript/popCalendar.js"></script>
<script type="text/javascript" src="javascript/ui.core.js"></script>

<div class="Section1" style="font-size: 14px;">
	<div>
		REGISTRO TUMORI<br></br> SCHEDA ANAMNESTICA PER CAMPIONI PROVENIENTI
		DA ANIMALI DI REDDITO DA SOTTOPORRE AD ESAME ISTOPATOLOGICO
	</div>
	<br></br>
	<div style="margin-left: 50px;" align="right">
		A: <br>
		<%=listaDestinatariRichiestaIstopatologico.getSelectedValue(richiestaIstopatologico
					.getIdDestinatarioRichiesta())%>
		<br> Sede
	</div>
	
	
<div id="barcode">
<img src="<%=createBarcodeImage(richiestaIstopatologico.getIdentificativoRichiesta())%>" /> &nbsp; &nbsp;
<img src="<%=createBarcodeImage(richiestaIstopatologico.getCapo().getCd_matricola())%>" />
</div>

<br></br>
	<div>
		Data prelievo:
		<%=DateUtils.timestamp2string(richiestaIstopatologico.getDataPrelievo())%>&nbsp;
		<br>
		<div>Veterinario prelevatore:</div>
		<div>
			Nominativo:
			<%=richiestaIstopatologico.getNominativoVeterinarioPrelevatore()%>
			<br> Asl di appartenenza: <%=ASL.getSelectedValue(OrgDetails.getIdAsl()) %>
			<div class="dott">&nbsp;</div>
			<!-- Distretto: Commentato come da verbale 27/11/2014
			<div class="dott">&nbsp;</div> -->
		</div>

		<br></br>Luogo del prelievo:
		<div class="sezione_macello"
			style="border: 1px solid black; height: auto;">

			<table>
				<tr>
					<td>Macello<br> <%=OrgDetails.getName()%></td>
					<td>Numero di riconoscimento<br><%=OrgDetails.getApprovalNumber()%>
					</td>
					<td>Telefono<br><%=(OrgDetails.getPrimaryPhoneNumber() != null && !("").equals(OrgDetails.getPrimaryPhoneNumber())) ? OrgDetails
					.getPrimaryPhoneNumber() : "---"%></td>
				</tr>
			</table>

		</div>


		<br></br>
		Provenienza del capo:
		<div class="sezione_provenienza" style="border: 1px solid black;">

			
			<table>
				<tr>
					<td>Specie<br> <%=Specie.getSelectedValue(richiestaIstopatologico.getCapo().getCd_specie())%>
						<br>Razza<br><%=(richiestaIstopatologico.getCapo().getCd_id_razza() > 0) ? Razze.getSelectedValue(richiestaIstopatologico.getCapo().getCd_id_razza()) : "N.D." %></td>
					<td>Ragione sociale allevamento<br><%=richiestaIstopatologico.getRagioneSocialeAllevamentoProvenienzaCapo()%>
					<td>Codice allevamento<br><%=richiestaIstopatologico.getCodiceAllevamentoProvenienza()%>
					<td>ASL provenienza Capo <br><%=(richiestaIstopatologico.getIdAslProvenienzaCapo() > 0) ? ASL.getSelectedValue(richiestaIstopatologico.getIdAslProvenienzaCapo()) : richiestaIstopatologico.getDenominazioneAsl()%>
				</tr>
			</table>

		</div>

		<br></br>
		Segnalamento dell'animale:
		<div class="sezione_animale" style="border: 1px solid black;">
			<table>
				<tr>				
				<%String categoria = "";
						categoria = 	(richiestaIstopatologico.getCapo().getCd_categoria_bovina() > 0) ? CategorieBovine.getSelectedValue(richiestaIstopatologico.getCapo().getCd_categoria_bovina()) : CategorieBufaline.getSelectedValue(richiestaIstopatologico.getCapo().getCd_categoria_bufalina());
					%>
					<td>Categoria<br>  <%=(!("").equals(categoria))? categoria : "N.D"%></td>
					<td>Data di nascita<br><%=DateUtils.timestamp2string(richiestaIstopatologico.getCapo().getCd_data_nascita())%></td>
					<td>Sesso<br><%=(richiestaIstopatologico.getCapo().isCd_maschio()) ? "M" : "F"%></td>
					<td>Marca auricolare <br><%=richiestaIstopatologico.getCapo().getCd_matricola()%></td>
				</tr>
			</table>

		</div>

<%-- 		<br></br>
		Provenienza del capo: --Commentato come da verbale 27/11/2014--
		<div class="sezione_dati_generici_animale"
			style="border: 1px solid black;">
			<table>
				<tr>
					<td>Habitat<br><%=lookup_habitat.getSelectedValue(richiestaIstopatologico.getIdHabitatAnimale())%></td>
					<td>Alimentazione<br><%=lookup_alimentazione.getSelectedValue(richiestaIstopatologico.getIdAlimentazioneAnimale())%>
					<td>Peso:<br><%=(String.valueOf(richiestaIstopatologico.getPesoAnimale()))%>
				</tr>
			</table>

		</div> --%>


		<br></br>
		Organi prelevati:
		<div class="sezione_organi_prelevati" style="border: 1px solid black;">
			
			<table>
				<tr>
					<th>Organo</th>
					<th>Topografia</th>
					<th>Interessamento altri organi</th>
					<th>Identificativo campione</th>
				</tr>
				<%
					Iterator i = richiestaIstopatologico.getLista_organi_richiesta().iterator();
					while (i.hasNext()) {
						Organi thisOrgano = (Organi) i.next();
				%>

				<tr>
					<td style="text-align: center;"><%=Organi.getSelectedValue(thisOrgano.getLcso_organo())%></td>
					<td style="text-align: center;"><%=thisOrgano.getIstopatologico_topografia()%></td>
					<td style="text-align: center;"><%=thisOrgano.getIstopatologico_interessamento_altri_organi()%></td>
					<td style="text-align: center;"><img src="<%=createBarcodeImage(thisOrgano.getIdentificativo_campione_organo())%>" /></td>

				</tr>
				<%
					}
				%>
			</table>

		</div>
		<br></br>
		<div class="sezione_osservazioni_veterinario" style="font-size: 12px;">
			Osservazioni del veterinario:<br>
			<%=richiestaIstopatologico.getNoteRichiesta()%>
		</div>
		<br></br>
		Importante<br>
		<div class="sezione_importante">
			<ul style="font-size: 8px;">
				<li>Fissare il campione in formalina al 10%</li>
				<li>Rispettare il rapporto volumetrico campione/formalina 1:10</li>
				<li>Nel caso di prelievo di più pezzi identificarli chiaramente o meglio porli in contenitori diversi</li>
				<li>Non usare contenitori in vetro o con bocca stretta, o comunque inadeguata all'estrazione del campione</li>
				<li>Compilare attentamente la richiesta pena l'esclusione dalla processazione del campione</li>
			</ul>

		</div>




	</div>
	
		<div style="margin-left: 50px;" align="right">
		Firma e timbro
	</div>

</div>
