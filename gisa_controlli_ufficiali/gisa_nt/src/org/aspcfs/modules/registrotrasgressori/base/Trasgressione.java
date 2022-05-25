/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.registrotrasgressori.base; 

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Vector;
import java.util.logging.Logger;

import org.aspcfs.modules.vigilanza.base.ComponenteNucleoIspettivo;
import org.aspcfs.modules.vigilanza.base.Qualifica;
import org.aspcfs.utils.DatabaseUtils;
import org.aspcfs.utils.PopolaCombo;

import com.darkhorseventures.framework.actions.ActionContext;

public class Trasgressione implements Comparable{
	Logger logger = Logger.getLogger("MainLogger");

	 public int compareTo(Object o)
	    {
		  Trasgressione e = (Trasgressione) o; 

		  int thisId = new Integer(this.progressivo);
		  int otherId = new Integer(e.progressivo);

		  if (thisId > otherId)
		    {
		        return -1;
		    }
		    else if (thisId == otherId)
		    {
		        return 0;
		    }
		    else 
		        return 1;
		    
	    }
	 
	 public int getTrimestre(){
		 Calendar calendar = Calendar.getInstance();
		 calendar.setTimeInMillis(dataAccertamento.getTime());
		 int mese = calendar.get(Calendar.MONTH);
		 if (mese>=0 && mese <3)
			 return 1;
		 else if (mese>=3 && mese <6)
			 return 2;
		 else if (mese>=6 && mese <9)
			 return 3;
		 else if (mese>=9 && mese <12)
			 return 4;
		 return -1;
	 }

	private int id = -1;
	private int progressivo = -1;
	private int idControllo = -1;
	private String asl="";
	private String ente1 = "";
	private String ente2 = "";
	private String ente3 = "";
	private String PV = "";
	private String PVsequestro = "";
	private String importoSequestroRiduzione = "";
	private Timestamp dataAccertamento = null;
	private String trasgressore="";
	private String obbligatoInSolido="";
	private float importoSanzioneRidotta=0;
	private boolean oblatoRidotta = false;
	private String fiAssegnatario = "";
	private boolean presentatiScritti = false;
	
	
	private int anno = -1;
	private int idSanzione = -1;
	private Timestamp dataProt = null;
	private boolean pvOblato = false;
	private boolean richiestaRiduzioneSanzione = false;
	private boolean richiestaAudizione = false;
	private String ordinanzaEmessa = "";
	private int importoSanzioneIngiunta = 0;
	private String numOrdinanza = null;
	private Timestamp dataEmissione = null;
	private int giorniLavorazione = -1;
	private boolean ingiunzioneOblata = false;
	private boolean presentataOpposizione = false;
	private boolean sentenzaFavorevole = false;
	private int importoStabilito = 0;
	private boolean ingiunzioneOblataSentenza = false;
	private boolean iscrizioneRuoliSattoriali = false;
	private String note1 = "";
	private String note2 = "";
	
	private boolean rata1 = false;
	
	private boolean rata2 = false;
	private boolean rata3 = false;
	private boolean rata4 = false;
	private boolean rata5 = false;
	private boolean rata6 = false;
	private boolean rata7 = false;
	private boolean rata8 = false;
	private boolean rata9 = false;
	private boolean rata10 = false;
	
	private boolean richiestaRateizzazione = false;
	
	private String allegatoRt = null;
	private String allegatoPv = null;
	private String allegatoAl = null;
	
	private int importoEffettivamenteVersato1;
	private int importoEffettivamenteVersato2;
	private int importoEffettivamenteVersato3;
	private int importoEffettivamenteVersato4;
	
	private boolean competenzaRegionale=true;
	private boolean praticaChiusa = false;
		
	private boolean esisteInRegistro = false;

	private Timestamp dataUltimaNotifica = null;
	private Timestamp dataPagamento = null;
	private boolean pagamentoRidottoConsentito = false;
	
	private Timestamp dataUltimaNotificaOrdinanza = null;
	private Timestamp dataPagamentoOrdinanza = null;
	private boolean pagamentoRidottoConsentitoOrdinanza = false;
	
	private int idUtenteModifica = -1;
	private Timestamp dataModifica = null;
	
	// nuove colonne pagopa
	
	private boolean pagopaPvOblatoRidotta = false;
	private boolean pagopaPvOblatoUltraRidotta = false;
	private Timestamp pagopaPvDataPagamento = null;
	private String pagopaNoRatePagate = null;
	private String pagopaPvIuv = null;
	private String pagopaNoIuv = null;
	
	public Timestamp getDataUltimaNotifica() {
		return dataUltimaNotifica;
	}
	public void setDataUltimaNotifica(Timestamp dataUltimaNotifica) {
		this.dataUltimaNotifica = dataUltimaNotifica;
	}
	
	public void setDataUltimaNotifica(String dataUltimaNotifica) {
		try {this.dataUltimaNotifica = DatabaseUtils.parseDateToTimestamp(dataUltimaNotifica);} catch (Exception e) {}
	}
	
	public Timestamp getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Timestamp dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	public void setDataPagamento(String dataPagamento) {
		try { this.dataPagamento = DatabaseUtils.parseDateToTimestamp(dataPagamento);} catch (Exception e) {}
	}
	
	
	public boolean isPagamentoRidottoConsentito() {
		return pagamentoRidottoConsentito;
	}
	public void setPagamentoRidottoConsentito(boolean pagamentoRidottoConsentito) {
		this.pagamentoRidottoConsentito = pagamentoRidottoConsentito;
	}
	
	public void setPagamentoRidottoConsentito(String pagamentoRidottoConsentito) {
		if (pagamentoRidottoConsentito!=null && (pagamentoRidottoConsentito.equals("true") || pagamentoRidottoConsentito.equals("on")))
			this.pagamentoRidottoConsentito = true;
		else
			this.pagamentoRidottoConsentito = false;
		}
	
	public Timestamp getDataUltimaNotificaOrdinanza() {
		return dataUltimaNotificaOrdinanza;
	}
	public void setDataUltimaNotificaOrdinanza(Timestamp dataUltimaNotificaOrdinanza) {
		this.dataUltimaNotificaOrdinanza = dataUltimaNotificaOrdinanza;
	}
	
	public void setDataUltimaNotificaOrdinanza(String dataUltimaNotificaOrdinanza) {
		try { this.dataUltimaNotificaOrdinanza = DatabaseUtils.parseDateToTimestamp(dataUltimaNotificaOrdinanza);} catch (Exception e) {}
	}
	
	
	public Timestamp getDataPagamentoOrdinanza() {
		return dataPagamentoOrdinanza;
	}
	public void setDataPagamentoOrdinanza(Timestamp dataPagamentoOrdinanza) {
		this.dataPagamentoOrdinanza = dataPagamentoOrdinanza;
	}
	
	public void setDataPagamentoOrdinanza(String dataPagamentoOrdinanza) {
		try { this.dataPagamentoOrdinanza = DatabaseUtils.parseDateToTimestamp(dataPagamentoOrdinanza);} catch (Exception e) {}
	}
	
	public boolean isPagamentoRidottoConsentitoOrdinanza() {
		return pagamentoRidottoConsentitoOrdinanza;
	}
	public void setPagamentoRidottoConsentitoOrdinanza(boolean pagamentoRidottoConsentitoOrdinanza) {
		this.pagamentoRidottoConsentitoOrdinanza = pagamentoRidottoConsentitoOrdinanza;
	}
	
	public void setPagamentoRidottoConsentitoOrdinanza(String pagamentoRidottoConsentitoOrdinanza) {
		if (pagamentoRidottoConsentitoOrdinanza!=null && (pagamentoRidottoConsentitoOrdinanza.equals("true") || pagamentoRidottoConsentitoOrdinanza.equals("on")))
			this.pagamentoRidottoConsentitoOrdinanza = true;	
		else
			this.pagamentoRidottoConsentitoOrdinanza = false;
		}
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getAnno() {
		return anno;
	}
	
	public void setAnno(int anno) {
		this.anno = anno;
	}
	
	public int getAnnoYY() {
		return anno%100;
	}
	
	public int getIdSanzione() {
		return idSanzione;
	}
	public void setIdSanzione(int idSanzione) {
		this.idSanzione = idSanzione;
	}
	
	public void setIdSanzione(String idSanzione) {
		try { this.idSanzione = Integer.parseInt(idSanzione);} catch (Exception e) {}
	}
	
		
	public Timestamp getDataProt() {
		return dataProt;
	}
	public void setDataProt(Timestamp dataProt) {
		this.dataProt = dataProt;
	}
		
	public void setDataProt(String dataProt) {
		try { this.dataProt = DatabaseUtils.parseDateToTimestamp(dataProt);} catch (Exception e) {}
	}
	
	public boolean isPvOblato() {
		return pvOblato;
	}
	public void setPvOblato(boolean pvOblato) {
		this.pvOblato = pvOblato;
	}
	public void setPvOblato(String pvOblato) {
		if (pvOblato!=null && (pvOblato.equals("true") || pvOblato.equals("on")))
			this.pvOblato = true;
		else
			this.pvOblato = false;
	}
	public int getIdControllo() {
		return idControllo;
	}
	public void setIdControllo(int idControllo) {
		this.idControllo = idControllo;
	}
	public void setIdControllo(String idControllo) {
		try { this.idControllo = Integer.parseInt(idControllo);} catch (Exception e) {}
	}
	public String getEnte1() {
		return ente1;
	}
	public void setEnte1(String ente1) {
		this.ente1 = ente1;
	}
	public String getEnte2() {
		return ente2;
	}
	public void setEnte2(String ente2) {
		this.ente2 = ente2;
	}
	public String getEnte3() {
		return ente3;
	}
	
	public LinkedHashMap<String, String> getListaEnti() {
		LinkedHashMap<String, String> listaEnti = new LinkedHashMap<String, String>();
		listaEnti.put(ente1, ente1);
		listaEnti.put(ente2, ente2);
		listaEnti.put(ente3, ente3);
		return listaEnti;
	}
	
	public void setEnte3(String ente3) {
		this.ente3 = ente3;
	}
	public String getPV() {
		return PV;
	}
	public void setPV(String pV) {
		PV = pV;
	}
	public Timestamp getDataAccertamento() {
		return dataAccertamento;
	}
	public void setDataAccertamento(Timestamp dataAccertamento) {
		this.dataAccertamento = dataAccertamento;
	}
	public void setDataAccertamento(String dataAccertamento) {
		try { this.dataAccertamento = DatabaseUtils.parseDateToTimestamp(dataAccertamento);} catch (Exception e) {}
	}
	public String getTrasgressore() {
		return trasgressore;
	}
	public void setTrasgressore(String trasgressore) {
		this.trasgressore = trasgressore;
	}
	public String getObbligatoInSolido() {
		return obbligatoInSolido;
	}
	public void setObbligatoInSolido(String obbligatoInSolido) {
		this.obbligatoInSolido = obbligatoInSolido;
	}
	public int getImportoSanzioneRidotta() {
		return (int)importoSanzioneRidotta;
	}
	public void setImportoSanzioneRidotta(float importoSanzioneRidotta) {
		this.importoSanzioneRidotta = importoSanzioneRidotta;
	}

	public String getPVsequestro() {
		return PVsequestro;
	}
	public void setPVsequestro(String pVsequestro) {
		PVsequestro = pVsequestro;
	}
	public boolean isOblatoRidotta() {
		return oblatoRidotta;
	}
	public void setOblatoRidotta(boolean oblatoRidotta) {
		this.oblatoRidotta = oblatoRidotta;
	}
	public void setOblatoRidotta(String oblatoRidotta) {
		if (oblatoRidotta!=null && (oblatoRidotta.equals("true") || oblatoRidotta.equals("on")))
			this.oblatoRidotta = true;
		else
			this.oblatoRidotta = false;
	}
	public String getFiAssegnatario() {
		return fiAssegnatario;
	}
	public void setFiAssegnatario(String fiAssegnatario) {
		this.fiAssegnatario = fiAssegnatario;
	}
	public boolean isPresentatiScritti() {
		return presentatiScritti;
	}
	public void setPresentatiScritti(boolean presentatiScritti) {
		this.presentatiScritti = presentatiScritti;
	}
	public void setPresentatiScritti(String presentatiScritti) {
		if (presentatiScritti!=null && (presentatiScritti.equals("true") || presentatiScritti.equals("on")))
			this.presentatiScritti = true;
		else
			this.presentatiScritti = false;
	}
	public boolean isRichiestaRiduzioneSanzione() {
		return richiestaRiduzioneSanzione;
	}
	public void setRichiestaRiduzioneSanzione(boolean richiestaRiduzioneSanzione) {
		this.richiestaRiduzioneSanzione = richiestaRiduzioneSanzione;
	}
	public void setRichiestaRiduzioneSanzione(String richiestaRiduzioneSanzione) {
		if (richiestaRiduzioneSanzione!=null && (richiestaRiduzioneSanzione.equals("true") || richiestaRiduzioneSanzione.equals("on")))
			this.richiestaRiduzioneSanzione = true;
		else
			this.richiestaRiduzioneSanzione = false;
	}
	public boolean isRichiestaAudizione() {
		return richiestaAudizione;
	}
	public void setRichiestaAudizione(boolean richiestaAudizione) {
		this.richiestaAudizione = richiestaAudizione;
	}
	public void setRichiestaAudizione(String richiestaAudizione) {
		if (richiestaAudizione!=null && (richiestaAudizione.equals("true") || richiestaAudizione.equals("on")))
			this.richiestaAudizione = true;
		else
			this.richiestaAudizione = false;
	}
	
	public int getImportoSanzioneIngiunta() {
		return importoSanzioneIngiunta;
	}
	public void setImportoSanzioneIngiunta(int importoSanzioneIngiunta) {
		this.importoSanzioneIngiunta = importoSanzioneIngiunta;
	}
	public void setImportoSanzioneIngiunta(String importoSanzioneIngiunta) {
		try { this.importoSanzioneIngiunta = Integer.parseInt(importoSanzioneIngiunta);} catch (Exception e) {}
	}
	public Timestamp getDataEmissione() {
		return dataEmissione;
	}
	public void setDataEmissione(Timestamp dataEmissione) {
		this.dataEmissione = dataEmissione;
//		if (dataEmissione!=null){
//			double giorni = calcolaDifferenza(dataEmissione, this.dataProt);
//			setGiorniLavorazione(giorni);}
	}  
	public void setDataEmissione(String dataEmissione) {
		try { this.dataEmissione = DatabaseUtils.parseDateToTimestamp(dataEmissione);} catch (Exception e) {}

	}
	public int getGiorniLavorazione() {
		return giorniLavorazione;
	}
	public void setGiorniLavorazione(int giorniLavorazione) {
		this.giorniLavorazione = giorniLavorazione;
	}
	public void setGiorniLavorazione(double giorniLavorazione) {
		this.giorniLavorazione = (int) giorniLavorazione;
	}
	
	public void setGiorniLavorazione(String giorniLavorazione) {
		try { this.giorniLavorazione = Integer.parseInt(giorniLavorazione);} catch (Exception e) {}
	}
	
	
	public boolean isIngiunzioneOblata() {
		return ingiunzioneOblata;
	}
	public void setIngiunzioneOblata(boolean ingiunzioneOblata) {
		this.ingiunzioneOblata = ingiunzioneOblata;
	}
	public void setIngiunzioneOblata(String ingiunzioneOblata) {
		if (ingiunzioneOblata!=null && (ingiunzioneOblata.equals("true") || ingiunzioneOblata.equals("on")))
			this.ingiunzioneOblata = true;
		else
			this.ingiunzioneOblata = false;
	}
	public boolean isPresentataOpposizione() {
		return presentataOpposizione;
	}
	public void setPresentataOpposizione(boolean presentataOpposizione) {
		this.presentataOpposizione = presentataOpposizione;
	}
	public void setPresentataOpposizione(String presentataOpposizione) {
		if (presentataOpposizione!=null && (presentataOpposizione.equals("true") || presentataOpposizione.equals("on")))
			this.presentataOpposizione = true;
		else
			this.presentataOpposizione = false;
	}
	public boolean isSentenzaFavorevole() {
		return sentenzaFavorevole;
	}
	public void setSentenzaFavorevole(boolean sentenzaFavorevole) {
		this.sentenzaFavorevole = sentenzaFavorevole;
	}
	public void setSentenzaFavorevole(String sentenzaFavorevole) {
		if (sentenzaFavorevole!=null && (sentenzaFavorevole.equals("true") || sentenzaFavorevole.equals("SI")))
			this.sentenzaFavorevole = true;
		else
			this.sentenzaFavorevole = false;
	}
	public int getImportoStabilito() {
		return importoStabilito;
	}
	public void setImportoStabilito(int importoStabilito) {
		this.importoStabilito = importoStabilito;
	}
	public void setImportoStabilito(String importoStabilito) {
		try { this.importoStabilito = Integer.parseInt(importoStabilito);} catch (Exception e) {}
	}
	public boolean isIngiunzioneOblataSentenza() {
		return ingiunzioneOblataSentenza;
	}
	public void setIngiunzioneOblataSentenza(boolean ingiunzioneOblataSentenza) {
		this.ingiunzioneOblataSentenza = ingiunzioneOblataSentenza;
	}
	public void setIngiunzioneOblataSentenza(String ingiunzioneOblataSentenza) {
		if (ingiunzioneOblataSentenza!=null && (ingiunzioneOblataSentenza.equals("true") || ingiunzioneOblataSentenza.equals("on")))
			this.ingiunzioneOblataSentenza = true;
		else
			this.ingiunzioneOblataSentenza = false;
	}
	public boolean isIscrizioneRuoliSattoriali() {
		return iscrizioneRuoliSattoriali;
	}
	public void setIscrizioneRuoliSattoriali(boolean iscrizioneRuoliSattoriali) {
		this.iscrizioneRuoliSattoriali = iscrizioneRuoliSattoriali;
	}
	public void setIscrizioneRuoliSattoriali(String iscrizioneRuoliSattoriali) {
		if (iscrizioneRuoliSattoriali!=null && (iscrizioneRuoliSattoriali.equals("true") || iscrizioneRuoliSattoriali.equals("on")))
			this.iscrizioneRuoliSattoriali = true;
		else
			this.iscrizioneRuoliSattoriali = false;
	}
	public String getNote2() {
		return note2;
	}
	public void setNote2(String note2) {
		this.note2 = note2;
	}
	
	public String getNote1() {
		return note1;
	}
	public void setNote1(String note1) {
		this.note1 = note1;
	}
	
	public int getImportoEffettivamenteVersato1() {
		return importoEffettivamenteVersato1;
	}
	public void setImportoEffettivamenteVersato1(int importoEffettivamenteVersato1) {
		this.importoEffettivamenteVersato1 = importoEffettivamenteVersato1;
	}
	public void setImportoEffettivamenteVersato1(String importoEffettivamenteVersato1) {
		try { this.importoEffettivamenteVersato1 = Integer.parseInt(importoEffettivamenteVersato1);} catch (Exception e) {}
	}
	public int getImportoEffettivamenteVersato3() {
		return importoEffettivamenteVersato3;
	}
	public void setImportoEffettivamenteVersato3(int importoEffettivamenteVersato3) {
		this.importoEffettivamenteVersato3 = importoEffettivamenteVersato3;
	}
	public void setImportoEffettivamenteVersato3(String importoEffettivamenteVersato3) {
		try { this.importoEffettivamenteVersato3 = Integer.parseInt(importoEffettivamenteVersato3);} catch (Exception e) {}
	}
	public int getImportoEffettivamenteVersato2() {
		return importoEffettivamenteVersato2;
	}
	public void setImportoEffettivamenteVersato2(int importoEffettivamenteVersato2) {
		this.importoEffettivamenteVersato2 = importoEffettivamenteVersato2;
	}
	public void setImportoEffettivamenteVersato2(String importoEffettivamenteVersato2) {
		try { this.importoEffettivamenteVersato2 = Integer.parseInt(importoEffettivamenteVersato2);} catch (Exception e) {}
	}
	
	public int getImportoEffettivamenteVersato4() {
		return importoEffettivamenteVersato4;
	}
	public void setImportoEffettivamenteVersato4(int importoEffettivamenteVersato4) {
		this.importoEffettivamenteVersato4 = importoEffettivamenteVersato4;
	}
	public void setImportoEffettivamenteVersato4(String importoEffettivamenteVersato4) {
		try { this.importoEffettivamenteVersato4 = Integer.parseInt(importoEffettivamenteVersato4);} catch (Exception e) {}
	}
	public Trasgressione() {
 
	}
	
	public Trasgressione(ResultSet rs, Connection db) throws SQLException {
		buildRecord(rs, db);
		}
	
	public Trasgressione(Connection db, int id) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from registro_trasgressori_vista where id = ?");
		pst.setInt(1, id);
		ResultSet rs = DatabaseUtils.executeQuery(db, pst); 
		 while (rs.next()){
				 buildRecord(rs, db);
	}
	}

	private void buildRecord(ResultSet rs, Connection db) throws SQLException {
		this.anno =  rs.getInt("anno_controllo");
		this.idControllo = rs.getInt("idcontrollo");
		this.idSanzione = rs.getInt("idsanzione");
		this.setAsl(rs.getString("asl"));
		this.ente1 = rs.getString("ente1");
		this.ente2 = rs.getString("ente2");
		this.ente3 = rs.getString("ente3");
		this.PV = rs.getString("numeroverbale");
		this.PVsequestro = rs.getString("numeroverbalesequestri");
		this.importoSequestroRiduzione = rs.getString("importosequestroriduzione");
		this.dataAccertamento = rs.getTimestamp("dataaccertamento");
		this.trasgressore = rs.getString("trasgressore");
		this.obbligatoInSolido = rs.getString("obbligato");
		this.importoSanzioneRidotta = rs.getFloat("importosanzioneridotta");
	 	this.allegatoRt = rs.getString("allegato_rt"); 
		this.allegatoPv = rs.getString("allegato_pv");
	 	this.allegatoAl = rs.getString("allegato_al");
	 	
	 	this.pagopaPvOblatoRidotta = rs.getBoolean("pagopa_pv_oblato_ridotta");
	 	this.pagopaPvOblatoUltraRidotta = rs.getBoolean("pagopa_pv_oblato_ultraridotta");
	 	this.pagopaPvDataPagamento = rs.getTimestamp("pagopa_pv_data_pagamento");
	 	this.pagopaNoRatePagate = rs.getString("pagopa_no_rate_pagate");
		this.pagopaPvIuv = rs.getString("pagopa_pv_iuv");
		this.pagopaNoIuv = rs.getString("pagopa_no_iuv");
		
		this.esisteInRegistro = rs.getBoolean("esistente");

		if (esisteInRegistro){
			completaDaRegistro(rs);
	}
	//	setEntiAccertatori(db);		
	}

	public Vector cercaControlli(Connection db, int anno, int trimestre){ 
		ResultSet rs = null;
		Vector trasgrList = new Vector();
		PreparedStatement pst;
		try {
			
			//String query = org.aspcfs.modules.accounts.utils.ApplicationProperties.getProperty("controlli_registro_sanzioni");
			String query = "select * from registro_trasgressori_vista";
			pst = db.prepareStatement(query);
		
			rs = DatabaseUtils.executeQuery(db, pst); 
			 while (rs.next()){
					 Trasgressione tras = new Trasgressione(rs, db);
					 if (tras.getAnno()== anno){
						 if (trimestre <=0 || tras.getTrimestre()==trimestre) {
							 if (!tras.esisteInRegistro /*&& anno == this.anno*/){
								 tras.setCompetenzaRegionale(PopolaCombo.isPrevistoPagoPA(tras.getIdSanzione()));
								 tras.insert(db);
								 }
							 trasgrList.add(tras); 
						 }
					 }
					 
					 
				 }
		rs.close();
		pst.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return trasgrList;
	}
	

	public void completaDaRegistro(ResultSet rs) throws SQLException {
		
		try {
				 	this.id = rs.getInt("id");
				 	this.progressivo = rs.getInt("progressivo");
				 	this.dataProt = rs.getTimestamp("data_prot_entrata");
				 	this.pvOblato = rs.getBoolean("pv_oblato_ridotta");
					this.fiAssegnatario = rs.getString("fi_assegnatario");
				 	this.presentatiScritti = rs.getBoolean("presentati_scritti");
				 	this.richiestaRateizzazione = rs.getBoolean("richiesta_rateizzazione");
				 	this.richiestaRiduzioneSanzione = rs.getBoolean("presentata_richiesta_riduzione");
				 	this.richiestaAudizione = rs.getBoolean("presentata_richiesta_audizione");
				 	this.ordinanzaEmessa = rs.getString("ordinanza_emessa");
				 	this.importoSanzioneIngiunta = rs.getInt("importo_sanzione_ingiunta");
				 	this.numOrdinanza = rs.getString("num_ordinanza");
				 	this.dataEmissione = rs.getTimestamp("data_emissione");
				 	this.giorniLavorazione = rs.getInt("giorni_lavorazione");
				 	this.ingiunzioneOblata = rs.getBoolean("ordinanza_ingiunzione_oblata");
				 	this.presentataOpposizione = rs.getBoolean("presentata_opposizione");
				 	this.sentenzaFavorevole= rs.getBoolean("sentenza_favorevole");
				 	this.importoStabilito = rs.getInt("importo_stabilito");
				 	this.ingiunzioneOblataSentenza = rs.getBoolean("ordinanza_ingiunzione_sentenza");
				 	this.iscrizioneRuoliSattoriali = rs.getBoolean("iscrizione_ruoli_sattoriali");
				 	this.note1 = rs.getString("note1");
				 	this.note2 = rs.getString("note2");
				 	this.rata1 = rs.getBoolean("rata1");
				 	this.rata2 = rs.getBoolean("rata2");
				 	this.rata3 = rs.getBoolean("rata3");
				 	this.rata4 = rs.getBoolean("rata4");
				 	this.rata5 = rs.getBoolean("rata5");
				 	this.rata6 = rs.getBoolean("rata6");
				 	this.rata7 = rs.getBoolean("rata7");
				 	this.rata8 = rs.getBoolean("rata8");
				 	this.rata9 = rs.getBoolean("rata9");
				 	this.rata10 = rs.getBoolean("rata10");
				 	this.allegatoRt = rs.getString("allegato_rt");
				 	this.importoEffettivamenteVersato1 = rs.getInt("importo_effettivamente_versato1");
				 	this.importoEffettivamenteVersato2 = rs.getInt("importo_effettivamente_versato2");
				 	this.importoEffettivamenteVersato3 = rs.getInt("importo_effettivamente_versato3");
					this.importoEffettivamenteVersato4 = rs.getInt("importo_effettivamente_versato4");
					this.competenzaRegionale = rs.getBoolean("competenza_regionale");
					this.praticaChiusa = rs.getBoolean("pratica_chiusa");
					
					this.dataUltimaNotifica = rs.getTimestamp("data_ultima_notifica");
					this.dataPagamento = rs.getTimestamp("data_pagamento");
					this.pagamentoRidottoConsentito = rs.getBoolean("pagamento_ridotto_consentito");
					
					this.dataUltimaNotificaOrdinanza = rs.getTimestamp("data_ultima_notifica_ordinanza");
					this.dataPagamentoOrdinanza = rs.getTimestamp("data_pagamento_ordinanza");
					this.pagamentoRidottoConsentitoOrdinanza = rs.getBoolean("pagamento_ridotto_consentito_ordinanza");
				 
					this.idUtenteModifica = rs.getInt("id_utente_modifica");
					this.dataModifica = rs.getTimestamp("data_modifica");
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}
	
public void completaDaRequest(ActionContext context) throws SQLException {
		
		setDataProt(context.getRequest().getParameter("data_prot_entrata"));
		setCompetenzaRegionale(context.getRequest().getParameter("competenza_regionale"));
		setDataUltimaNotifica(context.getRequest().getParameter("data_ultima_notifica"));
		setPvOblato(context.getRequest().getParameter("pv_oblato_ridotta"));
		setImportoEffettivamenteVersato1(context.getRequest().getParameter("importo_effettivamente_versato1"));
		setDataPagamento(context.getRequest().getParameter("data_pagamento"));
		setPagamentoRidottoConsentito(context.getRequest().getParameter("pagamento_ridotto_consentito"));
		setFiAssegnatario(context.getRequest().getParameter("fi_assegnatario"));
		setPresentatiScritti(context.getRequest().getParameter("presentati_scritti"));
		setRichiestaRiduzioneSanzione(context.getRequest().getParameter("presentata_richiesta_riduzione"));
		setRichiestaAudizione(context.getRequest().getParameter("presentata_richiesta_audizione"));
		setOrdinanzaEmessa(context.getRequest().getParameter("ordinanza_emessa"));
		setNumOrdinanza(context.getRequest().getParameter("num_ordinanza"));
		setDataEmissione(context.getRequest().getParameter("data_emissione"));
		setGiorniLavorazione(context.getRequest().getParameter("giorni_lavorazione"));
		setImportoSanzioneIngiunta(context.getRequest().getParameter("importo_sanzione_ingiunta"));
		setDataUltimaNotificaOrdinanza(context.getRequest().getParameter("data_ultima_notifica_ordinanza"));
		setDataPagamentoOrdinanza(context.getRequest().getParameter("data_pagamento_ordinanza"));
		setRichiestaRateizzazione(context.getRequest().getParameter("richiesta_rateizzazione"));
		setRata1(context.getRequest().getParameter("rata1"));
		setRata2(context.getRequest().getParameter("rata2"));
		setRata3(context.getRequest().getParameter("rata3"));
		setRata4(context.getRequest().getParameter("rata4"));
		setRata5(context.getRequest().getParameter("rata5"));
		setRata6(context.getRequest().getParameter("rata6"));
		setRata7(context.getRequest().getParameter("rata7"));
		setRata8(context.getRequest().getParameter("rata8"));
		setRata9(context.getRequest().getParameter("rata9"));
		setRata10(context.getRequest().getParameter("rata10"));
		setIngiunzioneOblata(context.getRequest().getParameter("ordinanza_ingiunzione_oblata"));
		setImportoEffettivamenteVersato2(context.getRequest().getParameter("importo_effettivamente_versato2"));
		setPresentataOpposizione(context.getRequest().getParameter("presentata_opposizione"));
		setSentenzaFavorevole(context.getRequest().getParameter("sentenza_favorevole"));
		setImportoStabilito(context.getRequest().getParameter("importo_stabilito"));
		setIngiunzioneOblataSentenza(context.getRequest().getParameter("ordinanza_ingiunzione_sentenza"));
		setImportoEffettivamenteVersato3(context.getRequest().getParameter("importo_effettivamente_versato3"));
		setIscrizioneRuoliSattoriali(context.getRequest().getParameter("iscrizione_ruoli_sattoriali"));
		setImportoEffettivamenteVersato4(context.getRequest().getParameter("importo_effettivamente_versato4"));
		setNote1(context.getRequest().getParameter("note1"));
		setNote2(context.getRequest().getParameter("note2"));
		setAllegatoRt(context.getRequest().getParameter("allegato_rt"));
		setPraticaChiusa(context.getRequest().getParameter("pratica_chiusa"));
	
	}
	
	public boolean esiste(Connection db){
		
		ResultSet rs = null;
		PreparedStatement pst;
		try {
			
			String query = "select * from registro_trasgressori_values where id_sanzione = "+idSanzione+" and anno = "+anno+" and trashed_date is null ";
			pst = db.prepareStatement(query);
			rs = DatabaseUtils.executeQuery(db, pst); 
			 if (rs.next()){
					 return true;
				 }
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}


public boolean insert(Connection db) {
	
	int newProgressivo = -1;
	String sqlProgressivo = "select MAX(progressivo) from registro_trasgressori_values where anno = ? ";
	PreparedStatement pstProgressivo;
	try {
		pstProgressivo = db.prepareStatement(sqlProgressivo);
		pstProgressivo.setInt(1, anno); 
		ResultSet rs = pstProgressivo.executeQuery();
		if (rs.next())
			newProgressivo = rs.getInt(1);
		newProgressivo++;
		this.setProgressivo(newProgressivo);
		} catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	StringBuffer sql = new StringBuffer();
	boolean doCommit = false;

	sql.append("INSERT INTO registro_trasgressori_values(id_controllo_ufficiale, id_sanzione, anno, progressivo, ");
	sql.append(" data_prot_entrata, pv_oblato_ridotta, fi_assegnatario, presentati_scritti, richiesta_rateizzazione, rata1, rata2, rata3, rata4, rata5, rata6, rata7, rata8, rata9, rata10, ");
	sql.append(" presentata_richiesta_riduzione, presentata_richiesta_audizione, ordinanza_emessa, importo_sanzione_ingiunta, num_ordinanza, data_emissione, giorni_lavorazione, data_ultima_notifica_ordinanza, ");
	sql.append(" ordinanza_ingiunzione_oblata, presentata_opposizione, sentenza_favorevole, importo_stabilito, ordinanza_ingiunzione_sentenza, iscrizione_ruoli_sattoriali, ");
	sql.append("note1, note2, importo_effettivamente_versato1, importo_effettivamente_versato2, importo_effettivamente_versato3, importo_effettivamente_versato4, ");
	sql.append("competenza_regionale, data_inserimento, data_modifica  ");
	sql.append(")");
	sql.append("VALUES ( ");
	sql.append(" ?, ?, ?, ?");
	sql.append(", ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?");
	sql.append(", ?, ?, ?, ?, ?, ?,?, ?, ");
	sql.append("?, ?, ?, ?, ?, ?, ");
	sql.append(" ?, ?, ?, ?, ?, ?, ");
	sql.append(" ?, now(), now()");
	sql.append(") returning id as id_inserito");
	int i = 0;
	PreparedStatement pst;
	try {
		pst = db.prepareStatement(sql.toString());
	
	pst.setInt(++i, idControllo); 
	pst.setInt(++i, idSanzione);
	pst.setInt(++i, anno);
	pst.setInt(++i, progressivo);
	pst.setTimestamp(++i, dataProt);
	pst.setBoolean(++i, pvOblato);
	pst.setString(++i, fiAssegnatario);
	pst.setBoolean(++i, presentatiScritti);
	pst.setBoolean(++i, richiestaRateizzazione);
	pst.setBoolean(++i, rata1);
	pst.setBoolean(++i, rata2);
	pst.setBoolean(++i, rata3);
	pst.setBoolean(++i, rata4);
	pst.setBoolean(++i, rata5);
	pst.setBoolean(++i, rata6);
	pst.setBoolean(++i, rata7);
	pst.setBoolean(++i, rata8);
	pst.setBoolean(++i, rata9);
	pst.setBoolean(++i, rata10);
	pst.setBoolean(++i, richiestaRiduzioneSanzione);
	pst.setBoolean(++i, richiestaAudizione);
	pst.setString(++i, ordinanzaEmessa);
	pst.setInt(++i, importoSanzioneIngiunta);
	pst.setString(++i, numOrdinanza);
	pst.setTimestamp(++i, dataEmissione);
	pst.setInt(++i, giorniLavorazione);
	pst.setTimestamp(++i, dataUltimaNotificaOrdinanza);
	pst.setBoolean(++i, ingiunzioneOblata);
	pst.setBoolean(++i, presentataOpposizione);
	pst.setBoolean(++i, sentenzaFavorevole);
	pst.setInt(++i, importoStabilito);
	pst.setBoolean(++i, ingiunzioneOblataSentenza);
	pst.setBoolean(++i, iscrizioneRuoliSattoriali);
	pst.setString(++i, note1);
	pst.setString(++i, note2);
	pst.setInt(++i, importoEffettivamenteVersato1);
	pst.setInt(++i, importoEffettivamenteVersato2);
	pst.setInt(++i, importoEffettivamenteVersato3);
	pst.setInt(++i, importoEffettivamenteVersato4);
	pst.setBoolean(++i, competenzaRegionale);
	
	ResultSet rs = pst.executeQuery();
	
	if (rs.next())
		this.id = rs.getInt("id_inserito");
	
	pst.close();

	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return false;
	}
	return true;

}

public Vector cercaControllidaRicerca(Connection db){
	ResultSet rs = null;
	Vector trasgrList = new Vector();
	PreparedStatement pst;
	try {
		
		String query = "select * from registro_trasgressori_vista where anno_controllo = ? ";
		//query = query.substring(0, query.indexOf("order by"));
		
		if (trasgressore!=null && !trasgressore.equals(""))
			query+=" and trasgressore ilike ? ";
		if (obbligatoInSolido!=null && !obbligatoInSolido.equals(""))
			query+=" and obbligato ilike ? ";
		if (PV!=null && !PV.equals(""))
			query+=" and numeroverbale ilike ? ";
		if (ente1!=null && !ente1.equals(""))
			query+=" and (ente1 ilike ? or ente2 ilike ? or ente3 ilike ?) ";
		query+=" order by idcontrollo ASC ";
		
		pst = db.prepareStatement(query);
		int i = 0;
		pst.setInt(++i, anno);
		if (trasgressore!=null && !trasgressore.equals(""))
			pst.setString(++i, "%"+trasgressore+"%");
		if (obbligatoInSolido!=null && !obbligatoInSolido.equals(""))
			pst.setString(++i, "%"+obbligatoInSolido+"%");
		if (PV!=null && !PV.equals(""))
			pst.setString(++i, "%"+PV+"%");
		if (ente1!=null && !ente1.equals("")){
			pst.setString(++i, "%"+ente1+"%");
			pst.setString(++i, "%"+ente1+"%");
			pst.setString(++i, "%"+ente1+"%");
		}
		
		rs = DatabaseUtils.executeQuery(db, pst); 
		 while (rs.next()){
				 Trasgressione tras = new Trasgressione(rs, db);
				 trasgrList.add(tras);
			 }
	rs.close();
	pst.close();
	
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return trasgrList;
}


public boolean isRichiestaRateizzazione() {
	return richiestaRateizzazione;
}
public void setRichiestaRateizzazione(boolean richiestaRateizzazione) {
	this.richiestaRateizzazione = richiestaRateizzazione;
}
public void setRichiestaRateizzazione(String richiestaRateizzazione) {
	if (richiestaRateizzazione!=null && (richiestaRateizzazione.equals("true") || richiestaRateizzazione.equals("SI")))
		this.richiestaRateizzazione = true;
	else
		this.richiestaRateizzazione = false;
}

public boolean isRata1() {
	return rata1;
}
public void setRata1(boolean rata1) {
	this.rata1 = rata1;
}
public void setRata1(String rata1) {
	if (rata1!=null && (rata1.equals("true") || rata1.equals("on")))
		this.rata1 = true;
	else
		this.rata1 = false;
}

public boolean isRata2() {
	return rata2;
}
public void setRata2(boolean rata2) {
	this.rata2 = rata2;
}

public void setRata2(String rata2) {
	if (rata2!=null && (rata2.equals("true") || rata2.equals("on")))
		this.rata2 = true;
	else
		this.rata2 = false;
}

public boolean isRata3() {
	return rata3;
}
public void setRata3(boolean rata3) {
	this.rata3 = rata3;
}
public void setRata3(String rata3) {
	if (rata3!=null && (rata3.equals("true") || rata3.equals("on")))
		this.rata3 = true;
	else
		this.rata3 = false;
}

public boolean isRata4() {
	return rata4;
}
public void setRata4(boolean rata4) {
	this.rata4 = rata4;
}
public void setRata4(String rata4) {
	if (rata4!=null && (rata4.equals("true") || rata4.equals("on")))
		this.rata4 = true;
	else
		this.rata4 = false;
}
public boolean isRata5() {
	return rata5;
}
public void setRata5(boolean rata5) {
	this.rata5 = rata5;
}
public void setRata5(String rata5) {
	if (rata5!=null && (rata5.equals("true") || rata5.equals("on")))
		this.rata5 = true;
	else
		this.rata5 = false;
}
public boolean isRata6() {
	return rata6;
}
public void setRata6(boolean rata6) {
	this.rata6 = rata6;
}
public void setRata6(String rata6) {
	if (rata6!=null && (rata6.equals("true") || rata6.equals("on")))
		this.rata6 = true;
	else
		this.rata6 = false;
}
public boolean isRata7() {
	return rata7;
}
public void setRata7(boolean rata7) {
	this.rata7 = rata7;
}
public void setRata7(String rata7) {
	if (rata7!=null && (rata7.equals("true") || rata7.equals("on")))
		this.rata7 = true;
	else
		this.rata7 = false;
}
public boolean isRata8() {
	return rata8;
}
public void setRata8(boolean rata8) {
	this.rata8 = rata8;
}
public void setRata8(String rata8) {
	if (rata8!=null && (rata8.equals("true") || rata8.equals("on")))
		this.rata8 = true;
	else
		this.rata8 = false;
}
public boolean isRata9() {
	return rata9;
}
public void setRata9(String rata9) {
	if (rata9!=null && (rata9.equals("true") || rata9.equals("on")))
		this.rata9 = true;
	else
		this.rata9 = false;
}
public void setRata9(boolean rata9) {
	this.rata9 = rata9;
}
public boolean isRata10() {
	return rata10;
}
public void setRata10(boolean rata10) {
	this.rata10 = rata10;
}
public void setRata10(String rata10) {
	if (rata10!=null && (rata10.equals("true") || rata10.equals("on")))
		this.rata10 = true;
	else 
		this.rata10 = false;
}

public boolean[] getRate() {
	boolean[] rate = {rata1, rata2, rata3, rata4, rata5, rata6, rata7, rata8, rata9, rata10};
	return rate;
}

public boolean isEsisteInRegistro() {
	return esisteInRegistro;
}
public void setEsisteInRegistro(boolean esisteInRegistro) {
	this.esisteInRegistro = esisteInRegistro;
}
public String getAsl() {
	return asl;
}
public void setAsl(String asl) { 
	this.asl = asl;
}

public String[] getListaAllegatiRt() {
	
	String[] allegati = new String[0];
	
	if (this.allegatoRt != null && !this.allegatoRt.equals("##"))
		allegati = this.allegatoRt.split(";");
	
	return allegati;
}

public String[] getListaAllegatiPv() {
	
	String[] allegati = new String[0];
	
	if (this.allegatoPv != null && !this.allegatoPv.equals("##"))
		allegati = this.allegatoPv.split(";");
	
	return allegati;
}

public String[] getListaAllegatiAl() {
	
	String[] allegati = new String[0];
	
	if (this.allegatoAl != null && !this.allegatoAl.equals("##"))
		allegati = this.allegatoAl.split(";");
	
	return allegati;
}

public String[] getListaPagopaPvIuv() {
	String[] listaIuv = this.pagopaPvIuv.split("##");
	return listaIuv;
}

public String[] getListaPagopaNoIuv() {
	String[] listaIuv = this.pagopaNoIuv.split("##");
	return listaIuv;
}

public boolean isCompetenzaRegionale() {
	return competenzaRegionale;
}
public void setCompetenzaRegionale(boolean competenzaRegionale) {
	this.competenzaRegionale = competenzaRegionale;
}

public void setCompetenzaRegionale(String competenzaRegionale) {
	if (competenzaRegionale!=null && (competenzaRegionale.equals("true") || competenzaRegionale.equals("on")))
		this.competenzaRegionale = true;
	else
		this.competenzaRegionale = false;
}


public boolean isPraticaChiusa() {
	return praticaChiusa;
}
public void setPraticaChiusa(boolean praticaChiusa) {
	this.praticaChiusa = praticaChiusa;
}

public void setPraticaChiusa(String praticaChiusa) {
	if (praticaChiusa!=null && (praticaChiusa.equals("true") || praticaChiusa.equals("on")))
		this.praticaChiusa = true;
	else
		this.praticaChiusa = false;	
	}


public int getProgressivo() {
	return progressivo;
}
public void setProgressivo(int progressivo) {
	this.progressivo = progressivo;
}
public String getImportoSequestroRiduzione() {
	return importoSequestroRiduzione;
}
public void setImportoSequestroRiduzione(String importoSequestroRiduzione) {
	this.importoSequestroRiduzione = importoSequestroRiduzione;
}

private void setEntiAccertatori(Connection db){
	
	ArrayList<ComponenteNucleoIspettivo> nuclei = new ArrayList<ComponenteNucleoIspettivo>();
	nuclei = ComponenteNucleoIspettivo.buildList(db, idControllo);
	
	for (int i =0; i<nuclei.size() && i<3; i++){
		ComponenteNucleoIspettivo componente = (ComponenteNucleoIspettivo) nuclei.get(i);
		String ente ="";
		int idQualifica = componente.getIdQualifica();
		
		Qualifica q = new Qualifica (db, idQualifica);
		if (q.isInDpat())
			ente = "ASL "+asl;
		else
			ente = q.getNome();
		if (i==0)
			setEnte1(ente);
		else if (i==1)
			setEnte2(ente);
		else if (i==2)
			setEnte3(ente);
		}
}
public String getNumOrdinanza() { 
	return numOrdinanza;
}
public void setNumOrdinanza(String numOrdinanza) {
	this.numOrdinanza = numOrdinanza;
}

public boolean update(Connection db, int idUtente) {

	try {
		StringBuffer sqlStorico = new StringBuffer(); 
		sqlStorico.append("INSERT INTO registro_trasgressori_values_storico_record SELECT now(), ?, * from registro_trasgressori_values where id = ?");
		PreparedStatement pstStorico = db.prepareStatement(sqlStorico.toString());
		pstStorico.setInt(1, idUtente);
		pstStorico.setInt(2, id);
		pstStorico.execute();
	
		StringBuffer sql = new StringBuffer(); 
		boolean doCommit = false;

		sql.append("UPDATE registro_trasgressori_values set ");
		sql.append(" data_prot_entrata = ?, pv_oblato_ridotta = ?, fi_assegnatario = ?, presentati_scritti = ?, richiesta_rateizzazione = ?, rata1 = ?, rata2 = ?, rata3 = ?, rata4 = ?, rata5 = ?, rata6 = ?, rata7 = ?, rata8 = ?, rata9 = ?, rata10 = ?, ");
		sql.append(" presentata_richiesta_riduzione = ?, presentata_richiesta_audizione = ?, ordinanza_emessa = ?, importo_sanzione_ingiunta = ?, num_ordinanza = ?, data_emissione = ?, giorni_lavorazione = ?, data_ultima_notifica_ordinanza = ?, ");
		sql.append(" ordinanza_ingiunzione_oblata = ?, presentata_opposizione = ?, sentenza_favorevole = ?, importo_stabilito = ?, ordinanza_ingiunzione_sentenza = ?, iscrizione_ruoli_sattoriali = ?, ");
		sql.append("note1 = ?, note2 = ?, importo_effettivamente_versato1 = ?, importo_effettivamente_versato2 = ?, importo_effettivamente_versato3 = ?, importo_effettivamente_versato4 = ?, data_ultima_notifica = ?, ");
		sql.append("competenza_regionale = ?, data_pagamento = ?, data_pagamento_ordinanza = ?, pratica_chiusa = ?, data_modifica = now(), id_utente_modifica = ? where id = ? ");
		int i = 0;
		PreparedStatement pst;
		pst = db.prepareStatement(sql.toString());
		
		pst.setTimestamp(++i, dataProt);
		pst.setBoolean(++i, pvOblato);
		pst.setString(++i, fiAssegnatario);
		pst.setBoolean(++i, presentatiScritti);
		pst.setBoolean(++i, richiestaRateizzazione);
		pst.setBoolean(++i, rata1);
		pst.setBoolean(++i, rata2);
		pst.setBoolean(++i, rata3);
		pst.setBoolean(++i, rata4);
		pst.setBoolean(++i, rata5);
		pst.setBoolean(++i, rata6);
		pst.setBoolean(++i, rata7);
		pst.setBoolean(++i, rata8);
		pst.setBoolean(++i, rata9);
		pst.setBoolean(++i, rata10);
		pst.setBoolean(++i, richiestaRiduzioneSanzione);
		pst.setBoolean(++i, richiestaAudizione);
		pst.setString(++i, ordinanzaEmessa);
		pst.setInt(++i, importoSanzioneIngiunta);
		pst.setString(++i, numOrdinanza);
		pst.setTimestamp(++i, dataEmissione);
		pst.setInt(++i, giorniLavorazione);
		pst.setTimestamp(++i, dataUltimaNotificaOrdinanza);
		pst.setBoolean(++i, ingiunzioneOblata);
		pst.setBoolean(++i, presentataOpposizione);
		pst.setBoolean(++i, sentenzaFavorevole);
		pst.setInt(++i, importoStabilito);
		pst.setBoolean(++i, ingiunzioneOblataSentenza);
		pst.setBoolean(++i, iscrizioneRuoliSattoriali);
		pst.setString(++i, note1);
		pst.setString(++i, note2);
		pst.setInt(++i, importoEffettivamenteVersato1);
		pst.setInt(++i, importoEffettivamenteVersato2);
		pst.setInt(++i, importoEffettivamenteVersato3);
		pst.setInt(++i, importoEffettivamenteVersato4);
		pst.setTimestamp(++i, dataUltimaNotifica);
		pst.setBoolean(++i, competenzaRegionale);
		pst.setTimestamp(++i, dataPagamento);
		pst.setTimestamp(++i, dataPagamentoOrdinanza);
		pst.setBoolean(++i, praticaChiusa);
		pst.setInt(++i, idUtente);
		pst.setInt(++i, id);
		
		pst.execute();
		pst.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;

		
}

public int getIdUtenteModifica() { 
	return idUtenteModifica;
}

public void setIdUtenteModifica(int idUtenteModifica) {
	this.idUtenteModifica = idUtenteModifica;
}

public Timestamp getDataModifica() {
	return dataModifica;
}

public void setDataModifica(Timestamp dataModifica) {
	this.dataModifica = dataModifica;
}

public String getAllegatoPv() {
	return allegatoPv;
}

public void setAllegatoPv(String allegatoPv) {
	this.allegatoPv = allegatoPv;
}

public String getAllegatoAl() {
	return allegatoAl;
}

public void setAllegatoAl(String allegatoAl) {
	this.allegatoAl = allegatoAl;
}

public String getOrdinanzaEmessa() {
	return ordinanzaEmessa;
}

public void setOrdinanzaEmessa(String ordinanzaEmessa) {
	this.ordinanzaEmessa = ordinanzaEmessa;
}

public boolean isPagopaPvOblatoRidotta() {
	return pagopaPvOblatoRidotta;
}

public void setPagopaPvOblatoRidotta(boolean pagopaPvOblatoRidotta) {
	this.pagopaPvOblatoRidotta = pagopaPvOblatoRidotta;
}

public boolean isPagopaPvOblatoUltraRidotta() {
	return pagopaPvOblatoUltraRidotta;
}

public void setPagopaPvOblatoUltraRidotta(boolean pagopaPvOblatoUltraRidotta) {
	this.pagopaPvOblatoUltraRidotta = pagopaPvOblatoUltraRidotta;
}

public Timestamp getPagopaPvDataPagamento() {
	return pagopaPvDataPagamento;
}

public void setPagopaPvDataPagamento(Timestamp pagopaPvDataPagamento) {
	this.pagopaPvDataPagamento = pagopaPvDataPagamento;
}

public String getPagopaNoRatePagate() {
	return pagopaNoRatePagate;
}

public void setPagopaNoRatePagate(String pagopaNoRatePagate) {
	this.pagopaNoRatePagate = pagopaNoRatePagate;
}

public String getPagopaPvIuv() {
	return pagopaPvIuv;
}

public void setPagopaPvIuv(String pagopaPvIuv) {
	this.pagopaPvIuv = pagopaPvIuv;
}

public String getPagopaNoIuv() {
	return pagopaNoIuv;
}

public void setPagopaNoIuv(String pagopaNoIuv) {
	this.pagopaNoIuv = pagopaNoIuv;
}

public String getAllegatoRt() {
	return allegatoRt;
}

public void setAllegatoRt(String allegatoRt) {
	this.allegatoRt = allegatoRt;
}

public void aggiornaAllegati(Connection db, ActionContext context, int idUtente) throws SQLException {
	
	int size = Integer.parseInt(context.getRequest().getParameter("allegato_rt_size"));
	int i = 0;
	
	for (i = 0; i<size; i++){
		String header = context.getRequest().getParameter("allegato_rt_"+i);
		String oggetto = context.getRequest().getParameter("allegato_rt_oggetto_"+i);
		String nomeClient = context.getRequest().getParameter("allegato_rt_nomeclient_"+i);
		
		PreparedStatement pst = db.prepareStatement("select id from sanzioni_allegati where tipo_allegato = 'RegistroTrasgressori' and id_sanzione = ? and header_allegato = ? and trashed_date is null");
		pst.setInt(1, idSanzione);
		pst.setString(2, header);
		ResultSet rs = pst.executeQuery();
		if (!rs.next()){
			PreparedStatement pst2 = db.prepareStatement("insert into sanzioni_allegati (id_sanzione, id_utente, tipo_allegato, header_allegato, oggetto, nome_client) values (?, ?, ?, ?, ?, ?);");
			pst2.setInt(1, idSanzione);
			pst2.setInt(2, idUtente);
			pst2.setString(3,  "RegistroTrasgressori");
			pst2.setString(4, header);
			pst2.setString(5, oggetto);
			pst2.setString(6, nomeClient);
			pst2.executeUpdate();
		}
		
	}
	
}






		
}


 


	

