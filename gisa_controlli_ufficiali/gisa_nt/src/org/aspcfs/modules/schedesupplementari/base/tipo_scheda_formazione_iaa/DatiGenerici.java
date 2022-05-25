/*
Copyright (C) AGPL-3.0  

This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License along with this program. If not, see <https://www.gnu.org/licenses/>.*/
package org.aspcfs.modules.schedesupplementari.base.tipo_scheda_formazione_iaa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.darkhorseventures.framework.actions.ActionContext;

public class DatiGenerici {
	
	private int idIstanza = -1;
	private String corsoNum = null;
	private String corsoAnno = null;
	private String corsoTipo = null;
	
	private String responsabileNome = null;
	private String responsabileCognome = null;
	private String responsabileCodiceFiscale = null;
	
	private ArrayList<SedeLezioni> listaSedeLezioni = new ArrayList<SedeLezioni>();
	private ArrayList<ComponenteSegreteria> listaComponenteSegreteria = new ArrayList<ComponenteSegreteria>();
	private ArrayList<Iscritto> listaIscritto = new ArrayList<Iscritto>();
	
	public DatiGenerici(){
		
	}
	
	public DatiGenerici(Connection db, int idIstanza){
		
	}
	
	private void buildRecord (ResultSet rs) throws SQLException{
		this.idIstanza = rs.getInt("id_istanza");
		
		this.corsoNum = rs.getString("num_corso");
		this.corsoAnno = rs.getString("anno_corso");
		this.corsoTipo = rs.getString("tipo_corso");
		
		this.responsabileNome = rs.getString("nome_responsabile");
		this.responsabileCognome = rs.getString("cognome_responsabile");
		this.responsabileCodiceFiscale = rs.getString("cf_responsabile");
		
	}

	public void buildDaRequest(ActionContext context) {
		
		this.corsoNum = context.getRequest().getParameter("corsoNum");
		this.corsoAnno = context.getRequest().getParameter("corsoAnno");
		this.corsoTipo = context.getRequest().getParameter("corsoTipo");
		
		this.responsabileNome = context.getRequest().getParameter("responsabileNome");
		this.responsabileCognome = context.getRequest().getParameter("responsabileCognome");
		this.responsabileCodiceFiscale = context.getRequest().getParameter("responsabileCf");
		
		listaSedeLezioni = SedeLezioni.buildDaRequest(context);
		listaComponenteSegreteria = ComponenteSegreteria.buildDaRequest(context);
		listaIscritto = Iscritto.buildDaRequest(context);

	}

	public void insert(Connection db, int idIstanza) throws SQLException {
		
		PreparedStatement pst = db.prepareStatement("select * from schede_supplementari.insert_dati_generici_formazione_iaa(?, ?, ?, ?, ?, ?, ?)");
		int i = 0;
		pst.setInt(++i, idIstanza);
		pst.setString(++i, corsoNum);
		pst.setString(++i, corsoAnno);
		pst.setString(++i, corsoTipo);
		pst.setString(++i, responsabileNome);
		pst.setString(++i, responsabileCognome);
		pst.setString(++i, responsabileCodiceFiscale);
		pst.executeQuery();		
		
		for (int j = 0; j<listaSedeLezioni.size();j++){
			SedeLezioni sede = (SedeLezioni) listaSedeLezioni.get(j);
			sede.insert(db, idIstanza);
		}
		for (int j = 0; j<listaComponenteSegreteria.size();j++){
			ComponenteSegreteria comp = (ComponenteSegreteria) listaComponenteSegreteria.get(j);
			comp.insert(db, idIstanza);
		}
		for (int j = 0; j<listaIscritto.size();j++){
			Iscritto isc = (Iscritto) listaIscritto.get(j);
			isc.insert(db, idIstanza);
		}
		
	}

	public void queryRecord(Connection db, int idIstanza) throws SQLException { 
		PreparedStatement pst = db.prepareStatement("select * from schede_supplementari.get_dati_generici_formazione_iaa(?)");
		int i = 0;
		pst.setInt(++i, idIstanza);
		ResultSet rs = pst.executeQuery();
		if (rs.next())
			buildRecord(rs);
		
		listaSedeLezioni = SedeLezioni.queryRecord(db, idIstanza);
		listaComponenteSegreteria = ComponenteSegreteria.queryRecord(db, idIstanza);
		listaIscritto = Iscritto.queryRecord(db, idIstanza);
	}
	
	public int getIdIstanza() {
		return idIstanza;
	}

	public void setIdIstanza(int idIstanza) {
		this.idIstanza = idIstanza;
	}

	public String getCorsoNum() {
		return corsoNum;
	}

	public void setCorsoNum(String corsoNum) {
		this.corsoNum = corsoNum;
	}

	public String getCorsoAnno() {
		return corsoAnno;
	}

	public void setCorsoAnno(String corsoAnno) {
		this.corsoAnno = corsoAnno;
	}

	public String getCorsoTipo() {
		return corsoTipo;
	}

	public void setCorsoTipo(String corsoTipo) {
		this.corsoTipo = corsoTipo;
	}

	public String getResponsabileNome() {
		return responsabileNome;
	}

	public void setResponsabileNome(String responsabileNome) {
		this.responsabileNome = responsabileNome;
	}

	public String getResponsabileCognome() {
		return responsabileCognome;
	}

	public void setResponsabileCognome(String responsabileCognome) {
		this.responsabileCognome = responsabileCognome;
	}

	public String getResponsabileCodiceFiscale() {
		return responsabileCodiceFiscale;
	}

	public void setResponsabileCodiceFiscale(String responsabileCodiceFiscale) {
		this.responsabileCodiceFiscale = responsabileCodiceFiscale;
	}

	public ArrayList<SedeLezioni> getListaSedeLezioni() {
		return listaSedeLezioni;
	}

	public void setListaSedeLezioni(ArrayList<SedeLezioni> listaSedeLezioni) {
		this.listaSedeLezioni = listaSedeLezioni;
	}

	public ArrayList<ComponenteSegreteria> getListaComponenteSegreteria() {
		return listaComponenteSegreteria;
	}

	public void setListaComponenteSegreteria(ArrayList<ComponenteSegreteria> listaComponenteSegreteria) {
		this.listaComponenteSegreteria = listaComponenteSegreteria;
	}

	public ArrayList<Iscritto> getListaIscritto() {
		return listaIscritto;
	}

	public void setListaIscritto(ArrayList<Iscritto> listaIscritto) {
		this.listaIscritto = listaIscritto;
	}


	



}
