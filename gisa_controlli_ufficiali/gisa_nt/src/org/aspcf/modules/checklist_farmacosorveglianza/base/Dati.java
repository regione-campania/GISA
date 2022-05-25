/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_farmacosorveglianza.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class Dati extends GenericBean {
 	

private static final long serialVersionUID = 1L;
private int id = 1;
private int idIstanza = -1;
private String numChecklist ="";
private String veterinarioIspettore = "";
private String numDataAut = "";
private String veterinarioResponsabile="";
private String rischio ="";
private String esitoControllo="";
private String prescrizioniAssegnate="";
private String prescrizioniDescrizione="";
private String prescrizioniData="";
private String sanzioniBlocco="";
private String sanzioniAbbattimento="";
private String sanzioniAmministrativa="";
private String sanzioniSequestro="";
private String sanzioniInformativa="";
private String sanzioniInformativaDescrizione="";
private String sanzioniAltro="";
private String sanzioniAltroDescrizione="";
private String noteControllore="";
private String noteProprietario="";
private String nomePresente="";
private String nomeControllore="";
private String prescrizioniVerificaEseguite="";
private String nomePresenteVerifica="";
private String nomeControlloreVerifica="";
private String prescrizioniVerificaData="";
private String prescrizioniVerificaDescrizione="";

private String presenteScorta ="";
private int punteggioTotale = 0;
private int punteggioAggiuntivo = 0;

private String valutazioneTipologiaSuini ="";
private String valutazioneTipologiaVitelli ="";
private String valutazioneTipologiaAltriBovini ="";
private String valutazioneTipologiaBroiler ="";
private String valutazioneTipologiaOvaiole ="";
private String valutazioneTipologiaTacchini ="";
private String valutazioneTipologiaOvini ="";
private String valutazioneTipologiaBufali ="";
private String valutazioneTipologiaConigli ="";
private String valutazioneTipologiaStruzzi ="";
private String valutazioneTipologiaCavalli ="";
private String valutazioneTipologiaPesci ="";



public Dati(Connection db, ResultSet rs) throws SQLException {
	buildRecord(rs);
}


public Dati() {
	// TODO Auto-generated constructor stub
}



private void buildRecord(ResultSet rs) throws SQLException{
	this.id  = rs.getInt("id"); 
	this.idIstanza   = rs.getInt("id_istanza"); 
	this.numChecklist   = rs.getString("num_checklist"); 
	this.veterinarioIspettore   = rs.getString("veterinario_ispettore"); 
	this.numDataAut   = rs.getString("num_data_aut");
	this.veterinarioResponsabile   = rs.getString("veterinario_responsabile"); 
	this.rischio   = rs.getString("rischio"); 
	this.esitoControllo   = rs.getString("esito_controllo"); 
	this.prescrizioniAssegnate   = rs.getString("prescrizioni_assegnate"); 
	this.prescrizioniDescrizione   = rs.getString("prescrizioni_descrizione"); 
	this.prescrizioniData   = rs.getString("prescrizioni_data"); 
	this.sanzioniBlocco   = rs.getString("sanzioni_blocco"); 
	this.sanzioniAbbattimento   = rs.getString("sanzioni_abbattimento"); 
	this.sanzioniAmministrativa   = rs.getString("sanzioni_amministrativa"); 
	this.sanzioniSequestro   = rs.getString("sanzioni_sequestro"); 
	this.sanzioniInformativa   = rs.getString("sanzioni_informativa"); 
	this.sanzioniInformativaDescrizione   = rs.getString("sanzioni_informativa_descrizione"); 
	this.sanzioniAltro   = rs.getString("sanzioni_altro"); 
	this.sanzioniAltroDescrizione   = rs.getString("sanzioni_altro_descrizione"); 
	this.noteControllore   = rs.getString("note_controllore"); 
	this.noteProprietario   = rs.getString("note_proprietario"); 
	this.nomePresente   = rs.getString("nome_presente"); 
	this.nomeControllore   = rs.getString("nome_controllore");
	this.prescrizioniVerificaEseguite   = rs.getString("prescrizioni_verifica_eseguite");
	this.nomePresenteVerifica   = rs.getString("nome_presente_verifica"); 
	this.nomeControlloreVerifica   = rs.getString("nome_controllore_verifica"); 
	this.prescrizioniVerificaData   = rs.getString("prescrizioni_verifica_data"); 
	this.prescrizioniVerificaDescrizione   = rs.getString("prescrizioni_verifica_descrizione"); 
	
	this.presenteScorta = rs.getString("presente_scorta"); 
	this.punteggioTotale = rs.getInt("punteggio_totale"); 
	this.punteggioAggiuntivo = rs.getInt("punteggio_aggiuntivo"); 
	this.valutazioneTipologiaSuini = rs.getString("valutazione_tipologia_suini");  
	this.valutazioneTipologiaVitelli = rs.getString("valutazione_tipologia_vitelli");  
	this.valutazioneTipologiaAltriBovini = rs.getString("valutazione_tipologia_altri_bovini");  
	this.valutazioneTipologiaBroiler = rs.getString("valutazione_tipologia_broiler");  
	this.valutazioneTipologiaOvaiole = rs.getString("valutazione_tipologia_ovaiole");  
	this.valutazioneTipologiaTacchini = rs.getString("valutazione_tipologia_tacchini");  
	this.valutazioneTipologiaOvini = rs.getString("valutazione_tipologia_ovini");  
	this.valutazioneTipologiaBufali = rs.getString("valutazione_tipologia_bufali");  
	this.valutazioneTipologiaConigli = rs.getString("valutazione_tipologia_conigli");  
	this.valutazioneTipologiaStruzzi = rs.getString("valutazione_tipologia_struzzi");  
	this.valutazioneTipologiaCavalli = rs.getString("valutazione_tipologia_cavalli");  
	this.valutazioneTipologiaPesci = rs.getString("valutazione_tipologia_pesci");  

}


public void queryRecordByIdIstanza(Connection db, int idIstanza) throws SQLException {

	PreparedStatement pst = null;
	pst = db.prepareStatement("select * from farmacosorveglianza_dati where trashed_date is null and id_istanza = ?");
	pst.setInt(1, idIstanza);
	ResultSet rs = pst.executeQuery();
	
	if (rs.next()){
		buildRecord(rs);
	}
	}


public int getId() {
	return id;
}


public void setId(int id) {
	this.id = id;
}


public int getIdIstanza() {
	return idIstanza;
}


public void setIdIstanza(int idIstanza) {
	this.idIstanza = idIstanza;
}


public String getNumChecklist() {
	return numChecklist;
}


public void setNumChecklist(String numChecklist) {
	this.numChecklist = numChecklist;
}


public String getVeterinarioIspettore() {
	return veterinarioIspettore;
}


public void setVeterinarioIspettore(String veterinarioIspettore) {
	this.veterinarioIspettore = veterinarioIspettore;
}


public String getNumDataAut() {
	return numDataAut;
}


public void setNumDataAut(String numDataAut) {
	this.numDataAut = numDataAut;
}


public String getVeterinarioResponsabile() {
	return veterinarioResponsabile;
}


public void setVeterinarioResponsabile(String veterinarioResponsabile) {
	this.veterinarioResponsabile = veterinarioResponsabile;
}


public String getRischio() {
	return rischio;
}


public void setRischio(String rischio) {
	this.rischio = rischio;
}


public String getEsitoControllo() {
	return esitoControllo;
}


public void setEsitoControllo(String esitoControllo) {
	this.esitoControllo = esitoControllo;
}


public String getPrescrizioniAssegnate() {
	return prescrizioniAssegnate;
}


public void setPrescrizioniAssegnate(String prescrizioniAssegnate) {
	this.prescrizioniAssegnate = prescrizioniAssegnate;
}


public String getPrescrizioniDescrizione() {
	return prescrizioniDescrizione;
}


public void setPrescrizioniDescrizione(String prescrizioniDescrizione) {
	this.prescrizioniDescrizione = prescrizioniDescrizione;
}


public String getPrescrizioniData() {
	return prescrizioniData;
}


public void setPrescrizioniData(String prescrizioniData) {
	this.prescrizioniData = prescrizioniData;
}


public String getSanzioniBlocco() {
	return sanzioniBlocco;
}


public void setSanzioniBlocco(String sanzioniBlocco) {
	this.sanzioniBlocco = sanzioniBlocco;
}


public String getSanzioniAbbattimento() {
	return sanzioniAbbattimento;
}


public void setSanzioniAbbattimento(String sanzioniAbbattimento) {
	this.sanzioniAbbattimento = sanzioniAbbattimento;
}


public String getSanzioniAmministrativa() {
	return sanzioniAmministrativa;
}


public void setSanzioniAmministrativa(String sanzioniAmministrativa) {
	this.sanzioniAmministrativa = sanzioniAmministrativa;
}


public String getSanzioniSequestro() {
	return sanzioniSequestro;
}


public void setSanzioniSequestro(String sanzioniSequestro) {
	this.sanzioniSequestro = sanzioniSequestro;
}


public String getSanzioniInformativa() {
	return sanzioniInformativa;
}


public void setSanzioniInformativa(String sanzioniInformativa) {
	this.sanzioniInformativa = sanzioniInformativa;
}


public String getSanzioniInformativaDescrizione() {
	return sanzioniInformativaDescrizione;
}


public void setSanzioniInformativaDescrizione(String sanzioniInformativaDescrizione) {
	this.sanzioniInformativaDescrizione = sanzioniInformativaDescrizione;
}


public String getSanzioniAltro() {
	return sanzioniAltro;
}


public void setSanzioniAltro(String sanzioniAltro) {
	this.sanzioniAltro = sanzioniAltro;
}


public String getSanzioniAltroDescrizione() {
	return sanzioniAltroDescrizione;
}


public void setSanzioniAltroDescrizione(String sanzioniAltroDescrizione) {
	this.sanzioniAltroDescrizione = sanzioniAltroDescrizione;
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


public String getNomePresente() {
	return nomePresente;
}


public void setNomePresente(String nomePresente) {
	this.nomePresente = nomePresente;
}


public String getNomeControllore() {
	return nomeControllore;
}


public void setNomeControllore(String nomeControllore) {
	this.nomeControllore = nomeControllore;
}


public String getPrescrizioniVerificaEseguite() {
	return prescrizioniVerificaEseguite;
}


public void setPrescrizioniVerificaEseguite(String prescrizioniVerificaEseguite) {
	this.prescrizioniVerificaEseguite = prescrizioniVerificaEseguite;
}


public String getNomePresenteVerifica() {
	return nomePresenteVerifica;
}


public void setNomePresenteVerifica(String nomePresenteVerifica) {
	this.nomePresenteVerifica = nomePresenteVerifica;
}


public String getNomeControlloreVerifica() {
	return nomeControlloreVerifica;
}


public void setNomeControlloreVerifica(String nomeControlloreVerifica) {
	this.nomeControlloreVerifica = nomeControlloreVerifica;
}


public String getPrescrizioniVerificaData() {
	return prescrizioniVerificaData;
}


public void setPrescrizioniVerificaData(String prescrizioniVerificaData) {
	this.prescrizioniVerificaData = prescrizioniVerificaData;
}


public String getPrescrizioniVerificaDescrizione() {
	return prescrizioniVerificaDescrizione;
}


public void setPrescrizioniVerificaDescrizione(String prescrizioniVerificaDescrizione) {
	this.prescrizioniVerificaDescrizione = prescrizioniVerificaDescrizione;
}
	
public String getPresenteScorta() {
	return presenteScorta;
}


public void setPresenteScorta(String presenteScorta) {
	this.presenteScorta = presenteScorta;
}


public int getPunteggioTotale() {
	return punteggioTotale;
}


public void setPunteggioTotale(int punteggioTotale) { 
	this.punteggioTotale = punteggioTotale;
}


public int getPunteggioAggiuntivo() {
	return punteggioAggiuntivo;
}


public void setPunteggioAggiuntivo(int punteggioAggiuntivo) {
	this.punteggioAggiuntivo = punteggioAggiuntivo;
}


public String getValutazioneTipologiaSuini() {
	return valutazioneTipologiaSuini;
}


public void setValutazioneTipologiaSuini(String valutazioneTipologiaSuini) {
	this.valutazioneTipologiaSuini = valutazioneTipologiaSuini;
}


public String getValutazioneTipologiaVitelli() {
	return valutazioneTipologiaVitelli;
}


public void setValutazioneTipologiaVitelli(String valutazioneTipologiaVitelli) {
	this.valutazioneTipologiaVitelli = valutazioneTipologiaVitelli;
}


public String getValutazioneTipologiaAltriBovini() {
	return valutazioneTipologiaAltriBovini;
}


public void setValutazioneTipologiaAltriBovini(String valutazioneTipologiaAltriBovini) {
	this.valutazioneTipologiaAltriBovini = valutazioneTipologiaAltriBovini;
}


public String getValutazioneTipologiaBroiler() {
	return valutazioneTipologiaBroiler;
}


public void setValutazioneTipologiaBroiler(String valutazioneTipologiaBroiler) {
	this.valutazioneTipologiaBroiler = valutazioneTipologiaBroiler;
}


public String getValutazioneTipologiaOvaiole() {
	return valutazioneTipologiaOvaiole;
}


public void setValutazioneTipologiaOvaiole(String valutazioneTipologiaOvaiole) {
	this.valutazioneTipologiaOvaiole = valutazioneTipologiaOvaiole;
}


public String getValutazioneTipologiaTacchini() {
	return valutazioneTipologiaTacchini;
}


public void setValutazioneTipologiaTacchini(String valutazioneTipologiaTacchini) {
	this.valutazioneTipologiaTacchini = valutazioneTipologiaTacchini;
}


public String getValutazioneTipologiaOvini() {
	return valutazioneTipologiaOvini;
}


public void setValutazioneTipologiaOvini(String valutazioneTipologiaOvini) {
	this.valutazioneTipologiaOvini = valutazioneTipologiaOvini;
}


public String getValutazioneTipologiaBufali() {
	return valutazioneTipologiaBufali;
}


public void setValutazioneTipologiaBufali(String valutazioneTipologiaBufali) {
	this.valutazioneTipologiaBufali = valutazioneTipologiaBufali;
}


public String getValutazioneTipologiaConigli() {
	return valutazioneTipologiaConigli;
}


public void setValutazioneTipologiaConigli(String valutazioneTipologiaConigli) {
	this.valutazioneTipologiaConigli = valutazioneTipologiaConigli;
}


public String getValutazioneTipologiaStruzzi() {
	return valutazioneTipologiaStruzzi;
}


public void setValutazioneTipologiaStruzzi(String valutazioneTipologiaStruzzi) {
	this.valutazioneTipologiaStruzzi = valutazioneTipologiaStruzzi;
}


public String getValutazioneTipologiaCavalli() {
	return valutazioneTipologiaCavalli;
}


public void setValutazioneTipologiaCavalli(String valutazioneTipologiaCavalli) {
	this.valutazioneTipologiaCavalli = valutazioneTipologiaCavalli;
}


public String getValutazioneTipologiaPesci() {
	return valutazioneTipologiaPesci;
}


public void setValutazioneTipologiaPesci(String valutazioneTipologiaPesci) {
	this.valutazioneTipologiaPesci = valutazioneTipologiaPesci;
}


public void recuperaDaForm(ActionContext context) throws SQLException {
	
	numChecklist = context.getRequest().getParameter("numChecklist");
	veterinarioIspettore  = context.getRequest().getParameter("veterinarioIspettore");
	numDataAut  = context.getRequest().getParameter("numDataAut");
	veterinarioResponsabile= context.getRequest().getParameter("veterinarioResponsabile");
	rischio = context.getRequest().getParameter("rischio");
	esitoControllo= context.getRequest().getParameter("esitoControllo");
	prescrizioniAssegnate= context.getRequest().getParameter("prescrizioniAssegnate");
	prescrizioniDescrizione= context.getRequest().getParameter("prescrizioniDescrizione");
	prescrizioniData= context.getRequest().getParameter("prescrizioniData");
	sanzioniBlocco= context.getRequest().getParameter("sanzioniBlocco");
	sanzioniAbbattimento= context.getRequest().getParameter("sanzioniAbbattimento");
	sanzioniAmministrativa= context.getRequest().getParameter("sanzioniAmministrativa");
	sanzioniSequestro= context.getRequest().getParameter("sanzioniSequestro");
	sanzioniInformativa= context.getRequest().getParameter("sanzioniInformativa");
	sanzioniInformativaDescrizione= context.getRequest().getParameter("sanzioniInformativaDescrizione");
	sanzioniAltro= context.getRequest().getParameter("sanzioniAltro");
	sanzioniAltroDescrizione= context.getRequest().getParameter("sanzioniAltroDescrizione");
	noteControllore= context.getRequest().getParameter("noteControllore");
	noteProprietario= context.getRequest().getParameter("noteProprietario");
	nomePresente= context.getRequest().getParameter("nomePresente");
	nomeControllore= context.getRequest().getParameter("nomeControllore");
	prescrizioniVerificaEseguite= context.getRequest().getParameter("prescrizioniVerificaEseguite");
	nomePresenteVerifica= context.getRequest().getParameter("nomePresenteVerifica");
	nomeControlloreVerifica= context.getRequest().getParameter("nomeControlloreVerifica");
	prescrizioniVerificaData= context.getRequest().getParameter("prescrizioniVerificaData");
	prescrizioniVerificaDescrizione= context.getRequest().getParameter("prescrizioniVerificaDescrizione");

	presenteScorta = context.getRequest().getParameter("presenteScorta");
	try { punteggioTotale =  Integer.parseInt(context.getRequest().getParameter("punteggioTotale"));} catch (Exception e) {}
	try { punteggioAggiuntivo =  Integer.parseInt(context.getRequest().getParameter("punteggioAggiuntivo"));} catch (Exception e) {}
	valutazioneTipologiaSuini = context.getRequest().getParameter("valutazioneTipologiaSuini");
	valutazioneTipologiaVitelli = context.getRequest().getParameter("valutazioneTipologiaVitelli");
	valutazioneTipologiaAltriBovini = context.getRequest().getParameter("valutazioneTipologiaAltriBovini");
	valutazioneTipologiaBroiler = context.getRequest().getParameter("valutazioneTipologiaBroiler");
	valutazioneTipologiaOvaiole = context.getRequest().getParameter("valutazioneTipologiaOvaiole");
	valutazioneTipologiaTacchini = context.getRequest().getParameter("valutazioneTipologiaTacchini");
	valutazioneTipologiaOvini = context.getRequest().getParameter("valutazioneTipologiaOvini");
	valutazioneTipologiaBufali = context.getRequest().getParameter("valutazioneTipologiaBufali");
	valutazioneTipologiaConigli = context.getRequest().getParameter("valutazioneTipologiaConigli");
	valutazioneTipologiaStruzzi = context.getRequest().getParameter("valutazioneTipologiaStruzzi");
	valutazioneTipologiaCavalli = context.getRequest().getParameter("valutazioneTipologiaCavalli");
	valutazioneTipologiaPesci = context.getRequest().getParameter("valutazioneTipologiaPesci");
	
}


public void insertDati(Connection db, int idIstanza) throws SQLException {
	PreparedStatement pst = db.prepareStatement("insert into farmacosorveglianza_dati (id_istanza, num_checklist, veterinario_ispettore, num_data_aut, "
			+ "veterinario_responsabile, rischio, esito_controllo, prescrizioni_assegnate, prescrizioni_descrizione, prescrizioni_data, "
			+ "sanzioni_blocco, sanzioni_abbattimento, sanzioni_amministrativa, sanzioni_sequestro, sanzioni_informativa, sanzioni_informativa_descrizione, "
			+ "sanzioni_altro, sanzioni_altro_descrizione, note_controllore, note_proprietario, nome_presente, nome_controllore, "
			+ "prescrizioni_verifica_eseguite, nome_presente_verifica, nome_controllore_verifica, prescrizioni_verifica_data, "
			+ "prescrizioni_verifica_descrizione,"
			+ "presente_scorta, punteggio_totale,punteggio_aggiuntivo, valutazione_tipologia_suini, valutazione_tipologia_vitelli, valutazione_tipologia_altri_bovini, valutazione_tipologia_broiler, valutazione_tipologia_ovaiole, valutazione_tipologia_tacchini, valutazione_tipologia_ovini,  valutazione_tipologia_bufali, valutazione_tipologia_conigli, valutazione_tipologia_struzzi, valutazione_tipologia_cavalli, valutazione_tipologia_pesci) "
			+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");
	
	int i = 0;
	
	pst.setInt(++i, idIstanza);
	pst.setString(++i, numChecklist);
	pst.setString(++i, veterinarioIspettore);
	pst.setString(++i, numDataAut);
	pst.setString(++i, veterinarioResponsabile);
	pst.setString(++i, rischio);
	pst.setString(++i, esitoControllo);
	pst.setString(++i, prescrizioniAssegnate);
	pst.setString(++i, prescrizioniDescrizione);
	pst.setString(++i, prescrizioniData);
	pst.setString(++i, sanzioniBlocco);
	pst.setString(++i, sanzioniAbbattimento);
	pst.setString(++i, sanzioniAmministrativa);
	pst.setString(++i, sanzioniSequestro);
	pst.setString(++i, sanzioniInformativa);
	pst.setString(++i, sanzioniInformativaDescrizione);
	pst.setString(++i, sanzioniAltro);
	pst.setString(++i, sanzioniAltroDescrizione);
	pst.setString(++i, noteControllore);
	pst.setString(++i, noteProprietario);
	pst.setString(++i, nomePresente);
	pst.setString(++i, nomeControllore);
	pst.setString(++i, prescrizioniVerificaEseguite);
	pst.setString(++i, nomePresenteVerifica);
	pst.setString(++i, nomeControlloreVerifica);
	pst.setString(++i, prescrizioniVerificaData);
	pst.setString(++i, prescrizioniVerificaDescrizione);
	
	pst.setString(++i, presenteScorta);
	pst.setInt(++i,  punteggioTotale);
	pst.setInt(++i,  punteggioAggiuntivo);
	pst.setString(++i, valutazioneTipologiaSuini);
	pst.setString(++i, valutazioneTipologiaVitelli);
	pst.setString(++i, valutazioneTipologiaAltriBovini);
	pst.setString(++i, valutazioneTipologiaBroiler);
	pst.setString(++i, valutazioneTipologiaOvaiole);
	pst.setString(++i, valutazioneTipologiaTacchini);
	pst.setString(++i, valutazioneTipologiaOvini);
	pst.setString(++i, valutazioneTipologiaBufali);
	pst.setString(++i, valutazioneTipologiaConigli);
	pst.setString(++i, valutazioneTipologiaStruzzi);
	pst.setString(++i, valutazioneTipologiaCavalli);
	pst.setString(++i, valutazioneTipologiaPesci);
	pst.execute();
	
}

}
