/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcf.modules.checklist_benessere.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.darkhorseventures.framework.actions.ActionContext;
import com.darkhorseventures.framework.beans.GenericBean;

public class ChecklistIstanzaCGO_2018 extends GenericBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 /**
	   *  Constructor for the Organization object, creates an empty Organization
	   *
	   * @since    1.0
	   */
	  public ChecklistIstanzaCGO_2018() { }
	  
	private int id;
	private int idChkBnsModIst;
	
	private Boolean appartenenteCondizionalita;
	
	private String criteriUtilizzati;
	private String altroCriterioDescrizione;

	private int numeroCapiPresenti;
	private int numeroCapiEffettivamentePresenti;
	private int numeroCapiControllati;
	
	private String puntoNote;
	private Boolean esitoControllo;
	private String intenzionalita;
	private Boolean esitoControlloTse;
	private String intenzionalitaTse;
	private Boolean riscontroNonConformita;
	
	private String evidenzeBenessere;
	private String evidenzeIdentificazione;
	private String evidenzeSostanze;
	private String prescrizioniSicurezza;
	
	private String prescrizioniSicurezzaNote;
	private String prescrizioniSicurezzaData;
	
	
	
	private String prescrizioniTse;
	private String prescrizioniTseNote;
	private String prescrizioniTseData;
	private String bloccoMovimentazioni;
	private String amministrativaPecuniaria;
	private String abbattimentoCapi;
	private String sequestroCapi;
	private String altroCapi;
	private String altroSpecificareDesc;
	private String noteControllore;
	private String noteDetentore;
	
	private String dataPrimoControlloLoco;
	private String nomeResponsabilePrimoControlloLoco;
	private String nomeControllorePrimoControlloLoco;
	
	private String prescrizioniEseguiteSicurezza;
	private String dataVerificaLoco;
	private String nomeResponsabileVerificaLoco;
	private String nomeControlloreVerificaLoco;
	private String prescrizioniEseguiteTse;
	private String dataVerificaLocoTse;
	private String nomeResponsabileVerificaLocoTse;
	private String nomeControlloreVerificaLocoTse;
	private String dataChiusura;
		
	
	protected void buildRecord(ResultSet rs) throws SQLException {
		 
		 //chk_bns_mod_ist table
		 id = rs.getInt("id");
		 idChkBnsModIst = rs.getInt("id_chk_bns_mod_ist");

		 appartenenteCondizionalita=rs.getBoolean("appartenenteCondizionalita");
		 criteriUtilizzati=rs.getString("criteriUtilizzati");
		 altroCriterioDescrizione=rs.getString("altroCriterioDescrizione");

		 numeroCapiPresenti=rs.getInt("numeroCapiPresenti");
		 numeroCapiEffettivamentePresenti=rs.getInt("numeroCapiEffettivamentePresenti");
		 numeroCapiControllati=rs.getInt("numeroCapiControllati");
		 puntoNote=rs.getString("puntoNote");
		 esitoControllo=rs.getBoolean("esitoControllo");
		 intenzionalita=rs.getString("intenzionalita");
		 esitoControlloTse=rs.getBoolean("esitoControlloTse");
		 intenzionalitaTse=rs.getString("intenzionalitaTse");
		 riscontroNonConformita=rs.getBoolean("riscontroNonConformita");
		 evidenzeBenessere=rs.getString("evidenzeBenessere");
		 evidenzeIdentificazione=rs.getString("evidenzeIdentificazione");
		 evidenzeSostanze=rs.getString("evidenzeSostanze");
		 prescrizioniSicurezza=rs.getString("prescrizioniSicurezza");
		 prescrizioniSicurezzaNote=rs.getString("prescrizioniSicurezzaNote");
		 prescrizioniSicurezzaData=rs.getString("prescrizioniSicurezzaData");
		 prescrizioniTse=rs.getString("prescrizioniTse");
		 prescrizioniTseNote=rs.getString("prescrizioniTseNote");
		 prescrizioniTseData=rs.getString("prescrizioniTseData");
		 bloccoMovimentazioni=rs.getString("bloccoMovimentazioni");
		 amministrativaPecuniaria=rs.getString("amministrativaPecuniaria");
		 abbattimentoCapi=rs.getString("abbattimentoCapi");
		 sequestroCapi=rs.getString("sequestroCapi");
		 altroCapi=rs.getString("altroCapi");
		 altroSpecificareDesc=rs.getString("altroSpecificareDesc");
		 noteControllore=rs.getString("noteControllore");
		 noteDetentore=rs.getString("noteDetentore");
		 dataPrimoControlloLoco=rs.getString("dataPrimoControlloLoco");
		 nomeResponsabilePrimoControlloLoco=rs.getString("nomeResponsabilePrimoControlloLoco");
		 nomeControllorePrimoControlloLoco=rs.getString("nomeControllorePrimoControlloLoco");
		 prescrizioniEseguiteSicurezza=rs.getString("prescrizioniEseguiteSicurezza");
		 dataVerificaLoco=rs.getString("dataVerificaLoco");
		 nomeResponsabileVerificaLoco=rs.getString("nomeResponsabileVerificaLoco");
		 nomeControlloreVerificaLoco=rs.getString("nomeControlloreVerificaLoco");
		 prescrizioniEseguiteTse=rs.getString("prescrizioniEseguiteTse");
		 dataVerificaLocoTse=rs.getString("dataVerificaLocoTse");
		 nomeResponsabileVerificaLocoTse=rs.getString("nomeResponsabileVerificaLocoTse");
		 nomeControlloreVerificaLocoTse=rs.getString("nomeControlloreVerificaLocoTse");
		 dataChiusura=rs.getString("dataChiusura");

			    
	 }
	

	public ChecklistIstanzaCGO_2018(Connection db, int idChkBnsModIst) 
		throws SQLException {
			
			  
		    if (idChkBnsModIst == -1) {
		      throw new SQLException("Invalid ChkIstID");
		    } 
		    PreparedStatement pst = db.prepareStatement(
		        "select * from chk_bns_mod_ist_cgo_2018 " +
		        "where id_chk_bns_mod_ist = ? ");

		    pst.setInt(1, idChkBnsModIst);
		    
		    ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		      buildRecord(rs);
		    }
		    
		    rs.close();
		    pst.close();
			  
	}
	  
	
	
	public ChecklistIstanzaCGO_2018(ActionContext context) {
		
		setAppartenenteCondizionalita(context.getRequest().getParameter("appartenenteCondizionalita"));
		setCriteriUtilizzati(context.getRequest().getParameter("criteriUtilizzati"));
		setAltroCriterioDescrizione(context.getRequest().getParameter("altroCriterioDescrizione"));
		setNumeroCapiPresenti(context.getRequest().getParameter("numeroCapiPresenti"));
		setNumeroCapiEffettivamentePresenti(context.getRequest().getParameter("numeroCapiEffettivamentePresenti"));
		setNumeroCapiControllati(context.getRequest().getParameter("numeroCapiControllati"));
		setPuntoNote(context.getRequest().getParameter("puntoNote"));
		setEsitoControllo(context.getRequest().getParameter("esitoControllo"));
		setIntenzionalita(context.getRequest().getParameter("intenzionalita"));
		setEsitoControlloTse(context.getRequest().getParameter("esitoControlloTse"));
		setIntenzionalitaTse(context.getRequest().getParameter("intenzionalitaTse"));
		setRiscontroNonConformita(context.getRequest().getParameter("riscontroNonConformita"));
		setEvidenzeBenessere(context.getRequest().getParameter("evidenzeBenessere"));
		setEvidenzeIdentificazione(context.getRequest().getParameter("evidenzeIdentificazione"));
		setEvidenzeSostanze(context.getRequest().getParameter("evidenzeSostanze"));
		setPrescrizioniSicurezza(context.getRequest().getParameter("prescrizioniSicurezza"));
		setPrescrizioniSicurezzaNote(context.getRequest().getParameter("prescrizioniSicurezzaNote"));
		setPrescrizioniSicurezzaData(context.getRequest().getParameter("prescrizioniSicurezzaData"));
		setPrescrizioniTse(context.getRequest().getParameter("prescrizioniTse"));
		setPrescrizioniTseNote(context.getRequest().getParameter("prescrizioniTseNote"));
		setPrescrizioniTseData(context.getRequest().getParameter("prescrizioniTseData"));
		setBloccoMovimentazioni(context.getRequest().getParameter("bloccoMovimentazioni"));
		setAmministrativaPecuniaria(context.getRequest().getParameter("amministrativaPecuniaria"));
		setAbbattimentoCapi(context.getRequest().getParameter("abbattimentoCapi"));
		setSequestroCapi(context.getRequest().getParameter("sequestroCapi"));
		setAltroCapi(context.getRequest().getParameter("altroCapi"));
		setAltroSpecificareDesc(context.getRequest().getParameter("altroSpecificareDesc"));
		setNoteControllore(context.getRequest().getParameter("noteControllore"));
		setNoteDetentore(context.getRequest().getParameter("noteDetentore"));
		setDataPrimoControlloLoco(context.getRequest().getParameter("dataPrimoControlloLoco"));
		setNomeResponsabilePrimoControlloLoco(context.getRequest().getParameter("nomeResponsabilePrimoControlloLoco"));
		setNomeControllorePrimoControlloLoco(context.getRequest().getParameter("nomeControllorePrimoControlloLoco"));
		setPrescrizioniEseguiteSicurezza(context.getRequest().getParameter("prescrizioniEseguiteSicurezza"));
		setDataVerificaLoco(context.getRequest().getParameter("dataVerificaLoco"));
		setNomeResponsabileVerificaLoco(context.getRequest().getParameter("nomeResponsabileVerificaLoco"));
		setNomeControlloreVerificaLoco(context.getRequest().getParameter("nomeControlloreVerificaLoco"));
		setPrescrizioniEseguiteTse(context.getRequest().getParameter("prescrizioniEseguiteTse"));
		setDataVerificaLocoTse(context.getRequest().getParameter("dataVerificaLocoTse"));
		setNomeResponsabileVerificaLocoTse(context.getRequest().getParameter("nomeResponsabileVerificaLocoTse"));
		setNomeControlloreVerificaLocoTse(context.getRequest().getParameter("nomeControlloreVerificaLocoTse"));
		setDataChiusura(context.getRequest().getParameter("dataChiusura"));

		

	}



	public boolean upsert(Connection db) throws SQLException {
	    
		int idRecuperato = -1;
		PreparedStatement pst = db.prepareStatement("select id from chk_bns_mod_ist_cgo_2018 where id_chk_bns_mod_ist = ?");
		pst.setInt(1, idChkBnsModIst);
		ResultSet rs = pst.executeQuery();
		
		if (rs.next())
			idRecuperato = rs.getInt("id");
		
		if (idRecuperato > 0)
			update(db, idRecuperato);
		else
			insert(db);
	
		return true;
	  }

	
	 public boolean update(Connection db, int idRecuperato) throws SQLException {
				 
		 String sql = "update chk_bns_mod_ist_cgo_2018 set "
		 		+ "appartenenteCondizionalita = ?, criteriUtilizzati = ?, altroCriterioDescrizione = ?, numeroCapiPresenti = ?, numeroCapiEffettivamentePresenti = ?, "
		 		+ "numeroCapiControllati = ?, puntoNote = ?, esitoControllo = ?, intenzionalita = ?, esitoControlloTse = ?,"
		 		+ "intenzionalitaTse = ?, riscontroNonConformita = ?, evidenzeBenessere = ?, evidenzeIdentificazione = ?, evidenzeSostanze = ?, "
		 		+ "prescrizioniSicurezza = ?, prescrizioniSicurezzaNote = ?,prescrizioniSicurezzaData = ?, prescrizioniTse = ?, prescrizioniTseNote = ?,"
		 		+ "prescrizioniTseData = ?, bloccoMovimentazioni = ?, amministrativaPecuniaria = ?, abbattimentoCapi = ?, sequestroCapi = ?, "
		 		+ "altroCapi = ?, altroSpecificareDesc = ?, noteControllore = ?,noteDetentore = ?,dataPrimoControlloLoco = ?,nomeResponsabilePrimoControlloLoco = ?, "
		 		+ "nomeControllorePrimoControlloLoco = ?, prescrizioniEseguiteSicurezza = ?,dataVerificaLoco = ?,nomeResponsabileVerificaLoco = ?,nomeControlloreVerificaLoco = ?,"
		 		+ "prescrizioniEseguiteTse = ?, dataVerificaLocoTse = ?,nomeResponsabileVerificaLocoTse = ?,nomeControlloreVerificaLocoTse = ?, dataChiusura = ?"
		 		+ " where id = ? "; 
	
		 PreparedStatement pst = db.prepareStatement(sql);
		 int i = 0;
		 
		 pst.setObject(++i, appartenenteCondizionalita);
		 pst.setString(++i, criteriUtilizzati);
		 pst.setString(++i, altroCriterioDescrizione);

		 pst.setInt(++i,numeroCapiPresenti);
		 pst.setInt(++i,numeroCapiEffettivamentePresenti);
		 
		 pst.setInt(++i,numeroCapiControllati);
		 pst.setString(++i,puntoNote);
		 pst.setObject(++i,esitoControllo);
		 pst.setObject(++i,intenzionalita);
		 pst.setObject(++i,esitoControlloTse);
		 
		 pst.setObject(++i,intenzionalitaTse);
		 pst.setObject(++i,riscontroNonConformita);
		 pst.setObject(++i,evidenzeBenessere);
		 pst.setObject(++i,evidenzeIdentificazione);
		 pst.setObject(++i,evidenzeSostanze);
		 
		 pst.setObject(++i,prescrizioniSicurezza);
		 pst.setString(++i,prescrizioniSicurezzaNote);
		 pst.setString(++i,prescrizioniSicurezzaData);
		 pst.setObject(++i,prescrizioniTse);
		 pst.setString(++i,prescrizioniTseNote);
		 
		 pst.setString(++i,prescrizioniTseData);
		 pst.setString(++i,bloccoMovimentazioni);
		 pst.setString(++i,amministrativaPecuniaria);
		 pst.setString(++i,abbattimentoCapi);
		 pst.setString(++i,sequestroCapi);
		 
		 pst.setString(++i,altroCapi);
		 pst.setString(++i,altroSpecificareDesc);
		 pst.setString(++i,noteControllore);
		 pst.setString(++i,noteDetentore);
		 pst.setString(++i,dataPrimoControlloLoco);
		 pst.setString(++i,nomeResponsabilePrimoControlloLoco);
		 
		 pst.setString(++i,nomeControllorePrimoControlloLoco);
		 pst.setObject(++i,prescrizioniEseguiteSicurezza);
		 pst.setString(++i,dataVerificaLoco);
		 pst.setString(++i,nomeResponsabileVerificaLoco);
		 pst.setString(++i,nomeControlloreVerificaLoco);
		 
		 pst.setObject(++i,prescrizioniEseguiteTse);
		 pst.setString(++i,dataVerificaLocoTse);
		 pst.setString(++i,nomeResponsabileVerificaLocoTse);
		 pst.setString(++i,nomeControlloreVerificaLocoTse);
		 pst.setString(++i,dataChiusura);
	
		 pst.setInt(++i, idRecuperato);
		 pst.executeUpdate();
		 
		 return true;
		 
		 
	 
	 }

	 
	 public boolean insert(Connection db) throws SQLException {
		 
		 String sql = "insert into chk_bns_mod_ist_cgo_2018 ("
		 		+ "id_chk_bns_mod_ist, appartenenteCondizionalita , criteriUtilizzati, altroCriterioDescrizione, "
		 		+ "numeroCapiPresenti , "
		 		+ "numeroCapiEffettivamentePresenti , numeroCapiControllati , puntoNote , esitoControllo , intenzionalita , "
		 		+ "esitoControlloTse , intenzionalitaTse , riscontroNonConformita , evidenzeBenessere , evidenzeIdentificazione , "
		 		+ "evidenzeSostanze ,prescrizioniSicurezza , prescrizioniSicurezzaNote ,prescrizioniSicurezzaData , prescrizioniTse , "
		 		+ "prescrizioniTseNote , prescrizioniTseData , bloccoMovimentazioni , amministrativaPecuniaria , abbattimentoCapi , "
		 		+ "sequestroCapi , altroCapi, altroSpecificareDesc , noteControllore ,noteDetentore ,dataPrimoControlloLoco ,"
		 		+ "nomeResponsabilePrimoControlloLoco ,nomeControllorePrimoControlloLoco ,prescrizioniEseguiteSicurezza ,dataVerificaLoco ,nomeResponsabileVerificaLoco ,"
		 		+ "nomeControlloreVerificaLoco ,prescrizioniEseguiteTse ,dataVerificaLocoTse ,nomeResponsabileVerificaLocoTse ,nomeControlloreVerificaLocoTse ,"
		 		+ "dataChiusura) values ("
		 		+ "?, ?, ?, ?,    "
		 		+ "?,   "
		 		+ "?, ?, ?, ?, ?,   "
		 		+ "?, ?, ?, ?, ?,   "
		 		+ "?, ?, ?, ?, ?,   "
		 		+ "?, ?, ?, ?, ?,   "
		 		+ "?, ?, ?, ?, ?, ?,   "
		 		+ "?, ?, ?, ?, ?,   "
		 		+ "?, ?, ?, ?, ?,"
		 		+ "?                );";
	
		 PreparedStatement pst = db.prepareStatement(sql);
		 int i = 0;
		 
		 pst.setInt(++i, idChkBnsModIst);
		 pst.setObject(++i, appartenenteCondizionalita);
		 pst.setString(++i, criteriUtilizzati);
		 pst.setString(++i, altroCriterioDescrizione);
		 
		 pst.setInt(++i,numeroCapiPresenti);
		 
		 pst.setInt(++i,numeroCapiEffettivamentePresenti);
		 pst.setInt(++i,numeroCapiControllati);
		 pst.setString(++i,puntoNote);
		 pst.setObject(++i,esitoControllo);
		 pst.setObject(++i,intenzionalita);
		 
		 pst.setObject(++i,esitoControlloTse);
		 pst.setObject(++i,intenzionalitaTse);
		 pst.setObject(++i,riscontroNonConformita);
		 pst.setObject(++i,evidenzeBenessere);
		 pst.setObject(++i,evidenzeIdentificazione);
		 
		 pst.setObject(++i,evidenzeSostanze);
		 pst.setObject(++i,prescrizioniSicurezza);
		 pst.setString(++i,prescrizioniSicurezzaNote);
		 pst.setString(++i,prescrizioniSicurezzaData);
		 pst.setObject(++i,prescrizioniTse);
		 
		 pst.setString(++i,prescrizioniTseNote);
		 pst.setString(++i,prescrizioniTseData);
		 pst.setString(++i,bloccoMovimentazioni);
		 pst.setString(++i,amministrativaPecuniaria);
		 pst.setString(++i,abbattimentoCapi);
		 
		 pst.setString(++i,sequestroCapi);
		 pst.setString(++i,altroCapi);
		 pst.setString(++i,altroSpecificareDesc);
		 pst.setString(++i,noteControllore);
		 pst.setString(++i,noteDetentore);
		 pst.setString(++i,dataPrimoControlloLoco);
		 
		 pst.setString(++i,nomeResponsabilePrimoControlloLoco);
		 pst.setString(++i,nomeControllorePrimoControlloLoco);
		 pst.setObject(++i,prescrizioniEseguiteSicurezza);
		 pst.setString(++i,dataVerificaLoco);
		 pst.setString(++i,nomeResponsabileVerificaLoco);
		 
		 pst.setString(++i,nomeControlloreVerificaLoco);
		 pst.setObject(++i,prescrizioniEseguiteTse);
		 pst.setString(++i,dataVerificaLocoTse);
		 pst.setString(++i,nomeResponsabileVerificaLocoTse);
		 pst.setString(++i,nomeControlloreVerificaLocoTse);
		 
		 pst.setString(++i,dataChiusura);
		 
		 pst.executeUpdate();
		 return true;
		 
		 
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


	public Boolean getAppartenenteCondizionalita() {
		return appartenenteCondizionalita;
	}


	public void setAppartenenteCondizionalita(Boolean appartenenteCondizionalita) {
		this.appartenenteCondizionalita = appartenenteCondizionalita;
	}

	public void setAppartenenteCondizionalita(String parameter) {
		if (parameter!=null && (parameter.equals("si") || parameter.equals("on")))
			appartenenteCondizionalita = true;
		else if (parameter!=null && parameter.equals("no"))
			appartenenteCondizionalita = false;
	}

	public int getNumeroCapiPresenti() {
		return numeroCapiPresenti;
	}


	public void setNumeroCapiPresenti(int numeroCapiPresenti) {
		this.numeroCapiPresenti = numeroCapiPresenti;
	}

	public void setNumeroCapiPresenti(String numeroCapiPresenti) {
		try {this.numeroCapiPresenti = Integer.parseInt(numeroCapiPresenti);} catch(Exception e) {};
	}

	
	public int getNumeroCapiEffettivamentePresenti() {
		return numeroCapiEffettivamentePresenti;
	}


	public void setNumeroCapiEffettivamentePresenti(int numeroCapiEffettivamentePresenti) {
		this.numeroCapiEffettivamentePresenti = numeroCapiEffettivamentePresenti;
	}

	public void setNumeroCapiEffettivamentePresenti(String numeroCapiEffettivamentePresenti) {
		try {this.numeroCapiEffettivamentePresenti = Integer.parseInt(numeroCapiEffettivamentePresenti);} catch(Exception e) {};
	}
	
	public int getNumeroCapiControllati() {
		return numeroCapiControllati;
	}


	public void setNumeroCapiControllati(int numeroCapiControllati) {
		this.numeroCapiControllati = numeroCapiControllati;
	}

	public void setNumeroCapiControllati(String numeroCapiControllati) {
		try {this.numeroCapiControllati = Integer.parseInt(numeroCapiControllati);} catch(Exception e) {};
	}

	public String getPuntoNote() {
		return puntoNote;
	}


	public void setPuntoNote(String puntoNote) {
		this.puntoNote = puntoNote;
	}


	public Boolean getEsitoControllo() {
		return esitoControllo;
	}


	public void setEsitoControllo(Boolean esitoControllo) {
		this.esitoControllo = esitoControllo;
	}

	public void setEsitoControllo(String parameter) {
		if (parameter!=null && (parameter.equals("si") || parameter.equals("on")))
			esitoControllo = true;
		else if (parameter!=null && parameter.equals("no"))
			esitoControllo = false;
	}

	public String getIntenzionalita() {
		return intenzionalita;
	}


	public void setIntenzionalita(String intenzionalita) {
		this.intenzionalita = intenzionalita;
	}


	public Boolean getEsitoControlloTse() {
		return esitoControlloTse;
	}


	public void setEsitoControlloTse(Boolean esitoControlloTse) {
		this.esitoControlloTse = esitoControlloTse;
	}

	public void setEsitoControlloTse(String parameter) {
		if (parameter!=null && (parameter.equals("si") || parameter.equals("on")))
			esitoControlloTse = true;
		else if (parameter!=null && parameter.equals("no"))
			esitoControlloTse = false;
	}


	public String getIntenzionalitaTse() {
		return intenzionalitaTse;
	}


	public void setIntenzionalitaTse(String intenzionalitaTse) {
		this.intenzionalitaTse = intenzionalitaTse;
	}
	
	public Boolean getRiscontroNonConformita() {
		return riscontroNonConformita;
	}


	public void setRiscontroNonConformita(Boolean riscontroNonConformita) {
		this.riscontroNonConformita = riscontroNonConformita;
	}

	public void setRiscontroNonConformita(String parameter) {
		if (parameter!=null && (parameter.equals("si") || parameter.equals("on")))
			riscontroNonConformita = true;
		else if (parameter!=null && parameter.equals("no"))
			riscontroNonConformita = false;
	}

	public String getEvidenzeBenessere() {
		return evidenzeBenessere;
	}


	public void setEvidenzeBenessere(String evidenzeBenessere) {
		this.evidenzeBenessere = evidenzeBenessere;
	}

	public String getEvidenzeIdentificazione() {
		return evidenzeIdentificazione;
	}


	public void setEvidenzeIdentificazione(String evidenzeIdentificazione) {
		this.evidenzeIdentificazione = evidenzeIdentificazione;
	}


	public String getEvidenzeSostanze() {
		return evidenzeSostanze;
	}


	public void setEvidenzeSostanze(String evidenzeSostanze) {
		this.evidenzeSostanze = evidenzeSostanze;
	}
	

	public String getPrescrizioniSicurezza() {
		return prescrizioniSicurezza; 
	}


	public void setPrescrizioniSicurezza(String prescrizioniSicurezza) {
		this.prescrizioniSicurezza = prescrizioniSicurezza;
	}

	public String getPrescrizioniSicurezzaNote() {
		return prescrizioniSicurezzaNote;
	}


	public void setPrescrizioniSicurezzaNote(String prescrizioniSicurezzaNote) {
		this.prescrizioniSicurezzaNote = prescrizioniSicurezzaNote;
	}


	public String getPrescrizioniSicurezzaData() {
		return prescrizioniSicurezzaData;
	}


	public void setPrescrizioniSicurezzaData(String prescrizioniSicurezzaData) {
		this.prescrizioniSicurezzaData = prescrizioniSicurezzaData;
	}


	public String getPrescrizioniTse() {
		return prescrizioniTse;
	}


	public void setPrescrizioniTse(String prescrizioniTse) {
		this.prescrizioniTse = prescrizioniTse;
	}

	public String getPrescrizioniTseNote() {
		return prescrizioniTseNote;
	}


	public void setPrescrizioniTseNote(String prescrizioniTseNote) {
		this.prescrizioniTseNote = prescrizioniTseNote;
	}


	public String getPrescrizioniTseData() {
		return prescrizioniTseData;
	}


	public void setPrescrizioniTseData(String prescrizioniTseData) {
		this.prescrizioniTseData = prescrizioniTseData;
	}


	public String getBloccoMovimentazioni() {
		return bloccoMovimentazioni;
	}


	public void setBloccoMovimentazioni(String bloccoMovimentazioni) {
		this.bloccoMovimentazioni = bloccoMovimentazioni;
	}


	public String getAmministrativaPecuniaria() {
		return amministrativaPecuniaria;
	}


	public void setAmministrativaPecuniaria(String amministrativaPecuniaria) {
		this.amministrativaPecuniaria = amministrativaPecuniaria;
	}


	public String getAbbattimentoCapi() {
		return abbattimentoCapi;
	}


	public void setAbbattimentoCapi(String abbattimentoCapi) {
		this.abbattimentoCapi = abbattimentoCapi;
	}


	public String getSequestroCapi() {
		return sequestroCapi;
	}


	public void setSequestroCapi(String sequestroCapi) {
		this.sequestroCapi = sequestroCapi;
	}


	public String getNoteControllore() {
		return noteControllore;
	}


	public void setNoteControllore(String noteControllore) {
		this.noteControllore = noteControllore;
	}


	public String getNoteDetentore() {
		return noteDetentore;
	}


	public void setNoteDetentore(String noteDetentore) {
		this.noteDetentore = noteDetentore;
	}


	public String getDataPrimoControlloLoco() {
		return dataPrimoControlloLoco;
	}


	public void setDataPrimoControlloLoco(String dataPrimoControlloLoco) {
		this.dataPrimoControlloLoco = dataPrimoControlloLoco;
	}


	public String getNomeResponsabilePrimoControlloLoco() {
		return nomeResponsabilePrimoControlloLoco;
	}


	public void setNomeResponsabilePrimoControlloLoco(String nomeResponsabilePrimoControlloLoco) {
		this.nomeResponsabilePrimoControlloLoco = nomeResponsabilePrimoControlloLoco;
	}


	public String getNomeControllorePrimoControlloLoco() {
		return nomeControllorePrimoControlloLoco;
	}


	public void setNomeControllorePrimoControlloLoco(String nomeControllorePrimoControlloLoco) {
		this.nomeControllorePrimoControlloLoco = nomeControllorePrimoControlloLoco;
	}


	public String getPrescrizioniEseguiteSicurezza() {
		return prescrizioniEseguiteSicurezza;
	}


	public void setPrescrizioniEseguiteSicurezza(String prescrizioniEseguiteSicurezza) {
		this.prescrizioniEseguiteSicurezza = prescrizioniEseguiteSicurezza;
	}
	


	public String getDataVerificaLoco() {
		return dataVerificaLoco;
	}


	public void setDataVerificaLoco(String dataVerificaLoco) {
		this.dataVerificaLoco = dataVerificaLoco;
	}


	public String getNomeResponsabileVerificaLoco() {
		return nomeResponsabileVerificaLoco;
	}


	public void setNomeResponsabileVerificaLoco(String nomeResponsabileVerificaLoco) {
		this.nomeResponsabileVerificaLoco = nomeResponsabileVerificaLoco;
	}


	public String getNomeControlloreVerificaLoco() {
		return nomeControlloreVerificaLoco;
	}


	public void setNomeControlloreVerificaLoco(String nomeControlloreVerificaLoco) {
		this.nomeControlloreVerificaLoco = nomeControlloreVerificaLoco;
	}


	public String getPrescrizioniEseguiteTse() {
		return prescrizioniEseguiteTse;
	}


	public void setPrescrizioniEseguiteTse(String prescrizioniEseguiteTse) {
		this.prescrizioniEseguiteTse = prescrizioniEseguiteTse;
	}

	public String getDataVerificaLocoTse() {
		return dataVerificaLocoTse;
	}


	public void setDataVerificaLocoTse(String dataVerificaLocoTse) {
		this.dataVerificaLocoTse = dataVerificaLocoTse;
	}


	public String getNomeResponsabileVerificaLocoTse() {
		return nomeResponsabileVerificaLocoTse;
	}


	public void setNomeResponsabileVerificaLocoTse(String nomeResponsabileVerificaLocoTse) {
		this.nomeResponsabileVerificaLocoTse = nomeResponsabileVerificaLocoTse;
	}


	public String getNomeControlloreVerificaLocoTse() {
		return nomeControlloreVerificaLocoTse;
	}


	public void setNomeControlloreVerificaLocoTse(String nomeControlloreVerificaLocoTse) {
		this.nomeControlloreVerificaLocoTse = nomeControlloreVerificaLocoTse;
	}


	public String getDataChiusura() {
		return dataChiusura;
	}


	public void setDataChiusura(String dataChiusura) {
		this.dataChiusura = dataChiusura;
	}


	public String getCriteriUtilizzati() {
		return criteriUtilizzati;
	}


	public void setCriteriUtilizzati(String criteriUtilizzati) {
		this.criteriUtilizzati = criteriUtilizzati;
	}


	public String getAltroCapi() {
		return altroCapi;
	}


	public void setAltroCapi(String altroCapi) {
		this.altroCapi = altroCapi;
	}


	public String getAltroSpecificareDesc() {
		return altroSpecificareDesc;
	}


	public void setAltroSpecificareDesc(String altroSpecificareDesc) {
		this.altroSpecificareDesc = altroSpecificareDesc;
	}


	public String getAltroCriterioDescrizione() { 
		return altroCriterioDescrizione;
	}


	public void setAltroCriterioDescrizione(String altroCriterioDescrizione) {
		this.altroCriterioDescrizione = altroCriterioDescrizione;
	}


	
	
	    
}
