/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.schedesupplementari.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.darkhorseventures.framework.actions.ActionContext;

public class IstanzaScheda {
	
	private int riferimentoId;
	private String riferimentoIdNomeTab;
	private int idIstanzaLinea;
	
	private String nomeLinea;
	private String ragioneSocialeImpresa;
	
	private int id;
	private int codeScheda;
	private String numScheda;
	private String descrizioneScheda;
	private String returnView;
	private int enteredBy;
	private int modifiedBy;
	
	private Timestamp entered = null;
	private Timestamp modified = null;
	private Timestamp trashedDate = null;
	
	private org.aspcfs.modules.schedesupplementari.base.tipo_scheda_formazione_iaa.DatiGenerici datiSchedaFormazioneIaa = new org.aspcfs.modules.schedesupplementari.base.tipo_scheda_formazione_iaa.DatiGenerici();
	private org.aspcfs.modules.schedesupplementari.base.tipo_scheda_lista_checkbox.DatiGenerici datiSchedaListaCheckbox = new org.aspcfs.modules.schedesupplementari.base.tipo_scheda_lista_checkbox.DatiGenerici();

	public IstanzaScheda() {
	}
	
	public IstanzaScheda(Connection db, int riferimentoId, String riferimentoIdNomeTab, int idIstanza, String numScheda) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from schede_supplementari.get_scheda_supplementare(?, ?, ?, ?)");
		pst.setInt(1,  riferimentoId);
		pst.setString(2, riferimentoIdNomeTab);
		pst.setInt(3, idIstanza);
		pst.setString(4, numScheda);
		ResultSet rs = pst.executeQuery();
		if (rs.next()){
			buildRecord(rs);
			
			if (returnView.equals("ListaCheckbox")) {
					datiSchedaListaCheckbox.queryRecord(db, this.numScheda, this.id); 
			}
			
			else if (returnView.equals("125")) {
				if (this.id>0){
					datiSchedaFormazioneIaa.queryRecord(db, this.id); 
				}
			}
			
			
			
			
			
			
			
			
			
			
			
		}
	}

	public IstanzaScheda(ResultSet rs) throws SQLException {
		buildRecord(rs);
	}

	private void buildRecord(ResultSet rs) throws SQLException {
		this.id = rs.getInt("id");
		this.riferimentoId = rs.getInt("riferimento_id");
		this.riferimentoIdNomeTab = rs.getString("riferimento_id_nome_tab");
		this.idIstanzaLinea = rs.getInt("id_istanza_linea");
		this.numScheda = rs.getString("num_scheda");
		this.codeScheda = rs.getInt("code_scheda");
		this.descrizioneScheda = rs.getString("descrizione_scheda"); 
		this.enteredBy = rs.getInt("enteredby");
		this.modifiedBy = rs.getInt("modifiedby");
		this.entered = rs.getTimestamp("entered");
		this.modified = rs.getTimestamp("modified");
		this.trashedDate = rs.getTimestamp("trashed_date");
		this.nomeLinea = rs.getString("nome_linea");
		this.ragioneSocialeImpresa = rs.getString("ragione_sociale_impresa");
		this.returnView = rs.getString("return_view");

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRiferimentoId() {
		return riferimentoId;
	}

	public void setRiferimentoId(int riferimentoId) {
		this.riferimentoId = riferimentoId;
	}

	public String getRiferimentoIdNomeTab() {
		return riferimentoIdNomeTab;
	}

	public void setRiferimentoIdNomeTab(String riferimentoIdNomeTab) {
		this.riferimentoIdNomeTab = riferimentoIdNomeTab;
	}

	public int getIdIstanzaLinea() {
		return idIstanzaLinea;
	}

	public void setIdIstanzaLinea(int idIstanzaLinea) {
		this.idIstanzaLinea = idIstanzaLinea;
	}

	public String getNumScheda() {
		return numScheda;
	}

	public void setNumScheda(String numScheda) {
		this.numScheda = numScheda;
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

	public Timestamp getEntered() {
		return entered;
	}

	public void setEntered(Timestamp entered) {
		this.entered = entered;
	}

	public Timestamp getModified() {
		return modified;
	}

	public void setModified(Timestamp modified) {
		this.modified = modified;
	}

	public Timestamp getTrashedDate() {
		return trashedDate;
	}

	public void setTrashedDate(Timestamp trashedDate) {
		this.trashedDate = trashedDate;
	}

	public int getCodeScheda() {
		return codeScheda;
	}

	public void setCodeScheda(int codeScheda) {
		this.codeScheda = codeScheda;
	}

	public String getDescrizioneScheda() {
		return descrizioneScheda;
	}

	public void setDescrizioneScheda(String descrizioneScheda) {
		this.descrizioneScheda = descrizioneScheda;
	}

	public String getNomeLinea() {
		return nomeLinea;
	}

	public void setNomeLinea(String nomeLinea) {
		this.nomeLinea = nomeLinea;
	}

	public String getRagioneSocialeImpresa() {
		return ragioneSocialeImpresa;
	}

	public void setRagioneSocialeImpresa(String ragioneSocialeImpresa) {
		this.ragioneSocialeImpresa = ragioneSocialeImpresa;
	}

	public static ArrayList<IstanzaScheda> buildListaSchede (Connection db, int riferimentoId, String riferimentoIdNomeTab) throws SQLException{
		ArrayList<IstanzaScheda> listaSchede = new ArrayList<IstanzaScheda>();
		PreparedStatement pst = db.prepareStatement("select * from schede_supplementari.get_scheda_supplementare(?, ?, null, null)");
		pst.setInt(1,  riferimentoId);
		pst.setString(2, riferimentoIdNomeTab);
		ResultSet rs = pst.executeQuery();
		while (rs.next()){
			IstanzaScheda scheda = new IstanzaScheda(rs);
			listaSchede.add(scheda);
			}
		return listaSchede;
	
		
	}

	public void insert(Connection db, int idUtente) throws SQLException {
		PreparedStatement pst = db.prepareStatement("select * from schede_supplementari.insert_istanza(?, ?, ?, ?, ?)");
		int i = 0;
		pst.setInt(++i, riferimentoId);
		pst.setString(++i, riferimentoIdNomeTab);
		pst.setInt(++i, idIstanzaLinea);
		pst.setString(++i, numScheda);
		pst.setInt(++i, idUtente);
		ResultSet rs = pst.executeQuery();
		
		if (rs.next())
			this.id = rs.getInt(1);
		
		if (this.id>0){
			
			if (this.returnView.equals("ListaCheckbox"))
				datiSchedaListaCheckbox.insert(db, this.id);
			
			else if (this.returnView.equals("125"))
				datiSchedaFormazioneIaa.insert(db, this.id);
		}

	}

	public void buildDaRequest(ActionContext context) {
		
		if (returnView.equals("ListaCheckbox"))
			datiSchedaListaCheckbox.buildDaRequest(context);
		
		else if (returnView.equals("125"))
			datiSchedaFormazioneIaa.buildDaRequest(context);
		
	}

	public org.aspcfs.modules.schedesupplementari.base.tipo_scheda_formazione_iaa.DatiGenerici getDatiSchedaFormazioneIaa() {
		return datiSchedaFormazioneIaa;
	}

	public void setDatiSchedaFormazioneIaa(
			org.aspcfs.modules.schedesupplementari.base.tipo_scheda_formazione_iaa.DatiGenerici datiSchedaFormazioneIaa) {
		this.datiSchedaFormazioneIaa = datiSchedaFormazioneIaa;
	}

	public String getReturnView() {
		return returnView;
	}

	public void setReturnView(String returnView) {
		this.returnView = returnView;
	}

	public org.aspcfs.modules.schedesupplementari.base.tipo_scheda_lista_checkbox.DatiGenerici getDatiSchedaListaCheckbox() {
		return datiSchedaListaCheckbox;
	}

	public void setDatiSchedaListaCheckbox(
			org.aspcfs.modules.schedesupplementari.base.tipo_scheda_lista_checkbox.DatiGenerici datiSchedaListaCheckbox) {
		this.datiSchedaListaCheckbox = datiSchedaListaCheckbox;
	}
}
