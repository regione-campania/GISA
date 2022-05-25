/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.macellazioninewopu.actions;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;


import org.aspcfs.modules.macellazioninewopu.base.Campione;
import org.aspcfs.modules.macellazioninewopu.base.CapoLog;
import org.aspcfs.modules.macellazioninewopu.base.CapoLogDao;
import org.aspcfs.modules.macellazioninewopu.base.Casl_Non_Conformita_Rilevata;
import org.aspcfs.modules.macellazioninewopu.base.ChiaveModuliMacelli;
import org.aspcfs.modules.macellazioninewopu.base.GenericBean;
import org.aspcfs.modules.macellazioninewopu.base.NonConformita;
import org.aspcfs.modules.macellazioninewopu.base.Organi;
import org.aspcfs.modules.macellazioninewopu.base.Partita;
import org.aspcfs.modules.macellazioninewopu.base.ProvvedimentiCASL;
import org.aspcfs.modules.macellazioninewopu.base.StampeModuli;
import org.aspcfs.modules.macellazioninewopu.utils.ConfigTipo;
import org.aspcfs.modules.macellazioninewopu.utils.ReflectionUtil;
import org.aspcfs.modules.speditori.base.OrganizationAddress;
import org.aspcfs.modules.stabilimenti.base.Organization;

import org.aspcfs.utils.web.LookupList;

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

public final class MacellazioniOvicaprini extends Macellazioni
{
	private static SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" );
	private static SimpleDateFormat sdfYear = new SimpleDateFormat( "yyyy" );
	
	public String executeCommandDefault( ActionContext context )
	{
		return executeCommandList( context );
	}
	
	
	private void createPDFIdatidosi(Connection db, Document document, PdfWriter writer, ByteArrayOutputStream out, Organization macello, ArrayList<GenericBean> listaCapi, String dataMacellazione, StampeModuli stampeModuli) throws Exception{

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
		cb.showTextAligned(Element.ALIGN_LEFT, "(All. I Sez. II Partita I punto 2.a Reg. 854/04)\n",108F,476F, 0);
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

		String speditore = "...........................";
		String cod_speditore = "...........................";
		String comune = "...........................";
		String loc = "...........................";

		org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;
		OrganizationAddress speditoreAddress = null;

		for(GenericBean c : listaCapi){

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



			ListItem li = new ListItem( " con partita " + ((Partita)c).getCd_partita() + ", " +
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

			ListItem li = new ListItem( " con matr. " + ((Partita)c).getCd_partita() + ", " +
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
		ReflectionUtil.invocaMetodo(configTipo.getPackageAction()+configTipo.getNomeAction(), "gestioneProgressivoModulo",  new Class[]{Connection.class, StampeModuli.class, ChiaveModuliMacelli.TIPO_DATA_MACELLO.getClass(), ByteArrayOutputStream.class }, new Object[]{db, stampeModuli, ChiaveModuliMacelli.TIPO_DATA_MACELLO, out});

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

		String speditore = "...........................";
		String cod_speditore = "...........................";
		String comune = "...........................";
		String loc = "...........................";
		String mod4 = "";
		String dataMod4 = "";

		org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;
		OrganizationAddress speditoreAddress = null;

		for(GenericBean c : listaCapiBrucellosi){
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

			ListItem li = new ListItem( " proveniente dall'azienda " + speditore + " cod. az. " + cod_speditore + 
					" sita nel comune di " + comune + " in loc. " + loc + " scortato dal" +
					" mod. 4 n. "+ mod4 +" datato "+ dataMod4, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
			li.setAlignment(Element.ALIGN_JUSTIFIED);

			ListItem matricole = new ListItem("Partita "+((Partita)c).getCd_partita());
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
			ListItem matricole = new ListItem(((Partita)c).getCd_partita());
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

			Paragraph p3 = new Paragraph("Si comunica che in data "+dataArrivoMacello+" il Servizio Veterinario operante presso lo stabilimento in oggetto ha effettuato la visita ante mortem sull'animale di seguito segnalato:\n\n" +
					 " con partita " + ((Partita)c).getCd_partita() + "\n\n" + 
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

			stampeModuli.setMatricolaCapo(((Partita)c).getCd_partita());

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

	private void createPDFMorteAnteMacellazione (Connection db, Document doc, PdfWriter wr, ByteArrayOutputStream out, Organization macello, ArrayList<GenericBean> listaCapi, String dataArrivoMacello, Image logo, StampeModuli stampeModuli) throws Exception{

		LookupList listaAsl	= new LookupList( db, "lookup_site_id" );
		LookupList listaSpecie	= new LookupList( db, "m_lookup_specie" );
		LookupList listaRazze	= new LookupList( db, "razze_bovini" );
		LookupList listaLuoghiVerifica = new LookupList(db, "m_lookup_luoghi_verifica");

		ArrayList<ByteArrayOutputStream> outputList = new ArrayList<ByteArrayOutputStream>();
		ByteArrayOutputStream output = null;
		Document document = null;
		PdfWriter writer = null;

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

			Paragraph p7 = new Paragraph(" con partita " + ((Partita)c).getCd_partita(),
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

			stampeModuli.setMatricolaCapo(((Partita)c).getCd_partita());

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
		String provenienza = "...........................";

		com.itextpdf.text.List elencoCapi = new com.itextpdf.text.List(true, 10);

		org.aspcfs.modules.speditori.base.Organization speditoreOrg = null;

		for(GenericBean c : listaCapi){

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
			ListItem li = new ListItem ("L'AUTOMEZZO "+ tipo +" TARGATO "+ targa +" SUL QUALE E' STATO TRASPORTATO L'ANIMALE CON PARTITA "+ ((Partita)c).getCd_partita()+" PROVENIENTE DA AZIENDA INFETTA "+provenienza+"\n", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12));
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

			//Set dei campi
			AcroFields form = stamper_1033.getAcroFields();
			form.setField("ragione_sociale", macello.getName());
			form.setField("asl",listaAsl.getSelectedValue(macello.getSiteId()));
			form.setField("partita",((Partita)capo_1033_tbc).getCd_partita());
			form.setField("comune", comuneMacello);
			form.setField("data_macellazione",dataMacellazione);
			form.setField("note",capo_1033_tbc.getCd_note());
			form.setField("veterinario_1",capo_1033_tbc.getCd_veterinario_1());
			form.setField("veterinario_2",capo_1033_tbc.getCd_veterinario_2());
			form.setField("veterinario_3",capo_1033_tbc.getCd_veterinario_3());

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
			stampeModuli.setMatricolaCapo(((Partita)capo_1033_tbc).getCd_partita());

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
				//se bufalini
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
					form.setField("PARTITA_" + i, ((Partita)c).getCd_partita());
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
					form.setField("PARTITA_" + i, ((Partita)c).getCd_partita());
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

			String comuneMacello = macello.getCity();
			String provinciaMacello = macello.getState();
			form.setField("asl_prov",listaAsl.getSelectedValue(speditore.getSiteId()));
			form.setField("ragione_sociale", macello.getName()+ " C.E: "+macello.getApprovalNumber());
			form.setField("comune_macello", comuneMacello);
			form.setField("prov_macello",provinciaMacello);
			form.setField("partita",((Partita)capo).getCd_partita());

			stamper_1033.setFormFlattening(true);
			stamper_1033.close();

			//Set info aggiuntive per StampeModuli
			stampeModuli.setMatricolaCapo(((Partita)capo).getCd_partita());

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
	
	private void loggaPerLiberoConsumo() throws Exception
	{
		System.out.println("aaa");
	}
	
	private void loggaPerLiberoConsumo(ConfigTipo configTipo, ActionContext context, Connection db) throws Exception
	{
			CapoLogDao capoLogDao = CapoLogDao.getInstance();
			CapoLog capoLog = new CapoLog();
			capoLog.setCodiceAziendaNascita(b.getCd_codice_azienda());
			capoLog.setEnteredBy(b.getEntered_by());
			capoLog.setIdMacello(b.getId_macello());
			capoLog.setMatricola(((Partita)b).getCd_partita());
			capoLog.setModifiedBy(b.getModified_by());
			capoLog.setTrashedDate(b.getTrashed_date());
			
			capoLogDao.log(db, capoLog);
			
			capoLogDao.log(db, capoLog);
		
	}
	
}
