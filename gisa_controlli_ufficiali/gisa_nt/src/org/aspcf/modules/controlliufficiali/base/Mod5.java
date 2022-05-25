/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.controlliufficiali.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServletResponse;
import org.aspcfs.modules.actions.CFSModule;
import org.aspcfs.modules.lineeattivita.base.LineeAttivita;
import org.aspcfs.modules.nonconformita.base.ElementoNonConformita;
import org.aspcfs.modules.nonconformita.base.TicketList;
import org.aspcfs.modules.vigilanza.base.Ticket;
import org.aspcfs.modules.campioni.util.CampioniUtil;
import com.darkhorseventures.framework.actions.ActionContext;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class Mod5 extends CFSModule {

	
	public Mod5(){}

	public void getFieldsMod5(Connection db,String b, String orgId, int idControllo, Organization org) {

		HttpServletResponse res = null;
		try {
			
			Ticket tic_vig = new Ticket(db, idControllo);
			
			tic_vig.setIdControlloUfficiale(Integer.toString(idControllo));
			
			org.setNum_specie1(tic_vig.getNum_specie1());
			org.setNum_specie2(tic_vig.getNum_specie2());
			org.setNum_specie3(tic_vig.getNum_specie3());
			org.setNum_specie4(tic_vig.getNum_specie4());
			org.setNum_specie5(tic_vig.getNum_specie5());
			org.setNum_specie6(tic_vig.getNum_specie6());
			org.setNum_specie7(tic_vig.getNum_specie7());
			org.setNum_specie8(tic_vig.getNum_specie8());
			org.setNum_specie9(tic_vig.getNum_specie9());
			org.setNum_specie10(tic_vig.getNum_specie10());
			org.setNum_specie11(tic_vig.getNum_specie11());
			org.setNum_specie12(tic_vig.getNum_specie12());
			org.setNum_specie13(tic_vig.getNum_specie13());
			org.setNum_specie14(tic_vig.getNum_specie14());
			org.setNum_specie15(tic_vig.getNum_specie15());
			org.setNum_specie22(tic_vig.getNum_specie22());
			org.setNum_specie23(tic_vig.getNum_specie23());
			org.setNum_specie24(tic_vig.getNum_specie24());
			org.setNum_specie25(tic_vig.getNum_specie25());
			org.setNum_specie26(tic_vig.getNum_specie26());
			
			org.setTipoCondizionalita (tic_vig.getTipo_ispezione_condizionalita());
			// Gestione nuova tipologia
		    ArrayList<LineeAttivita> tipologia = LineeAttivita.load_linea_attivita_per_cu(
					Integer.toString(idControllo), db,-1);

			if (tipologia.size() > 0) {
				LineeAttivita linea = tipologia.get(0);
				org.setTipologia_att(linea.getCategoria());//tipologiaAttivita
			}

			else if (tic_vig.getCodiceAteco() != null) { 
				org.setTipologia_att(tic_vig.getCodiceAteco()+ " " + tic_vig.getDescrizioneCodiceAteco());
			}
			
//			if(org.getTipologia() == 3){
//				String lineaCU = "";
//				ResultSet rs = null;
//				String sql =" select * from linee_attivita_controlli_ufficiali_stab_soa  where id_controllo_ufficiale = ? and trashed_date is null";
//				PreparedStatement pst = db.prepareStatement(sql);
//				pst.setInt(1, idControllo);
//				rs = pst.executeQuery();
//				while (rs.next()){
//					lineaCU = rs.getString("linea_attivita_stabilimenti_soa");
//				}
//				org.setTipologia_att(lineaCU);//tipologiaAttivita
//			}

			// Gestione num_civici_indirizzi
			String verificaNumCivico = null;
			// String verificaNumCivicoLegale = null;
			String verificaNumCivicoRapp = null;
		
			if (org.getIndirizzo_legale_rapp() != null
					&& org.getIndirizzo_legale_rapp().trim()
					.length() >= 3) {
				verificaNumCivicoRapp = org.getIndirizzo_legale_rapp().substring(
								org.getIndirizzo_legale_rapp().length() - 3);
			}

			// SETFIELD
			if (org.getData_referto() != null) {
				org.setAnnoReferto(org.getData_referto().substring(0, 4));
				org.setGiornoReferto(org.getData_referto().substring(8, 11));
				org.setMeseReferto(this.getMeseFromData(org.getData_referto()));		
			}

			if (org.getIndirizzo() != null
					&& org.getIndirizzo().trim().length() >= 0) {
				org.setIndirizzo(org.getIndirizzo());
			}

			if (org.getLegale_rapp() !=null && org.getLegale_rapp().length() == 9
					&& org.getLegale_rapp().contains("null")) {
				org.setLegale_rapp("");
			} 
			
			org.setDomicilioDigitale(org.getDomicilioDigitale());
			org.setLuogo_nascita_rappresentante(org.getLuogo_nascita_rappresentante());
			
			if(org.getData_fine_controllo() !=null)
			{
			org.setGiorno_chiusura(org.getData_fine_controllo()
					.substring(8, 10));
			org.setMese_chiusura(org.getData_fine_controllo()
					.substring(5, 7));
			org.setAnno_chiusura(org.getData_fine_controllo()
					.substring(0, 4));
			}
			else
			{
				org.setGiorno_chiusura("");
				org.setMese_chiusura("");
				org.setAnno_chiusura("");
			}
		
			if(org.getData_nascita_rappresentante() !=null)
			{
				org.setGiornoNascita(org.getData_nascita_rappresentante()
					.substring(8, 10));
				org.setMeseNascita(org.getData_nascita_rappresentante()
					.substring(5, 7));
				org.setAnnoNascita(org.getData_nascita_rappresentante().substring(0, 4));
			}
			else
			{
				org.setGiornoNascita("");
				org.setMeseNascita("");
				org.setAnnoNascita("");
			}
		
			
			//Lista Provvedimenti N.C
			String listaPNC = "";
			org.aspcfs.modules.prvvedimentinc.base.TicketList listProvv = new org.aspcfs.modules.prvvedimentinc.base.TicketList();
			
			
			if (tic_vig.getIdStabilimento()>0)
				listProvv.setIdStabilimento(Integer.parseInt(orgId));
			else
				listProvv.setOrgId(orgId);
			
			if (tic_vig.getAltId()>0){
				listProvv.setOrgId(-1);
				listProvv.buildListControlliAlt(db,tic_vig.getAltId(),Integer.toString(idControllo));
			}
			else
				listProvv.buildListControlli(db,Integer.parseInt(orgId),Integer.toString(idControllo));
			
			Iterator provvedimenti = listProvv.iterator();
			while (provvedimenti.hasNext()) {
				org.aspcfs.modules.prvvedimentinc.base.Ticket tic_p = (org.aspcfs.modules.prvvedimentinc.base.Ticket) provvedimenti.next();
				HashMap<Integer, String> hashMap_provv = tic_p.getProvvedimentiAdottati();
				for (Iterator iter = hashMap_provv.values().iterator(); iter.hasNext();) {
					Object val = iter.next();
					listaPNC += " - "+val.toString();
				}

			}
			
			
			// Richiamo metodo per settore trasporto animali
			//getFieldTrasportoAnimali(context, idControllo);
			//getFieldMotivoIspezione(db, idControllo);
		
			TicketList listConf = new TicketList();
			
			if (tic_vig.getIdStabilimento()>0)
				listConf.setIdStabilimento(Integer.parseInt(orgId));
			else
				listConf.setOrgId(orgId);
			
			if (tic_vig.getAltId()>0){
				listConf.setOrgId(-1);
				listConf.buildListControlliAlt(db, tic_vig.getAltId(), Integer.toString(idControllo));
			}
			else
				listConf.buildListControlli(db, Integer.parseInt(orgId), Integer.toString(idControllo));
			
			Iterator nonConfIterator = listConf.iterator();
			int count_nonConf = 0, count_sign = 0, count_gravi = 0;
			String list_formali = "";
			String list_sign = "";
			String list_gravi = "";
			String processiVerbali = "";
			String descrizione_ncf = "";
			String descrizione_ncs = "";
			String descrizione_ncg = "";
			String valutazioniFormali = "";
			String valutazioniSignificative = "";
			String valutazioniGravi = "";
			String punteggio_formale = "";
			String punteggio_significativo = "";
			String punteggio_grave = "";
			int punteggio_ispezione = 0;
			int count_seq_a = 0;
			int count_seq_s = 0;
			int count_seq_p = 0;
			

			while (nonConfIterator.hasNext()) {
				org.aspcfs.modules.nonconformita.base.Ticket tic = (org.aspcfs.modules.nonconformita.base.Ticket) nonConfIterator
						.next();
				ArrayList<ElementoNonConformita> ncf = tic
						.getNon_conformita_formali();
				ArrayList<ElementoNonConformita> ncs = tic
						.getNon_conformita_significative();
				ArrayList<ElementoNonConformita> ncg = tic
						.getNon_conformita_gravi();

				for (ElementoNonConformita e : ncf) {
					
					++count_nonConf;
					if (e.getNote() != null
							&& !e.getNote().trim().equals("")
							&& !e.getNote().contains(
									"INSERIRE QUI LA DESCRIZIONE")) {
						descrizione_ncf += "[NC" + count_nonConf + "] "
								+ e.getNote() + " ";

					}
				}
				for (ElementoNonConformita e : ncs) {
					++count_sign;
					if (e.getNote() != null
							&& !e.getNote().trim().equals("")
							&& !e.getNote().contains(
									"INSERIRE QUI LA DESCRIZIONE")) {
						descrizione_ncs += "[NC" + count_sign + "] "
								+ e.getNote() + " ";
						// 

					}
				}
				for (ElementoNonConformita e : ncg) {
					++count_gravi;
					if (e.getNote() != null
							&& !e.getNote().trim().equals("")
							&& !e.getNote().contains(
									"INSERIRE QUI LA DESCRIZIONE")) {
						descrizione_ncg += "[NC" + count_gravi + "] "
								+ e.getNote() + " ";
						// 
					}
				}

				org.aspcfs.modules.followup.base.TicketList followupList = new org.aspcfs.modules.followup.base.TicketList();
				// Parte relativa ai follow up
				if (tic_vig.getIdStabilimento()>0)
					followupList.setIdStabilimento(Integer.parseInt(orgId));
				else
					followupList.setOrgId(orgId);
				
				if (tic_vig.getAltId()>0){
					followupList.setOrgId(-1);
					followupList.buildListControlliAlt(db, tic_vig.getAltId(),Integer.toString(tic.getId()),8);
				}
				else
					followupList.buildListControlli(db, Integer.parseInt(orgId),Integer.toString(tic.getId()),8);
				
				Iterator followIterator = followupList.iterator();
				//String tipo_nc_per_followup = null;

				while (followIterator.hasNext()) {
					org.aspcfs.modules.followup.base.Ticket tic_follow = (org.aspcfs.modules.followup.base.Ticket) followIterator
							.next();
					HashMap<Integer, String> hashMap_follow = tic_follow
							.getListaLimitazioniFollowup();
					
					int size = hashMap_follow.size();
					int s = 0;
					
					for (Iterator it = hashMap_follow.values().iterator(); it
							.hasNext();) {
						Object values = it.next();
						++s;
						if (tic_follow.getTipo_nc() == 1) {
							//tipo_nc_per_followup = "Non conformita' formali";
							list_formali += " - "+values.toString();
							
							if(s == size){
								list_formali += " [NOTE] "+ tic_follow.getNoteFollowup();
							}
							
						} else if (tic_follow.getTipo_nc() == 2) {
							//tipo_nc_per_followup = "Non conformita' significative";
							list_sign += " - " +values.toString() + " - " + tic_follow.getNoteFollowup();
							if(s == size){
								list_sign += " [NOTE] "+ tic_follow.getNoteFollowup();
							}
							
						} else {
							//tipo_nc_per_followup = "Non conformita' gravi";
							list_gravi += " - "+ values.toString();
							valutazioniGravi += "- "+ tic_follow.getValutazione();
							
							if(s == size){
								list_gravi += " [NOTE] "+ tic_follow.getNoteFollowup();
							}
						}
													
					}
				}
				
				//Lista dei Sequestri
				org.aspcfs.modules.sequestri.base.TicketList sequestriList = new org.aspcfs.modules.sequestri.base.TicketList(); 
				
				if (tic_vig.getIdStabilimento()>0)
					sequestriList.setIdStabilimento(Integer.parseInt(orgId));
				else
					sequestriList.setOrgId(orgId);
				
				sequestriList.setOrgId(orgId);
				
				if (tic_vig.getAltId()>0){
					sequestriList.setOrgId(-1);
					sequestriList.buildListControlliAlt(db, tic_vig.getAltId(), Integer.toString(tic.getId()),8);
				}
				else
					sequestriList.buildListControlli(db, Integer.parseInt(orgId), Integer.toString(tic.getId()),8);
				
				Iterator sequestriIterator = sequestriList.iterator(); 
				
		
				while (sequestriIterator.hasNext()) {
					org.aspcfs.modules.sequestri.base.Ticket sequestro = (org.aspcfs.modules.sequestri.base.Ticket) sequestriIterator.next();
					
					if(sequestro.getTipologiaSequestro() == 1){
						//Sequestro Amministrativo
						++count_seq_a;
					}
					
					else if(sequestro.getTipologiaSequestro() == 2){
						++count_seq_s;
						if(sequestro.getValutazione() != null && !sequestro.getValutazione().equals("null")){
							sequestro.getValutazione().replaceAll("\n", "");
							valutazioniGravi += "- " + sequestro.getValutazione();
						}
						else 
							valutazioniGravi += "";

					}
					else {
						++count_seq_p;
					}	
					//Recuperare numero verbale ?
					//Verificare il tipo di sequestro
				}
				
				
				//Lista delle Sanzioni
				org.aspcfs.modules.sanzioni.base.TicketList sanzioniList = new org.aspcfs.modules.sanzioni.base.TicketList(); 
				
				if (tic_vig.getIdStabilimento()>0)
					sanzioniList.setIdStabilimento(Integer.parseInt(orgId));
				else
					sanzioniList.setOrgId(orgId);
				
				if (tic_vig.getAltId()>0){
					sanzioniList.setOrgId(-1);
					sanzioniList.buildListControlliAlt(db, tic_vig.getAltId(), Integer.toString(tic.getId()),8);
				}
				else
					sanzioniList.buildListControlli(db, Integer.parseInt(orgId), Integer.toString(tic.getId()),8);
				
				Iterator sanzioneIterator = sanzioniList.iterator(); 
				
				if(sanzioniList.size() > 0){
					org.setNcgCheck1(true);
				}
				
				while (sanzioneIterator.hasNext()) {
					org.aspcfs.modules.sanzioni.base.Ticket sanzione = (org.aspcfs.modules.sanzioni.base.Ticket) sanzioneIterator.next();
					processiVerbali += " - "+sanzione.getTipo_richiesta();
				}

				// 
				// Recupero punteggi
				punteggio_formale = tic.getPuntiFormali();
				punteggio_significativo = tic.getPuntiSignificativi();
				punteggio_grave = tic.getPuntiGravi();
				punteggio_ispezione = tic.getPunteggio();
				valutazioniFormali += tic.getNcFormaliValutazioni() + " ";
				valutazioniSignificative += tic.getNcSignificativeValutazioni() + " ";
				
				
				
				
			count_nonConf = 0;
			count_gravi = 0;
			count_sign = 0;

			
			// Gestione non conformita' nel verbale mod.5
			/*gestioneNonConformita(descrizione_ncf, descrizione_ncs,
					descrizione_ncg, form);
			gestioneFollowUp(list, form);
			gestioneValutazioni(valutazioniFormali, valutazioniSignificative,
					form);*/

		}
			
			//Gestione Descrizione NConformita
			if (descrizione_ncf.trim().length() == 0) {
				org.setDescrizione_ncf("NESSUNA NON CONFORMITA' FORMALE RILEVATA ");
			}else {
				org.setDescrizione_ncf(descrizione_ncf);
			}
			if (descrizione_ncs.trim().length() == 0) {
				org.setDescrizione_ncs("NESSUNA NON CONFORMITA' SIGNIFICATIVA RILEVATA ");	
			} else {
				org.setDescrizione_ncs(descrizione_ncs);
			}
			if (descrizione_ncg.trim().length() == 0) {
				org.setDescrizione_ncg("NESSUNA NON CONFORMITA' GRAVE RILEVATA ");
			}else {
				org.setDescrizione_ncg(descrizione_ncg);
			}
			
			if (list_formali.trim().equals("") && list_formali.trim() == "") {
				org.setFollowUpFormale("NESSUN FOLLOW UP DELLE N.C. FORMALI INSERITO");
			} else {
				org.setFollowUpFormale(list_formali);
			}
			
			if (list_sign.trim().equals("") && list_sign.trim() == "") {
				org.setFollowUpSign("NESSUN FOLLOW UP DELLE N.C. SIGNIFICATIVE INSERITO");
			} else {
				org.setFollowUpSign(list_sign);
				
			}
			
			if (list_gravi.trim().equals("") && list_gravi.trim() == "") {
				org.setFollowUpGravi("NESSUN FOLLOW UP DELLE N.C GRAVI INSERITO");
			} else {
				org.setFollowUpGravi(list_gravi);
				org.setNcgCheck6(true);
			}
			
			if(processiVerbali.trim().equals("") && processiVerbali.trim() == ""){
				org.setProcessiVerbali("");
			}
			else {
				org.setProcessiVerbali(processiVerbali);
			}
			
			if(list_gravi.contains("notifica di illecito amm.")){
				org.setNcgCheck2(true);
			}
			
			if (valutazioniFormali.trim() == "" && valutazioniFormali.equals("") || valutazioniFormali.startsWith("INSERIRE LA VALUTAZIONE")) {
				org.setValutazione_formale("NESSUNA VALUTAZIONE FORMALE");
			}else {
				org.setValutazione_formale(valutazioniFormali);
			}
			
			if (valutazioniSignificative.trim() == "" && valutazioniSignificative.equals("")  || valutazioniSignificative.startsWith("INSERIRE LA VALUTAZIONE")) {
				org.setValutazione_significativa("NESSUNA VALUTAZIONE SIGNIFICATIVA");
			}else {
				org.setValutazione_significativa(valutazioniSignificative);
			}
			
			if (valutazioniGravi.trim() == "" && valutazioniGravi.equals("") || valutazioniGravi.startsWith("INSERIRE LA VALUTAZIONE")) {
				org.setValutazione_grave("NESSUNA VALUTAZIONE GRAVE");
			}else {
				org.setValutazione_grave(valutazioniGravi);
			}
			
			if(listaPNC.trim() == "" && listaPNC.equals("") ){
				org.setListProvNC("NESSUN PROVVEDIMENTO CAUSATO DA N.C.");
			}
			else {
				org.setListProvNC(listaPNC);
			}
			
			if(count_seq_a > 0){
				org.setNcgCheck3(true);
			}
			
			if(count_seq_s > 0){
				org.setNcgCheck5(true);
			}
			
			if(count_seq_p > 0){
				org.setNcgCheck4(true);
			}
			
			org.setPunteggio_formale(punteggio_formale);
			org.setPunteggio_significativo(punteggio_significativo);
			org.setPunteggio_grave(punteggio_grave);
			org.setPunteggio_ispezione(punteggio_ispezione+"");
			
			
			
			org.setComponente_nucleo((tic_vig.getComponenteNucleo() != null ? tic_vig.getComponenteNucleo() : ""));
			org.setComponente_nucleo_due((tic_vig.getComponenteNucleoDue() != null ? tic_vig.getComponenteNucleoDue() : ""));
			org.setComponente_nucleo_tre((tic_vig.getComponenteNucleoTre() != null ? tic_vig.getComponenteNucleoTre() : ""));
			org.setComponente_nucleo_quattro((tic_vig.getComponenteNucleoQuattro() != null ? tic_vig.getComponenteNucleoQuattro() : ""));
			org.setComponente_nucleo_cinque((tic_vig.getComponenteNucleoCinque() != null ? tic_vig.getComponenteNucleoCinque() : ""));
			org.setComponente_nucleo_sei((tic_vig.getComponenteNucleoSei() != null ? tic_vig.getComponenteNucleoSei() : ""));
			org.setComponente_nucleo_sette((tic_vig.getComponenteNucleoSette() != null ? tic_vig.getComponenteNucleoSette() : ""));
			org.setComponente_nucleo_otto((tic_vig.getComponenteNucleoOtto() != null ? tic_vig.getComponenteNucleoOtto() : ""));
			org.setComponente_nucleo_nove((tic_vig.getComponenteNucleoNove() != null ? tic_vig.getComponenteNucleoNove() : ""));
			org.setComponente_nucleo_dieci((tic_vig.getComponenteNucleoDieci() != null ? tic_vig.getComponenteNucleoDieci() : ""));
			//db.close();
			
		} catch (Exception errorMessage) {
			errorMessage.printStackTrace();
		} 
	}

	private void getFieldTrasportoAnimali(ActionContext context, int idControllo) {

		java.sql.Connection db = null;
		int n_capi = 0;
		try {
			db = this.getConnection(context);
			Ticket cu = new Ticket(db, idControllo);
			HashMap<Integer, String> listaSpecieTrasportata = cu.getListaAnimali_Ispezioni();
			for (int key : listaSpecieTrasportata.keySet()){
				
				if(key == 1 && cu.getNum_specie1()>0) {
					n_capi += cu.getNum_specie1();
				}
				else if(key == 2 && cu.getNum_specie2()>0) {
					n_capi += cu.getNum_specie2();
				}
				else if(key == 4 && cu.getNum_specie3()>0) {
					n_capi += cu.getNum_specie3();
				}
				else if(key == 6 && cu.getNum_specie4()>0) {
					n_capi += cu.getNum_specie4();
				}
				else if(key == 10 && cu.getNum_specie5()>0) {
					n_capi += cu.getNum_specie5();
				}
				else if(key == 11 && cu.getNum_specie6()>0) {
					n_capi += cu.getNum_specie6();
				}
				else if(key == 12 && cu.getNum_specie7()>0) {
					n_capi += cu.getNum_specie7();
				}
				else if(key == 13 && cu.getNum_specie8()>0) {
					n_capi += cu.getNum_specie8();
				}
				else if(key == 14 && cu.getNum_specie9()>0) {
					n_capi += cu.getNum_specie9();
				}
				else if(key == 15 && cu.getNum_specie10()>0) {
					n_capi += cu.getNum_specie10();
				}
				else if(key == 16 && cu.getNum_specie11()>0) {
					n_capi += cu.getNum_specie11();
				}
				else if(key == 18 && cu.getNum_specie12()>0) {
					n_capi += cu.getNum_specie12();
				}
				else if(key == 19 && cu.getNum_specie13()>0) {
					n_capi += cu.getNum_specie13();
				}
				else if(key == 20 && cu.getNum_specie14()>0) {
					n_capi += cu.getNum_specie14();
				}
				else if(key == 21 && cu.getNum_specie15()>0) {
					n_capi += cu.getNum_specie15();
				}
				else if(key == 22 && cu.getNum_specie22()>0) {
					n_capi += cu.getNum_specie22();
				}
				else if(key == 23 && cu.getNum_specie23()>0) {
					n_capi += cu.getNum_specie23();
				}
				else if(key == 24 && cu.getNum_specie24()>0) {
					n_capi += cu.getNum_specie24();
				}
				else if(key == 25 && cu.getNum_specie25()>0) {
					n_capi += cu.getNum_specie25();
				}
				else if(key == 26 && cu.getNum_specie26()>0) {
					n_capi += cu.getNum_specie26();
				}
			}
			
			//form.setField("n_capi", n_capi+"");
			
			

		} catch (Exception e) {
			// TODO: handle exception
		}
		finally
		{
			this.freeConnection(context, db);
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
								+ (rect.getWidth() - img.getWidth()) / 2,
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
			// 
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
			// 
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
			// 

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

					int indice_2 = calculateIndex(descrizione_ncf, 2300); // 1770

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
	
	public String getMeseFromData(String data_referto){
		String mese = data_referto.substring(5,7);
		
		switch (Integer.parseInt(mese)) {
			case 01 : mese = "Gennaio"    ;  break;
			case 02 : mese = "Febbraio"   ;  break;
			case 03 : mese = "Marzo"      ;  break;
			case 04 : mese = "Aprile"     ;  break;
			case 05 : mese = "Maggio"     ;  break;
			case 06 : mese = "Giugno"     ;  break;
			case 07 : mese = "Luglio"     ;  break;
			//case 08 : mese = "Agosto"     ;  break;
			//case 09 : mese = "Settembre"  ;  break;
			case 10 : mese = "Ottobre"    ;  break;
			case 11 : mese = "Novembre"   ;  break;
			case 12 : mese = "Dicembre"   ;  break;
		}
		if (mese.equals("08")){
			mese = "Agosto"; 
		}
		if (mese.equals("09")){
			mese = "Settembre";
		}
		
		return mese;
	}
	
	public boolean alfanum (String valore){
		int num = 0;
		int str = 0;
		char [] comodo = valore.toCharArray();
		char prova;

		for (int i=0; i<valore.length(); i++){
			prova = comodo[i];
			if (Character.isDigit(prova)==true){
				str=1;
			} else{
				num=1;
			}	
		}
		if ((str==1) /*&& (num==1)*/) {
			return true;
		} else{
			return false;
		}
	}
	


}