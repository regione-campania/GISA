/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioninewsintesis.actions;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;


import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;

import org.aspcfs.controller.SystemStatus;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.base.Parameter;

import org.aspcfs.modules.gestioneDocumenti.actions.GestioneAllegatiMacelli;
import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.modules.macellazioninewsintesis.base.Campione;
import org.aspcfs.modules.macellazioninewsintesis.base.Capo;
import org.aspcfs.modules.macellazioninewsintesis.base.CapoAjax;
import org.aspcfs.modules.macellazioninewsintesis.base.CapoLog;
import org.aspcfs.modules.macellazioninewsintesis.base.CapoLogDao;
import org.aspcfs.modules.macellazioninewsintesis.base.Casl_Non_Conformita_Rilevata;
import org.aspcfs.modules.macellazioninewsintesis.base.ChiaveModuliMacelli;
import org.aspcfs.modules.macellazioninewsintesis.base.Esito;
import org.aspcfs.modules.macellazioninewsintesis.base.GenericBean;
import org.aspcfs.modules.macellazioninewsintesis.base.NonConformita;
import org.aspcfs.modules.macellazioninewsintesis.base.Organi;
import org.aspcfs.modules.macellazioninewsintesis.base.Partita;
import org.aspcfs.modules.macellazioninewsintesis.base.PartitaAjax;
import org.aspcfs.modules.macellazioninewsintesis.base.PartitaLog;
import org.aspcfs.modules.macellazioninewsintesis.base.PartitaLogDao;
import org.aspcfs.modules.macellazioninewsintesis.base.PartitaSeduta;
import org.aspcfs.modules.macellazioninewsintesis.base.PatologiaRilevata;
import org.aspcfs.modules.macellazioninewsintesis.base.ProvvedimentiCASL;
import org.aspcfs.modules.macellazioninewsintesis.base.StampeModuli;
import org.aspcfs.modules.macellazioninewsintesis.base.StampeModuliDao;
import org.aspcfs.modules.macellazioninewsintesis.base.Tampone;
import org.aspcfs.modules.macellazioninewsintesis.base.Tipo;
import org.aspcfs.modules.macellazioninewsintesis.base.TipoRicerca;
import org.aspcfs.modules.macellazioninewsintesis.utils.ConfigTipo;
import org.aspcfs.modules.macellazioninewsintesis.utils.ReflectionUtil;
import org.aspcfs.modules.speditori.base.OrganizationAddress;
import org.aspcfs.modules.stabilimenti.base.Organization;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.utils.AjaxCalls;

import org.aspcfs.utils.JasperReportUtils;
import org.aspcfs.utils.PopolaCombo;
import org.aspcfs.utils.web.LookupList;
import org.aspcfs.utils.web.ParameterUtils;
import org.bouncycastle.jce.provider.JDKKeyFactory.RSA;
import org.jmesa.core.filter.DateFilterMatcher;
import org.jmesa.core.filter.FilterMatcher;
import org.jmesa.core.filter.MatcherKey;
import org.jmesa.core.filter.NumberFilterMatcher;
import org.jmesa.facade.TableFacade;
import org.jmesa.facade.TableFacadeFactory;
import org.jmesa.limit.Limit;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.editor.DateCellEditor;
import org.jmesa.view.editor.NumberCellEditor;
import org.jmesa.view.html.component.HtmlColumn;
import org.jmesa.view.html.editor.DroplistFilterEditor;
import org.jmesa.view.html.editor.HtmlCellEditor;

import com.darkhorseventures.framework.actions.ActionContext;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.zeroio.webutils.FileDownload;

public final class MacellazioniBovini extends Macellazioni
{
	private static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
	private static SimpleDateFormat sdfYear = new SimpleDateFormat( "yyyy" );
	
	private void createPDFIdatidosi(Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, ArrayList<Capo> listaCapi, String dataMacellazione, StampeModuli stampeModuli) throws Exception{

		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaRazze	= new LookupList( db, "razze_bovini" );
		LookupList listaRegioni  = new LookupList( db, "m_lookup_regioni");

		Paragraph p = new Paragraph("REGIONE CAMPANIA\n" +
				"AZIENDA SANITARIA LOCALE " + listaAsl.getValueFromId(macello.getSiteId()) + "\n" +
				"SERVIZIO VETERINARIO\n" +
				"...............................\n" +
				"Unita' Operativa Veterinaria del Distretto ...........\n" +
				"INDIRIZZO .....................................................\n" +
				"TELEFONO .......................... FAX ..........................\n" +
				"MAIL .............................................................", 
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));

		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);

		document.add( Chunk.NEWLINE );

		//			Paragraph p2 = new Paragraph("Prot. " + stampeModuli.getProgressivo() + 
		//										 "/" + listaAsl.getValueFromId(macello.getSiteId())+ 
		//										 "/" + sdfYear.format(sdf.parse(dataMacellazione))  + 
		//										 "\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		//			p2.setAlignment(Element.ALIGN_LEFT);
		//			document.add(p2);

		Paragraph p3 = new Paragraph("Al Dipartimento di Prevenzione\nServizi Veterinari\ndell' A.S.L. (ASL di origine animale) ..........\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p3.setAlignment(Element.ALIGN_RIGHT);
		document.add(p3);

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		Paragraph p_bold = new Paragraph("OGGETTO: ",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD));
		p_bold.setAlignment(Element.ALIGN_LEFT);
		document.add(p_bold);

		PdfContentByte cb = writer.getDirectContent();
		cb.beginText();
		cb.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 12.0F);

		cb.showTextAligned(Element.ALIGN_LEFT, "COMUNICAZIONE RISULTATI ISPEZIONE POST MORTEM\n",108F,491F, 0);
		cb.showTextAligned(Element.ALIGN_LEFT, "(All. I Sez. II Capo I punto 2.a Reg. 854/04)\n",108F,476F, 0);
		cb.showTextAligned(Element.ALIGN_LEFT, "IDATIDOSI - (O.M. 21/04/1964)",108F,461F, 0);
		cb.endText();

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		String comuneMacello = ( macello.getCity() != null && !macello.getCity().equals("") ? macello.getCity() : ".................................") + 
		( macello.getState() != null && !macello.getState().equals("") ? " (" + macello.getState() + ")" :  " ....." );

		Paragraph p5 = new Paragraph("Si comunica che presso il macello " + macello.getName() + " " +
				"sito nel Comune di " + comuneMacello + " riconosciuto con " +
				"n. " + macello.getApprovalNumber() + " alla visita ispettiva post mortem i sottoelencati animali:\n" ,
				FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));

		p5.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p5);
		document.add( Chunk.NEWLINE );

		com.itextpdf.text.List elencoCapiIdatidosi = new com.itextpdf.text.List(true, 10);

		String specie = "";
		String razza = "";
		String speditore = "...........................";
		String cod_speditore = "...........................";
		String comune = "...........................";
		String loc = "...........................";

		org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;
		OrganizationAddress speditoreAddress = null;

		for(Capo c : listaCapi){
			specie = listaSpecie.getValueFromId(c.getCd_specie());
			specie = (specie != null && !specie.equals("") ? specie : "...........................");
			razza = listaRazze.getValueFromId(c.getCd_id_razza());
			razza = (razza != null && !razza.equals("") ? razza : "...........................");

			if(c.getCd_id_speditore() > 0){
				speditoreOrg = new org.aspcfs.modules.speditori.base.Organization(db,c.getCd_id_speditore());
			}

			if(speditoreOrg != null){

				if( speditoreOrg.getName() != null && !speditoreOrg.getName().equals("") ){
					speditore = speditoreOrg.getName();
				}

				if( speditoreOrg.getAccountNumber() != null && !speditoreOrg.getAccountNumber().equals("") ){
					cod_speditore = speditoreOrg.getAccountNumber();
				}

				if(speditoreOrg.getAddressList().size() > 0){
					speditoreAddress = (OrganizationAddress) speditoreOrg.getAddressList().get(0);
				}

			}

			if(speditoreAddress != null){

				if( speditoreAddress.getCity() != null && !speditoreAddress.getCity().equals("") ){
					comune = speditoreAddress.getCity();
				}

				if( speditoreAddress.getState() != null && !speditoreAddress.getState().equals("") ){
					loc = speditoreAddress.getState();
				}

			}

			//				speditore = (c.getCd_speditore() != null && !(c.getCd_speditore().equals("")) ? c.getCd_speditore() : "..........................."); 
			//				cod_speditore = (c.getCd_codice_speditore() != null && !(c.getCd_codice_speditore().equals("")) ? c.getCd_codice_speditore() : "...........................");
			//				comune = (c.getCd_provenienza_comune() != null && !(c.getCd_provenienza_comune().equals("")) ? c.getCd_provenienza_comune() : "...........................");			
			//				loc = ((listaRegioni.getValueFromId(c.getCd_provenienza_regione()) != null && !listaRegioni.getValueFromId(c.getCd_provenienza_regione()).equals("")) ? listaRegioni.getValueFromId(c.getCd_provenienza_regione()) : "...........................");



			ListItem li = new ListItem( "specie " + specie + " , razza " + razza + " con matr. " + c.getCd_matricola() + ", " +
					"proveniente dall'azienda " + speditore + " cod. az. " + cod_speditore + 
					" sita nel comune di " + comune + " in loc. " + loc, 
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			li.setAlignment(Element.ALIGN_JUSTIFIED);
			elencoCapiIdatidosi.add(li);

		}
		document.add(elencoCapiIdatidosi);

		document.add( Chunk.NEWLINE );
		Paragraph p7 = new Paragraph( (listaCapi.size() > 0 ? "macellati" : "macellato") + " in data " + dataMacellazione + (listaCapi.size() > 0 ? " sono risultati infestati" : " e' risultato infestato") + " da IDADITOSI.\n" +
				"Tanto si comunica per gli ulteriori accertamenti ed i seguiti di competenza.\n" +
				"La trasmissione del documento ha valore ufficiale non si provvedera' ad inviare lo stesso a mezzo posta (L.n. 412 art.6 del 30/12/1991 e succ. integ.)", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p7.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p7);

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		Paragraph p8 = new Paragraph("IL VETERINARIO\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p8.setAlignment(Element.ALIGN_RIGHT);
		document.add(p8);

		document.close();

		//Calcolo del progressivo
		super.gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO, out);

		ByteArrayOutputStream outputNew = new ByteArrayOutputStream();
		Document documentNew = new Document();
		PdfWriter writerNew = PdfWriter.getInstance(documentNew, outputNew);
		documentNew.open();
		PdfContentByte cbNew = writerNew.getDirectContent();

		// Load existing PDF
		PdfReader readerNew = new PdfReader(out.toByteArray());

		PdfImportedPage page = null;
		for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
			page = writerNew.getImportedPage(readerNew, j); 
			documentNew.newPage();
			cbNew.addTemplate(page, 0, 0);
			if(j == 1){
				cbNew.beginText();
				cbNew.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
				cbNew.showTextAligned(Element.ALIGN_LEFT, 
						"Prot. " + stampeModuli.getProgressivo() + "/" + 
						listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
						sdfYear.format(sdf.parse(dataMacellazione)),37F,580F, 0);
				if(stampeModuli.getOldProgressivo() > 0){
					cbNew.showTextAligned(Element.ALIGN_LEFT, 
							"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataMacellazione)) + ")",37F,565F, 0);
				}
				cbNew.endText();
			}
		}

		documentNew.close();

		out.reset();
		out.write(outputNew.toByteArray());

	}
	
	private void createPDFArrivoAlMacello(Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, ArrayList<GenericBean> listaCapi, TreeMap<Integer, ArrayList<Casl_Non_Conformita_Rilevata>> hashCapiNC, TreeMap<Integer, ArrayList<ProvvedimentiCASL>> hashCapiProvvedimenti, String dataArrivoMacello, StampeModuli stampeModuli) throws Exception{

		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaRegioni  = new LookupList( db, "m_lookup_regioni");
		LookupList listaNC  = new LookupList( db, "m_lookup_motivi_comunicazioni_asl");
		LookupList listaPA  = new LookupList( db, "m_lookup_provvedimenti_casl");

		String comuneMacello = ( macello.getCity() != null && !macello.getCity().equals("") ? macello.getCity() : ".................................") + 
		( macello.getState() != null && !macello.getState().equals("") ? " (" + macello.getState() + ")" :  " ....." );

		Paragraph p = new Paragraph("REGIONE CAMPANIA\nAZIENDA SANITARIA LOCALE "+ listaAsl.getValueFromId(macello.getSiteId())+"\nSERVIZIO VETERINARIO\n" +
				"...............................\n" +
				"Unita' Operativa Veterinaria del Distretto ...........\n" +
				"INDIRIZZO .....................................................\n" +
				"TELEFONO .......................... FAX ..........................\n" +
				"MAIL .............................................................", FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));

		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);

		document.add( Chunk.NEWLINE );

		Paragraph p2 = new Paragraph("Al................\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p2.setAlignment(Element.ALIGN_RIGHT);
		document.add(p2);

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		Paragraph p_bold = new Paragraph("OGGETTO: ",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD));
		p_bold.setAlignment(Element.ALIGN_LEFT);
		document.add(p_bold);

		PdfContentByte cb = writer.getDirectContent();
		cb.beginText();
		cb.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 12.0F);

		cb.showTextAligned(Element.ALIGN_LEFT, "Non conformita' rilevate all'arrivo degli animali al macello.\n",108F,527F, 0);
		cb.showTextAligned(Element.ALIGN_LEFT, "(Macello "+macello.getName()+" con numero CE "+ macello.getApprovalNumber()+" nel comune di "+comuneMacello+")\n",108F,516F, 0);
		cb.endText();

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		Paragraph p5 = new Paragraph("L'anno "+dataArrivoMacello.substring(6,10)+" addi "+dataArrivoMacello.substring(0,2)+" del mese di "+getMeseFromData(dataArrivoMacello.substring(3,5))+", presso il macello "+macello.getName()+
				" sito nel Comune di "+comuneMacello+" riconosciuto con n. "+macello.getApprovalNumber()+" \n" ,FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p5.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p5);

		document.add( Chunk.NEWLINE );

		Paragraph p6 = new Paragraph("e' stato accertato che i capi:",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p6.setAlignment(Element.ALIGN_CENTER);
		document.add(p6);

		com.itextpdf.text.List elencoCapiArrivoAlMacello = new com.itextpdf.text.List(true, 10);
		com.itextpdf.text.List elencoCapiNC = new com.itextpdf.text.List(true, 10);
		com.itextpdf.text.List elencoCapiPA = new com.itextpdf.text.List(true, 10);

		String specie = "";
		String mod4 = "";
		String dataMod4;
		String speditore = "...........................";
		String cod_speditore = "...........................";
		String comune = "...........................";
		String loc = "...........................";

		ArrayList<Casl_Non_Conformita_Rilevata> listNC = new ArrayList<Casl_Non_Conformita_Rilevata> ();
		ArrayList<ProvvedimentiCASL> listPA = new ArrayList<ProvvedimentiCASL> ();

		org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;
		OrganizationAddress speditoreAddress = null;

		for(GenericBean c : listaCapi){
			specie = listaSpecie.getValueFromId(((Capo)c).getCd_specie());
			specie = (specie != null && !specie.equals("") ? specie : "...........................");
			mod4 = (c.getCd_mod4()  != null && !c.getCd_mod4().equals("") ? c.getCd_mod4()  : "...........................");
			if(c.getCd_data_mod4() != null){
				//dataMod4 =  new Date(c.getCd_data_mod4().getTime()).toString();
				dataMod4 = sdf.format(new Date(c.getCd_data_mod4().getTime()));
			}
			else {
				dataMod4 = "..........................." ;
			}	

			if(c.getCd_id_speditore() > 0){
				speditoreOrg = new org.aspcfs.modules.speditori.base.Organization(db,c.getCd_id_speditore());
			}

			if(speditoreOrg != null){

				if( speditoreOrg.getName() != null && !speditoreOrg.getName().equals("") ){
					speditore = speditoreOrg.getName();
				}

				if( speditoreOrg.getAccountNumber() != null && !speditoreOrg.getAccountNumber().equals("") ){
					cod_speditore = speditoreOrg.getAccountNumber();
				}

				if(speditoreOrg.getAddressList().size() > 0){
					speditoreAddress = (OrganizationAddress) speditoreOrg.getAddressList().get(0);
				}

			}

			if(speditoreAddress != null){

				if( speditoreAddress.getCity() != null && !speditoreAddress.getCity().equals("") ){
					comune = speditoreAddress.getCity();
				}

				if( speditoreAddress.getState() != null && !speditoreAddress.getState().equals("") ){
					loc = speditoreAddress.getState();
				}

			}

			//				speditore = (c.getCd_speditore() != null && !(c.getCd_speditore().equals("")) ? c.getCd_speditore() : "..........................."); 
			//				cod_speditore = (c.getCd_codice_speditore() != null && !(c.getCd_codice_speditore().equals("")) ? c.getCd_codice_speditore() : "...........................");
			//				comune = (c.getCd_provenienza_comune() != null && !(c.getCd_provenienza_comune().equals("")) ? c.getCd_provenienza_comune() : "...........................");			
			//				loc = ((listaRegioni.getValueFromId(c.getCd_provenienza_regione()) != null && !listaRegioni.getValueFromId(c.getCd_provenienza_regione()).equals("")) ? listaRegioni.getValueFromId(c.getCd_provenienza_regione()) : "...........................");

			ListItem li = new ListItem( "specie " + specie + " , con matr. " + ((Capo)c).getCd_matricola() + ", " +
					"proveniente dall'azienda " + speditore + " cod. az. " + cod_speditore + 
					" sita nel comune di " + comune + " in loc. " + loc +" scortato/i dal relativo passaporto e dal" +
					" mod. 4 n. "+ mod4 +" datato "+ dataMod4,FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			li.setAlignment(Element.ALIGN_JUSTIFIED);
			elencoCapiArrivoAlMacello.add(li);	

		}
		ListItem nc = null;
		ListItem pa = null;

		for (int key : hashCapiNC.keySet()){
			nc = new ListItem();
			listNC = hashCapiNC.get(key);
			for (int k =0; k< listNC.size(); k++){
				if(k == 0){
					nc.add(listaNC.getSelectedValue(listNC.get(k).getId_casl_non_conformita()));
				}
				else{
					nc.add(" - " + listaNC.getSelectedValue(listNC.get(k).getId_casl_non_conformita()));
				}
			}
			elencoCapiNC.add(nc);
		}



		document.add( Chunk.NEWLINE );

		for (int k : hashCapiProvvedimenti.keySet()){
			pa = new ListItem();
			listPA = hashCapiProvvedimenti.get(k);
			for (int n =0; n< listPA.size(); n++){
				if(n == 0){
					pa.add(listaPA.getSelectedValue(listPA.get(n).getId_provvedimento()));
				}
				else{
					pa.add(" - " + listaPA.getSelectedValue(listPA.get(n).getId_provvedimento()));
				}
			}
			elencoCapiPA.add(pa);
		}


		document.add(elencoCapiArrivoAlMacello);
		document.add( Chunk.NEWLINE );

		Paragraph p7 = new Paragraph("presentano le seguenti non conformita':\n ",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p7.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p7);

		document.add(elencoCapiNC);

		document.add( Chunk.NEWLINE );

		Paragraph p8 = new Paragraph("Per tale/i non conformita' sono stati adottati i seguenti provvedimenti: ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p8.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p8);

		document.add( Chunk.NEWLINE );

		document.add(elencoCapiPA);

		document.add( Chunk.NEWLINE );

		Paragraph p9 = new Paragraph("Tanto si comunica per gli ulteriori accertamenti ed i seguiti di competenza.\n" +
				"La trasmissione del documento ha valore ufficiale e non si provvedera' ad inviare lo stesso a mezzo posta (L. n.412 art.6 del 30/12/1991 e succ. integ.).", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p9.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p9);

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		Paragraph p10 = new Paragraph("IL VETERINARIO\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p10.setAlignment(Element.ALIGN_RIGHT);
		document.add(p10);

		document.close();

		//Calcolo del progressivo
		Object o = ReflectionUtil.nuovaIstanza(configTipo.getPackageBean()+configTipo.getNomeBean());
		ReflectionUtil.invocaMetodo(configTipo.getPackageAction()+configTipo.getNomeAction(), "gestioneProgressivoModulo", new Class[]{Connection.class, StampeModuli.class, ChiaveModuliMacelli.TIPO_DATA_MACELLO.getClass(), ByteArrayOutputStream.class }, new Object[]{db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO, out});

		ByteArrayOutputStream outputNew = new ByteArrayOutputStream();
		Document documentNew = new Document();
		PdfWriter writerNew = PdfWriter.getInstance(documentNew, outputNew);
		documentNew.open();
		PdfContentByte cbNew = writerNew.getDirectContent();

		// Load existing PDF
		PdfReader readerNew = new PdfReader(out.toByteArray());

		PdfImportedPage page = null;
		for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
			page = writerNew.getImportedPage(readerNew, j); 
			documentNew.newPage();
			cbNew.addTemplate(page, 0, 0);
			if(j == 1){
				cbNew.beginText();
				cbNew.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
				cbNew.showTextAligned(Element.ALIGN_LEFT, 
						"Prot. " + stampeModuli.getProgressivo() + "/" + 
						listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
						sdfYear.format(sdf.parse(dataArrivoMacello)),37F,580F, 0);
				if(stampeModuli.getOldProgressivo() > 0){
					cbNew.showTextAligned(Element.ALIGN_LEFT, 
							"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataArrivoMacello)) + ")",37F,565F, 0);
				}
				cbNew.endText();
			}
		}

		documentNew.close();

		out.reset();
		out.write(outputNew.toByteArray());

	}
	
	
	private void createPDFAnimaliInfettiDa(Connection db, Document document, ByteArrayOutputStream out, ArrayList<GenericBean> listaCapiBrucellosi, Organization macello, String dataMacellazione, String malattia, StampeModuli stampeModuli) throws Exception {

		LookupList listaAsl = new LookupList(db,"lookup_site_id");
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaRegioni  = new LookupList( db, "m_lookup_regioni");

		//Gestione progressivo
		//int progressivoModulo = 0;
		//		stampeModuli.setMalattiaCapo(malattia);
		//		progressivoModulo = stampeModuli.selectProgressivo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MALATTIA);
		//		if(progressivoModulo == 0){
		//			stampeModuli.insert(db);
		//			progressivoModulo = stampeModuli.selectProgressivo(db,ChiaveModuliMacelli.TIPO_DATA_MACELLO_MALATTIA);
		//		}

		Paragraph p = new Paragraph("REGIONE CAMPANIA\nAZIENDA SANITARIA LOCALE "+ listaAsl.getValueFromId(macello.getSiteId())+"\nSERVIZIO VETERINARIO ......... \n" +
				"DISTRETTO SANITARIO DI ........................\n" +
				".................................................\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));

		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);

		document.add( Chunk.NEWLINE );

		Paragraph p2 = new Paragraph("Al Dipartimento di Prevenzione\n" +
				"Servizio Veterinario\n" +
				"la ASL ..................\n" +
				"Fax: ........................\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p2.setAlignment(Element.ALIGN_RIGHT);
		document.add(p2);

		Paragraph p3 = new Paragraph("Alla Regione .................\n" +
				"Assessorato Sanita'\n" +
				"Servizio Veterinario\n" +
				"Fax: ........................\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p3.setAlignment(Element.ALIGN_RIGHT);
		document.add(p3);

		Paragraph p4 = new Paragraph("Alla Regione CAMPANIA\n" +
				"Assessorato Sanita'\n" +
				"Servizio Veterinario (da inviare x email)\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p4.setAlignment(Element.ALIGN_RIGHT);
		document.add(p4);

		Paragraph p5 = new Paragraph("Al Ministero della Salute\n" +
				"Direzione Generale della Sanita'\n" +
				"Animale e del Farmaco Veterinario\n" +
				"Ufficio II\n"+
				"Via Ribotta\n" +
				"ROMA (da inviare x email)\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p5.setAlignment(Element.ALIGN_RIGHT);
		document.add(p5);

		document.add( Chunk.NEWLINE );

		//		Paragraph p6 = new Paragraph("Prot. " + progressivoModulo + 
		//									 "/" + listaAsl.getValueFromId(macello.getSiteId())+ 
		//									 "/" + sdfYear.format(sdf.parse(dataMacellazione))  + 
		//									 "\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		//		p6.setAlignment(Element.ALIGN_LEFT);
		//		document.add(p6);			
		//		
		//		document.add( Chunk.NEWLINE );

		Paragraph p_bold = new Paragraph("OGGETTO: Certificazione del ricevimento degli animali infetti e dell'avvenuta macellazione",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD));
		p_bold.setAlignment(Element.ALIGN_LEFT);
		document.add(p_bold);

		document.add( Chunk.NEWLINE );
		Paragraph p7 = new Paragraph("Si attesta che in data "+dataMacellazione+" i sottoelencati capi della specie:\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p7.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p7);

		document.add( Chunk.NEWLINE );

		com.itextpdf.text.List elencoCapiInfettiBrucellosi = new com.itextpdf.text.List(true, 10);
		com.itextpdf.text.List elencoMatricole = new com.itextpdf.text.List(true, 10);

		String specie = "";
		String speditore = "...........................";
		String cod_speditore = "...........................";
		String comune = "...........................";
		String loc = "...........................";
		String mod4 = "";
		String dataMod4 = "";

		org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;
		OrganizationAddress speditoreAddress = null;

		for(GenericBean c : listaCapiBrucellosi){
			specie = listaSpecie.getValueFromId(((Capo)c).getCd_specie());
			specie = (specie != null && !specie.equals("") ? specie : "...........................");				
			//			speditore = (c.getCd_speditore() != null && !(c.getCd_speditore().equals("")) ? c.getCd_speditore() : "..........................."); 
			//			cod_speditore = (c.getCd_codice_speditore() != null && !(c.getCd_codice_speditore().equals("")) ? c.getCd_codice_speditore() : "...........................");
			//			comune = (c.getCd_provenienza_comune() != null && !(c.getCd_provenienza_comune().equals("")) ? c.getCd_provenienza_comune() : "...........................");			
			//			loc = ((listaRegioni.getValueFromId(c.getCd_provenienza_regione()) != null && !listaRegioni.getValueFromId(c.getCd_provenienza_regione()).equals("")) ? listaRegioni.getValueFromId(c.getCd_provenienza_regione()) : "...........................");

			if(c.getCd_id_speditore() > 0){
				speditoreOrg = new org.aspcfs.modules.speditori.base.Organization(db,c.getCd_id_speditore());
			}

			if(speditoreOrg != null){

				if( speditoreOrg.getName() != null && !speditoreOrg.getName().equals("") ){
					speditore = speditoreOrg.getName();
				}

				if( speditoreOrg.getAccountNumber() != null && !speditoreOrg.getAccountNumber().equals("") ){
					cod_speditore = speditoreOrg.getAccountNumber();
				}

				if(speditoreOrg.getAddressList().size() > 0){
					speditoreAddress = (OrganizationAddress) speditoreOrg.getAddressList().get(0);
				}

			}

			if(speditoreAddress != null){

				if( speditoreAddress.getCity() != null && !speditoreAddress.getCity().equals("") ){
					comune = speditoreAddress.getCity();
				}

				if( speditoreAddress.getState() != null && !speditoreAddress.getState().equals("") ){
					loc = speditoreAddress.getState();
				}

			}

			mod4 = (c.getCd_mod4()  != null && !c.getCd_mod4().equals("") ? c.getCd_mod4()  : "...........................");
			if(c.getCd_data_mod4() != null){
				//dataMod4 =  new Date(c.getCd_data_mod4().getTime()).toString();
				dataMod4 = sdf.format(new Date(c.getCd_data_mod4().getTime()));
			}
			else {
				dataMod4 = "..........................." ;
			}

			ListItem li = new ListItem( "specie " + specie + " proveniente dall'azienda " + speditore + " cod. az. " + cod_speditore + 
					" sita nel comune di " + comune + " in loc. " + loc + " scortato dal" +
					" mod. 4 n. "+ mod4 +" datato "+ dataMod4, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			li.setAlignment(Element.ALIGN_JUSTIFIED);

			ListItem matricole = new ListItem("Matricola "+((Capo)c).getCd_matricola());
			matricole.setAlignment(Element.ALIGN_JUSTIFIED);
			elencoCapiInfettiBrucellosi.add(li);
			elencoMatricole.add(matricole);
		}

		document.add(elencoCapiInfettiBrucellosi);

		document.add( Chunk.NEWLINE );

		Paragraph p8 = new Paragraph(" infetti da "+malattia+" sono giunti in vincolo sanitario presso questo stabilimento di macellazione.\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p8.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p8);

		Paragraph p9 = new Paragraph("I suddetti animali sono stati sottoposti a macellazione in data "+dataMacellazione+"\n" +
				"Elenco degli animali abbattuti: \n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p9.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p9);

		document.add(elencoMatricole);

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		Paragraph p10 = new Paragraph("IL VETERINARIO\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p10.setAlignment(Element.ALIGN_RIGHT);
		document.add(p10);

		document.close();

		stampeModuli.setMalattiaCapo(malattia);

		//Calcolo del progressivo
		gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MALATTIA, out);

		ByteArrayOutputStream outputNew = new ByteArrayOutputStream();
		Document documentNew = new Document();
		PdfWriter writerNew = PdfWriter.getInstance(documentNew, outputNew);
		documentNew.open();
		PdfContentByte cbNew = writerNew.getDirectContent();

		// Load existing PDF
		PdfReader readerNew = new PdfReader(out.toByteArray());

		PdfImportedPage page = null;
		for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
			page = writerNew.getImportedPage(readerNew, j); 
			documentNew.newPage();
			cbNew.addTemplate(page, 0, 0);
			if(j == 1){
				cbNew.beginText();
				cbNew.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
				cbNew.showTextAligned(Element.ALIGN_LEFT, 
						"Prot. " + stampeModuli.getProgressivo() + "/" + 
						listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
						sdfYear.format(sdf.parse(dataMacellazione)),37F,580F, 0);
				if(stampeModuli.getOldProgressivo() > 0){
					cbNew.showTextAligned(Element.ALIGN_LEFT, 
							"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataMacellazione)) + ")",37F,565F, 0);
				}
				cbNew.endText();
			}
		}

		documentNew.close();

		out.reset();
		out.write(outputNew.toByteArray());

	}





	private void createPDFAnimaliGravidi(Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, ArrayList<GenericBean> listaCapiGravidi, String dataMacellazione, StampeModuli stampeModuli) throws Exception{

		LookupList listaRegioni  = new LookupList( db, "m_lookup_regioni");
		LookupList listaAsl = new LookupList(db,"lookup_site_id");

		//Gestione progressivo
		//int progressivoModulo = 0;
		//			progressivoModulo = stampeModuli.selectProgressivo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO);
		//			if(progressivoModulo == 0){
		//				stampeModuli.insert(db);
		//				progressivoModulo = stampeModuli.selectProgressivo(db,ChiaveModuliMacelli.TIPO_DATA_MACELLO);
		//			}

		Paragraph p = new Paragraph("REGIONE CAMPANIA\nAZIENDA SANITARIA LOCALE\nSERVIZIO VETERINARIO "+listaAsl.getValueFromId(macello.getSiteId())+"\n......................................", FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);

		document.add( Chunk.NEWLINE );

		Paragraph p2 = new Paragraph("A: ........................\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p2.setAlignment(Element.ALIGN_RIGHT);
		document.add(p2);

		document.add( Chunk.NEWLINE );

		//			Paragraph p3 = new Paragraph("Prot. " + progressivoModulo + 
		//										 "/" + listaAsl.getValueFromId(macello.getSiteId())+ 
		//										 "/" + sdfYear.format(sdf.parse(dataMacellazione))  + 
		//										 "\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		//			p3.setAlignment(Element.ALIGN_LEFT);
		//			document.add(p3);
		//			
		//			document.add( Chunk.NEWLINE );

		Paragraph p_bold = new Paragraph("OGGETTO: Macellazione animali gravidi\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD));
		p_bold.setAlignment(Element.ALIGN_LEFT);
		document.add(p_bold);

		document.add( Chunk.NEWLINE );

		Paragraph p4 = new Paragraph("L'anno "+dataMacellazione.substring(6,10)+" addi "+dataMacellazione.substring(0,2)+" del mese di "+getMeseFromData(dataMacellazione.substring(3,5))+", presso lo Stabilimento di Macellazione "+macello.getName()+" Riconoscimento CE "+macello.getAccountNumber()+"," +
				"il sottoscritto ....................... Veterinario Dirigente della ASL ..............................,\n" ,FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p4.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p4);

		document.add( Chunk.NEWLINE );

		Paragraph p5 = new Paragraph("ha verificato che ",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p5.setAlignment(Element.ALIGN_CENTER);
		document.add(p5);

		document.add( Chunk.NEWLINE );

		com.itextpdf.text.List elencoMatricole = new com.itextpdf.text.List(true, 10);
		com.itextpdf.text.List elencoDescrizioneAnimali = new com.itextpdf.text.List(true, 10);

		String speditore = "...........................";
		String cod_speditore = "...........................";
		String comune = "...........................";
		String loc = "...........................";
		String mod4 = "";

		org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;
		OrganizationAddress speditoreAddress = null;

		for(GenericBean c : listaCapiGravidi){
			//				speditore = (c.getCd_speditore() != null && !(c.getCd_speditore().equals("")) ? c.getCd_speditore() : "..........................."); 
			//				cod_speditore = (c.getCd_codice_speditore() != null && !(c.getCd_codice_speditore().equals("")) ? c.getCd_codice_speditore() : "...........................");
			//				comune = (c.getCd_provenienza_comune() != null && !(c.getCd_provenienza_comune().equals("")) ? c.getCd_provenienza_comune() : "...........................");			
			//				loc = ((listaRegioni.getValueFromId(c.getCd_provenienza_regione()) != null && !listaRegioni.getValueFromId(c.getCd_provenienza_regione()).equals("")) ? listaRegioni.getValueFromId(c.getCd_provenienza_regione()) : "...........................");

			if(c.getCd_id_speditore() > 0){
				speditoreOrg = new org.aspcfs.modules.speditori.base.Organization(db,c.getCd_id_speditore());
			}

			if(speditoreOrg != null){

				if( speditoreOrg.getName() != null && !speditoreOrg.getName().equals("") ){
					speditore = speditoreOrg.getName();
				}

				if( speditoreOrg.getAccountNumber() != null && !speditoreOrg.getAccountNumber().equals("") ){
					cod_speditore = speditoreOrg.getAccountNumber();
				}

				if(speditoreOrg.getAddressList().size() > 0){
					speditoreAddress = (OrganizationAddress) speditoreOrg.getAddressList().get(0);
				}

			}

			if(speditoreAddress != null){

				if( speditoreAddress.getCity() != null && !speditoreAddress.getCity().equals("") ){
					comune = speditoreAddress.getCity();
				}

				if( speditoreAddress.getState() != null && !speditoreAddress.getState().equals("") ){
					loc = speditoreAddress.getState();
				}

			}

			mod4 = (c.getCd_mod4()  != null && !c.getCd_mod4().equals("") ? c.getCd_mod4()  : "...........................");

			ListItem li = new ListItem( " l'animale  proveniente dall'azienda " + speditore + " cod. az. " + cod_speditore + 
					" sita nel comune di " + comune + " in loc. " + loc + " scortato dal relativo passaporto e " +
					" Mod. 4 n. "+ mod4, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			li.setAlignment(Element.ALIGN_JUSTIFIED);
			elencoDescrizioneAnimali.add(li);
			ListItem matricole = new ListItem(((Capo)c).getCd_matricola());
			elencoMatricole.add(matricole);
		}

		document.add(elencoDescrizioneAnimali);

		document.add( Chunk.NEWLINE );

		Paragraph p6 = new Paragraph("risultano in evidente stato di gravidanza i seguenti animali:\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p6.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p6);

		document.add( Chunk.NEWLINE );

		document.add(elencoMatricole);

		document.add( Chunk.NEWLINE );

		/*Paragraph p7 = new Paragraph("[PARTE DINAMICA ELENCO ANIMALI GRAVIDI] ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p7.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(p7);*/	

		Paragraph p8 = new Paragraph("Tanto si certifica per i doveri di competenza.\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p8.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p8);

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		Paragraph p9 = new Paragraph("Data .......................\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p9.setAlignment(Element.ALIGN_LEFT);
		document.add(p9);

		Paragraph p10 = new Paragraph("IL VETERINARIO DIRIGENTE\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p10.setAlignment(Element.ALIGN_RIGHT);
		document.add(p10);

		document.close();

		//Calcolo del progressivo
		gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO, out);

		ByteArrayOutputStream outputNew = new ByteArrayOutputStream();
		Document documentNew = new Document();
		PdfWriter writerNew = PdfWriter.getInstance(documentNew, outputNew);
		documentNew.open();
		PdfContentByte cbNew = writerNew.getDirectContent();

		// Load existing PDF
		PdfReader readerNew = new PdfReader(out.toByteArray());

		PdfImportedPage page = null;
		for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
			page = writerNew.getImportedPage(readerNew, j); 
			documentNew.newPage();
			cbNew.addTemplate(page, 0, 0);
			if(j == 1){
				cbNew.beginText();
				cbNew.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
				cbNew.showTextAligned(Element.ALIGN_LEFT, 
						"Prot. " + stampeModuli.getProgressivo() + "/" + 
						listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
						sdfYear.format(sdf.parse(dataMacellazione)),37F,680F, 0);
				if(stampeModuli.getOldProgressivo() > 0){
					cbNew.showTextAligned(Element.ALIGN_LEFT, 
							"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataMacellazione)) + ")",37F,665F, 0);
				}
				cbNew.endText();
			}
		}

		documentNew.close();

		out.reset();
		out.write(outputNew.toByteArray());

	}

	private void createPDFVisitaAnteMortem (Connection db, Document doc, PdfWriter wr, ByteArrayOutputStream out, Organization macello, ArrayList<GenericBean> listaCapi, TreeMap<Integer, ArrayList<NonConformita>> hashCapiNC, String dataArrivoMacello,Image logo, StampeModuli stampeModuli) throws Exception{

		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaNC  = new LookupList( db, "m_lookup_tipi_non_conformita");
		LookupList listaPA  = new LookupList( db, "m_lookup_provvedimenti_vam");
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaRazze	= new LookupList( db, "razze_bovini" );

		ArrayList<ByteArrayOutputStream> outputList = new ArrayList<ByteArrayOutputStream>();
		ByteArrayOutputStream output = null;
		Document document = null;
		PdfWriter writer = null;

		ArrayList<NonConformita> listNC = null;
		ListItem singolaNcItext = null;
		com.itextpdf.text.List elencoNcItext = null;

		String specie = "";
		String razza = "";
		String sesso = "";

		//int i = 0;
		for(GenericBean c : listaCapi){

			//				++i;
			//				if(i > 1){
			//					document.newPage();
			//					document.add(logo);
			//				}

			output = new ByteArrayOutputStream(); 
			document = new Document();
			writer = PdfWriter.getInstance(document, output);
			document.open();
			document.add(logo);

			
			String comucazione_to = "";
			
				if (c.isCasl_to_asl_origine())
				{
					comucazione_to  += "ASL ORIGINE \n" ;
				}
				if (c.isCasl_to_proprietario_animale())
				{
					comucazione_to += "PROPRIETARIO ANIMALE \n" ;
				}
				if (c.isCasl_to_azienda_origine())
				{
					comucazione_to += "AZIENDA ORIGINE \n" ;
				}
				if (c.isCasl_to_proprietario_macello())
				{
					comucazione_to += "PROPRIETARIO MACELLO \n" ;
				}
				if (c.isCasl_to_pif())
				{
					comucazione_to += "PIF \n" ;
				}
				if (c.isCasl_to_uvac())
				{
					comucazione_to += "UVAC \n" ;
				}
				if (c.isCasl_to_regione())
				{
					comucazione_to += "REGIONE \n" ;
				}
				if (c.isCasl_to_altro())
				{
					comucazione_to += "ALTRO : \n"+c.getCasl_to_altro_testo(); ;
				}
				if(comucazione_to.equals(""))
				{
					comucazione_to = "..................................." ; 
				}
			//c.getCasl_to_altro_testo;
			
				Paragraph pp = new Paragraph("\n\n\n\n\n\n\n\n\n\n\n", 
						FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));

				pp.setAlignment(Element.ALIGN_CENTER);
				document.add(pp);
			Paragraph p = new Paragraph("REGIONE CAMPANIA\n" +
					"AZIENDA SANITARIA LOCALE " + listaAsl.getValueFromId(macello.getSiteId()) + "\n" +
					"SERVIZIO VETERINARIO\n" +
					"...............................\n" +
					"Unita' Operativa Veterinaria del Distretto ...........\n" +
					"INDIRIZZO .....................................................\n" +
					"TELEFONO .......................... FAX ..........................\n" +
					"MAIL .............................................................", 
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));

			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);

//			Paragraph p1 = new Paragraph("Al Sig ................................. proprietario dell'animale .........\n" +
//					"Al Responsabile dello stabilimento ..............................\n" +
//					"All'ASL ............................\n" +
//					"Al PIF e UVAC ............................................\n" +
//					"..................................................\n" +
//					"..................................................\n" +
//					"..................................................\n" ,FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			
			Paragraph p1 = new Paragraph("Comunicazione a "+comucazione_to ,FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			
		
			
			p1.setAlignment(Element.ALIGN_RIGHT);
			document.add(p1);

			document.add( Chunk.NEWLINE );

			//				Paragraph p2 = new Paragraph("Prot. " + progressivoModulo + 
			//											 "/" + listaAsl.getValueFromId(macello.getSiteId())+ 
			//											 "/" + sdfYear.format(sdf.parse(dataArrivoMacello))  + 
			//											 "\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			//				p2.setAlignment(Element.ALIGN_LEFT);
			//				document.add(p2);

			document.add( Chunk.NEWLINE );
			document.add( Chunk.NEWLINE );

			Paragraph p_bold = new Paragraph("OGGETTO: Evidenze in fase di visita ante mortem presso lo stabilimento "+macello.getName()+ " con numero CE "+macello.getApprovalNumber(),FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD));
			p_bold.setAlignment(Element.ALIGN_LEFT);
			document.add(p_bold);

			document.add( Chunk.NEWLINE );
			document.add( Chunk.NEWLINE );

			specie = listaSpecie.getValueFromId(((Capo)c).getCd_specie());
			specie = (specie != null && !specie.equals("") ? specie : "...........................");
			razza = listaRazze.getValueFromId(((Capo)c).getCd_id_razza());
			razza = (razza != null && !razza.equals("") ? razza : "...........................");
			sesso = ((Capo)c).isCd_maschio() ? "M" : "F";

			Paragraph p3 = new Paragraph("Si comunica che in data "+dataArrivoMacello+" il Servizio Veterinario operante presso lo stabilimento in oggetto ha effettuato la visita ante mortem sull'animale di seguito segnalato:\n\n" +
					"specie " + specie + " , razza " + razza + " , sesso " + sesso + " con matr. " + ((Capo)c).getCd_matricola() + "\n\n" + 
					"Dal controllo effettuato e' stata rilevata la/le seguente/i evidenza/e:\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p3.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(p3);		


			listNC = hashCapiNC.get(c.getId());
			elencoNcItext = new com.itextpdf.text.List(true, 10);

			if(listNC.size() > 0){ //se ci sono non conformita' allora le aggiungiamo al documento
				for (int k = 0; k < listNC.size(); k++){
					singolaNcItext = new ListItem();
					singolaNcItext.add(listaNC.getSelectedValue(listNC.get(k).getId_tipo()));
					elencoNcItext.add(singolaNcItext);
				}
			}
			else{ //altrimenti metto 3 righe vuote nel documento per permettere di scrivere a mano le non conformita'
				for (int k = 0; k < 3; k++){
					singolaNcItext = new ListItem(".........................................");
					elencoNcItext.add(singolaNcItext);
				}
			}

			document.add(elencoNcItext);

			document.add( Chunk.NEWLINE );

			Paragraph p4 = new Paragraph("Per la predetta evidenza e' stato adottato il seguente provvedimento:", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p4.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(p4);

			document.add( Chunk.NEWLINE );

			String lista_PA = listaPA.getSelectedValue(c.getVam_provvedimenti());
			if( lista_PA != null && !lista_PA.equals("")){
				document.add(new Paragraph(lista_PA));
			}
			else {
				document.add(new Paragraph("......................................................."));
			}


			document.add( Chunk.NEWLINE );

			/*INSERIRE LE NOTE!*/
			String note = "";
			note = (c.getVam_provvedimenti_note() != null && !c.getVam_provvedimenti_note().equals("") ? note : "..............................................");;
			document.add(new Paragraph("NOTE: "+note));


			document.add( Chunk.NEWLINE );

			Paragraph p5 = new Paragraph("Data..................\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p5.setAlignment(Element.ALIGN_LEFT);
			document.add(p5);

			Paragraph p6 = new Paragraph("IL VETERINARIO\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p6.setAlignment(Element.ALIGN_RIGHT);
			document.add(p6);

			document.close();

			stampeModuli.setMatricolaCapo(((Capo)c).getCd_matricola());

			//Calcolo del progressivo
			gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA, output);

			ByteArrayOutputStream outputNew = new ByteArrayOutputStream();
			Document documentNew = new Document();
			PdfWriter writerNew = PdfWriter.getInstance(documentNew, outputNew);
			documentNew.open();
			PdfContentByte cbNew = writerNew.getDirectContent();

			// Load existing PDF
			PdfReader readerNew = new PdfReader(output.toByteArray());

			PdfImportedPage page = null;
			for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
				page = writerNew.getImportedPage(readerNew, j); 
				documentNew.newPage();
				cbNew.addTemplate(page, 0, 0);
				if(j == 1){
					cbNew.beginText();
					cbNew.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
					cbNew.showTextAligned(Element.ALIGN_LEFT, 
							"Prot. " + stampeModuli.getProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataArrivoMacello)),37F,580F, 0);
					if(stampeModuli.getOldProgressivo() > 0){
						cbNew.showTextAligned(Element.ALIGN_LEFT, 
								"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
								listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
								sdfYear.format(sdf.parse(dataArrivoMacello)) + ")",37F,565F, 0);
					}
					cbNew.endText();
				}
			}

			documentNew.close();

			outputList.add(outputNew);

		}

		super.mergePDF(outputList, out);

	}

	private void createPDFMorteAnteMacellazione (Connection db, Document doc, PdfWriter wr, ByteArrayOutputStream out, Organization macello, ArrayList<GenericBean> listaCapi, String dataArrivoMacello, Image logo, StampeModuli stampeModuli) throws Exception{

		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaRazze	= new LookupList( db, "razze_bovini" );
		LookupList listaLuoghiVerifica = new LookupList(db, "m_lookup_luoghi_verifica");

		ArrayList<ByteArrayOutputStream> outputList = new ArrayList<ByteArrayOutputStream>();
		ByteArrayOutputStream output = null;
		Document document = null;
		PdfWriter writer = null;

		String specie = "";
		String razza = "";
		String sesso = "";
		String luogo = "";

		//int i = 0;
		//int progressivoModulo = 0;
		for(GenericBean c : listaCapi){

			//Gestione progressivo
			//				stampeModuli.setMatricolaCapo(c.getCd_matricola());
			//				progressivoModulo = stampeModuli.selectProgressivo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA);
			//				if(progressivoModulo == 0){
			//					stampeModuli.insert(db);
			//					progressivoModulo = stampeModuli.selectProgressivo(db,ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA);
			//				}

			//				++i;
			//				if(i > 1){
			//					document.newPage();
			//					document.add(logo);
			//				}

			output = new ByteArrayOutputStream(); 
			document = new Document();
			writer = PdfWriter.getInstance(document, output);
			document.open();
			document.add(logo);

			Paragraph p = new Paragraph("REGIONE CAMPANIA\n" +
					"AZIENDA SANITARIA LOCALE " + listaAsl.getValueFromId(macello.getSiteId()) + "\n" +
					"SERVIZIO VETERINARIO\n" +
					"...............................\n" +
					"Unita' Operativa Veterinaria del Distretto ...........\n" +
					"INDIRIZZO .....................................................\n" +
					"TELEFONO .......................... FAX ..........................\n" +
					"MAIL .............................................................", 
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));

			p.setAlignment(Element.ALIGN_CENTER);
			document.add(p);

			Paragraph p1 = new Paragraph("Allo stabilimento della ditta ......................................\n" +
					"Al Sig ................................ proprietario dell'animale .........\n" +
					"Al Responsabile dello stabilimento ..............................\n" +
					"All'ASL ............................\n" +
					"Al PIF e UVAC ....................................\n" +
					"..................................................\n" +
					"..................................................\n" +
					"..................................................\n" ,FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p1.setAlignment(Element.ALIGN_RIGHT);
			document.add(p1);

			document.add( Chunk.NEWLINE );

			//				Paragraph p2 = new Paragraph("Prot. " + progressivoModulo + 
			//											 "/" + listaAsl.getValueFromId(macello.getSiteId())+ 
			//											 "/" + sdfYear.format(sdf.parse(dataArrivoMacello))  + 
			//											 "\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			//				p2.setAlignment(Element.ALIGN_LEFT);
			//				document.add(p2);
			//				
			//				document.add( Chunk.NEWLINE );
			//				document.add( Chunk.NEWLINE );

			Paragraph p_bold = new Paragraph("OGGETTO: Rilevazione di animale morto in fase antecedente la macellazione (STABILIMENTO "+macello.getName()+" con numero CE "+macello.getApprovalNumber()+")\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD));
			p_bold.setAlignment(Element.ALIGN_LEFT);
			document.add(p_bold);

			document.add( Chunk.NEWLINE );
			document.add( Chunk.NEWLINE );


			specie = listaSpecie.getValueFromId(((Capo)c).getCd_specie());
			specie = (specie != null && !specie.equals("") ? specie : "...........................");
			razza = listaRazze.getValueFromId(((Capo)c).getCd_id_razza());
			razza = (razza != null && !razza.equals("") ? razza : "...........................");
			sesso = ((Capo)c).isCd_maschio() ? "M" : "F";
			luogo = listaLuoghiVerifica.getSelectedValue(c.getMavam_luogo());
			if(c.getMavam_luogo() == 3){
				String desc_luogo = c.getMavam_descrizione_luogo_verifica();
				desc_luogo = (desc_luogo != null && !desc_luogo.equals("") ? desc_luogo : "...........................");
				luogo = luogo + ": " + desc_luogo;
			}

			Paragraph p3 = new Paragraph("Si comunica che in data "+dataArrivoMacello+" il Servizio Veterinario operante presso lo stabilimento in oggetto da controlli effettuati ha rilevato nel luogo \"" +
					luogo + "\"" +
					" che l'animale di seguito descritto e' morto antecedentemente alla macellazione:\n" ,FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p3.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(p3);

			document.add( Chunk.NEWLINE );

			Paragraph p7 = new Paragraph("specie " + specie + " , razza " + razza + " , sesso " + sesso + " con matr. " + ((Capo)c).getCd_matricola(),
					FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p7.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(p7);

			document.add( Chunk.NEWLINE );

			String note = c.getMavam_note();
			note = (note != null && !note.equals("") ? note : "....................................\n....................................\n....................................\n");

			Paragraph p4 = new Paragraph("L'animale sopra indicato e' inviato allo stabilimento in indirizzo per la distruzione.\n" +
					"[NOTE]\n" + note,FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p4.setAlignment(Element.ALIGN_JUSTIFIED);
			document.add(p4);

			document.add( Chunk.NEWLINE );
			document.add( Chunk.NEWLINE );

			Paragraph p5 = new Paragraph("Data..................\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p5.setAlignment(Element.ALIGN_LEFT);
			document.add(p5);

			Paragraph p6 = new Paragraph("IL VETERINARIO\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			p6.setAlignment(Element.ALIGN_RIGHT);
			document.add(p6);

			document.close();

			stampeModuli.setMatricolaCapo(((Capo)c).getCd_matricola());

			//Calcolo del progressivo
			gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA, output);

			ByteArrayOutputStream outputNew = new ByteArrayOutputStream();
			Document documentNew = new Document();
			PdfWriter writerNew = PdfWriter.getInstance(documentNew, outputNew);
			documentNew.open();
			PdfContentByte cbNew = writerNew.getDirectContent();

			// Load existing PDF
			PdfReader readerNew = new PdfReader(output.toByteArray());

			PdfImportedPage page = null;
			for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
				page = writerNew.getImportedPage(readerNew, j); 
				documentNew.newPage();
				cbNew.addTemplate(page, 0, 0);
				if(j == 1){
					cbNew.beginText();
					cbNew.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
					cbNew.showTextAligned(Element.ALIGN_LEFT, 
							"Prot. " + stampeModuli.getProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataArrivoMacello)),37F,580F, 0);
					if(stampeModuli.getOldProgressivo() > 0){
						cbNew.showTextAligned(Element.ALIGN_LEFT, 
								"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
								listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
								sdfYear.format(sdf.parse(dataArrivoMacello)) + ")",37F,565F, 0);
					}
					cbNew.endText();
				}
			}

			documentNew.close();

			outputList.add(outputNew);

		}

		this.mergePDF(outputList, out);

	}

	private void createPDFTrasportoAnimaliInfetti (Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, ArrayList<GenericBean> listaCapi, String dataArrivoMacello, StampeModuli stampeModuli) throws Exception{

		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );

		//Gestione progressivo
		//int progressivoModulo = 0;
		//			progressivoModulo = stampeModuli.selectProgressivo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO);
		//			if(progressivoModulo == 0){
		//				stampeModuli.insert(db);
		//				progressivoModulo = stampeModuli.selectProgressivo(db,ChiaveModuliMacelli.TIPO_DATA_MACELLO);
		//			}

		Paragraph p = new Paragraph("REGIONE CAMPANIA\n" +
				"AZIENDA SANITARIA LOCALE "+ listaAsl.getValueFromId(macello.getSiteId()) + "\n" +
				"SERVIZIO VETERINARIO ............... \n" +
				"DISTRETTO SANITARIO DI ................\n" +
				"...................................................\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 14));

		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		//			Paragraph p2 = new Paragraph("Prot. " + progressivoModulo + 
		//										 "/" + listaAsl.getValueFromId(macello.getSiteId())+ 
		//										 "/" + sdfYear.format(sdf.parse(dataArrivoMacello))  + 
		//										 "\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		//			p2.setAlignment(Element.ALIGN_LEFT);
		//			document.add(p2);
		//			
		//			document.add( Chunk.NEWLINE );
		//			document.add( Chunk.NEWLINE );

		/*[PARAGRAFO PER INDICARE IL MEZZO DI TRASPORTO NEL CONTROLLO DOCUMENTALE]*/

		Paragraph p_bold = new Paragraph("VERBALE DI DISINFEZIONE NEI CASI DI TRASPORTO\n DI ANIMALI INFETTI\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12,Font.BOLD));
		p_bold.setAlignment(Element.ALIGN_CENTER);
		document.add(p_bold);


		document.add( Chunk.NEWLINE );

		String comuneMacello = ( macello.getCity() != null && !macello.getCity().equals("") ? macello.getCity() : ".................................") + 
		( macello.getState() != null && !macello.getState().equals("") ? " (" + macello.getState() + ")" :  " ....." );

		Paragraph p3 = new Paragraph("IL SOTTOSCRITTO DOTT. ................................., VETERINARIO DIRIGENTE DELLA A.S.L. ......................... " +
				"IN SERVIZIO c/o L'IMPIANTO DI MACELLAZIONE "+macello.getName()+" SITO NEL COMUNE DI "+comuneMacello+" RICONOSCIUTO CON n. "+macello.getApprovalNumber()+",\n",FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p3.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p3);

		document.add( Chunk.NEWLINE );

		Paragraph p4 = new Paragraph("DICHIARA" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p4.setAlignment(Element.ALIGN_CENTER);
		document.add(p4);

		document.add( Chunk.NEWLINE );

		String tipo = "";
		String targa = "";
		String specie = "";
		String provenienza = "...........................";

		com.itextpdf.text.List elencoCapi = new com.itextpdf.text.List(true, 10);

		org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;

		for(GenericBean c : listaCapi){

			specie = listaSpecie.getValueFromId(((Capo)c).getCd_specie());
			specie = (specie != null && !specie.equals("") ? specie : "...........................");

			if(c.getCd_id_speditore() > 0){
				speditoreOrg = new org.aspcfs.modules.speditori.base.Organization(db,c.getCd_id_speditore());
			}

			if(speditoreOrg != null){

				if( speditoreOrg.getName() != null && !speditoreOrg.getName().equals("") ){
					provenienza = speditoreOrg.getName();
				}
			}

			//provenienza = (c.getCd_speditore() != null && !(c.getCd_speditore().equals("")) ? c.getCd_speditore() : "...........................");  
			tipo = ( c.getCd_tipo_mezzo_trasporto() != null && !c.getCd_tipo_mezzo_trasporto().equals("") ? c.getCd_tipo_mezzo_trasporto() : "...........................") ;
			targa = ( c.getCd_targa_mezzo_trasporto() != null && !c.getCd_targa_mezzo_trasporto().equals("") ? c.getCd_targa_mezzo_trasporto() : "...........................") ;
			ListItem li = new ListItem ("L'AUTOMEZZO "+ tipo +" TARGATO "+ targa +" SUL QUALE E' STATO TRASPORTATO L'ANIMALE DELLA SPECIE " +specie+" CON MATRICOLA "+ ((Capo)c).getCd_matricola()+" PROVENIENTE DA AZIENDA INFETTA "+provenienza+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			li.setAlignment(Element.ALIGN_JUSTIFIED);
			elencoCapi.add(li);
		}

		Paragraph p5 = new Paragraph("CHE IN DATA "+ dataArrivoMacello , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p5.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p5);

		document.add( Chunk.NEWLINE );

		document.add(elencoCapi);

		document.add( Chunk.NEWLINE );

		Paragraph p6 = new Paragraph(" SONO STATI LAVATI E DISINFETTATI COME DA NORMATIVA VIGENTE.\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p6.setAlignment(Element.ALIGN_JUSTIFIED);
		document.add(p6);

		document.add( Chunk.NEWLINE );
		document.add( Chunk.NEWLINE );

		Paragraph p7 = new Paragraph("IL VETERINARIO\n" , FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
		p7.setAlignment(Element.ALIGN_RIGHT);
		document.add(p7);

		document.close();

		//Calcolo del progressivo
		gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO, out);

		ByteArrayOutputStream outputNew = new ByteArrayOutputStream();
		Document documentNew = new Document();
		PdfWriter writerNew = PdfWriter.getInstance(documentNew, outputNew);
		documentNew.open();
		PdfContentByte cbNew = writerNew.getDirectContent();

		// Load existing PDF
		PdfReader readerNew = new PdfReader(out.toByteArray());

		PdfImportedPage page = null;
		for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
			page = writerNew.getImportedPage(readerNew, j); 
			documentNew.newPage();
			cbNew.addTemplate(page, 0, 0);
			if(j == 1){
				cbNew.beginText();
				cbNew.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
				cbNew.showTextAligned(Element.ALIGN_LEFT, 
						"Prot. " + stampeModuli.getProgressivo() + "/" + 
						listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
						sdfYear.format(sdf.parse(dataArrivoMacello)),37F,660F, 0);
				if(stampeModuli.getOldProgressivo() > 0){
					cbNew.showTextAligned(Element.ALIGN_LEFT, 
							"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataArrivoMacello)) + ")",37F,645F, 0);
				}
				cbNew.endText();
			}
		}

		documentNew.close();

		out.reset();
		out.write(outputNew.toByteArray());


	}
	
public String executeCommandStampeModuli( ActionContext context )
	{
		if (!hasPermission(context, "stabilimenti-stabilimenti-stampe-moduli-view"))
		{
			return ("PermissionError");
		}

		String reportDir = getWebInfPath(context, "template_report");
		String reportDirImg = getWebInfPath(context, "reports");
		String displayName = "";
		Connection db = null;


		try {

			db = this.getConnection(context);
			Organization macello = new Organization( db, Integer.parseInt( context.getParameter( "orgId" ) ) );
			context.getRequest().setAttribute( "OrgDetails", macello );

			HashMap<String, ArrayList<String>> stampe_date = new HashMap<String, ArrayList<String>>();

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Iterator<Object> key = org.aspcfs.modules.macellazioninewsintesis.actions.ApplicationProperties.getApplicationProperties().keySet().iterator();
			while (key.hasNext())
			{
				String kiave = (String) key.next();
				if(kiave.startsWith("GET_DATE"))
				{
					String select1 = org.aspcfs.modules.macellazioninewsintesis.actions.ApplicationProperties.getProperty(kiave);
					PreparedStatement stat1 = db.prepareStatement(select1);
					stat1.setInt( 1,  macello.getOrgId());
					ResultSet res1 = stat1.executeQuery();
					ArrayList<String> listadate= new ArrayList<String>();
					
					while(res1.next()){
						Timestamp thisdata = res1.getTimestamp(1);
						if(thisdata!=null)
						{
							listadate.add(sdf.format(new Date(thisdata.getTime())));
						}
					}
					stampe_date.put(kiave, listadate);
					
				}
			}
			context.getRequest().setAttribute( "DateStampa", stampe_date );

			
			int tipoModulo = Integer.parseInt(context.getParameter("tipoModulo"));

			String data = context.getParameter( "data" + tipoModulo );
			if(data == null || data.equals("")){
				context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
				return "ToStampeModuliOK";
			}
			Timestamp d = new Timestamp( sdf.parse( data ).getTime() );

			//Set info base per StampeModuli
			StampeModuli stampeModuli = new StampeModuli();
			stampeModuli.setTipoModulo(tipoModulo);
			stampeModuli.setDataModulo(d);
			stampeModuli.setAslMacello(macello.getSiteId());
			stampeModuli.setIdMacello(macello.getOrgId());

			ByteArrayOutputStream out = new ByteArrayOutputStream(); 
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, out);

			document.open();

			//RM
			int value_logo = macello.getSiteId();
			Image instanceImg = null;
			if (value_logo == -1 || value_logo == 16) {
				instanceImg = Image.getInstance(reportDir
						+ "/images/regionecampania.jpg");
			} else {
				LookupList asl = new LookupList(db, "lookup_site_id");
				instanceImg = Image.getInstance(reportDirImg + "/images/"
						+ asl.getSelectedValue(value_logo) + ".jpg");
			}

			instanceImg.setAbsolutePosition(6.0F,700.0F);
			instanceImg.scalePercent(30.0F);

			boolean closeDocument = true;

			switch (tipoModulo){
			case 1:	
				document.add(instanceImg);
				String select1 = org.aspcfs.modules.macellazioninewsintesis.actions.ApplicationProperties.getProperty("GET_IDATIDOSI");
				PreparedStatement stat1 = db.prepareStatement(select1);
				stat1.setInt( 1, macello.getOrgId() );
				stat1.setTimestamp( 2, d );
				ResultSet res1 = stat1.executeQuery();
				ArrayList<Capo> listaCapi = new ArrayList<Capo>();
				Capo capo = null;
				while(res1.next()){
					capo = (Capo)GenericBean.load(res1.getString("id"), db, configTipo);
					listaCapi.add(capo);
				}

				if( listaCapi.size() <= 0 ){
					context.getRequest().setAttribute( "messaggio", "Nessun capo con idatidosi macellato il giorno " + data );
					return "ToStampeModuliOK";
				}

				this.createPDFIdatidosi(db, document, writer, out, macello, listaCapi, data, stampeModuli); 
				displayName = "Modello_idatidosi.pdf";
				break;
			case 2:
				document.add(instanceImg);
				String select2 = org.aspcfs.modules.macellazioninewsintesis.actions.ApplicationProperties.getProperty("GET_MODELLO_MARCHI");
				PreparedStatement stat2 = db.prepareStatement( select2 );
				stat2.setInt( 1, macello.getOrgId() );
				stat2.setTimestamp( 2, d );
				ResultSet res2 = stat2.executeQuery();

				ArrayList<GenericBean> listaCapi_marchi = new ArrayList<GenericBean>();
				TreeMap<Integer, ArrayList<Casl_Non_Conformita_Rilevata>> hashCapiNC = new TreeMap<Integer, ArrayList<Casl_Non_Conformita_Rilevata>>();
				TreeMap<Integer, ArrayList<ProvvedimentiCASL>> hashCapiProvvedimenti = new TreeMap<Integer, ArrayList<ProvvedimentiCASL>>();
				GenericBean capo_macello = null;
				while(res2.next()){
					capo_macello = GenericBean.load(res2.getString("id"), db, configTipo );
					listaCapi_marchi.add(capo_macello);
					hashCapiNC.put(capo_macello.getId(), Casl_Non_Conformita_Rilevata.load(capo_macello.getId(), null,db));
					hashCapiProvvedimenti.put(capo_macello.getId(), ProvvedimentiCASL.load(capo_macello.getId(), null,db));
				}

				if( listaCapi_marchi.size() <= 0){
					context.getRequest().setAttribute( "messaggio", "Nessun capo arrivato al macello il giorno " + data );
					return "ToStampeModuliOK";
				}

				this.createPDFArrivoAlMacello(db, document, writer, out, macello, listaCapi_marchi, hashCapiNC, hashCapiProvvedimenti, data, stampeModuli);
				displayName = "Modello_Arrivo_Al_macello.pdf";

				break;
			case 3:  
				document.add(instanceImg);
				String select3 = org.aspcfs.modules.macellazioninewsintesis.actions.ApplicationProperties.getProperty("GET_ANIMALI_INFETTI");
				PreparedStatement stat3 = db.prepareStatement( select3 );
				stat3.setInt( 1, macello.getOrgId() );
				stat3.setTimestamp( 2, d );
				ResultSet res3 = stat3.executeQuery();

				ArrayList<GenericBean> listaCapiBrucellosi = new ArrayList<GenericBean>();
				ArrayList<GenericBean> listaCapiTubercolosi = new ArrayList<GenericBean>();
				GenericBean capo_infetto = null;
				while(res3.next()){
					capo_infetto = GenericBean.load(res3.getString("id"), db, configTipo);
					if(capo_infetto.getCd_macellazione_differita() == 1){
						listaCapiBrucellosi.add(capo_infetto);
					}
					else if(capo_infetto.getCd_macellazione_differita() == 2){
						listaCapiTubercolosi.add(capo_infetto);
					}
				}

				if( listaCapiBrucellosi.size() <= 0 && listaCapiTubercolosi.size() <= 0 ){
					context.getRequest().setAttribute( "messaggio", "Nessun capo infetto macellato il giorno " + data );
					return "ToStampeModuliOK";
				}

				if( listaCapiBrucellosi.size() > 0){
					this.createPDFAnimaliInfettiDa(db, document, out, listaCapiBrucellosi, macello, data, "BRUCELLOSI", stampeModuli );
				}
				if( listaCapiTubercolosi.size() > 0){
					if(listaCapiBrucellosi.size() > 0){
						ByteArrayOutputStream outNew = new ByteArrayOutputStream(); 
						ByteArrayOutputStream out2 = new ByteArrayOutputStream(); 
						Document document2 = new Document();
						PdfWriter writer2 = PdfWriter.getInstance(document2, out2);
						document2.open();
						document2.add(instanceImg);
						this.createPDFAnimaliInfettiDa(db, document2, out2, listaCapiTubercolosi, macello, data, "TUBERCOLOSI", stampeModuli );

						ArrayList<ByteArrayOutputStream> outputList = new ArrayList<ByteArrayOutputStream>();
						outputList.add(out);
						outputList.add(out2);
						mergePDF(outputList, outNew);
						out.reset();
						out.write(outNew.toByteArray());
					}
					else{
						this.createPDFAnimaliInfettiDa(db, document, out, listaCapiTubercolosi, macello, data, "TUBERCOLOSI", stampeModuli );
					}
				}

				displayName = "Modello_Animali_Infetti.pdf";
				break;
			case 4: 
				document.add(instanceImg);
				String select4 = org.aspcfs.modules.macellazioninewsintesis.actions.ApplicationProperties.getProperty("GET_ANIMALI_GRAVIDI");
				PreparedStatement stat4 = db.prepareStatement( select4 );
				stat4.setInt( 1, macello.getOrgId() );
				stat4.setTimestamp( 2, d );
				ResultSet res4 = stat4.executeQuery();

				ArrayList<GenericBean> listaCapiGravidi = new ArrayList<GenericBean>();
				GenericBean capo_gravido = null;

				while(res4.next()){
					capo_gravido = GenericBean.load(res4.getString("id"), db, configTipo);
					listaCapiGravidi.add(capo_gravido);
				}

				if( listaCapiGravidi.size() <= 0){
					context.getRequest().setAttribute( "messaggio", "Nessun capo gravido macellato il giorno " + data );
					return "ToStampeModuliOK";
				}

				this.createPDFAnimaliGravidi(db, document, writer, out, macello, listaCapiGravidi,data,stampeModuli);	
				displayName = "Modello_Animali_Gravidi.pdf";
				break;
			case 5: 
				String select5 = org.aspcfs.modules.macellazioninewsintesis.actions.ApplicationProperties.getProperty("GET_TBC_RILEVAZIONE_MACELLO");
				PreparedStatement stat5 = db.prepareStatement( select5 );
				stat5.setInt( 1, macello.getOrgId() );
				stat5.setTimestamp( 2, d );
				ResultSet res5 = stat5.executeQuery();

				TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditoreTBC = new TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization>();
				TreeMap<Integer, ArrayList<GenericBean>> hashSpeditoreCapiTBC = new TreeMap<Integer, ArrayList<GenericBean>>();
				GenericBean capo_tbc = null;

				while(res5.next()){
					capo_tbc = GenericBean.load(res5.getString("id"), db, configTipo);
					if(capo_tbc.getCd_id_speditore() > -1) {
					
						if(!hashSpeditoreCapiTBC.containsKey(capo_tbc.getCd_id_speditore())){
							hashSpeditoreCapiTBC.put(capo_tbc.getCd_id_speditore(), new ArrayList<GenericBean>());
						}
						hashSpeditoreCapiTBC.get(capo_tbc.getCd_id_speditore()).add(capo_tbc);
						if(!hashSpeditoreTBC.containsKey(capo_tbc.getCd_id_speditore())){
							hashSpeditoreTBC.put(capo_tbc.getCd_id_speditore(), new org.aspcfs.modules.speditori.base.Organization(db,capo_tbc.getCd_id_speditore()) );
						}
					}

					

				}

				if( hashSpeditoreCapiTBC.keySet().size() <= 0){
					context.getRequest().setAttribute( "messaggio", "Nessun capo con piano di risanamento tubercolosi macellato il giorno " + data );
					return "ToStampeModuliOK";
				}

				this.createPDFRilevazioneTBC(context, db, document, writer, out, macello, reportDir, hashSpeditoreTBC,hashSpeditoreCapiTBC,data,stampeModuli);

				closeDocument = false;

				displayName = "Modello_TBCRilevazione_Macelli.pdf";
				break;
			case 6: 
				String select6 = org.aspcfs.modules.macellazioninewsintesis.actions.ApplicationProperties.getProperty("GET_BRC_RILEVAZIONE_MACELLO");
				PreparedStatement stat6 = db.prepareStatement( select6 );
				stat6.setInt( 1, macello.getOrgId() );
				stat6.setTimestamp( 2, d );
				ResultSet res6 = stat6.executeQuery();

				TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditoreBRC = new TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization>();
				TreeMap<Integer, ArrayList<GenericBean>> hashSpeditoreCapiBRC = new TreeMap<Integer, ArrayList<GenericBean>>();
				GenericBean capo_brc = null;

				while(res6.next()){
					capo_brc = GenericBean.load(res6.getString("id"), db, configTipo);
					if(capo_brc.getCd_id_speditore() > -1){
						if(!hashSpeditoreCapiBRC.containsKey(capo_brc.getCd_id_speditore())){
							hashSpeditoreCapiBRC.put(capo_brc.getCd_id_speditore(), new ArrayList<GenericBean>());
						}
						hashSpeditoreCapiBRC.get(capo_brc.getCd_id_speditore()).add(capo_brc);
						if(!hashSpeditoreBRC.containsKey(capo_brc.getCd_id_speditore())){
							hashSpeditoreBRC.put(capo_brc.getCd_id_speditore(), new org.aspcfs.modules.speditori.base.Organization(db,capo_brc.getCd_id_speditore()) );
						}
					}
					
				}

				if( hashSpeditoreCapiBRC.keySet().size() <= 0){
					context.getRequest().setAttribute( "messaggio", "Nessun capo con piano di risanamento brucellosi macellato il giorno " + data );
					return "ToStampeModuliOK";
				}

				this.createPDFRilevazioneBRC(db, document, writer, out, macello, reportDir, hashSpeditoreBRC,hashSpeditoreCapiBRC,data,stampeModuli);

				closeDocument = false;

				displayName = "Modello_BRCRilevazione_Macelli.pdf";

				break;
			case 7:
				String select7 = org.aspcfs.modules.macellazioninewsintesis.actions.ApplicationProperties.getProperty("GET_1033_TBC");
				PreparedStatement stat7 = db.prepareStatement( select7 );
				stat7.setInt( 1, macello.getOrgId() );
				stat7.setTimestamp( 2, d );
				ResultSet res7 = stat7.executeQuery();

				ArrayList<GenericBean> listaCapi_1033_tbc = new ArrayList<GenericBean>();
				TreeMap<Integer, ArrayList<Organi>> hashCapiOrgani = new TreeMap<Integer, ArrayList<Organi>>();
				GenericBean capo_1033_tbc = null;

				while(res7.next()){
					capo_1033_tbc = GenericBean.load(res7.getString("id"), db, configTipo);
					listaCapi_1033_tbc.add(capo_1033_tbc);
					hashCapiOrgani.put(capo_1033_tbc.getId(), Organi.loadByOrgani(capo_1033_tbc.getId(), null,db));
				}

				if( listaCapi_1033_tbc.size() <= 0){
					context.getRequest().setAttribute( "messaggio", "Nessun capo con organi colpiti da tubercolosi macellato il giorno " + data );
					return "ToStampeModuliOK";
				}

				this.createPDF1033TBC(db, document, writer, out, macello, reportDir, listaCapi_1033_tbc,hashCapiOrgani,data,stampeModuli);

				closeDocument = false;
				displayName = "Modello_1033.pdf";

				break;
			case 8: 
				//document.add(instanceImg);
				String select8 = org.aspcfs.modules.macellazioninewsintesis.actions.ApplicationProperties.getProperty("GET_EVIDENZE_VISITA_ANTE_MORTEM");
				PreparedStatement stat8 = db.prepareStatement( select8 );
				stat8.setInt( 1, macello.getOrgId() );
				stat8.setTimestamp( 2, d );
				ResultSet res8 = stat8.executeQuery();

				/*Sicuramente va cambiata la query rispetto al modulo 2---*/
				ArrayList<GenericBean> listaCapi_ante_mortem = new ArrayList<GenericBean>();
				TreeMap<Integer, ArrayList<NonConformita>> hashCapiNCAnteMortem = new TreeMap<Integer, ArrayList<NonConformita>>();
				GenericBean capo_visita_ante_mortem = null;
				while(res8.next()){
					capo_visita_ante_mortem = GenericBean.load(res8.getString("id"), db, configTipo);
					//Recuperare non conformita'
					listaCapi_ante_mortem.add(capo_visita_ante_mortem);
					hashCapiNCAnteMortem.put(capo_visita_ante_mortem.getId(), NonConformita.load(capo_visita_ante_mortem.getId(), null,db));
				}

				if( listaCapi_ante_mortem.size() <= 0){
					context.getRequest().setAttribute( "messaggio", "Nessun capo arrivato al macello il giorno " + data );
					return "ToStampeModuliOK";
				}

				createPDFVisitaAnteMortem(db, document, writer, out, macello, listaCapi_ante_mortem, hashCapiNCAnteMortem, data, instanceImg, stampeModuli);
				closeDocument = false;
				displayName = "Modello_Evidenze_Visita_AnteMortem.pdf";
				break;
			case 9: 
				//document.add(instanceImg);
				String select9 = org.aspcfs.modules.macellazioninewsintesis.actions.ApplicationProperties.getProperty("GET_MORTE_ANTE_MACELLAZIONE");
				PreparedStatement stat9 = db.prepareStatement( select9 );
				stat9.setInt( 1, macello.getOrgId() );
				stat9.setTimestamp( 2, d );
				ResultSet res9 = stat9.executeQuery();

				ArrayList<GenericBean> listaCapiMortiAnte = new ArrayList<GenericBean>();
				GenericBean capo_ante_macellazione = null;

				while(res9.next()){

					capo_ante_macellazione = GenericBean.load(res9.getString("id"), db, configTipo);
					listaCapiMortiAnte.add(capo_ante_macellazione);

				}

				if( listaCapiMortiAnte.size() <= 0 ){
					context.getRequest().setAttribute( "messaggio", "Nessun capo morto ante macellazione il giorno " + data );
					return "ToStampeModuliOK";
				}

				createPDFMorteAnteMacellazione(db, document, writer, out, macello, listaCapiMortiAnte, data, instanceImg, stampeModuli);
				closeDocument = false;
				displayName = "Modello_Morte_Antecedente_Macellazione.pdf";
				break;
			case 10:
				String select10 = org.aspcfs.modules.macellazioninewsintesis.actions.ApplicationProperties.getProperty("GET_ANIMALI_LEB");
				PreparedStatement stat10 = db.prepareStatement( select10 );
				stat10.setInt( 1, macello.getOrgId() );
				stat10.setTimestamp( 2, d );
				ResultSet res10 = stat10.executeQuery();

				TreeMap<Integer, GenericBean> hashCapoLEB = new TreeMap<Integer, GenericBean>();
				TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditoreLEB = new TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization>();

				GenericBean capo_leb = null;

				while(res10.next()){
					capo_leb = GenericBean.load(res10.getString("id"), db, configTipo);
					hashCapoLEB.put(capo_leb.getId(), capo_leb );
					hashSpeditoreLEB.put(capo_leb.getId(), new org.aspcfs.modules.speditori.base.Organization(db,capo_leb.getCd_id_speditore()) );
				}

				if( hashCapoLEB.keySet().size() <= 0){
					context.getRequest().setAttribute( "messaggio", "Nessun capo bovino con piano di risanamento leucosi bovina macellato il giorno " + data );
					return "ToStampeModuliOK";
				}

				this.createPDFAnimaliLEB(db, document, writer, out, macello, reportDir, hashCapoLEB,hashSpeditoreLEB,data,stampeModuli);

				closeDocument = false;

				displayName = "Modello_LEB.pdf";
				break;
			case 11:
				document.add(instanceImg);
				String select11 = org.aspcfs.modules.macellazioninewsintesis.actions.ApplicationProperties.getProperty("GET_TRASPORTI_ANIMALI_INFETTI");
				PreparedStatement stat11 = db.prepareStatement( select11 );
				stat11.setInt( 1, macello.getOrgId() );
				stat11.setTimestamp( 2, d );
				ResultSet res11 = stat11.executeQuery();

				ArrayList<GenericBean> listaCapiTrasportati = new ArrayList<GenericBean>();
				GenericBean capo_trasp = null;

				while(res11.next()){
					capo_trasp = GenericBean.load(res11.getString("id"), db, configTipo);
					listaCapiTrasportati.add(capo_trasp);

				}

				if( listaCapiTrasportati.size() <= 0){
					context.getRequest().setAttribute( "messaggio", "Nessun capo affetto da tubercolosi o brucellosi arrivato al macello il giorno " + data );
					return "ToStampeModuliOK";
				}

				createPDFTrasportoAnimaliInfetti(db, document, writer, out, macello, listaCapiTrasportati, data, stampeModuli);
				displayName = "Modello_Trasporto_Animali_Infetti.pdf";

				break;
			default : System.out.println("Default");
			}

			/*RM: IN CASO DI LETTURA TEMPLATE IL DOCUMENT.CLOSE NN C VUOLE!!!!*/

			if(closeDocument){
				document.close();
			}

			FileDownload fileDownload = new FileDownload();
			fileDownload.setDisplayName(displayName);
			
			GestioneAllegatiMacelli allegato = new GestioneAllegatiMacelli();
			allegato.setBa(out.toByteArray());
			allegato.setOrg(macello.getOrgId());
			allegato.setTipoDocumento("Macelli_"+tipoModulo);
			return allegato.chiamaServerDocumentale(context);
				
			
			//fileDownload.sendFile(context,out.toByteArray(), "application/pdf");

		}
		catch ( ParseException pe ){
			pe.printStackTrace();
			context.getRequest().setAttribute( "messaggio", "Selezionare una data valida" );
			return "ToStampeModuliOK";
		}
		catch (Exception errorMessage){
			context.getRequest().setAttribute( "messaggio", "Si e' verificato un problema" );
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
			return "ToStampeModuliOK";
		}
		finally
		{
			this.freeConnection(context, db);
		}


		//return "-none-";
	}
	
	private void createPDF1033TBC(Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, String reportDir, ArrayList<GenericBean> listaCapi_1033_tbc, TreeMap<Integer, ArrayList<Organi>> hashCapiOrgani, String dataMacellazione, StampeModuli stampeModuli) throws Exception{

		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaRazze	= new LookupList( db, "razze_bovini" );
		LookupList listaOrgani	= new LookupList( db, "m_lookup_organi" );
		LookupList listaStadi  = new LookupList( db, "m_lookup_patologie_organi");

		PdfReader reader_1033 = null;
		PdfStamper stamper_1033 = null;
		ByteArrayOutputStream output = null;
		ByteArrayOutputStream outputNew = null;
		Document documentNew = null;
		PdfReader readerNew = null;
		PdfWriter writerNew = null;
		PdfContentByte cb = null;
		PdfImportedPage page = null;
		ArrayList<ByteArrayOutputStream> outputList = new ArrayList<ByteArrayOutputStream>();

		String comuneMacello = ( macello.getCity() != null && !macello.getCity().equals("") ? macello.getCity() : "") + 
		( macello.getState() != null && !macello.getState().equals("") ? " (" + macello.getState() + ")" :  "" );


		int i;
		for(GenericBean capo_1033_tbc : listaCapi_1033_tbc){
			output = new ByteArrayOutputStream(); 
			reader_1033 = new PdfReader(reportDir + "template_1033.pdf");
			stamper_1033 = new PdfStamper(reader_1033, output); //per l'acrofields

			String specie = listaSpecie.getValueFromId(((Capo)capo_1033_tbc).getCd_specie());
			String razza = listaRazze.getValueFromId(((Capo)capo_1033_tbc).getCd_id_razza());

			//Set dei campi
			AcroFields form = stamper_1033.getAcroFields();
			form.setField("ragione_sociale", macello.getName());
			form.setField("asl",listaAsl.getSelectedValue(macello.getSiteId()));
			form.setField("specie",specie);
			form.setField("razza",razza);
			form.setField("matricola",((Capo)capo_1033_tbc).getCd_matricola());
			form.setField("comune", comuneMacello);
			form.setField("data_macellazione",dataMacellazione);
			form.setField("note",capo_1033_tbc.getCd_note());
			form.setField("veterinario_1",capo_1033_tbc.getCd_veterinario_1());
			form.setField("veterinario_2",capo_1033_tbc.getCd_veterinario_2());
			form.setField("veterinario_3",capo_1033_tbc.getCd_veterinario_3());
			if(((Capo)capo_1033_tbc).getCd_data_nascita() != null){
				if(capo_1033_tbc.getVpm_data().getTime() - ((Capo)((Capo)capo_1033_tbc)).getCd_data_nascita().getTime() <= 2*365*24*60*60*1000L ){
					form.setField("eta_inf", "Yes");
				}
				else{
					form.setField("eta_sup", "Yes");
				}
			}
			if(((Capo)capo_1033_tbc).isCd_maschio()){
				form.setField("sesso_m","Yes");
			}
			else {
				form.setField("sesso_f","Yes");
			}

			String organi = "";
			String stadi = "";
			i = 0;
			for(Organi o : hashCapiOrgani.get(capo_1033_tbc.getId())){
				i++;
				if(i == 1){
					organi = organi + "1." + listaOrgani.getSelectedValue(o.getLcso_organo());
					stadi = stadi + "1." + listaStadi.getSelectedValue(o.getLcso_patologia());
				}
				else{
					organi = organi + " - " + i + "." + listaOrgani.getSelectedValue(o.getLcso_organo());
					stadi = stadi + " - " + i + "." + listaStadi.getSelectedValue(o.getLcso_patologia());
				}

			}

			form.setField("organi", organi);
			form.setField("stadi", stadi);
			stamper_1033.setFormFlattening(true);
			stamper_1033.close();


			//Set info aggiuntive per StampeModuli
			stampeModuli.setMatricolaCapo(((Capo)capo_1033_tbc).getCd_matricola());

			//Calcolo del progressivo
			gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA, output);

			outputNew = new ByteArrayOutputStream();
			documentNew = new Document();
			writerNew = PdfWriter.getInstance(documentNew, outputNew);
			documentNew.open();
			cb = writerNew.getDirectContent();

			readerNew = new PdfReader(output.toByteArray());

			for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
				page = writerNew.getImportedPage(readerNew, j); 
				documentNew.newPage();
				cb.addTemplate(page, 0, 0);
				if(j == 1){
					cb.beginText();
					cb.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
					cb.showTextAligned(Element.ALIGN_LEFT, 
							"Prot. " + stampeModuli.getProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataMacellazione)),56F,600F, 0);
					if(stampeModuli.getOldProgressivo() > 0){
						cb.showTextAligned(Element.ALIGN_LEFT, 
								"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
								listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
								sdfYear.format(sdf.parse(dataMacellazione)) + ")",56F,587F, 0);
					}
					cb.endText();
				}
			}

			documentNew.close();

			outputList.add(outputNew);		

		}

		mergePDF(outputList, out);

	}


	private void createPDFRilevazioneTBC(ActionContext context, Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, String reportDir, TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditore, TreeMap<Integer, ArrayList<GenericBean>> hashSpeditoreCapi, String dataMacellazione, StampeModuli stampeModuli) throws Exception{

		//lookup
		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaCategorieBovine	= new LookupList( db, "m_lookup_specie_categorie_bovine" );
		LookupList listaCategorieBufaline	= new LookupList( db, "m_lookup_specie_categorie_bufaline" );
		LookupList listaRazze	= new LookupList( db, "razze_bovini" );
		LookupList listaRegioni  = new LookupList( db, "m_lookup_regioni"); 
		LookupList listaPatologieOrgani  = new LookupList( db, "m_lookup_patologie_organi");

		PdfReader reader_1033 = null;
		PdfStamper stamper_1033 = null;
		AcroFields form = null;
		ByteArrayOutputStream output = null;
		ByteArrayOutputStream outputNew = null;
		Document documentNew = null;
		PdfReader readerNew = null;
		PdfWriter writerNew = null;
		PdfContentByte cb = null;
		PdfImportedPage page = null;
		ArrayList<ByteArrayOutputStream> outputList = new ArrayList<ByteArrayOutputStream>();

		org.aspcfs.modules.speditori.base.Organization speditore = null;
		OrganizationAddress speditoreAddress = null;
		HashMap<String, Integer> hashCategoriaNumeroVisitati = null;
		HashMap<String, Integer> hashCategoriaNumeroInfetti = null;
		TreeMap<Integer, ArrayList<Organi>> hashCapoOrganiTBC =  null; 
		TreeMap<Integer, ArrayList<Campione>> hashCapoCampioni =  null;
		ArrayList<GenericBean> listaCapi = null;


		//ciclo sugli speditori
		for(int idSpeditore : hashSpeditore.keySet()){
			output = new ByteArrayOutputStream(); 
			reader_1033 = new PdfReader(reportDir + "template_TBCRilevazioneMacello.pdf");
			stamper_1033 = new PdfStamper(reader_1033, output); //per l'acrofields

			//Gestione progressivo
			//			stampeModuli.setIdSpeditore(idSpeditore);
			//			progressivoModulo = stampeModuli.selectProgressivo(db, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE);
			//			if(progressivoModulo == 0){
			//				stampeModuli.insert(db);
			//				progressivoModulo = stampeModuli.selectProgressivo(db,ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE);
			//			}

			speditore = hashSpeditore.get(idSpeditore);
			if(speditore.getAddressList().size() > 0){
				speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
			}
			listaCapi = hashSpeditoreCapi.get(idSpeditore);
			hashCapoOrganiTBC = new TreeMap<Integer, ArrayList<Organi>>();
			hashCapoCampioni = new TreeMap<Integer, ArrayList<Campione>>();

			hashCategoriaNumeroVisitati = new HashMap<String, Integer>();
			hashCategoriaNumeroInfetti = new HashMap<String, Integer>();

			//Inizializzazione degli hash
			for(int codeCat : listaCategorieBovine.getCodes() ){
				hashCategoriaNumeroVisitati.put("NUM_1_" + codeCat, 0);
				hashCategoriaNumeroInfetti.put("NUM_1_" + codeCat, 0);
			}
			hashCategoriaNumeroVisitati.put("NUM_1_ALTRO", 0);
			hashCategoriaNumeroInfetti.put("NUM_1_ALTRO", 0);

			for(int codeCat : listaCategorieBufaline.getCodes() ){
				hashCategoriaNumeroVisitati.put("NUM_5_" + codeCat, 0);
				hashCategoriaNumeroInfetti.put("NUM_5_" + codeCat, 0);
			}
			hashCategoriaNumeroVisitati.put("NUM_5_ALTRO", 0);
			hashCategoriaNumeroInfetti.put("NUM_5_ALTRO", 0);

			//Valorizzazione degli hash
			int num = 0;
			for(GenericBean c : listaCapi){

				//se bovini
				if( ((Capo)c).getCd_specie() == 1 ){
					//se e' specificata la categoria
					if(hashCategoriaNumeroVisitati.containsKey("NUM_1_" + ((Capo)c).getCd_categoria_bovina())){
						num = hashCategoriaNumeroVisitati.get("NUM_1_" + ((Capo)c).getCd_categoria_bovina());
						hashCategoriaNumeroVisitati.put("NUM_1_" + ((Capo)c).getCd_categoria_bovina(), ++num);
					}
					else{
						num = hashCategoriaNumeroVisitati.get("NUM_1_ALTRO");
						hashCategoriaNumeroVisitati.put("NUM_1_ALTRO", ++num);
					}

					//se infetto
					if(c.getCd_macellazione_differita() == 2){
						if(hashCategoriaNumeroInfetti.containsKey("NUM_1_" + ((Capo)c).getCd_categoria_bovina())){
							num = hashCategoriaNumeroInfetti.get("NUM_1_" + ((Capo)c).getCd_categoria_bovina());
							hashCategoriaNumeroInfetti.put("NUM_1_" + ((Capo)c).getCd_categoria_bovina(), ++num);
						}
						else{
							num = hashCategoriaNumeroInfetti.get("NUM_1_ALTRO");
							hashCategoriaNumeroInfetti.put("NUM_1_ALTRO", ++num);
						}
					}
				}
				//se bufalini
				else if(((Capo)c).getCd_specie() == 5){
					//se e' specificata la categoria
					if(hashCategoriaNumeroVisitati.containsKey("NUM_5_" + ((Capo)c).getCd_categoria_bufalina())){
						num = hashCategoriaNumeroVisitati.get("NUM_5_" + ((Capo)c).getCd_categoria_bufalina());
						hashCategoriaNumeroVisitati.put("NUM_5_" + ((Capo)c).getCd_categoria_bufalina(), ++num);
					}
					else{
						num = hashCategoriaNumeroVisitati.get("NUM_5_ALTRO");
						hashCategoriaNumeroVisitati.put("NUM_5_ALTRO", ++num);
					}

					//se infetto
					if(c.getCd_macellazione_differita() == 2){
						if(hashCategoriaNumeroInfetti.containsKey("NUM_5_" + ((Capo)c).getCd_categoria_bufalina())){
							num = hashCategoriaNumeroInfetti.get("NUM_5_" + ((Capo)c).getCd_categoria_bufalina());
							hashCategoriaNumeroInfetti.put("NUM_5_" + ((Capo)c).getCd_categoria_bufalina(), ++num);
						}
						else{
							num = hashCategoriaNumeroInfetti.get("NUM_5_ALTRO");
							hashCategoriaNumeroInfetti.put("NUM_5_ALTRO", ++num);
						}
					}
				}

				if(c.getCd_macellazione_differita() == 2){
					hashCapoOrganiTBC.put(c.getId(), Organi.loadOrganiTBC(c.getId(), db, configTipo));
					hashCapoCampioni.put(c.getId(), Campione.loadCampioniTBC(c.getId(), db, configTipo)) ;
				}

			}

			//Set dei campi
			form = stamper_1033.getAcroFields();

			//form.setField("progressivo_modulo", "PROGRESSIVO_DA_SETTARE" + "/" + listaAsl.getValueFromId(macello.getSiteId())+ "/" + sdfYear.format(sdf.parse(dataMacellazione)));
			form.setField("codice_az", speditore.getAccountNumber());
			form.setField("ragione_sociale", speditore.getName());
			form.setField("proprietario", speditore.getNomeRappresentante());

			if(speditoreAddress != null){
				form.setField("comune_prop", speditoreAddress.getCity());
				String provincia = speditoreAddress.getState();

				// ipotizzo che la provincia sia del tipo yy...y(xx), 
				// quindi se anche yy...y fosse di lunghezza 1, il totale e' maggiore di 4
				if(provincia != null && provincia.length() > 4 ){ 
					String siglaProvincia = provincia.substring(provincia.length() - 3, provincia.length() - 1);
					form.setField("prov_prop", siglaProvincia);
				}

			}

			form.setField("asl_prop", listaAsl.getSelectedValue(speditore.getSiteId()));
			//campi relativi al numero e tipo di animali esaminati
			for(String chiave : hashCategoriaNumeroVisitati.keySet()){
				form.setField(chiave, "" + hashCategoriaNumeroVisitati.get(chiave));
				form.setField(chiave + "_BIS", "" + hashCategoriaNumeroVisitati.get(chiave));
			}

			//campi relativi al numero e tipo di animali infetti
			for(String chiave : hashCategoriaNumeroInfetti.keySet()){
				form.setField(chiave + "_INFETTI", "" + hashCategoriaNumeroInfetti.get(chiave));
			}

			//campi relativi agli organi sequestrati e agli organi prelevati(campioni)
			int i = 0;
			for(GenericBean c : listaCapi){
				//solo i capi infetti
				if(c.getCd_macellazione_differita() == 2){
					i++;
					form.setField("MATRICOLA_" + i, ((Capo)c).getCd_matricola());
					if(((Capo)c).getCd_data_nascita() != null){
						form.setField("DATA_NASCITA_" + i, sdf.format(((Capo)c).getCd_data_nascita() ));
					}
					for(Organi o : hashCapoOrganiTBC.get(c.getId())){
						form.setField("ORGANO_" + o.getLcso_organo() + "_" + i , "Yes");
					}
					for(Campione cmp : hashCapoCampioni.get(c.getId())){
						form.setField("CAMPIONE_" + cmp.getMatrice() + "_" + i , "Yes");
					}
				}
			}

			stamper_1033.setFormFlattening(true);
			stamper_1033.close();

			//Set info aggiuntive per StampeModuli
			stampeModuli.setIdSpeditore(idSpeditore);

			//Calcolo del progressivo
			gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE, output);

			outputNew = new ByteArrayOutputStream();
			documentNew = new Document();
			writerNew = PdfWriter.getInstance(documentNew, outputNew);
			documentNew.open();
			cb = writerNew.getDirectContent();

			readerNew = new PdfReader(output.toByteArray());

			for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
				page = writerNew.getImportedPage(readerNew, j); 
				documentNew.newPage();
				cb.addTemplate(page, 0, 0);
				if(j == 1){
					cb.beginText();
					cb.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
					cb.showTextAligned(Element.ALIGN_LEFT, 
							"Prot. " + stampeModuli.getProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataMacellazione)),56F,740F, 0);
					if(stampeModuli.getOldProgressivo() > 0){
						cb.showTextAligned(Element.ALIGN_LEFT, 
								"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
								listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
								sdfYear.format(sdf.parse(dataMacellazione)) + ")",56F,727F, 0);
					}
					cb.endText();
				}
			}

			documentNew.close();

			outputList.add(outputNew);

		}
		//fine ciclo sugli speditori

		mergePDF(outputList, out);

	}


	private void createPDFRilevazioneBRC(Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, String reportDir, TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditore, TreeMap<Integer, ArrayList<GenericBean>> hashSpeditoreCapi, String dataMacellazione, StampeModuli stampeModuli) throws Exception{

		//lookup
		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaCategorieBovine	= new LookupList( db, "m_lookup_specie_categorie_bovine" );
		LookupList listaCategorieBufaline	= new LookupList( db, "m_lookup_specie_categorie_bufaline" );
		LookupList listaRazze	= new LookupList( db, "razze_bovini" );
		LookupList listaRegioni  = new LookupList( db, "m_lookup_regioni"); 
		LookupList listaPatologieOrgani  = new LookupList( db, "m_lookup_patologie_organi");

		PdfReader reader_1033 = null;
		PdfStamper stamper_1033 = null;
		ByteArrayOutputStream output = null;
		ByteArrayOutputStream outputNew = null;
		Document documentNew = null;
		PdfReader readerNew = null;
		PdfWriter writerNew = null;
		PdfContentByte cb = null;
		PdfImportedPage page = null;
		ArrayList<ByteArrayOutputStream> outputList = new ArrayList<ByteArrayOutputStream>();

		org.aspcfs.modules.speditori.base.Organization speditore = null;
		HashMap<String, Integer> hashCategoriaNumeroVisitati = null;
		HashMap<String, Integer> hashCategoriaNumeroInfetti = null;
		TreeMap<Integer, ArrayList<Organi>> hashCapoOrganiBRC =  null; 
		TreeMap<Integer, ArrayList<Campione>> hashCapoCampioni =  null;

		//ciclo sugli speditori
		for(int idSpeditore : hashSpeditore.keySet()){
			output = new ByteArrayOutputStream(); 
			reader_1033 = new PdfReader(reportDir + "template_BRCRilevazioneMacello.pdf");
			stamper_1033 = new PdfStamper(reader_1033, output); //per l'acrofields

			speditore = hashSpeditore.get(idSpeditore);
			OrganizationAddress speditoreAddress = null;
			if(speditore.getAddressList().size() > 0){
				speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
			}
			ArrayList<GenericBean> listaCapi = hashSpeditoreCapi.get(idSpeditore);
			hashCapoOrganiBRC = new TreeMap<Integer, ArrayList<Organi>>();
			hashCapoCampioni = new TreeMap<Integer, ArrayList<Campione>>();

			hashCategoriaNumeroVisitati = new HashMap<String, Integer>();
			hashCategoriaNumeroInfetti = new HashMap<String, Integer>();

			//Inizializzazione degli hash
			for(int codeCat : listaCategorieBovine.getCodes() ){
				hashCategoriaNumeroVisitati.put("NUM_1_" + codeCat, 0);
				hashCategoriaNumeroInfetti.put("NUM_1_" + codeCat, 0);
			}
			hashCategoriaNumeroVisitati.put("NUM_1_ALTRO", 0);
			hashCategoriaNumeroInfetti.put("NUM_1_ALTRO", 0);

			for(int codeCat : listaCategorieBufaline.getCodes() ){
				hashCategoriaNumeroVisitati.put("NUM_5_" + codeCat, 0);
				hashCategoriaNumeroInfetti.put("NUM_5_" + codeCat, 0);
			}
			hashCategoriaNumeroVisitati.put("NUM_5_ALTRO", 0);
			hashCategoriaNumeroInfetti.put("NUM_5_ALTRO", 0);

			//Valorizzazione degli hash
			int num = 0;
			for(GenericBean c : listaCapi){

				//se bovini
				if( ((Capo)c).getCd_specie() == 1 ){
					//se e' specificata la categoria
					if(hashCategoriaNumeroVisitati.containsKey("NUM_1_" + ((Capo)c).getCd_categoria_bovina())){
						num = hashCategoriaNumeroVisitati.get("NUM_1_" + ((Capo)c).getCd_categoria_bovina());
						hashCategoriaNumeroVisitati.put("NUM_1_" + ((Capo)c).getCd_categoria_bovina(), ++num);
					}
					else{
						num = hashCategoriaNumeroVisitati.get("NUM_1_ALTRO");
						hashCategoriaNumeroVisitati.put("NUM_1_ALTRO", ++num);
					}

					//se infetto
					if(c.getCd_macellazione_differita() == 1){
						if(hashCategoriaNumeroInfetti.containsKey("NUM_1_" + ((Capo)c).getCd_categoria_bovina())){
							num = hashCategoriaNumeroInfetti.get("NUM_1_" + ((Capo)c).getCd_categoria_bovina());
							hashCategoriaNumeroInfetti.put("NUM_1_" + ((Capo)c).getCd_categoria_bovina(), ++num);
						}
						else{
							num = hashCategoriaNumeroInfetti.get("NUM_1_ALTRO");
							hashCategoriaNumeroInfetti.put("NUM_1_ALTRO", ++num);
						}
					}
				}
				//se bufalini
				else if(((Capo)c).getCd_specie() == 5){
					//se e' specificata la categoria
					if(hashCategoriaNumeroVisitati.containsKey("NUM_5_" + ((Capo)c).getCd_categoria_bufalina())){
						num = hashCategoriaNumeroVisitati.get("NUM_5_" + ((Capo)c).getCd_categoria_bufalina());
						hashCategoriaNumeroVisitati.put("NUM_5_" + ((Capo)c).getCd_categoria_bufalina(), ++num);
					}
					else{
						num = hashCategoriaNumeroVisitati.get("NUM_5_ALTRO");
						hashCategoriaNumeroVisitati.put("NUM_5_ALTRO", ++num);
					}

					//se infetto
					if(c.getCd_macellazione_differita() == 1){
						if(hashCategoriaNumeroInfetti.containsKey("NUM_5_" + ((Capo)c).getCd_categoria_bufalina())){
							num = hashCategoriaNumeroInfetti.get("NUM_5_" + ((Capo)c).getCd_categoria_bufalina());
							hashCategoriaNumeroInfetti.put("NUM_5_" + ((Capo)c).getCd_categoria_bufalina(), ++num);
						}
						else{
							num = hashCategoriaNumeroInfetti.get("NUM_5_ALTRO");
							hashCategoriaNumeroInfetti.put("NUM_5_ALTRO", ++num);
						}
					}
				}

				if(c.getCd_macellazione_differita() == 1){
					hashCapoOrganiBRC.put(c.getId(), Organi.loadOrganiBRC(c.getId(), db, configTipo));
					hashCapoCampioni.put(c.getId(), Campione.loadCampioniBRC(c.getId(), db, configTipo)) ;
				}

			}

			//Set dei campi
			AcroFields form = stamper_1033.getAcroFields();

			form.setField("codice_az", speditore.getAccountNumber());
			form.setField("ragione_sociale", speditore.getName());
			form.setField("proprietario", speditore.getNomeRappresentante());

			if(speditoreAddress != null){
				form.setField("comune_prop", speditoreAddress.getCity());
				String provincia = speditoreAddress.getState();

				// ipotizzo che la provincia sia del tipo yy...y(xx), 
				// quindi se anche yy...y fosse di lunghezza 1, il totale e' maggiore di 4
				if(provincia != null && provincia.length() > 4 ){ 
					String siglaProvincia = provincia.substring(provincia.length() - 3, provincia.length() - 1);
					form.setField("prov_prop", siglaProvincia);
				}

			}

			form.setField("asl_prop", listaAsl.getSelectedValue(speditore.getSiteId()));

			//campi relativi al numero e tipo di animali esaminati
			for(String chiave : hashCategoriaNumeroVisitati.keySet()){
				form.setField(chiave, "" + hashCategoriaNumeroVisitati.get(chiave));
			}

			//campi relativi al numero e tipo di animali infetti
			for(String chiave : hashCategoriaNumeroInfetti.keySet()){
				form.setField(chiave + "_INFETTI", "" + hashCategoriaNumeroInfetti.get(chiave));
			}

			//campi relativi agli organi sequestrati e agli organi prelevati(campioni)
			int i = 0;
			for(GenericBean c : listaCapi){
				//solo i capi infetti
				if(c.getCd_macellazione_differita() == 1){
					i++;
					form.setField("MATRICOLA_" + i, ((Capo)c).getCd_matricola());
					if(((Capo)c).getCd_data_nascita() != null){
						form.setField("DATA_NASCITA_" + i, sdf.format(((Capo)c).getCd_data_nascita() ));
					}
					form.setField("CAT_" + ((Capo)c).getCd_specie() + "_" + ((Capo)c).getCd_categoria_bovina() + "_" + i, "Yes");
					form.setField("CAT_" + ((Capo)c).getCd_specie() + "_" + ((Capo)c).getCd_categoria_bufalina() + "_" + i, "Yes");
					for(Organi o : hashCapoOrganiBRC.get(c.getId())){
						form.setField("ORGANO_" + o.getLcso_organo() + "_" + i , "Yes");
					}
					for(Campione cmp : hashCapoCampioni.get(c.getId())){
						form.setField("CAMPIONE_" + cmp.getMatrice() + "_" + i , "Yes");
					}
				}
			}

			stamper_1033.setFormFlattening(true);
			stamper_1033.close();

			//Set info aggiuntive per StampeModuli
			stampeModuli.setIdSpeditore(idSpeditore);

			//Calcolo del progressivo
			gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO_SPEDITORE, output);

			outputNew = new ByteArrayOutputStream();
			documentNew = new Document();
			writerNew = PdfWriter.getInstance(documentNew, outputNew);
			documentNew.open();
			cb = writerNew.getDirectContent();

			readerNew = new PdfReader(output.toByteArray());

			for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
				page = writerNew.getImportedPage(readerNew, j); 
				documentNew.newPage();
				cb.addTemplate(page, 0, 0);
				if(j == 1){
					cb.beginText();
					cb.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
					cb.showTextAligned(Element.ALIGN_LEFT, 
							"Prot. " + stampeModuli.getProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataMacellazione)),56F,740F, 0);
					if(stampeModuli.getOldProgressivo() > 0){
						cb.showTextAligned(Element.ALIGN_LEFT, 
								"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
								listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
								sdfYear.format(sdf.parse(dataMacellazione)) + ")",56F,727F, 0);
					}
					cb.endText();
				}
			}

			documentNew.close();

			outputList.add(outputNew);
		}
		//fine ciclo sugli speditori

		mergePDF(outputList, out);

	}


	private void createPDFAnimaliLEB(Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, String reportDir, TreeMap<Integer, GenericBean> hashCapo, TreeMap<Integer, org.aspcfs.modules.speditori.base.Organization> hashSpeditore, String dataMacellazione, StampeModuli stampeModuli) throws Exception{

		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaRazze	= new LookupList( db, "razze_bovini" );
		LookupList listaRegioni  = new LookupList( db, "m_lookup_regioni");

		PdfReader reader_1033 = null;
		PdfStamper stamper_1033 = null;
		ByteArrayOutputStream output = null;
		ByteArrayOutputStream outputNew = null;
		Document documentNew = null;
		PdfReader readerNew = null;
		PdfWriter writerNew = null;
		PdfContentByte cb = null;
		PdfImportedPage page = null;
		ArrayList<ByteArrayOutputStream> outputList = new ArrayList<ByteArrayOutputStream>();

		org.aspcfs.modules.speditori.base.Organization speditore = null;
		GenericBean capo = null;
		for(int idCapo : hashCapo.keySet()){
			output = new ByteArrayOutputStream(); 
			reader_1033 = new PdfReader(reportDir + "template_LEB.pdf");
			stamper_1033 = new PdfStamper(reader_1033, output); //per l'acrofields

			capo = hashCapo.get(idCapo);
			speditore = hashSpeditore.get(idCapo);

			OrganizationAddress speditoreAddress = null;
			if(speditore.getAddressList().size() > 0){
				speditoreAddress = (OrganizationAddress)(speditore.getAddressList().get(0));
			}


			//Set dei campi
			AcroFields form = stamper_1033.getAcroFields(); 
			form.setField("codice_az", speditore.getAccountNumber());
			form.setField("ragione_sociale", speditore.getName());
			form.setField("proprietario", speditore.getNomeRappresentante());

			if(speditoreAddress != null){
				form.setField("comune_prop", speditoreAddress.getCity());
				String provincia = speditoreAddress.getState();

				// ipotizzo che la provincia sia del tipo yy...y(xx),
				// quindi se anche yy...y fosse di lunghezza 1, il totale e' maggiore di 4
				if(provincia != null && provincia.length() > 4 ){
					String siglaProvincia = provincia.substring(provincia.length() - 3, provincia.length() - 1);
					form.setField("prov_prop", siglaProvincia);
				}      
			}

			form.setField("asl_prop", listaAsl.getSelectedValue(speditore.getSiteId()));

			String razza = listaRazze.getValueFromId(((Capo)capo).getCd_id_razza());
			String comuneMacello = macello.getCity();
			String provinciaMacello = macello.getState();
			form.setField("asl_prov",listaAsl.getSelectedValue(speditore.getSiteId()));
			form.setField("ragione_sociale", macello.getName()+ " C.E: "+macello.getApprovalNumber());
			form.setField("comune_macello", comuneMacello);
			form.setField("prov_macello",provinciaMacello);
			form.setField("matricola",((Capo)capo).getCd_matricola());

			if(((Capo)capo).getCd_data_nascita() != null){
				String dataNascita = sdf.format(new Date(((Capo)capo).getCd_data_nascita().getTime()));
				form.setField("data_nascita",dataNascita);
			}

			if(((Capo)capo).getCd_specie() == 1){
				form.setField("bovina","Yes");
				form.setField("razza",razza);
			}
			else if(((Capo)capo).getCd_specie() == 5) {
				form.setField("bufalina","Yes");
				form.setField("razza","N.D");
			}

			if(((Capo)capo).isCd_maschio()){
				form.setField("sesso_m","Yes");
			}
			else {
				form.setField("sesso_f","Yes");
			}

			stamper_1033.setFormFlattening(true);
			stamper_1033.close();

			//Set info aggiuntive per StampeModuli
			stampeModuli.setMatricolaCapo(((Capo)capo).getCd_matricola());

			//Calcolo del progressivo
			gestioneProgressivoModulo(db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO_MATRICOLA, output);

			outputNew = new ByteArrayOutputStream();
			documentNew = new Document();
			writerNew = PdfWriter.getInstance(documentNew, outputNew);
			documentNew.open();
			cb = writerNew.getDirectContent();

			readerNew = new PdfReader(output.toByteArray());

			for(int j = 1 ; j <= readerNew.getNumberOfPages(); j++){
				page = writerNew.getImportedPage(readerNew, j); 
				documentNew.newPage();
				cb.addTemplate(page, 0, 0);
				if(j == 1){
					cb.beginText();
					cb.setFontAndSize( BaseFont.createFont(BaseFont.TIMES_ROMAN, BaseFont.CP1252, BaseFont.NOT_EMBEDDED ), 10.0F);
					cb.showTextAligned(Element.ALIGN_LEFT, 
							"Prot. " + stampeModuli.getProgressivo() + "/" + 
							listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
							sdfYear.format(sdf.parse(dataMacellazione)),56F,700F, 0);
					if(stampeModuli.getOldProgressivo() > 0){
						cb.showTextAligned(Element.ALIGN_LEFT, 
								"(sostituisce il doc. con prot. " + stampeModuli.getOldProgressivo() + "/" + 
								listaAsl.getValueFromId(macello.getSiteId())+ "/" + 
								sdfYear.format(sdf.parse(dataMacellazione)) + ")",56F,687F, 0);
					}
					cb.endText();
				}
			}

			documentNew.close();

			outputList.add(outputNew);	

		}

		mergePDF(outputList, out);

	}
	
	private void loggaPerLiberoConsumo(ConfigTipo configTipo, ActionContext context, Connection db) throws Exception
	{
			CapoLogDao capoLogDao = CapoLogDao.getInstance();
			CapoLog capoLog = new CapoLog();
			capoLog.setCodiceAziendaNascita(b.getCd_codice_azienda());
			capoLog.setDataNascita(((Capo)b).getCd_data_nascita());
			capoLog.setEnteredBy(b.getEntered_by());
			capoLog.setIdMacello(b.getId_macello());
			capoLog.setInBdn(context.getRequest().getParameter("capo_in_bdn") != null && context.getRequest().getParameter("capo_in_bdn").equals("si"));
			capoLog.setMatricola(((Capo)b).getCd_matricola());
			capoLog.setModifiedBy(b.getModified_by());
			capoLog.setRazza(((Capo)b).getCd_id_razza());
			capoLog.setSesso(((Capo)b).isCd_maschio() ? "M" : "F");
			capoLog.setSpecie(((Capo)b).getCd_specie());
			capoLog.setTrashedDate(b.getTrashed_date());
			
			if(capoLog.isInBdn()){

				try{
					if(context.getRequest().getParameter("asl_speditore_from_bdn") != null){
						capoLog.setAslSpeditoreFromBdn( Integer.parseInt(context.getRequest().getParameter("asl_speditore_from_bdn")) );
					}
				}
				catch(Exception e){
					logger.severe("Il parametro asl_speditore_from_bdn non e' un intero: " + context.getRequest().getParameter("asl_speditore_from_bdn"));
				}

				capoLog.setCodiceAziendaNascitaFromBdn( context.getRequest().getParameter("codice_azienda_nascita_from_bdn") != null ? context.getRequest().getParameter("codice_azienda_nascita_from_bdn") : "" );
				capoLog.setComuneSpeditoreFromBdn( context.getRequest().getParameter("comune_speditore_from_bdn") != null ? context.getRequest().getParameter("comune_speditore_from_bdn") : "" );

				try{
					if(context.getRequest().getParameter("data_nascita_from_bdn") != null){
						Timestamp t = new Timestamp(sdf.parse(context.getRequest().getParameter("data_nascita_from_bdn")).getTime());
						capoLog.setDataNascitaFromBdn(t);
					}
				}
				catch(Exception e){
					logger.severe("Il parametro data_nascita_from_bdn non e' una data corretta: " + context.getRequest().getParameter("data_nascita_from_bdn"));
				}

				try{
					if(context.getRequest().getParameter("razza_from_bdn") != null){
						capoLog.setRazzaFromBdn( Integer.parseInt(context.getRequest().getParameter("razza_from_bdn")) );
					}
				}
				catch(Exception e){
					logger.severe("Il parametro razza_from_bdn non e' un intero: " + context.getRequest().getParameter("razza_from_bdn"));
				}

				capoLog.setSessoFromBdn( context.getRequest().getParameter("sesso_from_bdn") != null ? context.getRequest().getParameter("sesso_from_bdn") : "" );

				try{
					if(context.getRequest().getParameter("specie_from_bdn") != null){
						capoLog.setSpecieFromBdn( Integer.parseInt(context.getRequest().getParameter("specie_from_bdn")) );
					}
				}
				catch(Exception e){
					logger.severe("Il parametro specie_from_bdn non e' un intero: " + context.getRequest().getParameter("specie_from_bdn"));
				}

			}

			capoLogDao.log(db, capoLog);
			
			if(capoLog.isInBdn()){

//				try{
//					if(context.getRequest().getParameter("asl_speditore_from_bdn") != null){
//						capoLog.setAslSpeditoreFromBdn( Integer.parseInt(context.getRequest().getParameter("asl_speditore_from_bdn")) );
//					}
//				}
//				catch(Exception e){
//					logger.severe("Il parametro asl_speditore_from_bdn non e' un intero: " + context.getRequest().getParameter("asl_speditore_from_bdn"));
//				}

				capoLog.setCodiceAziendaNascitaFromBdn( context.getRequest().getParameter("codice_azienda_nascita_from_bdn") != null ? context.getRequest().getParameter("codice_azienda_nascita_from_bdn") : "" );
//				capoLog.setComuneSpeditoreFromBdn( context.getRequest().getParameter("comune_speditore_from_bdn") != null ? context.getRequest().getParameter("comune_speditore_from_bdn") : "" );

				try{
					if(context.getRequest().getParameter("data_nascita_from_bdn") != null){
						Timestamp t = new Timestamp(sdf.parse(context.getRequest().getParameter("data_nascita_from_bdn")).getTime());
						capoLog.setDataNascitaFromBdn(t);
					}
				}
				catch(Exception e){
					logger.severe("Il parametro data_nascita_from_bdn non e' una data corretta: " + context.getRequest().getParameter("data_nascita_from_bdn"));
				}

				try{
					if(context.getRequest().getParameter("razza_from_bdn") != null){
						capoLog.setRazzaFromBdn( Integer.parseInt(context.getRequest().getParameter("razza_from_bdn")) );
					}
				}
				catch(Exception e){
					logger.severe("Il parametro razza_from_bdn non e' un intero: " + context.getRequest().getParameter("razza_from_bdn"));
				}

				capoLog.setSessoFromBdn( context.getRequest().getParameter("sesso_from_bdn") != null ? context.getRequest().getParameter("sesso_from_bdn") : "" );

				try{
					if(context.getRequest().getParameter("specie_from_bdn") != null){
						capoLog.setSpecieFromBdn( Integer.parseInt(context.getRequest().getParameter("specie_from_bdn")) );
					}
				}
				catch(Exception e){
					logger.severe("Il parametro specie_from_bdn non e' un intero: " + context.getRequest().getParameter("specie_from_bdn"));
				}

			}
			
			capoLogDao.log(db, capoLog);
		
	}
	
}
