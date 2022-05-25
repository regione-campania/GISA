/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.allerte_new.base;

import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import org.apache.poi.util.IOUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;


import org.aspcfs.modules.login.beans.UserBean;
import org.aspcfs.utils.AuditReport;
import org.aspcfs.utils.web.LookupList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.lowagie.text.Chunk;
import com.lowagie.text.DocWriter;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Font;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.OutputStreamCounter;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class AllegatoF extends PdfPageEventHelper
{
	public Image headerImage;
	public PdfPTable table;
	public PdfGState gstate;
	public PdfTemplate tpl;
	public BaseFont helv;
	private float larghezza_tabella;
	private int idAslUtente = -1 ;
	
	public int getIdAslUtente() {
		return idAslUtente;
	}

	public void setIdAslUtente(int idAslUtente) {
		this.idAslUtente = idAslUtente;
	}

	public void generate(Connection db,OutputStream out,int idAsl , org.aspcfs.modules.allerte_new.base.Ticket allerta,String tipo_alimenti,ActionContext context )
	throws DocumentException, SQLException, MalformedURLException, IOException
	{
		String data = "" ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (allerta.getDataApertura()!=null)
		{
			 data = sdf.format(new Date(allerta.getDataApertura().getTime()));
		}
		
		
		LookupList lookup_asl = new LookupList(db,"lookup_site_id");
		 
		int numCu = getNumeroControlliAllerta(db, allerta, idAsl);
		
	
		HashMap<String,Integer> num_cu_esiti = getNumeroControlliAllertaEsito(db,allerta, idAsl);
		HashMap<String,Integer> num_cu_ritiro = getNumeroControlliAllertapProceduraRitiro(db,allerta, idAsl);
		HashMap<String,Integer> num_cu_richiamo = getNumeroControlliAllertapProceduraRichiamo(db,allerta, idAsl);
		HashMap<String,Azione> num_cu_azioni =  getNumeroControlliAllertaAzioniAdottate(db,allerta, idAsl);
		HashMap<String,Azione> num_cu_esito_partita =  getNumeroControlliAllertaEsitoPartita(db,allerta, idAsl);
		HashMap<String,Integer> num_cu_comunicazione_rischio = getNumeroControlliRicevutaComunicazione(db,allerta, idAsl);
		ArrayList<String> note_lista = getNoteComunicazioneRischio(db,allerta, idAsl);
		
		
		Document document = new Document( PageSize.A4, 15, 15, 60, 25 );
		PdfWriter writer = PdfWriter.getInstance(document, out);
		writer.setPageEvent( this );
		document.open();
		String path_reg = (AuditReport.class.getResource("regionecampania.jpg").getPath());
		headerImage = Image.getInstance( path_reg.replaceAll("%20"," "));
		headerImage.scaleToFit( 50, 50 );
		
		table = new PdfPTable( 2 );
		table.getDefaultCell().setBorderWidth(0);
		
		/**
		 * 	ALLINEAMENTO A SINISTRA LOGO REGIONE
		 */
		if (idAslUtente == -1)
		{
			table.getDefaultCell().setHorizontalAlignment( Element.ALIGN_LEFT );
			table.addCell(new Phrase(new Chunk(headerImage, 0, 0)));
		}
		else
		{
			table.getDefaultCell().setHorizontalAlignment( Element.ALIGN_LEFT );
			table.addCell(new Phrase(new Chunk("")));
		}

		PdfPTable table1 = new PdfPTable(1);
		table1.getDefaultCell().setBorderWidth(0);
		table1.addCell(new Phrase("Allegato F - Esiti Accertamenti ".toUpperCase(),new Font(Font.TIMES_ROMAN, 12, Font.BOLD, Color.black)));
		table1.addCell(new Phrase("Regione Campania ".toUpperCase(),new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black)));
		table1.addCell(new Phrase("AGC Assistenza Sanitaria ".toUpperCase(),new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black)));
		table1.addCell(new Phrase("Nodo Regionale Allerte ".toUpperCase(),new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black)));
		table1.addCell(new Paragraph("",new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black)));
		table1.getDefaultCell().setHorizontalAlignment( Element.ALIGN_TOP);
		table1.getDefaultCell().setVerticalAlignment( Element.ALIGN_LEFT);
		table.addCell(table1);
		
		gstate = new PdfGState();
		gstate.setFillOpacity(0.3f);
		gstate.setStrokeOpacity(0.3f);
		// initialization of the template
		tpl = writer.getDirectContent().createTemplate(100, 100);
		tpl.setBoundingBox(new Rectangle(-20, -20, 100, 100));
		// initialization of the font
		helv = BaseFont.createFont( BaseFont.TIMES_ROMAN, BaseFont.WINANSI, false);
		
		if(idAsl!=-1)
		{
			AuditReport auditReport = new AuditReport();
			auditReport.id_asl = idAsl;
			String path = (context.getServletContext().getRealPath("/WEB-INF")+"/reports/images/"+auditReport.getPathLogoAsl());
			Image logoAsl = null ;
			try {
				logoAsl = Image.getInstance( path.replaceAll("%20"," "));
				logoAsl.scaleToFit( 50, 50 );
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			PdfPTable tab_logo = new PdfPTable( 3 );
			
			tab_logo.getDefaultCell().setBorderWidth(0);
			
			/**
			 * 	ALLINEAMENTO A SINISTRA LOGO REGIONE
			 */
			tab_logo.addCell(new Paragraph( " 										", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
			tab_logo.addCell(new Paragraph( " 										", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
			
			tab_logo.getDefaultCell().setHorizontalAlignment( Element.ALIGN_RIGHT );
			tab_logo.addCell(new Phrase(new Chunk(logoAsl, 0, 0)));
		
			tab_logo.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
			document.add(tab_logo );
		}
		
		larghezza_tabella = document.right() - document.left();
		
		PdfPTable oggetto = new PdfPTable(1);
		oggetto.setSpacingBefore(16.0F);
		oggetto.setSpacingAfter(16.0F);
		oggetto.getDefaultCell().setBorderWidth(0);
		oggetto.getDefaultCell().setHorizontalAlignment( Element.ALIGN_LEFT );
		oggetto.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		oggetto.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		oggetto.addCell(new Paragraph( ("Oggetto : Sistema di Allerta; comunicazione esiti accertamenti allerta "+allerta.getIdAllerta()).toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		oggetto.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		oggetto.addCell(new Paragraph( ("In Relazione Alla comunicazione prot "+allerta.getIdAllerta()+"del "+data+" riguardante l'attivazione del sistema di allerta per il seguente prodotto ( riportare la denominazione , il numero di lotto , il fabbricante o distributore) : ").toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		oggetto.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		oggetto.addCell(new Paragraph( (tipo_alimenti+"").toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		if (allerta.getDenominazione_prodotto()!=null && !"null".equalsIgnoreCase(allerta.getDenominazione_prodotto()))
			oggetto.addCell(new Paragraph( ("Denominazione Prodotto : "+allerta.getDenominazione_prodotto()).toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		if (allerta.getNumero_lotto()!=null && !"null".equalsIgnoreCase(allerta.getNumero_lotto()))
		oggetto.addCell(new Paragraph( ("Numero Lotto : "+allerta.getNumero_lotto()).toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		if (allerta.getFabbricante_produttore()!=null && !"null".equalsIgnoreCase(allerta.getFabbricante_produttore()))
			oggetto.addCell(new Paragraph( ("Fabbricante o Produttore : "+allerta.getFabbricante_produttore()).toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));

		if(allerta.getData_scadenza_allerta()!=null)
		{
			String data_scadenza = sdf.format(new Date(allerta.getData_scadenza_allerta().getTime()));
			oggetto.addCell(new Paragraph( ("Data Scadenza/termine minimo di conservazione : "+data_scadenza).toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		}
		
		document.add( oggetto );
		
		PdfPTable cupianificati = new PdfPTable(1);
		cupianificati.setSpacingBefore(16.0F);
		cupianificati.setSpacingAfter(16.0F);
		cupianificati.getDefaultCell().setBorderWidth(0);
		cupianificati.getDefaultCell().setHorizontalAlignment( Element.ALIGN_LEFT );
		
		/**
		 *  SE SI TRATTA DEL SUPERVISORE DI UN ASL
		 */
		
		if(idAsl != -1)
		{
			AslCoinvolte aslCoinvolta = allerta.getAsl_coinvolte().get(lookup_asl.getValueFromId(idAsl));
			cupianificati.addCell(new Paragraph( ("Su "+aslCoinvolta.getControlliUfficialiRegionaliPianificati()+" controlli assegnati dalla Regione, sul territorio di competenza di questa ASL,sono stati pianificati "+aslCoinvolta.getCu_pianificati()+" controlli dall'ASL ed eseguiti "+numCu+" controlli, nel corso dei quali e' stato riscontrato che :").toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		}
		else
		{
			/**
			 *  CASO DI UTENTE REGIONALE
			 */
			int num_cu_regionali = 0 ;
			int num_cu_pianificati = 0 ;
			Iterator<String> itKey = allerta.getAsl_coinvolte().keySet().iterator();
			while (itKey.hasNext())
			{
				AslCoinvolte asl = allerta.getAsl_coinvolte().get(itKey.next());
				num_cu_regionali += asl.getControlliUfficialiRegionaliPianificati();
				num_cu_pianificati += asl.getCu_pianificati();
			}
			cupianificati.addCell(new Paragraph( ("Su "+num_cu_regionali+" controlli assegnati dalla Regione, sul territorio di competenza di tutte le ASL coinvolte,sono stati pianificati "+num_cu_pianificati+" controlli dall'ASL ed eseguiti "+numCu+" controlli, nel corso dei quali e' stato riscontrato che :").toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));

			
			
		}
		
		document.add( cupianificati );
		float[] widths = { 0.80f, 0.20f };
		/**
		 * RAGGRUPPAMENTO SU DISTRIBUZIONE DELLA PARTITA
		 */
		PdfPTable numControlliEsitpPartita = new PdfPTable( widths );
		numControlliEsitpPartita.getDefaultCell().setBorderWidth(0);
		Phrase ta32tit1 = new Phrase( "", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) );
		PdfPCell cell_3 = new PdfPCell( new Paragraph(ta32tit1) );
		cell_3.setBorder(0);
		cell_3.setColspan( 2 );
		numControlliEsitpPartita.addCell( cell_3 );
		
		Phrase int1 = new Phrase( ("Distribuzione Della Partita").toUpperCase(), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		numControlliEsitpPartita.addCell( int1 );
		numControlliEsitpPartita.getDefaultCell().setBackgroundColor(Color.white);
		Phrase int2 = new Phrase( ("Numero").toUpperCase(), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
		numControlliEsitpPartita.addCell(int2);
		
		if (num_cu_esito_partita.isEmpty())
		{
			Phrase num = new Phrase( ("Il Prodotto non e' stato ulteriormente Distribuito").toUpperCase(), new Font(Font.TIMES_ROMAN, 8, Font.BOLD, Color.black) );
			numControlliEsitpPartita.addCell( num );
		}
		else
		{
		Iterator<String> itEsitiPartita = num_cu_esito_partita.keySet().iterator();
		while (itEsitiPartita.hasNext())
		{
			String azione_value = itEsitiPartita.next();
			Azione azione = num_cu_esito_partita.get(azione_value);
			
			Phrase label_esito = new Phrase( azione_value.toUpperCase(), new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
			numControlliEsitpPartita.addCell( label_esito );
			numControlliEsitpPartita.getDefaultCell().setBackgroundColor(Color.white);
			Phrase num = new Phrase( ""+azione.getnumCu(), new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
			numControlliEsitpPartita.addCell( num );
			
		}
		}
		numControlliEsitpPartita.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		numControlliEsitpPartita.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		numControlliEsitpPartita.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		document.add( numControlliEsitpPartita );
		
		/**
		 * RAGGRUPPAMENTO SU COMUNICAZIONE DI RISCHIO
		 */
		PdfPTable numControlli_com_rischio = new PdfPTable( widths );
		numControlli_com_rischio.getDefaultCell().setBorderWidth(0);
		Phrase ta32tit5 = new Phrase( ("Ricevuta Comunicazione del Rischio dal produttore/fornitore").toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.BOLD, Color.black) );
		PdfPCell cell_5 = new PdfPCell( new Paragraph(ta32tit5) );
		cell_5.setBorder(0);
		cell_5.setColspan( 2 );
		numControlli_com_rischio.addCell( cell_5 );
	
		Iterator<String> itcom_rischio= num_cu_comunicazione_rischio.keySet().iterator();
		while (itcom_rischio.hasNext())
		{
			String com_rischio_val = itcom_rischio.next();
			int num = num_cu_comunicazione_rischio.get(com_rischio_val);
			
			Phrase label_esito = new Phrase( com_rischio_val.toUpperCase(), new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
			numControlli_com_rischio.addCell( label_esito );
			numControlli_com_rischio.getDefaultCell().setBackgroundColor(Color.white);
			Phrase num_rischio = new Phrase( ""+num, new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
			numControlli_com_rischio.addCell(num_rischio);
			
		}
		
		
		numControlli_com_rischio.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		numControlli_com_rischio.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		numControlli_com_rischio.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		document.add( numControlli_com_rischio );
		
		if (! note_lista.isEmpty())
		{
			PdfPTable inf_aggiuntive = new PdfPTable( widths );
			
			inf_aggiuntive.getDefaultCell().setBorderWidth(0);
			Phrase ta32tit6 = new Phrase( ("Eventuali Informazioni Aggiuntive").toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.BOLD, Color.black) );
			PdfPCell cell_6 = new PdfPCell( new Paragraph(ta32tit6) );
			cell_6.setBorder(0);
			cell_6.setColspan( 2 );
			inf_aggiuntive.addCell( cell_6 );
			int i = 1;
			for (String eventuali_note : note_lista)
			{
				inf_aggiuntive.addCell(new Paragraph( (i+"): "+eventuali_note.trim()).toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
				inf_aggiuntive.addCell(new Paragraph( "", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
				i++;
			}
			
			inf_aggiuntive.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black)));
			inf_aggiuntive.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
			inf_aggiuntive.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
			document.add( inf_aggiuntive );

		}
		/**
		 * 	RAGGRUPPAMENTO SU PROCEDURA RITIRO
		 */
		
		PdfPTable numControlliRitiro = new PdfPTable( widths );
		numControlliRitiro.getDefaultCell().setBorderWidth(0);
		Phrase tab1tit = new Phrase( ("Procedura di Ritiro").toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.BOLD, Color.black) );
		PdfPCell cell = new PdfPCell( new Paragraph(tab1tit) );
		cell.setBorder(0);
		cell.setColspan( 2 );
		numControlliRitiro.addCell( cell );
		
		Iterator<String> itRitiro = num_cu_ritiro.keySet().iterator();
		while (itRitiro.hasNext())
		{
			String esitValue = itRitiro.next();
			int numCuEsiti = num_cu_ritiro.get(esitValue);
			Phrase label_esito = new Phrase( esitValue.toUpperCase(), new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
			numControlliRitiro.addCell( label_esito );
			numControlliRitiro.getDefaultCell().setBackgroundColor(Color.white);
			Phrase num = new Phrase( (""+numCuEsiti).toUpperCase(), new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
			numControlliRitiro.addCell( num );
			
		}
		numControlliRitiro.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		numControlliRitiro.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		document.add( numControlliRitiro );
		
		/**
		 * 	RAGGRUPPAMENTO SU PROCEDURA RICHIAMO
		 */
		
		PdfPTable numControlliRichiamo = new PdfPTable( widths );
		numControlliRichiamo.getDefaultCell().setBorderWidth(0);
		Phrase tab2tit = new Phrase( ("Procedura di Richiamo").toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.BOLD, Color.black) );
		PdfPCell cell2 = new PdfPCell( new Paragraph(tab2tit) );
		cell2.setBorder(0);
		cell2.setColspan( 2 );
		numControlliRichiamo.addCell( cell2 );
		
		Iterator<String> itRichiamo= num_cu_richiamo.keySet().iterator();
		while (itRichiamo.hasNext())
		{
			String esitValue = itRichiamo.next();
			int numCuEsiti = num_cu_richiamo.get(esitValue);
			Phrase label_esito = new Phrase( (esitValue).toUpperCase(), new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
			numControlliRichiamo.addCell( label_esito );
			numControlliRichiamo.getDefaultCell().setBackgroundColor(Color.white);
			Phrase num = new Phrase( ""+numCuEsiti, new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
			numControlliRichiamo.addCell( num );
			
		}
		numControlliRichiamo.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		numControlliRichiamo.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		document.add( numControlliRichiamo );
		/**
		 * 	RAGGRUPPAMENTO SUGLI ESITI DEI CONTROLLI
		 */
		PdfPTable numControlliEsiti = new PdfPTable( widths );
		numControlliEsiti.getDefaultCell().setBorderWidth(0);
		Phrase ta32tit2 = new Phrase( ("Esito del Controllo").toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.BOLD, Color.black) );
		PdfPCell cell3_1 = new PdfPCell( new Paragraph(ta32tit2) );
		cell3_1.setBorder(0);
		cell3_1.setColspan( 2 );
		numControlliEsiti.addCell( cell3_1 );
		Iterator<String> itEsiti = num_cu_esiti.keySet().iterator();
		while (itEsiti.hasNext())
		{
			String esitValue = itEsiti.next();
			int numCuEsiti = num_cu_esiti.get(esitValue);
			Phrase label_esito = new Phrase( esitValue.toUpperCase(), new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
			numControlliEsiti.addCell( label_esito );
			numControlliEsiti.getDefaultCell().setBackgroundColor(Color.white);
			Phrase num = new Phrase( ""+numCuEsiti, new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
			numControlliEsiti.addCell( num );
			
		}
		numControlliEsiti.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		numControlliEsiti.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		document.add( numControlliEsiti );
		
		
		
		/**
		 * 	RAGGRUIPPAMENTO SU AZIONI ADOTTATE
		 */
		PdfPTable numControlliAzioni = new PdfPTable( widths );
		
		numControlliAzioni.getDefaultCell().setBorderWidth(0);
		Phrase ta32tit = new Phrase( ("Azioni Adottate").toUpperCase(), new Font(Font.TIMES_ROMAN, 10, Font.BOLD, Color.black) );
		PdfPCell cell3 = new PdfPCell( new Paragraph(ta32tit) );
		cell3.setBorder(0);
		cell3.setColspan( 2 );
		numControlliAzioni.addCell( cell3 );
		
		Iterator<String> itAzioni= num_cu_azioni.keySet().iterator();
		while (itAzioni.hasNext())
		{
			
			String azione_value = itAzioni.next();
			Azione azione = num_cu_azioni.get(azione_value);
			
			Phrase label_esito = new Phrase( azione_value.toUpperCase(), new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
			numControlliAzioni.addCell( label_esito );
			numControlliAzioni.getDefaultCell().setBackgroundColor(Color.white);
			Phrase num = new Phrase( ""+azione.getnumCu(), new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
			numControlliAzioni.addCell( num );
			
			if (!azione.getLista_sotto_azioni().isEmpty())
			{
				for (Azione azione2 :azione.getLista_sotto_azioni() )
				{
					
				String lab = "";
				if(azione2.getArticolo()!=null)
					lab+="					- di cui per Articolo "+azione2.getArticolo();
				if(azione2.getNorma_violata()!=null)
					lab+="					- di cui per Norma Violata "+azione2.getNorma_violata();

				
				if(!lab.equals(""))
				{
				Phrase label_esito1 = new Phrase( (lab).toUpperCase(), new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
				numControlliAzioni.addCell( label_esito1 );
				numControlliAzioni.getDefaultCell().setBackgroundColor(Color.white);
				Phrase num1 = new Phrase( ""+azione2.getnumCu(), new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
				numControlliAzioni.addCell( num1 );
				if(azione2.getNote_eventuali()!=null && !azione2.getNote_eventuali().equals("") )
				{
				Phrase label_esito2 = new Phrase("						Eventuali Informazioni Aggiuntive : "+azione2.getNote_eventuali(), new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
				numControlliAzioni.addCell( label_esito2 );
				numControlliAzioni.getDefaultCell().setBackgroundColor(Color.white);
				Phrase num2 = new Phrase( "", new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
				numControlliAzioni.addCell( num2 );
				}
				
				}
				
				}
				
				
			}
			
			
		}
		numControlliAzioni.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		numControlliAzioni.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		numControlliAzioni.addCell(new Paragraph( " ", new Font(Font.TIMES_ROMAN, 10, Font.NORMAL, Color.black) ));
		document.add( numControlliAzioni );
		
		/**
		 * 	FIRMA DEL VERBALE
		 */
		
		PdfPTable firma = new PdfPTable( 2 );
		firma.getDefaultCell().setBorderWidth(0);
		firma.getDefaultCell().setHorizontalAlignment( Element.ALIGN_LEFT );
		firma.addCell(new Paragraph( "DATA", new Font(Font.TIMES_ROMAN, 10, Font.BOLD, Color.black) ));
		
		if (idAsl!=-1)
		{
			firma.addCell(new Paragraph( "IL REFERENTE ALLERTE ASL", new Font(Font.TIMES_ROMAN, 10, Font.BOLD, Color.black) ));
		}
		else
		{
			firma.addCell(new Paragraph( "IL REFERENTE", new Font(Font.TIMES_ROMAN, 10, Font.BOLD, Color.black) ));
		}
		document.add(firma);
		
		document.close();

	}
	
	public String generate_doc(Connection db,OutputStream out,int idAsl , org.aspcfs.modules.allerte_new.base.Ticket allerta,String tipo_alimenti,ActionContext context )
	throws DocumentException, SQLException, FileNotFoundException, IOException
	{
		AuditReport auditReport = new AuditReport();
		String html = "" ;
		String data = "" ;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (allerta.getDataApertura()!=null)
		{
			 data = sdf.format(new Date(allerta.getDataApertura().getTime()));
		}
		
		
		LookupList lookup_asl = new LookupList(db,"lookup_site_id");
		 
		int numCu = getNumeroControlliAllerta(db, allerta, idAsl);
		
	
		HashMap<String,Integer> num_cu_esiti = getNumeroControlliAllertaEsito(db,allerta, idAsl);
		HashMap<String,Integer> num_cu_ritiro = getNumeroControlliAllertapProceduraRitiro(db,allerta, idAsl);
		HashMap<String,Integer> num_cu_richiamo = getNumeroControlliAllertapProceduraRichiamo(db,allerta, idAsl);
		HashMap<String,Azione> num_cu_azioni =  getNumeroControlliAllertaAzioniAdottate(db,allerta, idAsl);
		HashMap<String,Azione> num_cu_esito_partita =  getNumeroControlliAllertaEsitoPartita(db,allerta, idAsl);
		HashMap<String,Integer> num_cu_comunicazione_rischio = getNumeroControlliRicevutaComunicazione(db,allerta, idAsl);
		ArrayList<String> note_lista = getNoteComunicazioneRischio(db,allerta, idAsl);
		
		auditReport.id_asl = -1;
		String path1 = (context.getServletContext().getRealPath("/WEB-INF")+"/template_report/images/"+auditReport.getPathLogoAsl());
		html += "<table width='100%'><tr>";
	
		if(idAslUtente==-1)
			html+="<td><img width='80' height='80'  src='"+path1.replaceAll("%20"," ")+"'></td><td align ='right'><b>Allegato F - Esiti Accertamenti<br>Regione Campania <br>AGC Assistenza Sanitaria <br>Nodo Regionale Allerte </b></td></tr></table>";
		else
			html+="<td>&nbsp;</td><td align ='right'><b>Allegato F - Esiti Accertamenti<br>Regione Campania <br>AGC Assistenza Sanitaria <br>Nodo Regionale Allerte </b></td></tr></table>";

		if(idAsl!=-1)
		{
			
			auditReport.id_asl = idAsl;
			String path = (context.getServletContext().getRealPath("/WEB-INF")+"/template_report/images/"+auditReport.getPathLogoAsl());
			Image logoAsl = null ;
			try {
				logoAsl = Image.getInstance( path.replaceAll("%20"," "));
				logoAsl.scaleToFit( 50, 50 );
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			html += "<br><table width='100%'><tr><td></td><td></td><td align ='right'><img src='"+path.replaceAll("%20"," ")+"' align ='right'></td></tr></table>";
		}
		
	
		html+= "<br> <p>Oggetto : Sistema di Allerta; comunicazione esiti accertamenti allerta "+allerta.getIdAllerta()+"</p>";
		html+= "<br> <p>In Relazione Alla comunicazione prot "+allerta.getIdAllerta()+"del "+data+" riguardante l'attivazione del sistema di allerta per il seguente prodotto ( riportare la denominazione , il numero di lotto , il fabbricante o distributore) : </p>";
		html+=	"<br><p>"+tipo_alimenti+"</p>";
		
		if (allerta.getDenominazione_prodotto()!=null && !"null".equalsIgnoreCase(allerta.getDenominazione_prodotto()))
			html+= "<br>Denominazione Prodotto : "+allerta.getDenominazione_prodotto();
		if (allerta.getNumero_lotto()!=null && !"null".equalsIgnoreCase(allerta.getNumero_lotto()))
			html+= "<br>Numero Lotto : "+allerta.getNumero_lotto();
		if (allerta.getFabbricante_produttore()!=null && !"null".equalsIgnoreCase(allerta.getFabbricante_produttore()))
			html+= "<br>Fabbricante o Produttore : "+allerta.getFabbricante_produttore();

		if(allerta.getData_scadenza_allerta()!=null)
		{
			String data_scadenza = sdf.format(new Date(allerta.getData_scadenza_allerta().getTime()));
			html+= "<br>Data Scadenza/termine minimo di conservazione : "+data_scadenza;
		}
		
		
		
		
		
		/**
		 *  SE SI TRATTA DEL SUPERVISORE DI UN ASL
		 */
		
		if(idAsl != -1)
		{
			AslCoinvolte aslCoinvolta = allerta.getAsl_coinvolte().get(lookup_asl.getValueFromId(idAsl));
			html+=  "<br><br>Su "+aslCoinvolta.getControlliUfficialiRegionaliPianificati()+" controlli assegnati dalla Regione, sul territorio di competenza di questa ASL,sono stati pianificati "+aslCoinvolta.getCu_pianificati()+" controlli dall'ASL ed eseguiti "+numCu+" controlli, nel corso dei quali e' stato riscontrato che :";
		}
		else
		{
			/**
			 *  CASO DI UTENTE REGIONALE
			 */
			int num_cu_regionali = 0 ;
			int num_cu_pianificati = 0 ;
			Iterator<String> itKey = allerta.getAsl_coinvolte().keySet().iterator();
			while (itKey.hasNext())
			{
				AslCoinvolte asl = allerta.getAsl_coinvolte().get(itKey.next());
				num_cu_regionali += asl.getControlliUfficialiRegionaliPianificati();
				num_cu_pianificati += asl.getCu_pianificati();
			}
			html+= "<br><br>Su "+num_cu_regionali+" controlli assegnati dalla Regione, sul territorio di competenza di tutte le ASL coinvolte,sono stati pianificati "+num_cu_pianificati+" controlli dall'ASL ed eseguiti "+numCu+" controlli, nel corso dei quali e' stato riscontrato che :";
	
		}
		
		
		
		/**
		 * RAGGRUPPAMENTO SU DISTRIBUZIONE DELLA PARTITA
		 */
		
		html += "<br><br><table width='100%'><tr><td><b><font>Distribuzione Della Partita</font></b></td><td>Numero</td></tr></table>";
		
		if (num_cu_esito_partita.isEmpty())
		{
			html+="<br>Il Prodotto non e' stato ulteriormente Distribuito" ;
			
		}
		else
		{
			html+="<br><table width='100%'>";
		Iterator<String> itEsitiPartita= num_cu_esito_partita.keySet().iterator();
		while (itEsitiPartita.hasNext())
		{
			String azione_value = itEsitiPartita.next();
			Azione azione = num_cu_esito_partita.get(azione_value);
			html +="<tr><td>"+azione_value+"</td><td>"+azione.getnumCu()+"</td></tr>";
			
		}
		}
		html+="</table><br>";
		
		/**
		 * RAGGRUPPAMENTO SU COMUNICAZIONE DI RISCHIO
		 */

		html+="<br><b>Ricevuta Comunicazione del Rischio dal produttore/fornitore</b>";
		
		html+="<br><table width='100%'>";
		Iterator<String> itcom_rischio= num_cu_comunicazione_rischio.keySet().iterator();
		while (itcom_rischio.hasNext())
		{
			String com_rischio_val = itcom_rischio.next();
			int num = num_cu_comunicazione_rischio.get(com_rischio_val);
			html+="<tr><td>"+com_rischio_val+"</td><td>"+num+"</td></tr>";
			
			
		}
		
		html+="</table><br>";
		
		
		if (! note_lista.isEmpty())
		{
		
			
			html+="<br>Eventuali Informazioni Aggiuntive";
			
			int i = 1;
			html+="<table width='100%'>";
			for (String eventuali_note : note_lista)
			{
				html+="<tr><td>"+i+")</td><td>"+eventuali_note.trim()+"</td></tr>";
				i++;
			}
			
		
		}
		/**
		 * 	RAGGRUPPAMENTO SU PROCEDURA RITIRO
		 */
		
		
		html+="<br><b>Procedura di Ritiro</b>";
		html+="<br><table width='100%'>";
		Iterator<String> itRitiro = num_cu_ritiro.keySet().iterator();
		while (itRitiro.hasNext())
		{
			String esitValue = itRitiro.next();
			int numCuEsiti = num_cu_ritiro.get(esitValue);
			html+="<tr><td>"+esitValue+")</td><td>"+numCuEsiti+"</td></tr>";
			
		}
		html+="</table><br>";
		
		/**
		 * 	RAGGRUPPAMENTO SU PROCEDURA RICHIAMO
		 */
	
		
		html+="<br><b>Procedura di Richiamo</b>";
		html+="<br><table width='100%'>";
		Iterator<String> itRichiamo= num_cu_richiamo.keySet().iterator();
		while (itRichiamo.hasNext())
		{
			String esitValue = itRichiamo.next();
			int numCuEsiti = num_cu_richiamo.get(esitValue);
			html+="<tr><td>"+esitValue+")</td><td>"+numCuEsiti+"</td></tr>";
			
		}
		html+="</table><br>";
		/**
		 * 	RAGGRUPPAMENTO SUGLI ESITI DEI CONTROLLI
		 */
		html += "<br><b>Esito del Controllo</b>";
		html +="<br><table width='100%'>";
		Iterator<String> itEsiti = num_cu_esiti.keySet().iterator();
		while (itEsiti.hasNext())
		{
			String esitValue = itEsiti.next();
			int numCuEsiti = num_cu_esiti.get(esitValue);
			Phrase label_esito = new Phrase( esitValue, new Font(Font.TIMES_ROMAN, 8, Font.NORMAL, Color.black) );
			html+="<tr><td>"+label_esito+")</td><td>"+numCuEsiti+"</td></tr>";
			
		}
		html+="</table><br>";
		
		
		
		/**
		 * 	RAGGRUIPPAMENTO SU AZIONI ADOTTATE
		 */
		
		html+="<br><b>Azioni Adottate</b>";
		html+="<br><table width='100%'>";
		Iterator<String> itAzioni= num_cu_azioni.keySet().iterator();
		while (itAzioni.hasNext())
		{
			String azione_value = itAzioni.next();
			Azione azione = num_cu_azioni.get(azione_value);
			html+="<tr><td>"+azione_value+")</td><td>"+azione.getnumCu()+"</td></tr>";
			if (!azione.getLista_sotto_azioni().isEmpty())
			{
				for (Azione azione2 :azione.getLista_sotto_azioni() )
				{
					
				String lab = "";
				if(azione2.getArticolo()!=null)
					lab+="di cui per Articolo "+azione2.getArticolo();
				if(azione2.getNorma_violata()!=null)
					lab+="di cui per Norma Violata "+azione2.getNorma_violata();

				
				if(!lab.equals(""))
				{
					html+="<tr><td>"+lab+")</td><td>"+azione2.getnumCu()+"</td></tr>";	
					
				
				if(azione2.getNote_eventuali()!=null && !azione2.getNote_eventuali().equals("") )
				{
					html+="<tr><td>Eventuali Informazioni Aggiuntive : "+azione2.getNote_eventuali()+"</td><td>"+azione2.getnumCu()+"</td></tr>";	
				
				}
				
				}
				}
				
			}
			
		}
		html+="</table><br>";
		
		/**
		 * 	FIRMA DEL VERBALE
		 */
		
		
		if (idAsl!=-1)
		{
			html+="<br><br><table width='100%'><tr><td>DATA</td><td><p align='right'>IL REFERENTE ALLERTE ASL</p></td></tr></table>";
		}
		else
		{
			html+="<br><br><table width='100%'><tr><td>DATA</td><td><p align='right'>IL REFERENTE</p></td></tr></table>";
			}
		
		
		
		
		return html;
		
		
		
		

	}
	
	public int getNumeroControlliAllerta(Connection db , org.aspcfs.modules.allerte_new.base.Ticket t,int id_asl)
	{
		int numCu = 0;
		try
		{
			String sql = "select count (ticketid) " +
			"from ticket where tipologia = 3 and trashed_date is null " +
			"and codice_allerta = ?";
			
			if (id_asl != -1)
			{
				sql += " and site_id = ? ";
			}
			
			PreparedStatement pst = db.prepareStatement(sql);
			
			pst.setString(1, t.getIdAllerta());
			if (id_asl!=-1)
			{
				pst.setInt(2, id_asl)	;
			}
			
			ResultSet rs = pst.executeQuery();
			if (rs.next())
			{
				numCu = rs.getInt(1);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return numCu ;
	}
	
	public HashMap<String, Integer> getNumeroControlliAllertaEsito(Connection db , org.aspcfs.modules.allerte_new.base.Ticket t,int id_asl)
	{
		HashMap<String,Integer> num_cu_esito = new HashMap<String, Integer>();
		try
		{
			String sql = "select count(ticketid) as num,description ,code " +
						  "from " +
						  "ticket join lookup_esito_controllo on (esito_controllo = code) " +
						  "where tipologia = 3  and codice_allerta = ? and ticket.trashed_date is null " ;
			
			String group = "group by description ,code " +
							"order by code  ";
			
			if (id_asl != -1)
			{
				sql += " and site_id = ? "+group ;
			}
			else
			{
				sql += " "+group ;
			}
														;
			PreparedStatement pst = db.prepareStatement(sql) ;
			pst.setString(1, t.getIdAllerta());
			if (id_asl !=-1)
			{
				pst.setInt(2, id_asl)	;
			}
			
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				String esito = rs.getString("description");
				int value = rs.getInt("num");
				
				String s = getSommaQuantita(db,rs.getInt("code"),id_asl,t.getIdAllerta());
				if (!"".equals(s))
					esito += " Quantita' : "+s;
				num_cu_esito.put(esito,value );
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return num_cu_esito ;
	}
	
	public HashMap<String, Azione> getNumeroControlliAllertaEsitoPartita(Connection db , org.aspcfs.modules.allerte_new.base.Ticket t,int id_asl)
	{
		HashMap<String,Azione> num_cu_azioni = new HashMap<String, Azione>();
		try
		{
			String sql = "select count(ticketid) as num,esito.description as esito , esito.code,distribuzione_partita.description  as distribuzione," +
						"distribuzione_partita.code as distribuzione_int"+
						" from  ticket  join lookup_esito_controllo esito on (ticket.esito_controllo = esito.code) "+
						"join lookup_destinazione_distribuzione distribuzione_partita on (ticket.destinazione_distribuzione = distribuzione_partita.code) "+
						"where tipologia = 3 and codice_allerta = ? and ticket.esito_controllo in (13,14) and ticket.trashed_date is null  " ;
			String group = "group by esito.description , distribuzione_partita.description  ,esito.code , distribuzione_partita.code " +
			"order by esito.code , distribuzione_partita.code ";
			if (id_asl!=-1)
			{
				sql += " and site_id = ? "+group;
				
			}
			else
			{
				sql = sql+group ;
			}
			PreparedStatement pst = db.prepareStatement(sql);
			if (id_asl != -1)
			{
				pst.setString(1, t.getIdAllerta());
				pst.setInt(2, id_asl)	;
				
			}
			else
			{
				pst.setString(1, t.getIdAllerta());
			}
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				String azione_value = rs.getString("esito");
				String articolo_value = rs.getString("distribuzione");
				int value = rs.getInt("num");
				Azione azione = new Azione(articolo_value,azione_value,value);
				if (articolo_value!= null && ! "".equals(articolo_value))
				{
					azione_value += "\n(Ulteriore Distribuzione :"+articolo_value+" )";
				}
				num_cu_azioni.put(azione_value,azione );
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return num_cu_azioni ;
	}
	
	
	
	public HashMap<String, Integer> getNumeroControlliRicevutaComunicazione(Connection db , org.aspcfs.modules.allerte_new.base.Ticket t,int id_asl)
	{
		HashMap<String,Integer> num_cu_com_rischio = new HashMap<String, Integer>();
		try
		{
			String sql = 	" select count (ticketid) as num, "+
							" comunicazione_rischio"+
							" from ticket "+
							" where tipologia = 3 and codice_allerta = ? and ticket.trashed_date is null ";
			String group = " group by comunicazione_rischio";
			
			if (id_asl!=-1)
			{
				sql += " and site_id ="+id_asl+group;
				
			}
			else
			{
				sql = sql+group ;
			}
			PreparedStatement pst = db.prepareStatement(sql) ;
			pst.setString(1, t.getIdAllerta());
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				Boolean comunicazione_rischio = rs.getBoolean("comunicazione_rischio");
				String com_rischio = "N0";
				if (comunicazione_rischio == true)
				{
					com_rischio = "SI" ; 
				}
				int value = rs.getInt("num");
				num_cu_com_rischio.put(com_rischio,value );
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return num_cu_com_rischio ;
	}
	
	public ArrayList<String> getNoteComunicazioneRischio(Connection db , org.aspcfs.modules.allerte_new.base.Ticket t,int id_asl)
	{
		ArrayList<String> note_rischio_list = new ArrayList<String>();
		try
		{
			String sql = 	" select note_rischio from ticket " +
							" where tipologia = 3 and codice_allerta = ? "+
							" and (note_rischio is not null and note_rischio !='') and ticket.trashed_date is null ";
			
			if (id_asl!=-1)
			{
				sql += " and site_id ="+id_asl;
				
			}
		
			PreparedStatement pst = db.prepareStatement(sql) ;
			pst.setString(1, t.getIdAllerta());
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				String note_rischio = rs.getString("note_rischio");
				
				note_rischio_list.add(note_rischio);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return note_rischio_list ;
	}
	
	
	public HashMap<String, Integer> getNumeroControlliAllertapProceduraRichiamo(Connection db , org.aspcfs.modules.allerte_new.base.Ticket t,int id_asl)
	{
		HashMap<String,Integer> num_cu_richiamo = new HashMap<String, Integer>();
		try
		{
			String sql = "select count(ticketid) as num, "+
			"CASE "+
			"WHEN procedura_richiamo =0  THEN 'Attivata'::text "+
			"WHEN procedura_richiamo =1 THEN 'Non Attivata non necessaria'::text "+
			"WHEN procedura_richiamo =2 THEN 'Non Attivata ma necessaria'::text "+
			"END AS procedurarichiamo "+
			"from  ticket " +
			"where tipologia = 3 and codice_allerta =? and ticket.trashed_date is null ";
			
			String group = "group by procedurarichiamo  order by procedurarichiamo ";
			
			if (id_asl != -1)
			{
				sql += " and site_id = ? "+group ;
			}
			else
			{
				sql += group ;
			}
			PreparedStatement pst = db.prepareStatement(sql) ;
			
			pst.setString(1, t.getIdAllerta());
			if (id_asl != -1)
			{
				pst.setInt(2, id_asl)	;
			}
			
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				String esito = rs.getString("procedurarichiamo");
				int value = rs.getInt("num");
				num_cu_richiamo.put(esito,value );
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return num_cu_richiamo ;
	}
	
	public HashMap<String, Integer> getNumeroControlliAllertapProceduraRitiro(Connection db , org.aspcfs.modules.allerte_new.base.Ticket t,int id_asl)
	{
		HashMap<String,Integer> num_cu_ritiro = new HashMap<String, Integer>();
		try
		{
			
			String sql = "select count(ticketid) as num, "+
			"CASE "+
			"WHEN procedura_ritiro =TRUE  THEN 'Attivata'::text "+
			"WHEN procedura_ritiro =FALSE THEN 'Non Attivata'::text "+
			"END AS proceduraritiro "+
			"from  ticket " +
			"where tipologia = 3  and codice_allerta =? and ticket.trashed_date is null and ticket.trashed_date is null ";
			
			String gruop = "group by proceduraritiro order by proceduraritiro " ;
			
			if (id_asl != -1)
			{
				sql += " and site_id = ? "+gruop ;
			}
			else
			{
				sql += gruop ;
			}
			
			PreparedStatement pst = db.prepareStatement( sql) ;
		
			pst.setString(1, t.getIdAllerta());
			if (id_asl!=-1)
			{
				pst.setInt(2, id_asl)	;
			}
			
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				String esito = rs.getString("proceduraritiro");
				int value = rs.getInt("num");
				num_cu_ritiro.put(esito,value );
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return num_cu_ritiro ;
	}
	
	public  class Azione {
		String azione;
		String articolo ;
		int numCu;
		String norma_violata = "" ;
		String note_eventuali = "" ;
		ArrayList<Azione> lista_sotto_azioni = new ArrayList<Azione>();
		public ArrayList<Azione> getLista_sotto_azioni() {
			return lista_sotto_azioni;
		}

		public void setLista_sotto_azioni(ArrayList<Azione> lista_sotto_azioni) {
			this.lista_sotto_azioni = lista_sotto_azioni;
		}

		public String getNorma_violata() {
			return norma_violata;
		}

		public void setNorma_violata(String norma_violata) {
			this.norma_violata = norma_violata;
		}

		public Azione (String azione, String articolo,int numCu)
		{
			this.setArticolo(articolo);
			this.setAzione(azione);
			this.setnumCu(numCu);
		}
		public Azione ()
		{
			
		}
		
		public String getNote_eventuali() {
			return note_eventuali;
		}

		public void setNote_eventuali(String note_eventuali) {
			this.note_eventuali = note_eventuali;
		}

		public int getnumCu()
		{
			return numCu ;
		}
		public void setnumCu(int numCu)
		{
			this.numCu = numCu;
		}
		public String getAzione()
		{
			return azione ;
		}
		public String getArticolo()
		{
			return azione ;
		}
		public void setAzione(String azione)
		{
			this.azione = azione ;
		}
		public void setArticolo(String articolo)
		{
			this.articolo=articolo ;
		}
		
	}
	
	public HashMap<String, Azione> getNumeroControlliAllertaAzioniAdottate(Connection db , org.aspcfs.modules.allerte_new.base.Ticket t,int id_asl)
	{
		HashMap<String,Azione> num_cu_azioni = new HashMap<String, Azione>();
		try
		{
			String sql_sottoazioni = "select count(ticket.ticketid) as num,azioni.description as azione , azioni.code,"+
			"case when  azioni.code=3 then articoli.description ::text "+
			"end as articolo ,"+
			"case when  azioni.code=3 then articoli.code ::integer "+
			"end as articolo_int ,"+
			"case when  azioni.code=4 then note_reati.normaviolata ::text end as norma_violata ,"+
			"case when  azioni.code=4 then note_reati.problem ::text end as note_norma "+
			"from  ticket  join salvataggio_azioni_adottate azione on (ticket.ticketid = azione.id_controllo_ufficiale) "+
			"left join ticket note_reati on (ticket.id_controllo_ufficiale=note_reati.id_controllo_ufficiale and note_reati.tipologia = 6) "+
			"left join lookup_azioni_adottate azioni on (azioni.code = azione.id_azione_adottata) "+
			"left join lookup_articoli_azioni articoli on (ticket.articolo_azioni = articoli.code) "+
			"where ticket.tipologia = 3 and ticket.codice_allerta = ?  and azioni.code = ? and ticket.trashed_date is null " ;
			
			String group_sottoazioni = " group by azioni.description , articoli.description  ,azioni.code , articoli.code,norma_violata ,note_norma" +
			" order by azioni.code";

			String sql = "select count(ticket.ticketid) as num,azioni.description as azione , azioni.code "+
			" from  ticket  join salvataggio_azioni_adottate azione on (ticket.ticketid = azione.id_controllo_ufficiale) "+
			" left join lookup_azioni_adottate azioni on (azioni.code = azione.id_azione_adottata) "+
			" where ticket.tipologia = 3 and ticket.codice_allerta = ? and ticket.trashed_date is null " ;
			
			String group = "group by azioni.description ,azioni.code " +
			"order by azioni.code";

			if (id_asl!=-1)
			{
				sql += " and ticket.site_id = ? "+group;
				sql_sottoazioni += " and ticket.site_id = ? "+group_sottoazioni;
				
			}
			else
			{
				sql = sql+group ;
				sql_sottoazioni += group_sottoazioni;
			}
			PreparedStatement pst = db.prepareStatement(sql);
			PreparedStatement pst1 = db.prepareStatement(sql_sottoazioni);
			if (id_asl != -1)
			{
				pst.setString(1, t.getIdAllerta());
				pst.setInt(2, id_asl)	;
				pst1.setString(1, t.getIdAllerta());
				pst1.setInt(3, id_asl)	;
				
			}
			else
			{
				pst.setString(1, t.getIdAllerta());
				pst1.setString(1, t.getIdAllerta());
				
			}
			ResultSet rs = pst.executeQuery();
			ResultSet rs1 = null ; 
			while (rs.next())
			{
				String azione_value = rs.getString("azione");
				int codice = rs.getInt("code");
				int value = rs.getInt("num");
				Azione azione = new Azione(azione_value,azione_value,value);
				
					pst1.setInt(2, codice);
				
				rs1 = pst1.executeQuery();
				while(rs1.next())
				{
					String articolo = rs1.getString("articolo");
					String norma = rs1.getString("norma_violata");
					String note = rs1.getString("note_norma");
					int num = rs1.getInt("num");
					
					Azione sotto_azione = new Azione();
					sotto_azione.setNorma_violata(norma);
					sotto_azione.setArticolo(articolo);
					sotto_azione.setNote_eventuali(note);
					sotto_azione.setnumCu(num);
					azione.getLista_sotto_azioni().add(sotto_azione);
				}
				
				num_cu_azioni.put(azione_value, azione);
				
				
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return num_cu_azioni ;
	}
	
	
	public String getSommaQuantita(Connection db , int code ,int id_asl,String idAllerta)
	{
		double somma = 0	; 
		double somma_partita = 0 ;
		double somma_bloccata = 0 ;
		String sql = "select unita_misura_text,quantita_partita , quantita_partita_bloccata from " +
				"  ticket join lookup_esito_controllo on (esito_controllo = code) "+ 
				" where tipologia = 3  and codice_allerta = ? and esito_controllo = ?";
		if (id_asl!=-1)
		{
			sql += " and ticket.site_id = ? ";
			
		}
		try 
		{
			PreparedStatement pst = db.prepareStatement(sql);
			pst.setString(1, idAllerta);
			pst.setInt(2,code);
			if(id_asl !=-1)
			{
				pst.setInt(3, id_asl);
			}
			String unita_misura = "" ;
			ResultSet rs = pst.executeQuery();
			while (rs.next())
			{
				if (!"".equals(rs.getString("quantita_partita")) && !"null".equals(rs.getString("quantita_partita")) && rs.getString("quantita_partita") != null)
					somma_partita += Double.parseDouble(rs.getString("quantita_partita"));
				if (!"".equals(rs.getString("quantita_partita_bloccata")) && !"null".equals(rs.getString("quantita_partita_bloccata")) && rs.getString("quantita_partita_bloccata") !=null)
					somma_bloccata += Double.parseDouble(rs.getString("quantita_partita_bloccata"));
				unita_misura = rs.getString("unita_misura_text");
			}
			if (somma_partita != 0)
				return ""+somma_partita+" "+unita_misura;
			else
				if (somma_bloccata != 0)
					return ""+somma_bloccata+" "+unita_misura;
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "" ;
	}

	public void onOpenDocument(PdfWriter writer, Document document)
	{
		try
		{
			
			
			
			
			

		}
		catch(Exception e) {
			throw new ExceptionConverter(e);
		}
	}

	public void onEndPage(PdfWriter writer, Document document) {
		PdfContentByte cb = writer.getDirectContent();
		cb.saveState();
		// write the headertable
		table.setTotalWidth( document.right() - document.left() );
		table.writeSelectedRows( 0, -1, document.left(), document.getPageSize().getHeight() - 5, cb);
		// compose the footer
		String text = "Pagina " + writer.getPageNumber() ;
		float textSize = helv.getWidthPoint(text, 8);
		float textBase = document.bottom() - 15;
		cb.beginText();
		cb.setFontAndSize(helv, 8);
		// for odd pagenumbers, show the footer at the left
		/*
        if ((writer.getPageNumber() & 1) == 1) {
            cb.setTextMatrix(document.left(), textBase);
            cb.showText(text);
            cb.endText();
            cb.addTemplate(tpl, document.left() + textSize, textBase);
        }
        // for even numbers, show the footer at the right
        else {

		 */
		float adjust = helv.getWidthPoint("0", 12);
		cb.setTextMatrix(document.right() - textSize - adjust, textBase);
		cb.showText(text);
		cb.endText();
		cb.addTemplate(tpl, document.right() - adjust, textBase);
		//}
	cb.saveState();
	
	}


	



}
