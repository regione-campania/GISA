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
import java.util.logging.Logger;

import org.aspcf.modules.controlliufficiali.base.Organization;

public class AnagraficaPagatore {
	
	Logger logger = Logger.getLogger("MainLogger");

	private int id = -1;
	private String tipoPagatore = null;
	private String ragioneSocialeNominativo = null;
	private String partitaIvaCodiceFiscale = null;
	private String indirizzo = null;
	private String civico = null;
	private String cap = null;
	private String comune = null;
	private String codProvincia = null;
	private String nazione = null;
	private String domicilioDigitale = null;
	private String telefono = null;
	
	private NotificaPagatore notifica = new NotificaPagatore();
		
	public AnagraficaPagatore(){
		
	}
	
	
	public AnagraficaPagatore(String tipoPagatore, String piva, String nome, String indirizzo, String civico,String cap, String comune, String provincia, String nazione, String mail, String telefono) {
		this.tipoPagatore= tipoPagatore;
		this.partitaIvaCodiceFiscale= piva;
		this.ragioneSocialeNominativo=nome;
		this.indirizzo=indirizzo;
		this.civico=civico;
		this.cap=cap;
		this.comune=comune;
		this.codProvincia=provincia;
		this.nazione=nazione;
		this.domicilioDigitale=mail;
		this.telefono=telefono;
	}


	public AnagraficaPagatore(Connection db, int id) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from pagopa_anagrafica_pagatori where id = ?;");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
		
	}


	public AnagraficaPagatore(Connection db, int idSanzione, String trasgressoreObbligato) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select p.* from pagopa_anagrafica_pagatori p join pagopa_mapping_sanzioni_pagatori m on p.id = m.id_pagatore where m.id_sanzione = ? and m.trashed_date is null and m.trasgressore_obbligato = ?");
		pst.setInt(1, idSanzione);
		pst.setString(2, trasgressoreObbligato);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			buildRecord(rs);
			setNotifica(db, idSanzione);
		}
		
	}


	private void setNotifica(Connection db, int idSanzione) throws SQLException {
		NotificaPagatore not = new NotificaPagatore(db, idSanzione, id);
		this.notifica = not;
		
	}


	public void upsert(Connection db, int idSanzione, String trasgressoreObbligato, int userId) throws SQLException {
		PreparedStatement pst = null;
		
		pst = db.prepareStatement("update pagopa_anagrafica_pagatori set trashed_date = now(), note_hd = concat_ws(';', note_hd, 'Pagatore cancellato in seguito a modifica dato.') where id in (select id_pagatore from pagopa_mapping_sanzioni_pagatori where id_sanzione = ? and trasgressore_obbligato = ? and trashed_date is null) ");
		pst.setInt(1, idSanzione);
		pst.setString(2, trasgressoreObbligato);
		pst.executeUpdate();
		
		pst = db.prepareStatement("update pagopa_mapping_sanzioni_pagatori set trashed_date = now(), note_hd = concat_ws(';', note_hd, 'Relazione cancellata in seguito a modifica dato.') where id_sanzione = ? and trasgressore_obbligato = ? and trashed_date is null ");
		pst.setInt(1, idSanzione);
		pst.setString(2, trasgressoreObbligato);
		pst.executeUpdate();
		
		if (tipoPagatore!=null){
			pst = db.prepareStatement("insert into pagopa_anagrafica_pagatori (tipo_pagatore , piva_cf , ragione_sociale_nominativo , indirizzo , civico , cap , comune , cod_provincia , nazione , domicilio_digitale , telefono, enteredby , note_hd) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) returning id as id_inserito");
			int i = 0;
			pst.setString(++i,tipoPagatore );
			pst.setString(++i, partitaIvaCodiceFiscale);
			pst.setString(++i, ragioneSocialeNominativo);
			pst.setString(++i, indirizzo);
			pst.setString(++i, civico);
			pst.setString(++i, cap);
			pst.setString(++i,comune );
			pst.setString(++i, codProvincia);
			pst.setString(++i, nazione );
			pst.setString(++i, domicilioDigitale);
			pst.setString(++i, telefono);
			pst.setInt(++i, userId);
			pst.setString(++i, "Pagatore inserito.");
			ResultSet rs = pst.executeQuery();
			
			if (rs.next()){
				
				this.id = rs.getInt("id_inserito");
				
				pst = db.prepareStatement("insert into pagopa_mapping_sanzioni_pagatori (id_sanzione, id_pagatore, trasgressore_obbligato, enteredby , note_hd) values (?, ?, ?, ?, ?)");
				i = 0;
				pst.setInt(++i,idSanzione );
				pst.setInt(++i, rs.getInt("id_inserito"));
				pst.setString(++i, trasgressoreObbligato);
				pst.setInt(++i, userId);
				pst.setString(++i, "Relazione inserita.");
				pst.executeUpdate();
			}
		}
		
	}
	

	public Logger getLogger() {
		return logger;
	}


	public void setLogger(Logger logger) {
		this.logger = logger;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTipoPagatore() {
		return tipoPagatore;
	}


	public void setTipoPagatore(String tipoPagatore) {
		this.tipoPagatore = tipoPagatore;
	}


	public String getRagioneSocialeNominativo() {
		return ragioneSocialeNominativo;
	}


	public void setRagioneSocialeNominativo(String ragioneSocialeNominativo) {
		this.ragioneSocialeNominativo = ragioneSocialeNominativo;
	}


	public String getPartitaIvaCodiceFiscale() {
		return partitaIvaCodiceFiscale;
	}


	public void setPartitaIvaCodiceFiscale(String partitaIvaCodiceFiscale) {
		this.partitaIvaCodiceFiscale = partitaIvaCodiceFiscale;
	}


	public String getIndirizzo() {
		return indirizzo;
	}


	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}


	public String getCivico() {
		return civico;
	}


	public void setCivico(String civico) {
		this.civico = civico;
	}


	public String getCap() {
		return cap;
	}


	public void setCap(String cap) {
		this.cap = cap;
	}


	public String getComune() {
		return comune;
	}


	public void setComune(String comune) {
		this.comune = comune;
	}


	public String getCodProvincia() {
		return codProvincia;
	}


	public void setCodProvincia(String codProvincia) {
		this.codProvincia = codProvincia;
	}


	public String getNazione() {
		return nazione;
	}


	public void setNazione(String nazione) {
		this.nazione = nazione;
	}


	public String getDomicilioDigitale() {
		return domicilioDigitale;
	}


	public void setDomicilioDigitale(String domicilioDigitale) {
		this.domicilioDigitale = domicilioDigitale;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}


	public boolean isVuoto() {
		if (ragioneSocialeNominativo!=null && !ragioneSocialeNominativo.equals(""))
			return false;
		if (partitaIvaCodiceFiscale!=null && !partitaIvaCodiceFiscale.equals(""))
			return false;
		if (indirizzo!=null && !indirizzo.equals(""))
			return false;
		if (civico!=null && !civico.equals(""))
			return false;
		if (comune!=null && !comune.equals(""))
			return false;
		if (codProvincia!=null && !codProvincia.equals(""))
			return false;
		if (cap!=null && !cap.equals(""))
			return false;
		if (nazione!=null && !nazione.equals(""))
			return false;
		if (domicilioDigitale!=null && !domicilioDigitale.equals(""))
			return false;
		return true;
	}


	public void recuperaDatiDefault(Connection db, int idSanzione) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from pagopa_get_dati_pagatore_default(?);");
		pst.setInt(1, idSanzione);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
		
	}


	private void buildRecord(ResultSet rs) throws SQLException {
		try {rs.findColumn("id");this.id = rs.getInt("id");} catch (SQLException sqlex) {}		
		this.tipoPagatore= rs.getString("tipo_pagatore");
		this.partitaIvaCodiceFiscale=  rs.getString("piva_cf");
		this.ragioneSocialeNominativo=rs.getString("ragione_sociale_nominativo");
		this.indirizzo=rs.getString("indirizzo");
		this.civico=rs.getString("civico");
		this.cap=rs.getString("cap");
		this.comune=rs.getString("comune");
		this.codProvincia=rs.getString("cod_provincia");
		this.nazione=rs.getString("nazione");
		this.domicilioDigitale=rs.getString("domicilio_digitale");
		this.telefono=rs.getString("telefono");
		}


	public void recuperaDatiDefault(Connection db, Organization thisOrganization) throws SQLException {
		
		int riferimentoId = -1;
		String riferimentoIdNomeTab = "";
		
		if (thisOrganization.getIdApiario() > 0 || thisOrganization.getTipologia() == 1000) {
			riferimentoId = thisOrganization.getIdApiario();
			riferimentoIdNomeTab = "apicoltura_imprese";
			if (riferimentoId <=0)
				riferimentoId = thisOrganization.getOrgId();
		}
		else if (thisOrganization.getAltId() > 0 || thisOrganization.getTipologia() == 2000) {
			riferimentoId = thisOrganization.getAltId();
			riferimentoIdNomeTab = "sintesis_stabilimento";
			if (riferimentoId <=0)
				riferimentoId = thisOrganization.getOrgId();
		}
		else if (thisOrganization.getIdStabilimento() > 0 || thisOrganization.getTipologia() == 999){
			riferimentoId = thisOrganization.getIdStabilimento();
			riferimentoIdNomeTab = "opu_stabilimento";
			if (riferimentoId <=0)
				riferimentoId = thisOrganization.getOrgId();
		}
		else {
			riferimentoId = thisOrganization.getOrgId();
			riferimentoIdNomeTab = "organization";

		}
		
		PreparedStatement pst = db.prepareStatement("select * from pagopa_get_dati_pagatore_default_anagrafica(?, ?);");
		pst.setInt(1, riferimentoId);
		pst.setString(2, riferimentoIdNomeTab);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
		}


	public NotificaPagatore getNotifica() {
		return notifica;
	}


	public void setNotifica(NotificaPagatore notifica) {
		this.notifica = notifica;
	}

	
}


