/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_benessere.base.v6;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class ChecklistIstanza_Vitelli extends GenericBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 /**
	   *  Constructor for the Organization object, creates an empty Organization
	   *
	   * @since    1.0
	   */
	  public ChecklistIstanza_Vitelli() { }

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
	private String extrapiano;
	private String numCapi;
	private String numCapiCircolanti;
	private String numCapiMorti;
	private String mortalita;
	private String numCapiPartoriti;
	private String numCapiPartoritiGemellari;
	private String interparto;
	private String flagImpiantiElettrici;
	private String flagDichiarazioneConformita;
	private String presenzaManuale;
	private String criteriUtilizzati;
	private String criteriUtilizzatiAltroDescrizione;
	private String appartenenteCondizionalita;
	private String esitoControllo;
	private String intenzionalita;
	private String evidenze;
	private String evidenzeTse;
	private String evidenzeIr;
	private String evidenzeSv;
	private String prescrizioniDescrizione;
	private String assegnatePrescrizioni;
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
		
//		 		PER IL FUTURO: RECUPERA TUTTI I CAMPI DALLA FORM E LI METTE IN UN OGGETTO JSON    
//		    Map<String, String[]> params = context.getRequest().getParameterMap();
//		    JSONObject jo = new JSONObject();
//		    for (Iterator<String> it=params.keySet().iterator(); it.hasNext();) {
//		        String k = it.next();
//		        String[] v = params.get(k);
//		        if (v==null || v.length==0) {
//		            continue;
//		        } else if (v.length==1) {
//		            jo.accumulate(k, v[0]);
//		        } else {
//		            jo.accumulate(k, v[0]);
//		        }
//		    }
		
		 
		numCheckList = context.getRequest().getParameter("numChecklist"); 
		veterinarioIspettore = context.getRequest().getParameter("veterinarioIspettore");
		extrapiano = context.getRequest().getParameter("extrapiano");
		
		numCapi = context.getRequest().getParameter("numCapi");
		numCapiCircolanti = context.getRequest().getParameter("numCapiCircolanti");
		numCapiMorti = context.getRequest().getParameter("numCapiMorti");
		mortalita = context.getRequest().getParameter("mortalita");
		numCapiPartoriti = context.getRequest().getParameter("numCapiPartoriti");
		numCapiPartoritiGemellari = context.getRequest().getParameter("numCapiPartoritiGemellari");
		interparto = context.getRequest().getParameter("interparto");
		flagImpiantiElettrici = context.getRequest().getParameter("flagImpiantiElettrici");
		flagDichiarazioneConformita = context.getRequest().getParameter("flagDichiarazioneConformita");
	
		presenzaManuale = context.getRequest().getParameter("presenzaManuale");
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
		
  	    PreparedStatement pst = db.prepareStatement("select * from chk_bns_get_domande_v6(?, ?, ?, ?)");
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




	public String getNumCapi() {
		return numCapi;
	}




	public void setNumCapi(String numCapi) {
		this.numCapi = numCapi;
	}



	public String getNumCapiMorti() {
		return numCapiMorti;
	}




	public void setNumCapiMorti(String numCapiMorti) {
		this.numCapiMorti = numCapiMorti;
	}




	public String getNumCapiCircolanti() {
		return numCapiCircolanti;
	}




	public void setNumCapiCircolanti(String numCapiCircolanti) {
		this.numCapiCircolanti = numCapiCircolanti;
	}




	public String getMortalita() {
		return mortalita;
	}




	public void setMortalita(String mortalita) {
		this.mortalita = mortalita;
	}




	public String getNumCapiPartoriti() {
		return numCapiPartoriti;
	}




	public void setNumCapiPartoriti(String numCapiPartoriti) {
		this.numCapiPartoriti = numCapiPartoriti;
	}




	public String getNumCapiPartoritiGemellari() {
		return numCapiPartoritiGemellari;
	}




	public void setNumCapiPartoritiGemellari(String numCapiPartoritiGemellari) {
		this.numCapiPartoritiGemellari = numCapiPartoritiGemellari;
	}




	public String getInterparto() {
		return interparto;
	}




	public void setInterparto(String interparto) {
		this.interparto = interparto;
	}




	public String getFlagImpiantiElettrici() {
		return flagImpiantiElettrici;
	}




	public void setFlagImpiantiElettrici(String flagImpiantiElettrici) {
		this.flagImpiantiElettrici = flagImpiantiElettrici;
	}




	public String getFlagDichiarazioneConformita() {
		return flagDichiarazioneConformita;
	}




	public void setFlagDichiarazioneConformita(String flagDichiarazioneConformita) {
		this.flagDichiarazioneConformita = flagDichiarazioneConformita;
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
		
		pst = db.prepareStatement("update chk_bns_mod_ist_v6_bovini_bufalini set trashed_date = now() where id_chk_bns_mod_ist = ? and trashed_date is null");
		pst.setInt(1, this.idChkBnsModIst);
		pst.executeUpdate();
		
		pst = db.prepareStatement("update chk_bns_risposte_v6 set trashed_date = now() where id_chk_bns_mod_ist = ? and trashed_date is null");
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
	
	pst = db.prepareStatement("INSERT INTO public.chk_bns_mod_ist_v6_vitelli("+
            "id_chk_bns_mod_ist, entered_by, num_checklist, "+
            "veterinario_ispettore, extrapiano, num_capi, num_capi_circolanti, num_capi_morti, mortalita, num_capi_partoriti, "+
            "num_capi_partoriti_gemellari, interparto, flag_impianti_elettrici, flag_dichiarazione_conformita, "+
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
            "?, ?, ?,  ?, ?, ?, ?, "+
            "?, ?, ?, ?, "+
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
		pst.setString(++i, this.numCapi);
		pst.setString(++i, this.numCapiCircolanti);
		pst.setString(++i, this.numCapiMorti);
		pst.setString(++i, this.mortalita);
		pst.setString(++i, this.numCapiPartoriti);
		pst.setString(++i, this.numCapiPartoritiGemellari);
		pst.setString(++i, this.interparto);
		pst.setString(++i, this.flagImpiantiElettrici);
		pst.setString(++i, this.flagDichiarazioneConformita);
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
	
	
	public ChecklistIstanza_Vitelli(Connection db, int idControllo, int codAllegato) throws SQLException {
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
		PreparedStatement pst = db.prepareStatement("select * from chk_bns_mod_ist_v6_vitelli where id_chk_bns_mod_ist = ? and trashed_date is null");
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
		this.numCapi  = rs.getString("num_capi");
		this.numCapiCircolanti  = rs.getString("num_capi_circolanti");
		this.numCapiPartoriti  = rs.getString("num_capi_partoriti");
		this.numCapiPartoritiGemellari  = rs.getString("num_capi_partoriti_gemellari");
		this.numCapiMorti  = rs.getString("num_capi_morti");
		this.mortalita  = rs.getString("mortalita");
		this.interparto  = rs.getString("interparto");
		this.flagImpiantiElettrici  = rs.getString("flag_impianti_elettrici");
		this.flagDichiarazioneConformita  = rs.getString("flag_dichiarazione_conformita");
		this.presenzaManuale = rs.getString("presenza_manuale");
		this.criteriUtilizzati = rs.getString("criteri_utilizzati");
		this.criteriUtilizzatiAltroDescrizione = rs.getString("criteri_utilizzati_altro_descrizione");
		this.appartenenteCondizionalita = rs.getString("appartenente_condizionalita");
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
	
	
	
	
}