/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_benessere.base.v5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class ChecklistIstanza_Galline extends GenericBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 /**
	   *  Constructor for the Organization object, creates an empty Organization
	   *
	   * @since    1.0
	   */
	  public ChecklistIstanza_Galline() { }

	private int id;
	private int idChkBnsModIst;
	private int numAllegato;
	private int codAllegato;
	private int idCu;
	private int idSpecie;
	private int idVersione;
	private int orgId;
	private int enteredBy;
	private int modifiedBy;
	
	private java.sql.Timestamp entered = null;
	private java.sql.Timestamp modified = null;
	private java.sql.Timestamp trashedDate = null;
	private java.sql.Timestamp dataChk = null;
	
	boolean bozza = true;
		
	private ArrayList<Risposta> risposte = new ArrayList<Risposta>();
	private String numCheckList;
	private String veterinarioIspettore;
	private String presenzaManuale;
	private String extrapiano;
	private String numUovaAnno;
	private String batteria;
	private String batteriaUnicoPiano;
	private String batteriaPianiSfasati;
	private String batteriaPianiSovrapposti;
	private String numPianiGabbie;
	private String numGallineGabbia;
	private String ibridoRazza;
	private String selezioneImballaggio;
	private String selezioneImballaggioDestinazione;
	private String muta;
	private String appartenenteCondizionalita;
	private String criteriUtilizzati;
	private String criteriUtilizzatiAltroDescrizione;
	private String esitoControllo;
	private String intenzionalita;
	private String evidenze;
	private String evidenzeTse;
	private String evidenzeIr;
	private String evidenzeSv;
	private String assegnatePrescrizioni;
	private String prescrizioniDescrizione;
	private String dataPrescrizioni;
	private String sanzBlocco;
	private String sanzAmministrativa;
	private String sanzAbbattimento;
	private String sanzSequestro;
	private String sanzAltro;
	private String sanzInformativa;
	private String sanzAltroDesc;
	private String noteControllore;
	private String noteProprietario;
	private String nomeProprietario;
	private String dataPrimoControllo;
	private String nomeControllore;
	private String eseguitePrescrizioni;
	private String prescrizioniEseguiteDescrizione;
	private String nomeProprietarioPrescrizioniEseguite;
	private String dataVerifica;
	private String nomeControllorePrescrizioniEseguite;
	private String dataChiusuraRelazione;
	
	public void recuperaDaForm(ActionContext context, Connection db) throws SQLException {
			 
		numCheckList = context.getRequest().getParameter("numChecklist"); 
		veterinarioIspettore = context.getRequest().getParameter("veterinarioIspettore");
		presenzaManuale = context.getRequest().getParameter("presenzaManuale");
		extrapiano = context.getRequest().getParameter("extrapiano");
		
		numUovaAnno = context.getRequest().getParameter("numUovaAnno");
		batteria = context.getRequest().getParameter("batteria");
		batteriaUnicoPiano = context.getRequest().getParameter("batteriaUnicoPiano");
		batteriaPianiSfasati = context.getRequest().getParameter("batteriaPianiSfasati");
		batteriaPianiSovrapposti = context.getRequest().getParameter("batteriaPianiSovrapposti");
		numPianiGabbie = context.getRequest().getParameter("numPianiGabbie");
		numGallineGabbia = context.getRequest().getParameter("numGallineGabbia");
		ibridoRazza = context.getRequest().getParameter("ibridoRazza");
		selezioneImballaggio = context.getRequest().getParameter("selezioneImballaggio");
		selezioneImballaggioDestinazione = context.getRequest().getParameter("selezioneImballaggioDestinazione");
		muta = context.getRequest().getParameter("muta");
		
		appartenenteCondizionalita = context.getRequest().getParameter("appartenenteCondizionalita");
		criteriUtilizzati = context.getRequest().getParameter("criteriUtilizzati");
		criteriUtilizzatiAltroDescrizione = context.getRequest().getParameter("criteriUtilizzatiAltroDescrizione");
	
		esitoControllo = context.getRequest().getParameter("esitoControllo");
		intenzionalita = context.getRequest().getParameter("intenzionalita");
		evidenze = context.getRequest().getParameter("evidenze");
		evidenzeIr = context.getRequest().getParameter("evidenzeIr");
		evidenzeTse = context.getRequest().getParameter("evidenzeTse");
		evidenzeSv = context.getRequest().getParameter("evidenzeSv");
		
		assegnatePrescrizioni = context.getRequest().getParameter("assegnatePrescrizioni");
		prescrizioniDescrizione = context.getRequest().getParameter("prescrizioniDescrizione");
		dataPrescrizioni = context.getRequest().getParameter("dataPrescrizioni");
		
		sanzBlocco = context.getRequest().getParameter("sanzBlocco");
		sanzAmministrativa = context.getRequest().getParameter("sanzAmministrativa");
		sanzAbbattimento = context.getRequest().getParameter("sanzAbbattimento");
		sanzSequestro = context.getRequest().getParameter("sanzSequestro");
		sanzInformativa = context.getRequest().getParameter("sanzInformativa");
		sanzAltro = context.getRequest().getParameter("sanzAltro");
		sanzAltroDesc = context.getRequest().getParameter("sanzAltroDesc");

		noteControllore = context.getRequest().getParameter("noteControllore");
		noteProprietario = context.getRequest().getParameter("noteProprietario");
		dataPrimoControllo = context.getRequest().getParameter("dataPrimoControllo");
		nomeProprietario = context.getRequest().getParameter("nomeProprietario");
		nomeControllore = context.getRequest().getParameter("nomeControllore");
		
		eseguitePrescrizioni = context.getRequest().getParameter("eseguitePrescrizioni");
		prescrizioniEseguiteDescrizione = context.getRequest().getParameter("prescrizioniEseguiteDescrizione");
		dataVerifica = context.getRequest().getParameter("dataVerifica");
		nomeProprietarioPrescrizioniEseguite = context.getRequest().getParameter("nomeProprietarioPrescrizioniEseguite");
		nomeControllorePrescrizioniEseguite = context.getRequest().getParameter("nomeControllorePrescrizioniEseguite");
		dataChiusuraRelazione = context.getRequest().getParameter("dataChiusuraRelazione");
		
  	    PreparedStatement pst = db.prepareStatement("select * from chk_bns_get_domande_v5(?, ?, ?, ?)");
		pst.setInt(1, idSpecie);
		pst.setInt(2, numAllegato);
		pst.setInt(3, idVersione);
		pst.setInt(4, idCu);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			   Domanda domanda = new Domanda(db, rs, -1); 
			   Risposta risposta = new Risposta();
			   risposta.setIdDomanda(domanda.getId());
			   risposta.setRisposta(context.getRequest().getParameter("dom_"+domanda.getId()+"_risposta"));
			   risposta.setEvidenze(context.getRequest().getParameter("dom_"+domanda.getId()+"_evidenze"));
			   risposte.add(risposta);
		   }
		
	}




	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public int getIdChkBnsModIst() {
		return idChkBnsModIst;
	}




	public void setIdChkBnsModIst(int idChkBnsModIst) {
		this.idChkBnsModIst = idChkBnsModIst;
	}




	public int getNumAllegato() {
		return numAllegato;
	}




	public void setNumAllegato(int numAllegato) {
		this.numAllegato = numAllegato;
	}




	public int getIdCu() {
		return idCu;
	}




	public void setIdCu(int idCu) {
		this.idCu = idCu;
	}




	public int getIdSpecie() {
		return idSpecie;
	}




	public void setIdSpecie(int idSpecie) {
		this.idSpecie = idSpecie;
	}




	public int getIdVersione() {
		return idVersione;
	}




	public void setIdVersione(int idVersione) {
		this.idVersione = idVersione;
	}




	public int getOrgId() {
		return orgId;
	}




	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}




	public int getEnteredBy() {
		return enteredBy;
	}




	public void setEnteredBy(int enteredBy) {
		this.enteredBy = enteredBy;
	}




	public int getModifiedBy() {
		return modifiedBy;
	}




	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}




	public java.sql.Timestamp getEntered() {
		return entered;
	}




	public void setEntered(java.sql.Timestamp entered) {
		this.entered = entered;
	}




	public java.sql.Timestamp getModified() {
		return modified;
	}




	public void setModified(java.sql.Timestamp modified) {
		this.modified = modified;
	}




	public java.sql.Timestamp getTrashedDate() {
		return trashedDate;
	}




	public void setTrashedDate(java.sql.Timestamp trashedDate) {
		this.trashedDate = trashedDate;
	}




	public java.sql.Timestamp getDataChk() {
		return dataChk;
	}




	public void setDataChk(java.sql.Timestamp dataChk) {
		this.dataChk = dataChk;
	}




	public boolean isBozza() {
		return bozza;
	}




	public void setBozza(boolean bozza) {
		this.bozza = bozza;
	}




	public ArrayList<Risposta> getRisposte() {
		return risposte;
	}




	public void setRisposte(ArrayList<Risposta> risposte) {
		this.risposte = risposte;
	}

	public String getNumCheckList() {
		return numCheckList;
	}




	public void setNumCheckList(String numCheckList) {
		this.numCheckList = numCheckList;
	}




	public String getVeterinarioIspettore() {
		return veterinarioIspettore;
	}




	public void setVeterinarioIspettore(String veterinarioIspettore) {
		this.veterinarioIspettore = veterinarioIspettore;
	}

	public String getExtrapiano() {
		return extrapiano;
	}




	public void setExtrapiano(String extrapiano) {
		this.extrapiano = extrapiano;
	}


	


	public String getPresenzaManuale() {
		return presenzaManuale;
	}




	public void setPresenzaManuale(String presenzaManuale) {
		this.presenzaManuale = presenzaManuale;
	}




	public String getCriteriUtilizzati() {
		return criteriUtilizzati;
	}




	public void setCriteriUtilizzati(String criteriUtilizzati) {
		this.criteriUtilizzati = criteriUtilizzati;
	}




	public String getCriteriUtilizzatiAltroDescrizione() {
		return criteriUtilizzatiAltroDescrizione;
	}




	public void setCriteriUtilizzatiAltroDescrizione(String criteriUtilizzatiAltroDescrizione) {
		this.criteriUtilizzatiAltroDescrizione = criteriUtilizzatiAltroDescrizione;
	}




	public String getAppartenenteCondizionalita() {
		return appartenenteCondizionalita;
	}




	public void setAppartenenteCondizionalita(String appartenenteCondizionalita) {
		this.appartenenteCondizionalita = appartenenteCondizionalita;
	}




	public String getEsitoControllo() {
		return esitoControllo;
	}




	public void setEsitoControllo(String esitoControllo) {
		this.esitoControllo = esitoControllo;
	}




	public String getEvidenze() {
		return evidenze;
	}




	public void setEvidenze(String evidenze) {
		this.evidenze = evidenze;
	}




	public String getEvidenzeTse() {
		return evidenzeTse;
	}




	public void setEvidenzeTse(String evidenzeTse) {
		this.evidenzeTse = evidenzeTse;
	}




	public String getEvidenzeIr() {
		return evidenzeIr;
	}




	public void setEvidenzeIr(String evidenzeIr) {
		this.evidenzeIr = evidenzeIr;
	}




	public String getEvidenzeSv() {
		return evidenzeSv;
	}




	public void setEvidenzeSv(String evidenzeSv) {
		this.evidenzeSv = evidenzeSv;
	}




	public String getPrescrizioniDescrizione() {
		return prescrizioniDescrizione;
	}




	public void setPrescrizioniDescrizione(String prescrizioniDescrizione) {
		this.prescrizioniDescrizione = prescrizioniDescrizione;
	}




	public String getAssegnatePrescrizioni() {
		return assegnatePrescrizioni;
	}




	public void setAssegnatePrescrizioni(String assegnatePrescrizioni) {
		this.assegnatePrescrizioni = assegnatePrescrizioni;
	}




	public String getDataPrescrizioni() {
		return dataPrescrizioni;
	}




	public void setDataPrescrizioni(String dataPrescrizioni) {
		this.dataPrescrizioni = dataPrescrizioni;
	}




	public String getSanzBlocco() {
		return sanzBlocco;
	}




	public void setSanzBlocco(String sanzBlocco) {
		this.sanzBlocco = sanzBlocco;
	}




	public String getSanzAmministrativa() {
		return sanzAmministrativa;
	}




	public void setSanzAmministrativa(String sanzAmministrativa) {
		this.sanzAmministrativa = sanzAmministrativa;
	}




	public String getSanzAbbattimento() {
		return sanzAbbattimento;
	}




	public void setSanzAbbattimento(String sanzAbbattimento) {
		this.sanzAbbattimento = sanzAbbattimento;
	}




	public String getSanzSequestro() {
		return sanzSequestro;
	}




	public void setSanzSequestro(String sanzSequestro) {
		this.sanzSequestro = sanzSequestro;
	}




	public String getSanzAltro() {
		return sanzAltro;
	}




	public void setSanzAltro(String sanzAltro) {
		this.sanzAltro = sanzAltro;
	}




	public String getSanzInformativa() {
		return sanzInformativa;
	}




	public void setSanzInformativa(String sanzInformativa) {
		this.sanzInformativa = sanzInformativa;
	}




	public String getSanzAltroDesc() {
		return sanzAltroDesc;
	}




	public void setSanzAltroDesc(String sanzAltroDesc) {
		this.sanzAltroDesc = sanzAltroDesc;
	}




	public String getNoteControllore() {
		return noteControllore;
	}




	public void setNoteControllore(String noteControllore) {
		this.noteControllore = noteControllore;
	}




	public String getNoteProprietario() {
		return noteProprietario;
	}




	public void setNoteProprietario(String noteProprietario) {
		this.noteProprietario = noteProprietario;
	}




	public String getNomeProprietario() {
		return nomeProprietario;
	}




	public void setNomeProprietario(String nomeProprietario) {
		this.nomeProprietario = nomeProprietario;
	}




	public String getDataPrimoControllo() {
		return dataPrimoControllo;
	}




	public void setDataPrimoControllo(String dataPrimoControllo) {
		this.dataPrimoControllo = dataPrimoControllo;
	}




	public String getNomeControllore() {
		return nomeControllore;
	}




	public void setNomeControllore(String nomeControllore) {
		this.nomeControllore = nomeControllore;
	}




	public String getEseguitePrescrizioni() {
		return eseguitePrescrizioni;
	}




	public void setEseguitePrescrizioni(String eseguitePrescrizioni) {
		this.eseguitePrescrizioni = eseguitePrescrizioni;
	}




	public String getPrescrizioniEseguiteDescrizione() {
		return prescrizioniEseguiteDescrizione;
	}




	public void setPrescrizioniEseguiteDescrizione(String prescrizioniEseguiteDescrizione) {
		this.prescrizioniEseguiteDescrizione = prescrizioniEseguiteDescrizione;
	}




	public String getNomeProprietarioPrescrizioniEseguite() {
		return nomeProprietarioPrescrizioniEseguite;
	}




	public void setNomeProprietarioPrescrizioniEseguite(String nomeProprietarioPrescrizioniEseguite) {
		this.nomeProprietarioPrescrizioniEseguite = nomeProprietarioPrescrizioniEseguite;
	}




	public String getDataVerifica() {
		return dataVerifica;
	}




	public void setDataVerifica(String dataVerifica) {
		this.dataVerifica = dataVerifica;
	}




	public String getNomeControllorePrescrizioniEseguite() {
		return nomeControllorePrescrizioniEseguite;
	}




	public void setNomeControllorePrescrizioniEseguite(String nomeControllorePrescrizioniEseguite) {
		this.nomeControllorePrescrizioniEseguite = nomeControllorePrescrizioniEseguite;
	}




	public String getDataChiusuraRelazione() {
		return dataChiusuraRelazione;
	}




	public void setDataChiusuraRelazione(String dataChiusuraRelazione) {
		this.dataChiusuraRelazione = dataChiusuraRelazione;
	}




	public int getCodAllegato() {
		return codAllegato;
	}




	public void setCodAllegato(int codAllegato) {
		this.codAllegato = codAllegato;
	}




	public void upsert(Connection db) throws SQLException {

	PreparedStatement pst = null;
	ResultSet rs = null;
	
	String esitoImportPrecedente = null;
	String descrizioneErrorePrecedente = null;
	Timestamp dataImportPrecedente = null;
	int idBdnPrecedente = -1;

	System.out.println("CONTROLLO SE ESISTE GIA' UN'ISTANZA");
	pst = db.prepareStatement("select id,esito_import, descrizione_errore, id_bdn, data_import from chk_bns_mod_ist where id_alleg = ? and idcu = ? and trashed_date is null");
	pst.setInt(1, codAllegato);
	pst.setInt(2, idCu);
	rs = pst.executeQuery();
	if (rs.next()){
		this.idChkBnsModIst = rs.getInt("id");
		esitoImportPrecedente =  rs.getString("esito_import");
		descrizioneErrorePrecedente =  rs.getString("descrizione_errore");
		dataImportPrecedente = rs.getTimestamp("data_import");
		idBdnPrecedente = rs.getInt("id_bdn");
	}
	System.out.println("ID ISTANZA: "+this.idChkBnsModIst);
	
	System.out.println("SE ESISTE, DISABILITO L'ISTANZA");

	if (this.idChkBnsModIst>0){
		pst = db.prepareStatement("update chk_bns_mod_ist set trashed_date = now() where id = ? and trashed_date is null");
		pst.setInt(1, this.idChkBnsModIst);
		pst.executeUpdate();
		
		pst = db.prepareStatement("update chk_bns_mod_ist_v5_galline set trashed_date = now() where id_chk_bns_mod_ist = ? and trashed_date is null");
		pst.setInt(1, this.idChkBnsModIst);
		pst.executeUpdate();
		
		pst = db.prepareStatement("update chk_bns_risposte_v5 set trashed_date = now() where id_chk_bns_mod_ist = ? and trashed_date is null");
		pst.setInt(1, this.idChkBnsModIst);
		pst.executeUpdate();
		
		pst = db.prepareStatement("update chk_bns_boxes_v5 set trashed_date = now() where id_chk_bns_mod_ist = ? and trashed_date is null");
		pst.setInt(1, this.idChkBnsModIst);
		pst.executeUpdate();
	}
	
	System.out.println("INSERISCO LA NUOVA ISTANZA");
	pst = db.prepareStatement("insert into chk_bns_mod_ist (id_alleg, idcu, orgid, enteredby, modifiedby, bozza, esito_import, descrizione_errore, id_bdn, data_import) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id as id_inserito");
	pst.setInt(1, codAllegato);
	pst.setInt(2, idCu);
	pst.setInt(3, orgId);
	pst.setInt(4, enteredBy);
	pst.setInt(5, enteredBy);
	pst.setBoolean(6, bozza);
	pst.setString(7, esitoImportPrecedente);
	pst.setString(8, descrizioneErrorePrecedente);
	pst.setInt(9, idBdnPrecedente);
	pst.setTimestamp(10, dataImportPrecedente);
	
	boolean chkBnsModIstInserita = false;
	int chkBnsModIstInseritaIndex = 0;
	do {
		try {
			System.out.println("PROVO INSERIMENTO IN chk_bns_mod_ist");
			rs = pst.executeQuery();
			chkBnsModIstInserita = true;
			}
		catch (Exception e) {
			System.out.println("ERRORE INSERIMENTO IN chk_bns_mod_ist");
			e.printStackTrace();
		}
		chkBnsModIstInseritaIndex++;
	}
	while (!chkBnsModIstInserita && chkBnsModIstInseritaIndex<10);	
	
	if (chkBnsModIstInserita){
	
	if (rs.next())
		this.idChkBnsModIst = rs.getInt("id_inserito");

	System.out.println("INSERISCO I NUOVI DATI CHECKLIST");
	
	pst = db.prepareStatement("INSERT INTO public.chk_bns_mod_ist_v5_galline("+
            "id_chk_bns_mod_ist, entered_by, num_checklist, "+
            "veterinario_ispettore, extrapiano, num_uova_anno, batteria, batteria_unico_piano, "+
            "batteria_piani_sfasati, batteria_piani_sovrapposti, num_piani_gabbie, num_galline_gabbia, ibrido_razza, "+
            "selezione_imballaggio, selezione_imballaggio_destinazione, muta, "+
            "presenza_manuale, criteri_utilizzati, criteri_utilizzati_altro_descrizione, "+
            "appartenente_condizionalita, esito_controllo, evidenze, evidenze_tse, "+
            "evidenze_ir, evidenze_sv, prescrizioni_descrizione, assegnate_prescrizioni, "+
            "data_prescrizioni, sanz_blocco, sanz_amministrativa, sanz_abbattimento, "+
            "sanz_sequestro, sanz_altro, sanz_informativa, sanz_altro_desc, "+
            "note_controllore, note_proprietario, nome_proprietario, data_primo_controllo, "+
            "nome_controllore, eseguite_prescrizioni, prescrizioni_eseguite_descrizione, "+
            "nome_proprietario_prescrizioni_eseguite, data_verifica, nome_controllore_prescrizioni_eseguite, "+
            "data_chiusura_relazione, intenzionalita)"+
            "VALUES (?, ?, ?, "+
            "?, ?, ?, ?, ?, "+
            "?, ?, ?, ?, ?, "+
            "?, ?, ?, "+
            "?, ?, ?, "+
            "?, ?, ?, ?, "+
            "?, ?, ?, ?, "+
            "?, ?, ?, ?, "+
            "?, ?, ?, ?, "+
            "?, ?, ?, ?, "+
            "?, ?, ?, "+
            "?, ?, ?, "+
            "?, ?);");

		int i = 0;
		pst.setInt(++i, this.idChkBnsModIst);
		pst.setInt(++i, this.enteredBy);
		pst.setString(++i, this.numCheckList);
		pst.setString(++i, this.veterinarioIspettore);
		pst.setString(++i, this.extrapiano);
		pst.setString(++i, this.numUovaAnno);
		pst.setString(++i, this.batteria);
		pst.setString(++i, this.batteriaUnicoPiano);
		pst.setString(++i, this.batteriaPianiSfasati);
		pst.setString(++i, this.batteriaPianiSovrapposti);
		pst.setString(++i, this.numPianiGabbie);
		pst.setString(++i, this.numGallineGabbia);
		pst.setString(++i, this.ibridoRazza);
		pst.setString(++i, this.selezioneImballaggio);
		pst.setString(++i, this.selezioneImballaggioDestinazione);
		pst.setString(++i, this.muta);
		pst.setString(++i, this.presenzaManuale);
		pst.setString(++i, this.criteriUtilizzati);
		pst.setString(++i, this.criteriUtilizzatiAltroDescrizione);
		pst.setString(++i, this.appartenenteCondizionalita);
		pst.setString(++i, this.esitoControllo);
		pst.setString(++i, this.evidenze);
		pst.setString(++i, this.evidenzeTse);
		pst.setString(++i, this.evidenzeIr);
		pst.setString(++i, this.evidenzeSv);
		pst.setString(++i, this.prescrizioniDescrizione);
		pst.setString(++i, this.assegnatePrescrizioni);
		pst.setString(++i, this.dataPrescrizioni);
		pst.setString(++i, this.sanzBlocco);
		pst.setString(++i, this.sanzAmministrativa);
		pst.setString(++i, this.sanzAbbattimento);
		pst.setString(++i, this.sanzSequestro);
		pst.setString(++i, this.sanzAltro);
		pst.setString(++i, this.sanzInformativa);
		pst.setString(++i, this.sanzAltroDesc);
		pst.setString(++i, this.noteControllore);
		pst.setString(++i, this.noteProprietario);
		pst.setString(++i, this.nomeProprietario);
		pst.setString(++i, this.dataPrimoControllo);
		pst.setString(++i, this.nomeControllore);
		pst.setString(++i, this.eseguitePrescrizioni);
		pst.setString(++i, this.prescrizioniEseguiteDescrizione);
		pst.setString(++i, this.nomeProprietarioPrescrizioniEseguite);
		pst.setString(++i, this.dataVerifica);
		pst.setString(++i, this.nomeControllorePrescrizioniEseguite);
		pst.setString(++i, this.dataChiusuraRelazione);
		pst.setString(++i, this.intenzionalita);
		pst.executeUpdate();
	
	System.out.println("INSERISCO LE NUOVE RISPOSTE");
	
	for (int r = 0 ; r<risposte.size(); r++) {
		Risposta risposta = (Risposta) risposte.get(r);
		risposta.setEnteredBy(this.enteredBy);
		risposta.setIdChkBnsModIst(this.idChkBnsModIst);
		risposta.insert(db);
	}
	
	}	
		
	}
	
	
	public ChecklistIstanza_Galline(Connection db, int idControllo, int codAllegato) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select id, bozza from chk_bns_mod_ist where idcu = ? and id_alleg = ? and trashed_date is null");
		pst.setInt(1, idControllo);
		pst.setInt(2, codAllegato);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			this.idChkBnsModIst = rs.getInt("id");
			this.bozza = rs.getBoolean("bozza");
			queryRecordByIdChkBnsModIst(db);
		}
		
	}

	private void queryRecordByIdChkBnsModIst(Connection db) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from chk_bns_mod_ist_v5_galline where id_chk_bns_mod_ist = ? and trashed_date is null");
		pst.setInt(1, this.idChkBnsModIst);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			buildRecord(rs);
			setRisposte(db);
		}
				
	}


	private void setRisposte(Connection db) throws SQLException {
		this.risposte = Risposta.queryListByIdChkBnsModIst(db, this.idChkBnsModIst);
	}




	public String getIntenzionalita() {  
		return intenzionalita;
	}




	public void setIntenzionalita(String intenzionalita) {
		this.intenzionalita = intenzionalita;
	}

 


	private void buildRecord(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.idChkBnsModIst = rs.getInt("id_chk_bns_mod_ist");
		this.enteredBy = rs.getInt("entered_by");
		this.numCheckList = rs.getString("num_checklist");
		this.veterinarioIspettore = rs.getString("veterinario_ispettore");
		this.extrapiano = rs.getString("extrapiano");
		this.numUovaAnno = rs.getString("num_uova_anno");
		this.batteria = rs.getString("batteria");
		this.batteriaUnicoPiano = rs.getString("batteria_unico_piano");
		this.batteriaPianiSfasati = rs.getString("batteria_piani_sfasati");
		this.batteriaPianiSovrapposti = rs.getString("batteria_piani_sovrapposti");
		this.numPianiGabbie = rs.getString("num_piani_gabbie");
		this.numGallineGabbia = rs.getString("num_galline_gabbia");
		this.ibridoRazza = rs.getString("ibrido_razza");
		this.selezioneImballaggio = rs.getString("selezione_imballaggio");
		this.selezioneImballaggioDestinazione = rs.getString("selezione_imballaggio_destinazione");
		this.muta = rs.getString("muta");
		this.appartenenteCondizionalita = rs.getString("appartenente_condizionalita");
		this.presenzaManuale = rs.getString("presenza_manuale");
		this.criteriUtilizzati = rs.getString("criteri_utilizzati");
		this.criteriUtilizzatiAltroDescrizione = rs.getString("criteri_utilizzati_altro_descrizione");
		this.esitoControllo = rs.getString("esito_controllo");
		this.intenzionalita = rs.getString("intenzionalita");
		this.evidenze = rs.getString("evidenze");
		this.evidenzeTse = rs.getString("evidenze_tse");
		this.evidenzeIr = rs.getString("evidenze_ir");
		this.evidenzeSv = rs.getString("evidenze_sv");
		this.prescrizioniDescrizione = rs.getString("prescrizioni_descrizione");
		this.assegnatePrescrizioni = rs.getString("assegnate_prescrizioni");
		this.dataPrescrizioni = rs.getString("data_prescrizioni");
		this.sanzBlocco = rs.getString("sanz_blocco");
		this.sanzAmministrativa = rs.getString("sanz_amministrativa");
		this.sanzAbbattimento = rs.getString("sanz_abbattimento");
		this.sanzSequestro = rs.getString("sanz_sequestro");
		this.sanzInformativa = rs.getString("sanz_informativa");
		this.sanzAltro = rs.getString("sanz_altro");
		this.sanzAltroDesc = rs.getString("sanz_altro_desc");
		this.noteControllore = rs.getString("note_controllore");
		this.noteProprietario = rs.getString("note_proprietario");
		this.nomeProprietario = rs.getString("nome_proprietario");
		this.dataPrimoControllo = rs.getString("data_primo_controllo");
		this.nomeControllore = rs.getString("nome_controllore");
		this.eseguitePrescrizioni = rs.getString("eseguite_prescrizioni");
		this.prescrizioniEseguiteDescrizione = rs.getString("prescrizioni_eseguite_descrizione");
		this.nomeProprietarioPrescrizioniEseguite = rs.getString("nome_proprietario_prescrizioni_eseguite");
		this.dataVerifica = rs.getString("data_verifica");
		this.nomeControllorePrescrizioniEseguite = rs.getString("nome_controllore_prescrizioni_eseguite");
		this.dataChiusuraRelazione = rs.getString("data_chiusura_relazione");
		}




	public String getNumUovaAnno() {
		return numUovaAnno;
	}




	public void setNumUovaAnno(String numUovaAnno) {
		this.numUovaAnno = numUovaAnno;
	}




	public String getBatteria() {
		return batteria;
	}




	public void setBatteria(String batteria) {
		this.batteria = batteria;
	}




	public String getBatteriaUnicoPiano() {
		return batteriaUnicoPiano;
	}




	public void setBatteriaUnicoPiano(String batteriaUnicoPiano) {
		this.batteriaUnicoPiano = batteriaUnicoPiano;
	}




	public String getBatteriaPianiSfasati() {
		return batteriaPianiSfasati;
	}




	public void setBatteriaPianiSfasati(String batteriaPianiSfasati) {
		this.batteriaPianiSfasati = batteriaPianiSfasati;
	}




	public String getBatteriaPianiSovrapposti() {
		return batteriaPianiSovrapposti;
	}




	public void setBatteriaPianiSovrapposti(String batteriaPianiSovrapposti) {
		this.batteriaPianiSovrapposti = batteriaPianiSovrapposti;
	}




	public String getNumPianiGabbie() {
		return numPianiGabbie;
	}




	public void setNumPianiGabbie(String numPianiGabbie) {
		this.numPianiGabbie = numPianiGabbie;
	}




	public String getNumGallineGabbia() {
		return numGallineGabbia;
	}




	public void setNumGallineGabbia(String numGallineGabbia) {
		this.numGallineGabbia = numGallineGabbia;
	}




	public String getIbridoRazza() {
		return ibridoRazza;
	}




	public void setIbridoRazza(String ibridoRazza) {
		this.ibridoRazza = ibridoRazza;
	}




	public String getSelezioneImballaggio() {
		return selezioneImballaggio;
	}




	public void setSelezioneImballaggio(String selezioneImballaggio) {
		this.selezioneImballaggio = selezioneImballaggio;
	}




	public String getSelezioneImballaggioDestinazione() {
		return selezioneImballaggioDestinazione;
	}




	public void setSelezioneImballaggioDestinazione(String selezioneImballaggioDestinazione) {
		this.selezioneImballaggioDestinazione = selezioneImballaggioDestinazione;
	}




	public String getMuta() {
		return muta;
	}




	public void setMuta(String muta) {
		this.muta = muta;
	}
	
	
	
	
}