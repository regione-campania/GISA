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




<form method="post" name="myformPNAA" id="myformPNAA" action="CampioniReport.do?command=InsertSchedaPnaa">

<b>ENTE DI APPARTENENZA</b> <input class="editField" type="text" name="asl" id="asl" size="30" value=""/><br/>
<b>UNITA' TERRITORIALE-DISTRETTO</b> <input class="editField" type="text" size="30" name="distretto" id="distretto" value="" /><br/>
<b>campioni di ALIMENTO (*):</b> <br/>
<input type="radio" id="dpa" name="dpa" value="001" <%--= (PnaaDetails.getAlimenti().equalsIgnoreCase("001")) ? ("checked=\"checked\"") : ("")  --%>/> per ANIMALI NON DESTINATI alla produzione di alimenti (non DPA)<br/>
<input type="radio" id="dpa" name="dpa" value="002" <%--= (PnaaDetails.getAlimenti().equalsIgnoreCase("002")) ? ("checked=\"checked\"") : ("")  --%> /> per ANIMALI DESTINATI alla produzione di alimenti (DPA)<br/>
<br/>
<b>A.PARTE GENERALE</b><br/>
<b>A2. Metodo di campionamento (*):</b><br/>
<input type="radio"   name="a2" id="a2" value="001" />Individuale/singolo
<input type="radio"   name="a2" id="a2" value="020" />Norma di riferimento (solo se trattasi di una norma UE):<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; IN ACCORDO AL REG 152/2009 <br/>
<input type="radio"   name="a2" id="a2" value="003" />Sconosciuto<br/>
<input type="radio"   name="a2" id="a2" value="011" />Altro metodo di campionamento<br/>
<b>A5. Luogo di prelievo (*):</b><br/>
<input type="radio"   name="a5" id="a5" value="67"> Stabilimenti di miscelazione grassi<br/>
<input type="radio"   name="a5" id="a5" value="22"> Stabilimento di produzione oli e grassi animali <br/>
<input type="radio"   name="a5" id="a5" value="50"> Stabilimento di mangimi composti<br/>
<input type="radio"   name="a5" id="a5" value="52"> Stabilimento di mangimi composti per animali da compagnia<br/>
<input type="radio"   name="a5" id="a5" value="42"> Mezzo di trasporto su rotaia <br/>
<input type="radio"   name="a5" id="a5" value="44"> Mezzo di trasporto aereo <br/>
<input type="radio"   name="a5" id="a5" value="41"> Mezzo di trasporto su strada <br/>
<input type="radio"   name="a5" id="a5" value="43"> Mezzo di trasporto su acqua <br/>
<input type="radio"   name="a5" id="a5" value="49"> Deposito/Magazzinaggio <br/>
<input type="radio"   name="a5" id="a5" value="55"> Impianto che produce grassi vegetali per l'alimentazione animale <br/>
<input type="radio"   name="a5" id="a5" value="56"> Impianto oleochimico che produce materie prime per l'alimentazione animale <br/>
<input type="radio"   name="a5" id="a5" value="47"> Vendita al dettaglio <br/> 
<input type="radio"   name="a5" id="a5" value="51"> Stabilimento di produzione di additivi/premiscele<br/>
<input type="radio"   name="a5" id="a5" value="68"> Stabilimento per la produzione di BIODIESEL<br/>
<input type="radio"   name="a5" id="a5" value="48"> Mulino per la produzione di mangimi semplici<br/>
<input type="radio"   name="a5" id="a5" value="53"> Vendita all'ingrosso/intermediario di mangimi <br/>
<input type="radio"   name="a5" id="a5" value="2"> Azienda agricola<br/>
<input type="radio"   name="a5" id="a5" value="57"> Azienda zootecnica con ruminanti<br/>>
<input type="radio"   name="a5" id="a5" value="58"> Azienda zootecnica che non detiene ruminanti<br/>>
<input type="radio"   name="a5" id="a5" value="59"> Attività di importazione (Primo deposito di materie prime importate) <br/>>
<b>A6. Codice identificativo luogo di prelievo (*):</b>&nbsp;&nbsp;&nbsp;<br/>
<input class="editField" type="text"  name="a6" id="a6" size="30" value=""/><br/>
<b>B. INFORMAZIONI SUL CAMPIONE PRELEVATO</b><br/>
<b>B2. Trattamento applicato al mangime prelevato (*):</b></br/>
 <input class="editField" type="text" size="30" name="b2" id="b2" value="" /><br/>
 <b>B4. Ragione sociale ditta produttrice (*):</b><br/>
 <b>B5. Indirizzo ditta produttrice (*):</b><br/>
<input class="editField" type="text" size="30" name="b4" id="b4" value=""/><br/>
<input class="editField" type="text" size="30" name="b5" id="b5" value=""/><br/>
<b>B8. Nome commerciale del mangime (*):</b><br/>
<input class="editField" type="text" id="b8" name="b8" size="30" value=""><br/>
<input type="radio"  id="b7" name="b7" value="1" >Convezionale<br/>
<input type="radio"  id="b7" name="b7" value="2" >Sconosciuto (no per OGM)<br/>
<b>B12. Paese di produzione (*):</b><br/>
<b>B14. Data di scadenza (*):</b><br/>
<input class="editField" type="text" size="30" name="b12" id="b12" value="" /><br/>
<input class="editField" type="text" size="30" name="b14" id="b14" value=""/><br/>
<b>B16. Dimensione di lotto (*):</b><br/>
<input class="editField" type="text" size=30" name="b16" id="b16" value=""/><br/>
<b>B17. Ingredienti (*):</b><br/>
<input class="editField" type="text" size="30" name="b17" id="b17" value=""/><br/>
<input  type="submit" name="salva" id="sId" class = "buttonClass" onclick="javascript:this.form.tipoAzione.value='salva';if( confirm('Attenzione! Controlla bene tutti i dati inseriti in quanto alla chiusura della finestra, i dati contrassegnati in giallo saranno persi.\nVuoi effettuare il salvataggio della scheda?')){ return checkForm(this.form);} else return false;" value="Salva e completa"/>
<input type="hidden" id="tipoAzione" name="tipoAzione" value="" />
</form>


