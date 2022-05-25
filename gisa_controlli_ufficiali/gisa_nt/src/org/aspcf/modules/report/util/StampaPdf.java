/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.report.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServletResponse;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import net.sourceforge.barbecue.output.OutputException;

import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.nonconformita.base.ElementoNonConformita;
import org.aspcfs.modules.nonconformita.base.TicketList;
import org.aspcfs.modules.util.imports.ApplicationProperties;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.modules.accounts.base.*;
import org.aspcfs.modules.allerte.base.GestionePEC;
import org.aspcfs.modules.campioni.util.CampioniUtil;
import org.aspcfs.utils.web.LookupList;
import org.servlet.ServletPdf;


import com.darkhorseventures.framework.actions.ActionContext;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.zeroio.webutils.FileDownload;

public class StampaPdf extends CFSModule {

	private Filtro filtro = null;
	
	public StampaPdf(){
		
	}

	public StampaPdf(ActionContext context, Filtro filter) {
		setFiltro(filter);
	}

	public void setFiltro(Filtro filter) {
		this.filtro = filter;
	}

	public Filtro getFiltro() {
		return this.filtro;
	}

	/**
	 * COSTRUZIONE DELLA SCHEDA DI UN CAMPIONE CON TIPO DI ANALISI
	 * BATTEREOLOGICO POPOLO IL FILE DI TEMPLATE A PARTIRE DALL'OGGETTO FILTRO
	 * PASSATO IN INPUT
	 * 
	 * @param context
	 * @param filter
	 */
	public void stampaVerbaleCampioneBattereologico(ActionContext context,
			Filtro filter, String idCampione) {

		try {
			HttpServletResponse res = context.getResponse();

			// generazione del bar code
			String barCodeDir = getWebInfPath(context, "barcode_temp");
			if (filtro.getNumVerbale() != null
					&& filtro.getNumVerbale().length() > 0) {
				File barcodeF = new File(barCodeDir + "/"
						+ filtro.getNumVerbale() + ".png");
				if (!barcodeF.exists()) {
					Barcode barcode = null;
					try {
						barcode = BarcodeFactory.createCode128B(filtro
								.getNumVerbale());

					} catch (BarcodeException e) {
						e.printStackTrace();
					}
					barcode.setBarHeight(70);
					try {
						BarcodeImageHandler.savePNG(barcode, barcodeF);
					} catch (OutputException e) {
						e.printStackTrace();
					}
				}
			}

			// recupero del path in cui sono contenuti i pdf
			String reportDir = getWebInfPath(context, "template_report");
			PdfReader reader = new PdfReader(reportDir
					+ "template_campioni_battereologico.pdf");

			// scrittura nel file
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PdfStamper stamper = new PdfStamper(reader, out);
			AcroFields form = stamper.getAcroFields();
			String verificaNumCivico = null;
			if (filter.getIndirizzo() != null)
				verificaNumCivico = filter.getIndirizzo().substring(
						filter.getIndirizzo().length() - 3,
						filter.getIndirizzo().length());
			String verificaNumCivicoLegale = null;
			if (filter.getIndirizzo() != null)
				verificaNumCivicoLegale = filter.getIndirizzoLegale()
				.substring(filter.getIndirizzoLegale().length() - 3,
						filter.getIndirizzoLegale().length());
			/**
			 * SETTO I CAMPI NEL FILE PDF
			 */
			java.sql.Connection db = null;
			db = this.getConnection(context);
			org.aspcfs.modules.campioni.base.Ticket tic = new org.aspcfs.modules.campioni.base.Ticket(
					db, Integer.parseInt(idCampione));

			String tipoCampione = context.getParameter("tipoAlimenti");
			if (tic.getMotivazione().toLowerCase().contains("sospetto")) {
				form.setField("su_sospetto", "On");
			} else if (tic.getMotivazione().toLowerCase()
					.contains("monitoraggio")) {
				form.setField("piano_monitoraggio", "On");
			} else {
				form.setField("per", "On");
				form.setField("edit_per", tic.getNoteMotivazione());
			}
			form.setField("campione", tipoCampione);
			form.setField("ragione_sociale", filter.getRagioneSociale());
			form.setField("anno", filter.getData_referto().substring(0, 4));
			form.setField("giorno", filter.getData_referto().substring(8, 11));
			form.setField("mese",
					filter.getMeseFromData(filter.getData_referto()));
			form.setField("componente_nucleo", filter.getComponente_nucleo());
			form.setField("componente_nucleo_due",
					filter.getComponente_nucleo_due());
			form.setField("componente_nucleo_tre",
					filter.getComponente_nucleo_tre());
			form.setField("tipologia_attivita", filter.getTipologiaAttivita());
			form.setField("n_reg", filter.getNum_reg());
			form.setField("comune", filter.getComune());
			form.setField("indirizzo", filter.getIndirizzo());
			if (filter.getIndirizzo() != null) {
				if (filter.alfanum(verificaNumCivico)) {
					form.setField("numero", filter.getNumero());
					form.setField(
							"indirizzo",
							filter.getIndirizzo().substring(0,
									filter.getIndirizzo().length() - 3));
				}
			}
			form.setField("codice_fiscale", filter.getCodiceFiscale());
			form.setField("sede_legale", filter.getSedeLegale());
			form.setField("indirizzo_legale", filter.getIndirizzoLegale());
			if (filter.getIndirizzo() != null) {
				if (filter.alfanum(verificaNumCivicoLegale)) {
					form.setField("numero_legale", filter.getNumeroIndLegale());
					form.setField(
							"indirizzo_legale",
							filter.getIndirizzoLegale().substring(0,
									filter.getIndirizzoLegale().length() - 3));
				}
			}
			form.setField("nome_rappresentante",
					filter.getLegaleRappresentante());
			// form.setField("data_nascita_rappresentante",filter.getData_nascita_rappresentante());
			form.setField("luogo_nascita_rappresentante",
					filter.getLuogo_nascita_rappresentante());
			if (!filter.getGiornoNascita().equals("")) {
				form.setField("giorno_nascita", filter.getGiornoNascita());
				form.setField("mese_nascita", filter.getMeseNascita());
				form.setField("anno_nascita", filter.getAnnoNascita());
			}
			// System.out.println("Stampo numero: "+filter.getNumero());
			// incollo il bar code generato in precedenza
			float[] photograph = form.getFieldPositions("barcode");

			Rectangle rect = new Rectangle(photograph[1], photograph[2],
					photograph[3], photograph[4]);
			Image img = Image.getInstance(barCodeDir + "/"
					+ filtro.getNumVerbale() + ".png");
			img.scaleToFit(rect.getWidth(), rect.getHeight());
			img.setAbsolutePosition(
					photograph[1] + (rect.getWidth() - img.getScaledWidth())
					/ 2,
					photograph[2] + (rect.getHeight() - img.getHeight()) / 2);
			PdfContentByte cb = stamper.getOverContent(1);
			cb.addImage(img);
			stamper.close();
			File f = new File(barCodeDir + "/" + filtro.getNumVerbale()
					+ ".png");
			f.delete();

			stamper.setFormFlattening(true);

			stamper.close();
			res.setContentType("application/pdf");

			FileDownload fileDownload = new FileDownload();
			fileDownload
			.setDisplayName("PRI-18-Mod-2-Verbale-campione-battereologico.pdf");
			fileDownload
			.sendFile(context, out.toByteArray(), "application/pdf");

			// res.getOutputStream().write();
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
		}
	}

	public void stampaVerbaleCampioneChimico(ActionContext context,
			Filtro filter, String idCampione) {

		try {
			HttpServletResponse res = context.getResponse();

			// generazione del bar code
			String barCodeDir = getWebInfPath(context, "barcode_temp");
			if (filtro.getNumVerbale() != null
					&& filtro.getNumVerbale().length() > 0) {
				File barcodeF = new File(barCodeDir + "/"
						+ filtro.getNumVerbale() + ".png");
				if (!barcodeF.exists()) {
					Barcode barcode = null;
					try {
						barcode = BarcodeFactory.createCode128B(filtro
								.getNumVerbale());

					} catch (BarcodeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					barcode.setBarHeight(70);
					try {
						BarcodeImageHandler.savePNG(barcode, barcodeF);
					} catch (OutputException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}

			// recupero del path in cui sono contenuti i pdf
			String reportDir = getWebInfPath(context, "template_report");
			PdfReader reader = new PdfReader(reportDir
					+ "template_campioni_chimico.pdf");
			// scrittura nel file
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PdfStamper stamper = new PdfStamper(reader, out);
			AcroFields form = stamper.getAcroFields();
			String verificaNumCivico = filter.getIndirizzo().substring(
					filter.getIndirizzo().length() - 3,
					filter.getIndirizzo().length());
			String verificaNumCivicoLegale = filter.getIndirizzoLegale()
					.substring(filter.getIndirizzoLegale().length() - 3,
							filter.getIndirizzoLegale().length());
			java.sql.Connection db = null;
			db = this.getConnection(context);
			org.aspcfs.modules.campioni.base.Ticket tic = new org.aspcfs.modules.campioni.base.Ticket(
					db, Integer.parseInt(idCampione));
			String tipoCampione = context.getParameter("tipoAlimenti");
			if (tic.getMotivazione().toLowerCase().contains("sospetto")) {
				form.setField("su_sospetto", "On");
			} else if (tic.getMotivazione().toLowerCase()
					.contains("monitoraggio")) {
				form.setField("piano_monitoraggio", "On");
			} else {
				form.setField("per_edit", "On");
				form.setField("per_edit", "Yes");
				form.setField("edit_per", tic.getNoteMotivazione());
			}
			form.setField("campione", tipoCampione);

			/**
			 * SETTO I CAMPI NEL FILE PDF
			 */
			form.setField("ragione_sociale", filter.getRagioneSociale());
			form.setField("anno", filter.getData_referto().substring(0, 4));
			form.setField("giorno", filter.getData_referto().substring(8, 11));
			form.setField("mese",
					filter.getMeseFromData(filter.getData_referto()));
			form.setField("componente_nucleo", filter.getComponente_nucleo());
			form.setField("componente_nucleo_due",
					filter.getComponente_nucleo_due());
			form.setField("componente_nucleo_tre",
					filter.getComponente_nucleo_tre());
			form.setField("tipologia_attivita", filter.getTipologiaAttivita());
			form.setField("num_reg", filter.getNum_reg());
			form.setField("comune", filter.getComune());
			form.setField("indirizzo", filter.getIndirizzo());
			if (filter.alfanum(verificaNumCivico)) {
				form.setField("num", filter.getNumero());
				form.setField(
						"indirizzo",
						filter.getIndirizzo().substring(0,
								filter.getIndirizzo().length() - 3));
			}
			form.setField("codice_fiscale", filter.getCodiceFiscale());
			form.setField("sede_legale", filter.getSedeLegale());
			form.setField("indirizzo_legale", filter.getIndirizzoLegale());
			if (filter.alfanum(verificaNumCivicoLegale)) {
				form.setField("num_legale", filter.getNumeroIndLegale());
				form.setField("indirizzo_legale", filter.getIndirizzoLegale()
						.substring(0, filter.getIndirizzoLegale().length() - 3));
			}
			form.setField("nome_rappresentante",
					filter.getLegaleRappresentante());
			// form.setField("data_nascita_rappresentante",filter.getData_nascita_rappresentante());
			form.setField("luogo_nascita_rappresentante",
					filter.getLuogo_nascita_rappresentante());
			if (!filter.getGiornoNascita().equals("")) {
				form.setField("giorno_nascita", filter.getGiornoNascita());
				form.setField("mese_nascita", filter.getMeseNascita());
				form.setField("anno_nascita", filter.getAnnoNascita());
			}
			// System.out.println("Stampo numero: "+filter.getNumero());

			// incollo il bar code generato in precedenza
			float[] photograph = form.getFieldPositions("barcode");

			Rectangle rect = new Rectangle(photograph[1], photograph[2],
					photograph[3], photograph[4]);
			Image img = Image.getInstance(barCodeDir + "/"
					+ filtro.getNumVerbale() + ".png");
			img.scaleToFit(rect.getWidth(), rect.getHeight());
			img.setAbsolutePosition(
					photograph[1] + (rect.getWidth() - img.getScaledWidth())
					/ 2,
					photograph[2] + (rect.getHeight() - img.getHeight()) / 2);
			PdfContentByte cb = stamper.getOverContent(1);
			cb.addImage(img);
			stamper.close();
			File f = new File(barCodeDir + "/" + filtro.getNumVerbale()
					+ ".png");
			f.delete();

			stamper.setFormFlattening(true);

			stamper.close();
			res.setContentType("application/pdf");

			FileDownload fileDownload = new FileDownload();
			fileDownload
			.setDisplayName("PRI-18-Mod-2-Verbale-campione-chimico.pdf");
			fileDownload
			.sendFile(context, out.toByteArray(), "application/pdf");

			// res.getOutputStream().write();
		} catch (Exception errorMessage) {
			context.getRequest().setAttribute("Error", errorMessage);
			errorMessage.printStackTrace();
		}
	}


	private void getFieldTrasportoAnimali(ActionContext context,
			AcroFields form, Filtro f) {

		java.sql.Connection db = null;
		int n_capi = 0;
		try {
			db = this.getConnection(context);
			Ticket cu = new Ticket(db, f.getIdControllo());
			HashMap<Integer, String> listaSpecieTrasportata = cu.getListaAnimali_Ispezioni();
			for (int key : listaSpecieTrasportata.keySet()){
				form.setField("t_" + key+"", "Yes");
				if(key == 1) {
					n_capi += cu.getNum_specie1();
				}
				else if(key == 2) {
					n_capi += cu.getNum_specie2();
				}
				else if(key == 4) {
					n_capi += cu.getNum_specie3();
				}
				else if(key == 6) {
					n_capi += cu.getNum_specie4();
				}
				else if(key == 10) {
					n_capi += cu.getNum_specie5();
				}
				else if(key == 11) {
					n_capi += cu.getNum_specie6();
				}
				else if(key == 12) {
					n_capi += cu.getNum_specie7();
				}
				else if(key == 13) {
					n_capi += cu.getNum_specie8();
				}
				else if(key == 14) {
					n_capi += cu.getNum_specie9();
				}
				else if(key == 15) {
					n_capi += cu.getNum_specie10();
				}
				else if(key == 16) {
					n_capi += cu.getNum_specie11();
				}
				else if(key == 18) {
					n_capi += cu.getNum_specie12();
				}
				else if(key == 19) {
					n_capi += cu.getNum_specie13();
				}
				else if(key == 20) {
					n_capi += cu.getNum_specie14();
				}
				else if(key == 21) {
					n_capi += cu.getNum_specie15();
				}
				
			}
			form.setField("n_capi", n_capi+"");
			
			

		} catch (Exception e) {
			// TODO: handle exception
		}

	}


	/**
	 * LA FUNZIONE ESEGUE I SEGUENTI STEP :
	 * 
	 * - CERCA IN UNA CARTELLA SPECIFICA (/PDF) IL FILE - APRE IL FILE E INCOLLA
	 * L'IMMAGINE DI INPUT ALL'INTERNO DEL FILE NEL PUNTO SPECIFICATO IN INPUT
	 * 
	 * @param pInFile
	 *            : NOME DEL FILE DA CERCARE
	 * @param pOutFile
	 *            : NOME DEL FILE GENERATO CONTENENTE IL BAR CODE
	 * @param pImageFile
	 *            : IMMAGINE PNG DEL BAR CODE
	 * @param pPDFField
	 *            : NOME DEL FIELD CHE DEVE ESISTERE NEL FILE pInFile
	 * @param response
	 * @throws Exception
	 */
	public static void createPDFWithImageInAcroField(String pInFile,
			String pOutFile, String pImageFile, String pPDFField,
			HttpServletResponse response) throws Exception {
		if (pInFile == null || pOutFile == null || pImageFile == null
				|| pPDFField == null) {
			throw new Exception(
					"Method createPDFWithImageInAcroField() requires non-NULL parameters"
							+ " for InputPDF, OutputPDF, ImageFile, and AcroFormFieldName");
		}
		try {
			PdfReader reader = new PdfReader(pInFile);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
					pOutFile));
			AcroFields form = stamper.getAcroFields();
			float[] photograph = form.getFieldPositions(pPDFField);
			if (photograph == null) {
				response.getOutputStream().print(
						"TEMPLATE NON VALIDO , NON e' STATO TROVATO IL FIELD "
								+ pPDFField);
				System.out
				.println("TEMPLATE NON VALIDO , NON e' STATO TROVATO IL FIELD "
						+ pPDFField);

			} else {
				Rectangle rect = new Rectangle(photograph[1], photograph[2],
						photograph[3], photograph[4]);
				Image img = Image.getInstance(pImageFile);
				img.scaleToFit(rect.getWidth(), rect.getHeight());
				img.setAbsolutePosition(
						photograph[1]
								+ (rect.getWidth() - img.getScaledWidth()) / 2,
								photograph[2] + (rect.getHeight() - img.getHeight())
								/ 2);
				PdfContentByte cb = stamper.getOverContent(1);
				cb.addImage(img);
				stamper.close();
				File f = new File(pImageFile);
				f.delete();
			}

		} catch (IOException e) {

			response.getOutputStream().print(
					"IL FILE RICHIESTO NON e' STATO TROVATO");
			e.printStackTrace();
		}
	}

	private int calculateIndex(String s, int index) {

		while (s.charAt(index) != ' ') {
			// System.out.println("charAt vale: "+ s.charAt(index));
			index--;
		}

		return index;
	}

	private String getFillCharFormali(String descrizione_ncf) {

		String fill_char = "";
		int len = 0;
		if (descrizione_ncf.trim().length() == 0) {
			// Helvetica 98 char *riga
			// len = 1365;
			len = 1470;
		} else {

			// len = 1335 - descrizione_ncf.length();
			// len = 1303 - descrizione_ncf.length();
			// len = 1365 - descrizione_ncf.length();
			int nrow = (int) (Math
					.ceil((double) descrizione_ncf.length() / 120));
			len = (15 - nrow) * 98; // ex 91
			// System.out.println("ceil: "+ nrow);
		}

		for (int i = 0; i < len; i++) {
			fill_char += "_";
		}

		return fill_char;
	}

	private String getFillCharFormali_1() {

		String fill_f = "";
		// ex 455
		for (int i = 0; i < 490; i++) {
			fill_f += "_";
		}
		return fill_f;
	}

	// gestisce solo il caso di meta' riempimento di testo
	private String getFillCharFormali_1diff(String descrizione_ncf) {

		String fill_f = "";
		int nrow = (int) (Math.ceil((double) descrizione_ncf.length() / 120));
		int len = (5 - nrow) * 98; // ex 98
		for (int i = 0; i < len; i++) {
			fill_f += "_";
		}
		return fill_f;
	}

	// 21 righe
	private String getFillCharSignificative(String descrizione_ncs) {

		String fill_char_s = "";
		int len_s = 0;
		if (descrizione_ncs.trim().length() == 0) {
			// len_s = 1820;
			// len_s = 1911;
			len_s = 2058;
		} else {

			// len_s = 1894 - descrizione_ncs.length();
			// len_s = 1871 - descrizione_ncs.length();
			// len_s = 1911 - descrizione_ncs.length();
			int nrow = (int) (Math
					.ceil((double) descrizione_ncs.length() / 120));
			len_s = (21 - nrow) * 98; // ex 91
			// System.out.println("desc significative: "+
			// descrizione_ncs.length());
			// System.out.println("ceil significative: "+ nrow);

		}

		for (int i = 0; i < len_s; i++) {
			fill_char_s += "_";
		}

		return fill_char_s;
	}

	// 3 righe
	private String getFillCharGravi(String descrizione_ncg) {

		String fill_char_g = "";
		int len_g = 0;
		if (descrizione_ncg.trim().length() == 0) {
			// len_g = 184;
			// len_g = 273;
			len_g = 294;
		} else {
			// len_g = 182 - descrizione_ncg.length();
			// len_g = 273 - descrizione_ncg.length();
			int nrow = (int) (Math
					.ceil((double) descrizione_ncg.length() / 120));
			len_g = (3 - nrow) * 98; // ex 98
			// System.out.println("ceil ncg1: "+ nrow);

			// len_g2 = 1547 - descrizione_ncg.length();
		}

		for (int i = 0; i < len_g; i++) {
			fill_char_g += "_";
		}

		return fill_char_g;
	}

	// 18/19 ?righe
	private String getFillCharGravi_1(String descrizione_ncg) {

		String fill_char_g2 = "";
		int len_g2 = 0;

		if (descrizione_ncg.trim().length() == 0) {
			// len_g2 = 1638;
			// len_g2 = 1791;
			len_g2 = 1862;
		} else {
			// len_g2 = 1547 - descrizione_ncg.length();
			// len_g2 = 1638 - descrizione_ncg.length();
			int nrow = (int) (Math
					.ceil((double) descrizione_ncg.length() / 120));
			len_g2 = (18 - nrow) * 98; // 91
			// System.out.println("ceil ncg_2: "+ nrow);
		}

		for (int i = 0; i < len_g2; i++) {
			fill_char_g2 += "_";
		}

		return fill_char_g2;
	}

	private String getFillCharFollow(String list) {

		String fill_follow = "";
		int len_follow = 0;
		if (list.trim().length() == 0) {
			// len_follow = 1440;
			// len_follow = 1456;
			len_follow = 1568;
		} else {
			// len_follow = 1351 - list.length();
			// len_follow = 1432 - list.length();
			// len_follow = 1456 - list.length();
			int nrow = (int) (Math.ceil((double) list.length() / 120));
			len_follow = (16 - nrow) * 98; // ex 91
			// System.out.println("ceil: "+ nrow);
		}
		for (int i = 0; i < len_follow; i++) {
			fill_follow += "_";
		}

		return fill_follow;
	}

	private void gestioneNonConformita(String descrizione_ncf,
			String descrizione_ncs, String descrizione_ncg, AcroFields form) {

		int limite_ncf = 1830; // 1365 in realta' ex 1470

		int limite_ncs = 2300; // ex 1865

		int limite_ncg1 = 365;
		int limite_ncg2 = 2300;// 1638; 1791

		String note_in_coda = "";

		// Nel metodo di gestione ncf
		/*
		 * descrizione_ncf +=
		 * "here are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc here are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc. here are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc here are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc. "
		 * ; descrizione_ncs +=
		 * "here are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc. here are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc. here are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc here are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc."
		 * ; descrizione_ncg +=
		 * "here are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc. here are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc. here are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc here are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc."
		 * ;
		 */

		try {
			if (descrizione_ncf.trim().length() <= limite_ncf
					&& descrizione_ncf.trim().length() != 0) {
				form.setField("non_conformita_rilevate", "\n" + descrizione_ncf
						+ " " + getFillCharFormali(descrizione_ncf));
				form.setField("non_conformita_rilevate_1",
						getFillCharFormali_1());
			}
			// Se e' maggiore del primo limite ma non del secondo....
			else if (descrizione_ncf.trim().length() > limite_ncf
					&& descrizione_ncf.trim().length() < 2320) { // 1965
				// Gestione
				// altre
				// righe...dal
				// 1491simo
				// carattere
				// in poi
				int indice = calculateIndex(descrizione_ncf, limite_ncf);
				// System.out.println("indice ncf vale: "+ indice);
				form.setField("non_conformita_rilevate",
						"\n" + descrizione_ncf.substring(0, indice + 1));
				form.setField(
						"non_conformita_rilevate_1",
						"\n"
								+ descrizione_ncf.substring(indice + 1)
								+ " "
								+ getFillCharFormali_1diff(descrizione_ncf
										.substring(indice + 1)));
			} else {
				if (descrizione_ncf.trim().length() == 0) {
					form.setField("non_conformita_rilevate", "\n"
							+ "NESSUNA NON CONFORMITA' FORMALE RILEVATA  "
							+ getFillCharFormali(descrizione_ncf));
					form.setField("non_conformita_rilevate_1",
							getFillCharFormali_1());
				} else {
					int indice = calculateIndex(descrizione_ncf, limite_ncf);
					// System.out.println("indice ncf vale: "+ indice);
					int indice_2 = calculateIndex(descrizione_ncf, 2300); // 1770
					// System.out.println("indice vale: "+ indice_2);
					form.setField("non_conformita_rilevate", "\n"
							+ descrizione_ncf.substring(0, indice + 1));
					form.setField(
							"non_conformita_rilevate_1",
							"\n"
									+ descrizione_ncf.substring(indice + 1,
											indice_2 + 1)
											+ "[Continua in coda]");
					note_in_coda += "[CONTINUA DA NON CONFORMITA' FORMALI] "
							+ descrizione_ncf.substring(indice_2 + 1);
					form.setField("note_in_coda", note_in_coda);
				}
			}

			if (descrizione_ncs.trim().length() == 0) {
				form.setField("non_conformita_significative", "\n"
						+ "NESSUNA NON CONFORMITA' SIGNIFICATIVA RILEVATA "
						+ getFillCharSignificative(descrizione_ncs));
			} else if (descrizione_ncs.trim().length() <= limite_ncs) {
				form.setField("non_conformita_significative", "\n"
						+ descrizione_ncs + " "
						+ getFillCharSignificative(descrizione_ncs));
			}
			// dobbiamo andare a scrivere in coda al documento
			else {
				int indice = calculateIndex(descrizione_ncs, limite_ncs - 20);
				form.setField("non_conformita_significative", "\n"
						+ descrizione_ncs.substring(0, indice + 1)
						+ "[Continua in coda]");
				note_in_coda += "\n\n"
						+ "[CONTINUA DA NON CONFORMITA' SIGNIFICATIVE] "
						+ descrizione_ncs.substring(indice + 1);
				form.setField("note_in_coda", "\n" + note_in_coda);
			}

			if (descrizione_ncg.trim().length() == 0) {
				form.setField("non_conformita_gravi", "\n"
						+ "NESSUNA NON CONFORMITA' GRAVE RILEVATA  "
						+ getFillCharGravi(descrizione_ncg));
				form.setField("non_gravi", "\n"
						+ getFillCharGravi_1(descrizione_ncg));
			} else if (descrizione_ncg.trim().length() <= limite_ncg1) {
				form.setField("non_conformita_gravi", "\n" + descrizione_ncg
						+ " " + getFillCharGravi(descrizione_ncg));
				form.setField("non_gravi", "\n"
						+ getFillCharGravi_1(descrizione_ncg));
			} else if (descrizione_ncg.trim().length() > limite_ncg1
					&& descrizione_ncg.length() <= limite_ncg2) {
				int indice = calculateIndex(descrizione_ncg, limite_ncg1);
				// System.out.println("indice in gravi: "+indice);
				form.setField("non_conformita_gravi",
						"\n" + descrizione_ncg.substring(0, indice + 1));
				form.setField("non_gravi",
						"\n" + descrizione_ncg.substring(indice + 1) + " "
								+ getFillCharGravi_1(descrizione_ncg));
			}
			// Caso gestione nota in coda ncg
			else {
				int indice = calculateIndex(descrizione_ncg, limite_ncg1);
				int indice_2 = calculateIndex(descrizione_ncg, limite_ncg2 - 20);
				form.setField("non_conformita_gravi",
						"\n" + descrizione_ncg.substring(0, indice + 1));
				form.setField(
						"non_gravi",
						"\n"
								+ descrizione_ncg.substring(indice + 1,
										indice_2 + 1) + "[Continua in coda]");
				note_in_coda += "\n\n" + "[CONTINUA DA NON CONFORMITA' GRAVI] "
						+ descrizione_ncg.substring(indice_2 + 1);
				form.setField("note_in_coda", note_in_coda);

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void gestioneFollowUp(String list, AcroFields form) {

		try {
			if (list.trim().equals("") && list.trim() == "") {
				form.setField("follow_up", "\n" + "NESSUN FOLLOW UP INSERITO  "
						+ getFillCharFollow(list));
			} else {
				form.setField("follow_up", "\n" + list + " "
						+ getFillCharFollow(list));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void gestioneValutazioni(String valutazioniFormali,
			String valutazioniSignificative, AcroFields form) {

		// Recupero num_caratteri per filling _ relativi alle valutazione
		String fill_char_vf = "";
		String fill_char_vs = "";
		int len_vf = 0;
		int len_vs = 0;

		/*
		 * System.out.println("valutazione_formale: "+
		 * valutazioniFormali.trim().length());
		 * System.out.println("DESC valutazione formale: "+ valutazioniFormali);
		 * System.out.println("valutazione_significativa: "+
		 * valutazioniSignificative.trim().length());
		 * System.out.println("DESC valutazione formale: "+
		 * valutazioniSignificative);
		 */

		if (valutazioniFormali.trim().length() == 0
				|| valutazioniFormali.trim().length() == 41) {
			// len_vf = 910;
			len_vf = 882;
		} else {
			// len_vf = 857 - valutazioniFormali.length();
			// len_vf = 826 - valutazioniFormali.length();
			// len_vf = 910 - valutazioniFormali.length();
			int nrow = (int) (Math
					.ceil((double) valutazioniFormali.length() / 98));
			len_vf = (10 - nrow) * 98; // ex 91
			// System.out.println("ceil: "+ nrow);
		}

		for (int i = 0; i < len_vf; i++) {
			fill_char_vf += "_";
		}

		if (valutazioniSignificative.trim().length() == 0
				|| valutazioniSignificative.trim().length() == 41) {
			// len_vs = 920;
			// len_vs = 910;
			len_vs = 882;
		} else {
			// len_vs = 875 - valutazioniSignificative.length();
			// len_vs = 854 - valutazioniSignificative.length();
			// len_vs = 910 - valutazioniSignificative.length();
			int nrow = (int) (Math.ceil((double) valutazioniSignificative
					.length() / 98));
			len_vs = (10 - nrow) * 98; // ex 91
			// System.out.println("ceil: "+ nrow);
		}

		for (int i = 0; i < len_vs; i++) {
			fill_char_vs += "_";
		}

		try {

			// Imposta campi risoluzione non conformita'
			if (valutazioniFormali.trim().length() == 0
					|| valutazioniFormali.startsWith("INSERIRE LA VALUTAZIONE")) {
				form.setField(
						"valutazione_rischio_non_conformita",
						"\n"
								+ "NESSUNA VALUTAZIONE PER LE NON CONFORMITA' FORMALI  "
								+ fill_char_vf);
			} else {
				form.setField("valutazione_rischio_non_conformita", "\n"
						+ valutazioniFormali + " " + fill_char_vf);
			}
			if (valutazioniSignificative.trim().length() == 0
					|| valutazioniSignificative
					.startsWith("INSERIRE LA VALUTAZIONE")) {
				form.setField(
						"valutazione_rischio_non_conformita_significative",
						"\n"
								+ "NESSUNA VALUTAZIONE PER LE NON CONFORMITA' SIGNIFICATIVE  "
								+ fill_char_vs);
			} else {
				form.setField(
						"valutazione_rischio_non_conformita_significative",
						"\n" + valutazioniSignificative + " " + fill_char_vs);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * COSTRUZIONE DELLA SCHEDA DI ACCOMPAGNAMENTO CAMPIONI
	 * 
	 * @param context
	 * @param filter
	 */

}