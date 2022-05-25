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
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class ChecklistIstanza_Suini extends GenericBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 /**
	   *  Constructor for the Organization object, creates an empty Organization
	   *
	   * @since    1.0
	   */
	  public ChecklistIstanza_Suini() { }

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
	private ArrayList<Box> boxes = new ArrayList<Box>();
	private String numCheckList;
	private String veterinarioIspettore;
	private String extrapiano;
	private String censCapi;
	private String censDecessi;
	private String censScrofette;
	private String censNascite;
	private String censScrofe;
	private String censVerri;
	private String censCinghiali;
	private String censMagroncelli;
	private String censMagroni;
	private String censLattonzoli;
	private String censGrassi;
	private String scrofeMorteAnno;
	private String suinettiSvezzatiAnno;
	private String svezzamentoSuiniPresenti;
	private String svezzamentoTutto;
	private String svezzamentoSuiniMorti;
	private String svezzamentoNumAnimaliCiclo;
	private String svezzamentoNumCicli;
	private String svezzamentoFessurato;
	private String svezzamentoParzialmenteFessurato;
	private String svezzamentoPieno;
	private String svezzamentoParzialmenteGrigliato;
	private String svezzamentoLettiera;
	private String ingrassoSuiniPresenti;
	private String ingrassoTutto;
	private String ingrassoSuiniMorti;
	private String ingrassoNumAnimaliCiclo;
	private String ingrassoNumCicli;
	private String ingrassoFessurato;
	private String ingrassoPieno;
	private String ingrassoParzialmenteFessurato;
	private String ingrassoParzialmenteGrigliato;
	private String ingrassoLettiera;
	private String presenzaGruppiAnimaliCodaTagliata;
	private String presenzaAnimaliCodaTagliata;
	private String presenzaAnimaliTipiche;
	private String utilizzoAnestetici;
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
		
		censCapi = context.getRequest().getParameter("censCapi");
		censNascite = context.getRequest().getParameter("censNascite");
		censDecessi = context.getRequest().getParameter("censDecessi");
		censScrofe = context.getRequest().getParameter("censScrofe");
		censScrofette = context.getRequest().getParameter("censScrofette");
		censVerri = context.getRequest().getParameter("censVerri");
		censCinghiali = context.getRequest().getParameter("censCinghiali");
		censMagroncelli = context.getRequest().getParameter("censMagroncelli");
		censMagroni = context.getRequest().getParameter("censMagroni");
		censLattonzoli = context.getRequest().getParameter("censLattonzoli");
		censGrassi = context.getRequest().getParameter("censGrassi");
		
		scrofeMorteAnno = context.getRequest().getParameter("scrofeMorteAnno");
		suinettiSvezzatiAnno = context.getRequest().getParameter("suinettiSvezzatiAnno");
		
		svezzamentoSuiniPresenti = context.getRequest().getParameter("svezzamentoSuiniPresenti");
		svezzamentoTutto = context.getRequest().getParameter("svezzamentoTutto");
		svezzamentoNumAnimaliCiclo = context.getRequest().getParameter("svezzamentoNumAnimaliCiclo");
		svezzamentoSuiniMorti = context.getRequest().getParameter("svezzamentoSuiniMorti");
		svezzamentoNumCicli = context.getRequest().getParameter("svezzamentoNumCicli");
		svezzamentoFessurato = context.getRequest().getParameter("svezzamentoFessurato");
		svezzamentoPieno = context.getRequest().getParameter("svezzamentoPieno");
		svezzamentoParzialmenteFessurato = context.getRequest().getParameter("svezzamentoParzialmenteFessurato");
		svezzamentoParzialmenteGrigliato = context.getRequest().getParameter("svezzamentoParzialmenteGrigliato");
		svezzamentoLettiera = context.getRequest().getParameter("svezzamentoLettiera");
		
		ingrassoSuiniPresenti = context.getRequest().getParameter("ingrassoSuiniPresenti");
		ingrassoTutto = context.getRequest().getParameter("ingrassoTutto");
		ingrassoNumAnimaliCiclo = context.getRequest().getParameter("ingrassoNumAnimaliCiclo");
		ingrassoSuiniMorti = context.getRequest().getParameter("ingrassoSuiniMorti");
		ingrassoNumCicli = context.getRequest().getParameter("ingrassoNumCicli");
		ingrassoFessurato = context.getRequest().getParameter("ingrassoFessurato");
		ingrassoPieno = context.getRequest().getParameter("ingrassoPieno");
		ingrassoParzialmenteFessurato = context.getRequest().getParameter("ingrassoParzialmenteFessurato");
		ingrassoParzialmenteGrigliato = context.getRequest().getParameter("ingrassoParzialmenteGrigliato");
		ingrassoLettiera = context.getRequest().getParameter("ingrassoLettiera");
		
		presenzaAnimaliCodaTagliata = context.getRequest().getParameter("presenzaAnimaliCodaTagliata");
		presenzaGruppiAnimaliCodaTagliata = context.getRequest().getParameter("presenzaGruppiAnimaliCodaTagliata");
		presenzaAnimaliTipiche = context.getRequest().getParameter("presenzaAnimaliTipiche");
		utilizzoAnestetici = context.getRequest().getParameter("utilizzoAnestetici");
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
		
		for (int i = 0; i<5; i++){
				Box box = new Box();
				box.setIndice(i);
				box.setNumero(context.getRequest().getParameter("box_"+i+"_numero"));
				box.setLarghezza(context.getRequest().getParameter("box_"+i+"_larghezza"));
				box.setLunghezza(context.getRequest().getParameter("box_"+i+"_lunghezza"));
				box.setAnimali(context.getRequest().getParameter("box_"+i+"_animali"));
				box.setPeso(context.getRequest().getParameter("box_"+i+"_peso"));
				box.setCategoria(context.getRequest().getParameter("box_"+i+"_categoria"));
				box.setPavimento(context.getRequest().getParameter("box_"+i+"_pavimento"));
				box.setTravetti(context.getRequest().getParameter("box_"+i+"_travetti"));
				box.setFessure(context.getRequest().getParameter("box_"+i+"_fessure"));
				box.setRegolare(context.getRequest().getParameter("box_"+i+"_regolare"));
				boxes.add(box);
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




	public ArrayList<Box> getBoxes() {
		return boxes;
	}




	public void setBoxes(ArrayList<Box> boxes) {
		this.boxes = boxes;
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


	public String getCensCapi() {
		return censCapi;
	}




	public void setCensCapi(String censCapi) {
		this.censCapi = censCapi;
	}




	public String getCensDecessi() {
		return censDecessi;
	}




	public void setCensDecessi(String censDecessi) {
		this.censDecessi = censDecessi;
	}




	public String getCensScrofette() {
		return censScrofette;
	}




	public void setCensScrofette(String censScrofette) {
		this.censScrofette = censScrofette;
	}




	public String getCensNascite() {
		return censNascite;
	}




	public void setCensNascite(String censNascite) {
		this.censNascite = censNascite;
	}




	public String getCensScrofe() {
		return censScrofe;
	}




	public void setCensScrofe(String censScrofe) {
		this.censScrofe = censScrofe;
	}




	public String getCensVerri() {
		return censVerri;
	}




	public void setCensVerri(String censVerri) {
		this.censVerri = censVerri;
	}




	public String getCensCinghiali() {
		return censCinghiali;
	}




	public void setCensCinghiali(String censCinghiali) {
		this.censCinghiali = censCinghiali;
	}




	public String getCensMagroncelli() {
		return censMagroncelli;
	}




	public void setCensMagroncelli(String censMagroncelli) {
		this.censMagroncelli = censMagroncelli;
	}




	public String getCensMagroni() {
		return censMagroni;
	}




	public void setCensMagroni(String censMagroni) {
		this.censMagroni = censMagroni;
	}




	public String getCensLattonzoli() {
		return censLattonzoli;
	}




	public void setCensLattonzoli(String censLattonzoli) {
		this.censLattonzoli = censLattonzoli;
	}




	public String getCensGrassi() {
		return censGrassi;
	}




	public void setCensGrassi(String censGrassi) {
		this.censGrassi = censGrassi;
	}




	public String getScrofeMorteAnno() {
		return scrofeMorteAnno;
	}




	public void setScrofeMorteAnno(String scrofeMorteAnno) {
		this.scrofeMorteAnno = scrofeMorteAnno;
	}




	public String getSuinettiSvezzatiAnno() {
		return suinettiSvezzatiAnno;
	}




	public void setSuinettiSvezzatiAnno(String suinettiSvezzatiAnno) {
		this.suinettiSvezzatiAnno = suinettiSvezzatiAnno;
	}




	public String getSvezzamentoSuiniPresenti() {
		return svezzamentoSuiniPresenti;
	}




	public void setSvezzamentoSuiniPresenti(String svezzamentoSuiniPresenti) {
		this.svezzamentoSuiniPresenti = svezzamentoSuiniPresenti;
	}




	public String getSvezzamentoTutto() {
		return svezzamentoTutto;
	}




	public void setSvezzamentoTutto(String svezzamentoTutto) {
		this.svezzamentoTutto = svezzamentoTutto;
	}




	public String getSvezzamentoSuiniMorti() {
		return svezzamentoSuiniMorti;
	}




	public void setSvezzamentoSuiniMorti(String svezzamentoSuiniMorti) {
		this.svezzamentoSuiniMorti = svezzamentoSuiniMorti;
	}




	public String getSvezzamentoNumAnimaliCiclo() {
		return svezzamentoNumAnimaliCiclo;
	}




	public void setSvezzamentoNumAnimaliCiclo(String svezzamentoNumAnimaliCiclo) {
		this.svezzamentoNumAnimaliCiclo = svezzamentoNumAnimaliCiclo;
	}




	public String getSvezzamentoNumCicli() {
		return svezzamentoNumCicli;
	}




	public void setSvezzamentoNumCicli(String svezzamentoNumCicli) {
		this.svezzamentoNumCicli = svezzamentoNumCicli;
	}




	public String getSvezzamentoFessurato() {
		return svezzamentoFessurato;
	}




	public void setSvezzamentoFessurato(String svezzamentoFessurato) {
		this.svezzamentoFessurato = svezzamentoFessurato;
	}




	public String getSvezzamentoParzialmenteFessurato() {
		return svezzamentoParzialmenteFessurato;
	}




	public void setSvezzamentoParzialmenteFessurato(String svezzamentoParzialmenteFessurato) {
		this.svezzamentoParzialmenteFessurato = svezzamentoParzialmenteFessurato;
	}




	public String getSvezzamentoPieno() {
		return svezzamentoPieno;
	}




	public void setSvezzamentoPieno(String svezzamentoPieno) {
		this.svezzamentoPieno = svezzamentoPieno;
	}




	public String getSvezzamentoParzialmenteGrigliato() {
		return svezzamentoParzialmenteGrigliato;
	}




	public void setSvezzamentoParzialmenteGrigliato(String svezzamentoParzialmenteGrigliato) {
		this.svezzamentoParzialmenteGrigliato = svezzamentoParzialmenteGrigliato;
	}




	public String getSvezzamentoLettiera() {
		return svezzamentoLettiera;
	}




	public void setSvezzamentoLettiera(String svezzamentoLettiera) {
		this.svezzamentoLettiera = svezzamentoLettiera;
	}




	public String getIngrassoSuiniPresenti() {
		return ingrassoSuiniPresenti;
	}




	public void setIngrassoSuiniPresenti(String ingrassoSuiniPresenti) {
		this.ingrassoSuiniPresenti = ingrassoSuiniPresenti;
	}




	public String getIngrassoTutto() {
		return ingrassoTutto;
	}




	public void setIngrassoTutto(String ingrassoTutto) {
		this.ingrassoTutto = ingrassoTutto;
	}




	public String getIngrassoSuiniMorti() {
		return ingrassoSuiniMorti;
	}




	public void setIngrassoSuiniMorti(String ingrassoSuiniMorti) {
		this.ingrassoSuiniMorti = ingrassoSuiniMorti;
	}




	public String getIngrassoNumAnimaliCiclo() {
		return ingrassoNumAnimaliCiclo;
	}




	public void setIngrassoNumAnimaliCiclo(String ingrassoNumAnimaliCiclo) {
		this.ingrassoNumAnimaliCiclo = ingrassoNumAnimaliCiclo;
	}




	public String getIngrassoNumCicli() {
		return ingrassoNumCicli;
	}




	public void setIngrassoNumCicli(String ingrassoNumCicli) {
		this.ingrassoNumCicli = ingrassoNumCicli;
	}




	public String getIngrassoFessurato() {
		return ingrassoFessurato;
	}




	public void setIngrassoFessurato(String ingrassoFessurato) {
		this.ingrassoFessurato = ingrassoFessurato;
	}




	public String getIngrassoPieno() {
		return ingrassoPieno;
	}




	public void setIngrassoPieno(String ingrassoPieno) {
		this.ingrassoPieno = ingrassoPieno;
	}




	public String getIngrassoParzialmenteFessurato() {
		return ingrassoParzialmenteFessurato;
	}




	public void setIngrassoParzialmenteFessurato(String ingrassoParzialmenteFessurato) {
		this.ingrassoParzialmenteFessurato = ingrassoParzialmenteFessurato;
	}




	public String getIngrassoParzialmenteGrigliato() {
		return ingrassoParzialmenteGrigliato;
	}




	public void setIngrassoParzialmenteGrigliato(String ingrassoParzialmenteGrigliato) {
		this.ingrassoParzialmenteGrigliato = ingrassoParzialmenteGrigliato;
	}




	public String getIngrassoLettiera() {
		return ingrassoLettiera;
	}




	public void setIngrassoLettiera(String ingrassoLettiera) {
		this.ingrassoLettiera = ingrassoLettiera;
	}




	public String getPresenzaGruppiAnimaliCodaTagliata() {
		return presenzaGruppiAnimaliCodaTagliata;
	}




	public void setPresenzaGruppiAnimaliCodaTagliata(String presenzaGruppiAnimaliCodaTagliata) {
		this.presenzaGruppiAnimaliCodaTagliata = presenzaGruppiAnimaliCodaTagliata;
	}




	public String getPresenzaAnimaliCodaTagliata() {
		return presenzaAnimaliCodaTagliata;
	}




	public void setPresenzaAnimaliCodaTagliata(String presenzaAnimaliCodaTagliata) {
		this.presenzaAnimaliCodaTagliata = presenzaAnimaliCodaTagliata;
	}




	public String getPresenzaAnimaliTipiche() {
		return presenzaAnimaliTipiche;
	}




	public void setPresenzaAnimaliTipiche(String presenzaAnimaliTipiche) {
		this.presenzaAnimaliTipiche = presenzaAnimaliTipiche;
	}




	public String getUtilizzoAnestetici() {
		return utilizzoAnestetici;
	}




	public void setUtilizzoAnestetici(String utilizzoAnestetici) {
		this.utilizzoAnestetici = utilizzoAnestetici;
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
		
		pst = db.prepareStatement("update chk_bns_mod_ist_v6_suini set trashed_date = now() where id_chk_bns_mod_ist = ? and trashed_date is null");
		pst.setInt(1, this.idChkBnsModIst);
		pst.executeUpdate();
		
		pst = db.prepareStatement("update chk_bns_risposte_v6 set trashed_date = now() where id_chk_bns_mod_ist = ? and trashed_date is null");
		pst.setInt(1, this.idChkBnsModIst);
		pst.executeUpdate();
		
		pst = db.prepareStatement("update chk_bns_boxes_v6 set trashed_date = now() where id_chk_bns_mod_ist = ? and trashed_date is null");
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
	
	pst = db.prepareStatement("INSERT INTO public.chk_bns_mod_ist_v6_suini("+
            "id_chk_bns_mod_ist, entered_by, num_checklist, "+
            "veterinario_ispettore, extrapiano, cens_capi, cens_decessi, cens_scrofette, "+
            "cens_nascite, cens_scrofe, cens_verri, cens_cinghiali, cens_magroncelli, "+
            "cens_magroni, cens_lattonzoli, cens_grassi, scrofe_morte_anno, "+
            "suinetti_svezzati_anno, svezzamento_suini_presenti, svezzamento_tutto, "+
            "svezzamento_suini_morti, svezzamento_num_animali_ciclo, svezzamento_num_cicli, "+
            "svezzamento_fessurato, svezzamento_parzialmente_fessurato, svezzamento_pieno, "+
            "svezzamento_parzialmente_grigliato, svezzamento_lettiera, ingrasso_suini_presenti, "+
            "ingrasso_tutto, ingrasso_suini_morti, ingrasso_num_animali_ciclo, "+
            "ingrasso_num_cicli, ingrasso_fessurato, ingrasso_pieno, ingrasso_parzialmente_fessurato, "+
            "ingrasso_parzialmente_grigliato, ingrasso_lettiera, presenza_gruppi_animali_coda_tagliata, "+
            "presenza_animali_coda_tagliata, presenza_animali_tipiche, utilizzo_anestetici, "+
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
            "?, ?, ?, ?, "+
            "?, ?, ?, "+
            "?, ?, ?, "+
            "?, ?, ?, "+
            "?, ?, ?, "+
            "?, ?, ?, "+
            "?, ?, ?, ?, "+
            "?, ?, ?, "+
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
		pst.setString(++i, this.censCapi);
		pst.setString(++i, this.censDecessi);
		pst.setString(++i, this.censScrofette);
		pst.setString(++i, this.censNascite);
		pst.setString(++i, this.censScrofe);
		pst.setString(++i, this.censVerri);
		pst.setString(++i, this.censCinghiali);
		pst.setString(++i, this.censMagroncelli);
		pst.setString(++i, this.censMagroni);
		pst.setString(++i, this.censLattonzoli);
		pst.setString(++i, this.censGrassi);
		pst.setString(++i, this.scrofeMorteAnno);
		pst.setString(++i, this.suinettiSvezzatiAnno);
		pst.setString(++i, this.svezzamentoSuiniPresenti);
		pst.setString(++i, this.svezzamentoTutto);
		pst.setString(++i, this.svezzamentoSuiniMorti);
		pst.setString(++i, this.svezzamentoNumAnimaliCiclo);
		pst.setString(++i, this.svezzamentoNumCicli);
		pst.setString(++i, this.svezzamentoFessurato);
		pst.setString(++i, this.svezzamentoParzialmenteFessurato);
		pst.setString(++i, this.svezzamentoPieno);
		pst.setString(++i, this.svezzamentoParzialmenteGrigliato);
		pst.setString(++i, this.svezzamentoLettiera);
		pst.setString(++i, this.ingrassoSuiniPresenti);
		pst.setString(++i, this.ingrassoTutto);
		pst.setString(++i, this.ingrassoSuiniMorti);
		pst.setString(++i, this.ingrassoNumAnimaliCiclo);
		pst.setString(++i, this.ingrassoNumCicli);
		pst.setString(++i, this.ingrassoFessurato);
		pst.setString(++i, this.ingrassoPieno);
		pst.setString(++i, this.ingrassoParzialmenteFessurato);
		pst.setString(++i, this.ingrassoParzialmenteGrigliato);
		pst.setString(++i, this.ingrassoLettiera);
		pst.setString(++i, this.presenzaGruppiAnimaliCodaTagliata);
		pst.setString(++i, this.presenzaAnimaliCodaTagliata);
		pst.setString(++i, this.presenzaAnimaliTipiche);
		pst.setString(++i, this.utilizzoAnestetici);
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
	
	System.out.println("INSERISCO I NUOVI BOX");

	for (int b = 0 ; b<boxes.size(); b++) {
		Box box = (Box) boxes.get(b);
		box.setEnteredBy(this.enteredBy);
		box.setIdChkBnsModIst(this.idChkBnsModIst);
		box.setIndice(b);
		box.insert(db);
	}
	
	}
		
		
	}
	
	
	public ChecklistIstanza_Suini(Connection db, int idControllo, int codAllegato) throws SQLException {
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
		PreparedStatement pst = db.prepareStatement("select * from chk_bns_mod_ist_v6_suini where id_chk_bns_mod_ist = ? and trashed_date is null");
		pst.setInt(1, this.idChkBnsModIst);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			buildRecord(rs);
			setRisposte(db);
			setBoxes(db);
		}
				
	}




	private void setBoxes(Connection db) throws SQLException {
		this.boxes = Box.queryListByIdChkBnsModIst(db, this.idChkBnsModIst);
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
		this.censCapi = rs.getString("cens_capi");
		this.censDecessi = rs.getString("cens_decessi");
		this.censScrofette = rs.getString("cens_scrofette");
		this.censNascite = rs.getString("cens_nascite");
		this.censScrofe = rs.getString("cens_scrofe");
		this.censVerri = rs.getString("cens_verri");
		this.censCinghiali = rs.getString("cens_cinghiali");
		this.censMagroncelli = rs.getString("cens_magroncelli");
		this.censMagroni = rs.getString("cens_magroni");
		this.censLattonzoli = rs.getString("cens_lattonzoli");
		this.censGrassi = rs.getString("cens_grassi");
		this.scrofeMorteAnno = rs.getString("scrofe_morte_anno");
		this.suinettiSvezzatiAnno = rs.getString("suinetti_svezzati_anno");
		this.svezzamentoSuiniPresenti = rs.getString("svezzamento_suini_presenti");
		this.svezzamentoTutto = rs.getString("svezzamento_tutto");
		this.svezzamentoSuiniMorti = rs.getString("svezzamento_suini_morti");
		this.svezzamentoNumAnimaliCiclo = rs.getString("svezzamento_num_animali_ciclo");
		this.svezzamentoNumCicli = rs.getString("svezzamento_num_cicli");
		this.svezzamentoFessurato = rs.getString("svezzamento_fessurato");
		this.svezzamentoParzialmenteFessurato = rs.getString("svezzamento_parzialmente_fessurato");
		this.svezzamentoPieno = rs.getString("svezzamento_pieno");
		this.svezzamentoParzialmenteGrigliato = rs.getString("svezzamento_parzialmente_grigliato");
		this.svezzamentoLettiera = rs.getString("svezzamento_lettiera");
		this.ingrassoSuiniPresenti = rs.getString("ingrasso_suini_presenti");
		this.ingrassoTutto = rs.getString("ingrasso_tutto");
		this.ingrassoSuiniMorti = rs.getString("ingrasso_suini_morti");
		this.ingrassoNumAnimaliCiclo = rs.getString("ingrasso_num_animali_ciclo");
		this.ingrassoNumCicli = rs.getString("ingrasso_num_cicli");
		this.ingrassoFessurato = rs.getString("ingrasso_fessurato");
		this.ingrassoParzialmenteFessurato = rs.getString("ingrasso_parzialmente_fessurato");
		this.ingrassoPieno = rs.getString("ingrasso_pieno");
		this.ingrassoParzialmenteGrigliato = rs.getString("ingrasso_parzialmente_grigliato");
		this.ingrassoLettiera = rs.getString("ingrasso_lettiera");
		this.presenzaAnimaliCodaTagliata = rs.getString("presenza_animali_coda_tagliata");
		this.presenzaGruppiAnimaliCodaTagliata = rs.getString("presenza_gruppi_animali_coda_tagliata");
		this.presenzaAnimaliTipiche = rs.getString("presenza_animali_tipiche");
		this.utilizzoAnestetici = rs.getString("utilizzo_anestetici");
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